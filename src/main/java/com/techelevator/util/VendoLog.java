package com.techelevator.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class VendoLog {

    static PrintWriter logOutput = null;

    public static void log(String tranName, String oldBalance, String newBalance, String date) {

        String logPath = "logs/vendolog" + "_" + date + ".log";
        File logFile = new File(logPath);
        //formatter for audit log
        LocalDateTime time = LocalDateTime.now();
        String timestamp = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a").format(time);

        if (!logFile.exists()) {
            System.out.println("Log File created!");
            try {
                logFile.createNewFile();

            } catch (IOException e) {
                throw new VendoLogException(e.getMessage()); }
        }

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
            throw new VendoLogException(e.getMessage());
        } finally {
            if (tranName.equals("GIVE CHANGE")) {
                logOutput.close();
            } else {
                logOutput.flush();
            }
        }
    }
}