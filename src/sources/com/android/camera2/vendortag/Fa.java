package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Fa implements Supplier {
    public static final /* synthetic */ Fa INSTANCE = new Fa();

    private /* synthetic */ Fa() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.li();
    }
}
