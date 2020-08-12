package com.android.camera.ui.drawable.focus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.android.camera.Util;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraFocusPaintBigCircle extends CameraPaintBase {
    public CameraFocusPaintBigCircle(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        ((CameraPaintBase) this).mPaint.setAlpha(((CameraPaintBase) this).mCurrentAlpha);
        canvas.drawCircle(((CameraPaintBase) this).mMiddleX, ((CameraPaintBase) this).mMiddleY, ((CameraPaintBase) this).mBaseRadius * ((CameraPaintBase) this).mCurrentWidthPercent, ((CameraPaintBase) this).mPaint);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        ((CameraPaintBase) this).mPaint.setAntiAlias(true);
        ((CameraPaintBase) this).mPaint.setStrokeWidth((float) Util.dpToPixel(1.0f));
        ((CameraPaintBase) this).mPaint.setStyle(Paint.Style.STROKE);
        ((CameraPaintBase) this).mPaint.setAlpha(((CameraPaintBase) this).mCurrentAlpha);
    }
}
