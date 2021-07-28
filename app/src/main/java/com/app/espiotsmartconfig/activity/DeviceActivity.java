package com.app.espiotsmartconfig.activity;

import android.os.Bundle;
import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.base.BaseActivity;
import com.app.espiotsmartconfig.cache.Const;
import com.app.espiotsmartconfig.fragment.BaseFragment;
import com.app.espiotsmartconfig.fragment.deviceFragment.LightFireFragment;
import com.app.espiotsmartconfig.model.Device;

public class DeviceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Device device = getIntent().getParcelableExtra(Const.SERIALIZABLE);

        BaseFragment fragment = BaseFragment.newInstance(LightFireFragment.class, device);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment).commit();
    }

}