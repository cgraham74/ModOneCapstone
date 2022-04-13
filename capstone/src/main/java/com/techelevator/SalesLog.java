package com.techelevator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SalesLog extends GenerateTime {

    private Map<String, Integer> salesMap = new HashMap<>();
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private double totalGrossSales;

    public SalesLog() {
    }


    public void generateFileName() {
        try {
            String newSalesDate = new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss'.log'").format(new Date());
            Path originalSales = Paths.get("log\\sales.log");
            Path datesSales = Paths.get("log\\" + newSalesDate);
            Files.copy(originalSales, datesSales);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void readSalesLog() {
        File file = new File("log\\sales.log");
        if (file.exists()) {
            try (Scanner scanIn = new Scanner(file)) {
                while (scanIn.hasNextLine()) {
                    String line = scanIn.nextLine();
                    if (line.contains("Total")) {
                        String[] totalSales = line.split(":");
                        String money = totalSales[1].substring(2);
                        totalGrossSales = Double.parseDouble(money);
                    } else {
                        String[] items = line.split("\\|");
                        int temp = Integer.parseInt(items[1]);
                        salesMap.put(items[0], temp);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            //Creating the file.
            try {
                boolean value = file.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        try (PrintWriter writer = new PrintWriter("log\\sales.log")) {
            writer.print("");
        } catch (FileNotFoundException e) {
            System.out.println(e);

        }

    }

    public void log(Map<String, Integer> currentSales, Inventory inventory) {
        readSalesLog();
        currentSales.forEach((key, value) -> salesMap.merge(key, value, Integer::sum));
        try (PrintWriter salesOutput = new PrintWriter(new FileOutputStream("log\\sales.log", true))) {
            double totalSales = 0;
            for (String key : salesMap.keySet()) {
                salesOutput.print(key + "|" + salesMap.get(key) + "\n");
                for (Product i : inventory.getVendingProducts()) {
                    if (i.getProductName().equals(key)) {
                        totalSales += i.getProductPrice() * salesMap.get(key);
                    }
                }
            }
            salesOutput.print("Total Sales: " + formatter.format(totalSales + totalGrossSales));
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}