package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Yb implements Supplier {
    public static final /* synthetic */ Yb INSTANCE = new Yb();

    private /* synthetic */ Yb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.ri();
    }
}
