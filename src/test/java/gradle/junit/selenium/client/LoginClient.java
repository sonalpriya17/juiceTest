package gradle.junit.selenium.client;

import gradle.junit.selenium.dto.request.LoginRequest;
import gradle.junit.selenium.dto.response.LoginResponse;
import gradle.junit.selenium.exceptions.ApiException;
import io.restassured.response.Response;

public class LoginClient extends BaseApiClient {
    public LoginClient(String baseUri) {
        super(baseUri);
    }
    
    public LoginResponse login(LoginRequest loginRequest) {

            Response response = null;
            try {
                response = requestSpec
                        .body(loginRequest)
                        .post("/rest/user/login");

                int statusCode = response.getStatusCode();
                System.out.println("Login Response HTTP Status code: "+ statusCode);

                if (statusCode >= 200 && statusCode < 300) {
                    return response.as(LoginResponse.class);
                } else {
                    String errorMessage = response.getBody().asString();
                    switch (statusCode) {
                        case 400:
                            throw new ApiException("Bad Request: " + errorMessage);
                        case 401:
                            throw new ApiException("Unauthorized this was 401: " + errorMessage);
                        case 403:
                            throw new ApiException("Forbidden: " + errorMessage);
                        case 404:
                            throw new ApiException("Not Found: " + errorMessage);
                            // Add more cases for other status codes as needed
                        default:
                            throw new ApiException("API request failed with status code: " + statusCode + ", Error message: " + errorMessage);
                    }
                }
            } catch (ApiException e) {
                throw e;
            } catch (Exception e) {
                throw new ApiException("Error occurred during login request", e);
            } finally {
                if (response != null) {
                    response.then().log().ifError();
                }
            }
        }

}