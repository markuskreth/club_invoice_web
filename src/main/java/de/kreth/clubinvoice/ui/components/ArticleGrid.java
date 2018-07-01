package de.kreth.clubinvoice.ui.components;

import java.text.NumberFormat;
import java.util.ResourceBundle;

import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.data.Article;

public class ArticleGrid extends Grid<Article> {

	private static final long serialVersionUID = 4063720728031721955L;

	public ArticleGrid(ResourceBundle resBundle) {

		setCaption(resBundle.getString("caption.articles"));
		setStyleName("bordered");

		addColumn(Article::getTitle)
				.setCaption(resBundle.getString("caption.article.title"));

		addColumn(Article::getPricePerHour)
				.setRenderer(
						new NumberRenderer(NumberFormat.getCurrencyInstance()))
				.setCaption(resBundle.getString("caption.article.price"));
		addColumn(Article::getDescription)
				.setCaption(resBundle.getString("caption.article.description"));

	}

}
