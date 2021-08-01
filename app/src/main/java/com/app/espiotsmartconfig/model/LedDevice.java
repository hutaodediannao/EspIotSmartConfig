package com.app.espiotsmartconfig.model;

/**
 * 作者:胡涛
 * 日期:2021-7-31
 * 时间:20:48
 * 功能:led矩阵灯的属性类别
 */
public class LedDevice {

    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public LedDevice(boolean isOpen) {
        this.isOpen = isOpen;
    }
}
