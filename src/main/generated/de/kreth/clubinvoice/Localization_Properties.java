package de.kreth.clubinvoice;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.annotation.processing.Generated;

/**
 * Property keys from localization.properties
 */
@Generated(date = "18.10.2020, 21:37:18", value = "de.kreth.property2java.Generator")
public enum Localization_Properties {

	/**
	 * caption.adress.city = "Ort"
	 */
	CAPTION_ADRESS_CITY ("caption.adress.city"),
	/**
	 * caption.adress.street1 = "Adresse"
	 */
	CAPTION_ADRESS_STREET1 ("caption.adress.street1"),
	/**
	 * caption.adress.street2 = "Adresse"
	 */
	CAPTION_ADRESS_STREET2 ("caption.adress.street2"),
	/**
	 * caption.adress.zipcode = "Postleitzahl"
	 */
	CAPTION_ADRESS_ZIPCODE ("caption.adress.zipcode"),
	/**
	 * caption.article = "Artikel"
	 */
	CAPTION_ARTICLE ("caption.article"),
	/**
	 * caption.article.description = "Beschreibung"
	 */
	CAPTION_ARTICLE_DESCRIPTION ("caption.article.description"),
	/**
	 * caption.article.price = "Stundenpreis"
	 */
	CAPTION_ARTICLE_PRICE ("caption.article.price"),
	/**
	 * caption.article.report = "Mit Trainer-Lizenz"
	 */
	CAPTION_ARTICLE_REPORT ("caption.article.report"),
	/**
	 * caption.article.title = "Titel"
	 */
	CAPTION_ARTICLE_TITLE ("caption.article.title"),
	/**
	 * caption.article.type.assistant = "Übungsleiter"
	 */
	CAPTION_ARTICLE_TYPE_ASSISTANT ("caption.article.type.assistant"),
	/**
	 * caption.article.type.trainer = "Trainer"
	 */
	CAPTION_ARTICLE_TYPE_TRAINER ("caption.article.type.trainer"),
	/**
	 * caption.articles = "Artikel"
	 */
	CAPTION_ARTICLES ("caption.articles"),
	/**
	 * caption.bank.bic = "BIC"
	 */
	CAPTION_BANK_BIC ("caption.bank.bic"),
	/**
	 * caption.bank.iban = "IBAN"
	 */
	CAPTION_BANK_IBAN ("caption.bank.iban"),
	/**
	 * caption.bank.name = "Bankname"
	 */
	CAPTION_BANK_NAME ("caption.bank.name"),
	/**
	 * caption.invoice.create = "Rechnung erstellen"
	 */
	CAPTION_INVOICE_CREATE ("caption.invoice.create"),
	/**
	 * caption.invoice.invoicedate = "Rechnungsdatum"
	 */
	CAPTION_INVOICE_INVOICEDATE ("caption.invoice.invoicedate"),
	/**
	 * caption.invoice.invoiceno = "Rechnungsnummer"
	 */
	CAPTION_INVOICE_INVOICENO ("caption.invoice.invoiceno"),
	/**
	 * caption.invoice.pattern = "Rechnung-{0}"
	 */
	CAPTION_INVOICE_PATTERN ("caption.invoice.pattern"),
	/**
	 * caption.invoice.printsignature = "Unterschrift drucken"
	 */
	CAPTION_INVOICE_PRINTSIGNATURE ("caption.invoice.printsignature"),
	/**
	 * caption.invoice.sum = "Summe"
	 */
	CAPTION_INVOICE_SUM ("caption.invoice.sum"),
	/**
	 * caption.invoiceitem = ""
	 */
	CAPTION_INVOICEITEM ("caption.invoiceitem"),
	/**
	 * caption.invoiceitem.add = "Neuer Posten"
	 */
	CAPTION_INVOICEITEM_ADD ("caption.invoiceitem.add"),
	/**
	 * caption.invoiceitem.date = "Datum"
	 */
	CAPTION_INVOICEITEM_DATE ("caption.invoiceitem.date"),
	/**
	 * caption.invoiceitem.end = "Ende"
	 */
	CAPTION_INVOICEITEM_END ("caption.invoiceitem.end"),
	/**
	 * caption.invoiceitem.name = "Rechnungsposition"
	 */
	CAPTION_INVOICEITEM_NAME ("caption.invoiceitem.name"),
	/**
	 * caption.invoiceitem.participants = "Teilnehmer"
	 */
	CAPTION_INVOICEITEM_PARTICIPANTS ("caption.invoiceitem.participants"),
	/**
	 * caption.invoiceitem.start = "Beginn"
	 */
	CAPTION_INVOICEITEM_START ("caption.invoiceitem.start"),
	/**
	 * caption.invoiceitem.sumprice = "Betrag"
	 */
	CAPTION_INVOICEITEM_SUMPRICE ("caption.invoiceitem.sumprice"),
	/**
	 * caption.invoiceitems = "Rechnungspositionen"
	 */
	CAPTION_INVOICEITEMS ("caption.invoiceitems"),
	/**
	 * caption.invoices = "Rechnungen"
	 */
	CAPTION_INVOICES ("caption.invoices"),
	/**
	 * caption.user.details = "Benutzer Details"
	 */
	CAPTION_USER_DETAILS ("caption.user.details"),
	/**
	 * caption.user.login = "Anmelden"
	 */
	CAPTION_USER_LOGIN ("caption.user.login"),
	/**
	 * caption.user.loginname = "Anmeldename:"
	 */
	CAPTION_USER_LOGINNAME ("caption.user.loginname"),
	/**
	 * caption.user.password = "Ihr Password:"
	 */
	CAPTION_USER_PASSWORD ("caption.user.password"),
	/**
	 * caption.user.passwordconfirmation = "Password bestätigen:"
	 */
	CAPTION_USER_PASSWORDCONFIRMATION ("caption.user.passwordconfirmation"),
	/**
	 * caption.user.prename = "Vorname:"
	 */
	CAPTION_USER_PRENAME ("caption.user.prename"),
	/**
	 * caption.user.surname = "Nachname:"
	 */
	CAPTION_USER_SURNAME ("caption.user.surname"),
	/**
	 * error.article.undefined = "Bitte Artikel anlegen."
	 */
	ERROR_ARTICLE_UNDEFINED ("error.article.undefined"),
	/**
	 * error.invoice.text.noitems = "Bitte Posten für Rechnung auswählen."
	 */
	ERROR_INVOICE_TEXT_NOITEMS ("error.invoice.text.noitems"),
	/**
	 * error.invoice.title.noitems = "Leere Abrechnung nicht erlaubt."
	 */
	ERROR_INVOICE_TITLE_NOITEMS ("error.invoice.title.noitems"),
	/**
	 * error.userdetails.adress_empty = "Adresse darf nicht leer sein."
	 */
	ERROR_USERDETAILS_ADRESS_EMPTY ("error.userdetails.adress_empty"),
	/**
	 * error.userdetails.bankname_empty = "Bankname darf nicht leer sein."
	 */
	ERROR_USERDETAILS_BANKNAME_EMPTY ("error.userdetails.bankname_empty"),
	/**
	 * error.userdetails.city_empty = "Ort darf nicht leer sein."
	 */
	ERROR_USERDETAILS_CITY_EMPTY ("error.userdetails.city_empty"),
	/**
	 * error.userdetails.iban_empty = "Iban darf nicht leer sein."
	 */
	ERROR_USERDETAILS_IBAN_EMPTY ("error.userdetails.iban_empty"),
	/**
	 * error.userdetails.prename_empty = "Vorname darf nicht leer sein."
	 */
	ERROR_USERDETAILS_PRENAME_EMPTY ("error.userdetails.prename_empty"),
	/**
	 * error.userdetails.surname_empty = "Nachname darf nicht leer sein."
	 */
	ERROR_USERDETAILS_SURNAME_EMPTY ("error.userdetails.surname_empty"),
	/**
	 * error.userdetails.username_empty = "Anmeldename darf nicht leer sein."
	 */
	ERROR_USERDETAILS_USERNAME_EMPTY ("error.userdetails.username_empty"),
	/**
	 * error.userdetails.zip_empty = "Postleitzahl darf nicht leer sein."
	 */
	ERROR_USERDETAILS_ZIP_EMPTY ("error.userdetails.zip_empty"),
	/**
	 * label.addarticle = "Neuer Artikel"
	 */
	LABEL_ADDARTICLE ("label.addarticle"),
	/**
	 * label.cancel = "Abbrechen"
	 */
	LABEL_CANCEL ("label.cancel"),
	/**
	 * label.close = "Schließen"
	 */
	LABEL_CLOSE ("label.close"),
	/**
	 * label.delete = "Löschen"
	 */
	LABEL_DELETE ("label.delete"),
	/**
	 * label.discart = "Verwerfen"
	 */
	LABEL_DISCART ("label.discart"),
	/**
	 * label.loggedin = "Angemeldet:"
	 */
	LABEL_LOGGEDIN ("label.loggedin"),
	/**
	 * label.logout = "Abmelden"
	 */
	LABEL_LOGOUT ("label.logout"),
	/**
	 * label.ok = "OK"
	 */
	LABEL_OK ("label.ok"),
	/**
	 * label.open = "Öffnen"
	 */
	LABEL_OPEN ("label.open"),
	/**
	 * label.preview = "Vorschau"
	 */
	LABEL_PREVIEW ("label.preview"),
	/**
	 * label.store = "Speichern"
	 */
	LABEL_STORE ("label.store"),
	/**
	 * label.user.register = "Registrieren"
	 */
	LABEL_USER_REGISTER ("label.user.register"),
	/**
	 * message.article.error.invoiceexists = "Kann nicht geändert werden, da bereits Rechnungen bestehen. Bitte neuen Artikel anlegen."
	 */
	MESSAGE_ARTICLE_ERROR_INVOICEEXISTS ("message.article.error.invoiceexists"),
	/**
	 * message.article.priceerror = "Bitte legen Sie den Preis fest."
	 */
	MESSAGE_ARTICLE_PRICEERROR ("message.article.priceerror"),
	/**
	 * message.delete.text = "{0} löschen?"
	 */
	MESSAGE_DELETE_TEXT ("message.delete.text"),
	/**
	 * message.delete.title = "Wirklich löschen?"
	 */
	MESSAGE_DELETE_TITLE ("message.delete.title"),
	/**
	 * message.invoiceitem.allfieldsmustbeset = "Start, Ende und Artikel müssen gesetzt sein!"
	 */
	MESSAGE_INVOICEITEM_ALLFIELDSMUSTBESET ("message.invoiceitem.allfieldsmustbeset"),
	/**
	 * message.invoiceitem.startbeforeend = "Ende darf nicht vor Start liegen."
	 */
	MESSAGE_INVOICEITEM_STARTBEFOREEND ("message.invoiceitem.startbeforeend"),
	/**
	 * message.user.create.failure = "Fehler beim Erstellen von Benutzer {0}! Ändern Sie den Benutzernamen oder fragen Sie nach dem Passwort. Detail: {1}"
	 */
	MESSAGE_USER_CREATE_FAILURE ("message.user.create.failure"),
	/**
	 * message.user.create.success = "{0} erstellt!"
	 */
	MESSAGE_USER_CREATE_SUCCESS ("message.user.create.success"),
	/**
	 * message.user.loginfailure = "Anmeldefehler! Falscher Name oder Passwort?"
	 */
	MESSAGE_USER_LOGINFAILURE ("message.user.loginfailure"),
	/**
	 * message.user.passwordmissmatch = "Passworter stimmen nicht überein!"
	 */
	MESSAGE_USER_PASSWORDMISSMATCH ("message.user.passwordmissmatch");

	private final String value;

	private Localization_Properties (String value) {
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
	 * e.g. <code>Localization_Properties.getString(resBundle::getString)</code>
	 * @param resourceFunction {@link Properties#getProperty(String)} or {@link ResourceBundle#getString(String)}
	 * @return
	 */
	public String getString(UnaryOperator<String> resourceFunction) {
		return resourceFunction.apply(value);
	}
}
