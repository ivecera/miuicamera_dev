package com.bumptech.glide.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.request.c;
import com.bumptech.glide.util.l;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: RequestTracker */
public class p {
    private static final String TAG = "RequestTracker";
    private final Set<c> requests = Collections.newSetFromMap(new WeakHashMap());
    private final List<c> ys = new ArrayList();
    private boolean zs;

    private boolean a(@Nullable c cVar, boolean z) {
        boolean z2 = true;
        if (cVar == null) {
            return true;
        }
        boolean remove = this.requests.remove(cVar);
        if (!this.ys.remove(cVar) && !remove) {
            z2 = false;
        }
        if (z2) {
            cVar.clear();
            if (z) {
                cVar.recycle();
            }
        }
        return z2;
    }

    public void Lj() {
        for (c cVar : l.b(this.requests)) {
            a(cVar, false);
        }
        this.ys.clear();
    }

    public void Mj() {
        for (c cVar : l.b(this.requests)) {
            if (!cVar.isComplete() && !cVar.isCancelled()) {
                cVar.pause();
                if (!this.zs) {
                    cVar.begin();
                } else {
                    this.ys.add(cVar);
                }
            }
        }
    }

    public void Si() {
        this.zs = true;
        for (c cVar : l.b(this.requests)) {
            if (cVar.isRunning() || cVar.isComplete()) {
                cVar.pause();
                this.ys.add(cVar);
            }
        }
    }

    public void Ti() {
        this.zs = true;
        for (c cVar : l.b(this.requests)) {
            if (cVar.isRunning()) {
                cVar.pause();
                this.ys.add(cVar);
            }
        }
    }

    public void Vi() {
        this.zs = false;
        for (c cVar : l.b(this.requests)) {
            if (!cVar.isComplete() && !cVar.isCancelled() && !cVar.isRunning()) {
                cVar.begin();
            }
        }
        this.ys.clear();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void addRequest(c cVar) {
        this.requests.add(cVar);
    }

    public boolean h(@Nullable c cVar) {
        return a(cVar, true);
    }

    public void i(@NonNull c cVar) {
        this.requests.add(cVar);
        if (!this.zs) {
            cVar.begin();
            return;
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "Paused, delaying request");
        }
        this.ys.add(cVar);
    }

    public boolean isPaused() {
        return this.zs;
    }

    public String toString() {
        return super.toString() + "{numRequests=" + this.requests.size() + ", isPaused=" + this.zs + "}";
    }
}
