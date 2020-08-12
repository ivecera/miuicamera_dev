package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.j;
import com.bumptech.glide.util.f;
import com.bumptech.glide.util.l;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: ResourceCacheKey */
final class C implements c {
    private static final f<Class<?>, byte[]> Tn = new f<>(50);
    private final b Ma;
    private final Class<?> Rn;
    private final j<?> Sn;
    private final int height;
    private final c lm;
    private final g options;
    private final c qm;
    private final int width;

    C(b bVar, c cVar, c cVar2, int i, int i2, j<?> jVar, Class<?> cls, g gVar) {
        this.Ma = bVar;
        this.lm = cVar;
        this.qm = cVar2;
        this.width = i;
        this.height = i2;
        this.Sn = jVar;
        this.Rn = cls;
        this.options = gVar;
    }

    private byte[] rn() {
        byte[] bArr = Tn.get(this.Rn);
        if (bArr != null) {
            return bArr;
        }
        byte[] bytes = this.Rn.getName().getBytes(c.CHARSET);
        Tn.put(this.Rn, bytes);
        return bytes;
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (!(obj instanceof C)) {
            return false;
        }
        C c2 = (C) obj;
        return this.height == c2.height && this.width == c2.width && l.d(this.Sn, c2.Sn) && this.Rn.equals(c2.Rn) && this.lm.equals(c2.lm) && this.qm.equals(c2.qm) && this.options.equals(c2.options);
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        int hashCode = (((((this.lm.hashCode() * 31) + this.qm.hashCode()) * 31) + this.width) * 31) + this.height;
        j<?> jVar = this.Sn;
        if (jVar != null) {
            hashCode = (hashCode * 31) + jVar.hashCode();
        }
        return (((hashCode * 31) + this.Rn.hashCode()) * 31) + this.options.hashCode();
    }

    public String toString() {
        return "ResourceCacheKey{sourceKey=" + this.lm + ", signature=" + this.qm + ", width=" + this.width + ", height=" + this.height + ", decodedResourceClass=" + this.Rn + ", transformation='" + this.Sn + '\'' + ", options=" + this.options + '}';
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        byte[] bArr = (byte[]) this.Ma.b(8, byte[].class);
        ByteBuffer.wrap(bArr).putInt(this.width).putInt(this.height).array();
        this.qm.updateDiskCacheKey(messageDigest);
        this.lm.updateDiskCacheKey(messageDigest);
        messageDigest.update(bArr);
        j<?> jVar = this.Sn;
        if (jVar != null) {
            jVar.updateDiskCacheKey(messageDigest);
        }
        this.options.updateDiskCacheKey(messageDigest);
        messageDigest.update(rn());
        this.Ma.put(bArr);
    }
}
