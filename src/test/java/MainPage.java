import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {

    @FindBy(xpath = "//a[contains(@href, '#drafts') and contains(text(), 'Drafts')]")
    private WebElement draftFolder;

    @FindBy(xpath = "//a[text()='Sent']")
    private WebElement sentFolder;

    @FindBy(xpath = "//a[contains(@aria-label, 'Google Account')]")
    private WebElement accountMenuButton;

    @FindBy(className = "sign-out")
    private WebElement signOutButton;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void openDrafts() {
        clickElement(draftFolder);
        waitWithJsExecutor(driver);
    }

    public void openSent() {
        clickElement(sentFolder);
        waitWithJsExecutor(driver);
    }

    public void logout() {
        driver.get(accountMenuButton.getAttribute("href"));
        clickElement(signOutButton);
    }
}