package de.kreth.clubinvoice.ui;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
	private ResourceBundle resBundle;

	public OverviewUi(PropertyStore store, OverviewBusiness business) {
		super();
		this.business = business;
		this.user = (User) store.getAttribute(PropertyStore.LOGGED_IN_USER);
		resBundle = ResourceBundle.getBundle("/application");
	}

	@Override
	public void setContent(UI ui, VaadinRequest vaadinRequest) {

		Label l1 = new Label(resBundle.getString("label.loggedin"));
		Label l2 = new Label(user.toString());

		VerticalLayout head = new VerticalLayout();
		head.addComponents(l1, l2);
		HorizontalLayout main = new HorizontalLayout();

		Button addArticle = new Button(resBundle.getString("label.addarticle"));
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
		gridArticle.setCaption(resBundle.getString("caption.articles"));
		gridArticle.setStyleName("bordered");

		List<Article> loadArticles = loadArticles();

		gridArticle.addColumn(Article::getTitle)
				.setCaption(resBundle.getString("caption.article.title"));

		gridArticle.addColumn(Article::getPricePerHour)
				.setRenderer(
						new NumberRenderer(NumberFormat.getCurrencyInstance()))
				.setCaption(resBundle.getString("caption.article.price"));
		gridArticle.addColumn(Article::getDescription)
				.setCaption(resBundle.getString("caption.article.description"));

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

	List<Article> loadArticles() {
		List<Article> articles = new ArrayList<>();
		articles.addAll(business.getArticles());
		return articles;
	}
}
