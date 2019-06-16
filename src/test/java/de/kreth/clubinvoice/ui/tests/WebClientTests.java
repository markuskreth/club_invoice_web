package de.kreth.clubinvoice.ui.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebClientTests {

	private static ChromeOptions options;

	private WebDriver driver;

	@BeforeAll
	public static void setDriverPaths() throws NamingException, SQLException {

		if (System.getProperty("webdriver.chrome.driver") == null && System.getenv("webdriver.chrome.driver") != null) {
			System.setProperty("webdriver.chrome.driver", System.getenv("webdriver.chrome.driver"));
		}

		options = new ChromeOptions();
		options.setHeadless(true);

	}

	@BeforeEach
	public void setUp() throws Exception {
		driver = new ChromeDriver(options);
	}

	@AfterEach
	public void shutdown() {
		if (driver != null) {
			driver.close();
		}
	}

	@Test
	public void testGuru99Demo() {

		driver.get("http://demo.guru99.com/test/guru99home/");
		String title = driver.getTitle();
		assertTrue(title.contains("Demo Guru99 Page"));
	}

}
