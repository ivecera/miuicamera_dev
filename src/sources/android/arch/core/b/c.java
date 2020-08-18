package android.arch.core.b;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* compiled from: SafeIterableMap */
public class c<K, V> implements Iterable<Map.Entry<K, V>> {
    private WeakHashMap<f<K, V>, Boolean> Ja = new WeakHashMap<>();
    private C0004c<K, V> mEnd;
    private int mSize = 0;
    /* access modifiers changed from: private */
    public C0004c<K, V> mStart;

    /* compiled from: SafeIterableMap */
    static class a<K, V> extends e<K, V> {
        a(C0004c<K, V> cVar, C0004c<K, V> cVar2) {
            super(cVar, cVar2);
        }

        /* access modifiers changed from: package-private */
        @Override // android.arch.core.b.c.e
        public C0004c<K, V> b(C0004c<K, V> cVar) {
            return cVar.Ga;
        }

        /* access modifiers changed from: package-private */
        @Override // android.arch.core.b.c.e
        public C0004c<K, V> c(C0004c<K, V> cVar) {
            return cVar.mNext;
        }
    }

    /* compiled from: SafeIterableMap */
    private static class b<K, V> extends e<K, V> {
        b(C0004c<K, V> cVar, C0004c<K, V> cVar2) {
            super(cVar, cVar2);
        }

        /* access modifiers changed from: package-private */
        @Override // android.arch.core.b.c.e
        public C0004c<K, V> b(C0004c<K, V> cVar) {
            return cVar.mNext;
        }

        /* access modifiers changed from: package-private */
        @Override // android.arch.core.b.c.e
        public C0004c<K, V> c(C0004c<K, V> cVar) {
            return cVar.Ga;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: android.arch.core.b.c$c  reason: collision with other inner class name */
    /* compiled from: SafeIterableMap */
    public static class C0004c<K, V> implements Map.Entry<K, V> {
        C0004c<K, V> Ga;
        @NonNull
        final K mKey;
        C0004c<K, V> mNext;
        @NonNull
        final V mValue;

        C0004c(@NonNull K k, @NonNull V v) {
            this.mKey = k;
            this.mValue = v;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof C0004c)) {
                return false;
            }
            C0004c cVar = (C0004c) obj;
            return this.mKey.equals(cVar.mKey) && this.mValue.equals(cVar.mValue);
        }

        @Override // java.util.Map.Entry
        @NonNull
        public K getKey() {
            return this.mKey;
        }

        @Override // java.util.Map.Entry
        @NonNull
        public V getValue() {
            return this.mValue;
        }

        public int hashCode() {
            return this.mValue.hashCode() ^ this.mKey.hashCode();
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public String toString() {
            return ((Object) this.mKey) + "=" + ((Object) this.mValue);
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: SafeIterableMap */
    public class d implements Iterator<Map.Entry<K, V>>, f<K, V> {
        private boolean Ha;
        private C0004c<K, V> mCurrent;

        private d() {
            this.Ha = true;
        }

        @Override // android.arch.core.b.c.f
        public void a(@NonNull C0004c<K, V> cVar) {
            C0004c<K, V> cVar2 = this.mCurrent;
            if (cVar == cVar2) {
                this.mCurrent = cVar2.Ga;
                this.Ha = this.mCurrent == null;
            }
        }

        public boolean hasNext() {
            if (this.Ha) {
                return c.this.mStart != null;
            }
            C0004c<K, V> cVar = this.mCurrent;
            return (cVar == null || cVar.mNext == null) ? false : true;
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            if (this.Ha) {
                this.Ha = false;
                this.mCurrent = c.this.mStart;
            } else {
                C0004c<K, V> cVar = this.mCurrent;
                this.mCurrent = cVar != null ? cVar.mNext : null;
            }
            return this.mCurrent;
        }
    }

    /* compiled from: SafeIterableMap */
    private static abstract class e<K, V> implements Iterator<Map.Entry<K, V>>, f<K, V> {
        C0004c<K, V> Ia;
        C0004c<K, V> mNext;

        e(C0004c<K, V> cVar, C0004c<K, V> cVar2) {
            this.Ia = cVar2;
            this.mNext = cVar;
        }

        private C0004c<K, V> nextNode() {
            C0004c<K, V> cVar = this.mNext;
            C0004c<K, V> cVar2 = this.Ia;
            if (cVar == cVar2 || cVar2 == null) {
                return null;
            }
            return c(cVar);
        }

        @Override // android.arch.core.b.c.f
        public void a(@NonNull C0004c<K, V> cVar) {
            if (this.Ia == cVar && cVar == this.mNext) {
                this.mNext = null;
                this.Ia = null;
            }
            C0004c<K, V> cVar2 = this.Ia;
            if (cVar2 == cVar) {
                this.Ia = b(cVar2);
            }
            if (this.mNext == cVar) {
                this.mNext = nextNode();
            }
        }

        /* access modifiers changed from: package-private */
        public abstract C0004c<K, V> b(C0004c<K, V> cVar);

        /* access modifiers changed from: package-private */
        public abstract C0004c<K, V> c(C0004c<K, V> cVar);

        public boolean hasNext() {
            return this.mNext != null;
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            C0004c<K, V> cVar = this.mNext;
            this.mNext = nextNode();
            return cVar;
        }
    }

    /* compiled from: SafeIterableMap */
    interface f<K, V> {
        void a(@NonNull C0004c<K, V> cVar);
    }

    public Iterator<Map.Entry<K, V>> descendingIterator() {
        b bVar = new b(this.mEnd, this.mStart);
        this.Ja.put(bVar, false);
        return bVar;
    }

    public Map.Entry<K, V> eldest() {
        return this.mStart;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        if (size() != cVar.size()) {
            return false;
        }
        Iterator<Map.Entry<K, V>> it = iterator();
        Iterator<Map.Entry<K, V>> it2 = cVar.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry<K, V> next = it.next();
            Map.Entry<K, V> next2 = it2.next();
            if ((next == null && next2 != null) || (next != null && !next.equals(next2))) {
                return false;
            }
        }
        return !it.hasNext() && !it2.hasNext();
    }

    /* access modifiers changed from: protected */
    public C0004c<K, V> get(K k) {
        C0004c<K, V> cVar = this.mStart;
        while (cVar != null && !cVar.mKey.equals(k)) {
            cVar = cVar.mNext;
        }
        return cVar;
    }

    public int hashCode() {
        Iterator<Map.Entry<K, V>> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().hashCode();
        }
        return i;
    }

    @Override // java.lang.Iterable
    @NonNull
    public Iterator<Map.Entry<K, V>> iterator() {
        a aVar = new a(this.mStart, this.mEnd);
        this.Ja.put(aVar, false);
        return aVar;
    }

    public c<K, V>.d pa() {
        c<K, V>.d dVar = new d();
        this.Ja.put(dVar, false);
        return dVar;
    }

    /* access modifiers changed from: protected */
    public C0004c<K, V> put(@NonNull K k, @NonNull V v) {
        C0004c<K, V> cVar = new C0004c<>(k, v);
        this.mSize++;
        C0004c<K, V> cVar2 = this.mEnd;
        if (cVar2 == null) {
            this.mStart = cVar;
            this.mEnd = this.mStart;
            return cVar;
        }
        cVar2.mNext = cVar;
        cVar.Ga = cVar2;
        this.mEnd = cVar;
        return cVar;
    }

    public V putIfAbsent(@NonNull K k, @NonNull V v) {
        C0004c<K, V> cVar = get(k);
        if (cVar != null) {
            return cVar.mValue;
        }
        put(k, v);
        return null;
    }

    public Map.Entry<K, V> qa() {
        return this.mEnd;
    }

    public V remove(@NonNull K k) {
        C0004c<K, V> cVar = get(k);
        if (cVar == null) {
            return null;
        }
        this.mSize--;
        if (!this.Ja.isEmpty()) {
            for (f<K, V> fVar : this.Ja.keySet()) {
                fVar.a(cVar);
            }
        }
        C0004c<K, V> cVar2 = cVar.Ga;
        if (cVar2 != null) {
            cVar2.mNext = cVar.mNext;
        } else {
            this.mStart = cVar.mNext;
        }
        C0004c<K, V> cVar3 = cVar.mNext;
        if (cVar3 != null) {
            cVar3.Ga = cVar.Ga;
        } else {
            this.mEnd = cVar.Ga;
        }
        cVar.mNext = null;
        cVar.Ga = null;
        return cVar.mValue;
    }

    public int size() {
        return this.mSize;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Map.Entry<K, V>> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
