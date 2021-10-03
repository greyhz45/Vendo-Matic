package com.techelevator.util;

import com.techelevator.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SalesReport {

    private String folder;
    private String date;

    public SalesReport(String folder, String date) {
        this.folder = folder;
        this.date = date;
    }

    private String findFile() {

        List<String> files = new ArrayList<>();
        File[] filePath = new File(this.folder).listFiles();
        String fileName = "";
        String dateToCompare = "";

        if (filePath.length > 0) {
            for (File file : filePath) {
                if (file.exists() && file.isFile()) {
                    String tempFile = file.getName().toString();
                    dateToCompare = tempFile.substring(tempFile.indexOf("_") + 1, tempFile.indexOf("."));
                    if (dateToCompare.equals(this.date)) {
                        fileName = tempFile;
                        break;
                    }
                }
            }
        }

        return fileName;
    }

    private Map<String, BigDecimal> summarizeSales(Map<String, Product> inventory) {

        String filePath = this.findFile();
        Map<String, BigDecimal> salesSummary = new HashMap<>();
        BigDecimal increment = new BigDecimal(1.00);

        if (!filePath.isEmpty()) {
            File dataFile = new File(this.folder, filePath);
            if (dataFile.exists() && dataFile.isFile()) {
                try (Scanner fileScanner = new Scanner(dataFile)) {
                    while (fileScanner.hasNextLine()) {
                        String[] lineOutput = fileScanner.nextLine().split(" ");
                        for (int x = 0; x < lineOutput.length; x++) {
                            if (inventory.containsKey(lineOutput[x])) {
                                if (!salesSummary.containsKey(lineOutput[x])) {
                                    salesSummary.put(lineOutput[x], new BigDecimal(1.00));
                                } else {
                                    BigDecimal ctr = salesSummary.get(lineOutput[x]).add(increment);
                                    salesSummary.put(lineOutput[x], ctr);
                                }
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found exception: " + dataFile.getAbsolutePath());
                }
            }
        }

        return salesSummary;
    }

    public void generateSalesReport(Map<String, Product> inventory) {

        //date formatter for filename
        LocalDateTime time = LocalDateTime.now();
        String timestamp = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss").format(time);
        String salesPath = this.folder + "/SalesReport_" + timestamp + ".csv";

        //for currency formatting
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String formattedSales;

        final String PIPE = "|";
        BigDecimal totalSales = new BigDecimal(0.00);

        Map<String, BigDecimal> summary = this.summarizeSales(inventory);

        if (!summary.isEmpty()) {
            File salesFile = new File(salesPath);
            PrintWriter lineOut = null;
            boolean skip = false;
            try {
                if (!salesFile.exists()) {
                    salesFile.createNewFile();
                }
                lineOut = new PrintWriter(salesFile);
                //sort map by keys
                TreeMap<String, Product> sortedMap = new TreeMap<>(inventory);
                Iterator itr = sortedMap.keySet().iterator();

                while (itr.hasNext()) {
                    String key = (String) itr.next();
                    Product item = inventory.get(key);

                    if (summary.containsKey(item.getSlot())) {
                        lineOut.println(item.getSlot() +
                                PIPE + item.getName() +
                                PIPE + summary.get(item.getSlot()));
                        BigDecimal bigValue = summary.get(item.getSlot());
                        bigValue = bigValue.multiply(item.getPrice());;
                        totalSales = totalSales.add(bigValue);
                    } else {
                        lineOut.println(item.getSlot() +
                                PIPE + item.getName() +
                                PIPE + 0);
                    }
                }
            } catch (IOException e) {
                System.out.println("Input/Output Exception error: " + salesFile.getAbsolutePath());
                skip = true;
            } finally {
                if (!skip) {
                    formattedSales = currencyFormat.format(totalSales);
                    lineOut.println("**TOTAL SALES** " + formattedSales);
                    lineOut.close();
                    System.out.println("*** " + salesPath + " generated.");
                }
            }
        }
    }
}