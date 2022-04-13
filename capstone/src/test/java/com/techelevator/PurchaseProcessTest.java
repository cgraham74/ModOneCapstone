package com.techelevator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class PurchaseProcessTest extends TestCase {

    PurchaseProcess purchaseProcess;
    private ByteArrayOutputStream output;
    private String salesPath = "log\\sales.log";

    @Before
    public void setup() {
        output = new ByteArrayOutputStream();
    }

    public void testGetProductSales() {
    }

    public void testGetCurrentMoney() {
    }

    public void testSetCurrentMoney() {
    }

    @Test
    public void testFeedMoney_floating_point_number_should_be_ZERO() {
        PurchaseProcess purchaseProcess = new PurchaseProcess(salesPath);
        InputStream clearOut = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("7.32".getBytes());
        System.setIn(in);
        purchaseProcess.feedMoney();
        Assert.assertEquals(0.00, purchaseProcess.getCurrentMoney(), 0);
        System.setIn(clearOut);
    }

    @Test
    public void testFeedMoney_Whole_Number_Should_Be_Accepted() {
        PurchaseProcess purchaseProcess = new PurchaseProcess(salesPath);
        InputStream clearOut = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("5.00".getBytes());
        System.setIn(in);
        purchaseProcess.feedMoney();
        Assert.assertEquals(5.00, purchaseProcess.getCurrentMoney(), 0);
        System.setIn(clearOut);
    }

    @Test
    public void testHappyPathPurchaseItem() {
        Inventory testInventory = new Inventory();
        testInventory.createInventory("vendingmachine.csv");
        PurchaseProcess purchaseProcess = new PurchaseProcess(salesPath);
        InputStream clearOut = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("A1".getBytes());
        System.setIn(in);
        purchaseProcess.setCurrentMoney(5.00);
        purchaseProcess.purchaseItem(testInventory);
        Assert.assertEquals(1.95, purchaseProcess.getCurrentMoney(), 0.001);
        System.setIn(clearOut);
    }

    @Test
    public void testSadPathPurchaseItem() {
        Inventory testInventory = new Inventory();
        testInventory.createInventory("vendingmachine.csv");
        PurchaseProcess purchaseProcess = new PurchaseProcess(salesPath);
        InputStream clearOut = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("Z9".getBytes());
        System.setIn(in);
        purchaseProcess.setCurrentMoney(3.05);
        purchaseProcess.purchaseItem(testInventory);
        Assert.assertEquals(3.05, purchaseProcess.getCurrentMoney(), 0);
        System.setIn(clearOut);
    }

    @Test
    public void testSoldOutPurchaseItem() {
        Inventory testInventory = new Inventory();
        testInventory.createInventory("vendingmachine.csv");
        PurchaseProcess purchaseProcess = new PurchaseProcess(salesPath);
        InputStream clearOut = System.in;
        purchaseProcess.setCurrentMoney(7.00);

        for (int i = 0; i < 6; i++) {
            ByteArrayInputStream in = new ByteArrayInputStream("D4".getBytes());
            System.setIn(in);
            purchaseProcess.purchaseItem(testInventory);
        }
        Assert.assertEquals(3.25, purchaseProcess.getCurrentMoney(), 0.001);
        System.setIn(clearOut);
    }

    @Test
    public void testHappySadPathPurchaseItem() {
        Inventory testInventory = new Inventory();
        testInventory.createInventory("vendingmachine.csv");
        PurchaseProcess purchaseProcess = new PurchaseProcess(salesPath);
        InputStream clearOut = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("A1".getBytes());
        System.setIn(in);
        purchaseProcess.setCurrentMoney(1.00);
        purchaseProcess.purchaseItem(testInventory);
        Assert.assertEquals(1.00, purchaseProcess.getCurrentMoney(), 0.001);
        System.setIn(clearOut);
    }

    @Test
    public void testMakeChange() {
        PurchaseProcess testPurchaseProcess = new PurchaseProcess(salesPath);
        final ByteArrayOutputStream testingStreams = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testingStreams));
        Inventory testInventory = new Inventory();
        testPurchaseProcess.setCurrentMoney(1.00);
        testPurchaseProcess.makeChange(testInventory);

        //Throws a number exception and we don't know why it's printing
        assertEquals("Dispensing Change:\r\n" +
                "Quarters: 4\r\nDimes: 0\r\nNickels: 0\r\nAmount Remaining: $0.00\r\n" +
                "Thanks for using the Vendo-Matic 800!", testingStreams.toString().trim());
    }
}