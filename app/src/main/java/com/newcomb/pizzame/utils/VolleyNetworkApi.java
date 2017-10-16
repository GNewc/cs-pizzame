package com.newcomb.pizzame.utils;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Newcomb on 10/16/2017.
 */

public class VolleyNetworkApi {
    enum HttpMethod {
        GET(Request.Method.GET),
        POST(Request.Method.POST),
        PUT(Request.Method.PUT),
        DELETE(Request.Method.DELETE),
        PATCH(Request.Method.PATCH);

        private int _type;
        HttpMethod(int t) { _type = t; }
        public int value() { return _type; }
    }
    private RequestQueue _requestQueue;

    private VolleyNetworkApi(Context context) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        _requestQueue = new RequestQueue(cache, network);
        _requestQueue.start();
    }

    private static VolleyNetworkApi s_instance = null;

    synchronized static VolleyNetworkApi getInstance(Context context) {
        if(s_instance == null)
            s_instance = new VolleyNetworkApi(context.getApplicationContext());
        return s_instance;
    }

    Request<JSONObject> doJsonObjectRequest(HttpMethod methodType,
                                            String url,
                                            JSONObject data,
                                            Response.Listener<JSONObject> listener,
                                            Response.ErrorListener errorListener)
    {
        JsonObjectRequest request = new JsonObjectRequest(methodType.value(),
                                                          url,
                                                          data,
                                                          listener,
                                                          errorListener);
        return _requestQueue.add(request);
    }

    Request<JSONArray> doJsonArrayRequest(HttpMethod methodType,
                                          String url,
                                          JSONArray data,
                                          Response.Listener<JSONArray> listener,
                                          Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(methodType.value(),
                                                        url,
                                                        data,
                                                        listener,
                                                        errorListener);
        return _requestQueue.add(request);
    }
}
