package de.kreth.clubinvoice.ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import de.kreth.clubinvoice.InvoiceMainUI;

public class GoogleCall {

	private static ChromeOptions options;

	@BeforeClass
	public static void setDriverPaths() {
//
		System.setProperty("webdriver.chrome.driver",
				"E:\\Markus\\eclipse_workspace\\seleniumdrivers\\chromedriver.exe");

		options = new ChromeOptions();
//		options.setHeadless(true);

	}

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new ChromeDriver(options);
	}

	@After
	public void shutdown() {
		if (driver != null) {
			driver.close();
		}
	}

//	@Test
	public void testGuru99Demo() {

		driver.get("http://demo.guru99.com/test/guru99home/");
		String title = driver.getTitle();
		Assert.assertTrue(title.contains("Demo Guru99 Page"));
	}

//	@Test
	public void testLocalWebservice() throws Exception {

		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/");
		webAppContext.addServlet(InvoiceMainUI.InvoiceUIServlet.class, "/ClubInvoice");
		assertEquals("E:\\Markus\\eclipse_workspace\\club_invoice_web\\src\\main\\webapp",
				new File("src/main/webapp").getAbsolutePath());
		webAppContext.setResourceBase("src/main/webapp");
		webAppContext.setClassLoader(getClass().getClassLoader());

		Server server = new Server(9099);
		server.setStopAtShutdown(true);
		server.addHandler(webAppContext);
		try {

			server.start();

			String uri = String.format("http://localhost:%d/ClubInvoice", 9099);
			driver.get(uri);
			String content = driver.getPageSource();

			assertEquals("Hallo", content);
		} finally {
			server.stop();
		}
	}

//	@Test
	public void testInvoice() {
		driver.get("http://localhost:8080/kreth-clubinvoice/");
		WebElement loginname = driver.findElement(By.id("user.loginname"));
		loginname.sendKeys("test");
		driver.findElement(By.id("user.password")).sendKeys("test");
		driver.findElement(By.id("user.login")).click();
		List<WebElement> buttons = driver.findElements(By.className("v-button"));
		assertEquals(5, buttons.size());
//		driver.findElement(By.linkText("Benutzer Details")).click();
		Set<Cookie> cookies = driver.manage().getCookies();

		assertFalse(cookies.isEmpty());
	}
}
