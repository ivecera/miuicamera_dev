package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class jb implements Supplier {
    public static final /* synthetic */ jb INSTANCE = new jb();

    private /* synthetic */ jb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.ui();
    }
}
