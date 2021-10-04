package com.techelevator.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class VendoLog {

    static PrintWriter logOutput = null;

    public static void log(String tranName, String oldBalance, String newBalance, String logPath) {

        File logFile = new File(logPath);
        //formatter for audit log
        LocalDateTime time = LocalDateTime.now();
        String timestamp = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a").format(time);
        boolean skip = false;

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
                System.out.println("Log File created!");
            } catch (IOException e) {
                System.out.println("Input/Output error encountered.\n" +
                        "Pls. check with Vendo-Matic.\n" +
                        "No log file to write: " +
                        logFile.getAbsolutePath());
                skip = true;
            }
        }

        if (!skip) {
            try {
                if (logOutput == null) {
                    logOutput = new PrintWriter(new FileOutputStream(logFile, true));
                }
                if (tranName.equals("FEED MONEY") || tranName.equals("GIVE CHANGE")) {
                    logOutput.println(timestamp + " " + tranName + ": " + oldBalance + " " + newBalance);
                } else {
                    logOutput.println(timestamp + " " + tranName + " " + oldBalance + " " + newBalance);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found. \n" +
                        "Pls. check with Vendo-Matic.\n" +
                        "No log file to write: " +
                        logFile.getAbsolutePath());
                skip = true;
            } finally {
                logOutput.flush();
            }
        }
    }
}