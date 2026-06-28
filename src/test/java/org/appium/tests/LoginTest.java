package org.appium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("✅ WebDriver initialized for LoginTest.");
    }

    @Test
    public void verifyLoginPageLoads() {
        driver.get("https://fyi.ai");

        WebElement body = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))
        );

        Assert.assertTrue(body.isDisplayed(), "Page body is not displayed.");
        Assert.assertTrue(driver.getCurrentUrl().contains("fyi.ai"), "URL does not contain fyi.ai.");

        System.out.println("✅ Login smoke test passed.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ WebDriver closed for LoginTest.");
        }
    }
}