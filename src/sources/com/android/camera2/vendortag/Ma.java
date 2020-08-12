package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ma implements Supplier {
    public static final /* synthetic */ Ma INSTANCE = new Ma();

    private /* synthetic */ Ma() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.qi();
    }
}
