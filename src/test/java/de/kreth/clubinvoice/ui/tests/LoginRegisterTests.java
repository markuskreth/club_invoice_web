package de.kreth.clubinvoice.ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.store.fs.FileUtils;
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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.osjava.sj.memory.MemoryContextFactory;

import com.mysql.cj.jdbc.MysqlDataSource;

import de.kreth.clubinvoice.utils.PropertiesResourceBundle;

@Disabled("Chrome driver not valid")
class LoginRegisterTests {

	private static ChromeOptions options;

	private static final int PORT = PortFinder.findFreePort();

	private static final List<String> OPTIONS = new ArrayList<>(
			Arrays.asList("--no-sandbox", "--disable-dev-shm-usage"));

	@BeforeAll
	public static void setDriverPaths() throws Exception {

		if (System.getProperty("webdriver.chrome.driver") == null) {
			System.setProperty("webdriver.chrome.driver", System.getenv("webdriver.chrome.driver"));
		}
		if (System.getProperty("webdriver.chrome.driver") == null) {
			throw new IllegalStateException("Property \"webdriver.chrome.driver\" not set, unable to execute "
					+ LoginRegisterTests.class.getName());
		}

		options = new ChromeOptions();

		if (GraphicsEnvironment.isHeadless()) {
			options.setHeadless(false);
			System.out.println("Using headless test mode");
		}
		options.addArguments(OPTIONS);

		createJndiDatasource();

		PropertiesResourceBundle.install();
	}

	public static DataSource createJndiDatasource() throws NamingException, SQLException, IOException {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.osjava.sj.memory.MemoryContextFactory");
		System.setProperty("org.osjava.sj.jndi.shared", "true");
		InitialContext ic = new InitialContext();

		ic.createSubcontext("java:/comp/env/jdbc");

		DataSource myDs = createMysqlDatasource();
		myDs = createH2Datasource();

		// Put datasource in JNDI context
		ic.bind("java:comp/env/jdbc/clubinvoice", myDs);
		return myDs;
	}

	private static DataSource createH2Datasource() throws SQLException, IOException {

		File databaseDir = new File("testdatabase");
		FileUtils.deleteRecursive(databaseDir.getAbsolutePath(), true);
		File database = new File(databaseDir, "database");

		JdbcDataSource ds = new JdbcDataSource();
		String url = "jdbc:h2:" + database.getAbsolutePath() + ";MODE=MYSQL";
		ds.setURL(url);
		ds.setUser("sa");

		List<String> tablenames = new ArrayList<String>();

		try (Connection conn = ds.getConnection();
				Statement stm = conn.createStatement()) {
			createSchemaAndInsertTestUser(stm);
			if (conn.getAutoCommit() == false) {
				conn.commit();
			}
			try (ResultSet rs = conn.getMetaData().getTables(null, null, null, new String[] { "TABLE" })) {
				while (rs.next()) {
					tablenames.add(rs.getString("TABLE_NAME"));
				}
			}
		}

		System.out.println(tablenames);

		return ds;
	}

	private static void createSchemaAndInsertTestUser(Statement stm)
			throws SQLException, IOException, FileNotFoundException {
		List<File> listFiles = getSqlFiles();

		for (File sqlFile : listFiles) {
			try (BufferedReader in = new BufferedReader(new FileReader(sqlFile))) {
				String sqlContent = in.lines().collect(Collectors.joining("\n"));
				String[] sqls = sqlContent.trim().split(";");

				for (String sql : sqls) {
					stm.execute(sql.trim());
				}
			}
		}

		String insertTestUser = "INSERT INTO login_user (login, prename, surname, password) VALUES (\"test\", \"Test\", \"Test\", \"Test\")";
		insertTestUser = "INSERT INTO login_user (login, prename, surname, password) VALUES ('test', 'Test', 'Test', 'Test')";
		stm.execute(insertTestUser);
	}

	private static List<File> getSqlFiles() {
		File sqlSources = new File(LoginRegisterTests.class.getResource("/sql_ddl").getFile());
		List<File> listFiles = Arrays.asList(sqlSources.listFiles(f -> f.getName().endsWith(".sql")));
		listFiles.sort((f1, f2) -> f1.getName().compareTo(f2.getName()));
		return listFiles;
	}

	private static MysqlDataSource createMysqlDatasource() {
		MysqlDataSource myDs = new MysqlDataSource();
		myDs.setURL(
				"jdbc:mysql://localhost:3306/clubinvoice?useUnicode=yes&characterEncoding=utf8&serverTimezone=Europe/Berlin&useSSL=false");
		myDs.setUser("root");
		myDs.setPassword("07!73");
		return myDs;
	}

	private WebDriver driver;

	@BeforeEach
	void setUp() throws Exception {
		Main.main();
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
	}

	@AfterEach
	void shutdown() {
		if (driver != null) {
			driver.manage().deleteAllCookies();
			driver.close();
		}
		Main.shutdown();
	}

	@Test
	public void testMysqlConnection() throws NamingException, SQLException {
		MemoryContextFactory factory = new MemoryContextFactory();
		Context ctx = factory.getInitialContext(null);
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/clubinvoice");

		assertNotNull(ds);

		List<String> tablenames = new ArrayList<String>();

		try (Connection conn = ds.getConnection()) {

			try (ResultSet rs = conn.getMetaData().getTables(null, null, null, new String[] { "TABLE" })) {
				while (rs.next()) {
					tablenames.add(rs.getString("TABLE_NAME"));
				}
			}
		}

		assertFalse(tablenames.isEmpty());

		try (Connection conn = ds.getConnection();
				Statement stm = conn.createStatement()) {
			try (ResultSet rs = stm.executeQuery("SELECT * FROM login_user")) {
				assertTrue(rs.next());
			}
		}
	}

	@Test
	public void whatIsMyBrowser() {
		driver.get("http://whatismybrowser.com/");
		WebElement body = driver.findElement(By.tagName("body"));
		assertNotNull(body);
		WebElement javascriptDetection = driver.findElement(By.id("javascript-detection"));
		WebElement cookiesDetection = driver.findElement(By.id("cookies-detection"));
		assertEquals("Yes", javascriptDetection.findElement(By.xpath(".//*")).getText());
		assertEquals("Yes", cookiesDetection.findElement(By.xpath(".//*")).getText());
	}

	@Test
	public void testLogin() {

		loadLocalhostAndWaitForVaadinUI();
		WebElement loginname = driver.findElement(By.id("user.loginname"));
		loginname.sendKeys("test");
		driver.findElement(By.id("user.password")).sendKeys("test");
		driver.findElement(By.id("user.login")).click();
		List<WebElement> buttons = driver.findElements(By.className("v-button"));
		assertEquals(7, buttons.size());
	}

	@Test
	public void testRegister() {

		loadLocalhostAndWaitForVaadinUI();
		WebElement btnRegister = driver.findElement(By.id("user.register"));
		btnRegister.click();
		List<WebElement> buttons = driver.findElements(By.className("v-button"));
		assertEquals(1, buttons.size());
	}

	public void loadLocalhostAndWaitForVaadinUI() {

		driver.get("http://localhost:" + PORT);

		ExpectedCondition<Boolean> loginFound = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return driver.findElements(By.id("user.loginname")).size() > 0;
			}
		};

		WebDriverWait wait = new WebDriverWait(driver, 30);
		assertTrue(wait.until(loginFound));

	}
}
