package com.android.camera.ui.drawable.snap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import com.android.camera.R;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraSnapPaintCenterVV extends CameraPaintBase {
    private static final float RECORDING_ROUND_WIDTH = 0.265f;
    private float mCurrentRoundRectRadius;
    private String mDurationText;
    private RectF mRectF;
    private float mRoundingProgress = 1.0f;
    private Rect mTextBound;
    private Paint mTextPaint;

    public CameraSnapPaintCenterVV(Context context) {
        super(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.SettingStatusBarText, new int[]{16842901, 16842904});
        int color = obtainStyledAttributes.getColor(obtainStyledAttributes.getIndex(1), -1);
        obtainStyledAttributes.getDimensionPixelSize(obtainStyledAttributes.getIndex(0), 0);
        obtainStyledAttributes.recycle();
        this.mTextPaint = new Paint();
        this.mTextPaint.setColor(color);
        this.mTextPaint.setStyle(Paint.Style.FILL);
        this.mTextPaint.setTextSize((float) context.getResources().getDimensionPixelSize(R.dimen.vv_progress_center_text));
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setAlpha(255);
        this.mTextBound = new Rect();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        float f2 = ((CameraPaintBase) this).mBaseRadius * this.mCurrentRoundRectRadius;
        float f3 = ((CameraPaintBase) this).mMiddleX;
        float f4 = ((CameraPaintBase) this).mMiddleY;
        RectF rectF = this.mRectF;
        rectF.set(f3 - f2, f4 - f2, f3 + f2, f4 + f2);
        if (!((CameraPaintBase) this).isRecording) {
            RectF rectF2 = this.mRectF;
            float f5 = this.mRoundingProgress;
            canvas.drawRoundRect(rectF2, f2 * f5, f2 * f5, ((CameraPaintBase) this).mPaint);
        }
        if (!TextUtils.isEmpty(this.mDurationText)) {
            Paint paint = this.mTextPaint;
            String str = this.mDurationText;
            paint.getTextBounds(str, 0, str.length(), this.mTextBound);
            canvas.drawText(this.mDurationText, ((CameraPaintBase) this).mMiddleX - ((float) (this.mTextBound.width() / 2)), ((CameraPaintBase) this).mMiddleY + ((float) (this.mTextBound.height() / 2)), this.mTextPaint);
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
        this.mDurationText = null;
    }

    public void setDurationText(String str) {
        this.mDurationText = str;
    }

    public void updateRecordValue(float f2, boolean z) {
        if (z) {
            this.mRoundingProgress = 1.0f - (0.65f * f2);
            float f3 = ((CameraPaintBase) this).mCurrentWidthPercent;
            this.mCurrentRoundRectRadius = f3 - ((f3 - RECORDING_ROUND_WIDTH) * f2);
            return;
        }
        this.mRoundingProgress = (0.65f * f2) + 0.35f;
        this.mCurrentRoundRectRadius = ((((CameraPaintBase) this).mCurrentWidthPercent - RECORDING_ROUND_WIDTH) * f2) + RECORDING_ROUND_WIDTH;
    }
}
