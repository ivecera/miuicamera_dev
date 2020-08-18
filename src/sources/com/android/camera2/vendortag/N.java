package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class N implements Supplier {
    public static final /* synthetic */ N INSTANCE = new N();

    private /* synthetic */ N() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.jh();
    }
}
