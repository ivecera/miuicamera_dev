package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class tb implements Supplier {
    public static final /* synthetic */ tb INSTANCE = new tb();

    private /* synthetic */ tb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.ti();
    }
}
