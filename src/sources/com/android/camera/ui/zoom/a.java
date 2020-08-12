package com.android.camera.ui.zoom;

import android.animation.AnimatorSet;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class a implements Consumer {
    public static final /* synthetic */ a INSTANCE = new a();

    private /* synthetic */ a() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ZoomRatioToggleView.a((AnimatorSet) obj);
    }
}
