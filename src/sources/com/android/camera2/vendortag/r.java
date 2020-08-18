package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class r implements Supplier {
    public static final /* synthetic */ r INSTANCE = new r();

    private /* synthetic */ r() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.fg();
    }
}
