package com.bumptech.glide.load.engine.bitmap_recycle;

/* compiled from: ArrayPool */
public interface b {
    public static final int Gz = 65536;

    <T> T a(int i, Class<T> cls);

    @Deprecated
    <T> void a(T t, Class<T> cls);

    void aa();

    <T> T b(int i, Class<T> cls);

    <T> void put(T t);

    void trimMemory(int i);
}
