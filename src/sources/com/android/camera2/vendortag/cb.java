package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class cb implements Supplier {
    public static final /* synthetic */ cb INSTANCE = new cb();

    private /* synthetic */ cb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.lg();
    }
}