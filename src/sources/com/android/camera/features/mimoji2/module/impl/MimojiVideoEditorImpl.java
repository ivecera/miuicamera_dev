package com.android.camera.features.mimoji2.module.impl;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.Surface;
import com.android.camera.ActivityBase;
import com.android.camera.constant.DurationConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.ui.TextureVideoView;
import com.arcsoft.avatar.emoticon.EmoInfo;
import com.xiaomi.MediaRecord.SystemUtil;
import com.xiaomi.Video2GifEditer.EffectNotifier;
import com.xiaomi.Video2GifEditer.EffectType;
import com.xiaomi.Video2GifEditer.MediaComposeFile;
import com.xiaomi.Video2GifEditer.MediaEffect;
import com.xiaomi.Video2GifEditer.MediaEffectGraph;
import com.xiaomi.Video2GifEditer.MediaProcess;
import java.util.HashMap;
import java.util.List;

public class MimojiVideoEditorImpl implements MimojiModeProtocol.MimojiVideoEditor {
    /* access modifiers changed from: private */
    public static final String TAG = "MimojiVideoEditorImpl";
    private MediaComposeFile mComposeFile;
    private Context mContext;
    private int mEncodeHeight = 1080;
    private int mEncodeWidth = 1920;
    private MediaEffectGraph mMediaEffectGraph;
    private MimojiMediaPlayerCallback mMimojiMediaPlayerCallback;
    private boolean mNeedPrepare = true;
    private int mOrientation;
    private long mSourceID = 0;
    private TextureVideoView mTextureVideoView;
    private String mVideoSavePath;
    private long mVoiceChangeFilterID = 0;
    /* access modifiers changed from: private */
    public boolean mWaitingResultSurfaceTexture;

    private class MimojiMediaPlayerCallback implements TextureVideoView.MediaPlayerCallback {
        private MimojiMediaPlayerCallback() {
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public void onCompletion(MediaPlayer mediaPlayer) {
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            Log.e(MimojiVideoEditorImpl.TAG, "mimoji boolean onError[mp, what, extra]");
            MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
            if (mimojiFullScreenProtocol != null) {
                mimojiFullScreenProtocol.onCombineError();
                return false;
            }
            Log.e(MimojiVideoEditorImpl.TAG, "mimoji void combineVideoAudio[savePath] null");
            return false;
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
            return false;
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public void onPrepared(MediaPlayer mediaPlayer) {
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public void onSurfaceReady(Surface surface) {
            Log.d(MimojiVideoEditorImpl.TAG, "mimoji void onSurfaceReady[surface]");
            if (MimojiVideoEditorImpl.this.mWaitingResultSurfaceTexture) {
                MimojiVideoEditorImpl.this.startPlay(surface);
            }
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        }
    }

    static {
        System.loadLibrary("vvc++_shared");
        System.loadLibrary("mimoji_video2gif");
    }

    private MimojiVideoEditorImpl(ActivityBase activityBase) {
        this.mContext = activityBase.getCameraAppImpl().getApplicationContext();
    }

    static /* synthetic */ void a(int i, List list, int i2) {
        MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon;
        if (i2 != 100) {
            String str = TAG;
            Log.d(str, "mimoji void video2gif[]  Video  " + i + " convert GIF progress : " + i2 + "%");
        } else if (i == list.size() - 1 && (mimojiEmoticon = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250)) != null) {
            mimojiEmoticon.coverEmoticonSuccess();
        }
        String str2 = TAG;
        Log.d(str2, "progress value: " + i2);
    }

    public static MimojiVideoEditorImpl create(ActivityBase activityBase) {
        return new MimojiVideoEditorImpl(activityBase);
    }

    /* access modifiers changed from: private */
    public void onFail() {
        Log.d(TAG, "mimoji void onFail[]");
        FileUtils.deleteFile(MimojiHelper2.VIDEO_DEAL_CACHE_FILE);
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        if (mimojiFullScreenProtocol != null) {
            mimojiFullScreenProtocol.onCombineError();
        } else {
            Log.e(TAG, "mimoji void onFail null");
        }
    }

    /* access modifiers changed from: private */
    public void onSuccess(String str) {
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        if (mimojiFullScreenProtocol != null) {
            mimojiFullScreenProtocol.concatResult(str);
        } else {
            Log.e(TAG, "mimoji void cnSuccess[savePath] null");
        }
    }

    private void prepareComposeFile() {
        if (this.mMediaEffectGraph == null) {
            prepareEffectGraph();
        }
        if (this.mComposeFile == null) {
            this.mComposeFile = new MediaComposeFile(this.mMediaEffectGraph);
        }
        this.mComposeFile.ConstructMediaComposeFile(this.mEncodeWidth, this.mEncodeHeight, 20971520, 30);
    }

    private void prepareEffectGraph() {
        SystemUtil.Init(this.mContext, 123);
        if (this.mMediaEffectGraph == null) {
            this.mMediaEffectGraph = new MediaEffectGraph();
        }
        this.mMediaEffectGraph.ConstructMediaEffectGraph();
        long j = this.mVoiceChangeFilterID;
        if (j != 0) {
            this.mMediaEffectGraph.RemoveEffect(j, this.mSourceID, false);
            this.mVoiceChangeFilterID = 0;
        }
    }

    /* access modifiers changed from: private */
    public void startPlay(Surface surface) {
        Log.d(TAG, "mimoji void startPlay[surface]");
        if (surface != null) {
            this.mWaitingResultSurfaceTexture = false;
            int i = this.mOrientation;
            if (i == 0 || i == 180) {
                this.mTextureVideoView.setScaleType(3);
                this.mTextureVideoView.setRotation((float) this.mOrientation);
            } else {
                this.mTextureVideoView.setScaleType(6);
                this.mTextureVideoView.setRotation(this.mOrientation == 270 ? 0.0f : 180.0f);
            }
            this.mTextureVideoView.setLoop(true);
            this.mTextureVideoView.setClearSurface(true);
            this.mTextureVideoView.setVideoPath(this.mVideoSavePath);
            this.mTextureVideoView.setVideoSpecifiedSize(this.mEncodeWidth, this.mEncodeHeight);
            this.mTextureVideoView.start();
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void changeTimbre() {
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        if (mimojiFullScreenProtocol != null) {
            mimojiFullScreenProtocol.startMimojiRecordPreview();
        }
        pausePlay();
        FileUtils.deleteFile(MimojiHelper2.VIDEO_DEAL_CACHE_FILE);
        combineVideoAudio(MimojiHelper2.VIDEO_NORMAL_CACHE_FILE);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void combineVideoAudio(String str) {
        String str2 = TAG;
        Log.d(str2, "mimoji void combineVideoAudio[savePath]" + str);
        prepareEffectGraph();
        this.mSourceID = this.mMediaEffectGraph.AddVideoSource(str, false);
        MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        if (mimojiStatusManager2.getCurrentMimojiTimbreInfo() != null) {
            Log.d(TAG, "mimoji void startPlay[surface]  timbre start");
            if (this.mVoiceChangeFilterID == 0) {
                this.mVoiceChangeFilterID = MediaEffect.CreateEffect(EffectType.VoiceChangeFilter);
                long j = this.mVoiceChangeFilterID;
                if (j != 0) {
                    this.mMediaEffectGraph.AddEffect(j, this.mSourceID, false);
                    HashMap hashMap = new HashMap();
                    hashMap.put("mode", mimojiStatusManager2.getCurrentMimojiTimbreInfo().getTimbreId() + "");
                    MediaEffect.SetParamsForEffect(EffectType.VoiceChangeFilter, this.mVoiceChangeFilterID, hashMap);
                }
            }
            if (this.mVoiceChangeFilterID == 0) {
                Log.e(TAG, "mimoji void startPlay[surface] mVoiceChangeFilterID==0 timbre init fail");
                onFail();
                return;
            }
            prepareComposeFile();
            this.mComposeFile.SetComposeNotify(new EffectNotifier() {
                /* class com.android.camera.features.mimoji2.module.impl.MimojiVideoEditorImpl.AnonymousClass1 */

                @Override // com.xiaomi.Video2GifEditer.EffectNotifier
                public void OnReadyNow() {
                }

                @Override // com.xiaomi.Video2GifEditer.EffectNotifier
                public void OnReceiveFailed() {
                    Log.d(MimojiVideoEditorImpl.TAG, "ComposeVoiceChangeFile OnReceiveFailed");
                    MimojiVideoEditorImpl.this.onFail();
                }

                @Override // com.xiaomi.Video2GifEditer.EffectNotifier
                public void OnReceiveFinish() {
                    Log.d(MimojiVideoEditorImpl.TAG, "ComposeVoiceChangeFile OnReceiveFinish");
                    MimojiVideoEditorImpl.this.onSuccess(MimojiHelper2.VIDEO_DEAL_CACHE_FILE);
                }
            });
            this.mComposeFile.SetComposeFileName(MimojiHelper2.VIDEO_DEAL_CACHE_FILE);
            this.mComposeFile.BeginComposeFile();
            return;
        }
        onSuccess(MimojiHelper2.VIDEO_NORMAL_CACHE_FILE);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public boolean init(TextureVideoView textureVideoView, String str) {
        String str2 = TAG;
        Log.v(str2, "mimoji videoeditor init videoUri " + str);
        this.mWaitingResultSurfaceTexture = true;
        this.mTextureVideoView = textureVideoView;
        this.mVideoSavePath = str;
        this.mMimojiMediaPlayerCallback = new MimojiMediaPlayerCallback();
        this.mTextureVideoView.setMediaPlayerCallback(this.mMimojiMediaPlayerCallback);
        return true;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public boolean isPlaying() {
        TextureVideoView textureVideoView = this.mTextureVideoView;
        if (textureVideoView != null) {
            return textureVideoView.isPlaying();
        }
        return false;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void onDestory() {
        Log.d(TAG, "mimoji void onDestory[]");
        TextureVideoView textureVideoView = this.mTextureVideoView;
        if (textureVideoView != null) {
            textureVideoView.stop();
            this.mTextureVideoView = null;
        }
        MediaComposeFile mediaComposeFile = this.mComposeFile;
        if (mediaComposeFile != null) {
            mediaComposeFile.CancelComposeFile();
            this.mComposeFile.DestructMediaComposeFile();
            this.mComposeFile = null;
        }
        MediaEffectGraph mediaEffectGraph = this.mMediaEffectGraph;
        if (mediaEffectGraph != null) {
            mediaEffectGraph.DestructMediaEffectGraph();
            this.mMediaEffectGraph = null;
            SystemUtil.UnInit();
        }
        this.mSourceID = 0;
        this.mVoiceChangeFilterID = 0;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public boolean pausePlay() {
        TextureVideoView textureVideoView;
        Log.d(TAG, "mimoji void pausePlay[]");
        if (!isPlaying() || (textureVideoView = this.mTextureVideoView) == null) {
            return false;
        }
        textureVideoView.pause();
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(252, this);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public boolean resumePlay() {
        Log.d(TAG, "mimoji void resumePlay[]");
        TextureVideoView textureVideoView = this.mTextureVideoView;
        if (textureVideoView == null) {
            return false;
        }
        textureVideoView.resume();
        return false;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void setRecordParameter(int i, int i2, int i3) {
        this.mOrientation = Math.max(0, i3);
        String str = TAG;
        Log.d(str, "setRecordParameter:  " + i + " | " + i2 + " | " + this.mOrientation);
        this.mEncodeWidth = i;
        this.mEncodeHeight = i2;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void startPlay() {
        TextureVideoView textureVideoView = this.mTextureVideoView;
        if (textureVideoView != null && this.mVideoSavePath != null) {
            if (textureVideoView.getPreviewSurface() == null) {
                this.mWaitingResultSurfaceTexture = true;
                this.mTextureVideoView.start();
                return;
            }
            startPlay(this.mTextureVideoView.getPreviewSurface());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(252, this);
        onDestory();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void video2gif(List<EmoInfo> list) {
        if (list == null || list.size() == 0) {
            MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
            if (mimojiEmoticon != null) {
                mimojiEmoticon.coverEmoticonSuccess();
                return;
            }
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            String str = MimojiHelper2.EMOTICON_MP4_CACHE_DIR + list.get(i).getEmoName() + ".mp4";
            String str2 = MimojiHelper2.EMOTICON_GIF_CACHE_DIR + list.get(i).getEmoName() + ".gif";
            FileUtils.makeDir(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
            Log.d(TAG, " source :" + str + "\n target : " + str2);
            if (MediaProcess.Convert(str, 2000, str2, true, 20, DurationConstant.DURATION_RESET_FALLBACK, 0, 5000, 1.0f, new h(i, list)) != 0) {
                Log.d(TAG, "mimoji void video2gif[] cover fail");
                MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon2 = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
                if (mimojiEmoticon2 != null) {
                    mimojiEmoticon2.coverEmoticonError();
                }
            }
        }
    }
}
