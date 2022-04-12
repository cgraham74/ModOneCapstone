package com.techelevator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class ProductTest extends TestCase {
    Product testProduct = new Product("A2", "Cloud Popcorn", 3.67,"chip");

    @Test
    public void test_product_constructor(){
        boolean testingProductConstructor = testProduct.toString().equals("A2 Cloud Popcorn $3.67 chip");
        Assert.assertTrue("This is true", testingProductConstructor);
    }
}