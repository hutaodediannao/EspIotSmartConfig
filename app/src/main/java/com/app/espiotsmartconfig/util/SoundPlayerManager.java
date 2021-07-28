package com.app.espiotsmartconfig.util;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import java.io.IOException;

/**
 * 作者:胡涛
 * 日期:2021-7-28
 * 时间:22:28
 * 功能:音乐播放管理
 */
public class SoundPlayerManager {

    private MediaPlayer mediaPlayer;

    public SoundPlayerManager() {
        mediaPlayer = new MediaPlayer();
    }

    /**
     * 播放assets中的音乐
     * @param fileDescriptor
     */
    public void playerSound(AssetFileDescriptor fileDescriptor){
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void release(){
        mediaPlayer.release();
    }
}
