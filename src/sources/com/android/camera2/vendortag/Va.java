package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Va implements Supplier {
    public static final /* synthetic */ Va INSTANCE = new Va();

    private /* synthetic */ Va() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.wg();
    }
}
