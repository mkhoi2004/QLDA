package dtm;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Random;

public class Utility {

    // ===== WAIT ELEMENT =====
    public static void waitForElementVisible(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForElementClickable(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ===== CLICK =====
    public static void click(WebDriver driver, By locator) {
        waitForElementClickable(driver, locator);
        driver.findElement(locator).click();
    }

    // ===== SEND KEYS =====
    public static void sendKeys(WebDriver driver, By locator, String text) {
        waitForElementVisible(driver, locator);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }

    // ===== GET TEXT =====
    public static String getText(WebDriver driver, By locator) {
        waitForElementVisible(driver, locator);
        return driver.findElement(locator).getText();
    }

    // ===== RANDOM STRING =====
    public static String randomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }

    // ===== SCREENSHOT WHEN FAIL =====
    public static void takeScreenshot(WebDriver driver, String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        try {
            Files.copy(src.toPath(),
                    new File("screenshots/" + fileName + ".png").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}