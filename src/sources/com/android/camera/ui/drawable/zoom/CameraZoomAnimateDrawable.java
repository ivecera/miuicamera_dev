package com.android.camera.ui.drawable.zoom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.camera.Util;
import com.android.camera.ui.ZoomView;
import d.h.a.m;

public class CameraZoomAnimateDrawable extends Drawable implements Animatable {
    /* access modifiers changed from: private */
    public CameraZoomAdjustPaint mCameraZoomAdjustPaint;
    private Context mContext;
    private boolean mIsHorizontal;
    /* access modifiers changed from: private */
    public float mMaxZoomRatio;
    /* access modifiers changed from: private */
    public float mMinZoomRatio;
    /* access modifiers changed from: private */
    public float mSpeed = 0.0f;
    private ValueAnimator mTouchDownAnimator;
    /* access modifiers changed from: private */
    public float mZoomRatio = 1.0f;

    public CameraZoomAnimateDrawable(Context context, boolean z) {
        this.mContext = context;
        this.mIsHorizontal = z;
        this.mCameraZoomAdjustPaint = new CameraZoomAdjustPaint(context, z);
    }

    static /* synthetic */ float access$016(CameraZoomAnimateDrawable cameraZoomAnimateDrawable, float f2) {
        float f3 = cameraZoomAnimateDrawable.mZoomRatio + f2;
        cameraZoomAnimateDrawable.mZoomRatio = f3;
        return f3;
    }

    private boolean isAnimatorRunning(Animator animator) {
        return animator != null && animator.isRunning();
    }

    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        this.mCameraZoomAdjustPaint.drawCanvas(canvas);
        canvas.restore();
    }

    public float getCurrentZoomRatio() {
        return this.mZoomRatio;
    }

    public int getOpacity() {
        return -1;
    }

    public boolean isRunning() {
        ValueAnimator valueAnimator = this.mTouchDownAnimator;
        return valueAnimator != null && valueAnimator.isRunning();
    }

    public void move(Point point) {
        if (isAnimatorRunning(this.mTouchDownAnimator)) {
            this.mSpeed = ((-((float) (this.mIsHorizontal ? -(point.x - (CameraZoomAdjustPaint.SLIDER_HEIGHT / 2)) : point.y - (CameraZoomAdjustPaint.SLIDER_HEIGHT / 2)))) / ((float) CameraZoomAdjustPaint.SLIDER_HEIGHT)) * 0.1f;
            this.mCameraZoomAdjustPaint.move(point);
            invalidateSelf();
        }
    }

    public void reset() {
        this.mSpeed = 0.0f;
        this.mZoomRatio = 1.0f;
        this.mCameraZoomAdjustPaint.reset();
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int i) {
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public void setCurrentZoomRatio(float f2) {
        this.mZoomRatio = f2;
        this.mCameraZoomAdjustPaint.setCurrentZoomRatio(this.mZoomRatio);
    }

    public void start() {
    }

    public boolean startTouchDownAnimation(Point point, final ZoomView.zoomValueChangeListener zoomvaluechangelistener) {
        if (!this.mCameraZoomAdjustPaint.getSliderTouchRect().contains(point.x, point.y)) {
            return false;
        }
        if (isAnimatorRunning(this.mTouchDownAnimator)) {
            this.mTouchDownAnimator.cancel();
            this.mTouchDownAnimator = null;
        }
        this.mTouchDownAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mTouchDownAnimator.setRepeatCount(-1);
        this.mTouchDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.zoom.CameraZoomAnimateDrawable.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CameraZoomAnimateDrawable cameraZoomAnimateDrawable = CameraZoomAnimateDrawable.this;
                CameraZoomAnimateDrawable.access$016(cameraZoomAnimateDrawable, cameraZoomAnimateDrawable.mSpeed);
                CameraZoomAnimateDrawable cameraZoomAnimateDrawable2 = CameraZoomAnimateDrawable.this;
                float unused = cameraZoomAnimateDrawable2.mZoomRatio = Util.clamp(cameraZoomAnimateDrawable2.mZoomRatio, CameraZoomAnimateDrawable.this.mMinZoomRatio, CameraZoomAnimateDrawable.this.mMaxZoomRatio);
                CameraZoomAnimateDrawable.this.mCameraZoomAdjustPaint.setCurrentZoomRatio(CameraZoomAnimateDrawable.this.mZoomRatio);
                ZoomView.zoomValueChangeListener zoomvaluechangelistener = zoomvaluechangelistener;
                if (zoomvaluechangelistener != null) {
                    zoomvaluechangelistener.onZoomValueChanged(CameraZoomAnimateDrawable.this.mZoomRatio);
                }
                CameraZoomAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mTouchDownAnimator.start();
        return true;
    }

    public void stop() {
    }

    public void stopTouchUpAnimation() {
        if (isAnimatorRunning(this.mTouchDownAnimator)) {
            this.mTouchDownAnimator.cancel();
            this.mTouchDownAnimator = null;
        }
        this.mCameraZoomAdjustPaint.startSliderBackAnimation();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(300L);
        ofFloat.setInterpolator(new m() {
            /* class com.android.camera.ui.drawable.zoom.CameraZoomAnimateDrawable.AnonymousClass2 */

            @Override // d.h.a.m
            public float getInterpolation(float f2) {
                float interpolation = super.getInterpolation(f2);
                CameraZoomAnimateDrawable.this.mCameraZoomAdjustPaint.updateSliderPositionForBackAnim(interpolation);
                CameraZoomAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        this.mSpeed = 0.0f;
        ofFloat.start();
    }

    public void updateZoomRatio(float f2, float f3) {
        this.mMinZoomRatio = f2;
        this.mMaxZoomRatio = f3;
        this.mCameraZoomAdjustPaint.setZoomRange(this.mMinZoomRatio, this.mMaxZoomRatio);
    }
}
