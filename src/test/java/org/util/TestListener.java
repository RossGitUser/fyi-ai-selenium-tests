package org.util;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("❌ Test FAILED: {}", result.getName());

        Object testInstance = result.getInstance();
        WebDriver driver = null;

        try {
            // Reflection to get the driver field from the test class
            driver = (WebDriver) testInstance.getClass().getDeclaredField("driver").get(testInstance);
        } catch (Exception e) {
            logger.error("⚠️ Failed to access WebDriver for screenshot", e);
        }

        if (driver != null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String screenshotPath = "screenshots/" + result.getName() + "_" + timestamp + ".png";
            ScreenshotUtil.captureScreenshot(driver, screenshotPath);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("🧪 Starting test: {}", result.getName());
    }

    @Override public void onTestSuccess(ITestResult result) {
        logger.info("✅ Test PASSED: {}", result.getName());
    }

    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
}
