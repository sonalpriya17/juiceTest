package gradle.junit.selenium.exceptions;

import io.restassured.response.Response;
import lombok.Getter;

@Getter

public class ApiException extends RuntimeException {
    Response response;
    public ApiException(String message,Response response) {
        super(message);
        this.response=response;
    }

}