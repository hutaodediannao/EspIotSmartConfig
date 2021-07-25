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

    public static List<Device> getDeviceDataSource(Context context) {
        List<Device> list = Arrays.asList(
                new Device(1000, "智趣打火机", R.drawable.ic_fire),
                new Device(1000, "智声彩灯", R.drawable.ic_cailed),
                new Device(1000, "智能控车", R.drawable.ic_car),
                new Device(1000, "远程相机", R.drawable.ic_camera),
                new Device(1000, "机械手", R.drawable.ic_robot),
                new Device(1000, "感应垃圾桶", R.drawable.ic_lajitong),
                new Device(1000, "随心风扇", R.drawable.ic_feng_shan),
                new Device(1000, "云台智控", R.drawable.ic_cloudcontroller)
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

}
