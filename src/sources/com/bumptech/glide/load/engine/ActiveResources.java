package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.u;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

final class ActiveResources {
    private static final int hm = 1;
    private final Handler La = new Handler(Looper.getMainLooper(), new a(this));
    @VisibleForTesting
    final Map<c, ResourceWeakReference> activeEngineResources = new HashMap();
    @Nullable
    private volatile DequeuedResourceCallback cb;
    @Nullable
    private ReferenceQueue<u<?>> em;
    @Nullable
    private Thread fm;
    private volatile boolean gm;
    private u.a listener;
    private final boolean oj;

    @VisibleForTesting
    interface DequeuedResourceCallback {
        void S();
    }

    @VisibleForTesting
    static final class ResourceWeakReference extends WeakReference<u<?>> {
        final c key;
        @Nullable
        A<?> resource;
        final boolean tn;

        ResourceWeakReference(@NonNull c cVar, @NonNull u<?> uVar, @NonNull ReferenceQueue<? super u<?>> referenceQueue, boolean z) {
            super(uVar, referenceQueue);
            A<?> a2;
            i.checkNotNull(cVar);
            this.key = cVar;
            if (!uVar.xj() || !z) {
                a2 = null;
            } else {
                A<?> wj = uVar.wj();
                i.checkNotNull(wj);
                a2 = wj;
            }
            this.resource = a2;
            this.tn = uVar.xj();
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.resource = null;
            clear();
        }
    }

    ActiveResources(boolean z) {
        this.oj = z;
    }

    private ReferenceQueue<u<?>> hn() {
        if (this.em == null) {
            this.em = new ReferenceQueue<>();
            this.fm = new Thread(new b(this), "glide-active-resources");
            this.fm.start();
        }
        return this.em;
    }

    /* access modifiers changed from: package-private */
    public void a(@NonNull ResourceWeakReference resourceWeakReference) {
        A<?> a2;
        l.Lk();
        this.activeEngineResources.remove(resourceWeakReference.key);
        if (resourceWeakReference.tn && (a2 = resourceWeakReference.resource) != null) {
            u<?> uVar = new u<>(a2, true, false);
            uVar.a(resourceWeakReference.key, this.listener);
            this.listener.a(resourceWeakReference.key, uVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(u.a aVar) {
        this.listener = aVar;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public u<?> b(c cVar) {
        ResourceWeakReference resourceWeakReference = this.activeEngineResources.get(cVar);
        if (resourceWeakReference == null) {
            return null;
        }
        u<?> uVar = (u) resourceWeakReference.get();
        if (uVar == null) {
            a(resourceWeakReference);
        }
        return uVar;
    }

    /* access modifiers changed from: package-private */
    public void b(c cVar, u<?> uVar) {
        ResourceWeakReference put = this.activeEngineResources.put(cVar, new ResourceWeakReference(cVar, uVar, hn(), this.oj));
        if (put != null) {
            put.reset();
        }
    }

    /* access modifiers changed from: package-private */
    public void d(c cVar) {
        ResourceWeakReference remove = this.activeEngineResources.remove(cVar);
        if (remove != null) {
            remove.reset();
        }
    }

    /* access modifiers changed from: package-private */
    public void ej() {
        while (!this.gm) {
            try {
                this.La.obtainMessage(1, (ResourceWeakReference) this.em.remove()).sendToTarget();
                DequeuedResourceCallback dequeuedResourceCallback = this.cb;
                if (dequeuedResourceCallback != null) {
                    dequeuedResourceCallback.S();
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setDequeuedResourceCallback(DequeuedResourceCallback dequeuedResourceCallback) {
        this.cb = dequeuedResourceCallback;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void shutdown() {
        this.gm = true;
        Thread thread = this.fm;
        if (thread != null) {
            thread.interrupt();
            try {
                this.fm.join(TimeUnit.SECONDS.toMillis(5));
                if (this.fm.isAlive()) {
                    throw new RuntimeException("Failed to join in time");
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
