package com.app.espiotsmartconfig;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.app.espiotsmartconfig.core.IMQMsgCallBack;
import com.app.espiotsmartconfig.core.MQTTService;
import com.app.espiotsmartconfig.core.MyServiceConnection;
import com.app.espiotsmartconfig.model.EventMsg;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_ED_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_ERROR_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_FAILED_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_ING_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.RECEIVE_MESSAGE_CODE;

public class EspApp extends Application {

    private static EspApp app;
    private MutableLiveData<String> mBroadcastData;
    private Map<String, Object> mCacheMap;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }

            switch (action) {
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                case LocationManager.PROVIDERS_CHANGED_ACTION:
                    mBroadcastData.setValue(action);
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mCacheMap = new HashMap<>();
        mBroadcastData = new MutableLiveData<>();
        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        }
        registerReceiver(mReceiver, filter);

        //启动Mqtt服务
        startMqttServer();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(mReceiver);
        EspApp.getInstance().getServiceConnection().onServiceDisconnected(ComponentName.createRelative(this, EspApp.class.getComponentType().getName()));
    }

    public static EspApp getInstance() {
        return app;
    }

    public void observeBroadcast(LifecycleOwner owner, Observer<String> observer) {
        mBroadcastData.observe(owner, observer);
    }

    public void observeBroadcastForever(Observer<String> observer) {
        mBroadcastData.observeForever(observer);
    }

    public void removeBroadcastObserver(Observer<String> observer) {
        mBroadcastData.removeObserver(observer);
    }

    public void putCache(String key, Object value) {
        mCacheMap.put(key, value);
    }

    public Object takeCache(String key) {
        return mCacheMap.remove(key);
    }

    private MyServiceConnection mServiceConnection;

    /**
     * 启动MQTTserver并监听数据
     */
    private void startMqttServer() {
        mServiceConnection = new MyServiceConnection(imqMsgCallBack);
        Intent intent = new Intent(this, MQTTService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    IMQMsgCallBack imqMsgCallBack = new IMQMsgCallBack() {
        @Override
        public void connectIng() {
            //服务器连接中
            EventBus.getDefault().post(new EventMsg(CONNECT_ING_CODE));
        }

        @Override
        public void connected() {
            //服务器已连接
            EventBus.getDefault().post(new EventMsg(CONNECT_ED_CODE));
        }

        @Override
        public void connectFailed() {
            //服务器连接失败
            EventBus.getDefault().post(new EventMsg(CONNECT_ERROR_CODE));
        }

        @Override
        public void setMessage(String message) {
            //收到MQTT下发的消息
            EventBus.getDefault().post(new EventMsg(RECEIVE_MESSAGE_CODE, message));
        }

        @Override
        public void onError(Throwable e) {
            //出错
            EventBus.getDefault().post(new EventMsg(CONNECT_FAILED_CODE, e.getMessage()));
        }
    };

    public MyServiceConnection getServiceConnection() {
        return mServiceConnection;
    }

}
