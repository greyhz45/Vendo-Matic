package com.techelevator.inventory;

public class InputDetails {
    private String slotIdentifier;
    private String name;
    private double price;
    private String category;

    public InputDetails(String slotIdentifier, String name, double price, String category) {
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

    public double getPrice() {
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

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
