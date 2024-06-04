package gradle.junit.selenium.tests.apiTests;


import gradle.junit.selenium.baseTest.BaseTestAPI;

import gradle.junit.selenium.client.LoginClient;
import gradle.junit.selenium.client.ReviewClient;
import gradle.junit.selenium.dto.request.LoginRequest;
import gradle.junit.selenium.dto.request.ReviewRequest;
import gradle.junit.selenium.dto.response.LoginResponse;
import gradle.junit.selenium.dto.response.ReviewResponse;
import gradle.junit.selenium.baseTest.BaseTestUI;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
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
        System.out.println("Login response token: "+ loginResponse.getAuthentication().getToken());
        System.out.println("Login response email : "+ loginResponse.getAuthentication().getEmail());

        ReviewClient reviewClient = new ReviewClient(baseUrl);
        ReviewResponse review = reviewClient.getReviews(loginResponse.getAuthentication().getToken());
        System.out.println("get review : "+ review.getData().get(1).getMessage());


    }

    private Map<String, String> createCommonHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

}
