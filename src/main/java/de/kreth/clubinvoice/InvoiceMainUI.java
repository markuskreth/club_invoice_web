package de.kreth.clubinvoice;

import java.util.Set;

import javax.persistence.Entity;
import javax.servlet.annotation.WebServlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import de.kreth.clubinvoice.business.CookieStore;
import de.kreth.clubinvoice.business.OverviewBusiness;
import de.kreth.clubinvoice.business.PropertyStore;
import de.kreth.clubinvoice.business.UserRegister;
import de.kreth.clubinvoice.ui.InvoiceUi;
import de.kreth.clubinvoice.ui.LoginUi;
import de.kreth.clubinvoice.ui.OverviewUi;

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
public class InvoiceMainUI extends UI {

	private static final long serialVersionUID = -507823166251133871L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(InvoiceMainUI.class);

	private Session sessionObj;

	private PropertyStore store;

	private CookieStore cookies;

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		LOGGER.trace("Starting Vaadin webapp");
		try {
			sessionObj = buildSessionFactory().openSession();
		} catch (RuntimeException e) {
			LOGGER.error("Error initializing database.", e);
			throw e;
		}

		this.store = new PropertyStore(vaadinRequest.getWrappedSession());
		cookies = new CookieStore();

		if (vaadinRequest.getPathInfo() != null
				&& vaadinRequest.getPathInfo().contains("logout")) {

			LOGGER.debug("Logging out.");
			store.removeAttribute(PropertyStore.LOGGED_IN_USER);
			CookieStore cs = new CookieStore();
			cs.remove(CookieStore.PASSWORD);
			cs.remove(CookieStore.USER_NAME);
		}

		InvoiceUi content = createView(vaadinRequest);
		content.setContent(this, vaadinRequest);
		LOGGER.trace("Startet with UI Class : {}", content.getClass());

	}

	private InvoiceUi createView(VaadinRequest vaadinRequest) {

		UserRegister business = new UserRegister(sessionObj, store, cookies);

		InvoiceUi content;
		if (business.isLoggedIn() == false) {
			content = new LoginUi(business);
		} else {
			OverviewBusiness overViewBusiness = new OverviewBusiness(sessionObj,
					store, cookies);
			content = new OverviewUi(store, overViewBusiness);
		}

		return content;
	}

	static SessionFactory sessionFactoryObj;

	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration
		// File

		Set<Class<?>> entities = findEntityClasses();

		LOGGER.debug("Found these Entities: {}", entities);

		Configuration configObj = new Configuration()
				.configure("hibernate.cfg.xml");

		for (Class<?> entity : entities) {
			configObj.addAnnotatedClass(entity);
		}

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
				.applySettings(configObj.getProperties()).build();

		LOGGER.trace("Loaded Hibernate config file");

		// Creating Hibernate SessionFactory Instance
		sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
		return sessionFactoryObj;
	}

	public static Set<Class<?>> findEntityClasses() {
		Reflections refl = new Reflections("de.kreth.clubinvoice");

		Set<Class<?>> entities = refl.getTypesAnnotatedWith(Entity.class);
		return entities;
	}

	@WebServlet(urlPatterns = "/*", name = "InvoiceUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = InvoiceMainUI.class, productionMode = false)
	public static class InvoiceUIServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3191599415794244147L;
	}
}
