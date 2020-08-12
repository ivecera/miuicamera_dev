package com.android.camera.fragment.vv;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class b implements Consumer {
    private final /* synthetic */ FragmentVVGallery Hi;

    public /* synthetic */ b(FragmentVVGallery fragmentVVGallery) {
        this.Hi = fragmentVVGallery;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.Hi.a((VVList) obj);
    }
}
