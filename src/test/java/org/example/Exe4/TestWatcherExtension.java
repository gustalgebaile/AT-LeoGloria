package org.example.Exe4;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestWatcherExtension implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Object testInstance = context.getRequiredTestInstance();
        WebDriver driver = extractDriver(testInstance);

        if (driver != null) {
            try {
                driver.getWindowHandle();
                takeScreenshot(context, driver);
            } catch (Exception e) {
                System.err.println("Não foi possível capturar screenshot: " + e.getMessage());
            }
        }
    }

    private WebDriver extractDriver(Object testInstance) {
        if (testInstance instanceof LoginTest) {
            return ((LoginTest) testInstance).getDriver();
        } else if (testInstance instanceof UserRegistrationTest) {
            return ((UserRegistrationTest) testInstance).getDriver();
        }
        return null;
    }

    private void takeScreenshot(ExtensionContext context, WebDriver driver) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotsDir = "src/test/resources/screenshots/";
            Files.createDirectories(Paths.get(screenshotsDir));
            String filename = screenshotsDir + context.getDisplayName() + "_" + System.currentTimeMillis() + ".png";
            Files.copy(srcFile.toPath(), Paths.get(filename));
            System.out.println("Screenshot salva em: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
