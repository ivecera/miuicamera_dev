package com.android.gallery3d.ui;

import android.graphics.Bitmap;

public class BitmapTexture extends UploadedTexture {
    protected Bitmap mContentBitmap;

    public BitmapTexture(Bitmap bitmap) {
        this(bitmap, false);
    }

    private BitmapTexture(Bitmap bitmap, boolean z) {
        super(z);
        Utils.assertTrue(bitmap != null && !bitmap.isRecycled());
        this.mContentBitmap = bitmap;
    }

    @Override // com.android.gallery3d.ui.UploadedTexture
    public Bitmap getBitmap() {
        return this.mContentBitmap;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.gallery3d.ui.UploadedTexture
    public void onFreeBitmap(Bitmap bitmap) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.gallery3d.ui.UploadedTexture
    public Bitmap onGetBitmap() {
        return this.mContentBitmap;
    }
}
