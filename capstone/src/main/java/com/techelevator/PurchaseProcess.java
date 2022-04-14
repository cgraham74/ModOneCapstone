package com.techelevator;

import java.text.NumberFormat;
import java.util.*;

public class PurchaseProcess {

    //<editor-fold desc="*DATA MEMBERS*">
    private SecurityLog securityLog = new SecurityLog();
    private String salesLogPath;
    private SalesLog salesLog;
    private double currentMoney;
    private double moneyEntered;
    private final double ONE_DOLLAR_BILL = 1.00;
    private final double TWO_DOLLAR_BILL = 2.00;
    private final double FIVE_DOLLAR_BILL = 5.00;
    private final double TEN_DOLLAR_BILL = 10.00;
    private final int MAX_COUNT = 5;
    private final int QUARTER = 25;
    private final int DIME = 10;
    private final int NICKEL = 5;
    private final int PERCENT = 100;

    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    Map<String, Integer> productSales = new HashMap<>();

    //</editor-fold>
    public Map<String, Integer> getProductSales() {
        return productSales;
    }

    public PurchaseProcess(String salesLogPath) {
        this.salesLogPath = salesLogPath;
        salesLog = new SalesLog(salesLogPath);

    }


    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double currentMoney) {
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
                    if (moneyEntered == ONE_DOLLAR_BILL || moneyEntered == TWO_DOLLAR_BILL ||
                            moneyEntered == FIVE_DOLLAR_BILL || moneyEntered == TEN_DOLLAR_BILL) {
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
                        productSales.put(vp.getProductName(), (MAX_COUNT - vp.getProductCount()));

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
        int quarter = (int) (currentMoney * PERCENT) / QUARTER;
        int dime = (int) ((currentMoney * PERCENT) % QUARTER) / DIME;
        // This is to stop stealing Nickels.
        int nickel = (int) Math.round((((currentMoney * PERCENT) % QUARTER) % DIME) / NICKEL);
        System.out.println("Dispensing Change:\r\nQuarters: " + quarter + "\r\nDimes: " + dime +
                "\r\nNickels: " + nickel);
        currentMoney = 0.00;
        System.out.println("Amount Remaining: " + formatter.format(currentMoney) +
                "\r\nThanks for using the Vendo-Matic 800!");
    }
}