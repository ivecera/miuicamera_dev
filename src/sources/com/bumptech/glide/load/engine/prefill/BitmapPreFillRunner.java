package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.a.o;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.resource.bitmap.f;
import com.bumptech.glide.util.l;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

final class BitmapPreFillRunner implements Runnable {
    static final int Ap = 4;
    static final long Bp = TimeUnit.SECONDS.toMillis(1);
    static final long INITIAL_BACKOFF_MS = 40;
    static final long MAX_DURATION_MS = 32;
    @VisibleForTesting
    static final String TAG = "PreFillRunner";
    private static final Clock zp = new Clock();
    private boolean Il;
    private final d Xi;
    private final o Yi;
    private final Handler handler;
    private final b vp;
    private final Clock wp;
    private final Set<c> xp;
    private long yp;

    @VisibleForTesting
    static class Clock {
        Clock() {
        }

        /* access modifiers changed from: package-private */
        public long now() {
            return SystemClock.currentThreadTimeMillis();
        }
    }

    private static final class a implements c {
        a() {
        }

        @Override // com.bumptech.glide.load.c
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            throw new UnsupportedOperationException();
        }
    }

    public BitmapPreFillRunner(d dVar, o oVar, b bVar) {
        this(dVar, oVar, bVar, zp, new Handler(Looper.getMainLooper()));
    }

    @VisibleForTesting
    BitmapPreFillRunner(d dVar, o oVar, b bVar, Clock clock, Handler handler2) {
        this.xp = new HashSet();
        this.yp = INITIAL_BACKOFF_MS;
        this.Xi = dVar;
        this.Yi = oVar;
        this.vp = bVar;
        this.wp = clock;
        this.handler = handler2;
    }

    private long An() {
        return this.Yi.getMaxSize() - this.Yi.da();
    }

    private long ca() {
        long j = this.yp;
        this.yp = Math.min(4 * j, Bp);
        return j;
    }

    private boolean g(long j) {
        return this.wp.now() - j >= 32;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean allocate() {
        Bitmap bitmap;
        long now = this.wp.now();
        while (!this.vp.isEmpty() && !g(now)) {
            c remove = this.vp.remove();
            if (!this.xp.contains(remove)) {
                this.xp.add(remove);
                bitmap = this.Xi.c(remove.getWidth(), remove.getHeight(), remove.getConfig());
            } else {
                bitmap = Bitmap.createBitmap(remove.getWidth(), remove.getHeight(), remove.getConfig());
            }
            int j = l.j(bitmap);
            if (An() >= ((long) j)) {
                this.Yi.a(new a(), f.a(bitmap, this.Xi));
            } else {
                this.Xi.a(bitmap);
            }
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "allocated [" + remove.getWidth() + "x" + remove.getHeight() + "] " + remove.getConfig() + " size: " + j);
            }
        }
        return !this.Il && !this.vp.isEmpty();
    }

    public void cancel() {
        this.Il = true;
    }

    public void run() {
        if (allocate()) {
            this.handler.postDelayed(this, ca());
        }
    }
}
