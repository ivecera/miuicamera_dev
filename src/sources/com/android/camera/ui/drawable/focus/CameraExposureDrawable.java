package com.android.camera.ui.drawable.focus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.internal.view.SupportMenu;
import com.android.camera.Util;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraExposureDrawable extends Drawable {
    public static final int BASE_COLOR = Color.rgb(255, 204, 0);
    private static final int LINE_COLOR = Color.argb(102, Color.red(BASE_COLOR), Color.green(BASE_COLOR), Color.blue(BASE_COLOR));
    private float mMiddleX = -1.0f;
    private float mMiddleY = -1.0f;
    private CameraFocusPaintBigCircle mPaintBigCircle;
    private CameraFocusPaintCenterIndicator mPaintCenterIndicator;
    private CameraFocusPaintEvAdjust mPaintEvAdjust;
    private CameraFocusPaintEvText mPaintEvText;

    public CameraExposureDrawable(Context context) {
        this.mPaintBigCircle = new CameraFocusPaintBigCircle(context);
        this.mPaintEvAdjust = new CameraFocusPaintEvAdjust(context);
        this.mPaintEvText = new CameraFocusPaintEvText(context);
        this.mPaintCenterIndicator = new CameraFocusPaintCenterIndicator(context);
        this.mPaintBigCircle.setTargetValues(1.0f, SupportMenu.CATEGORY_MASK, 205, (float) Util.dpToPixel(1.0f));
        this.mPaintEvAdjust.setTargetValues(1.0f, BASE_COLOR, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(1.0f));
        this.mPaintEvText.setTargetValues(1.0f, BASE_COLOR, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(1.0f));
        this.mPaintCenterIndicator.setTargetValues(1.0f, BASE_COLOR, 240, (float) Util.dpToPixel(1.3f));
        this.mPaintBigCircle.setResult();
        this.mPaintEvAdjust.setResult();
        this.mPaintEvText.setResult();
        this.mPaintCenterIndicator.setResult();
    }

    public void draw(@NonNull Canvas canvas) {
        if (canvas != null && this.mMiddleX != -1.0f && this.mMiddleY != -1.0f) {
            canvas.save();
            this.mPaintBigCircle.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mPaintCenterIndicator.drawCanvas(canvas);
            canvas.restore();
        }
    }

    public int getOpacity() {
        return -1;
    }

    public void reset() {
        this.mPaintEvAdjust.setShowLine(false);
        this.mPaintEvAdjust.setDistanceY(0.0f);
        this.mPaintEvAdjust.setEvValue(0.0f);
        this.mPaintEvText.setEvValue(0.0f);
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int i) {
    }

    public void setCenter(int i, int i2) {
        float f2 = (float) i;
        this.mMiddleX = f2;
        float f3 = (float) i2;
        this.mMiddleY = f3;
        this.mPaintBigCircle.setMiddle(f2, f3, (float) CameraFocusAnimateDrawable.BIG_RADIUS);
        this.mPaintEvAdjust.setMiddle(f2, f3, (float) CameraFocusAnimateDrawable.BIG_RADIUS);
        this.mPaintEvText.setMiddle(f2, f3, (float) CameraFocusAnimateDrawable.BIG_RADIUS);
        this.mPaintCenterIndicator.setMiddle(f2, f3, (float) CameraFocusAnimateDrawable.CENTER_BASE_RADIUS);
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public void setEvChanged(float f2, float f3) {
        this.mPaintEvAdjust.setEvValue(f3);
        this.mPaintEvAdjust.setShowLine(true);
        this.mPaintEvAdjust.setDistanceY(f2);
        this.mPaintEvText.setEvValue(f3);
        invalidateSelf();
    }

    public void setIndicatorData(CameraIndicatorState cameraIndicatorState, Bitmap bitmap) {
        this.mPaintCenterIndicator.setIndicatorData(cameraIndicatorState, bitmap);
    }

    public void setOrientation(int i) {
        this.mPaintEvAdjust.setOrientation(i);
    }

    public void setRtlAndDisplayRect(boolean z, Rect rect) {
        this.mPaintEvAdjust.setRtlAndDisplayRect(z, rect);
    }
}
