package com.app.espiotsmartconfig.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.base.BaseActivity;
import com.app.espiotsmartconfig.cache.Const;
import com.app.espiotsmartconfig.core.BspHelper;
import com.app.espiotsmartconfig.model.Device;
import com.app.espiotsmartconfig.widget.HeaderView;

public class DeviceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        HeaderView mHeaderView = findViewById(R.id.header);
        Device device = getIntent().getParcelableExtra(Const.SERIALIZABLE);
        mHeaderView.setTitle(device.getName())
                .setBackVisible(true)
                .setMenuVisible(false);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch ledSwitch = findViewById(R.id.ledSwitch);
        ledSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            BspHelper.pushData(b ? "1" : "0");
        });

    }
}