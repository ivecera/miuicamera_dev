package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class od implements Supplier {
    public static final /* synthetic */ od INSTANCE = new od();

    private /* synthetic */ od() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.bi();
    }
}
