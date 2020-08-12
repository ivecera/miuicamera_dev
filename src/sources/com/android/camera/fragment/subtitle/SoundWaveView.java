package com.android.camera.fragment.subtitle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.android.camera.R;

public class SoundWaveView extends View {
    private static final int COUNT_LINE = 5;
    private static final int DEFAULT_DEGREES = 0;
    private static final int INVALIDATE_SOUND_VIEW_MSG = 100;
    private float LINE_SPACE;
    private float LINE_WIDTH;
    /* access modifiers changed from: private */
    public float[] curHeights;
    /* access modifiers changed from: private */
    @SuppressLint({"HandlerLeak"})
    public Handler handler;
    private float[] intervalHeights;
    /* access modifiers changed from: private */
    public boolean isAnimationProcessing;
    private boolean[] isElongation;
    private int mDegrees;
    private float[] mMaxLineHeights;
    /* access modifiers changed from: private */
    public float[] mMinLineHeights;
    private Paint mPaint;
    private RectF mRect;

    public SoundWaveView(Context context) {
        this(context, null, -1);
    }

    public SoundWaveView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SoundWaveView);
        this.mDegrees = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
    }

    public SoundWaveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAnimationProcessing = false;
        this.mMaxLineHeights = new float[]{24.0f, 17.0f, 26.0f, 17.0f, 24.0f};
        this.mMinLineHeights = new float[]{8.0f, 15.0f, 8.0f, 15.0f, 8.0f};
        this.intervalHeights = new float[]{0.8f, 0.1f, 0.9f, 0.1f, 0.8f};
        this.curHeights = new float[]{8.0f, 17.0f, 26.0f, 17.0f, 8.0f};
        this.isElongation = new boolean[]{true, true, true, true, true};
        this.handler = new Handler() {
            /* class com.android.camera.fragment.subtitle.SoundWaveView.AnonymousClass1 */

            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 100) {
                    return;
                }
                if (SoundWaveView.this.isAnimationProcessing || SoundWaveView.this.curHeights[0] > SoundWaveView.this.mMinLineHeights[0]) {
                    SoundWaveView.this.invalidate();
                    SoundWaveView.this.handler.sendEmptyMessageDelayed(100, 30);
                    return;
                }
                SoundWaveView.this.handler.removeCallbacksAndMessages(null);
            }
        };
        init();
    }

    private void init() {
        this.LINE_WIDTH = 6.0f;
        this.LINE_SPACE = 10.0f;
        this.mPaint = new Paint(1);
        this.mPaint.setColor(-1);
        this.mPaint.setStrokeWidth(this.LINE_WIDTH);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mRect = new RectF();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.handler.removeCallbacksAndMessages(null);
        this.isAnimationProcessing = false;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f2 = this.LINE_WIDTH + this.LINE_SPACE;
        if (this.mRect == null) {
            this.mRect = new RectF();
        }
        for (int i = 0; i < 5; i++) {
            int i2 = this.mDegrees;
            if (i2 == 90 || i2 == 270) {
                float width = ((float) getWidth()) / 2.0f;
                float height = (((((float) getHeight()) - (5.0f * f2)) + this.LINE_SPACE) / 2.0f) + (((float) i) * f2);
                RectF rectF = this.mRect;
                float[] fArr = this.curHeights;
                rectF.left = width - fArr[i];
                rectF.top = height;
                rectF.right = width + fArr[i];
                rectF.bottom = height + 6.0f;
                canvas.drawRoundRect(rectF, 15.0f, 15.0f, this.mPaint);
            } else {
                float width2 = ((((float) getWidth()) - (5.0f * f2)) + this.LINE_SPACE) / 2.0f;
                float height2 = ((float) getHeight()) / 2.0f;
                float f3 = (((float) i) * f2) + width2;
                Log.d("", "onDraw: startX " + width2 + " getWidth " + getWidth() + " unitWidth " + f2);
                RectF rectF2 = this.mRect;
                rectF2.left = f3;
                float[] fArr2 = this.curHeights;
                rectF2.top = fArr2[i] + height2;
                rectF2.right = f3 + 6.0f;
                rectF2.bottom = height2 - fArr2[i];
                canvas.drawRoundRect(rectF2, 15.0f, 15.0f, this.mPaint);
            }
            boolean[] zArr = this.isElongation;
            if (zArr[i]) {
                float[] fArr3 = this.curHeights;
                fArr3[i] = fArr3[i] + this.intervalHeights[i];
                float f4 = fArr3[i];
                float[] fArr4 = this.mMaxLineHeights;
                if (f4 >= fArr4[i]) {
                    zArr[i] = false;
                    fArr3[i] = fArr4[i];
                    if (i == 0) {
                        int i3 = i + 1;
                        zArr[i3] = true;
                        int i4 = i + 2;
                        zArr[i4] = true;
                        float[] fArr5 = this.mMinLineHeights;
                        fArr3[i4] = fArr5[i3];
                        fArr3[i4] = fArr5[i4];
                    }
                }
            } else {
                float[] fArr6 = this.curHeights;
                fArr6[i] = fArr6[i] - this.intervalHeights[i];
                float f5 = fArr6[i];
                float[] fArr7 = this.mMinLineHeights;
                if (f5 <= fArr7[i]) {
                    fArr6[i] = fArr7[i];
                    zArr[i] = true;
                    if (i == 0) {
                        int i5 = i + 1;
                        zArr[i5] = false;
                        int i6 = i + 2;
                        zArr[i6] = false;
                        float[] fArr8 = this.mMaxLineHeights;
                        fArr6[i5] = fArr8[i5];
                        fArr6[i6] = fArr8[i6];
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public void resetAnimation() {
        this.isAnimationProcessing = false;
    }

    public void startAnimation() {
        if (!this.isAnimationProcessing) {
            this.isAnimationProcessing = true;
            if (this.curHeights[0] <= this.mMinLineHeights[0]) {
                this.handler.removeCallbacksAndMessages(null);
                this.handler.sendEmptyMessage(100);
            }
        }
    }
}
