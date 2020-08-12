package com.android.camera.ui.drawable;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

public abstract class CameraPaintBase {
    public static int ALPHA_HINT = 102;
    public static int ALPHA_OPAQUE = 255;
    public static int ALPHA_OUTSTANDING = 255;
    public boolean isClockwise = true;
    public boolean isRecording;
    public float mBaseRadius;
    public int mCurrentAlpha;
    @ColorInt
    public int mCurrentColor;
    public float mCurrentStrokeWidth;
    public float mCurrentWidthPercent;
    protected int mDstAlpha;
    private float mDstBaseRadius;
    protected int mDstColor;
    private float mDstMiddleX;
    private float mDstMiddleY;
    protected float mDstStrokeWidth;
    protected float mDstWidthPercent;
    public float mMiddleX;
    public float mMiddleY;
    public Paint mPaint = new Paint();
    public int mSrcAlpha;
    private float mSrcBaseRadius;
    public int mSrcColor;
    private float mSrcMiddleX;
    private float mSrcMiddleY;
    public float mSrcStrokeWidth;
    public float mSrcWidthPercent;
    public boolean needZero = true;
    public float timeAngle;
    public int visible;

    public CameraPaintBase(Context context) {
        initPaint(context);
    }

    /* access modifiers changed from: protected */
    public abstract void draw(Canvas canvas);

    public void drawCanvas(Canvas canvas) {
        if (!isHide()) {
            draw(canvas);
        }
    }

    public int getVisible() {
        return this.visible;
    }

    /* access modifiers changed from: protected */
    public abstract void initPaint(Context context);

    /* access modifiers changed from: protected */
    public boolean isHide() {
        return this.visible == 8;
    }

    public void resetRecordingState() {
        this.timeAngle = 0.0f;
        this.isClockwise = true;
        this.isRecording = false;
        this.needZero = true;
    }

    public void reverseClock() {
        this.isClockwise = !this.isClockwise;
        if (this.isClockwise) {
            this.mCurrentAlpha = ALPHA_HINT;
        } else {
            this.mCurrentAlpha = ALPHA_OUTSTANDING;
        }
    }

    public CameraPaintBase setCurrentAlpha(int i) {
        this.mCurrentAlpha = i;
        this.mPaint.setAlpha(i);
        return this;
    }

    public CameraPaintBase setCurrentColor(int i) {
        this.mCurrentColor = i;
        this.mPaint.setColor(i);
        return this;
    }

    public CameraPaintBase setCurrentStrokeWidth(float f2) {
        this.mCurrentStrokeWidth = f2;
        this.mPaint.setStrokeWidth(f2);
        return this;
    }

    public void setCurrentValues(float f2, @ColorInt int i, int i2, float f3) {
        this.mCurrentWidthPercent = f2;
        this.mCurrentColor = i;
        this.mCurrentAlpha = i2;
        this.mCurrentStrokeWidth = f3;
        this.mPaint.setColor(this.mCurrentColor);
        this.mPaint.setAlpha(this.mCurrentAlpha);
        this.mPaint.setStrokeWidth(this.mCurrentStrokeWidth);
    }

    public CameraPaintBase setCurrentWidthPercent(float f2) {
        this.mCurrentWidthPercent = f2;
        return this;
    }

    public void setMiddle(float f2, float f3, float f4) {
        this.mMiddleX = f2;
        this.mSrcMiddleX = f2;
        this.mDstMiddleX = f2;
        this.mMiddleY = f3;
        this.mSrcMiddleY = f3;
        this.mDstMiddleY = f3;
        this.mBaseRadius = f4;
        this.mSrcBaseRadius = f4;
        this.mDstBaseRadius = f4;
    }

    public void setResult() {
        this.mCurrentWidthPercent = this.mDstWidthPercent;
        this.mCurrentColor = this.mDstColor;
        this.mCurrentAlpha = this.mDstAlpha;
        this.mCurrentStrokeWidth = this.mDstStrokeWidth;
        this.mMiddleX = this.mDstMiddleX;
        this.mMiddleY = this.mDstMiddleY;
        this.mBaseRadius = this.mDstBaseRadius;
        this.mPaint.setColor(this.mCurrentColor);
        this.mPaint.setAlpha(this.mCurrentAlpha);
        this.mPaint.setStrokeWidth(this.mCurrentStrokeWidth);
        this.mSrcWidthPercent = this.mCurrentWidthPercent;
        this.mSrcColor = this.mCurrentColor;
        this.mSrcAlpha = this.mCurrentAlpha;
        this.mSrcStrokeWidth = this.mCurrentStrokeWidth;
        this.mSrcMiddleX = this.mMiddleX;
        this.mSrcMiddleY = this.mMiddleY;
        this.mSrcBaseRadius = this.mBaseRadius;
    }

    public CameraPaintBase setTargetAlpha(int i) {
        this.mDstAlpha = i;
        this.mSrcAlpha = this.mCurrentAlpha;
        return this;
    }

    public CameraPaintBase setTargetColor(int i) {
        this.mDstColor = i;
        this.mSrcColor = this.mCurrentColor;
        return this;
    }

    public void setTargetMiddle(float f2, float f3, float f4) {
        this.mDstMiddleX = f2;
        this.mDstMiddleY = f3;
        this.mDstBaseRadius = f4;
        this.mSrcMiddleX = this.mMiddleX;
        this.mSrcMiddleY = this.mMiddleY;
        this.mSrcBaseRadius = this.mBaseRadius;
    }

    public CameraPaintBase setTargetStrokeWidth(float f2) {
        this.mDstStrokeWidth = f2;
        this.mSrcStrokeWidth = this.mCurrentStrokeWidth;
        return this;
    }

    public void setTargetValues(float f2, @ColorInt int i, int i2, float f3) {
        this.mDstWidthPercent = f2;
        this.mDstColor = i;
        this.mDstAlpha = i2;
        this.mDstStrokeWidth = f3;
        this.mSrcWidthPercent = this.mCurrentWidthPercent;
        this.mSrcColor = this.mCurrentColor;
        this.mSrcAlpha = this.mCurrentAlpha;
        this.mSrcStrokeWidth = this.mCurrentStrokeWidth;
    }

    public CameraPaintBase setTargetWidthPercent(float f2) {
        this.mDstWidthPercent = f2;
        this.mSrcWidthPercent = this.mCurrentWidthPercent;
        return this;
    }

    public void setVisible(int i) {
        this.visible = i;
    }

    public void updateValue(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        float f3 = this.mDstMiddleX;
        float f4 = this.mSrcMiddleX;
        float f5 = this.mDstMiddleY;
        float f6 = this.mSrcMiddleY;
        float f7 = this.mDstBaseRadius;
        float f8 = this.mSrcBaseRadius;
        this.mMiddleX = f4 + ((f3 - f4) * f2);
        this.mMiddleY = f6 + ((f5 - f6) * f2);
        this.mBaseRadius = f8 + ((f7 - f8) * f2);
        float f9 = this.mDstWidthPercent;
        float f10 = this.mSrcWidthPercent;
        this.mCurrentWidthPercent = f10 + ((f9 - f10) * f2);
        float f11 = this.mDstStrokeWidth;
        float f12 = this.mSrcStrokeWidth;
        this.mCurrentStrokeWidth = f12 + ((f11 - f12) * f2);
        this.mPaint.setStrokeWidth(this.mCurrentStrokeWidth);
        if (this.mSrcColor != this.mDstColor) {
            this.mCurrentColor = ((Integer) new ArgbEvaluator().evaluate(f2, Integer.valueOf(this.mSrcColor), Integer.valueOf(this.mDstColor))).intValue();
            this.mPaint.setColor(this.mCurrentColor);
        }
        int i = this.mSrcAlpha;
        int i2 = this.mDstAlpha;
        if (i != i2) {
            this.mCurrentAlpha = Math.round(((float) i) + (((float) (i2 - i)) * f2));
            this.mPaint.setAlpha(this.mCurrentAlpha);
        }
        if (f2 == 1.0f) {
            setResult();
        }
    }
}
