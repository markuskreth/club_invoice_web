package de.kreth.clubinvoice.report;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;

import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataSourceProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseField;

public class InvoiceReportSource implements JRDataSource, JRDataSourceProvider {

	public static final String FIELD_INVOICE_NO = "INVOICE_NO";
	public static final String FIELD_INVOICE_DATE = "INVOICE_DATE";
	public static final String FIELD_INVOICE_SUM = "INVOICE_SUM";
	public static final String FIELD_USER_PRENAME = "USER_PRENAME";
	public static final String FIELD_USER_SURNAME = "USER_SURNAME";
	public static final String FIELD_USER_ADRESS1 = "USER_ADRESS1";
	public static final String FIELD_USER_ADRESS2 = "USER_ADRESS2";
	public static final String FIELD_USER_ZIP = "USER_ZIPCODE";
	public static final String FIELD_USER_CITY = "USER_CITY";
	public static final String FIELD_BANK_NAME = "BANK_NAME";
	public static final String FIELD_BANK_IBAN = "BANK_IBAN";
	public static final String FIELD_BANK_BIC = "BANK_BIC";
	public static final String FIELD_ARTICLE_TITLE = "ARTICLE_TITLE";
	public static final String FIELD_ARTICLE_DESCRIPTION = "ARTICLE_DESCRIPTION";
	public static final String FIELD_ARTICLE_PRICE_PER_HOUR = "ARTICLE_PRICE_PER_HOUR";
	public static final String FIELD_ITEM_START = "ITEM_START";
	public static final String FIELD_ITEM_END = "ITEM_END";
	public static final String FIELD_ITEM_PARTICIPANTS = "FIELD_ITEM_PARTICIPANTS";
	public static final String FIELD_ITEM_DURATION_MINUTES = "ITEM_DURATION_MINUTES";
	public static final String FIELD_ITEM_SUM = "ITEM_SUM";

	private Invoice invoice;
	private Iterator<InvoiceItem> itemIterator;
	private InvoiceItem currentItem;

	public InvoiceReportSource() {
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
		itemIterator = invoice.getItems().iterator();
	}

	@Override
	public boolean next() throws JRException {
		if (itemIterator.hasNext() == false) {
			currentItem = null;
			return false;
		}
		currentItem = itemIterator.next();
		return true;
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		switch (jrField.getName()) {
			case FIELD_INVOICE_NO :
				return invoice.getInvoiceId();
			case FIELD_INVOICE_DATE :
				return invoice.getInvoiceDate();
			case FIELD_INVOICE_SUM :
				return invoice.getSum();
			case FIELD_USER_PRENAME :
				return invoice.getUser().getPrename();
			case FIELD_USER_SURNAME :
				return invoice.getUser().getSurname();

			case FIELD_BANK_NAME :
				return invoice.getUser().getBank().getBankName();
			case FIELD_BANK_IBAN :
				return invoice.getUser().getBank().getIban();
			case FIELD_BANK_BIC :
				return invoice.getUser().getBank().getBic();
			case FIELD_USER_ADRESS1 :
				return invoice.getUser().getAdress().getAdress1();
			case FIELD_USER_ADRESS2 :
				return invoice.getUser().getAdress().getAdress2();
			case FIELD_USER_ZIP :
				return invoice.getUser().getAdress().getZip();
			case FIELD_USER_CITY :
				return invoice.getUser().getAdress().getCity();
			default :
				break;
		}

		if (currentItem != null) {

			switch (jrField.getName()) {
				case FIELD_ARTICLE_TITLE :
					return currentItem.getArticle().getTitle();
				case FIELD_ARTICLE_DESCRIPTION :
					return currentItem.getArticle().getDescription();
				case FIELD_ARTICLE_PRICE_PER_HOUR :
					return currentItem.getArticle().getPricePerHour();
				case FIELD_ITEM_START :
					return currentItem.getStart();
				case FIELD_ITEM_END :
					return currentItem.getEnd();
				case FIELD_ITEM_DURATION_MINUTES :
					return currentItem.getDurationInMinutes();
				case FIELD_ITEM_SUM :
					return currentItem.getSumPrice();
				case FIELD_ITEM_PARTICIPANTS :
					return currentItem.getParticipants();

				default :
					break;
			}
		}

		return null;
	}

	public static JRDataSource getDataSource() {
		return new InvoiceReportSource();
	}

	@Override
	public boolean supportsGetFieldsOperation() {
		return true;
	}

	@Override
	public JRField[] getFields(JasperReport report)
			throws JRException, UnsupportedOperationException {
		JRField[] fields = {
				new InternalField(FIELD_INVOICE_NO, "Invoice No", String.class),
				new InternalField(FIELD_INVOICE_DATE, "Invoice date",
						LocalDateTime.class),
				new InternalField(FIELD_INVOICE_SUM, "Invoice sum",
						BigDecimal.class),
				new InternalField(FIELD_USER_PRENAME, "User Prename",
						String.class),
				new InternalField(FIELD_USER_SURNAME, "User Surname",
						String.class),

				new InternalField(FIELD_USER_ADRESS1, "User Adress1",
						String.class),
				new InternalField(FIELD_USER_ADRESS2, "User Âdress2",
						String.class),
				new InternalField(FIELD_USER_ZIP, "User Zipcode", String.class),
				new InternalField(FIELD_USER_CITY, "User City", String.class),

				new InternalField(FIELD_BANK_NAME, "Bank Name", String.class),
				new InternalField(FIELD_BANK_IBAN, "Bank IBAN", String.class),
				new InternalField(FIELD_BANK_BIC, "Bank Bic", String.class),

				new InternalField(FIELD_ARTICLE_TITLE, "Article Title",
						String.class),
				new InternalField(FIELD_ARTICLE_DESCRIPTION,
						"Article Description", String.class),
				new InternalField(FIELD_ARTICLE_PRICE_PER_HOUR,
						"Article Price per Hour", BigDecimal.class),
				new InternalField(FIELD_ITEM_START, "Item Start",
						LocalDateTime.class),
				new InternalField(FIELD_ITEM_END, "Item End",
						LocalDateTime.class),
				new InternalField(FIELD_ITEM_DURATION_MINUTES,
						"Item Duration in Minutes", Long.class),
				new InternalField(FIELD_ITEM_PARTICIPANTS, "Item Participants",
						String.class),
				new InternalField(FIELD_ITEM_SUM, "Item Sum",
						BigDecimal.class)};
		return fields;
	}

	@Override
	public JRDataSource create(JasperReport report) throws JRException {
		return this;
	}

	@Override
	public void dispose(JRDataSource dataSource) throws JRException {
	}

	static class InternalField extends JRBaseField {
		private static final long serialVersionUID = -495777796541981790L;

		InternalField(String name, String desc, Class<?> clazz) {
			this.name = name;
			this.description = desc;
			this.valueClass = clazz;
			this.valueClassName = clazz.getName();
		}
	}
}
