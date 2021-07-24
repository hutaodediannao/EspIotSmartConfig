package com.app.espiotsmartconfig.core;

import com.app.espiotsmartconfig.EspApp;

/**
 * 作者:胡涛
 * 日期:2021-7-24
 * 时间:21:38
 * 功能:消息发送中枢
 */
public class BspHelper {

    public static boolean pushData(String msg) {
        return EspApp.getInstance().getServiceConnection().getMqttService().publish(msg);
    }

    public static boolean pushData(String topic, String msg) {
        return EspApp.getInstance().getServiceConnection().getMqttService().publish(topic, msg);
    }

}
