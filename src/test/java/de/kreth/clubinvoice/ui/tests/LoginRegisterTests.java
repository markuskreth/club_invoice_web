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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.osjava.sj.memory.MemoryContextFactory;

import com.mysql.cj.jdbc.MysqlDataSource;

import de.kreth.clubinvoice.utils.PropertiesResourceBundle;

class LoginRegisterTests {

	private static ChromeOptions options;

	private static final int PORT = 8080;

	@BeforeAll
	public static void setDriverPaths() throws Exception {

		if (System.getProperty("webdriver.chrome.driver") == null) {
			System.setProperty("webdriver.chrome.driver", System.getenv("webdriver.chrome.driver"));
		}

		options = new ChromeOptions();
		options.setHeadless(false);
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");

		createJndiDatasource();
		PropertiesResourceBundle.install();
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
