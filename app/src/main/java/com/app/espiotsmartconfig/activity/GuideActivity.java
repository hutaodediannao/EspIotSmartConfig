package com.app.espiotsmartconfig.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.espiotsmartconfig.MainActivity;
import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.base.BaseActivity;

/**
 * 作者:胡涛
 * 日期:2021-7-21
 * 时间:23:04
 * 功能:引导页
 */
public class GuideActivity extends BaseActivity {

    private LinearLayout iconLay;
    private ImageView imageView;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        iconLay = findViewById(R.id.iconLay);
        iconLay.setAlpha(0.0f);
        imageView = findViewById(R.id.imageView);
        tvContent = findViewById(R.id.tvContent);
        tvContent.setVisibility(View.GONE);

        initAnim();
    }

    AnimatorSet animatorSet;

    private void initAnim() {
        animatorSet = new AnimatorSet();
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(iconLay, "alpha", 0.0f, 1.0f);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(tvContent, "scaleX", 0.5f, 1.5f, 1.0f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(tvContent, "scaleY", 0.5f, 1.5f, 1.0f);

        animatorAlpha.setDuration(1500);
        animatorScaleX.setDuration(500);
        animatorScaleY.setDuration(500);

        animatorAlpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tvContent.setVisibility(View.VISIBLE);
            }
        });

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startNextPage();
            }
        });

        animatorSet.play(animatorScaleX).with(animatorScaleY).after(animatorAlpha);
        animatorSet.start();
    }

    private void startNextPage() {
        mHandler.postDelayed(() -> {
            Activity activity = mWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animatorSet.isRunning()) {
            animatorSet.cancel();
        }
    }
}