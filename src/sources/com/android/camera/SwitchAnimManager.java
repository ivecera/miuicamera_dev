package com.android.camera;

import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.GLCanvas;
import com.android.gallery3d.ui.RawTexture;

public class SwitchAnimManager {
    private static final float ROTATE_DURATION = 300.0f;
    private static final String TAG = "SwitchAnimManager";
    private static final float ZOOM_DELTA_PREVIEW = 0.2f;
    private boolean mAlphaAnimation;
    private long mAnimStartTime;
    private float mExtScale = 1.0f;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private boolean mMoveBack;
    private boolean mNewPreview;
    private int mPreviewFrameLayoutWidth;
    private boolean mRecurBlur;
    private int mReviewDrawingHeight;
    private int mReviewDrawingWidth;
    private int mReviewDrawingX;
    private int mReviewDrawingY;
    private float mTimeOut = ROTATE_DURATION;

    public SwitchAnimManager() {
    }

    public SwitchAnimManager(float f2) {
        this.mTimeOut = f2;
    }

    private boolean drawAnimationBlend(GLCanvas gLCanvas, int i, int i2, int i3, int i4, CameraScreenNail cameraScreenNail, RawTexture rawTexture) {
        boolean z;
        if (this.mNewPreview == 1) {
            return false;
        }
        long uptimeMillis = SystemClock.uptimeMillis() - this.mAnimStartTime;
        float f2 = this.mTimeOut;
        if (((float) uptimeMillis) > f2) {
            uptimeMillis = (long) f2;
            z = false;
        } else {
            z = true;
        }
        if (!z && this.mRecurBlur == 1) {
            this.mRecurBlur = false;
        }
        drawBlurTexture(gLCanvas, i, i2, i3, i4, cameraScreenNail, this.mInterpolator.getInterpolation(((float) uptimeMillis) / this.mTimeOut));
        return z;
    }

    private void drawBlurTexture(GLCanvas gLCanvas, int i, int i2, int i3, int i4, CameraScreenNail cameraScreenNail, float f2) {
        if (this.mRecurBlur == 1) {
            cameraScreenNail.renderBlurTexture(gLCanvas);
        }
        gLCanvas.getState().pushState();
        if (this.mAlphaAnimation == 1) {
            gLCanvas.getState().setBlendAlpha(1.0f - (f2 * 0.7f));
        }
        cameraScreenNail.drawBlurTexture(gLCanvas, this.mReviewDrawingX, this.mReviewDrawingY, this.mReviewDrawingWidth, this.mReviewDrawingHeight);
        gLCanvas.getState().popState();
    }

    private void drawCopiedTexture(GLCanvas gLCanvas, int i, int i2, RawTexture rawTexture, float f2) {
        if (this.mMoveBack == 1) {
            int i3 = this.mReviewDrawingWidth;
            float f3 = ((float) this.mReviewDrawingX) + (((float) i3) / 2.0f);
            int i4 = this.mReviewDrawingHeight;
            float f4 = 1.0f - (ZOOM_DELTA_PREVIEW * f2);
            float f5 = ((float) i3) * f4;
            float f6 = ((float) i4) * f4;
            rawTexture.draw(gLCanvas, Math.round(f3 - (f5 / 2.0f)), Math.round((((float) this.mReviewDrawingY) + (((float) i4) / 2.0f)) - (f6 / 2.0f)), Math.round(f5), Math.round(f6));
            return;
        }
        float width = (float) rawTexture.getWidth();
        float f7 = width / 2.0f;
        float height = (float) rawTexture.getHeight();
        float f8 = height / 2.0f;
        float f9 = 1.0f - (ZOOM_DELTA_PREVIEW * f2);
        float f10 = width * f9;
        float f11 = height * f9;
        rawTexture.draw(gLCanvas, Math.round(f7 - (f10 / 2.0f)), Math.round(f8 - (f11 / 2.0f)), Math.round(f10), Math.round(f11), this.mReviewDrawingX, this.mReviewDrawingY, this.mReviewDrawingWidth, this.mReviewDrawingHeight);
    }

    private void drawRealTimeTexture(GLCanvas gLCanvas, int i, int i2, int i3, int i4, CameraScreenNail cameraScreenNail, float f2) {
        if (this.mNewPreview) {
            gLCanvas.getState().pushState();
            gLCanvas.getState().setAlpha(f2);
            cameraScreenNail.directDraw(gLCanvas, i, i2, i3, i4);
            gLCanvas.getState().popState();
        }
    }

    public void clearAnimation() {
        this.mAnimStartTime = 0;
        this.mRecurBlur = false;
        this.mAlphaAnimation = false;
    }

    public boolean drawAnimation(GLCanvas gLCanvas, int i, int i2, int i3, int i4, CameraScreenNail cameraScreenNail, RawTexture rawTexture) {
        return drawAnimationBlend(gLCanvas, i, i2, i3, i4, cameraScreenNail, rawTexture);
    }

    public boolean drawPreview(GLCanvas gLCanvas, int i, int i2, int i3, int i4, RawTexture rawTexture) {
        float f2;
        float f3 = (float) i3;
        float f4 = ((float) i) + (f3 / 2.0f);
        float f5 = ((float) i2) + (((float) i4) / 2.0f);
        int i5 = this.mPreviewFrameLayoutWidth;
        if (i5 != 0) {
            f2 = f3 / ((float) i5);
        } else {
            Log.e(TAG, "previewFrameLayoutWidth=0");
            f2 = 1.0f;
        }
        float f6 = ((float) this.mReviewDrawingWidth) * f2;
        float f7 = ((float) this.mReviewDrawingHeight) * f2;
        int round = Math.round(f4 - (f6 / 2.0f));
        int round2 = Math.round(f5 - (f7 / 2.0f));
        float alpha = gLCanvas.getState().getAlpha();
        rawTexture.draw(gLCanvas, round, round2, Math.round(f6), Math.round(f7));
        gLCanvas.getState().setAlpha(alpha);
        return true;
    }

    public float getExtScaleX() {
        return this.mExtScale;
    }

    public float getExtScaleY() {
        return this.mExtScale;
    }

    public void setPreviewFrameLayoutSize(int i, int i2) {
        this.mPreviewFrameLayoutWidth = i;
        if (i == 0) {
            Log.e(TAG, "invalid preview frame width", new RuntimeException());
        }
    }

    public void setReviewDrawingSize(int i, int i2, int i3, int i4) {
        this.mReviewDrawingX = i;
        this.mReviewDrawingY = i2;
        this.mReviewDrawingWidth = i3;
        this.mReviewDrawingHeight = i4;
        this.mMoveBack = CameraSettings.isBackCamera();
        this.mNewPreview = false;
    }

    public void startAnimation(boolean z) {
        if (z == 1) {
            Log.v(TAG, "startAnimation with alpha animation");
        } else {
            Log.v(TAG, "startAnimation");
        }
        this.mAnimStartTime = SystemClock.uptimeMillis();
        this.mRecurBlur = true;
        this.mAlphaAnimation = z;
    }

    public void startResume() {
        Log.v(TAG, "startResume");
        this.mNewPreview = true;
        this.mRecurBlur = false;
    }
}
