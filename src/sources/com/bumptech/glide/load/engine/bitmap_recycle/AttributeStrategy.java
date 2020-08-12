package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.l;

class AttributeStrategy implements k {
    private final KeyPool _n = new KeyPool();
    private final g<Key, Bitmap> bo = new g<>();

    @VisibleForTesting
    static class Key implements l {
        private Bitmap.Config config;
        private int height;
        private final KeyPool pool;
        private int width;

        public Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.l
        public void O() {
            this.pool.a(this);
        }

        public void e(int i, int i2, Bitmap.Config config2) {
            this.width = i;
            this.height = i2;
            this.config = config2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return this.width == key.width && this.height == key.height && this.config == key.config;
        }

        public int hashCode() {
            int i = ((this.width * 31) + this.height) * 31;
            Bitmap.Config config2 = this.config;
            return i + (config2 != null ? config2.hashCode() : 0);
        }

        public String toString() {
            return AttributeStrategy.f(this.width, this.height, this.config);
        }
    }

    @VisibleForTesting
    static class KeyPool extends c<Key> {
        KeyPool() {
        }

        /* access modifiers changed from: protected */
        @Override // com.bumptech.glide.load.engine.bitmap_recycle.c
        public Key create() {
            return new Key(this);
        }

        /* access modifiers changed from: package-private */
        public Key d(int i, int i2, Bitmap.Config config) {
            Key key = (Key) get();
            key.e(i, i2, config);
            return key;
        }
    }

    AttributeStrategy() {
    }

    static String f(int i, int i2, Bitmap.Config config) {
        return "[" + i + "x" + i2 + "], " + config;
    }

    private static String k(Bitmap bitmap) {
        return f(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    public void a(Bitmap bitmap) {
        this.bo.a(this._n.d(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig()), bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    public String b(int i, int i2, Bitmap.Config config) {
        return f(i, i2, config);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    public int c(Bitmap bitmap) {
        return l.j(bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    public Bitmap d(int i, int i2, Bitmap.Config config) {
        return this.bo.b(this._n.d(i, i2, config));
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    public String e(Bitmap bitmap) {
        return k(bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    public Bitmap removeLast() {
        return this.bo.removeLast();
    }

    public String toString() {
        return "AttributeStrategy:\n  " + this.bo;
    }
}
