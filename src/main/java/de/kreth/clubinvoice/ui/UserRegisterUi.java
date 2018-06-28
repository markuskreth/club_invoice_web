package de.kreth.clubinvoice.ui;

import java.time.LocalDateTime;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kreth.clubinvoice.business.Business;
import de.kreth.clubinvoice.data.User;

public class UserRegisterUi implements InvoiceUi {

	private TextField loginName;
	private PasswordField passwordField;
	private TextField prename;
	private TextField surname;
	private Button button;
	private VerticalLayout layout;
	private final Business<User> business;

	public UserRegisterUi(Business<User> business) {
		this.business = business;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.kreth.clubinvoice.ui.InvoiceUi#setContent(com.vaadin.ui.UI)
	 */
	@Override
	public void setContent(UI ui) {

		layout = new VerticalLayout();

		loginName = new TextField();
		loginName.setCaption("Ihr Loginname:");

		passwordField = new PasswordField();
		passwordField.setCaption("Ihr Password:");

		prename = new TextField();
		prename.setCaption("Vorname:");

		surname = new TextField();
		prename.setCaption("Nachname:");

		button = new Button("Login");
		button.addClickListener(e -> {
			storeUserData();
		});

		layout.addComponents(loginName, prename, surname, passwordField,
				button);

		ui.setContent(layout);
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

		layout.addComponent(new Label("Thanks " + user + " created!"));
	}

}
