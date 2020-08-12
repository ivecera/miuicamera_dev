package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.j;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;
import java.util.Map;

/* compiled from: EngineKey */
class s implements c {
    private final Class<?> Pj;
    private int hashCode;
    private final int height;
    private final Object model;
    private final g options;
    private final c qm;
    private final Class<?> rm;
    private final int width;
    private final Map<Class<?>, j<?>> xl;

    s(Object obj, c cVar, int i, int i2, Map<Class<?>, j<?>> map, Class<?> cls, Class<?> cls2, g gVar) {
        i.checkNotNull(obj);
        this.model = obj;
        i.b(cVar, "Signature must not be null");
        this.qm = cVar;
        this.width = i;
        this.height = i2;
        i.checkNotNull(map);
        this.xl = map;
        i.b(cls, "Resource class must not be null");
        this.rm = cls;
        i.b(cls2, "Transcode class must not be null");
        this.Pj = cls2;
        i.checkNotNull(gVar);
        this.options = gVar;
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (!(obj instanceof s)) {
            return false;
        }
        s sVar = (s) obj;
        return this.model.equals(sVar.model) && this.qm.equals(sVar.qm) && this.height == sVar.height && this.width == sVar.width && this.xl.equals(sVar.xl) && this.rm.equals(sVar.rm) && this.Pj.equals(sVar.Pj) && this.options.equals(sVar.options);
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.model.hashCode();
            this.hashCode = (this.hashCode * 31) + this.qm.hashCode();
            this.hashCode = (this.hashCode * 31) + this.width;
            this.hashCode = (this.hashCode * 31) + this.height;
            this.hashCode = (this.hashCode * 31) + this.xl.hashCode();
            this.hashCode = (this.hashCode * 31) + this.rm.hashCode();
            this.hashCode = (this.hashCode * 31) + this.Pj.hashCode();
            this.hashCode = (this.hashCode * 31) + this.options.hashCode();
        }
        return this.hashCode;
    }

    public String toString() {
        return "EngineKey{model=" + this.model + ", width=" + this.width + ", height=" + this.height + ", resourceClass=" + this.rm + ", transcodeClass=" + this.Pj + ", signature=" + this.qm + ", hashCode=" + this.hashCode + ", transformations=" + this.xl + ", options=" + this.options + '}';
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        throw new UnsupportedOperationException();
    }
}
