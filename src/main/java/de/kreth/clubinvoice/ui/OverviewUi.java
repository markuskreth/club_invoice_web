package de.kreth.clubinvoice.ui;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.business.OverviewBusiness;
import de.kreth.clubinvoice.business.PropertyStore;
import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.ui.dialogs.ArticleDialog;

public class OverviewUi extends VerticalLayout implements InvoiceUi {

	private static final long serialVersionUID = 318645298331660865L;
	private final User user;
	private final OverviewBusiness business;
	private Grid<Article> gridArticle;

	public OverviewUi(PropertyStore store, OverviewBusiness business) {
		super();
		this.business = business;
		this.user = (User) store.getAttribute(PropertyStore.LOGGED_IN_USER);
	}

	@Override
	public void setContent(UI ui, VaadinRequest vaadinRequest) {

		Label l1 = new Label("Logged in:");
		Label l2 = new Label(user.toString());

		VerticalLayout head = new VerticalLayout();
		head.addComponents(l1, l2);
		HorizontalLayout main = new HorizontalLayout();

		Button addArticle = new Button("Add Article");
		addArticle.addClickListener(ev -> {
			final ArticleDialog dlg = new ArticleDialog();
			dlg.addOkClickListener(clickEv -> {
				Article a = new Article();
				a.setTitle(dlg.getTitle());
				a.setPricePerHour(dlg.getPricePerHour());
				a.setDescription(dlg.getDescription());
				a.setUserId(user.getId());

				business.createArticle(a);

				gridArticle.setItems(loadArticles());
			});
			ui.addWindow(dlg);
		});

		gridArticle = new Grid<>();
		gridArticle.setCaption("Articles");
		gridArticle.setStyleName("bordered");

		List<Article> loadArticles = loadArticles();

		gridArticle.addColumn(Article::getTitle).setCaption("Title");

		gridArticle.addColumn(Article::getPricePerHour)
				.setRenderer(
						new NumberRenderer(NumberFormat.getCurrencyInstance()))
				.setCaption("Price per Hour");
		gridArticle.addColumn(Article::getDescription)
				.setCaption("Description");

		gridArticle.setItems(loadArticles);

		VerticalLayout left = new VerticalLayout();
		left.addComponents(addArticle, gridArticle);

		GridLayout right = new GridLayout(3, 1);
		right.setCaption("Right Grid");
		right.setStyleName("bordered");

		main.addComponents(left, right);

		addComponents(head, main);

		ui.setContent(this);
	}

	// private Component createBorderLabel(String text) {
	// Label label = new Label(text);
	// label.setStyleName("bordered");
	// label.setSizeFull();
	// return label;
	// }

	List<Article> loadArticles() {
		List<Article> articles = new ArrayList<>();
		// Article a = new Article();
		// a.setTitle("Übungsleiter");
		// a.setPricePerHour(BigDecimal.valueOf(8.5));
		// a.setCreatedDate(LocalDateTime.now());
		// a.setChangeDate(LocalDateTime.now());
		// articles.add(a);
		// a = new Article();
		// a.setTitle("Trainer");
		// a.setPricePerHour(BigDecimal.valueOf(11));
		// a.setCreatedDate(LocalDateTime.now());
		// a.setChangeDate(LocalDateTime.now());
		// articles.add(a);
		articles.addAll(business.getArticles());
		return articles;
	}
}
