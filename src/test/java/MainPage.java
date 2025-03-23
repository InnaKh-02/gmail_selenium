import org.openqa.selenium.*;
import java.time.Duration;
import java.util.List;

public class MainPage extends BasePage {
    By bodyLocator = By.xpath("//*[contains(@role,'textbox')]");
    By mailLocator = By.cssSelector("div.ae4.UI");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void createDraft(String recipient, String subject, String body) {
        clickElement(By.xpath("//div[text()='Compose']"));
        enterText(By.xpath("//input[contains(@aria-label, 'To')]"),recipient);
        enterText(By.name("subjectbox"),subject);
        enterText(bodyLocator, body);
        clickElement(By.cssSelector("img[aria-label^='Save & close']"));
    }

    public boolean isDraftCorrect(String recipient, String subject, String body) {
        List<WebElement> inboxEmails = waitAndFindElements(mailLocator);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        for (WebElement e : inboxEmails) {
            if (e.isDisplayed() && e.getText().contains(subject)) {
                e.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                WebElement recipientElement = driver.findElement(By.xpath(
                        "//div[contains(@class, 'oL aDm az9')]"));
                WebElement bodyElement = driver.findElement(bodyLocator);
                boolean isRecipientCorrect = recipientElement.getText().equals(recipient);
                boolean isBodyCorrect = bodyElement.getText().equals(body);
                return isRecipientCorrect && isBodyCorrect;
            }
        }
        return false;
    }

    public void sendDraft(String recipient, String subject) {
        List<WebElement> inboxEmails = waitAndFindElements(mailLocator);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        for (WebElement e : inboxEmails) {
            if (e.isDisplayed() && e.getText().contains(subject) && e.getText().contains(recipient)) {
                e.click();
            }
        }
        clickElement(By.cssSelector("div[role='button'][aria-label^='Send']"));
        clickElement(mailLocator);
    }

    public boolean isDraftPresent(String recipient, String subject, String body) {
        List<WebElement> drafts = waitAndFindElements(By.cssSelector("tr.zA.yO"));
        if(drafts != null){
            for (WebElement draft : drafts) {
                if (draft.isDisplayed() && draft.getText().contains(subject)) {
                    draft.click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                    WebElement recipientElement = driver.findElement(
                            By.xpath("//div[contains(@class, 'oL aDm az9')]"));
                    WebElement bodyElement = driver.findElement(bodyLocator);

                    if (recipientElement.getText().equals(recipient)
                            && bodyElement.getText().equals(body)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void logout(){
        WebElement accountButtonElement = waitAndFindElement(By.xpath(
                "//a[contains(@aria-label, 'Google Account')]"));
        driver.get(accountButtonElement.getAttribute("href"));
        clickElement(By.className("sign-out"));
    }
}
