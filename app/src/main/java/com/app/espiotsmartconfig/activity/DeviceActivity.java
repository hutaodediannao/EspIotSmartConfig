package com.app.espiotsmartconfig.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.base.BaseActivity;
import com.app.espiotsmartconfig.cache.Const;
import com.app.espiotsmartconfig.core.BspHelper;
import com.app.espiotsmartconfig.model.Device;
import com.app.espiotsmartconfig.widget.CustomFireView;
import com.app.espiotsmartconfig.widget.HeaderView;

public class DeviceActivity extends BaseActivity {

    private boolean isOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        HeaderView mHeaderView = findViewById(R.id.header);
        Device device = getIntent().getParcelableExtra(Const.SERIALIZABLE);
        mHeaderView.setTitle(device.getName())
                .setBackVisible(true)
                .setMenuVisible(false);

        CustomFireView imageView = findViewById(R.id.ivFire);
//        CustomFireView.setOnClickListener(view -> {
//            if (isOn) return;
//            isOn = true;
//            BspHelper.pushData("1");
//            imageView.setImageResource(isOn ? R.mipmap.on_fire : R.mipmap.off_fire);
//            if (isOn) {
//                //3秒之后，自动关闭打火机
//                mHandler.postDelayed(() -> {
//                    if (isOn) {
//                        BspHelper.pushData("0");
//                        isOn = false;
//                        imageView.setImageResource(isOn ? R.mipmap.on_fire : R.mipmap.off_fire);
//                    }
//                }, 5000);
//            }
//        });

        imageView.setCallbackListener(new CustomFireView.CallbackListener() {
            @Override
            public void openFire() {
                if (!isOn) {
                    isOn = true;
                    imageView.setImageResource(isOn ? R.mipmap.on_fire : R.mipmap.off_fire);
                    BspHelper.pushData("1");
                }
            }

            @Override
            public void closeFire() {
                if (isOn) {
                    isOn = false;
                    imageView.setImageResource(isOn ? R.mipmap.on_fire : R.mipmap.off_fire);
                    BspHelper.pushData("0");
                }
            }
        });
    }
}