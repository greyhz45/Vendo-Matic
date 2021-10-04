package com.techelevator.util;

import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class VendoLogTest2 {

    //this testing was separated because when run button for
    //for the class was pressed, no data was written for this file.
    
    @Test
    public void methodLog_createNewLogFile() {

        LocalDateTime time = LocalDateTime.now();
        String timestamp = DateTimeFormatter.ofPattern("MMddyyyy_hhmmss").format(time);
        String newPathName = "src/test/java/com/techelevator/logs/vendolog_" + timestamp + ".log";

        File file = new File(newPathName);

        VendoLog vendo = new VendoLog();
        vendo.log("Stackers A2", "$2.00", "$0.55", newPathName);

        assertTrue(file.exists());
    }
}
