package gradle.junit.selenium.tests.uiTests;


import gradle.junit.selenium.pages.AllProductsPage;
import gradle.junit.selenium.pages.LoginPage;
import gradle.junit.selenium.pages.ReviewPage;
import gradle.junit.selenium.baseTest.BaseTestUI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JuiceUITestUI extends BaseTestUI {
    
    //TODO Task2: Login and post a product review using Selenium
    @Test
    void loginAndPostProductReviewViaUi() throws InterruptedException {

        driver.get(baseUrl + "/#/login");
        Assertions.assertFalse(driver.getTitle().isEmpty(), "Title should not be empty");
        
        LoginPage loginPage = new LoginPage(driver);
        AllProductsPage allProductsPage = new AllProductsPage(driver);
        ReviewPage reviewPage = new ReviewPage(driver);

        // TODO Dismiss popup (click close) //span[normalize-space()='Dismiss']

        loginPage.dismissPopup();
        loginPage.login(customer.getEmail(), customer.getPassword());
        loginPage.clickMeWantIt();
    
        // TODO Navigate to product and post review

        if(allProductsPage.isAllProductsVisible()){
            allProductsPage.clickOnAppleJuice();
        }
        reviewPage.enterReview("This is new Product Review");
        reviewPage.submitReview();
        reviewPage.verifyReviewSubmission();
    }




}
