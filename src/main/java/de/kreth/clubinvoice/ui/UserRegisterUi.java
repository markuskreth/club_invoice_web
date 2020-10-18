package de.kreth.clubinvoice.ui;

import static de.kreth.clubinvoice.Localization_Properties.CAPTION_USER_LOGINNAME;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_USER_PASSWORD;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_USER_PASSWORDCONFIRMATION;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_USER_PRENAME;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_USER_SURNAME;
import static de.kreth.clubinvoice.Localization_Properties.LABEL_USER_REGISTER;
import static de.kreth.clubinvoice.Localization_Properties.MESSAGE_USER_CREATE_FAILURE;
import static de.kreth.clubinvoice.Localization_Properties.MESSAGE_USER_CREATE_SUCCESS;
import static de.kreth.clubinvoice.Localization_Properties.MESSAGE_USER_PASSWORDMISSMATCH;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javax.persistence.PersistenceException;

import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kreth.clubinvoice.Localization_Properties;
import de.kreth.clubinvoice.business.OverviewBusiness;
import de.kreth.clubinvoice.business.UserRegister;
import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.utils.ResourceBundleProvider;

public class UserRegisterUi extends VerticalLayout implements InvoiceUi {

	private static final long serialVersionUID = -7409842921091527159L;

	public static final String ID_LOGIN_NAME = UserRegisterUi.class.getName() + ".loginText";

	private final transient UserRegister business;

	private final transient ResourceBundle resBundle;

	private TextField loginName;

	private PasswordField passwordField;

	private TextField prename;

	private TextField surname;

	private Button button;

	private PasswordField passwordFieldVerification;

	private Label errorLabel;

	public UserRegisterUi(UserRegister business) {
		this.business = business;
		this.resBundle = ResourceBundleProvider.getBundle();
	}

	@Override
	public void setContent(final UI ui, final VaadinRequest vaadinRequest) {

		loginName = new TextField();
		loginName.setId(ID_LOGIN_NAME);
		loginName.setCaption(getString(CAPTION_USER_LOGINNAME));

		passwordField = new PasswordField();
		passwordField.setCaption(getString(CAPTION_USER_PASSWORD));

		passwordFieldVerification = new PasswordField();
		passwordFieldVerification.setCaption(getString(CAPTION_USER_PASSWORDCONFIRMATION));

		prename = new TextField();
		prename.setCaption(getString(CAPTION_USER_PRENAME));

		surname = new TextField();
		surname.setCaption(getString(CAPTION_USER_SURNAME));

		button = new Button(getString(LABEL_USER_REGISTER));
		button.addClickListener(e -> {
			if (inputIsValid() && storeUserData()) {
				OverviewBusiness overViewBusiness = new OverviewBusiness(business.getSessionObj(),
						business.getStore(),
						business.getCookieStore());
				OverviewUi overview = new OverviewUi(business.getStore(), overViewBusiness);
				overview.setContent(ui, vaadinRequest);
			}
		});

		addComponents(loginName, prename, surname, passwordField, passwordFieldVerification, button);

		ui.setContent(this);
	}

	private boolean inputIsValid() {
		String value = passwordField.getValue();
		String repeat = passwordFieldVerification.getValue();

		passwordField.setComponentError(null);
		boolean isValid = true;
		if (value.equals(repeat) == false) {
			UserError err = new UserError(getString(MESSAGE_USER_PASSWORDMISSMATCH));
			passwordField.setComponentError(err);
			passwordFieldVerification.setComponentError(err);
			isValid = false;
		}

		return isValid;
	}

	private boolean storeUserData() {
		if (errorLabel != null) {
			removeComponent(errorLabel);
			errorLabel = null;
			button.setComponentError(null);
			loginName.setComponentError(null);
		}
		User user = new User();
		user.setLoginName(loginName.getValue());
		user.setPrename(prename.getValue());
		user.setSurname(surname.getValue());
		user.setPassword(passwordField.getValue());
		user.setCreatedDate(LocalDateTime.now());
		user.setChangeDate(LocalDateTime.now());

		try {
			business.save(user);
			addComponent(
					new Label(MessageFormat.format(getString(MESSAGE_USER_CREATE_SUCCESS), user)));
			return true;
		}
		catch (PersistenceException ex) {

			Throwable e = ex;
			while (e.getCause() != null) {
				e = e.getCause();
			}
			String formated = MessageFormat.format(getString(MESSAGE_USER_CREATE_FAILURE), user.getLoginName(),
					e.getLocalizedMessage());
			UserError componentError = new UserError(formated);
			button.setComponentError(componentError);
			loginName.setComponentError(componentError);
			errorLabel = new Label(formated);
			addComponent(errorLabel);
			return false;
		}

	}

	private String getString(Localization_Properties properties) {
		return properties.getString(resBundle::getString);
	}

}
