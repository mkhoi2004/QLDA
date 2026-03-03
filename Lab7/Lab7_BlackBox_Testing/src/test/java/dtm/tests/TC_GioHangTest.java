package dtm.tests;

import dtm.base.BaseTest;
import dtm.base.GioHangData;
import dtm.pages.*;
import org.testng.annotations.Test;

public class TC_GioHangTest extends BaseTest {

    @Test(dataProvider = "cartData", dataProviderClass = GioHangData.class)
    public void testAddToCart(String user, String pass, String product) {

        LoginPage login = new LoginPage(driver);
        login.login(user, pass);

        InventoryPage inventory = new InventoryPage(driver);
        inventory.addToCart(product);
        inventory.goToCart();
    }
}