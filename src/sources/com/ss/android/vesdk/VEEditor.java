package com.ss.android.vesdk;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.statistic.MistatsConstants;
import com.ss.android.medialib.FFMpegInvoker;
import com.ss.android.ttve.common.TECommonCallback;
import com.ss.android.ttve.common.TETrackIndexManager;
import com.ss.android.ttve.model.FilterBean;
import com.ss.android.ttve.model.MVInfoBean;
import com.ss.android.ttve.model.MVResourceBean;
import com.ss.android.ttve.monitor.MonitorUtils;
import com.ss.android.ttve.monitor.TEMonitor;
import com.ss.android.ttve.monitor.TEMonitorFilterMgr;
import com.ss.android.ttve.monitor.TEMonitorNewKeys;
import com.ss.android.ttve.nativePort.NativeCallbacks;
import com.ss.android.ttve.nativePort.TEInterface;
import com.ss.android.ttve.nativePort.TEVideoUtils;
import com.ss.android.vesdk.VEListener;
import com.ss.android.vesdk.VEVideoEncodeSettings;
import com.ss.android.vesdk.keyvaluepair.VEKeyValue;
import com.ss.android.vesdk.runtime.VEEditorResManager;
import com.ss.android.vesdk.runtime.VEEffectConfig;
import com.ss.android.vesdk.runtime.VERuntime;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class VEEditor implements SurfaceTexture.OnFrameAvailableListener, LifecycleObserver {
    private static final String AUDIO_VOLUME = "audio volume";
    private static final String EFFECT_REQ_ID = "effect req id";
    private static final String EFFECT_RES_PATH = "effect res path";
    private static final String EFFECT_STICKER_ID = "effect sticker id";
    private static final String EFFECT_USE_AMAZING = "effect use amazing";
    private static final int ENGINE_PREPARE_FOR_COMPILE = 1;
    private static final int ENGINE_PREPARE_FOR_COMPILE_WITHWATERMARK = 2;
    private static final int ENGINE_PREPARE_FOR_PLAYBACK = 0;
    private static final String ENTITY_ALPHA = "entity alpha";
    private static final String ENTITY_END_TIME = "entity end time";
    private static final String ENTITY_LAYER = "entity layer";
    private static final String ENTITY_PATH = "entity path";
    private static final String ENTITY_POSITION_X = "entity position x";
    private static final String ENTITY_POSITION_Y = "entity position y";
    private static final String ENTITY_ROTATION = "entity rotation";
    private static final String ENTITY_SCALE_X = "entity scale x";
    private static final String ENTITY_SCALE_Y = "entity scale y";
    private static final String ENTITY_START_TIME = "entity start time";
    private static final String FILTER_INTENSITY = "filter intensity";
    private static final String FILTER_PATH_LEFT = "left filter";
    private static final String FILTER_PATH_RIGHT = "right filter";
    private static final String FILTER_POSITION = "filter position";
    private static final String MUSIC_SRT_EFFECT_PARAM = "music srt effect para";
    public static final int PAGEMODE_COMPILE_CROP = 1;
    public static final int PAGEMODE_COMPILE_DOWNLOAD = 2;
    public static final int PAGEMODE_COMPILE_PUBLISH = 0;
    public static final int PAGEMODE_COMPILE_UNKNOWN = -1;
    private static final String TAG = "VEEditor";
    private static final int TEFilterType_Audio = 1;
    private static final int TEFilterType_Caption = 4;
    private static final int TEFilterType_Color = 2;
    private static final int TEFilterType_Effect_Color = 7;
    private static final int TEFilterType_Effect_Filter = 8;
    private static final int TEFilterType_Info_Sticker = 9;
    private static final int TEFilterType_Music_Srt_Effect_filter = 10;
    private static final int TEFilterType_Sticker = 0;
    private static final int TEFilterType_TimeEffect = 6;
    private static final int TEFilterType_TransForm = 3;
    private static final int TEFilterType_WaterMark = 5;
    private static final int TESeekTo_IFrame = 2;
    private static final int TETrackProperty_BGM = 1;
    private static final int TETrackProperty_OriginalSound = 0;
    public static final int TETrackType_Audio = 1;
    public static final int TETrackType_Video = 0;
    private static final String USE_FILTER_RES_INTENSITY = "use filter res intensity";
    private static boolean isMVInit;
    private int mAudioEffectfilterIndex;
    /* access modifiers changed from: private */
    public boolean mBCompileHighQualityGif;
    private boolean mBReversePlay;
    @ColorInt
    private int mBackGroundColor;
    private boolean mCancelReverse;
    private int mColorFilterIndex;
    /* access modifiers changed from: private */
    public volatile VEListener.VEEditorCompileListener mCompileListener;
    private String mCompileType;
    private FilterBean mCurColorFilter;
    private Bitmap mCurrentBmp;
    private NativeCallbacks.IEncoderDataCallback mEncoderDataCallback;
    /* access modifiers changed from: private */
    public VEListener.VEEncoderListener mEncoderListener;
    /* access modifiers changed from: private */
    public volatile VEListener.VEFirstFrameListener mFirstFrameListener;
    private boolean mFirstTimeSurfaceCreate;
    private NativeCallbacks.IGetImageCallback mGetImageCallback;
    /* access modifiers changed from: private */
    public VEListener.VEGetImageListener mGetImageListener;
    private int mInPoint;
    private int mInitDisplayHeight;
    private int mInitDisplayWidth;
    private VESize mInitSize;
    private boolean mInitSuccess;
    private int mMVBackgroundAudioRid;
    private int mMVBackgroundAudioTrackIndex;
    private int mMasterTrackIndex;
    /* access modifiers changed from: private */
    public VEEditorMessageHandler mMessageHandler;
    private int mMusicSRTEffectFilterIndex;
    private NativeCallbacks.IOpenGLCallback mOpenGLCallback;
    private int mOutPoint;
    private String mOutputFile;
    private int mPageMode;
    private boolean mReDrawSurface;
    private boolean mReDrawSurfaceOnce;
    private VEEditorResManager mResManager;
    private boolean mReverseDone;
    /* access modifiers changed from: private */
    public volatile VEListener.VEEditorSeekListener mSeekListener;
    /* access modifiers changed from: private */
    public Surface mSurface;
    private SurfaceHolder.Callback2 mSurfaceCallback;
    /* access modifiers changed from: private */
    public int mSurfaceHeight;
    /* access modifiers changed from: private */
    public SurfaceTexture mSurfaceTexture;
    private SurfaceView mSurfaceView;
    /* access modifiers changed from: private */
    public int mSurfaceWidth;
    private TEMonitorFilterMgr mTEMonitorFilterMgr;
    private TECommonCallback mTeCommonErrorCallback;
    private TECommonCallback mTeCommonInfoCallback;
    private final TextureView.SurfaceTextureListener mTextureListener;
    private TextureView mTextureView;
    private TETrackIndexManager mTrackIndexManager;
    /* access modifiers changed from: private */
    public VECommonCallback mUserCommonErrorCallback;
    /* access modifiers changed from: private */
    public VECommonCallback mUserCommonInfoCallback;
    private TEInterface mVideoEditor;
    private VIDEO_RATIO mVideoOutRes;
    private Boolean mbSeparateAV;
    /* access modifiers changed from: private */
    public int miFrameCount;
    private long mlCompileStartTime;
    /* access modifiers changed from: private */
    public long mlCurTimeMS;
    /* access modifiers changed from: private */
    public long mlLastTimeMS;
    /* access modifiers changed from: private */
    public Mp4ToHighQualityGIFConverter mp4ToGIFConverter;
    private MVInfoBean mvInfo;
    private float rotate;
    private float scaleH;
    private float scaleW;
    private VETimelineParams veTimelineParams;
    private String waterMarkFile;
    private double waterMarkHeight;
    private double waterMarkOffsetX;
    private double waterMarkOffsetY;
    private double waterMarkWidth;

    /* renamed from: com.ss.android.vesdk.VEEditor$10  reason: invalid class name */
    static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$com$ss$android$vesdk$VEVideoEncodeSettings$COMPILE_TYPE = new int[VEVideoEncodeSettings.COMPILE_TYPE.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            $SwitchMap$com$ss$android$vesdk$VEVideoEncodeSettings$COMPILE_TYPE[VEVideoEncodeSettings.COMPILE_TYPE.COMPILE_TYPE_MP4.ordinal()] = 1;
            $SwitchMap$com$ss$android$vesdk$VEVideoEncodeSettings$COMPILE_TYPE[VEVideoEncodeSettings.COMPILE_TYPE.COMPILE_TYPE_GIF.ordinal()] = 2;
            try {
                $SwitchMap$com$ss$android$vesdk$VEVideoEncodeSettings$COMPILE_TYPE[VEVideoEncodeSettings.COMPILE_TYPE.COMPILE_TYPE_HIGH_GIF.ordinal()] = 3;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public class MVConsts {
        public static final String TYPE_AUDIO = "audio";
        public static final String TYPE_GIF = "gif";
        public static final String TYPE_IMG = "img";
        public static final String TYPE_TEXT = "text";
        public static final String TYPE_VIDEO = "video";

        public MVConsts() {
        }
    }

    /* access modifiers changed from: private */
    public class Mp4ToHighQualityGIFConverter implements Runnable {
        VECommonCallback mCallback;
        String mInputFile;
        boolean mIsRunning;
        String mOutputFile;
        String mPaletteFile;
        private String waterMarkFile;
        private int waterMarkHeight;
        private int waterMarkOffsetX;
        private int waterMarkOffsetY;
        private int waterMarkWidth;

        Mp4ToHighQualityGIFConverter() {
            this.mIsRunning = false;
            this.waterMarkFile = null;
            this.waterMarkWidth = 50;
            this.waterMarkHeight = 50;
            this.waterMarkOffsetX = 100;
            this.waterMarkOffsetY = 100;
            this.mPaletteFile = null;
            this.mInputFile = null;
            this.mOutputFile = null;
            this.mCallback = null;
        }

        Mp4ToHighQualityGIFConverter(String str, String str2, VECommonCallback vECommonCallback) {
            this.mIsRunning = false;
            this.waterMarkFile = null;
            this.waterMarkWidth = 50;
            this.waterMarkHeight = 50;
            this.waterMarkOffsetX = 100;
            this.waterMarkOffsetY = 100;
            this.mInputFile = str;
            this.mOutputFile = str2;
            this.mCallback = vECommonCallback;
            this.mPaletteFile = this.mOutputFile + FileUtils.FILTER_FILE_SUFFIX;
        }

        public void run() {
            String str;
            if (TextUtils.isEmpty(this.mInputFile) || TextUtils.isEmpty(this.mOutputFile) || this.mIsRunning) {
                VECommonCallback vECommonCallback = this.mCallback;
                if (vECommonCallback != null) {
                    vECommonCallback.onCallback(4103, VEResult.TER_BAD_FILE, 0.0f, "");
                    return;
                }
                return;
            }
            this.mIsRunning = true;
            int executeFFmpegCommand = TEVideoUtils.executeFFmpegCommand(String.format("ffmpeg -y -i %s -vf palettegen %s", this.mInputFile, this.mPaletteFile), null);
            if (executeFFmpegCommand != 0) {
                this.mIsRunning = false;
                VECommonCallback vECommonCallback2 = this.mCallback;
                if (vECommonCallback2 != null) {
                    vECommonCallback2.onCallback(4103, executeFFmpegCommand, 0.0f, "");
                    return;
                }
                return;
            }
            String str2 = this.waterMarkFile;
            if (str2 != null) {
                str = String.format(Locale.US, "ffmpeg -y -i %s -i %s -i %s -filter_complex [2:v]scale=w=%d:h=%d[o0];[0:v][o0]overlay=x=%d-w/2:y=%d-h/2[o1];[o1][1:v]paletteuse -f gif %s", this.mInputFile, this.mPaletteFile, str2, Integer.valueOf(this.waterMarkWidth), Integer.valueOf(this.waterMarkHeight), Integer.valueOf(this.waterMarkOffsetX), Integer.valueOf(this.waterMarkOffsetY), this.mOutputFile);
            } else {
                str = String.format(Locale.US, "ffmpeg -y -i %s -i %s -lavfi paletteuse -f gif %s", this.mInputFile, this.mPaletteFile, this.mOutputFile);
            }
            int executeFFmpegCommand2 = TEVideoUtils.executeFFmpegCommand(str, null);
            VECommonCallback vECommonCallback3 = this.mCallback;
            if (vECommonCallback3 != null) {
                vECommonCallback3.onCallback(4103, executeFFmpegCommand2, 0.0f, "");
            }
            this.mIsRunning = false;
        }

        public void setCallback(VECommonCallback vECommonCallback) {
            this.mCallback = vECommonCallback;
        }

        public void setInputFile(String str) {
            this.mInputFile = str;
        }

        public void setOutputFile(String str) {
            this.mOutputFile = str;
            if (TextUtils.isEmpty(this.mOutputFile)) {
                this.mPaletteFile = null;
                return;
            }
            this.mPaletteFile = new File(this.mOutputFile).getParent() + File.separatorChar + "palette.png";
        }

        public void setWaterMarkFile(String str) {
            this.waterMarkFile = str;
        }

        public void setWaterMarkHeight(int i) {
            this.waterMarkHeight = i;
        }

        public void setWaterMarkOffsetX(int i) {
            this.waterMarkOffsetX = i;
        }

        public void setWaterMarkOffsetY(int i) {
            this.waterMarkOffsetY = i;
        }

        public void setWaterMarkWidth(int i) {
            this.waterMarkWidth = i;
        }
    }

    public enum SCALE_MODE {
        SCALE_MODE_CENTER_INSIDE,
        SCALE_MODE_CENTER_CROP,
        SCALE_MODE_CENTER_INSIDE_WITH_2DENGINE
    }

    public enum SEEK_MODE {
        EDITOR_SEEK_FLAG_OnGoing(0),
        EDITOR_SEEK_FLAG_LastSeek(1),
        EDITOR_SEEK_FLAG_ToIframe(2),
        EDITOR_SEEK_FLAG_LAST_UpdateIn(EDITOR_SEEK_FLAG_LastSeek.getValue() | 4),
        EDITOR_SEEK_FLAG_LAST_UpdateOut(EDITOR_SEEK_FLAG_LastSeek.getValue() | 8),
        EDITOR_SEEK_FLAG_LAST_UpdateInOut(EDITOR_SEEK_FLAG_LastSeek.getValue() | 16);
        
        private int mValue;

        private SEEK_MODE(int i) {
            this.mValue = i;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public enum SET_RANGE_MODE {
        EDITOR_TIMERANGE_FLAG_BEFORE_SPEED(0),
        EDITOR_TIMERANGE_FLAG_AFTER_SPEED(1);
        
        private int mValue;

        private SET_RANGE_MODE(int i) {
            this.mValue = i;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    /* access modifiers changed from: private */
    public class VEEditorMessageHandler extends Handler {
        public VEEditorMessageHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 4101) {
                if (i != 4103) {
                    if (i != 4105) {
                        if (i == 4117 && VEEditor.this.mGetImageListener != null) {
                            VEEditor.this.mGetImageListener.onGetImageData(null, -1, -1, -1);
                        }
                    } else if (VEEditor.this.mCompileListener != null) {
                        VEEditor.this.mCompileListener.onCompileProgress(((Float) message.obj).floatValue());
                    }
                } else if (VEEditor.this.mCompileListener != null) {
                    if (message.arg1 < 0) {
                        VEEditor.this.mCompileListener.onCompileError(message.arg1, 0, 0.0f, "");
                    } else {
                        VEEditor.this.mCompileListener.onCompileDone();
                    }
                    VEListener.VEEditorCompileListener unused = VEEditor.this.mCompileListener = null;
                }
            } else if (VEEditor.this.mSeekListener != null) {
                VEEditor.this.mSeekListener.onSeekDone(0);
                VEListener.VEEditorSeekListener unused2 = VEEditor.this.mSeekListener = null;
            }
        }
    }

    public enum VEState {
        ANY(65535),
        ERROR(0),
        NOTHING(1048576),
        IDLE(1),
        INITIALIZED(2),
        PREPARED(4),
        STARTED(8),
        PAUSED(16),
        SEEKING(32),
        STOPPED(64),
        COMPLETED(128);
        
        private int mValue;

        private VEState(int i) {
            this.mValue = i;
        }

        public static VEState valueOf(int i) {
            if (i == 0) {
                return ERROR;
            }
            if (i == 1) {
                return IDLE;
            }
            if (i == 2) {
                return INITIALIZED;
            }
            if (i == 4) {
                return PREPARED;
            }
            if (i == 8) {
                return STARTED;
            }
            if (i == 16) {
                return PAUSED;
            }
            if (i == 32) {
                return SEEKING;
            }
            if (i == 64) {
                return STOPPED;
            }
            if (i == 128) {
                return COMPLETED;
            }
            if (i == 65535) {
                return ANY;
            }
            if (i != 1048576) {
                return null;
            }
            return NOTHING;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public enum VIDEO_RATIO {
        VIDEO_OUT_RATIO_1_1,
        VIDEO_OUT_RATIO_4_3,
        VIDEO_OUT_RATIO_3_4,
        VIDEO_OUT_RATIO_16_9,
        VIDEO_OUT_RATIO_9_16,
        VIDEO_OUT_RATIO_ORIGINAL
    }

    public VEEditor(String str) throws VEException {
        this.mInitSize = new VESize(-1, -1);
        this.mCompileType = "mp4";
        this.mMessageHandler = new VEEditorMessageHandler(Looper.getMainLooper());
        this.mSeekListener = null;
        this.mCompileListener = null;
        this.mFirstFrameListener = null;
        this.mUserCommonInfoCallback = null;
        this.mUserCommonErrorCallback = null;
        this.mTrackIndexManager = new TETrackIndexManager();
        this.mTeCommonInfoCallback = new TECommonCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass1 */

            @Override // com.ss.android.ttve.common.TECommonCallback
            public void onCallback(int i, int i2, float f2, String str) {
                if (i != 4101) {
                    if (i != 4103) {
                        if (i != 4105) {
                            if (VEEditor.this.mUserCommonInfoCallback != null) {
                                VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                            }
                        } else if (VEEditor.this.mCompileListener != null) {
                            Message message = new Message();
                            message.what = i;
                            message.obj = Float.valueOf(f2);
                            VEEditor.this.mMessageHandler.sendMessage(message);
                        } else if (VEEditor.this.mUserCommonInfoCallback != null) {
                            VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                        }
                    } else if (VEEditor.this.mBCompileHighQualityGif) {
                        VEEditor.this.mp4ToGIFConverter.setCallback(VEEditor.this.mUserCommonInfoCallback);
                        new Thread(VEEditor.this.mp4ToGIFConverter).start();
                        boolean unused = VEEditor.this.mBCompileHighQualityGif = false;
                    } else {
                        if (i2 == 1 || i2 == 0) {
                            VEEditor.this.onMonitorCompile();
                        }
                        if (VEEditor.this.mCompileListener != null) {
                            Message message2 = new Message();
                            message2.what = 4103;
                            message2.arg1 = i2;
                            VEEditor.this.mMessageHandler.sendMessage(message2);
                        } else if (VEEditor.this.mUserCommonInfoCallback != null) {
                            VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                        }
                    }
                } else if (VEEditor.this.mSeekListener != null) {
                    VEEditor.this.mMessageHandler.sendEmptyMessage(4101);
                } else if (VEEditor.this.mUserCommonInfoCallback != null) {
                    VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                }
            }
        };
        this.mTeCommonErrorCallback = new TECommonCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass2 */

            @Override // com.ss.android.ttve.common.TECommonCallback
            public void onCallback(final int i, final int i2, final float f2, final String str) {
                VEEditor.this.onMonitorError();
                if (VEEditor.this.mUserCommonErrorCallback != null) {
                    VEEditor.this.mUserCommonErrorCallback.onCallback(i, i2, f2, str);
                }
                if (VEEditor.this.mCompileListener != null) {
                    VEEditor.this.mMessageHandler.post(new Runnable() {
                        /* class com.ss.android.vesdk.VEEditor.AnonymousClass2.AnonymousClass1 */

                        public void run() {
                            if (VEEditor.this.mCompileListener != null) {
                                VEEditor.this.mCompileListener.onCompileError(i, i2, f2, str);
                            }
                        }
                    });
                }
            }
        };
        this.mMVBackgroundAudioRid = 0;
        this.mMVBackgroundAudioTrackIndex = -1;
        this.mSurfaceWidth = 0;
        this.mSurfaceHeight = 0;
        this.mInitDisplayWidth = 0;
        this.mInitDisplayHeight = 0;
        this.mbSeparateAV = false;
        this.mAudioEffectfilterIndex = -1;
        this.mMasterTrackIndex = 0;
        this.mVideoEditor = TEInterface.createEngine();
        this.miFrameCount = 0;
        this.mlCurTimeMS = 0;
        this.mlLastTimeMS = 0;
        this.mInPoint = 0;
        this.mOutPoint = -1;
        this.mInitSuccess = false;
        this.mReverseDone = false;
        this.mCancelReverse = false;
        this.mColorFilterIndex = -1;
        this.mMusicSRTEffectFilterIndex = -1;
        this.mOutputFile = null;
        this.mlCompileStartTime = 0;
        this.mBReversePlay = false;
        this.mBCompileHighQualityGif = false;
        this.mp4ToGIFConverter = null;
        this.mEncoderListener = null;
        this.mGetImageListener = null;
        this.mTEMonitorFilterMgr = new TEMonitorFilterMgr();
        this.mReDrawSurface = false;
        this.mFirstTimeSurfaceCreate = true;
        this.mReDrawSurfaceOnce = false;
        this.mCurrentBmp = null;
        this.rotate = 0.0f;
        this.scaleW = 1.0f;
        this.scaleH = 1.0f;
        this.mPageMode = -1;
        this.mBackGroundColor = ViewCompat.MEASURED_STATE_MASK;
        this.mTextureListener = new TextureView.SurfaceTextureListener() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass3 */

            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                if (VEEditor.this.mSurfaceTexture == surfaceTexture) {
                    VEEditor vEEditor = VEEditor.this;
                    vEEditor.onSurfaceCreated(vEEditor.mSurface);
                } else {
                    Surface unused = VEEditor.this.mSurface = new Surface(surfaceTexture);
                    VEEditor vEEditor2 = VEEditor.this;
                    vEEditor2.onSurfaceCreated(vEEditor2.mSurface);
                }
                SurfaceTexture unused2 = VEEditor.this.mSurfaceTexture = surfaceTexture;
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                VEEditor.this.onSurfaceDestroyed();
                VEEditor.this.mSurface.release();
                return true;
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
                int unused = VEEditor.this.mSurfaceWidth = i;
                int unused2 = VEEditor.this.mSurfaceHeight = i2;
                VEEditor.this.updateInitDisplaySize();
            }

            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        };
        this.mSurfaceCallback = new SurfaceHolder.Callback2() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass4 */

            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                VELogUtil.d(VEEditor.TAG, String.format("surfaceChanged: pixel format [%d], size [%d, %d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
                int unused = VEEditor.this.mSurfaceWidth = i2;
                int unused2 = VEEditor.this.mSurfaceHeight = i3;
                VEEditor.this.updateInitDisplaySize();
                VEEditor.this.onSurfaceChanged(i2, i3);
            }

            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                VEEditor.this.onSurfaceCreated(surfaceHolder.getSurface());
            }

            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                VEEditor.this.onSurfaceDestroyed();
            }

            public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
                VELogUtil.d(VEEditor.TAG, "surfaceRedrawNeeded...");
            }
        };
        this.mOpenGLCallback = new NativeCallbacks.IOpenGLCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass5 */

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLCreate(int i) {
                VELogUtil.d(VEEditor.TAG, "onOpenGLCreate: ret = " + i);
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLDestroy(int i) {
                VELogUtil.d(VEEditor.TAG, "onOpenGLDestroy: ret = " + i);
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLDrawAfter(int i, double d2) {
                VELogUtil.v(VEEditor.TAG, "onOpenGLDrawing: tex = " + i + " timeStamp = " + d2);
                if (VEEditor.this.miFrameCount == 0) {
                    TEMonitor.perfLong(1, TEMonitorNewKeys.TE_EDIT_FIRST_FRAME_TIME, System.currentTimeMillis() - VEEditor.this.mlLastTimeMS);
                    if (VEEditor.this.mFirstFrameListener != null) {
                        VEEditor.this.mFirstFrameListener.onRendered();
                    }
                }
                VEEditor.access$1804(VEEditor.this);
                if (VEEditor.this.miFrameCount == 30) {
                    long unused = VEEditor.this.mlCurTimeMS = System.currentTimeMillis();
                    if (VEEditor.this.mlLastTimeMS != VEEditor.this.mlCurTimeMS) {
                        float access$2100 = 30000.0f / ((float) (VEEditor.this.mlCurTimeMS - VEEditor.this.mlLastTimeMS));
                        VELogUtil.d(VEEditor.TAG, "Render FPS = " + access$2100);
                        VEEditor vEEditor = VEEditor.this;
                        long unused2 = vEEditor.mlLastTimeMS = vEEditor.mlCurTimeMS;
                        int unused3 = VEEditor.this.miFrameCount = 0;
                    }
                }
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLDrawBefore(int i, double d2) {
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onPreviewSurface(int i) {
                return 0;
            }
        };
        this.mEncoderDataCallback = new NativeCallbacks.IEncoderDataCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass6 */

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IEncoderDataCallback
            public int onCompressBuffer(byte[] bArr, int i, int i2, boolean z) {
                if (bArr == null || i < 0 || i2 <= 0) {
                    return -1;
                }
                if (VEEditor.this.mEncoderListener == null) {
                    return -2;
                }
                VEEditor.this.mEncoderListener.onEncoderDataAvailable(bArr, i, i2, z);
                return 0;
            }
        };
        this.mGetImageCallback = new NativeCallbacks.IGetImageCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass7 */

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IGetImageCallback
            public int onImageData(byte[] bArr, int i, int i2, int i3) {
                if (VEEditor.this.mGetImageListener == null) {
                    return -100;
                }
                if (bArr != null) {
                    return VEEditor.this.mGetImageListener.onGetImageData(bArr, i, i2, i3);
                }
                Message message = new Message();
                message.what = VECommonCallbackInfo.TE_INFO_GET_IMAGE_DONE;
                VEEditor.this.mMessageHandler.sendMessage(message);
                return 0;
            }
        };
        if (!TextUtils.isEmpty(str)) {
            this.mResManager = new VEEditorResManager(str);
            this.mVideoEditor.setInfoListener(this.mTeCommonInfoCallback);
            this.mVideoEditor.setErrorListener(this.mTeCommonErrorCallback);
            MonitorUtils.monitorStatistics("iesve_veeditor_offscreen", 1, null);
            return;
        }
        throw new VEException(-100, "workspace is: " + str);
    }

    public VEEditor(String str, SurfaceView surfaceView) {
        this.mInitSize = new VESize(-1, -1);
        this.mCompileType = "mp4";
        this.mMessageHandler = new VEEditorMessageHandler(Looper.getMainLooper());
        this.mSeekListener = null;
        this.mCompileListener = null;
        this.mFirstFrameListener = null;
        this.mUserCommonInfoCallback = null;
        this.mUserCommonErrorCallback = null;
        this.mTrackIndexManager = new TETrackIndexManager();
        this.mTeCommonInfoCallback = new TECommonCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass1 */

            @Override // com.ss.android.ttve.common.TECommonCallback
            public void onCallback(int i, int i2, float f2, String str) {
                if (i != 4101) {
                    if (i != 4103) {
                        if (i != 4105) {
                            if (VEEditor.this.mUserCommonInfoCallback != null) {
                                VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                            }
                        } else if (VEEditor.this.mCompileListener != null) {
                            Message message = new Message();
                            message.what = i;
                            message.obj = Float.valueOf(f2);
                            VEEditor.this.mMessageHandler.sendMessage(message);
                        } else if (VEEditor.this.mUserCommonInfoCallback != null) {
                            VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                        }
                    } else if (VEEditor.this.mBCompileHighQualityGif) {
                        VEEditor.this.mp4ToGIFConverter.setCallback(VEEditor.this.mUserCommonInfoCallback);
                        new Thread(VEEditor.this.mp4ToGIFConverter).start();
                        boolean unused = VEEditor.this.mBCompileHighQualityGif = false;
                    } else {
                        if (i2 == 1 || i2 == 0) {
                            VEEditor.this.onMonitorCompile();
                        }
                        if (VEEditor.this.mCompileListener != null) {
                            Message message2 = new Message();
                            message2.what = 4103;
                            message2.arg1 = i2;
                            VEEditor.this.mMessageHandler.sendMessage(message2);
                        } else if (VEEditor.this.mUserCommonInfoCallback != null) {
                            VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                        }
                    }
                } else if (VEEditor.this.mSeekListener != null) {
                    VEEditor.this.mMessageHandler.sendEmptyMessage(4101);
                } else if (VEEditor.this.mUserCommonInfoCallback != null) {
                    VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                }
            }
        };
        this.mTeCommonErrorCallback = new TECommonCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass2 */

            @Override // com.ss.android.ttve.common.TECommonCallback
            public void onCallback(final int i, final int i2, final float f2, final String str) {
                VEEditor.this.onMonitorError();
                if (VEEditor.this.mUserCommonErrorCallback != null) {
                    VEEditor.this.mUserCommonErrorCallback.onCallback(i, i2, f2, str);
                }
                if (VEEditor.this.mCompileListener != null) {
                    VEEditor.this.mMessageHandler.post(new Runnable() {
                        /* class com.ss.android.vesdk.VEEditor.AnonymousClass2.AnonymousClass1 */

                        public void run() {
                            if (VEEditor.this.mCompileListener != null) {
                                VEEditor.this.mCompileListener.onCompileError(i, i2, f2, str);
                            }
                        }
                    });
                }
            }
        };
        this.mMVBackgroundAudioRid = 0;
        this.mMVBackgroundAudioTrackIndex = -1;
        this.mSurfaceWidth = 0;
        this.mSurfaceHeight = 0;
        this.mInitDisplayWidth = 0;
        this.mInitDisplayHeight = 0;
        this.mbSeparateAV = false;
        this.mAudioEffectfilterIndex = -1;
        this.mMasterTrackIndex = 0;
        this.mVideoEditor = TEInterface.createEngine();
        this.miFrameCount = 0;
        this.mlCurTimeMS = 0;
        this.mlLastTimeMS = 0;
        this.mInPoint = 0;
        this.mOutPoint = -1;
        this.mInitSuccess = false;
        this.mReverseDone = false;
        this.mCancelReverse = false;
        this.mColorFilterIndex = -1;
        this.mMusicSRTEffectFilterIndex = -1;
        this.mOutputFile = null;
        this.mlCompileStartTime = 0;
        this.mBReversePlay = false;
        this.mBCompileHighQualityGif = false;
        this.mp4ToGIFConverter = null;
        this.mEncoderListener = null;
        this.mGetImageListener = null;
        this.mTEMonitorFilterMgr = new TEMonitorFilterMgr();
        this.mReDrawSurface = false;
        this.mFirstTimeSurfaceCreate = true;
        this.mReDrawSurfaceOnce = false;
        this.mCurrentBmp = null;
        this.rotate = 0.0f;
        this.scaleW = 1.0f;
        this.scaleH = 1.0f;
        this.mPageMode = -1;
        this.mBackGroundColor = ViewCompat.MEASURED_STATE_MASK;
        this.mTextureListener = new TextureView.SurfaceTextureListener() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass3 */

            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                if (VEEditor.this.mSurfaceTexture == surfaceTexture) {
                    VEEditor vEEditor = VEEditor.this;
                    vEEditor.onSurfaceCreated(vEEditor.mSurface);
                } else {
                    Surface unused = VEEditor.this.mSurface = new Surface(surfaceTexture);
                    VEEditor vEEditor2 = VEEditor.this;
                    vEEditor2.onSurfaceCreated(vEEditor2.mSurface);
                }
                SurfaceTexture unused2 = VEEditor.this.mSurfaceTexture = surfaceTexture;
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                VEEditor.this.onSurfaceDestroyed();
                VEEditor.this.mSurface.release();
                return true;
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
                int unused = VEEditor.this.mSurfaceWidth = i;
                int unused2 = VEEditor.this.mSurfaceHeight = i2;
                VEEditor.this.updateInitDisplaySize();
            }

            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        };
        this.mSurfaceCallback = new SurfaceHolder.Callback2() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass4 */

            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                VELogUtil.d(VEEditor.TAG, String.format("surfaceChanged: pixel format [%d], size [%d, %d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
                int unused = VEEditor.this.mSurfaceWidth = i2;
                int unused2 = VEEditor.this.mSurfaceHeight = i3;
                VEEditor.this.updateInitDisplaySize();
                VEEditor.this.onSurfaceChanged(i2, i3);
            }

            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                VEEditor.this.onSurfaceCreated(surfaceHolder.getSurface());
            }

            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                VEEditor.this.onSurfaceDestroyed();
            }

            public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
                VELogUtil.d(VEEditor.TAG, "surfaceRedrawNeeded...");
            }
        };
        this.mOpenGLCallback = new NativeCallbacks.IOpenGLCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass5 */

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLCreate(int i) {
                VELogUtil.d(VEEditor.TAG, "onOpenGLCreate: ret = " + i);
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLDestroy(int i) {
                VELogUtil.d(VEEditor.TAG, "onOpenGLDestroy: ret = " + i);
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLDrawAfter(int i, double d2) {
                VELogUtil.v(VEEditor.TAG, "onOpenGLDrawing: tex = " + i + " timeStamp = " + d2);
                if (VEEditor.this.miFrameCount == 0) {
                    TEMonitor.perfLong(1, TEMonitorNewKeys.TE_EDIT_FIRST_FRAME_TIME, System.currentTimeMillis() - VEEditor.this.mlLastTimeMS);
                    if (VEEditor.this.mFirstFrameListener != null) {
                        VEEditor.this.mFirstFrameListener.onRendered();
                    }
                }
                VEEditor.access$1804(VEEditor.this);
                if (VEEditor.this.miFrameCount == 30) {
                    long unused = VEEditor.this.mlCurTimeMS = System.currentTimeMillis();
                    if (VEEditor.this.mlLastTimeMS != VEEditor.this.mlCurTimeMS) {
                        float access$2100 = 30000.0f / ((float) (VEEditor.this.mlCurTimeMS - VEEditor.this.mlLastTimeMS));
                        VELogUtil.d(VEEditor.TAG, "Render FPS = " + access$2100);
                        VEEditor vEEditor = VEEditor.this;
                        long unused2 = vEEditor.mlLastTimeMS = vEEditor.mlCurTimeMS;
                        int unused3 = VEEditor.this.miFrameCount = 0;
                    }
                }
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLDrawBefore(int i, double d2) {
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onPreviewSurface(int i) {
                return 0;
            }
        };
        this.mEncoderDataCallback = new NativeCallbacks.IEncoderDataCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass6 */

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IEncoderDataCallback
            public int onCompressBuffer(byte[] bArr, int i, int i2, boolean z) {
                if (bArr == null || i < 0 || i2 <= 0) {
                    return -1;
                }
                if (VEEditor.this.mEncoderListener == null) {
                    return -2;
                }
                VEEditor.this.mEncoderListener.onEncoderDataAvailable(bArr, i, i2, z);
                return 0;
            }
        };
        this.mGetImageCallback = new NativeCallbacks.IGetImageCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass7 */

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IGetImageCallback
            public int onImageData(byte[] bArr, int i, int i2, int i3) {
                if (VEEditor.this.mGetImageListener == null) {
                    return -100;
                }
                if (bArr != null) {
                    return VEEditor.this.mGetImageListener.onGetImageData(bArr, i, i2, i3);
                }
                Message message = new Message();
                message.what = VECommonCallbackInfo.TE_INFO_GET_IMAGE_DONE;
                VEEditor.this.mMessageHandler.sendMessage(message);
                return 0;
            }
        };
        if (!TextUtils.isEmpty(str)) {
            VELogUtil.i(TAG, "VEEditor surfaceView");
            this.mResManager = new VEEditorResManager(str);
            this.mSurfaceView = surfaceView;
            surfaceView.getHolder().addCallback(this.mSurfaceCallback);
            this.mVideoEditor.setOpenGLListeners(this.mOpenGLCallback);
            this.mVideoEditor.setInfoListener(this.mTeCommonInfoCallback);
            this.mVideoEditor.setErrorListener(this.mTeCommonErrorCallback);
            return;
        }
        throw new VEException(-100, "workspace is: " + str);
    }

    public VEEditor(String str, SurfaceView surfaceView, @NonNull LifecycleOwner lifecycleOwner) {
        this(str, surfaceView);
        VELogUtil.i(TAG, "VEEditor surfaceView LifecycleOwner");
        lifecycleOwner.getLifecycle().addObserver(this);
        this.mVideoEditor.setInfoListener(this.mTeCommonInfoCallback);
        this.mVideoEditor.setErrorListener(this.mTeCommonErrorCallback);
    }

    public VEEditor(String str, TextureView textureView) throws VEException {
        this.mInitSize = new VESize(-1, -1);
        this.mCompileType = "mp4";
        this.mMessageHandler = new VEEditorMessageHandler(Looper.getMainLooper());
        this.mSeekListener = null;
        this.mCompileListener = null;
        this.mFirstFrameListener = null;
        this.mUserCommonInfoCallback = null;
        this.mUserCommonErrorCallback = null;
        this.mTrackIndexManager = new TETrackIndexManager();
        this.mTeCommonInfoCallback = new TECommonCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass1 */

            @Override // com.ss.android.ttve.common.TECommonCallback
            public void onCallback(int i, int i2, float f2, String str) {
                if (i != 4101) {
                    if (i != 4103) {
                        if (i != 4105) {
                            if (VEEditor.this.mUserCommonInfoCallback != null) {
                                VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                            }
                        } else if (VEEditor.this.mCompileListener != null) {
                            Message message = new Message();
                            message.what = i;
                            message.obj = Float.valueOf(f2);
                            VEEditor.this.mMessageHandler.sendMessage(message);
                        } else if (VEEditor.this.mUserCommonInfoCallback != null) {
                            VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                        }
                    } else if (VEEditor.this.mBCompileHighQualityGif) {
                        VEEditor.this.mp4ToGIFConverter.setCallback(VEEditor.this.mUserCommonInfoCallback);
                        new Thread(VEEditor.this.mp4ToGIFConverter).start();
                        boolean unused = VEEditor.this.mBCompileHighQualityGif = false;
                    } else {
                        if (i2 == 1 || i2 == 0) {
                            VEEditor.this.onMonitorCompile();
                        }
                        if (VEEditor.this.mCompileListener != null) {
                            Message message2 = new Message();
                            message2.what = 4103;
                            message2.arg1 = i2;
                            VEEditor.this.mMessageHandler.sendMessage(message2);
                        } else if (VEEditor.this.mUserCommonInfoCallback != null) {
                            VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                        }
                    }
                } else if (VEEditor.this.mSeekListener != null) {
                    VEEditor.this.mMessageHandler.sendEmptyMessage(4101);
                } else if (VEEditor.this.mUserCommonInfoCallback != null) {
                    VEEditor.this.mUserCommonInfoCallback.onCallback(i, i2, f2, str);
                }
            }
        };
        this.mTeCommonErrorCallback = new TECommonCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass2 */

            @Override // com.ss.android.ttve.common.TECommonCallback
            public void onCallback(final int i, final int i2, final float f2, final String str) {
                VEEditor.this.onMonitorError();
                if (VEEditor.this.mUserCommonErrorCallback != null) {
                    VEEditor.this.mUserCommonErrorCallback.onCallback(i, i2, f2, str);
                }
                if (VEEditor.this.mCompileListener != null) {
                    VEEditor.this.mMessageHandler.post(new Runnable() {
                        /* class com.ss.android.vesdk.VEEditor.AnonymousClass2.AnonymousClass1 */

                        public void run() {
                            if (VEEditor.this.mCompileListener != null) {
                                VEEditor.this.mCompileListener.onCompileError(i, i2, f2, str);
                            }
                        }
                    });
                }
            }
        };
        this.mMVBackgroundAudioRid = 0;
        this.mMVBackgroundAudioTrackIndex = -1;
        this.mSurfaceWidth = 0;
        this.mSurfaceHeight = 0;
        this.mInitDisplayWidth = 0;
        this.mInitDisplayHeight = 0;
        this.mbSeparateAV = false;
        this.mAudioEffectfilterIndex = -1;
        this.mMasterTrackIndex = 0;
        this.mVideoEditor = TEInterface.createEngine();
        this.miFrameCount = 0;
        this.mlCurTimeMS = 0;
        this.mlLastTimeMS = 0;
        this.mInPoint = 0;
        this.mOutPoint = -1;
        this.mInitSuccess = false;
        this.mReverseDone = false;
        this.mCancelReverse = false;
        this.mColorFilterIndex = -1;
        this.mMusicSRTEffectFilterIndex = -1;
        this.mOutputFile = null;
        this.mlCompileStartTime = 0;
        this.mBReversePlay = false;
        this.mBCompileHighQualityGif = false;
        this.mp4ToGIFConverter = null;
        this.mEncoderListener = null;
        this.mGetImageListener = null;
        this.mTEMonitorFilterMgr = new TEMonitorFilterMgr();
        this.mReDrawSurface = false;
        this.mFirstTimeSurfaceCreate = true;
        this.mReDrawSurfaceOnce = false;
        this.mCurrentBmp = null;
        this.rotate = 0.0f;
        this.scaleW = 1.0f;
        this.scaleH = 1.0f;
        this.mPageMode = -1;
        this.mBackGroundColor = ViewCompat.MEASURED_STATE_MASK;
        this.mTextureListener = new TextureView.SurfaceTextureListener() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass3 */

            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                if (VEEditor.this.mSurfaceTexture == surfaceTexture) {
                    VEEditor vEEditor = VEEditor.this;
                    vEEditor.onSurfaceCreated(vEEditor.mSurface);
                } else {
                    Surface unused = VEEditor.this.mSurface = new Surface(surfaceTexture);
                    VEEditor vEEditor2 = VEEditor.this;
                    vEEditor2.onSurfaceCreated(vEEditor2.mSurface);
                }
                SurfaceTexture unused2 = VEEditor.this.mSurfaceTexture = surfaceTexture;
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                VEEditor.this.onSurfaceDestroyed();
                VEEditor.this.mSurface.release();
                return true;
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
                int unused = VEEditor.this.mSurfaceWidth = i;
                int unused2 = VEEditor.this.mSurfaceHeight = i2;
                VEEditor.this.updateInitDisplaySize();
            }

            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        };
        this.mSurfaceCallback = new SurfaceHolder.Callback2() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass4 */

            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                VELogUtil.d(VEEditor.TAG, String.format("surfaceChanged: pixel format [%d], size [%d, %d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
                int unused = VEEditor.this.mSurfaceWidth = i2;
                int unused2 = VEEditor.this.mSurfaceHeight = i3;
                VEEditor.this.updateInitDisplaySize();
                VEEditor.this.onSurfaceChanged(i2, i3);
            }

            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                VEEditor.this.onSurfaceCreated(surfaceHolder.getSurface());
            }

            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                VEEditor.this.onSurfaceDestroyed();
            }

            public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
                VELogUtil.d(VEEditor.TAG, "surfaceRedrawNeeded...");
            }
        };
        this.mOpenGLCallback = new NativeCallbacks.IOpenGLCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass5 */

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLCreate(int i) {
                VELogUtil.d(VEEditor.TAG, "onOpenGLCreate: ret = " + i);
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLDestroy(int i) {
                VELogUtil.d(VEEditor.TAG, "onOpenGLDestroy: ret = " + i);
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLDrawAfter(int i, double d2) {
                VELogUtil.v(VEEditor.TAG, "onOpenGLDrawing: tex = " + i + " timeStamp = " + d2);
                if (VEEditor.this.miFrameCount == 0) {
                    TEMonitor.perfLong(1, TEMonitorNewKeys.TE_EDIT_FIRST_FRAME_TIME, System.currentTimeMillis() - VEEditor.this.mlLastTimeMS);
                    if (VEEditor.this.mFirstFrameListener != null) {
                        VEEditor.this.mFirstFrameListener.onRendered();
                    }
                }
                VEEditor.access$1804(VEEditor.this);
                if (VEEditor.this.miFrameCount == 30) {
                    long unused = VEEditor.this.mlCurTimeMS = System.currentTimeMillis();
                    if (VEEditor.this.mlLastTimeMS != VEEditor.this.mlCurTimeMS) {
                        float access$2100 = 30000.0f / ((float) (VEEditor.this.mlCurTimeMS - VEEditor.this.mlLastTimeMS));
                        VELogUtil.d(VEEditor.TAG, "Render FPS = " + access$2100);
                        VEEditor vEEditor = VEEditor.this;
                        long unused2 = vEEditor.mlLastTimeMS = vEEditor.mlCurTimeMS;
                        int unused3 = VEEditor.this.miFrameCount = 0;
                    }
                }
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onOpenGLDrawBefore(int i, double d2) {
                return 0;
            }

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IOpenGLCallback
            public int onPreviewSurface(int i) {
                return 0;
            }
        };
        this.mEncoderDataCallback = new NativeCallbacks.IEncoderDataCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass6 */

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IEncoderDataCallback
            public int onCompressBuffer(byte[] bArr, int i, int i2, boolean z) {
                if (bArr == null || i < 0 || i2 <= 0) {
                    return -1;
                }
                if (VEEditor.this.mEncoderListener == null) {
                    return -2;
                }
                VEEditor.this.mEncoderListener.onEncoderDataAvailable(bArr, i, i2, z);
                return 0;
            }
        };
        this.mGetImageCallback = new NativeCallbacks.IGetImageCallback() {
            /* class com.ss.android.vesdk.VEEditor.AnonymousClass7 */

            @Override // com.ss.android.ttve.nativePort.NativeCallbacks.IGetImageCallback
            public int onImageData(byte[] bArr, int i, int i2, int i3) {
                if (VEEditor.this.mGetImageListener == null) {
                    return -100;
                }
                if (bArr != null) {
                    return VEEditor.this.mGetImageListener.onGetImageData(bArr, i, i2, i3);
                }
                Message message = new Message();
                message.what = VECommonCallbackInfo.TE_INFO_GET_IMAGE_DONE;
                VEEditor.this.mMessageHandler.sendMessage(message);
                return 0;
            }
        };
        if (!TextUtils.isEmpty(str)) {
            VELogUtil.i(TAG, "VEEditor textureView");
            this.mResManager = new VEEditorResManager(str);
            this.mTextureView = textureView;
            textureView.setSurfaceTextureListener(this.mTextureListener);
            this.mVideoEditor.setOpenGLListeners(this.mOpenGLCallback);
            this.mVideoEditor.setInfoListener(this.mTeCommonInfoCallback);
            this.mVideoEditor.setErrorListener(this.mTeCommonErrorCallback);
            return;
        }
        throw new VEException(-100, "workspace is: " + str);
    }

    public VEEditor(String str, TextureView textureView, @NonNull LifecycleOwner lifecycleOwner) {
        this(str, textureView);
        VELogUtil.i(TAG, "VEEditor TextureView LifecycleOwner");
        lifecycleOwner.getLifecycle().addObserver(this);
        this.mVideoEditor.setInfoListener(this.mTeCommonInfoCallback);
        this.mVideoEditor.setErrorListener(this.mTeCommonErrorCallback);
    }

    private boolean _compile(String str, String str2, VEVideoEncodeSettings vEVideoEncodeSettings) throws VEException {
        synchronized (this) {
            if (!this.mInitSuccess) {
                throw new VEException(VEResult.TER_INVALID_STAT, "编码前需确保初始化成功！！！");
            } else if (this.mVideoEditor.getNativeHandler() == 0) {
                return false;
            } else {
                VERuntime.getInstance().updateVideoEncodeSettings(vEVideoEncodeSettings);
                String str3 = str;
                this.mOutputFile = str3;
                this.mlCompileStartTime = System.currentTimeMillis();
                if (this.mReDrawSurface) {
                    VERect displayRect = this.mVideoEditor.getDisplayRect();
                    if (displayRect.width == 0 || displayRect.height == 0) {
                        this.mCurrentBmp = null;
                    } else {
                        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(displayRect.width * displayRect.height * 4);
                        this.mVideoEditor.getDisplayImage(allocateDirect.array(), displayRect.width, displayRect.height);
                        this.mCurrentBmp = Bitmap.createBitmap(displayRect.width, displayRect.height, Bitmap.Config.ARGB_8888);
                        this.mCurrentBmp.copyPixelsFromBuffer(allocateDirect);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(this.rotate);
                        matrix.postScale(this.scaleW, this.scaleH);
                        this.mCurrentBmp = Bitmap.createBitmap(this.mCurrentBmp, 0, 0, this.mCurrentBmp.getWidth(), this.mCurrentBmp.getHeight(), matrix, true);
                    }
                }
                VELogUtil.w(TAG, "compile...");
                this.mVideoEditor.stop();
                int i = AnonymousClass10.$SwitchMap$com$ss$android$vesdk$VEVideoEncodeSettings$COMPILE_TYPE[vEVideoEncodeSettings.getCompileType().ordinal()];
                if (i == 1) {
                    this.mVideoEditor.setCompileType(1);
                    this.mCompileType = "mp4";
                } else if (i == 2) {
                    this.mVideoEditor.setCompileType(2);
                    this.mCompileType = MVConsts.TYPE_GIF;
                } else if (i != 3) {
                    this.mVideoEditor.setCompileType(1);
                    this.mCompileType = "mp4";
                } else if (this.mp4ToGIFConverter != null && this.mp4ToGIFConverter.mIsRunning) {
                    return false;
                } else {
                    this.mBCompileHighQualityGif = true;
                    this.mVideoEditor.setCompileType(4);
                    if (this.mp4ToGIFConverter == null) {
                        this.mp4ToGIFConverter = new Mp4ToHighQualityGIFConverter();
                    }
                    str3 = new File(this.mOutputFile).getParent() + File.separatorChar + "gif.mp4";
                    this.mp4ToGIFConverter.setInputFile(str3);
                    this.mp4ToGIFConverter.setOutputFile(this.mOutputFile);
                    this.mp4ToGIFConverter.setWaterMarkFile(this.waterMarkFile);
                    this.mp4ToGIFConverter.setWaterMarkWidth((int) (this.waterMarkWidth * ((double) vEVideoEncodeSettings.getVideoRes().width)));
                    this.mp4ToGIFConverter.setWaterMarkHeight((int) (this.waterMarkHeight * ((double) vEVideoEncodeSettings.getVideoRes().height)));
                    this.mp4ToGIFConverter.setWaterMarkOffsetX((int) (this.waterMarkOffsetX * ((double) vEVideoEncodeSettings.getVideoRes().width)));
                    this.mp4ToGIFConverter.setWaterMarkOffsetY((int) (this.waterMarkOffsetY * ((double) vEVideoEncodeSettings.getVideoRes().height)));
                    this.mCompileType = "high_gif";
                }
                this.mVideoEditor.setCompileFps(vEVideoEncodeSettings.getFps());
                this.mVideoEditor.setEngineCompilePath(str3, str2);
                this.mVideoEditor.setResizer(vEVideoEncodeSettings.getResizeMode(), vEVideoEncodeSettings.getResizeX(), vEVideoEncodeSettings.getResizeY());
                this.mVideoEditor.setUsrRotate(vEVideoEncodeSettings.getRotate());
                this.mVideoEditor.setSpeedRatio(vEVideoEncodeSettings.getSpeed());
                this.mVideoEditor.setEnableRemuxVideo(vEVideoEncodeSettings.isEnableRemuxVideo());
                this.mVideoEditor.setEnableInterLeave(vEVideoEncodeSettings.isEnableInterLeave());
                if (this.mEncoderListener != null) {
                    this.mVideoEditor.setEncoderParallel(true);
                    this.mVideoEditor.setEncoderDataListener(this.mEncoderDataCallback);
                } else {
                    this.mVideoEditor.setEncoderParallel(false);
                    this.mVideoEditor.setEncoderDataListener(null);
                }
                this.mVideoEditor.setWidthHeight(vEVideoEncodeSettings.getVideoRes().width, vEVideoEncodeSettings.getVideoRes().height);
                VEWatermarkParam watermarkParam = vEVideoEncodeSettings.getWatermarkParam();
                if (watermarkParam == null || !watermarkParam.needExtFile) {
                    if (watermarkParam != null) {
                        this.mVideoEditor.setEnableRemuxVideo(false);
                    }
                    if (this.mVideoEditor.prepareEngine(1) != 0) {
                        onMonitorError();
                        return false;
                    }
                } else {
                    this.mVideoEditor.setCompileWatermark(watermarkParam);
                    if (this.mVideoEditor.prepareEngine(2) != 0) {
                        onMonitorError();
                        return false;
                    }
                }
                if (watermarkParam != null) {
                    ArrayList<String[]> arrayList = new ArrayList<>();
                    arrayList.add(watermarkParam.images);
                    if (watermarkParam.secondHalfImages != null) {
                        arrayList.add(watermarkParam.secondHalfImages);
                    }
                    this.mVideoEditor.setWaterMark(arrayList, watermarkParam.interval, watermarkParam.xOffset, watermarkParam.yOffset, watermarkParam.width, watermarkParam.height, watermarkParam.duration, watermarkParam.position, watermarkParam.mask);
                    TEMonitor.perfLong(1, TEMonitorNewKeys.TE_COMPOSITION_WATERMARK_ADD, 1);
                } else {
                    TEMonitor.perfLong(1, TEMonitorNewKeys.TE_COMPOSITION_WATERMARK_ADD, 0);
                }
                this.mVideoEditor.start();
                VEKeyValue vEKeyValue = new VEKeyValue();
                vEKeyValue.add("iesve_veeditor_composition_start_file", this.mCompileType);
                MonitorUtils.monitorStatistics("iesve_veeditor_composition_start", 1, vEKeyValue);
                return true;
            }
        }
    }

    static /* synthetic */ int access$1804(VEEditor vEEditor) {
        int i = vEEditor.miFrameCount + 1;
        vEEditor.miFrameCount = i;
        return i;
    }

    private int addSticker(Bitmap bitmap, VESize vESize, int i, int i2) {
        return 0;
    }

    private int addWaterMark(Bitmap[] bitmapArr, int i, VESize vESize, int i2, int i3) {
        return 0;
    }

    private List<List<List<MVResourceBean>>> genMVResourceTracks(MVInfoBean mVInfoBean, List<String> list, List<String> list2) {
        ArrayList<MVResourceBean> arrayList;
        ArrayList arrayList2;
        double d2;
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        arrayList3.add(arrayList4);
        arrayList3.add(arrayList5);
        ArrayList<MVResourceBean> arrayList6 = mVInfoBean.resources;
        ArrayList arrayList7 = new ArrayList();
        while (arrayList7.size() != arrayList6.size()) {
            ArrayList arrayList8 = new ArrayList();
            double d3 = 0.0d;
            double d4 = 0.0d;
            for (MVResourceBean mVResourceBean : arrayList6) {
                if (!arrayList7.contains(Integer.valueOf(mVResourceBean.rid))) {
                    if ("video".equals(mVResourceBean.type) || MVConsts.TYPE_IMG.equals(mVResourceBean.type)) {
                        arrayList2 = arrayList5;
                        arrayList = arrayList6;
                        if (mVResourceBean.seqIn >= d3) {
                            MVResourceBean mVResourceBean2 = new MVResourceBean();
                            mVResourceBean2.seqIn = mVResourceBean.seqIn * 1000.0d;
                            mVResourceBean2.seqOut = mVResourceBean.seqOut * 1000.0d;
                            mVResourceBean2.trimIn = mVResourceBean.trimIn * 1000.0d;
                            mVResourceBean2.trimOut = mVResourceBean.trimOut * 1000.0d;
                            if (!MVConsts.TYPE_IMG.equals(mVResourceBean.type)) {
                                d2 = d4;
                            } else if (mVResourceBean2.trimOut == 0.0d) {
                                d2 = d4;
                                mVResourceBean2.trimOut = mVResourceBean2.seqOut - mVResourceBean2.seqIn;
                            } else {
                                d2 = d4;
                            }
                            mVResourceBean2.content = mVResourceBean.content;
                            mVResourceBean2.type = mVResourceBean.type;
                            mVResourceBean2.rid = mVResourceBean.rid;
                            arrayList8.add(mVResourceBean2);
                            double d5 = mVResourceBean.seqOut;
                            arrayList7.add(Integer.valueOf(mVResourceBean2.rid));
                            list.add(mVResourceBean2.content);
                            d3 = d5;
                        } else {
                            d2 = d4;
                        }
                    } else if (!MVConsts.TYPE_AUDIO.equals(mVResourceBean.type) || mVResourceBean.seqIn < d4) {
                        arrayList2 = arrayList5;
                        arrayList = arrayList6;
                        d2 = d4;
                    } else {
                        ArrayList arrayList9 = new ArrayList();
                        MVResourceBean mVResourceBean3 = new MVResourceBean();
                        mVResourceBean3.seqIn = mVResourceBean.seqIn * 1000.0d;
                        mVResourceBean3.seqOut = mVResourceBean.seqOut * 1000.0d;
                        mVResourceBean3.trimIn = mVResourceBean.trimIn * 1000.0d;
                        mVResourceBean3.trimOut = mVResourceBean.trimOut * 1000.0d;
                        mVResourceBean3.content = mVResourceBean.content;
                        mVResourceBean3.type = mVResourceBean.type;
                        mVResourceBean3.rid = mVResourceBean.rid;
                        if (this.mMVBackgroundAudioRid == 0) {
                            this.mMVBackgroundAudioRid = mVResourceBean3.rid;
                        }
                        arrayList9.add(mVResourceBean3);
                        d4 = mVResourceBean.seqOut;
                        arrayList7.add(Integer.valueOf(mVResourceBean3.rid));
                        list2.add(mVResourceBean3.content);
                        if (arrayList9.size() > 0) {
                            arrayList5.add(arrayList9);
                        }
                        arrayList2 = arrayList5;
                        arrayList = arrayList6;
                        arrayList6 = arrayList;
                        arrayList5 = arrayList2;
                    }
                    d4 = d2;
                    arrayList6 = arrayList;
                    arrayList5 = arrayList2;
                }
            }
            if (arrayList8.size() > 0) {
                arrayList4.add(arrayList8);
            }
            arrayList6 = arrayList6;
            arrayList5 = arrayList5;
        }
        return arrayList3;
    }

    private void genResourcesArr(List<MVResourceBean> list, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, String[] strArr, int[] iArr5) {
        int i = 0;
        for (MVResourceBean mVResourceBean : list) {
            iArr[i] = (int) mVResourceBean.trimIn;
            iArr2[i] = (int) mVResourceBean.trimOut;
            iArr3[i] = (int) mVResourceBean.seqIn;
            iArr4[i] = (int) mVResourceBean.seqOut;
            strArr[i] = mVResourceBean.content;
            iArr5[i] = mVResourceBean.rid;
            i++;
        }
    }

    public static synchronized VEEditor genReverseVideo(final VEEditorResManager vEEditorResManager, VETimelineParams vETimelineParams, int i, int i2, final VEListener.VEEditorGenReverseListener vEEditorGenReverseListener) {
        VEEditor vEEditor;
        synchronized (VEEditor.class) {
            VELogUtil.w(TAG, "genReverseVideo with param:startTime:" + i + "endTime:" + i2);
            vEEditor = new VEEditor(vEEditorResManager.getWorkspace());
            vEEditorResManager.mReverseDone = false;
            float[] fArr = new float[vETimelineParams.speed.length];
            for (int i3 = 0; i3 < vETimelineParams.speed.length; i3++) {
                fArr[i3] = (float) vETimelineParams.speed[i3];
            }
            vEEditor.init2(vETimelineParams.videoFilePaths, vETimelineParams.vTrimIn, vETimelineParams.vTrimOut, null, vETimelineParams.audioFilePaths, vETimelineParams.aTrimIn, vETimelineParams.aTrimOut, fArr, vETimelineParams.rotate, VIDEO_RATIO.VIDEO_OUT_RATIO_ORIGINAL);
            VEVideoEncodeSettings build = new VEVideoEncodeSettings.Builder(2).setVideoRes(-1, -1).setFps(30).setHwEnc(false).setSWCRF(13).setGopSize(1).setWatermarkParam(null).setEncodeProfile(VEVideoEncodeSettings.ENCODE_PROFILE.ENCODE_PROFILE_BASELINE).setEncodePreset(VEVideoEncodeSettings.ENCODE_PRESET.ENCODE_LEVEL_ULTRAFAST).build();
            vEEditor.setTimeRange(i, i2, SET_RANGE_MODE.EDITOR_TIMERANGE_FLAG_BEFORE_SPEED);
            final String genReverseVideoPath = vEEditorResManager.genReverseVideoPath(0);
            final String genReverseVideoPath2 = vEEditorResManager.genReverseVideoPath(1);
            final String genSeqAudioPath = vEEditorResManager.genSeqAudioPath(0);
            vEEditor.setOnInfoListener(new VECommonCallback(vEEditor) {
                /* class com.ss.android.vesdk.VEEditor.AnonymousClass8 */
                final /* synthetic */ VEEditor val$mVideoCompiler;

                {
                    this.val$mVideoCompiler = r1;
                }

                @Override // com.ss.android.vesdk.VECommonCallback
                public void onCallback(int i, int i2, float f2, String str) {
                    if (i == 4103 && this.val$mVideoCompiler.isValid()) {
                        TEVideoUtils.reverseAllIVideo(genReverseVideoPath, genReverseVideoPath2);
                        new Thread(new Runnable() {
                            /* class com.ss.android.vesdk.VEEditor.AnonymousClass8.AnonymousClass1 */

                            public void run() {
                                AnonymousClass8.this.val$mVideoCompiler.destroy();
                                AnonymousClass8 r5 = AnonymousClass8.this;
                                VEEditorResManager vEEditorResManager = vEEditorResManager;
                                vEEditorResManager.mVideoPaths = new String[]{genReverseVideoPath};
                                vEEditorResManager.mReverseAudioPaths = new String[]{genSeqAudioPath};
                                vEEditorResManager.mReverseVideoPath = new String[]{genReverseVideoPath2};
                                vEEditorResManager.mReverseDone = true;
                                VEListener.VEEditorGenReverseListener vEEditorGenReverseListener = vEEditorGenReverseListener;
                                if (vEEditorGenReverseListener != null) {
                                    vEEditorGenReverseListener.onReverseDone(0);
                                }
                            }
                        }).start();
                    }
                }
            });
            vEEditor.setOnErrorListener(new VECommonCallback(vEEditor) {
                /* class com.ss.android.vesdk.VEEditor.AnonymousClass9 */
                final /* synthetic */ VEEditor val$mVideoCompiler;

                {
                    this.val$mVideoCompiler = r1;
                }

                @Override // com.ss.android.vesdk.VECommonCallback
                public void onCallback(int i, int i2, float f2, String str) {
                    new Thread(new Runnable() {
                        /* class com.ss.android.vesdk.VEEditor.AnonymousClass9.AnonymousClass1 */

                        public void run() {
                            AnonymousClass9.this.val$mVideoCompiler.destroy();
                            VEListener.VEEditorGenReverseListener vEEditorGenReverseListener = vEEditorGenReverseListener;
                            if (vEEditorGenReverseListener != null) {
                                vEEditorGenReverseListener.onReverseDone(-1);
                            }
                        }
                    }).start();
                }
            });
            vEEditor.compile(genReverseVideoPath, genSeqAudioPath, build);
        }
        return vEEditor;
    }

    /* access modifiers changed from: private */
    public void onMonitorCompile() {
        long currentTimeMillis = System.currentTimeMillis() - this.mlCompileStartTime;
        TEMonitor.perfLong("te_composition_time", currentTimeMillis);
        TEMonitor.perfLong(1, "te_composition_time", currentTimeMillis);
        if (com.ss.android.medialib.FileUtils.checkFileExists(this.mOutputFile)) {
            int[] iArr = new int[10];
            if (TEVideoUtils.getVideoFileInfo(this.mOutputFile, iArr) == 0) {
                long length = new File(this.mOutputFile).length();
                TEMonitor.perfLong("te_composition_page_mode", (long) this.mPageMode);
                double d2 = (((double) length) / 1024.0d) / 1024.0d;
                TEMonitor.perfDouble("te_composition_file_size", d2);
                TEMonitor.perfDouble("te_composition_file_duration", (double) iArr[3]);
                TEMonitor.perfDouble("te_composition_bit_rate", (double) iArr[6]);
                TEMonitor.perfDouble("te_composition_fps", (double) iArr[7]);
                TEMonitor.perfString("te_composition_resolution", "" + iArr[0] + "x" + iArr[1]);
                TEMonitor.perfLong(1, "te_composition_page_mode", (long) this.mPageMode);
                TEMonitor.perfString(1, "te_composition_resolution", "" + iArr[0] + "x" + iArr[1]);
                TEMonitor.perfDouble(1, "te_composition_fps", (double) iArr[7]);
                TEMonitor.perfDouble(1, "te_composition_bit_rate", (double) iArr[6]);
                TEMonitor.perfDouble(1, "te_composition_file_duration", (double) iArr[3]);
                TEMonitor.perfDouble(1, "te_composition_file_size", d2);
                int timeEffectType = this.mTEMonitorFilterMgr.getTimeEffectType();
                if (timeEffectType != 0) {
                    TEMonitor.perfLong(1, TEMonitorNewKeys.TE_COMPOSITION_TIME_FILTER_TYPE, (long) timeEffectType);
                }
            }
        }
        boolean isEffectAdd = this.mTEMonitorFilterMgr.isEffectAdd();
        long j = 0;
        TEMonitor.perfLong(1, TEMonitorNewKeys.TE_COMPOSITION_EFFECT_ADD, isEffectAdd ? 0 : 1);
        if (!isEffectAdd) {
            TEMonitor.perfString(1, TEMonitorNewKeys.TE_COMPOSITION_EFFECT_JSON, this.mTEMonitorFilterMgr.serializeMap(0));
        }
        boolean isInfoStickerAdd = this.mTEMonitorFilterMgr.isInfoStickerAdd();
        TEMonitor.perfLong(1, TEMonitorNewKeys.TE_COMPOSITION_INFO_STICKER_ADD, isInfoStickerAdd ? 0 : 1);
        if (!isInfoStickerAdd) {
            TEMonitor.perfString(1, TEMonitorNewKeys.TE_COMPOSITION_INFO_STICKER_JSON, this.mTEMonitorFilterMgr.serializeMap(1));
        }
        if (!is2DBrushEmpty()) {
            j = 1;
        }
        TEMonitor.perfLong(1, TEMonitorNewKeys.TE_COMPOSITION_BRUSH_ADD, j);
        this.mTEMonitorFilterMgr.reset();
        TEMonitor.report(TEMonitor.MONITOR_ACTION_COMPILE);
        TEMonitor.perfString(1, "iesve_veeditor_composition_finish_file", this.mCompileType);
        TEMonitor.perfString(1, "iesve_veeditor_composition_finish_result", "succ");
        TEMonitor.perfString(1, "iesve_veeditor_composition_finish_reason", "");
        TEMonitor.reportWithType(1);
    }

    /* access modifiers changed from: private */
    public void onMonitorError() {
        boolean isEffectAdd = this.mTEMonitorFilterMgr.isEffectAdd();
        long j = 0;
        TEMonitor.perfLong(1, TEMonitorNewKeys.TE_COMPOSITION_EFFECT_ADD, isEffectAdd ? 0 : 1);
        if (!isEffectAdd) {
            TEMonitor.perfString(1, TEMonitorNewKeys.TE_COMPOSITION_EFFECT_JSON, this.mTEMonitorFilterMgr.serializeMap(0));
        }
        boolean isInfoStickerAdd = this.mTEMonitorFilterMgr.isInfoStickerAdd();
        TEMonitor.perfLong(1, TEMonitorNewKeys.TE_COMPOSITION_INFO_STICKER_ADD, isInfoStickerAdd ? 0 : 1);
        if (!isInfoStickerAdd) {
            TEMonitor.perfString(1, TEMonitorNewKeys.TE_COMPOSITION_INFO_STICKER_JSON, this.mTEMonitorFilterMgr.serializeMap(1));
        }
        if (!is2DBrushEmpty()) {
            j = 1;
        }
        TEMonitor.perfLong(1, TEMonitorNewKeys.TE_COMPOSITION_BRUSH_ADD, j);
        this.mTEMonitorFilterMgr.reset();
        TEMonitor.perfString(1, "iesve_veeditor_composition_finish_file", this.mCompileType);
        TEMonitor.perfString(1, "iesve_veeditor_composition_finish_result", "fail");
        TEMonitor.perfString(1, "iesve_veeditor_composition_finish_reason", "");
        TEMonitor.reportWithType(1);
    }

    /* access modifiers changed from: private */
    public void onSurfaceChanged(int i, int i2) {
        VELogUtil.i(TAG, "onSurfaceChanged...");
        if (i != 0 && i2 != 0) {
            this.mVideoEditor.setSurfaceSize(i, i2);
        }
    }

    /* access modifiers changed from: private */
    public void onSurfaceCreated(Surface surface) {
        Rect rect;
        VELogUtil.i(TAG, "surfaceCreated...");
        this.mFirstTimeSurfaceCreate = false;
        if (this.mReDrawSurface && this.mCurrentBmp != null) {
            Canvas lockCanvas = surface.lockCanvas(null);
            int width = lockCanvas.getWidth();
            int height = lockCanvas.getHeight();
            int width2 = this.mCurrentBmp.getWidth();
            int height2 = this.mCurrentBmp.getHeight();
            VELogUtil.i(TAG, "width " + width + " height " + height + " image width " + width2 + " image height " + height2);
            float f2 = (float) width;
            float f3 = (float) height;
            float f4 = ((float) width2) / ((float) height2);
            if (f4 > f2 / f3) {
                int i = (height - ((int) (f2 / f4))) / 2;
                rect = new Rect(0, i, width, height - i);
            } else {
                int i2 = (width - ((int) (f3 * f4))) / 2;
                rect = new Rect(i2, 0, width - i2, height);
            }
            lockCanvas.drawBitmap(this.mCurrentBmp, (Rect) null, rect, (Paint) null);
            surface.unlockCanvasAndPost(lockCanvas);
            if (this.mReDrawSurfaceOnce) {
                Bitmap bitmap = this.mCurrentBmp;
                if (bitmap != null && !bitmap.isRecycled()) {
                    this.mCurrentBmp.recycle();
                    this.mCurrentBmp = null;
                }
                this.mReDrawSurfaceOnce = false;
            }
        }
        this.mVideoEditor.setPreviewSurface(surface);
    }

    /* access modifiers changed from: private */
    public void onSurfaceDestroyed() {
        VELogUtil.d(TAG, "surfaceDestroyed...");
        this.mVideoEditor.releasePreviewSurface();
    }

    private void setAudioEffectParam(int i, int i2, int i3, VEAudioEffectBean vEAudioEffectBean) {
        VELogUtil.w(TAG, "setAudioEffectParam...");
        TEInterface tEInterface = this.mVideoEditor;
        tEInterface.setFilterParam(i3, "audioEffectType", "" + vEAudioEffectBean.type);
        String str = "1";
        this.mVideoEditor.setFilterParam(i3, "formatShiftOn", vEAudioEffectBean.formatShiftOn ? str : "0");
        TEInterface tEInterface2 = this.mVideoEditor;
        if (!vEAudioEffectBean.smoothOn) {
            str = "0";
        }
        tEInterface2.setFilterParam(i3, "smoothOn", str);
        TEInterface tEInterface3 = this.mVideoEditor;
        tEInterface3.setFilterParam(i3, "processChMode", "" + vEAudioEffectBean.processChMode);
        TEInterface tEInterface4 = this.mVideoEditor;
        tEInterface4.setFilterParam(i3, "transientDetectMode", "" + vEAudioEffectBean.transientDetectMode);
        TEInterface tEInterface5 = this.mVideoEditor;
        tEInterface5.setFilterParam(i3, "phaseResetMode", "" + vEAudioEffectBean.phaseResetMode);
        TEInterface tEInterface6 = this.mVideoEditor;
        tEInterface6.setFilterParam(i3, "phaseAdjustMethod", "" + vEAudioEffectBean.phaseAdjustMethod);
        TEInterface tEInterface7 = this.mVideoEditor;
        tEInterface7.setFilterParam(i3, "windowMode", "" + vEAudioEffectBean.windowMode);
        TEInterface tEInterface8 = this.mVideoEditor;
        tEInterface8.setFilterParam(i3, "pitchTunerMode", "" + vEAudioEffectBean.pitchTunerMode);
        TEInterface tEInterface9 = this.mVideoEditor;
        tEInterface9.setFilterParam(i3, "blockSize", "" + vEAudioEffectBean.blockSize);
        TEInterface tEInterface10 = this.mVideoEditor;
        tEInterface10.setFilterParam(i3, "centtone", "" + vEAudioEffectBean.centtone);
        TEInterface tEInterface11 = this.mVideoEditor;
        tEInterface11.setFilterParam(i3, "semiton", "" + vEAudioEffectBean.semiton);
        TEInterface tEInterface12 = this.mVideoEditor;
        tEInterface12.setFilterParam(i3, "octative", "" + vEAudioEffectBean.octative);
        TEInterface tEInterface13 = this.mVideoEditor;
        tEInterface13.setFilterParam(i3, "speedRatio", "" + vEAudioEffectBean.speedRatio);
    }

    private void setBitrateOptions(VEVideoEncodeSettings vEVideoEncodeSettings) {
        this.mVideoEditor.setCompileSoftwareEncodeOptions(vEVideoEncodeSettings.getSwCRF(), vEVideoEncodeSettings.getSwMaxRate(), vEVideoEncodeSettings.getSwPreset(), vEVideoEncodeSettings.getSwQP());
        this.mVideoEditor.setCompileHardwareEncodeOptions(vEVideoEncodeSettings.getBps());
        this.mVideoEditor.setCompileCommonEncodeOptions(vEVideoEncodeSettings.getBitrateMode().ordinal(), vEVideoEncodeSettings.getEncodeProfile());
    }

    private int setColorFilter(String str, float f2, boolean z) {
        synchronized (this) {
            if (this.mColorFilterIndex < 0) {
                return VEResult.TER_INVALID_STAT;
            }
            if (f2 >= 0.0f) {
                if (str != null) {
                    if (f2 > 1.0f) {
                        f2 = 1.0f;
                    }
                    if (this.mCurColorFilter == null) {
                        this.mCurColorFilter = new FilterBean();
                    }
                    if (str.equals(this.mCurColorFilter.getLeftResPath()) && this.mCurColorFilter.getRightResPath().length() == 0 && this.mCurColorFilter.getIntensity() == f2 && this.mCurColorFilter.getPosition() == 1.0f && this.mCurColorFilter.useFilterResIntensity() == z) {
                        return 0;
                    }
                    this.mCurColorFilter.setLeftResPath(str);
                    this.mCurColorFilter.setRightResPath("");
                    this.mCurColorFilter.setPosition(1.0f);
                    this.mCurColorFilter.setIntensity(f2);
                    this.mCurColorFilter.setUseFilterResIntensity(z);
                    this.mVideoEditor.setFilterParam(this.mColorFilterIndex, FILTER_PATH_LEFT, str);
                    this.mVideoEditor.setFilterParam(this.mColorFilterIndex, USE_FILTER_RES_INTENSITY, String.valueOf(z));
                    this.mVideoEditor.setFilterParam(this.mColorFilterIndex, FILTER_INTENSITY, "" + f2);
                    this.mVideoEditor.setFilterParam(this.mColorFilterIndex, FILTER_PATH_RIGHT, "");
                    this.mVideoEditor.setFilterParam(this.mColorFilterIndex, FILTER_POSITION, "1.0");
                    VEKeyValue vEKeyValue = new VEKeyValue();
                    String str2 = "";
                    if (!TextUtils.isEmpty(str)) {
                        String[] split = str.split(File.separator);
                        if (split.length > 0) {
                            str2 = split[split.length - 1];
                        }
                    }
                    vEKeyValue.add("iesve_veeditor_set_filter_click_filter_id", str2);
                    MonitorUtils.monitorStatistics("iesve_veeditor_set_filter_click", 1, vEKeyValue);
                    TEMonitor.perfString(1, TEMonitorNewKeys.TE_COMPOSITION_FILTER_ID, str);
                    return 0;
                }
            }
            return -100;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0124  */
    private int setColorFilter(String str, String str2, float f2, float f3, boolean z) {
        String str3;
        if (this.mColorFilterIndex < 0) {
            return VEResult.TER_INVALID_STAT;
        }
        if (f3 < 0.0f || f2 < 0.0f || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return -100;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        if (this.mCurColorFilter == null) {
            this.mCurColorFilter = new FilterBean();
        }
        if (str.equals(this.mCurColorFilter.getLeftResPath()) && str2.equals(this.mCurColorFilter.getRightResPath()) && this.mCurColorFilter.getIntensity() == f3 && this.mCurColorFilter.getPosition() == f2 && this.mCurColorFilter.useFilterResIntensity() == z) {
            return 0;
        }
        this.mCurColorFilter.setLeftResPath(str);
        this.mCurColorFilter.setRightResPath(str2);
        this.mCurColorFilter.setPosition(f2);
        this.mCurColorFilter.setIntensity(f3);
        this.mCurColorFilter.setUseFilterResIntensity(z);
        VELogUtil.d(TAG, "leftFilterPath: " + str + "\nrightFilterPath: " + str2 + " position: " + f2 + " intensity: " + f3);
        this.mVideoEditor.setFilterParam(this.mColorFilterIndex, FILTER_PATH_LEFT, str);
        this.mVideoEditor.setFilterParam(this.mColorFilterIndex, USE_FILTER_RES_INTENSITY, String.valueOf(z));
        TEInterface tEInterface = this.mVideoEditor;
        int i = this.mColorFilterIndex;
        StringBuilder sb = new StringBuilder();
        String str4 = "";
        sb.append(str4);
        sb.append(f3);
        tEInterface.setFilterParam(i, FILTER_INTENSITY, sb.toString());
        this.mVideoEditor.setFilterParam(this.mColorFilterIndex, FILTER_PATH_RIGHT, str2);
        this.mVideoEditor.setFilterParam(this.mColorFilterIndex, FILTER_POSITION, str4 + f2);
        VEKeyValue vEKeyValue = new VEKeyValue();
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(File.separator);
            if (split.length > 0) {
                str3 = split[split.length - 1];
                if (!TextUtils.isEmpty(str2)) {
                    String[] split2 = str2.split(File.separator);
                    if (split2.length > 0) {
                        str4 = split2[split2.length - 1];
                    }
                }
                vEKeyValue.add("iesve_veeditor_set_filter_slide_left_id", str3);
                vEKeyValue.add("iesve_veeditor_set_filter_slide_right_id", str4);
                MonitorUtils.monitorStatistics("iesve_veeditor_set_filter_slide", 1, vEKeyValue);
                return 0;
            }
        }
        str3 = str4;
        if (!TextUtils.isEmpty(str2)) {
        }
        vEKeyValue.add("iesve_veeditor_set_filter_slide_left_id", str3);
        vEKeyValue.add("iesve_veeditor_set_filter_slide_right_id", str4);
        MonitorUtils.monitorStatistics("iesve_veeditor_set_filter_slide", 1, vEKeyValue);
        return 0;
    }

    /* access modifiers changed from: private */
    public void updateInitDisplaySize() {
        VESize vESize = this.mInitSize;
        int i = vESize.width;
        int i2 = vESize.height;
        float f2 = ((float) i) / ((float) i2);
        int i3 = this.mSurfaceWidth;
        int i4 = this.mSurfaceHeight;
        if (f2 > ((float) i3) / ((float) i4)) {
            this.mInitDisplayWidth = i3;
            this.mInitDisplayHeight = (int) (((float) i3) / (((float) i) / ((float) i2)));
            return;
        }
        this.mInitDisplayHeight = i4;
        this.mInitDisplayWidth = (int) (((float) i4) / (((float) i2) / ((float) i)));
    }

    public synchronized int addAudioCleanFilter(int i, int i2, int i3, int i4) {
        return this.mVideoEditor.addFilters(new int[]{i}, new String[]{"audio cleaner"}, new int[]{i3}, new int[]{i4}, new int[]{i2}, new int[]{1})[0];
    }

    public synchronized int addAudioDRCFilter(int i, @NonNull float[] fArr, int i2, int i3) {
        if (13 != fArr.length) {
            return -1;
        }
        int[] addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"audio drc"}, new int[]{i2}, new int[]{i3}, new int[]{1}, new int[]{1});
        for (int i4 = 0; i4 < fArr.length; i4++) {
            this.mVideoEditor.setFilterParam(addFilters[0], "drc_params_" + i4, "" + fArr[i4]);
        }
        return addFilters[0];
    }

    public synchronized int[] addAudioEffects(int i, int i2, int[] iArr, int[] iArr2, VEAudioEffectBean[] vEAudioEffectBeanArr) {
        int[] addFilters;
        VELogUtil.w(TAG, "addAudioEffects...");
        int length = iArr.length;
        int[] iArr3 = new int[length];
        int[] iArr4 = new int[length];
        int[] iArr5 = new int[length];
        String[] strArr = new String[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr3[i3] = i;
            iArr4[i3] = i2;
            strArr[i3] = "audio effect";
            iArr5[i3] = 1;
        }
        addFilters = this.mVideoEditor.addFilters(iArr3, strArr, iArr, iArr2, iArr4, iArr5);
        for (int i4 = 0; i4 < length; i4++) {
            setAudioEffectParam(i, i2, addFilters[i4], vEAudioEffectBeanArr[i4]);
        }
        return addFilters;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0036, code lost:
        return -100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0039, code lost:
        return -100;
     */
    public synchronized int addAudioTrack(String str, int i, int i2, int i3, int i4, boolean z) {
        synchronized (this) {
            VELogUtil.i(TAG, "addAudioTrack...");
            if (TextUtils.isEmpty(str)) {
                return -100;
            }
            if (i2 > i && i >= 0) {
                if (i4 > i3 && i3 >= 0) {
                    int addTrack = this.mTrackIndexManager.addTrack(1, this.mVideoEditor.addAudioTrack(str, i3, i4, i, i2, z));
                    return addTrack;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0042, code lost:
        return -100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0045, code lost:
        return -100;
     */
    public synchronized int addAudioTrack(String str, int i, int i2, int i3, int i4, boolean z, int i5, int i6) {
        synchronized (this) {
            VELogUtil.i(TAG, "addAudioTrack...");
            if (TextUtils.isEmpty(str)) {
                return -100;
            }
            if (i2 > i && i >= 0) {
                if (i4 > i3 && i3 >= 0) {
                    int addTrack = this.mTrackIndexManager.addTrack(1, this.mVideoEditor.addAudioTrack(str, i3, i4, i, i2, z, i5, i6));
                    return addTrack;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0047, code lost:
        return -100;
     */
    public synchronized int addAudioTrack(String str, int i, int i2, boolean z) {
        synchronized (this) {
            if (TextUtils.isEmpty(str)) {
                return -100;
            }
            if (i2 > i && i >= 0) {
                MonitorUtils.monitorStatistics("iesve_veeditor_import_music", 1, null);
                int addTrack = this.mTrackIndexManager.addTrack(1, this.mVideoEditor.addAudioTrack(str, 0, i2 - i, i, i2, z));
                VELogUtil.w(TAG, "addAudioTrack... " + addTrack);
                return addTrack;
            }
        }
    }

    public synchronized int[] addCherEffect(int i, int i2, VECherEffectParam vECherEffectParam) {
        int[] addFilters;
        int length = vECherEffectParam.getMatrix().length;
        int[] iArr = new int[length];
        int[] iArr2 = new int[length];
        int[] iArr3 = new int[length];
        String[] strArr = new String[length];
        int[] iArr4 = new int[length];
        int[] iArr5 = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = i;
            iArr2[i3] = i2;
            strArr[i3] = "audio chereffect";
            iArr3[i3] = 1;
            int i4 = i3 * 2;
            iArr4[i3] = (int) vECherEffectParam.getDuration()[i4];
            iArr5[i3] = (int) vECherEffectParam.getDuration()[i4 + 1];
        }
        addFilters = this.mVideoEditor.addFilters(iArr, strArr, iArr4, iArr5, iArr2, iArr3);
        for (int i5 = 0; i5 < length; i5++) {
            this.mVideoEditor.setFilterParam(addFilters[i5], "cher_matrix", vECherEffectParam.getMatrix()[i5]);
        }
        return addFilters;
    }

    public synchronized int addEqualizer(int i, int i2, int i3, int i4, int i5) {
        int[] addFilters;
        addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"audio equalizer"}, new int[]{i4}, new int[]{i5}, new int[]{i2}, new int[]{1});
        TEInterface tEInterface = this.mVideoEditor;
        int i6 = addFilters[0];
        tEInterface.setFilterParam(i6, "preset_id", "" + i3);
        return addFilters[0];
    }

    public synchronized int addEqualizer(int i, VEEqualizerParams vEEqualizerParams, int i2, int i3) {
        int[] addFilters;
        addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"audio equalizer"}, new int[]{i2}, new int[]{i3}, new int[]{1}, new int[]{1});
        TEInterface tEInterface = this.mVideoEditor;
        int i4 = addFilters[0];
        tEInterface.setFilterParam(i4, "equalizer_params", "" + vEEqualizerParams.getParamsAsString());
        return addFilters[0];
    }

    public synchronized int addExtRes(String str, int i, int i2, int i3, int i4, float f2, float f3, float f4, float f5) {
        VELogUtil.w(TAG, "addSticker...");
        if (i <= i2 && i >= 0) {
            if (!TextUtils.isEmpty(str)) {
                MonitorUtils.monitorStatistics("iesve_veeditor_import_sticker", 1, null);
                return this.mVideoEditor.addSticker(new String[]{str}, null, new int[]{i}, new int[]{i2}, new int[]{i3}, new int[]{i4}, (double) f4, (double) f5, (double) f2, (double) f3);
            }
        }
        return -100;
    }

    public synchronized int addFFmpegPitchTempo(int i, float f2, float f3, int i2, int i3) {
        int[] addFilters;
        addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"audio ffmpeg pitch tempo"}, new int[]{i2}, new int[]{i3}, new int[]{1}, new int[]{1});
        TEInterface tEInterface = this.mVideoEditor;
        int i4 = addFilters[0];
        tEInterface.setFilterParam(i4, "pitch_scale", "" + f2);
        TEInterface tEInterface2 = this.mVideoEditor;
        int i5 = addFilters[0];
        tEInterface2.setFilterParam(i5, "time_ratio", "" + f3);
        return addFilters[0];
    }

    public synchronized int addFadeInFadeOut(int i, int i2, int i3, int i4, int i5, int i6) {
        int[] addFilters;
        addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"audio fading"}, new int[]{i3}, new int[]{i4}, new int[]{i2}, new int[]{1});
        TEInterface tEInterface = this.mVideoEditor;
        int i7 = addFilters[0];
        tEInterface.setFilterParam(i7, "fade_int_length", "" + (i5 * 1000));
        TEInterface tEInterface2 = this.mVideoEditor;
        int i8 = addFilters[0];
        tEInterface2.setFilterParam(i8, "fade_out_length", "" + (i6 * 1000));
        return addFilters[0];
    }

    public synchronized int[] addFilterEffects(int[] iArr, int[] iArr2, String[] strArr) {
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            if (iArr[i] >= 0 && iArr[i] <= getDuration()) {
                if (!TextUtils.isEmpty(strArr[i])) {
                    i++;
                }
            }
            return new int[]{-100};
        }
        boolean[] zArr = new boolean[length];
        int[] iArr3 = new int[length];
        int[] iArr4 = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            zArr[i2] = false;
            iArr3[i2] = 0;
            iArr4[i2] = 0;
        }
        return addFilterEffects(iArr, iArr2, strArr, zArr, iArr3, iArr4);
    }

    public synchronized int[] addFilterEffects(int[] iArr, int[] iArr2, String[] strArr, boolean[] zArr, int[] iArr3, int[] iArr4) {
        int length = iArr.length;
        int[] iArr5 = new int[length];
        int[] iArr6 = new int[length];
        int[] iArr7 = new int[length];
        String[] strArr2 = new String[length];
        for (int i = 0; i < length; i++) {
            iArr5[i] = 0;
            iArr6[i] = 0;
            strArr2[i] = "filter effect";
            iArr7[i] = 8;
        }
        int[] addFilters = this.mVideoEditor.addFilters(iArr5, strArr2, iArr, iArr2, iArr6, iArr7);
        if (length != addFilters.length) {
            int[] iArr8 = new int[length];
            Arrays.fill(iArr8, -1);
            return iArr8;
        }
        for (int i2 = 0; i2 < length; i2++) {
            this.mVideoEditor.setFilterParam(addFilters[i2], EFFECT_RES_PATH, strArr[i2]);
            this.mVideoEditor.setFilterParam(addFilters[i2], EFFECT_USE_AMAZING, zArr[i2] ? MistatsConstants.BaseEvent.VALUE_TRUE : MistatsConstants.BaseEvent.VALUE_FALSE);
            this.mVideoEditor.setFilterParam(addFilters[i2], EFFECT_STICKER_ID, iArr3[i2] + "");
            this.mVideoEditor.setFilterParam(addFilters[i2], EFFECT_REQ_ID, iArr4[i2] + "");
            TEMonitorFilterMgr.TEMonitorFilter tEMonitorFilter = new TEMonitorFilterMgr.TEMonitorFilter();
            tEMonitorFilter.path = strArr[i2];
            tEMonitorFilter.start = iArr[i2];
            tEMonitorFilter.duration = iArr2[i2] - iArr[i2];
            this.mTEMonitorFilterMgr.addFilter(0, addFilters[i2], tEMonitorFilter);
        }
        return addFilters;
    }

    public synchronized int addImageSticker(@NonNull String str, float f2, float f3, float f4, float f5) {
        VELogUtil.i(TAG, "addInfoSticker...");
        if (TextUtils.isEmpty(str)) {
            return -100;
        }
        MonitorUtils.monitorStatistics("iesve_veeditor_import_sticker", 1, null);
        return this.mVideoEditor.addInfoSticker(str, new String[]{String.valueOf(f2), String.valueOf(f3), String.valueOf(f4), String.valueOf(f5)});
    }

    public synchronized int addInfoSticker(String str, @Nullable String[] strArr) {
        int addInfoSticker;
        VELogUtil.i(TAG, "addInfoSticker...");
        if (TextUtils.isEmpty(str)) {
            return -100;
        }
        MonitorUtils.monitorStatistics("iesve_veeditor_import_sticker", 1, null);
        synchronized (this) {
            addInfoSticker = this.mVideoEditor.addInfoSticker(str, strArr);
            TEMonitorFilterMgr.TEMonitorFilter tEMonitorFilter = new TEMonitorFilterMgr.TEMonitorFilter();
            tEMonitorFilter.path = str;
            this.mTEMonitorFilterMgr.addFilter(1, addInfoSticker, tEMonitorFilter);
        }
        return addInfoSticker;
    }

    public synchronized int addMetadata(String str, String str2) {
        synchronized (this) {
            VELogUtil.w(TAG, "addMetadata...");
            if (!TextUtils.isEmpty(str)) {
                if (!TextUtils.isEmpty(str2)) {
                    this.mVideoEditor.addMetaData(str, str2);
                    return 0;
                }
            }
            return -100;
        }
    }

    public synchronized int addPitchTempo(int i, int i2, float f2, float f3, int i3, int i4) {
        int[] addFilters;
        addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"audio pitch tempo"}, new int[]{i3}, new int[]{i4}, new int[]{i2}, new int[]{1});
        TEInterface tEInterface = this.mVideoEditor;
        int i5 = addFilters[0];
        tEInterface.setFilterParam(i5, "pitch_scale", "" + f2);
        TEInterface tEInterface2 = this.mVideoEditor;
        int i6 = addFilters[0];
        tEInterface2.setFilterParam(i6, "time_ratio", "" + f3);
        return addFilters[0];
    }

    public synchronized int addRepeatEffect(int i, int i2, int i3, int i4, int i5) {
        synchronized (this) {
            VELogUtil.w(TAG, "addRepeatEffect... " + i + " " + i2 + " " + i3 + " " + i4 + " " + i5);
            int pauseSync = this.mVideoEditor.pauseSync();
            if (pauseSync == 0 || pauseSync == -101) {
                int duration = getDuration();
                int[] addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"timeEffect repeating"}, new int[]{i3}, new int[]{duration}, new int[]{i2}, new int[]{6});
                this.mAudioEffectfilterIndex = addFilters[0];
                TEInterface tEInterface = this.mVideoEditor;
                int i6 = addFilters[0];
                tEInterface.setFilterParam(i6, "timeEffect repeating duration", "" + i5);
                TEInterface tEInterface2 = this.mVideoEditor;
                int i7 = addFilters[0];
                tEInterface2.setFilterParam(i7, "timeEffect repeating times", "" + i4);
                this.mVideoEditor.createTimeline();
                VEKeyValue vEKeyValue = new VEKeyValue();
                vEKeyValue.add("iesve_veeditor_time_effect_id", "repeat");
                MonitorUtils.monitorStatistics("iesve_veeditor_time_effect", 1, vEKeyValue);
                this.mTEMonitorFilterMgr.setTimeEffectType(1);
                int i8 = addFilters[0];
                return i8;
            }
            VELogUtil.e(TAG, "pauseSync failed in addRepeatEffect, ret " + pauseSync);
            return -1;
        }
    }

    public synchronized int addReverb(int i, String str, int i2, int i3) {
        int[] addFilters;
        addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"audio reverb"}, new int[]{i2}, new int[]{i3}, new int[]{1}, new int[]{1});
        this.mVideoEditor.setFilterParam(addFilters[0], "reverb_params", str);
        return addFilters[0];
    }

    public synchronized int addReverb2(int i, VEReverb2Params vEReverb2Params, int i2, int i3) {
        int[] addFilters;
        addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"audio reverb2"}, new int[]{i2}, new int[]{i3}, new int[]{1}, new int[]{1});
        VELogUtil.w(TAG, "addReverb2..." + addFilters[0]);
        TEInterface tEInterface = this.mVideoEditor;
        int i4 = addFilters[0];
        tEInterface.setFilterParam(i4, "reverb2_params", "" + vEReverb2Params.getParamsAsString());
        return addFilters[0];
    }

    public synchronized int[] addSegmentVolume(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, float[] fArr) {
        int[] addFilters;
        int length = iArr3.length;
        String[] strArr = new String[length];
        int[] iArr5 = new int[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = "audio volume filter";
            iArr5[i] = 1;
        }
        addFilters = this.mVideoEditor.addFilters(iArr, strArr, iArr3, iArr4, iArr2, iArr5);
        for (int i2 = 0; i2 < length; i2++) {
            this.mVideoEditor.setFilterParam(addFilters[i2], AUDIO_VOLUME, "" + fArr[i2]);
        }
        return addFilters;
    }

    public synchronized int addSlowMotionEffect(int i, int i2, int i3, int i4, float f2, float f3) {
        synchronized (this) {
            VELogUtil.w(TAG, "addSlowMotionEffect... " + i + " " + i2 + " " + i3 + " " + i4 + " " + f2 + " " + f3);
            int pauseSync = this.mVideoEditor.pauseSync();
            if (pauseSync == 0 || pauseSync == -101) {
                int duration = getDuration();
                int[] addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"timeEffect slow motion"}, new int[]{i3}, new int[]{duration}, new int[]{i2}, new int[]{6});
                this.mAudioEffectfilterIndex = addFilters[0];
                TEInterface tEInterface = this.mVideoEditor;
                int i5 = addFilters[0];
                tEInterface.setFilterParam(i5, "timeEffect slow motion duration", "" + i4);
                TEInterface tEInterface2 = this.mVideoEditor;
                int i6 = addFilters[0];
                tEInterface2.setFilterParam(i6, "timeEffect slow motion speed", "" + f2);
                TEInterface tEInterface3 = this.mVideoEditor;
                int i7 = addFilters[0];
                tEInterface3.setFilterParam(i7, "timeEffect fast motion speed", "" + f3);
                this.mVideoEditor.createTimeline();
                VEKeyValue vEKeyValue = new VEKeyValue();
                vEKeyValue.add("iesve_veeditor_time_effect_id", "slow");
                MonitorUtils.monitorStatistics("iesve_veeditor_time_effect", 1, vEKeyValue);
                this.mTEMonitorFilterMgr.setTimeEffectType(2);
                int i8 = addFilters[0];
                return i8;
            }
            VELogUtil.e(TAG, "pauseSync failed in addSlowMotionEffect, ret " + pauseSync);
            return -1;
        }
    }

    public synchronized int addSticker(String str, int i, int i2, float f2, float f3, float f4, float f5) {
        return addSticker(str, i, i2, 0, i2 - i, f2, f3, f4, f5);
    }

    public synchronized int addSticker(String str, int i, int i2, int i3, int i4, float f2, float f3, float f4, float f5) {
        VELogUtil.w(TAG, "addSticker...");
        if (i <= i2 && i >= 0) {
            if (!TextUtils.isEmpty(str)) {
                MonitorUtils.monitorStatistics("iesve_veeditor_import_sticker", 1, null);
                return this.mVideoEditor.addSticker(new String[]{str}, null, new int[]{i}, new int[]{i2}, new int[]{i3}, new int[]{i4}, (double) f4, (double) f5, (double) f2, (double) f3);
            }
        }
        return -100;
    }

    public synchronized int addWaterMark(String str, double d2, double d3, double d4, double d5) {
        VELogUtil.w(TAG, "addWaterMark...");
        return this.mVideoEditor.addWaterMark(new String[]{str}, null, new int[]{0}, new int[]{this.mVideoEditor.getDuration()}, d4, d5, d2, d3);
    }

    public synchronized int addWaterMarkForGifHigh(String str, double d2, double d3, double d4, double d5) {
        this.waterMarkFile = str;
        this.waterMarkWidth = d2;
        this.waterMarkHeight = d3;
        this.waterMarkOffsetX = d4;
        this.waterMarkOffsetY = d5;
        return 0;
    }

    public synchronized int begin2DBrush() {
        return this.mVideoEditor.begin2DBrush();
    }

    public synchronized int cancelGetVideoFrames() {
        return this.mVideoEditor.cancelGetImages();
    }

    public synchronized int cancelReverseVideo() {
        if (this.mResManager.mReverseDone) {
            return VEResult.TER_INVALID_STAT;
        }
        synchronized (this) {
            new FFMpegInvoker().stopReverseVideo();
            this.mCancelReverse = true;
        }
        return 0;
    }

    public synchronized int changeRes(String[] strArr, int[] iArr, int[] iArr2, String[] strArr2, String[] strArr3, int[] iArr3, int[] iArr4, VIDEO_RATIO video_ratio) throws VEException {
        VELogUtil.i(TAG, "reInit...");
        int stop = this.mVideoEditor.stop();
        if (stop != 0) {
            VELogUtil.i(TAG, "stop in changeRes failed, ret = " + stop);
            return -1;
        }
        int init2 = init2(strArr, iArr, iArr2, strArr2, strArr3, iArr3, iArr4, video_ratio);
        if (init2 != 0) {
            VELogUtil.e(TAG, "init2 in changeRes failed, ret = " + init2);
            return init2;
        }
        this.mVideoEditor.createTimeline();
        return this.mVideoEditor.prepareEngine(-1);
    }

    public synchronized int changeRes(String[] strArr, int[] iArr, int[] iArr2, String[] strArr2, String[] strArr3, int[] iArr3, int[] iArr4, float[] fArr, VIDEO_RATIO video_ratio) throws VEException {
        VELogUtil.i(TAG, "reInit...");
        int stop = this.mVideoEditor.stop();
        if (stop != 0) {
            VELogUtil.i(TAG, "stop in changeRes failed, ret = " + stop);
            return -1;
        }
        int init2 = init2(strArr, iArr, iArr2, strArr2, strArr3, iArr3, iArr4, fArr, null, video_ratio);
        if (init2 != 0) {
            VELogUtil.e(TAG, "init2 in changeRes failed, ret = " + init2);
            return init2;
        }
        this.mVideoEditor.createTimeline();
        return this.mVideoEditor.prepareEngine(-1);
    }

    public synchronized void clearDisplay(int i) {
        this.mVideoEditor.clearDisplay(i);
    }

    public synchronized boolean compile(String str, String str2, VEVideoEncodeSettings vEVideoEncodeSettings) throws VEException {
        this.mCompileListener = null;
        return _compile(str, str2, vEVideoEncodeSettings);
    }

    public synchronized boolean compile(String str, String str2, VEVideoEncodeSettings vEVideoEncodeSettings, VEListener.VEEditorCompileListener vEEditorCompileListener) throws VEException {
        this.mCompileListener = vEEditorCompileListener;
        return _compile(str, str2, vEVideoEncodeSettings);
    }

    public synchronized int deleteAudioFilters(int[] iArr) {
        int removeFilter;
        synchronized (this) {
            VELogUtil.w(TAG, "deleteAudioFilter..." + iArr[0]);
            removeFilter = this.mVideoEditor.removeFilter(iArr);
        }
        return removeFilter;
        return removeFilter;
    }

    public synchronized int deleteAudioTrack(int i) {
        synchronized (this) {
            VELogUtil.w(TAG, "deleteAudioTrack...");
            if (i < 0) {
                return -100;
            }
            int nativeTrackIndex = this.mTrackIndexManager.getNativeTrackIndex(1, i);
            this.mTrackIndexManager.removeTrack(1, i);
            int deleteAudioTrack = this.mVideoEditor.deleteAudioTrack(nativeTrackIndex);
            return deleteAudioTrack;
        }
    }

    public synchronized int deleteFilterEffects(int[] iArr) {
        for (int i : iArr) {
            this.mTEMonitorFilterMgr.removeFilter(0, i);
        }
        return this.mVideoEditor.removeFilter(iArr);
    }

    public synchronized int deleteRepeatEffect(int i) {
        synchronized (this) {
            VELogUtil.w(TAG, "deleteRepeatEffect... " + i);
            int pauseSync = this.mVideoEditor.pauseSync();
            if (pauseSync == 0 || pauseSync == -101) {
                int removeFilter = this.mVideoEditor.removeFilter(new int[]{i});
                this.mTEMonitorFilterMgr.setTimeEffectType(0);
                this.mVideoEditor.createTimeline();
                return removeFilter;
            }
            VELogUtil.i(TAG, "pauseSync failed, ret " + pauseSync);
            return -1;
        }
    }

    public synchronized int deleteSlowEffect(int i) {
        synchronized (this) {
            VELogUtil.w(TAG, "deleteSlowEffect... " + i);
            int pauseSync = this.mVideoEditor.pauseSync();
            if (pauseSync == 0 || pauseSync == -101) {
                int removeFilter = this.mVideoEditor.removeFilter(new int[]{i});
                this.mVideoEditor.createTimeline();
                this.mTEMonitorFilterMgr.setTimeEffectType(0);
                return removeFilter;
            }
            VELogUtil.w(TAG, "pauseSync failed, ret " + pauseSync);
            return -1;
        }
    }

    public synchronized int deleteSticker(int i) {
        VELogUtil.w(TAG, "deleteSticker...");
        if (i < 0) {
            return -100;
        }
        return this.mVideoEditor.deleteSticker(i);
    }

    public synchronized int deleteWaterMark(int i) {
        return 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0072, code lost:
        return;
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public synchronized void destroy() {
        synchronized (this) {
            this.mInitSuccess = false;
            VELogUtil.w(TAG, "onDestroy... ");
            if (this.mVideoEditor.getNativeHandler() != 0) {
                if (this.mSurfaceView != null) {
                    this.mSurfaceView.getHolder().removeCallback(this.mSurfaceCallback);
                } else if (this.mTextureView != null && this.mTextureView.getSurfaceTextureListener() == this.mTextureListener) {
                    this.mTextureView.setSurfaceTextureListener(null);
                }
                this.mSurfaceView = null;
                this.mTextureView = null;
                if (this.mVideoEditor != null) {
                    this.mVideoEditor.setOpenGLListeners(null);
                    this.mVideoEditor.setInfoListener(null);
                    this.mVideoEditor.setErrorListener(null);
                    this.mVideoEditor.destroyEngine();
                }
                this.mResManager = null;
                if (this.mCurrentBmp != null && !this.mCurrentBmp.isRecycled()) {
                    this.mCurrentBmp.recycle();
                    this.mCurrentBmp = null;
                }
            }
        }
    }

    public synchronized int disableAudioEffect(int i, int i2) {
        VELogUtil.w(TAG, "disableAudioEffect...");
        if (i == -1) {
            return -100;
        }
        return this.mVideoEditor.adjustFilterInOut(i, -1, i2);
    }

    public synchronized int disableFilterEffect(int i, int i2) {
        if (i < 0 || i2 < 0) {
            return -100;
        }
        TEMonitorFilterMgr.TEMonitorFilter tEMonitorFilter = this.mTEMonitorFilterMgr.effectMap.get(Integer.valueOf(i));
        if (tEMonitorFilter != null) {
            tEMonitorFilter.duration = i2 - tEMonitorFilter.start;
        }
        return this.mVideoEditor.adjustFilterInOut(i, -1, i2);
    }

    public synchronized int enableAudioEffect(int i, int i2, int i3, VEAudioEffectBean vEAudioEffectBean) {
        int[] addFilters;
        VELogUtil.w(TAG, "enableAudioEffect...");
        int duration = getDuration();
        addFilters = this.mVideoEditor.addFilters(new int[]{i}, new String[]{"audio effect"}, new int[]{i3}, new int[]{duration}, new int[]{i2}, new int[]{1});
        this.mAudioEffectfilterIndex = addFilters[0];
        setAudioEffectParam(i, i2, addFilters[0], vEAudioEffectBean);
        return addFilters[0];
    }

    @Deprecated
    public synchronized int enableAudioEffect(int i, VEAudioEffectBean vEAudioEffectBean) {
        VELogUtil.w(TAG, "enableAudioEffect...");
        this.mAudioEffectfilterIndex = enableAudioEffect(0, this.mbSeparateAV.booleanValue() ? 1 : 0, i, vEAudioEffectBean);
        return this.mAudioEffectfilterIndex;
    }

    public synchronized void enableEffectAmazing(boolean z) {
        VEEffectConfig.enableEffectAmazingForEditor(z);
    }

    public synchronized int enableFilterEffect(int i, String str) {
        return enableFilterEffect(i, str, false, 0, 0);
    }

    public synchronized int enableFilterEffect(int i, String str, boolean z, int i2, int i3) {
        if (i >= 0) {
            if (i <= getDuration()) {
                if (!TextUtils.isEmpty(str)) {
                    int[] addFilters = this.mVideoEditor.addFilters(new int[]{0}, new String[]{"video effect"}, new int[]{i}, new int[]{getDuration()}, new int[]{0}, new int[]{8});
                    this.mVideoEditor.setFilterParam(addFilters[0], EFFECT_RES_PATH, str);
                    this.mVideoEditor.setFilterParam(addFilters[0], EFFECT_USE_AMAZING, z ? MistatsConstants.BaseEvent.VALUE_TRUE : MistatsConstants.BaseEvent.VALUE_FALSE);
                    TEInterface tEInterface = this.mVideoEditor;
                    int i4 = addFilters[0];
                    tEInterface.setFilterParam(i4, EFFECT_STICKER_ID, i2 + "");
                    TEInterface tEInterface2 = this.mVideoEditor;
                    int i5 = addFilters[0];
                    tEInterface2.setFilterParam(i5, EFFECT_REQ_ID, i3 + "");
                    VEKeyValue vEKeyValue = new VEKeyValue();
                    String str2 = "";
                    if (!TextUtils.isEmpty(str)) {
                        String[] split = str.split(File.separator);
                        if (split.length > 0) {
                            str2 = split[split.length - 1];
                        }
                    }
                    vEKeyValue.add("iesve_veeditor_filter_effect_id", str2);
                    MonitorUtils.monitorStatistics("iesve_veeditor_filter_effect", 1, vEKeyValue);
                    TEMonitorFilterMgr.TEMonitorFilter tEMonitorFilter = new TEMonitorFilterMgr.TEMonitorFilter();
                    tEMonitorFilter.path = str;
                    tEMonitorFilter.start = i;
                    this.mTEMonitorFilterMgr.addFilter(0, addFilters[0], tEMonitorFilter);
                    return addFilters[0];
                }
            }
        }
        return -100;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x011c, code lost:
        return 0;
     */
    public synchronized int enableReversePlay(boolean z) {
        synchronized (this) {
            VELogUtil.w(TAG, "enableReversePlay:" + z);
            if (!this.mResManager.mReverseDone) {
                VELogUtil.e(TAG, "enableReversePlay error: reverse video is not ready!");
                return -100;
            } else if (this.mResManager == null || this.mResManager.mReverseVideoPath == null || this.mResManager.mReverseVideoPath.length <= 0) {
                VELogUtil.e(TAG, "enableReversePlay error: reverse video path is invalid!");
                return VEResult.TER_INVALID_STAT;
            } else {
                this.mVideoEditor.stop();
                int updateTrackClips = this.mVideoEditor.updateTrackClips(0, 0, z ? this.mResManager.mReverseVideoPath : this.mResManager.mVideoPaths);
                if (updateTrackClips != 0) {
                    VELogUtil.e(TAG, "Create Scene failed, ret = " + updateTrackClips);
                    return updateTrackClips;
                }
                if (!(this.mResManager.mReverseAudioPaths == null || this.mResManager.mOriginalSoundTrackType == 1)) {
                    this.mResManager.mOriginalSoundTrackIndex = this.mVideoEditor.addAudioTrack(this.mResManager.mReverseAudioPaths[0], 0, this.mVideoEditor.getDuration(), 0, this.mVideoEditor.getDuration(), false);
                    this.mResManager.mOriginalSoundTrackType = 1;
                    VELogUtil.e(TAG, "add org audio track index " + this.mResManager.mOriginalSoundTrackIndex + " type " + this.mResManager.mOriginalSoundTrackType);
                }
                this.mVideoEditor.updateTrackFilter(0, 0, z != this.mBReversePlay);
                this.mVideoEditor.createTimeline();
                int prepareEngine = this.mVideoEditor.prepareEngine(0);
                if (prepareEngine != 0) {
                    VELogUtil.e(TAG, "enableReversePlay() prepareEngine failed: result: " + prepareEngine);
                    return prepareEngine;
                }
                this.mMusicSRTEffectFilterIndex = -1;
                seek(0, SEEK_MODE.EDITOR_SEEK_FLAG_LastSeek);
                this.mBReversePlay = z;
                if (z) {
                    VEKeyValue vEKeyValue = new VEKeyValue();
                    vEKeyValue.add("iesve_veeditor_time_effect_id", "reverse");
                    MonitorUtils.monitorStatistics("iesve_veeditor_time_effect", 1, vEKeyValue);
                    this.mTEMonitorFilterMgr.setTimeEffectType(3);
                }
            }
        }
    }

    public synchronized void enableSimpleProcessor(boolean z) {
        VELogUtil.i(TAG, "enableSimpleProcessor: " + String.valueOf(z));
        this.mVideoEditor.enableSimpleProcessor(z);
    }

    public synchronized int end2DBrush(@NonNull String str) {
        return this.mVideoEditor.end2DBrush(str);
    }

    public int genReverseVideo() throws VEException {
        VELogUtil.w(TAG, "genReverseVideo");
        String[] strArr = this.mResManager.mVideoPaths;
        if (strArr == null || strArr.length <= 0) {
            VELogUtil.e(TAG, "genReverseVideo error:invalid videoPaths");
            return -100;
        }
        long currentTimeMillis = System.currentTimeMillis();
        FFMpegInvoker fFMpegInvoker = new FFMpegInvoker();
        VEEditorResManager vEEditorResManager = this.mResManager;
        vEEditorResManager.mReverseVideoPath = new String[vEEditorResManager.mVideoPaths.length];
        int i = 0;
        while (true) {
            VEEditorResManager vEEditorResManager2 = this.mResManager;
            if (i < vEEditorResManager2.mVideoPaths.length) {
                String genReverseVideoPath = vEEditorResManager2.genReverseVideoPath(i);
                int addFastReverseVideo = fFMpegInvoker.addFastReverseVideo(this.mResManager.mVideoPaths[i], genReverseVideoPath);
                if (this.mCancelReverse) {
                    VELogUtil.w(TAG, "genReverseVideo fail: cancel reverse");
                    this.mCancelReverse = false;
                    return -1;
                } else if (addFastReverseVideo == 0) {
                    this.mResManager.mReverseVideoPath[i] = genReverseVideoPath;
                    i++;
                } else {
                    throw new VEException(-1, "reverse mResManager.mVideoPaths[i] failed: " + addFastReverseVideo);
                }
            } else {
                vEEditorResManager2.mReverseDone = true;
                TEMonitor.perfLong(1, TEMonitorNewKeys.TE_EDIT_REVERSE_TIME, System.currentTimeMillis() - currentTimeMillis);
                return 0;
            }
        }
    }

    public synchronized int get2DBrushStrokeCount() {
        return this.mVideoEditor.get2DBrushStrokeCount();
    }

    public synchronized int getCurPosition() {
        return this.mVideoEditor.getCurPosition();
    }

    public synchronized Bitmap getCurrDisplayImage() {
        return getCurrDisplayImage(-1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0045, code lost:
        if (r3 == 0) goto L_0x005f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0047, code lost:
        com.ss.android.vesdk.VELogUtil.e(com.ss.android.vesdk.VEEditor.TAG, "getDisplayImage failed " + r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005e, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r2 = android.graphics.Bitmap.createBitmap(r6, r1, android.graphics.Bitmap.Config.ARGB_8888);
        r2.copyPixelsFromBuffer(r0.position(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006e, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006f, code lost:
        com.ss.android.vesdk.VELogUtil.e(com.ss.android.vesdk.VEEditor.TAG, "getDisplayImage createBitmap failed " + r6.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008d, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0030  */
    public synchronized Bitmap getCurrDisplayImage(int i) {
        Bitmap bitmap;
        int i2;
        synchronized (this) {
            VERect displayRect = this.mVideoEditor.getDisplayRect();
            bitmap = null;
            if (displayRect.width != 0) {
                if (displayRect.height != 0) {
                    if (i > 0) {
                        if (i < displayRect.width) {
                            i2 = (displayRect.height * i) / displayRect.width;
                            if (i % 2 == 1) {
                                i++;
                            }
                            if (i2 % 2 == 1) {
                                i2++;
                            }
                            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i * i2 * 4);
                            int displayImage = this.mVideoEditor.getDisplayImage(allocateDirect.array(), i, i2);
                        }
                    }
                    i = displayRect.width;
                    i2 = displayRect.height;
                    if (i % 2 == 1) {
                    }
                    if (i2 % 2 == 1) {
                    }
                    ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(i * i2 * 4);
                    int displayImage2 = this.mVideoEditor.getDisplayImage(allocateDirect2.array(), i, i2);
                }
            }
        }
        return bitmap;
    }

    public synchronized int getDuration() {
        return this.mVideoEditor.getDuration();
    }

    public synchronized int getImages(int[] iArr, int i, int i2, VEListener.VEGetImageListener vEGetImageListener) {
        this.mGetImageListener = vEGetImageListener;
        this.mVideoEditor.setGetImageCallback(this.mGetImageCallback);
        return this.mVideoEditor.getImages(iArr, i, i2);
    }

    public synchronized float[] getInfoStickerBoundingBox(int i) throws VEException {
        VELogUtil.i(TAG, "getInfoStickerBoundingBox...");
        if (i >= 0) {
        } else {
            throw new VEException(-100, "");
        }
        return this.mVideoEditor.getInfoStickerBoundingBox(i);
    }

    public synchronized VESize getInitSize() {
        return new VESize(this.mInitDisplayWidth, this.mInitDisplayHeight);
    }

    public synchronized int getMVBackgroundAudioRid() {
        return this.mMVBackgroundAudioRid;
    }

    public synchronized int getMVBackgroundAudioTrackIndex() {
        return this.mMVBackgroundAudioTrackIndex;
    }

    @NonNull
    public synchronized MVInfoBean getMVInfo() {
        if (!isMVInit) {
            throw new VEException(-1, "The MV resource has not been initialized yet, please call the init2 method");
        } else if (this.mvInfo != null) {
        } else {
            throw new VEException(-1, "MV资源信息构建失败");
        }
        return this.mvInfo;
    }

    public synchronized String getMetadata(String str) {
        synchronized (this) {
            VELogUtil.w(TAG, "getMetadata...");
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            String metaData = this.mVideoEditor.getMetaData(str);
            return metaData;
        }
    }

    public synchronized Bitmap getReDrawBmp() {
        if (this.mCurrentBmp == null || this.mCurrentBmp.isRecycled()) {
            return null;
        }
        return Bitmap.createBitmap(this.mCurrentBmp);
    }

    public VEEditorResManager getResManager() {
        return this.mResManager;
    }

    public synchronized String[] getReverseAudioPaths() {
        if (!this.mResManager.mReverseDone) {
            return null;
        }
        return this.mResManager.mReverseAudioPaths;
    }

    public synchronized String[] getReverseVideoPaths() {
        if (!this.mResManager.mReverseDone) {
            return null;
        }
        return this.mResManager.mReverseVideoPath;
    }

    public synchronized VEState getState() throws VEException {
        int curState;
        if (this.mVideoEditor != null) {
            curState = this.mVideoEditor.getCurState();
            if (curState != -1) {
            } else {
                throw new VEException(VEResult.TER_INVALID_STAT, " native video editor is null");
            }
        } else {
            throw new VEException(VEResult.TER_INVALID_STAT, "video editor is null");
        }
        return VEState.valueOf(curState);
    }

    public synchronized String[] getVideoPaths() {
        return this.mResManager.mVideoPaths;
    }

    public synchronized float getVolume(int i, int i2, int i3) {
        VELogUtil.w(TAG, "getVolume...");
        if (i3 >= 0) {
            if (i3 <= getDuration()) {
                return this.mVideoEditor.getTrackVolume(this.mTrackIndexManager.getNativeTrackIndex(1, i), i, i3);
            }
        }
        return -100.0f;
    }

    public synchronized int init(String[] strArr, String[] strArr2, String[] strArr3, VIDEO_RATIO video_ratio) throws VEException {
        synchronized (this) {
            if (!VERuntime.isValidAuthorization()) {
                return -1;
            }
            TEMonitor.clearWithType(1);
            TEMonitor.initStats(1);
            this.mlLastTimeMS = System.currentTimeMillis();
            VELogUtil.i(TAG, "init...");
            int createScene = this.mVideoEditor.createScene(this.mResManager.getWorkspace(), strArr, strArr3, strArr2, null, video_ratio.ordinal());
            if (createScene != 0) {
                VELogUtil.e(TAG, "Create Scene failed, ret = " + createScene);
                onMonitorError();
                this.mInitSuccess = false;
                return createScene;
            }
            this.mInitSuccess = true;
            this.mResManager.mReverseDone = false;
            this.mVideoOutRes = video_ratio;
            this.mResManager.mAudioPaths = strArr3;
            this.mResManager.mVideoPaths = strArr;
            this.mResManager.mTransitions = strArr2;
            this.mMusicSRTEffectFilterIndex = -1;
            this.mbSeparateAV = Boolean.valueOf((strArr3 == null || strArr3.length == 0) ? false : true);
            if (this.mbSeparateAV.booleanValue()) {
                this.mResManager.mOriginalSoundTrackType = 1;
            } else {
                this.mResManager.mOriginalSoundTrackType = 0;
            }
            this.mResManager.mOriginalSoundTrackIndex = 0;
            this.mMasterTrackIndex = 0;
            try {
                this.mColorFilterIndex = this.mVideoEditor.addFilters(new int[]{0}, new String[]{"color filter"}, new int[]{0}, new int[]{this.mOutPoint}, new int[]{0}, new int[]{7})[0];
                return createScene;
            } catch (NullPointerException unused) {
                throw new VEException(-1, "init failed: VESDK need to be init");
            }
        }
    }

    public synchronized int init2(@NonNull String str, @NonNull String[] strArr, @NonNull String[] strArr2) throws VEException {
        boolean z;
        String[] strArr3;
        String[] strArr4;
        String str2;
        synchronized (this) {
            if (!VERuntime.isValidAuthorization()) {
                return -1;
            }
            TEMonitor.clearWithType(1);
            TEMonitor.initStats(1);
            this.mlLastTimeMS = System.currentTimeMillis();
            VELogUtil.i(TAG, "init...");
            TEInterface tEInterface = this.mVideoEditor;
            if (this.mSurfaceView != null) {
                str2 = str;
                strArr4 = strArr;
                strArr3 = strArr2;
                z = true;
            } else {
                str2 = str;
                strArr4 = strArr;
                strArr3 = strArr2;
                z = false;
            }
            this.mvInfo = (MVInfoBean) tEInterface.initMVResources(str2, strArr4, strArr3, z);
            if (this.mvInfo != null) {
                isMVInit = true;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                List<List<List<MVResourceBean>>> genMVResourceTracks = genMVResourceTracks(this.mvInfo, arrayList, arrayList2);
                if (genMVResourceTracks.size() != 0) {
                    List<List<MVResourceBean>> list = genMVResourceTracks.get(0);
                    if (list.size() != 0) {
                        int size = list.get(0).size();
                        int[] iArr = new int[size];
                        int[] iArr2 = new int[size];
                        int[] iArr3 = new int[size];
                        int[] iArr4 = new int[size];
                        String[] strArr5 = new String[size];
                        int[] iArr5 = new int[size];
                        genResourcesArr(list.get(0), iArr, iArr2, iArr3, iArr4, strArr5, iArr5);
                        List<List<MVResourceBean>> list2 = genMVResourceTracks.get(1);
                        VIDEO_RATIO video_ratio = VIDEO_RATIO.VIDEO_OUT_RATIO_ORIGINAL;
                        float f2 = ((((float) this.mvInfo.width) * 1.0f) / ((float) this.mvInfo.height)) * 1.0f;
                        if (f2 == 1.0f) {
                            video_ratio = VIDEO_RATIO.VIDEO_OUT_RATIO_1_1;
                        } else if (f2 == 0.75f) {
                            video_ratio = VIDEO_RATIO.VIDEO_OUT_RATIO_3_4;
                        } else if (f2 == 1.3333334f) {
                            video_ratio = VIDEO_RATIO.VIDEO_OUT_RATIO_4_3;
                        } else if (f2 == 1.7777778f) {
                            video_ratio = VIDEO_RATIO.VIDEO_OUT_RATIO_16_9;
                        } else if (f2 == 0.5625f) {
                            video_ratio = VIDEO_RATIO.VIDEO_OUT_RATIO_9_16;
                        }
                        VIDEO_RATIO video_ratio2 = video_ratio;
                        int createScene2 = this.mVideoEditor.createScene2(strArr5, iArr, iArr2, iArr3, iArr4, null, null, null, null, null, iArr5, null, null, null, video_ratio2.ordinal());
                        if (createScene2 != 0) {
                            VELogUtil.e(TAG, "Create Scene failed, ret = " + createScene2);
                            onMonitorError();
                            this.mInitSuccess = false;
                            return createScene2;
                        }
                        getDuration();
                        boolean z2 = true;
                        for (List<MVResourceBean> list3 : list) {
                            if (z2) {
                                z2 = false;
                            } else {
                                int size2 = list3.size();
                                int[] iArr6 = new int[size2];
                                int[] iArr7 = new int[size2];
                                int[] iArr8 = new int[size2];
                                int[] iArr9 = new int[size2];
                                String[] strArr6 = new String[size2];
                                int[] iArr10 = new int[size2];
                                genResourcesArr(list3, iArr6, iArr7, iArr8, iArr9, strArr6, iArr10);
                                this.mVideoEditor.addVideoTrackForMV(strArr6, null, iArr8, iArr9, iArr6, iArr7, iArr10);
                                createScene2 = createScene2;
                                video_ratio2 = video_ratio2;
                            }
                        }
                        for (List<MVResourceBean> list4 : list2) {
                            if (list4.size() != 0) {
                                int i = (int) list4.get(0).seqIn;
                                int i2 = (int) list4.get(0).seqOut;
                                String str3 = list4.get(0).content;
                                int i3 = list4.get(0).rid;
                                int addAudioTrackForMV = this.mVideoEditor.addAudioTrackForMV(str3, i, i2, (int) list4.get(0).trimIn, (int) list4.get(0).trimOut, i3, true);
                                if (i3 == this.mMVBackgroundAudioRid) {
                                    this.mMVBackgroundAudioTrackIndex = addAudioTrackForMV;
                                }
                            }
                        }
                        this.mInitSuccess = true;
                        this.mReverseDone = false;
                        this.mVideoOutRes = video_ratio2;
                        this.mResManager.mAudioPaths = new String[arrayList2.size()];
                        this.mResManager.mVideoPaths = new String[arrayList2.size()];
                        arrayList2.toArray(this.mResManager.mAudioPaths);
                        arrayList.toArray(this.mResManager.mVideoPaths);
                        this.mResManager.mTransitions = null;
                        this.mMusicSRTEffectFilterIndex = -1;
                        this.mbSeparateAV = false;
                        this.mResManager.mOriginalSoundTrackType = 0;
                        this.mResManager.mOriginalSoundTrackIndex = 0;
                        this.mMasterTrackIndex = 0;
                        this.mVideoEditor.setWidthHeight(this.mvInfo.width, this.mvInfo.height);
                        try {
                            this.mColorFilterIndex = this.mVideoEditor.addFilters(new int[]{0}, new String[]{"color filter"}, new int[]{0}, new int[]{this.mOutPoint}, new int[]{0}, new int[]{7})[0];
                            return createScene2;
                        } catch (NullPointerException unused) {
                            throw new VEException(-1, "init failed: VESDK need to be init");
                        }
                    } else {
                        throw new VEException(-1, "没有MV视频信息");
                    }
                } else {
                    throw new VEException(-1, "没有MV信息");
                }
            } else {
                throw new VEException(-1, "MV资源信息构建失败");
            }
        }
    }

    public synchronized int init2(Bitmap[] bitmapArr, int[] iArr, int[] iArr2, String[] strArr, String[] strArr2, int[] iArr3, int[] iArr4, float[] fArr, VIDEO_RATIO video_ratio) throws VEException {
        synchronized (this) {
            if (!VERuntime.isValidAuthorization()) {
                return -1;
            }
            TEMonitor.clearWithType(1);
            TEMonitor.initStats(1);
            this.mlLastTimeMS = System.currentTimeMillis();
            VELogUtil.i(TAG, "init...");
            int createImageScene = this.mVideoEditor.createImageScene(bitmapArr, iArr, iArr2, strArr2, iArr3, iArr4, strArr, null, fArr, video_ratio.ordinal());
            if (createImageScene != 0) {
                VELogUtil.e(TAG, "Create Scene failed, ret = " + createImageScene);
                this.mInitSuccess = false;
                return createImageScene;
            }
            this.mInitSuccess = true;
            this.mResManager.mReverseDone = false;
            this.mVideoOutRes = video_ratio;
            this.mResManager.mAudioPaths = strArr2;
            this.mResManager.mTransitions = strArr;
            this.mMusicSRTEffectFilterIndex = -1;
            this.mbSeparateAV = Boolean.valueOf((strArr2 == null || strArr2.length == 0) ? false : true);
            if (this.mbSeparateAV.booleanValue()) {
                this.mResManager.mOriginalSoundTrackType = 1;
            } else {
                this.mResManager.mOriginalSoundTrackType = 0;
            }
            this.mResManager.mOriginalSoundTrackIndex = 0;
            this.mMasterTrackIndex = 0;
            try {
                this.mColorFilterIndex = this.mVideoEditor.addFilters(new int[]{0}, new String[]{"color filter"}, new int[]{0}, new int[]{this.mOutPoint}, new int[]{0}, new int[]{7})[0];
                return createImageScene;
            } catch (NullPointerException unused) {
                throw new VEException(-1, "init failed: VESDK need to be init");
            }
        }
    }

    public synchronized int init2(String[] strArr, int[] iArr, int[] iArr2, String[] strArr2, String[] strArr3, int[] iArr3, int[] iArr4, VIDEO_RATIO video_ratio) throws VEException {
        return init2(strArr, iArr, iArr2, strArr2, strArr3, iArr3, iArr4, null, null, video_ratio);
    }

    public synchronized int init2(String[] strArr, int[] iArr, int[] iArr2, String[] strArr2, String[] strArr3, int[] iArr3, int[] iArr4, float[] fArr, ROTATE_DEGREE[] rotate_degreeArr, VIDEO_RATIO video_ratio) throws VEException {
        synchronized (this) {
            if (!VERuntime.isValidAuthorization()) {
                return -1;
            }
            TEMonitor.clearWithType(1);
            TEMonitor.initStats(1);
            this.mlLastTimeMS = System.currentTimeMillis();
            VELogUtil.i(TAG, "init...");
            int createScene2 = this.mVideoEditor.createScene2(strArr, iArr, iArr2, strArr3, iArr3, iArr4, strArr2, null, fArr, ROTATE_DEGREE.toIntArray(rotate_degreeArr), video_ratio.ordinal());
            if (createScene2 != 0) {
                VELogUtil.e(TAG, "Create Scene failed, ret = " + createScene2);
                onMonitorError();
                this.mInitSuccess = false;
                return createScene2;
            }
            this.mInitSuccess = true;
            this.mResManager.mReverseDone = false;
            this.mVideoOutRes = video_ratio;
            this.mResManager.mAudioPaths = strArr3;
            this.mResManager.mVideoPaths = strArr;
            this.mResManager.mTransitions = strArr2;
            this.mMusicSRTEffectFilterIndex = -1;
            this.mbSeparateAV = Boolean.valueOf((strArr3 == null || strArr3.length == 0) ? false : true);
            if (this.mbSeparateAV.booleanValue()) {
                this.mResManager.mOriginalSoundTrackType = 1;
            } else {
                this.mResManager.mOriginalSoundTrackType = 0;
            }
            this.mResManager.mOriginalSoundTrackIndex = 0;
            this.mMasterTrackIndex = 0;
            try {
                this.mColorFilterIndex = this.mVideoEditor.addFilters(new int[]{0}, new String[]{"color filter"}, new int[]{0}, new int[]{this.mOutPoint}, new int[]{0}, new int[]{7})[0];
                return createScene2;
            } catch (NullPointerException unused) {
                throw new VEException(-1, "init failed: VESDK need to be init");
            }
        }
    }

    public synchronized void invalidate() {
        this.mVideoEditor.setDisplayState(-1.0f, -1.0f, -1.0f, -1.0f, -80000, -80000, true);
    }

    public synchronized boolean is2DBrushEmpty() {
        return this.mVideoEditor.get2DBrushStrokeCount() == 0;
    }

    public synchronized boolean isInfoStickerAnimatable(int i) {
        VELogUtil.i(TAG, "addInfoSticker...");
        return this.mVideoEditor.isInfoStickerAnimatable(i);
    }

    public synchronized boolean isValid() {
        boolean z;
        synchronized (this) {
            z = this.mVideoEditor.getNativeHandler() != 0;
        }
        return z;
        return z;
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        VELogUtil.v(TAG, "onFrameAvailable...");
    }

    public synchronized int pause() {
        VELogUtil.w(TAG, "pause...");
        return this.mVideoEditor.pause();
    }

    public synchronized int pauseInfoStickerAnimation(boolean z) {
        VELogUtil.i(TAG, "pauseInfoStickerAnimation...");
        return this.mVideoEditor.pauseInfoStickerAnimation(z);
    }

    public synchronized int pauseSync() {
        VELogUtil.i(TAG, "pauseSync...");
        return this.mVideoEditor.pauseSync();
    }

    public synchronized int play() {
        VELogUtil.w(TAG, "play...");
        return this.mVideoEditor.start();
    }

    public synchronized int prepare() {
        int prepareEngine;
        synchronized (this) {
            VELogUtil.w(TAG, "prepare...");
            prepareEngine = this.mVideoEditor.prepareEngine(0);
            if (prepareEngine != 0) {
                VELogUtil.e(TAG, "prepare() prepareEngine failed: result: " + prepareEngine);
                onMonitorError();
            }
            int[] initResolution = this.mVideoEditor.getInitResolution();
            this.mInitSize.width = initResolution[0];
            this.mInitSize.height = initResolution[1];
            setBackgroundColor(this.mBackGroundColor);
        }
        return prepareEngine;
        return prepareEngine;
    }

    public synchronized int processLongPressEvent(float f2, float f3) {
        if (this.mSurfaceWidth != 0) {
            if (this.mSurfaceHeight != 0) {
                return this.mVideoEditor.processLongPressEvent(f2, f3);
            }
        }
        return VEResult.TER_INVALID_STAT;
    }

    public synchronized int processPanEvent(float f2, float f3, float f4, float f5, float f6) {
        if (this.mSurfaceWidth != 0) {
            if (this.mSurfaceHeight != 0) {
                return this.mVideoEditor.processPanEvent(f2, f3, f4, f5, f6);
            }
        }
        return VEResult.TER_INVALID_STAT;
    }

    public synchronized int processRotationEvent(float f2, float f3) {
        return this.mVideoEditor.processRotationEvent(f2, f3);
    }

    public synchronized int processScaleEvent(float f2, float f3) {
        return this.mVideoEditor.processScaleEvent(f2, f3);
    }

    public synchronized int processTouchDownEvent(float f2, float f3, VEGestureType vEGestureType) {
        return this.mVideoEditor.processTouchDownEvent(f2, f3, vEGestureType);
    }

    public synchronized int processTouchMoveEvent(float f2, float f3) {
        if (this.mSurfaceWidth != 0) {
            if (this.mSurfaceHeight != 0) {
                return this.mVideoEditor.processTouchMoveEvent(f2, f3);
            }
        }
        return VEResult.TER_INVALID_STAT;
    }

    public synchronized int processTouchUpEvent(float f2, float f3, VEGestureType vEGestureType) {
        if (this.mSurfaceWidth != 0) {
            if (this.mSurfaceHeight != 0) {
                return this.mVideoEditor.processTouchUpEvent(f2, f3, vEGestureType);
            }
        }
        return VEResult.TER_INVALID_STAT;
    }

    public synchronized int removeInfoSticker(int i) {
        VELogUtil.i(TAG, "removeInfoSticker... index: " + i);
        if (i < 0) {
            return -100;
        }
        this.mTEMonitorFilterMgr.removeFilter(1, i);
        return this.mVideoEditor.removeInfoSticker(i);
    }

    public synchronized int removeSegmentVolume(int i) {
        if (i < 0) {
            return -100;
        }
        return this.mVideoEditor.removeFilter(new int[]{i});
    }

    public synchronized boolean restore(@NonNull VEEditorModel vEEditorModel) {
        String str = vEEditorModel.projectXML;
        if (!com.ss.android.medialib.FileUtils.checkFileExists(str)) {
            VELogUtil.e(TAG, "projectXML not exists: " + str);
            return false;
        }
        int restore = this.mVideoEditor.restore(str);
        if (restore < 0) {
            VELogUtil.e(TAG, "video editor restore failed: result: " + restore + ", project xml: " + str);
            return false;
        }
        this.mInPoint = vEEditorModel.inPoint;
        this.mOutPoint = vEEditorModel.outputPoint;
        this.mResManager.mReverseDone = vEEditorModel.reverseDone;
        this.mVideoOutRes = vEEditorModel.videoOutRes;
        this.mbSeparateAV = Boolean.valueOf(vEEditorModel.separateAV);
        this.mMasterTrackIndex = vEEditorModel.masterTrackIndex;
        this.mAudioEffectfilterIndex = vEEditorModel.audioEffectFilterIndex;
        this.mColorFilterIndex = vEEditorModel.colorFilterIndex;
        this.mResManager.mVideoPaths = vEEditorModel.videoPaths;
        this.mResManager.mAudioPaths = vEEditorModel.audioPaths;
        this.mResManager.mTransitions = vEEditorModel.transitions;
        this.mBackGroundColor = vEEditorModel.backgroundColor;
        this.mOutputFile = vEEditorModel.outputFile;
        this.waterMarkFile = vEEditorModel.watermarkFile;
        this.waterMarkWidth = vEEditorModel.watermarkWidth;
        this.waterMarkHeight = vEEditorModel.watermarkHeight;
        this.waterMarkOffsetX = vEEditorModel.watermarkOffsetX;
        this.waterMarkOffsetY = vEEditorModel.watermarkOffsetY;
        if (TextUtils.isEmpty(vEEditorModel.colorFilterRightPath)) {
            setColorFilter(vEEditorModel.colorFilterLeftPath, vEEditorModel.colorFilterIntensity);
        } else {
            setColorFilter(vEEditorModel.colorFilterLeftPath, vEEditorModel.colorFilterRightPath, vEEditorModel.colorFilterPosition, vEEditorModel.colorFilterIntensity);
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x009d, code lost:
        return r1;
     */
    @Nullable
    public synchronized VEEditorModel save() {
        String save = this.mVideoEditor.save();
        if (!TextUtils.isEmpty(save)) {
            if (com.ss.android.medialib.FileUtils.checkFileExists(save)) {
                VEEditorModel vEEditorModel = new VEEditorModel();
                vEEditorModel.projectXML = save;
                vEEditorModel.inPoint = this.mInPoint;
                vEEditorModel.outputPoint = this.mOutPoint;
                vEEditorModel.reverseDone = this.mResManager.mReverseDone;
                vEEditorModel.videoOutRes = this.mVideoOutRes;
                vEEditorModel.separateAV = this.mbSeparateAV.booleanValue();
                vEEditorModel.masterTrackIndex = this.mMasterTrackIndex;
                vEEditorModel.audioEffectFilterIndex = this.mAudioEffectfilterIndex;
                vEEditorModel.colorFilterIndex = this.mColorFilterIndex;
                vEEditorModel.videoPaths = this.mResManager.mVideoPaths;
                vEEditorModel.audioPaths = this.mResManager.mAudioPaths;
                vEEditorModel.transitions = this.mResManager.mTransitions;
                vEEditorModel.backgroundColor = this.mBackGroundColor;
                vEEditorModel.outputFile = this.mOutputFile;
                vEEditorModel.watermarkFile = this.waterMarkFile;
                vEEditorModel.watermarkWidth = this.waterMarkWidth;
                vEEditorModel.watermarkHeight = this.waterMarkHeight;
                vEEditorModel.watermarkOffsetX = this.waterMarkOffsetX;
                vEEditorModel.watermarkOffsetY = this.waterMarkOffsetY;
                if (this.mCurColorFilter != null) {
                    vEEditorModel.colorFilterLeftPath = this.mCurColorFilter.getLeftResPath();
                    vEEditorModel.colorFilterRightPath = this.mCurColorFilter.getRightResPath();
                    vEEditorModel.colorFilterPosition = this.mCurColorFilter.getPosition();
                    vEEditorModel.colorFilterIntensity = this.mCurColorFilter.getIntensity();
                    vEEditorModel.useColorFilterResIntensity = this.mCurColorFilter.useFilterResIntensity();
                }
            }
        }
        return null;
    }

    public synchronized int seek(int i, SEEK_MODE seek_mode) {
        VELogUtil.w(TAG, "seek...");
        this.mSeekListener = null;
        return this.mVideoEditor.seek(i, this.mSurfaceWidth, this.mSurfaceHeight, seek_mode.getValue());
    }

    public synchronized int seek(int i, SEEK_MODE seek_mode, VEListener.VEEditorSeekListener vEEditorSeekListener) {
        int seek;
        VELogUtil.w(TAG, "seek...");
        if ((seek_mode.getValue() | SEEK_MODE.EDITOR_SEEK_FLAG_LastSeek.getValue()) != 0) {
            this.mSeekListener = vEEditorSeekListener;
        }
        seek = this.mVideoEditor.seek(i, this.mSurfaceWidth, this.mSurfaceHeight, seek_mode.getValue());
        if (seek != 0) {
            VELogUtil.e(TAG, "seek failed, result = " + seek);
            this.mSeekListener = null;
        }
        return seek;
    }

    public synchronized int seekIFrame(int i, SEEK_MODE seek_mode) {
        VELogUtil.w(TAG, "seekIFrame...");
        return this.mVideoEditor.seek(i, this.mSurfaceWidth, this.mSurfaceHeight, seek_mode.getValue() | 2);
    }

    public synchronized int set2DBrushCanvasAlpha(float f2) {
        return this.mVideoEditor.set2DBrushCanvasAlpha(f2);
    }

    public synchronized int set2DBrushColor(@ColorInt int i) {
        float f2;
        f2 = ((float) ((i >>> 24) & 255)) * 0.003921569f;
        return this.mVideoEditor.set2DBrushColor(((float) ((i >>> 16) & 255)) * 0.003921569f, ((float) ((i >>> 8) & 255)) * 0.003921569f, ((float) (i & 255)) * 0.003921569f, f2);
    }

    public synchronized int set2DBrushSize(float f2) {
        return this.mVideoEditor.set2DBrushSize(f2);
    }

    public synchronized int setAudioOffset(int i, int i2) {
        return this.mVideoEditor.setAudioOffset(i, i2);
    }

    public synchronized void setBackgroundColor(int i) {
        this.mBackGroundColor = i;
        this.mVideoEditor.setBackGroundColor(i);
    }

    public synchronized int setColorFilter(String str) {
        return setColorFilter(str, 0.0f, true);
    }

    public synchronized int setColorFilter(String str, float f2) {
        return setColorFilter(str, f2, false);
    }

    public synchronized int setColorFilter(String str, String str2, float f2) {
        return setColorFilter(str, str2, f2, 0.0f, true);
    }

    public synchronized int setColorFilter(String str, String str2, float f2, float f3) {
        return setColorFilter(str, str2, f2, f3, false);
    }

    public synchronized void setCompileListener(VEListener.VEEditorCompileListener vEEditorCompileListener, Looper looper) {
        this.mCompileListener = vEEditorCompileListener;
        if (looper != null) {
            this.mMessageHandler = new VEEditorMessageHandler(looper);
        } else {
            this.mMessageHandler = null;
        }
    }

    public synchronized void setCrop(int i, int i2, int i3, int i4) {
        VEKeyValue vEKeyValue = new VEKeyValue();
        vEKeyValue.add("iesve_veeditor_cut_scale", i4 / i3);
        MonitorUtils.monitorStatistics("iesve_veeditor_cut_scale", 1, vEKeyValue);
        this.mVideoEditor.setCrop(i, i2, i3, i4);
    }

    public synchronized int setDestroyVersion(boolean z) {
        return this.mVideoEditor.setDestroyVersion(z);
    }

    public synchronized void setDisplayPos(int i, int i2, int i3, int i4) {
        VELogUtil.i(TAG, "setDisplayPos... " + i + " " + i2 + " " + i3 + " " + i4);
        setDisplayState(((float) i3) / ((float) this.mInitDisplayWidth), ((float) i4) / ((float) this.mInitDisplayHeight), 0.0f, -(((this.mSurfaceWidth / 2) - (i3 / 2)) - i), ((this.mSurfaceHeight / 2) - (i4 / 2)) - i2);
    }

    public synchronized void setDisplayState(float f2, float f3, float f4, int i, int i2) {
        VEKeyValue vEKeyValue = new VEKeyValue();
        vEKeyValue.add("iesve_veeditor_video_scale_width", f2).add("iesve_veeditor_video_scale_heigh", f3);
        MonitorUtils.monitorStatistics("iesve_veeditor_video_scale", 1, vEKeyValue);
        this.rotate = f4;
        this.scaleW = f3;
        this.scaleH = f3;
        VELogUtil.i(TAG, "setDisplayState... " + f2 + " " + f3 + " " + f4 + " " + i + " " + i2);
        this.mVideoEditor.setDisplayState(f2, f3, f4, 0.0f, i, i2, false);
    }

    public synchronized void setDldThrVal(int i) {
        this.mVideoEditor.setDldThrVal(i);
    }

    public synchronized void setDleEnabled(boolean z) {
        this.mVideoEditor.setDleEnabled(z);
    }

    public synchronized int setEffectListener(VEListener.VEEditorEffectListener vEEditorEffectListener) {
        TEEffectCallback tEEffectCallback = new TEEffectCallback();
        tEEffectCallback.setListener(vEEditorEffectListener);
        VERuntime.getInstance().setEffectCallback(tEEffectCallback);
        return 0;
    }

    public synchronized void setEnableMultipleAudioFilter(boolean z) {
        this.mVideoEditor.setEnableMultipleAudioFilter(z);
    }

    public synchronized void setExpandLastFrame(boolean z) {
        this.mVideoEditor.setExpandLastFrame(z);
    }

    public synchronized int setFileRotate(int i, int i2, ROTATE_DEGREE rotate_degree) {
        TEInterface tEInterface;
        VELogUtil.i(TAG, "setFileRotate..." + i + " " + i2 + " " + rotate_degree);
        tEInterface = this.mVideoEditor;
        return tEInterface.setClipAttr(0, i, i2, "clip rotate", "" + rotate_degree.ordinal());
    }

    public synchronized void setFirstFrameListener(VEListener.VEFirstFrameListener vEFirstFrameListener) {
        this.mFirstFrameListener = vEFirstFrameListener;
    }

    public synchronized int setInOut(int i, int i2) {
        int prepareEngine;
        synchronized (this) {
            VEKeyValue vEKeyValue = new VEKeyValue();
            vEKeyValue.add("iesve_veeditor_cut_duration", i2 - i);
            MonitorUtils.monitorStatistics("iesve_veeditor_cut_duration", 1, vEKeyValue);
            VELogUtil.i(TAG, "setInOut... " + i + " " + i2);
            this.mVideoEditor.stop();
            this.mVideoEditor.setTimeRange(i, i2, 0);
            prepareEngine = this.mVideoEditor.prepareEngine(0);
        }
        return prepareEngine;
        return prepareEngine;
    }

    public synchronized int setInOut(int i, int i2, SET_RANGE_MODE set_range_mode) {
        int prepareEngine;
        synchronized (this) {
            VEKeyValue vEKeyValue = new VEKeyValue();
            vEKeyValue.add("iesve_veeditor_cut_duration", i2 - i);
            MonitorUtils.monitorStatistics("iesve_veeditor_cut_duration", 1, vEKeyValue);
            VELogUtil.i(TAG, "setInOut... " + i + " " + i2 + " mode " + set_range_mode.getValue());
            this.mVideoEditor.stop();
            this.mVideoEditor.setTimeRange(i, i2, set_range_mode.getValue());
            prepareEngine = this.mVideoEditor.prepareEngine(0);
        }
        return prepareEngine;
        return prepareEngine;
    }

    public synchronized int setInfoStickerAlpha(int i, float f2) {
        VELogUtil.i(TAG, "setInfoStickerAlpha... index: " + i + "alpha: " + f2);
        if (i < 0) {
            return -100;
        }
        return this.mVideoEditor.setFilterParam(i, ENTITY_ALPHA, String.valueOf(f2));
    }

    public synchronized int setInfoStickerEditMode(boolean z) {
        return -1;
    }

    public synchronized int setInfoStickerLayer(int i, int i2) {
        VELogUtil.i(TAG, "setInfoStickerLayer... index: " + i + "layer: " + i2);
        if (i < 0) {
            return -100;
        }
        return this.mVideoEditor.setFilterParam(i, ENTITY_LAYER, String.valueOf(i2));
    }

    public synchronized int setInfoStickerPosition(int i, float f2, float f3) {
        VELogUtil.i(TAG, "setInfoStickerPosition... index: " + i + "offsetX: " + f2 + "offsetY: " + f3);
        if (i < 0) {
            return -100;
        }
        return this.mVideoEditor.setFilterParam(i, ENTITY_POSITION_X, String.valueOf(f2)) + this.mVideoEditor.setFilterParam(i, ENTITY_POSITION_Y, String.valueOf(f3));
    }

    public synchronized int setInfoStickerRotation(int i, float f2) {
        VELogUtil.i(TAG, "setInfoStickerRotation... index: " + i + "degree: " + f2);
        if (i < 0) {
            return -100;
        }
        return this.mVideoEditor.setFilterParam(i, ENTITY_ROTATION, String.valueOf(f2));
    }

    public synchronized int setInfoStickerScale(int i, float f2) {
        VELogUtil.i(TAG, "setInfoStickerScale... index: " + i + "scale: " + f2);
        if (i < 0) {
            return -100;
        }
        return this.mVideoEditor.setFilterParam(i, ENTITY_SCALE_X, String.valueOf(f2)) + this.mVideoEditor.setFilterParam(i, ENTITY_SCALE_Y, String.valueOf(f2));
    }

    public synchronized int setInfoStickerTime(int i, int i2, int i3) {
        VELogUtil.i(TAG, "setInfoStickerTime... index: " + i + "startTime: " + i2 + "endTime: " + i3);
        if (i < 0) {
            return -100;
        }
        TEMonitorFilterMgr.TEMonitorFilter tEMonitorFilter = this.mTEMonitorFilterMgr.infoStickerMap.get(Integer.valueOf(i));
        if (tEMonitorFilter != null) {
            tEMonitorFilter.start = i2;
            tEMonitorFilter.duration = i3 - i2;
        }
        return this.mVideoEditor.setFilterParam(i, ENTITY_START_TIME, String.valueOf(i2)) + this.mVideoEditor.setFilterParam(i, ENTITY_END_TIME, String.valueOf(i3));
    }

    public synchronized void setLoopPlay(boolean z) {
        VELogUtil.i(TAG, "setLoopPlay");
        this.mVideoEditor.setLooping(z);
    }

    public synchronized void setMaxWidthHeight(int i, int i2) {
        this.mVideoEditor.setMaxWidthHeight(i, i2);
    }

    public synchronized int setMusicSrtEffect(VEMusicSRTEffectParam vEMusicSRTEffectParam) {
        int i;
        i = -1;
        if (this.mVideoEditor != null) {
            if (this.mMusicSRTEffectFilterIndex <= 0) {
                this.mMusicSRTEffectFilterIndex = this.mVideoEditor.addFilters(new int[]{0}, new String[]{"music srt effect filter"}, new int[]{0}, new int[]{this.mOutPoint}, new int[]{0}, new int[]{10})[0];
            }
            i = this.mVideoEditor.setFilterParam(this.mMusicSRTEffectFilterIndex, MUSIC_SRT_EFFECT_PARAM, vEMusicSRTEffectParam);
        }
        return i;
    }

    public synchronized void setOnErrorListener(@NonNull VECommonCallback vECommonCallback) {
        this.mUserCommonErrorCallback = vECommonCallback;
        VELogUtil.i(TAG, "setOnErrorListener...");
    }

    public synchronized void setOnInfoListener(@NonNull VECommonCallback vECommonCallback) {
        VELogUtil.i(TAG, "setOnInfoListener...");
        this.mUserCommonInfoCallback = vECommonCallback;
    }

    public synchronized void setPageMode(int i) {
        this.mPageMode = i;
        VELogUtil.i(TAG, "setPageMode");
        this.mVideoEditor.setPageMode(i);
    }

    public synchronized int setPreviewFps(int i) {
        this.mVideoEditor.setPreviewFps(i);
        return 0;
    }

    public synchronized void setReDrawBmp(Bitmap bitmap) {
        if (this.mFirstTimeSurfaceCreate) {
            if (this.mCurrentBmp != null && !this.mCurrentBmp.isRecycled()) {
                this.mCurrentBmp.recycle();
            }
            this.mCurrentBmp = Bitmap.createBitmap(bitmap);
            this.mReDrawSurface = true;
            this.mReDrawSurfaceOnce = true;
        }
    }

    public synchronized int setReverseMediaPaths(@NonNull String[] strArr, String[] strArr2) {
        VELogUtil.w(TAG, "setReverseMediaPaths");
        if (strArr != null) {
            if (strArr.length > 0) {
                if (strArr2 == null || strArr2.length <= 0) {
                    VELogUtil.w(TAG, "setReverseMediaPaths with reverseAudioPaths is null");
                }
                if (this.mResManager != null) {
                    this.mResManager.mReverseAudioPaths = strArr2;
                    this.mResManager.mReverseVideoPath = strArr;
                    this.mResManager.mReverseDone = true;
                }
                return 0;
            }
        }
        VELogUtil.e(TAG, "setReverseMediaPaths error, reverseVideoPaths = null");
        return -100;
    }

    public synchronized int setReverseVideoPaths(String[] strArr) {
        VELogUtil.w(TAG, "setReverseVideoPaths");
        if (strArr != null) {
            if (strArr.length > 0) {
                if (this.mResManager != null) {
                    this.mResManager.mReverseVideoPath = strArr;
                    this.mResManager.mReverseDone = true;
                }
                return 0;
            }
        }
        VELogUtil.e(TAG, "setReverseVideoPaths error, reverseVideoPaths is null");
        return -100;
    }

    public synchronized int setScaleMode(SCALE_MODE scale_mode) {
        VELogUtil.w(TAG, "setScaleMode...");
        if (scale_mode == SCALE_MODE.SCALE_MODE_CENTER_CROP) {
            this.mVideoEditor.setResizer(2, 0.0f, 0.0f);
        } else if (scale_mode == SCALE_MODE.SCALE_MODE_CENTER_INSIDE) {
            this.mVideoEditor.setResizer(1, 0.0f, 0.0f);
        } else if (scale_mode == SCALE_MODE.SCALE_MODE_CENTER_INSIDE_WITH_2DENGINE) {
            this.mVideoEditor.setResizer(3, 0.0f, 0.0f);
        }
        return 0;
    }

    public synchronized int setSpeedRatio(float f2) {
        this.mVideoEditor.setSpeedRatio(f2);
        return 0;
    }

    public synchronized int setSpeedRatioAndUpdate(float f2) {
        VELogUtil.w(TAG, "setSpeedRatioAndUpdate " + f2);
        this.mVideoEditor.stop();
        if (f2 > 3.0f) {
            f2 = 3.0f;
        }
        this.mVideoEditor.setSpeedRatio(f2);
        this.mVideoEditor.createTimeline();
        return this.mVideoEditor.prepareEngine(0);
    }

    public synchronized int setStickerAnimator(int i, @NonNull VEStickerAnimator vEStickerAnimator) {
        VELogUtil.w(TAG, "addAnimator...");
        if (i >= 0) {
            if (vEStickerAnimator != null) {
                int stickerFilterIndex = this.mVideoEditor.getStickerFilterIndex(i);
                if (stickerFilterIndex < 0) {
                    return stickerFilterIndex;
                }
                return this.mVideoEditor.setFilterParam(stickerFilterIndex, "animator", vEStickerAnimator);
            }
        }
        return -100;
    }

    public synchronized void setSurfaceReDraw(boolean z) {
        this.mReDrawSurface = z;
        if (!z) {
            this.mCurrentBmp = null;
        }
    }

    public synchronized int setTimeRange(int i, int i2, SET_RANGE_MODE set_range_mode) {
        int timeRange;
        synchronized (this) {
            timeRange = this.mVideoEditor.setTimeRange(i, i2, set_range_mode.getValue());
        }
        return timeRange;
        return timeRange;
    }

    public synchronized void setUseLargeMattingModel(boolean z) {
        synchronized (this) {
            this.mVideoEditor.setUseLargeMattingModel(z);
        }
    }

    public synchronized void setVEEncoderListener(VEListener.VEEncoderListener vEEncoderListener) {
        this.mEncoderListener = vEEncoderListener;
    }

    public synchronized void setVideoPaths(String[] strArr) {
        VELogUtil.w(TAG, "setVideoPaths");
        this.mResManager.mVideoPaths = strArr;
    }

    public synchronized boolean setVolume(int i, int i2, float f2) {
        boolean trackVolume;
        synchronized (this) {
            VELogUtil.w(TAG, "setVolume... index " + i + " type " + i2);
            trackVolume = this.mVideoEditor.setTrackVolume(i2, this.mTrackIndexManager.getNativeTrackIndex(1, i), f2);
        }
        return trackVolume;
        return trackVolume;
    }

    public synchronized void setWidthHeight(int i, int i2) {
        this.mVideoEditor.setWidthHeight(i, i2);
    }

    public synchronized boolean testSerialization() {
        return this.mVideoEditor.testSerialization();
    }

    public synchronized int undo2DBrush() {
        return this.mVideoEditor.undo2DBrush();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0031, code lost:
        return -100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0034, code lost:
        return -100;
     */
    public synchronized int updateAudioTrack(int i, int i2, int i3, int i4, int i5, boolean z) {
        synchronized (this) {
            VELogUtil.i(TAG, "updateAudioTrack...");
            if (i < 0) {
                return -100;
            }
            if (i3 > i2 && i2 >= 0) {
                if (i5 > i4 && i4 >= 0) {
                    int updateAudioTrack = this.mVideoEditor.updateAudioTrack(this.mTrackIndexManager.getNativeTrackIndex(1, i), i4, i5, i2, i3, z);
                    return updateAudioTrack;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0047, code lost:
        return -100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x004a, code lost:
        return -100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x004d, code lost:
        return -100;
     */
    public synchronized int updateAudioTrack(int i, int i2, int i3, int i4, int i5, boolean z, int i6, int i7) {
        synchronized (this) {
            VELogUtil.i(TAG, "updateAudioTrack...");
            if (i < 0) {
                return -100;
            }
            if (i3 > i2 && i2 >= 0) {
                if (i5 > i4 && i4 >= 0) {
                    if (i7 > i6 && i6 >= 0) {
                        int updateAudioTrack = this.mVideoEditor.updateAudioTrack(this.mTrackIndexManager.getNativeTrackIndex(1, i), i4, i5, i2, i3, z, i6, i7);
                        return updateAudioTrack;
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002d, code lost:
        return -100;
     */
    public synchronized int updateAudioTrack(int i, int i2, int i3, boolean z) {
        synchronized (this) {
            VELogUtil.w(TAG, "updateAudioTrack...");
            if (i < 0) {
                return -100;
            }
            if (i3 > i2 && i2 >= 0) {
                int updateAudioTrack = this.mVideoEditor.updateAudioTrack(this.mTrackIndexManager.getNativeTrackIndex(1, i), 0, i3 - i2, i2, i3, z);
                return updateAudioTrack;
            }
        }
    }

    public synchronized void updatePreViewResolution(int i, int i2, int i3, int i4) {
        this.mVideoEditor.updateResolution(i, i2, i3, i4);
    }

    public synchronized int updateSceneFileOrder(VETimelineParams vETimelineParams) {
        synchronized (this) {
            this.mVideoEditor.stop();
            int updateSceneFileOrder = this.mVideoEditor.updateSceneFileOrder(vETimelineParams);
            if (updateSceneFileOrder < 0) {
                VELogUtil.e(TAG, "updateSceneFileOrder failed, ret = " + updateSceneFileOrder);
                return updateSceneFileOrder;
            }
            this.mMasterTrackIndex = vETimelineParams.videoFileIndex[0];
            this.mVideoEditor.createTimeline();
            this.mVideoEditor.setTimeRange(0, updateSceneFileOrder, 0);
            int prepareEngine = this.mVideoEditor.prepareEngine(0);
            if (prepareEngine == 0) {
                return 0;
            }
            VELogUtil.e(TAG, "Prepare Engine failed, ret = " + prepareEngine);
            return prepareEngine;
        }
    }

    public synchronized int updateSceneTime(VETimelineParams vETimelineParams) {
        VELogUtil.w(TAG, "update sence time");
        synchronized (this) {
            this.mVideoEditor.stop();
            int updateSenceTime = this.mVideoEditor.updateSenceTime(vETimelineParams);
            if (updateSenceTime < 0) {
                VELogUtil.e(TAG, "updateSceneTime failed, ret = " + updateSenceTime);
                return updateSenceTime;
            }
            this.mMasterTrackIndex = 0;
            this.mVideoEditor.setTimeRange(0, updateSenceTime, 0);
            int prepareEngine = this.mVideoEditor.prepareEngine(0);
            if (prepareEngine == 0) {
                return 0;
            }
            VELogUtil.e(TAG, "Prepare Engine failed, ret = " + prepareEngine);
            return prepareEngine;
        }
    }

    public synchronized int updateSceneTime(VETimelineParams vETimelineParams, int i, int i2) {
        VELogUtil.w(TAG, "update sence time with start/end time");
        synchronized (this) {
            this.mVideoEditor.stop();
            int updateSenceTime = this.mVideoEditor.updateSenceTime(vETimelineParams);
            if (updateSenceTime < 0) {
                VELogUtil.e(TAG, "updateSceneTime failed, ret = " + updateSenceTime);
                return updateSenceTime;
            }
            this.mMasterTrackIndex = 0;
            this.mVideoEditor.setTimeRange(i, i2, 0);
            int prepareEngine = this.mVideoEditor.prepareEngine(0);
            if (prepareEngine == 0) {
                return 0;
            }
            VELogUtil.e(TAG, "Prepare Engine failed, ret = " + prepareEngine);
            return prepareEngine;
        }
    }

    public synchronized int updateSegmentVolume(int i, float f2) {
        if (i < 0) {
            return -100;
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        return this.mVideoEditor.setFilterParam(i, AUDIO_VOLUME, "" + f2);
    }

    public synchronized int updateVideoClips(String[] strArr, int[] iArr, int[] iArr2) {
        VELogUtil.w(TAG, "update video clips.");
        synchronized (this) {
            VELogUtil.i(TAG, "init...");
            this.mVideoEditor.stop();
            this.mMusicSRTEffectFilterIndex = -1;
            int updateScene = this.mVideoEditor.updateScene(strArr, iArr, iArr2);
            if (updateScene != 0) {
                VELogUtil.e(TAG, "Create Scene failed, ret = " + updateScene);
            }
            this.mResManager.mVideoPaths = strArr;
            this.mMasterTrackIndex = 0;
            this.mVideoEditor.createTimeline();
            int prepareEngine = this.mVideoEditor.prepareEngine(0);
            if (prepareEngine != 0) {
                VELogUtil.e(TAG, "Prepare Engine failed, ret = " + prepareEngine);
                return prepareEngine;
            }
            seek(0, SEEK_MODE.EDITOR_SEEK_FLAG_LastSeek);
            return 0;
        }
    }
}
