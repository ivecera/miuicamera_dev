package com.bumptech.glide.load.engine;

import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.c;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Jobs */
final class w {
    private final Map<c, EngineJob<?>> Jn = new HashMap();
    private final Map<c, EngineJob<?>> hn = new HashMap();

    w() {
    }

    private Map<c, EngineJob<?>> I(boolean z) {
        return z ? this.Jn : this.hn;
    }

    /* access modifiers changed from: package-private */
    public EngineJob<?> a(c cVar, boolean z) {
        return I(z).get(cVar);
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar, EngineJob<?> engineJob) {
        I(engineJob.vj()).put(cVar, engineJob);
    }

    /* access modifiers changed from: package-private */
    public void b(c cVar, EngineJob<?> engineJob) {
        Map<c, EngineJob<?>> I = I(engineJob.vj());
        if (engineJob.equals(I.get(cVar))) {
            I.remove(cVar);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Map<c, EngineJob<?>> getAll() {
        return Collections.unmodifiableMap(this.hn);
    }
}
