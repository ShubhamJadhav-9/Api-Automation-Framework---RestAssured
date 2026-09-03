package base;

import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;

public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        String baseUri = ConfigReader.getBaseUri();
        RestAssured.baseURI = baseUri;
        log.info("======= [@BeforeSuite] Base URI set to: " + baseUri + " =======");
    }

    @BeforeClass(alwaysRun = true)
    public void setup() {
        // safety net – in case any class is run alone
        if (RestAssured.baseURI == null || RestAssured.baseURI.isEmpty()) {
            RestAssured.baseURI = ConfigReader.getBaseUri();
        }
        log.info("======= [@BeforeClass] Base URI = " + RestAssured.baseURI + " =======");
    }
}