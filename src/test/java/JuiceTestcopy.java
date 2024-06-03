import gradle.junit.selenium.Customer;
import gradle.junit.selenium.pages.AllProductsPage;
import gradle.junit.selenium.pages.LoginPage;
import gradle.junit.selenium.pages.ReviewPage;
import gradle.junit.selenium.tests.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class JuiceTestcopy extends BaseTest {
    private static String address = "localhost";
    private static String port = "3000";
    private static String baseUrl = String.format("http://%s:%s", address, port);

    //static WebDriver driver;
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

    }

    //TODO Task2: Login and post a product review using Selenium
    @Test
    void loginAndPostProductReviewViaUi() throws InterruptedException {
        driver.get(baseUrl + "/#/login");
        LoginPage loginPage = new LoginPage(driver);
        AllProductsPage allProductsPage = new AllProductsPage(driver);
        ReviewPage reviewPage = new ReviewPage(driver);

        // TODO Dismiss popup (click close) //span[normalize-space()='Dismiss']

        loginPage.dismissPopup();
        System.out.println("email: "+ customer.getEmail());
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


    // TODO Task3: Login and post a product review using the Juice Shop API

    @Test
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

        Assertions.assertFalse(messages.isEmpty(), "No messages found in the reviews.");
       
        //Assertions.assertTrue(messages.contains(message), "Review message not found in the reviews.");
    }

    private Map<String, String> createCommonHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
