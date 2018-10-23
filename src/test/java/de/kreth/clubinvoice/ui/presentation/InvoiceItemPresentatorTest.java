package de.kreth.clubinvoice.ui.presentation;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.User;

class InvoiceItemPresentatorTest {

	private InvoiceItem item;
	private Article article;
	private Invoice invoice;
	private User user;

	@BeforeEach
	void setUp() throws Exception {
		article = new Article();
		article.setTitle("title");
		article.setUserId(2);
		article.setDescription("description");
		article.setPricePerHour(BigDecimal.valueOf(8.5));
		
		user = new User();
		user.setPrename("prename");
		user.setSurname("surname");
		user.setLoginName("loginName");
		
		invoice = new Invoice();
		invoice.setUser(user);
		invoice.setInvoiceId("invoiceId");
		
		item = new InvoiceItem();
		item.setId(15);
		item.setArticle(article);
		item.setInvoice(invoice);
		item.setStart(LocalDateTime.of(2018, 10, 23, 21, 13, 45));
		item.setEnd(LocalDateTime.of(2018, 10, 23, 23, 13, 45));
		item.setParticipants("ca. 13");
		item.setCreatedDate(LocalDateTime.now());
		item.setChangeDate(LocalDateTime.now());
	}

	@Test
	void testInvoiceItemPresentation() {
		String textPresentation = DataPresentators.toPresentation(item);
		assertEquals("Invoice Item [Date=2018/10/23, Start=21:13, End=23:13, Article=title]", textPresentation);
	}

	@Test
	void testLoadResourceBundle() {
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		assertNotNull(bundle);
	}
	
	@Test
	void testNullObject() {
		item = null;
		assertEquals("null", DataPresentators.toPresentation(item));
	}
}
