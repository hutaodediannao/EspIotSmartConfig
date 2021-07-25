package com.app.espiotsmartconfig.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * 作者:胡涛
 * 日期:2021-7-25
 * 时间:12:02
 * 功能:智能设备
 */
public class Device implements Parcelable {

    private int id;
    private String name;
    private int status;
    private String desc;
    private int iconRes;
    private View.OnClickListener onClickListener;

    public Device(int id, String name, int iconRes) {
        this.id = id;
        this.name = name;
        this.iconRes = iconRes;
    }

    public Device(int id, String name, int status, String desc, int iconRes) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.desc = desc;
        this.iconRes = iconRes;
    }

    protected Device(Parcel in) {
        id = in.readInt();
        name = in.readString();
        status = in.readInt();
        desc = in.readString();
        iconRes = in.readInt();
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(status);
        parcel.writeString(desc);
        parcel.writeInt(iconRes);
    }
}
