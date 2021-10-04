package com.techelevator.menu_process;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.*;

public class RunningBalanceTest {

    @Test
    public void classRunningBalance_addToBalanceTest() {

        RunningBalance.setOldBalance(new BigDecimal(0.00));
        RunningBalance.setCurrBalance(new BigDecimal(0.00));

        RunningBalance.addToBalance(new BigDecimal(20.00));

        BigDecimal oldActual = RunningBalance.getOldBalance();
        BigDecimal currentActual = RunningBalance.getCurrBalance();

        assertEquals("Old Balance not correct.", new BigDecimal(0.00), oldActual);
        assertEquals("Current Balance not correct.", new BigDecimal(20.00), currentActual);

        //add more amount
        RunningBalance.addToBalance(new BigDecimal(50.00));

        oldActual = RunningBalance.getOldBalance();
        currentActual = RunningBalance.getCurrBalance();

        assertEquals("Old Balance not correct.", new BigDecimal(20.00), oldActual);
        assertEquals("Current Balance not correct.", new BigDecimal(70.00), currentActual);
    }

    @Test
    public void classRunningBalance_subtractToBalanceTest() {

        RunningBalance.setOldBalance(new BigDecimal(0.00));
        RunningBalance.setCurrBalance(new BigDecimal(0.00));
        BigDecimal oldActual = new BigDecimal(0.00);
        BigDecimal currentActual = new BigDecimal(0.00);

        //add to amount
        RunningBalance.addToBalance(new BigDecimal(10.00));
        //subtract to amount
        RunningBalance.subtractToBalance(new BigDecimal(2.00));

        oldActual = RunningBalance.getOldBalance();
        currentActual = RunningBalance.getCurrBalance();

        assertEquals("Old Balance not correct.", new BigDecimal(10.00), oldActual);
        assertEquals("Current Balance not correct.", new BigDecimal(8.00), currentActual);

        //subtract more amount
        RunningBalance.subtractToBalance(new BigDecimal(1.75));

        oldActual = RunningBalance.getOldBalance();
        currentActual = RunningBalance.getCurrBalance();

        assertEquals("Old Balance not correct.", new BigDecimal(8.00), oldActual);
        assertEquals("Current Balance not correct.", new BigDecimal(6.25), currentActual);
    }

    @Test
    public void classRunningBalance_giveChangeTest() {

        RunningBalance.setOldBalance(new BigDecimal(0.00));
        RunningBalance.setCurrBalance(new BigDecimal(30.00));

        BigDecimal oldActual = RunningBalance.getOldBalance();
        BigDecimal currentActual = RunningBalance.getCurrBalance();

        //subtract amounts
        RunningBalance.subtractToBalance(new BigDecimal(0.95));
        RunningBalance.subtractToBalance(new BigDecimal(1.75));
        RunningBalance.subtractToBalance(new BigDecimal(3.50));
        //give change
        MathContext mc = new MathContext(4);
        oldActual = new BigDecimal(String.valueOf(RunningBalance.getCurrBalance()), mc);
        RunningBalance.giveChange();

        currentActual = RunningBalance.getCurrBalance();

        BigDecimal curr = new BigDecimal(String.valueOf(RunningBalance.getOldBalance()), mc);

        assertEquals("Old Balance not correct.", curr, oldActual);
        assertEquals("Current Balance not correct.", new BigDecimal(0.00), currentActual);
    }
}