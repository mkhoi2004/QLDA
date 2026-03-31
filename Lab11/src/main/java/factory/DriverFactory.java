package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {

        String gridUrl = System.getProperty("grid.url");

        // 👉 Nếu có grid → dùng remote
        if (gridUrl != null && !gridUrl.isBlank()) {
            return createRemoteDriver(browser, gridUrl);
        }

        // 👉 Nếu không → chạy local (FIX LỖI CỦA BẠN)
        return createLocalDriver(browser);
    }

    // ================= REMOTE =================
    private static WebDriver createRemoteDriver(String browser, String gridUrl) {
        try {
            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox", "--disable-dev-shm-usage");

                return new RemoteWebDriver(new URL(gridUrl), options);
            }

            if (browser.equalsIgnoreCase("firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                return new RemoteWebDriver(new URL(gridUrl), options);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Browser not supported");
    }

    // ================= LOCAL =================
    private static WebDriver createLocalDriver(String browser) {

        boolean isCI = System.getenv("CI") != null;

        if (browser.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();

            if (isCI) {
                options.addArguments("--headless=new");
            }

            return new ChromeDriver(options);
        }

        if (browser.equalsIgnoreCase("firefox")) {

            FirefoxOptions options = new FirefoxOptions();

            return new FirefoxDriver(options);
        }

        throw new RuntimeException("Browser not supported");
    }
}