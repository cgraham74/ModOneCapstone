package com.techelevator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class InventoryTest extends TestCase {
    Inventory testInventory = new Inventory();
    //Product testProduct;
    private String filePath = "vendingmachine.csv";
    @Test
    public void testCreateInventoryObject1() {
        testInventory.createInventory(filePath);
        List<Product> list = testInventory.getVendingProducts();
        Assert.assertEquals("A1 Potato Crisps 3.05 Chip 5",list.get(0).getProductId()+" "
                +list.get(0).getProductName()+ " "+ list.get(0).getProductPrice()+ " " + list.get(0).getProductType()+ " " + list.get(0).getProductCount());

    }

    @Test
    public void testCreateInventoryObject2() {
        testInventory.createInventory(filePath);
        List<Product> list = testInventory.getVendingProducts();
        Assert.assertEquals("A2 Stackers 1.45 Chip 5",list.get(1).getProductId()+" "
                +list.get(1).getProductName()+ " "+ list.get(1).getProductPrice()+ " " + list.get(1).getProductType()+ " " + list.get(1).getProductCount());

    }

    @Test
    public void testDisplayAvailableProducts() {

    }

    @Test
    public void testDisplayItemCountSoldOut() {
        Assert.assertEquals("SOLD OUT", testInventory.displayItemCount(0));
    }

    @Test
    public void testDisplayItemCountWithValue() {
        Assert.assertEquals("(3)", testInventory.displayItemCount(3));
    }
}