package org.appium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

class VerifyCopyrightText {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the Terms of Service page
            driver.get("https://www.fyi.ai/terms");

            // Explicit wait for the copyright text to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement copyrightElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), '©2024 FYI.FYI, Inc.')]"))
            );

            // Validate the text
            String expectedText = "©2024 FYI.FYI, Inc.";
            String actualText = copyrightElement.getText();

            if (actualText.contains(expectedText)) {
                System.out.println("Test Passed: Copyright text is displayed correctly.");
            } else {
                System.out.println("Test Failed: Expected '" + expectedText + "', but found '" + actualText + "'.");
            }
        } catch (Exception e) {
            System.out.println("Test Encountered an Error: " + e.getMessage());
        } finally {

            driver.quit();
        }
    }
}
