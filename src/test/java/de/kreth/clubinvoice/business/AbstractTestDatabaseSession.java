package de.kreth.clubinvoice.business;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import de.kreth.clubinvoice.InvoiceMainUI;
import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.testutils.MockPropertyStore;

class AbstractTestDatabaseSession {

	protected static SessionFactory sessionFactory;
	protected Session session = null;
	protected MockPropertyStore propStore;

	@BeforeEach
	void createMemoryDatabase() throws Exception {
		if (sessionFactory != null) {
			return;
		}

		// setup the session factory
		Configuration configuration = new Configuration();
		for (Class<?> entity : InvoiceMainUI.findEntityClasses()) {
			configuration.addAnnotatedClass(entity);
		}

		configuration.setProperty("hibernate.dialect",
				"org.hibernate.dialect.H2Dialect");
		configuration.setProperty("hibernate.connection.driver_class",
				"org.h2.Driver");
		configuration.setProperty("hibernate.connection.url",
				"jdbc:h2:mem:test");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		sessionFactory = configuration.buildSessionFactory();

		session = sessionFactory.openSession();

		propStore = new MockPropertyStore();
	}

	protected static SessionFactory createFileDatabaseSession(String filePath) {

		Configuration configuration = new Configuration();
		for (Class<?> entity : InvoiceMainUI.findEntityClasses()) {
			configuration.addAnnotatedClass(entity);
		}

		configuration.setProperty("hibernate.dialect",
				"org.hibernate.dialect.H2Dialect");
		configuration.setProperty("hibernate.connection.driver_class",
				"org.h2.Driver");
		configuration.setProperty("hibernate.connection.url",
				"jdbc:h2:" + filePath);
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		return configuration.buildSessionFactory();

	}

	@AfterEach
	public void destroyDatabase() {
		sessionFactory = null;
	}

	/**
	 * Article with PricePerHour of 8.5;
	 * 
	 * @return
	 */
	public Article createTestArticle() {

		Article article = new Article();
		article.setTitle("TestArticle");
		article.setPricePerHour(BigDecimal.valueOf(8.5));
		article.setDescription("TestArticle");
		article.setDescription("");
		article.setChangeDate(LocalDateTime.MIN);
		article.setCreatedDate(LocalDateTime.MIN);
		return article;
	}
}
