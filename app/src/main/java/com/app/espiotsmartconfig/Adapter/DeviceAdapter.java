package com.app.espiotsmartconfig.Adapter;

import android.content.Context;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.model.Device;

import java.util.List;

public class DeviceAdapter extends BaseRecyclerAdapter<Device> {

    public DeviceAdapter(List mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    public int getRecyclerItemViewType(int position) {
        return 0;
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.item_device_lay;
    }

    @Override
    public void bindHolder(BaseViewHolder holder, int position, Device device) {
        holder.setImageView(R.id.ivIcon, device.getIconRes())
                .setText(R.id.tvTitle, device.getName());
        holder.itemView.setOnClickListener(device.getOnClickListener());
    }
}
