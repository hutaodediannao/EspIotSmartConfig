package com.app.espiotsmartconfig.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public
class CustomFeedImageView extends ImageView {
    public CustomFeedImageView(Context context) {
        super(context);
    }

    public CustomFeedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFeedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (touchEventHandleCallback != null) {
                    touchEventHandleCallback.down();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (touchEventHandleCallback != null) {
                    touchEventHandleCallback.up();
                }
                break;
        }
        return true;
    }

    private TouchEventHandleCallback touchEventHandleCallback;

    public void setTouchEventHandleCallback(TouchEventHandleCallback touchEventHandleCallback) {
        this.touchEventHandleCallback = touchEventHandleCallback;
    }

    public interface TouchEventHandleCallback{
        void down();
        void up();
    }
}
