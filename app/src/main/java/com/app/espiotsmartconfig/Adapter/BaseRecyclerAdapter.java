package com.app.espiotsmartconfig.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public List<T> mList;
    public Context mContext;
    public static final int HEADER_TYPE = 1;
    public static final int NORMAL_TYPE = 2;
    public static final int FOOTER_TYPE = 3;

    public BaseRecyclerAdapter(List<T> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseViewHolder.getInstance(mContext, getLayout(viewType), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        bindHolder(holder, position, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getRecyclerItemViewType(position);
    }

    public abstract int getRecyclerItemViewType(int position);

    public abstract int getLayout(int viewType);

    public abstract void bindHolder(BaseViewHolder holder, int position, T t);

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
