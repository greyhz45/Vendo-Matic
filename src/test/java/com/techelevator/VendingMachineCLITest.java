package com.techelevator;

import com.techelevator.view.Menu;
import org.junit.Test;

public class VendingMachineCLITest {

    @Test
    public void displayItems() {

        Menu menu = new Menu(System.in, System.out, " ");
        Menu purchaseMenu = new Menu(System.in, System.out, "purchase");
        VendingMachineCLI vendo = new VendingMachineCLI(menu, purchaseMenu);

        vendo.run();
    }
}
