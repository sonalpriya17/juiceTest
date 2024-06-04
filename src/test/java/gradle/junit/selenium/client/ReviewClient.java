package gradle.junit.selenium.client;

import gradle.junit.selenium.dto.request.ReviewRequest;
import gradle.junit.selenium.dto.response.ReviewResponse;

public class ReviewClient extends ApiClient {
    public ReviewClient(String baseUri) {
        super(baseUri);
    }
    
    public ReviewResponse postReview(ReviewRequest reviewRequest, String token) {
        return requestSpec
                .header("Authorization", "Bearer " + token)
                .body(reviewRequest)
                .put("/rest/products/1/reviews")
                .then()
                .statusCode(201)
                .extract()
                .as(ReviewResponse.class);
    }
    
    public ReviewResponse getReviews(String token) {
        return requestSpec
                .header("Authorization", "Bearer " + token)
                .get("/rest/products/1/reviews")
                .then()
                .statusCode(200)
                .extract()
                .as(ReviewResponse.class);
    }
}