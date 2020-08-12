package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Pc implements Supplier {
    public static final /* synthetic */ Pc INSTANCE = new Pc();

    private /* synthetic */ Pc() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.di();
    }
}
