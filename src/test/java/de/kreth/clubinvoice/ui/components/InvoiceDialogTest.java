package de.kreth.clubinvoice.ui.components;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.parallel.ParallelTest;

@Disabled
class InvoiceDialogTest extends ParallelTest {

	private static final String URL = "http://localhost";
	private static final String PORT = "8080";

	@BeforeEach
	void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "_data/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "_data/geckodriver.exe");
		RemoteWebDriver webDriver = new FirefoxDriver();
		setDesiredCapabilities(DesiredCapabilities.firefox());
		setDriver(webDriver);

		getDriver().get(URL + ":" + PORT);
	}

	@AfterEach
	public void tearDown() throws Exception {
		getDriver().quit();
	}

	@Test
	void testButtonClick() {

		ButtonElement button = $(ButtonElement.class).first();
		button.click();
	}

}
