package com.newcomb.pizzame.utils;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Locale;


public class HttpUtils {
    private static final String LOG_TAG = HttpUtils.class.getSimpleName();
    private static final String SERVER_FORMAT = "https://query.yahooapis.com/v1/public/yql?q=%s&format=json&diagnostics=true&callback=";
    private static final String QUERY_FORMAT_LAT_LON =
            "select * from local.search where latitude='%3.5f' and longitude='%3.5f' and query='%s'";
    public static void requestNearestBusinesses(Context context,
                                                String businessType,
                                                Location location,
                                                Response.Listener<JSONObject> listener,
                                                Response.ErrorListener errorListener)
    {
        double latitude = getLatitude(location);
        double longitude = getLongitude(location);
        String uriString = buildUriStringFrom(businessType, latitude, longitude);
        VolleyNetworkApi.getInstance(context)
                .doJsonObjectRequest(VolleyNetworkApi.HttpMethod.GET,
                                     uriString,
                                     new JSONObject(),
                                     listener,
                                     errorListener);
    }

    public static double getLatitude(Location loc) { return loc.getLatitude(); }
    public static double getLongitude(Location loc) { return loc.getLongitude(); }

    private static String buildUriStringFrom(String businessType,
                                             double latitude,
                                             double longitude)
    {
        String urlString = String.format(Locale.US,
                   QUERY_FORMAT_LAT_LON,
                   latitude,
                   longitude,
                   businessType);
        try {
            return String.format(SERVER_FORMAT,
                                 URLEncoder.encode(urlString, "UTF-8").replaceAll("\\+", "%20"));
        }
        catch(Exception ex) {
           Log.e(LOG_TAG, "Failed to encode url string. Try without encoding..");
        }
        return String.format(SERVER_FORMAT, urlString);
    }
}
