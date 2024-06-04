package gradle.junit.selenium.tests.apiTests;


import gradle.junit.selenium.baseTest.BaseTestAPI;

import gradle.junit.selenium.client.LoginClient;
import gradle.junit.selenium.client.ReviewClient;
import gradle.junit.selenium.dto.request.LoginRequest;
import gradle.junit.selenium.dto.request.ReviewRequest;
import gradle.junit.selenium.dto.response.LoginResponse;
import gradle.junit.selenium.dto.response.ReviewResponse;
import gradle.junit.selenium.exceptions.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


class JuiceAPITestAPI extends BaseTestAPI {


    // TODO Task3: Login and post a product review using the Juice Shop API
    @Test
    void api_loginAndPostProductReviewViaApi2() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(customer.getEmail());
        loginRequest.setPassword(customer.getPassword());
        LoginClient loginClient = new LoginClient(baseUrl);
        LoginResponse loginResponse = loginClient.login(loginRequest);
        System.out.println("Login response token: " + loginResponse.getAuthentication().getToken());
        System.out.println("Login response email : " + loginResponse.getAuthentication().getEmail());

        ReviewClient reviewClient = new ReviewClient(baseUrl);
        ReviewResponse review = reviewClient.getReviews(loginResponse.getAuthentication().getToken());
        System.out.println("get review : " + review.getData().get(1).getMessage());


    }

    private Map<String, String> createCommonHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

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

        System.out.println("Login response token: " + loginToken);
        System.out.println("Login response email : " + loginEmail);

        //Put a review
        ReviewClient reviewClient = new ReviewClient(baseUrl);

        ReviewRequest reviewRequest = new ReviewRequest();
        Date date = new Date();
        System.out.println("Date: " + date.toString());
        String expectedMessage = "SonalTest_" + date.toString();
        System.out.println("expectedMessage: " + expectedMessage);
        reviewRequest.setMessage(expectedMessage);

        String status = reviewClient.postReview(reviewRequest, loginToken).getStatus();
        System.out.println("Status on 1st post call: " + status);

        ReviewResponse getReview = reviewClient.getReviews(loginResponse.getAuthentication().getToken());
        int totalReview = getReview.getData().size();
        int i = 0;

        while (i < getReview.getData().size()) {
            String actualMessage = getReview.getData().get(i).getMessage();
            if (actualMessage.equals(expectedMessage)) {
                System.out.println("------Match Found-------");
                System.out.println("actualMessage: " + actualMessage);
                System.out.println("expectedMessage: " + expectedMessage);
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
        });
    }

    @Test // The system should not allow login to happen, and should throw error.
    void testInvalidCredentials() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invalid@example.com");
        loginRequest.setPassword("wrongpassword");

        LoginClient loginClient = new LoginClient(baseUrl);
        try {
            loginClient.login(loginRequest);
            Assertions.fail("Expected an ApiException to be thrown");
        } catch (ApiException e) {
            // Assert the error message or perform other checks
            System.out.println("print msg - "+ e.getMessage());
            Assertions.assertEquals("Unauthorized this was 401: Invalid email or password.", e.getMessage());
        }


    }
}
