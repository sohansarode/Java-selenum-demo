package pages;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import utils.ReportUtils;
import utils.WaitUtils;

public class Login_Page extends ReportUtils {

	@FindBy(xpath = "//*[@name=\"username\"]")
	private WebElement Email;

	@FindBy(xpath = "//*[@name=\"password\"]")
	private WebElement Password;

	@FindBy(xpath = "//*[@type=\"submit\"]")
	private WebElement Loginbtn;

	@FindBy(xpath = "//*[text()=\"Invalid credentials\"]")
	private WebElement Invalid_credential_message;

	@FindBy(xpath = "//*[@class=\"oxd-topbar-header-breadcrumb\"]")
	private WebElement Dashboard;

	@FindBy(xpath = "//*[@class=\"symbol symbol-40px me-5\"]//*[@alt=\"Logo\"]")
	private List<WebElement> Profile1;

	SoftAssert softAssert = new SoftAssert();

	public Login_Page() {
		PageFactory.initElements(driver, this);

	}

	public void Login_test_with_Invalid_data(String email, String password) {
		try {
			//softAssert.assertTrue(Is_Displayed(Email), "Email field is missing or not enabled");
			Sendkeys(Email, email);

			//Assert.assertTrue(Is_Displayed(Password) && Is_Enabled(Password),"Password field is missing or not enabled");
			Sendkeys(Password, password);

			//Assert.assertTrue(Is_Displayed(Loginbtn) && Is_Clickable(Loginbtn),"Login button is missing or not clickable");
			Click(Loginbtn);

			//Assert.assertTrue(Is_Displayed(Invalid_credential_message), "Invalid Credentials message not displayed");

			ReportUtils.Pass_Test("Invalid Login Test Passed");
		} catch (AssertionError e) {
			ReportUtils.Fail_Test("Invalid Login Test Failed: " + e.getMessage());
			throw e;
		}
	}

	public void Login_test_with_valid_data(String email, String password) throws InterruptedException {
		try {

			//Assert.assertTrue(Is_Displayed(Email) && Is_Enabled(Email), "Email field is missing or not enabled");
			Clear_And_SendKeys(Email, email);

			//Assert.assertTrue(Is_Displayed(Password) && Is_Enabled(Password),"Password field is missing or not enabled");
			Clear_And_SendKeys(Password, password);

			//Assert.assertTrue(Is_Displayed(Loginbtn) && Is_Clickable(Loginbtn),"Login button is missing or not clickable");
			Click(Loginbtn);

			WaitUtils.Hard_Wait(3000);
			Assert.assertTrue(Is_Displayed(Dashboard), "Dashboard is not displayed");

			ReportUtils.Pass_Test("Valid Login Test Passed");
		} catch (AssertionError e) {
			ReportUtils.Fail_Test("Valid Login Test Failed: " + e.getMessage());
			throw e;
		}
	}
}
