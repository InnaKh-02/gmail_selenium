import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected static WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public static void waitWithJsExecutor(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 2000);");
    }

    public static WebElement waitForVisibility(WebElement element) {
        try {
            return wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
        } catch (TimeoutException | StaleElementReferenceException e) {
            return null;
        }
    }

    public static void waitForVisibility(List<WebElement> elements) {
        try {
            boolean isFound = wait.until(driver -> !elements.isEmpty());
            if (isFound) {
            }
        } catch (TimeoutException | StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    public void clickElement(WebElement element) {
        WebElement el = waitForVisibility(element);
        if (el != null) {
            el.click();
        } else {
            throw new NoSuchElementException("Element not found: " + element);
        }
    }

    public void enterText(WebElement element, String text) {
        waitForVisibility(element).sendKeys(text);
    }
}