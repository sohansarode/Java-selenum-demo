package utils;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfigReader {

	public static WebDriver driver;
	protected static Properties prop, prop1,prop2;
	protected static Logger logger;

//----------------------------------------------------------------------------------------------------------//
	// This method is use to Read properties from property file
	protected void Read_Property_File() {
		logger = LogManager.getLogger(ConfigReader.class);

		try {
			String projectPath = System.getProperty("user.dir");
			// Construct relative path to config.properties
			String path = projectPath + "/src/test/resources/config.properties";

			prop = new Properties();
			prop.load(new FileInputStream(path));

		} catch (Exception e) {
			System.out.println("Not able to read property file");
			e.printStackTrace();
		}
	}

//----------------------------------------------------------------------------------------------------------//
	// This method is use to Read properties from QA Environment property file
	protected void Read_QA_Property() {
		logger = LogManager.getLogger(ConfigReader.class);

		try {
			String projectPath = System.getProperty("user.dir");
			String path = projectPath + "/src/main/resources/environments/QAEnvironment.properties";

			prop1 = new Properties();
			prop1.load(new FileInputStream(path));

		} catch (Exception e) {
			System.out.println("Not able to read QA property file");
			e.printStackTrace();
		}
	}

//----------------------------------------------------------------------------------------------------------//
	// This method is use to Read properties from Dev Environment property file
	protected void Read_Live_Property() {
		logger = LogManager.getLogger(ConfigReader.class);

		
		try {
			
			String projectPath = System.getProperty("user.dir");
			String path = projectPath + "/src/main/resources/environments/LiveEnvironment.properties";
			prop2 = new Properties();
			prop2.load(new FileInputStream(path));

		} catch (Exception E) {
			System.out.println("Not able to read Dev property file");
		}

	}

//----------------------------------------------------------------------------------------------------------//

	protected static void checkAndClosePopup() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				// Using findElements which returns a list
				List<WebElement> popups = driver.findElements(By.xpath("(//*[@data-cta-id=\"150887\"])[2]"));

				if (!popups.isEmpty() && popups.get(0).isDisplayed()) {
					// Use JavaScript to "click" the popup without triggering browser events
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", popups.get(0));
					// Sleep a bit to ensure the popup is fully processed before checking again.
					TimeUnit.SECONDS.sleep(5);
				}
			} catch (Exception e) {
				System.out.println("Unexpected error occurred: " + e.getMessage());
			}

			try {
				TimeUnit.MILLISECONDS.sleep(2000);
			} catch (InterruptedException e) {
				// If the background thread is interrupted (i.e., the main test is done), exit
				// the loop
				break;
			}
		}
	}

//----------------------------------------------------------------------------------------------------------//

}
