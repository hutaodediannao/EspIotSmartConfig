package com.app.espiotsmartconfig.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.espiotsmartconfig.R;

public class HeaderView extends FrameLayout {

    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvMenu;

    private String mTitleContent, mRightContent;

    public HeaderView(@NonNull Context context) {
        this(context, null);
    }

    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        TypedArray tp = context.obtainStyledAttributes(attrs, R.styleable.HeaderView);
        this.mTitleContent = tp.getString(R.styleable.HeaderView_titleContent);
        this.mRightContent = tp.getString(R.styleable.HeaderView_rightContent);
        int colorRes = tp.getResourceId(R.styleable.HeaderView_contentColor, R.color.white);
        this.tvTitle.setTextColor(getResources().getColor(colorRes));
        this.tvMenu.setTextColor(getResources().getColor(colorRes));
        setTitle(this.mTitleContent);
        setRight(this.mRightContent);
        setBackVisible(tp.getBoolean(R.styleable.HeaderView_backVisible, true));
        tp.recycle();
    }

    private void initView() {
        FrameLayout.inflate(getContext(), R.layout.header_lay, this);
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvMenu = findViewById(R.id.tvMenu);
        ivBack.setVisibility(VISIBLE);
        ivBack.setOnClickListener(view -> {
            if (getContext() instanceof Activity) {
                Activity activity = (Activity) getContext();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        });
        tvMenu.setOnClickListener(view -> {
            if (mHeaderClickListener != null) {
                mHeaderClickListener.clickRight();
            }
        });
    }

    public HeaderView setTitle(String title) {
        this.tvTitle.setText(title);
        return this;
    }

    public HeaderView setRight(String right) {
        this.tvMenu.setText(right);
        return this;
    }

    public HeaderView setBackVisible(boolean visible) {
        this.ivBack.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public interface HeaderClickListener {
        void clickRight();
    }

    private HeaderClickListener mHeaderClickListener;

    public HeaderView setHeaderClickListener(HeaderClickListener headerClickListener) {
        this.mHeaderClickListener = headerClickListener;
        return this;
    }
}
