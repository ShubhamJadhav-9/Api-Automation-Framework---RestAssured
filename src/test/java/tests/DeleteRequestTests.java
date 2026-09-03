package tests;

import base.BaseTest;
import io.restassured.response.Response;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteRequestTests extends BaseTest {

    @Test(groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void deletePost() {
        log.info("Starting DELETE request test");

        // JSONPlaceholder allows delete on existing resources (fake delete)
        Response response = given()
                .when()
                .delete("/posts/1")
                .then()
                .extract().response();

        log.info("Status Code: " + response.getStatusCode());
        log.info("Response body: " + response.asPrettyString());

        // JSONPlaceholder returns 200 for successful fake delete
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}