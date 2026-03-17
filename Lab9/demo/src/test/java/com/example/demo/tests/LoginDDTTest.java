package com.example.demo.tests;

import framework.base.BaseTest;
import framework.pages.*;
import framework.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginDDTTest extends BaseTest {
    @DataProvider(name = "excelLoginData")
    public Object[][] getLoginData() {
        String path = "src/test/resources/testdata/login_data.xlsx";
        return ExcelReader.getData(path, "SmokeCases"); // đổi sheet khi chạy regression
    }

    @Test(dataProvider = "excelLoginData")
    public void testLoginFromExcel(String username, String password, String expected, String description) {
        LoginPage lp = new LoginPage(getDriver());
        if (expected.contains("inventory")) {
            InventoryPage inv = lp.login(username, password);
            Assert.assertTrue(inv.isLoaded(), description);
        } else {
            LoginPage failed = lp.loginExpectingFailure(username, password);
            Assert.assertTrue(failed.isErrorDisplayed(), description);
        }
    }
}