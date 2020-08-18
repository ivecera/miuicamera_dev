package com.android.camera.ui.drawable.snap;

import android.animation.ArgbEvaluator;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import com.android.camera.log.Log;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

public class CameraSnapPaintItemBeauty {
    private static final String TAG = "CameraSnapPaintItemBeau";
    private static final SpringConfig mCameraDownConfig = SpringConfig.fromOrigamiTensionAndFriction(100.0d, 15.0d);
    public static final SpringConfig mCameraUpConfig = SpringConfig.fromOrigamiTensionAndFriction(120.0d, 30.0d);
    public static final SpringConfig mCameraUpSplashConfig = SpringConfig.fromOrigamiTensionAndFriction(180.0d, 10.0d);
    public static final SpringConfig mRecordScaleConfig = SpringConfig.fromOrigamiTensionAndFriction(180.0d, 30.0d);
    /* access modifiers changed from: private */
    public CameraSnapAnimateDrawable mCameraSnapAnimateDrawable;
    public int mCurrentAlpha;
    @ColorInt
    public int mCurrentColor;
    public float mCurrentWidth = 0.9f;
    private Spring mDownSpring;
    private int mDstAlpha;
    private int mDstColor;
    private float mDstWidth;
    public LongPressIncreaseListener mLongPressIncreaseListener = new LongPressIncreaseListener() {
        /* class com.android.camera.ui.drawable.snap.CameraSnapPaintItemBeauty.AnonymousClass1 */

        @Override // com.android.camera.ui.drawable.snap.CameraSnapPaintItemBeauty.LongPressIncreaseListener
        public void OnIncrease(float f2) {
            CameraSnapPaintItemBeauty.this.mRecordSpring.setEndValue(Math.min(1.1d, ((((double) f2) - 0.6d) / 1.0d) + 0.6000000238418579d));
        }

        @Override // com.android.camera.ui.drawable.snap.CameraSnapPaintItemBeauty.LongPressIncreaseListener
        public void OnTheValue(boolean z) {
            if (z) {
                CameraSnapPaintItemBeauty.this.mUpSpring.setSpringConfig(CameraSnapPaintItemBeauty.mCameraUpSplashConfig);
                CameraSnapPaintItemBeauty.this.mUpSpring.setEndValue(1.0d);
                CameraSnapPaintItemBeauty.this.mRecordSpring.setEndValue(0.6000000238418579d);
            }
        }

        @Override // com.android.camera.ui.drawable.snap.CameraSnapPaintItemBeauty.LongPressIncreaseListener
        public void OnValueUp(float f2) {
            CameraSnapPaintItemBeauty.this.mUpSpring.setEndValue((double) f2);
            CameraSnapPaintItemBeauty.this.mRecordSpring.setEndValue((double) Math.max(0.3f, f2));
        }
    };
    public Paint mPaint;
    public Spring mRecordSpring;
    private SpringSystem mSpringSystem;
    private int mSrcAlpha;
    private int mSrcColor;
    private float mSrcWidth;
    public Spring mUpSpring;

    public interface LongPressIncreaseListener {
        void OnIncrease(float f2);

        void OnTheValue(boolean z);

        void OnValueUp(float f2);
    }

    public CameraSnapPaintItemBeauty(CameraSnapAnimateDrawable cameraSnapAnimateDrawable) {
        this.mCameraSnapAnimateDrawable = cameraSnapAnimateDrawable;
        initClickReboundSystem();
        initLongPressReboundSystem();
    }

    private void initClickReboundSystem() {
        this.mSpringSystem = SpringSystem.create();
        this.mDownSpring = this.mSpringSystem.createSpring();
        this.mDownSpring.setSpringConfig(mCameraDownConfig);
        this.mDownSpring.addListener(new SimpleSpringListener() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapPaintItemBeauty.AnonymousClass2 */

            @Override // com.facebook.rebound.SpringListener, com.facebook.rebound.SimpleSpringListener
            public void onSpringUpdate(Spring spring) {
                float mapValueFromRangeToRange = (float) SpringUtil.mapValueFromRangeToRange((double) ((float) spring.getCurrentValue()), 0.0d, 1.0d, 1.0d, 0.8d);
                Log.e(CameraSnapPaintItemBeauty.TAG, "scaleValue = " + mapValueFromRangeToRange);
                CameraSnapPaintItemBeauty cameraSnapPaintItemBeauty = CameraSnapPaintItemBeauty.this;
                cameraSnapPaintItemBeauty.mCurrentWidth = mapValueFromRangeToRange * 1.0f;
                cameraSnapPaintItemBeauty.mCameraSnapAnimateDrawable.invalidateSelf();
            }
        });
    }

    private void initLongPressReboundSystem() {
        this.mUpSpring = this.mSpringSystem.createSpring();
        this.mUpSpring.setSpringConfig(mCameraUpConfig);
        this.mUpSpring.addListener(new SimpleSpringListener() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapPaintItemBeauty.AnonymousClass3 */

            @Override // com.facebook.rebound.SpringListener, com.facebook.rebound.SimpleSpringListener
            public void onSpringAtRest(Spring spring) {
                if (spring.getCurrentValue() == 0.0d) {
                    CameraSnapPaintItemBeauty.this.mUpSpring.setSpringConfig(CameraSnapPaintItemBeauty.mCameraUpConfig);
                }
            }

            @Override // com.facebook.rebound.SpringListener, com.facebook.rebound.SimpleSpringListener
            public void onSpringUpdate(Spring spring) {
                spring.getCurrentValue();
            }
        });
        this.mRecordSpring = this.mSpringSystem.createSpring();
        this.mRecordSpring.setSpringConfig(mRecordScaleConfig);
        this.mRecordSpring.setCurrentValue(1.0d);
        this.mRecordSpring.addListener(new SimpleSpringListener() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapPaintItemBeauty.AnonymousClass4 */

            @Override // com.facebook.rebound.SpringListener, com.facebook.rebound.SimpleSpringListener
            public void onSpringUpdate(Spring spring) {
                CameraSnapPaintItemBeauty cameraSnapPaintItemBeauty = CameraSnapPaintItemBeauty.this;
                cameraSnapPaintItemBeauty.mCurrentWidth = ((float) spring.getCurrentValue()) * 0.9f;
                cameraSnapPaintItemBeauty.mCameraSnapAnimateDrawable.invalidateSelf();
            }
        });
    }

    public void onDown() {
        this.mDownSpring.setEndValue(1.0d);
    }

    public void onLongPress() {
    }

    public void onUp() {
        this.mDownSpring.setEndValue(0.0d);
    }

    public void reInitValues(float f2, @ColorInt int i, int i2) {
        this.mDstWidth = f2;
        this.mDstColor = i;
        this.mDstAlpha = i2;
        this.mSrcWidth = this.mCurrentWidth;
        this.mSrcColor = this.mCurrentColor;
        this.mSrcAlpha = this.mCurrentAlpha;
    }

    public void setResult() {
        this.mCurrentWidth = this.mDstWidth;
        this.mCurrentAlpha = this.mDstAlpha;
        this.mCurrentColor = this.mDstColor;
        this.mPaint.setColor(this.mCurrentColor);
        this.mPaint.setAlpha(this.mCurrentAlpha);
        this.mSrcWidth = this.mCurrentWidth;
        this.mSrcColor = this.mCurrentColor;
        this.mSrcAlpha = this.mCurrentAlpha;
    }

    public void updateValue(float f2) {
        float f3 = this.mDstWidth;
        float f4 = this.mSrcWidth;
        this.mCurrentWidth = f4 + ((f3 - f4) * f2);
        if (this.mSrcColor != this.mDstColor) {
            this.mCurrentColor = ((Integer) new ArgbEvaluator().evaluate(f2, Integer.valueOf(this.mSrcColor), Integer.valueOf(this.mDstColor))).intValue();
            this.mPaint.setColor(this.mCurrentColor);
        }
        int i = this.mSrcAlpha;
        int i2 = this.mDstAlpha;
        if (i != i2) {
            this.mCurrentAlpha = (int) (((float) i) + (((float) (i2 - i)) * f2));
            this.mPaint.setAlpha(this.mCurrentAlpha);
        }
    }
}
