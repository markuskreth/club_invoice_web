package de.kreth.clubinvoice.business;

import org.hibernate.Session;
import org.hibernate.query.Query;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.InvoiceItem;

public class ArticleBusiness extends AbstractBusiness<Article> {

	public ArticleBusiness(Session sessionObj, PropertyStore propStore) {
		super(sessionObj, propStore, Article.class);
	}

	public boolean hasInvoice(Article current) {
		Query<InvoiceItem> query = sessionObj.createQuery("from InvoiceItem where article_id = :articleId",
				InvoiceItem.class);
		query.setParameter("articleId", current.getId());
		int size = query.list().size();
		return size > 0;
	}

}
