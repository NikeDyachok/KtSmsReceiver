package com.dev.kt.ktsmsreceiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.kt.ktsmsreceiver.utils.DeviceManager;
import com.dev.kt.ktsmsreceiver.utils.PermissionHelper;
import com.dev.kt.ktsmsreceiver.utils.SmsService;
import com.dev.kt.ktsmsreceiver.utils.StoragePref;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_view_status)
    TextView mTextViewStatus;

    @BindView(R.id.button_disable)
    Button mButtonDisable;

    @BindView(R.id.button_enable)
    Button mButtonEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!PermissionHelper.hasSMSPermission(this)) {
            // ask for permission
            Toast.makeText(this, "Version is upper Lollipop, SMS permission is not available", Toast.LENGTH_LONG).show();
        } else {

            if (DeviceManager.isServiceRunning(this, SmsService.class)) {
                changeViewToDisableService();
            } else {
                changeViewToEnableService();
            }

            mButtonDisable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    stopService(new Intent(MainActivity.this, SmsService.class));

                    changeViewToEnableService();
                }
            });

            mButtonEnable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent mServiceIntent = new Intent(MainActivity.this, SmsService.class);
                    startService(mServiceIntent);

                    changeViewToDisableService();
                }
            });
        }

    }

    // better use some kind of MVP architecture but project too small
    private void changeViewToEnableService() {
        StoragePref.setServiceStarted(MainActivity.this, false);
        mTextViewStatus.setText(getString(R.string.main_service_is_offline));

        mButtonDisable.setVisibility(View.INVISIBLE);
        mButtonEnable.setVisibility(View.VISIBLE);
    }

    private void changeViewToDisableService() {
        StoragePref.setServiceStarted(MainActivity.this, true);
        mTextViewStatus.setText(getString(R.string.main_service_is_online));

        mButtonDisable.setVisibility(View.VISIBLE);
        mButtonEnable.setVisibility(View.INVISIBLE);
    }
}











