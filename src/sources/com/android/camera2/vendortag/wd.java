package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class wd implements Supplier {
    public static final /* synthetic */ wd INSTANCE = new wd();

    private /* synthetic */ wd() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.fg();
    }
}
