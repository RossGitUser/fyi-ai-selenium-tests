package org.appium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org.testng.annotations.AfterClass;

import java.time.Duration;

public class ContactSupportTest {

    private static final Logger logger = LoggerFactory.getLogger(ContactSupportTest.class);
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        logger.info("🧪 [ContactSupportTest] @BeforeClass - setUp() called");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().window().maximize();
        logger.info("🚀 Chrome browser launched.");
    }

    @Test
    public void verifyContactSupportForm() {
        try {
            logger.info("🧪 [ContactSupportTest] @Test - verifyContactSupportForm() starting");
            driver.get("https://help.fyi.me/hc/en-us");
            logger.info("🌐 Navigated to the help page.");

            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
            logger.info("📄 Page load complete.");

            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");
            Thread.sleep(2000);

            WebElement contactSupportButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@aria-label='Contact Support Button']")));
            contactSupportButton.click();
            logger.info("✅ Clicked on Contact Support button.");

            logger.info("⏳ Waiting for form to load at /requests/new...");
            wait.until(ExpectedConditions.urlContains("/hc/en-us/requests/new"));

            WebElement formInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='text']")));

            if (formInput.isDisplayed()) {
                logger.info("✅ Test Passed: Redirected to the correct 'Submit Request' page.");
            } else {
                logger.warn("❌ Test Failed: Form field not visible.");
            }

        } catch (Exception e) {
            logger.error("❌ Test Encountered an Error", e);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        logger.info("🧪 [ContactSupportTest] @AfterClass - tearDown() called");

        if (driver != null) {
            try {
                logger.info("🧹 Attempting to quit WebDriver...");
                driver.quit();
                logger.info("🚪 Browser closed successfully.");
            } catch (Exception e) {
                logger.error("❌ Exception while trying to quit WebDriver", e);
            }
        } else {
            logger.warn("⚠️ WebDriver is null. Skipping browser quit.");
        }
    }
}
