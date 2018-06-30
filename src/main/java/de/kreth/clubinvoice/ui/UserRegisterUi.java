package de.kreth.clubinvoice.ui;

import java.time.LocalDateTime;

import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kreth.clubinvoice.business.Business;
import de.kreth.clubinvoice.business.OverviewBusiness;
import de.kreth.clubinvoice.data.User;

public class UserRegisterUi extends VerticalLayout implements InvoiceUi {

	private static final long serialVersionUID = -7409842921091527159L;

	private TextField loginName;
	private PasswordField passwordField;
	private TextField prename;
	private TextField surname;
	private Button button;
	private final Business<User> business;

	private PasswordField passwordFieldVerification;

	public UserRegisterUi(Business<User> business) {
		this.business = business;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.kreth.clubinvoice.ui.InvoiceUi#setContent(com.vaadin.ui.UI)
	 */
	@Override
	public void setContent(final UI ui, final VaadinRequest vaadinRequest) {

		loginName = new TextField();
		loginName.setCaption("Ihr Loginname:");

		passwordField = new PasswordField();
		passwordField.setCaption("Ihr Password:");

		passwordFieldVerification = new PasswordField();
		passwordFieldVerification.setCaption("Wiederholung Password:");

		prename = new TextField();
		prename.setCaption("Vorname:");

		surname = new TextField();
		surname.setCaption("Nachname:");

		button = new Button("Register");
		button.addClickListener(e -> {
			if (inputIsValid()) {
				storeUserData();
				OverviewBusiness overViewBusiness = new OverviewBusiness(
						business.getSessionObj(), business.getStore());
				OverviewUi overview = new OverviewUi(business.getStore(),
						overViewBusiness);
				overview.setContent(ui, vaadinRequest);
			}
		});

		addComponents(loginName, prename, surname, passwordField,
				passwordFieldVerification, button);

		ui.setContent(this);
	}

	private boolean inputIsValid() {
		String value = passwordField.getValue();
		String repeat = passwordFieldVerification.getValue();

		boolean isValid = true;
		if (value.equals(repeat) == false) {
			UserError err = new UserError("Passwords don't match.");
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

		addComponent(new Label("Thanks " + user + " created!"));

	}

}
