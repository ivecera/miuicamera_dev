package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class w implements Supplier {
    public static final /* synthetic */ w INSTANCE = new w();

    private /* synthetic */ w() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.tg();
    }
}
