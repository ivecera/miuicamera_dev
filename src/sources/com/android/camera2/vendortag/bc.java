package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class bc implements Supplier {
    public static final /* synthetic */ bc INSTANCE = new bc();

    private /* synthetic */ bc() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.si();
    }
}
