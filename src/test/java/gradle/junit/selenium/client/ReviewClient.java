package gradle.junit.selenium.client;

import org.slf4j.Logger;

import gradle.junit.selenium.dto.request.ReviewRequest;
import gradle.junit.selenium.dto.response.ReviewResponse;
import gradle.junit.selenium.util.ApiResponseValidator;
import gradle.junit.selenium.util.LoggerUtil;
import io.restassured.response.Response;

public class ReviewClient extends BaseApiClient {
    private static final Logger logger = LoggerUtil.getLogger();

    public ReviewClient(String baseUri) {
        super(baseUri);
        logger.debug("ReviewClient initialized with base URI: " + baseUri);
    }

    public ReviewResponse postReview(ReviewRequest reviewRequest, String token) {
        logger.debug("Attempting to post review with token: " + token);
        Response response = requestSpec
                .header("Authorization", "Bearer " + token)
                .body(reviewRequest)
                .put("/rest/products/1/reviews");

        logger.info("Review posted, validating response status code.");
       // ApiResponseValidator.validateHTTPStatusCode(response);
        ApiResponseValidator.validateHTTPStatusCode(response);
        ReviewResponse reviewResponse = response.as(ReviewResponse.class);
        logger.debug("Review post response: " + reviewResponse);
        return reviewResponse;
    }

    public ReviewResponse getReviews(String token) {
        logger.debug("Attempting to get reviews with token: " + token);
        Response response = requestSpec
                .header("Authorization", "Bearer " + token)
                .get("/rest/products/1/reviews");

        logger.info("Reviews retrieved, validating response status code.");
        ApiResponseValidator.validateHTTPStatusCode(response);
        ReviewResponse reviewResponse = response.as(ReviewResponse.class);
        logger.debug("Review get response: " + reviewResponse);
        return reviewResponse;
    }
}