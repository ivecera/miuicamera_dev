package com.bumptech.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.view.View;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.manager.c;
import com.bumptech.glide.manager.d;
import com.bumptech.glide.manager.i;
import com.bumptech.glide.manager.j;
import com.bumptech.glide.manager.p;
import com.bumptech.glide.manager.q;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.util.l;
import java.io.File;
import java.net.URL;

/* compiled from: RequestManager */
public class m implements j, g<j<Drawable>> {
    private static final f Zj = f.b(o.DATA).a(Priority.LOW).B(true);
    private static final f fk = f.k(Bitmap.class).lock();
    private static final f gk = f.k(com.bumptech.glide.load.resource.gif.b.class).lock();
    private final Handler La;
    private f Qj;
    private final p _j;
    private final com.bumptech.glide.manager.o bk;
    private final q ck;
    protected final Context context;
    private final Runnable dk;
    private final c ek;
    protected final c gj;
    final i wa;

    /* compiled from: RequestManager */
    private static class a extends ViewTarget<View, Object> {
        a(@NonNull View view) {
            super(view);
        }

        @Override // com.bumptech.glide.request.target.o
        public void a(@NonNull Object obj, @Nullable com.bumptech.glide.request.a.f<? super Object> fVar) {
        }
    }

    /* compiled from: RequestManager */
    private static class b implements c.a {
        private final p _j;

        b(@NonNull p pVar) {
            this._j = pVar;
        }

        @Override // com.bumptech.glide.manager.c.a
        public void m(boolean z) {
            if (z) {
                this._j.Mj();
            }
        }
    }

    public m(@NonNull c cVar, @NonNull i iVar, @NonNull com.bumptech.glide.manager.o oVar, @NonNull Context context2) {
        this(cVar, iVar, oVar, new p(), cVar.Gi(), context2);
    }

    m(c cVar, i iVar, com.bumptech.glide.manager.o oVar, p pVar, d dVar, Context context2) {
        this.ck = new q();
        this.dk = new k(this);
        this.La = new Handler(Looper.getMainLooper());
        this.gj = cVar;
        this.wa = iVar;
        this.bk = oVar;
        this._j = pVar;
        this.context = context2;
        this.ek = dVar.a(context2.getApplicationContext(), new b(pVar));
        if (l.Mk()) {
            this.La.post(this.dk);
        } else {
            iVar.b(this);
        }
        iVar.b(this.ek);
        d(cVar.Hi().ta());
        cVar.b(this);
    }

    private void e(@NonNull f fVar) {
        this.Qj = this.Qj.b(fVar);
    }

    private void g(@NonNull com.bumptech.glide.request.target.o<?> oVar) {
        if (!e(oVar) && !this.gj.a(oVar) && oVar.getRequest() != null) {
            com.bumptech.glide.request.c request = oVar.getRequest();
            oVar.f(null);
            request.clear();
        }
    }

    @CheckResult
    @NonNull
    public j<Bitmap> Ni() {
        return b(Bitmap.class).b(fk);
    }

    @CheckResult
    @NonNull
    public j<Drawable> Oi() {
        return b(Drawable.class);
    }

    @CheckResult
    @NonNull
    public j<File> Pi() {
        return b(File.class).b(f.C(true));
    }

    @CheckResult
    @NonNull
    public j<com.bumptech.glide.load.resource.gif.b> Qi() {
        return b(com.bumptech.glide.load.resource.gif.b.class).b(gk);
    }

    @CheckResult
    @NonNull
    public j<File> Ri() {
        return b(File.class).b(Zj);
    }

    public void Si() {
        l.Lk();
        this._j.Si();
    }

    public void Ti() {
        l.Lk();
        this._j.Ti();
    }

    public void Ui() {
        l.Lk();
        Ti();
        for (m mVar : this.bk.ba()) {
            mVar.Ti();
        }
    }

    public void Vi() {
        l.Lk();
        this._j.Vi();
    }

    public void Wi() {
        l.Lk();
        Vi();
        for (m mVar : this.bk.ba()) {
            mVar.Vi();
        }
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<Drawable> a(@Nullable Drawable drawable) {
        return Oi().a(drawable);
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<Drawable> a(@Nullable File file) {
        return Oi().a(file);
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<Drawable> a(@RawRes @DrawableRes @Nullable Integer num) {
        return Oi().a(num);
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @Deprecated
    public j<Drawable> a(@Nullable URL url) {
        return Oi().a(url);
    }

    @NonNull
    public m a(@NonNull f fVar) {
        d(fVar);
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public <T> n<?, T> a(Class<T> cls) {
        return this.gj.Hi().a(cls);
    }

    /* access modifiers changed from: package-private */
    public void a(@NonNull com.bumptech.glide.request.target.o<?> oVar, @NonNull com.bumptech.glide.request.c cVar) {
        this.ck.f(oVar);
        this._j.i(cVar);
    }

    @CheckResult
    @NonNull
    public <ResourceType> j<ResourceType> b(@NonNull Class<ResourceType> cls) {
        return new j<>(this.gj, this, cls, this.context);
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<Drawable> c(@Nullable Uri uri) {
        return Oi().c(uri);
    }

    @NonNull
    public m c(@NonNull f fVar) {
        e(fVar);
        return this;
    }

    public void d(@NonNull View view) {
        d(new a(view));
    }

    /* access modifiers changed from: protected */
    public void d(@NonNull f fVar) {
        this.Qj = fVar.clone().Rj();
    }

    public void d(@Nullable com.bumptech.glide.request.target.o<?> oVar) {
        if (oVar != null) {
            if (l.Nk()) {
                g(oVar);
            } else {
                this.La.post(new l(this, oVar));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean e(@NonNull com.bumptech.glide.request.target.o<?> oVar) {
        com.bumptech.glide.request.c request = oVar.getRequest();
        if (request == null) {
            return true;
        }
        if (!this._j.h(request)) {
            return false;
        }
        this.ck.e(oVar);
        oVar.f(null);
        return true;
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<Drawable> f(@Nullable byte[] bArr) {
        return Oi().f(bArr);
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<Drawable> g(@Nullable Bitmap bitmap) {
        return Oi().g(bitmap);
    }

    public boolean isPaused() {
        l.Lk();
        return this._j.isPaused();
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<Drawable> load(@Nullable Object obj) {
        return Oi().load(obj);
    }

    @Override // com.bumptech.glide.g
    @CheckResult
    @NonNull
    public j<Drawable> load(@Nullable String str) {
        return Oi().load(str);
    }

    @CheckResult
    @NonNull
    public j<File> m(@Nullable Object obj) {
        return Ri().load(obj);
    }

    @Override // com.bumptech.glide.manager.j
    public void onDestroy() {
        this.ck.onDestroy();
        for (com.bumptech.glide.request.target.o<?> oVar : this.ck.getAll()) {
            d(oVar);
        }
        this.ck.clear();
        this._j.Lj();
        this.wa.a(this);
        this.wa.a(this.ek);
        this.La.removeCallbacks(this.dk);
        this.gj.c(this);
    }

    @Override // com.bumptech.glide.manager.j
    public void onStart() {
        Vi();
        this.ck.onStart();
    }

    @Override // com.bumptech.glide.manager.j
    public void onStop() {
        Ti();
        this.ck.onStop();
    }

    /* access modifiers changed from: package-private */
    public f ta() {
        return this.Qj;
    }

    public String toString() {
        return super.toString() + "{tracker=" + this._j + ", treeNode=" + this.bk + "}";
    }
}
