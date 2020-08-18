package com.bumptech.glide.d;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.i;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ResourceEncoderRegistry */
public class f {
    private final List<a<?>> Bs = new ArrayList();

    /* compiled from: ResourceEncoderRegistry */
    private static final class a<T> {
        final i<T> encoder;
        private final Class<T> rm;

        a(@NonNull Class<T> cls, @NonNull i<T> iVar) {
            this.rm = cls;
            this.encoder = iVar;
        }

        /* access modifiers changed from: package-private */
        public boolean g(@NonNull Class<?> cls) {
            return this.rm.isAssignableFrom(cls);
        }
    }

    public synchronized <Z> void a(@NonNull Class<Z> cls, @NonNull i<Z> iVar) {
        this.Bs.add(new a<>(cls, iVar));
    }

    public synchronized <Z> void b(@NonNull Class<Z> cls, @NonNull i<Z> iVar) {
        this.Bs.add(0, new a<>(cls, iVar));
    }

    /* JADX DEBUG: Type inference failed for r5v3. Raw type applied. Possible types: com.bumptech.glide.load.i<T>, com.bumptech.glide.load.i<Z> */
    @Nullable
    public synchronized <Z> i<Z> get(@NonNull Class<Z> cls) {
        int size = this.Bs.size();
        for (int i = 0; i < size; i++) {
            a<?> aVar = this.Bs.get(i);
            if (aVar.g(cls)) {
                return aVar.encoder;
            }
        }
        return null;
    }
}
