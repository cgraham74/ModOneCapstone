package com.techelevator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenerateLogTime {


    public void logFolder(Path path) {
        if (!(Files.exists(path))) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public String logTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");
        return currentTime.format(formatter);
    }
}