package com.techelevator.menu_process;

import com.techelevator.Product;
import com.techelevator.util.VendoLog;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PurchaseItem {

    private String codeBought;

    public PurchaseItem() {

        this.codeBought = " ";
    }

    public String getCodeBought() {

        return this.codeBought;
    }

    public void setCodeBought(String codeBought) {

        this.codeBought = codeBought;
    }

    public boolean checkAvailability(Map<String, Product> items) {

        String keyCode = this.codeBought.toUpperCase();
        if (items.containsKey(keyCode)) {
            if (items.get(keyCode).getQuantity() > 0) {
                return true;
            } else {
                System.out.println("*** Item code " + "\"" +
                        this.codeBought.toUpperCase() + "\"" +
                        " already SOLD OUT.");
                return false;
            }
        } else {
            System.out.println("*** Item code " + "\"" +
                    this.codeBought + "\"" +
                    " invalid. Pls. enter valid code.");
            return false;
        }
    }

    public boolean isValidPurchase(Map<String, Product> items) {

        String keyCode = this.codeBought.toUpperCase();
        if (items.containsKey(keyCode)) {
            if ((items.get(keyCode).getPrice().compareTo(RunningBalance.getCurrBalance()) == -1) ||
                (items.get(keyCode).getPrice().compareTo(RunningBalance.getCurrBalance()) == 0)) {
                return true;
            }
        }

        System.out.println("*** Not enough balance. Pls. provide money.");
        return false;
    }

    public boolean updateInventoryAndBalance(Map<String, Product> items) {

        //format currency
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        //for log date formatting
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        String logPath = "logs/vendolog" + "_" + formattedDate + ".log";

        String oldBalStr;
        String currBalStr;
        String keycode = this.codeBought.toUpperCase();

        if (checkAvailability(items) && isValidPurchase(items)) {
            int qty = items.get(keycode).getQuantity();
            //update quantity
            items.get(keycode).decreaseQuantity();
            //update balance
            RunningBalance.subtractToBalance(items.get(keycode).getPrice());
            oldBalStr = currencyFormat.format(RunningBalance.getOldBalance());
            currBalStr = currencyFormat.format(RunningBalance.getCurrBalance());
            //display in console
            if (items.containsKey(keycode)) {
                System.out.println("*** " + items.get(keycode).getSound());
            }
            String forLog = items.get(keycode).getName() + " " + items.get(keycode).getSlot();
            VendoLog.log(forLog, oldBalStr, currBalStr, logPath);
            return true;
        } else {
            return false;
        }
    }
}
