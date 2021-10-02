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

}
