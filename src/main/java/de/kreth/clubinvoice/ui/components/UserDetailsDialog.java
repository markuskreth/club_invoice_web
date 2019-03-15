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
import static de.kreth.clubinvoice.Application_Properties.LABEL_CANCEL;
import static de.kreth.clubinvoice.Application_Properties.LABEL_OK;

import java.util.ResourceBundle;

import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.kreth.clubinvoice.Application_Properties;
import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.data.UserAdress;
import de.kreth.clubinvoice.data.UserBank;

public class UserDetailsDialog extends Window {

	private static final long serialVersionUID = -6255487997073609068L;

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

	public UserDetailsDialog(ResourceBundle resBundle) {
		loginName = new TextField();
		loginName.setCaption(resBundle.getString(CAPTION_USER_LOGINNAME.getValue()));

		loginName.setRequiredIndicatorVisible(true);
		beanBinder.forField(loginName)
				.asRequired(resBundle.getString(Application_Properties.ERROR_USERDETAILS_USERNAME_EMPTY.getValue()))
				.bind(User::getLoginName, User::setLoginName);

		prename = new TextField();
		prename.setCaption(resBundle.getString(CAPTION_USER_PRENAME.getValue()));
		beanBinder.forField(prename)
				.asRequired(resBundle.getString(Application_Properties.ERROR_USERDETAILS_PRENAME_EMPTY.getValue()))
				.bind(User::getPrename, User::setPrename);

		surname = new TextField();
		surname.setCaption(resBundle.getString(CAPTION_USER_SURNAME.getValue()));
		beanBinder.forField(surname)
				.asRequired(resBundle.getString(Application_Properties.ERROR_USERDETAILS_SURNAME_EMPTY.getValue()))
				.bind(User::getSurname, User::setSurname);

		bankName = new TextField();
		bankName.setCaption(resBundle.getString(CAPTION_BANK_NAME.getValue()));
		beanBinder.forField(bankName)
				.asRequired(resBundle.getString(Application_Properties.ERROR_USERDETAILS_BANKNAME_EMPTY.getValue()))
				.bind(new BankNameProvider(), new BankNameSetter());

		iban = new TextField();
		iban.setCaption(resBundle.getString(CAPTION_BANK_IBAN.getValue()));
		beanBinder.forField(iban)
				.asRequired(resBundle.getString(Application_Properties.ERROR_USERDETAILS_IBAN_EMPTY.getValue()))
				.bind(new BankIbanProvider(), new BankIbanSetter());

		bic = new TextField();
		bic.setCaption(resBundle.getString(CAPTION_BANK_BIC.getValue()));
		beanBinder.forField(bic).bind(new BankBicProvider(), new BankBicSetter());

		adress1 = new TextField();
		adress1.setCaption(resBundle.getString(CAPTION_ADRESS_STREET1.getValue()));
		beanBinder.forField(adress1)
				.asRequired(resBundle.getString(Application_Properties.ERROR_USERDETAILS_ADRESS_EMPTY.getValue()))
				.bind(new Adress1Provider(), new Adress1Setter());

		adress2 = new TextField();
		adress2.setCaption(resBundle.getString(CAPTION_ADRESS_STREET2.getValue()));
		beanBinder.forField(adress2).bind(new Adress2Provider(), new Adress2Setter());

		zipCode = new TextField();
		zipCode.setCaption(resBundle.getString(CAPTION_ADRESS_ZIPCODE.getValue()));
		beanBinder.forField(zipCode)
				.asRequired(resBundle.getString(Application_Properties.ERROR_USERDETAILS_ZIP_EMPTY.getValue()))
				.bind(new ZipCodeProvider(), new ZipCodeSetter());

		city = new TextField();
		city.setCaption(resBundle.getString(CAPTION_ADRESS_CITY.getValue()));
		beanBinder.forField(city)
				.asRequired(resBundle.getString(Application_Properties.ERROR_USERDETAILS_CITY_EMPTY.getValue()))
				.bind(new CityProvider(), new CitySetter());

		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(loginName, prename, surname);
		layout.addComponent(new Label("<hr />", ContentMode.HTML));
		layout.addComponents(bankName, iban, bic);
		layout.addComponent(new Label("<hr />", ContentMode.HTML));
		HorizontalLayout cityLayout = new HorizontalLayout();
		cityLayout.addComponents(zipCode, city);

		layout.addComponents(adress1, adress2, cityLayout);

		okButton = new Button((LABEL_OK.getString(resBundle::getString)), ev -> {
			BinderValidationStatus<User> validation = beanBinder.validate();
			if (validation.isOk()) {
				close();
			}
		});

		Button cancel = new Button((LABEL_CANCEL.getString(resBundle::getString)), ev -> close());

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponents(okButton, cancel);
		layout.addComponent(buttons);

		setContent(layout);
	}

	public Registration addOkClickListener(ClickListener listener) {
		return okButton.addClickListener(listener);
	}

	public void setUser(User user) {
		beanBinder.setBean(user);
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
