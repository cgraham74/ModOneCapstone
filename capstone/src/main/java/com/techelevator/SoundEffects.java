package com.techelevator;

public class SoundEffects {

    private Product product;

    public SoundEffects(Product product) {
        this.product = product;
    }


    @Override
    public String toString() {
        if (product.getProductType().equalsIgnoreCase("chip")) {

            return  "Crunch Crunch, Yum!";
        }
        if (product.getProductType().equalsIgnoreCase("candy")) {
            return "Munch Munch, Yum!";
        }
        if (product.getProductType().equalsIgnoreCase("drink")) {
            return "Glug Glug, Yum!";
        }
        return "Chew Chew, Yum!";
    }
}