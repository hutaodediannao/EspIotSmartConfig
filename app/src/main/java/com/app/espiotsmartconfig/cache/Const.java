package com.app.espiotsmartconfig.cache;

import android.content.Context;
import android.content.Intent;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.activity.DeviceActivity;
import com.app.espiotsmartconfig.model.Device;

import java.util.Arrays;
import java.util.List;

public
class Const {

    public static final String SERIALIZABLE = "Serializable";

    public static final String FIRE_COD = "仿真打火机";
    public static final String CAILED_COD = "智声彩灯";
    public static final String CAR_COD = "智能控车";
    public static final String CAMERA_COD = "远程相机";
    public static final String ROBOT_COD = "机械手臂";
    public static final String LAJITONG_COD = "感应垃圾桶";
    public static final String FENGSHAN_COD = "随心风扇";
    public static final String CLOUDCONTROLLER_COD = "云台智控";

    public static List<Device> getDeviceDataSource(Context context) {
        List<Device> list = Arrays.asList(
                new Device(1000, FIRE_COD, R.drawable.ic_fire),
                new Device(1000, CAILED_COD, R.drawable.ic_cailed),
                new Device(1000, CAR_COD, R.drawable.ic_car),
                new Device(1000, CAMERA_COD, R.drawable.ic_camera),
                new Device(1000, ROBOT_COD, R.drawable.ic_robot),
                new Device(1000, LAJITONG_COD, R.drawable.ic_lajitong),
                new Device(1000, FENGSHAN_COD, R.drawable.ic_feng_shan),
                new Device(1000, CLOUDCONTROLLER_COD, R.drawable.ic_cloudcontroller)
        );
        for (Device dv : list) {
            dv.setOnClickListener(view -> {
                Intent intent = new Intent(context, DeviceActivity.class);
                intent.putExtra(SERIALIZABLE, dv);
                context.startActivity(intent);
            });
        }
        return list;
    }

    //云台接入的指令设备
    public static final String LIG = "LIG";
    public static final String CLO = "CLO";
    public static final String LED = "LED";
    public static final String CAR = "CAR";
    public static final String CAM = "CAM";
    public static final String ROT = "ROT";
    public static final String JUN = "JUN";
    public static final String FAN = "FAN";
}
