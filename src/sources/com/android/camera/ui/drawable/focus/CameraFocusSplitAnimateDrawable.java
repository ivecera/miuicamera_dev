package com.android.camera.ui.drawable.focus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.PathInterpolator;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.ui.drawable.CameraPaintBase;
import d.h.a.E;
import d.h.a.k;
import d.h.a.m;

public class CameraFocusSplitAnimateDrawable extends Drawable implements Animatable {
    public static final int BIG_ALPHA = 205;
    public static int BIG_RADIUS = 0;
    public static final int CENTER_ALPHA = 240;
    public static int CENTER_BASE_RADIUS = 0;
    private static final float CENTER_DOWN_SMALL = 0.85f;
    private static final float CENTER_FOCUSING_BIG = 1.0f;
    private static final float CENTER_FOCUSING_SMALL = 0.95f;
    private static final float CIRCLE_DOWN_BIG = 1.1f;
    private static final float CIRCLE_DOWN_SMALL = 0.94f;
    private static final int LOCK_PAINT_COLOR = 16765730;
    private static final int STATE_COMBO = 1;
    private static final int STATE_SPLIT = 2;
    private static final String TAG = "CameraFocusSplitAnimateDrawable";
    private Bitmap mCaptureBitmap;
    private Bitmap mExposureBitmap;
    private CameraIndicatorState mExposureIndicatorState;
    private boolean mExposureLocked;
    private Bitmap mExposureLockedMap;
    private int mExposureX;
    private int mExposureY;
    private CameraIndicatorState mFocusIndicatorState;
    private boolean mFocusLocked;
    private Bitmap mFocusLockedBitmap;
    private String mFocusMode = "auto";
    private int mFocusX;
    private int mFocusY;
    private ValueAnimator mFocusingAnimator;
    /* access modifiers changed from: private */
    public boolean mIsTouchFocus;
    private int mOrientation = 0;
    private CameraFocusPaintCenterIndicator mPaintExposureCenterIndicator;
    private CameraFocusPaintBigCircle mPaintExposureCircle;
    /* access modifiers changed from: private */
    public CameraFocusPaintCenterIndicator mPaintFocusCenterIndicator;
    private CameraFocusPaintBigCircle mPaintFocusCircle;
    private ValueAnimator mResetCenterAnimator;
    /* access modifiers changed from: private */
    public CameraFocusPaintBigSplitCircle mSplitBigCircle;
    private int mState;
    private AnimatorSet mSuccessAnimatorSet;
    /* access modifiers changed from: private */
    public int mSuccessFlag;
    /* access modifiers changed from: private */
    public ValueAnimator mTouchDownAnimator;
    /* access modifiers changed from: private */
    public int pendingState;

    public CameraFocusSplitAnimateDrawable(Context context) {
        BIG_RADIUS = context.getResources().getDimensionPixelSize(R.dimen.focus_big_radius);
        CENTER_BASE_RADIUS = context.getResources().getDimensionPixelSize(R.dimen.focus_center_base_radius);
        this.mSplitBigCircle = new CameraFocusPaintBigSplitCircle(context);
        this.mPaintFocusCircle = new CameraFocusPaintBigCircle(context);
        this.mPaintExposureCircle = new CameraFocusPaintBigCircle(context);
        this.mPaintFocusCenterIndicator = new CameraFocusPaintCenterIndicator(context);
        this.mPaintExposureCenterIndicator = new CameraFocusPaintCenterIndicator(context);
        this.mSplitBigCircle.setTargetValues(1.0f, -1, 205, (float) Util.dpToPixel(1.0f));
        this.mPaintFocusCircle.setTargetValues(1.0f, -1, 205, (float) Util.dpToPixel(1.0f));
        this.mPaintExposureCircle.setTargetValues(1.0f, Color.rgb(255, 204, 0), 205, (float) Util.dpToPixel(1.0f));
        this.mPaintFocusCenterIndicator.setTargetValues(1.0f, -1, 240, (float) Util.dpToPixel(1.3f));
        this.mPaintExposureCenterIndicator.setTargetValues(1.0f, Color.rgb(255, 204, 0), 240, (float) Util.dpToPixel(1.3f));
        this.mSplitBigCircle.setResult();
        this.mPaintFocusCircle.setResult();
        this.mPaintExposureCircle.setResult();
        this.mPaintFocusCenterIndicator.setResult();
        this.mPaintExposureCenterIndicator.setResult();
        this.mFocusLocked = false;
        this.mExposureLocked = false;
        this.mState = 1;
    }

    private void cancelSuccessAnimation() {
        AnimatorSet animatorSet = this.mSuccessAnimatorSet;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.mSuccessAnimatorSet.cancel();
        }
        this.mSuccessAnimatorSet = null;
    }

    private void drawCombo(@NonNull Canvas canvas) {
        canvas.save();
        this.mSplitBigCircle.draw(canvas);
        canvas.restore();
        canvas.save();
        this.mPaintFocusCenterIndicator.draw(canvas);
        canvas.restore();
    }

    private void drawSplit(@NonNull Canvas canvas) {
        canvas.save();
        this.mPaintExposureCircle.draw(canvas);
        this.mPaintExposureCenterIndicator.draw(canvas);
        canvas.restore();
        canvas.save();
        this.mPaintFocusCircle.draw(canvas);
        this.mPaintFocusCenterIndicator.draw(canvas);
        canvas.restore();
    }

    private boolean isAnimatorRunning(Animator animator) {
        return animator != null && animator.isRunning();
    }

    private void start3ALockSuccessAnimation(final CameraPaintBase cameraPaintBase, final CameraFocusPaintCenterIndicator cameraFocusPaintCenterIndicator) {
        Log.d(TAG, "start3ALockSuccessAnimation");
        this.mSuccessAnimatorSet = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(200L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass7 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                cameraPaintBase.updateValue(floatValue);
                cameraFocusPaintCenterIndicator.updateValue(floatValue);
                CameraFocusSplitAnimateDrawable.this.invalidateSelf();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass8 */

            public void onAnimationStart(Animator animator) {
                cameraPaintBase.setCurrentWidthPercent(1.0f).setTargetWidthPercent(1.35f);
                cameraFocusPaintCenterIndicator.setCurrentWidthPercent(1.0f).setTargetWidthPercent(0.9f);
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setDuration(200L);
        ofFloat2.setStartDelay(200);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass9 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                cameraPaintBase.updateValue(floatValue);
                cameraFocusPaintCenterIndicator.updateValue(floatValue);
                CameraFocusSplitAnimateDrawable.this.invalidateSelf();
            }
        });
        ofFloat2.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass10 */

            public void onAnimationStart(Animator animator) {
                cameraPaintBase.setCurrentWidthPercent(1.35f).setTargetWidthPercent(1.0f);
                cameraFocusPaintCenterIndicator.setCurrentWidthPercent(0.9f).setTargetWidthPercent(0.5f);
                cameraFocusPaintCenterIndicator.setCurrentAlpha(255).setTargetAlpha(0);
            }
        });
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat3.setDuration(300L);
        ofFloat3.setStartDelay(400);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass11 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                cameraFocusPaintCenterIndicator.updateValue(((Float) valueAnimator.getAnimatedValue()).floatValue());
                CameraFocusSplitAnimateDrawable.this.invalidateSelf();
            }
        });
        ofFloat3.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass12 */

            public void onAnimationStart(Animator animator) {
                cameraFocusPaintCenterIndicator.setCurrentColor(CameraFocusSplitAnimateDrawable.LOCK_PAINT_COLOR).setTargetColor(CameraFocusSplitAnimateDrawable.LOCK_PAINT_COLOR);
                cameraFocusPaintCenterIndicator.setCenterFlag(2);
                cameraFocusPaintCenterIndicator.setCurrentWidthPercent(1.0f).setTargetWidthPercent(1.0f);
                cameraFocusPaintCenterIndicator.setCurrentAlpha(0).setTargetAlpha(255);
            }
        });
        this.mSuccessAnimatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        this.mSuccessAnimatorSet.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 0.1f));
        this.mSuccessAnimatorSet.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass13 */

            public void onAnimationEnd(Animator animator) {
                CameraFocusSplitAnimateDrawable.this.invalidateSelf();
                super.onAnimationEnd(animator);
            }
        });
        this.mSuccessAnimatorSet.start();
    }

    /* access modifiers changed from: private */
    public void startFocusingAnimation() {
        Log.d(TAG, "startFocusingAnimation");
        cancelFocusingAnimation();
        this.mFocusingAnimator = ValueAnimator.ofFloat(1.0f, CENTER_FOCUSING_SMALL);
        this.mFocusingAnimator.setInterpolator(new E());
        this.mFocusingAnimator.setDuration(200L);
        this.mFocusingAnimator.setRepeatMode(2);
        this.mFocusingAnimator.setRepeatCount(-1);
        this.mFocusingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass3 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ((CameraPaintBase) CameraFocusSplitAnimateDrawable.this.mPaintFocusCenterIndicator).mCurrentWidthPercent = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                CameraFocusSplitAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mFocusingAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass4 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                if (((CameraPaintBase) CameraFocusSplitAnimateDrawable.this.mPaintFocusCenterIndicator).mCurrentWidthPercent == 1.0f) {
                    CameraFocusSplitAnimateDrawable.this.cancelFocusingAnimation();
                    int access$300 = CameraFocusSplitAnimateDrawable.this.pendingState;
                    if (access$300 == 2) {
                        CameraFocusSplitAnimateDrawable cameraFocusSplitAnimateDrawable = CameraFocusSplitAnimateDrawable.this;
                        cameraFocusSplitAnimateDrawable.startFocusSuccessAnimation(cameraFocusSplitAnimateDrawable.mSuccessFlag, CameraFocusSplitAnimateDrawable.this.mIsTouchFocus, 3);
                    } else if (access$300 == 4) {
                        CameraFocusSplitAnimateDrawable.this.startFocusFailAnimation();
                    }
                }
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        this.mFocusingAnimator.start();
    }

    private void startNormalFocusSuccessAnimation(final CameraPaintBase cameraPaintBase, final CameraFocusPaintCenterIndicator cameraFocusPaintCenterIndicator) {
        Log.d(TAG, "startNormalFocusSuccessAnimation");
        cameraPaintBase.setTargetWidthPercent(1.0f);
        cameraFocusPaintCenterIndicator.setTargetWidthPercent(CENTER_DOWN_SMALL);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(200L);
        ofFloat.setInterpolator(new k() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass5 */

            @Override // d.h.a.k
            public float getInterpolation(float f2) {
                float interpolation = super.getInterpolation(f2);
                cameraPaintBase.updateValue(interpolation);
                cameraFocusPaintCenterIndicator.updateValue(interpolation);
                CameraFocusSplitAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        ofFloat.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass6 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                CameraFocusSplitAnimateDrawable.this.invalidateSelf();
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        ofFloat.start();
    }

    public void cancelFocusingAnimation() {
        ValueAnimator valueAnimator = this.mFocusingAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mFocusingAnimator.cancel();
        }
        this.mFocusingAnimator = null;
    }

    public void cancelResetCenter() {
        ValueAnimator valueAnimator = this.mResetCenterAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mResetCenterAnimator.cancel();
        }
    }

    public void draw(@NonNull Canvas canvas) {
        if (this.mState == 1) {
            drawCombo(canvas);
        } else {
            drawSplit(canvas);
        }
    }

    public int getOpacity() {
        return -1;
    }

    public boolean isExposureLocked() {
        if (this.mState != 2 || !this.mExposureLocked) {
            return this.mState == 1 && this.mSuccessFlag == 5;
        }
        return true;
    }

    public boolean isFocusLocked() {
        if (this.mState != 2 || !this.mFocusLocked) {
            return this.mState == 1 && this.mSuccessFlag == 5;
        }
        return true;
    }

    public boolean isFocusOrExposureLocked() {
        return this.mState == 2 && (this.mFocusLocked || this.mExposureLocked);
    }

    public boolean isRunning() {
        return false;
    }

    public void reset() {
        this.mSuccessFlag = 0;
        this.mFocusLocked = false;
        this.mExposureLocked = false;
        this.mState = 1;
        this.mPaintFocusCenterIndicator.setCenterFlag(1);
        this.mPaintExposureCenterIndicator.setIndicatorData(this.mExposureIndicatorState, this.mExposureBitmap);
    }

    public void setAeAfLockedBitmap(Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3) {
        this.mExposureIndicatorState = new CameraIndicatorState();
        CameraIndicatorState cameraIndicatorState = this.mExposureIndicatorState;
        cameraIndicatorState.mCenterFlag = 2;
        this.mPaintExposureCenterIndicator.setIndicatorData(cameraIndicatorState, bitmap3);
        this.mFocusIndicatorState = new CameraIndicatorState();
        CameraIndicatorState cameraIndicatorState2 = this.mFocusIndicatorState;
        cameraIndicatorState2.mCenterFlag = 1;
        this.mPaintFocusCenterIndicator.setIndicatorData(cameraIndicatorState2, bitmap);
        this.mCaptureBitmap = bitmap;
        this.mFocusLockedBitmap = bitmap2;
        this.mExposureBitmap = bitmap3;
    }

    public void setAlpha(int i) {
    }

    public void setCenter(int i, int i2) {
        this.mExposureX = i;
        this.mFocusX = i;
        this.mExposureY = i2;
        this.mFocusY = i2;
        this.mState = 1;
        this.mSplitBigCircle.setMiddle((float) i, (float) i2, (float) BIG_RADIUS);
        setExposureCenter(i, i2);
        setFocusCenter(i, i2);
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public void setExposureCenter(int i, int i2) {
        this.mExposureX = i;
        this.mExposureY = i2;
        float f2 = (float) i;
        float f3 = (float) i2;
        this.mPaintExposureCircle.setMiddle(f2, f3, (float) BIG_RADIUS);
        this.mPaintExposureCenterIndicator.setMiddle(f2, f3, (float) CENTER_BASE_RADIUS);
        invalidateSelf();
    }

    public void setFocusCenter(int i, int i2) {
        this.mFocusX = i;
        this.mFocusY = i2;
        float f2 = (float) i;
        float f3 = (float) i2;
        this.mPaintFocusCircle.setMiddle(f2, f3, (float) BIG_RADIUS);
        this.mPaintFocusCenterIndicator.setMiddle(f2, f3, (float) CENTER_BASE_RADIUS);
        invalidateSelf();
    }

    public void setFocusMode(String str) {
        this.mFocusMode = str;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    public void setState(int i) {
        if (this.mState != i && i == 2) {
            this.mState = i;
        }
    }

    public void start() {
    }

    public void startFocusFailAnimation() {
        cancelFocusingAnimation();
        if (isAnimatorRunning(this.mTouchDownAnimator)) {
            this.pendingState = 3;
        } else if (isAnimatorRunning(this.mFocusingAnimator)) {
            this.pendingState = 4;
        } else {
            this.mSplitBigCircle.setTargetWidthPercent(1.0f).setTargetAlpha(0);
            this.mPaintFocusCenterIndicator.setTargetWidthPercent(CENTER_FOCUSING_SMALL).setTargetAlpha(0);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setDuration(200L);
            ofFloat.setInterpolator(new k() {
                /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass14 */

                @Override // d.h.a.k
                public float getInterpolation(float f2) {
                    float interpolation = super.getInterpolation(f2);
                    CameraFocusSplitAnimateDrawable.this.mSplitBigCircle.updateValue(interpolation);
                    CameraFocusSplitAnimateDrawable.this.mPaintFocusCenterIndicator.updateValue(interpolation);
                    CameraFocusSplitAnimateDrawable.this.invalidateSelf();
                    return interpolation;
                }
            });
            ofFloat.start();
        }
    }

    public void startFocusSuccessAnimation(int i, boolean z, int i2) {
        Log.d(TAG, "startFocusSuccessAnimation");
        cancelFocusingAnimation();
        this.mSuccessFlag = i;
        if (this.mSuccessFlag == 5) {
            this.mState = 1;
        }
        this.mIsTouchFocus = z;
        if (!this.mIsTouchFocus) {
            this.mSuccessFlag = 1;
        }
        if (isAnimatorRunning(this.mTouchDownAnimator)) {
            this.pendingState = 1;
        } else if (isAnimatorRunning(this.mFocusingAnimator)) {
            this.pendingState = 2;
        } else {
            int i3 = this.mSuccessFlag;
            if (i3 == 2 || i3 == 5) {
                this.mPaintFocusCenterIndicator.setCenterFlag(this.mSuccessFlag);
            }
            if (this.mSuccessFlag == 2) {
                this.mPaintFocusCenterIndicator.setIndicatorData(this.mFocusIndicatorState, this.mCaptureBitmap);
            }
            if (this.mSuccessFlag == 5) {
                if (i2 == 0) {
                    this.mPaintFocusCenterIndicator.setIndicatorData(this.mFocusIndicatorState, this.mFocusLockedBitmap);
                    start3ALockSuccessAnimation(this.mSplitBigCircle, this.mPaintFocusCenterIndicator);
                } else if (i2 == 1) {
                    this.mFocusLocked = true;
                    start3ALockSuccessAnimation(this.mPaintFocusCircle, this.mPaintFocusCenterIndicator);
                } else if (i2 == 2) {
                    this.mExposureLocked = true;
                    start3ALockSuccessAnimation(this.mPaintExposureCircle, this.mPaintExposureCenterIndicator);
                }
            } else if (i2 == 0) {
                startNormalFocusSuccessAnimation(this.mSplitBigCircle, this.mPaintFocusCenterIndicator);
            } else if (i2 == 1) {
                startNormalFocusSuccessAnimation(this.mPaintFocusCircle, this.mPaintFocusCenterIndicator);
            } else if (i2 == 2) {
                startNormalFocusSuccessAnimation(this.mPaintExposureCircle, this.mPaintExposureCenterIndicator);
            }
        }
    }

    public void startTouchDownAnimation(final int i) {
        Log.d(TAG, "startTouchDownAnimation");
        cancelFocusingAnimation();
        cancelSuccessAnimation();
        cancelResetCenter();
        this.pendingState = -1;
        if (isAnimatorRunning(this.mTouchDownAnimator)) {
            this.mTouchDownAnimator.cancel();
            this.mTouchDownAnimator = null;
        }
        this.mSplitBigCircle.setCurrentColor(-1).setTargetColor(-1);
        this.mPaintFocusCenterIndicator.setCurrentColor(-1).setTargetColor(-1);
        this.mTouchDownAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mTouchDownAnimator.setDuration(300L);
        this.mTouchDownAnimator.setInterpolator(new m() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass1 */

            @Override // d.h.a.m
            public float getInterpolation(float f2) {
                float interpolation = super.getInterpolation(f2);
                CameraFocusSplitAnimateDrawable.this.mSplitBigCircle.updateValue(interpolation);
                CameraFocusSplitAnimateDrawable.this.mPaintFocusCenterIndicator.updateValue(interpolation);
                CameraFocusSplitAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        this.mTouchDownAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable.AnonymousClass2 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                if (CameraFocusSplitAnimateDrawable.this.mTouchDownAnimator != null) {
                    CameraFocusSplitAnimateDrawable.this.mTouchDownAnimator.cancel();
                    ValueAnimator unused = CameraFocusSplitAnimateDrawable.this.mTouchDownAnimator = null;
                }
                int access$300 = CameraFocusSplitAnimateDrawable.this.pendingState;
                if (access$300 == 1) {
                    CameraFocusSplitAnimateDrawable cameraFocusSplitAnimateDrawable = CameraFocusSplitAnimateDrawable.this;
                    cameraFocusSplitAnimateDrawable.startFocusSuccessAnimation(cameraFocusSplitAnimateDrawable.mSuccessFlag, CameraFocusSplitAnimateDrawable.this.mIsTouchFocus, i);
                } else if (access$300 != 3) {
                    CameraFocusSplitAnimateDrawable.this.startFocusingAnimation();
                } else {
                    CameraFocusSplitAnimateDrawable.this.startFocusFailAnimation();
                }
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                CameraFocusSplitAnimateDrawable.this.mSplitBigCircle.setCurrentWidthPercent(CameraFocusSplitAnimateDrawable.CIRCLE_DOWN_BIG).setTargetWidthPercent(CameraFocusSplitAnimateDrawable.CIRCLE_DOWN_SMALL);
                CameraFocusSplitAnimateDrawable.this.mSplitBigCircle.setCurrentAlpha(0).setTargetAlpha(205);
                CameraFocusSplitAnimateDrawable.this.mPaintFocusCenterIndicator.setCurrentWidthPercent(CameraFocusSplitAnimateDrawable.CENTER_DOWN_SMALL).setTargetWidthPercent(1.0f);
                CameraFocusSplitAnimateDrawable.this.mPaintFocusCenterIndicator.setCurrentAlpha(0).setTargetAlpha(255);
            }
        });
        this.mTouchDownAnimator.start();
    }

    public void stop() {
    }
}
