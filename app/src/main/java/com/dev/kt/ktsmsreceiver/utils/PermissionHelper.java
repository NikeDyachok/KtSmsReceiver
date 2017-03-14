package com.dev.kt.ktsmsreceiver.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Андрей on 13.03.2017.
 */

public class PermissionHelper {


    public static boolean hasSMSPermission(Context context){
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }
}
