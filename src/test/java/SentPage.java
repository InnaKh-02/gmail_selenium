import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class SentPage extends BasePage {

    @FindBy(css = "div.ae4.UI")
    private List<WebElement> visibleSents;

    @FindBy(xpath = "//*[contains(@class, 'nH a98 iY')]")
    private WebElement resSubjAndBodyText;

    @FindBy(xpath = "//*[contains(@class, 'ajz')]")
    private WebElement showRecipientButton;

    @FindBy(xpath = "(//*[contains(@class, 'gI')])[4]")
    private WebElement resRecipientInMail;

    @FindBy(xpath = "//div[text()='Send']")
    private WebElement sendButton;

    public SentPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSentPresent(String recipient, String subject, String body) {
        try {
            waitForVisibility(visibleSents);
            for (WebElement email : visibleSents) {
                if (email.isDisplayed() && email.getText().contains(subject)) {
                    email.click();
                    String resText = waitForVisibility(resSubjAndBodyText).getText();
                    clickElement(showRecipientButton);
                    String resRecipientText = waitForVisibility(resRecipientInMail).getText();
                    return resText.contains(subject) && resText.contains(body) && resRecipientText.contains(recipient);
                }
            }
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void sendDraft(String subject) {
        waitWithJsExecutor(driver);
        for (WebElement e : visibleSents) {
            if (e.isDisplayed() && e.getText().contains(subject)) {
                e.click();
                clickElement(sendButton);
                break;
            }
        }
    }
}