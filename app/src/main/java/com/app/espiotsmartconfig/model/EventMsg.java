package com.app.espiotsmartconfig.model;

public class EventMsg {
    public int code;
    public String data;

    public EventMsg(int code) {
        this.code = code;
    }

    public EventMsg(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static final int TOAST_CODE = 200;

    //MQTT连接各种状态
    public static final int CONNECT_ING_CODE = 100;
    public static final int CONNECT_ED_CODE = 101;
    public static final int CONNECT_FAILED_CODE = 102;
    public static final int RECEIVE_MESSAGE_CODE = 103;
    public static final int CONNECT_ERROR_CODE = 104;
}
