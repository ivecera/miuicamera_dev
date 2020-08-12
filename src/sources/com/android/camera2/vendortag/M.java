package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class M implements Supplier {
    public static final /* synthetic */ M INSTANCE = new M();

    private /* synthetic */ M() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.ig();
    }
}
