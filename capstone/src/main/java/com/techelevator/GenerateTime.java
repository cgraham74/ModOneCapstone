package com.techelevator;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenerateTime {

    public void logFolder() {
        File directory = new File("log");
        boolean testDirectory = directory.mkdir();
    }

    public String logTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");
        return currentTime.format(formatter);
    }
}
