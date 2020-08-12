package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class ya implements Supplier {
    public static final /* synthetic */ ya INSTANCE = new ya();

    private /* synthetic */ ya() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.sh();
    }
}
