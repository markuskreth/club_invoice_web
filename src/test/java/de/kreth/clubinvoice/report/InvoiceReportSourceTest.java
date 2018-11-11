package de.kreth.clubinvoice.report;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.data.UserAdress;
import de.kreth.clubinvoice.data.UserBank;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;

class InvoiceReportSourceTest {

	private InvoiceReportSource source;
	private User user;
	private UserAdress adress;
	@Mock
	private JasperReport report;
	private UserBank bank;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		adress = new UserAdress();
		adress.setAdress1("adress1");
		adress.setAdress2("adress2");
		adress.setCity("city");
		adress.setZip("zip");
		
		bank = new UserBank();
		bank.setBankName("bankName");
		bank.setBic("bic");
		bank.setIban("iban");
		
		user = new User();
		user.setLoginName("loginName");
		user.setPrename("prename");
		user.setSurname("surname");
		user.setAdress(adress);
		user.setBank(bank);

		adress.setUser(user);
		bank.setUser(user);
		
		InvoiceItem item = new InvoiceItem();
		Article article = new Article();
		article.setTitle("title");
		article.setUserId(user.getId());
		article.setPricePerHour(BigDecimal.TEN);
		article.setDescription("description");

		item.setArticle(article);
		item.setParticipants("participants");
		LocalDateTime start = LocalDateTime.of(2018, 6, 21, 17, 0);
		item.setStart(start);
		item.setEnd(start.plusMinutes(90));
		
		Invoice invoice = new Invoice();
		invoice.setId(-1);
		invoice.setUser(user);
		invoice.setItems(Arrays.asList(item));
		invoice.setInvoiceId("invoiceId");
		invoice.setInvoiceDate(start.plusDays(45));
		
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
			assertNotNull(value, "No value found for Field: " + f);
		}
		assertFalse(source.next());
	}

}
