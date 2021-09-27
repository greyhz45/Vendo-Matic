package com.techelevator.controllers;

import com.techelevator.models.VendingMachine;
import com.techelevator.views.common.InventoryView;
import com.techelevator.views.home.HomeDefaultView;
import com.techelevator.views.menus.MainMenu;

public class HomeController extends Controller
{
    HomeDefaultView homeView;

    public HomeController ( VendingMachine vendingMachine )
    {
        super( vendingMachine );
        homeView = new HomeDefaultView("Home", vendingMachine);
    }

    @Override
    public void run ()
    {
        boolean goOn = true;

        do
        {
            homeView.display();
            String choice = homeView.getSelectedOption();

            switch ( choice )
            {
                case MainMenu.Options.DisplayItems:
                    displayProducts();
                    break;
                case MainMenu.Options.Purchase:
                    purchaseProducts();
                    break;
                case MainMenu.Options.Exit:
                    goOn = false;
                    break;
            }

        } while ( goOn );
    }

    private void displayProducts ()
    {
        InventoryView view = new InventoryView(vendingMachine);
        view.display();
    }

    private void purchaseProducts()
    {
        PurchaseController purchaseController = new PurchaseController( vendingMachine );
        purchaseController.run();
    }
}