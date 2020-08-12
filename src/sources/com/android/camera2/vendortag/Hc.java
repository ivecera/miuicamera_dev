package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Hc implements Supplier {
    public static final /* synthetic */ Hc INSTANCE = new Hc();

    private /* synthetic */ Hc() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.vh();
    }
}
