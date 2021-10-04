package com.techelevator;

import com.techelevator.inventory.RestockMachine;
import com.techelevator.view.MakeChange;
import com.techelevator.view.Menu;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Map;

public class VendingMachineCLITest {

    @Test
    public void methodDisplayItems_validFile() {

        Menu menu = new Menu(System.in, System.out, " ");
        Menu purchaseMenu = new Menu(System.in, System.out, "purchase");
        MakeChange change = new MakeChange();
        Menu mainMenu = new Menu(System.in, System.out, "mainMenu");
        VendingMachineCLI vendo = new VendingMachineCLI(menu, purchaseMenu, change, mainMenu);

        //restock vending machine
        RestockMachine restock = new RestockMachine("src/test/vendingmachinetest.csv");
        vendo.restockVendo();
        Map<String, Product> inventory = restock.buildStock();

        vendo.displayItemsToConsole(inventory);
        assertEquals("No. of items incorrect", 13, inventory.size());
    }

    @Test
    public void methodDisplayItems_invalidFile() {

        Menu menu = new Menu(System.in, System.out, " ");
        Menu purchaseMenu = new Menu(System.in, System.out, "purchase");
        MakeChange change = new MakeChange();
        Menu mainMenu = new Menu(System.in, System.out, "mainMenu");
        VendingMachineCLI vendo = new VendingMachineCLI(menu, purchaseMenu, change, mainMenu);

        //restock vending machine
        RestockMachine restock = new RestockMachine("src/test/vendingmachinetest-bk.csv");
        vendo.restockVendo();
        Map<String, Product> inventory = restock.buildStock();

        vendo.displayItemsToConsole(inventory);
        assertEquals("No. of items incorrect", 0, inventory.size());
    }

    //testing for other menus are located in separate Test classes.
    //please refer to src/test folders for reference.
}
