Project Name: Interview Prac
Tech Stack Used:
- Java
- Selenium WebDriver
- TestNG
- Maven
- ExtentReports
- Page Object Model (POM)
- Apache POI (for Excel)

---------------------------------------------------------------------------------------------

Project Structure:

1. Base – Contains base setup classes like browser initialization, loading properties, etc.
2. Pages – All page classes (POM) where WebElements and page actions are written.
3. TestCases – Contains TestNG test classes for different scenarios.
4. Utility – Common reusable methods like Excel readers, waits, loggers.
5. Resources – Contains config.properties, log4j.properties, and Excel test data files.
6. Reports – After running tests, reports will be generated here.
7. testng-All_in_one.xml – The main TestNG suite file used to run all tests.

---------------------------------------------------------------------------------------------
[Can also check the Project_explaination_recording.mp4]  how it will work
How to Run the Tests:

Option 1: Using Eclipse
- Right-click on testng-All_in_one.xml
- Choose Run As > TestNG Suite

Option 2: Using Maven
- Make sure Maven is installed and added to your system PATH
- Use this command in terminal or command prompt (from project root):

mvn test -DsuiteXmlFile=testng-All_in_one.xml

-------------------------------------------------------------------------------------------------

Reports Location:
- After execution, go to the Reports folder.
- Open the ExtentReport.html file in any browser to view results.

