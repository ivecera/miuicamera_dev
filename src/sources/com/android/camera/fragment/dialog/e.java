package com.android.camera.fragment.dialog;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class e implements Consumer {
    private final /* synthetic */ FragmentLiveReview Hi;

    public /* synthetic */ e(FragmentLiveReview fragmentLiveReview) {
        this.Hi = fragmentLiveReview;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.Hi.c((Integer) obj);
    }
}
