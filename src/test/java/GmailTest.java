import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.List;

import static org.testng.Assert.*;

public class GmailTest {

    private LoginPage loginPage;
    private MainPage mainPage;
    private WebDriver driver;

    By draftFolderLocator = By.xpath(
            "//a[contains(@href, '#drafts') and contains(text(), 'Drafts')]");
    By sentFolderLocator = By.xpath("//a[text()='Sent']");

    By mailLocator = By.cssSelector("tr.zA.yO");


    @Parameters({"URL"})
    @BeforeSuite
    public void setUpDriver(String URL) {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
    }

    @Parameters({"EMAIL", "PASSWORD"})
    @Test(priority = 1)
    public void loginTest(String EMAIL, String PASSWORD) {
        loginPage = new LoginPage(driver);
        loginPage.login(EMAIL, PASSWORD);
        assertTrue(loginPage.isAccountPresent(EMAIL));
    }

    @Parameters({"RECIPIENT", "SUBJECT", "BODY"})
    @Test(priority = 2)
    public void createDraftMailTest(String RECIPIENT, String SUBJECT, String BODY) {
        mainPage = new MainPage(driver);
        mainPage.createDraft(RECIPIENT, SUBJECT, BODY);
        mainPage.clickElement(draftFolderLocator);
        assertTrue(mainPage.isDraftPresent(RECIPIENT, SUBJECT, BODY));
    }

    @Parameters({"RECIPIENT", "SUBJECT", "BODY"})
    @Test(priority = 3)
    public void sendDraftMailTest(String RECIPIENT, String SUBJECT, String BODY) {
        mainPage.sendDraft(RECIPIENT, SUBJECT, BODY);//mainPage = new MainPage(driver);
        mainPage.clickElement(sentFolderLocator);

        List<WebElement> inboxEmails = mainPage.waitAndFindElements(mailLocator);
        boolean actual = false;
        for (WebElement email : inboxEmails) {
            if (email.isDisplayed() && email.getText().contains(SUBJECT)) {
                email.click();
                WebElement resultMail = mainPage.waitAndFindElement(By.xpath(
                        "//*[contains(@class, 'nH a98 iY')]"));
                String resText = resultMail.getText();
                mainPage.clickElement(By.xpath("//*[contains(@class, 'ajz')]"));
                WebElement resultRecipient = mainPage.waitAndFindElement(By.xpath(
                        "(//*[contains(@class, 'gI')])[4]"));
                String resRecipient = resultRecipient.getText();
                System.out.println(resRecipient);
                actual = resText.contains(SUBJECT) && resText.contains(BODY) && resRecipient.contains(RECIPIENT);
                if (actual == true) break;
            }
        }
        assertTrue(actual);
    }

    /*@Parameters({"EMAIL"})
    @Test(priority = 4)
    public void logoutTest(String EMAIL) {
        mainPage = new MainPage(driver);
        WebElement accountButtonElement = mainPage.waitAndFindElement(By.xpath(
        "//a[contains(@aria-label, 'Google Account')]"));
        accountButtonElement.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

        //There are 4 options of executing sign out

        *//*((JavascriptExecutor) driver).executeScript(
                "document.querySelector(\"span:contains('Sign out')\").click();"
        );

        WebElement signOutButton = driver.findElement(By.xpath("//a[@rel='noopener noreferrer']"));
        //By.xpath("//*[contains(@class, 'JWEMkf')]"));
        System.out.println(signOutButton.getTagName() + "\t" + signOutButton.getText());
        signOutButton.click();

        Actions actions = new Actions(driver);
        WebElement signOutLink = driver.findElement(By.xpath("//a[contains(@href,
        'https://accounts.google.com/Logout?hl=en&continue=https://mail.google.com/mail/&service=mail&timeStmp=1742376503&secTok=.AG5fkS_BfD3ydA8roUS2KMtS4d8pUtaH7A&ec=GAdAFw')]"));
        actions.moveToElement(signOutLink).click().perform();

        WebElement signOutLink2 = driver.findElement(By.xpath("//a[contains(@href,
        'https://accounts.google.com/Logout?hl=en&continue=https://mail.google.com/mail/&service=mail&timeStmp=1742376503&secTok=.AG5fkS_BfD3ydA8roUS2KMtS4d8pUtaH7A&ec=GAdAFw')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signOutLink);*//*

    }*/

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }
}
