package com.app.espiotsmartconfig.activity;

import android.content.Intent;
import android.os.Bundle;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.base.BaseActivity;
import com.app.espiotsmartconfig.model.EventMsg;
import com.app.espiotsmartconfig.widget.HeaderView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 作者:胡涛
 * 日期:2021-7-22
 * 时间:0:14
 * 功能:主页
 */
public class MainActivity extends BaseActivity {

    private HeaderView mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeaderView = findViewById(R.id.header);
        mHeaderView.setHeaderClickListener(() -> startActivity(new Intent(this, SmargConfigActivity.class)));
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