package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class x implements Supplier {
    public static final /* synthetic */ x INSTANCE = new x();

    private /* synthetic */ x() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.rg();
    }
}
