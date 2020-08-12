package com.bumptech.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.widget.ImageView;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.SingleRequest;
import com.bumptech.glide.request.a;
import com.bumptech.glide.request.b;
import com.bumptech.glide.request.c;
import com.bumptech.glide.request.d;
import com.bumptech.glide.request.e;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.i;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.util.l;
import java.io.File;
import java.net.URL;

/* compiled from: RequestBuilder */
public class j<TranscodeType> implements Cloneable, g<j<TranscodeType>> {
    protected static final f Zj = new f().a(o.DATA).a(Priority.LOW).B(true);
    private final f Oa;
    private final Class<TranscodeType> Pj;
    @NonNull
    protected f Qj;
    @NonNull
    private n<?, ? super TranscodeType> Rj;
    @Nullable
    private e<TranscodeType> Sj;
    @Nullable
    private j<TranscodeType> Tj;
    @Nullable
    private j<TranscodeType> Uj;
    @Nullable
    private Float Vj;
    private boolean Wj;
    private boolean Xj;
    private boolean Yj;
    private final e _i;
    private final Context context;
    private final c gj;
    @Nullable
    private Object model;
    private final m za;

    protected j(c cVar, m mVar, Class<TranscodeType> cls, Context context2) {
        this.Wj = true;
        this.gj = cVar;
        this.za = mVar;
        this.Pj = cls;
        this.Oa = mVar.ta();
        this.context = context2;
        this.Rj = mVar.a(cls);
        this.Qj = this.Oa;
        this._i = cVar.Hi();
    }

    protected j(Class<TranscodeType> cls, j<?> jVar) {
        this(jVar.gj, jVar.za, cls, jVar.context);
        this.model = jVar.model;
        this.Xj = jVar.Xj;
        this.Qj = jVar.Qj;
    }

    private c a(com.bumptech.glide.request.target.o<TranscodeType> oVar, @Nullable e<TranscodeType> eVar, @Nullable d dVar, n<?, ? super TranscodeType> nVar, Priority priority, int i, int i2, f fVar) {
        a aVar;
        a aVar2;
        if (this.Uj != null) {
            aVar2 = new a(dVar);
            aVar = aVar2;
        } else {
            aVar = null;
            aVar2 = dVar;
        }
        c b2 = b(oVar, eVar, aVar2, nVar, priority, i, i2, fVar);
        if (aVar == null) {
            return b2;
        }
        int hk = this.Uj.Qj.hk();
        int gk = this.Uj.Qj.gk();
        if (l.o(i, i2) && !this.Uj.Qj.wk()) {
            hk = fVar.hk();
            gk = fVar.gk();
        }
        j<TranscodeType> jVar = this.Uj;
        aVar.a(b2, jVar.a(oVar, eVar, aVar, jVar.Rj, jVar.Qj.getPriority(), hk, gk, this.Uj.Qj));
        return aVar;
    }

    private c a(com.bumptech.glide.request.target.o<TranscodeType> oVar, @Nullable e<TranscodeType> eVar, f fVar) {
        return a(oVar, eVar, (d) null, this.Rj, fVar.getPriority(), fVar.hk(), fVar.gk(), fVar);
    }

    private c a(com.bumptech.glide.request.target.o<TranscodeType> oVar, e<TranscodeType> eVar, f fVar, d dVar, n<?, ? super TranscodeType> nVar, Priority priority, int i, int i2) {
        Context context2 = this.context;
        e eVar2 = this._i;
        return SingleRequest.a(context2, eVar2, this.model, this.Pj, fVar, i, i2, priority, oVar, eVar, this.Sj, dVar, eVar2.ua(), nVar.Yi());
    }

    private boolean a(f fVar, c cVar) {
        return !fVar.qk() && cVar.isComplete();
    }

    private c b(com.bumptech.glide.request.target.o<TranscodeType> oVar, e<TranscodeType> eVar, @Nullable d dVar, n<?, ? super TranscodeType> nVar, Priority priority, int i, int i2, f fVar) {
        j<TranscodeType> jVar = this.Tj;
        if (jVar != null) {
            if (!this.Yj) {
                n<?, ? super TranscodeType> nVar2 = jVar.Wj ? nVar : jVar.Rj;
                Priority priority2 = this.Tj.Qj.rk() ? this.Tj.Qj.getPriority() : c(priority);
                int hk = this.Tj.Qj.hk();
                int gk = this.Tj.Qj.gk();
                if (l.o(i, i2) && !this.Tj.Qj.wk()) {
                    hk = fVar.hk();
                    gk = fVar.gk();
                }
                i iVar = new i(dVar);
                c a2 = a(oVar, eVar, fVar, iVar, nVar, priority, i, i2);
                this.Yj = true;
                j<TranscodeType> jVar2 = this.Tj;
                c a3 = jVar2.a(oVar, eVar, iVar, nVar2, priority2, hk, gk, jVar2.Qj);
                this.Yj = false;
                iVar.a(a2, a3);
                return iVar;
            }
            throw new IllegalStateException("You cannot use a request as both the main request and a thumbnail, consider using clone() on the request(s) passed to thumbnail()");
        } else if (this.Vj == null) {
            return a(oVar, eVar, fVar, dVar, nVar, priority, i, i2);
        } else {
            i iVar2 = new i(dVar);
            iVar2.a(a(oVar, eVar, fVar, iVar2, nVar, priority, i, i2), a(oVar, eVar, fVar.clone().g(this.Vj.floatValue()), iVar2, nVar, c(priority), i, i2));
            return iVar2;
        }
    }

    private <Y extends com.bumptech.glide.request.target.o<TranscodeType>> Y b(@NonNull Y y, @Nullable e<TranscodeType> eVar, @NonNull f fVar) {
        l.Lk();
        com.bumptech.glide.util.i.checkNotNull(y);
        if (this.Xj) {
            f Rj2 = fVar.Rj();
            c a2 = a(y, eVar, Rj2);
            c request = y.getRequest();
            if (!a2.a(request) || a(Rj2, request)) {
                this.za.d((com.bumptech.glide.request.target.o<?>) y);
                y.f(a2);
                this.za.a(y, a2);
                return y;
            }
            a2.recycle();
            com.bumptech.glide.util.i.checkNotNull(request);
            if (!request.isRunning()) {
                request.begin();
            }
            return y;
        }
        throw new IllegalArgumentException("You must call #load() before calling #into()");
    }

    @NonNull
    private Priority c(@NonNull Priority priority) {
        int i = i.Oj[priority.ordinal()];
        if (i == 1) {
            return Priority.NORMAL;
        }
        if (i == 2) {
            return Priority.HIGH;
        }
        if (i == 3 || i == 4) {
            return Priority.IMMEDIATE;
        }
        throw new IllegalArgumentException("unknown priority: " + this.Qj.getPriority());
    }

    @NonNull
    private j<TranscodeType> u(@Nullable Object obj) {
        this.model = obj;
        this.Xj = true;
        return this;
    }

    /* access modifiers changed from: protected */
    @CheckResult
    @NonNull
    public j<File> Ki() {
        return new j(File.class, this).b(Zj);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public f Li() {
        f fVar = this.Oa;
        f fVar2 = this.Qj;
        return fVar == fVar2 ? fVar2.clone() : fVar2;
    }

    @NonNull
    public b<TranscodeType> Mi() {
        return j(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<TranscodeType> a(@Nullable Drawable drawable) {
        u(drawable);
        return b(f.b(o.NONE));
    }

    @NonNull
    public j<TranscodeType> a(@Nullable j<TranscodeType> jVar) {
        this.Uj = jVar;
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> a(@NonNull n<?, ? super TranscodeType> nVar) {
        com.bumptech.glide.util.i.checkNotNull(nVar);
        this.Rj = nVar;
        this.Wj = false;
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> a(@Nullable e<TranscodeType> eVar) {
        this.Sj = eVar;
        return this;
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<TranscodeType> a(@Nullable File file) {
        u(file);
        return this;
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<TranscodeType> a(@RawRes @DrawableRes @Nullable Integer num) {
        u(num);
        return b(f.h(com.bumptech.glide.e.a.obtain(this.context)));
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @Deprecated
    public j<TranscodeType> a(@Nullable URL url) {
        u(url);
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> a(@Nullable j<TranscodeType>... jVarArr) {
        j<TranscodeType> jVar = null;
        if (jVarArr == null || jVarArr.length == 0) {
            return b((j) null);
        }
        for (int length = jVarArr.length - 1; length >= 0; length--) {
            j<TranscodeType> jVar2 = jVarArr[length];
            if (jVar2 != null) {
                jVar = jVar == null ? jVar2 : jVar2.b(jVar);
            }
        }
        return b(jVar);
    }

    @NonNull
    public ViewTarget<ImageView, TranscodeType> a(@NonNull ImageView imageView) {
        l.Lk();
        com.bumptech.glide.util.i.checkNotNull(imageView);
        f fVar = this.Qj;
        if (!fVar.vk() && fVar.tk() && imageView.getScaleType() != null) {
            switch (i.Nj[imageView.getScaleType().ordinal()]) {
                case 1:
                    fVar = fVar.clone().zk();
                    break;
                case 2:
                    fVar = fVar.clone().Ak();
                    break;
                case 3:
                case 4:
                case 5:
                    fVar = fVar.clone().Ck();
                    break;
                case 6:
                    fVar = fVar.clone().Ak();
                    break;
            }
        }
        ViewTarget<ImageView, X> a2 = this._i.a(imageView, this.Pj);
        b(a2, null, fVar);
        return a2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public <Y extends com.bumptech.glide.request.target.o<TranscodeType>> Y a(@NonNull Y y, @Nullable e<TranscodeType> eVar) {
        b(y, eVar, Li());
        return y;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> b(float f2) {
        if (f2 < 0.0f || f2 > 1.0f) {
            throw new IllegalArgumentException("sizeMultiplier must be between 0 and 1");
        }
        this.Vj = Float.valueOf(f2);
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> b(@Nullable j<TranscodeType> jVar) {
        this.Tj = jVar;
        return this;
    }

    @CheckResult
    @NonNull
    public j<TranscodeType> b(@NonNull f fVar) {
        com.bumptech.glide.util.i.checkNotNull(fVar);
        this.Qj = Li().b(fVar);
        return this;
    }

    @CheckResult
    @Deprecated
    public <Y extends com.bumptech.glide.request.target.o<File>> Y b(@NonNull Y y) {
        return Ki().c((com.bumptech.glide.request.target.o) y);
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<TranscodeType> c(@Nullable Uri uri) {
        u(uri);
        return this;
    }

    @NonNull
    public <Y extends com.bumptech.glide.request.target.o<TranscodeType>> Y c(@NonNull Y y) {
        return a(y, (e) null);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.bumptech.glide.n, com.bumptech.glide.n<?, ? super TranscodeType>] */
    @Override // java.lang.Object
    @CheckResult
    public j<TranscodeType> clone() {
        try {
            j<TranscodeType> jVar = (j) super.clone();
            jVar.Qj = jVar.Qj.clone();
            jVar.Rj = jVar.Rj.clone();
            return jVar;
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<TranscodeType> f(@Nullable byte[] bArr) {
        u(bArr);
        if (!this.Qj.pk()) {
            this = b(f.b(o.NONE));
        }
        return !this.Qj.sk() ? this.b(f.C(true)) : this;
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<TranscodeType> g(@Nullable Bitmap bitmap) {
        u(bitmap);
        return b(f.b(o.NONE));
    }

    @CheckResult
    @Deprecated
    public b<File> g(int i, int i2) {
        return Ki().j(i, i2);
    }

    @Deprecated
    public b<TranscodeType> h(int i, int i2) {
        return j(i, i2);
    }

    @NonNull
    public com.bumptech.glide.request.target.o<TranscodeType> i(int i, int i2) {
        return c(com.bumptech.glide.request.target.l.a(this.za, i, i2));
    }

    @NonNull
    public b<TranscodeType> j(int i, int i2) {
        RequestFutureTarget requestFutureTarget = new RequestFutureTarget(this._i.va(), i, i2);
        if (l.Mk()) {
            this._i.va().post(new h(this, requestFutureTarget));
        } else {
            a(requestFutureTarget, requestFutureTarget);
        }
        return requestFutureTarget;
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<TranscodeType> load(@Nullable Object obj) {
        u(obj);
        return this;
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<TranscodeType> load(@Nullable String str) {
        u(str);
        return this;
    }

    @NonNull
    public com.bumptech.glide.request.target.o<TranscodeType> preload() {
        return i(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }
}
