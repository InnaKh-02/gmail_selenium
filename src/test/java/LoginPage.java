import org.openqa.selenium.*;

public class LoginPage extends BasePage {
    private By emailLocator = By.id("identifierId");
    private By emailNextButtonLocator = By.id("identifierNext");
    private By passwordLocator = By.name("Passwd");
    private By passwordNextButtonLocator = By.id("passwordNext");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        enterText(emailLocator,email);
        clickElement(emailNextButtonLocator);
        enterText(passwordLocator,password);
        clickElement(passwordNextButtonLocator);
    }

    public boolean isAccountPresent(String email) {
        try {
            WebElement emailElement = waitAndFindElement(By.xpath(
                    "//div[contains(text(), '" + email + "')]"));
            return emailElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
