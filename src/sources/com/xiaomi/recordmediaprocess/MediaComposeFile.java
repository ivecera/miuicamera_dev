package com.xiaomi.recordmediaprocess;

import android.util.Log;

public class MediaComposeFile {
    private static String TAG = "MediaComposeFile";
    private long m_compose_file;
    private MediaEffectGraph m_media_graph;

    public MediaComposeFile(MediaEffectGraph mediaEffectGraph) {
        this.m_media_graph = mediaEffectGraph;
    }

    private static native void BeginComposeJni();

    private static native void CancelComposeJni();

    private static native boolean ConstructMediaComposeFileJni(long j, int i, int i2, int i3, int i4, EffectNotifier effectNotifier);

    private static native void DestructMediaComposeFileJni();

    private static native void SetComposeFileNameJni(String str);

    public void BeginCompose() {
        String str = TAG;
        Log.d(str, "begin mComposefile:" + this.m_compose_file);
        BeginComposeJni();
    }

    public void CancelCompose() {
        String str = TAG;
        Log.d(str, "cancel mComposefile:" + this.m_compose_file);
        CancelComposeJni();
    }

    public boolean ConstructMediaComposeFile(int i, int i2, int i3, int i4, EffectNotifier effectNotifier) {
        MediaEffectGraph mediaEffectGraph = this.m_media_graph;
        if (mediaEffectGraph == null) {
            Log.e(TAG, "effect graph is null, failed!");
            return false;
        }
        ConstructMediaComposeFileJni(mediaEffectGraph.GetGraphLine(), i, i2, i3, i4, effectNotifier);
        String str = TAG;
        Log.d(str, "construct compose file: " + this.m_compose_file);
        return true;
    }

    public void DestructMediaComposeFile() {
        String str = TAG;
        Log.d(str, "destruct mComposefile:" + this.m_compose_file);
        DestructMediaComposeFileJni();
    }

    public void SetComposeFileName(String str) {
        String str2 = TAG;
        Log.d(str2, "SetComposeFileName " + str);
        SetComposeFileNameJni(str);
    }

    public void SetMediaEffectGraph(MediaEffectGraph mediaEffectGraph) {
        this.m_media_graph = mediaEffectGraph;
    }
}
