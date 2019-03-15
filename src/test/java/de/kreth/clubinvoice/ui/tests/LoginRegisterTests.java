package de.kreth.clubinvoice.ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.osjava.sj.memory.MemoryContextFactory;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import com.mysql.cj.jdbc.MysqlDataSource;

import de.kreth.clubinvoice.InvoiceMainUI;
import de.kreth.clubinvoice.utils.PropertiesResourceBundle;

@Disabled
class LoginRegisterTests {

	private static ChromeOptions options;

	private static Swarm swarm;
	private static final int PORT = 8080;

	@BeforeAll
	public static void setDriverPaths() throws Exception {

		if (System.getProperty("webdriver.chrome.driver") == null) {
			System.setProperty("webdriver.chrome.driver", System.getenv("webdriver.chrome.driver"));
		}

		options = new ChromeOptions();
		options.setHeadless(false);

		createJndiDatasource();
		PropertiesResourceBundle.install();
		startUndertow();
	}

	public static void createJndiDatasource() throws NamingException, SQLException {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.osjava.sj.memory.MemoryContextFactory");
		System.setProperty("org.osjava.sj.jndi.shared", "true");
		InitialContext ic = new InitialContext();

		ic.createSubcontext("java:/comp/env/jdbc");

		// Construct DataSource
		MysqlDataSource myDs = new MysqlDataSource();
		myDs.setURL(
				"jdbc:mysql://localhost:3306/clubinvoice?useUnicode=yes&characterEncoding=utf8&serverTimezone=Europe/Berlin&useSSL=false");
//		myDs.setConnectionAttributes("useUnicode=yes&characterEncoding=utf8&serverTimezone=Europe/Berlin");
		myDs.setUser("root");
		myDs.setPassword("07!73");

		// Put datasource in JNDI context
		ic.bind("java:comp/env/jdbc/clubinvoice", myDs);
	}

	public static void startUndertow() throws Exception {
		swarm = new Swarm();
		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
		deployment.addClass(InvoiceMainUI.InvoiceUIServlet.class);
		swarm.start().deploy(deployment);
	}

	@AfterAll
	static void stopUndertow() throws Exception {
//		swarm.stop();
	}

	private WebDriver driver;

	@BeforeEach
	void setUp() throws Exception {
		driver = new ChromeDriver(options);
	}

	@AfterEach
	void shutdown() {
		if (driver != null) {
			driver.close();
		}
	}

	@Test
	public void testMysqlConnection() throws NamingException, SQLException {
		MemoryContextFactory factory = new MemoryContextFactory();
		Context ctx = factory.getInitialContext(null);
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/clubinvoice");
		assertNotNull(ds);
		try (Connection conn = ds.getConnection()) {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM clubinvoice.login_user");
			assertTrue(rs.next());
		}
	}

	@Test
	public void testLogin() {
		driver.get("http://localhost:" + PORT);
		WebElement loginname = driver.findElement(By.id("user.loginname"));
		loginname.sendKeys("test");
		driver.findElement(By.id("user.password")).sendKeys("test");
		driver.findElement(By.id("user.login")).click();
		List<WebElement> buttons = driver.findElements(By.className("v-button"));
		assertEquals(5, buttons.size());
	}

	@Test
	public void testRegister() {
		driver.get("http://localhost:" + PORT);
		WebElement btnRegister = driver.findElement(By.id("user.register"));
		btnRegister.click();
		List<WebElement> buttons = driver.findElements(By.className("v-button"));
		assertEquals(5, buttons.size());
	}

}
