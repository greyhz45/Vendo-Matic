package com.techelevator;
import java.math.BigDecimal;
public class Product implements Sellable, Soundable {
    private String slot;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String sound;
    public Product (String slot, String name, BigDecimal price, int quantity){
        this.slot = slot;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public String getSlot() {
        return slot;
    }
    public void setSlot(String slot) {
        this.slot = slot;
    }
    public String getName() {
        return name;
    }
    @Override
    public String getSound() {
        return sound;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void decreaseQuantity() {
        quantity--;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

}