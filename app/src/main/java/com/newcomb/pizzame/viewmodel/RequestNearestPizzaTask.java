package com.newcomb.pizzame.viewmodel;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.newcomb.pizzame.model.PizzaOption;
import com.newcomb.pizzame.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 *  AsyncTask implementation that will find our position and then request
 *  an update to the nearest pizza joints. This will update the ViewModel
 *  for the list of possible places.
 */
public class RequestNearestPizzaTask extends AsyncTask<Object, Void, Boolean>
                                     implements Response.Listener<JSONObject>,
                                                Response.ErrorListener
{
    private static final String LOG_TAG = RequestNearestPizzaTask.class.getSimpleName();
    private WeakReference<PizzaOptionsViewModel> _wrViewModel;

    public RequestNearestPizzaTask(PizzaOptionsViewModel viewModel) {
        _wrViewModel = new WeakReference<>(viewModel);
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        if(params.length<2)
            return false;
        try {
            Context context = (Context) params[0];
            Location location = (Location) params[1];
            if (context != null) {
                HttpUtils.requestNearestBusinesses(context,
                        "pizza",
                        location,
                        this,
                        this);
            }
        }
        catch(Exception ex) {
            Log.e(LOG_TAG, "Deal with bad things", ex);
            return false;
        }
        return true;
    }

    //-------------------------------------------------------------------------
    // ErrorListener implementation
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(LOG_TAG, "Issues fetching data" + error);
    }

    //-------------------------------------------------------------------------
    // Response.Listener<JSONObject> implementation
    @Override
    public void onResponse(JSONObject response) {
        try {
            List<PizzaOption> options = new ArrayList<>();
            JSONArray results = response.getJSONObject("query").getJSONObject("results").getJSONArray("Result");
            for(int i=0; i< results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                PizzaOption po = PizzaOption.parseFromJson(result);
                options.add(po);
            }
            _wrViewModel.get().updateOptions(options);
        }
        catch(Exception ex) {
            Log.e(LOG_TAG, "Issues parsing response");
        }
    }
}