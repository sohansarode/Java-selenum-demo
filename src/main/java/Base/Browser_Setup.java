package base;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;

public class Browser_Setup extends ConfigReader {
	Thread popupHandlerThread = new Thread(() -> checkAndClosePopup());

	// @BeforeSuite
	protected void Browser_Launch() throws Exception {

		// Start recording the screen for test execution.
		My_Screen_Recorder.Start_Recording("Recording Started");

		// Read properties from the main property file.
		Read_Property_File();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		// Get Environment from CLI or fallback to config file
		String Environment = System.getProperty("environment", prop.getProperty("Environment"));
		String Browser;

		// Load respective environment properties
		if ("QA".equalsIgnoreCase(Environment)) {
			Read_QA_Property();
			Browser = System.getProperty("browser", prop1.getProperty("Browser"));
		} else if ("Live".equalsIgnoreCase(Environment)) {
			Read_Live_Property();
			Browser = System.getProperty("browser", prop2.getProperty("Browser"));
		} else {
			throw new RuntimeException("Invalid environment specified: " + Environment);
		}

		// Launch the specified browser
		switch (Browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver(options);
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			throw new RuntimeException("Invalid browser specified: " + Browser);
		}

		// Maximize the browser window.
		logger.info("::::Maximizing Window::::");
		driver.manage().window().maximize();

		// Navigate to the specified URL.
		logger.info("::::Entering URL::::");
		driver.navigate().to(prop1.getProperty("url"));
	}

	@BeforeSuite(alwaysRun = true)
	public void bridgeBrowserSetup() throws Exception {
		new Browser_Setup().Browser_Launch(); // ✅ Calls your actual browser setup
	}

	@AfterSuite
	protected void Close_Browser() throws Exception {
		driver.quit();
		My_Screen_Recorder.Stop_Recording();
	}
}