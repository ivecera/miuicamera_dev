package com.miui.extravideo.watermark.gles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;

public class CanvasTexture {
    private float mBitmapScale;
    private Bitmap mBitmapToDraw;
    protected Canvas mCanvas;
    private final Bitmap.Config mConfig = Bitmap.Config.ARGB_8888;
    private int mHeight;
    private int mPaddingX;
    private int mPaddingY;
    private String mStrToDraw;
    private int mWidth;

    public CanvasTexture(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    public Bitmap getBitmap() {
        Bitmap createBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, this.mConfig);
        this.mCanvas = new Canvas(createBitmap);
        if (this.mStrToDraw != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 0);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setAntiAlias(true);
            paint.setTextSize(50.0f);
            this.mCanvas.drawText(this.mStrToDraw, (float) (this.mWidth / 2), (float) (this.mHeight / 2), paint);
        }
        if (this.mBitmapToDraw != null) {
            new Paint();
            Matrix matrix = new Matrix();
            float f2 = this.mBitmapScale;
            matrix.postScale(f2, f2);
            matrix.postTranslate((float) this.mPaddingX, (float) ((this.mHeight - ((int) (((float) this.mBitmapToDraw.getHeight()) * this.mBitmapScale))) - this.mPaddingY));
            this.mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            this.mCanvas.drawBitmap(this.mBitmapToDraw, matrix, new Paint(2));
        }
        return createBitmap;
    }

    public void setBitmap(Bitmap bitmap, int i, int i2, float f2) {
        this.mBitmapToDraw = bitmap;
        this.mPaddingX = i;
        this.mPaddingY = i2;
        this.mBitmapScale = f2;
    }

    public void setText(String str) {
        this.mStrToDraw = str;
    }
}
