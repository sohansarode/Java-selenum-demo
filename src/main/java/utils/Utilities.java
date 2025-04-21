package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

public class Utilities extends CommonUtils {

	protected static String Filepath;

//----------------------------------------------------------------------------------------------------------//

	// This method is use to Scroll till the element on page

	protected void Scroll_Into_View(WebElement Element) {
		// Create a JavascriptExecutor instance
		JavascriptExecutor ScriptExecutor = (JavascriptExecutor) driver;

		// Execute JavaScript to scroll the specified element into view
		ScriptExecutor.executeScript("arguments[0].scrollIntoView();", Element);

		// Log information about scrolling
		logger.info("Scrolled to element: " + Element.getText());
	}
//----------------------------------------------------------------------------------------------------------//

	// This method is use to scroll by pixel[X-Axis,Y-Axis]

	protected void Scroll(int Horizontal, int Vertical) {
		// Create a JavascriptExecutor instance
		JavascriptExecutor ScriptExecutor = (JavascriptExecutor) driver;

		// Execute JavaScript to scroll the window by the specified horizontal and
		// vertical offsets
		ScriptExecutor.executeScript("window.scrollBy(" + Horizontal + "," + Vertical + ")");

		// Log information about the scroll operation
		logger.info("Scrolled horizontally & vertically by " + Horizontal + " pixels and " + Vertical + " pixels");
	}

//----------------------------------------------------------------------------------------------------------//
	// This method is use to scroll directly at bottom of page

	protected void Scroll_At_Bottom() {
		// Create a JavascriptExecutor instance
		JavascriptExecutor ScriptExecutor = (JavascriptExecutor) driver;

		// Execute JavaScript to scroll the window to the bottom of the document
		ScriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		// Log information about scrolling to the bottom
		logger.info("Scrolled to the bottom of the page");
	}
//----------------------------------------------------------------------------------------------------------//

	// This method is use to scroll directly at top of page

	protected void Scroll_At_Top() {
		// Create a JavascriptExecutor instance
		JavascriptExecutor ScriptExecutor = (JavascriptExecutor) driver;

		// Execute JavaScript to scroll the window to the top of the document
		ScriptExecutor.executeScript("window.scrollTo(0, 0);");

		// Log information about scrolling to the top
		logger.info("Scrolled to the top of the page");
	}
//----------------------------------------------------------------------------------------------------------//
	// This method is use to find the hiddenelement which is not visible on
	// screen ,so used javascript executor for it

	protected WebElement Find_Hidden_Element(String Element, String Loacatortype) {
		// Create a JavascriptExecutor instance
		JavascriptExecutor ScriptExecutor = (JavascriptExecutor) driver;

		// Initialize script variable
		String Script = "";

		// Build script based on locator type
		switch (Loacatortype.toLowerCase()) {
		case "id":
			Script = "return document.getElementById(arguments[0]);";
			break;
		case "class":
			Script = "return document.getElementsByClassName(arguments[0])[0];";
			break;
		case "tag":
			Script = "return document.getElementsByTagName(arguments[0])[0];";
			break;
		default:
			throw new IllegalArgumentException("Unsupported locator type");
		}

		// Execute JavaScript to find the hidden element
		WebElement Ele = (WebElement) ScriptExecutor.executeScript(Script, Element);

		return Ele;
	}

//----------------------------------------------------------------------------------------------------------//

	// This method is use to take screenshot of failed test

	protected static String Capture_Screenshot() {
		// Generate a timestamp for the screenshot filename
		String datename = new SimpleDateFormat("yyyyMMdd hhmmss").format(new Date());

		// Create a TakesScreenshot instance
		TakesScreenshot scrshot = ((TakesScreenshot) driver);

		// Capture the screenshot as a file
		File srcfile = scrshot.getScreenshotAs(OutputType.FILE);

		// Specify the destination path for the screenshot
		String destinationpath = Filepath + "/" + datename + ".png";
		File destfile = new File(destinationpath);

		try {
			// Copy the captured screenshot file to the destination path
			FileUtils.copyFile(srcfile, destfile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Return the filename of the captured screenshot
		return datename + ".png";
	}
//----------------------------------------------------------------------------------------------------------//

	protected static void Capture_Screenshot_Ints() {
	    File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    String destinationpath = Filepath + "/screenshot.png";

	    try {
	        FileUtils.copyFile(srcfile, new File(destinationpath));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
}
