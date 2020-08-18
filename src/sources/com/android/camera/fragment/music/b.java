package com.android.camera.fragment.music;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class b implements Consumer {
    private final /* synthetic */ FragmentLiveMusicPager Hi;

    public /* synthetic */ b(FragmentLiveMusicPager fragmentLiveMusicPager) {
        this.Hi = fragmentLiveMusicPager;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.Hi.d((Throwable) obj);
    }
}
