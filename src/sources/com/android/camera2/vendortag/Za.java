package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Za implements Supplier {
    public static final /* synthetic */ Za INSTANCE = new Za();

    private /* synthetic */ Za() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.Di();
    }
}
