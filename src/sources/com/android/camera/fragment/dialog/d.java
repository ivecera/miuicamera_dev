package com.android.camera.fragment.dialog;

import com.android.camera.fragment.fullscreen.ShareInfo;
import java.util.Comparator;

/* compiled from: lambda */
public final /* synthetic */ class d implements Comparator {
    public static final /* synthetic */ d INSTANCE = new d();

    private /* synthetic */ d() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Integer.compare(((ShareInfo) obj).index, ((ShareInfo) obj2).index);
    }
}
