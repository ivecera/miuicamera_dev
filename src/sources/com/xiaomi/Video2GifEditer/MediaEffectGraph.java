package com.xiaomi.Video2GifEditer;

import android.util.Log;
import java.util.Map;

public class MediaEffectGraph {
    private static String TAG = "MediaEffectGraph";
    private long mGraphLine = 0;

    public MediaEffectGraph() {
        Log.d(TAG, " ");
    }

    private static native long AddAudioTrackJni(String str, boolean z);

    private static native boolean AddEffectJni(long j, long j2, boolean z);

    private static native void AddSourceAndEffectByTemplateJni(String[] strArr, String str);

    private static native boolean AddTransitionEffectJni(long j, long j2, long j3);

    private static native boolean AddVideoBackGroudJni(String str);

    private static native long AddVideoSourceJni(String str, boolean z);

    private static native long ConstructMediaEffectGraphJni();

    private static native void DestructMediaEffectGraphJni();

    private static native boolean RemoveAudioTrackJni(long j);

    private static native boolean RemoveEffectJni(long j, long j2, boolean z);

    private static native boolean RemoveTransitionEffectJni(long j, long j2, long j3);

    private static native boolean RemoveVideoBackGroudJni();

    private static native boolean RemoveVideoSourceJni(long j);

    private static native void SetAudioMuteJni(boolean z);

    private static native void SetCurrentSourceJni(long j);

    private static native boolean SetParamsForVideoSourceJni(long j, String[] strArr);

    private static native boolean SwapVideoSourceJni(long j, long j2);

    public long AddAudioTrack(String str, boolean z) {
        String str2 = TAG;
        Log.d(str2, "add video source: " + str + " play loop: " + z);
        if (this.mGraphLine == 0) {
            String str3 = TAG;
            Log.e(str3, "GraphLine is null, failed add audio source: " + str);
            return 0;
        }
        long AddAudioTrackJni = AddAudioTrackJni(str, z);
        String str4 = TAG;
        Log.d(str4, "add audio source: " + AddAudioTrackJni + "--" + str);
        return AddAudioTrackJni;
    }

    public boolean AddEffect(long j, long j2, long j3) {
        if (this.mGraphLine == 0) {
            Log.e(TAG, "GraphLine is null, add effect failed! ");
            return false;
        }
        String str = TAG;
        Log.d(str, "add effect: " + j + ", " + j2 + ", " + j3);
        return AddTransitionEffectJni(j, j2, j3);
    }

    public boolean AddEffect(long j, long j2, boolean z) {
        if (this.mGraphLine == 0) {
            Log.e(TAG, "GraphLine is null, add effect failed!");
            return false;
        }
        String str = TAG;
        Log.d(str, "add effect: " + j + ", " + j2);
        return AddEffectJni(j, j2, z);
    }

    public void AddSourceAndEffectByTemplate(String[] strArr, String str) {
        AddSourceAndEffectByTemplateJni(strArr, str);
    }

    public boolean AddVideoBackGroud(String str) {
        String str2 = TAG;
        Log.d(str2, "add video backgroud: " + str);
        if (this.mGraphLine != 0) {
            return AddVideoBackGroudJni(str);
        }
        String str3 = TAG;
        Log.e(str3, "GraphLine is null, failed add video backgroud: " + str);
        return false;
    }

    public long AddVideoSource(String str, boolean z) {
        String str2 = TAG;
        Log.d(str2, "add video source: " + str);
        if (this.mGraphLine == 0) {
            String str3 = TAG;
            Log.e(str3, "GraphLine is null, failed add video source: " + str);
            return 0;
        }
        long AddVideoSourceJni = AddVideoSourceJni(str, z);
        String str4 = TAG;
        Log.d(str4, "add video source: " + AddVideoSourceJni + "--" + str);
        return AddVideoSourceJni;
    }

    public void ConstructMediaEffectGraph() {
        this.mGraphLine = ConstructMediaEffectGraphJni();
        Log.d(TAG, "Construct: ");
    }

    public void DestructMediaEffectGraph() {
        Log.d(TAG, "Destruct");
        this.mGraphLine = 0;
        DestructMediaEffectGraphJni();
    }

    public long GetGraphLine() {
        return this.mGraphLine;
    }

    public boolean RemoveAudioTrack(long j) {
        if (this.mGraphLine == 0) {
            Log.e(TAG, "GraphLine is null , failed remove video source!");
            return false;
        }
        String str = TAG;
        Log.d(str, "remove video source: " + j);
        return RemoveAudioTrackJni(j);
    }

    public boolean RemoveEffect(long j, long j2, long j3) {
        if (this.mGraphLine == 0) {
            Log.e(TAG, "GraphLine is null, remove effect failed!");
            return false;
        }
        String str = TAG;
        Log.d(str, "remove effect: " + j + ", " + j2 + ", " + j3);
        return RemoveTransitionEffectJni(j, j2, j3);
    }

    public boolean RemoveEffect(long j, long j2, boolean z) {
        if (this.mGraphLine == 0) {
            Log.e(TAG, "GraphLine is null, remove effect failed!");
            return false;
        }
        String str = TAG;
        Log.d(str, "remove effect: " + j + ", " + j2);
        return RemoveEffectJni(j, j2, z);
    }

    public boolean RemoveVideoBackGroud() {
        Log.d(TAG, "remove video backgroud");
        if (this.mGraphLine != 0) {
            return RemoveVideoBackGroudJni();
        }
        Log.e(TAG, "GraphLine is null , failed remove video backgroud!");
        return false;
    }

    public boolean RemoveVideoSource(long j) {
        if (this.mGraphLine == 0) {
            Log.e(TAG, "GraphLine is null , failed remove video source!");
            return false;
        }
        String str = TAG;
        Log.d(str, "remove video source: " + j);
        return RemoveVideoSourceJni(j);
    }

    public void SetAudioMute(boolean z) {
        String str = TAG;
        Log.d(str, "SetAudioMute: " + z);
        SetAudioMuteJni(z);
    }

    public void SetCurrentSource(long j) {
        String str = TAG;
        Log.d(str, "set current source id: " + j);
        SetCurrentSourceJni(j);
    }

    public boolean SetParamsForVideoSource(long j, Map<String, String> map) {
        String[] strArr;
        String str = TAG;
        Log.d(str, "set param for source, source id: " + j);
        int i = 0;
        if (map == null || map.size() == 0) {
            Log.d(TAG, "Param Map: <null, null>");
            strArr = new String[0];
        } else {
            strArr = new String[(map.size() * 2)];
            for (String str2 : map.keySet()) {
                String str3 = TAG;
                Log.d(str3, "Param Map: <" + str2 + ", " + map.get(str2) + ">");
                int i2 = i * 2;
                strArr[i2] = str2.toLowerCase();
                strArr[i2 + 1] = map.get(str2);
                i++;
            }
        }
        return SetParamsForVideoSourceJni(j, strArr);
    }

    public boolean SwapVideoSource(long j, long j2) {
        if (this.mGraphLine == 0) {
            Log.e(TAG, "GraphLine is null , failed Swap video source!");
            return false;
        }
        String str = TAG;
        Log.d(str, "Swap video source: " + j + ", " + j2);
        return SwapVideoSourceJni(j, j2);
    }
}
