package com.dev.kt.ktsmsreceiver.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Андрей on 14.03.2017.
 */

public class StoragePref {

    private static final String MAIN_PREFERENCES = "main_preferences";

    private static final String SMS_SERVICE_STATUS = "sms_service_status";

    public static boolean isServiceStarted(Context context) {
        SharedPreferences settings = context
                .getSharedPreferences(MAIN_PREFERENCES, Context.MODE_PRIVATE);
        return settings.getBoolean(SMS_SERVICE_STATUS, false);
    }

    public static void setServiceStarted(Context context, boolean started){
        SharedPreferences settings = context
                .getSharedPreferences(MAIN_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(SMS_SERVICE_STATUS, started);
        editor.apply();
    }
}
