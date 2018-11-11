package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.ui.Constants.CAPTION_ADRESS_CITY;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_ADRESS_STREET1;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_ADRESS_STREET2;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_ADRESS_ZIPCODE;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_BANK_BIC;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_BANK_IBAN;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_BANK_NAME;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_USER_LOGINNAME;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_USER_PRENAME;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_USER_SURNAME;
import static de.kreth.clubinvoice.ui.Constants.LABEL_CANCEL;
import static de.kreth.clubinvoice.ui.Constants.LABEL_OK;

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

import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.data.UserAdress;
import de.kreth.clubinvoice.data.UserBank;

public class UserDetailsDialog extends Window {

	private static final long serialVersionUID = -6255487997073609068L;

	private TextField loginName;
	private TextField prename;
	private TextField surname;

	private TextField bankName;
	private TextField iban;
	private TextField bic;

	private TextField adress1;
	private TextField adress2;
	private TextField zipCode;
	private TextField city;

	private Button okButton;

	Binder<User> beanBinder = new Binder<>();

	public UserDetailsDialog(ResourceBundle resBundle) {
		loginName = new TextField();
		loginName.setCaption(resBundle.getString(CAPTION_USER_LOGINNAME));

		loginName.setRequiredIndicatorVisible(true);
		beanBinder.forField(loginName).asRequired(resBundle.getString("error.userdetails.username_empty"))
				.bind(User::getLoginName, User::setLoginName);

		prename = new TextField();
		prename.setCaption(resBundle.getString(CAPTION_USER_PRENAME));
		beanBinder.forField(prename).asRequired(resBundle.getString("error.userdetails.prename_empty"))
				.bind(User::getPrename, User::setPrename);

		surname = new TextField();
		surname.setCaption(resBundle.getString(CAPTION_USER_SURNAME));
		beanBinder.forField(surname).asRequired(resBundle.getString("error.userdetails.surname_empty"))
				.bind(User::getSurname, User::setSurname);

		bankName = new TextField();
		bankName.setCaption(resBundle.getString(CAPTION_BANK_NAME));
		beanBinder.forField(bankName).asRequired(resBundle.getString("error.userdetails.bankname_empty"))
				.bind(new BankNameProvider(), new BankNameSetter());

		iban = new TextField();
		iban.setCaption(resBundle.getString(CAPTION_BANK_IBAN));
		beanBinder.forField(iban).asRequired(resBundle.getString("error.userdetails.iban_empty"))
				.bind(new BankIbanProvider(), new BankIbanSetter());

		bic = new TextField();
		bic.setCaption(resBundle.getString(CAPTION_BANK_BIC));
		beanBinder.forField(bic).bind(new BankBicProvider(), new BankBicSetter());

		adress1 = new TextField();
		adress1.setCaption(resBundle.getString(CAPTION_ADRESS_STREET1));
		beanBinder.forField(adress1).asRequired(resBundle.getString("error.userdetails.adress_empty"))
				.bind(new Adress1Provider(), new Adress1Setter());

		adress2 = new TextField();
		adress2.setCaption(resBundle.getString(CAPTION_ADRESS_STREET2));
		beanBinder.forField(adress2).bind(new Adress2Provider(), new Adress2Setter());

		zipCode = new TextField();
		zipCode.setCaption(resBundle.getString(CAPTION_ADRESS_ZIPCODE));
		beanBinder.forField(zipCode).asRequired(resBundle.getString("error.userdetails.zip_empty"))
				.bind(new ZipCodeProvider(), new ZipCodeSetter());

		city = new TextField();
		city.setCaption(resBundle.getString(CAPTION_ADRESS_CITY));
		beanBinder.forField(city).asRequired(resBundle.getString("error.userdetails.city_empty"))
				.bind(new CityProvider(), new CitySetter());

		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(loginName, prename, surname);
		layout.addComponent(new Label("<hr />", ContentMode.HTML));
		layout.addComponents(bankName, iban, bic);
		layout.addComponent(new Label("<hr />", ContentMode.HTML));
		HorizontalLayout cityLayout = new HorizontalLayout();
		cityLayout.addComponents(zipCode, city);

		layout.addComponents(adress1, adress2, cityLayout);

		okButton = new Button(resBundle.getString(LABEL_OK), ev -> {
			BinderValidationStatus<User> validation = beanBinder.validate();
			if (validation.isOk()) {
				close();
			}
		});

		Button cancel = new Button(resBundle.getString(LABEL_CANCEL), ev -> close());

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
			;
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
