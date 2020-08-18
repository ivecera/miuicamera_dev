package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class W implements Supplier {
    public static final /* synthetic */ W INSTANCE = new W();

    private /* synthetic */ W() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.ci();
    }
}
