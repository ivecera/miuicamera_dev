package com.android.camera.module.impl.component;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.text.TextUtils;
import android.view.Surface;
import com.android.camera.log.Log;
import com.android.camera.module.MiLiveModule;
import com.android.camera.module.impl.component.ILive;
import com.xiaomi.recordmediaprocess.EffectMediaPlayer;
import com.xiaomi.recordmediaprocess.EffectNotifier;
import com.xiaomi.recordmediaprocess.MediaComposeFile;
import com.xiaomi.recordmediaprocess.MediaEffectGraph;
import java.util.Arrays;
import java.util.List;

public class MiLivePlayer implements ILive.ILivePlayer {
    private static final int DEFAULT_FPS = 30;
    private static final int DEFAULT_TARGET_BITRATE = 31457280;
    /* access modifiers changed from: private */
    public static final String TAG = "MiLivePlayer";
    private String mAudioPath;
    private EffectNotifier mComposeNotifier = new EffectNotifier() {
        /* class com.android.camera.module.impl.component.MiLivePlayer.AnonymousClass2 */

        @Override // com.xiaomi.recordmediaprocess.EffectNotifier
        public void OnReceiveFailed() {
            Log.d(MiLivePlayer.TAG, "Compose notifier OnReceiveFailed");
            MiLivePlayer.this.setComposeState(-1);
            MiLivePlayer.this.setComposeState(1);
        }

        @Override // com.xiaomi.recordmediaprocess.EffectNotifier
        public void OnReceiveFinish() {
            Log.d(MiLivePlayer.TAG, "Compose notifier OnReceiveFinish");
            MiLivePlayer.this.setComposeState(3);
        }
    };
    private int mComposeState = 0;
    private EffectMediaPlayer mEffectMediaPlayer;
    private MediaComposeFile mMediaComposeFile;
    private MediaEffectGraph mMediaEffectGraph;
    private EffectNotifier mPlayerNotifier = new EffectNotifier() {
        /* class com.android.camera.module.impl.component.MiLivePlayer.AnonymousClass1 */

        @Override // com.xiaomi.recordmediaprocess.EffectNotifier
        public void OnReceiveFailed() {
            Log.d(MiLivePlayer.TAG, "player notifier OnReceiveFailed");
            MiLivePlayer.this.setPlayerState(-1);
        }

        @Override // com.xiaomi.recordmediaprocess.EffectNotifier
        public void OnReceiveFinish() {
            Log.d(MiLivePlayer.TAG, "player notifier OnReceiveFinish");
        }
    };
    private int mPlayerState = 0;
    private int mPreviewHeight;
    private int mPreviewWidth;
    private List<ILive.ILiveSegmentData> mSegmentData;
    private ILive.ILivePlayerStateListener mStateListener;
    private int mVideoHeight;
    private int mVideoWidth;

    public MiLivePlayer(Activity activity) {
        MiLiveModule.loadLibs(activity.getApplicationContext(), MiLiveModule.LIB_LOAD_CALLER_PLAYER);
    }

    private String getComposeStateString(int i) {
        return i != -1 ? i != 0 ? i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "COMPOSED" : "COMPOSING" : "PREPARE" : "IDLE" : "ERROR";
    }

    private String getPlayerStateString(int i) {
        return i != -1 ? i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "UNKNOWN" : "PAUSE" : "PREVIEWING" : "PENDING_START" : "PREPARE" : "IDLE" : "ERROR";
    }

    private void initEffectGraph() {
        List<ILive.ILiveSegmentData> list = this.mSegmentData;
        if (list != null && list.size() > 0) {
            MediaEffectGraph mediaEffectGraph = this.mMediaEffectGraph;
            if (mediaEffectGraph != null) {
                mediaEffectGraph.DestructMediaEffectGraph();
            }
            String str = TAG;
            Log.d(str, "initEffectGraph mSegmentData = " + Arrays.toString(this.mSegmentData.toArray()));
            String[] strArr = new String[this.mSegmentData.size()];
            float[] fArr = new float[this.mSegmentData.size()];
            for (int i = 0; i < this.mSegmentData.size(); i++) {
                strArr[i] = this.mSegmentData.get(i).getPath();
                fArr[i] = this.mSegmentData.get(i).getSpeed();
            }
            this.mMediaEffectGraph = new MediaEffectGraph();
            this.mMediaEffectGraph.ConstructMediaEffectGraph();
            this.mMediaEffectGraph.AddSourcesAndEffectBySourcesPath(strArr, fArr);
            if (!TextUtils.isEmpty(this.mAudioPath)) {
                this.mMediaEffectGraph.SetAudioMute(true);
                this.mMediaEffectGraph.AddAudioTrack(this.mAudioPath, false);
            }
        }
    }

    private void initMediaCompose() {
        if (this.mVideoHeight <= 0 || this.mVideoWidth <= 0) {
            Log.e(TAG, "invalid video size.");
            return;
        }
        if (this.mMediaComposeFile != null) {
            releaseComposeFile();
        }
        initEffectGraph();
        this.mMediaComposeFile = new MediaComposeFile(this.mMediaEffectGraph);
        this.mMediaComposeFile.ConstructMediaComposeFile(this.mVideoWidth, this.mVideoHeight, DEFAULT_TARGET_BITRATE, 30, this.mComposeNotifier);
    }

    private void initMediaPlayer(int i, int i2) {
        if (this.mVideoHeight <= 0 || this.mVideoWidth <= 0 || i <= 0 || i2 <= 0) {
            Log.e(TAG, "invalid video size.");
            return;
        }
        if (this.mEffectMediaPlayer != null) {
            releasePlayer();
        }
        initEffectGraph();
        this.mEffectMediaPlayer = new EffectMediaPlayer(this.mMediaEffectGraph);
        this.mEffectMediaPlayer.ConstructMediaPlayer();
        this.mEffectMediaPlayer.SetPlayerNotify(this.mPlayerNotifier);
        this.mEffectMediaPlayer.SetPlayLoop(true);
        this.mEffectMediaPlayer.setGravity(EffectMediaPlayer.SurfaceGravity.SurfaceGravityResizeAspectFit, i, i2);
        this.mEffectMediaPlayer.SetGraphLoop(true);
        this.mEffectMediaPlayer.EnableUserAdjustRotatePlay(true);
        Log.d(TAG, "initMediaPlayer");
    }

    private void releaseComposeFile() {
        MediaComposeFile mediaComposeFile = this.mMediaComposeFile;
        if (mediaComposeFile != null) {
            mediaComposeFile.DestructMediaComposeFile();
            this.mMediaComposeFile = null;
        }
    }

    private void releaseEffectGraph() {
        MediaEffectGraph mediaEffectGraph = this.mMediaEffectGraph;
        if (mediaEffectGraph != null) {
            mediaEffectGraph.DestructMediaEffectGraph();
            this.mMediaEffectGraph = null;
        }
    }

    private void releasePlayer() {
        EffectMediaPlayer effectMediaPlayer = this.mEffectMediaPlayer;
        if (effectMediaPlayer != null) {
            effectMediaPlayer.DestructMediaPlayer();
            this.mEffectMediaPlayer = null;
        }
    }

    /* access modifiers changed from: private */
    public void setComposeState(int i) {
        if (this.mComposeState != i) {
            String str = TAG;
            Log.d(str, "ComposeState state change from " + getComposeStateString(this.mComposeState) + " to " + getComposeStateString(i));
            this.mComposeState = i;
            ILive.ILivePlayerStateListener iLivePlayerStateListener = this.mStateListener;
            if (iLivePlayerStateListener != null) {
                iLivePlayerStateListener.onComposeStateChange(this.mComposeState);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setPlayerState(int i) {
        if (this.mPlayerState != i) {
            String str = TAG;
            Log.d(str, "Player state change from " + getPlayerStateString(this.mPlayerState) + " to " + getPlayerStateString(i));
            this.mPlayerState = i;
            ILive.ILivePlayerStateListener iLivePlayerStateListener = this.mStateListener;
            if (iLivePlayerStateListener != null) {
                iLivePlayerStateListener.onPlayStateChange(this.mPlayerState);
            }
        }
    }

    @Override // com.android.camera.module.impl.component.ILive.ILivePlayer
    public void cancelCompose() {
        if (this.mComposeState == 2) {
            this.mMediaComposeFile.CancelCompose();
        }
    }

    @Override // com.android.camera.module.impl.component.ILive.ILivePlayer
    public void init(int i, int i2, int i3, int i4, List<ILive.ILiveSegmentData> list, String str) {
        String str2 = TAG;
        Log.d(str2, "init video size = " + i + "x" + i2 + ", preview size = " + i3 + "x" + i4 + ", data = " + Arrays.toString(list.toArray()) + ", audioPath = " + str);
        this.mVideoWidth = Math.max(i, i2);
        this.mVideoHeight = Math.min(i, i2);
        this.mPreviewWidth = i3;
        this.mPreviewHeight = i4;
        this.mSegmentData = list;
        this.mAudioPath = str;
        setPlayerState(1);
        setComposeState(1);
    }

    @Override // com.android.camera.module.impl.component.ILive.ILivePlayer
    public void pausePlayer() {
        if (this.mPlayerState == 3) {
            this.mEffectMediaPlayer.PausePreView();
            setPlayerState(4);
        }
    }

    @Override // com.android.camera.module.impl.component.ILive.ILivePlayer
    public void release() {
        Log.d(TAG, "release");
        releasePlayer();
        releaseComposeFile();
        releaseEffectGraph();
        reset();
        MiLiveModule.unloadLibs(MiLiveModule.LIB_LOAD_CALLER_PLAYER);
    }

    public void reset() {
        if (this.mPlayerState != 0) {
            setPlayerState(1);
        }
        if (this.mComposeState != 0) {
            setComposeState(1);
        }
    }

    @Override // com.android.camera.module.impl.component.ILive.ILivePlayer
    public void resumePlayer() {
        if (this.mPlayerState == 4) {
            this.mEffectMediaPlayer.ResumePreView();
            setPlayerState(3);
        }
    }

    @Override // com.android.camera.module.impl.component.ILive.ILivePlayer
    public void setStateListener(ILive.ILivePlayerStateListener iLivePlayerStateListener) {
        this.mStateListener = iLivePlayerStateListener;
    }

    @Override // com.android.camera.module.impl.component.ILive.ILivePlayer
    public void startCompose(String str) {
        String str2 = TAG;
        Log.d(str2, "startCompose path = " + str + ", state = " + getComposeStateString(this.mComposeState));
        if (this.mComposeState == 1 || this.mPlayerState == 4) {
            initMediaCompose();
            setComposeState(2);
            Log.d(TAG, "startCompose +");
            this.mMediaComposeFile.SetComposeFileName(str);
            this.mMediaComposeFile.BeginCompose();
            Log.d(TAG, "startCompose -");
        }
    }

    @Override // com.android.camera.module.impl.component.ILive.ILivePlayer
    public void startPlayer(SurfaceTexture surfaceTexture) {
        String str = TAG;
        Log.d(str, "startPlayer state = " + getPlayerStateString(this.mPlayerState) + ",texture = " + surfaceTexture);
        if (this.mPlayerState == 1) {
            initMediaPlayer(this.mPreviewWidth, this.mPreviewHeight);
            this.mEffectMediaPlayer.SetViewSurface(new Surface(surfaceTexture));
            setPlayerState(2);
            this.mEffectMediaPlayer.StartPreView();
            setPlayerState(3);
        }
    }

    @Override // com.android.camera.module.impl.component.ILive.ILivePlayer
    public void stopPlayer() {
        String str = TAG;
        Log.d(str, "stopPlayer state = " + getPlayerStateString(this.mPlayerState));
        int i = this.mPlayerState;
        if (i == 3 || i == 4) {
            this.mEffectMediaPlayer.StopPreView();
            setPlayerState(1);
        }
    }
}
