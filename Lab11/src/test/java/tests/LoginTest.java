package tests;

import factory.DriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ConfigReader;

@Epic("Authentication")
@Feature("Login Feature")
public class LoginTest {

    @Test(description = "Test login với Allure report", groups = { "smoke" })
    @Severity(SeverityLevel.CRITICAL)
    @Parameters("browser")
    public void testLogin(@Optional("chrome") String browser) {

        WebDriver driver = DriverFactory.createDriver(browser);

        stepOpenWebsite(driver);
        stepLogin(driver);
        stepVerify(driver);

        attachScreenshot(driver);

        driver.quit();
    }

    @Step("Mở trang đăng nhập")
    public void stepOpenWebsite(WebDriver driver) {
        driver.get("https://www.saucedemo.com");
    }

    @Step("Nhập username/password")
    public void stepLogin(WebDriver driver) {

        String username = ConfigReader.getUsername();
        String password = ConfigReader.getPassword();

        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    @Step("Kiểm tra login thành công")
    public void stepVerify(WebDriver driver) {
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}