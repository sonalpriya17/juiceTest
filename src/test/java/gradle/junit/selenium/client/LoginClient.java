package gradle.junit.selenium.client;

import gradle.junit.selenium.dto.request.LoginRequest;
import gradle.junit.selenium.dto.response.LoginResponse;
import gradle.junit.selenium.exceptions.ApiException;
import gradle.junit.selenium.util.ApiResponseValidator;
import io.restassured.response.Response;
import io.restassured.http.Header;
import org.slf4j.Logger;
import gradle.junit.selenium.util.LoggerUtil;

public class LoginClient extends BaseApiClient {
    private static final Logger logger = LoggerUtil.getLogger();

    public LoginClient(String baseUri) {
        super(baseUri);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Response response = requestSpec
                .body(loginRequest)
                .post("/rest/user/login");
        
        ApiResponseValidator.validateHTTPStatusCode(response);
        logger.debug("response status Code: ---->  " + response.getStatusCode());
        LoginResponse loginResponse = response.as(LoginResponse.class);
        loginResponse.setHttpStatusCode(response.getStatusCode());
        

        logger.debug("Received response with status code: " + response.getStatusCode());
        String contentTypeHeader = response.getHeader("Content-Type");
        if (contentTypeHeader != null && !contentTypeHeader.isEmpty()) {
            if (contentTypeHeader.contains("application/json") || contentTypeHeader.contains("text/html")) {
                logger.debug("Processing login response with supported Content-Type: " + contentTypeHeader);
                return loginResponse;
            } else {
                logger.error("Failed to process login response due to unsupported Content-Type: " + contentTypeHeader);
                throw new IllegalStateException("Unsupported Content-Type in response: " + contentTypeHeader);
            }
        } else {
            logger.error("Content-Type header is missing in the response.");
            throw new IllegalStateException("Missing Content-Type in response");
        }
    }

}