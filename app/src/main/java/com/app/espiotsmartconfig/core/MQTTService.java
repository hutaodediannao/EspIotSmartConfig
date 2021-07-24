package com.app.espiotsmartconfig.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.app.espiotsmartconfig.core.Config.CLIENT_ID;
import static com.app.espiotsmartconfig.core.Config.HOST_URL;
import static com.app.espiotsmartconfig.core.Config.PASSWORD;
import static com.app.espiotsmartconfig.core.Config.SUB_TOPIC;
import static com.app.espiotsmartconfig.core.Config.USER_NAME;

public class MQTTService extends Service {

    public static final String TAG = MQTTService.class.getSimpleName();

    private MqttAndroidClient client;
    private MqttConnectOptions conOpt;
    private IMQMsgCallBack iMqMsgCallback;


    /**
     * MQTT监听并且接受消息
     */
    private MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            String str1 = new String(message.getPayload());
            if (iMqMsgCallback != null) {
                iMqMsgCallback.setMessage(str1);
            }
            String str2 = topic + ";qos:" + message.getQos() + ";retained:" + message.isRetained();
            Log.i(TAG, "messageArrived:" + str1);
            Log.i(TAG, str2);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {
            Log.i(TAG, "==================> deliveryComplete");
        }

        @Override
        public void connectionLost(Throwable arg0) {
            // 失去连接，重连
            Log.i(TAG, "==================> connectionLost:" + arg0.getMessage());
            if (iMqMsgCallback != null) {
                iMqMsgCallback.onError(arg0);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(getClass().getName(), "onCreate");
        init();
    }

    /**
     * 发布消息
     */
    public boolean publish(String topic, String msg) {
        int qos = 0;
        try {
            boolean isConnected = client.isConnected();
            boolean isConnectNet = isConnectIsNormal();

            if (client != null && isConnected && isConnectNet) {
                client.publish(topic == null ? SUB_TOPIC : topic, msg.getBytes(), (int) qos, false);
            }
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean publish(String msg) {
        return this.publish(null, msg);
    }

    private void init() {
        // 服务器地址（协议+地址+端口号）
        client = new MqttAndroidClient(this, HOST_URL, CLIENT_ID);
        // 设置MQTT监听并且接受消息
        client.setCallback(mqttCallback);
        conOpt = new MqttConnectOptions();
        // 清除缓存
        conOpt.setCleanSession(true);
        // 设置超时时间，单位：秒
//        conOpt.setConnectionTimeout(10);
        // 心跳包发送间隔，单位：秒
//        conOpt.setKeepAliveInterval(1);
        // 用户名
        conOpt.setUserName(USER_NAME);
        // 密码
        //将字符串转换为字符串数组
        conOpt.setPassword(PASSWORD.toCharArray());
        doClientConnection();
    }

    @Override
    public void onDestroy() {
        stopSelf();
        super.onDestroy();
    }

    /**
     * 连接MQTT服务器
     */
    private void doClientConnection() {
        if (!client.isConnected() && isConnectIsNormal()) {
            try {
                if (iMqMsgCallback != null) {
                    iMqMsgCallback.connectIng();
                }
                client.connect(conOpt, null, iMqttActionListener);
            } catch (MqttException e) {
                e.printStackTrace();
                if (iMqMsgCallback != null) {
                    iMqMsgCallback.connectFailed();
                }
            }
        } else {
            if (iMqMsgCallback != null) {
                iMqMsgCallback.connectFailed();
            }
        }

    }

    // MQTT是否连接成功
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            Log.i(TAG, "连接成功 ");
            iMqMsgCallback.connected();
            try {
                // 订阅mTopic话题
                client.subscribe(SUB_TOPIC, 0);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            arg1.printStackTrace();
            // 连接失败，重连
            if (isConnectIsNormal()) {
                connect();
            } else {
                iMqMsgCallback.connectFailed();
            }

        }
    };

    public void connect() {
        try {
            iMqMsgCallback.connectIng();
            client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
            iMqMsgCallback.connectFailed();
        }
    }

    /**
     * 判断网络是否连接
     */
    private boolean isConnectIsNormal() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Log.i(TAG, "MQTT当前网络名称：" + name);
            return true;
        } else {
            Log.i(TAG, "MQTT 没有可用网络");
            return false;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getName(), "onBind");
        return new CustomBinder();
    }

    public void setImqMsgCallBack(IMQMsgCallBack iMqMsgCallback) {
        this.iMqMsgCallback = iMqMsgCallback;
    }

    class CustomBinder extends Binder {
        MQTTService getService() {
            return MQTTService.this;
        }
    }

}