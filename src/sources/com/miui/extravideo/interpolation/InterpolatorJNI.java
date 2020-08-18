package com.miui.extravideo.interpolation;

import android.util.Log;
import java.nio.ByteBuffer;
import java.util.ArrayList;

class InterpolatorJNI {
    public static final int RGBA8888 = 2;
    public static final int YUV420SP = 0;
    public static final int YVU420SP = 1;
    private final int MOTION_LIST_MAX_NUM = 30;
    private String failureCode;
    private long handler = 0;
    private TypicalMotion motionAve = new TypicalMotion();
    private ArrayList<TypicalMotion> motionList = new ArrayList<>();

    public static class Params {
        public int accuracy;
        public int mult;
    }

    private class TypicalMotion {
        public double x;
        public double y;

        public TypicalMotion() {
            this.x = 0.0d;
            this.y = 0.0d;
        }

        public TypicalMotion(double d2, double d3) {
            this.x = d2;
            this.y = d3;
        }
    }

    static {
        System.loadLibrary("video_extra_interpolator");
    }

    InterpolatorJNI() {
    }

    private final native int finishJNI(long j);

    private final native int getDefaultParamsJNI(long j, Params params);

    private final native ByteBuffer getImageBufferJNI(long j, int i, int i2);

    private final native int getImageIndexJNI(long j, int[] iArr);

    public static final native String getVersion();

    private final native long initializeJNI(int i, int i2, int i3, int i4, int i5);

    private final native int processJNI(long j, byte[] bArr, int i, int i2, int[] iArr, double[] dArr, boolean z);

    private final native int setDividePositionJNI(long j, int i);

    private final native int startJNI(long j);

    public void clearMotion() {
        TypicalMotion typicalMotion = this.motionAve;
        typicalMotion.y = 0.0d;
        typicalMotion.x = 0.0d;
        this.motionList.clear();
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        long j = this.handler;
        if (j != 0) {
            finishJNI(j);
        }
    }

    public void finish() {
        long j = this.handler;
        if (j != 0) {
            this.handler = 0;
            finishJNI(j);
        }
    }

    public int getDefaultParams(Params params) {
        return getDefaultParamsJNI(0, params);
    }

    public String getFailureCode() {
        return this.failureCode;
    }

    public ByteBuffer getImageBuffer(int i, int i2) {
        long j = this.handler;
        if (j != 0) {
            return getImageBufferJNI(j, i, i2);
        }
        return null;
    }

    public int getImageIndex(int[] iArr) {
        long j = this.handler;
        if (j != 0) {
            return getImageIndexJNI(j, iArr);
        }
        return -1;
    }

    public double getMotionX() {
        return this.motionAve.x;
    }

    public double getMotionY() {
        return this.motionAve.y;
    }

    public int initialize(int i, int i2, int i3, int i4, int i5) {
        this.handler = initializeJNI(i, i2, i3, i4, i5);
        int i6 = this.handler == 0 ? Integer.MIN_VALUE : 0;
        this.failureCode = "";
        return i6;
    }

    public int process(byte[] bArr, int i, int i2, boolean z) {
        if (this.handler == 0) {
            return -2147483646;
        }
        int[] iArr = new int[1];
        long nanoTime = System.nanoTime();
        int processJNI = processJNI(this.handler, bArr, i, i2, iArr, new double[2], z) | 0;
        Log.i("FN_FF", "processJNI diff = " + (((double) (System.nanoTime() - nanoTime)) / 1000000.0d) + ", skip_engine_process=" + z + ", index=" + i);
        int i3 = iArr[0];
        if (i3 == 1) {
            this.failureCode = "Local motion";
            return processJNI;
        } else if (i3 == 2) {
            this.failureCode = "Global motion";
            return processJNI;
        } else if (i3 != 4) {
            this.failureCode = "";
            return processJNI;
        } else {
            this.failureCode = "Scene change";
            return processJNI;
        }
    }

    public int setDividePosition(int i) {
        long j = this.handler;
        if (j != 0) {
            return setDividePositionJNI(j, i);
        }
        return -2147483646;
    }

    public int start() {
        if (this.handler == 0) {
            return -2147483646;
        }
        clearMotion();
        return startJNI(this.handler);
    }
}
