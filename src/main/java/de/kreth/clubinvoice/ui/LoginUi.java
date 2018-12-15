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
import com.vaadin.ui.themes.ValoTheme;

import de.kreth.clubinvoice.business.OverviewBusiness;
import de.kreth.clubinvoice.business.UserRegister;
import de.kreth.clubinvoice.utils.ResourceBundleProvider;

public class LoginUi extends VerticalLayout implements InvoiceUi {

	private static final String CAPTION_USER_LOGIN = "caption.user.login";
	private static final String CAPTION_USER_PASSWORD = "caption.user.password";
	private static final String CAPTION_USER_LOGINNAME2 = "caption.user.loginname";
	private static final long serialVersionUID = 7795197656597564420L;
	private transient final UserRegister business;
	private transient final ResourceBundle resBundle;

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
		loginName.setCaption(resBundle.getString(CAPTION_USER_LOGINNAME2));

		passwordField = new PasswordField();
		passwordField.setId("user.password");
		passwordField.setCaption(resBundle.getString(CAPTION_USER_PASSWORD));

		loginButton = new Button(resBundle.getString(CAPTION_USER_LOGIN));
		loginButton.setId("user.login");
		loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		loginButton.addClickListener(e -> {
			String login = loginName.getValue();
			String password = passwordField.getValue();
			if (business.login(login, password)) {

				OverviewBusiness overviewBusiness = new OverviewBusiness(business.getSessionObj(), business.getStore(),
						business.getCookieStore());
				OverviewUi overview = new OverviewUi(business.getStore(), overviewBusiness);
				overview.setContent(ui, vaadinRequest);
			} else {
				String failMessage = resBundle.getString("message.user.loginfailure");
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

}
