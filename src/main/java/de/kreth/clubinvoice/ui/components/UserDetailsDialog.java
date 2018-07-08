package de.kreth.clubinvoice.ui.components;

import java.util.ResourceBundle;

import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
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
import de.kreth.clubinvoice.data.UserBank;

public class UserDetailsDialog extends Window {

	private static final long serialVersionUID = -6255487997073609068L;

	private TextField loginName;
	private TextField prename;
	private TextField surname;

	private TextField bankName;
	private TextField iban;
	private TextField bic;

	private Button okButton;

	Binder<User> beanBinder = new Binder<>();

	public UserDetailsDialog(ResourceBundle resBundle) {
		loginName = new TextField();
		loginName.setCaption(resBundle.getString("caption.user.loginname"));

		loginName.setRequiredIndicatorVisible(true);
		beanBinder.forField(loginName).asRequired("UserName cannot be empty.")
				.bind(User::getLoginName, User::setLoginName);

		prename = new TextField();
		prename.setCaption(resBundle.getString("caption.user.prename"));
		beanBinder.forField(prename).asRequired("Prename cannot be empty.")
				.bind(User::getPrename, User::setPrename);

		surname = new TextField();
		surname.setCaption(resBundle.getString("caption.user.surname"));
		beanBinder.forField(surname).asRequired("Surname cannot be empty.")
				.bind(User::getSurname, User::setSurname);

		bankName = new TextField();
		bankName.setCaption("BankName");
		beanBinder.forField(bankName).asRequired("Bankname cannot be empty.")
				.bind(new BankNameProvider(), new BankNameSetter());

		iban = new TextField();
		iban.setCaption("IBAN");
		beanBinder.forField(iban).asRequired("Iban cannot be empty.")
				.bind(new BankIbanProvider(), new BankIbanSetter());

		bic = new TextField();
		bic.setCaption("BIC");
		beanBinder.forField(bic).bind(new BankBicProvider(),
				new BankBicSetter());

		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(loginName, prename, surname);
		layout.addComponent(new Label("<hr />", ContentMode.HTML));
		layout.addComponents(bankName, iban, bic);

		okButton = new Button(resBundle.getString("label.ok"), ev -> {
			BinderValidationStatus<User> validation = beanBinder.validate();
			if (validation.isOk()) {
				close();
			}
		});

		Button cancel = new Button(resBundle.getString("label.cancel"),
				ev -> close());

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

	class UserDetailValidator implements Validator<User> {

		private static final long serialVersionUID = 3579978389383152777L;

		@Override
		public ValidationResult apply(User value, ValueContext context) {

			return null;
		}

	}

	class BankNameProvider implements ValueProvider<User, String> {

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

	class BankIbanProvider implements ValueProvider<User, String> {

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
			bank.setIban(fieldvalue);;
		}

	}

	class BankBicProvider implements ValueProvider<User, String> {

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

	UserBank getBankFromUser(User u) {

		UserBank bank = u.getBank();
		if (bank == null) {
			bank = new UserBank();
			u.setBank(bank);
		}
		return bank;
	}
}
