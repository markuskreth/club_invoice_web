package de.kreth.clubinvoice.ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.kreth.clubinvoice.business.CookieStore;
import de.kreth.clubinvoice.business.OverviewBusiness;
import de.kreth.clubinvoice.business.PropertyStore;
import de.kreth.clubinvoice.business.UserRegister;
import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.ui.components.ArticleDialog;
import de.kreth.clubinvoice.ui.components.InvoiceDialog;
import de.kreth.clubinvoice.ui.components.InvoiceGrid;
import de.kreth.clubinvoice.ui.components.InvoiceItemDialog;
import de.kreth.clubinvoice.ui.components.InvoiceItemGrid;
import de.kreth.clubinvoice.ui.components.UserDetailsDialog;

public class OverviewUi extends VerticalLayout implements InvoiceUi {

	private static final long serialVersionUID = 318645298331660865L;
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

		HorizontalLayout head = createHeadView(ui);

		VerticalLayout left = createItemsView(ui);
		left.setSizeFull();

		VerticalLayout right = createInvoicesView(ui);
		right.setSizeFull();

		HorizontalLayout main = new HorizontalLayout();
		main.setWidth(80, Unit.PERCENTAGE);
		main.addComponents(left, right);

		addComponents(head, main);

		ui.setContent(this);
		if (user.getBank() == null || user.getAdress() == null) {
			showUserDetailDialog(ui);
		}
	}

	public VerticalLayout createInvoicesView(final UI ui) {
		gridInvoices = new InvoiceGrid(resBundle);
		gridInvoices.setSizeFull();
		gridInvoices.setItems(loadInvoices());
		gridInvoices.addItemClickListener(itemEv -> {

			InvoiceDialog dlg = new InvoiceDialog(resBundle);
			dlg.setInvoice(itemEv.getItem());
			dlg.setOkVisible(false);
			ui.addWindow(dlg);
		});
		gridInvoices.setStyleName("bordered");

		Button createInvoice = new Button(
				resBundle.getString("caption.invoice.create"));
		createInvoice.addClickListener(ev -> {

			String invoiceNo = business.createNextInvoiceId(user,
					resBundle.getString("caption.invoice.pattern"));
			Set<InvoiceItem> selectedItems = gridItems.getSelectedItems();
			final Invoice inv = new Invoice();
			inv.setInvoiceId(invoiceNo);
			inv.setInvoiceDate(LocalDateTime.now());
			inv.setItems(selectedItems);
			inv.setUser(user);
			InvoiceDialog dlg = new InvoiceDialog(resBundle);
			dlg.setInvoice(inv);
			dlg.addOkClickListener(okEv -> {
				business.save(inv);
				gridItems.setItems(loadItems());
				gridInvoices.setItems(loadInvoices());
			});
			ui.addWindow(dlg);

		});
		VerticalLayout right = new VerticalLayout();
		right.setStyleName("bordered");
		right.addComponents(createInvoice, gridInvoices);
		return right;
	}

	public VerticalLayout createItemsView(final UI ui) {
		gridItems = new InvoiceItemGrid<>(resBundle);
		gridItems.setSizeFull();
		gridItems.setSelectionMode(SelectionMode.MULTI);
		gridItems.setItems(loadItems());
		gridItems.addItemClickListener(ev -> {

			final InvoiceItemDialog dlg = new InvoiceItemDialog(resBundle);
			dlg.setItem(ev.getItem());
			dlg.addOkClickListener(e -> {
				business.save(dlg.getItem());
				gridItems.setItems(loadItems());
			});
			dlg.setSelectableArticles(business.getArticles(user));
			ui.addWindow(dlg);

		});
		gridItems.setStyleName("bordered");

		Button addItem = new Button(
				resBundle.getString("caption.invoiceitem.add"));
		addItem.addClickListener(ev -> {
			final InvoiceItemDialog dlg = new InvoiceItemDialog(resBundle);
			dlg.addOkClickListener(e -> {
				business.save(dlg.getItem());
				gridItems.setItems(loadItems());
			});
			dlg.setSelectableArticles(business.getArticles(user));
			ui.addWindow(dlg);
		});
		VerticalLayout left = new VerticalLayout();
		left.addComponents(addItem, gridItems);
		left.setStyleName("bordered");
		return left;
	}

	public HorizontalLayout createHeadView(final UI ui) {
		Label l1 = new Label(resBundle.getString("label.loggedin"));
		Label l2 = new Label(
				String.format("%s %s", user.getPrename(), user.getSurname()));

		Button addArticle = new Button(resBundle.getString("label.addarticle"));
		addArticle.addClickListener(ev -> {
			final ArticleDialog dlg = new ArticleDialog(resBundle);
			dlg.addOkClickListener(clickEv -> {
				Article a = new Article();
				a.setTitle(dlg.getTitle());
				a.setPricePerHour(dlg.getPricePerHour());
				a.setDescription(dlg.getDescription());
				a.setUserId(user.getId());

				business.createArticle(a);

			});
			ui.addWindow(dlg);
		});

		Button logoutButton = new Button(resBundle.getString("label.logout"));
		logoutButton.addClickListener(ev -> {
			logout(ui);
		});

		Button userDetail = new Button("Benutzer Details", ev -> {
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
			business.save(dlg.getUser());
		});

		dlg.center();
		ui.addWindow(dlg);
	}

	private void logout(UI ui) {

		store.removeAttribute(PropertyStore.LOGGED_IN_USER);
		CookieStore cs = new CookieStore();
		cs.remove(CookieStore.PASSWORD);
		cs.remove(CookieStore.USER_NAME);

		UserRegister business = new UserRegister(
				OverviewUi.this.business.getSessionObj(), store, cs);

		LoginUi content = new LoginUi(business);
		Window w = new Window();
		w.setContent(content);
		w.setModal(true);
		ui.addWindow(w);
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
