package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import java.io.File;

/* compiled from: HardwareConfigState */
final class t {
    private static volatile t instance = null;
    private static final int or = 128;
    private static final File pr = new File("/proc/self/fd");
    private static final int qr = 50;
    private static final int rr = 700;
    private volatile int lr;
    private volatile boolean mr = true;

    private t() {
    }

    private synchronized boolean Kn() {
        int i = this.lr + 1;
        this.lr = i;
        if (i >= 50) {
            boolean z = false;
            this.lr = 0;
            int length = pr.list().length;
            if (length < 700) {
                z = true;
            }
            this.mr = z;
            if (!this.mr && Log.isLoggable("Downsampler", 5)) {
                Log.w("Downsampler", "Excluding HARDWARE bitmap config because we're over the file descriptor limit, file descriptors " + length + ", limit " + 700);
            }
        }
        return this.mr;
    }

    static t getInstance() {
        if (instance == null) {
            synchronized (t.class) {
                if (instance == null) {
                    instance = new t();
                }
            }
        }
        return instance;
    }

    /* access modifiers changed from: package-private */
    @TargetApi(26)
    public boolean a(int i, int i2, BitmapFactory.Options options, DecodeFormat decodeFormat, boolean z, boolean z2) {
        if (!z || Build.VERSION.SDK_INT < 26 || decodeFormat == DecodeFormat.Hx || z2) {
            return false;
        }
        boolean z3 = i >= 128 && i2 >= 128 && Kn();
        if (z3) {
            options.inPreferredConfig = Bitmap.Config.HARDWARE;
            options.inMutable = false;
        }
        return z3;
    }
}
