package com.app.espiotsmartconfig;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.espiotsmartconfig.activity.SmargConfigActivity;
import com.app.espiotsmartconfig.base.BaseActivity;
import com.app.espiotsmartconfig.core.BspHelper;
import com.app.espiotsmartconfig.model.EventMsg;
import com.app.espiotsmartconfig.widget.HeaderView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_ED_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_ERROR_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_ING_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.RECEIVE_MESSAGE_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.TOAST_CODE;

/**
 * 作者:胡涛
 * 日期:2021-7-22
 * 时间:0:14
 * 功能:主页
 */
public class MainActivity extends BaseActivity {

    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HeaderView mHeaderView = findViewById(R.id.header);
        mHeaderView.setHeaderClickListener(() -> startActivity(new Intent(this, SmargConfigActivity.class)));
    }

    @Override
    public boolean needRegistEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(EventMsg msg) {
        switch (msg.code) {
            case TOAST_CODE:
            case RECEIVE_MESSAGE_CODE:
                //接收到指令
                String data = msg.data;
                showToast(data);
                break;
            case CONNECT_ING_CODE:
                if (loadingDialog == null) {
                    loadingDialog = ProgressDialog.show(MainActivity.this, "服务初始化", "正在连接服务器...");
                } else {
                    loadingDialog.show();
                }
                break;
            case CONNECT_ED_CODE:
            case CONNECT_ERROR_CODE:
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    public void open(View view) {
        BspHelper.pushData("1");
    }

    public void close(View view) {
        BspHelper.pushData("0");
    }
}