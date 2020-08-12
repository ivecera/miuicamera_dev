package com.xiaomi.Video2GifEditer;

import android.util.Log;

public class MediaComposeFile {
    private static String TAG = "MediaComposeFile";
    private long mComposeFile;
    private MediaEffectGraph mMediaEffectGraph;

    public MediaComposeFile(MediaEffectGraph mediaEffectGraph) {
        this.mMediaEffectGraph = mediaEffectGraph;
    }

    private static native void BeginComposeFileJni();

    private static native void CancelComposeFileJni();

    private static native boolean ConstructMediaComposeFileJni(long j, int i, int i2, int i3, int i4);

    private static native void DestructMediaComposeFileJni();

    private static native void SetComposeFileNameJni(String str);

    private static native void SetComposeNotifyJni(EffectNotifier effectNotifier);

    public void BeginComposeFile() {
        String str = TAG;
        Log.d(str, "begin mComposefile:" + this.mComposeFile);
        BeginComposeFileJni();
    }

    public void CancelComposeFile() {
        String str = TAG;
        Log.d(str, "cancel mComposefile:" + this.mComposeFile);
        CancelComposeFileJni();
    }

    public boolean ConstructMediaComposeFile(int i, int i2, int i3, int i4) {
        MediaEffectGraph mediaEffectGraph = this.mMediaEffectGraph;
        if (mediaEffectGraph == null) {
            Log.e(TAG, "effect graph is null, failed!");
            return false;
        }
        ConstructMediaComposeFileJni(mediaEffectGraph.GetGraphLine(), i, i2, i3, i4);
        String str = TAG;
        Log.d(str, "construct compose file: " + this.mComposeFile);
        return true;
    }

    public void DestructMediaComposeFile() {
        String str = TAG;
        Log.d(str, "destruct mComposefile:" + this.mComposeFile);
        DestructMediaComposeFileJni();
    }

    public void SetComposeFileName(String str) {
        String str2 = TAG;
        Log.d(str2, "SetComposeFileName " + str);
        SetComposeFileNameJni(str);
    }

    public void SetComposeNotify(EffectNotifier effectNotifier) {
        String str = TAG;
        Log.d(str, "SetComposeNotify mComposefile:" + this.mComposeFile);
        SetComposeNotifyJni(effectNotifier);
    }

    public void SetMediaEffectGraph(MediaEffectGraph mediaEffectGraph) {
        this.mMediaEffectGraph = mediaEffectGraph;
    }
}
