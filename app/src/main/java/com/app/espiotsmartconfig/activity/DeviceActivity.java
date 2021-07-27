package com.app.espiotsmartconfig.activity;

import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.base.BaseActivity;
import com.app.espiotsmartconfig.cache.Const;
import com.app.espiotsmartconfig.core.BspHelper;
import com.app.espiotsmartconfig.model.Device;
import com.app.espiotsmartconfig.widget.CustomFireView;
import com.app.espiotsmartconfig.widget.HeaderView;

import java.io.IOException;

import static com.app.espiotsmartconfig.cache.Const.LIG;

public class DeviceActivity extends BaseActivity {

    private boolean isOn = false;
    private SoundPool mSoundPool;
    private int openId, closeId;
    private static final String OPEN = "open";
    private static final String CLOSE = "close";
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        HeaderView mHeaderView = findViewById(R.id.header);
        Device device = getIntent().getParcelableExtra(Const.SERIALIZABLE);
        mHeaderView.setTitle(device.getName())
                .setBackVisible(true)
                .setMenuVisible(false);
        CustomFireView imageView = findViewById(R.id.ivFire);
        initListener(imageView);
        initSound();
    }

    private AssetFileDescriptor fileDescriptor;

    private void initListener(CustomFireView imageView) {
        imageView.setCallbackListener(new CustomFireView.CallbackListener() {
            @Override
            public void openFire() {
                if (!isOn) {
                    isOn = true;
                    imageView.setImageResource(isOn ? R.mipmap.on_fire : R.mipmap.off_fire);
                    BspHelper.pushData(LIG + "1");
                    playSound(OPEN);

                    try {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        mediaPlayer.reset();
                        fileDescriptor = getAssets().openFd("open_fire.mp3");
                        mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
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
                    playSound(CLOSE);

                    try {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        mediaPlayer.reset();
                        fileDescriptor = getAssets().openFd("close_fire.mp3");
                        mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
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
            mSoundPool = builder.build();
        } else {
            mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
        }

        mediaPlayer = new MediaPlayer();
    }

    /**
     * 播放打火机打开关闭的声音
     */
    private void playSound(String status) {
//        switch (status) {
//            case OPEN:
//                if (closeId != 0) {
//                    mSoundPool.stop(closeId);
//                }
//                openId = mSoundPool.load(DeviceActivity.this, R.raw.open_fire, 1);
//                mHandler.postDelayed(() -> mSoundPool.play(openId, 1, 1, 1, 1, 1), 50);
//                break;
//            case CLOSE:
//                if (openId != 0) {
//                    mSoundPool.stop(openId);
//                }
//                closeId = mSoundPool.load(DeviceActivity.this, R.raw.close_fire, 1);
//                mHandler.postDelayed(() -> mSoundPool.play(closeId, 1, 1, 1, 1, 1), 50);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }

}