package de.kreth.clubinvoice.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.testutils.MockPropertyStore;

class InvoiceBusinessTest extends AbstractTestDatabaseSession {

	private InvoiceBusiness business;
	private InvoiceItemBusiness itemBusiness;
	private Article testArticle;
	private List<InvoiceItem> invoiceItems;

	@BeforeEach
	void setUp() throws Exception {
		testArticle = createTestArticle();
		ArticleBusiness ab = new ArticleBusiness(session, propStore);
		ab.save(testArticle);
		business = new InvoiceBusiness(session, propStore);
		itemBusiness = new InvoiceItemBusiness(session, propStore);
		invoiceItems = new ArrayList<>();

		for (int i = 1; i < 11; i++) {
			InvoiceItem item = new InvoiceItem();
			item.setArticle(testArticle);
			item.setStart(LocalDateTime.of(2017, Month.of(i), i, 17, 0));
			item.setEnd(LocalDateTime.of(2017, Month.of(i), i, 18, 30));
			itemBusiness.save(item);
			invoiceItems.add(item);
		}
	}

	@Test
	void testSave() {
		InvoiceItem item1 = invoiceItems.get(0);
		assertNull(item1.getInvoice());

		Invoice inv = new Invoice();
		inv.setInvoiceDate(LocalDateTime.of(2017, Month.DECEMBER, 1, 0, 0));
		inv.setInvoiceId("Invoice Decemper 2017");
		inv.setItems(invoiceItems);
		assertTrue(business.save(inv));
		for (InvoiceItem i : inv.getItems()) {
			assertNotNull(i.getInvoice());
		}
	}

	@Test
	void testInvoiceSum() {

		Invoice inv = new Invoice();
		inv.setInvoiceDate(LocalDateTime.of(2017, Month.DECEMBER, 1, 0, 0));
		inv.setInvoiceId("Invoice Decemper 2017");
		inv.setItems(invoiceItems);
		assertNotNull(inv.getSum());
		BigDecimal expected = testArticle.getPricePerHour().setScale(2)
				.multiply(BigDecimal.valueOf(invoiceItems.size())
						.multiply(BigDecimal.valueOf(1.5)))
				.setScale(2, RoundingMode.HALF_UP);
		assertEquals(expected, inv.getSum());

	}

	public static void main(String[] args) throws Exception {
		SessionFactory factory = InvoiceBusinessTest
				.createFileDatabaseSession("./testdatabase");
		InvoiceBusinessTest invBusi = new InvoiceBusinessTest();

		invBusi.session = factory.openSession();
		invBusi.propStore = new MockPropertyStore();
		invBusi.setUp();
		invBusi.testSave();
		invBusi.session.close();
		factory.close();
	}
}
