package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.l;
import java.util.NavigableMap;

@RequiresApi(19)
final class SizeStrategy implements k {
    private static final int so = 8;
    private final KeyPool _n = new KeyPool();
    private final g<Key, Bitmap> bo = new g<>();
    private final NavigableMap<Integer, Integer> fo = new PrettyPrintTreeMap();

    @VisibleForTesting
    static final class Key implements l {
        private final KeyPool pool;
        int size;

        Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.l
        public void O() {
            this.pool.a(this);
        }

        public boolean equals(Object obj) {
            return (obj instanceof Key) && this.size == ((Key) obj).size;
        }

        public int hashCode() {
            return this.size;
        }

        public void init(int i) {
            this.size = i;
        }

        public String toString() {
            return SizeStrategy.z(this.size);
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

        public Key get(int i) {
            Key key = (Key) super.get();
            key.init(i);
            return key;
        }
    }

    SizeStrategy() {
    }

    private void g(Integer num) {
        Integer num2 = (Integer) this.fo.get(num);
        if (num2.intValue() == 1) {
            this.fo.remove(num);
        } else {
            this.fo.put(num, Integer.valueOf(num2.intValue() - 1));
        }
    }

    private static String k(Bitmap bitmap) {
        return z(l.j(bitmap));
    }

    static String z(int i) {
        return "[" + i + "]";
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    public void a(Bitmap bitmap) {
        Key key = this._n.get(l.j(bitmap));
        this.bo.a(key, bitmap);
        Integer num = (Integer) this.fo.get(Integer.valueOf(key.size));
        NavigableMap<Integer, Integer> navigableMap = this.fo;
        Integer valueOf = Integer.valueOf(key.size);
        int i = 1;
        if (num != null) {
            i = 1 + num.intValue();
        }
        navigableMap.put(valueOf, Integer.valueOf(i));
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    public String b(int i, int i2, Bitmap.Config config) {
        return z(l.g(i, i2, config));
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    public int c(Bitmap bitmap) {
        return l.j(bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    @Nullable
    public Bitmap d(int i, int i2, Bitmap.Config config) {
        int g = l.g(i, i2, config);
        Key key = this._n.get(g);
        Integer ceilingKey = this.fo.ceilingKey(Integer.valueOf(g));
        if (!(ceilingKey == null || ceilingKey.intValue() == g || ceilingKey.intValue() > g * 8)) {
            this._n.a(key);
            key = this._n.get(ceilingKey.intValue());
        }
        Bitmap b2 = this.bo.b(key);
        if (b2 != null) {
            b2.reconfigure(i, i2, config);
            g(ceilingKey);
        }
        return b2;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    public String e(Bitmap bitmap) {
        return k(bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.k
    @Nullable
    public Bitmap removeLast() {
        Bitmap removeLast = this.bo.removeLast();
        if (removeLast != null) {
            g(Integer.valueOf(l.j(removeLast)));
        }
        return removeLast;
    }

    public String toString() {
        return "SizeStrategy:\n  " + this.bo + "\n  SortedSizes" + this.fo;
    }
}
