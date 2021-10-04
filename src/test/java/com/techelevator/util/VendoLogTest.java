package com.techelevator.util;

import org.junit.Test;

import java.io.File;
import static org.junit.Assert.*;

public class VendoLogTest {

    @Test
    public void methodLog_insertTransaction() {

        String pathName = "src/test/java/com/techelevator/logs/vendolog_10022021.log";
        File file = new File(pathName);
        long oldLength = file.length();

        VendoLog vendo = new VendoLog();
        vendo.log("FEED MONEY", "$2.00", "$5.00", pathName);

        long newLength = file.length();
        assertNotEquals("File length shouldn't be the same.", oldLength, newLength);
    }
}
