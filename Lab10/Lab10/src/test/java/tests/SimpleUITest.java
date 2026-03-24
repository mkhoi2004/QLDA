
package tests;

import base.BaseUITest;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class SimpleUITest extends BaseUITest {

    @Test
    public void testLogin() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }
}
