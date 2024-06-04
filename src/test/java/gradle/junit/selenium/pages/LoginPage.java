package gradle.junit.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage{
    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id=\"mat-dialog-0\"]/app-welcome-banner/div/div[2]/button[2]") // todo handle this
    private WebElement dismissButton;

    @FindBy(xpath = "/html/body/div[1]/div/a")
    private WebElement meWantIt;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public void dismissPopup() {
        if (dismissButton.isDisplayed()) {
            dismissButton.click();
        }
    }

    public void clickMeWantIt() {
        if (meWantIt.isDisplayed()) {
            meWantIt.click();
        }
    }
}