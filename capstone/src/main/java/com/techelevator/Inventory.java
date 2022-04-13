package com.techelevator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    Product products;

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
                double price = Double.parseDouble(items[2]);
                products = new Product(items[0], items[1], price, items[3]);
                vendingProducts.add(products);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void displayAvailableProducts() {

        for (Product product : vendingProducts) {
            System.out.println(product.toString() + " " + displayItemCount(product.getProductCount()));

        }
    }

    public String displayItemCount(int amount) {
        if (amount == 0) {
            return "SOLD OUT";
        } else {
            return "(" + amount + ")";
        }
    }
}
