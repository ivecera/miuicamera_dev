package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Fb implements Supplier {
    public static final /* synthetic */ Fb INSTANCE = new Fb();

    private /* synthetic */ Fb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.oi();
    }
}
