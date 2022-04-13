package com.techelevator;

import java.text.NumberFormat;

public class Product {

    //<editor-fold desc="*DATA MEMBERS*">
    private String productId;
    private String productName;
    private double productPrice;
    private String productType;
    private int productCount;
    private final int MAX_INVENTORY_COUNT = 5;
    //</editor-fold>

    //<editor-fold desc="*GETTERS & SETTERS">
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
    //</editor-fold>

    public Product(String productId, String productName, double productPrice, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;
        this.productCount = MAX_INVENTORY_COUNT;

    }


    @Override
    public String toString() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return productId + " " + productName + " "
                + formatter.format(productPrice) + " " + productType;
    }
}
