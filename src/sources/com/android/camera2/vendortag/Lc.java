package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Lc implements Supplier {
    public static final /* synthetic */ Lc INSTANCE = new Lc();

    private /* synthetic */ Lc() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.ci();
    }
}
