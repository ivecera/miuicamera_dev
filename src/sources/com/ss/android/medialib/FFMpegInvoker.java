package com.ss.android.medialib;

import android.support.annotation.Keep;
import com.ss.android.ttve.nativePort.TENativeLibsLoader;

@Keep
public class FFMpegInvoker {
    static {
        TENativeLibsLoader.loadLibrary();
    }

    public native int addFastReverseVideo(String str, String str2);

    public native int stopReverseVideo();
}
