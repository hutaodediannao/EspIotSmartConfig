package com.app.espiotsmartconfig.core;

/**
 * 作者:胡涛
 * 日期:2021-5-17
 * 时间:22:19
 * 功能:配置信息
 */
public class Config {

    //public static final String HOST_URL = "tcp://192.168.1.105:61613";
    // 本地cmd命令行ipconfig命令再加上服务器启动的端口就可以了
    public static final String HOST_URL = "tcp://test.mosquitto.org:1883";
    public static final String CLIENT_ID = "hutao";
    public static final String USER_NAME = "admin";
    public static final String PASSWORD = "password";
    //要订阅的主题
    public static final String SUB_TOPIC = "client-wifi-topic";

}
