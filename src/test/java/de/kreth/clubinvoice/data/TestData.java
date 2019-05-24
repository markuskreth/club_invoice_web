package de.kreth.clubinvoice.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TestData {

	public final UserAdress adress;

	public final UserBank bank;

	public final User user;

	public final Invoice invoice;

	public final InvoiceItem item;

	public final Article article;

	public TestData() {

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
		user.setPassword("password");
		user.setPrename("prename");
		user.setSurname("surname");
		user.setAdress(adress);
		user.setBank(bank);

		adress.setUser(user);
		bank.setUser(user);

		invoice = new Invoice();
		invoice.setUser(user);
		invoice.setInvoiceId("invoiceId");

		article = new Article();
		article.setTitle("title");
		article.setUserId(user.getId());
		article.setPricePerHour(BigDecimal.TEN);
		article.setDescription("description");

		item = new InvoiceItem();
		item.setId(15);
		item.setArticle(article);
		item.setInvoice(invoice);
		item.setStart(LocalDateTime.of(2018, 10, 23, 21, 13, 45));
		item.setEnd(LocalDateTime.of(2018, 10, 23, 23, 13, 45));
		item.setParticipants("ca. 13");
		item.setCreatedDate(LocalDateTime.now());
		item.setChangeDate(LocalDateTime.now());
		invoice.setItems(Arrays.asList(item));
	}
}
