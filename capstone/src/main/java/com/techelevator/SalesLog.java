package com.techelevator;

import javax.swing.text.NumberFormatter;
import java.io.*;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SalesLog extends GenerateLog {

        private Map<String, Integer> salesMap = new HashMap<>();
        private NumberFormat formatter = NumberFormat.getCurrencyInstance();
        private double totalGrossSales;

        public SalesLog() {
        }

        public void readSalesLog() {
            File file = new File("sales.log");
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
            try (PrintWriter writer = new PrintWriter("sales.log")) {
                writer.print("");
            } catch (FileNotFoundException e) {
                System.out.println(e);

            }

        }


        public void log(Map<String, Integer> currentSales, Inventory inventory)  {
            readSalesLog();
            currentSales.forEach((key, value) -> salesMap.merge(key, value, Integer :: sum));
            try (PrintWriter salesOutput = new PrintWriter(new FileOutputStream("sales.log", true))){
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
            } catch (FileNotFoundException e){
                System.out.println(e);

            }
        }
}