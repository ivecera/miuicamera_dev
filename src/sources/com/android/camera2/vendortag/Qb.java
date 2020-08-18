package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Qb implements Supplier {
    public static final /* synthetic */ Qb INSTANCE = new Qb();

    private /* synthetic */ Qb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.sg();
    }
}
