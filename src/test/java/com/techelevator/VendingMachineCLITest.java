package com.techelevator;

import com.techelevator.view.MakeChange;
import com.techelevator.view.Menu;
import org.junit.Test;

public class VendingMachineCLITest {

    @Test
    public void displayItems() {

        Menu menu = new Menu(System.in, System.out, " ");
        Menu purchaseMenu = new Menu(System.in, System.out, "purchase");
        MakeChange change = new MakeChange();
        Menu mainMenu = new Menu(System.in, System.out, "mainMenu");
        VendingMachineCLI vendo = new VendingMachineCLI(menu, purchaseMenu, change, mainMenu);

        vendo.run();
    }
}
