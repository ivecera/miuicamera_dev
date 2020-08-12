package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Dc implements Supplier {
    public static final /* synthetic */ Dc INSTANCE = new Dc();

    private /* synthetic */ Dc() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.ai();
    }
}
