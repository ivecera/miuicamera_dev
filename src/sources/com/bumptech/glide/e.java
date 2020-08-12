package com.bumptech.glide;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.widget.ImageView;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.target.i;
import java.util.Map;

/* compiled from: GlideContext */
public class e extends ContextWrapper {
    @VisibleForTesting
    static final n<?, ?> DEFAULT_TRANSITION_OPTIONS = new b();
    private final Handler La = new Handler(Looper.getMainLooper());
    private final b Ma;
    private final i Na;
    private final f Oa;
    private final Map<Class<?>, n<?, ?>> Pa;
    private final Engine Qa;
    private final int logLevel;
    private final Registry registry;

    public e(@NonNull Context context, @NonNull b bVar, @NonNull Registry registry2, @NonNull i iVar, @NonNull f fVar, @NonNull Map<Class<?>, n<?, ?>> map, @NonNull Engine engine, int i) {
        super(context.getApplicationContext());
        this.Ma = bVar;
        this.registry = registry2;
        this.Na = iVar;
        this.Oa = fVar;
        this.Pa = map;
        this.Qa = engine;
        this.logLevel = i;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.bumptech.glide.n<?, ?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: com.bumptech.glide.n<?, ?>} */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX DEBUG: Type inference failed for r0v4. Raw type applied. Possible types: com.bumptech.glide.n<?, ?>, com.bumptech.glide.n<?, T> */
    @NonNull
    public <T> n<?, T> a(@NonNull Class<T> cls) {
        n<?, T> nVar = this.Pa.get(cls);
        if (nVar == null) {
            for (Map.Entry<Class<?>, n<?, ?>> entry : this.Pa.entrySet()) {
                if (entry.getKey().isAssignableFrom(cls)) {
                    nVar = entry.getValue();
                }
            }
        }
        return nVar == null ? DEFAULT_TRANSITION_OPTIONS : nVar;
    }

    @NonNull
    public <X> ViewTarget<ImageView, X> a(@NonNull ImageView imageView, @NonNull Class<X> cls) {
        return this.Na.b(imageView, cls);
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    @NonNull
    public b sa() {
        return this.Ma;
    }

    public f ta() {
        return this.Oa;
    }

    @NonNull
    public Engine ua() {
        return this.Qa;
    }

    @NonNull
    public Handler va() {
        return this.La;
    }

    @NonNull
    public Registry wa() {
        return this.registry;
    }
}
