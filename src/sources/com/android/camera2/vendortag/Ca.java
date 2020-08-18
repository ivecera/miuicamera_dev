package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ca implements Supplier {
    public static final /* synthetic */ Ca INSTANCE = new Ca();

    private /* synthetic */ Ca() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.vg();
    }
}
