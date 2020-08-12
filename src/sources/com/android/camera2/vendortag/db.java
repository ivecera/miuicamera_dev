package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class db implements Supplier {
    public static final /* synthetic */ db INSTANCE = new db();

    private /* synthetic */ db() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.Sg();
    }
}
