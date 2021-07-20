package com.app.espiotsmartconfig;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HeaderView extends FrameLayout {

    private ImageView ivBack;
    private TextView tvTitle;

    public HeaderView(@NonNull Context context) {
        this(context, null);
    }

    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        FrameLayout.inflate(getContext(), R.layout.header_lay, this);
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack.setVisibility(VISIBLE);
        ivBack.setOnClickListener(view -> {
            if (getContext() instanceof Activity) {
                Activity activity = (Activity) getContext();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        });
    }

    public HeaderView setTitle(String title) {
        this.tvTitle.setText(title);
        return this;
    }

    public HeaderView setBackVisible(boolean visible) {
        this.ivBack.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

}
