package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class u implements Supplier {
    public static final /* synthetic */ u INSTANCE = new u();

    private /* synthetic */ u() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.lg();
    }
}
