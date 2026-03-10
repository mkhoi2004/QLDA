package com.dtm.lab8;

public class ShippingCalculator {

    public double tinhPhiShip(double weight, String region) {

        double phi = 0;

        if (weight <= 1) {
            phi = 20000;
        } else if (weight <= 5) {
            phi = 30000;
        } else {
            phi = 50000;
        }

        if (region.equals("remote")) {
            phi = phi + 15000;
        }

        return phi;
    }
}