package de.kreth.clubinvoice.ui;

import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_LOGINNAME;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_PASSWORD;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_PASSWORDCONFIRMATION;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_PRENAME;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_SURNAME;
import static de.kreth.clubinvoice.Application_Properties.MESSAGE_USER_CREATE_SUCCESS;
import static de.kreth.clubinvoice.Application_Properties.MESSAGE_USER_PASSWORDMISSMATCH;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kreth.clubinvoice.Application_Properties;
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

	public UserRegisterUi(UserRegister business) {
		this.business = business;
		this.resBundle = ResourceBundleProvider.getBundle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.kreth.clubinvoice.ui.InvoiceUi#setContent(com.vaadin.ui.UI)
	 */
	@Override
	public void setContent(final UI ui, final VaadinRequest vaadinRequest) {

		loginName = new TextField();
		loginName.setId(ID_LOGIN_NAME);
		loginName.setCaption(CAPTION_USER_LOGINNAME.getString(resBundle::getString));

		passwordField = new PasswordField();
		passwordField.setCaption(CAPTION_USER_PASSWORD.getString(resBundle::getString));

		passwordFieldVerification = new PasswordField();
		passwordFieldVerification.setCaption(CAPTION_USER_PASSWORDCONFIRMATION.getString(resBundle::getString));

		prename = new TextField();
		prename.setCaption(CAPTION_USER_PRENAME.getString(resBundle::getString));

		surname = new TextField();
		surname.setCaption(CAPTION_USER_SURNAME.getString(resBundle::getString));

		button = new Button(Application_Properties.LABEL_USER_REGISTER.getString(resBundle::getString));
		button.addClickListener(e -> {
			if (inputIsValid()) {
				storeUserData();
				OverviewBusiness overViewBusiness = new OverviewBusiness(business.getSessionObj(), business.getStore(),
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

		boolean isValid = true;
		if (value.equals(repeat) == false) {
			UserError err = new UserError(MESSAGE_USER_PASSWORDMISSMATCH.getString(resBundle::getString));
			passwordField.setComponentError(err);
			passwordFieldVerification.setComponentError(err);
			isValid = false;
		}

		return isValid;
	}

	private void storeUserData() {
		User user = new User();
		user.setLoginName(loginName.getValue());
		user.setPrename(prename.getValue());
		user.setSurname(surname.getValue());
		user.setPassword(passwordField.getValue());
		user.setCreatedDate(LocalDateTime.now());
		user.setChangeDate(LocalDateTime.now());

		business.save(user);

		addComponent(
				new Label(MessageFormat.format(MESSAGE_USER_CREATE_SUCCESS.getString(resBundle::getString), user)));

	}

}
