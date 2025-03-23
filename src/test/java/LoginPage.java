import org.openqa.selenium.*;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        enterText(By.id("identifierId"),email);
        clickElement(By.id("identifierNext"));
        enterText(By.name("Passwd"),password);
        clickElement(By.id("passwordNext"));
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
