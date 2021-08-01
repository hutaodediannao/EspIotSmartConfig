package com.app.espiotsmartconfig.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者:胡涛
 * 日期:2021-6-19
 * 时间:15:26
 * 功能:ViewHolder
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewSparseArray;

    public static BaseViewHolder getInstance(Context context, int layout, ViewGroup parent) {
        return new BaseViewHolder(LayoutInflater.from(context).inflate(layout, parent, false));
    }

    private BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mViewSparseArray = new SparseArray<>();
    }

    public <T> T getView(int viewId) {
        View t = mViewSparseArray.get(viewId);
        if (t == null) {
            t = this.itemView.findViewById(viewId);
            this.mViewSparseArray.put(viewId, t);
        }
        return (T) t;
    }

    public BaseViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public BaseViewHolder setCheckBox(int viewId, boolean checked) {
        CheckBox checkBox = getView(viewId);
        checkBox.setChecked(checked);
        return this;
    }

    public BaseViewHolder setImageView(int viewId, int imgResId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(imgResId);
        return this;
    }

    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseViewHolder setCardViewBackgroundColor(int viewId, int color) {
        CardView cardView = getView(viewId);
        cardView.setCardBackgroundColor(color);
        return this;
    }

}
