package com.goorwl.submitbuttonanim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class SubmitButtonView extends View {
    private static final String TAG = "SubmitButtonView";

    private OnViewClickListener mOnViewClickListener;

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        mOnViewClickListener = onViewClickListener;
    }

    // 当前控件限定的长方形区域
    private RectF mRectF = new RectF();

    // 背景颜色
    private int   bg_color = 0xffbc7d53;
    private Paint bgPaint;
    private Paint textPaint;
    private Paint okPaint;

    // 按钮文字
    private String textShow = "点击完成";

    public SubmitButtonView(Context context) {
        this(context, null);
    }

    public SubmitButtonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubmitButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnViewClickListener != null) {
                    mOnViewClickListener.animStart();
                }
            }
        });
        initPaint();
    }

    public interface OnViewClickListener {
        void animStart();

        void animEnd();
    }

    // 初始化画笔
    private void initPaint() {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(bg_color);


        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        okPaint = new Paint();
        okPaint.setStrokeWidth(10);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setAntiAlias(true);
        okPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawText(canvas);
    }

    // 绘制文字
    private void drawText(Canvas canvas) {
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        canvas.drawText(textShow, mRectF.centerX(), (mRectF.bottom - mRectF.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2, textPaint);
    }

    // 绘制背景
    private void drawBg(Canvas canvas) {
        canvas.drawRect(mRectF, bgPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.bottom = h;
        mRectF.right = w;
    }

}
