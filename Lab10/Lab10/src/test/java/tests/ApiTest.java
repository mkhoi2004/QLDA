package tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

// ADD THIS IMPORT
import base.BaseUITest;

public class ApiTest extends BaseUITest {

    @Test
    public void testLoginUI() {

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }
}