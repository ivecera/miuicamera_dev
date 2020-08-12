package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ha implements Supplier {
    public static final /* synthetic */ Ha INSTANCE = new Ha();

    private /* synthetic */ Ha() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.ih();
    }
}
