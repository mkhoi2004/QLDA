package dtm.base;

import org.testng.annotations.DataProvider;

public class GioHangData {

    @DataProvider(name = "cartData")
    public Object[][] cartData() {
        return new Object[][] {
                { "standard_user", "secret_sauce", "Sauce Labs Backpack" },
                { "standard_user", "secret_sauce", "Sauce Labs Bike Light" }
        };
    }
}