package com.android.gallery3d.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;

abstract class CanvasTexture extends UploadedTexture {
    protected Canvas mCanvas;
    private final Bitmap.Config mConfig = Bitmap.Config.ARGB_8888;

    public CanvasTexture(int i, int i2) {
        setSize(i, i2);
        setOpaque(false);
    }

    /* access modifiers changed from: protected */
    public abstract void onDraw(Canvas canvas, Bitmap bitmap);

    /* access modifiers changed from: protected */
    @Override // com.android.gallery3d.ui.UploadedTexture
    public void onFreeBitmap(Bitmap bitmap) {
        if (!BasicTexture.inFinalizer()) {
            bitmap.recycle();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.gallery3d.ui.UploadedTexture
    public Bitmap onGetBitmap() {
        Bitmap createBitmap = Bitmap.createBitmap(((BasicTexture) this).mWidth, ((BasicTexture) this).mHeight, this.mConfig);
        this.mCanvas = new Canvas(createBitmap);
        onDraw(this.mCanvas, createBitmap);
        return createBitmap;
    }
}
