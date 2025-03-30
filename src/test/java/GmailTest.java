import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class GmailTest {

    private LoginPage loginPage;
    private MainPage mainPage;
    private DraftPage draftPage;
    private SentPage sentPage;
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
        draftPage = new DraftPage(driver);
        sentPage = new SentPage(driver);

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
        draftPage.createDraft(RECIPIENT, SUBJECT, BODY);
        mainPage.openDrafts();
        assertTrue(draftPage.isDraftPresent(RECIPIENT, SUBJECT, BODY));
    }


    @Parameters({"RECIPIENT", "SUBJECT", "BODY"})
    @Test(priority = 3)
    public void areDraftContentsCorrectTest(String RECIPIENT, String SUBJECT, String BODY) {
        assertTrue(draftPage.isDraftCorrect(RECIPIENT, SUBJECT, BODY));
    }

    @Parameters({"RECIPIENT", "SUBJECT", "BODY"})
    @Test(priority = 4)
    public void draftIsAbsentTest(String RECIPIENT, String SUBJECT, String BODY) {
        sentPage.sendDraft(SUBJECT);
        assertFalse(draftPage.isDraftPresent(RECIPIENT, SUBJECT, BODY));
    }

    @Parameters({"RECIPIENT", "SUBJECT", "BODY"})
    @Test(priority = 5)
    public void draftInSentTest(String RECIPIENT, String SUBJECT, String BODY) {
        mainPage.openSent();
        assertTrue(sentPage.isSentPresent(RECIPIENT, SUBJECT, BODY));
    }

    @AfterSuite
    public void tearDown() {
        mainPage.logout();
        driver.quit();
    }
}
