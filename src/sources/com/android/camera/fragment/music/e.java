package com.android.camera.fragment.music;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class e implements Consumer {
    private final /* synthetic */ FragmentLiveMusicPager Hi;

    public /* synthetic */ e(FragmentLiveMusicPager fragmentLiveMusicPager) {
        this.Hi = fragmentLiveMusicPager;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.Hi.e((Throwable) obj);
    }
}
