package com.app.espiotsmartconfig.activity;

import android.os.Bundle;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.base.BaseActivity;
import com.app.espiotsmartconfig.cache.Const;
import com.app.espiotsmartconfig.fragment.BaseFragment;
import com.app.espiotsmartconfig.fragment.deviceFragment.CloudFragment;
import com.app.espiotsmartconfig.fragment.deviceFragment.LightFireFragment;
import com.app.espiotsmartconfig.model.Device;

public class DeviceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Device device = getIntent().getParcelableExtra(Const.SERIALIZABLE);

        BaseFragment fragment = null;
        switch (device.getName()) {
            case Const.FIRE_COD:
                fragment = BaseFragment.newInstance(LightFireFragment.class, device);
                break;
            case Const.CAILED_COD:
                break;
            case Const.CAR_COD:
                break;
            case Const.CAMERA_COD:
                break;
            case Const.ROBOT_COD:
                break;
            case Const.LAJITONG_COD:
                break;
            case Const.FENGSHAN_COD:
                break;
            case Const.CLOUDCONTROLLER_COD:
                fragment = BaseFragment.newInstance(CloudFragment.class, device);
                break;
            default:
                break;
        }
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment).commit();
    }

}