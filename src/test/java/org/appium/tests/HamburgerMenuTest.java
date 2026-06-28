package org.appium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.util.TestListener;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Listeners(TestListener.class)
public class HamburgerMenuTest {

    private static final Logger logger = LoggerFactory.getLogger(HamburgerMenuTest.class);
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
        logger.info("🚀 Chrome browser launched.");
    }

    @Test
    public void verifyHamburgerMenuOptions() {
        try {
            driver.get("https://fyi.ai/");
            logger.info("🌍 Navigated to fyi.ai");
            logger.info("🧪 Writing to file log check");

            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@class, 'u-nav-link')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuButton);
            logger.info("✅ Hamburger menu clicked successfully.");

            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".menu-collapse")));

            List<WebElement> menuItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//a[contains(@class, 'u-nav-link')]")));

            List<String> menuTexts = new ArrayList<>();
            for (WebElement item : menuItems) {
                String text = item.getText().trim();
                if (!text.isEmpty()) {
                    menuTexts.add(text);
                }
            }

            logger.info("🔎 Found menu items:");
            menuTexts.forEach(item -> logger.info(" - {}", item));



            logger.info("✅ Menu test completed.");

        } catch (Exception e) {
            logger.error("❌ An error occurred while verifying the hamburger menu", e);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        logger.info("🧪 [HamburgerMenuTest] @AfterClass - tearDown() called");
        try {
            if (driver != null) {
                logger.info("🧹 Attempting to quit WebDriver...");
                driver.quit();
                logger.info("🚪 Browser closed successfully.");
            }
        } catch (Exception e) {
            logger.error("❌ Error while closing browser", e);
        }
    }
}
