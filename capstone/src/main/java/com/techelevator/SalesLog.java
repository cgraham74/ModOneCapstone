package com.techelevator;

import javax.swing.text.NumberFormatter;
import java.io.*;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class SalesLog extends GenerateLog {

        private Map<String, Integer> salesMap = new HashMap<>();
        private NumberFormat formatter = NumberFormat.getCurrencyInstance();
        private double totalGrossSales;

        public SalesLog() {
        }

        public void readSalesLog() {

            try (BufferedReader br = new BufferedReader
                    (new FileReader("sales.log"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("Total Sales")) {
                        String[] items = line.split(":");
                        totalGrossSales = Double.parseDouble(items[1]);
                    }
                    String[] items = line.split("\\|");
                    int temp = Integer.parseInt(items[1]);
                    salesMap.put(items[0], temp);

                    try (PrintWriter writer = new PrintWriter("sales.log")) {
                        writer.println("");
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }


        public void log(Map<String, Integer> currentSales, Inventory inventory)  {
            readSalesLog();
            currentSales.forEach((key, value) -> salesMap.merge(key, value, Integer :: sum));
            try (PrintWriter salesOutput = new PrintWriter(new FileOutputStream("sales.log", true))){
                double totalSales = 0;
                for (String key : salesMap.keySet()) {
                    salesOutput.println(key + "|" + salesMap.get(key));
                    for (Product i : inventory.getVendingProducts()) {
                        if (i.getProductName().equals(key)) {
                            totalSales += i.getProductPrice() * salesMap.get(key);
                        }
                    }
                }
                salesOutput.println("Total Sales: " + formatter.format(totalSales + totalGrossSales));
            } catch (FileNotFoundException e){
                System.out.println(e);
            }
        }
}