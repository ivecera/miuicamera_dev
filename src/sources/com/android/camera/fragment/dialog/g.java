package com.android.camera.fragment.dialog;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class g implements Consumer {
    private final /* synthetic */ FragmentLiveReview Hi;

    public /* synthetic */ g(FragmentLiveReview fragmentLiveReview) {
        this.Hi = fragmentLiveReview;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.Hi.d((Integer) obj);
    }
}
