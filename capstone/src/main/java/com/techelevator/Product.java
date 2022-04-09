package com.techelevator;

import java.text.NumberFormat;

public class Product {

    private String productId;
    private String productName;
    private double productPrice;
    private String productType;
    private int productCount;

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

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public Product(String productId, String productName, double productPrice, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;
        this.productCount = 5;

    }

    @Override
     public String toString() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return productId + " " + productName + " "
                + formatter.format(productPrice) + " " + productType;
    }
}
