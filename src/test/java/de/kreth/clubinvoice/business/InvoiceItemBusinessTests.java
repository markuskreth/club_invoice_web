package de.kreth.clubinvoice.business;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.User;

class InvoiceItemBusinessTests extends AbstractTestDatabaseSession {

	private static LocalDateTime now;

	@BeforeAll
	static void createNow() {
		now = LocalDateTime.now();
	}

	private Article testArticle;
	private User testUser;
	private CookieStore cookies;
	private InvoiceItemBusiness business;

	@BeforeEach
	void setUp() throws Exception {

		cookies = mock(CookieStore.class);
		testUser = new User();
		testUser.setLoginName("test");
		testUser.setPrename("Test");
		testUser.setSurname("Test");
		testUser.setPassword("test");
		testUser.setChangeDate(now);
		testUser.setCreatedDate(now);

		UserRegister userRegister = new UserRegister(session, propStore,
				cookies);
		userRegister.save(testUser);

		ArticleBusiness ab = new ArticleBusiness(session, propStore);
		testArticle = new Article();
		testArticle.setTitle("TestArtikel");
		testArticle.setPricePerHour(BigDecimal.valueOf(7.1));
		testArticle.setDescription("TestArtikel");
		testArticle.setChangeDate(now);
		testArticle.setCreatedDate(now);
		ab.save(testArticle);
		business = new InvoiceItemBusiness(session, propStore);
	}

	@Test
	void testInvalidDurationAndSumCalculation() {

		LocalDateTime start = LocalDateTime.of(2018, Month.APRIL, 1, 17, 0, 0);
		LocalDateTime end = LocalDateTime.of(2018, Month.APRIL, 1, 19, 30, 0);

		InvoiceItem item = new InvoiceItem();
		assertEquals(-1, item.getDurationInMinutes());

		item.setStart(start);
		assertEquals(-1, item.getDurationInMinutes());

		item.setStart(null);
		item.setEnd(end);
		assertEquals(-1, item.getDurationInMinutes());

		item.setStart(start);

		assertNull(item.getSumPrice());

		item.setArticle(testArticle);
		item.setStart(null);
		assertNull(item.getSumPrice());

		item.setStart(start);
		item.setEnd(null);
		assertNull(item.getSumPrice());

	}

	@Test
	void testCreateAndCalculateSum() {
		InvoiceItem item = new InvoiceItem();
		item.setArticle(testArticle);
		LocalDateTime start = LocalDateTime.of(2018, Month.APRIL, 1, 17, 17, 0);
		LocalDateTime end = LocalDateTime.of(2018, Month.APRIL, 1, 19, 13, 0);
		item.setStart(start);
		item.setEnd(end);
		long minutes = item.getDurationInMinutes();
		long expectedMinutes = 116L;
		assertEquals(expectedMinutes, minutes);

		BigDecimal expected = BigDecimal.valueOf(13.70).setScale(2);
		BigDecimal sum = item.getSumPrice();
		assertEquals(expected, sum);

		expected = testArticle.getPricePerHour()
				.setScale(2, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(1.5))
				.setScale(2, RoundingMode.HALF_UP);
		item.setStart(LocalDateTime.of(2018, Month.APRIL, 1, 17, 0, 0));
		item.setEnd(LocalDateTime.of(2018, Month.APRIL, 1, 18, 30, 50));

		assertEquals(90L, item.getDurationInMinutes());
		assertEquals(expected, item.getSumPrice());
	}

	@Test
	void testInsertAndLoadItems() {
		InvoiceItem item = new InvoiceItem();
		item.setArticle(testArticle);
		item.setStart(LocalDateTime.of(2018, Month.APRIL, 1, 17, 0, 0));
		item.setEnd(LocalDateTime.of(2018, Month.APRIL, 1, 18, 30, 0));
		business.save(item);

		item = new InvoiceItem();
		item.setArticle(testArticle);
		item.setStart(LocalDateTime.of(2018, Month.APRIL, 2, 17, 0, 0));
		item.setEnd(LocalDateTime.of(2018, Month.APRIL, 2, 18, 30, 0));
		business.save(item);

		List<InvoiceItem> allItems = business.loadAll();
		assertEquals(2, allItems.size());
	}

}
