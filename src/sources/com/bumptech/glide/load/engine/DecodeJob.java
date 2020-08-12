package com.bumptech.glide.load.engine;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.engine.f;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.j;
import com.bumptech.glide.load.resource.bitmap.o;
import com.bumptech.glide.util.a.d;
import com.bumptech.glide.util.a.g;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class DecodeJob<R> implements f.a, Runnable, Comparable<DecodeJob<?>>, d.c {
    private static final String TAG = "DecodeJob";
    private final g<R> Fm = new g<>();
    private final List<Throwable> Gm = new ArrayList();
    private final g Hm = g.newInstance();
    private volatile boolean Il;
    private final c<?> Im = new c<>();
    private final e Jm = new e();
    private s Km;
    private Stage Lm;
    private RunReason Mm;
    private long Nm;
    private boolean Om;
    private Thread Pm;
    private com.bumptech.glide.load.c Qm;
    private com.bumptech.glide.load.c Rm;
    private Object Sm;
    private DataSource Tm;
    private com.bumptech.glide.load.a.d<?> Um;
    private volatile f Vm;
    private volatile boolean Wm;
    private com.bumptech.glide.e _i;
    private a<R> callback;
    private int height;
    private Object model;
    private com.bumptech.glide.load.g options;
    private int order;
    private final Pools.Pool<DecodeJob<?>> pool;
    private Priority priority;
    private com.bumptech.glide.load.c qm;
    private final d sm;
    private o vm;
    private int width;

    /* access modifiers changed from: private */
    public enum RunReason {
        INITIALIZE,
        SWITCH_TO_SOURCE_SERVICE,
        DECODE_DATA
    }

    /* access modifiers changed from: private */
    public enum Stage {
        INITIALIZE,
        RESOURCE_CACHE,
        DATA_CACHE,
        SOURCE,
        ENCODE,
        FINISHED
    }

    interface a<R> {
        void a(A<R> a2, DataSource dataSource);

        void a(DecodeJob<?> decodeJob);

        void a(GlideException glideException);
    }

    private final class b<Z> implements i.a<Z> {
        private final DataSource dataSource;

        b(DataSource dataSource2) {
            this.dataSource = dataSource2;
        }

        @Override // com.bumptech.glide.load.engine.i.a
        @NonNull
        public A<Z> a(@NonNull A<Z> a2) {
            return DecodeJob.this.a(this.dataSource, a2);
        }
    }

    private static class c<Z> {
        private z<Z> Bm;
        private com.bumptech.glide.load.i<Z> encoder;
        private com.bumptech.glide.load.c key;

        c() {
        }

        /* access modifiers changed from: package-private */
        public <X> void a(com.bumptech.glide.load.c cVar, com.bumptech.glide.load.i<X> iVar, z<X> zVar) {
            this.key = cVar;
            this.encoder = iVar;
            this.Bm = zVar;
        }

        /* access modifiers changed from: package-private */
        public void a(d dVar, com.bumptech.glide.load.g gVar) {
            try {
                dVar.H().a(this.key, new e(this.encoder, this.Bm, gVar));
            } finally {
                this.Bm.unlock();
            }
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            this.key = null;
            this.encoder = null;
            this.Bm = null;
        }

        /* access modifiers changed from: package-private */
        public boolean nj() {
            return this.Bm != null;
        }
    }

    interface d {
        com.bumptech.glide.load.engine.a.a H();
    }

    private static class e {
        private boolean Cm;
        private boolean Dm;
        private boolean Em;

        e() {
        }

        private boolean H(boolean z) {
            return (this.Em || z || this.Dm) && this.Cm;
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean oj() {
            this.Dm = true;
            return H(false);
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean onFailed() {
            this.Em = true;
            return H(false);
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean release(boolean z) {
            this.Cm = true;
            return H(z);
        }

        /* access modifiers changed from: package-private */
        public synchronized void reset() {
            this.Dm = false;
            this.Cm = false;
            this.Em = false;
        }
    }

    DecodeJob(d dVar, Pools.Pool<DecodeJob<?>> pool2) {
        this.sm = dVar;
        this.pool = pool2;
    }

    private <Data> A<R> a(com.bumptech.glide.load.a.d<?> dVar, Data data, DataSource dataSource) throws GlideException {
        if (data == null) {
            dVar.cleanup();
            return null;
        }
        try {
            long Jk = com.bumptech.glide.util.e.Jk();
            A<R> a2 = a(data, dataSource);
            if (Log.isLoggable(TAG, 2)) {
                c("Decoded result " + a2, Jk);
            }
            return a2;
        } finally {
            dVar.cleanup();
        }
    }

    private <Data> A<R> a(Data data, DataSource dataSource) throws GlideException {
        return a(data, dataSource, this.Fm.c(data.getClass()));
    }

    private <Data, ResourceType> A<R> a(Data data, DataSource dataSource, x<Data, ResourceType, R> xVar) throws GlideException {
        com.bumptech.glide.load.g b2 = b(dataSource);
        com.bumptech.glide.load.a.e<Data> k = this._i.wa().k(data);
        try {
            return xVar.a(k, b2, this.width, this.height, new b(dataSource));
        } finally {
            k.cleanup();
        }
    }

    private Stage a(Stage stage) {
        int i = h.zm[stage.ordinal()];
        if (i == 1) {
            return this.vm.qj() ? Stage.DATA_CACHE : a(Stage.DATA_CACHE);
        }
        if (i == 2) {
            return this.Om ? Stage.FINISHED : Stage.SOURCE;
        }
        if (i == 3 || i == 4) {
            return Stage.FINISHED;
        }
        if (i == 5) {
            return this.vm.rj() ? Stage.RESOURCE_CACHE : a(Stage.RESOURCE_CACHE);
        }
        throw new IllegalArgumentException("Unrecognized stage: " + stage);
    }

    private void a(String str, long j, String str2) {
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" in ");
        sb.append(com.bumptech.glide.util.e.e(j));
        sb.append(", load key: ");
        sb.append(this.Km);
        if (str2 != null) {
            str3 = ", " + str2;
        } else {
            str3 = "";
        }
        sb.append(str3);
        sb.append(", thread: ");
        sb.append(Thread.currentThread().getName());
        Log.v(TAG, sb.toString());
    }

    @NonNull
    private com.bumptech.glide.load.g b(DataSource dataSource) {
        com.bumptech.glide.load.g gVar = this.options;
        if (Build.VERSION.SDK_INT < 26 || gVar.a(o.br) != null) {
            return gVar;
        }
        if (dataSource != DataSource.RESOURCE_DISK_CACHE && !this.Fm.mj()) {
            return gVar;
        }
        com.bumptech.glide.load.g gVar2 = new com.bumptech.glide.load.g();
        gVar2.b(this.options);
        gVar2.a(o.br, true);
        return gVar2;
    }

    private void b(A<R> a2, DataSource dataSource) {
        pn();
        this.callback.a(a2, dataSource);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.bumptech.glide.load.engine.z} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: com.bumptech.glide.load.engine.A<R>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: com.bumptech.glide.load.engine.z} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: com.bumptech.glide.load.engine.A<R>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: com.bumptech.glide.load.engine.z} */
    /* JADX WARN: Multi-variable type inference failed */
    private void c(A<R> a2, DataSource dataSource) {
        if (a2 instanceof v) {
            ((v) a2).initialize();
        }
        z zVar = 0;
        if (this.Im.nj()) {
            a2 = z.f(a2);
            zVar = a2;
        }
        b(a2, dataSource);
        this.Lm = Stage.ENCODE;
        try {
            if (this.Im.nj()) {
                this.Im.a(this.sm, this.options);
            }
            oj();
        } finally {
            if (zVar != 0) {
                zVar.unlock();
            }
        }
    }

    private void c(String str, long j) {
        a(str, j, (String) null);
    }

    private int getPriority() {
        return this.priority.ordinal();
    }

    private void jn() {
        if (Log.isLoggable(TAG, 2)) {
            long j = this.Nm;
            a("Retrieved data", j, "data: " + this.Sm + ", cache key: " + this.Qm + ", fetcher: " + this.Um);
        }
        A<R> a2 = null;
        try {
            a2 = a(this.Um, this.Sm, this.Tm);
        } catch (GlideException e2) {
            e2.a(this.Rm, this.Tm);
            this.Gm.add(e2);
        }
        if (a2 != null) {
            c(a2, this.Tm);
        } else {
            nn();
        }
    }

    private f kn() {
        int i = h.zm[this.Lm.ordinal()];
        if (i == 1) {
            return new B(this.Fm, this);
        }
        if (i == 2) {
            return new c(this.Fm, this);
        }
        if (i == 3) {
            return new E(this.Fm, this);
        }
        if (i == 4) {
            return null;
        }
        throw new IllegalStateException("Unrecognized stage: " + this.Lm);
    }

    private void ln() {
        if (this.Jm.onFailed()) {
            mn();
        }
    }

    private void mn() {
        this.Jm.reset();
        this.Im.clear();
        this.Fm.clear();
        this.Wm = false;
        this._i = null;
        this.qm = null;
        this.options = null;
        this.priority = null;
        this.Km = null;
        this.callback = null;
        this.Lm = null;
        this.Vm = null;
        this.Pm = null;
        this.Qm = null;
        this.Sm = null;
        this.Tm = null;
        this.Um = null;
        this.Nm = 0;
        this.Il = false;
        this.model = null;
        this.Gm.clear();
        this.pool.release(this);
    }

    private void nn() {
        this.Pm = Thread.currentThread();
        this.Nm = com.bumptech.glide.util.e.Jk();
        boolean z = false;
        while (!this.Il && this.Vm != null && !(z = this.Vm.K())) {
            this.Lm = a(this.Lm);
            this.Vm = kn();
            if (this.Lm == Stage.SOURCE) {
                ea();
                return;
            }
        }
        if ((this.Lm == Stage.FINISHED || this.Il) && !z) {
            notifyFailed();
        }
    }

    private void notifyFailed() {
        pn();
        this.callback.a(new GlideException("Failed to load resource", new ArrayList(this.Gm)));
        ln();
    }

    private void oj() {
        if (this.Jm.oj()) {
            mn();
        }
    }

    private void on() {
        int i = h.ym[this.Mm.ordinal()];
        if (i == 1) {
            this.Lm = a(Stage.INITIALIZE);
            this.Vm = kn();
            nn();
        } else if (i == 2) {
            nn();
        } else if (i == 3) {
            jn();
        } else {
            throw new IllegalStateException("Unrecognized run reason: " + this.Mm);
        }
    }

    private void pn() {
        this.Hm.Pk();
        if (!this.Wm) {
            this.Wm = true;
            return;
        }
        throw new IllegalStateException("Already notified");
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public <Z> A<Z> a(DataSource dataSource, @NonNull A<Z> a2) {
        j<Z> jVar;
        A<Z> a3;
        EncodeStrategy encodeStrategy;
        com.bumptech.glide.load.c cVar;
        Class<?> cls = a2.get().getClass();
        com.bumptech.glide.load.i iVar = null;
        if (dataSource != DataSource.RESOURCE_DISK_CACHE) {
            j<Z> d2 = this.Fm.d(cls);
            jVar = d2;
            a3 = d2.transform(this._i, a2, this.width, this.height);
        } else {
            a3 = a2;
            jVar = null;
        }
        if (!a2.equals(a3)) {
            a2.recycle();
        }
        if (this.Fm.d((A<?>) a3)) {
            iVar = this.Fm.c(a3);
            encodeStrategy = iVar.a(this.options);
        } else {
            encodeStrategy = EncodeStrategy.NONE;
        }
        if (!this.vm.a(!this.Fm.e(this.Qm), dataSource, encodeStrategy)) {
            return a3;
        }
        if (iVar != null) {
            int i = h.Am[encodeStrategy.ordinal()];
            if (i == 1) {
                cVar = new d(this.Qm, this.qm);
            } else if (i == 2) {
                cVar = new C(this.Fm.sa(), this.Qm, this.qm, this.width, this.height, jVar, cls, this.options);
            } else {
                throw new IllegalArgumentException("Unknown strategy: " + encodeStrategy);
            }
            z f2 = z.f(a3);
            this.Im.a(cVar, iVar, f2);
            return f2;
        }
        throw new Registry.NoResultEncoderAvailableException(a3.get().getClass());
    }

    /* access modifiers changed from: package-private */
    public DecodeJob<R> a(com.bumptech.glide.e eVar, Object obj, s sVar, com.bumptech.glide.load.c cVar, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority2, o oVar, Map<Class<?>, j<?>> map, boolean z, boolean z2, boolean z3, com.bumptech.glide.load.g gVar, a<R> aVar, int i3) {
        this.Fm.a(eVar, obj, cVar, i, i2, oVar, cls, cls2, priority2, gVar, map, z, z2, this.sm);
        this._i = eVar;
        this.qm = cVar;
        this.priority = priority2;
        this.Km = sVar;
        this.width = i;
        this.height = i2;
        this.vm = oVar;
        this.Om = z3;
        this.options = gVar;
        this.callback = aVar;
        this.order = i3;
        this.Mm = RunReason.INITIALIZE;
        this.model = obj;
        return this;
    }

    @Override // com.bumptech.glide.load.engine.f.a
    public void a(com.bumptech.glide.load.c cVar, Exception exc, com.bumptech.glide.load.a.d<?> dVar, DataSource dataSource) {
        dVar.cleanup();
        GlideException glideException = new GlideException("Fetching data failed", exc);
        glideException.a(cVar, dataSource, dVar.ga());
        this.Gm.add(glideException);
        if (Thread.currentThread() != this.Pm) {
            this.Mm = RunReason.SWITCH_TO_SOURCE_SERVICE;
            this.callback.a(this);
            return;
        }
        nn();
    }

    @Override // com.bumptech.glide.load.engine.f.a
    public void a(com.bumptech.glide.load.c cVar, Object obj, com.bumptech.glide.load.a.d<?> dVar, DataSource dataSource, com.bumptech.glide.load.c cVar2) {
        this.Qm = cVar;
        this.Sm = obj;
        this.Um = dVar;
        this.Tm = dataSource;
        this.Rm = cVar2;
        if (Thread.currentThread() != this.Pm) {
            this.Mm = RunReason.DECODE_DATA;
            this.callback.a(this);
            return;
        }
        jn();
    }

    /* renamed from: b */
    public int compareTo(@NonNull DecodeJob<?> decodeJob) {
        int priority2 = getPriority() - decodeJob.getPriority();
        return priority2 == 0 ? this.order - decodeJob.order : priority2;
    }

    public void cancel() {
        this.Il = true;
        f fVar = this.Vm;
        if (fVar != null) {
            fVar.cancel();
        }
    }

    @Override // com.bumptech.glide.load.engine.f.a
    public void ea() {
        this.Mm = RunReason.SWITCH_TO_SOURCE_SERVICE;
        this.callback.a(this);
    }

    @Override // com.bumptech.glide.util.a.d.c
    @NonNull
    public g getVerifier() {
        return this.Hm;
    }

    /* access modifiers changed from: package-private */
    public boolean pj() {
        Stage a2 = a(Stage.INITIALIZE);
        return a2 == Stage.RESOURCE_CACHE || a2 == Stage.DATA_CACHE;
    }

    /* access modifiers changed from: package-private */
    public void release(boolean z) {
        if (this.Jm.release(z)) {
            mn();
        }
    }

    public void run() {
        com.bumptech.glide.load.a.d<?> dVar = this.Um;
        try {
            if (this.Il) {
                notifyFailed();
                if (dVar != null) {
                    dVar.cleanup();
                    return;
                }
                return;
            }
            on();
            if (dVar == null) {
                return;
            }
            dVar.cleanup();
        } catch (Throwable th) {
            if (dVar != null) {
                dVar.cleanup();
            }
            throw th;
        }
    }
}
