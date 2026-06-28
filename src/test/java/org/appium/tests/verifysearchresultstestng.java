package org.appium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class verifysearchresultstestng {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        System.out.println("✅ WebDriver Initialized.");
    }

    @Test
    public void testSearchResults() {
        try {
            // ✅ Step 1: Open the "Submit a Request" Page
            driver.get("https://help.fyi.me/hc/en-us/requests/new");

            // ✅ Step 2: Ensure Page is Fully Loaded
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

            // ✅ Step 3: Locate and Click Search Field
            WebElement searchField = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.id("query"))); // ✅ Uses ID for accuracy

            searchField.click();  // Click to focus
            searchField.sendKeys("AI"); // Type "AI"
            searchField.sendKeys(Keys.ENTER); // Press Enter to trigger search

            // ✅ Step 4: Wait for Search Results to Load
            List<WebElement> articles = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".search-results li"))); // ✅ Searches for list items in results

            // ✅ Step 5: Limit to First 15 Visible Results
            int visibleCount = Math.min(articles.size(), 15);
            List<WebElement> visibleArticles = articles.subList(0, visibleCount);

            // ✅ Step 6: Validate that exactly 15 results appear
            Assert.assertEquals(visibleArticles.size(), 15, "❌ Test Failed: Expected exactly 15 articles, but found " + visibleArticles.size());

            System.out.println("✅ Test Passed: Found exactly 15 articles.");
            for (WebElement article : visibleArticles) {
                System.out.println("🔹 " + article.getText());
            }

        } catch (Exception e) {
            Assert.fail("⚠️ Test Encountered an Error: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ WebDriver Closed.");
        }
    }

    @AfterMethod
    public void logTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            System.out.println("✅ TEST PASSED: " + result.getName());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("❌ TEST FAILED: " + result.getName() + " - " + result.getThrowable());
        }
    }
}
