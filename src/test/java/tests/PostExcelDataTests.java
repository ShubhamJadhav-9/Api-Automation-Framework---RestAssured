package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostExcelDataTests extends BaseTest {

    @DataProvider(name = "excelData")
    public Object[][] getExcelData() {
        // Path relative to project root
        return ExcelUtils.getExcelData("testdata/users.xlsx", "Sheet1");
    }

    @Test(dataProvider = "excelData", groups = {"regression", "dataDriven"},
            retryAnalyzer = RetryAnalyzer.class)
    public void createPostFromExcel(String title, String body, String userId) {
        log.info("Excel Data Driven POST - Title: " + title);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", title);
        requestBody.put("body", body);
        requestBody.put("userId", Integer.parseInt(userId.replace(".0", "")));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .extract().response();

        log.info("Status: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("title"), title);
    }
}