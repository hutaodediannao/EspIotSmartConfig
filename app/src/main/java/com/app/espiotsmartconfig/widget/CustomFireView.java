package com.app.espiotsmartconfig.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class CustomFireView extends ImageView {

    private static final String TAG = "CustomFireView";
    //点火最小下滑距离
    private static final int OPEN_FIRE_DISTANCE = 40;

    public CustomFireView(Context context) {
        super(context);
    }

    public CustomFireView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFireView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                if (moveY - downY > OPEN_FIRE_DISTANCE) {
                    if (callbackListener != null) {
                        Log.i(TAG, "callbackListener.openFire() 开始执行...");
                        callbackListener.openFire();
                        downY = event.getY();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (callbackListener != null) {
                    callbackListener.closeFire();
                    Log.i(TAG, "callbackListener.closeFire() 开始执行...");
                    downY = event.getY();
                }
                break;
        }
        return true;
    }

    public interface CallbackListener {
        void openFire();

        void closeFire();
    }

    private CallbackListener callbackListener;

    public void setCallbackListener(CallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }
}
