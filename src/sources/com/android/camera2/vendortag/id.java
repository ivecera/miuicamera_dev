package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class id implements Supplier {
    public static final /* synthetic */ id INSTANCE = new id();

    private /* synthetic */ id() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.ig();
    }
}
