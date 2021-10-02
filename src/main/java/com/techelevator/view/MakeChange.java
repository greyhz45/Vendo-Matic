package com.techelevator.view;

import com.techelevator.menu_process.RunningBalance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MakeChange {

    private List<Integer> changeList = new ArrayList<Integer>();

    public List<Integer> makeChange(BigDecimal cartBalance) {

        double[] changeValues = {0.25, 0.10, 0.05};


        for (int i = 0; i < changeValues.length; i++) {
            if (cartBalance.compareTo(new BigDecimal(0)) == 1) {
                BigDecimal changeValue = new BigDecimal(changeValues[i]).setScale(2, RoundingMode.HALF_UP);
                BigDecimal[] quotientAndRemainder = cartBalance.setScale(2, RoundingMode.HALF_UP).divideAndRemainder(changeValue);
                Integer numberOf = quotientAndRemainder[0].intValue();
                BigDecimal quotient = quotientAndRemainder[1];
                cartBalance = quotient;
                changeList.add(numberOf);


            }
        }
        return changeList;
    }

    public List<String> retrieveCurrencyDenominations() {
        List<String> currencyDenominations = new ArrayList<String>();
        currencyDenominations.add(" Quarter(s)");
        currencyDenominations.add(" Dime(s)");
        currencyDenominations.add(" Nickel(s)");
        return currencyDenominations;
    }









}