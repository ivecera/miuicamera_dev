package com.android.camera.fragment.top;

import com.android.camera.statistic.CameraStatUtils;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    public static final /* synthetic */ g INSTANCE = new g();

    private /* synthetic */ g() {
    }

    public final void run() {
        CameraStatUtils.trackLyingDirectShow(0);
    }
}
