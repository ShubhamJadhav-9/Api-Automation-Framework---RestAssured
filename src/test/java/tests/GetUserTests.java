package tests;

import base.BaseTest;
import io.restassured.response.Response;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetUserTests extends BaseTest {

    @Test(groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void getAllUsers() {
        log.info("Starting GET all users test");

        Response response = given()
                .when()
                .get("/users")
                .then()
                .extract().response();

        log.info("Response status code: " + response.getStatusCode());
        log.info("Response body: " + response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getList("$").size() > 0);
    }

    @Test(groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class)
    public void getSingleUser() {
        log.info("Starting GET single user test");

        Response response = given()
                .when()
                .get("/users/1")
                .then()
                .extract().response();

        log.info("Status Code: " + response.getStatusCode());
        log.info("User name: " + response.jsonPath().getString("name"));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), 1);
        Assert.assertNotNull(response.jsonPath().getString("email"));
    }
}