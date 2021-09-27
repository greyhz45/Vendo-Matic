package com.techelevator.controllers;

import com.techelevator.models.finance.Change;
import com.techelevator.models.VendingMachine;
import com.techelevator.views.Console;
import com.techelevator.views.Menu;

public abstract class Controller
{

    protected VendingMachine vendingMachine;

    public Controller ( VendingMachine vendingMachine)
    {
        this.vendingMachine = vendingMachine;
    }

    public abstract void run ();
}