package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Z implements Supplier {
    public static final /* synthetic */ Z INSTANCE = new Z();

    private /* synthetic */ Z() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.bi();
    }
}
