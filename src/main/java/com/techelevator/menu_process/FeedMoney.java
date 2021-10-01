package com.techelevator.menu_process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeedMoney {

    List<BigDecimal> validMoney = Arrays.asList(new BigDecimal (1.00), new BigDecimal (2.00), new BigDecimal(5.00),
            new BigDecimal(10.00), new BigDecimal(20.00), new BigDecimal(50.00), new BigDecimal(100.00),
            new BigDecimal(1000.00));

    public FeedMoney() {}

    public boolean isValidMoney(BigDecimal amt) {

        boolean isValidMoney = false;

        for (BigDecimal money: validMoney) {
            if (money.equals(amt)) {
                RunningBalance.addToBalance(amt);
                isValidMoney = true;
                break;
            }
        }

        return isValidMoney;
    }
}
