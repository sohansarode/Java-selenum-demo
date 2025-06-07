package api;

import java.io.File;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reports implements ITestListener{
	
	protected static ExtentReports reports;
	protected static ExtentTest test;
	private static String filepath;

/*
	public static void deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					System.out.println(files[i].getName());
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
	}*/

/*
 * private static String getResultPath() { File file = new File("results");
 * file.mkdir(); resultpath = file.getPath();
 * 
 * resultpath = new SimpleDateFormat("yyyy-MM-dd hh-mm.ss").format(new Date());
 * if (!new File(resultpath).isFile()) { new File(resultpath); } return
 * resultpath; }
 */

	public static void Report() {
		Date date = new Date();
		String time = date.toString().replace(":", "-");
		File file = new File("results/"+ time);
		file.mkdir();
		filepath = file.getPath();
		reports = new ExtentReports(file.getPath() + "/result.html", true);

	}
	
	
	
	
	String ReportLocation = "reports" + filepath + "/";

	public void onTestStart(ITestResult result) {

		test = reports.startTest(result.getMethod().getMethodName());
		System.out.println(result.getTestClass().getTestName());
		System.out.println(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		test.log(LogStatus.INFO, result.getMethod().getMethodName());
		test.log(LogStatus.PASS, "Test is pass");
	}

	public void onTestFailure(ITestResult result) {
		test.log(LogStatus.INFO, result.getMethod().getMethodName());
		test.log(LogStatus.FAIL, "Test is fail");

	}

	public void onTestSkipped(ITestResult result) {
		test.log(LogStatus.INFO, result.getMethod().getMethodName());
		test.log(LogStatus.SKIP, "Test is skipped");

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		

	}

	public void onStart(ITestContext context) {
		Report();
	}

	public void onFinish(ITestContext context) {
		reports.endTest(test);
		reports.flush();

	}
	
	
}
