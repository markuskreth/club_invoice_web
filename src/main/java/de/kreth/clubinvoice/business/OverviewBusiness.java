package de.kreth.clubinvoice.business;

import java.util.Collection;

import org.hibernate.Session;

import de.kreth.clubinvoice.data.Article;

public class OverviewBusiness {

	private final ArticleBusiness articleBusiness;

	public OverviewBusiness(Session sessionObj, PropertyStore propStore) {
		articleBusiness = new ArticleBusiness(sessionObj, propStore);
	}

	public void createArticle(Article a) {
		articleBusiness.save(a);
	}

	public Collection<Article> getArticles() {
		return articleBusiness.loadAll();
	}

}
