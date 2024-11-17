package Base;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Browser_Setup extends Read_Property {
	Thread popupHandlerThread = new Thread(() -> checkAndClosePopup());

//----------------------------------------------------------------------------------------------------------//
	// This method is executed before the test suite starts.
	@BeforeSuite
	protected void Browser_Launch() throws Exception {

		// Start recording the screen for test execution.
		My_Screen_Recorder.Start_Recording("Recording Started");

		// Read properties from the main property file.
		Read_Property_File();
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
		// Get the environment value from the properties file.
		String Environment = prop.getProperty("Environment");
		String Browser;

		// Depending on the environment, read properties from QA or Live property file.
		if ("QA".equalsIgnoreCase(Environment)) {
			Read_QA_Property();
			Browser = prop1.getProperty("Browser");
		} else if ("Live".equalsIgnoreCase(Environment)) {
			Read_Live_Property();
			Browser = prop2.getProperty("Browser");
		} else {
			// Throw an exception if an invalid environment is specified.
			throw new RuntimeException("Invalid environment specified in properties file");
		}

		// Launch the specified browser based on the property.
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
			// Throw an exception if an invalid browser is specified.
			throw new RuntimeException("Invalid browser specified in properties file");
		}

		// Maximize the browser window.
		logger.info("::::Maximizing Window::::");
		driver.manage().window().maximize();

		// Navigate to the specified URL.
		logger.info("::::Entering URL::::");
		driver.navigate().to(prop1.getProperty("url"));
	}
//----------------------------------------------------------------------------------------------------------//

	// This method is use to quit browser
	@AfterSuite
	protected void Close_Browser() throws Exception {

		// driver.quit();
		// popupHandlerThread.interrupt();
		My_Screen_Recorder.Stop_Recording();
	}

//----------------------------------------------------------------------------------------------------------//
}
