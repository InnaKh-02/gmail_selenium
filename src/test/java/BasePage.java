import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public List<WebElement> waitAndFindElements(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            return driver.findElements(locator);
        } catch (TimeoutException e) {
            return null;
        }
    }

    public WebElement waitAndFindElement(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator);
        } catch (TimeoutException e) {
            return null;
        }
    }

    public void clickElement(By locator) {
        WebElement element = waitAndFindElement(locator);
        element.click();
    }

    public void enterText(By locator, String text) {
        WebElement element = waitAndFindElement(locator);
        element.sendKeys(text);
    }
}
