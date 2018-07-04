package de.kreth.clubinvoice.business;

import java.util.List;

import org.hibernate.Session;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.User;

public class OverviewBusiness {

	private final ArticleBusiness articleBusiness;
	private InvoiceItemBusiness itemBusiness;
	private InvoiceBusiness invoiceBusiness;

	public OverviewBusiness(Session sessionObj, PropertyStore propStore) {
		articleBusiness = new ArticleBusiness(sessionObj, propStore);
		itemBusiness = new InvoiceItemBusiness(sessionObj, propStore);
		invoiceBusiness = new InvoiceBusiness(sessionObj, propStore);
	}

	public void createArticle(Article a) {
		articleBusiness.save(a);
	}

	public List<Article> getArticles(User user) {
		return articleBusiness.loadAll(a -> a.getUserId() == user.getId());
	}

	public List<InvoiceItem> getInvoiceItems(User user) {
		return itemBusiness
				.loadAll(i -> i.getArticle().getUserId() == user.getId());
	}

	public List<Invoice> getInvoices(User user) {
		return invoiceBusiness.loadAll(i -> i.getUser().equals(user));
	}

	public void save(InvoiceItem item) {
		itemBusiness.save(item);
	}

	public void save(Invoice inv) {
		invoiceBusiness.save(inv);
	}

	public Session getSessionObj() {
		return itemBusiness.getSessionObj();
	}

	public String createNextInvoiceId(User user, String pattern) {
		return invoiceBusiness.createNextInvoiceId(getInvoices(user), pattern);
	}

}
