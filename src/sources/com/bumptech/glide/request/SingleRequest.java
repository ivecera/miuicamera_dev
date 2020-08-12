package com.bumptech.glide.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.e;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.b.b.a;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.a.g;
import com.bumptech.glide.request.target.n;
import com.bumptech.glide.request.target.o;
import com.bumptech.glide.util.a.d;
import com.bumptech.glide.util.l;

public final class SingleRequest<R> implements c, n, g, d.c {
    private static final String Dt = "Glide";
    private static final boolean Et = Log.isLoggable(TAG, 2);
    private static final Pools.Pool<SingleRequest<?>> Nn = d.a(150, new h());
    private static final String TAG = "Request";
    private g<? super R> At;
    private Engine.b Bt;
    private Drawable Ct;
    private final com.bumptech.glide.util.a.g Hm;
    private Class<R> Pj;
    private Drawable Ps;
    private Engine Qa;
    private f Qj;
    private int Rs;
    private e<R> Sj;
    private int Ss;
    private Drawable Us;
    private e _i;
    private Context context;
    private int height;
    @Nullable
    private Object model;
    private Priority priority;
    private A<R> resource;
    private long startTime;
    private Status status;
    @Nullable
    private final String tag;
    private o<R> target;
    private int width;
    private boolean xt;
    @Nullable
    private e<R> yt;
    private d zt;

    private enum Status {
        PENDING,
        RUNNING,
        WAITING_FOR_SIZE,
        COMPLETE,
        FAILED,
        CANCELLED,
        CLEARED,
        PAUSED
    }

    SingleRequest() {
        this.tag = Et ? String.valueOf(super.hashCode()) : null;
        this.Hm = com.bumptech.glide.util.a.g.newInstance();
    }

    private void U(String str) {
        Log.v(TAG, str + " this: " + this.tag);
    }

    private void Un() {
        if (this.xt) {
            throw new IllegalStateException("You can't start or clear loads in RequestListener or Target callbacks. If you're trying to start a fallback request when a load fails, use RequestBuilder#error(RequestBuilder). Otherwise consider posting your into() or clear() calls to the main thread using a Handler instead.");
        }
    }

    private boolean Vn() {
        d dVar = this.zt;
        return dVar == null || dVar.g(this);
    }

    private boolean Wn() {
        d dVar = this.zt;
        return dVar == null || dVar.c(this);
    }

    private boolean Xn() {
        d dVar = this.zt;
        return dVar == null || dVar.d(this);
    }

    private Drawable Yn() {
        if (this.Ct == null) {
            this.Ct = this.Qj.ck();
            if (this.Ct == null && this.Qj.bk() > 0) {
                this.Ct = da(this.Qj.bk());
            }
        }
        return this.Ct;
    }

    private boolean Zn() {
        d dVar = this.zt;
        return dVar == null || !dVar.E();
    }

    private void _n() {
        d dVar = this.zt;
        if (dVar != null) {
            dVar.e(this);
        }
    }

    private static int a(int i, float f2) {
        return i == Integer.MIN_VALUE ? i : Math.round(f2 * ((float) i));
    }

    public static <R> SingleRequest<R> a(Context context2, e eVar, Object obj, Class<R> cls, f fVar, int i, int i2, Priority priority2, o<R> oVar, e<R> eVar2, e<R> eVar3, d dVar, Engine engine, g<? super R> gVar) {
        SingleRequest<R> acquire = Nn.acquire();
        if (acquire == null) {
            acquire = new SingleRequest<>();
        }
        acquire.b(context2, eVar, obj, cls, fVar, i, i2, priority2, oVar, eVar2, eVar3, dVar, engine, gVar);
        return acquire;
    }

    /* JADX INFO: finally extract failed */
    private void a(A<R> a2, R r, DataSource dataSource) {
        boolean Zn = Zn();
        this.status = Status.COMPLETE;
        this.resource = a2;
        if (this._i.getLogLevel() <= 3) {
            Log.d(Dt, "Finished loading " + r.getClass().getSimpleName() + " from " + dataSource + " for " + this.model + " with size [" + this.width + "x" + this.height + "] in " + com.bumptech.glide.util.e.e(this.startTime) + " ms");
        }
        this.xt = true;
        try {
            if ((this.Sj == null || !this.Sj.a(r, this.model, this.target, dataSource, Zn)) && (this.yt == null || !this.yt.a(r, this.model, this.target, dataSource, Zn))) {
                this.target.a(r, this.At.a(dataSource, Zn));
            }
            this.xt = false;
            ao();
        } catch (Throwable th) {
            this.xt = false;
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    private void a(GlideException glideException, int i) {
        this.Hm.Pk();
        int logLevel = this._i.getLogLevel();
        if (logLevel <= i) {
            Log.w(Dt, "Load failed for " + this.model + " with size [" + this.width + "x" + this.height + "]", glideException);
            if (logLevel <= 4) {
                glideException.F(Dt);
            }
        }
        this.Bt = null;
        this.status = Status.FAILED;
        this.xt = true;
        try {
            if ((this.Sj == null || !this.Sj.a(glideException, this.model, this.target, Zn())) && (this.yt == null || !this.yt.a(glideException, this.model, this.target, Zn()))) {
                bo();
            }
            this.xt = false;
            _n();
        } catch (Throwable th) {
            this.xt = false;
            throw th;
        }
    }

    private void ao() {
        d dVar = this.zt;
        if (dVar != null) {
            dVar.b(this);
        }
    }

    private void b(Context context2, e eVar, Object obj, Class<R> cls, f fVar, int i, int i2, Priority priority2, o<R> oVar, e<R> eVar2, e<R> eVar3, d dVar, Engine engine, g<? super R> gVar) {
        this.context = context2;
        this._i = eVar;
        this.model = obj;
        this.Pj = cls;
        this.Qj = fVar;
        this.Ss = i;
        this.Rs = i2;
        this.priority = priority2;
        this.target = oVar;
        this.yt = eVar2;
        this.Sj = eVar3;
        this.zt = dVar;
        this.Qa = engine;
        this.At = gVar;
        this.status = Status.PENDING;
    }

    private void bo() {
        if (Wn()) {
            Drawable drawable = null;
            if (this.model == null) {
                drawable = dk();
            }
            if (drawable == null) {
                drawable = Yn();
            }
            if (drawable == null) {
                drawable = ik();
            }
            this.target.d(drawable);
        }
    }

    private Drawable da(@DrawableRes int i) {
        return a.a(this._i, i, this.Qj.getTheme() != null ? this.Qj.getTheme() : this.context.getTheme());
    }

    private Drawable dk() {
        if (this.Us == null) {
            this.Us = this.Qj.dk();
            if (this.Us == null && this.Qj.ek() > 0) {
                this.Us = da(this.Qj.ek());
            }
        }
        return this.Us;
    }

    private Drawable ik() {
        if (this.Ps == null) {
            this.Ps = this.Qj.ik();
            if (this.Ps == null && this.Qj.jk() > 0) {
                this.Ps = da(this.Qj.jk());
            }
        }
        return this.Ps;
    }

    private void m(A<?> a2) {
        this.Qa.e(a2);
        this.resource = null;
    }

    @Override // com.bumptech.glide.request.c
    public boolean F() {
        return isComplete();
    }

    @Override // com.bumptech.glide.request.target.n
    public void a(int i, int i2) {
        this.Hm.Pk();
        if (Et) {
            U("Got onSizeReady in " + com.bumptech.glide.util.e.e(this.startTime));
        }
        if (this.status == Status.WAITING_FOR_SIZE) {
            this.status = Status.RUNNING;
            float kk = this.Qj.kk();
            this.width = a(i, kk);
            this.height = a(i2, kk);
            if (Et) {
                U("finished setup for calling load in " + com.bumptech.glide.util.e.e(this.startTime));
            }
            this.Bt = this.Qa.a(this._i, this.model, this.Qj.getSignature(), this.width, this.height, this.Qj.T(), this.Pj, this.priority, this.Qj.hj(), this.Qj.getTransformations(), this.Qj.uk(), this.Qj.mj(), this.Qj.getOptions(), this.Qj.qk(), this.Qj.mk(), this.Qj.lk(), this.Qj.fk(), this);
            if (this.status != Status.RUNNING) {
                this.Bt = null;
            }
            if (Et) {
                U("finished onSizeReady in " + com.bumptech.glide.util.e.e(this.startTime));
            }
        }
    }

    /* JADX DEBUG: Additional 2 move instruction added to help type inference */
    @Override // com.bumptech.glide.request.g
    public void a(A<?> a2, DataSource dataSource) {
        this.Hm.Pk();
        this.Bt = null;
        if (a2 == null) {
            a(new GlideException("Expected to receive a Resource<R> with an object of " + this.Pj + " inside, but instead got null."));
            return;
        }
        R r = a2.get();
        if (r == null || !this.Pj.isAssignableFrom(r.getClass())) {
            m(a2);
            StringBuilder sb = new StringBuilder();
            sb.append("Expected to receive an object of ");
            sb.append(this.Pj);
            sb.append(" but instead got ");
            String str = "";
            sb.append(r != null ? r.getClass() : str);
            sb.append("{");
            sb.append((Object) r);
            sb.append("} inside Resource{");
            sb.append(a2);
            sb.append("}.");
            String str2 = str;
            if (r == null) {
                str2 = " To indicate failure return a null Resource object, rather than a Resource object containing null data.";
            }
            sb.append(str2);
            a(new GlideException(sb.toString()));
        } else if (!Xn()) {
            m(a2);
            this.status = Status.COMPLETE;
        } else {
            a(a2, r, dataSource);
        }
    }

    @Override // com.bumptech.glide.request.g
    public void a(GlideException glideException) {
        a(glideException, 5);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0044 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    @Override // com.bumptech.glide.request.c
    public boolean a(c cVar) {
        if (!(cVar instanceof SingleRequest)) {
            return false;
        }
        SingleRequest singleRequest = (SingleRequest) cVar;
        if (this.Ss != singleRequest.Ss || this.Rs != singleRequest.Rs || !l.c(this.model, singleRequest.model) || !this.Pj.equals(singleRequest.Pj) || !this.Qj.equals(singleRequest.Qj) || this.priority != singleRequest.priority) {
            return false;
        }
        if (this.Sj != null) {
            return singleRequest.Sj != null;
        }
        if (singleRequest.Sj != null) {
            return false;
        }
    }

    @Override // com.bumptech.glide.request.c
    public void begin() {
        Un();
        this.Hm.Pk();
        this.startTime = com.bumptech.glide.util.e.Jk();
        if (this.model == null) {
            if (l.o(this.Ss, this.Rs)) {
                this.width = this.Ss;
                this.height = this.Rs;
            }
            a(new GlideException("Received null model"), dk() == null ? 5 : 3);
            return;
        }
        Status status2 = this.status;
        if (status2 == Status.RUNNING) {
            throw new IllegalArgumentException("Cannot restart a running request");
        } else if (status2 == Status.COMPLETE) {
            a((A<?>) this.resource, DataSource.MEMORY_CACHE);
        } else {
            this.status = Status.WAITING_FOR_SIZE;
            if (l.o(this.Ss, this.Rs)) {
                a(this.Ss, this.Rs);
            } else {
                this.target.b(this);
            }
            Status status3 = this.status;
            if ((status3 == Status.RUNNING || status3 == Status.WAITING_FOR_SIZE) && Wn()) {
                this.target.c(ik());
            }
            if (Et) {
                U("finished run method in " + com.bumptech.glide.util.e.e(this.startTime));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
        Un();
        this.Hm.Pk();
        this.target.a(this);
        this.status = Status.CANCELLED;
        Engine.b bVar = this.Bt;
        if (bVar != null) {
            bVar.cancel();
            this.Bt = null;
        }
    }

    @Override // com.bumptech.glide.request.c
    public void clear() {
        l.Lk();
        Un();
        this.Hm.Pk();
        if (this.status != Status.CLEARED) {
            cancel();
            A<R> a2 = this.resource;
            if (a2 != null) {
                m(a2);
            }
            if (Vn()) {
                this.target.b(ik());
            }
            this.status = Status.CLEARED;
        }
    }

    @Override // com.bumptech.glide.util.a.d.c
    @NonNull
    public com.bumptech.glide.util.a.g getVerifier() {
        return this.Hm;
    }

    @Override // com.bumptech.glide.request.c
    public boolean isCancelled() {
        Status status2 = this.status;
        return status2 == Status.CANCELLED || status2 == Status.CLEARED;
    }

    @Override // com.bumptech.glide.request.c
    public boolean isComplete() {
        return this.status == Status.COMPLETE;
    }

    @Override // com.bumptech.glide.request.c
    public boolean isFailed() {
        return this.status == Status.FAILED;
    }

    @Override // com.bumptech.glide.request.c
    public boolean isPaused() {
        return this.status == Status.PAUSED;
    }

    @Override // com.bumptech.glide.request.c
    public boolean isRunning() {
        Status status2 = this.status;
        return status2 == Status.RUNNING || status2 == Status.WAITING_FOR_SIZE;
    }

    @Override // com.bumptech.glide.request.c
    public void pause() {
        clear();
        this.status = Status.PAUSED;
    }

    @Override // com.bumptech.glide.request.c
    public void recycle() {
        Un();
        this.context = null;
        this._i = null;
        this.model = null;
        this.Pj = null;
        this.Qj = null;
        this.Ss = -1;
        this.Rs = -1;
        this.target = null;
        this.Sj = null;
        this.yt = null;
        this.zt = null;
        this.At = null;
        this.Bt = null;
        this.Ct = null;
        this.Ps = null;
        this.Us = null;
        this.width = -1;
        this.height = -1;
        Nn.release(this);
    }
}
