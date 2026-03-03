package dtm.tests;

import dtm.base.BaseTest;
import dtm.base.DangNhapData;
import dtm.pages.LoginPage;
import org.testng.annotations.Test;

public class TC_DangNhapTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = DangNhapData.class)
    public void testLogin(String user, String pass) {
        LoginPage login = new LoginPage(driver);
        login.login(user, pass);
    }
}