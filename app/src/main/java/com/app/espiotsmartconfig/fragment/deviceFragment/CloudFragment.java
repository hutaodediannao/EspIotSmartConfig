package com.app.espiotsmartconfig.fragment.deviceFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.base.BaseActivity;
import com.app.espiotsmartconfig.cache.Const;
import com.app.espiotsmartconfig.core.BspHelper;
import com.app.espiotsmartconfig.fragment.BaseFragment;
import com.app.espiotsmartconfig.model.Device;
import com.app.espiotsmartconfig.widget.CustomFeedImageView;
import com.app.espiotsmartconfig.widget.CustomFireView;
import com.app.espiotsmartconfig.widget.HeaderView;

import org.jetbrains.annotations.NotNull;

import static com.app.espiotsmartconfig.cache.Const.CLO;
import static com.app.espiotsmartconfig.cache.Const.LIG;

/**
 * 作者:胡涛
 * 日期:2021-7-28
 * 时间:23:22
 * 功能:云台控制
 */
public class CloudFragment extends BaseFragment implements View.OnClickListener {

    private Device mDevice;
    private CustomFeedImageView ivUp;
    private CustomFeedImageView ivDown;
    private CustomFeedImageView ivLeft;
    private CustomFeedImageView ivRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDevice = getArguments().getParcelable(Const.SERIALIZABLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cloud, container, false);
        HeaderView mHeaderView = rootView.findViewById(R.id.header);
        mHeaderView.setTitle(mDevice.getName())
                .setBackVisible(true)
                .setMenuVisible(false);
        ivUp = rootView.findViewById(R.id.ivUp);
        ivDown = rootView.findViewById(R.id.ivDown);
        ivLeft = rootView.findViewById(R.id.ivLeft);
        ivRight = rootView.findViewById(R.id.ivRight);
        return rootView;
    }


    boolean mNeedSendUP = true;

    private void sendUp() {
        mVertical -= 30;
        handData();
        BspHelper.pushData(CLO + "T" + mVertical);
        if (mNeedSendUP) {
            ((BaseActivity) getActivity()).mHandler.postDelayed(this::sendUp, 500);
        }
    }

    boolean mNeedSendDown = true;

    private void sendDown() {
        mVertical += 30;
        handData();
        BspHelper.pushData(CLO + "T" + mVertical);
        if (mNeedSendDown) {
            ((BaseActivity) getActivity()).mHandler.postDelayed(this::sendDown, 500);
        }
    }

    boolean mNeedSendLeft = true;

    private void sendLeft() {
        mHorizontalRoute += 30;
        handData();
        BspHelper.pushData(CLO + "B" + mHorizontalRoute);
        if (mNeedSendLeft) {
            ((BaseActivity) getActivity()).mHandler.postDelayed(this::sendLeft, 500);
        }
    }

    boolean mNeedSendRight = true;

    private void sendRight() {
        mHorizontalRoute -= 30;
        handData();
        BspHelper.pushData(CLO + "B" + mHorizontalRoute);
        if (mNeedSendRight) {
            ((BaseActivity) getActivity()).mHandler.postDelayed(this::sendRight, 500);
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivUp.setTouchEventHandleCallback(new CustomFeedImageView.TouchEventHandleCallback() {
            @Override
            public void down() {
                sendUp();
            }

            @Override
            public void up() {
                mNeedSendUP = false;
            }
        });
        ivDown.setTouchEventHandleCallback(new CustomFeedImageView.TouchEventHandleCallback() {
            @Override
            public void down() {
                sendDown();
            }

            @Override
            public void up() {
                mNeedSendDown = false;
            }
        });
        ivLeft.setTouchEventHandleCallback(new CustomFeedImageView.TouchEventHandleCallback() {
            @Override
            public void down() {
                sendLeft();
            }

            @Override
            public void up() {
                mNeedSendLeft = false;
            }
        });
        ivRight.setTouchEventHandleCallback(new CustomFeedImageView.TouchEventHandleCallback() {
            @Override
            public void down() {
                sendRight();
            }

            @Override
            public void up() {
                mNeedSendRight = false;
            }
        });
    }

    private int mHorizontalRoute = 90;
    private int mVertical = 90;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivUp:
                mVertical -= 30;
                handData();
                BspHelper.pushData(CLO + "T" + mVertical);
                break;
            case R.id.ivDown:
                mVertical += 30;
                handData();
                BspHelper.pushData(CLO + "T" + mVertical);
                break;
            case R.id.ivLeft:
                mHorizontalRoute += 30;
                handData();
                BspHelper.pushData(CLO + "B" + mHorizontalRoute);
                break;
            case R.id.ivRight:
                mHorizontalRoute -= 30;
                handData();
                BspHelper.pushData(CLO + "B" + mHorizontalRoute);
                break;
            default:
                break;
        }
    }

    private void handData() {
        if (mHorizontalRoute < 5) mHorizontalRoute = 5;
        else if (mHorizontalRoute > 175) mHorizontalRoute = 175;
        if (mVertical < 5) mVertical = 5;
        else if (mVertical > 175) mVertical = 175;
    }
}