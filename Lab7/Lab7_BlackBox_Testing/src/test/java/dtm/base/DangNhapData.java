package dtm.base;

import org.testng.annotations.DataProvider;

public class DangNhapData {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
                { "standard_user", "secret_sauce" },
                { "locked_out_user", "secret_sauce" },
                { "invalid", "invalid" }
        };
    }
}