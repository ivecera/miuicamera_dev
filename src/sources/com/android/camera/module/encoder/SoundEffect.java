package com.android.camera.module.encoder;

import com.android.camera.log.Log;

public class SoundEffect {
    private static String TAG = "SoundEffect";
    private long handle = 0;

    static {
        try {
            System.loadLibrary("camera_sound_effect");
        } catch (Throwable th) {
            Log.e(TAG, "load libcamera_sound_effect.so failed.", th);
        }
    }

    public SoundEffect(int i, int i2) {
        this.handle = newInstance(i, i2);
    }

    private static final native void flush(long j);

    private static final native long newInstance(int i, int i2);

    private static final native void putSamples(long j, short[] sArr);

    private static final native short[] receiveSamples(long j, int i);

    private static final native void release(long j);

    private static final native void setTempo(long j, float f2);

    public void flush() {
        flush(this.handle);
    }

    public void putSamples(short[] sArr) {
        putSamples(this.handle, sArr);
    }

    public short[] receiveSamples(int i) {
        return receiveSamples(this.handle, i);
    }

    public void release() {
        release(this.handle);
        this.handle = 0;
    }

    public void setTempo(float f2) {
        setTempo(this.handle, f2);
    }
}
