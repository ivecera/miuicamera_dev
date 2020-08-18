package com.ss.android.ugc.effectmanager.common;

import android.os.Looper;

public final class Preconditions {
    private Preconditions() {
    }

    public static void checkState(boolean z) {
        if (!z) {
            throw new IllegalStateException();
        }
    }

    public static void checkUiThread() {
        Thread currentThread = Thread.currentThread();
        if (Looper.getMainLooper().getThread() != currentThread) {
            throw new IllegalStateException("Method cannot be called off the main application thread (on: " + currentThread.getName() + ")");
        }
    }
}
