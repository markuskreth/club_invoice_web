package de.kreth.clubinvoice.report;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.TestData;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;

class InvoiceReportSourceTest {

	private InvoiceReportSource source;

	@Mock
	private JasperReport report;

	private TestData testData;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		testData = new TestData();
		InvoiceItem item = new InvoiceItem();
		Article article = new Article();
		article.setTitle("title");
		article.setUserId(testData.user.getId());
		article.setPricePerHour(BigDecimal.TEN);
		article.setDescription("description");

		item.setArticle(article);
		item.setParticipants("participants");
		LocalDateTime start = LocalDateTime.of(2018, 6, 21, 17, 0);
		item.setStart(start);
		item.setEnd(start.plusMinutes(90));

		Invoice invoice = new Invoice();
		invoice.setId(-1);
		invoice.setUser(testData.user);
		invoice.setItems(Arrays.asList(item));
		invoice.setInvoiceId("invoiceId");
		invoice.setInvoiceDate(start.plusDays(45));
		invoice.setSignImagePath(Path.of("."));

		source = new InvoiceReportSource();
		source.setInvoice(invoice);
	}

	@Test
	void testEachFiel() throws UnsupportedOperationException, JRException {
		assertTrue(source.supportsGetFieldsOperation());
		JRField[] fields = source.getFields(report);
		assertNotNull(fields);
//		assertThat(fields.length, Matchers.greaterThan(10));
		assertTrue(source.next());
		for (JRField f : fields) {
			Object value = source.getFieldValue(f);
			assertNotNull(value, "No value found for Field: " + f.getName() + ", " + f.getDescription());
		}
		assertFalse(source.next());
	}

}
