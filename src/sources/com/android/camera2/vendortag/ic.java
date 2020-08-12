package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class ic implements Supplier {
    public static final /* synthetic */ ic INSTANCE = new ic();

    private /* synthetic */ ic() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.gi();
    }
}
