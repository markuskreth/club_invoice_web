package de.kreth.clubinvoice.business;

import java.util.List;

import org.hibernate.Session;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.User;

public class OverviewBusiness {

	private final ArticleBusiness articleBusiness;

	private final InvoiceItemBusiness itemBusiness;

	private final InvoiceBusiness invoiceBusiness;

	private final UserRegister userBusiness;

	public OverviewBusiness(Session sessionObj, PropertyStore propStore, CookieStore cookieStore) {
		articleBusiness = new ArticleBusiness(sessionObj, propStore);
		itemBusiness = new InvoiceItemBusiness(sessionObj, propStore);
		invoiceBusiness = new InvoiceBusiness(sessionObj, propStore);
		userBusiness = new UserRegister(sessionObj, propStore, cookieStore);
	}

	public void createArticle(Article a) {
		articleBusiness.save(a);
	}

	public List<Article> getArticles(User user) {
		return articleBusiness.loadAll(a -> a.getUserId() == user.getId());
	}

	public List<InvoiceItem> getInvoiceItems(User user) {
		return itemBusiness.loadAll(i -> i.getUserId() == user.getId() && i.getInvoice() == null);
	}

	public List<Invoice> getInvoices(User user) {
		List<Invoice> loadAll = invoiceBusiness.loadAll(i -> i.getUser().equals(user));
		loadAll.sort((o1, o2) -> o1.getInvoiceDate().compareTo(o2.getInvoiceDate()));
		return loadAll;
	}

	public void save(InvoiceItem item) {
		itemBusiness.save(item);
	}

	public void delete(InvoiceItem item) {
		itemBusiness.delete(item);
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

	public void save(User user) {
		userBusiness.save(user);
	}

}
