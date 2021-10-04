package com.techelevator.inventory;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class RestockMachineTest {

    @Test
    public void classRestockMachine_readInput() {

        RestockMachine restock = new RestockMachine("src/test/vendingmachinetest.csv");
        List<InputDetails> stockList = restock.getItems();

        assertEquals("Extracted list is not the same.", 13, stockList.size());
    }

    @Test
    public void classRestockMachine_readInvalid_file() {

        RestockMachine restock = new RestockMachine("src/test/vendingmachine.csv");
        List<InputDetails> stockList = restock.getItems();

        assertEquals("No data should be read.", 0, stockList.size());
    }

}
