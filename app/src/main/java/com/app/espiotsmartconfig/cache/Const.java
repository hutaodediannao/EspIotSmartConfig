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

    public static List<Device> getDeviceDataSource(Context context){
        List<Device> list = Arrays.asList(
                new Device(1000, "智能打火机", R.drawable.ic_fire),
                new Device(1000, "智能打火机", R.drawable.ic_fire),
                new Device(1000, "智能打火机", R.drawable.ic_fire),
                new Device(1000, "智能打火机", R.drawable.ic_fire),
                new Device(1000, "智能打火机", R.drawable.ic_fire)
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
