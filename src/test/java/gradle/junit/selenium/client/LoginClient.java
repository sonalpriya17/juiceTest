package gradle.junit.selenium.client;

import gradle.junit.selenium.dto.request.LoginRequest;
import gradle.junit.selenium.dto.response.LoginResponse;

public class LoginClient extends ApiClient {
    public LoginClient(String baseUri) {
        super(baseUri);
    }
    
    public LoginResponse login(LoginRequest loginRequest) {
        return requestSpec
                .body(loginRequest)
                .post("/rest/user/login")
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }
}