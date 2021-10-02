package com.techelevator.menu_process;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class FeedMoneyTest {

    @Test
    public void isValidMoney_validInput() {

        FeedMoney feed = new FeedMoney();
        boolean actual;

        actual = feed.isValidMoney(new BigDecimal(5.00));

        actual = feed.isValidMoney(new BigDecimal(10));

        BigDecimal currBalance = RunningBalance.getCurrBalance();
        BigDecimal oldBalance = RunningBalance.getOldBalance();

        assertEquals("Invalid money entered", true, actual);
        assertEquals(new BigDecimal(15.00), currBalance);
        assertEquals(new BigDecimal(5.00), oldBalance);
    }

    @Test
    public void isValidMoney_invalidInput() {

        FeedMoney feed = new FeedMoney();
        boolean actual = feed.isValidMoney(new BigDecimal(7.00));

        assertEquals("Should return false since invalid money was entered.", false, actual);
    }
}
