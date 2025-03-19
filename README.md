# Gmail Automation Test

This project automates Gmail's functionality using Selenium WebDriver and Java. The test scenarios cover basic operations like logging in, creating and sending drafts, and verifying email contents. The tests are structured using the Page Object Model (POM) design pattern.

## Requirements

- JDK 17 or later
- Maven
- Selenium WebDriver
- ChromeDriver
## Test Scenario

1. **Login to Gmail**
   - Enter email and password.
   - Verify successful login.

2. **Create and Save Draft**
   - Create a new email with recipient, subject, and body.
   - Save the email as a draft.

3. **Verify Draft**
   - Ensure the draft is saved in the 'Drafts' folder.
   - Verify the draft's content (recipient, subject, body).

4. **Send the Draft**
   - Send the email from the draft.

5. **Verify Sent Mail**
   - Ensure the email is no longer in 'Drafts' and is in the 'Sent' folder.

6. **Logout** (currently commented out due to issues with element interaction).

## Test Execution

The tests can be executed in **IntelliJ IDEA** using the `testng.xml` configuration file. Follow the steps below to run the tests:

### Steps to run the tests:

1. **Open the Project in IntelliJ IDEA**  

2. **Ensure TestNG Plugin is Installed**  
   Make sure that the **TestNG** plugin is installed and enabled in IntelliJ IDEA. If it's not installed, you can do so by going to:  
   `File > Settings > Plugins > Marketplace > Search for TestNG > Install`

3. **Configure the `testng.xml` file**  
   The `testng.xml` file contains all necessary test parameters. Ensure that it is correctly set up with your credentials and other parameters. You can find the file in the project directory.

4. **Run Tests Using `testng.xml`**  
   In **IntelliJ IDEA**, right-click on the `testng.xml` file and select **Run 'testng.xml'** from the context menu. This will execute all the tests defined in the `testng.xml`.

### Example of `testng.xml`

Here is a template for the `testng.xml` configuration file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Test Suite">
    <parameter name="URL" value="https://www.gmail.com"/>
    <parameter name="EMAIL" value="${EMAIL}"/>
    <parameter name="PASSWORD" value="${PASSWORD}"/>
    <parameter name="RECIPIENT" value="${RECIPIENT}"/>
    <parameter name="SUBJECT" value="Test Draft Mail"/>
    <parameter name="BODY" value="Test Draft Mail Body"/>

    <test name="Gmail Automation Test">
        <classes>
            <class name="GmailTest"/>
        </classes>
    </test>
</suite>
