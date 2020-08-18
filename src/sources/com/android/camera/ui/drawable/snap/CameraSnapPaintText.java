package com.android.camera.ui.drawable.snap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraSnapPaintText extends CameraPaintBase {
    private static final float RECORDING_ROUND_WIDTH = 0.265f;
    public boolean isRecordingCircle;
    public boolean isRoundingCircle;
    private float mCurrentRoundRectRadius;
    private RectF mRectF;
    private float mRoundingProgress = 1.0f;

    public CameraSnapPaintText(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        if (!((CameraPaintBase) this).isRecording) {
            canvas.drawCircle(((CameraPaintBase) this).mMiddleX, ((CameraPaintBase) this).mMiddleY, ((CameraPaintBase) this).mBaseRadius * ((CameraPaintBase) this).mCurrentWidthPercent, ((CameraPaintBase) this).mPaint);
        } else if (this.isRecordingCircle) {
            canvas.drawCircle(((CameraPaintBase) this).mMiddleX, ((CameraPaintBase) this).mMiddleY, ((CameraPaintBase) this).mBaseRadius * 0.55f, ((CameraPaintBase) this).mPaint);
        } else if (this.isRoundingCircle) {
            canvas.drawCircle(((CameraPaintBase) this).mMiddleX, ((CameraPaintBase) this).mMiddleY, ((CameraPaintBase) this).mBaseRadius * ((CameraPaintBase) this).mCurrentWidthPercent * this.mRoundingProgress, ((CameraPaintBase) this).mPaint);
        } else {
            float f2 = ((CameraPaintBase) this).mBaseRadius * this.mCurrentRoundRectRadius;
            float f3 = ((CameraPaintBase) this).mMiddleX;
            float f4 = ((CameraPaintBase) this).mMiddleY;
            RectF rectF = this.mRectF;
            rectF.set(f3 - f2, f4 - f2, f3 + f2, f4 + f2);
            RectF rectF2 = this.mRectF;
            float f5 = this.mRoundingProgress;
            canvas.drawRoundRect(rectF2, f2 * f5, f2 * f5, ((CameraPaintBase) this).mPaint);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        ((CameraPaintBase) this).mPaint.setAntiAlias(true);
        ((CameraPaintBase) this).mPaint.setStyle(Paint.Style.FILL);
        this.mRectF = new RectF();
    }

    public void prepareRecord() {
        this.mCurrentRoundRectRadius = ((CameraPaintBase) this).mCurrentWidthPercent;
        this.mRoundingProgress = 1.0f;
    }

    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void resetRecordingState() {
        super.resetRecordingState();
        this.isRecordingCircle = false;
        this.isRoundingCircle = false;
    }

    public void updateRecordValue(float f2, boolean z) {
        if (z) {
            if (this.isRoundingCircle) {
                this.mRoundingProgress = 1.0f - (0.45f * f2);
            } else {
                this.mRoundingProgress = 1.0f - (0.65f * f2);
            }
            float f3 = ((CameraPaintBase) this).mCurrentWidthPercent;
            this.mCurrentRoundRectRadius = f3 - ((f3 - RECORDING_ROUND_WIDTH) * f2);
            return;
        }
        if (this.isRoundingCircle) {
            this.mRoundingProgress = (0.45f * f2) + 0.55f;
        } else {
            this.mRoundingProgress = (0.65f * f2) + 0.35f;
        }
        this.mCurrentRoundRectRadius = ((((CameraPaintBase) this).mCurrentWidthPercent - RECORDING_ROUND_WIDTH) * f2) + RECORDING_ROUND_WIDTH;
    }
}
