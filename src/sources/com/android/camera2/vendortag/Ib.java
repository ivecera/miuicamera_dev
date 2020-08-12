package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ib implements Supplier {
    public static final /* synthetic */ Ib INSTANCE = new Ib();

    private /* synthetic */ Ib() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.lh();
    }
}
