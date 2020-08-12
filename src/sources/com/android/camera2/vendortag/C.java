package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class C implements Supplier {
    public static final /* synthetic */ C INSTANCE = new C();

    private /* synthetic */ C() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.vg();
    }
}
