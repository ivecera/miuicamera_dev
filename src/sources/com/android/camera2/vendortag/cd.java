package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class cd implements Supplier {
    public static final /* synthetic */ cd INSTANCE = new cd();

    private /* synthetic */ cd() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.gi();
    }
}
