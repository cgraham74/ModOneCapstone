package com.techelevator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;

public class SecurityLog extends GenerateLogTime {

    private String securityLogPath = "log\\security.log";

    public void log(String loggedText, double balance, double endingBalance) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        try (PrintWriter securityOutput = new PrintWriter(new FileOutputStream(securityLogPath, true))) {
            securityOutput.print(logTime() + " -- " + loggedText + " -- BALANCE (" + format.format(balance) +
                    ") -> ENDING BALANCE (" + format.format(endingBalance) + ")\n");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}