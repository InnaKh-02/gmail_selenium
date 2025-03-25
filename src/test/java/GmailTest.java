import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class GmailTest {

    private LoginPage loginPage;
    private MainPage mainPage;
    private WebDriver driver;

    @Parameters({"URL"})
    @BeforeSuite
    public void setUpDriver(String URL) {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
    }

    @Parameters({"EMAIL", "PASSWORD"})
    @Test(priority = 1)
    public void loginTest(String EMAIL, String PASSWORD) {
        loginPage.login(EMAIL, PASSWORD);
        assertTrue(loginPage.isAccountPresent(EMAIL));
    }

    @Parameters({"RECIPIENT", "SUBJECT", "BODY"})
    @Test(priority = 2)
    public void isMailPresentInDraftTest(String RECIPIENT, String SUBJECT, String BODY) {
        mainPage.createDraft(RECIPIENT, SUBJECT, BODY);
        //mainPage.clickElement(By.xpath("//a[contains(@href, '#drafts') and contains(text(), 'Drafts')]"));
        assertTrue(mainPage.isDraftPresent(RECIPIENT, SUBJECT, BODY));
    }


    @Parameters({"RECIPIENT", "SUBJECT", "BODY"})
    @Test(priority = 3)
    public void areDraftContentsCorrectTest(String RECIPIENT, String SUBJECT, String BODY) {
        assertTrue(mainPage.isDraftCorrect(RECIPIENT, SUBJECT, BODY));
    }

    @Parameters({"RECIPIENT", "SUBJECT", "BODY"})
    @Test(priority = 4)
    public void draftIsAbsentTest(String RECIPIENT, String SUBJECT, String BODY) {
        mainPage.sendDraft(RECIPIENT, SUBJECT, BODY);
        assertFalse(mainPage.isDraftPresent(RECIPIENT, SUBJECT, BODY));
    }

    @Parameters({"RECIPIENT", "SUBJECT", "BODY"})
    @Test(priority = 5)
    public void draftInSentTest(String RECIPIENT, String SUBJECT, String BODY) {
        assertTrue(mainPage.isSentPresent(RECIPIENT, SUBJECT, BODY));
    }

    @AfterSuite
    public void tearDown() {
        mainPage.logout();
        driver.quit();
    }
}
