package android.arch.core.b;

import android.arch.core.b.c;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* compiled from: FastSafeIterableMap */
public class a<K, V> extends c<K, V> {
    private HashMap<K, c.C0004c<K, V>> Ka = new HashMap<>();

    public boolean contains(K k) {
        return this.Ka.containsKey(k);
    }

    public Map.Entry<K, V> g(K k) {
        if (contains(k)) {
            return this.Ka.get(k).Ga;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // android.arch.core.b.c
    public c.C0004c<K, V> get(K k) {
        return this.Ka.get(k);
    }

    @Override // android.arch.core.b.c
    public V putIfAbsent(@NonNull K k, @NonNull V v) {
        c.C0004c<K, V> cVar = get(k);
        if (cVar != null) {
            return cVar.mValue;
        }
        this.Ka.put(k, put(k, v));
        return null;
    }

    @Override // android.arch.core.b.c
    public V remove(@NonNull K k) {
        V remove = super.remove(k);
        this.Ka.remove(k);
        return remove;
    }
}
