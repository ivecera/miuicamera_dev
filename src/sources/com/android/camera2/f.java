package com.android.camera2;

import java.util.Comparator;
import java.util.HashMap;

/* compiled from: lambda */
public final /* synthetic */ class f implements Comparator {
    private final /* synthetic */ HashMap Hi;

    public /* synthetic */ f(HashMap hashMap) {
        this.Hi = hashMap;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return MiCamera2.a(this.Hi, (Integer) obj, (Integer) obj2);
    }
}
