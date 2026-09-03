package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostRequestTests extends BaseTest {

    // ---------- 1. POST with data written directly in code ----------
    @Test(groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void createPostWithHardcodedData() {
        log.info("Starting POST with hardcoded data");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "My First API Test");
        requestBody.put("body", "This is a beginner friendly RestAssured test");
        requestBody.put("userId", 1);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .extract().response();

        log.info("Status Code: " + response.getStatusCode());
        log.info("Response: " + response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("title"), "My First API Test");
        Assert.assertNotNull(response.jsonPath().get("id"));
    }

    // ---------- 2. POST with data from "examples table" style (DataProvider) ----------
    @DataProvider(name = "postData")
    public Object[][] getPostData() {
        // This is like Examples table in feature file
        return new Object[][]{
                {"Title One", "Body content one", 1},
                {"Title Two", "Body content two", 2},
                {"Title Three", "Body content three", 1}
        };
    }

    @Test(dataProvider = "postData", groups = {"regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void createPostWithDataProvider(String title, String body, int userId) {
        log.info("Starting DataProvider POST test with title: " + title);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", title);
        requestBody.put("body", body);
        requestBody.put("userId", userId);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .extract().response();

        log.info("Status Code: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("title"), title);
    }
}