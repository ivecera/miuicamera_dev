package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Uc implements Supplier {
    public static final /* synthetic */ Uc INSTANCE = new Uc();

    private /* synthetic */ Uc() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.vg();
    }
}
