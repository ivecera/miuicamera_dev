package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class fa implements Supplier {
    public static final /* synthetic */ fa INSTANCE = new fa();

    private /* synthetic */ fa() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.ji();
    }
}
