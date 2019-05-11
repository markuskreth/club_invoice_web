package de.kreth.clubinvoice.ui.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.User;

class InvoiceItemPresentatorTest {

	private static Locale defaultLocale;

	private InvoiceItem item;

	private Article article;

	private Invoice invoice;

	private User user;

	@BeforeAll
	public static void initLocale() {
		defaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.GERMANY);
		DataPresentators.cleanCache();
	}

	@AfterAll
	public static void resetLocale() {
		Locale.setDefault(defaultLocale);
		DataPresentators.cleanCache();
	}

	@BeforeEach
	void setUp() throws Exception {
		article = new Article();
		article.setTitle("title");
		article.setUserId(2);
		article.setDescription("description");
		article.setReport("");
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
		assertEquals("Rechnungsposition [Datum=23.10.2018, Beginn=21:13, Ende=23:13, Artikel=title]", textPresentation);
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
