package de.kreth.clubinvoice;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.annotation.processing.Generated;

/**
 * Property keys from application.properties
 */
@Generated(date = "15.04.2019, 01:23:59", value = "de.kreth.property2java.Generator")
public enum Application_Properties {

	/**
	 * caption.invoiceitem.date = "Date"
	 */
	CAPTION_INVOICEITEM_DATE ("caption.invoiceitem.date"),
	/**
	 * caption.user.login = "Login"
	 */
	CAPTION_USER_LOGIN ("caption.user.login"),
	/**
	 * error.invoice.title.noitems = "Unable to create empty Invoice"
	 */
	ERROR_INVOICE_TITLE_NOITEMS ("error.invoice.title.noitems"),
	/**
	 * caption.invoice.sum = "Sum"
	 */
	CAPTION_INVOICE_SUM ("caption.invoice.sum"),
	/**
	 * caption.invoiceitem.participants = "Participants"
	 */
	CAPTION_INVOICEITEM_PARTICIPANTS ("caption.invoiceitem.participants"),
	/**
	 * caption.invoiceitem.add = "New Item"
	 */
	CAPTION_INVOICEITEM_ADD ("caption.invoiceitem.add"),
	/**
	 * caption.invoice.pattern = "Invoice-{0}"
	 */
	CAPTION_INVOICE_PATTERN ("caption.invoice.pattern"),
	/**
	 * caption.invoiceitem.start = "Start"
	 */
	CAPTION_INVOICEITEM_START ("caption.invoiceitem.start"),
	/**
	 * caption.article.report = "With License"
	 */
	CAPTION_ARTICLE_REPORT ("caption.article.report"),
	/**
	 * caption.invoice.invoiceno = "Invoice No"
	 */
	CAPTION_INVOICE_INVOICENO ("caption.invoice.invoiceno"),
	/**
	 * caption.invoiceitems = "Invoice Items"
	 */
	CAPTION_INVOICEITEMS ("caption.invoiceitems"),
	/**
	 * message.user.passwordmissmatch = "Passwords don't match."
	 */
	MESSAGE_USER_PASSWORDMISSMATCH ("message.user.passwordmissmatch"),
	/**
	 * label.delete = "Delete"
	 */
	LABEL_DELETE ("label.delete"),
	/**
	 * message.user.loginfailure = "Login Error! Wrong user or password?"
	 */
	MESSAGE_USER_LOGINFAILURE ("message.user.loginfailure"),
	/**
	 * caption.article.type.trainer = "Trainer"
	 */
	CAPTION_ARTICLE_TYPE_TRAINER ("caption.article.type.trainer"),
	/**
	 * caption.invoiceitem.sumprice = "sum"
	 */
	CAPTION_INVOICEITEM_SUMPRICE ("caption.invoiceitem.sumprice"),
	/**
	 * message.delete.text = "Delete {0}?"
	 */
	MESSAGE_DELETE_TEXT ("message.delete.text"),
	/**
	 * label.user.register = "Register"
	 */
	LABEL_USER_REGISTER ("label.user.register"),
	/**
	 * error.userdetails.bankname_empty = "Bankname cannot be empty."
	 */
	ERROR_USERDETAILS_BANKNAME_EMPTY ("error.userdetails.bankname_empty"),
	/**
	 * label.ok = "OK"
	 */
	LABEL_OK ("label.ok"),
	/**
	 * label.open = "Open"
	 */
	LABEL_OPEN ("label.open"),
	/**
	 * label.discart = "Discart"
	 */
	LABEL_DISCART ("label.discart"),
	/**
	 * caption.article = "Article"
	 */
	CAPTION_ARTICLE ("caption.article"),
	/**
	 * message.article.priceerror = "Please set the price."
	 */
	MESSAGE_ARTICLE_PRICEERROR ("message.article.priceerror"),
	/**
	 * error.userdetails.iban_empty = "Iban cannot be empty."
	 */
	ERROR_USERDETAILS_IBAN_EMPTY ("error.userdetails.iban_empty"),
	/**
	 * message.user.create.success = "Thanks {0} created!"
	 */
	MESSAGE_USER_CREATE_SUCCESS ("message.user.create.success"),
	/**
	 * error.userdetails.prename_empty = "Prename cannot be empty."
	 */
	ERROR_USERDETAILS_PRENAME_EMPTY ("error.userdetails.prename_empty"),
	/**
	 * error.invoice.text.noitems = "Please select items for the new invoice."
	 */
	ERROR_INVOICE_TEXT_NOITEMS ("error.invoice.text.noitems"),
	/**
	 * caption.invoiceitem.end = "End"
	 */
	CAPTION_INVOICEITEM_END ("caption.invoiceitem.end"),
	/**
	 * caption.invoiceitem = ""
	 */
	CAPTION_INVOICEITEM ("caption.invoiceitem"),
	/**
	 * caption.adress.zipcode = "Zip code"
	 */
	CAPTION_ADRESS_ZIPCODE ("caption.adress.zipcode"),
	/**
	 * caption.user.password = "Your Password:"
	 */
	CAPTION_USER_PASSWORD ("caption.user.password"),
	/**
	 * caption.invoiceitem.name = "Invoice Item"
	 */
	CAPTION_INVOICEITEM_NAME ("caption.invoiceitem.name"),
	/**
	 * message.delete.title = "Really delete?"
	 */
	MESSAGE_DELETE_TITLE ("message.delete.title"),
	/**
	 * error.userdetails.zip_empty = "Zipcode cannot be empty"
	 */
	ERROR_USERDETAILS_ZIP_EMPTY ("error.userdetails.zip_empty"),
	/**
	 * message.invoiceitem.allfieldsmustbeset = "Start, end and article must not be empty!"
	 */
	MESSAGE_INVOICEITEM_ALLFIELDSMUSTBESET ("message.invoiceitem.allfieldsmustbeset"),
	/**
	 * caption.invoices = "Invoices"
	 */
	CAPTION_INVOICES ("caption.invoices"),
	/**
	 * caption.user.passwordconfirmation = "Password confirmation:"
	 */
	CAPTION_USER_PASSWORDCONFIRMATION ("caption.user.passwordconfirmation"),
	/**
	 * caption.adress.city = "City"
	 */
	CAPTION_ADRESS_CITY ("caption.adress.city"),
	/**
	 * caption.article.title = "Title"
	 */
	CAPTION_ARTICLE_TITLE ("caption.article.title"),
	/**
	 * caption.article.price = "Price per Hour"
	 */
	CAPTION_ARTICLE_PRICE ("caption.article.price"),
	/**
	 * error.userdetails.adress_empty = "Adress cannot be empty"
	 */
	ERROR_USERDETAILS_ADRESS_EMPTY ("error.userdetails.adress_empty"),
	/**
	 * caption.bank.iban = "IBAN"
	 */
	CAPTION_BANK_IBAN ("caption.bank.iban"),
	/**
	 * caption.invoice.create = "Create invoice"
	 */
	CAPTION_INVOICE_CREATE ("caption.invoice.create"),
	/**
	 * caption.bank.bic = "BIC"
	 */
	CAPTION_BANK_BIC ("caption.bank.bic"),
	/**
	 * caption.article.type.assistant = "Assistant"
	 */
	CAPTION_ARTICLE_TYPE_ASSISTANT ("caption.article.type.assistant"),
	/**
	 * error.userdetails.city_empty = "City cannot be empty."
	 */
	ERROR_USERDETAILS_CITY_EMPTY ("error.userdetails.city_empty"),
	/**
	 * label.cancel = "Cancel"
	 */
	LABEL_CANCEL ("label.cancel"),
	/**
	 * caption.article.description = "Description"
	 */
	CAPTION_ARTICLE_DESCRIPTION ("caption.article.description"),
	/**
	 * caption.user.loginname = "Your Loginname:"
	 */
	CAPTION_USER_LOGINNAME ("caption.user.loginname"),
	/**
	 * message.article.error.invoiceexists = "Can not be changed due to existing invoice."
	 */
	MESSAGE_ARTICLE_ERROR_INVOICEEXISTS ("message.article.error.invoiceexists"),
	/**
	 * error.userdetails.surname_empty = "Surname cannot be empty."
	 */
	ERROR_USERDETAILS_SURNAME_EMPTY ("error.userdetails.surname_empty"),
	/**
	 * label.close = "Close"
	 */
	LABEL_CLOSE ("label.close"),
	/**
	 * caption.adress.street2 = "Adress"
	 */
	CAPTION_ADRESS_STREET2 ("caption.adress.street2"),
	/**
	 * caption.adress.street1 = "Adress"
	 */
	CAPTION_ADRESS_STREET1 ("caption.adress.street1"),
	/**
	 * label = ""
	 */
	LABEL ("label"),
	/**
	 * caption.user.surname = "Surname:"
	 */
	CAPTION_USER_SURNAME ("caption.user.surname"),
	/**
	 * label.loggedin = "Logged in:"
	 */
	LABEL_LOGGEDIN ("label.loggedin"),
	/**
	 * label.logout = "Logout"
	 */
	LABEL_LOGOUT ("label.logout"),
	/**
	 * caption.user.prename = "Prename:"
	 */
	CAPTION_USER_PRENAME ("caption.user.prename"),
	/**
	 * caption.bank.name = "Bank Name"
	 */
	CAPTION_BANK_NAME ("caption.bank.name"),
	/**
	 * label.preview = "Preview"
	 */
	LABEL_PREVIEW ("label.preview"),
	/**
	 * label.addarticle = "Add Article"
	 */
	LABEL_ADDARTICLE ("label.addarticle"),
	/**
	 * message.invoiceitem.startbeforeend = "End must be later than start."
	 */
	MESSAGE_INVOICEITEM_STARTBEFOREEND ("message.invoiceitem.startbeforeend"),
	/**
	 * caption.articles = "Articles"
	 */
	CAPTION_ARTICLES ("caption.articles"),
	/**
	 * caption.invoice.invoicedate = "Invoice Date"
	 */
	CAPTION_INVOICE_INVOICEDATE ("caption.invoice.invoicedate"),
	/**
	 * caption.user.details = "User Details"
	 */
	CAPTION_USER_DETAILS ("caption.user.details"),
	/**
	 * error.userdetails.username_empty = "UserName cannot be empty."
	 */
	ERROR_USERDETAILS_USERNAME_EMPTY ("error.userdetails.username_empty"),
	/**
	 * label.store = "Store"
	 */
	LABEL_STORE ("label.store");

	private final String value;

	private Application_Properties (String value) {
		this.value = value;
	}

	/**
	 * Represented Key in property File.
	 * @return key
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Resolves the value for this key from the parameter function.
	 * <p>
	 * e.g. <code>Application_Properties.getString(resBundle::getString)</code>
	 * @param resourceFunction {@link Properties#getProperty(String)} or {@link ResourceBundle#getString(String)}
	 * @return
	 */
	public String getString(UnaryOperator<String> resourceFunction) {
		return resourceFunction.apply(value);
	}
}
