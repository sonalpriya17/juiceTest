package gradle.junit.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.JsonParser;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JuiceTest {
    private static String address = "localhost";
    private static String port = "3000";
    private static String baseUrl = String.format("http://%s:%s", address, port);

    static WebDriver driver;
    static Customer customer;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //TODO Task1: Add your credentials to customer i.e. email, password and security answer.
        customer = new Customer.Builder()
        .setEmail("sonalpriya172@gmail.com")
        .setPassword("KritiKrisha26")
        .setSecurityAnswer("gita").build();

        //customer = new Customer.Builder().build();
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }


    //TODO Task2: Login and post a product review using Selenium
    //@Test
    void loginAndPostProductReviewViaUi() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.get(baseUrl + "/#/login");
        
        // TODO Dismiss popup (click close)
        WebElement dismissButton = driver.findElement(By.xpath("//*[@id=\"mat-dialog-0\"]/app-welcome-banner/div/div[2]/button[2]"));
        wait.until(ExpectedConditions.visibilityOf(dismissButton));
        if(dismissButton.isDisplayed()){
            dismissButton.click();
        }

        // Login with credentials
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));

        emailField.sendKeys(customer.getEmail());
        passwordField.sendKeys(customer.getPassword());
        loginButton.click();

        // TODO Navigate to product and post review
        //Me Want it - /html/body/div[1]/div/a
        WebElement meWantIt = driver.findElement(By.xpath("/html/body/div[1]/div/a"));
        wait.until(ExpectedConditions.visibilityOf(meWantIt));
        if (meWantIt.isDisplayed()){
            meWantIt.click();
        }
        Boolean invisible = wait.until(ExpectedConditions.invisibilityOf(meWantIt));
        
        String appleXpath= "//div[contains(text(), 'Apple Juice (1000ml)')]";
        WebElement appleJuice = driver.findElement(By.xpath(appleXpath));
        wait.until(ExpectedConditions.visibilityOf(appleJuice));
        if(invisible && appleJuice.isDisplayed()){
            appleJuice.click();
        }

        WebElement review = driver.findElement(By.xpath("//*[@id=\"mat-input-3\"]"));
        wait.until(ExpectedConditions.visibilityOf(review));
        review.sendKeys("This is review for product");
        WebElement submitBtn = driver.findElement(By.xpath("//*[@id=\"submitButton\"]/span[1]/span"));
        if(submitBtn.isEnabled()){
            submitBtn.click();
        }

        WebElement toastMsg = driver.findElement(By.cssSelector("simple-snack-bar > span.mat-simple-snack-bar-content"));
        wait.until(ExpectedConditions.visibilityOf(toastMsg));
        Assertions.assertTrue(toastMsg.isDisplayed());
        Assertions.assertTrue(toastMsg.getText().equals("You review has been saved."),"toast msg not visible");
    }

    // TODO Task3: Login and post a product review using the Juice Shop API
    @Test
    void api_loginAndPostProductReviewViaApi() {
        Map<String, String> commonHeaders = createCommonHeaders();
        
        String loginPayload = "{ \"email\": \"sonalpriya172@gmail.com\", \"password\": \"KritiKrisha26\" }";
        
        String token = given()
                .headers(commonHeaders)
                .body(loginPayload)
                .post(baseUrl + "/rest/user/login")
                .then()
                .statusCode(200)
                .extract()
                .path("authentication.token");

        System.out.println("Retrieved token: " + token);

        // Use token to post review to product
        String message = "test_" + java.time.LocalDateTime.now();
        String reviewPayload = "{ \"message\": \"" + message + "\", \"author\": \"sonalpriya172@gmail.com\" }";
        
        Map<String, String> authHeaders = new HashMap<>(commonHeaders);
        authHeaders.put("Authorization", "Bearer " + token);
        authHeaders.put("Cookie", "language=en; welcomebanner_status=dismiss; token=" + token + "; cookieconsent_status=dismiss");

        given()
                .headers(authHeaders)
                .body(reviewPayload)
                .put(baseUrl + "/rest/products/1/reviews")
                .then()
                .statusCode(201)
                .body("status", equalTo("success"));

        // Assert that the product review has persisted
        // Fetch all reviews for the product and verify if the review message exists
        String reviewsJson = given()
                .headers(authHeaders)
                .get(baseUrl + "/rest/products/1/reviews")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        // Parse the JSON response and assert the review message exists
        JsonPath jsonPath = JsonPath.from(reviewsJson);
        List<String> messages = jsonPath.getList("data.message");
        System.out.println("First message in the list: " + messages.get(0) + " _ " + java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Kolkata")));
        Assertions.assertTrue(messages.contains(message), "Review message not found in the reviews.");
    }
    //@Test
    void api_loginAndPostProductReviewViaApi2() {
        Map<String, String> commonHeaders = createCommonHeaders();

        String loginPayload = "{ \"email\": \"sonalpriya172@gmail.com\", \"password\": \"KritiKrisha26\" }";

        String token = given()
                .headers(commonHeaders)
                .body(loginPayload)
                .post(baseUrl + "/rest/user/login")
                .then()
                .statusCode(200)
                .extract()
                .path("authentication.token");

        System.out.println("Retrieved token: " + token);

        // Use token to post review to product
        String message = "test_" + java.time.LocalDateTime.now();
        String reviewPayload = "{ \"message\": \"" + message + "\", \"author\": \"sonalpriya172@gmail.com\" }";

        Map<String, String> authHeaders = new HashMap<>(commonHeaders);
        authHeaders.put("Authorization", "Bearer " + token);
        authHeaders.put("Cookie", "language=en; welcomebanner_status=dismiss; token=" + token + "; cookieconsent_status=dismiss");

/*        given()
                .headers(authHeaders)
                .body(reviewPayload)
                .put(baseUrl + "/rest/products/1/reviews")
                .then()
                .statusCode(201)
                .body("status", equalTo("success"));*/

        // Assert that the product review has persisted
        // Fetch all reviews for the product and verify if the review message exists
        String reviewsJson = given()
                .headers(authHeaders)
                .get(baseUrl + "/rest/products/1/reviews")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        // Parse the JSON response and assert the review message exists
        JsonPath jsonPath = JsonPath.from(reviewsJson);
        List<String> messages = jsonPath.getList("data.message");

        Assertions.assertFalse(messages.isEmpty(), "No messages found in the reviews.");
       
        //Assertions.assertTrue(messages.contains(message), "Review message not found in the reviews.");
    }

    private Map<String, String> createCommonHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
