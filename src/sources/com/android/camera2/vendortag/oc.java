package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class oc implements Supplier {
    public static final /* synthetic */ oc INSTANCE = new oc();

    private /* synthetic */ oc() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.rg();
    }
}
