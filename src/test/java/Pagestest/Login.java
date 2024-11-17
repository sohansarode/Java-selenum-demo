package Pagestest;

import java.io.IOException;
import org.testng.annotations.Test;
import Base.Read_Excel;
import Base.Reports;
import Base.Waits;
import Pages.Login_Page;

public class Login extends Reports {
	private String Validemail, ValidPassword;


	{
		try {
			Validemail = Read_Excel.Get_String_Cell_Data("Loginpage", "Email", "Values");
			ValidPassword = Read_Excel.Get_String_Cell_Data("Loginpage", "Password", "Values");
		} catch (IOException e) {
			// Handle the IOException appropriately
			e.printStackTrace();
		}
	}
//--------------------------------------------------------------------------------------------------//

	@Test(enabled = true, testName = "Test Login Feature", priority = 0)
	public void Login_test_with_Invalid_data_test() throws InterruptedException {

		Login_Page login = new Login_Page();
		Reports.Start_Test("Invalid Login Test Started");

		login.Login_test_with_Invalid_data(Validemail, Random_Number(6) + "@" + "V" + "v");
		Waits.Hard_Wait(5000);

	}

//--------------------------------------------------------------------------------------------------//

	@Test(enabled = true, testName = "Test Login Feature", priority = 1)
	public void Login_test_with_Invalid_User_data_test() throws InterruptedException {

		Login_Page login = new Login_Page();
		Reports.Start_Test("Invalid User Login Test Started");

		login.Login_test_with_Invalid_User_data(Random_Email(), ValidPassword);
		Waits.Hard_Wait(5000);

	}

//--------------------------------------------------------------------------------------------------//

	@Test(enabled = true, testName = "Test Login Feature", priority = 2)
	public void Login_test_with_valid_data_test() {

		Login_Page login = new Login_Page();
		Reports.Start_Test("Valid Login Test Started");

		login.Login_test_with_valid_data(Validemail, ValidPassword);

	}
}