package de.kreth.clubinvoice.ui;

import java.util.List;
import java.util.ResourceBundle;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kreth.clubinvoice.business.OverviewBusiness;
import de.kreth.clubinvoice.business.PropertyStore;
import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.ui.components.ArticleDialog;
import de.kreth.clubinvoice.ui.components.InvoiceGrid;
import de.kreth.clubinvoice.ui.components.InvoiceItemDialog;
import de.kreth.clubinvoice.ui.components.InvoiceItemGrid;

public class OverviewUi extends VerticalLayout implements InvoiceUi {

	private static final long serialVersionUID = 318645298331660865L;
	private final User user;
	private final OverviewBusiness business;
	private Grid<InvoiceItem> gridItems;
	private ResourceBundle resBundle;
	private InvoiceGrid gridInvoices;

	public OverviewUi(PropertyStore store, OverviewBusiness business) {
		super();
		this.business = business;
		this.user = (User) store.getAttribute(PropertyStore.LOGGED_IN_USER);
		resBundle = ResourceBundle.getBundle("/application");
	}

	@Override
	public void setContent(UI ui, VaadinRequest vaadinRequest) {

		HorizontalLayout head = createHeadView(ui);

		VerticalLayout left = createItemsView(ui);

		VerticalLayout right = createInvoicesView();

		HorizontalLayout main = new HorizontalLayout();

		main.addComponents(left, right);

		addComponents(head, main);

		ui.setContent(this);
	}

	public VerticalLayout createInvoicesView() {
		gridInvoices = new InvoiceGrid();
		gridInvoices.setItems(loadInvoices());

		Button createInvoice = new Button("Create Invoice");
		createInvoice.addClickListener(ev -> {
			Notification.show("Create Invoice");
		});
		VerticalLayout right = new VerticalLayout();
		right.addComponents(createInvoice, gridInvoices);
		return right;
	}

	public VerticalLayout createItemsView(UI ui) {
		gridItems = new InvoiceItemGrid<>(resBundle);
		gridItems.setItems(loadItems());

		Button addItem = new Button("Add Item");
		addItem.addClickListener(ev -> {
			final InvoiceItemDialog dlg = new InvoiceItemDialog(resBundle);
			dlg.addOkClickListener(e -> {
				business.saveInvoiceItem(dlg.getItem());
				gridItems.setItems(loadItems());
			});
			dlg.setSelectableArticles(business.getArticles(user));
			ui.addWindow(dlg);
		});
		VerticalLayout left = new VerticalLayout();
		left.addComponents(addItem, gridItems);
		return left;
	}

	public HorizontalLayout createHeadView(UI ui) {
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

		VerticalLayout userId = new VerticalLayout();
		userId.addComponents(l1, l2);
		HorizontalLayout head = new HorizontalLayout();
		head.addComponents(userId, addArticle);
		return head;
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
