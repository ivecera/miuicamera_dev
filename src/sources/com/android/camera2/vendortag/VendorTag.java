package com.android.camera2.vendortag;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public abstract class VendorTag<K> {
    private final AtomicReference<Optional<K>> mCachedValue = new AtomicReference<>(Optional.empty());

    /* access modifiers changed from: protected */
    public abstract K create();

    /* access modifiers changed from: package-private */
    public final K getKey() {
        if (!this.mCachedValue.get().isPresent()) {
            synchronized (this.mCachedValue) {
                if (!this.mCachedValue.get().isPresent()) {
                    this.mCachedValue.set(Optional.ofNullable(create()));
                }
            }
        }
        return this.mCachedValue.get().get();
    }

    public abstract String getName();

    public String toString() {
        return getName();
    }
}
