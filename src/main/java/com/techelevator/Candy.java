package com.techelevator;

import java.math.BigDecimal;

public class Candy extends com.techelevator.Product {

    public Candy(String slot, String name, BigDecimal price, int quantity) {
        super(slot, name, price, quantity);
        super.setSound("Munch, munch yum!");
    }
}