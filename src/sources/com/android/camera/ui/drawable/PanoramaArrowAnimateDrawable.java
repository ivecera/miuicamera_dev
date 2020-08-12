package com.android.camera.ui.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class PanoramaArrowAnimateDrawable extends Drawable {
    public static final float LEFT_ARROW_RATIO = 0.0f;
    public static final float RIGHT_ARROW_RATIO = 2.0f;
    private Paint mPaint = new Paint();
    private float mTransformationRatio;

    public PanoramaArrowAnimateDrawable() {
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(-1);
        this.mPaint.setAntiAlias(true);
        setTransformationRatio(2.0f);
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        Path path = new Path();
        float f2 = (float) height;
        path.moveTo(0.0f, ((2.0f - this.mTransformationRatio) * f2) / 4.0f);
        path.lineTo(0.0f, ((this.mTransformationRatio + 2.0f) * f2) / 4.0f);
        float f3 = (float) width;
        path.lineTo(f3, (((2.0f - this.mTransformationRatio) + 2.0f) * f2) / 4.0f);
        path.lineTo(f3, ((2.0f - (2.0f - this.mTransformationRatio)) * f2) / 4.0f);
        path.lineTo(0.0f, ((2.0f - this.mTransformationRatio) * f2) / 4.0f);
        path.close();
        canvas.drawPath(path, this.mPaint);
    }

    public int getOpacity() {
        return -1;
    }

    public float getTransformationRatio() {
        return this.mTransformationRatio;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setTransformationRatio(float f2) {
        this.mTransformationRatio = f2;
        invalidateSelf();
    }
}
