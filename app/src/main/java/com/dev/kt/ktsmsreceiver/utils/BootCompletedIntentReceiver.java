package com.dev.kt.ktsmsreceiver.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Андрей on 14.03.2017.
 */

public class BootCompletedIntentReceiver extends BroadcastReceiver {

    private static final String PERMISSION_BOOT_NAME = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (PERMISSION_BOOT_NAME.equals(intent.getAction())) {
            if (StoragePref.isServiceStarted(context)) {
                Intent pushIntent = new Intent(context, SmsService.class);
                context.startService(pushIntent);
            }
        }
    }
}
