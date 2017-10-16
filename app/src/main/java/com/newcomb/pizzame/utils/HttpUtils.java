package com.newcomb.pizzame.utils;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by Newcomb on 10/16/2017.
 */

public class HttpUtils {
    private static final String BUSINESS_LOCATION_FORMAT_LAT_LON =
            "https://query.yahooapis.com/v1/public/yql?q=select * from local.search where latitude='%f' and longitude='%f' and query='s'&format=json&diagnostics=true&callback=";
    public static void requestNearestBusinesses(Context context,
                                                String businessType,
                                                float latitude,
                                                float longitude,
                                                Response.Listener<JSONObject> listener,
                                                Response.ErrorListener errorListener)
    {
        String uriString = buildUriStringFrom(businessType, latitude, longitude);
        VolleyNetworkApi.getInstance(context)
                .doJsonObjectRequest(VolleyNetworkApi.HttpMethod.GET,
                                     uriString,
                                     new JSONObject(),
                                     listener,
                                     errorListener);
    }

    private static String buildUriStringFrom(String businessType,
                                             float latitude,
                                             float longitude)
    {
        return String.format(Locale.US,
                             BUSINESS_LOCATION_FORMAT_LAT_LON,
                             latitude,
                             longitude,
                             businessType);
    }
}
