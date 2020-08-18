package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class X implements Supplier {
    public static final /* synthetic */ X INSTANCE = new X();

    private /* synthetic */ X() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.mg();
    }
}
