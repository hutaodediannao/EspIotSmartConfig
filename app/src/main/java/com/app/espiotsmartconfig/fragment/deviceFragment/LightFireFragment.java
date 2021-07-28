package com.app.espiotsmartconfig.fragment.deviceFragment;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.cache.Const;
import com.app.espiotsmartconfig.core.BspHelper;
import com.app.espiotsmartconfig.fragment.BaseFragment;
import com.app.espiotsmartconfig.model.Device;
import com.app.espiotsmartconfig.util.SoundPlayerManager;
import com.app.espiotsmartconfig.widget.CustomFireView;
import com.app.espiotsmartconfig.widget.HeaderView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.app.espiotsmartconfig.cache.Const.LIG;

/**
 * 作者:胡涛
 * 日期:2021-7-28
 * 时间:22:43
 * 功能:打火机控制也
 */
public class LightFireFragment extends BaseFragment {

    private boolean isOn = false;
    private Device mDevice;
    private SoundPlayerManager mSoundPlayerManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDevice = getArguments().getParcelable(Const.SERIALIZABLE);
            mSoundPlayerManager = new SoundPlayerManager();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_l_ight_fire, container, false);
        HeaderView mHeaderView = rootView.findViewById(R.id.header);
        mHeaderView.setTitle(mDevice.getName())
                .setBackVisible(true)
                .setMenuVisible(false);
        CustomFireView imageView = rootView.findViewById(R.id.ivFire);
        initListener(imageView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSound();
    }

    private void initListener(CustomFireView imageView) {
        imageView.setCallbackListener(new CustomFireView.CallbackListener() {
            @Override
            public void openFire() {
                if (!isOn) {
                    isOn = true;
                    imageView.setImageResource(isOn ? R.mipmap.on_fire : R.mipmap.off_fire);
                    BspHelper.pushData(LIG + "1");
                    try {
                        mSoundPlayerManager.playerSound(getContext().getAssets().openFd("open_fire.mp3"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void closeFire() {
                if (isOn) {
                    isOn = false;
                    imageView.setImageResource(isOn ? R.mipmap.on_fire : R.mipmap.off_fire);
                    BspHelper.pushData(LIG + "0");
                    try {
                        mSoundPlayerManager.playerSound(getContext().getAssets().openFd("close_fire.mp3"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initSound() {
        //当前系统的SDK版本大于等于21(Android 5.0)时
        if (Build.VERSION.SDK_INT > 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频数量
            builder.setMaxStreams(5);
            //AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_SYSTEM);//STREAM_MUSIC
            //加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
        }
    }

    @Override
    public void onStop() {
        mSoundPlayerManager.release();
        super.onStop();
    }

}