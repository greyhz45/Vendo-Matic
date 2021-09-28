package com.techelevator;

import java.math.BigDecimal;

public class Chip extends com.techelevator.Product {

    public Chip(String slot, String name, BigDecimal price, int quantity) {

        super(slot, name, price, quantity);
        super.setSound("Crunch, crunch yum!");
    }
}