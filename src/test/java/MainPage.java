import org.openqa.selenium.*;
import java.time.Duration;
import java.util.List;

public class MainPage extends BasePage {
    By newMailButtonLocator = By.xpath("//div[text()='Compose']");
    By skipButtonLocator = By.cssSelector("img[aria-label^='Save & close']");
    By sendButtonLocator = By.cssSelector("div[role='button'][aria-label^='Send']");

    By recipientLocator = By.xpath("//input[contains(@aria-label, 'To')]");
    By subjectLocator = By.name("subjectbox");
    By bodyLocator = By.xpath("//*[contains(@role,'textbox')]");

    By mailLocator = By.cssSelector("div.ae4.UI");


    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void createDraft(String recipient, String subject, String body) {
        clickElement(newMailButtonLocator);
        enterText(recipientLocator,recipient);
        enterText(subjectLocator,subject);
        enterText(bodyLocator, body);
        clickElement(skipButtonLocator);
    }

    public void sendDraft(String recipient, String subject, String body) {
        List<WebElement> inboxEmails = waitAndFindElements(mailLocator);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        for (WebElement e : inboxEmails) {
            if (e.isDisplayed() && e.getText().contains(subject)) {
                e.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                WebElement recipientElement = driver.findElement(By.xpath(
                        "//div[contains(@class, 'oL aDm az9')]"));
                WebElement subjectElement = driver.findElement(subjectLocator);

                WebElement bodyElement = driver.findElement(bodyLocator);

                if (!recipientElement.getText().equals(recipient)) {
                    subjectElement.clear();
                    recipientElement.sendKeys(recipient);
                }
                if (!subjectElement.getAttribute("value").equals(subject)) {
                    subjectElement.clear();
                    subjectElement.sendKeys(subject);
                }
                if (!bodyElement.getText().equals(body)) {
                    bodyElement.clear();
                    bodyElement.sendKeys(body);
                }
            }
        }
        clickElement(sendButtonLocator);
        clickElement(mailLocator);
    }

    public boolean isDraftPresent(String recipient, String subject, String body) {
        List<WebElement> drafts = waitAndFindElements(By.cssSelector("tr.zA.yO"));
        for (WebElement draft : drafts) {
            if (draft.isDisplayed() && draft.getText().contains(subject)) {
                draft.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                WebElement recipientElement = driver.findElement(
                        By.xpath("//div[contains(@class, 'oL aDm az9')]"));
                WebElement subjectElement = driver.findElement(subjectLocator);
                WebElement bodyElement = driver.findElement(bodyLocator);

                if (recipientElement.getText().equals(recipient)
                        && subjectElement.getAttribute("value").equals(subject)
                        && bodyElement.getText().equals(body)) {
                    return true;
                }
            }
        }
        return false;
    }
}
