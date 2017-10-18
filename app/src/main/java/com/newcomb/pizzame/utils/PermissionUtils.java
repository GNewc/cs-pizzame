package com.newcomb.pizzame.utils;

import android.Manifest;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.newcomb.pizzame.R;

/**
 * Gotta have permission...
 */

public class PermissionUtils {

    public static void requestPermissionsFromFragment(final Fragment fragment,
                                                      final int returnRequestCode)
    {
        int rationaleResId = R.string.permission_location_rationale;
        String locationPerm = Manifest.permission.ACCESS_FINE_LOCATION;
        final String [] permissions = new String[] {locationPerm};
        if (fragment.getView() != null && fragment.shouldShowRequestPermissionRationale(locationPerm))
        {
            // Provide a rationale to the user if the permission was not granted and the user would
            // benefit from additional context for the use of the permission.
            int snackbarLength = Snackbar.LENGTH_INDEFINITE;
            Snackbar.make(fragment.getView(), rationaleResId, snackbarLength)
                    .setAction(android.R.string.ok, v -> fragment.requestPermissions(permissions, returnRequestCode))
                    .show();
        }
        else {
            // Request permission(s) directly
            fragment.requestPermissions(permissions, returnRequestCode);
        }
    }
}
