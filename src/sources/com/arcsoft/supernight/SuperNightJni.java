package com.arcsoft.supernight;

import android.graphics.Rect;
import com.arcsoft.supernight.SuperNightProcess;

public class SuperNightJni {

    /* renamed from: a  reason: collision with root package name */
    private SuperNightJni f270a;

    /* renamed from: b  reason: collision with root package name */
    private long f271b = 0;

    static {
        System.loadLibrary("arcsoft_supernight_jni");
        System.loadLibrary("arcsoft_sn_mtk_apu");
        System.loadLibrary("arcsoft_super_night_raw_mtk");
        System.loadLibrary("arcsoft_super_night_raw_mpbase");
    }

    private native int nativeAddOneInputInfo(long j, SuperNightProcess.RawInfo rawInfo, SuperNightProcess.InputInfo inputInfo);

    private native int nativeCancelSuperNight();

    private native long nativeInit(int i, int i2, int i3, int i4);

    private native int nativePostProcess(long j);

    private native int nativePreProcess(long j);

    private native int nativeProcess(long j, SuperNightProcess.FaceInfo faceInfo, SuperNightProcess.InputInfo inputInfo, int i, Rect rect, ProgressCallback progressCallback);

    private native int nativeProcessEx(long j, SuperNightProcess.FaceInfo faceInfo, byte[] bArr, int i, int i2, int i3, int i4, int i5, Rect rect);

    private native int nativeSetDumpImageFile(boolean z);

    private native int nativeUninit(long j);

    public synchronized int addOneInputInfo(SuperNightProcess.RawInfo rawInfo, SuperNightProcess.InputInfo inputInfo) {
        int nativeAddOneInputInfo;
        TimeConsumingUtil.startTheTimer("SN addOneInputInfo");
        nativeAddOneInputInfo = nativeAddOneInputInfo(this.f271b, rawInfo, inputInfo);
        TimeConsumingUtil.stopTiming("SN addOneInputInfo");
        return nativeAddOneInputInfo;
    }

    public void cancelSuperNight() {
        nativeCancelSuperNight();
    }

    public void init(int i, int i2, int i3, int i4) {
        TimeConsumingUtil.startTheTimer("SN init");
        this.f271b = nativeInit(i, i2, i3, i4);
        TimeConsumingUtil.stopTiming("SN init");
    }

    public int postProcess() {
        TimeConsumingUtil.startTheTimer("SN postProcess");
        int nativePostProcess = nativePostProcess(this.f271b);
        TimeConsumingUtil.stopTiming("SN postProcess");
        return nativePostProcess;
    }

    public int preProcess() {
        TimeConsumingUtil.startTheTimer("SN preProcess");
        int nativePreProcess = nativePreProcess(this.f271b);
        TimeConsumingUtil.stopTiming("SN preProcess");
        return nativePreProcess;
    }

    public int process(SuperNightProcess.FaceInfo faceInfo, SuperNightProcess.InputInfo inputInfo, int i, Rect rect, ProgressCallback progressCallback) {
        TimeConsumingUtil.startTheTimer("SN process");
        int nativeProcess = nativeProcess(this.f271b, faceInfo, inputInfo, i, rect, progressCallback);
        TimeConsumingUtil.stopTiming("SN process");
        return nativeProcess;
    }

    public int processEx(SuperNightProcess.FaceInfo faceInfo, byte[] bArr, int i, int i2, int i3, int i4, int i5, Rect rect) {
        return nativeProcessEx(this.f271b, faceInfo, bArr, i, i2, i3, i4, i5, rect);
    }

    public void setDumpImageFile(boolean z) {
        nativeSetDumpImageFile(z);
    }

    public int unInit() {
        TimeConsumingUtil.startTheTimer("SN unInit");
        int nativeUninit = nativeUninit(this.f271b);
        TimeConsumingUtil.stopTiming("SN unInit");
        return nativeUninit;
    }
}
