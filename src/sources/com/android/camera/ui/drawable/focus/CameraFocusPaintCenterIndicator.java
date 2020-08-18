package com.android.camera.ui.drawable.focus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraFocusPaintCenterIndicator extends CameraPaintBase {
    public CameraIndicatorState indicatorState;
    private Bitmap mAEAFLockBodyBitmap;
    private Bitmap mAEAFLockHeadBitmap;
    private Bitmap mCaptureIndicatorBitmap;
    private Matrix mCaptureIndicatorBitmapMatrix;
    public int mCenterFlagForAnimator;
    private Matrix mLockMatrix;
    private Paint mMinusMoonPaint;
    private Paint mSunPaint;

    public CameraFocusPaintCenterIndicator(Context context) {
        super(context);
    }

    private void drawCenterCaptureBitmap(Canvas canvas) {
        Matrix matrix = this.mCaptureIndicatorBitmapMatrix;
        if (matrix == null) {
            this.mCaptureIndicatorBitmapMatrix = new Matrix();
        } else {
            matrix.reset();
        }
        if (!this.mCaptureIndicatorBitmap.isRecycled()) {
            this.mCaptureIndicatorBitmapMatrix.postTranslate((float) ((-this.mCaptureIndicatorBitmap.getWidth()) / 2), (float) ((-this.mCaptureIndicatorBitmap.getHeight()) / 2));
            canvas.drawBitmap(this.mCaptureIndicatorBitmap, this.mCaptureIndicatorBitmapMatrix, ((CameraPaintBase) this).mPaint);
        }
    }

    private void drawCenterCircle(Canvas canvas, float f2, float f3, float f4, @NonNull Paint paint) {
        canvas.drawCircle(f2, f3, f4, paint);
    }

    private void drawCenterLock(Canvas canvas) {
        if (this.mAEAFLockHeadBitmap != null && this.mAEAFLockBodyBitmap != null) {
            Matrix matrix = this.mLockMatrix;
            if (matrix == null) {
                this.mLockMatrix = new Matrix();
            } else {
                matrix.reset();
            }
            if (!this.mAEAFLockHeadBitmap.isRecycled() && !this.mAEAFLockBodyBitmap.isRecycled()) {
                this.mLockMatrix.postTranslate((float) ((-this.mAEAFLockHeadBitmap.getWidth()) / 2), ((float) ((-this.mAEAFLockHeadBitmap.getHeight()) / 2)) + this.indicatorState.mAEAFHeadPosition);
                canvas.drawBitmap(this.mAEAFLockHeadBitmap, this.mLockMatrix, ((CameraPaintBase) this).mPaint);
                this.mLockMatrix.reset();
                this.mLockMatrix.postTranslate((float) ((-this.mAEAFLockBodyBitmap.getWidth()) / 2), (float) ((-this.mAEAFLockBodyBitmap.getHeight()) / 2));
                canvas.drawBitmap(this.mAEAFLockBodyBitmap, this.mLockMatrix, ((CameraPaintBase) this).mPaint);
            }
        }
    }

    private void drawCenterMoon(Canvas canvas) {
        drawCenterCircle(canvas, 0.0f, 0.0f, (float) this.indicatorState.mCurrentRadius, this.mSunPaint);
        CameraIndicatorState cameraIndicatorState = this.indicatorState;
        int i = cameraIndicatorState.mCurrentMinusCircleCenter;
        drawCenterCircle(canvas, (float) (-i), (float) (-i), cameraIndicatorState.mCurrentMinusCircleRadius, this.mMinusMoonPaint);
    }

    private void drawCenterSun(Canvas canvas) {
        canvas.rotate(this.indicatorState.mCurrentAngle);
        for (int i = 0; i < 2; i++) {
            if (i > 0) {
                canvas.rotate(45.0f);
            }
            CameraIndicatorState cameraIndicatorState = this.indicatorState;
            int i2 = cameraIndicatorState.mCurrentRayWidth;
            int i3 = cameraIndicatorState.mCurrentRayBottom;
            canvas.drawRect((float) ((-i2) / 2), (float) ((-i3) - cameraIndicatorState.mCurrentRayHeight), (float) (i2 / 2), (float) (-i3), this.mSunPaint);
            CameraIndicatorState cameraIndicatorState2 = this.indicatorState;
            int i4 = cameraIndicatorState2.mCurrentRayWidth;
            int i5 = cameraIndicatorState2.mCurrentRayBottom;
            canvas.drawRect((float) ((-i4) / 2), (float) i5, (float) (i4 / 2), (float) (i5 + cameraIndicatorState2.mCurrentRayHeight), this.mSunPaint);
            CameraIndicatorState cameraIndicatorState3 = this.indicatorState;
            int i6 = cameraIndicatorState3.mCurrentRayBottom;
            int i7 = cameraIndicatorState3.mCurrentRayWidth;
            canvas.drawRect((float) ((-i6) - cameraIndicatorState3.mCurrentRayHeight), (float) ((-i7) / 2), (float) (-i6), (float) (i7 / 2), this.mSunPaint);
            CameraIndicatorState cameraIndicatorState4 = this.indicatorState;
            int i8 = cameraIndicatorState4.mCurrentRayBottom;
            int i9 = cameraIndicatorState4.mCurrentRayWidth;
            canvas.drawRect((float) i8, (float) ((-i9) / 2), (float) (i8 + cameraIndicatorState4.mCurrentRayHeight), (float) (i9 / 2), this.mSunPaint);
        }
        canvas.drawCircle(0.0f, 0.0f, (float) this.indicatorState.mCurrentRadius, this.mSunPaint);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        canvas.translate(((CameraPaintBase) this).mMiddleX, ((CameraPaintBase) this).mMiddleY);
        int i = this.indicatorState.mCenterFlag;
        if (i == 2) {
            ((CameraPaintBase) this).mPaint.setAlpha(((CameraPaintBase) this).mCurrentAlpha);
            drawCenterCaptureBitmap(canvas);
        } else if (i == 3) {
            this.mSunPaint.setColor(((CameraPaintBase) this).mCurrentColor);
            this.mSunPaint.setAlpha(((CameraPaintBase) this).mCurrentAlpha);
            drawCenterSun(canvas);
        } else if (i == 4) {
            this.mSunPaint.setColor(((CameraPaintBase) this).mCurrentColor);
            this.mSunPaint.setAlpha(((CameraPaintBase) this).mCurrentAlpha);
            drawCenterMoon(canvas);
        } else if (i != 5) {
            ((CameraPaintBase) this).mPaint.setStrokeWidth(((CameraPaintBase) this).mCurrentStrokeWidth);
            ((CameraPaintBase) this).mPaint.setAlpha(((CameraPaintBase) this).mCurrentAlpha);
            drawCenterCircle(canvas, 0.0f, 0.0f, ((CameraPaintBase) this).mBaseRadius * ((CameraPaintBase) this).mCurrentWidthPercent, ((CameraPaintBase) this).mPaint);
        } else {
            ((CameraPaintBase) this).mPaint.setAlpha(((CameraPaintBase) this).mCurrentAlpha);
            drawCenterLock(canvas);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        ((CameraPaintBase) this).mPaint.setAntiAlias(true);
        ((CameraPaintBase) this).mPaint.setStyle(Paint.Style.STROKE);
        this.mSunPaint = new Paint();
        this.mSunPaint.setColor(-1);
        this.mSunPaint.setStyle(Paint.Style.FILL);
        this.mSunPaint.setAntiAlias(true);
        this.mMinusMoonPaint = new Paint();
        this.mMinusMoonPaint.setColor(-1);
        this.mMinusMoonPaint.setStyle(Paint.Style.FILL);
        this.mMinusMoonPaint.setAntiAlias(true);
        this.mMinusMoonPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    public void setAEAFIndicatorData(CameraIndicatorState cameraIndicatorState, Bitmap bitmap, Bitmap bitmap2) {
        this.indicatorState = cameraIndicatorState;
        this.mAEAFLockHeadBitmap = bitmap;
        this.mAEAFLockBodyBitmap = bitmap2;
    }

    public void setCenterFlag(int i) {
        this.indicatorState.mCenterFlag = i;
        this.mCenterFlagForAnimator = i;
    }

    public void setIndicatorData(CameraIndicatorState cameraIndicatorState, Bitmap bitmap) {
        this.indicatorState = cameraIndicatorState;
        this.mCaptureIndicatorBitmap = bitmap;
    }

    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void updateValue(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        super.updateValue(f2);
        if (this.mCenterFlagForAnimator == 5) {
            this.indicatorState.mAEAFHeadPosition = f2 * 5.0f;
        }
    }
}
