package com.bumptech.glide.request;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.Priority;
import com.bumptech.glide.e.b;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.d;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.j;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.bitmap.k;
import com.bumptech.glide.load.resource.bitmap.l;
import com.bumptech.glide.load.resource.bitmap.r;
import com.bumptech.glide.load.resource.bitmap.s;
import com.bumptech.glide.load.resource.gif.e;
import com.bumptech.glide.util.CachedHashCodeArrayMap;
import com.bumptech.glide.util.i;
import java.util.Map;

/* compiled from: RequestOptions */
public class f implements Cloneable {
    private static final int PRIORITY = 8;
    private static final int SIGNATURE = 1024;
    private static final int THEME = 32768;
    private static final int UNSET = -1;
    private static final int Ys = 2;
    private static final int Zs = 4;
    private static final int _s = 16;
    private static final int bt = 32;
    private static final int ct = 64;
    private static final int dt = 128;
    private static final int et = 256;
    private static final int ft = 512;
    private static final int gt = 4096;
    private static final int ht = 8192;
    private static final int jt = 16384;
    private static final int kt = 65536;
    private static final int lt = 131072;
    private static final int mt = 262144;
    private static final int nt = 524288;
    private static final int ot = 1048576;
    @Nullable
    private static f pt = null;
    @Nullable
    private static f qt = null;
    private static final int rq = 2048;
    @Nullable
    private static f rt;
    @Nullable
    private static f st;
    @Nullable
    private static f tt;
    @Nullable
    private static f ut;
    @Nullable
    private static f vt;
    @Nullable
    private static f wt;
    private boolean Mn;
    private float Ms = 1.0f;
    @Nullable
    private Drawable Ns;
    private boolean Om;
    private int Os;
    @Nullable
    private Drawable Ps;
    private int Qs;
    private int Rs = -1;
    private int Ss = -1;
    private boolean Ts = true;
    @Nullable
    private Drawable Us;
    private int Vs;
    private boolean Ws;
    private boolean Xs;
    private int fields;
    @NonNull
    private g options = new g();
    @NonNull
    private Priority priority = Priority.NORMAL;
    @NonNull
    private c qm = b.obtain();
    @NonNull
    private Class<?> rm = Object.class;
    @Nullable
    private Resources.Theme theme;
    private boolean tn = true;
    @NonNull
    private o vm = o.AUTOMATIC;
    private boolean vn;
    private boolean wm;
    @NonNull
    private Map<Class<?>, j<?>> xl = new CachedHashCodeArrayMap();
    private boolean xm = true;

    @CheckResult
    @NonNull
    public static f C(boolean z) {
        if (z) {
            if (pt == null) {
                pt = new f().B(true).Rj();
            }
            return pt;
        }
        if (qt == null) {
            qt = new f().B(false).Rj();
        }
        return qt;
    }

    @CheckResult
    @NonNull
    public static f G(@IntRange(from = 0, to = 100) int i) {
        return new f().F(i);
    }

    @CheckResult
    @NonNull
    public static f H(@DrawableRes int i) {
        return new f().error(i);
    }

    @CheckResult
    @NonNull
    public static f K(@IntRange(from = 0) int i) {
        return m(i, i);
    }

    @CheckResult
    @NonNull
    public static f M(@DrawableRes int i) {
        return new f().L(i);
    }

    @CheckResult
    @NonNull
    public static f O(@IntRange(from = 0) int i) {
        return new f().N(i);
    }

    @CheckResult
    @NonNull
    public static f Tj() {
        if (tt == null) {
            tt = new f().Sj().Rj();
        }
        return tt;
    }

    @NonNull
    private f Tn() {
        if (!this.Mn) {
            return this;
        }
        throw new IllegalStateException("You cannot modify locked RequestOptions, consider clone()");
    }

    @CheckResult
    @NonNull
    public static f Vj() {
        if (st == null) {
            st = new f().Uj().Rj();
        }
        return st;
    }

    @CheckResult
    @NonNull
    public static f Xj() {
        if (ut == null) {
            ut = new f().Wj().Rj();
        }
        return ut;
    }

    @CheckResult
    @NonNull
    public static f a(@NonNull j<Bitmap> jVar) {
        return new f().c(jVar);
    }

    @NonNull
    private f a(@NonNull j<Bitmap> jVar, boolean z) {
        if (this.Ws) {
            return clone().a(jVar, z);
        }
        r rVar = new r(jVar, z);
        a(Bitmap.class, jVar, z);
        a(Drawable.class, rVar, z);
        a(BitmapDrawable.class, rVar.Hj(), z);
        a(com.bumptech.glide.load.resource.gif.b.class, new e(jVar), z);
        Tn();
        return this;
    }

    @NonNull
    private f a(@NonNull DownsampleStrategy downsampleStrategy, @NonNull j<Bitmap> jVar, boolean z) {
        f b2 = z ? b(downsampleStrategy, jVar) : a(downsampleStrategy, jVar);
        b2.xm = true;
        return b2;
    }

    @NonNull
    private <T> f a(@NonNull Class<T> cls, @NonNull j<T> jVar, boolean z) {
        if (this.Ws) {
            return clone().a(cls, jVar, z);
        }
        i.checkNotNull(cls);
        i.checkNotNull(jVar);
        this.xl.put(cls, jVar);
        this.fields |= 2048;
        this.Ts = true;
        this.fields |= 65536;
        this.xm = false;
        if (z) {
            this.fields |= 131072;
            this.wm = true;
        }
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public static f ak() {
        if (rt == null) {
            rt = new f().fitCenter().Rj();
        }
        return rt;
    }

    @CheckResult
    @NonNull
    public static f b(@NonNull Bitmap.CompressFormat compressFormat) {
        return new f().a(compressFormat);
    }

    @CheckResult
    @NonNull
    public static f b(@NonNull Priority priority2) {
        return new f().a(priority2);
    }

    @CheckResult
    @NonNull
    public static f b(@NonNull DecodeFormat decodeFormat) {
        return new f().a(decodeFormat);
    }

    @CheckResult
    @NonNull
    public static f b(@NonNull o oVar) {
        return new f().a(oVar);
    }

    @CheckResult
    @NonNull
    public static <T> f b(@NonNull com.bumptech.glide.load.f<T> fVar, @NonNull T t) {
        return new f().a(fVar, t);
    }

    @CheckResult
    @NonNull
    public static f b(@NonNull DownsampleStrategy downsampleStrategy) {
        return new f().a(downsampleStrategy);
    }

    @NonNull
    private f c(@NonNull DownsampleStrategy downsampleStrategy, @NonNull j<Bitmap> jVar) {
        return a(downsampleStrategy, jVar, false);
    }

    @CheckResult
    @NonNull
    public static f d(@IntRange(from = 0) long j) {
        return new f().c(j);
    }

    @NonNull
    private f d(@NonNull DownsampleStrategy downsampleStrategy, @NonNull j<Bitmap> jVar) {
        return a(downsampleStrategy, jVar, true);
    }

    @CheckResult
    @NonNull
    public static f g(@Nullable Drawable drawable) {
        return new f().f(drawable);
    }

    @CheckResult
    @NonNull
    public static f h(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return new f().g(f2);
    }

    @CheckResult
    @NonNull
    public static f h(@NonNull c cVar) {
        return new f().g(cVar);
    }

    private boolean isSet(int i) {
        return r(this.fields, i);
    }

    @CheckResult
    @NonNull
    public static f j(@Nullable Drawable drawable) {
        return new f().i(drawable);
    }

    @CheckResult
    @NonNull
    public static f k(@NonNull Class<?> cls) {
        return new f().j(cls);
    }

    @CheckResult
    @NonNull
    public static f m(@IntRange(from = 0) int i, @IntRange(from = 0) int i2) {
        return new f().l(i, i2);
    }

    private static boolean r(int i, int i2) {
        return (i & i2) != 0;
    }

    @CheckResult
    @NonNull
    public static f xk() {
        if (wt == null) {
            wt = new f().Zj().Rj();
        }
        return wt;
    }

    @CheckResult
    @NonNull
    public static f yk() {
        if (vt == null) {
            vt = new f()._j().Rj();
        }
        return vt;
    }

    @CheckResult
    @NonNull
    public f A(boolean z) {
        if (this.Ws) {
            return clone().A(z);
        }
        this.Om = z;
        this.fields |= 524288;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f Ak() {
        return c(DownsampleStrategy.CENTER_INSIDE, new k());
    }

    @CheckResult
    @NonNull
    public f B(boolean z) {
        if (this.Ws) {
            return clone().B(true);
        }
        this.tn = !z;
        this.fields |= 256;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f Bk() {
        return a(DownsampleStrategy.Uq, new l());
    }

    @CheckResult
    @NonNull
    public f Ck() {
        return c(DownsampleStrategy.FIT_CENTER, new s());
    }

    @CheckResult
    @NonNull
    public f D(boolean z) {
        if (this.Ws) {
            return clone().D(z);
        }
        this.vn = z;
        this.fields |= 1048576;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f E(boolean z) {
        if (this.Ws) {
            return clone().E(z);
        }
        this.Xs = z;
        this.fields |= 262144;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f F(@IntRange(from = 0, to = 100) int i) {
        return a(com.bumptech.glide.load.resource.bitmap.e.sq, Integer.valueOf(i));
    }

    @CheckResult
    @NonNull
    public f I(@DrawableRes int i) {
        if (this.Ws) {
            return clone().I(i);
        }
        this.Vs = i;
        this.fields |= 16384;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f J(int i) {
        return l(i, i);
    }

    @CheckResult
    @NonNull
    public f L(@DrawableRes int i) {
        if (this.Ws) {
            return clone().L(i);
        }
        this.Qs = i;
        this.fields |= 128;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f N(@IntRange(from = 0) int i) {
        return a(com.bumptech.glide.load.model.a.b.TIMEOUT, Integer.valueOf(i));
    }

    @NonNull
    public f Rj() {
        if (!this.Mn || this.Ws) {
            this.Ws = true;
            return lock();
        }
        throw new IllegalStateException("You cannot auto lock an already locked options object, try clone() first");
    }

    @CheckResult
    @NonNull
    public f Sj() {
        return b(DownsampleStrategy.Uq, new com.bumptech.glide.load.resource.bitmap.j());
    }

    @NonNull
    public final Class<?> T() {
        return this.rm;
    }

    @CheckResult
    @NonNull
    public f Uj() {
        return d(DownsampleStrategy.CENTER_INSIDE, new k());
    }

    @CheckResult
    @NonNull
    public f Wj() {
        return b(DownsampleStrategy.CENTER_INSIDE, new l());
    }

    @CheckResult
    @NonNull
    public f Yj() {
        return a((com.bumptech.glide.load.f) com.bumptech.glide.load.resource.bitmap.o.br, (Object) false);
    }

    @CheckResult
    @NonNull
    public f Zj() {
        return a((com.bumptech.glide.load.f) com.bumptech.glide.load.resource.gif.g.Zr, (Object) true);
    }

    @CheckResult
    @NonNull
    public f _j() {
        if (this.Ws) {
            return clone()._j();
        }
        this.xl.clear();
        this.fields &= -2049;
        this.wm = false;
        this.fields &= -131073;
        this.Ts = false;
        this.fields |= 65536;
        this.xm = true;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f a(@Nullable Resources.Theme theme2) {
        if (this.Ws) {
            return clone().a(theme2);
        }
        this.theme = theme2;
        this.fields |= 32768;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f a(@NonNull Bitmap.CompressFormat compressFormat) {
        com.bumptech.glide.load.f<Bitmap.CompressFormat> fVar = com.bumptech.glide.load.resource.bitmap.e.tq;
        i.checkNotNull(compressFormat);
        return a(fVar, compressFormat);
    }

    @CheckResult
    @NonNull
    public f a(@NonNull Priority priority2) {
        if (this.Ws) {
            return clone().a(priority2);
        }
        i.checkNotNull(priority2);
        this.priority = priority2;
        this.fields |= 8;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f a(@NonNull DecodeFormat decodeFormat) {
        i.checkNotNull(decodeFormat);
        return a(com.bumptech.glide.load.resource.bitmap.o.Yq, decodeFormat).a(com.bumptech.glide.load.resource.gif.g.Yq, decodeFormat);
    }

    @CheckResult
    @NonNull
    public f a(@NonNull o oVar) {
        if (this.Ws) {
            return clone().a(oVar);
        }
        i.checkNotNull(oVar);
        this.vm = oVar;
        this.fields |= 4;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public <T> f a(@NonNull com.bumptech.glide.load.f<T> fVar, @NonNull T t) {
        if (this.Ws) {
            return clone().a(fVar, t);
        }
        i.checkNotNull(fVar);
        i.checkNotNull(t);
        this.options.a(fVar, t);
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f a(@NonNull DownsampleStrategy downsampleStrategy) {
        com.bumptech.glide.load.f<DownsampleStrategy> fVar = DownsampleStrategy.Wq;
        i.checkNotNull(downsampleStrategy);
        return a(fVar, downsampleStrategy);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final f a(@NonNull DownsampleStrategy downsampleStrategy, @NonNull j<Bitmap> jVar) {
        if (this.Ws) {
            return clone().a(downsampleStrategy, jVar);
        }
        a(downsampleStrategy);
        return a(jVar, false);
    }

    @CheckResult
    @NonNull
    public <T> f a(@NonNull Class<T> cls, @NonNull j<T> jVar) {
        return a((Class) cls, (j) jVar, false);
    }

    @CheckResult
    @NonNull
    public f a(@NonNull j<Bitmap>... jVarArr) {
        return a((j<Bitmap>) new d(jVarArr), true);
    }

    @CheckResult
    @NonNull
    public f b(@NonNull j<Bitmap> jVar) {
        return a(jVar, false);
    }

    /* access modifiers changed from: package-private */
    @CheckResult
    @NonNull
    public final f b(@NonNull DownsampleStrategy downsampleStrategy, @NonNull j<Bitmap> jVar) {
        if (this.Ws) {
            return clone().b(downsampleStrategy, jVar);
        }
        a(downsampleStrategy);
        return c(jVar);
    }

    @CheckResult
    @NonNull
    public f b(@NonNull f fVar) {
        if (this.Ws) {
            return clone().b(fVar);
        }
        if (r(fVar.fields, 2)) {
            this.Ms = fVar.Ms;
        }
        if (r(fVar.fields, 262144)) {
            this.Xs = fVar.Xs;
        }
        if (r(fVar.fields, 1048576)) {
            this.vn = fVar.vn;
        }
        if (r(fVar.fields, 4)) {
            this.vm = fVar.vm;
        }
        if (r(fVar.fields, 8)) {
            this.priority = fVar.priority;
        }
        if (r(fVar.fields, 16)) {
            this.Ns = fVar.Ns;
        }
        if (r(fVar.fields, 32)) {
            this.Os = fVar.Os;
        }
        if (r(fVar.fields, 64)) {
            this.Ps = fVar.Ps;
        }
        if (r(fVar.fields, 128)) {
            this.Qs = fVar.Qs;
        }
        if (r(fVar.fields, 256)) {
            this.tn = fVar.tn;
        }
        if (r(fVar.fields, 512)) {
            this.Ss = fVar.Ss;
            this.Rs = fVar.Rs;
        }
        if (r(fVar.fields, 1024)) {
            this.qm = fVar.qm;
        }
        if (r(fVar.fields, 4096)) {
            this.rm = fVar.rm;
        }
        if (r(fVar.fields, 8192)) {
            this.Us = fVar.Us;
        }
        if (r(fVar.fields, 16384)) {
            this.Vs = fVar.Vs;
        }
        if (r(fVar.fields, 32768)) {
            this.theme = fVar.theme;
        }
        if (r(fVar.fields, 65536)) {
            this.Ts = fVar.Ts;
        }
        if (r(fVar.fields, 131072)) {
            this.wm = fVar.wm;
        }
        if (r(fVar.fields, 2048)) {
            this.xl.putAll(fVar.xl);
            this.xm = fVar.xm;
        }
        if (r(fVar.fields, 524288)) {
            this.Om = fVar.Om;
        }
        if (!this.Ts) {
            this.xl.clear();
            this.fields &= -2049;
            this.wm = false;
            this.fields &= -131073;
            this.xm = true;
        }
        this.fields |= fVar.fields;
        this.options.b(fVar.options);
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public <T> f b(@NonNull Class<T> cls, @NonNull j<T> jVar) {
        return a((Class) cls, (j) jVar, true);
    }

    public final int bk() {
        return this.Os;
    }

    @CheckResult
    @NonNull
    public f c(@IntRange(from = 0) long j) {
        return a(VideoDecoder.Er, Long.valueOf(j));
    }

    @CheckResult
    @NonNull
    public f c(@NonNull j<Bitmap> jVar) {
        return a(jVar, true);
    }

    @Nullable
    public final Drawable ck() {
        return this.Ns;
    }

    @Override // java.lang.Object
    @CheckResult
    public f clone() {
        try {
            f fVar = (f) super.clone();
            fVar.options = new g();
            fVar.options.b(this.options);
            fVar.xl = new CachedHashCodeArrayMap();
            fVar.xl.putAll(this.xl);
            fVar.Mn = false;
            fVar.Ws = false;
            return fVar;
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Nullable
    public final Drawable dk() {
        return this.Us;
    }

    public final int ek() {
        return this.Vs;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return Float.compare(fVar.Ms, this.Ms) == 0 && this.Os == fVar.Os && com.bumptech.glide.util.l.d(this.Ns, fVar.Ns) && this.Qs == fVar.Qs && com.bumptech.glide.util.l.d(this.Ps, fVar.Ps) && this.Vs == fVar.Vs && com.bumptech.glide.util.l.d(this.Us, fVar.Us) && this.tn == fVar.tn && this.Rs == fVar.Rs && this.Ss == fVar.Ss && this.wm == fVar.wm && this.Ts == fVar.Ts && this.Xs == fVar.Xs && this.Om == fVar.Om && this.vm.equals(fVar.vm) && this.priority == fVar.priority && this.options.equals(fVar.options) && this.xl.equals(fVar.xl) && this.rm.equals(fVar.rm) && com.bumptech.glide.util.l.d(this.qm, fVar.qm) && com.bumptech.glide.util.l.d(this.theme, fVar.theme);
    }

    @CheckResult
    @NonNull
    public f error(@DrawableRes int i) {
        if (this.Ws) {
            return clone().error(i);
        }
        this.Os = i;
        this.fields |= 32;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f f(@Nullable Drawable drawable) {
        if (this.Ws) {
            return clone().f(drawable);
        }
        this.Ns = drawable;
        this.fields |= 16;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f fitCenter() {
        return d(DownsampleStrategy.FIT_CENTER, new s());
    }

    public final boolean fk() {
        return this.Om;
    }

    @CheckResult
    @NonNull
    public f g(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        if (this.Ws) {
            return clone().g(f2);
        }
        if (f2 < 0.0f || f2 > 1.0f) {
            throw new IllegalArgumentException("sizeMultiplier must be between 0 and 1");
        }
        this.Ms = f2;
        this.fields |= 2;
        Tn();
        return this;
    }

    @CheckResult
    @NonNull
    public f g(@NonNull c cVar) {
        if (this.Ws) {
            return clone().g(cVar);
        }
        i.checkNotNull(cVar);
        this.qm = cVar;
        this.fields |= 1024;
        Tn();
        return this;
    }

    @NonNull
    public final g getOptions() {
        return this.options;
    }

    @NonNull
    public final Priority getPriority() {
        return this.priority;
    }

    @NonNull
    public final c getSignature() {
        return this.qm;
    }

    @Nullable
    public final Resources.Theme getTheme() {
        return this.theme;
    }

    @NonNull
    public final Map<Class<?>, j<?>> getTransformations() {
        return this.xl;
    }

    public final int gk() {
        return this.Rs;
    }

    @CheckResult
    @NonNull
    public f h(@Nullable Drawable drawable) {
        if (this.Ws) {
            return clone().h(drawable);
        }
        this.Us = drawable;
        this.fields |= 8192;
        Tn();
        return this;
    }

    public int hashCode() {
        return com.bumptech.glide.util.l.a(this.theme, com.bumptech.glide.util.l.a(this.qm, com.bumptech.glide.util.l.a(this.rm, com.bumptech.glide.util.l.a(this.xl, com.bumptech.glide.util.l.a(this.options, com.bumptech.glide.util.l.a(this.priority, com.bumptech.glide.util.l.a(this.vm, com.bumptech.glide.util.l.c(this.Om, com.bumptech.glide.util.l.c(this.Xs, com.bumptech.glide.util.l.c(this.Ts, com.bumptech.glide.util.l.c(this.wm, com.bumptech.glide.util.l.n(this.Ss, com.bumptech.glide.util.l.n(this.Rs, com.bumptech.glide.util.l.c(this.tn, com.bumptech.glide.util.l.a(this.Us, com.bumptech.glide.util.l.n(this.Vs, com.bumptech.glide.util.l.a(this.Ps, com.bumptech.glide.util.l.n(this.Qs, com.bumptech.glide.util.l.a(this.Ns, com.bumptech.glide.util.l.n(this.Os, com.bumptech.glide.util.l.hashCode(this.Ms)))))))))))))))))))));
    }

    @NonNull
    public final o hj() {
        return this.vm;
    }

    public final int hk() {
        return this.Ss;
    }

    @CheckResult
    @NonNull
    public f i(@Nullable Drawable drawable) {
        if (this.Ws) {
            return clone().i(drawable);
        }
        this.Ps = drawable;
        this.fields |= 64;
        Tn();
        return this;
    }

    @Nullable
    public final Drawable ik() {
        return this.Ps;
    }

    public final boolean isLocked() {
        return this.Mn;
    }

    @CheckResult
    @NonNull
    public f j(@NonNull Class<?> cls) {
        if (this.Ws) {
            return clone().j(cls);
        }
        i.checkNotNull(cls);
        this.rm = cls;
        this.fields |= 4096;
        Tn();
        return this;
    }

    public final int jk() {
        return this.Qs;
    }

    public final float kk() {
        return this.Ms;
    }

    @CheckResult
    @NonNull
    public f l(int i, int i2) {
        if (this.Ws) {
            return clone().l(i, i2);
        }
        this.Ss = i;
        this.Rs = i2;
        this.fields |= 512;
        Tn();
        return this;
    }

    public final boolean lk() {
        return this.vn;
    }

    @NonNull
    public f lock() {
        this.Mn = true;
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean mj() {
        return this.xm;
    }

    public final boolean mk() {
        return this.Xs;
    }

    /* access modifiers changed from: protected */
    public boolean nk() {
        return this.Ws;
    }

    public final boolean pk() {
        return isSet(4);
    }

    public final boolean qk() {
        return this.tn;
    }

    public final boolean rk() {
        return isSet(8);
    }

    public final boolean sk() {
        return isSet(256);
    }

    public final boolean tk() {
        return this.Ts;
    }

    public final boolean uk() {
        return this.wm;
    }

    public final boolean vk() {
        return isSet(2048);
    }

    public final boolean wk() {
        return com.bumptech.glide.util.l.o(this.Ss, this.Rs);
    }

    @CheckResult
    @NonNull
    public f zk() {
        return a(DownsampleStrategy.Uq, new com.bumptech.glide.load.resource.bitmap.j());
    }
}
