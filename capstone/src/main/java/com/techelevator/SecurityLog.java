package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.GenericArrayType;
import java.text.NumberFormat;

public class SecurityLog extends GenerateLog {


    public void log(String loggedText, double balance, double endingBalance)  {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        try (PrintWriter securityOutput = new PrintWriter(new FileOutputStream("security.log", true))){
            securityOutput.println(logTime() + " " + loggedText + " " + format.format(balance) + " " + format.format(endingBalance));
        } catch (FileNotFoundException e){
            System.out.println(e);
        }
    }
}
