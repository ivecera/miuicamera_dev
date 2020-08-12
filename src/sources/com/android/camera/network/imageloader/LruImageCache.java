package com.android.camera.network.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

class LruImageCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(this.max) {
        /* class com.android.camera.network.imageloader.LruImageCache.AnonymousClass1 */

        /* access modifiers changed from: protected */
        public int sizeOf(String str, Bitmap bitmap) {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    };
    private int max = 5242880;

    @Override // com.android.volley.toolbox.ImageLoader.ImageCache
    public Bitmap getBitmap(String str) {
        return this.lruCache.get(str);
    }

    @Override // com.android.volley.toolbox.ImageLoader.ImageCache
    public void putBitmap(String str, Bitmap bitmap) {
        this.lruCache.put(str, bitmap);
    }
}
