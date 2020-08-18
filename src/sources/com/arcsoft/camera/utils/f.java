package com.arcsoft.camera.utils;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: MPoint */
class f implements Parcelable.Creator {
    f() {
    }

    /* renamed from: a */
    public b b(Parcel parcel) {
        b bVar = new b();
        bVar.a(parcel);
        return bVar;
    }

    /* renamed from: a */
    public b[] b(int i) {
        return new b[i];
    }
}
