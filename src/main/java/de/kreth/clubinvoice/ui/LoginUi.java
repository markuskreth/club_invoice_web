package de.kreth.clubinvoice.ui;

import java.util.ResourceBundle;

import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kreth.clubinvoice.business.OverviewBusiness;
import de.kreth.clubinvoice.business.UserRegister;

public class LoginUi extends VerticalLayout implements InvoiceUi {

	private static final long serialVersionUID = 7795197656597564420L;
	private final UserRegister business;

	private TextField loginName;
	private PasswordField passwordField;
	private Button loginButton;
	private Label separator;
	private Button linkToRegister;
	private Label errorMsg;
	private ResourceBundle resBundle;

	public LoginUi(UserRegister business) {
		this.business = business;
		this.resBundle = ResourceBundle.getBundle("/application");
	}

	@Override
	public void setContent(UI ui, VaadinRequest vaadinRequest) {

		loginName = new TextField();
		loginName.setCaption(resBundle.getString("caption.user.loginname"));

		passwordField = new PasswordField();
		passwordField.setCaption(resBundle.getString("caption.user.password"));

		loginButton = new Button(resBundle.getString("caption.user.login"));
		loginButton.addClickListener(e -> {
			String login = loginName.getValue();
			String password = passwordField.getValue();
			if (business.login(login, password)) {

				OverviewBusiness overviewBusiness = new OverviewBusiness(
						business.getSessionObj(), business.getStore());
				OverviewUi overview = new OverviewUi(business.getStore(),
						overviewBusiness);
				overview.setContent(ui, vaadinRequest);
			} else {
				String failMessage = resBundle
						.getString("message.user.loginfailure");
				UserError err = new UserError(failMessage);
				loginName.setComponentError(err);
				passwordField.setComponentError(err);
				errorMsg = new Label(failMessage);
				addComponent(errorMsg);
			}
		});
		separator = new Label("<hr />", ContentMode.HTML);
		linkToRegister = new Button("Register");
		linkToRegister.addClickListener(e -> {

			UserRegisterUi registerUi = new UserRegisterUi(business);
			registerUi.setContent(ui, vaadinRequest);
		});

		addComponents(loginName, passwordField, loginButton, separator,
				linkToRegister);

		ui.setContent(this);
	}

}
