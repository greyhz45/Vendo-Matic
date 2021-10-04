package com.techelevator.menu_process;

import com.techelevator.Product;
import com.techelevator.inventory.RestockMachine;
import com.techelevator.util.SalesReport;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

import static org.junit.Assert.*;

public class PurchaseItemTest {

    @Test
    public void updateInventoryAndBalance_validCodeTest() {

        RestockMachine restock = new RestockMachine("src/test/vendingmachinetest.csv");
        Map<String, Product> inventory = restock.buildStock();

        //feed money first
        FeedMoney feed = new FeedMoney();
        boolean actual = feed.isValidMoney(new BigDecimal(5.00));

        PurchaseItem codeBought = new PurchaseItem();
        codeBought.setCodeBought("A2");

        boolean success = codeBought.updateInventoryAndBalance(inventory);

        assertTrue("Item not updated.", success);

        //check if balance are correct
        MathContext mc = new MathContext(3);
        BigDecimal oldActual = RunningBalance.getOldBalance();
        BigDecimal currentActual = new BigDecimal(String.valueOf(RunningBalance.getCurrBalance()), mc);

        assertEquals("Old Balance not correct.", new BigDecimal(5.00), oldActual);
        assertEquals("Current Balance not correct.", new BigDecimal(3.55, mc), currentActual);
    }

    @Test
    public void updateInventoryAndBalance_invalidCodeTest() {

        RestockMachine restock = new RestockMachine("src/test/vendingmachinetest.csv");
        Map<String, Product> inventory = restock.buildStock();

        //feed money first
        FeedMoney feed = new FeedMoney();
        boolean actual = feed.isValidMoney(new BigDecimal(5.00));

        PurchaseItem codeBought = new PurchaseItem();
        codeBought.setCodeBought("Z2");

        boolean result = codeBought.updateInventoryAndBalance(inventory);

        assertFalse("Result should be false.", result);

        //check if balance are correct
        MathContext mc = new MathContext(3);
        BigDecimal oldActual = RunningBalance.getOldBalance();
        BigDecimal currentActual = new BigDecimal(String.valueOf(RunningBalance.getCurrBalance()), mc);

        assertEquals("Old Balance not correct.", new BigDecimal(0.00), oldActual);
        assertEquals("Current Balance not correct.", new BigDecimal(5.00, mc), currentActual);
    }
}