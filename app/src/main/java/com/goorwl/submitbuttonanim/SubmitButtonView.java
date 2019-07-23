package com.goorwl.submitbuttonanim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

    // 动画持续时间
    private int   duration     = 1000;
    // 保存初始宽度
    private int   viewWidth;
    // 上移距离
    private float moveDistance = 150f;

    // 圆角角度
    private float roundRadius = 0;

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
        canvas.drawRoundRect(mRectF, roundRadius, roundRadius, bgPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        mRectF.bottom = h;
        mRectF.right = w;
    }

    // 开始绘制动画，文字透明度消失
    public void rectToOval() {
        final float   vRate         = mRectF.centerX() - mRectF.centerY();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(vRate);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.e(TAG, "onAnimationUpdate: " + value);
                mRectF.left = value;
                roundRadius = value;
                mRectF.right = viewWidth - value;
                Float textAlpha = 255 - value / vRate * 255;
                textPaint.setAlpha(textAlpha.intValue());
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                moveUp();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    // 控件上移
    private void moveUp() {
        float tY = getTranslationY(); // 当前控件在布局的位置
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", tY, tY - moveDistance);
        translationY.setDuration(duration);
        translationY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                drawOk();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        translationY.start();
    }

    // 开始画对勾
    private void drawOk() {
    }

}
