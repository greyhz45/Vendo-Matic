package com.techelevator;

import java.math.BigDecimal;

public class Gum extends Product{

    public Gum(String slot, String name, BigDecimal price, int quantity) {

        super(slot, name, price, quantity);
        super.setSound("Chew, chew yum!");
    }
}