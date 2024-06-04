package gradle.junit.selenium.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class ApiClient {
    protected final RequestSpecification requestSpec;
    
    public ApiClient(String baseUri) {
        this.requestSpec = RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON);
    }
}