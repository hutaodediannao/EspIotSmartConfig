package com.app.espiotsmartconfig;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

    private HeaderView mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeaderView = findViewById(R.id.header);
        mHeaderView.setTitle("设备列表")
                .setBackVisible(false);
    }

    public void startSmartConfig(View view) {
        startActivity(new Intent(this, SmargConfigActivity.class));
    }

    @Override
    public boolean needRegistEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(EventMsg msg) {
        if (msg.code == 100) {
            String data = msg.data;
            showToast(data);
        }
    }

}