package com.example.sweater;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void test() {
        int a =2;
        int b =5;

        Assert.assertEquals(7, a+b);
    }
}
