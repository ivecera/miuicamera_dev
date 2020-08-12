package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Kc implements Supplier {
    public static final /* synthetic */ Kc INSTANCE = new Kc();

    private /* synthetic */ Kc() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.lg();
    }
}
