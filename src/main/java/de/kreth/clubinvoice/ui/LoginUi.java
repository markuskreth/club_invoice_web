package de.kreth.clubinvoice.ui;

import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_LOGIN;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_LOGINNAME;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_USER_PASSWORD;
import static de.kreth.clubinvoice.Application_Properties.MESSAGE_USER_LOGINFAILURE;

import java.util.ResourceBundle;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.kreth.clubinvoice.Application_Properties;
import de.kreth.clubinvoice.business.OverviewBusiness;
import de.kreth.clubinvoice.business.UserRegister;
import de.kreth.clubinvoice.utils.ResourceBundleProvider;

public class LoginUi extends VerticalLayout implements InvoiceUi {

	private static final long serialVersionUID = 7795197656597564420L;

	private final transient UserRegister business;

	private final transient ResourceBundle resBundle;

	private TextField loginName;

	private PasswordField passwordField;

	private Button loginButton;

	private Label separator;

	private Button linkToRegister;

	private Label errorMsg;

	public LoginUi(UserRegister business) {
		this.business = business;
		this.resBundle = ResourceBundleProvider.getBundle();
	}

	@Override
	public void setContent(UI ui, VaadinRequest vaadinRequest) {

		loginName = new TextField();
		loginName.setId("user.loginname");
		loginName.setCaption(getString(CAPTION_USER_LOGINNAME));

		passwordField = new PasswordField();
		passwordField.setId("user.password");
		passwordField.setCaption(getString(CAPTION_USER_PASSWORD));

		loginButton = new Button(getString(CAPTION_USER_LOGIN));
		loginButton.setId("user.login");
		loginButton.setClickShortcut(KeyCode.ENTER);
		loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		loginButton.addClickListener(e -> {
			String login = loginName.getValue();
			String password = passwordField.getValue();
			if (business.login(login, password)) {

				OverviewBusiness overviewBusiness = new OverviewBusiness(business.getSessionObj(), business.getStore(),
						business.getCookieStore());
				OverviewUi overview = new OverviewUi(business.getStore(), overviewBusiness);
				overview.setContent(ui, vaadinRequest);
			}
			else {
				String failMessage = getString(MESSAGE_USER_LOGINFAILURE);
				UserError err = new UserError(failMessage);
				loginName.setComponentError(err);
				passwordField.setComponentError(err);
				errorMsg = new Label(failMessage);
				addComponent(errorMsg);
			}
		});
		separator = new Label("<hr />", ContentMode.HTML);
		linkToRegister = new Button("Register");
		linkToRegister.setId("user.register");
		linkToRegister.addClickListener(e -> {

			UserRegisterUi registerUi = new UserRegisterUi(business);
			registerUi.setContent(ui, vaadinRequest);
		});

		addComponents(loginName, passwordField, loginButton, separator, linkToRegister);

		ui.setContent(this);
	}

	private String getString(Application_Properties property) {
		return property.getString(resBundle::getString);
	}

}
