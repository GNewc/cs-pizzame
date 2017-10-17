package com.newcomb.pizzame.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class PizzaOption implements Serializable {
    /**
     * {"id":"89211502",
     *  "xmlns":"urn:yahoo:lcl",
     *  "Title":"Pizza Day",
     *  "Address":"10225 Research Blvd, Ste 110",
     *  "City":"Austin",
     *  "State":"TX",
     *  "Phone":"(512) 345-7492",
     *  "Latitude":"30.39537",
     *  "Longitude":"-97.74545",
     *  "Rating":{
     *      "AverageRating":"NaN",
     *      "TotalRatings":"0",
     *      "TotalReviews":"0",
     *      "LastReviewDate":null,
     *      "LastReviewIntro":null
     *  },
     *  "Distance":"0.53",
     *  "Url":"https://local.yahoo.com/info-89211502-pizza-day-austin",
     *  "ClickUrl":"https://local.yahoo.com/info-89211502-pizza-day-austin",
     *  "MapUrl":"https://local.yahoo.com/info-89211502-pizza-day-austin?viewtype=map",
     *  "BusinessUrl":"http://www.pizzadaytx.com/",
     *  "BusinessClickUrl":"http://www.pizzadaytx.com/",
     *  "Categories":{
     *      "Category":{
     *          "id":"96926243",
     *          "content":"Pizza"}
     *      }
     *   }
     */
    private String _name;
    private String _address;
    private String _city;
    private String _state;
    private String _phone;
    private double _distance;
    private double _latitude;
    private double _longitude;

    public PizzaOption(String name,
                       String address,
                       String city,
                       String state,
                       String phone,
                       double distance,
                       double latitude,
                       double longitude)
    {
        _name     = name;
        _address  = address;
        _city     = city;
        _state    = state;
        _phone    = phone;
        _distance = distance;
        _latitude = latitude;
        _longitude = longitude;
    }

    public String getName() { return _name; }
    public String getAddress() { return _address; }
    public String getCity() { return _city; }
    public String getState() { return _state; }
    public String getPhone() { return _phone; }
    public double getDistance() { return _distance; }
    public double getLatitude(){ return _latitude; }
    public double getLongitude() { return _longitude; }

    public static PizzaOption parseFromJson(JSONObject json) throws JSONException {
        return new PizzaOption(json.getString("Title"),
                               json.getString("Address"),
                               json.getString("City"),
                               json.getString("State"),
                               json.getString("Phone"),
                               Double.parseDouble(json.getString("Distance")),
                               Double.parseDouble(json.getString("Latitude")),
                               Double.parseDouble(json.getString("Longitude")));
    }
}
