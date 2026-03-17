package com.example.demo.tests;

import framework.base.BaseTest;
import framework.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {
    @Test
    public void testAddAndRemoveItem() {
        InventoryPage inv = new LoginPage(getDriver())
                .login(ConfigReader.getInstance().getUsername(), ConfigReader.getInstance().getPassword());
        CartPage cart = inv.addFirstItemToCart().goToCart();
        Assert.assertEquals(cart.getItemCount(), 1);
        cart.removeFirstItem();
        Assert.assertEquals(cart.getItemCount(), 0);
    }
}