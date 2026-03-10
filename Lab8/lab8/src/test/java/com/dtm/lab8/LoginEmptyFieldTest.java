package com.dtm.lab8;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.*;

public class LoginEmptyFieldTest {

        WebDriver driver;

        @BeforeMethod
        public void setup() {

                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();

                driver.manage().window().maximize();
                driver.get("https://www.saucedemo.com");
        }

        @AfterMethod
        public void tearDown() {

                if (driver != null) {
                        driver.quit();
                }
        }

        @Test
        public void testEmptyLogin() {

                driver.findElement(By.id("login-button")).click();

                String error = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

                Assert.assertTrue(error.contains("Username is required"));
        }
}