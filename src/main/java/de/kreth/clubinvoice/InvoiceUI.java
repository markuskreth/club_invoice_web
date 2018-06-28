package de.kreth.clubinvoice;

import javax.servlet.annotation.WebServlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import de.kreth.clubinvoice.business.Business;
import de.kreth.clubinvoice.business.UserRegister;
import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.ui.InvoiceUi;
import de.kreth.clubinvoice.ui.UserRegisterUi;

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

	private Session sessionObj;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		sessionObj = buildSessionFactory().openSession();

		InvoiceUi content = createView(vaadinRequest);
		content.setContent(this);

	}

	private InvoiceUi createView(VaadinRequest vaadinRequest) {
		Business<User> business = new UserRegister(sessionObj);
		InvoiceUi content = new UserRegisterUi(business);
		return content;
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
