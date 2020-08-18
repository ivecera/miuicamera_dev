package com.android.camera.data.observeable;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import java.util.HashMap;

public class DataItemObservable {
    private static final String DEFAULT_KEY = "com.android.camera.ViewModelProvider.DefaultKey";
    private final HashMap<String, VMBase> mMap = new HashMap<>();

    @NonNull
    @MainThread
    private <T extends VMBase> T get(@NonNull String str, @NonNull Class<T> cls) {
        T t = get(str);
        if (cls.isInstance(t)) {
            return t;
        }
        T create = create(cls);
        put(str, create);
        return create;
    }

    public final void clear() {
        for (VMBase vMBase : this.mMap.values()) {
            vMBase.onCleared();
        }
        this.mMap.clear();
    }

    @NonNull
    public <T extends VMBase> T create(@NonNull Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e2) {
            throw new RuntimeException("Cannot create an instance of " + cls, e2);
        } catch (IllegalAccessException e3) {
            throw new RuntimeException("Cannot create an instance of " + cls, e3);
        }
    }

    @NonNull
    @MainThread
    public <T extends VMBase> T get(@NonNull Class<T> cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return get("com.android.camera.ViewModelProvider.DefaultKey:" + canonicalName, cls);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    /* access modifiers changed from: package-private */
    public final VMBase get(String str) {
        return this.mMap.get(str);
    }

    /* access modifiers changed from: package-private */
    public final void put(String str, VMBase vMBase) {
        VMBase put = this.mMap.put(str, vMBase);
        if (put != null) {
            put.onCleared();
        }
    }
}
