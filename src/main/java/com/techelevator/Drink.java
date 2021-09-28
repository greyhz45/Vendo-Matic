package com.techelevator;

import java.math.BigDecimal;

public class Drink extends com.techelevator.Product {

    public Drink(String slot, String name, BigDecimal price, int quantity) {

        super(slot, name, price, quantity);
        super.setSound("Glug glug yum!");
    }
}