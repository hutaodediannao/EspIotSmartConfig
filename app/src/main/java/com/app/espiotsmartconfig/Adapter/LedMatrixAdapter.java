package com.app.espiotsmartconfig.Adapter;

import android.content.Context;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.model.LedDevice;

import java.util.List;

/**
 * 作者:胡涛
 * 日期:2021-7-31
 * 时间:20:55
 * 功能:led工具类
 */
public class LedMatrixAdapter extends BaseRecyclerAdapter<LedDevice> {

    public LedMatrixAdapter(List<LedDevice> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.item_led_lay;
    }

    @Override
    public void bindHolder(BaseViewHolder holder, int position, LedDevice ledDevice) {
        int color = mContext.getResources().getColor(ledDevice.isOpen() ? R.color.red : R.color.darkGray);
        holder.setCardViewBackgroundColor(R.id.cardLed, color);
    }
}
