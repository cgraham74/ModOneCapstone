package com.techelevator;

import java.text.Format;
import java.text.NumberFormat;
import java.util.*;

public class PurchaseProcess {

    private SecurityLog securityLog = new SecurityLog();
    private SalesLog salesLog = new SalesLog();
    private double currentMoney;
    private double moneyEntered;
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    Map<String, Integer> productSales = new HashMap<>();

    public Map<String, Integer> getProductSales() {
        return productSales;
    }

    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public PurchaseProcess() {
        this.currentMoney = currentMoney;

    }

    public double feedMoney() {
        Scanner moneyIn = new Scanner(System.in);
        String pleaseEnter = "Please Enter Whole Dollar Amount: \r\n" +
                "Example: (1), (2), (5), (10)";
        System.out.println(pleaseEnter);

        try {
            if (moneyIn.hasNextDouble()) {
                moneyEntered = moneyIn.nextDouble();
                if (moneyEntered % 1 == 0) {
                    if (moneyEntered == 1 || moneyEntered == 2 || moneyEntered == 5 ||
                    moneyEntered == 10 || moneyEntered == 20 || moneyEntered == 50 ||
                    moneyEntered == 100) {
                        currentMoney += moneyEntered;
                    } else {
                        System.out.println(pleaseEnter);
                    }
                }
                System.out.println("Current Money Provided: " + formatter.format(currentMoney));
            }
            securityLog.log("FEED MONEY", moneyEntered, currentMoney);

        } catch (InputMismatchException e) {
            System.err.println(e);
        }
        return currentMoney;
    }

    public void purchaseItem(Inventory inventory) {
        boolean isMatch = false;

        Scanner productCode = new Scanner(System.in);
        System.out.println("Please enter Product Key!");
        String product = productCode.nextLine();

        for (Product vp : inventory.getVendingProducts()) {
            if (product.equalsIgnoreCase(vp.getProductId())) {
                isMatch = true;
                if (vp.getProductCount() > 0) {
                    if (currentMoney >= vp.getProductPrice()) {
                        double moneyBeforeDecrement = currentMoney;
                        currentMoney -= vp.getProductPrice();
                        System.out.println(vp.getProductName() + " " + formatter.format(vp.getProductPrice()));
                        vp.setProductCount(vp.getProductCount() - 1);
                        SoundEffects soundEffects = new SoundEffects(vp);
                        System.out.println("Balance: " + formatter.format(currentMoney));
                        System.out.println(soundEffects);
                        securityLog.log(vp.getProductName(), moneyBeforeDecrement, currentMoney);
                        productSales.put(vp.getProductName(), (5 - vp.getProductCount()));

                    } else {
                        System.out.println("Insufficient Funds! \nYour current funds are: "
                                + formatter.format(currentMoney));
                    }
                } else {
                    inventory.displayItemCount(vp.getProductCount());
                    if (vp.getProductCount() == 0) {
                        System.out.println("SOLD OUT! Please select something else!");
                    }
                }
            }
        }
        if (!isMatch) {
            System.out.println("Invalid Product Key!");
        }
    }

    public void makeChange(Inventory inventory) {
        securityLog.log("GIVE CHANGE", currentMoney, 0.00);
        salesLog.log(productSales, inventory);
        int quarter = (int)(currentMoney * 100) / 25;
        int dime = (int)((currentMoney * 100) % 25) / 10;
        // This is to stop stealing Nickels.
        int nickel = (int)Math.round((((currentMoney * 100) % 25) % 10) / 5);
        System.out.println("Dispensing Change:\r\nQuarters: " + quarter + "\r\nDimes: " + dime +
                "\r\nNickels: " + nickel);
        currentMoney = 0.00;
        System.out.println("Amount Remaining: " + formatter.format(currentMoney) +
                "\r\nThanks for using the Vendo-Matic 800!");
    }
}

