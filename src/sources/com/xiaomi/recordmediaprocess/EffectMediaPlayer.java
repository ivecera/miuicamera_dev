package com.xiaomi.recordmediaprocess;

import android.util.Log;
import android.view.Surface;
import com.xiaomi.recordplayer.enums.PlayerSeekingMode;

public class EffectMediaPlayer {
    private static String TAG = "EffectMediaPlayer";
    private MediaEffectGraph mMediaEffectGraph;
    private long mPlayer;

    public enum SurfaceGravity {
        SurfaceGravityResizeAspect(0),
        SurfaceGravityResizeAspectFit(1),
        SurfaceGravityResizeAspectFill(2);
        
        private int code;

        private SurfaceGravity(int i) {
            this.code = i;
        }

        public int toInt() {
            return this.code;
        }
    }

    public EffectMediaPlayer(MediaEffectGraph mediaEffectGraph) {
        this.mMediaEffectGraph = mediaEffectGraph;
    }

    private static native boolean ConstructMediaPlayerJni(long j);

    private static native void DestructMediaPlayerJni();

    private static native void EnableUserAdjustRotatePlayJni(boolean z);

    private static native void FlushEffectGraphJni(long j);

    private static native long GetCurrentPlayingPositionJni();

    private static native int GetPreViewStatusJni();

    private static native long GetStreamDurationJni(boolean z);

    private static native void PausePreViewJni();

    private static native boolean ResumePreViewJni();

    private static native boolean SeekToJni(long j, int i);

    private static native void SetGraphLoopJni(boolean z);

    private static native void SetGravityJni(int i, int i2, int i3);

    private static native void SetPlayLoopJni(boolean z);

    private static native void SetPlayerNotifyJni(EffectNotifier effectNotifier);

    private static native void SetViewSurfaceJni(Surface surface);

    private static native void StartPreViewJni();

    private static native void StartPreViewSourceidJni(long j);

    private static native void StopPreViewJni();

    public boolean ConstructMediaPlayer() {
        MediaEffectGraph mediaEffectGraph = this.mMediaEffectGraph;
        if (mediaEffectGraph == null) {
            Log.e(TAG, "effect graph is null, failed!");
            return false;
        }
        ConstructMediaPlayerJni(mediaEffectGraph.GetGraphLine());
        Log.d(TAG, "construct EffectPlayer");
        return true;
    }

    public void DestructMediaPlayer() {
        Log.d(TAG, "desctruct EffectPlayer");
        DestructMediaPlayerJni();
    }

    public void EnableUserAdjustRotatePlay(boolean z) {
        EnableUserAdjustRotatePlayJni(z);
    }

    public long GetCurrentPlayingPosition() {
        return GetCurrentPlayingPositionJni();
    }

    public PreViewStatus GetPreViewStatus() {
        Log.d(TAG, "GetPreViewStatus ");
        return PreViewStatus.int2enum(GetPreViewStatusJni());
    }

    public long GetStreamDuration(boolean z) {
        return GetStreamDurationJni(z);
    }

    public void PausePreView() {
        Log.d(TAG, "pause preview ");
        PausePreViewJni();
    }

    public boolean ResumePreView() {
        boolean ResumePreViewJni = ResumePreViewJni();
        String str = TAG;
        Log.d(str, "resume preview " + ResumePreViewJni);
        return ResumePreViewJni;
    }

    public boolean SeekTo(long j, PlayerSeekingMode playerSeekingMode) {
        return SeekToJni(j, playerSeekingMode.ordinal());
    }

    public void SetGraphLoop(boolean z) {
        SetGraphLoopJni(z);
    }

    public void SetMediaEffectGraph(MediaEffectGraph mediaEffectGraph) {
        this.mMediaEffectGraph = mediaEffectGraph;
        FlushEffectGraphJni(this.mMediaEffectGraph.GetGraphLine());
    }

    public void SetPlayLoop(boolean z) {
        SetPlayLoopJni(z);
    }

    public void SetPlayerNotify(EffectNotifier effectNotifier) {
        String str = TAG;
        Log.d(str, "SetPlayerNotify " + effectNotifier);
        SetPlayerNotifyJni(effectNotifier);
    }

    public void SetViewSurface(Surface surface) {
        Log.d(TAG, "set view surface ");
        SetViewSurfaceJni(surface);
    }

    public void StartPreView() {
        Log.d(TAG, "start preview ");
        StartPreViewJni();
    }

    public void StartPreView(long j) {
        String str = TAG;
        Log.d(str, "start preview " + j);
        StartPreViewSourceidJni(j);
    }

    public void StopPreView() {
        Log.d(TAG, "stop preview ");
        StopPreViewJni();
    }

    public void setGravity(SurfaceGravity surfaceGravity, int i, int i2) {
        SetGravityJni(surfaceGravity.toInt(), i, i2);
    }
}
