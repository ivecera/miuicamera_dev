package com.arcsoft.camera.wideselfie;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;

public class WideSelfieEngine {
    public static final int HORIZ_FLIP = 12289;
    public static final int MPAF_16BITS = 83886080;
    public static final int MPAF_32BITS = 117440512;
    public static final int MPAF_BGR = 4096;
    public static final int MPAF_OTHERS_NV21 = 1879048194;
    public static final int MPAF_RGB16_R5G6B5 = (MPAF_MAKE_B(5) | ((MPAF_MAKE_R(5) | 352321536) | MPAF_MAKE_G(6)));
    public static final int MPAF_RGB32_A8R8G8B8 = (MPAF_MAKE_B(8) | ((MPAF_MAKE_R(8) | 922746880) | MPAF_MAKE_G(8)));
    public static final int MPAF_RGB32_B8G8R8A8 = (MPAF_RGB32_A8R8G8B8 | 4096);
    public static final int MPAF_RGBA_BASE = 805306368;
    public static final int MPAF_RGB_BASE = 268435456;
    public static final int VERT_FLIP = 12290;

    /* renamed from: a  reason: collision with root package name */
    private static final String f246a = "com.arcsoft.camera.wideselfie.WideSelfieEngine";

    /* renamed from: b  reason: collision with root package name */
    private static WideSelfieEngine f247b;

    /* renamed from: c  reason: collision with root package name */
    private AwsInitParameter f248c = null;

    /* renamed from: d  reason: collision with root package name */
    private WideSelfieCallback f249d = null;

    /* renamed from: e  reason: collision with root package name */
    private boolean f250e = false;

    private static final class a {

        /* renamed from: a  reason: collision with root package name */
        int f251a;

        /* renamed from: b  reason: collision with root package name */
        int f252b;

        /* renamed from: c  reason: collision with root package name */
        int f253c;

        /* renamed from: d  reason: collision with root package name */
        int f254d;

        /* renamed from: e  reason: collision with root package name */
        String f255e;

        /* renamed from: f  reason: collision with root package name */
        String f256f;
        String g;

        private a() {
        }
    }

    static {
        System.loadLibrary("camera_wideselfie_mpbase");
        System.loadLibrary("arcsoft_wideselfie");
        System.loadLibrary("jni_wideselfie");
    }

    private native byte[] ConvertCS(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6);

    private native Bitmap ConvertCS2Bitmap(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z);

    private native byte[] ConvertCSEx(ByteBuffer[] byteBufferArr, int i, int i2, int i3, int i4, int i5, int i6);

    private native byte[] ConvertCSExx(long j, int i, int i2, int i3);

    public static final int MPAF_MAKE_B(int i) {
        return (i - 1) << 0;
    }

    public static final int MPAF_MAKE_G(int i) {
        return (i - 1) << 4;
    }

    public static final int MPAF_MAKE_R(int i) {
        return (i - 1) << 8;
    }

    private native void SetLogLevel(int i);

    private a a() {
        return native_version();
    }

    private int b() {
        return native_capture();
    }

    private WideSelfieCallback c() {
        return this.f249d;
    }

    public static synchronized WideSelfieEngine getSingleInstance() {
        WideSelfieEngine wideSelfieEngine;
        synchronized (WideSelfieEngine.class) {
            if (f247b == null) {
                synchronized (WideSelfieEngine.class) {
                    if (f247b == null) {
                        f247b = new WideSelfieEngine();
                    }
                }
            }
            wideSelfieEngine = f247b;
        }
        return wideSelfieEngine;
    }

    private native int native_capture();

    private native int native_init(int i, int i2, float f2, float f3, float f4, float f5, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, float f6, int i13, int i14, boolean z);

    private native int native_init_default(int i, int i2);

    private native int native_process(int i, byte[] bArr);

    private native int native_process(int i, byte[] bArr, boolean z, int i2);

    private native int native_push_sensor_data_in(float[] fArr, long j, int i);

    private native void native_register_callback(WideSelfieCallback wideSelfieCallback);

    private native int native_uninit();

    private native a native_version();

    public synchronized byte[] convertCS(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6) {
        return ConvertCS(bArr, i, i2, i3, i4, i5, i6);
    }

    public synchronized Bitmap convertCS2Bitmap(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        return ConvertCS2Bitmap(bArr, i, i2, i3, i4, i5, i6, i7, i8, z);
    }

    public synchronized byte[] convertCSEx(ByteBuffer[] byteBufferArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        return ConvertCSEx(byteBufferArr, i, i2, i3, i4, i5, i6);
    }

    public synchronized byte[] convertCSExx(long j, int i, int i2, int i3) {
        return ConvertCSExx(j, i, i2, i3);
    }

    public synchronized int init(AwsInitParameter awsInitParameter) {
        int native_init;
        try {
            boolean z = false;
            if (this.f250e) {
                return 0;
            }
            if (awsInitParameter == null) {
                return 2;
            }
            this.f248c = awsInitParameter;
            try {
                native_init = native_init(this.f248c.getBufferSize(), this.f248c.mode, this.f248c.cameraViewAngleForWidth, this.f248c.cameraViewAngleForHeight, this.f248c.resultAngleForWidth, this.f248c.resultAngleForHeight, this.f248c.getSrcFormat(), this.f248c.getFullImageWidth(), this.f248c.getFullImageHeight(), this.f248c.getThumbForamt(), this.f248c.getThumbnailWidth(), this.f248c.getThumbnailHeight(), this.f248c.maxResultWidth, this.f248c.progressBarThumbHeight, this.f248c.guideStopBarThumbHeight, this.f248c.guideStableBarThumbHeight, this.f248c.progressBarThumbHeightCropRatio, this.f248c.changeDirectionThumbThreshold, this.f248c.getDeviceOrientation(), this.f248c.convertNV21);
                if (native_init == 0) {
                    z = true;
                }
            } catch (Throwable th) {
                th = th;
                throw th;
            }
            try {
                this.f250e = z;
                return native_init;
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            throw th;
        }
    }

    public synchronized int process(int i, byte[] bArr) {
        return bArr != null ? native_process(i, bArr) : 2;
    }

    public synchronized int process(int i, byte[] bArr, boolean z, int i2) {
        return bArr != null ? native_process(i, bArr, z, i2) : 2;
    }

    public synchronized void setOnCallback(WideSelfieCallback wideSelfieCallback) {
        this.f249d = wideSelfieCallback;
        if (this.f249d != null) {
            native_register_callback(this.f249d);
        }
    }

    public void setSensorData(float[] fArr, long j, int i) {
        if (this.f250e) {
            native_push_sensor_data_in(fArr, j, i);
        }
    }

    public synchronized int uninit() {
        boolean z = false;
        if (!this.f250e) {
            return 0;
        }
        int native_uninit = native_uninit();
        if (native_uninit != 0) {
            z = true;
        }
        this.f250e = z;
        return native_uninit;
    }
}
