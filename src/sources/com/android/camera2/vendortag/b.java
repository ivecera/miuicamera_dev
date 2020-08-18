package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class b implements Supplier {
    public static final /* synthetic */ b INSTANCE = new b();

    private /* synthetic */ b() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.pg();
    }
}
