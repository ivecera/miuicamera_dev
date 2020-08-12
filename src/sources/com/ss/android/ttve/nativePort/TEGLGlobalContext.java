package com.ss.android.ttve.nativePort;

public class TEGLGlobalContext {
    static {
        TENativeLibsLoader.loadLibrary();
    }

    public static native void nativeSetGLVersion(int i);
}
