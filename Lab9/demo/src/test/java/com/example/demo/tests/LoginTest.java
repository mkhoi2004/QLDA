package com.example.demo.tests;

import framework.base.BaseTest;
import framework.pages.*;
import framework.config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inv = loginPage.login(ConfigReader.getInstance().getUsername(),
                ConfigReader.getInstance().getPassword());
        Assert.assertTrue(inv.isLoaded());
    }

    @Test
    public void testLoginWithFluent() {
        new LoginPage(getDriver())
                .login(ConfigReader.getInstance().getUsername(), ConfigReader.getInstance().getPassword())
                .addFirstItemToCart()
                .goToCart()
                .removeFirstItem();
        Assert.assertTrue(true); // pass
    }
}