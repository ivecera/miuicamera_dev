package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Xb implements Supplier {
    public static final /* synthetic */ Xb INSTANCE = new Xb();

    private /* synthetic */ Xb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.xi();
    }
}
