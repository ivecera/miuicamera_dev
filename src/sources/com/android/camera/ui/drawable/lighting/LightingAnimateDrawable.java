package com.android.camera.ui.drawable.lighting;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.PathInterpolator;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.ui.drawable.CameraPaintBase;
import d.h.a.E;
import d.h.a.k;

public class LightingAnimateDrawable extends Drawable {
    public static final float CENTER_FOCUSING_BIG = 0.576f;
    public static final float CENTER_FOCUSING_SMALL = 0.548f;
    /* access modifiers changed from: private */
    public boolean isShown;
    private ValueAnimator mAlphaAnimator;
    /* access modifiers changed from: private */
    public boolean mAnimateFocusDoing = false;
    private float mCircleHeightRatio = 1.0f;
    private float mCircleRatio = 1.0f;
    private ValueAnimator mExitAnimator;
    private ValueAnimator mFocusingAnimator;
    private int mHeight;
    private float mMiddleHorizontalY;
    private float mMiddleVerticalY;
    private float mMiddleX;
    /* access modifiers changed from: private */
    public LightingPaintCircle mPaintCircle;
    /* access modifiers changed from: private */
    public LightingPaintMask mPaintMask;
    private ValueAnimator mStartAnimator;
    private int mTopMargin;
    private ValueAnimator mUpdateDegreeAnimator;
    private int mWidth;
    /* access modifiers changed from: private */
    public int pendingState;

    public LightingAnimateDrawable(Context context) {
        this.mPaintMask = new LightingPaintMask(context);
        this.mPaintCircle = new LightingPaintCircle(context);
        this.mTopMargin = Util.getDisplayRect(1).top;
    }

    /* access modifiers changed from: private */
    public void animateFocusing() {
        this.mAnimateFocusDoing = true;
        this.pendingState = -1;
        this.mPaintCircle.setCurrentColor(-1).setTargetColor(-1);
        cancelAllAnimation();
        this.mFocusingAnimator = ValueAnimator.ofFloat(0.548f, 0.576f);
        this.mFocusingAnimator.setInterpolator(new E());
        this.mFocusingAnimator.setDuration(600L);
        this.mFocusingAnimator.setRepeatMode(2);
        this.mFocusingAnimator.setRepeatCount(-1);
        this.mFocusingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass7 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ((CameraPaintBase) LightingAnimateDrawable.this.mPaintMask).mCurrentWidthPercent = floatValue;
                ((CameraPaintBase) LightingAnimateDrawable.this.mPaintCircle).mCurrentWidthPercent = floatValue;
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mFocusingAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass8 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
                if (0.576f - ((CameraPaintBase) LightingAnimateDrawable.this.mPaintCircle).mCurrentWidthPercent <= 0.02f && LightingAnimateDrawable.this.pendingState == 2) {
                    LightingAnimateDrawable.this.animateSuccess();
                }
            }

            public void onAnimationStart(Animator animator) {
                boolean unused = LightingAnimateDrawable.this.mAnimateFocusDoing = false;
            }
        });
        this.mFocusingAnimator.start();
    }

    private void animateMiddleChange(int i) {
        cancelAllAnimation();
        if (i == 90 || i == 270) {
            this.mPaintMask.setTargetMiddle(this.mMiddleX, this.mMiddleHorizontalY, (((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f) / 1.15f);
            this.mPaintCircle.setTargetMiddle(this.mMiddleX, this.mMiddleHorizontalY, (((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f) / 1.15f);
        } else {
            this.mPaintMask.setTargetMiddle(this.mMiddleX, this.mMiddleVerticalY, ((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f);
            this.mPaintCircle.setTargetMiddle(this.mMiddleX, this.mMiddleVerticalY, ((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f);
        }
        this.mUpdateDegreeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mUpdateDegreeAnimator.setDuration(300L);
        this.mUpdateDegreeAnimator.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f));
        this.mUpdateDegreeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                LightingAnimateDrawable.this.mPaintMask.updateValue(floatValue);
                LightingAnimateDrawable.this.mPaintCircle.updateValue(floatValue);
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mUpdateDegreeAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass2 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                if (LightingAnimateDrawable.this.isShown && !LightingAnimateDrawable.this.mAnimateFocusDoing) {
                    LightingAnimateDrawable.this.animateFocusing();
                }
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        this.mUpdateDegreeAnimator.start();
    }

    private void cancelAllAnimation() {
        ValueAnimator valueAnimator = this.mStartAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mStartAnimator.cancel();
            this.mStartAnimator = null;
        }
        ValueAnimator valueAnimator2 = this.mAlphaAnimator;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            this.mAlphaAnimator.cancel();
            this.mAlphaAnimator = null;
        }
        ValueAnimator valueAnimator3 = this.mFocusingAnimator;
        if (valueAnimator3 != null && valueAnimator3.isRunning()) {
            this.mFocusingAnimator.cancel();
            this.mFocusingAnimator = null;
        }
        ValueAnimator valueAnimator4 = this.mExitAnimator;
        if (valueAnimator4 != null && valueAnimator4.isRunning()) {
            this.mExitAnimator.cancel();
            this.mExitAnimator = null;
        }
        ValueAnimator valueAnimator5 = this.mUpdateDegreeAnimator;
        if (valueAnimator5 != null && valueAnimator5.isRunning()) {
            this.mUpdateDegreeAnimator.cancel();
            this.mUpdateDegreeAnimator = null;
        }
    }

    public void animateAlphaIn() {
        this.mPaintCircle.setVisible(0);
        this.mPaintCircle.setCurrentWidthPercent(0.548f).setTargetWidthPercent(0.548f);
        this.mPaintCircle.setCurrentColor(-1).setTargetColor(-1);
        this.mPaintCircle.setCurrentAlpha(0).setTargetAlpha(255);
        this.mAlphaAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAlphaAnimator.setDuration(200L);
        this.mAlphaAnimator.setInterpolator(new k());
        this.mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass5 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LightingAnimateDrawable.this.mPaintCircle.updateValue(((Float) valueAnimator.getAnimatedValue()).floatValue());
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mAlphaAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass6 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                LightingAnimateDrawable.this.triggerFocusing();
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        this.mAlphaAnimator.start();
    }

    public void animateExit() {
        cancelAllAnimation();
        this.mPaintCircle.setVisible(8);
        this.mPaintMask.setTargetWidthPercent(1.5f);
        this.mPaintMask.setTargetAlpha(114);
        this.mExitAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mExitAnimator.setDuration(200L);
        this.mExitAnimator.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f));
        this.mExitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass10 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LightingAnimateDrawable.this.mPaintMask.updateValue(((Float) valueAnimator.getAnimatedValue()).floatValue());
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mExitAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass11 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                boolean unused = LightingAnimateDrawable.this.isShown = false;
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        this.mExitAnimator.start();
    }

    public void animateStart() {
        cancelAllAnimation();
        this.isShown = true;
        this.mPaintCircle.setVisible(8);
        this.mPaintMask.setCurrentWidthPercent(1.0f).setTargetWidthPercent(0.548f);
        this.mPaintMask.setTargetAlpha(114);
        this.mStartAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mStartAnimator.setDuration(380L);
        this.mStartAnimator.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f));
        this.mStartAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass3 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LightingAnimateDrawable.this.mPaintMask.updateValue(((Float) valueAnimator.getAnimatedValue()).floatValue());
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mStartAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass4 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                LightingAnimateDrawable.this.animateAlphaIn();
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        this.mStartAnimator.start();
    }

    public void animateSuccess() {
        cancelAllAnimation();
        this.mPaintCircle.setCurrentColor(-1).setTargetColor(ColorConstant.COLOR_COMMON_SELECTED);
        this.mPaintCircle.setCurrentWidthPercent(0.576f).setTargetWidthPercent(0.548f);
        this.mPaintMask.setCurrentWidthPercent(0.576f).setTargetWidthPercent(0.548f);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setInterpolator(new k());
        ofFloat.setDuration(250L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass9 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                LightingAnimateDrawable.this.mPaintMask.updateValue(floatValue);
                LightingAnimateDrawable.this.mPaintCircle.updateValue(floatValue);
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        ofFloat.start();
    }

    public void clear() {
        this.isShown = false;
        cancelAllAnimation();
        invalidateSelf();
    }

    public void draw(@NonNull Canvas canvas) {
        if (this.isShown && canvas != null) {
            int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) this.mWidth, (float) this.mHeight, null, 31);
            canvas.save();
            this.mPaintMask.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mPaintCircle.drawCanvas(canvas);
            canvas.restore();
            canvas.restoreToCount(saveLayer);
        }
    }

    public int getOpacity() {
        return -1;
    }

    public void setAlpha(int i) {
    }

    public void setCircleHeightRatio(float f2) {
        this.mCircleHeightRatio = f2;
    }

    public void setCircleRatio(float f2) {
        this.mCircleRatio = f2;
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public void setWidthHeight(int i, int i2, int i3) {
        this.mWidth = i;
        this.mHeight = i2;
        float f2 = (float) i;
        this.mMiddleX = f2 / 2.0f;
        this.mMiddleVerticalY = (((f2 * 1.33f) / 2.0f) * 0.8f) + ((float) Util.dpToPixel(24.0f));
        if (DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            this.mMiddleHorizontalY = (((float) Math.min(this.mWidth, this.mHeight)) * 1.33f) / 2.0f;
        } else {
            this.mMiddleHorizontalY = (((float) Math.min(this.mWidth, this.mHeight)) * 1.77f) / 2.0f;
        }
        if (CameraSettings.isGifOn()) {
            this.mMiddleHorizontalY = ((float) Math.min(this.mWidth, this.mHeight)) / 2.0f;
            this.mMiddleVerticalY = ((float) Math.min(this.mWidth, this.mHeight)) / 2.0f;
        } else {
            float f3 = this.mMiddleVerticalY;
            float f4 = this.mCircleHeightRatio;
            this.mMiddleVerticalY = f3 * f4;
            this.mMiddleHorizontalY *= f4;
        }
        if (DataRepository.dataItemRunning().getUiStyle() == 3) {
            float f5 = this.mMiddleVerticalY;
            int i4 = this.mTopMargin;
            this.mMiddleVerticalY = f5 + ((float) i4);
            this.mMiddleHorizontalY += (float) i4;
        }
        this.mPaintMask.setData(this.mWidth, this.mHeight);
        updateMiddleValue(i3, false);
    }

    public void triggerFocusing() {
        if (this.isShown && !this.mAnimateFocusDoing) {
            animateFocusing();
        }
    }

    public void triggerSuccess() {
        this.pendingState = 2;
    }

    public void updateMiddleValue(int i, boolean z) {
        if (this.isShown && z) {
            animateMiddleChange(i);
        } else if (i == 90 || i == 270) {
            this.mPaintMask.setMiddle(this.mMiddleX, this.mMiddleHorizontalY, (((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f) / 1.15f);
            this.mPaintCircle.setMiddle(this.mMiddleX, this.mMiddleHorizontalY, (((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f) / 1.15f);
        } else {
            this.mPaintMask.setMiddle(this.mMiddleX, this.mMiddleVerticalY, ((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f);
            this.mPaintCircle.setMiddle(this.mMiddleX, this.mMiddleVerticalY, ((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f);
        }
    }
}
