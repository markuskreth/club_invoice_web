package de.kreth.clubinvoice.business;

import org.hibernate.Session;

import de.kreth.clubinvoice.data.Article;

public class ArticleBusiness extends AbstractBusiness<Article> {

	public ArticleBusiness(Session sessionObj, PropertyStore propStore) {
		super(sessionObj, propStore, Article.class);
	}

}
