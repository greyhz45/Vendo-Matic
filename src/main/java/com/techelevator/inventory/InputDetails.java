package com.techelevator.inventory;

import java.math.BigDecimal;

public class InputDetails {
    private String slotIdentifier;
    private String name;
    private BigDecimal price;
    private String category;

    public InputDetails(String slotIdentifier, String name, BigDecimal price, String category) {
        this.slotIdentifier = slotIdentifier;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getSlotIdentifier() {
        return slotIdentifier;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setSlotIdentifier(String slotIdentifier) {
        this.slotIdentifier = slotIdentifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
