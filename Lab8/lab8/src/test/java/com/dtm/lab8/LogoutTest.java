package com.dtm.lab8;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.*;

public class LogoutTest {

        WebDriver driver;

        @BeforeMethod
        public void setup() {

                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();

                driver.manage().window().maximize();
                driver.get("https://www.saucedemo.com");

                driver.findElement(By.id("user-name")).sendKeys("standard_user");
                driver.findElement(By.id("password")).sendKeys("secret_sauce");
                driver.findElement(By.id("login-button")).click();
        }

        @AfterMethod
        public void tearDown() {

                if (driver != null) {
                        driver.quit();
                }
        }

        @Test
        public void testLogout() {

                driver.findElement(By.id("react-burger-menu-btn")).click();
                driver.findElement(By.id("logout_sidebar_link")).click();

                String url = driver.getCurrentUrl();

                Assert.assertEquals(url, "https://www.saucedemo.com/");
        }
}