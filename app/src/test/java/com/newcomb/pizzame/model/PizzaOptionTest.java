package com.newcomb.pizzame.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Example of a basic unit test
 */
public class PizzaOptionTest {
    private PizzaOption TEST_OBJECT;

    @Before
    public void setUp() throws Exception {
        TEST_OBJECT = new PizzaOption("Pizza Day",
                                      "10225 Research Blvd, Ste 110",
                                      "Austin",
                                      "TX",
                                      "(512) 345-7492",
                                      0.53,
                                      30.39537,
                                      -97.74545);
    }

    @Test
    public void getName() throws Exception {
        Assert.assertEquals("Pizza Day", TEST_OBJECT.getName());
    }

    @Test
    public void getAddress() throws Exception {
        Assert.assertEquals("10225 Research Blvd, Ste 110", TEST_OBJECT.getAddress());
    }

    @Test
    public void getCity() throws Exception {
        Assert.assertEquals("Austin", TEST_OBJECT.getCity());
    }

    @Test
    public void getState() throws Exception {
        Assert.assertEquals("TX", TEST_OBJECT.getState());
    }

    @Test
    public void getPhone() throws Exception {
        Assert.assertEquals("(512) 345-7492", TEST_OBJECT.getPhone());
    }

    @Test
    public void getDistance() throws Exception {
        Assert.assertEquals(0.53, TEST_OBJECT.getDistance(), 0.000001);
    }

    @Test
    public void getLatitude() throws Exception {
        Assert.assertEquals(30.39537, TEST_OBJECT.getLatitude(), 0.000001);
    }

    @Test
    public void getLongitude() throws Exception {
        Assert.assertEquals(-97.74545, TEST_OBJECT.getLongitude(), 0.000001);
    }
}