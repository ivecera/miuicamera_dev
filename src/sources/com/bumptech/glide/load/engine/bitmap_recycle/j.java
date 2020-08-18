package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: LruBitmapPool */
public class j implements d {
    private static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.ARGB_8888;
    private static final String TAG = "LruBitmapPool";
    private long currentSize;
    private final Set<Bitmap.Config> jo;
    private final long ko;
    private final a lo;
    private long maxSize;
    private int mo;
    private int oo;
    private int po;
    private int qo;
    private final k strategy;

    /* compiled from: LruBitmapPool */
    private interface a {
        void b(Bitmap bitmap);

        void f(Bitmap bitmap);
    }

    /* compiled from: LruBitmapPool */
    private static final class b implements a {
        b() {
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.j.a
        public void b(Bitmap bitmap) {
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.j.a
        public void f(Bitmap bitmap) {
        }
    }

    /* compiled from: LruBitmapPool */
    private static class c implements a {

        /* renamed from: io  reason: collision with root package name */
        private final Set<Bitmap> f285io = Collections.synchronizedSet(new HashSet());

        private c() {
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.j.a
        public void b(Bitmap bitmap) {
            if (!this.f285io.contains(bitmap)) {
                this.f285io.add(bitmap);
                return;
            }
            throw new IllegalStateException("Can't add already added bitmap: " + bitmap + " [" + bitmap.getWidth() + "x" + bitmap.getHeight() + "]");
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.j.a
        public void f(Bitmap bitmap) {
            if (this.f285io.contains(bitmap)) {
                this.f285io.remove(bitmap);
                return;
            }
            throw new IllegalStateException("Cannot remove bitmap not in tracker");
        }
    }

    public j(long j) {
        this(j, wn(), vn());
    }

    j(long j, k kVar, Set<Bitmap.Config> set) {
        this.ko = j;
        this.maxSize = j;
        this.strategy = kVar;
        this.jo = set;
        this.lo = new b();
    }

    public j(long j, Set<Bitmap.Config> set) {
        this(j, wn(), set);
    }

    @TargetApi(26)
    private static void b(Bitmap.Config config) {
        if (Build.VERSION.SDK_INT >= 26 && config == Bitmap.Config.HARDWARE) {
            throw new IllegalArgumentException("Cannot create a mutable Bitmap with config: " + config + ". Consider setting Downsampler#ALLOW_HARDWARE_CONFIG to false in your RequestOptions and/or in GlideBuilder.setDefaultRequestOptions");
        }
    }

    @NonNull
    private static Bitmap createBitmap(int i, int i2, @Nullable Bitmap.Config config) {
        if (config == null) {
            config = DEFAULT_CONFIG;
        }
        return Bitmap.createBitmap(i, i2, config);
    }

    private void dump() {
        if (Log.isLoggable(TAG, 2)) {
            un();
        }
    }

    private synchronized void f(long j) {
        while (this.currentSize > j) {
            Bitmap removeLast = this.strategy.removeLast();
            if (removeLast == null) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Size mismatch, resetting");
                    un();
                }
                this.currentSize = 0;
                return;
            }
            this.lo.f(removeLast);
            this.currentSize -= (long) this.strategy.c(removeLast);
            this.qo++;
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Evicting bitmap=" + this.strategy.e(removeLast));
            }
            dump();
            removeLast.recycle();
        }
    }

    @Nullable
    private synchronized Bitmap h(int i, int i2, @Nullable Bitmap.Config config) {
        Bitmap d2;
        b(config);
        d2 = this.strategy.d(i, i2, config != null ? config : DEFAULT_CONFIG);
        if (d2 == null) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Missing bitmap=" + this.strategy.b(i, i2, config));
            }
            this.oo++;
        } else {
            this.mo++;
            this.currentSize -= (long) this.strategy.c(d2);
            this.lo.f(d2);
            m(d2);
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "Get bitmap=" + this.strategy.b(i, i2, config));
        }
        dump();
        return d2;
    }

    @TargetApi(19)
    private static void l(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            bitmap.setPremultiplied(true);
        }
    }

    private static void m(Bitmap bitmap) {
        bitmap.setHasAlpha(true);
        l(bitmap);
    }

    private void sn() {
        f(this.maxSize);
    }

    private void un() {
        Log.v(TAG, "Hits=" + this.mo + ", misses=" + this.oo + ", puts=" + this.po + ", evictions=" + this.qo + ", currentSize=" + this.currentSize + ", maxSize=" + this.maxSize + "\nStrategy=" + this.strategy);
    }

    @TargetApi(26)
    private static Set<Bitmap.Config> vn() {
        HashSet hashSet = new HashSet(Arrays.asList(Bitmap.Config.values()));
        if (Build.VERSION.SDK_INT >= 19) {
            hashSet.add(null);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            hashSet.remove(Bitmap.Config.HARDWARE);
        }
        return Collections.unmodifiableSet(hashSet);
    }

    private static k wn() {
        return Build.VERSION.SDK_INT >= 19 ? new SizeConfigStrategy() : new AttributeStrategy();
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    public synchronized void a(float f2) {
        this.maxSize = (long) Math.round(((float) this.ko) * f2);
        sn();
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    public synchronized void a(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap must not be null");
        } else if (!bitmap.isRecycled()) {
            if (bitmap.isMutable() && ((long) this.strategy.c(bitmap)) <= this.maxSize) {
                if (this.jo.contains(bitmap.getConfig())) {
                    int c2 = this.strategy.c(bitmap);
                    this.strategy.a(bitmap);
                    this.lo.b(bitmap);
                    this.po++;
                    this.currentSize += (long) c2;
                    if (Log.isLoggable(TAG, 2)) {
                        Log.v(TAG, "Put bitmap in pool=" + this.strategy.e(bitmap));
                    }
                    dump();
                    sn();
                    return;
                }
            }
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Reject bitmap from pool, bitmap: " + this.strategy.e(bitmap) + ", is mutable: " + bitmap.isMutable() + ", is allowed config: " + this.jo.contains(bitmap.getConfig()));
            }
            bitmap.recycle();
        } else {
            throw new IllegalStateException("Cannot pool recycled bitmap");
        }
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    public void aa() {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "clearMemory");
        }
        f(0);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    @NonNull
    public Bitmap c(int i, int i2, Bitmap.Config config) {
        Bitmap h = h(i, i2, config);
        return h == null ? createBitmap(i, i2, config) : h;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    @NonNull
    public Bitmap d(int i, int i2, Bitmap.Config config) {
        Bitmap h = h(i, i2, config);
        if (h == null) {
            return createBitmap(i, i2, config);
        }
        h.eraseColor(0);
        return h;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    public long getMaxSize() {
        return this.maxSize;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.d
    @SuppressLint({"InlinedApi"})
    public void trimMemory(int i) {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "trimMemory, level=" + i);
        }
        if (i >= 40) {
            aa();
        } else if (i >= 20 || i == 15) {
            f(getMaxSize() / 2);
        }
    }
}
