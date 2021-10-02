package com.techelevator.menu_process;

import com.techelevator.view.MakeChange;

import java.math.BigDecimal;
import java.util.List;

public class RunningBalance {
    private static MakeChange change;

    private static BigDecimal currBalance = new BigDecimal(0.00);
    ;
    private static BigDecimal oldBalance = new BigDecimal(0.00);
    ;

    public RunningBalance() {

    }

    public static BigDecimal getCurrBalance() {

        return currBalance;
    }

    public static BigDecimal getOldBalance() {

        return oldBalance;
    }

    public static void setCurrBalance(BigDecimal currBalance) {
        RunningBalance.currBalance = currBalance;
    }

    public static void setOldBalance(BigDecimal oldBalance) {
        RunningBalance.oldBalance = oldBalance;
    }

    public static void addToBalance(BigDecimal amt) {

        setOldBalance(getCurrBalance());
        setCurrBalance(getCurrBalance().add(amt));
    }

    public static void subtractToBalance(BigDecimal amt) {

        setOldBalance(getCurrBalance());
        setCurrBalance(getCurrBalance().subtract(amt));
    }

    public static void giveChange() {

        setOldBalance(getCurrBalance());
        setCurrBalance(new BigDecimal(0.00));


    }
}
