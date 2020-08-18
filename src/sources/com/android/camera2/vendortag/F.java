package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class F implements Supplier {
    public static final /* synthetic */ F INSTANCE = new F();

    private /* synthetic */ F() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.mg();
    }
}
