package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ac implements Supplier {
    public static final /* synthetic */ Ac INSTANCE = new Ac();

    private /* synthetic */ Ac() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.gg();
    }
}
