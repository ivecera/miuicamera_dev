package com.android.camera.ui.zoom;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;

public class ScaleGestureDetector {
    private static final String TAG = "ScaleGestureDetector";
    private float mCurrSpan;
    private float mCurrSpanX;
    private float mCurrSpanY;
    private long mCurrTime;
    private float mFocusX;
    private float mFocusY;
    private boolean mInProgress;
    private float mInitialSpan;
    private final OnScaleGestureListener mListener;
    private int mMinSpan;
    private float mPrevSpan;
    private float mPrevSpanX;
    private float mPrevSpanY;
    private long mPrevTime;
    private int mSpanSlop;

    public interface OnScaleGestureListener {
        boolean onScale(ScaleGestureDetector scaleGestureDetector);

        boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector);

        void onScaleEnd(ScaleGestureDetector scaleGestureDetector);
    }

    public static class SimpleOnScaleGestureListener implements OnScaleGestureListener {
        @Override // com.android.camera.ui.zoom.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override // com.android.camera.ui.zoom.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override // com.android.camera.ui.zoom.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        }
    }

    public ScaleGestureDetector(Context context, OnScaleGestureListener onScaleGestureListener) {
        this.mListener = onScaleGestureListener;
        this.mSpanSlop = ViewConfiguration.get(context).getScaledTouchSlop() * 2;
        this.mMinSpan = CompatibilityUtils.getScaledMinimumScalingSpan(context);
    }

    public float getCurrentSpan() {
        return this.mCurrSpan;
    }

    public float getCurrentSpanX() {
        return this.mCurrSpanX;
    }

    public float getCurrentSpanY() {
        return this.mCurrSpanY;
    }

    public long getEventTime() {
        return this.mCurrTime;
    }

    public float getFocusX() {
        return this.mFocusX;
    }

    public float getFocusY() {
        return this.mFocusY;
    }

    public float getPreviousSpan() {
        return this.mPrevSpan;
    }

    public float getPreviousSpanX() {
        return this.mPrevSpanX;
    }

    public float getPreviousSpanY() {
        return this.mPrevSpanY;
    }

    public float getScaleFactor() {
        float f2 = this.mPrevSpan;
        if (f2 > 0.0f) {
            return this.mCurrSpan / f2;
        }
        return 1.0f;
    }

    /* access modifiers changed from: protected */
    public int getScaledMinimumScalingSpan() {
        return this.mMinSpan;
    }

    public long getTimeDelta() {
        return this.mCurrTime - this.mPrevTime;
    }

    public boolean isInProgress() {
        return this.mInProgress;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mCurrTime = motionEvent.getEventTime();
        int actionMasked = motionEvent.getActionMasked();
        int pointerCount = motionEvent.getPointerCount();
        boolean z = actionMasked == 1 || actionMasked == 3;
        if (actionMasked == 0 || z) {
            if (this.mInProgress) {
                this.mListener.onScaleEnd(this);
                this.mInProgress = false;
                this.mInitialSpan = 0.0f;
            }
            if (z) {
                return true;
            }
        }
        boolean z2 = actionMasked == 0 || actionMasked == 6 || actionMasked == 5;
        boolean z3 = actionMasked == 6;
        int actionIndex = z3 ? motionEvent.getActionIndex() : -1;
        int i = z3 ? pointerCount - 1 : pointerCount;
        float f2 = 0.0f;
        float f3 = 0.0f;
        for (int i2 = 0; i2 < pointerCount; i2++) {
            if (actionIndex != i2) {
                f2 += motionEvent.getX(i2);
                f3 += motionEvent.getY(i2);
            }
        }
        float f4 = (float) i;
        float f5 = f2 / f4;
        float f6 = f3 / f4;
        float f7 = 0.0f;
        float f8 = 0.0f;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            if (actionIndex != i3) {
                f7 += Math.abs(motionEvent.getX(i3) - f5);
                f8 += Math.abs(motionEvent.getY(i3) - f6);
            }
        }
        float f9 = (f7 / f4) * 2.0f;
        float f10 = (f8 / f4) * 2.0f;
        float hypot = (float) Math.hypot((double) f9, (double) f10);
        boolean z4 = this.mInProgress;
        this.mFocusX = f5;
        this.mFocusY = f6;
        if (z4 && z2) {
            this.mListener.onScaleEnd(this);
            this.mInProgress = false;
            this.mInitialSpan = hypot;
        }
        if (z2) {
            this.mCurrSpanX = f9;
            this.mPrevSpanX = f9;
            this.mCurrSpanY = f10;
            this.mPrevSpanY = f10;
            this.mCurrSpan = hypot;
            this.mPrevSpan = hypot;
            this.mInitialSpan = hypot;
        }
        int scaledMinimumScalingSpan = getScaledMinimumScalingSpan();
        if (!this.mInProgress && hypot >= ((float) scaledMinimumScalingSpan) && (z4 || Math.abs(hypot - this.mInitialSpan) > ((float) this.mSpanSlop))) {
            this.mCurrSpanX = f9;
            this.mPrevSpanX = f9;
            this.mCurrSpanY = f10;
            this.mPrevSpanY = f10;
            this.mCurrSpan = hypot;
            this.mPrevSpan = hypot;
            this.mPrevTime = this.mCurrTime;
            this.mInProgress = this.mListener.onScaleBegin(this);
        }
        if (actionMasked == 2) {
            this.mCurrSpanX = f9;
            this.mCurrSpanY = f10;
            this.mCurrSpan = hypot;
            if (this.mInProgress ? this.mListener.onScale(this) : true) {
                this.mPrevSpanX = this.mCurrSpanX;
                this.mPrevSpanY = this.mCurrSpanY;
                this.mPrevSpan = this.mCurrSpan;
                this.mPrevTime = this.mCurrTime;
            }
        }
        return true;
    }
}
