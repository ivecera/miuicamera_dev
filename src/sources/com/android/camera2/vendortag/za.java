package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class za implements Supplier {
    public static final /* synthetic */ za INSTANCE = new za();

    private /* synthetic */ za() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.di();
    }
}
