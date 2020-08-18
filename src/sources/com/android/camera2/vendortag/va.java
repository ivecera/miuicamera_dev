package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class va implements Supplier {
    public static final /* synthetic */ va INSTANCE = new va();

    private /* synthetic */ va() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.Pg();
    }
}
