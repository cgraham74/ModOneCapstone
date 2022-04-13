package com.techelevator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;

public class SecurityLog extends GenerateTime {

    public void log(String loggedText, double balance, double endingBalance) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        try (PrintWriter securityOutput = new PrintWriter(new FileOutputStream("log\\security.log", true))) {
            securityOutput.print(logTime() + " " + loggedText + " " + format.format(balance) + " " + format.format(endingBalance) + "\n");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}
