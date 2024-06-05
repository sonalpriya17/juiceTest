package gradle.junit.selenium.tests.apiTests;

import gradle.junit.selenium.baseTest.BaseTestAPI;
import gradle.junit.selenium.client.LoginClient;
import gradle.junit.selenium.client.ReviewClient;
import gradle.junit.selenium.dto.request.LoginRequest;
import gradle.junit.selenium.dto.request.ReviewRequest;
import gradle.junit.selenium.dto.response.LoginResponse;
import gradle.junit.selenium.dto.response.ReviewResponse;
import gradle.junit.selenium.exceptions.ApiException;
import io.restassured.response.Response;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import gradle.junit.selenium.util.LoggerUtil;
import org.slf4j.Logger;

import java.util.Date;

class JuiceAPITestAPI extends BaseTestAPI {

    private static final Logger logger = LoggerUtil.getLogger();

    //TODO- Sonal - test putReview and postReview together
    @Test
    void api_putReviewGetReview() {
        //login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(customer.getEmail());
        loginRequest.setPassword(customer.getPassword());
        LoginClient loginClient = new LoginClient(baseUrl);
        LoginResponse loginResponse = loginClient.login(loginRequest);

        String loginToken = loginResponse.getAuthentication().getToken();
        String loginEmail = loginResponse.getAuthentication().getEmail();

        logger.info("Login response token: {}", loginToken);
        logger.info("Login response email: {}", loginEmail);

        //Put a review
        ReviewClient reviewClient = new ReviewClient(baseUrl);

        ReviewRequest reviewRequest = new ReviewRequest();
        Date date = new Date();
        logger.info("Date: {}", date.toString());
        String expectedMessage = "SonalTest_" + date.toString();
        logger.info("Expected message: {}", expectedMessage);
        reviewRequest.setMessage(expectedMessage);

        String status = reviewClient.postReview(reviewRequest, loginToken).getStatus();
        logger.info("Status on 1st post call: {}", status);

        ReviewResponse getReview = reviewClient.getReviews(loginResponse.getAuthentication().getToken());
        int totalReview = getReview.getData().size();
        int i = 0;

        while (i < totalReview) {
            String actualMessage = getReview.getData().get(i).getMessage();
            if (actualMessage.equals(expectedMessage)) {
                logger.info("------Match Found-------");
                logger.info("Actual message: {}", actualMessage);
                logger.info("Expected message: {}", expectedMessage);
                Assertions.assertEquals(expectedMessage, actualMessage, "Expected message is not same as actual message obtained..");
            }
            i++;
        }
    }

    //TODO test SQL Query Injection
    @Test
    void testSqlInjection() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("' OR 1=1--");
        loginRequest.setPassword("anypassword");

        LoginClient loginClient = new LoginClient(baseUrl);
        Assertions.assertThrows(ApiException.class, () -> {
            loginClient.login(loginRequest);
        }, "Login should not work. No exception thrown means SQL injection vulnerability");
    }

    @Test // The system should not allow login to happen, and should throw error.
    void testInvalidCredentials() {
        String expectedMsg = "Invalid email or password.";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invalid@example.com");
        loginRequest.setPassword("wrongpassword");

        LoginClient loginClient = new LoginClient(baseUrl);
        logger.info("Attempting to login with invalid credentials: Email - {}, Password - {}", loginRequest.getEmail(), loginRequest.getPassword());

        try {
            LoginResponse p = loginClient.login(loginRequest);
            logger.error("Login attempt should have failed but succeeded with HTTP status code: {}", p.getHttpStatusCode());
            Assertions.fail("Invalid credentials on login expected:\nExpected: " + HttpStatus.SC_UNAUTHORIZED + "\nFound: " + p.getHttpStatusCode());
        } catch (ApiException apiException) {
            Response response = apiException.getResponse();
            String actualMsg = response.asString();
            logger.info("--NEGATIVE TEST-- Login with invalid credentials threw expected exception.");
            Assertions.assertEquals(expectedMsg, actualMsg, "Invalid failure message on invalid credentials");
            logger.info("--NEGATIVE TEST PASS--");
        }
    }
}
