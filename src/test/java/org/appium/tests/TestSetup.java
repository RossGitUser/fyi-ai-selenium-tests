package org.appium.tests;  // Ensure this matches your package structure

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestSetup {
    public static void main(String[] args) {
        // Set Chrome options (optional)
        ChromeOptions options = new ChromeOptions();

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver(options);

        // Navigate to a page
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());

        // Close the browser
        driver.quit();
    }
}

