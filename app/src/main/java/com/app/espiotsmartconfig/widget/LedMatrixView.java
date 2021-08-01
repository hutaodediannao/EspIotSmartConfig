package com.app.espiotsmartconfig.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.core.BspHelper;
import com.app.espiotsmartconfig.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:胡涛
 * 日期:2021-8-1
 * 时间:12:11
 * 功能:矩阵Led控制面板
 */
@SuppressLint("AppCompatCustomView")
public class LedMatrixView extends ImageView {

    private static final String TAG = "LedMatrixView";
    private Paint mLinePaint;
    private Paint mCheckOnPaint;
    private Paint mCheckOffPaint;
    private List<Boolean> mStateList;
    private List<Integer> mCacheList;

    public LedMatrixView(Context context) {
        this(context, null);
    }

    public LedMatrixView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LedMatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(getResources().getColor(R.color.darkGray));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(DensityUtil.dip2px(context, 2));

        mCheckOnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCheckOnPaint.setColor(getResources().getColor(R.color.blue));
        mCheckOnPaint.setStyle(Paint.Style.FILL);

        mCheckOffPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCheckOffPaint.setColor(getResources().getColor(R.color.darkGray));
        mCheckOffPaint.setStyle(Paint.Style.FILL);

        mCacheList = new ArrayList<>();
        mStateList = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            mStateList.add(Boolean.FALSE);
        }
    }

    int mWidth, mHeight;
    float singleWidth;
    float mLedRadius;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = getMeasuredWidth();
        this.mHeight = getMeasuredHeight();
        //正方形棋盘
        int width = Math.min(this.mWidth, this.mHeight);
        singleWidth = width / 8;
        mLedRadius = singleWidth / 3;
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制led矩阵
        drawMatrix(canvas);
        //绘制灯珠
        drawLeds(canvas);
    }

    private void drawLeds(Canvas canvas) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                //计算状态码下的led灯的下标位置
                int index = y * 8 + x;
                boolean b = mStateList.get(index);
                canvas.drawCircle(
                        singleWidth / 2 + singleWidth * x,
                        singleWidth / 2 + singleWidth * y,
                        mLedRadius,
                        b ? mCheckOnPaint : mCheckOffPaint
                );
            }
        }
    }

    /**
     * 绘制矩阵
     *
     * @param canvas
     */
    private void drawMatrix(Canvas canvas) {
        canvas.drawRect(0, 0, mWidth, mWidth, mLinePaint);
        //纵向绘制线框
        for (int i = 1; i < 8; i++) {
            canvas.drawLine(singleWidth * i, 0, singleWidth * i, mWidth, mLinePaint);
        }
        //横向绘制线框
        for (int i = 1; i < 8; i++) {
            canvas.drawLine(0, singleWidth * i, mWidth, singleWidth * i, mLinePaint);
        }
    }

    /**
     * 滑动绘制灯珠的状态
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float eventX = event.getX();
                float eventY = event.getY();
                //滑动计算数据
                handleXYEvent(eventX, eventY);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //松手发送数据
                pushMsg();
                mCacheList.clear();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 需要mSateList状态发送消息给服务器
     */
    private void pushMsg() {
        if (pushCallback != null) {
            pushCallback.pushList(mCacheList);
        }
    }

    /**
     * 处理触摸事件
     *
     * @param eventX
     * @param eventY
     */
    private void handleXYEvent(float eventX, float eventY) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                //计算矩阵范围
                float startX = singleWidth * x;
                float endX = startX + singleWidth;
                float startY = singleWidth * y;
                float endY = startY + singleWidth;
                //判断触发的坐标是否属于矩阵范围内的
                if (eventX > startX && eventX < endX && eventY > startY && eventY < endY) {
                    //表示当前触发的坐标属于该范围内部，需要更新mState对应的状态
                    //计算状态码下的led灯的下标位置
                    int index = y * 8 + x;
                    invalidate();
                    mStateList.set(index, true);
                    if (!mCacheList.contains(index)) {
                        mCacheList.add(index);
                    }
                }
            }
        }
    }

    /**
     * 重新开始刷新初始状态
     */
    public void resetAll() {
        int len = mStateList.size();
        for (int i = 0; i < len; i++) {
            mStateList.set(i, Boolean.FALSE);
        }
        invalidate();
    }

    public interface PushCallback {
        void pushList(List<Integer> list);
    }

    private PushCallback pushCallback;

    public void setPushCallback(PushCallback pushCallback) {
        this.pushCallback = pushCallback;
    }
}
