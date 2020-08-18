package com.bumptech.glide.util;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;

public final class CachedHashCodeArrayMap<K, V> extends ArrayMap<K, V> {
    private int hashCode;

    @Override // android.support.v4.util.SimpleArrayMap
    public void clear() {
        this.hashCode = 0;
        super.clear();
    }

    @Override // android.support.v4.util.SimpleArrayMap
    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = super.hashCode();
        }
        return this.hashCode;
    }

    @Override // android.support.v4.util.SimpleArrayMap, java.util.Map
    public V put(K k, V v) {
        this.hashCode = 0;
        return super.put(k, v);
    }

    @Override // android.support.v4.util.SimpleArrayMap
    public void putAll(SimpleArrayMap<? extends K, ? extends V> simpleArrayMap) {
        this.hashCode = 0;
        super.putAll(simpleArrayMap);
    }

    @Override // android.support.v4.util.SimpleArrayMap
    public V removeAt(int i) {
        this.hashCode = 0;
        return super.removeAt(i);
    }

    @Override // android.support.v4.util.SimpleArrayMap
    public V setValueAt(int i, V v) {
        this.hashCode = 0;
        return super.setValueAt(i, v);
    }
}
