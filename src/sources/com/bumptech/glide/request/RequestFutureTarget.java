package com.bumptech.glide.request;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.a.f;
import com.bumptech.glide.request.target.n;
import com.bumptech.glide.request.target.o;
import com.bumptech.glide.util.l;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RequestFutureTarget<R> implements b<R>, e<R>, Runnable {
    private static final Waiter Ls = new Waiter();
    private final boolean Hs;
    private boolean Il;
    private final Waiter Is;
    private boolean Js;
    private boolean Ks;
    private final Handler La;
    @Nullable
    private GlideException exception;
    private final int height;
    @Nullable
    private c request;
    @Nullable
    private R resource;
    private final int width;

    @VisibleForTesting
    static class Waiter {
        Waiter() {
        }

        /* access modifiers changed from: package-private */
        public void a(Object obj, long j) throws InterruptedException {
            obj.wait(j);
        }

        /* access modifiers changed from: package-private */
        public void n(Object obj) {
            obj.notifyAll();
        }
    }

    public RequestFutureTarget(Handler handler, int i, int i2) {
        this(handler, i, i2, true, Ls);
    }

    RequestFutureTarget(Handler handler, int i, int i2, boolean z, Waiter waiter) {
        this.La = handler;
        this.width = i;
        this.height = i2;
        this.Hs = z;
        this.Is = waiter;
    }

    private void Sn() {
        this.La.post(this);
    }

    private synchronized R doGet(Long l) throws ExecutionException, InterruptedException, TimeoutException {
        if (this.Hs && !isDone()) {
            l.Kk();
        }
        if (this.Il) {
            throw new CancellationException();
        } else if (this.Ks) {
            throw new ExecutionException(this.exception);
        } else if (this.Js) {
            return this.resource;
        } else {
            if (l == null) {
                this.Is.a(this, 0);
            } else if (l.longValue() > 0) {
                long currentTimeMillis = System.currentTimeMillis();
                long longValue = l.longValue() + currentTimeMillis;
                while (!isDone() && currentTimeMillis < longValue) {
                    this.Is.a(this, longValue - currentTimeMillis);
                    currentTimeMillis = System.currentTimeMillis();
                }
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            } else if (this.Ks) {
                throw new ExecutionException(this.exception);
            } else if (this.Il) {
                throw new CancellationException();
            } else if (this.Js) {
                return this.resource;
            } else {
                throw new TimeoutException();
            }
        }
    }

    @Override // com.bumptech.glide.request.target.o
    public void a(@NonNull n nVar) {
    }

    @Override // com.bumptech.glide.request.target.o
    public synchronized void a(@NonNull R r, @Nullable f<? super R> fVar) {
    }

    @Override // com.bumptech.glide.request.e
    public synchronized boolean a(@Nullable GlideException glideException, Object obj, o<R> oVar, boolean z) {
        this.Ks = true;
        this.exception = glideException;
        this.Is.n(this);
        return false;
    }

    @Override // com.bumptech.glide.request.e
    public synchronized boolean a(R r, Object obj, o<R> oVar, DataSource dataSource, boolean z) {
        this.Js = true;
        this.resource = r;
        this.Is.n(this);
        return false;
    }

    @Override // com.bumptech.glide.request.target.o
    public void b(@Nullable Drawable drawable) {
    }

    @Override // com.bumptech.glide.request.target.o
    public void b(@NonNull n nVar) {
        nVar.a(this.width, this.height);
    }

    @Override // com.bumptech.glide.request.target.o
    public void c(@Nullable Drawable drawable) {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        return true;
     */
    public synchronized boolean cancel(boolean z) {
        if (isDone()) {
            return false;
        }
        this.Il = true;
        this.Is.n(this);
        if (z) {
            Sn();
        }
    }

    @Override // com.bumptech.glide.request.target.o
    public synchronized void d(@Nullable Drawable drawable) {
    }

    @Override // com.bumptech.glide.request.target.o
    public void f(@Nullable c cVar) {
        this.request = cVar;
    }

    @Override // java.util.concurrent.Future
    public R get() throws InterruptedException, ExecutionException {
        try {
            return doGet(null);
        } catch (TimeoutException e2) {
            throw new AssertionError(e2);
        }
    }

    @Override // java.util.concurrent.Future
    public R get(long j, @NonNull TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return doGet(Long.valueOf(timeUnit.toMillis(j)));
    }

    @Override // com.bumptech.glide.request.target.o
    @Nullable
    public c getRequest() {
        return this.request;
    }

    public synchronized boolean isCancelled() {
        return this.Il;
    }

    public synchronized boolean isDone() {
        return this.Il || this.Js || this.Ks;
    }

    @Override // com.bumptech.glide.manager.j
    public void onDestroy() {
    }

    @Override // com.bumptech.glide.manager.j
    public void onStart() {
    }

    @Override // com.bumptech.glide.manager.j
    public void onStop() {
    }

    public void run() {
        c cVar = this.request;
        if (cVar != null) {
            cVar.clear();
            this.request = null;
        }
    }
}
