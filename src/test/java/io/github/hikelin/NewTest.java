package io.github.hikelin;

import org.testng.Assert;
import org.testng.annotations.Test;


public class NewTest {

    @Test
    public void test1() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void test2() {
        Assert.assertEquals(2, 3);
    }

    @Test
    public void test3() {
        Assert.assertEquals("3", "3");
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void divisionWithException() {
        int i = 1 / 0;
        System.out.println("After division the value of i is :"+ i);
    }

    @Test(timeOut = 5000) // time in mulliseconds
    public void testTimeout() throws InterruptedException {
        Thread.sleep(4000);
    }
}
