package gradle.junit.selenium.tests.uiTests;

import gradle.junit.selenium.pages.AllProductsPage;
import gradle.junit.selenium.pages.LoginPage;
import gradle.junit.selenium.pages.ReviewPage;
import gradle.junit.selenium.baseTest.BaseTestUI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import gradle.junit.selenium.util.LoggerUtil;
import org.slf4j.Logger;

class JuiceUITestUI extends BaseTestUI {
    
    private static final Logger logger = LoggerUtil.getLogger();

    //TODO Task2: Login and post a product review using Selenium
    @Test
    void loginAndPostProductReviewViaUi() throws InterruptedException {

        logger.info("Navigating to login page");
        driver.get(baseUrl + "/#/login");
        Assertions.assertFalse(driver.getTitle().isEmpty(), "Title should not be empty");
        
        LoginPage loginPage = new LoginPage(driver);
        AllProductsPage allProductsPage = new AllProductsPage(driver);
        ReviewPage reviewPage = new ReviewPage(driver);

        logger.info("Attempting to dismiss any popups if present");
        loginPage.dismissPopup();
        logger.info("Logging in with customer credentials");
        loginPage.login(customer.getEmail(), customer.getPassword());
        loginPage.clickMeWantIt();
    
        logger.info("Navigating to all products page to select a product");
        if(allProductsPage.isAllProductsVisible()){
            allProductsPage.clickOnAppleJuice();
            logger.info("Product selected: Apple Juice");
        }
        logger.info("Entering and submitting a product review");
        String reviewMessage = "This is new Product Review " + java.time.LocalDateTime.now();
        reviewPage.enterReview(reviewMessage);
        logger.info("Review message entered: " + reviewMessage);
        reviewPage.submitReview();
        reviewPage.verifyReviewSubmission();
        logger.info("Review submitted and verified successfully");
    }
}

