package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Pa implements Supplier {
    public static final /* synthetic */ Pa INSTANCE = new Pa();

    private /* synthetic */ Pa() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.vi();
    }
}
