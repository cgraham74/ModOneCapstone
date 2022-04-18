package com.techelevator;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    Product products;
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private List<Product> vendingProducts = new ArrayList<>();

    public Inventory() {
    }


    public List<Product> getVendingProducts() {
        return vendingProducts;
    }

    public void createInventory(String path) {

        try (BufferedReader br = new BufferedReader
                (new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] items = line.split("\\|");
                products = new Product(items[0], items[1], Double.parseDouble(items[2]), items[3]);
                vendingProducts.add(products);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void displayAvailableProducts() {
        System.out.printf("%-3s%-20s%-8s%-8s%s", "ID", "NAME", "PRICE", "TYPE", "AMOUNT\n");
        for (Product product : vendingProducts) {
            System.out.printf("%-3s%-20s%-8s%-8s(%s)\n", product.getProductId(),
                    product.getProductName(), formatter.format(product.getProductPrice()),
                    product.getProductType(),displayItemCount(product.getProductCount()));
        }
    }

    public String displayItemCount(int amount) {
        if (amount == 0) {
            return "SOLD OUT";
        } else {
            return "" + amount;
        }
    }
}