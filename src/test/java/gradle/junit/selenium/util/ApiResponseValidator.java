package gradle.junit.selenium.util;

import org.slf4j.Logger;

import gradle.junit.selenium.exceptions.ApiException;
import io.restassured.response.Response;
import gradle.junit.selenium.util.LoggerUtil;

public class ApiResponseValidator {
    private static final Logger logger = LoggerUtil.getLogger();

    public static int validateHTTPStatusCode(Response response) {
        int statusCode = response.getStatusCode();
        logger.debug("Received HTTP status code: " + statusCode);
        if (!(200<=statusCode && statusCode <= 300)) {
            String errorMessage = response.getBody().asString();
            logger.error("API request failed with status code: " + statusCode + ", Error message: " + errorMessage);
            logger.error("FAILED API response: " + response);
            throw new ApiException("API request failed with status code: " + statusCode + ", Error message: " + errorMessage , response);
        }
        return statusCode;
    }
}