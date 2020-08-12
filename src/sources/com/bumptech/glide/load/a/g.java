package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.e;
import com.bumptech.glide.util.i;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: DataRewinderRegistry */
public class g {
    private static final e.a<?> El = new f();
    private final Map<Class<?>, e.a<?>> Dl = new HashMap();

    /* access modifiers changed from: private */
    /* compiled from: DataRewinderRegistry */
    public static final class a implements e<Object> {
        private final Object data;

        a(@NonNull Object obj) {
            this.data = obj;
        }

        @Override // com.bumptech.glide.load.a.e
        @NonNull
        public Object W() {
            return this.data;
        }

        @Override // com.bumptech.glide.load.a.e
        public void cleanup() {
        }
    }

    public synchronized void a(@NonNull e.a<?> aVar) {
        this.Dl.put(aVar.ga(), aVar);
    }

    /* JADX DEBUG: Type inference failed for r6v2. Raw type applied. Possible types: com.bumptech.glide.load.a.e<?>, com.bumptech.glide.load.a.e<T> */
    @NonNull
    public synchronized <T> e<T> build(@NonNull T t) {
        e.a<?> aVar;
        i.checkNotNull(t);
        aVar = this.Dl.get(t.getClass());
        if (aVar == null) {
            Iterator<e.a<?>> it = this.Dl.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                e.a<?> next = it.next();
                if (next.ga().isAssignableFrom(t.getClass())) {
                    aVar = next;
                    break;
                }
            }
        }
        if (aVar == null) {
            aVar = El;
        }
        return aVar.build(t);
    }
}
