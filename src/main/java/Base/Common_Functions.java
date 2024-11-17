package Base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.javafaker.Faker;

public class Common_Functions extends Browser_Setup {

//========================================-- Log Methods Starts --======================================================================//
	// Logs information along with the class name of the caller.
	private static void logInfos(Object obj) {
		// Get the current call stack
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		// Default class name (if not enough stack frames)
		String className = "Unknown";

		// Check if there are enough stack frames to retrieve the caller's class name
		if (stackTrace.length >= 4) {
			// Retrieve the caller's class name
			className = stackTrace[3].getClassName();
		}

		// Log the class name and object's string representation
		logger.info(className + " ===>  " + obj.toString());
	}

//========================================-- Dropdown Methods Starts --======================================================================//
	// Selects an option from a dropdown menu based on the specified selection type
	// and value.
	public static Object Select_By_Element(WebElement ElementToScroll, String SelectionType, String SelectionValue) {
		// Create a Select object for the given WebElement representing the dropdown
		// menu
		Select select = new Select(ElementToScroll);

		// Use a switch statement to handle different selection types
		switch (SelectionType.toLowerCase()) {
		case "text":
			// Select an option by visible text
			select.selectByVisibleText(SelectionValue);
			break;
		case "value":
			// Select an option by attribute "value"
			select.selectByValue(SelectionValue);
			break;
		case "index":
			// Select an option by index (position in the dropdown list)
			select.selectByIndex(Integer.parseInt(SelectionValue));
			break;
		default:
			// Throw an exception for invalid selection types
			throw new IllegalArgumentException("Invalid selection type: " + SelectionType);
		}
		return select;
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// Selects a random option from a dropdown menu.
	public static Object Select_Random_Option_From_Dropdown(WebElement DropdownElement) {
		// Create a Select object for the given WebElement representing the dropdown
		// menu
		Select dropdown = new Select(DropdownElement);

		// Get the list of available options in the dropdown
		List<WebElement> options = dropdown.getOptions();

		// Check if there are options available in the dropdown
		if (!options.isEmpty()) {
			// Generate a random index within the range of available options
			int randomIndex = new Random().nextInt(options.size());

			// Select the option at the randomly generated index
			dropdown.selectByIndex(randomIndex);

			// Get the selected option text
			WebElement selectedOption = dropdown.getFirstSelectedOption();
			String selectedOptionText = selectedOption.getText();

			// Print or use the selected option text as needed
			logInfos("Selected Option --->  " + selectedOptionText);

			// Return the selected option text
			return selectedOptionText;
		} else {
			throw new NoSuchElementException("Dropdown has no options available");
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//	
	// This method is use to select value from dropdown for single web element with
	// select class with ignoring any index

	public static void Select_Random_Option_From_Dropdown_Ignore_Index(WebElement DropdownElement, int a) {
		// Create a Select object for the given WebElement representing the dropdown
		// menu
		Select dropdown = new Select(DropdownElement);

		// Get the list of available options in the dropdown
		List<WebElement> options = dropdown.getOptions();

		// Check if there are more than 'a' options (excluding the first 'a' options)
		if (options.size() > a) {
			int randomIndex;

			// Repeat the random selection until a non-first option is selected
			do {
				randomIndex = new Random().nextInt(options.size());
			} while (randomIndex < a); // Ensure the selected index is greater than or equal to 'a'

			// Select the option at the randomly generated index
			dropdown.selectByIndex(randomIndex);
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//
	public static void Select_Random_Option_From_Dropdown_Ignore_Indices(WebElement DropdownElement,
			List<Integer> ignoredIndices) {
		// Create a Select object for the given WebElement representing the dropdown
		// menu
		Select dropdown = new Select(DropdownElement);

		// Get the list of available options in the dropdown
		List<WebElement> options = dropdown.getOptions();

		// Create a Set to store the ignored indices for faster lookup
		Set<Integer> ignoredIndicesSet = new HashSet<>(ignoredIndices);

		// Check if there are options to select from
		if (options.size() > 0) {
			int randomIndex;

			// Repeat the random selection until a non-ignored index is selected
			do {
				randomIndex = new Random().nextInt(options.size());
			} while (ignoredIndicesSet.contains(randomIndex)); // Ensure the selected index is not in the ignored
																// indices list

			// Select the option at the randomly generated index
			dropdown.selectByIndex(randomIndex);
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to select values from dynamic drop down without select

	public static void Dynamic_Dropdown_Selection(List<WebElement> Checkboxes, String Attribute, String Value) {
		// Iterate through the list of checkboxes
		for (WebElement checkbox : Checkboxes) {
			// Check if the current checkbox's attribute value matches the specified value
			if (checkbox.getAttribute(Attribute).equalsIgnoreCase(Value)) {
				// Click the matching checkbox
				checkbox.click();

				// Stop the loop after clicking the first matching checkbox
				break;
			}
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static Object Dropdown_And_Other_Selection_By_Text(List<WebElement> Elements, String Text) {
		// Iterate through each WebElement in the list
		for (WebElement element : Elements) {
			// Check if the text of the current element matches the desired text
			if (element.getText().equalsIgnoreCase(Text)) {
				// Click on the element to select it
				element.click();
				// Exit the loop after selecting the option
				break;
			}
		}
		return Text;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to select value from dropdown for List of web element
	// without select class

	public static Object Select_Random_Option_From_Dropdown(List<WebElement> Options) {
		String optionText = "";
		// Check if the list of options is not empty
		if (!Options.isEmpty()) {
			// Generate a random index within the range of available options
			int randomIndex = new Random().nextInt(Options.size());

			// Get the WebElement at the randomly generated index
			WebElement selectedOption = Options.get(randomIndex);

			// Get the text of the selected option
			optionText = selectedOption.getText().trim();

			// Click the selected option
			selectedOption.click();

			// Print the selected option text to the console
			logInfos("Selected option ---> " + optionText);
		} else {
			// Print a message if no options are available in the dropdown
			logInfos("No options available in the dropdown.");
		}
		return optionText;
	}

//========================================-- Dropdown Methods Ends --=========================================================================//

//...........................................................................................................................................//

//========================================-- Alerts Methods Starts --=========================================================================//	

	// This method is use to Accept & Dismiss Alerts

	public static void Handle_Alerts(String Action) {
		try {
			// Switch to the currently active alert
			Alert alert = driver.switchTo().alert();

			// Check the specified action and perform the corresponding alert operation
			if ("accept".equalsIgnoreCase(Action)) {
				// Accept the alert (click OK or Yes)
				alert.accept();
			} else if ("dismiss".equalsIgnoreCase(Action)) {
				// Dismiss the alert (click Cancel or No)
				alert.dismiss();
			} else {
				// Print a message if the specified action is not recognized
				logInfos("Invalid alert action --> " + Action);
			}
		} catch (NoAlertPresentException e) {
			// Print a message if no alert is present
			logInfos("Alert is not present");
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to perform a context-switch to an alert and retrieve its
	// text

	public static String Switch_To_Alert_And_Get_Text() {
		try {
			// Switch to the currently active alert
			Alert alert = driver.switchTo().alert();

			// Get the text of the alert
			String alertText = alert.getText();

			// Log information about switching to the alert and retrieving text
			logInfos("Switched to alert and retrieved text: " + alertText);

			// Return the text of the alert
			return alertText;
		} catch (NoAlertPresentException e) {
			// Log information if no alert is present
			logInfos("No alert present.");

			// Return null if no alert is present
			return null;
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static void Send_Keys_To_Alert(String text) {
		try {
			// Switch to alert
			Alert alert = driver.switchTo().alert();

			// Send text to alert input field
			alert.sendKeys(text);

			// Log action
			logInfos("Sent keys to alert: " + text);
		} catch (NoAlertPresentException e) {
			// Log error if no alert is present
			logInfos("No alert present.");
		}
	}
//========================================-- Alerts Methods Ends --=========================================================================//

//........................................................................................................................................//

//========================================-- Windows Methods Starts --======================================================================//	

	// This method is use to switch between Windows

	public static void Switch_To_Different_Browser_Window() {
		// Get the handle of the current window
		String currentWindowHandle = driver.getWindowHandle();

		// Get the handles of all open windows
		Set<String> windowHandles = driver.getWindowHandles();

		// Iterate through the window handles using a for loop
		for (String nextWindowHandle : windowHandles) {
			// Check if the current window handle is not equal to the next window handle
			if (!currentWindowHandle.equalsIgnoreCase(nextWindowHandle)) {
				// Switch to the next window
				driver.switchTo().window(nextWindowHandle);
				break; // Exit the loop after switching to the first non-matching window
			}
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method will handle switching between two windows
	public static void Switch_To_Specific_Browser_Window(String windowHandleToSwitch) {
		// Get the handle of the current window
		String currentWindowHandle = driver.getWindowHandle();

		// Get the handles of all open windows
		Set<String> windowHandles = driver.getWindowHandles();

		// Iterate through the window handles using a for loop
		for (String nextWindowHandle : windowHandles) {
			// Check if the current window handle is not equal to the next window handle
			if (!currentWindowHandle.equalsIgnoreCase(nextWindowHandle)) {
				// Check if the provided handle matches the next window handle
				if ("second".equalsIgnoreCase(windowHandleToSwitch)) {
					// Switch to the next window if the provided handle is "second"
					driver.switchTo().window(nextWindowHandle);
					break; // Exit the method after switching to the specified window
				} else if ("first".equalsIgnoreCase(windowHandleToSwitch)) {
					// Switch back to the original window if the provided handle is "first"
					driver.switchTo().window(currentWindowHandle);
					break; // Exit the method after switching back to the original window
				}
			}
		}
	}
//========================================-- Windows Methods Ends --=========================================================================//

//........................................................................................................................................//

//========================================-- All Click Methods Starts --=====================================================================//

	// This method is use to Click on element
	public static void Click(WebElement Element) {
		// Create a WebDriverWait instance with a timeout of 10 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the element to be clickable before proceeding
		wait.until(ExpectedConditions.elementToBeClickable(Element));

		// Get the accessible name of the element
		String elementName = Element.getAccessibleName();

		// If the name attribute is not present or empty, fallback to using the
		// element's toString()
		if (elementName == null || elementName.isEmpty()) {
			elementName = Element.toString();
		}

		// Perform the click operation on the element
		Element.click();

		logInfos("Clicked on element: " + "--->" + elementName);
		// Log information about the click operation
		// logger.info("Clicked on element: " + "--->" + elementName);
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//	

	// This method is use to Click on element using javascript.
	public static void Click_Using_JavaScript_Executor(WebElement Element) {
		// Create a JavascriptExecutor instance
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Execute a click operation on the provided WebElement using JavaScript
		js.executeScript("arguments[0].click();", Element);

		// Log information about the click operation
		logInfos("Clicked on element: " + Element);
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static void Click_All_Elements(List<WebElement> elements) {
		for (WebElement element : elements) {
			element.click();
			// You can also add a wait here if needed, for example, to avoid overwhelming
			// the page
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static void Double_Click(WebElement element) {
		Actions actions = new Actions(driver);
		actions.doubleClick(element).perform();
	}
//========================================-- All Click Methods Ends --========================================================================//

//............................................................................................................................................//

//========================================-- Send Keys Methods Starts --======================================================================//	

	// This method is use to Sendkeys[Pass any data in field]

	public static void Sendkeys(WebElement Element, Object Keys) {
		// Create a WebDriverWait instance with a timeout of 10 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the element to be visible before proceeding
		wait.until(ExpectedConditions.visibilityOf(Element));

		// Get the accessible name of the element
		String elementName = Element.getAccessibleName();

		// If the accessible name is not present or empty, fallback to the element's
		// text
		if (elementName == null || elementName.isEmpty()) {
			elementName = Element.getText();

			// If the text is also empty, fallback to using the element's toString()
			if (elementName == null || elementName.isEmpty()) {
				elementName = Element.toString();
			}
		}

		// Check the type of the Keys parameter and handle accordingly
		if (Keys instanceof String) {
			// Enter the specified keys (string) into the element
			Element.sendKeys((String) Keys);
			logInfos((String) Keys + " --> has been entered in field " + "--->" + elementName);
		} else if (Keys instanceof Integer) {
			// Enter the specified keys (integer) into the element
			Element.sendKeys(String.valueOf(Keys));
			logInfos(Keys + " --> has been entered in field " + "--->" + elementName);
		} else {
			// Handle other types if needed
			logInfos("Unsupported type for 'Keys' parameter");
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//
	// This method is use to Sendkeys & click

	public static void Sendkeys_And_Click(WebElement Element, String Keys) {
		// Enter the specified keys into the element
		Element.sendKeys(Keys);

		// Click on the element
		Element.click();

		// Log information about the keys entered and the click operation
		logInfos(Keys + " has been entered in field and clicked on element: " + Element);
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to Clear data and Send keys in field
	public static void Clear_And_SendKeys(WebElement Element, Object Keys) {
		// Create a WebDriverWait instance with a timeout of 10 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the element to be visible before proceeding
		wait.until(ExpectedConditions.visibilityOf(Element));

		// Clear the content of the WebElement
		Element.clear();

		// Get the accessible name of the element
		String elementName = Element.getAccessibleName();

		// If the name attribute is not present or empty, fallback to using the
		// element's toString()
		if (elementName == null || elementName.isEmpty()) {
			elementName = Element.toString();
		}

		// Check the type of the Keys parameter and handle accordingly
		if (Keys instanceof String) {
			// Send the specified keys (string) to the WebElement
			Element.sendKeys((String) Keys);
			logInfos((String) Keys + " has been entered in field " + "--->" + elementName);
		} else if (Keys instanceof Integer) {
			// Send the specified keys (integer) to the WebElement
			Element.sendKeys(String.valueOf(Keys));
			logInfos(Keys + " has been entered in field " + "--->" + elementName);
		} else {
			// Handle other types if needed
			logInfos("Unsupported type for 'Keys' parameter");
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static void Clear_Data(WebElement element) {
		element.clear();
	}

//========================================-- Send Keys Methods Ends --========================================================================//

//...........................................................................................................................................//

//========================================-- Radio Button Methods Starts --===================================================================//

	// This method is used to check if a radio button is selected

	public static boolean Is_Radio_Button_Selected(WebElement RadioButton) {
		try {
			// Check if the radio button is selected
			return RadioButton.isSelected();
		} catch (NoSuchElementException e) {
			// Handle exception if needed, for example, log the exception or take
			// appropriate action
		}
		// Return false if the radio button is not found or an exception occurs
		return false;
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to select a radio button

	public static void Select_Radio_Button(WebElement RadioButton) {
		try {
			// Check if the radio button is not already selected
			if (!Is_Radio_Button_Selected(RadioButton)) {
				// Click on the radio button to select it
				RadioButton.click();
				logInfos("Selected the radio button");
			} else {
				// Log a message if the radio button is already selected
				logInfos("Radio button is already selected");
			}
		} catch (NoSuchElementException e) {
			// Handle exception if needed, for example, log the exception or take
			// appropriate action
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to unselect a radio button (if applicable)
	public static void Unselect_Radio_Button(WebElement RadioButton) {
		try {
			if (Is_Radio_Button_Selected(RadioButton)) {
				RadioButton.click();
				logInfos("Unselected the radio button");
			} else {
				logInfos("Radio button is already unselected");
			}
		} catch (NoSuchElementException e) {
			// Handle exception if needed
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to get the value (attribute) of a selected radio button

	public static String Get_Selected_Radiobtn_Value(List<WebElement> RadioButtons) {
		try {
			// Iterate through the list of radio buttons
			for (WebElement radioButton : RadioButtons) {
				// Check if the current radio button is selected
				if (Is_Radio_Button_Selected(radioButton)) {
					// Return the value attribute of the selected radio button
					return radioButton.getAttribute("value");
				}
			}
		} catch (NoSuchElementException e) {
			// Handle exception if needed, for example, log the exception or take
			// appropriate action
		}
		// Return null if no radio button is selected or an exception occurs
		return null;
	}
//========================================-- Radio Button Methods Ends --=====================================================================//

//........................................................................................................................................//

//========================================-- Check Box Methods Starts --======================================================================//	

	// This method is use to select checkboxes

	public static void Check_Checkbox(List<WebElement> Checkboxes, String Attribute, String Value) {
		List<WebElement> listOfCheckboxes = Checkboxes;

		// Iterate through the list of checkboxes
		for (int i = 0; i < listOfCheckboxes.size(); i++) {
			// Check if the current checkbox's attribute value matches the specified value
			if (listOfCheckboxes.get(i).getAttribute(Attribute).equalsIgnoreCase(Value)) {
				// Click on the checkbox to check it
				listOfCheckboxes.get(i).click();

				// Print the index of the checkbox (optional, for debugging purposes)
				System.out.println(i);

				// Exit the loop after checking the first matching checkbox
				break;
			}
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to select checkboxes

	public static void Uncheck_Checkbox(List<WebElement> Checkboxes, String Attribute, String Value) {
		List<WebElement> listOfCheckboxes = Checkboxes;

		// Print the list of checkboxes (optional, for debugging purposes)
		System.out.println(listOfCheckboxes);

		// Iterate through the list of checkboxes
		for (int i = 0; i < listOfCheckboxes.size(); i++) {
			// Check if the current checkbox's attribute value matches the specified value
			if (listOfCheckboxes.get(i).getAttribute(Attribute).equalsIgnoreCase(Value)) {
				// Click on the checkbox to uncheck it
				listOfCheckboxes.get(i).click();
				// Note: If the checkbox is already unchecked, clicking it again will uncheck it

				// Exit the loop after unchecking the first matching checkbox
				break;
			}
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//	

	// This method is use to Check multiple checkboxes
	public static void Check_Multiple_Checkboxes(List<WebElement> Options, String Attribute) {
		// Define an array of values to select
		String[] valuesToSelect = {};

		// Iterate through the list of checkboxes
		for (WebElement checkbox : Options) {
			// Get the value of the current checkbox for the specified attribute
			String checkboxValue = checkbox.getAttribute(Attribute);

			// Iterate through the array of values to select
			for (String value : valuesToSelect) {
				// Check if the checkbox value matches the current value to select
				// and the checkbox is not already selected
				if (checkboxValue.equals(value) && !checkbox.isSelected()) {
					// Click on the checkbox to select it
					checkbox.click();
				}
			}
		}
	}
//========================================-- Check Box Methods Ends --=======================================================================//	

//........................................................................................................................................//

//========================================-- Actions Methods Starts --=======================================================================//	

	// This method is use to Move to the web element on page

	public static void Move_To_Element(WebElement Element) {
		// Create an Actions object
		Actions actions = new Actions(driver);

		// Move the mouse pointer to the specified element
		actions.moveToElement(Element).perform();
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to Move to the element & click on it

	public static void Move_To_Element_And_Click(WebElement Element) {
		// Create an Actions object
		Actions actions = new Actions(driver);

		// Move the mouse pointer to the specified element and perform a click
		actions.moveToElement(Element).click().perform();
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to Drag & Drop element from One source to its target place
	// on page

	public static void Drag_And_Drop(WebElement Source, WebElement Target) {
		// Create an Actions object
		Actions actions = new Actions(driver);

		// Perform a drag-and-drop operation from the source element to the target
		// element
		actions.dragAndDrop(Source, Target).perform();
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to Drag & Drop element from source to target by x-axis & y
	// axis of page

	public void Drag_and_Drop_By_Axis(WebElement Source, int Xaxis, int Yaxis) {
		// Create an Actions object
		Actions actions = new Actions(driver);

		// Perform a drag-and-drop operation on the source element by the specified
		// offset
		actions.dragAndDropBy(Source, Xaxis, Yaxis).perform();
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to Drag & Drop element from source to target by x-axis &
	// y-axis of page

	public static void Right_Click(WebElement Element) {
		// Create an Actions object
		Actions actions = new Actions(driver);

		// Perform a right-click (context click) on the specified element
		actions.contextClick(Element).perform();
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to click & hold at one place
	public static void Click_And_Hold(WebElement Element) {
		// Create an Actions object
		Actions actions = new Actions(driver);

		// Perform a click-and-hold action on the specified element
		actions.clickAndHold(Element).perform();

		// Log information about the performed click-and-hold action
		logInfos("Performed click and hold on element: " + Element);
	}

//========================================-- Actions Methods Ends --=========================================================================//

//........................................................................................................................................//

//========================================-- Boolean Methods Starts --=========================================================================//	

	// This method is use to check if element is displayed or not on the page

	public static boolean Is_Displayed(WebElement Element) {
		// Create a WebDriverWait instance with a timeout of 10 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the element to be visible before checking if it is displayed
		wait.until(ExpectedConditions.visibilityOf(Element));

		try {
			// Check if the element is displayed
			return Element.isDisplayed();
		} catch (NoSuchElementException e) {
			// Handle exception if needed
		}

		// Return false if the element is not displayed or an exception occurs
		return false;
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to check if element is clickable or not on the page

	public static boolean Is_Clickable(WebElement Element) {
		// Create a WebDriverWait instance with a timeout of 10 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the element to be clickable before checking if it is enabled
		wait.until(ExpectedConditions.elementToBeClickable(Element));

		try {
			// Check if the element is enabled (clickable)
			return Element.isEnabled();
		} catch (NoSuchElementException e) {
			// Handle exception if needed
		}

		// Return false if the element is not clickable or an exception occurs
		return false;
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to check if an element is enabled or not

	public static boolean Is_Enabled(WebElement Element) {
		// Create a WebDriverWait instance with a timeout of 10 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the element to be visible before checking if it is enabled
		wait.until(ExpectedConditions.visibilityOf(Element));

		try {
			// Check if the element is enabled
			return Element.isEnabled();
		} catch (NoSuchElementException e) {
			// Handle exception if needed, but it's unlikely to be thrown by isEnabled
			e.printStackTrace(); // Print the exception for debugging
		}

		// Return false if the element is not enabled or an exception occurs
		return false;
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to check if an element is selected or not

	public static boolean Is_Selected(WebElement Element) {
		try {
			// Check if the element is not null and is selected
			return Element != null && Element.isSelected();
		} catch (NoSuchElementException e) {
			// Handle exception if needed, although it's unlikely to be thrown by isSelected
			e.printStackTrace(); // Print the exception for debugging
		}

		// Return false if the element is null, not selected, or an exception occurs
		return false;
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to check if an element is present on page or not
	public static boolean Is_Present(WebElement Element) {
		try {
			// Check if the element is not null (exists)
			return Element != null;
		} catch (NoSuchElementException e) {
			// Handle exception if needed, although it's unlikely to be thrown here
			e.printStackTrace(); // Print the exception for debugging
		}

		// Return false if the element is null or an exception occurs
		return false;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static boolean Contains_Text(WebElement element, String text) {
		// Using getText() and contains() might not always be accurate, consider using
		// JavaScript for a more reliable approach
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (Boolean) js.executeScript("return arguments[0].textContent.includes(arguments[1]);", element, text);
	}
//========================================-- Boolean Methods Ends --=======================================================================//

//........................................................................................................................................//	

//========================================-- Random Methods Starts --======================================================================//

	public static String Random_String(int Length) {
		// Generate a random string consisting of alphabetic characters
		String randomString = RandomStringUtils.randomAlphabetic(Length);

		// Print the generated random string (optional, for debugging purposes)
		System.out.println(randomString);

		// Return the generated random string
		return randomString;
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to generate random number

	public static int Random_Number(int digit) {
		// Create a Faker instance for generating random data
		Faker faker = new Faker();

		// Generate a random number with the specified number of digits
		String randomNumberString = faker.number().digits(digit);

		// Parse the generated random number String to an int
		int randomNumber = Integer.parseInt(randomNumberString);

		// Return the generated random number as an int
		return randomNumber;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static Object Random_Number(int digit, boolean asString) {
		// Create a Faker instance for generating random data
		Faker faker = new Faker();

		// Generate a random number with the specified number of digits
		String randomNumberString = faker.number().digits(digit);

		if (asString) {
			// Return the generated random number as a string
			return randomNumberString;
		} else {
			// Parse the generated random number String to an int
			return Integer.parseInt(randomNumberString);
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static int Random_Number_Between(int start, int end) {
		// Create a Faker instance for generating random data
		Faker faker = new Faker();

		// Generate a random number between 1 and 8
		int randomNumber = faker.number().numberBetween(start, end);

		// Convert the generated number to a String
		int randomStringNumber = randomNumber;

		// Return the generated random number as a String
		logInfos(randomStringNumber);
		return randomStringNumber;
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to generate a random email address

	public static String Random_Email() {
		// Create a Faker instance for generating random data
		Faker faker = new Faker();

		// Generate a random email address
		String randomEmail = faker.internet().emailAddress();

		// Return the generated random email address
		return randomEmail;
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is use to generate random names

	public static String Random_Name(String Nametype) {
		// Create a Faker instance for generating random data
		Faker faker = new Faker(new Locale("en-IN"));

		// Variable to store the generated name
		String generatedName = "";

		// Determine the name type and generate the corresponding name
		if (Nametype.equals("firstname")) {
			generatedName = faker.name().firstName();
		} else if (Nametype.equals("lastname")) {
			generatedName = faker.name().lastName();
		} else if (Nametype.equals("fullname")) {
			generatedName = faker.name().fullName();
		} else if (Nametype.equals("username")) {
			generatedName = faker.name().username();
		}
		// Return the generated name
		return generatedName;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//
	// This method is use to generate random Street Address

	public static String Random_Address() {
		// Create a Faker instance for generating random data
		Faker faker = new Faker();

		// Generate a random street address
		String randomAddress = faker.address().streetAddress();

		// Return the generated random street address
		return randomAddress;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static String Random_LoremIpsum(int paragraphs) {
		// Create a Faker instance
		Faker faker = new Faker();

		// Generate a specified number of paragraphs of Lorem Ipsum text
		String randomLoremIpsum = faker.lorem().paragraph(paragraphs);

		// Return the generated Lorem Ipsum text
		return randomLoremIpsum;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//	

	public static String Get_Random_Value(List<String> values) {
		// Generate a random index within the range of the list
		Random random = new Random();
		int randomIndex = random.nextInt(values.size());

		// Retrieve the random value from the list
		return values.get(randomIndex);
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//	

//========================================-- Random Methods Ends --=======================================================================//

	public static void Webtable(List<WebElement> Rows, List<WebElement> Columns) {
		List<WebElement> rowsNumber = Rows;
		int rowCount = rowsNumber.size();
		System.out.println("No of rows in this table : " + rowCount);

		// Finding number of Columns

		List<WebElement> columnsNumber = Columns;
		int columnCount = columnsNumber.size();
		System.out.println("No of columns in this table : " + columnCount);

		// Finding cell value at 4th row and 3rd column

		WebElement cellAddress = driver.findElement(null);
		String value = cellAddress.getText();
		System.out.println("The Cell Value is :" + value);

	}

//========================================-- Frame Methods Starts --=======================================================================//

	// This method can be used to enter into frame

	public static void Switch_To_Frame_By_Index(int Index) {
		// Switch the WebDriver focus to the frame with the specified index
		driver.switchTo().frame(Index);
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//	

	// This method can be used to come out of frame
	public static void Switch_Out_Of_Frame() {
		// Switch the WebDriver focus out of the current frame to the default content
		driver.switchTo().defaultContent();
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to switch to a frame by its name or id

	public static void Switch_To_Frame_By_Name_Or_Id(String frameNameOrId) {
		// Switch the WebDriver focus to the frame with the specified name or id
		driver.switchTo().frame(frameNameOrId);

		// Log information about the switched frame (optional, for debugging purposes)
		logInfos("Switched to frame with name or id: " + frameNameOrId);
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to switch to the parent frame
	public static void Switch_To_Parent_Frame() {
		// Switch the WebDriver focus to the parent frame
		driver.switchTo().parentFrame();

		// Log information about the switched frame (optional, for debugging purposes)
		logInfos("Switched to parent frame");
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to get the total number of frames in a page
	public static int Get_Number_Of_Frames() {
		// Find all elements with the 'iframe' tag to determine the number of frames
		int numberOfFrames = driver.findElements(By.tagName("iframe")).size();

		// Log information about the number of frames in the page (optional, for
		// debugging purposes)
		logInfos("Number of frames in the page: " + numberOfFrames);

		// Return the number of frames
		return numberOfFrames;
	}

//========================================-- Frame Methods Ends --=======================================================================//	

//........................................................................................................................................//

//========================================-- Navigation Methods Starts --====================================================================//

	// This method is used to navigate back
	public static void Navigate_Back() {
		// Use the WebDriver's navigate method to go back to the previous page
		driver.navigate().back();

		// Log information about the navigation (optional, for debugging purposes)
		logInfos("Navigated back to the previous page");
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to navigate forward to the next page
	public static void Navigate_Forward() {
		// Use the WebDriver's navigate method to go forward to the next page
		driver.navigate().forward();

		// Log information about the navigation (optional, for debugging purposes)
		logInfos("Navigated forward to the next page");
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to refresh the current page
	public static void Refresh_Page() {
		// Use the WebDriver's navigate method to refresh the current page
		driver.navigate().refresh();

		// Log information about the page refresh (optional, for debugging purposes)
		logInfos("Refreshed the current page");
	}

//========================================-- Navigation Methods Ends --=====================================================================//

//........................................................................................................................................//

//========================================-- Upload files Methods Starts --===============================================================//

	// This method is used to upload file using javascript

	public static void Upload_File_By_JS(WebElement InputElement, String FilePath) throws IllegalArgumentException {
		// Create a File object with the specified file path
		File file = new File(FilePath);

		// Check if the file exists
		if (!file.exists()) {
			throw new IllegalArgumentException("File not found: " + FilePath);
		}

		// Use JavaScriptExecutor to set the value of the input field to the file path
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = '" + FilePath + "'", InputElement);
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//
	// This method is used to upload file using sendkeys
	public static void Upload_File_By_Sendkeys(WebElement InputElement, String FilePath) {
		// Use the sendKeys method to set the file path in the input field
		InputElement.sendKeys(FilePath);
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	// This method is used to upload file using keyboard

	public static void Upload_File_By_Robot(WebElement InputElement, String FilePath) {
		// Click on the input element to open the file dialog
		InputElement.click();

		try {
			// Create a Robot instance for simulating keyboard and mouse events
			Robot robot = new Robot();

			// Create a StringSelection object containing the file path
			StringSelection stringSelection = new StringSelection(FilePath);

			// Set the file path to the system clipboard
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

			// Paste the file path using CTRL+V
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			// Press Enter to confirm the file selection
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			// Handle exception if needed
			e.printStackTrace();
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//	

	public void upload_File_From_Local(String filepath) throws AWTException, InterruptedException {
		StringSelection ss = new StringSelection(filepath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		// Use Robot class to handle native system interactions
		Robot robot = new Robot();

		// Press Enter to focus on the file dialog
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Pause briefly to ensure the file dialog is focused
		Thread.sleep(1000); // Adjust as needed

		// Press Ctrl+V to paste the file path into the file dialog
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		// Press Enter to confirm the file selection
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
//========================================-- Upload files Methods Ends --===============================================================//

	public static List<String> Get_Text_From_WebElement_List(List<WebElement> ElementList) {
		// Create a List to store the text content of each WebElement
		List<String> textList = new ArrayList<>();

		// Iterate through each WebElement in the list and retrieve its text content
		for (WebElement element : ElementList) {
			String text = element.getText();
			textList.add(text);
		}

		// Return the List of text content
		return textList;
	}
//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static boolean Compare_WebElement_Text_Contains(List<WebElement> List1, List<WebElement> List2) {
		// Check if the sizes of the two lists are different
		if (List1.size() != List2.size()) {
			return false;
		}

		// Compare text content of corresponding elements in the two lists
		return IntStream.range(0, List1.size()).allMatch(i -> List1.get(i).getText().contains(List2.get(i).getText())
				|| List2.get(i).getText().contains(List1.get(i).getText()));
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	public static boolean compareListTextContains(List<String> cellTexts, List<WebElement> list2) {
		// Check if the sizes of the two lists are different
		if (cellTexts.size() != list2.size()) {
			return false;
		}

		// Compare text content of corresponding elements in the two lists
		for (int i = 0; i < cellTexts.size(); i++) {
			String text1 = cellTexts.get(i);
			String text2 = list2.get(i).getText();

			// Check if either text contains the other
			if (!(text1.contains(text2) || text2.contains(text1))) {
				return false; // If not, return false immediately
			}
		}

		return true; // All corresponding elements contain each other's text
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//
	public static boolean Compare_String_List_Ignoring_Icons(List<String> list1, List<String> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}

		return IntStream.range(0, list1.size()).allMatch(i -> {
			String text1 = list1.get(i).replaceAll("[↓↑]", "").trim();
			String text2 = list2.get(i).replaceAll("[↓↑]", "").trim();
			return text1.contains(text2) || text2.contains(text1);
		});
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//
	public List<String> Get_Text_Of_Checked_Options(List<WebElement> List1) {
		List<String> checkedOptionsText = new ArrayList<>();

		for (WebElement option : List1) {
			WebElement checkbox = option.findElement(By.xpath("preceding-sibling::input[@type='checkbox']"));

			if (checkbox.isSelected()) {
				checkedOptionsText.add(option.getText());
			}
		}

		System.out.println(checkedOptionsText);
		return checkedOptionsText;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//
	public static void Click_Data_In_CusNo_Column(String dataToClick, String cusNoHeaderText) {
		// Find the "Cus No" column header
		WebElement cusNoHeader = driver.findElement(
				By.xpath("//table[@class=\"table cus-table-row-spacing mb-0\"]//th[text()='" + cusNoHeaderText + "']"));

		// Get the index of the "Cus No" column
		int cusNoColumnIndex = Get_Index_Of_Element(cusNoHeader);

		// Find all rows in the table
		List<WebElement> rows = driver.findElements(
				By.xpath("//table[@class=\"table cus-table-row-spacing mb-0\"]/tbody/tr[@class=\"bg-white\"]"));

		for (WebElement row : rows) {
			// Find the "Cus No" cell in the current row using the dynamic index
			WebElement cusNoCell = row.findElement(By.xpath("./td[" + cusNoColumnIndex + "]"));

			// Check if the cell's text matches the target data
			if (cusNoCell.getText().equals(dataToClick)) {
				// Click on the cell (or any element within it, if needed)
				cusNoCell.click(); // Or click on a specific element within the cell
				break; // Exit the loop once the data is found and clicked
			}
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//

	private static int Get_Index_Of_Element(WebElement Element) {
		List<WebElement> siblings = Element.findElements(By.xpath("./preceding-sibling::*"));
		return siblings.size() + 1; // Add 1 to account for the element itself
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//	

//========================================-- Web Tables Methods Starts --===============================================================//	

	public static void Webtable(WebElement Table) {
		List<WebElement> rows = Table.findElements(By.tagName("tr"));

		// Iterate through the rows
		for (WebElement row : rows) {
			// Get all the columns in each row
			List<WebElement> columns = row.findElements(By.tagName("td"));

			// Iterate through the columns
			for (WebElement column : columns) {
				// Print the text from each column
				System.out.print(column.getText() + "\t");
			}
			System.out.println(); // Move to the next line after printing each row
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------//	

	public static void scrapeTable(WebElement tableElement, List<WebElement> paginationElements,
			String numPagesToScrape) {
		int currentPage = 1;
		while (numPagesToScrape.equalsIgnoreCase("all") || currentPage <= Integer.parseInt(numPagesToScrape)) { // Scrapes
																												// until
																												// numPagesToScrape
																												// is
																												// reached
																												// or if
																												// numPagesToScrape
																												// is
																												// -1,
																												// scrape
																												// all
																												// pages
			List<WebElement> rows = tableElement.findElements(By.tagName("tr"));

			// Iterate through the rows
			for (WebElement row : rows) {
				// Get all the columns in each row
				List<WebElement> columns = row.findElements(By.tagName("td"));

				// Iterate through the columns
				for (WebElement column : columns) {
					// Print the text from each column
					System.out.print(column.getText() + "\t");
				}
				System.out.println(); // Move to the next line after printing each row
			}

			// Find and click on the next page number if available
			boolean nextPageClicked = false;
			for (WebElement paginationElement : paginationElements) {
				if (paginationElement.getText().equals(String.valueOf(currentPage + 1))) {
					paginationElement.click();
					currentPage++;
					nextPageClicked = true;
					break;
				}
			}

			// Exit the loop if there's no next page number or if the next page is already
			// clicked
			if (!nextPageClicked) {
				break;
			}

			// Wait for the page to load (you may need to adjust the wait time)
			try {
				Thread.sleep(2000); // Adjust the wait time as needed
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
