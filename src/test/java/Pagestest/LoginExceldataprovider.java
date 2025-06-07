package pagestest;

import java.io.IOException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Login_Page;
import utils.ExcelUtils;
import utils.ReportUtils;

public class LoginExceldataprovider extends ReportUtils {

	@DataProvider(name = "invalidLoginData")
	public Object[][] provideInvalidData() throws IOException {
		String projectPath = System.getProperty("user.dir");
		String filePath = projectPath + "/src/main/resources/data/Excels/Logindataprovider.xlsx";
		Object[][] data = ExcelUtils.getSheetData(filePath, "Loginpageinvalid");

		System.out.println("Invalid Login Data:");
		for (Object[] row : data) {
			System.out.println(row[0] + " | " + row[1]);
		}

		return data;
	}

	@DataProvider(name = "validLoginData")
	public Object[][] provideValidData() throws IOException {
		String projectPath = System.getProperty("user.dir");
		String filePath = projectPath + "/src/main/resources/data/Excels/Logindataprovider.xlsx";
		Object[][] data = ExcelUtils.getSheetData(filePath, "Loginpagevalid");

		System.out.println("Valid Login Data:");
		for (Object[] row : data) {
			System.out.println(row[0] + " | " + row[1]);
		}

		return data;
	}

	// --------------------------------------------------------------------------------------------------//

	@Test(dataProvider = "invalidLoginData", priority = 0, groups = { "regression" })
	public void Login_test_with_Invalid_data_test(String email, String password) throws InterruptedException {
		Login_Page login = new Login_Page();
		ReportUtils.Start_Test("Invalid Login Test Started");

		login.Login_test_with_Invalid_data(email, password);
	}

	// --------------------------------------------------------------------------------------------------//

	@Test(dataProvider = "validLoginData", priority = 1, groups = { "smoke", "regression" })
	public void Login_test_with_valid_data_test(String email, String password) throws InterruptedException {
		Login_Page login = new Login_Page();
		ReportUtils.Start_Test("Valid Login Test Started");

		login.Login_test_with_valid_data(email, password);
	}
}
