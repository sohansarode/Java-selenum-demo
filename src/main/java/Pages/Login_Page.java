package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Base.Reports;
import Base.Waits;

public class Login_Page extends Reports {

	@FindBy(xpath = "//*[text()=\"Visit Site\"]")
	private WebElement Visitbtn;

	@FindBy(xpath = "//*[@name=\"email\"]")
	private WebElement Email;

	@FindBy(xpath = "//*[@name=\"password\"]")
	private WebElement Password;

	@FindBy(xpath = "//*[@type=\"submit\"]")
	private WebElement Loginbtn;

	@FindBy(xpath = "//*[text()=\"Invalid Credentials\"]")
	private WebElement Invalid_credential_message;

	@FindBy(xpath = "//*[text()=\"User not found\"]")
	private WebElement User_not_found_message;

	@FindBy(xpath = "//*[@class=\"symbol symbol-40px me-5\"]//*[@alt=\"Logo\"]")
	private WebElement Profile;
//--------------------------------------------------------------------------------------------------//	

	public Login_Page() {
		PageFactory.initElements(driver, this);
		//Test
	}

//--------------------------------------------------------------------------------------------------//	

	public void Login_test_with_Invalid_data(String email, String object) {
		try {

			if (Is_Displayed(Email) && Is_Enabled(Email)) {
				Clear_And_SendKeys(Email, email);
				Reports.Info("Email Entered");
			} else {
				throw new Exception("Email field not displayed");
			}

			if (Is_Displayed(Password) && Is_Enabled(Password)) {
				Clear_And_SendKeys(Password, object);
				Reports.Info("Password Entered");
			} else {
				throw new Exception("Password field not displayed");
			}

			if (Is_Displayed(Loginbtn) && Is_Clickable(Loginbtn)) {
				Click(Loginbtn);
				Reports.Info("Login Btn Clicked");
			} else {
				throw new Exception("Login Btn not Clicked");
			}

			if (Is_Displayed(Invalid_credential_message)) {
				Reports.Pass_Test("InValid Login Test Pass");
			}
		} catch (Exception e) {
			Reports.Fail_Test("InLogin Test Failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

//--------------------------------------------------------------------------------------------------//	

	public void Login_test_with_Invalid_User_data(String email, String object) {
		try {

			if (Waits.waitForVisibilityOfElement(By.xpath("//*[text()=\"Visit Site\"]"), 5)) {
				Click(Visitbtn);
				Reports.Info("Visit Btn Clicked");
			} else {

			}
			
			if (Is_Displayed(Email) && Is_Enabled(Email)) {
				Clear_And_SendKeys(Email, email);
				Reports.Info("Email Entered");
			} else {
				throw new Exception("Email field not displayed");
			}

			if (Is_Displayed(Password) && Is_Enabled(Password)) {
				Clear_And_SendKeys(Password, object);
				Reports.Info("Password Entered");
			} else {
				throw new Exception("Password field not displayed");
			}

			if (Is_Displayed(Loginbtn) && Is_Clickable(Loginbtn)) {
				Click(Loginbtn);
				Reports.Info("Login Btn Clicked");
			} else {
				throw new Exception("Login Btn not Clicked");
			}

			if (Is_Displayed(User_not_found_message)) {
				Reports.Pass_Test(
						"InValid Login with non-existing user Test Pass -->" + User_not_found_message.getText());
			}
		} catch (Exception e) {
			Reports.Fail_Test("InValid Login with non-existing user Test Failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

//--------------------------------------------------------------------------------------------------//	

	public void Login_test_with_valid_data(String email, String password) {
		try {

			if (Waits.waitForVisibilityOfElement(By.xpath("//*[text()=\"Visit Site\"]"), 5)) {
				Click(Visitbtn);
				Reports.Info("Visit Btn Clicked");
			} else {

			}
			Waits.Hard_Wait(3000);
			Handle_Alerts("accept");
			if (Is_Displayed(Email) && Is_Enabled(Email)) {
				Clear_And_SendKeys(Email, email);
				Reports.Info("Email Entered");
			} else {
				throw new Exception("Email field not displayed");
			}

			if (Is_Displayed(Password) && Is_Enabled(Password)) {
				Clear_And_SendKeys(Password, password);
				Reports.Info("Password Entered");
			} else {
				throw new Exception("Password field not displayed");
			}

			if (Is_Displayed(Loginbtn) && Is_Clickable(Loginbtn)) {
				Click(Loginbtn);
				Reports.Info("Login Btn Clicked");
			} else {
				throw new Exception("Login Btn not Clicked");
			}

		} catch (Exception e) {
			Reports.Fail_Test("Valid Login Test Failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

//--------------------------------------------------------------------------------------------------//
}
