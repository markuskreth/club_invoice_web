package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.Localization_Properties.CAPTION_ARTICLES;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_ARTICLE_DESCRIPTION;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_ARTICLE_PRICE;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_ARTICLE_TITLE;

import java.text.NumberFormat;
import java.util.ResourceBundle;

import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.data.Article;

public class ArticleGrid extends Grid<Article> {

	private static final long serialVersionUID = 4063720728031721955L;

	public ArticleGrid(ResourceBundle resBundle) {

		setCaption(CAPTION_ARTICLES.getString(resBundle::getString));
		setStyleName("bordered");

		addColumn(Article::getTitle)
				.setCaption(CAPTION_ARTICLE_TITLE.getString(resBundle::getString));

		addColumn(Article::getPricePerHour)
				.setRenderer(
						new NumberRenderer(NumberFormat.getCurrencyInstance()))
				.setCaption(CAPTION_ARTICLE_PRICE.getString(resBundle::getString));
		addColumn(Article::getDescription)
				.setCaption(CAPTION_ARTICLE_DESCRIPTION.getString(resBundle::getString));

	}

}
