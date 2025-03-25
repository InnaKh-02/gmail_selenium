import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class MainPage extends BasePage {
    @FindBy(xpath = "//div[text()='Compose']")
    WebElement composeButton;
    @FindBy(xpath = "//input[contains(@aria-label, 'To')]")
    WebElement toField;
    @FindBy(xpath = "//div[contains(@class, 'oL aDm az9')]")
    WebElement recipientDisplayField;
    @FindBy(name = "subjectbox")
    WebElement subjectField;
    @FindBy(xpath = "//*[contains(@role,'textbox')]")
    WebElement bodyField;

    @FindBy(css = "div.ae4.UI")
    List<WebElement> visibleEmails;
    @FindBy(css = "tr.zA.yO")
    List<WebElement> visibleDrafts;

    @FindBy(css = "img[aria-label^='Save & close']")
    WebElement saveAndCloseButton;
    @FindBy(css = "div[role='button'][aria-label^='Send']")
    WebElement sendButton;
    @FindBy(xpath = "//a[contains(@aria-label, 'Google Account')]")
    WebElement accountMenuButton;
    @FindBy(className = "sign-out")
    WebElement signOutButton;

    @FindBy(xpath = "//a[contains(@href, '#drafts') and contains(text(), 'Drafts')]")
    WebElement draftFolder;
    @FindBy(xpath = "//a[text()='Sent']")
    WebElement sentFolder;

    @FindBy(xpath = "//*[contains(@class, 'nH a98 iY')]")
    WebElement resSubjAndBodyText;
    @FindBy(xpath = "//*[contains(@class, 'ajz')]")
    WebElement showRecipientButton;
    @FindBy(xpath = "(//*[contains(@class, 'gI')])[4]")
    WebElement resRecipientInMail;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void createDraft(String recipient, String subject, String body) {
        clickElement(composeButton);
        enterText(toField, recipient);
        enterText(subjectField, subject);
        enterText(bodyField, body);
        clickElement(saveAndCloseButton);
    }

    public boolean isDraftCorrect(String recipient, String subject, String body) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(visibleEmails));
        for (WebElement e : visibleEmails) {
            if (e.isDisplayed() && e.getText().contains(subject)) {
                e.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                boolean isRecipientCorrect = recipientDisplayField.getText().equals(recipient);
                boolean isBodyCorrect = bodyField.getText().equals(body);
                return isRecipientCorrect && isBodyCorrect;
            }
        }
        return false;
    }

    public void sendDraft(String recipient, String subject, String body) {
        for (WebElement e : visibleEmails) {
            if (e.isDisplayed() && e.getText().contains(subject)) {
                e.click();
                wait.until(ExpectedConditions.visibilityOf(sendButton));
                System.out.println(sendButton.getTagName() + "\t" + sendButton.getText());
                if (recipientDisplayField.getText().equals(recipient) && bodyField.getText().equals(body)) {
                    clickElement(sendButton);
                    break;
                }
            }
        }
    }

    public boolean isDraftPresent(String recipient, String subject, String body) {
        clickElement(draftFolder);
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(visibleDrafts));
            if (visibleDrafts != null) {
                for (WebElement draft : visibleDrafts) {
                    wait.until(ExpectedConditions.visibilityOf(draft));
                    if (draft.isDisplayed() && draft.getText().contains(subject)) {
                        draft.click();
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                        if (recipientDisplayField.getText().equals(recipient)
                                && bodyField.getText().equals(body)) {
                            clickElement(saveAndCloseButton);
                            return true;
                        }
                        clickElement(saveAndCloseButton);
                    }
                }
            }
        } catch (TimeoutException e) {
            return false;
        }
        return false;
    }

    public boolean isSentPresent(String recipient, String subject, String body) {
        clickElement(sentFolder);
        wait.until((ExpectedConditions.visibilityOfAllElements(visibleDrafts)));
        boolean actual;
        for (WebElement email : visibleDrafts) {
            if (email.isDisplayed() && email.getText().contains(subject)) {
                email.click();
                String resText = waitAndFindElement(resSubjAndBodyText).getText();
                System.out.println(resText);
                clickElement(showRecipientButton);
                String resRecipientText = waitAndFindElement(resRecipientInMail).getText();
                System.out.println(resRecipientText);
                System.out.println(resText.contains(subject) + "\t" + resText.contains(body) + "\t" + resRecipientText.contains(recipient));
                actual = resText.contains(subject) && resText.contains(body) && resRecipientText.contains(recipient);
                if (actual) return true;
            }
        }
        return false;
    }

    public void logout() {
        driver.get(accountMenuButton.getAttribute("href"));
        clickElement(signOutButton);
    }
}