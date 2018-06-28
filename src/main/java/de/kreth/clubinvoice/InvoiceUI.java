package de.kreth.clubinvoice;

import java.util.Date;

import javax.servlet.annotation.WebServlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kreth.clubinvoice.data.User;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("invoiceTheme")
public class InvoiceUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -507823166251133871L;
	private TextField loginName;
	private PasswordField passwordField;
	private TextField prename;
	private TextField surname;
	private Button button;
	private Session sessionObj;
	private VerticalLayout layout;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		layout = new VerticalLayout();

		sessionObj = buildSessionFactory().openSession();

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

		VaadinSession session = getSession();
		VaadinService service = session.getService();
		System.out.println(session);
		System.out.println(service);

		setContent(layout);
	}

	private void storeUserData() {
		sessionObj.beginTransaction();

		User user = new User();
		user.setLoginName(loginName.getValue());
		user.setPrename(prename.getValue());
		user.setSurname(surname.getValue());
		user.setPassword(passwordField.getValue());
		user.setCreatedDate(new Date());
		user.setChangeDate(new Date());

		sessionObj.save(user);
		sessionObj.getTransaction().commit();
		layout.addComponent(new Label("Thanks " + user + " created!"));
	}

	static SessionFactory sessionFactoryObj;

	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration
		// File
		Configuration configObj = new Configuration()
				.addAnnotatedClass(User.class).configure("hibernate.cfg.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
				.applySettings(configObj.getProperties()).build();

		// Creating Hibernate SessionFactory Instance
		sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
		return sessionFactoryObj;
	}

	@WebServlet(urlPatterns = "/*", name = "InvoiceUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = InvoiceUI.class, productionMode = false)
	public static class InvoiceUIServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3191599415794244147L;
	}
}
