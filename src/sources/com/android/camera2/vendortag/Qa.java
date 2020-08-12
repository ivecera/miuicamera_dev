package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Qa implements Supplier {
    public static final /* synthetic */ Qa INSTANCE = new Qa();

    private /* synthetic */ Qa() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.ii();
    }
}
