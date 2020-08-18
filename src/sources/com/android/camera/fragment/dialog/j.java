package com.android.camera.fragment.dialog;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/* compiled from: lambda */
public final /* synthetic */ class j implements ObservableOnSubscribe {
    private final /* synthetic */ FragmentLiveReview Hi;

    public /* synthetic */ j(FragmentLiveReview fragmentLiveReview) {
        this.Hi = fragmentLiveReview;
    }

    @Override // io.reactivex.ObservableOnSubscribe
    public final void subscribe(ObservableEmitter observableEmitter) {
        this.Hi.b(observableEmitter);
    }
}
