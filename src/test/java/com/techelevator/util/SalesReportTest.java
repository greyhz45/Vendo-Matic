package com.techelevator.util;

import com.techelevator.Product;
import com.techelevator.inventory.RestockMachine;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;

public class SalesReportTest {

    //I created a logs folder in the test folder
    //and saved the file to be used for this testing.

    @Test
    public void methodFindFile_validFilename() {

        SalesReport sales = new SalesReport("src/test/java/com/techelevator/logs", "10022021");
        String filename = sales.findFile();

        assertEquals("File not found.", "vendolog_10022021.log", filename);
    }

    @Test
    public void methodFindFile_invalidFilename() {

        SalesReport sales = new SalesReport("src/test/java/com/techelevator/logs", "10022020");
        String filename = sales.findFile();

        assertNotEquals("File shouldn't exists.", "vendolog_10022020.log", filename);
    }

    @Test
    public void methodGenerateReport() {

        SalesReport sales = new SalesReport("src/test/java/com/techelevator/logs", "10022021");
        RestockMachine restock = new RestockMachine("vendingmachine.csv");
        Map<String, Product> inventory = restock.buildStock();

        String filename = sales.generateSalesReport(inventory);

        File file = new File(filename);

        assertTrue("Sales Report was not generated.", file.exists());

    }
}
