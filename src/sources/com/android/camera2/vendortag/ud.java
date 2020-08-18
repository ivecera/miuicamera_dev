package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class ud implements Supplier {
    public static final /* synthetic */ ud INSTANCE = new ud();

    private /* synthetic */ ud() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.ki();
    }
}
