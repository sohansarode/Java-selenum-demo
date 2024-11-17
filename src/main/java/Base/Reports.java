package Base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Status;

public class Reports extends Utilities {

	static ExtentReports Report;
	static ExtentTest Test;

//----------------------------------------------------------------------------------------------------------//
	
	@BeforeTest(alwaysRun = true)
	public void Set_Up_Report() {
	    // Get the current timestamp in the specified format.
	    String time = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss a").format(new Date());

	    // Create a directory with the timestamp to store the test results.
	    File file = new File("Results/" + time);
	    file.mkdir();

	    // Set the file path to the created directory.
	    Filepath = file.getPath();

	    // Initialize the Extent Reports instance with the result.html file in the created directory.
	    Report = new ExtentReports(file.getPath() + "/result.html", true);
	}

//----------------------------------------------------------------------------------------------------------//

	public static void Start_Test(String Testcase) {
		Log_Info(Testcase);
		Test = Report.startTest(Testcase);
		 Allure.step(Testcase);
	}

//----------------------------------------------------------------------------------------------------------//

	public static void Pass_Test(String Pass) {
		Log_Info(Pass);
		Test.log(LogStatus.PASS, Pass);
		Allure.step(Pass, Status.PASSED);
		
	}

//----------------------------------------------------------------------------------------------------------//

	public static void Fail_Test(String Fail) {
		Log_Info(Fail);
		Test.log(LogStatus.FAIL, Fail, Test.addScreenCapture(Utilities.Capture_Screenshot()));
		Allure.step(Fail, Status.FAILED);
	
	}

//----------------------------------------------------------------------------------------------------------//

	public static void Info(String Info) {
		Log_Info(Info);
		Test.log(LogStatus.INFO, Info);
		Allure.step(Info);
	}

//----------------------------------------------------------------------------------------------------------//	

	public static void Error(String Error) {
		Log_Info(Error);
		Test.log(LogStatus.ERROR, Error);
	}

//----------------------------------------------------------------------------------------------------------//

	public void logWebTableToExtentReport(WebElement Table) {
		List<WebElement> rows = Table.findElements(By.tagName("tr"));

		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			StringBuilder rowContent = new StringBuilder();

			for (WebElement cell : cells) {
				rowContent.append(cell.getText()).append("\t");
			}
			Log_Info(rowContent.toString().trim());
			Test.log(LogStatus.INFO, rowContent.toString().trim());
		}
	}

//----------------------------------------------------------------------------------------------------------//

	@AfterTest(alwaysRun = true)
	public void End_Test() {
		Report.endTest(Test);
		Report.flush();
	}

//----------------------------------------------------------------------------------------------------------//
// Logs an informational message with the class name and the provided message.This method is designed for internal logging purposes.
	private static void Log_Info(String Message) {
		// Get the name of the class calling this method.
		String currentClassName = Thread.currentThread().getStackTrace()[2].getClassName();

		// Log the informational message with the class name.
		logger.info(currentClassName + "===>" + Message);
	}
//----------------------------------------------------------------------------------------------------------//
	
	
}