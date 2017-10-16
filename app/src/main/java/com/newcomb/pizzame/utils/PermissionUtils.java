package com.newcomb.pizzame.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.newcomb.pizzame.R;

/**
 * Created by Newcomb on 10/16/2017.
 */

public class PermissionUtils {

    public static void requestPermissionsFromFragment(final Fragment fragment,
                                                      View view,
                                                      final int returnRequestCode)
    {
        Context fragmentContext = fragment.getContext();
        int rationaleResId = R.string.permission_location_rationale;
        String locationPerm = Manifest.permission.ACCESS_FINE_LOCATION;
        int permStatus = ContextCompat.checkSelfPermission(fragmentContext, locationPerm);
        final String [] permissions = new String[] {locationPerm};
        if (view != null && fragment.shouldShowRequestPermissionRationale(locationPerm))
        {
            // Provide a rationale to the user if the permission was not granted and the user would
            // benefit from additional context for the use of the permission.
            int snackbarLength = Snackbar.LENGTH_INDEFINITE;
            Snackbar.make(view, rationaleResId, snackbarLength)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragment.requestPermissions(permissions, returnRequestCode);
                        }
                    })
                    .show();
        }
        else {
            // Request permission(s) directly
            fragment.requestPermissions(permissions, returnRequestCode);
        }
    }

    public static boolean hasLocationPermission(@NonNull Context context) {
        return ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED;
    }
}
