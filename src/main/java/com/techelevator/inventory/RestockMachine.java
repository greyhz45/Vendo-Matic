package com.techelevator.inventory;

import com.techelevator.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class RestockMachine {

    private List<InputDetails> items;
    private String file;

    public RestockMachine(String file) {
        this.file = file;
        this.items = readInput();
    }

    public List<InputDetails> getItems() {
        return items;
    }

    private List<InputDetails> readInput() {

        //make sure directory exists for logs
        makeDir_Log();

        List<InputDetails> items = new ArrayList<>();
        File dataFile = new File(this.file);

        boolean cont = false;

        try (Scanner dataInput = new Scanner(dataFile)) {
            while (dataInput.hasNextLine()) {
                String[] inputArray = dataInput.nextLine().split("\\|");
                InputDetails inputDetails = new InputDetails(inputArray[0], inputArray[1], new BigDecimal(inputArray[2]), inputArray[3]);
                items.add(inputDetails);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.\n" +
                    "Make sure it is in it's proper folder.\n" +
                    "Please call Vendo-Matic. " +
                    dataFile.getAbsolutePath() +
                    "\n");
        }

        return items;
    }

    public Map<String, Product> buildStock() {

        int MAX_QUANTITY = 5;
        Map<String, Product> displayItems = new HashMap<>();
        List<InputDetails> inputDetails = this.getItems();

        for (InputDetails input: inputDetails) {
            switch (input.getCategory().toUpperCase()) {
                case "CHIP": {
                    Product chip = new Chip(input.getSlotIdentifier(), input.getName(), input.getPrice(), MAX_QUANTITY);
                    displayItems.put(input.getSlotIdentifier(), chip);
                    break;
                }
                case "CANDY": {
                    Product candy = new Candy(input.getSlotIdentifier(), input.getName(), input.getPrice(), MAX_QUANTITY);
                    displayItems.put(input.getSlotIdentifier(), candy);
                    break;
                }
                case "DRINK": {
                    Product drink = new Drink(input.getSlotIdentifier(), input.getName(), input.getPrice(), MAX_QUANTITY);
                    displayItems.put(input.getSlotIdentifier(), drink);
                    break;
                }
                case "GUM": {
                    Product gum = new Gum(input.getSlotIdentifier(), input.getName(), input.getPrice(), MAX_QUANTITY);
                    displayItems.put(input.getSlotIdentifier(), gum);
                }
            }
        }

        return displayItems;
    }

    private static void makeDir_Log() {

        //make sure logs/ folder exists
        String logFolder = "logs";
        File logs = new File(logFolder);

        if (!logs.exists()) {
            logs.mkdir();
        }
    }
}