package utils;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class ReportUtils extends Utilities {

	static ExtentReports Report;
	static ExtentTest Test;
	static Logger logger = LogManager.getLogger(ReportUtils.class); // ✅ FIXED: Logger initialized

	// This will overwrite the result always
	@BeforeTest(alwaysRun = true)
	public void Set_Up_Report() {
		String projectPath = System.getProperty("user.dir");
		String reportFolderPath = projectPath + "/Reports";

		File file = new File(reportFolderPath);
		if (!file.exists()) {
			file.mkdir();
		}

		Filepath = reportFolderPath + "/ExtentReport.html";
		Report = new ExtentReports(Filepath, true);
	}

	// Start a test with title
	public static void Start_Test(String Testcase) {
		Log_Info(Testcase);
		Test = Report.startTest(Testcase);
		Allure.step(Testcase);
	}

	// Log a passed step
	public static void Pass_Test(String Pass) {
		Log_Info(Pass);
		Test.log(LogStatus.PASS, Pass);
		Allure.step(Pass, Status.PASSED);
	}

	// Log a failed step
	public static void Fail_Test(String Fail) {
		Log_Info(Fail);
		Test.log(LogStatus.FAIL, Fail, Test.addScreenCapture(Utilities.Capture_Screenshot()));
		Allure.step(Fail, Status.FAILED);
	}

	// Log an info step
	public static void Info(String Info) {
		Log_Info(Info);
		Test.log(LogStatus.INFO, Info);
		Allure.step(Info);
	}

	// Log an error
	public static void Error(String Error) {
		Log_Info(Error);
		Test.log(LogStatus.ERROR, Error);
	}

	// Log all table rows into the report
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

	@AfterTest(alwaysRun = true)
	public void End_Test() {
		Report.endTest(Test);
		Report.flush();
		
		
	}

	// ✅ FIXED: Logger safe call
	private static void Log_Info(Object obj) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String className = "Unknown";

		for (StackTraceElement element : stackTrace) {
			if (!element.getClassName().contains("ReportUtils")
					&& !element.getClassName().contains("java.lang.Thread")
					&& !element.getClassName().contains("utils.CommonUtils")) {
				className = element.getClassName();
				break;
			}
		}

		if (logger != null) {
			logger.info(className + " ===>  " + obj.toString());
		} else {
			System.out.println(className + " ===>  " + obj.toString());
		}
	}
}
