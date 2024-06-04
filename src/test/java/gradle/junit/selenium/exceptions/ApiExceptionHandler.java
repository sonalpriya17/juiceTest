package gradle.junit.selenium.exceptions;

import io.restassured.response.Response;

public class ApiExceptionHandler {
    public static void handleApiException(Response response) {
        if (response.getStatusCode() >= 400) {
            String errorMessage = response.getBody().asString();
            throw new ApiException("API request failed with status code: " + response.getStatusCode() + ", Error message: " + errorMessage);
        }
    }
}