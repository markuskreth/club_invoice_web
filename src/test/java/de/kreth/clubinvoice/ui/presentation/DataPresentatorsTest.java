package de.kreth.clubinvoice.ui.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.BankingConnection;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.UserAdress;

class DataPresentatorsTest {

	@Test
	void testGetPresentorInvoiceItem() throws ReflectiveOperationException {
		DataPresentator<InvoiceItem> actual = DataPresentators
				.getPresentor(InvoiceItem.class);
		assertNotNull(actual);
		assertEquals(InvoiceItemPresentator.class, actual.getClass());
	}

	@Test
	void testNonExistentPresentor() throws ReflectiveOperationException {
		assertNull(DataPresentators.getPresentor(BankingConnection.class));
	}

	@Test
	void testNullObject() {
		assertEquals("null",
				DataPresentators.toPresentation((InvoiceItem) null));
	}

	@Test
	void testNonExistingPresentorToString() {
		UserAdress adress = new UserAdress();
		adress.setAdress1("adress1");
		adress.setAdress2("adress2");
		adress.setZip("zip");
		adress.setCity("city");
		String expected = adress.toString();
		assertEquals(expected, DataPresentators.toPresentation(adress));
	}

	@Test
	void testInvoiceItemPresentator() {
		Article article = new Article();
		article.setTitle("Article");
		article.setPricePerHour(BigDecimal.valueOf(8.5));

		InvoiceItem item = new InvoiceItem();
		item.setId(1);
		item.setArticle(article);
		item.setParticipants("15");
		item.setStart(LocalDateTime.of(2017, 8, 21, 17, 0, 0));
		item.setEnd(LocalDateTime.of(2017, 8, 21, 20, 30, 0));
		assertNotNull(DataPresentators.toPresentation(item));
	}
}
