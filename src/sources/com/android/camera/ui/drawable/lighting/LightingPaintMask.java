package com.android.camera.ui.drawable.lighting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.android.camera.ui.drawable.CameraPaintBase;

public class LightingPaintMask extends CameraPaintBase {
    private int mHeight;
    private int mWidth;
    private PorterDuffXfermode porterDuffXfermode;

    public LightingPaintMask(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        ((CameraPaintBase) this).mPaint.reset();
        ((CameraPaintBase) this).mPaint.setColor(Color.argb(((CameraPaintBase) this).mCurrentAlpha, 0, 0, 0));
        canvas.drawRect(0.0f, 0.0f, (float) this.mWidth, (float) this.mHeight, ((CameraPaintBase) this).mPaint);
        ((CameraPaintBase) this).mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawCircle(((CameraPaintBase) this).mMiddleX, ((CameraPaintBase) this).mMiddleY, ((CameraPaintBase) this).mBaseRadius * ((CameraPaintBase) this).mCurrentWidthPercent, ((CameraPaintBase) this).mPaint);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        ((CameraPaintBase) this).mPaint.setAntiAlias(true);
        this.porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    public void setData(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }
}
