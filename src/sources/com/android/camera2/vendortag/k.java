package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class k implements Supplier {
    public static final /* synthetic */ k INSTANCE = new k();

    private /* synthetic */ k() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.sg();
    }
}
