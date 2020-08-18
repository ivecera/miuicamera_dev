package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class ea implements Supplier {
    public static final /* synthetic */ ea INSTANCE = new ea();

    private /* synthetic */ ea() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.gh();
    }
}
