package gradle.junit.selenium.client;

import gradle.junit.selenium.dto.response.LoginResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApiClient {
    protected final RequestSpecification requestSpec;
    
    public BaseApiClient(String baseUri) {
        this.requestSpec = RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON);
    }



}