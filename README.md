# RestAssured API Automation Framework (Java + TestNG)

A beginner-friendly **REST API Test Automation** project built with **Rest Assured**, **TestNG**, and **Maven**.  
It demonstrates real-world API testing practices using the public [JSONPlaceholder](https://jsonplaceholder.typicode.com/) API.

---

```
## 📋 Features

| Feature                        | Description                                              |
|--------------------------------|----------------------------------------------------------|
| **GET Requests**               | Fetch all users and single user                          |
| **POST Requests**              | Create resources with hardcoded data                     |
| **Data-Driven Testing**        | Using TestNG `@DataProvider` (Examples table style)      |
| **Excel Data-Driven**          | Read test data from `.xlsx` file using Apache POI        |
| **DELETE Requests**            | Delete resources                                         |
| **API Chaining**               | Capture response from one API and use it in another      |
| **Path & Query Parameters**    | Demonstrate `.pathParam()` and `.queryParam()`           |
| **Custom Headers**             | Set and verify request/response headers                  |
| **Authentication**             | Basic Auth, Bearer Token, and Preemptive Basic Auth      |
| **Logging**                    | Log4j2 console + file logging                            |
| **Grouping**                   | Smoke & Regression groups via TestNG                     |
| **Extent Reports**             | Beautiful HTML reports                                   |
| **Retry Failed Tests**         | Automatic retry using `IRetryAnalyzer`                   |
| **CI Ready**                   | Easy integration with Jenkins & GitHub                   |

```

---

## 🛠️ Tech Stack
```

- **Language:** Java 11+
- **Build Tool:** Maven
- **API Library:** Rest Assured 5.5.x
- **Test Framework:** TestNG 7.10.x
- **Reporting:** ExtentReports 5.x
- **Excel Support:** Apache POI
- **Logging:** Log4j2
- **IDE:** Eclipse / IntelliJ

```
---

## 📁 Project Structure

```

RestAssuredAPIProject
│
├── pom.xml
├── testng.xml
├── README.md
├── .gitignore
│
├── src
│   └── test
│       ├── java
│       │   ├── base
│       │   │   └── BaseTest.java
│       │   ├── listeners
│       │   │   ├── ExtentReportListener.java
│       │   │   └── RetryAnalyzer.java
│       │   ├── utils
│       │   │   ├── ConfigReader.java
│       │   │   └── ExcelUtils.java
│       │   └── tests
│       │       ├── GetUserTests.java
│       │       ├── PostRequestTests.java
│       │       ├── PostExcelDataTests.java
│       │       ├── DeleteRequestTests.java
│       │       └── AdvancedApiTests.java
│       │
│       └── resources
│           ├── config.properties
│           ├── log4j2.xml
│           └── testdata
│               └── users.xlsx
│
├── reports                  ← Extent reports (generated after run)
└── logs                     ← Log files (generated after run)


```
---

```

## ⚙️ Prerequisites

1. **JDK 11 or higher**
2. **Maven 3.8+**
3. **Eclipse IDE** (or IntelliJ) with TestNG plugin
4. Internet connection (to hit JSONPlaceholder API)


Than cloning the repo prefer creating folder structure in ide & then paste the required code.

```

---

```

Make sure this file exists:
src/test/resources/testdata/users.xlsx
Sheet name: Sheet1

```

---

```

Option 1 – Run full suite (Recommended)
Right-click testng.xml → Run As → TestNG Suite

Option 2 – Run single test class
Right-click any class under tests package → Run As → TestNG Test

Option 3 – Run from terminal
mvn clean test

Option 4 – Run only Smoke tests
In testng.xml, keep only the Smoke test section or use groups.

```

---

Test Coverage Summary

```

Category Test - Class - Count (approx)
GET Requests = GetUserTests = 2
POST (Hardcoded) = PostRequestTests = 1
POST (DataProvider) = PostRequestTests = 3
POST (Excel) = PostExcelDataTests = 3
DELETE = DeleteRequestTests = 1
API Chaining = AdvancedApiTests = 1
Parameters = AdvancedApiTests = 1
Headers = AdvancedApiTests = 1
Authentication = AdvancedApiTests = 1

When both Smoke and Regression sections are enabled in testng.xml, total executed tests ≈ 19 (some tests run in both groups).

```

---

📈 Reports & Logs

```

After execution you will find:

Extent Report = reports/ExtentReport.html
Log file = logs/api-automation.log
TestNG Report = test-output/index.html

Open the Extent Report in any browser for a rich HTML view of pass/fail status, logs, and execution details.

```
---

🔁 Retry Failed Tests

```
Automatic retry is enabled via RetryAnalyzer (retries once on failure).
You can also re-run only failed tests using:

test-output/testng-failed.xml

Right-click → Run As → TestNG Suite
```

---

🔧 Configuration

```
Edit src/test/resources/config.properties:

base.uri=https://jsonplaceholder.typicode.com
```

---

🧪 Sample Test Types Included

```

1.Simple GET – Validate status code and response body
2.POST with body in code
3.POST with DataProvider (similar to Cucumber Examples table)
4.POST with Excel data
5.DELETE
6.API Chaining – GET user → use id & name in POST
7.Path Parameter & Query Parameter
8.Custom Headers
9.Authentication (Basic / Bearer / Preemptive)

```

---

☁️ Jenkins Integration 

```

Create a Freestyle or Pipeline job
Connect your GitHub repository

```

---

📝 Note

```

No Page Object Model (POM) pattern is used – kept simple on purpose.
No Cucumber / BDD – pure TestNG.
JSONPlaceholder is a fake online REST API (perfect for learning). Create/Delete operations return success but data is not permanently stored.
All tests are independent and can be run in any order.

```

---


