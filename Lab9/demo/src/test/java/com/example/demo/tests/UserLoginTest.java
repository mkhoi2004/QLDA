package com.example.demo.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.pages.UserData;
import framework.utils.JsonReader;
import framework.utils.TestDataFactory;

public class UserLoginTest extends BaseTest {
    @DataProvider(name = "jsonUsers")
    public Object[][] getJsonData() throws IOException {
        List<UserData> users = JsonReader.readUsers("src/test/resources/testdata/users.json");
        return users.stream()
                .map(u -> new Object[] { u.username, u.password, u.expectSuccess, u.description })
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "jsonUsers")
    public void testLoginFromJson(String user, String pass, boolean expectSuccess, String desc) {
        LoginPage lp = new LoginPage(getDriver());
        if (expectSuccess) {
            InventoryPage inv = lp.login(user, pass);
            Assert.assertTrue(inv.isLoaded(), desc);
        } else {
            LoginPage failed = lp.loginExpectingFailure(user, pass);
            Assert.assertTrue(failed.isErrorDisplayed(), desc);
        }
    }

    @Test
    public void testCheckoutWithFaker() {
        // Login trước
        new LoginPage(getDriver()).login(ConfigReader.getInstance().getUsername(),
                ConfigReader.getInstance().getPassword());
        // Checkout với data ngẫu nhiên
        Map<String, String> data = TestDataFactory.randomCheckoutData();
        boolean complete = new InventoryPage(getDriver())
                .addFirstItemToCart()
                .goToCart()
                .goToCheckout()
                .fillInformation(data.get("firstName"), data.get("lastName"), data.get("postalCode"))
                .finishCheckout()
                .isCheckoutComplete();
        Assert.assertTrue(complete, "Checkout với Faker thành công");
    }
}