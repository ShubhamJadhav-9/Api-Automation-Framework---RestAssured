package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AdvancedApiTests extends BaseTest {

    // =========================================================
    // 1. API CHAINING
    // Capture response from one API and use it in next API
    // =========================================================
    @Test(groups = {"regression", "chaining"}, retryAnalyzer = RetryAnalyzer.class)
    public void apiChaining_GetUserThenCreatePost() {

        log.info("===== API CHAINING TEST STARTED =====");

        // Step 1: GET a user and capture data from response
        Response userResponse = given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .extract().response();

        // Capture values we need for next request
        int userId = userResponse.jsonPath().getInt("id");
        String userName = userResponse.jsonPath().getString("name");
        String userEmail = userResponse.jsonPath().getString("email");

        log.info("Captured User ID   : " + userId);
        log.info("Captured User Name : " + userName);
        log.info("Captured Email     : " + userEmail);

        // Step 2: Use the captured data as payload for POST request
        Map<String, Object> postBody = new HashMap<>();
        postBody.put("title", "Post created by " + userName);
        postBody.put("body", "This post is created using chaining. Email: " + userEmail);
        postBody.put("userId", userId);          // <-- using value from previous response

        Response postResponse = given()
                .contentType(ContentType.JSON)
                .body(postBody)
                .when()
                .post("/posts")
                .then()
                .extract().response();

        log.info("Chained POST Status Code : " + postResponse.getStatusCode());
        log.info("Chained POST Response    : " + postResponse.asPrettyString());

        // Validations
        Assert.assertEquals(postResponse.getStatusCode(), 201);
        Assert.assertEquals(postResponse.jsonPath().getInt("userId"), userId);
        Assert.assertTrue(postResponse.jsonPath().getString("title").contains(userName));

        log.info("===== API CHAINING TEST COMPLETED =====");
    }


    // =========================================================
    // 2. PARAMETERS SETTING (Query Params + Path Params)
    // =========================================================
    @Test(groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void testWithQueryAndPathParameters() {

        log.info("===== PARAMETERS TEST STARTED =====");

        // Example 1: Path Parameter
        Response pathParamResponse = given()
                .pathParam("userId", 2)                 // path parameter
                .when()
                .get("/users/{userId}")
                .then()
                .extract().response();

        log.info("Path Param Status Code : " + pathParamResponse.getStatusCode());
        log.info("Path Param Response    : " + pathParamResponse.asPrettyString());

        Assert.assertEquals(pathParamResponse.getStatusCode(), 200);
        Assert.assertEquals(pathParamResponse.jsonPath().getInt("id"), 2);


        // Example 2: Query Parameters
        // JSONPlaceholder supports filtering comments by postId
        Response queryParamResponse = given()
                .queryParam("postId", 1)                // query parameter
                .queryParam("_limit", 3)                // another query param
                .when()
                .get("/comments")
                .then()
                .extract().response();

        log.info("Query Param Status Code : " + queryParamResponse.getStatusCode());
        log.info("Query Param Response    : " + queryParamResponse.asPrettyString());

        Assert.assertEquals(queryParamResponse.getStatusCode(), 200);
        // All returned comments should belong to postId = 1
        Assert.assertTrue(queryParamResponse.jsonPath().getList("postId")
                .stream().allMatch(id -> id.equals(1)));

        log.info("===== PARAMETERS TEST COMPLETED =====");
    }


    // =========================================================
    // 3. HEADERS SETTING
    // =========================================================
    @Test(groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void testWithCustomHeaders() {

        log.info("===== HEADERS TEST STARTED =====");

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("User-Agent", "RestAssured-Beginner-Project")
                .header("X-Custom-Header", "MyCustomValue")   // custom header example
                .when()
                .get("/users/1")
                .then()
                .extract().response();

        log.info("Headers Test Status Code : " + response.getStatusCode());
        log.info("Response Headers         : " + response.getHeaders());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), 1);

        // You can also verify a response header if needed
        Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));

        log.info("===== HEADERS TEST COMPLETED =====");
    }


    // =========================================================
    // 4. AUTHENTICATION SETTING
    // JSONPlaceholder does not require real auth,
    // but we show the correct RestAssured syntax.
    // =========================================================
    @Test(groups = {"regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void testWithAuthentication() {

        log.info("===== AUTHENTICATION TEST STARTED =====");

        // -------- Option A: Basic Authentication --------
        Response basicAuthResponse = given()
                .auth().basic("username", "password")   // basic auth
                .when()
                .get("/users/1")
                .then()
                .extract().response();

        log.info("Basic Auth Status Code : " + basicAuthResponse.getStatusCode());
        Assert.assertEquals(basicAuthResponse.getStatusCode(), 200);


        // -------- Option B: Bearer Token (most common in real APIs) --------
        String dummyToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.dummyToken";

        Response bearerResponse = given()
                .header("Authorization", dummyToken)    // Bearer token style
                .when()
                .get("/users/1")
                .then()
                .extract().response();

        log.info("Bearer Token Status Code : " + bearerResponse.getStatusCode());
        Assert.assertEquals(bearerResponse.getStatusCode(), 200);


        // -------- Option C: Preemptive Basic Auth (sends credentials immediately) --------
        Response preemptiveResponse = given()
                .auth().preemptive().basic("admin", "admin123")
                .when()
                .get("/users/1")
                .then()
                .extract().response();

        log.info("Preemptive Auth Status Code : " + preemptiveResponse.getStatusCode());
        Assert.assertEquals(preemptiveResponse.getStatusCode(), 200);

        log.info("===== AUTHENTICATION TEST COMPLETED =====");
    }
}