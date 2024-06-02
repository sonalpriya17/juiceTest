package gradle.junit.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AllProductsPage extends BasePage {
    @FindBy(xpath = "//div[contains(@class, 'ng-star-inserted') and text()='All Products']")
    private WebElement allProductsHeader;

    @FindBy(xpath = "//div[contains(@class, 'item-name') and contains(text(), 'Apple Juice')]")
    private WebElement appleJuiceProduct;


    public AllProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAllProductsVisible() {
        wait.until(ExpectedConditions.visibilityOf(allProductsHeader));
        return allProductsHeader.isDisplayed();
    }

    public void clickOnAppleJuice() {
        wait.until(ExpectedConditions.elementToBeClickable(appleJuiceProduct));
        appleJuiceProduct.click();
    }

}