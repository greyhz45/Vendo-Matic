package com.techelevator.inventory;

public class InventoryDetails {
    private String inventoryName;
    private double inventoryPrice;
    private int inventoryQty;
    private String inventoryCategory;
    private boolean isAvailable;

    public InventoryDetails(String inventoryName, double inventoryPrice, int inventoryQty, String inventoryCategory, boolean isAvailable) {
        this.inventoryName = inventoryName;
        this.inventoryPrice = inventoryPrice;
        this.inventoryQty = inventoryQty;
        this.inventoryCategory = inventoryCategory;
        this.isAvailable = isAvailable;
    }

    public InventoryDetails() {
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public double getInventoryPrice() {
        return inventoryPrice;
    }

    public int getInventoryQty() {
        return inventoryQty;
    }










    public String getInventoryCategory() {
        return inventoryCategory;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public void setInventoryPrice(double inventoryPrice) {
        this.inventoryPrice = inventoryPrice;
    }

    public void setInventoryQty(int inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

    public void setInventoryCategory(String inventoryCategory) {
        this.inventoryCategory = inventoryCategory;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

