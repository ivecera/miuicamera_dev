package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Rb implements Supplier {
    public static final /* synthetic */ Rb INSTANCE = new Rb();

    private /* synthetic */ Rb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.fg();
    }
}
