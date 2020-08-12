package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class c implements Supplier {
    public static final /* synthetic */ c INSTANCE = new c();

    private /* synthetic */ c() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.wg();
    }
}
