package org.appium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class FooterLinksTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        System.out.println("✅ WebDriver initialized for FooterLinksTest.");
    }

    @Test
    public void verifyFooterLinksExist() {
        driver.get("https://fyi.ai");

        List<WebElement> links = driver.findElements(By.tagName("a"));

        Assert.assertTrue(
                links.size() > 0,
                "❌ No links found on the page."
        );

        int validLinks = 0;

        for (WebElement link : links) {
            String href = link.getAttribute("href");
            String text = link.getText();

            if (href != null && !href.trim().isEmpty()) {
                validLinks++;
                System.out.println("🔗 Link found: " + text + " → " + href);
            }
        }

        Assert.assertTrue(
                validLinks > 0,
                "❌ No valid links with href values found."
        );

        System.out.println("✅ Footer/link validation test passed. Valid links found: " + validLinks);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ WebDriver closed for FooterLinksTest.");
        }
    }
}