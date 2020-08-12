package com.android.camera.fragment.music;

import com.android.camera.resource.tmmusic.TMMusicCatrgoryRequest;
import com.android.camera.resource.tmmusic.TMMusicList;
import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class c implements Function {
    public static final /* synthetic */ c INSTANCE = new c();

    private /* synthetic */ c() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return new TMMusicCatrgoryRequest((TMMusicList) obj).startObservable(TMMusicList.class);
    }
}
