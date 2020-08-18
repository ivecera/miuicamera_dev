package com.android.camera.ui.drawable.snap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.android.camera.Util;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraSnapPaintSecond extends CameraPaintBase {
    private boolean mNeedSpacing;

    public CameraSnapPaintSecond(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        float f2 = ((CameraPaintBase) this).mBaseRadius * ((CameraPaintBase) this).mCurrentWidthPercent;
        for (int i = 0; i < 90; i++) {
            canvas.save();
            float f3 = (float) (i * 4);
            canvas.rotate(f3, ((CameraPaintBase) this).mMiddleX, ((CameraPaintBase) this).mMiddleY);
            int i2 = ((CameraPaintBase) this).isRecording ? (f3 != 0.0f || !((CameraPaintBase) this).needZero) ? f3 < ((CameraPaintBase) this).timeAngle ? ((CameraPaintBase) this).isClockwise ? CameraPaintBase.ALPHA_OUTSTANDING : CameraPaintBase.ALPHA_HINT : ((CameraPaintBase) this).isClockwise ? CameraPaintBase.ALPHA_HINT : CameraPaintBase.ALPHA_OUTSTANDING : CameraPaintBase.ALPHA_OUTSTANDING : ((CameraPaintBase) this).mCurrentAlpha;
            if (this.mNeedSpacing && i > 67 && i < 90 && i % 2 != 0) {
                i2 = 0;
            }
            ((CameraPaintBase) this).mPaint.setAlpha(i2);
            float f4 = ((CameraPaintBase) this).mMiddleX;
            float f5 = ((CameraPaintBase) this).mMiddleY;
            canvas.drawLine(f4, f5 - f2, f4, (f5 - f2) + ((float) Util.dpToPixel(5.0f)), ((CameraPaintBase) this).mPaint);
            canvas.restore();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        ((CameraPaintBase) this).mPaint.setAntiAlias(true);
        ((CameraPaintBase) this).mPaint.setStyle(Paint.Style.STROKE);
        ((CameraPaintBase) this).mPaint.setStrokeWidth(3.0f);
    }

    public void setNeedSpacing(boolean z) {
        this.mNeedSpacing = z;
    }
}
