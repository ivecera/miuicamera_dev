package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Vc implements Supplier {
    public static final /* synthetic */ Vc INSTANCE = new Vc();

    private /* synthetic */ Vc() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.pg();
    }
}
