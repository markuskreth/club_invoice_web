package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.Application_Properties.CAPTION_ADRESS_CITY;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_ADRESS_STREET1;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_ADRESS_STREET2;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_ADRESS_ZIPCODE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_BANK_BIC;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_BANK_IBAN;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_BANK_NAME;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_LOGINNAME;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_PRENAME;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_SURNAME;
import static de.kreth.clubinvoice.Application_Properties.ERROR_USERDETAILS_ADRESS_EMPTY;
import static de.kreth.clubinvoice.Application_Properties.ERROR_USERDETAILS_BANKNAME_EMPTY;
import static de.kreth.clubinvoice.Application_Properties.ERROR_USERDETAILS_CITY_EMPTY;
import static de.kreth.clubinvoice.Application_Properties.ERROR_USERDETAILS_IBAN_EMPTY;
import static de.kreth.clubinvoice.Application_Properties.ERROR_USERDETAILS_PRENAME_EMPTY;
import static de.kreth.clubinvoice.Application_Properties.ERROR_USERDETAILS_SURNAME_EMPTY;
import static de.kreth.clubinvoice.Application_Properties.ERROR_USERDETAILS_USERNAME_EMPTY;
import static de.kreth.clubinvoice.Application_Properties.ERROR_USERDETAILS_ZIP_EMPTY;
import static de.kreth.clubinvoice.Application_Properties.LABEL_CANCEL;
import static de.kreth.clubinvoice.Application_Properties.LABEL_OK;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.FileResource;
import com.vaadin.server.Setter;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.kreth.clubinvoice.Application_Properties;
import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.data.UserAdress;
import de.kreth.clubinvoice.data.UserBank;
import de.kreth.clubinvoice.report.Signature;

public class UserDetailsDialog extends Window {

	private static final long serialVersionUID = -6255487997073609068L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final Binder<User> beanBinder = new Binder<>();

	private final TextField loginName;

	private final TextField prename;

	private final TextField surname;

	private final TextField bankName;

	private final TextField iban;

	private final TextField bic;

	private final TextField adress1;

	private final TextField adress2;

	private final TextField zipCode;

	private final TextField city;

	private final Button okButton;

	private final Image signatureImage;

	private final ResourceBundle resBundle;

	public UserDetailsDialog(ResourceBundle resBundle) {
		this.resBundle = resBundle;
		loginName = new TextField();
		loginName.setCaption(getString(CAPTION_USER_LOGINNAME));

		loginName.setRequiredIndicatorVisible(true);
		beanBinder.forField(loginName)
				.asRequired(getString(ERROR_USERDETAILS_USERNAME_EMPTY))
				.bind(User::getLoginName, User::setLoginName);

		prename = new TextField();
		prename.setCaption(getString(CAPTION_USER_PRENAME));
		beanBinder.forField(prename)
				.asRequired(getString(ERROR_USERDETAILS_PRENAME_EMPTY))
				.bind(User::getPrename, User::setPrename);

		surname = new TextField();
		surname.setCaption(getString(CAPTION_USER_SURNAME));
		beanBinder.forField(surname)
				.asRequired(getString(ERROR_USERDETAILS_SURNAME_EMPTY))
				.bind(User::getSurname, User::setSurname);

		bankName = new TextField();
		bankName.setCaption(getString(CAPTION_BANK_NAME));
		beanBinder.forField(bankName)
				.asRequired(getString(ERROR_USERDETAILS_BANKNAME_EMPTY))
				.bind(new BankNameProvider(), new BankNameSetter());

		iban = new TextField();
		iban.setCaption(getString(CAPTION_BANK_IBAN));
		beanBinder.forField(iban)
				.asRequired(getString(ERROR_USERDETAILS_IBAN_EMPTY))
				.bind(new BankIbanProvider(), new BankIbanSetter());

		bic = new TextField();
		bic.setCaption(getString(CAPTION_BANK_BIC));
		beanBinder.forField(bic).bind(new BankBicProvider(), new BankBicSetter());

		adress1 = new TextField();
		adress1.setCaption(getString(CAPTION_ADRESS_STREET1));
		beanBinder.forField(adress1)
				.asRequired(getString(ERROR_USERDETAILS_ADRESS_EMPTY))
				.bind(new Adress1Provider(), new Adress1Setter());

		adress2 = new TextField();
		adress2.setCaption(getString(CAPTION_ADRESS_STREET2));
		beanBinder.forField(adress2).bind(new Adress2Provider(), new Adress2Setter());

		zipCode = new TextField();
		zipCode.setCaption(getString(CAPTION_ADRESS_ZIPCODE));
		beanBinder.forField(zipCode)
				.asRequired(getString(ERROR_USERDETAILS_ZIP_EMPTY))
				.bind(new ZipCodeProvider(), new ZipCodeSetter());

		city = new TextField();
		city.setCaption(getString(CAPTION_ADRESS_CITY));
		beanBinder.forField(city)
				.asRequired(getString(ERROR_USERDETAILS_CITY_EMPTY))
				.bind(new CityProvider(), new CitySetter());

		signatureImage = new Image("Unterschrift");
		signatureImage.setWidth("192px");
		signatureImage.setHeight("62px");
		signatureImage.setAlternateText("Keine Unterschrift konfiguriert");
		
		Upload upload = new Upload("Unterschrift Bild hochladen", this::receiveUpload);
		upload.addFinishedListener(ev -> updateSignatureImage());

		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(loginName, prename, surname);
		layout.addComponent(new Label("<hr />", ContentMode.HTML));
		layout.addComponents(bankName, iban, bic);
		layout.addComponent(new Label("<hr />", ContentMode.HTML));
		HorizontalLayout cityLayout = new HorizontalLayout();
		cityLayout.addComponents(zipCode, city);

		layout.addComponents(adress1, adress2, cityLayout, new HorizontalLayout(signatureImage, upload));

		okButton = new Button((getString(LABEL_OK)), ev -> {
			BinderValidationStatus<User> validation = beanBinder.validate();
			if (validation.isOk()) {
				close();
			}
		});

		Button cancel = new Button((getString(LABEL_CANCEL)), ev -> close());

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponents(okButton, cancel);
		layout.addComponent(buttons);

		setContent(layout);
	}

	private OutputStream receiveUpload(String filename, String mimeType) {
		User user = beanBinder.getBean();
		Signature signature = new Signature(user);
		try {
			return signature.createOutputStream(filename);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private String getString(Application_Properties property) {
		return property.getString(resBundle::getString);
	}

	public Registration addOkClickListener(ClickListener listener) {
		return okButton.addClickListener(listener);
	}

	public void setUser(User user) {
		beanBinder.setBean(user);
		updateSignatureImage();
	}

	private void updateSignatureImage() {
		User user = beanBinder.getBean();
		if (user != null) {
			Signature signature = new Signature(user);
			if (signature.isSignatureImageExists()) {
				File signatureUrl = signature.getSignatureUrl();
				logger.info("Showing signature: {}", signatureUrl);
				FileResource resource = new FileResource(signatureUrl);
				signatureImage.setSource(resource);
			}
			else {
				signatureImage.setSource(null);
			}
		}
	}

	public User getUser() {
		return beanBinder.getBean();
	}

	static class BankNameProvider implements ValueProvider<User, String> {

		private static final long serialVersionUID = -8618332916879793810L;

		@Override
		public String apply(User source) {
			if (source.getBank() == null) {
				return "";
			}
			return source.getBank().getBankName();
		}
	}

	class BankNameSetter implements Setter<User, String> {

		private static final long serialVersionUID = -6268229621228451775L;

		@Override
		public void accept(User bean, String fieldvalue) {
			UserBank bank = getBankFromUser(bean);
			bank.setBankName(fieldvalue);
		}

	}

	static class BankIbanProvider implements ValueProvider<User, String> {

		private static final long serialVersionUID = 1932625906615935431L;

		@Override
		public String apply(User source) {
			if (source.getBank() == null) {
				return "";
			}
			return source.getBank().getIban();
		}
	}

	class BankIbanSetter implements Setter<User, String> {

		private static final long serialVersionUID = 692606402537996082L;

		@Override
		public void accept(User bean, String fieldvalue) {
			UserBank bank = getBankFromUser(bean);
			bank.setIban(fieldvalue);
		}

	}

	static class BankBicProvider implements ValueProvider<User, String> {

		private static final long serialVersionUID = -7980150151044551352L;

		@Override
		public String apply(User source) {
			if (source.getBank() == null) {
				return "";
			}
			return source.getBank().getBic();
		}
	}

	class BankBicSetter implements Setter<User, String> {

		private static final long serialVersionUID = -7893980345474552470L;

		@Override
		public void accept(User bean, String fieldvalue) {
			UserBank bank = getBankFromUser(bean);
			bank.setBic(fieldvalue);
		}

	}

	static class Adress1Provider implements ValueProvider<User, String> {

		private static final long serialVersionUID = -7980150151044551352L;

		@Override
		public String apply(User source) {
			if (source.getAdress() == null) {
				return "";
			}
			return source.getAdress().getAdress1();
		}
	}

	class Adress1Setter implements Setter<User, String> {

		private static final long serialVersionUID = -7893980345474552470L;

		@Override
		public void accept(User bean, String fieldvalue) {
			UserAdress adress = getUserAdressFromUser(bean);
			adress.setAdress1(fieldvalue);
		}

	}

	static class Adress2Provider implements ValueProvider<User, String> {

		private static final long serialVersionUID = -7980150151044551352L;

		@Override
		public String apply(User source) {
			if (source.getAdress() == null) {
				return "";
			}
			return source.getAdress().getAdress2();
		}
	}

	class Adress2Setter implements Setter<User, String> {

		private static final long serialVersionUID = -7893980345474552470L;

		@Override
		public void accept(User bean, String fieldvalue) {
			UserAdress adress = getUserAdressFromUser(bean);
			adress.setAdress2(fieldvalue);
		}

	}

	static class ZipCodeProvider implements ValueProvider<User, String> {

		private static final long serialVersionUID = -7980150151044551352L;

		@Override
		public String apply(User source) {
			if (source.getAdress() == null) {
				return "";
			}
			return source.getAdress().getZip();
		}
	}

	class ZipCodeSetter implements Setter<User, String> {

		private static final long serialVersionUID = -7893980345474552470L;

		@Override
		public void accept(User bean, String fieldvalue) {
			UserAdress adress = getUserAdressFromUser(bean);
			adress.setZip(fieldvalue);
		}

	}

	static class CityProvider implements ValueProvider<User, String> {

		private static final long serialVersionUID = -7980150151044551352L;

		@Override
		public String apply(User source) {
			if (source.getAdress() == null) {
				return "";
			}
			return source.getAdress().getCity();
		}
	}

	class CitySetter implements Setter<User, String> {

		private static final long serialVersionUID = -7893980345474552470L;

		@Override
		public void accept(User bean, String fieldvalue) {
			UserAdress adress = getUserAdressFromUser(bean);
			adress.setCity(fieldvalue);
		}

	}

	UserBank getBankFromUser(User u) {

		UserBank bank = u.getBank();
		if (bank == null) {
			bank = new UserBank();
			u.setBank(bank);
		}
		return bank;
	}

	public UserAdress getUserAdressFromUser(User bean) {
		UserAdress adr = bean.getAdress();
		if (adr == null) {
			adr = new UserAdress();
			bean.setAdress(adr);
		}
		return adr;
	}
}
