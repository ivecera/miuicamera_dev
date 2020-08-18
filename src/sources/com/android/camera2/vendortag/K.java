package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class K implements Supplier {
    public static final /* synthetic */ K INSTANCE = new K();

    private /* synthetic */ K() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.kg();
    }
}
