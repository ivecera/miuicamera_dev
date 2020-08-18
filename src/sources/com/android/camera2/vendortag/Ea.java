package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ea implements Supplier {
    public static final /* synthetic */ Ea INSTANCE = new Ea();

    private /* synthetic */ Ea() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.ki();
    }
}
