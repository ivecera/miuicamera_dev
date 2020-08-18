package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class A implements Supplier {
    public static final /* synthetic */ A INSTANCE = new A();

    private /* synthetic */ A() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.gg();
    }
}
