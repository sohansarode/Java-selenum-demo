package pagestest;

import pages.Login_Page;
import utils.JsonReader;
import utils.ReportUtils;
import utils.WaitUtils;

public class LoginJson extends ReportUtils {
	private String Validemail, ValidPassword;

	{
		try {
			String projectPath = System.getProperty("user.dir");
			String filePath = projectPath + "/src/main/resources/data/Jsons/Normalfile.json";
			JsonReader jsonReader = new JsonReader(filePath); // Update with correct path
			Validemail = jsonReader.getValue("Email");
			ValidPassword = jsonReader.getValue("Password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@org.testng.annotations.Test(enabled = true, testName = "Test Login Feature", priority = 0)
	public void Login_test_with_Invalid_data_test() throws InterruptedException {
		Login_Page login = new Login_Page();
		ReportUtils.Start_Test("Invalid Login Test Started");

		login.Login_test_with_Invalid_data(Validemail, Random_Number(6) + "@" + "V" + "v");
		WaitUtils.Hard_Wait(5000);
	}

	@org.testng.annotations.Test(enabled = true, description = "Verifies successful login with valid credentials", priority = 1)
	public void Login_test_with_valid_data_test() throws InterruptedException {
		Login_Page login = new Login_Page();
		ReportUtils.Start_Test("Valid Login Test Started");

		login.Login_test_with_valid_data(Validemail, ValidPassword);
	}
}
