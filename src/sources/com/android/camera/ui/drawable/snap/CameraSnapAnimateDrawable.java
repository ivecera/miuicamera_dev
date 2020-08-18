package com.android.camera.ui.drawable.snap;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import com.android.camera.Util;
import com.android.camera.fragment.bottom.BottomAnimationConfig;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.drawable.CameraPaintBase;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import d.h.a.j;
import d.h.a.m;

public class CameraSnapAnimateDrawable extends Drawable implements Animatable {
    private static final float SNAP_ROUND_ORIGINAL_WIDTH = 0.688f;
    private static final SpringConfig mCameraDownConfig = SpringConfig.fromOrigamiTensionAndFriction(100.0d, 15.0d);
    public static final SpringConfig mCameraUpConfig = SpringConfig.fromOrigamiTensionAndFriction(120.0d, 30.0d);
    public static final SpringConfig mCameraUpSplashConfig = SpringConfig.fromOrigamiTensionAndFriction(180.0d, 10.0d);
    public static final SpringConfig mRecordScaleConfig = SpringConfig.fromOrigamiTensionAndFriction(180.0d, 30.0d);
    /* access modifiers changed from: private */
    public CameraSnapPaintCenterVV mCenterVVPaintItem;
    /* access modifiers changed from: private */
    public CameraSnapPaintCircle mCirclePaintItem;
    private Spring mDownSpring;
    /* access modifiers changed from: private */
    public float mLiveSpeed;
    /* access modifiers changed from: private */
    public long mLiveStartTime;
    /* access modifiers changed from: private */
    public long mLiveTotalTime;
    /* access modifiers changed from: private */
    public LongPressIncreaseListener mLongPressIncreaseListener = new LongPressIncreaseListener() {
        /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass16 */

        @Override // com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.LongPressIncreaseListener
        public void OnIncrease(float f2) {
            CameraSnapAnimateDrawable.this.mRecordSpring.setEndValue(Math.min(1.1d, ((((double) f2) - 0.6d) / 1.0d) + 0.6000000238418579d));
            CameraSnapAnimateDrawable.this.invalidateSelf();
        }

        @Override // com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.LongPressIncreaseListener
        public void OnTheValue(boolean z) {
            if (z) {
                CameraSnapAnimateDrawable.this.mUpSpring.setSpringConfig(CameraSnapAnimateDrawable.mCameraUpSplashConfig);
                CameraSnapAnimateDrawable.this.mUpSpring.setEndValue(1.0d);
                CameraSnapAnimateDrawable.this.mRecordSpring.setEndValue(0.6d);
            }
        }

        @Override // com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.LongPressIncreaseListener
        public void OnValueUp(float f2) {
            double d2 = (double) f2;
            CameraSnapAnimateDrawable.this.mUpSpring.setEndValue(d2);
            CameraSnapAnimateDrawable.this.mRecordSpring.setEndValue(d2);
        }
    };
    private ValueAnimator mModeChangeAnimator;
    /* access modifiers changed from: private */
    public CameraSnapPaintMotion mMotionPaintItem;
    private ValueAnimator mReboundAnimator;
    public Spring mRecordSpring;
    /* access modifiers changed from: private */
    public CameraPaintBase mRecordingPaint;
    private ValueAnimator mRingAnimator;
    /* access modifiers changed from: private */
    public CameraSnapPaintRound mRoundPaintItem;
    /* access modifiers changed from: private */
    public CameraSnapPaintSecond mSecondPaintItem;
    private SpringSystem mSpringSystem;
    private ValueAnimator mTimeAnimator;
    public Spring mUpSpring;

    public interface BeautyRecordingListener {
        void onAngleChanged(float f2);
    }

    public interface LongPressIncreaseListener {
        void OnIncrease(float f2);

        void OnTheValue(boolean z);

        void OnValueUp(float f2);
    }

    public CameraSnapAnimateDrawable(Context context) {
        this.mCirclePaintItem = new CameraSnapPaintCircle(context);
        this.mRoundPaintItem = new CameraSnapPaintRound(context);
        this.mSecondPaintItem = new CameraSnapPaintSecond(context);
        this.mMotionPaintItem = new CameraSnapPaintMotion(context);
        this.mCenterVVPaintItem = new CameraSnapPaintCenterVV(context);
    }

    private void initReboundSystem() {
        if (this.mSpringSystem == null) {
            this.mSpringSystem = SpringSystem.create();
            this.mUpSpring = this.mSpringSystem.createSpring();
            this.mUpSpring.setSpringConfig(mCameraUpConfig);
            this.mUpSpring.addListener(new SimpleSpringListener() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass14 */

                @Override // com.facebook.rebound.SpringListener, com.facebook.rebound.SimpleSpringListener
                public void onSpringAtRest(Spring spring) {
                    if (spring.getCurrentValue() == 0.0d) {
                        CameraSnapAnimateDrawable.this.mUpSpring.setSpringConfig(CameraSnapAnimateDrawable.mCameraUpConfig);
                    }
                }

                @Override // com.facebook.rebound.SpringListener, com.facebook.rebound.SimpleSpringListener
                public void onSpringUpdate(Spring spring) {
                    float mapValueFromRangeToRange = (float) SpringUtil.mapValueFromRangeToRange((double) ((float) spring.getCurrentValue()), 0.0d, 1.0d, 1.0d, 1.2d);
                    ((CameraPaintBase) CameraSnapAnimateDrawable.this.mCirclePaintItem).isRecording = true;
                    CameraSnapAnimateDrawable.this.mCirclePaintItem.isInBeautyMode = true;
                    ((CameraPaintBase) CameraSnapAnimateDrawable.this.mCirclePaintItem).mCurrentWidthPercent = ((CameraPaintBase) CameraSnapAnimateDrawable.this.mCirclePaintItem).mSrcWidthPercent * mapValueFromRangeToRange;
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                }
            });
            this.mRecordSpring = this.mSpringSystem.createSpring();
            this.mRecordSpring.setSpringConfig(mRecordScaleConfig);
            this.mRecordSpring.setCurrentValue(1.0d);
            this.mRecordSpring.addListener(new SimpleSpringListener() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass15 */

                @Override // com.facebook.rebound.SpringListener, com.facebook.rebound.SimpleSpringListener
                public void onSpringUpdate(Spring spring) {
                    ((CameraPaintBase) CameraSnapAnimateDrawable.this.mRoundPaintItem).mCurrentWidthPercent = ((CameraPaintBase) CameraSnapAnimateDrawable.this.mRoundPaintItem).mSrcWidthPercent * ((float) spring.getCurrentValue());
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                }
            });
        }
    }

    private void updateLiveAnimationConfig() {
        ModeProtocol.LiveSpeedChanges liveSpeedChanges = (ModeProtocol.LiveSpeedChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(232);
        if (liveSpeedChanges != null) {
            this.mLiveSpeed = liveSpeedChanges.getRecordSpeed();
            this.mLiveTotalTime = liveSpeedChanges.getTotalRecordingTime();
            this.mLiveStartTime = liveSpeedChanges.getStartRecordingTime();
        }
    }

    public void addSegmentNow() {
        ValueAnimator valueAnimator = this.mTimeAnimator;
        if (valueAnimator != null) {
            this.mCirclePaintItem.addSegmentNow(valueAnimator.getCurrentPlayTime());
            invalidateSelf();
        }
    }

    public void cancelAnimation() {
        ValueAnimator valueAnimator = this.mTimeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mTimeAnimator = null;
        }
        ValueAnimator valueAnimator2 = this.mRingAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
            this.mRingAnimator = null;
        }
    }

    public void cancelRebound() {
        ValueAnimator valueAnimator = this.mReboundAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            CameraSnapPaintCircle cameraSnapPaintCircle = this.mCirclePaintItem;
            cameraSnapPaintCircle.isInBeautyMode = false;
            cameraSnapPaintCircle.resetRecordingState();
            this.mUpSpring.setEndValue(0.0d);
            this.mRecordSpring.setEndValue(1.0d);
        }
    }

    public void directFinishRecord() {
        cancelAnimation();
        CameraPaintBase cameraPaintBase = this.mRecordingPaint;
        if (cameraPaintBase != null) {
            ((CameraPaintBase) this.mRoundPaintItem).isRecording = false;
            cameraPaintBase.setCurrentValues(cameraPaintBase.mCurrentWidthPercent, cameraPaintBase.mCurrentColor, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(1.0f));
            CameraPaintBase cameraPaintBase2 = this.mRecordingPaint;
            cameraPaintBase2.isRecording = false;
            cameraPaintBase2.resetRecordingState();
            this.mRecordingPaint = null;
        }
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            canvas.save();
            this.mCirclePaintItem.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mRoundPaintItem.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mCenterVVPaintItem.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mSecondPaintItem.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mMotionPaintItem.drawCanvas(canvas);
            canvas.restore();
        }
    }

    public void finishRecord(BottomAnimationConfig bottomAnimationConfig) {
        if (this.mRecordingPaint == null || bottomAnimationConfig.mIsInMimojiCreate) {
            invalidateSelf();
        } else if (bottomAnimationConfig.mNeedFinishRecord) {
            cancelAnimation();
            this.mRecordingPaint.resetRecordingState();
            this.mRecordingPaint.setTargetAlpha(255);
            this.mRoundPaintItem.setTargetAlpha(255);
            this.mTimeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mTimeAnimator.setStartDelay(200);
            this.mTimeAnimator.setDuration(260L);
            this.mTimeAnimator.setInterpolator(new j() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass12 */

                @Override // d.h.a.j
                public float getInterpolation(float f2) {
                    float interpolation = super.getInterpolation(f2);
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                    CameraSnapAnimateDrawable.this.mRecordingPaint.updateValue(interpolation);
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                    return interpolation;
                }
            });
            this.mTimeAnimator.removeAllListeners();
            this.mTimeAnimator.addListener(new Animator.AnimatorListener() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass13 */

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    ((CameraPaintBase) CameraSnapAnimateDrawable.this.mRoundPaintItem).isRecording = false;
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                }
            });
            this.mTimeAnimator.start();
        } else {
            stopRecord(bottomAnimationConfig);
        }
    }

    public int getOpacity() {
        return -1;
    }

    public boolean hasSegments() {
        return this.mCirclePaintItem.hasSegments();
    }

    public void hidePaintCenterVVItem() {
        this.mCenterVVPaintItem.setVisible(8);
    }

    public void hideRoundPaintItem() {
        this.mRoundPaintItem.setVisible(8);
    }

    public void initParameters(int i, boolean z) {
        initTargetValues(i, z);
        this.mCirclePaintItem.setResult();
        this.mRoundPaintItem.setResult();
        this.mSecondPaintItem.setResult();
        this.mMotionPaintItem.setResult();
    }

    public void initTargetValues(int i, boolean z) {
        if (!(i == 161 || i == 162)) {
            if (i == 169) {
                this.mCirclePaintItem.setTargetValues(0.75f, -1, 0, (float) Util.dpToPixel(1.0f));
                this.mRoundPaintItem.setTargetValues(0.55f, -109521, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                this.mSecondPaintItem.setTargetValues(0.751f, -1, 0, (float) Util.dpToPixel(1.0f));
                this.mMotionPaintItem.setTargetValues(0.751f, -1, CameraPaintBase.ALPHA_OUTSTANDING, (float) Util.dpToPixel(1.0f));
                return;
            } else if (i != 183) {
                if (i == 179) {
                    this.mCirclePaintItem.setTargetValues(0.734f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(2.18f));
                    this.mRoundPaintItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -109521, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                    this.mSecondPaintItem.setTargetValues(0.833f, -1, 0, 3.0f);
                    this.mMotionPaintItem.setTargetValues(0.833f, -1, 0, 3.0f);
                    return;
                } else if (i != 180) {
                    switch (i) {
                        case 172:
                            this.mCirclePaintItem.setTargetValues(0.75f, -1, 0, (float) Util.dpToPixel(1.0f));
                            this.mRoundPaintItem.setTargetValues(0.55f, -109521, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                            this.mSecondPaintItem.setTargetValues(0.751f, -1, CameraPaintBase.ALPHA_OUTSTANDING, (float) Util.dpToPixel(1.0f));
                            this.mSecondPaintItem.setNeedSpacing(z);
                            this.mMotionPaintItem.setTargetValues(0.751f, -1, 0, (float) Util.dpToPixel(1.0f));
                            return;
                        case 173:
                            this.mCirclePaintItem.setTargetValues(0.75f, -1, 0, (float) Util.dpToPixel(1.0f));
                            this.mRoundPaintItem.setTargetValues(0.55f, -1, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                            this.mSecondPaintItem.setTargetValues(0.751f, -1, CameraPaintBase.ALPHA_OUTSTANDING, (float) Util.dpToPixel(1.0f));
                            this.mSecondPaintItem.setNeedSpacing(false);
                            this.mMotionPaintItem.setTargetValues(0.751f, -1, 0, (float) Util.dpToPixel(1.0f));
                            return;
                        case 174:
                            break;
                        default:
                            this.mCirclePaintItem.setTargetValues(0.75f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(1.0f));
                            this.mRoundPaintItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -1, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                            this.mSecondPaintItem.setTargetValues(0.833f, -1, 0, 3.0f);
                            this.mMotionPaintItem.setTargetValues(0.833f, -1, 0, 3.0f);
                            return;
                    }
                }
            }
        }
        this.mCirclePaintItem.setTargetValues(0.75f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(1.0f));
        this.mRoundPaintItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -109521, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
        this.mSecondPaintItem.setTargetValues(0.833f, -1, 0, 3.0f);
        this.mMotionPaintItem.setTargetValues(0.833f, -1, 0, 3.0f);
    }

    public boolean isRunning() {
        ValueAnimator valueAnimator;
        ValueAnimator valueAnimator2 = this.mModeChangeAnimator;
        return (valueAnimator2 != null && valueAnimator2.isRunning()) || ((valueAnimator = this.mTimeAnimator) != null && valueAnimator.isRunning());
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
    }

    public void pauseRecording() {
        ValueAnimator valueAnimator = this.mTimeAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTimeAnimator.pause();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00f3  */
    public void prepareRecording(final BottomAnimationConfig bottomAnimationConfig) {
        cancelAnimation();
        int i = bottomAnimationConfig.mCurrentMode;
        float f2 = 2.0f;
        if (i != 161) {
            if (!(i == 162 || i == 166)) {
                if (i == 169) {
                    CameraSnapPaintMotion cameraSnapPaintMotion = this.mMotionPaintItem;
                    cameraSnapPaintMotion.setTargetValues(((CameraPaintBase) cameraSnapPaintMotion).mCurrentWidthPercent, ((CameraPaintBase) cameraSnapPaintMotion).mCurrentColor, CameraPaintBase.ALPHA_HINT, ((CameraPaintBase) cameraSnapPaintMotion).mCurrentStrokeWidth);
                    this.mRecordingPaint = this.mMotionPaintItem;
                } else if (i != 176) {
                    if (i != 177) {
                        if (i != 179) {
                            if (i != 180) {
                                if (!(i == 183 || i == 184)) {
                                    switch (i) {
                                        case 172:
                                        case 173:
                                            CameraSnapPaintSecond cameraSnapPaintSecond = this.mSecondPaintItem;
                                            cameraSnapPaintSecond.setTargetValues(((CameraPaintBase) cameraSnapPaintSecond).mCurrentWidthPercent, ((CameraPaintBase) cameraSnapPaintSecond).mCurrentColor, CameraPaintBase.ALPHA_HINT, ((CameraPaintBase) cameraSnapPaintSecond).mCurrentStrokeWidth);
                                            this.mRecordingPaint = this.mSecondPaintItem;
                                            break;
                                    }
                                }
                            }
                        } else {
                            this.mCirclePaintItem.setNeedSplit(true);
                            this.mCirclePaintItem.clearSegments();
                            CameraSnapPaintCircle cameraSnapPaintCircle = this.mCirclePaintItem;
                            cameraSnapPaintCircle.setTargetValues(0.812538f, ((CameraPaintBase) cameraSnapPaintCircle).mCurrentColor, CameraPaintBase.ALPHA_HINT, (float) Util.dpToPixel(2.18f));
                            this.mCirclePaintItem.setNeedProcessShade(true);
                            this.mRecordingPaint = this.mCirclePaintItem;
                            this.mCenterVVPaintItem.setTargetAlpha(0);
                            ((CameraPaintBase) this.mCenterVVPaintItem).isRecording = true;
                        }
                    }
                }
                this.mRoundPaintItem.prepareRecord();
                CameraSnapPaintRound cameraSnapPaintRound = this.mRoundPaintItem;
                ((CameraPaintBase) cameraSnapPaintRound).isRecording = true;
                cameraSnapPaintRound.setTargetAlpha(255);
                float[] fArr = new float[2];
                fArr[0] = bottomAnimationConfig.mIsInMimojiCreate ? 1.0f : 0.0f;
                fArr[1] = 1.0f;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
                ofFloat.setDuration(250L);
                ofFloat.setInterpolator(new DecelerateInterpolator() {
                    /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass6 */

                    public float getInterpolation(float f2) {
                        float interpolation = super.getInterpolation(f2);
                        CameraSnapAnimateDrawable.this.mRecordingPaint.updateValue(interpolation);
                        if (!bottomAnimationConfig.mIsInMimojiCreate) {
                            CameraSnapAnimateDrawable.this.mRoundPaintItem.updateRecordValue(interpolation, true);
                        }
                        CameraSnapAnimateDrawable.this.mCenterVVPaintItem.updateValue(interpolation);
                        CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                        CameraSnapAnimateDrawable.this.invalidateSelf();
                        return interpolation;
                    }
                });
                ofFloat.start();
            }
            this.mCirclePaintItem.setNeedSplit(false);
            CameraSnapPaintCircle cameraSnapPaintCircle2 = this.mCirclePaintItem;
            float f3 = ((CameraPaintBase) cameraSnapPaintCircle2).mCurrentWidthPercent;
            int i2 = ((CameraPaintBase) cameraSnapPaintCircle2).mCurrentColor;
            int i3 = CameraPaintBase.ALPHA_OPAQUE;
            if (!bottomAnimationConfig.mIsVideoBokeh) {
                f2 = 1.0f;
            }
            cameraSnapPaintCircle2.setTargetValues(f3, i2, i3, (float) Util.dpToPixel(f2));
            this.mRecordingPaint = this.mCirclePaintItem;
            this.mRoundPaintItem.prepareRecord();
            CameraSnapPaintRound cameraSnapPaintRound2 = this.mRoundPaintItem;
            ((CameraPaintBase) cameraSnapPaintRound2).isRecording = true;
            cameraSnapPaintRound2.setTargetAlpha(255);
            float[] fArr2 = new float[2];
            fArr2[0] = bottomAnimationConfig.mIsInMimojiCreate ? 1.0f : 0.0f;
            fArr2[1] = 1.0f;
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(fArr2);
            ofFloat2.setDuration(250L);
            ofFloat2.setInterpolator(new DecelerateInterpolator() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass6 */

                public float getInterpolation(float f2) {
                    float interpolation = super.getInterpolation(f2);
                    CameraSnapAnimateDrawable.this.mRecordingPaint.updateValue(interpolation);
                    if (!bottomAnimationConfig.mIsInMimojiCreate) {
                        CameraSnapAnimateDrawable.this.mRoundPaintItem.updateRecordValue(interpolation, true);
                    }
                    CameraSnapAnimateDrawable.this.mCenterVVPaintItem.updateValue(interpolation);
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                    return interpolation;
                }
            });
            ofFloat2.start();
        }
        this.mCirclePaintItem.setNeedSplit(true);
        this.mCirclePaintItem.clearSegments();
        int i4 = bottomAnimationConfig.mCurrentMode;
        if (i4 == 177 || i4 == 184) {
            ((CameraPaintBase) this.mCirclePaintItem).mCurrentWidthPercent *= 1.2f;
        }
        CameraSnapPaintCircle cameraSnapPaintCircle3 = this.mCirclePaintItem;
        cameraSnapPaintCircle3.setTargetValues(((CameraPaintBase) cameraSnapPaintCircle3).mCurrentWidthPercent, ((CameraPaintBase) cameraSnapPaintCircle3).mCurrentColor, CameraPaintBase.ALPHA_HINT, (float) Util.dpToPixel(2.0f));
        this.mRecordingPaint = this.mCirclePaintItem;
        this.mRoundPaintItem.prepareRecord();
        CameraSnapPaintRound cameraSnapPaintRound22 = this.mRoundPaintItem;
        ((CameraPaintBase) cameraSnapPaintRound22).isRecording = true;
        cameraSnapPaintRound22.setTargetAlpha(255);
        float[] fArr22 = new float[2];
        fArr22[0] = bottomAnimationConfig.mIsInMimojiCreate ? 1.0f : 0.0f;
        fArr22[1] = 1.0f;
        ValueAnimator ofFloat22 = ValueAnimator.ofFloat(fArr22);
        ofFloat22.setDuration(250L);
        ofFloat22.setInterpolator(new DecelerateInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass6 */

            public float getInterpolation(float f2) {
                float interpolation = super.getInterpolation(f2);
                CameraSnapAnimateDrawable.this.mRecordingPaint.updateValue(interpolation);
                if (!bottomAnimationConfig.mIsInMimojiCreate) {
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.updateRecordValue(interpolation, true);
                }
                CameraSnapAnimateDrawable.this.mCenterVVPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        ofFloat22.start();
    }

    public void removeLastSegment() {
        if (!this.mCirclePaintItem.getSegmentRatios().isEmpty()) {
            this.mCirclePaintItem.removeLastSegmentAndGetLastTime();
            invalidateSelf();
        }
    }

    public void resetRecordingState() {
        this.mCirclePaintItem.resetRecordingState();
        this.mRoundPaintItem.resetRecordingState();
        this.mSecondPaintItem.resetRecordingState();
        this.mMotionPaintItem.resetRecordingState();
        this.mCenterVVPaintItem.resetRecordingState();
    }

    public void resumeRecording() {
        updateLiveAnimationConfig();
        ValueAnimator valueAnimator = this.mTimeAnimator;
        if (valueAnimator != null && valueAnimator.isPaused()) {
            this.mTimeAnimator.resume();
        }
    }

    public void setAlpha(int i) {
    }

    public void setBounds(Rect rect) {
        super.setBounds(rect);
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setDurationText(String str) {
        this.mCenterVVPaintItem.setDurationText(str);
        invalidateSelf();
    }

    public void setWidthHeight(float f2, float f3) {
        float f4 = f2 / 2.0f;
        float min = Math.min(f2, f3) / 2.0f;
        this.mCirclePaintItem.setMiddle(f4, f4, min);
        this.mRoundPaintItem.setMiddle(f4, f4, min);
        this.mSecondPaintItem.setMiddle(f4, f4, min);
        this.mMotionPaintItem.setMiddle(f4, f4, min);
        this.mCenterVVPaintItem.setMiddle(f4, f4, min);
    }

    public void showPaintCenterVVItem() {
        this.mCenterVVPaintItem.setTargetValues(0.711f, -1417668, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
        this.mCenterVVPaintItem.setResult();
        this.mCenterVVPaintItem.prepareRecord();
        this.mCenterVVPaintItem.setVisible(0);
    }

    public void showRoundPaintItem() {
        this.mRoundPaintItem.setVisible(0);
    }

    public void start() {
    }

    public void startModeChangeAnimation() {
        this.mModeChangeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mModeChangeAnimator.setDuration(300L);
        this.mModeChangeAnimator.setInterpolator(new DecelerateInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass1 */

            public float getInterpolation(float f2) {
                float interpolation = super.getInterpolation(f2);
                CameraSnapAnimateDrawable.this.mCirclePaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.mSecondPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.mMotionPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        this.mModeChangeAnimator.setupEndValues();
        this.mModeChangeAnimator.start();
    }

    public void startRebound() {
        initReboundSystem();
        this.mReboundAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mReboundAnimator.setStartDelay(300);
        this.mReboundAnimator.setDuration(8500L);
        this.mReboundAnimator.setInterpolator(new LinearInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass17 */

            public float getInterpolation(float f2) {
                float interpolation = super.getInterpolation(f2);
                float f3 = 8500.0f * interpolation;
                if (f3 > 500.0f) {
                    ((CameraPaintBase) CameraSnapAnimateDrawable.this.mCirclePaintItem).timeAngle = ((f3 - 500.0f) / 8000.0f) * 360.0f;
                }
                if (f3 <= 1000.0f) {
                    CameraSnapAnimateDrawable.this.mLongPressIncreaseListener.OnValueUp((((f3 / 31.25f) * 2.0f) - 10.0f) / 90.0f);
                    CameraSnapAnimateDrawable.this.mLongPressIncreaseListener.OnTheValue(false);
                } else if (f3 - 1000.0f < 30.0f) {
                    CameraSnapAnimateDrawable.this.mLongPressIncreaseListener.OnTheValue(true);
                } else {
                    CameraSnapAnimateDrawable.this.mLongPressIncreaseListener.OnIncrease((((f3 / 31.25f) * 2.0f) - 10.0f) / 90.0f);
                }
                return interpolation;
            }
        });
        this.mReboundAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass18 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                ((ModeProtocol.BeautyRecording) ModeCoordinatorImpl.getInstance().getAttachProtocol(173)).handleBeautyRecordingStop();
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                ((ModeProtocol.BeautyRecording) ModeCoordinatorImpl.getInstance().getAttachProtocol(173)).handleBeautyRecordingStart();
            }
        });
        this.mReboundAnimator.start();
    }

    public void startRecord(final BottomAnimationConfig bottomAnimationConfig) {
        if (this.mRecordingPaint != null) {
            cancelAnimation();
            this.mTimeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            int i = bottomAnimationConfig.mCurrentMode;
            if (i == 174 || i == 183) {
                updateLiveAnimationConfig();
            }
            this.mTimeAnimator.setDuration((long) bottomAnimationConfig.mDuration);
            this.mTimeAnimator.setInterpolator(new LinearInterpolator() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass7 */

                public float getInterpolation(float f2) {
                    float f3 = f2 * 360.0f;
                    int i = bottomAnimationConfig.mCurrentMode;
                    if (i == 174 || i == 183) {
                        f3 = (((float) (((long) ((((double) (System.currentTimeMillis() - CameraSnapAnimateDrawable.this.mLiveStartTime)) * 1.0d) / ((double) CameraSnapAnimateDrawable.this.mLiveSpeed))) + CameraSnapAnimateDrawable.this.mLiveTotalTime)) * 360.0f) / ((float) bottomAnimationConfig.mDuration);
                        if (f3 > 360.0f) {
                            f3 = 360.0f;
                        }
                    }
                    CameraSnapAnimateDrawable.this.mRecordingPaint.timeAngle = f3;
                    float interpolation = super.getInterpolation(f2);
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                    return interpolation;
                }
            });
            this.mTimeAnimator.removeAllListeners();
            this.mTimeAnimator.addListener(new Animator.AnimatorListener() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass8 */

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                    int i = bottomAnimationConfig.mCurrentMode;
                    if (i != 174 && i != 183) {
                        CameraSnapAnimateDrawable.this.mRecordingPaint.reverseClock();
                    }
                }

                public void onAnimationStart(Animator animator) {
                    CameraSnapAnimateDrawable.this.mRecordingPaint.isRecording = true;
                    ((CameraPaintBase) CameraSnapAnimateDrawable.this.mRoundPaintItem).isRecording = true;
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.isRecordingCircle = bottomAnimationConfig.mIsRecordingCircle;
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.isRoundingCircle = bottomAnimationConfig.mIsRoundingCircle;
                }
            });
            if (bottomAnimationConfig.mShouldRepeat) {
                this.mTimeAnimator.setRepeatMode(1);
                this.mTimeAnimator.setRepeatCount(-1);
            }
            this.mTimeAnimator.start();
        }
    }

    public void startRecordAnimation(BottomAnimationConfig bottomAnimationConfig) {
        if (bottomAnimationConfig.mIsStart) {
            startRecord(bottomAnimationConfig);
        } else {
            finishRecord(bottomAnimationConfig);
        }
    }

    public void startRingAnimation() {
        cancelAnimation();
        this.mCirclePaintItem.setRingVisible(0);
        this.mRoundPaintItem.setVisible(8);
        this.mRingAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mRingAnimator.setDuration(500L);
        this.mRingAnimator.setRepeatCount(-1);
        this.mRingAnimator.setRepeatMode(1);
        this.mRingAnimator.setInterpolator(new BounceInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass5 */

            public float getInterpolation(float f2) {
                float interpolation = super.getInterpolation(f2);
                CameraSnapAnimateDrawable.this.mCirclePaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        this.mRingAnimator.start();
    }

    public void startScaleDownAnimation() {
        CameraSnapPaintRound cameraSnapPaintRound = this.mRoundPaintItem;
        cameraSnapPaintRound.setTargetValues(0.63296f, ((CameraPaintBase) cameraSnapPaintRound).mCurrentColor, ((CameraPaintBase) cameraSnapPaintRound).mCurrentAlpha, ((CameraPaintBase) cameraSnapPaintRound).mCurrentStrokeWidth);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(200L);
        ofFloat.setInterpolator(new m() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass2 */

            @Override // d.h.a.m
            public float getInterpolation(float f2) {
                float interpolation = super.getInterpolation(f2);
                CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        ofFloat.setupEndValues();
        ofFloat.start();
    }

    public void startScaleUpAnimation(long j, Animator.AnimatorListener animatorListener) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setStartDelay(j);
        ofFloat.setDuration(200L);
        ofFloat.setInterpolator(new m() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass3 */

            @Override // d.h.a.m
            public float getInterpolation(float f2) {
                float interpolation = super.getInterpolation(f2);
                CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        ofFloat.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass4 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                CameraSnapAnimateDrawable.this.mRoundPaintItem.setTargetValues(CameraSnapAnimateDrawable.SNAP_ROUND_ORIGINAL_WIDTH, ((CameraPaintBase) CameraSnapAnimateDrawable.this.mRoundPaintItem).mCurrentColor, ((CameraPaintBase) CameraSnapAnimateDrawable.this.mRoundPaintItem).mCurrentAlpha, ((CameraPaintBase) CameraSnapAnimateDrawable.this.mRoundPaintItem).mCurrentStrokeWidth);
            }
        });
        if (animatorListener != null) {
            ofFloat.addListener(animatorListener);
        }
        ofFloat.setupEndValues();
        ofFloat.start();
    }

    public void stop() {
    }

    public void stopRecord(final BottomAnimationConfig bottomAnimationConfig) {
        CameraPaintBase cameraPaintBase = this.mRecordingPaint;
        if (cameraPaintBase != null) {
            cameraPaintBase.isRecording = true;
            cancelAnimation();
            int i = bottomAnimationConfig.mCurrentMode;
            if (i != 161) {
                if (i != 162) {
                    if (i != 177) {
                        if (i != 179) {
                            if (!(i == 183 || i == 184)) {
                                switch (i) {
                                    case 172:
                                    case 173:
                                        break;
                                    case 174:
                                        break;
                                    default:
                                        CameraPaintBase cameraPaintBase2 = this.mRecordingPaint;
                                        cameraPaintBase2.setTargetValues(cameraPaintBase2.mCurrentWidthPercent, cameraPaintBase2.mCurrentColor, CameraPaintBase.ALPHA_OUTSTANDING, cameraPaintBase2.mCurrentStrokeWidth);
                                        break;
                                }
                            }
                        } else {
                            CameraSnapPaintCenterVV cameraSnapPaintCenterVV = this.mCenterVVPaintItem;
                            ((CameraPaintBase) cameraSnapPaintCenterVV).isRecording = false;
                            cameraSnapPaintCenterVV.setTargetAlpha(255);
                            this.mCenterVVPaintItem.setDurationText(null);
                            CameraPaintBase cameraPaintBase3 = this.mRecordingPaint;
                            cameraPaintBase3.setTargetValues(0.734f, cameraPaintBase3.mCurrentColor, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(2.18f));
                        }
                        this.mTimeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                        this.mTimeAnimator.setDuration(300L);
                        this.mTimeAnimator.setInterpolator(new LinearInterpolator() {
                            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass9 */

                            public float getInterpolation(float f2) {
                                float interpolation = super.getInterpolation(f2);
                                CameraSnapAnimateDrawable.this.mRoundPaintItem.updateRecordValue(interpolation, false);
                                if (bottomAnimationConfig.mIsPostProcessing) {
                                    CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                                }
                                CameraSnapAnimateDrawable.this.mCenterVVPaintItem.updateValue(interpolation);
                                CameraSnapAnimateDrawable.this.invalidateSelf();
                                return interpolation;
                            }
                        });
                        this.mTimeAnimator.removeAllListeners();
                        this.mTimeAnimator.addListener(new Animator.AnimatorListener() {
                            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass10 */

                            public void onAnimationCancel(Animator animator) {
                            }

                            public void onAnimationEnd(Animator animator) {
                                CameraSnapAnimateDrawable.this.mRecordingPaint.resetRecordingState();
                                ((CameraPaintBase) CameraSnapAnimateDrawable.this.mRoundPaintItem).isRecording = false;
                                CameraSnapAnimateDrawable.this.mRoundPaintItem.isRecordingCircle = false;
                            }

                            public void onAnimationRepeat(Animator animator) {
                            }

                            public void onAnimationStart(Animator animator) {
                                CameraSnapAnimateDrawable.this.mRecordingPaint.resetRecordingState();
                                CameraSnapAnimateDrawable.this.mRecordingPaint.needZero = false;
                            }
                        });
                        this.mTimeAnimator.start();
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                        ofFloat.setDuration(400L);
                        ofFloat.setInterpolator(new AccelerateInterpolator() {
                            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass11 */

                            public float getInterpolation(float f2) {
                                CameraSnapAnimateDrawable.this.mRecordingPaint.timeAngle = 0.0f;
                                float interpolation = super.getInterpolation(f2);
                                CameraSnapAnimateDrawable.this.mRecordingPaint.updateValue(interpolation);
                                CameraSnapAnimateDrawable.this.invalidateSelf();
                                return interpolation;
                            }
                        });
                        ofFloat.start();
                    }
                }
                if (bottomAnimationConfig.mIsPostProcessing) {
                    this.mRoundPaintItem.setTargetAlpha(0);
                }
                CameraPaintBase cameraPaintBase4 = this.mRecordingPaint;
                cameraPaintBase4.setTargetValues(cameraPaintBase4.mCurrentWidthPercent, cameraPaintBase4.mCurrentColor, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(1.0f));
                this.mTimeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mTimeAnimator.setDuration(300L);
                this.mTimeAnimator.setInterpolator(new LinearInterpolator() {
                    /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass9 */

                    public float getInterpolation(float f2) {
                        float interpolation = super.getInterpolation(f2);
                        CameraSnapAnimateDrawable.this.mRoundPaintItem.updateRecordValue(interpolation, false);
                        if (bottomAnimationConfig.mIsPostProcessing) {
                            CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                        }
                        CameraSnapAnimateDrawable.this.mCenterVVPaintItem.updateValue(interpolation);
                        CameraSnapAnimateDrawable.this.invalidateSelf();
                        return interpolation;
                    }
                });
                this.mTimeAnimator.removeAllListeners();
                this.mTimeAnimator.addListener(new Animator.AnimatorListener() {
                    /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass10 */

                    public void onAnimationCancel(Animator animator) {
                    }

                    public void onAnimationEnd(Animator animator) {
                        CameraSnapAnimateDrawable.this.mRecordingPaint.resetRecordingState();
                        ((CameraPaintBase) CameraSnapAnimateDrawable.this.mRoundPaintItem).isRecording = false;
                        CameraSnapAnimateDrawable.this.mRoundPaintItem.isRecordingCircle = false;
                    }

                    public void onAnimationRepeat(Animator animator) {
                    }

                    public void onAnimationStart(Animator animator) {
                        CameraSnapAnimateDrawable.this.mRecordingPaint.resetRecordingState();
                        CameraSnapAnimateDrawable.this.mRecordingPaint.needZero = false;
                    }
                });
                this.mTimeAnimator.start();
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat2.setDuration(400L);
                ofFloat2.setInterpolator(new AccelerateInterpolator() {
                    /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass11 */

                    public float getInterpolation(float f2) {
                        CameraSnapAnimateDrawable.this.mRecordingPaint.timeAngle = 0.0f;
                        float interpolation = super.getInterpolation(f2);
                        CameraSnapAnimateDrawable.this.mRecordingPaint.updateValue(interpolation);
                        CameraSnapAnimateDrawable.this.invalidateSelf();
                        return interpolation;
                    }
                });
                ofFloat2.start();
            }
            CameraPaintBase cameraPaintBase5 = this.mRecordingPaint;
            cameraPaintBase5.setTargetValues(cameraPaintBase5.mCurrentWidthPercent, cameraPaintBase5.mCurrentColor, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(1.0f));
            this.mTimeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mTimeAnimator.setDuration(300L);
            this.mTimeAnimator.setInterpolator(new LinearInterpolator() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass9 */

                public float getInterpolation(float f2) {
                    float interpolation = super.getInterpolation(f2);
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.updateRecordValue(interpolation, false);
                    if (bottomAnimationConfig.mIsPostProcessing) {
                        CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                    }
                    CameraSnapAnimateDrawable.this.mCenterVVPaintItem.updateValue(interpolation);
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                    return interpolation;
                }
            });
            this.mTimeAnimator.removeAllListeners();
            this.mTimeAnimator.addListener(new Animator.AnimatorListener() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass10 */

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    CameraSnapAnimateDrawable.this.mRecordingPaint.resetRecordingState();
                    ((CameraPaintBase) CameraSnapAnimateDrawable.this.mRoundPaintItem).isRecording = false;
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.isRecordingCircle = false;
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                    CameraSnapAnimateDrawable.this.mRecordingPaint.resetRecordingState();
                    CameraSnapAnimateDrawable.this.mRecordingPaint.needZero = false;
                }
            });
            this.mTimeAnimator.start();
            ValueAnimator ofFloat22 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat22.setDuration(400L);
            ofFloat22.setInterpolator(new AccelerateInterpolator() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass11 */

                public float getInterpolation(float f2) {
                    CameraSnapAnimateDrawable.this.mRecordingPaint.timeAngle = 0.0f;
                    float interpolation = super.getInterpolation(f2);
                    CameraSnapAnimateDrawable.this.mRecordingPaint.updateValue(interpolation);
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                    return interpolation;
                }
            });
            ofFloat22.start();
        }
    }

    public void stopRingAnimation() {
        ValueAnimator valueAnimator = this.mRingAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mCirclePaintItem.setRingVisible(8);
            this.mRoundPaintItem.setVisible(0);
            invalidateSelf();
        }
    }
}
