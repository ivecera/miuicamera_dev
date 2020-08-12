package com.android.camera.ui.drawable.lighting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.android.camera.ui.drawable.CameraPaintBase;

public class LightingPaintCircle extends CameraPaintBase {
    private RectF arcRectF;

    public LightingPaintCircle(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        ((CameraPaintBase) this).mPaint.setStrokeWidth(8.0f);
        canvas.drawCircle(((CameraPaintBase) this).mMiddleX, ((CameraPaintBase) this).mMiddleY, ((CameraPaintBase) this).mBaseRadius * ((CameraPaintBase) this).mCurrentWidthPercent, ((CameraPaintBase) this).mPaint);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        this.arcRectF = new RectF();
        ((CameraPaintBase) this).mCurrentColor = -1;
        ((CameraPaintBase) this).mPaint.setAntiAlias(true);
        ((CameraPaintBase) this).mPaint.setStyle(Paint.Style.STROKE);
    }
}
