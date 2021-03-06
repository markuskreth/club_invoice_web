package de.kreth.clubinvoice.ui;

import static de.kreth.clubinvoice.Localization_Properties.CAPTION_ARTICLES;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_INVOICEITEM_ADD;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_INVOICE_CREATE;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_INVOICE_PATTERN;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_USER_DETAILS;
import static de.kreth.clubinvoice.Localization_Properties.LABEL_LOGGEDIN;
import static de.kreth.clubinvoice.Localization_Properties.LABEL_LOGOUT;
import static de.kreth.clubinvoice.Localization_Properties.MESSAGE_DELETE_TEXT;
import static de.kreth.clubinvoice.Localization_Properties.MESSAGE_DELETE_TITLE;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.server.CompositeErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kreth.clubinvoice.Localization_Properties;
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
import de.kreth.clubinvoice.utils.ResourceBundleProvider;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;

public class OverviewUi extends VerticalLayout implements InvoiceUi {

	private static final long serialVersionUID = 318645298331660865L;

	private static final Logger LOGGER = LoggerFactory.getLogger(OverviewUi.class);

	public static final String STYLE_BORDERED = "bordered";

	private final User user;

	private final transient OverviewBusiness business;

	private final transient ResourceBundle resBundle;

	private final transient PropertyStore store;

	private Grid<InvoiceItem> gridItems;

	private InvoiceGrid gridInvoices;

	private Button createInvoice;

	private Button addItem;

	private Button userDetail;

	private Button addArticle;

	public OverviewUi(PropertyStore store, OverviewBusiness business) {
		super();
		this.business = business;
		this.store = store;
		this.user = (User) store.getAttribute(PropertyStore.LOGGED_IN_USER);
		resBundle = ResourceBundleProvider.getBundle();
	}

	@Override
	public void setContent(UI ui, VaadinRequest vaadinRequest) {

		HorizontalLayout head = createHeadView(ui, vaadinRequest);

		VerticalLayout left = createItemsView(ui);
		left.setSizeFull();

		VerticalLayout right = createInvoicesView(ui);
		right.setSizeFull();

		AbstractLayout main;
		if (ui.getPage().getBrowserWindowWidth() > 1000) {
			main = new HorizontalLayout();
		}
		else {
			main = new VerticalLayout();
		}
		main.setSizeFull();
		main.addComponents(left, right);

		Layout footer = createFooter();

		addComponents(head, main, footer);

		ui.setContent(this);

		if (user.getBank() == null || user.getAdress() == null) {
			LOGGER.info("User data incomplete, showing user detail dialog.");
			showUserDetailDialog(ui);
		}
		checkButtonStates();
	}

	private void checkButtonStates() {
		boolean noItems = loadItems().isEmpty();
		if (noItems) {
			createInvoice.setEnabled(false);
		}
		else {
			createInvoice.setEnabled(true);
		}
		checkSettings();
	}

	public void checkSettings() {
		updateArticleError();
		List<AbstractErrorMessage> errors = new ArrayList<>();
		if (user.getAdress() == null) {
			errors.add(new UserError(getString(Localization_Properties.ERROR_USERDETAILS_ADRESS_EMPTY)));
		}
		if (user.getBank() == null) {
			errors.add(new UserError(getString(Localization_Properties.ERROR_USERDETAILS_BANKNAME_EMPTY)));
			errors.add(new UserError(getString(Localization_Properties.ERROR_USERDETAILS_IBAN_EMPTY)));
		}
		else {
			if (user.getBank().getIban().isBlank()) {
				errors.add(new UserError(getString(Localization_Properties.ERROR_USERDETAILS_IBAN_EMPTY)));
			}
			if (user.getBank().getBankName().isBlank()) {
				errors.add(new UserError(getString(Localization_Properties.ERROR_USERDETAILS_BANKNAME_EMPTY)));
			}
		}

		if (errors.isEmpty()) {
			this.userDetail.setComponentError(null);
		}
		else {
			buildErrorMessage(errors);
		}
	}

	private void updateArticleError() {
		boolean noArticles = business.getArticles(user).isEmpty();
		if (noArticles) {
			addItem.setEnabled(false);
			addArticle.setComponentError(new UserError(getString(Localization_Properties.ERROR_ARTICLE_UNDEFINED)));
		}
		else {
			addItem.setEnabled(true);
			addArticle.setComponentError(null);
		}
	}

	public void buildErrorMessage(List<AbstractErrorMessage> errors) {
		this.userDetail.setComponentError(new CompositeErrorMessage(errors));
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
			InvoiceDialog dlg = new InvoiceDialog(resBundle, InvoiceDialog.InvoiceMode.VIEW_ONLY);
			dlg.center();
			dlg.setHeight(80, Unit.PERCENTAGE);
			dlg.setWidth(60, Unit.PERCENTAGE);
			dlg.setInvoice(itemEv.getItem());
			dlg.setOkVisible(false);
			ui.addWindow(dlg);
		});
		gridInvoices.setStyleName(STYLE_BORDERED);

		createInvoice = new Button(getString(CAPTION_INVOICE_CREATE));
		createInvoice.addClickListener(ev -> {

			String invoiceNo = business.createNextInvoiceId(user,
					getString(CAPTION_INVOICE_PATTERN));

			LOGGER.info("Creating new invoice no: {}", invoiceNo);

			Set<InvoiceItem> selectedItems = gridItems.getSelectedItems();
			if (selectedItems == null || selectedItems.isEmpty()) {
				selectedItems = new HashSet<>(loadItems());
			}
			if (selectedItems.isEmpty()) {
				return;
			}
			Iterator<InvoiceItem> iter = selectedItems.iterator();
			InvoiceItem current = iter.next();
			BigDecimal pricePerHour = current.getPricePerHour();
			String title = current.getTitle();
			String report = current.getReport();

			while (iter.hasNext()) {
				current = iter.next();
				if (!current.getPricePerHour().equals(pricePerHour)
						|| !current.getTitle().equals(title)
						|| !current.getReport().equals(report)) {
					iter.remove();
				}
			}
			final Invoice inv = new Invoice();
			inv.setInvoiceId(invoiceNo);
			inv.setInvoiceDate(LocalDateTime.now());
			inv.setItems(selectedItems);
			inv.setUser(user);
			InvoiceDialog dlg = new InvoiceDialog(resBundle, InvoiceDialog.InvoiceMode.CREATE);
			dlg.center();
			dlg.setHeight(80, Unit.PERCENTAGE);
			dlg.setWidth(80, Unit.PERCENTAGE);
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

	private String getString(Localization_Properties properties) {
		return properties.getString(resBundle::getString);
	}

	public VerticalLayout createItemsView(final UI ui) {
		gridItems = new InvoiceItemGrid<>(resBundle);
		gridItems.setSizeFull();
		gridItems.setSelectionMode(SelectionMode.MULTI);
		gridItems.setItems(loadItems());
		gridItems.addItemClickListener(ev -> {

			LOGGER.debug("Opening item for edit: {}", ev.getItem());
			final InvoiceItemDialog dlg = new InvoiceItemDialog(resBundle);
			dlg.setItem(ev.getItem());
			dlg.addOkClickListener(e -> {
				business.save(dlg.getItem());
				gridItems.setItems(loadItems());
			});
			if (ev.getItem().getInvoice() == null) {

				dlg.addDeleteClickListener(e -> {
					InvoiceItem item = dlg.getItem();
					LOGGER.warn("Showing delete dialog for {}", item);

					MessageBox.createQuestion().asModal(true)
							.withCaption(getString(MESSAGE_DELETE_TITLE))
							.withMessage(MessageFormat.format(
									getString(MESSAGE_DELETE_TEXT),
									DataPresentators.toPresentation(item)))
							.withCancelButton(ButtonOption.closeOnClick(true)).withOkButton(() -> {
								LOGGER.warn("Deleting {}", item);
								business.delete(item);
								gridItems.setItems(loadItems());
								dlg.close();
								checkButtonStates();
							}, ButtonOption.focus()).open();

				});
			}

			dlg.setSelectableArticles(business.getArticles(user));
			ui.addWindow(dlg);

		});
		gridItems.setStyleName(STYLE_BORDERED);

		addItem = new Button(getString(CAPTION_INVOICEITEM_ADD));
		addItem.addClickListener(ev -> {
			final InvoiceItemDialog dlg = new InvoiceItemDialog(resBundle);
			LOGGER.info("Creating new Item.");
			dlg.addOkClickListener(e -> {
				business.save(dlg.getItem());
				gridItems.setItems(loadItems());
				checkButtonStates();
			});
			dlg.setSelectableArticles(business.getArticles(user));
			ui.addWindow(dlg);
		});
		VerticalLayout left = new VerticalLayout();
		left.addComponents(addItem, gridItems);
		left.setStyleName(STYLE_BORDERED);
		return left;
	}

	public HorizontalLayout createHeadView(final UI ui, VaadinRequest vaadinRequest) {
		Label l1 = new Label(getString(LABEL_LOGGEDIN));
		Label l2 = new Label(String.format("%s %s", user.getPrename(), user.getSurname()));

		addArticle = new Button(getString(CAPTION_ARTICLES));
		addArticle.addClickListener(ev -> {

			final ArticleDialog dlg = new ArticleDialog(resBundle,
					new ArticleBusiness(business.getSessionObj(), store));
			dlg.setUser(user);

			ui.addWindow(dlg);
			dlg.addCloseListener((evt) -> checkButtonStates());
		});

		Button logoutButton = new Button(getString(LABEL_LOGOUT));
		logoutButton.addClickListener(ev -> {
			LOGGER.warn("Logging out.");
			logout(ui, vaadinRequest);
		});

		userDetail = new Button(getString(CAPTION_USER_DETAILS), ev -> {
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
			checkButtonStates();
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
