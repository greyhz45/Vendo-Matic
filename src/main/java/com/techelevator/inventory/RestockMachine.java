package com.techelevator.inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        List<InputDetails> items = new ArrayList<>();
        File dataFile = new File(this.file);

        try (Scanner dataInput = new Scanner(dataFile)) {
            while (dataInput.hasNextLine()) {
                String[] inputArray = dataInput.nextLine().split("\\|");
                InputDetails inputDetails = new InputDetails(inputArray[0], inputArray[1], Double.parseDouble(inputArray[2]), inputArray[3]);
                /*inputDetails.setSlotIdentifier(inputArray[0]);
                inputDetails.setName(inputArray[1]);*/
                items.add(inputDetails);
            }
        } catch (FileNotFoundException e) {
            throw new RestockMachineException(e.getMessage());
        }

        return items;
    }
}
