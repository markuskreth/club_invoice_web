package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.ui.Constants.CAPTION_ARTICLES;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_ARTICLE_DESCRIPTION;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_ARTICLE_PRICE;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_ARTICLE_TITLE;

import java.text.NumberFormat;
import java.util.ResourceBundle;

import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.data.Article;

public class ArticleGrid extends Grid<Article> {

	private static final long serialVersionUID = 4063720728031721955L;

	public ArticleGrid(ResourceBundle resBundle) {

		setCaption(resBundle.getString(CAPTION_ARTICLES));
		setStyleName("bordered");

		addColumn(Article::getTitle)
				.setCaption(resBundle.getString(CAPTION_ARTICLE_TITLE));

		addColumn(Article::getPricePerHour)
				.setRenderer(
						new NumberRenderer(NumberFormat.getCurrencyInstance()))
				.setCaption(resBundle.getString(CAPTION_ARTICLE_PRICE));
		addColumn(Article::getDescription)
				.setCaption(resBundle.getString(CAPTION_ARTICLE_DESCRIPTION));

	}

}
