package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Id implements Supplier {
    public static final /* synthetic */ Id INSTANCE = new Id();

    private /* synthetic */ Id() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.Mh();
    }
}
