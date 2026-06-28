package org.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static void captureScreenshot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            File screenshotsDir = new File("screenshots");
            if (!screenshotsDir.exists()) {
                if (!screenshotsDir.mkdirs()) {
                    System.err.println("❌ Failed to create screenshots directory!");
                    return; // Exit early if we can't create the folder
                }
            }

            File dest = new File(screenshotsDir, testName + "_" + timestamp + ".png");
            FileUtils.copyFile(src, dest);
            System.out.println("📸 Screenshot saved: " + dest.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("❌ Failed to capture screenshot: " + e.getMessage());
        }
    }
}
