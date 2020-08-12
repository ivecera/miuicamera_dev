package com.bumptech.glide.request;

import android.support.annotation.Nullable;

/* compiled from: ErrorRequestCoordinator */
public final class a implements d, c {
    private c error;
    @Nullable
    private final d parent;
    private c primary;

    public a(@Nullable d dVar) {
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

    private boolean j(c cVar) {
        return cVar.equals(this.primary) || (this.primary.isFailed() && cVar.equals(this.error));
    }

    @Override // com.bumptech.glide.request.d
    public boolean E() {
        return Rn() || F();
    }

    @Override // com.bumptech.glide.request.c
    public boolean F() {
        return (this.primary.isFailed() ? this.error : this.primary).F();
    }

    public void a(c cVar, c cVar2) {
        this.primary = cVar;
        this.error = cVar2;
    }

    @Override // com.bumptech.glide.request.c
    public boolean a(c cVar) {
        if (!(cVar instanceof a)) {
            return false;
        }
        a aVar = (a) cVar;
        return this.primary.a(aVar.primary) && this.error.a(aVar.error);
    }

    @Override // com.bumptech.glide.request.d
    public void b(c cVar) {
        d dVar = this.parent;
        if (dVar != null) {
            dVar.b(this);
        }
    }

    @Override // com.bumptech.glide.request.c
    public void begin() {
        if (!this.primary.isRunning()) {
            this.primary.begin();
        }
    }

    @Override // com.bumptech.glide.request.d
    public boolean c(c cVar) {
        return Pn() && j(cVar);
    }

    @Override // com.bumptech.glide.request.c
    public void clear() {
        this.primary.clear();
        if (this.error.isRunning()) {
            this.error.clear();
        }
    }

    @Override // com.bumptech.glide.request.d
    public boolean d(c cVar) {
        return Qn() && j(cVar);
    }

    @Override // com.bumptech.glide.request.d
    public void e(c cVar) {
        if (cVar.equals(this.error)) {
            d dVar = this.parent;
            if (dVar != null) {
                dVar.e(this);
            }
        } else if (!this.error.isRunning()) {
            this.error.begin();
        }
    }

    @Override // com.bumptech.glide.request.d
    public boolean g(c cVar) {
        return On() && j(cVar);
    }

    @Override // com.bumptech.glide.request.c
    public boolean isCancelled() {
        return (this.primary.isFailed() ? this.error : this.primary).isCancelled();
    }

    @Override // com.bumptech.glide.request.c
    public boolean isComplete() {
        return (this.primary.isFailed() ? this.error : this.primary).isComplete();
    }

    @Override // com.bumptech.glide.request.c
    public boolean isFailed() {
        return this.primary.isFailed() && this.error.isFailed();
    }

    @Override // com.bumptech.glide.request.c
    public boolean isPaused() {
        return (this.primary.isFailed() ? this.error : this.primary).isPaused();
    }

    @Override // com.bumptech.glide.request.c
    public boolean isRunning() {
        return (this.primary.isFailed() ? this.error : this.primary).isRunning();
    }

    @Override // com.bumptech.glide.request.c
    public void pause() {
        if (!this.primary.isFailed()) {
            this.primary.pause();
        }
        if (this.error.isRunning()) {
            this.error.pause();
        }
    }

    @Override // com.bumptech.glide.request.c
    public void recycle() {
        this.primary.recycle();
        this.error.recycle();
    }
}
