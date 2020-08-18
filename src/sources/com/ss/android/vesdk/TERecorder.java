package com.ss.android.vesdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Surface;
import android.view.WindowManager;
import com.ss.android.medialib.FaceBeautyInvoker;
import com.ss.android.medialib.audio.AudioDataProcessThread;
import com.ss.android.medialib.camera.ImageFrame;
import com.ss.android.medialib.common.Common;
import com.ss.android.medialib.common.ImageUtils;
import com.ss.android.medialib.listener.NativeInitListener;
import com.ss.android.medialib.listener.SlamDetectListener;
import com.ss.android.ttve.monitor.MonitorUtils;
import com.ss.android.vesdk.VECameraSettings;
import com.ss.android.vesdk.VEListener;
import com.ss.android.vesdk.VEVideoEncodeSettings;
import com.ss.android.vesdk.keyvaluepair.VEKeyValue;
import com.ss.android.vesdk.runtime.VERecorderResManager;
import com.ss.android.vesdk.runtime.VERuntime;
import com.ss.android.vesdk.utils.VETextUtils;
import e.a.a.a;
import e.a.a.b;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class TERecorder implements a, AudioDataProcessThread.OnProcessDataListener {
    public static final int PIC_STATUS_OUTPUT = 2;
    public static final int PIC_STATUS_RENDER = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_RECORDING = 3;
    public static final int STATE_RUNNING = 2;
    /* access modifiers changed from: private */
    public static final String TAG = "TERecorder";
    public static final int TYPE_PLAY_AUDIOTRACK = 2;
    public static final int TYPE_PLAY_OPENSL = 4;
    public static final int TYPE_RECORD_AUDIORECODER = 1;
    public static final int TYPE_RECORD_EMPTY = 0;
    public static final int TYPE_RECORD_PLAY_AUDIOTRACK = 3;
    public static final int TYPE_RECORD_PLAY_OPENSL = 5;
    private VEAudioEncodeSettings mAudioEncodeSettings;
    private b mAudioRecorder;
    private boolean mBGMIsLoop;
    private String mBGMPath;
    private int mBGMTrimIn;
    private float mBGMVolume;
    /* access modifiers changed from: private */
    public VECameraSettings mCameraSettings;
    private Context mContext;
    /* access modifiers changed from: private */
    public Handler mExternalListenerInvokerHandler;
    /* access modifiers changed from: private */
    public VEListener.VERecorderNativeInitListener mExternalNativeInitListener;
    private OnPictureRenderListener mExternalPictureRenderListener;
    /* access modifiers changed from: private */
    public IRenderCallback mExternalRenderCallback;
    /* access modifiers changed from: private */
    public List<OnSlamDetectListener> mExternalSlamDetectListeners;
    private Common.IOnOpenGLCallback mGLCreateCallback;
    /* access modifiers changed from: private */
    public Texture mInputTexture;
    /* access modifiers changed from: private */
    public AtomicBoolean mIsCameraSettings;
    /* access modifiers changed from: private */
    public boolean mIsTextureCreatedBySelf;
    private int mLastAudioOption;
    private NativeInitListener mNativeInitListener;
    /* access modifiers changed from: private */
    public FaceBeautyInvoker mNativeInvoker;
    private VEPreviewSettings mPreviewSettings;
    private int mRecordCount;
    private VERecorderResManager mResManager;
    private SlamDetectListener mSlamDetectListener;
    private volatile int mState;
    private ExecutorService mThreadPool;
    private VERuntime mVERuntime;
    private VEVideoEncodeSettings mVideoEncodeSettings;

    private static class ConcatResultListenerInvoker implements Runnable {
        private OnConcatFinishedListener mListener;
        private int mRet;

        ConcatResultListenerInvoker(@NonNull OnConcatFinishedListener onConcatFinishedListener, int i) {
            this.mListener = onConcatFinishedListener;
            this.mRet = i;
        }

        public void run() {
            this.mListener.onConcatFinished(this.mRet);
        }
    }

    public interface IRenderCallback {
        Texture onCreateTexture();

        boolean onDestroy();

        void onTextureCreated(Texture texture);
    }

    private static class NativeHardEncoderInitListenerInvoker implements Runnable {
        private VEListener.VERecorderNativeInitListener mListener;
        private boolean mRet;

        NativeHardEncoderInitListenerInvoker(@NonNull VEListener.VERecorderNativeInitListener vERecorderNativeInitListener, boolean z) {
            this.mListener = vERecorderNativeInitListener;
            this.mRet = z;
        }

        public void run() {
            this.mListener.onHardEncoderInit(this.mRet);
        }
    }

    private static class NativeInitListenerInvoker implements Runnable {
        private VEListener.VERecorderNativeInitListener mListener;
        private int mRet;

        NativeInitListenerInvoker(@NonNull VEListener.VERecorderNativeInitListener vERecorderNativeInitListener, int i) {
            this.mListener = vERecorderNativeInitListener;
            this.mRet = i;
        }

        public void run() {
            this.mListener.onNativeInit(this.mRet, "Native init");
        }
    }

    public interface OnConcatFinishedListener {
        void onConcatFinished(int i);
    }

    public interface OnPictureRenderListener {
        void onImage(Bitmap bitmap);

        void onResult(int i, int i2);
    }

    public interface OnSlamDetectListener {
        void onSlam(boolean z);
    }

    private static class RenderPictureOnImageListenerInvoker implements Runnable {
        private Bitmap mBitmap;
        private OnPictureRenderListener mListener;

        public RenderPictureOnImageListenerInvoker(OnPictureRenderListener onPictureRenderListener, Bitmap bitmap) {
            this.mListener = onPictureRenderListener;
            this.mBitmap = bitmap;
        }

        public void run() {
            this.mListener.onImage(this.mBitmap);
        }
    }

    private static class RenderPictureOnResultListenerInvoker implements Runnable {
        private OnPictureRenderListener mListener;
        private int mRet;
        private int mState;

        public RenderPictureOnResultListenerInvoker(OnPictureRenderListener onPictureRenderListener, int i, int i2) {
            this.mListener = onPictureRenderListener;
            this.mState = i;
            this.mRet = i2;
        }

        public void run() {
            this.mListener.onResult(this.mState, this.mRet);
        }
    }

    private static class SlamDetectListenerInvoker implements Runnable {
        private List<OnSlamDetectListener> mListeners;
        private boolean mRet;

        SlamDetectListenerInvoker(@NonNull List<OnSlamDetectListener> list, boolean z) {
            this.mListeners = list;
            this.mRet = z;
        }

        public void run() {
            for (OnSlamDetectListener onSlamDetectListener : this.mListeners) {
                onSlamDetectListener.onSlam(this.mRet);
            }
        }
    }

    public static class Texture {
        private SurfaceTexture mSurfaceTexture;
        private int mTextureID;

        public Texture(int i, SurfaceTexture surfaceTexture) {
            this.mTextureID = i;
            this.mSurfaceTexture = surfaceTexture;
        }

        public SurfaceTexture getSurfaceTexture() {
            return this.mSurfaceTexture;
        }

        public int getTextureID() {
            return this.mTextureID;
        }

        public void release() {
            SurfaceTexture surfaceTexture = this.mSurfaceTexture;
            if (surfaceTexture != null) {
                surfaceTexture.release();
                this.mSurfaceTexture = null;
            }
            int i = this.mTextureID;
            if (i != 0) {
                Common.deleteTextureID(i);
                this.mTextureID = 0;
            }
        }
    }

    public TERecorder(@NonNull String str, @NonNull Context context) {
        this(str, context, null);
    }

    public TERecorder(@NonNull String str, @NonNull Context context, @Nullable Handler handler) {
        this.mInputTexture = null;
        this.mThreadPool = Executors.newSingleThreadExecutor();
        this.mAudioEncodeSettings = null;
        this.mVideoEncodeSettings = null;
        this.mPreviewSettings = null;
        this.mCameraSettings = null;
        this.mIsCameraSettings = new AtomicBoolean(false);
        this.mLastAudioOption = -1;
        this.mExternalRenderCallback = null;
        this.mExternalNativeInitListener = null;
        this.mExternalPictureRenderListener = null;
        this.mExternalSlamDetectListeners = new ArrayList();
        this.mState = 0;
        this.mIsTextureCreatedBySelf = false;
        this.mGLCreateCallback = new Common.IOnOpenGLCallback() {
            /* class com.ss.android.vesdk.TERecorder.AnonymousClass4 */

            /* access modifiers changed from: private */
            public float[] fetchTransformMatrixFromTexture(SurfaceTexture surfaceTexture) {
                float[] fArr = new float[16];
                surfaceTexture.getTransformMatrix(fArr);
                return fArr;
            }

            private double getDrawFrameTime(long j) {
                long nanoTime = System.nanoTime();
                return ((double) (nanoTime - Math.min(Math.min(Math.abs(nanoTime - j), Build.VERSION.SDK_INT >= 17 ? Math.abs(SystemClock.elapsedRealtimeNanos() - j) : Long.MAX_VALUE), Math.abs((SystemClock.uptimeMillis() * 1000000) - j)))) / 1000000.0d;
            }

            private boolean verifyTexture(int i) {
                if (GLES20.glIsTexture(i)) {
                    return true;
                }
                VELogUtil.e(TERecorder.TAG, "OpenGL has created but encounter getting invalid textureID from render Callback");
                return false;
            }

            @Override // com.ss.android.medialib.common.Common.IOnOpenGLCallback
            public void onOpenGLCreate() {
                if (TERecorder.this.mExternalRenderCallback == null) {
                    VELogUtil.e(TERecorder.TAG, "Couldn't find render callback. Consider set render callback first");
                    return;
                }
                Texture onCreateTexture = TERecorder.this.mExternalRenderCallback.onCreateTexture();
                if (onCreateTexture == null) {
                    int genSurfaceTextureID = Common.genSurfaceTextureID();
                    Texture texture = new Texture(genSurfaceTextureID, new SurfaceTexture(genSurfaceTextureID));
                    boolean unused = TERecorder.this.mIsTextureCreatedBySelf = true;
                    onCreateTexture = texture;
                }
                if (!verifyTexture(onCreateTexture.getTextureID())) {
                    VELogUtil.e(TERecorder.TAG, "Didn't attach render routine to texture due to invalid texture.");
                    return;
                }
                if (TERecorder.this.mInputTexture != null) {
                    TERecorder.this.mInputTexture.getSurfaceTexture().release();
                }
                Texture unused2 = TERecorder.this.mInputTexture = onCreateTexture;
                TERecorder.this.mInputTexture.getSurfaceTexture().setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
                    /* class com.ss.android.vesdk.TERecorder.AnonymousClass4.AnonymousClass1 */

                    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                        if (TERecorder.this.mNativeInvoker == null) {
                            VELogUtil.d(TERecorder.TAG, "OnFrameAvailable encounter mNativeInvoker == null");
                            return;
                        }
                        TERecorder.this.mNativeInvoker.onDrawFrame(TERecorder.this.mInputTexture.getTextureID(), AnonymousClass4.this.fetchTransformMatrixFromTexture(surfaceTexture));
                    }
                });
                TERecorder.this.mExternalRenderCallback.onTextureCreated(onCreateTexture);
            }

            @Override // com.ss.android.medialib.common.Common.IOnOpenGLCallback
            public void onOpenGLDestroy() {
                if (TERecorder.this.mIsTextureCreatedBySelf || (TERecorder.this.mExternalRenderCallback != null && TERecorder.this.mExternalRenderCallback.onDestroy())) {
                    TERecorder.this.mInputTexture.release();
                }
            }

            @Override // com.ss.android.medialib.common.Common.IOnOpenGLCallback
            public int onOpenGLRunning() {
                if (TERecorder.this.mInputTexture == null) {
                    VELogUtil.e(TERecorder.TAG, "Couldn't find source texture");
                    return -1;
                }
                SurfaceTexture surfaceTexture = TERecorder.this.mInputTexture.getSurfaceTexture();
                if (surfaceTexture == null) {
                    VELogUtil.e(TERecorder.TAG, "Couldn't find surfaceTexture from source texture");
                    return -1;
                }
                surfaceTexture.updateTexImage();
                TERecorder.this.mNativeInvoker.onDrawFrameTime(getDrawFrameTime(surfaceTexture.getTimestamp()));
                if (TERecorder.this.mIsCameraSettings.get() && TERecorder.this.mCameraSettings != null) {
                    FaceBeautyInvoker access$200 = TERecorder.this.mNativeInvoker;
                    TERecorder tERecorder = TERecorder.this;
                    access$200.updateRotation(tERecorder.getRotation(tERecorder.mCameraSettings.getFacingID()), TERecorder.this.mCameraSettings.getFacingID() == VECameraSettings.CAMERA_FACING_ID.FACING_FRONT);
                    VESize size = TERecorder.this.mCameraSettings.getSize();
                    TERecorder.this.mNativeInvoker.setPreviewSizeRatio(((float) size.height) / ((float) size.width));
                    TERecorder.this.mIsCameraSettings.set(false);
                }
                return 0;
            }
        };
        this.mNativeInitListener = new NativeInitListener() {
            /* class com.ss.android.vesdk.TERecorder.AnonymousClass5 */

            @Override // com.ss.android.medialib.listener.NativeInitListener
            public void onNativeInitCallBack(int i) {
                if (TERecorder.this.mExternalListenerInvokerHandler != null) {
                    TERecorder.this.mExternalListenerInvokerHandler.post(new NativeInitListenerInvoker(TERecorder.this.mExternalNativeInitListener, i));
                } else if (TERecorder.this.mExternalNativeInitListener != null) {
                    TERecorder.this.mExternalNativeInitListener.onNativeInit(i, "");
                }
            }

            @Override // com.ss.android.medialib.listener.NativeInitListener
            public void onNativeInitHardEncoderRetCallback(int i, int i2) {
                boolean z = true;
                if (TERecorder.this.mExternalListenerInvokerHandler != null) {
                    Handler access$100 = TERecorder.this.mExternalListenerInvokerHandler;
                    VEListener.VERecorderNativeInitListener access$1000 = TERecorder.this.mExternalNativeInitListener;
                    if (i == 0) {
                        z = false;
                    }
                    access$100.post(new NativeHardEncoderInitListenerInvoker(access$1000, z));
                } else if (TERecorder.this.mExternalNativeInitListener != null) {
                    VEListener.VERecorderNativeInitListener access$10002 = TERecorder.this.mExternalNativeInitListener;
                    if (i == 0) {
                        z = false;
                    }
                    access$10002.onHardEncoderInit(z);
                }
            }
        };
        this.mSlamDetectListener = new SlamDetectListener() {
            /* class com.ss.android.vesdk.TERecorder.AnonymousClass6 */

            @Override // com.ss.android.medialib.listener.SlamDetectListener
            public void onSlam(boolean z) {
                TERecorder.this.mExternalListenerInvokerHandler.post(new SlamDetectListenerInvoker(TERecorder.this.mExternalSlamDetectListeners, z));
            }
        };
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("workspace can't be null or empty String");
        } else if (context != null) {
            this.mExternalListenerInvokerHandler = handler;
            this.mVERuntime = VERuntime.getInstance();
            this.mVERuntime.init(context, str);
            this.mContext = context.getApplicationContext();
            this.mResManager = new VERecorderResManager(str);
            attachNativeInvoker();
        } else {
            throw new IllegalArgumentException("context can't be null");
        }
    }

    private void attachNativeInvoker() {
        this.mNativeInvoker = new FaceBeautyInvoker();
        this.mNativeInvoker.setOnOpenGLCallback(this.mGLCreateCallback);
        this.mNativeInvoker.setNativeInitListener2(this.mNativeInitListener);
        FaceBeautyInvoker.addSlamDetectListener(this.mSlamDetectListener);
    }

    private boolean checkCameraSettingsChange(VECameraSettings vECameraSettings) {
        if (vECameraSettings == null) {
            return false;
        }
        VECameraSettings vECameraSettings2 = this.mCameraSettings;
        if (vECameraSettings2 == null || vECameraSettings2.getOrientation() != vECameraSettings.getOrientation() || this.mCameraSettings.getFacingID() != vECameraSettings.getFacingID()) {
            return true;
        }
        VESize size = this.mCameraSettings.getSize();
        VESize size2 = vECameraSettings.getSize();
        return (size2.width == size.width && size2.height == size.height) ? false : true;
    }

    private void detachNavieInvoer() {
        this.mNativeInvoker.setOnOpenGLCallback(null);
        this.mNativeInvoker.setNativeInitListener2(null);
        FaceBeautyInvoker.removeSlamDetectListener(this.mSlamDetectListener);
    }

    private int determineCurrentAudioOption() {
        if (!this.mPreviewSettings.isAudioRecordEnabled()) {
            return 0;
        }
        return TextUtils.isEmpty(this.mBGMPath) ? 1 : 5;
    }

    /* access modifiers changed from: private */
    public int getRotation(VECameraSettings.CAMERA_FACING_ID camera_facing_id) {
        int rotation = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        int i = 0;
        if (rotation != 0) {
            if (rotation == 1) {
                i = 90;
            } else if (rotation == 2) {
                i = 180;
            } else if (rotation == 3) {
                i = 270;
            }
        }
        return camera_facing_id == VECameraSettings.CAMERA_FACING_ID.FACING_FRONT ? (540 - ((this.mCameraSettings.getOrientation() + i) % 360)) % 360 : ((this.mCameraSettings.getOrientation() - i) + 360) % 360;
    }

    private void initAudioPlayerIfNeed(int i) {
        if (i != this.mLastAudioOption) {
            if ((i & 4) == 0) {
                this.mNativeInvoker.uninitAudioPlayer();
                this.mNativeInvoker.setUseMusic(0);
            } else if (!TextUtils.isEmpty(this.mBGMPath)) {
                this.mNativeInvoker.setBGMVolume(this.mBGMVolume);
                this.mNativeInvoker.initAudioPlayer(this.mContext, this.mBGMPath, (long) this.mBGMTrimIn, this.mBGMIsLoop, false);
                this.mNativeInvoker.setUseMusic(1);
            } else {
                VELogUtil.e(TAG, "Illegal state. BGM path is empty. when current audio option is PLAY_OPENSL");
                throw new IllegalStateException("Shouldn't reach here");
            }
        }
    }

    private void initAudioRecorderIfNeed(int i) {
        if ((i & 1) != (this.mLastAudioOption & 1) || this.mAudioRecorder == null) {
            this.mAudioRecorder = new b(this);
            this.mAudioRecorder.init(1);
        }
    }

    private int initNativeInvokerWithSettings(VEVideoEncodeSettings vEVideoEncodeSettings, VEPreviewSettings vEPreviewSettings) {
        String effectModelResourceDirPath = this.mVERuntime.getEnv().getEffectModelResourceDirPath();
        String tempSegmentDirPath = this.mResManager.getTempSegmentDirPath();
        VESize videoRes = vEVideoEncodeSettings.getVideoRes();
        VESize renderSize = vEPreviewSettings.getRenderSize();
        int initFaceBeautyPlay = this.mNativeInvoker.initFaceBeautyPlay(renderSize.height, renderSize.width, tempSegmentDirPath, videoRes.width, videoRes.height, effectModelResourceDirPath, 0);
        if (initFaceBeautyPlay == 0) {
            return 0;
        }
        String str = TAG;
        VELogUtil.e(str, "NativeInvoker init Failed. ret = " + initFaceBeautyPlay);
        return initFaceBeautyPlay;
    }

    private void releaseAudioComponentIfNeed() {
        b bVar = this.mAudioRecorder;
        if (bVar != null) {
            bVar.unInit();
            this.mAudioRecorder = null;
        }
    }

    private void startAudioRecorderIfNeed(float f2, int i) {
        if ((i & 1) != 0) {
            this.mAudioRecorder.a((double) f2, true);
        }
    }

    private int updateAudioComponentIfNeedWithStartRecord(long j, float f2) {
        int determineCurrentAudioOption = determineCurrentAudioOption();
        initAudioRecorderIfNeed(determineCurrentAudioOption);
        initAudioPlayerIfNeed(determineCurrentAudioOption);
        startAudioRecorderIfNeed(f2, determineCurrentAudioOption);
        this.mNativeInvoker.setMusicTime((long) this.mBGMTrimIn, j);
        this.mLastAudioOption = determineCurrentAudioOption;
        return 0;
    }

    @Override // e.a.a.a
    public int addPCMData(byte[] bArr, int i) {
        this.mNativeInvoker.onAudioCallback(bArr, i);
        return 0;
    }

    public synchronized void addSlamDetectListener(@NonNull OnSlamDetectListener onSlamDetectListener) {
        this.mExternalSlamDetectListeners.add(onSlamDetectListener);
    }

    public synchronized void chooseSlamFace(int i) {
        this.mNativeInvoker.chooseSlamFace(i);
    }

    public synchronized int clearEnv() {
        int clearFragFile;
        if (!(this.mState == 1 && this.mState == 2)) {
            VELogUtil.e(TAG, "clearEnv() can only be invoked when state : 1,or2");
        }
        clearFragFile = this.mNativeInvoker.clearFragFile();
        if (clearFragFile != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker clearFragFile() failed. ret = " + clearFragFile);
        } else {
            this.mRecordCount = 0;
        }
        return clearFragFile;
    }

    public synchronized void clearSlamDetectListener() {
        this.mExternalSlamDetectListeners.clear();
    }

    @Override // e.a.a.a
    public int closeWavFile(boolean z) {
        return this.mNativeInvoker.closeWavFile(z);
    }

    public synchronized void concatAsync(@NonNull final String str, @NonNull final String str2, @NonNull final OnConcatFinishedListener onConcatFinishedListener) {
        if (!(this.mState == 1 || this.mState == 2)) {
            String str3 = TAG;
            VELogUtil.e(str3, "concat() can only be called in error state when current state code is " + this.mState);
        }
        if (this.mRecordCount <= 0) {
            String str4 = TAG;
            VELogUtil.e(str4, "Can't invoke concat() when current recorded segment count = " + this.mRecordCount);
        }
        this.mThreadPool.execute(new Runnable() {
            /* class com.ss.android.vesdk.TERecorder.AnonymousClass3 */

            public void run() {
                int concat = TERecorder.this.mNativeInvoker.concat(str, str2, "", "");
                if (concat < 0) {
                    String access$000 = TERecorder.TAG;
                    VELogUtil.e(access$000, "NativeInvoker concat failed. ret = " + concat);
                }
                if (TERecorder.this.mExternalListenerInvokerHandler != null) {
                    TERecorder.this.mExternalListenerInvokerHandler.post(new ConcatResultListenerInvoker(onConcatFinishedListener, concat));
                    return;
                }
                OnConcatFinishedListener onConcatFinishedListener = onConcatFinishedListener;
                if (onConcatFinishedListener != null) {
                    onConcatFinishedListener.onConcatFinished(concat);
                }
            }
        });
    }

    public synchronized int deleteLastFrag() {
        int deleteLastFrag;
        if (!(this.mState == 1 && this.mState == 2)) {
            VELogUtil.e(TAG, "deleteLastFrag() can only be invoked when state : 1,or2");
        }
        if (this.mRecordCount <= 0) {
            VELogUtil.e(TAG, "About to invoke deleteLastFrag when recordCount = " + this.mRecordCount);
        }
        deleteLastFrag = this.mNativeInvoker.deleteLastFrag();
        if (deleteLastFrag != 0) {
            VELogUtil.e(TAG, "NativeInvoker deleteLastFrag() failed. ret = " + deleteLastFrag);
        } else {
            this.mRecordCount--;
        }
        return deleteLastFrag;
    }

    public synchronized void destroy() {
        releaseAudioComponentIfNeed();
        int uninitFaceBeautyPlay = this.mNativeInvoker.uninitFaceBeautyPlay();
        if (uninitFaceBeautyPlay != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker uninitFaceBeautyPlay() failed. ret = " + uninitFaceBeautyPlay);
        }
        detachNavieInvoer();
        this.mExternalNativeInitListener = null;
        this.mExternalSlamDetectListeners.clear();
        this.mExternalRenderCallback = null;
        this.mExternalPictureRenderListener = null;
        this.mExternalListenerInvokerHandler = null;
        this.mNativeInitListener = null;
        this.mState = 0;
    }

    public synchronized long getEndFrameTime() {
        if (this.mState != 3) {
            String str = TAG;
            VELogUtil.e(str, "getEndFrameTime() when current state is " + this.mState);
        }
        return this.mNativeInvoker.getEndFrameTime();
    }

    public synchronized int getSlamFaceCount() {
        int slamFaceCount;
        slamFaceCount = this.mNativeInvoker.getSlamFaceCount();
        if (slamFaceCount != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker getSlamFaceCount failed. ret = " + slamFaceCount);
        }
        return slamFaceCount;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0033, code lost:
        return r2;
     */
    public synchronized int init(@NonNull VEVideoEncodeSettings vEVideoEncodeSettings, @NonNull VEAudioEncodeSettings vEAudioEncodeSettings, @NonNull VEPreviewSettings vEPreviewSettings, @Nullable VECameraSettings vECameraSettings) {
        if (!VERuntime.isValidAuthorization()) {
            return -1;
        }
        this.mVideoEncodeSettings = vEVideoEncodeSettings;
        this.mAudioEncodeSettings = vEAudioEncodeSettings;
        this.mPreviewSettings = vEPreviewSettings;
        this.mCameraSettings = vECameraSettings;
        int initNativeInvokerWithSettings = initNativeInvokerWithSettings(vEVideoEncodeSettings, vEPreviewSettings);
        if (initNativeInvokerWithSettings != 0) {
            String str = TAG;
            VELogUtil.e(str, "init failed ret: " + initNativeInvokerWithSettings);
        } else {
            this.mState = 1;
        }
    }

    @Override // e.a.a.a
    public int initAudioConfig(int i, int i2) {
        return this.mNativeInvoker.initAudioConfig(i, i2);
    }

    @Override // e.a.a.a
    public int initWavFile(int i, int i2, double d2) {
        return this.mNativeInvoker.initWavFile(i, i2, d2);
    }

    @Override // e.a.a.a
    public void lackPermission() {
        VELogUtil.e(TAG, "Failed. AudioRecorder open failed. Lack permission.");
    }

    @Override // com.ss.android.medialib.audio.AudioDataProcessThread.OnProcessDataListener, e.a.a.a
    public int onProcessData(byte[] bArr, int i) {
        return this.mNativeInvoker.addPCMData(bArr, i);
    }

    public synchronized void pauseEffectAudio(boolean z) {
        this.mNativeInvoker.pauseEffectAudio(z);
    }

    public synchronized int processTouchEvent(float f2, float f3) {
        int processTouchEvent;
        processTouchEvent = this.mNativeInvoker.processTouchEvent(f2, f3);
        if (processTouchEvent != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker processTouchEvent failed. ret = " + processTouchEvent);
        }
        return processTouchEvent;
    }

    @Override // e.a.a.a
    public void recordStatus(boolean z) {
        String str = TAG;
        VELogUtil.e(str, "Failed. AudioRecorder record error. isRecording = " + z);
    }

    public synchronized void removeSlamDetectListener(@NonNull OnSlamDetectListener onSlamDetectListener) {
        this.mExternalSlamDetectListeners.remove(onSlamDetectListener);
    }

    public synchronized int renderPicture(@NonNull byte[] bArr, int i, int i2, int i3, @NonNull final OnPictureRenderListener onPictureRenderListener) {
        if (this.mState != 2) {
            VELogUtil.e(TAG, "renderPicture() can only be invoked after in state:2");
            return VEResult.TER_INVALID_STAT;
        }
        ImageFrame imageFrame = new ImageFrame(bArr, i, i2, i3);
        return this.mNativeInvoker.renderPicture(imageFrame, imageFrame.getWidth(), imageFrame.getHeight(), new FaceBeautyInvoker.OnPictureCallbackV2() {
            /* class com.ss.android.vesdk.TERecorder.AnonymousClass2 */

            @Override // com.ss.android.medialib.FaceBeautyInvoker.OnPictureCallbackV2
            public void onImage(int[] iArr, int i, int i2) {
                Bitmap bitmap;
                if (iArr == null || iArr.length <= 0 || i <= 0 || i2 <= 0) {
                    String access$000 = TERecorder.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed. NativeInvoker renderPicture() return invalid data. data == null ? ");
                    sb.append(iArr == null);
                    sb.append(", data.length = ");
                    sb.append(iArr.length);
                    sb.append(", width = ");
                    sb.append(i);
                    sb.append(", height = ");
                    sb.append(i2);
                    VELogUtil.e(access$000, sb.toString());
                    bitmap = null;
                } else {
                    bitmap = Bitmap.createBitmap(iArr, i, i2, Bitmap.Config.ARGB_8888);
                }
                if (TERecorder.this.mExternalListenerInvokerHandler != null) {
                    TERecorder.this.mExternalListenerInvokerHandler.post(new RenderPictureOnImageListenerInvoker(onPictureRenderListener, null));
                    return;
                }
                OnPictureRenderListener onPictureRenderListener = onPictureRenderListener;
                if (onPictureRenderListener != null) {
                    onPictureRenderListener.onImage(bitmap);
                }
            }

            @Override // com.ss.android.medialib.FaceBeautyInvoker.OnPictureCallbackV2
            public void onResult(int i, int i2) {
                if (TERecorder.this.mExternalListenerInvokerHandler != null) {
                    TERecorder.this.mExternalListenerInvokerHandler.post(new RenderPictureOnResultListenerInvoker(onPictureRenderListener, i, i2));
                    return;
                }
                OnPictureRenderListener onPictureRenderListener = onPictureRenderListener;
                if (onPictureRenderListener != null) {
                    onPictureRenderListener.onResult(i, i2);
                }
            }
        });
    }

    public synchronized int setBeautyFace(int i, @Nullable String str) {
        int beautyFace;
        VEKeyValue vEKeyValue = new VEKeyValue();
        vEKeyValue.add("iesve_verecorder_set_beauty_algorithm", i).add("old", 0);
        MonitorUtils.monitorStatistics("iesve_verecorder_set_beauty_algorithm", 1, vEKeyValue);
        beautyFace = this.mNativeInvoker.setBeautyFace(i, str);
        if (beautyFace != 0) {
            String str2 = TAG;
            VELogUtil.e(str2, "NativeInvoker setBeautyFace failed. ret = " + beautyFace);
        }
        return beautyFace;
    }

    public synchronized int setBeautyFaceIntensity(float f2, float f3) {
        int beautyFaceIntensity;
        VEKeyValue vEKeyValue = new VEKeyValue();
        vEKeyValue.add("iesve_verecorder_set_beauty_level", f2).add("old", 0);
        MonitorUtils.monitorStatistics("iesve_verecorder_set_beauty_level", 1, vEKeyValue);
        beautyFaceIntensity = this.mNativeInvoker.setBeautyFaceIntensity(f2, f3);
        if (beautyFaceIntensity != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker setBeautyFaceIntensity failed. ret = " + beautyFaceIntensity);
        }
        return beautyFaceIntensity;
    }

    public synchronized void setCameraSettings(@NonNull VECameraSettings vECameraSettings) {
        if (checkCameraSettingsChange(vECameraSettings)) {
            this.mCameraSettings = vECameraSettings;
            this.mIsCameraSettings.set(true);
        }
    }

    public synchronized void setDeviceRotation(float[] fArr) {
        this.mNativeInvoker.setDeviceRotation(fArr);
    }

    public synchronized int setFaceReshape(@NonNull String str, float f2, float f3) {
        int reshape;
        VEKeyValue vEKeyValue = new VEKeyValue();
        vEKeyValue.add("iesve_verecorder_set_bigeyes_smallface_level", f2).add("old", 0);
        MonitorUtils.monitorStatistics("iesve_verecorder_set_bigeyes_smallface", 1, vEKeyValue);
        reshape = this.mNativeInvoker.setReshape(str, f2, f3);
        if (reshape != 0) {
            String str2 = TAG;
            VELogUtil.e(str2, "NativeInvoker setFaceReshape failed. ret = " + reshape);
        }
        return reshape;
    }

    public synchronized int setFilter(@NonNull String str) {
        int filter;
        VEKeyValue vEKeyValue = new VEKeyValue();
        vEKeyValue.add("iesve_verecorder_set_filter_click_idfilter_id", VETextUtils.parsePathSimpleName(str)).add("old", 0);
        MonitorUtils.monitorStatistics("iesve_verecorder_set_filter_click", 1, vEKeyValue);
        filter = this.mNativeInvoker.setFilter(str);
        if (filter != 0) {
            String str2 = TAG;
            VELogUtil.e(str2, "NativeInvoker setFilter failed ret = " + filter);
        }
        return filter;
    }

    public synchronized int setFilter(@NonNull String str, float f2) {
        int filter = setFilter(str);
        if (filter != 0) {
            return filter;
        }
        return setFilterIntensity(f2);
    }

    public synchronized int setFilter(@NonNull String str, @NonNull String str2, float f2) {
        int filter;
        VEKeyValue vEKeyValue = new VEKeyValue();
        vEKeyValue.add("iesve_verecorder_set_filter_slide_left_id", VETextUtils.parsePathSimpleName(str)).add("iesve_verecorder_set_filter_slide_right_id", VETextUtils.parsePathSimpleName(str2)).add("old", 0);
        MonitorUtils.monitorStatistics("iesve_verecorder_set_filter_slide", 1, vEKeyValue);
        filter = this.mNativeInvoker.setFilter(str, str2, f2);
        if (filter != 0) {
            String str3 = TAG;
            VELogUtil.e(str3, "NativeInvoker setFilter with position failed. ret = " + filter);
        }
        return filter;
    }

    public synchronized int setFilterIntensity(float f2) {
        int filterIntensity;
        filterIntensity = this.mNativeInvoker.setFilterIntensity(f2);
        if (filterIntensity != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker setFilterIntensity failed. ret = " + filterIntensity);
        }
        return filterIntensity;
    }

    public synchronized void setNativeInitListener(VEListener.VERecorderNativeInitListener vERecorderNativeInitListener) {
        this.mExternalNativeInitListener = vERecorderNativeInitListener;
    }

    public synchronized int setRecordBGM(@NonNull String str, int i, int i2) {
        return setRecordBGM(str, i, i2, false);
    }

    public synchronized int setRecordBGM(@NonNull String str, int i, int i2, boolean z) {
        if (this.mState != 1) {
            VELogUtil.e(TAG, "stopPreview() can only be invoked after init()");
        }
        this.mBGMPath = str;
        int changeMusicPath = this.mNativeInvoker.changeMusicPath(str);
        if (changeMusicPath != 0) {
            String str2 = TAG;
            VELogUtil.e(str2, "NativeInvoker changeMusicPath() failed. ret = " + changeMusicPath);
        }
        int musicTime = this.mNativeInvoker.setMusicTime((long) i, (long) i2);
        if (musicTime != 0) {
            String str3 = TAG;
            VELogUtil.e(str3, "NativeInvoker setMusicTime() failed. ret = " + musicTime);
        }
        this.mBGMTrimIn = i;
        this.mBGMIsLoop = z;
        return 0;
    }

    public synchronized void setRenderCallback(IRenderCallback iRenderCallback) {
        this.mExternalRenderCallback = iRenderCallback;
    }

    public synchronized int setSlamFace(Bitmap bitmap) {
        int slamFace;
        slamFace = this.mNativeInvoker.setSlamFace(bitmap);
        if (slamFace != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker setSlamFace failed. ret = " + slamFace);
        }
        return slamFace;
    }

    public synchronized void setUseLargeMattingModel(boolean z) {
        this.mNativeInvoker.useLargeMattingModel(z);
    }

    public synchronized int shotScreen(@NonNull final String str, @NonNull int[] iArr, boolean z, final Bitmap.CompressFormat compressFormat, Common.IShotScreenCallback iShotScreenCallback) {
        int i = -1;
        if (compressFormat == Bitmap.CompressFormat.JPEG) {
            i = 1;
        } else if (compressFormat == Bitmap.CompressFormat.PNG) {
            i = 0;
        }
        AnonymousClass1 r6 = new FaceBeautyInvoker.OnPictureCallback() {
            /* class com.ss.android.vesdk.TERecorder.AnonymousClass1 */

            @Override // com.ss.android.medialib.FaceBeautyInvoker.OnPictureCallback
            public void onResult(int[] iArr, int i, int i2) {
                Bitmap createBitmap = Bitmap.createBitmap(iArr, i, i2, Bitmap.Config.ARGB_8888);
                ImageUtils.saveBitmapWithPath(createBitmap, str, compressFormat);
                if (createBitmap != null) {
                    createBitmap.recycle();
                }
            }
        };
        if (this.mState == 2) {
            if (this.mState == 3) {
                return this.mNativeInvoker.shotScreen(str, iArr, z, i, r6, iShotScreenCallback);
            }
        }
        VELogUtil.e(TAG, "shotScreen() can only be invoked after startRecord() invoked successfully");
        return VEResult.TER_INVALID_STAT;
    }

    public synchronized int slamDeviceConfig(boolean z, boolean z2, boolean z3, boolean z4) {
        int slamDeviceConfig;
        slamDeviceConfig = this.mNativeInvoker.slamDeviceConfig(z, z2, z3, z4);
        if (slamDeviceConfig != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker slamDeviceConfig failed. ret = " + slamDeviceConfig);
        }
        return slamDeviceConfig;
    }

    public synchronized int slamProcessIngestAcc(double d2, double d3, double d4, double d5) {
        int slamProcessIngestAcc;
        slamProcessIngestAcc = this.mNativeInvoker.slamProcessIngestAcc(d2, d3, d4, d5);
        if (slamProcessIngestAcc != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker slamProcessIngestAcc failed. ret = " + slamProcessIngestAcc);
        }
        return slamProcessIngestAcc;
    }

    public synchronized int slamProcessIngestGra(double d2, double d3, double d4, double d5) {
        int slamProcessIngestGra;
        slamProcessIngestGra = this.mNativeInvoker.slamProcessIngestGra(d2, d3, d4, d5);
        if (slamProcessIngestGra != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker slamProcessIngestGra failed. ret = " + slamProcessIngestGra);
        }
        return slamProcessIngestGra;
    }

    public synchronized int slamProcessIngestGyr(double d2, double d3, double d4, double d5) {
        int slamProcessIngestGyr;
        slamProcessIngestGyr = this.mNativeInvoker.slamProcessIngestGyr(d2, d3, d4, d5);
        if (slamProcessIngestGyr != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker slamProcessIngestGyr failed. ret = " + slamProcessIngestGyr);
        }
        return slamProcessIngestGyr;
    }

    public synchronized int slamProcessIngestOri(double[] dArr, double d2) {
        int slamProcessIngestOri;
        slamProcessIngestOri = this.mNativeInvoker.slamProcessIngestOri(dArr, d2);
        if (slamProcessIngestOri != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker slamProcessIngestOri failed. ret = " + slamProcessIngestOri);
        }
        return slamProcessIngestOri;
    }

    public synchronized int slamProcessPanEvent(float f2, float f3, float f4, float f5, float f6) {
        int slamProcessPanEvent;
        slamProcessPanEvent = this.mNativeInvoker.slamProcessPanEvent(f2, f3, f4, f5, f6);
        if (slamProcessPanEvent != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker slamProcessPanEvent failed. ret = " + slamProcessPanEvent);
        }
        return slamProcessPanEvent;
    }

    public synchronized int slamProcessRotationEvent(float f2, float f3) {
        int slamProcessRotationEvent;
        slamProcessRotationEvent = this.mNativeInvoker.slamProcessRotationEvent(f2, f3);
        if (slamProcessRotationEvent != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker slamProcessRotationEvent failed. ret = " + slamProcessRotationEvent);
        }
        return slamProcessRotationEvent;
    }

    public synchronized int slamProcessScaleEvent(float f2, float f3) {
        int slamProcessScaleEvent;
        slamProcessScaleEvent = this.mNativeInvoker.slamProcessScaleEvent(f2, f3);
        if (slamProcessScaleEvent != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker slamProcessScaleEvent failed. ret = " + slamProcessScaleEvent);
        }
        return slamProcessScaleEvent;
    }

    public synchronized int slamProcessTouchEvent(float f2, float f3) {
        int slamProcessTouchEvent;
        slamProcessTouchEvent = this.mNativeInvoker.slamProcessTouchEvent(f2, f3);
        if (slamProcessTouchEvent != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker slamProcessTouchEvent failed. ret = " + slamProcessTouchEvent);
        }
        return slamProcessTouchEvent;
    }

    public synchronized int slamProcessTouchEventByType(int i, float f2, float f3, int i2) {
        int slamProcessTouchEventByType;
        slamProcessTouchEventByType = this.mNativeInvoker.slamProcessTouchEventByType(i, f2, f3, i2);
        if (slamProcessTouchEventByType != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker slamProcessTouchEventByType failed. ret = " + slamProcessTouchEventByType);
        }
        return slamProcessTouchEventByType;
    }

    public synchronized int startPreview(@NonNull Surface surface) {
        int startPlay;
        if (this.mState != 1) {
            VELogUtil.e(TAG, "startPreview() can only be invoked after init()");
        }
        int i = 0;
        int rotation = this.mCameraSettings != null ? getRotation(this.mCameraSettings.getFacingID()) : 0;
        if (this.mCameraSettings != null && this.mCameraSettings.getFacingID() == VECameraSettings.CAMERA_FACING_ID.FACING_FRONT) {
            i = 1;
        }
        startPlay = this.mNativeInvoker.startPlay(surface, Build.DEVICE, rotation, i);
        if (startPlay != 0) {
            VELogUtil.e(TAG, "NativeInvoker startPlay() failed. ret = " + startPlay);
        } else {
            this.mState = 2;
        }
        initAudioRecorderIfNeed(determineCurrentAudioOption());
        return startPlay;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0089  */
    public synchronized int startRecord(float f2, long j) {
        int i;
        int startRecord;
        int i2 = 2;
        if (this.mState != 2) {
            VELogUtil.e(TAG, "startRecord() can only be invoked after startPreview()");
        }
        String genSegmentVideoPath = this.mResManager.genSegmentVideoPath(this.mRecordCount);
        this.mResManager.addSegmentAudioPath(this.mResManager.genSegmentAudioPath(this.mRecordCount));
        this.mResManager.addSegmentVideoPath(genSegmentVideoPath);
        float bps = (((float) this.mVideoEncodeSettings.getBps()) * 1.0f) / 4194304.0f;
        if (this.mVideoEncodeSettings.getEncodeProfile() != VEVideoEncodeSettings.ENCODE_PROFILE.ENCODE_PROFILE_MAIN.ordinal()) {
            if (this.mVideoEncodeSettings.getEncodeProfile() == VEVideoEncodeSettings.ENCODE_PROFILE.ENCODE_PROFILE_HIGH.ordinal()) {
                i2 = 8;
            } else {
                i = 1;
                updateAudioComponentIfNeedWithStartRecord(j, f2);
                startRecord = this.mNativeInvoker.startRecord((double) f2, this.mVideoEncodeSettings.isHwEnc(), bps, 1, i);
                if (startRecord == 0) {
                    VELogUtil.e(TAG, "NativeInvoker startRecord() failed. ret = " + startRecord);
                } else {
                    this.mState = 3;
                }
            }
        }
        i = i2;
        updateAudioComponentIfNeedWithStartRecord(j, f2);
        startRecord = this.mNativeInvoker.startRecord((double) f2, this.mVideoEncodeSettings.isHwEnc(), bps, 1, i);
        if (startRecord == 0) {
        }
        return startRecord;
    }

    public synchronized int stopPreview() {
        int stopPlay;
        if (this.mState == 3) {
            stopRecord();
        }
        if (this.mState != 2) {
            VELogUtil.e(TAG, "stopPreview() can only be invoked after init()");
        }
        stopPlay = this.mNativeInvoker.stopPlay();
        if (stopPlay != 0) {
            String str = TAG;
            VELogUtil.e(str, "NativeInvoker stopPlay() failed. ret = " + stopPlay);
        }
        return stopPlay;
    }

    public synchronized int stopRecord() {
        int stopRecord;
        if (this.mState != 3) {
            VELogUtil.e(TAG, "stopRecord() can only be invoked after startRecord() invoked successfully");
        }
        stopRecord = this.mNativeInvoker.stopRecord(false);
        if (this.mAudioRecorder != null) {
            this.mAudioRecorder.stopFeeding();
        }
        if (stopRecord < 0) {
            VELogUtil.e(TAG, "NativeInvoker stopRecord failed. ret = " + stopRecord);
        } else {
            this.mRecordCount++;
            this.mState = 2;
        }
        return stopRecord;
    }

    public synchronized int switchEffect(String str) {
        int stickerPath;
        VEKeyValue vEKeyValue = new VEKeyValue();
        String parsePathSimpleName = VETextUtils.parsePathSimpleName(str);
        if (TextUtils.isEmpty(parsePathSimpleName)) {
            parsePathSimpleName = "0";
        }
        vEKeyValue.add("iesve_verecorder_set_sticker_id", parsePathSimpleName).add("old", 0);
        MonitorUtils.monitorStatistics("iesve_verecorder_set_sticker", 1, vEKeyValue);
        stickerPath = this.mNativeInvoker.setStickerPath(str, 0, 0, false);
        if (stickerPath != 0) {
            VELogUtil.e(TAG, "NativeInvoker setStickerPath failed");
        }
        return stickerPath;
    }

    public synchronized int tryRestore(int i) {
        int tryRestore;
        if (this.mState != 1) {
            String str = TAG;
            VELogUtil.e(str, "Can only invoke tryRestore() in idle state but current state code is " + this.mState);
        }
        this.mRecordCount = i;
        String str2 = TAG;
        VELogUtil.w(str2, "About to invoke tryRestore(), record count has been overwrite with " + i);
        tryRestore = this.mNativeInvoker.tryRestore(this.mRecordCount, this.mResManager.getTempSegmentDirPath());
        if (tryRestore != 0) {
            String str3 = TAG;
            VELogUtil.e(str3, "NativeInvoker tryStore() failed. ret = " + tryRestore);
        }
        return tryRestore;
    }

    public synchronized void updateRotation(float f2, float f3, float f4) {
        this.mNativeInvoker.updateRotation(f2, f3, f4);
    }
}
