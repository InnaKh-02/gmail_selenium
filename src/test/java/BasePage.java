import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public WebElement waitAndFindElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element;
        } catch (TimeoutException e) {
            return null;
        }
    }

    public void clickElement(WebElement element) {
        waitAndFindElement(element).click();
    }

    public void enterText(WebElement element, String text) {
        waitAndFindElement(element).sendKeys(text);
    }
}