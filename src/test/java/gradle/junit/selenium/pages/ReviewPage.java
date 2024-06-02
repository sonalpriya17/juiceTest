package gradle.junit.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ReviewPage extends BasePage {

    @FindBy(xpath = "//textarea[@aria-label='Text field to review a product']")
    private WebElement reviewInputField;

    @FindBy(xpath = "//span[normalize-space()='Submit']")
    private WebElement submitReviewButton;

    @FindBy(xpath = "//span[@class='mat-simple-snack-bar-content' and text()='You review has been saved.']")
    private WebElement toastMessage;

    public ReviewPage(WebDriver driver) {
        super(driver);
    }



    public void enterReview(String reviewText) {
        wait.until(ExpectedConditions.visibilityOf(reviewInputField));
        reviewInputField.sendKeys(reviewText);
    }

    public void submitReview() {
        wait.until(ExpectedConditions.elementToBeClickable(submitReviewButton));
        submitReviewButton.click();
    }

    public boolean verifyReviewSubmission() {
        wait.until(ExpectedConditions.visibilityOf(toastMessage));
        return toastMessage.getText().equals("You review has been saved.");
    }
}