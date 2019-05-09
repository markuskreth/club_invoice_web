package de.kreth.clubinvoice;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.annotation.processing.Generated;

/**
 * Property keys from application_de_DE.properties
 */
@Generated(date = "09.05.2019, 20:30:32", value = "de.kreth.property2java.Generator")
public enum Application_Properties {

	/**
	 * caption.invoiceitem.date = "Datum"
	 */
	CAPTION_INVOICEITEM_DATE ("caption.invoiceitem.date"),
	/**
	 * caption.user.login = "Anmelden"
	 */
	CAPTION_USER_LOGIN ("caption.user.login"),
	/**
	 * error.invoice.title.noitems = "Leere Abrechnung nicht erlaubt."
	 */
	ERROR_INVOICE_TITLE_NOITEMS ("error.invoice.title.noitems"),
	/**
	 * caption.invoice.sum = "Summe"
	 */
	CAPTION_INVOICE_SUM ("caption.invoice.sum"),
	/**
	 * caption.invoiceitem.participants = "Teilnehmer"
	 */
	CAPTION_INVOICEITEM_PARTICIPANTS ("caption.invoiceitem.participants"),
	/**
	 * caption.invoiceitem.add = "Neuer Posten"
	 */
	CAPTION_INVOICEITEM_ADD ("caption.invoiceitem.add"),
	/**
	 * caption.invoice.pattern = "Rechnung-{0}"
	 */
	CAPTION_INVOICE_PATTERN ("caption.invoice.pattern"),
	/**
	 * caption.invoiceitem.start = "Beginn"
	 */
	CAPTION_INVOICEITEM_START ("caption.invoiceitem.start"),
	/**
	 * caption.article.report = "Mit Trainer-Lizenz"
	 */
	CAPTION_ARTICLE_REPORT ("caption.article.report"),
	/**
	 * caption.invoice.invoiceno = "Rechnungsnummer"
	 */
	CAPTION_INVOICE_INVOICENO ("caption.invoice.invoiceno"),
	/**
	 * caption.invoiceitems = "Rechnungspositionen"
	 */
	CAPTION_INVOICEITEMS ("caption.invoiceitems"),
	/**
	 * message.user.passwordmissmatch = "Passworter stimmen nicht überein!"
	 */
	MESSAGE_USER_PASSWORDMISSMATCH ("message.user.passwordmissmatch"),
	/**
	 * label.delete = "Löschen"
	 */
	LABEL_DELETE ("label.delete"),
	/**
	 * message.user.loginfailure = "Anmeldefehler! Falscher Name oder Passwort?"
	 */
	MESSAGE_USER_LOGINFAILURE ("message.user.loginfailure"),
	/**
	 * caption.article.type.trainer = "Trainer"
	 */
	CAPTION_ARTICLE_TYPE_TRAINER ("caption.article.type.trainer"),
	/**
	 * caption.invoiceitem.sumprice = "Betrag"
	 */
	CAPTION_INVOICEITEM_SUMPRICE ("caption.invoiceitem.sumprice"),
	/**
	 * message.delete.text = "{0} löschen?"
	 */
	MESSAGE_DELETE_TEXT ("message.delete.text"),
	/**
	 * label.user.register = "Registrieren"
	 */
	LABEL_USER_REGISTER ("label.user.register"),
	/**
	 * error.userdetails.bankname_empty = "Bankname darf nicht leer sein."
	 */
	ERROR_USERDETAILS_BANKNAME_EMPTY ("error.userdetails.bankname_empty"),
	/**
	 * label.ok = "OK"
	 */
	LABEL_OK ("label.ok"),
	/**
	 * label.open = "Öffnen"
	 */
	LABEL_OPEN ("label.open"),
	/**
	 * label.discart = "Verwerfen"
	 */
	LABEL_DISCART ("label.discart"),
	/**
	 * caption.article = "Artikel"
	 */
	CAPTION_ARTICLE ("caption.article"),
	/**
	 * message.article.priceerror = "Bitte legen Sie den Preis fest."
	 */
	MESSAGE_ARTICLE_PRICEERROR ("message.article.priceerror"),
	/**
	 * error.userdetails.iban_empty = "Iban darf nicht leer sein."
	 */
	ERROR_USERDETAILS_IBAN_EMPTY ("error.userdetails.iban_empty"),
	/**
	 * message.user.create.success = "{0} erstellt!"
	 */
	MESSAGE_USER_CREATE_SUCCESS ("message.user.create.success"),
	/**
	 * error.userdetails.prename_empty = "Vorname darf nicht leer sein."
	 */
	ERROR_USERDETAILS_PRENAME_EMPTY ("error.userdetails.prename_empty"),
	/**
	 * error.invoice.text.noitems = "Bitte Posten für Rechnung auswählen."
	 */
	ERROR_INVOICE_TEXT_NOITEMS ("error.invoice.text.noitems"),
	/**
	 * caption.invoiceitem.end = "Ende"
	 */
	CAPTION_INVOICEITEM_END ("caption.invoiceitem.end"),
	/**
	 * caption.invoiceitem = ""
	 */
	CAPTION_INVOICEITEM ("caption.invoiceitem"),
	/**
	 * caption.adress.zipcode = "Postleitzahl"
	 */
	CAPTION_ADRESS_ZIPCODE ("caption.adress.zipcode"),
	/**
	 * caption.user.password = "Ihr Password:"
	 */
	CAPTION_USER_PASSWORD ("caption.user.password"),
	/**
	 * caption.invoiceitem.name = "Rechnungsposition"
	 */
	CAPTION_INVOICEITEM_NAME ("caption.invoiceitem.name"),
	/**
	 * message.delete.title = "Wirklich löschen?"
	 */
	MESSAGE_DELETE_TITLE ("message.delete.title"),
	/**
	 * error.userdetails.zip_empty = "Postleitzahl darf nicht leer sein."
	 */
	ERROR_USERDETAILS_ZIP_EMPTY ("error.userdetails.zip_empty"),
	/**
	 * message.invoiceitem.allfieldsmustbeset = "Start, Ende und Artikel müssen gesetzt sein!"
	 */
	MESSAGE_INVOICEITEM_ALLFIELDSMUSTBESET ("message.invoiceitem.allfieldsmustbeset"),
	/**
	 * caption.invoices = "Rechnungen"
	 */
	CAPTION_INVOICES ("caption.invoices"),
	/**
	 * caption.user.passwordconfirmation = "Password bestätigen:"
	 */
	CAPTION_USER_PASSWORDCONFIRMATION ("caption.user.passwordconfirmation"),
	/**
	 * caption.adress.city = "Ort"
	 */
	CAPTION_ADRESS_CITY ("caption.adress.city"),
	/**
	 * caption.article.title = "Titel"
	 */
	CAPTION_ARTICLE_TITLE ("caption.article.title"),
	/**
	 * caption.article.price = "Stundenpreis"
	 */
	CAPTION_ARTICLE_PRICE ("caption.article.price"),
	/**
	 * error.userdetails.adress_empty = "Adresse darf nicht leer sein."
	 */
	ERROR_USERDETAILS_ADRESS_EMPTY ("error.userdetails.adress_empty"),
	/**
	 * caption.bank.iban = "IBAN"
	 */
	CAPTION_BANK_IBAN ("caption.bank.iban"),
	/**
	 * caption.invoice.create = "Rechnung erstellen"
	 */
	CAPTION_INVOICE_CREATE ("caption.invoice.create"),
	/**
	 * caption.bank.bic = "BIC"
	 */
	CAPTION_BANK_BIC ("caption.bank.bic"),
	/**
	 * caption.article.type.assistant = "Übungsleiter"
	 */
	CAPTION_ARTICLE_TYPE_ASSISTANT ("caption.article.type.assistant"),
	/**
	 * error.userdetails.city_empty = "Ort darf nicht leer sein."
	 */
	ERROR_USERDETAILS_CITY_EMPTY ("error.userdetails.city_empty"),
	/**
	 * label.cancel = "Abbrechen"
	 */
	LABEL_CANCEL ("label.cancel"),
	/**
	 * message.user.create.failure = "Fehler beim Erstellen von Benutzer {0}! Ändern Sie den Benutzernamen oder fragen Sie nach dem Passwort. Detail: {1}"
	 */
	MESSAGE_USER_CREATE_FAILURE ("message.user.create.failure"),
	/**
	 * caption.article.description = "Beschreibung"
	 */
	CAPTION_ARTICLE_DESCRIPTION ("caption.article.description"),
	/**
	 * caption.user.loginname = "Anmeldename:"
	 */
	CAPTION_USER_LOGINNAME ("caption.user.loginname"),
	/**
	 * message.article.error.invoiceexists = "Kann nicht geändert werden, da bereits Rechnungen bestehen. Bitte neuen Artikel anlegen."
	 */
	MESSAGE_ARTICLE_ERROR_INVOICEEXISTS ("message.article.error.invoiceexists"),
	/**
	 * error.userdetails.surname_empty = "Nachname darf nicht leer sein."
	 */
	ERROR_USERDETAILS_SURNAME_EMPTY ("error.userdetails.surname_empty"),
	/**
	 * label.close = "Schließen"
	 */
	LABEL_CLOSE ("label.close"),
	/**
	 * caption.adress.street2 = "Adresse"
	 */
	CAPTION_ADRESS_STREET2 ("caption.adress.street2"),
	/**
	 * caption.adress.street1 = "Adresse"
	 */
	CAPTION_ADRESS_STREET1 ("caption.adress.street1"),
	/**
	 * caption.user.surname = "Nachname:"
	 */
	CAPTION_USER_SURNAME ("caption.user.surname"),
	/**
	 * label.loggedin = "Angemeldet:"
	 */
	LABEL_LOGGEDIN ("label.loggedin"),
	/**
	 * label.logout = "Abmelden"
	 */
	LABEL_LOGOUT ("label.logout"),
	/**
	 * caption.user.prename = "Vorname:"
	 */
	CAPTION_USER_PRENAME ("caption.user.prename"),
	/**
	 * caption.bank.name = "Bankname"
	 */
	CAPTION_BANK_NAME ("caption.bank.name"),
	/**
	 * label.preview = "Vorschau"
	 */
	LABEL_PREVIEW ("label.preview"),
	/**
	 * label.addarticle = "Neuer Artikel"
	 */
	LABEL_ADDARTICLE ("label.addarticle"),
	/**
	 * message.invoiceitem.startbeforeend = "Ende darf nicht vor Start liegen."
	 */
	MESSAGE_INVOICEITEM_STARTBEFOREEND ("message.invoiceitem.startbeforeend"),
	/**
	 * caption.articles = "Artikel"
	 */
	CAPTION_ARTICLES ("caption.articles"),
	/**
	 * caption.invoice.invoicedate = "Rechnungsdatum"
	 */
	CAPTION_INVOICE_INVOICEDATE ("caption.invoice.invoicedate"),
	/**
	 * caption.user.details = "Benutzer Details"
	 */
	CAPTION_USER_DETAILS ("caption.user.details"),
	/**
	 * error.userdetails.username_empty = "Anmeldename darf nicht leer sein."
	 */
	ERROR_USERDETAILS_USERNAME_EMPTY ("error.userdetails.username_empty"),
	/**
	 * label.store = "Speichern"
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
