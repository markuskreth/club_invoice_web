package de.kreth.clubinvoice.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.User;

class OverviewBusinessTest {

	@Mock
	private Session sessionObj;
	@Mock
	private PropertyStore propStore;
	@Mock
	private CookieStore cookieStore;
	@Mock
	private Transaction transaction;
	private OverviewBusiness business;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(sessionObj.save(any())).thenReturn(Long.valueOf(1L));
		when(sessionObj.beginTransaction()).thenReturn(transaction);
		business = new OverviewBusiness(sessionObj, propStore, cookieStore);
	}

	@Test
	void testCreateArticle() {
		Article a = new Article();
		business.createArticle(a);
		verify(sessionObj).save(a);
	}

	@Test
	public void testCreateInvoiceId() {
		User user = new User();
		user.setId(15);
		
		String pattern = "invoiceId-{0}";
		@SuppressWarnings("unchecked")
		Query<Invoice> query = mock(Query.class);
		List<Invoice> values = new ArrayList<>();
		Invoice inv = new Invoice();
		inv.setId(10);
		inv.setInvoiceId("invoiceId-12");
		inv.setUser(user);
		values.add(inv);
		when(sessionObj.createQuery(anyString(), eq(Invoice.class))).thenReturn(query);
		when(query.list()).thenReturn(values);
		String actual = business.createNextInvoiceId(user , pattern);
		assertEquals("invoiceId-13", actual);
	}
	
	@Test
	void testGetInvoiceItems() {

		User user = new User();
		user.setId(15);

		@SuppressWarnings("unchecked")
		Query<InvoiceItem> query = mock(Query.class);
		List<InvoiceItem> values = new ArrayList<>();
		InvoiceItem item = new InvoiceItem();
		item.setId(1);
		Article article = new Article();
		article.setUserId(user.getId());
		
		item.setArticle(article);

		values.add(item);
		when(sessionObj.createQuery(anyString(), eq(InvoiceItem.class))).thenReturn(query);
		when(query.list()).thenReturn(values);
		
		List<InvoiceItem> items = business.getInvoiceItems(user);
		assertEquals(1, items.size());
	}
	
}
