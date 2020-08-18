package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ic implements Supplier {
    public static final /* synthetic */ Ic INSTANCE = new Ic();

    private /* synthetic */ Ic() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.sg();
    }
}
