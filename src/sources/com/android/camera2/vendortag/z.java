package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class z implements Supplier {
    public static final /* synthetic */ z INSTANCE = new z();

    private /* synthetic */ z() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.jg();
    }
}
