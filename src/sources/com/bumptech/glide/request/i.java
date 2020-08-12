package com.bumptech.glide.request;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

/* compiled from: ThumbnailRequestCoordinator */
public class i implements d, c {
    private c Ft;
    private boolean isRunning;
    @Nullable
    private final d parent;
    private c thumb;

    @VisibleForTesting
    i() {
        this(null);
    }

    public i(@Nullable d dVar) {
        this.parent = dVar;
    }

    private boolean On() {
        d dVar = this.parent;
        return dVar == null || dVar.g(this);
    }

    private boolean Pn() {
        d dVar = this.parent;
        return dVar == null || dVar.c(this);
    }

    private boolean Qn() {
        d dVar = this.parent;
        return dVar == null || dVar.d(this);
    }

    private boolean Rn() {
        d dVar = this.parent;
        return dVar != null && dVar.E();
    }

    @Override // com.bumptech.glide.request.d
    public boolean E() {
        return Rn() || F();
    }

    @Override // com.bumptech.glide.request.c
    public boolean F() {
        return this.Ft.F() || this.thumb.F();
    }

    public void a(c cVar, c cVar2) {
        this.Ft = cVar;
        this.thumb = cVar2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0029 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    @Override // com.bumptech.glide.request.c
    public boolean a(c cVar) {
        if (!(cVar instanceof i)) {
            return false;
        }
        i iVar = (i) cVar;
        c cVar2 = this.Ft;
        if (cVar2 == null) {
            if (iVar.Ft != null) {
                return false;
            }
        } else if (!cVar2.a(iVar.Ft)) {
            return false;
        }
        c cVar3 = this.thumb;
        if (cVar3 == null) {
            return iVar.thumb == null;
        }
        if (!cVar3.a(iVar.thumb)) {
            return false;
        }
    }

    @Override // com.bumptech.glide.request.d
    public void b(c cVar) {
        if (!cVar.equals(this.thumb)) {
            d dVar = this.parent;
            if (dVar != null) {
                dVar.b(this);
            }
            if (!this.thumb.isComplete()) {
                this.thumb.clear();
            }
        }
    }

    @Override // com.bumptech.glide.request.c
    public void begin() {
        this.isRunning = true;
        if (!this.Ft.isComplete() && !this.thumb.isRunning()) {
            this.thumb.begin();
        }
        if (this.isRunning && !this.Ft.isRunning()) {
            this.Ft.begin();
        }
    }

    @Override // com.bumptech.glide.request.d
    public boolean c(c cVar) {
        return Pn() && cVar.equals(this.Ft) && !E();
    }

    @Override // com.bumptech.glide.request.c
    public void clear() {
        this.isRunning = false;
        this.thumb.clear();
        this.Ft.clear();
    }

    @Override // com.bumptech.glide.request.d
    public boolean d(c cVar) {
        return Qn() && (cVar.equals(this.Ft) || !this.Ft.F());
    }

    @Override // com.bumptech.glide.request.d
    public void e(c cVar) {
        d dVar;
        if (cVar.equals(this.Ft) && (dVar = this.parent) != null) {
            dVar.e(this);
        }
    }

    @Override // com.bumptech.glide.request.d
    public boolean g(c cVar) {
        return On() && cVar.equals(this.Ft);
    }

    @Override // com.bumptech.glide.request.c
    public boolean isCancelled() {
        return this.Ft.isCancelled();
    }

    @Override // com.bumptech.glide.request.c
    public boolean isComplete() {
        return this.Ft.isComplete() || this.thumb.isComplete();
    }

    @Override // com.bumptech.glide.request.c
    public boolean isFailed() {
        return this.Ft.isFailed();
    }

    @Override // com.bumptech.glide.request.c
    public boolean isPaused() {
        return this.Ft.isPaused();
    }

    @Override // com.bumptech.glide.request.c
    public boolean isRunning() {
        return this.Ft.isRunning();
    }

    @Override // com.bumptech.glide.request.c
    public void pause() {
        this.isRunning = false;
        this.Ft.pause();
        this.thumb.pause();
    }

    @Override // com.bumptech.glide.request.c
    public void recycle() {
        this.Ft.recycle();
        this.thumb.recycle();
    }
}
