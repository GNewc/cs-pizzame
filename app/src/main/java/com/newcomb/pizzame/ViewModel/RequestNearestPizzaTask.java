package com.newcomb.pizzame.ViewModel;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.newcomb.pizzame.model.PizzaOption;
import com.newcomb.pizzame.utils.HttpUtils;
import com.newcomb.pizzame.utils.PermissionUtils;

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
public class RequestNearestPizzaTask extends AsyncTask<AppCompatActivity, Void, Boolean>
                                     implements Response.Listener<JSONObject>,
                                                Response.ErrorListener
{
    private static final String LOG_TAG = RequestNearestPizzaTask.class.getSimpleName();
    private WeakReference<PizzaOptionsViewModel> _wrViewModel;

    public RequestNearestPizzaTask(PizzaOptionsViewModel viewModel) {
        _wrViewModel = new WeakReference<>(viewModel);
    }

    @Override
    protected Boolean doInBackground(AppCompatActivity... activities) {
        PizzaOptionsViewModel pof = _wrViewModel.get();
        AppCompatActivity activity = activities[0];
        if (pof != null && activity != null) {
            if(ContextCompat.checkSelfPermission(activity,
                                                 Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(activity);
                client.getLastLocation().addOnSuccessListener(location -> {
                    HttpUtils.requestNearestBusinesses(activity.getApplicationContext(),
                                                       "pizza",
                                                       location.getLatitude(),
                                                       location.getLongitude(),
                                                       this,
                                                       this);
                });
            }
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