package com.newcomb.pizzame.model;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * JSON belongs in android...
 */

public class PizzaOptionTest {
    private static final String jsonStr   = "{\"id\" : \"89211502\"," +
            "\"xmlns\":\"urn:yahoo:lcl\"," +
            "\"Title\":\"Pizza Day\"," +
            "\"Address\":\"10225 Research Blvd, Ste 110\"," +
            "\"City\":\"Austin\"," +
            "\"State\":\"TX\"," +
            "\"Phone\":\"(512) 345-7492\"," +
            "\"Latitude\":\"30.39537\"," +
            "\"Longitude\":\"-97.74545\"," +
            "\"Rating\":{" +
            "\"AverageRating\":\"NaN\"," +
            "\"TotalRatings\":\"0\"," +
            "\"TotalReviews\":\"0\"," +
            "\"LastReviewDate\":null," +
            "\"LastReviewIntro\":null" +
            "}," +
            "\"Distance\":\"0.53\"," +
            "\"Url\":\"https://local.yahoo.com/info-89211502-pizza-day-austin\"," +
            "\"ClickUrl\":\"https://local.yahoo.com/info-89211502-pizza-day-austin\"," +
            "\"MapUrl\":\"https://local.yahoo.com/info-89211502-pizza-day-austin?viewtype=map\"," +
            "\"BusinessUrl\":\"http://www.pizzadaytx.com/\"," +
            "\"BusinessClickUrl\":\"http://www.pizzadaytx.com/\"," +
            "\"Categories\":{" +
            "    \"Category\":{" +
            "        \"id\":\"96926243\"," +
            "        \"content\":\"Pizza\"}" +
            "    }" +
            " }" +
            "}";
    private JSONObject TEST_JSON;
    private PizzaOption TEST_OBJECT;

    @Before
    public void setUp() throws Exception {
        TEST_JSON = new JSONObject(jsonStr);
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
    public void parseFromJson() throws Exception {
        Assert.assertEquals(TEST_OBJECT, PizzaOption.parseFromJson(TEST_JSON));
    }

}