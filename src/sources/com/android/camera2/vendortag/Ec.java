package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ec implements Supplier {
    public static final /* synthetic */ Ec INSTANCE = new Ec();

    private /* synthetic */ Ec() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.wh();
    }
}
