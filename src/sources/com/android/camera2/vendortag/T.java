package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class T implements Supplier {
    public static final /* synthetic */ T INSTANCE = new T();

    private /* synthetic */ T() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.ei();
    }
}
