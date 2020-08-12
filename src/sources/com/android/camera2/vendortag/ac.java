package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class ac implements Supplier {
    public static final /* synthetic */ ac INSTANCE = new ac();

    private /* synthetic */ ac() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.vh();
    }
}
