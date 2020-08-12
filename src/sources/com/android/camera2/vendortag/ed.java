package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class ed implements Supplier {
    public static final /* synthetic */ ed INSTANCE = new ed();

    private /* synthetic */ ed() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.wg();
    }
}
