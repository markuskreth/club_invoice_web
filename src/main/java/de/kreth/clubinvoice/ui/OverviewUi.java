package de.kreth.clubinvoice.ui;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kreth.clubinvoice.business.ArticleBusiness;
import de.kreth.clubinvoice.business.OverviewBusiness;
import de.kreth.clubinvoice.business.PropertyStore;
import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.ui.components.ArticleDialog;
import de.kreth.clubinvoice.ui.components.FooterComponent;
import de.kreth.clubinvoice.ui.components.InvoiceDialog;
import de.kreth.clubinvoice.ui.components.InvoiceGrid;
import de.kreth.clubinvoice.ui.components.InvoiceItemDialog;
import de.kreth.clubinvoice.ui.components.InvoiceItemGrid;
import de.kreth.clubinvoice.ui.components.UserDetailsDialog;
import de.kreth.clubinvoice.ui.presentation.DataPresentators;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;

public class OverviewUi extends BorderLayout implements InvoiceUi {

	private static final long serialVersionUID = 318645298331660865L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OverviewUi.class);

	private final User user;
	private final OverviewBusiness business;
	private final ResourceBundle resBundle;
	private final PropertyStore store;
	private Grid<InvoiceItem> gridItems;
	private InvoiceGrid gridInvoices;

	public OverviewUi(PropertyStore store, OverviewBusiness business) {
		super();
		this.business = business;
		this.store = store;
		this.user = (User) store.getAttribute(PropertyStore.LOGGED_IN_USER);
		resBundle = ResourceBundle.getBundle("/application");
	}

	@Override
	public void setContent(UI ui, VaadinRequest vaadinRequest) {

		HorizontalLayout head = createHeadView(ui, vaadinRequest);

		VerticalLayout left = createItemsView(ui);
		left.setSizeFull();

		VerticalLayout right = createInvoicesView(ui);
		right.setSizeFull();

		HorizontalLayout main = new HorizontalLayout();
		main.setSizeFull();
		main.addComponents(left, right);

		Layout footer = createFooter();

		addComponent(head, BorderLayout.Constraint.PAGE_START);

		addComponent(main, BorderLayout.Constraint.CENTER);

		addComponent(footer, BorderLayout.Constraint.PAGE_END);

		ui.setContent(this);

		if (user.getBank() == null || user.getAdress() == null) {
			LOGGER.info("User data incomplete, showing user detail dialog.");
			showUserDetailDialog(ui);
		}
	}

	private Layout createFooter() {
		return new FooterComponent();
	}

	public VerticalLayout createInvoicesView(final UI ui) {
		gridInvoices = new InvoiceGrid(resBundle);
		gridInvoices.setSizeFull();
		gridInvoices.setItems(loadInvoices());
		gridInvoices.addItemClickListener(itemEv -> {

			LOGGER.debug("Invoice clicked, opening {}", itemEv.getItem());
			InvoiceDialog dlg = new InvoiceDialog(resBundle);
			dlg.center();
			dlg.setInvoice(itemEv.getItem());
			dlg.setOkVisible(false);
			ui.addWindow(dlg);
		});
		gridInvoices.setStyleName(STYLE_BORDERED);

		Button createInvoice = new Button(
				resBundle.getString(CAPTION_INVOICE_CREATE));
		createInvoice.addClickListener(ev -> {

			String invoiceNo = business.createNextInvoiceId(user,
					resBundle.getString(CAPTION_INVOICE_PATTERN));

			LOGGER.info("Creating new invoice no: {}", invoiceNo);

			Set<InvoiceItem> selectedItems = gridItems.getSelectedItems();
			if (selectedItems.isEmpty()) {
				LOGGER.error("No items selected for invoice.");
				Notification.show(
						resBundle.getString(ERROR_INVOICE_TITLE_NOITEMS),
						resBundle.getString(ERROR_INVOICE_TEXT_NOITEMS),
						Notification.Type.ERROR_MESSAGE);
				return;
			}
			final Invoice inv = new Invoice();
			inv.setInvoiceId(invoiceNo);
			inv.setInvoiceDate(LocalDateTime.now());
			inv.setItems(selectedItems);
			inv.setUser(user);
			InvoiceDialog dlg = new InvoiceDialog(resBundle);
			dlg.center();
			dlg.setInvoice(inv);
			dlg.addOkClickListener(okEv -> {
				LOGGER.info("Storing invoice {}", inv);
				business.save(inv);
				gridItems.setItems(loadItems());
				gridInvoices.setItems(loadInvoices());
			});
			ui.addWindow(dlg);

		});
		VerticalLayout right = new VerticalLayout();
		right.setStyleName(STYLE_BORDERED);
		right.addComponents(createInvoice, gridInvoices);
		return right;
	}

	public VerticalLayout createItemsView(final UI ui) {
		gridItems = new InvoiceItemGrid<>(resBundle);
		gridItems.setSizeFull();
		gridItems.setSelectionMode(SelectionMode.MULTI);
		gridItems.setItems(loadItems());
		gridItems.addItemClickListener(ev -> {

			LOGGER.debug("Opening item for edit: {}" + ev.getItem());
			final InvoiceItemDialog dlg = new InvoiceItemDialog(resBundle);
			dlg.setItem(ev.getItem());
			dlg.addOkClickListener(e -> {
				business.save(dlg.getItem());
				gridItems.setItems(loadItems());
			});
			if (ev.getItem().getInvoice() == null) {

				dlg.addDeleteClickListener(e -> {
					InvoiceItem item = dlg.getItem();
					LOGGER.warn("Showing delete dialog for {}" + item);
					MessageBox.createQuestion().asModal(true)
							.withCaption(
									resBundle.getString("message.delete.title"))
							.withMessage(MessageFormat.format(
									resBundle.getString("message.delete.text"),
									DataPresentators.toPresentation(item)))
							.withCancelButton(ButtonOption.closeOnClick(true))
							.withOkButton(() -> {
								LOGGER.warn("Deleting {}", item);
								business.delete(item);
								gridItems.setItems(loadItems());
								dlg.close();
							}, ButtonOption.focus()).open();

				});
			}

			dlg.setSelectableArticles(business.getArticles(user));
			ui.addWindow(dlg);

		});
		gridItems.setStyleName(STYLE_BORDERED);

		Button addItem = new Button(
				resBundle.getString(CAPTION_INVOICEITEM_ADD));
		addItem.addClickListener(ev -> {
			final InvoiceItemDialog dlg = new InvoiceItemDialog(resBundle);
			LOGGER.info("Creating new Item.");
			dlg.addOkClickListener(e -> {
				business.save(dlg.getItem());
				gridItems.setItems(loadItems());
			});
			dlg.setSelectableArticles(business.getArticles(user));
			ui.addWindow(dlg);
		});
		VerticalLayout left = new VerticalLayout();
		left.addComponents(addItem, gridItems);
		left.setStyleName(STYLE_BORDERED);
		return left;
	}

	public HorizontalLayout createHeadView(final UI ui,
			VaadinRequest vaadinRequest) {
		Label l1 = new Label(resBundle.getString(LABEL_LOGGEDIN));
		Label l2 = new Label(
				String.format("%s %s", user.getPrename(), user.getSurname()));

		Button addArticle = new Button(resBundle.getString(CAPTION_ARTICLES));
		addArticle.addClickListener(ev -> {
			final ArticleDialog dlg = new ArticleDialog(resBundle);
			dlg.setUser(user);
			dlg.setBusiness(
					new ArticleBusiness(business.getSessionObj(), store));

			ui.addWindow(dlg);
		});

		Button logoutButton = new Button(resBundle.getString(LABEL_LOGOUT));
		logoutButton.addClickListener(ev -> {
			LOGGER.warn("Logging out.");
			logout(ui, vaadinRequest);
		});

		Button userDetail = new Button(
				resBundle.getString(CAPTION_USER_DETAILS), ev -> {
					showUserDetailDialog(ui);
				});

		VerticalLayout userId = new VerticalLayout();
		userId.addComponents(l1, l2);
		HorizontalLayout head = new HorizontalLayout();
		head.setWidth(80, Unit.PERCENTAGE);
		head.addComponents(userId, addArticle, userDetail, logoutButton);
		head.setComponentAlignment(userId, Alignment.MIDDLE_LEFT);
		head.setComponentAlignment(addArticle, Alignment.MIDDLE_RIGHT);
		head.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);
		return head;
	}

	private void showUserDetailDialog(UI ui) {
		UserDetailsDialog dlg = new UserDetailsDialog(resBundle);
		dlg.setModal(true);
		dlg.setUser(user);
		dlg.addOkClickListener(evOkClicked -> {
			LOGGER.info("Updating User: {}", dlg.getUser());
			business.save(dlg.getUser());
		});

		dlg.center();
		ui.addWindow(dlg);
	}

	private void logout(UI ui, VaadinRequest vaadinRequest) {
		ui.getPage().setLocation("logout");
	}

	private List<InvoiceItem> loadItems() {
		return business.getInvoiceItems(user);
	}

	private List<Invoice> loadInvoices() {
		return business.getInvoices(user);
	}

	List<Article> loadArticles() {
		return business.getArticles(user);
	}
}
