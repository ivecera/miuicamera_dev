package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class pb implements Supplier {
    public static final /* synthetic */ pb INSTANCE = new pb();

    private /* synthetic */ pb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.mh();
    }
}
