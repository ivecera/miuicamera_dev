package com.android.camera.features.mimoji2.fragment.gif;

public interface MiLibLoader {
    void loadLibrary(String str) throws UnsatisfiedLinkError, SecurityException;
}
