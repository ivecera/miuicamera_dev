package com.android.camera.module.impl.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.Image;
import android.opengl.GLES20;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Surface;
import com.android.camera.ActivityBase;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSize;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.observeable.VMProcessing;
import com.android.camera.fragment.vv.VVItem;
import com.android.camera.log.Log;
import com.android.camera.module.AudioController;
import com.android.camera.module.BaseModule;
import com.android.camera.module.LiveModuleSubVV;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.ui.V6CameraGLSurfaceView;
import com.android.camera2.Camera2Proxy;
import com.xiaomi.mediaprocess.EffectCameraNotifier;
import com.xiaomi.mediaprocess.EffectMediaPlayer;
import com.xiaomi.mediaprocess.EffectNotifier;
import com.xiaomi.mediaprocess.MediaComposeFile;
import com.xiaomi.mediaprocess.MediaEffectCamera;
import com.xiaomi.mediaprocess.MediaEffectGraph;
import com.xiaomi.mediaprocess.OpenGlRender;
import com.xiaomi.utils.SystemUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LiveSubVVImpl implements ModeProtocol.LiveConfigVV, SurfaceTextureScreenNail.ExternalFrameProcessor, EffectCameraNotifier {
    public static final String COMPOSE_PATH = (WORKSPACE_PATH + "compose/");
    public static final String SEGMENTS_PATH = (WORKSPACE_PATH + "segments");
    /* access modifiers changed from: private */
    public static final String TAG = "LiveSubVVImpl";
    public static final String TEMPLATE_PATH = (VV_DIR + "template/");
    public static final String VV_DIR = (Environment.getExternalStorageDirectory().getPath() + "/MIUI/Camera/vv/");
    private static final String WATERMARK_PATH = (TEMPLATE_PATH + "watermark.mp4");
    public static final String WORKSPACE_PATH = (VV_DIR + "workspace/");
    private ByteBuffer RGBColor = null;
    private final int TABLESIZE = 512;
    /* access modifiers changed from: private */
    public ActivityBase mActivity;
    private AudioController mAudioController;
    private int mCameraFacing;
    /* access modifiers changed from: private */
    public V6CameraGLSurfaceView mCameraView;
    private MediaComposeFile mComposeFile;
    private Context mContext;
    private int mCurrentIndex;
    private int mCurrentOrientation = -1;
    private VVItem mCurrentVVItem;
    private EffectMediaPlayer mEffectMediaPlayer;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public SurfaceTexture mInputSurfaceTexture;
    /* access modifiers changed from: private */
    public ModeProtocol.LiveVVProcess mLiveVVProcess;
    private MediaEffectCamera mMediaCamera;
    private MediaEffectGraph mMediaEffectGraph;
    private boolean mMediaRecorderRecording;
    private boolean mMediaRecorderRecordingPaused;
    /* access modifiers changed from: private */
    public MiGLSurfaceViewRender mMiGLSurfaceViewRender;
    private boolean mNeedRequestRender;
    /* access modifiers changed from: private */
    public boolean mNeedStop;
    private OpenGlRender mOpenGlRender;
    /* access modifiers changed from: private */
    public boolean mPlayFinished;
    /* access modifiers changed from: private */
    public Disposable mRecordingTimerDisposable;
    private List<String> mTempVideoList;
    private long mValidTime;
    /* access modifiers changed from: private */
    public VMProcessing mVmProcessing;

    private LiveSubVVImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
        this.mContext = activityBase.getCameraAppImpl().getApplicationContext();
        this.mCameraView = activityBase.getGLView();
        this.mHandler = new Handler(this.mActivity.getMainLooper());
        this.mAudioController = new AudioController(this.mActivity.getApplicationContext());
    }

    public static LiveSubVVImpl create(ActivityBase activityBase) {
        return new LiveSubVVImpl(activityBase);
    }

    private void initFilter(String str) {
        Bitmap bitmap;
        if (!TextUtils.isEmpty(str) && new File(str).exists()) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.outWidth = 512;
                options.outHeight = 512;
                bitmap = BitmapFactory.decodeFile(str, options);
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
                bitmap = null;
            }
            if (bitmap != null) {
                try {
                    int[] iArr = new int[262144];
                    for (int i = 0; i < 512; i++) {
                        for (int i2 = 0; i2 < 512; i2++) {
                            int pixel = bitmap.getPixel(i2, i);
                            iArr[(i * 512) + i2] = ((((((bitmap.hasAlpha() ? Color.alpha(pixel) : 255) * 256) + Color.blue(pixel)) * 256) + Color.green(pixel)) * 256) + Color.red(pixel);
                        }
                    }
                    this.RGBColor = ByteBuffer.allocateDirect(iArr.length * 32);
                    this.RGBColor.order(ByteOrder.nativeOrder());
                    this.RGBColor.asIntBuffer().put(iArr);
                    this.RGBColor.position(0);
                } catch (OutOfMemoryError e3) {
                    e3.printStackTrace();
                    this.RGBColor = null;
                }
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

    private void makeSureVVProcess() {
        if (this.mLiveVVProcess == null) {
            this.mLiveVVProcess = (ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230);
        }
    }

    /* access modifiers changed from: private */
    public void notifyModuleRecordingFinish() {
        resetFlag();
        BaseModule baseModule = (BaseModule) this.mActivity.getCurrentModule();
        if (baseModule.getModuleIndex() == 179) {
            ((LiveModuleSubVV) baseModule).stopVideoRecording(true, false);
        }
    }

    private void prepareEffectGraph() {
        VVItem vVItem = this.mCurrentVVItem;
        String str = vVItem.composeJsonPath;
        String str2 = vVItem.musicPath;
        ArrayList arrayList = new ArrayList(this.mTempVideoList);
        arrayList.add(WATERMARK_PATH);
        this.mMediaEffectGraph = new MediaEffectGraph();
        this.mMediaEffectGraph.ConstructMediaEffectGraph();
        this.mMediaEffectGraph.SetAudioMute(true);
        this.mMediaEffectGraph.AddSourceAndEffectByTemplate((String[]) arrayList.toArray(new String[0]), str);
        this.mMediaEffectGraph.AddAudioTrack(str2, false);
    }

    private void resetFlag() {
        this.mMediaRecorderRecording = false;
        this.mNeedRequestRender = false;
        this.mNeedStop = false;
    }

    private void restoreWorkSpace() {
    }

    private void saveWorkSpace() {
    }

    private void startCountDown(long j) {
        long j2 = j / 100;
        String str = TAG;
        Log.d(str, "startCountDown: " + j);
        final long j3 = (j2 * 100) - 100;
        Observable.intervalRange(0, j2, 0, 100, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            /* class com.android.camera.module.impl.component.LiveSubVVImpl.AnonymousClass5 */

            @Override // io.reactivex.Observer
            public void onComplete() {
                Log.d(LiveSubVVImpl.TAG, "onFinish");
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            public void onNext(Long l) {
                LiveSubVVImpl.this.updateRecordingTime(j3 - (l.longValue() * 100));
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                Disposable unused = LiveSubVVImpl.this.mRecordingTimerDisposable = disposable;
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateRecordingTime(long j) {
        this.mLiveVVProcess.updateRecordingTime(String.format(Locale.ENGLISH, "%.1fS", Float.valueOf(Math.abs(((float) j) / 1000.0f))));
    }

    @Override // com.xiaomi.mediaprocess.EffectCameraNotifier
    public void OnNeedStopRecording() {
        Log.d(TAG, "OnNeedStopRecording");
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.module.impl.component.LiveSubVVImpl.AnonymousClass4 */

            public void run() {
                boolean unused = LiveSubVVImpl.this.mNeedStop = true;
                LiveSubVVImpl.this.stopRecording();
            }
        });
    }

    @Override // com.xiaomi.mediaprocess.EffectCameraNotifier
    public void OnNotifyRender() {
        Log.d(TAG, "OnNotifyRender");
        this.mNeedRequestRender = true;
        this.mCameraView.requestRender();
    }

    @Override // com.xiaomi.mediaprocess.EffectCameraNotifier
    public void OnRecordFailed() {
        Log.d(TAG, "OnRecordFailed");
    }

    @Override // com.xiaomi.mediaprocess.EffectCameraNotifier
    public void OnRecordFinish(String str) {
        this.mValidTime = System.currentTimeMillis();
        if (!isRecording()) {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
                return;
            }
            return;
        }
        this.mTempVideoList.add(str);
        String str2 = TAG;
        Log.d(str2, "OnRecordFinish | s: " + str + " | " + Thread.currentThread().getName());
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.module.impl.component.LiveSubVVImpl.AnonymousClass3 */

            public void run() {
                LiveSubVVImpl.this.notifyModuleRecordingFinish();
            }
        });
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordControl
    public boolean canFinishRecording() {
        return this.mTempVideoList.size() >= this.mCurrentVVItem.getEssentialFragmentSize();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVExternal
    public void combineVideoAudio(String str) {
        EffectMediaPlayer effectMediaPlayer = this.mEffectMediaPlayer;
        if (effectMediaPlayer != null) {
            effectMediaPlayer.StopPreView();
        }
        prepareEffectGraph();
        this.mComposeFile = new MediaComposeFile(this.mMediaEffectGraph);
        this.mComposeFile.ConstructMediaComposeFile(1920, 1088, 20971520, 30);
        this.mComposeFile.SetComposeNotify(new EffectNotifier() {
            /* class com.android.camera.module.impl.component.LiveSubVVImpl.AnonymousClass7 */

            @Override // com.xiaomi.mediaprocess.EffectNotifier
            public void OnReceiveFailed() {
                android.util.Log.d(LiveSubVVImpl.TAG, "ComposeCameraRecord OnReceiveFinish");
                LiveSubVVImpl.this.mVmProcessing.updateState(9);
            }

            @Override // com.xiaomi.mediaprocess.EffectNotifier
            public void OnReceiveFinish() {
                android.util.Log.d(LiveSubVVImpl.TAG, "ComposeCameraRecord OnReceiveFinish");
                LiveSubVVImpl.this.mVmProcessing.updateState(8);
            }
        });
        this.mComposeFile.SetComposeFileName(str);
        this.mComposeFile.BeginComposeFile();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordControl
    public void deleteLastFragment() {
        if (!this.mTempVideoList.isEmpty()) {
            int size = this.mTempVideoList.size() - 1;
            FileUtils.deleteFile(this.mTempVideoList.get(size));
            List<String> list = this.mTempVideoList;
            list.remove(list.size() - 1);
            this.mCurrentIndex = this.mTempVideoList.size();
            makeSureVVProcess();
            this.mLiveVVProcess.onRecordingFragmentUpdate(size, -this.mCurrentVVItem.getDuration(size));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public CameraSize getAlgorithmPreviewSize(List<CameraSize> list) {
        return Util.getOptimalVideoSnapshotPictureSize(list, (double) getPreviewRatio(), 176, 144);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public int getAuthResult() {
        return 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveModuleSub
    public SurfaceTexture getInputSurfaceTexture() {
        return this.mInputSurfaceTexture;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordControl
    public int getNextRecordStep() {
        if (!isRecording() && this.mValidTime != 0 && System.currentTimeMillis() - this.mValidTime >= 200) {
            return canFinishRecording() ? 3 : 2;
        }
        return 1;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public float getPreviewRatio() {
        return 1.7777777f;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVExternal
    public String getSegmentPath(int i) {
        return this.mTempVideoList.get(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveModuleSub
    public void initPreview(int i, int i2, int i3, CameraScreenNail cameraScreenNail) {
        this.mCameraFacing = i3;
        SurfaceTexture surfaceTexture = this.mInputSurfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.release();
            this.mOpenGlRender = null;
            this.mValidTime = 0;
        }
        this.mInputSurfaceTexture = new SurfaceTexture(false);
        this.mInputSurfaceTexture.setDefaultBufferSize(i, i2);
        this.mInputSurfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
            /* class com.android.camera.module.impl.component.LiveSubVVImpl.AnonymousClass1 */

            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                LiveSubVVImpl.this.mActivity.getCameraScreenNail().notifyFrameAvailable(4);
                LiveSubVVImpl.this.mCameraView.requestRender();
            }
        });
        cameraScreenNail.setExternalFrameProcessor(this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public void initResource() {
        FileUtils.makeDir(VV_DIR);
        FileUtils.makeNoMediaDir(TEMPLATE_PATH);
        FileUtils.makeNoMediaDir(WORKSPACE_PATH);
        FileUtils.makeNoMediaDir(SEGMENTS_PATH);
        FileUtils.makeNoMediaDir(COMPOSE_PATH);
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public boolean isProcessorReady() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordControl
    public boolean isRecording() {
        return this.mMediaRecorderRecording;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordControl
    public boolean isRecordingPaused() {
        return this.mMediaRecorderRecordingPaused;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveModuleSub
    public boolean onBackPressed() {
        return false;
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public void onDrawFrame(Rect rect, int i, int i2, boolean z) {
        SurfaceTexture surfaceTexture = this.mInputSurfaceTexture;
        if (surfaceTexture != null && !surfaceTexture.isReleased()) {
            if (this.mOpenGlRender == null) {
                this.mOpenGlRender = new OpenGlRender();
                OpenGlRender openGlRender = this.mOpenGlRender;
                int i3 = Util.sWindowHeight;
                int i4 = rect.bottom;
                openGlRender.SetWindowSize(0, i3 - i4, rect.right, i4 - rect.top);
                this.mMiGLSurfaceViewRender = new MiGLSurfaceViewRender(this.mOpenGlRender);
                this.mMiGLSurfaceViewRender.setFilterRGBColor(this.RGBColor);
                this.mMiGLSurfaceViewRender.init(this.mInputSurfaceTexture);
            }
            this.mMiGLSurfaceViewRender.updateTexImage();
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16384);
            if (this.mValidTime <= 0) {
                this.mValidTime = System.currentTimeMillis();
            }
            if (!isRecording() || this.mNeedStop) {
                MiGLSurfaceViewRender miGLSurfaceViewRender = this.mMiGLSurfaceViewRender;
                int i5 = Util.sWindowHeight;
                int i6 = rect.bottom;
                miGLSurfaceViewRender.DrawCameraPreview(0, i5 - i6, rect.right, i6 - rect.top);
                return;
            }
            this.mMiGLSurfaceViewRender.bind(rect, i, i2);
            this.mMediaCamera.NeedProcessTexture(this.mInputSurfaceTexture.getTimestamp() / 1000000);
            this.mOpenGlRender.RenderFrame();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public void onOrientationChanged(int i, int i2, int i3) {
        if (this.mCurrentOrientation != i2 && this.mMediaCamera != null && !isRecording()) {
            this.mCurrentOrientation = i2;
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PreviewCallback
    public boolean onPreviewFrame(Image image, Camera2Proxy camera2Proxy, int i) {
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordControl
    public void onRecordingNewFragmentFinished() {
        makeSureVVProcess();
        if (isRecording()) {
            resetFlag();
            stopRecording();
            Disposable disposable = this.mRecordingTimerDisposable;
            if (disposable != null && !disposable.isDisposed()) {
                this.mRecordingTimerDisposable.dispose();
            }
            ModeProtocol.LiveVVProcess liveVVProcess = this.mLiveVVProcess;
            int i = this.mCurrentIndex;
            liveVVProcess.onRecordingFragmentUpdate(i, -this.mCurrentVVItem.getDuration(i));
            return;
        }
        ModeProtocol.LiveVVProcess liveVVProcess2 = this.mLiveVVProcess;
        int i2 = this.mCurrentIndex;
        liveVVProcess2.onRecordingFragmentUpdate(i2, this.mCurrentVVItem.getDuration(i2));
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVExternal
    public void pausePlay() {
        this.mEffectMediaPlayer.PausePreView();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public void prepare() {
        System.loadLibrary("vvc++_shared");
        System.loadLibrary("vvmediaeditor");
        SystemUtil.Init(this.mContext, 123);
        try {
            Util.verifyAssetZip(this.mContext, "vv/watermark.zip", TEMPLATE_PATH, 8192);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        prepare(DataRepository.dataItemLive().getCurrentVVItem());
        this.mMediaCamera = new MediaEffectCamera();
        this.mMediaCamera.SetOrientation(90);
        this.mMediaCamera.ConstructMediaEffectCamera(1920, 1088, 31457280, 30, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVExternal
    public void prepare(VVItem vVItem) {
        this.mCurrentVVItem = vVItem;
        if (this.mVmProcessing == null) {
            this.mVmProcessing = (VMProcessing) DataRepository.dataItemObservable().get(VMProcessing.class);
        }
        this.mTempVideoList = this.mVmProcessing.getTempVideoList();
        initFilter(vVItem.filterPath);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(228, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public void release() {
        if (isRecording()) {
            resetFlag();
            stopRecording();
        }
        if (this.mMediaCamera != null) {
            Log.d(TAG, "release mediaCamera");
            this.mMediaCamera.DestructMediaEffectCamera();
            this.mMediaCamera = null;
        }
        if (this.mEffectMediaPlayer != null) {
            Log.d(TAG, "release mediaPlayer");
            this.mEffectMediaPlayer.StopPreView();
            this.mEffectMediaPlayer.DestructMediaPlayer();
            this.mEffectMediaPlayer = null;
        }
        if (this.mComposeFile != null) {
            Log.d(TAG, "release composeFile");
            this.mComposeFile.CancelComposeFile();
            this.mComposeFile.DestructMediaComposeFile();
            this.mComposeFile = null;
        }
        if (this.mMediaEffectGraph != null) {
            Log.d(TAG, "release mediaEffectGraph");
            this.mMediaEffectGraph.DestructMediaEffectGraph();
            this.mMediaEffectGraph = null;
        }
        saveWorkSpace();
        this.mHandler.removeCallbacksAndMessages(null);
        Disposable disposable = this.mRecordingTimerDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        SystemUtil.UnInit();
        this.mCameraView.queueEvent(new Runnable() {
            /* class com.android.camera.module.impl.component.LiveSubVVImpl.AnonymousClass2 */

            public void run() {
                if (LiveSubVVImpl.this.mInputSurfaceTexture != null) {
                    LiveSubVVImpl.this.mInputSurfaceTexture.release();
                }
                if (LiveSubVVImpl.this.mMiGLSurfaceViewRender != null) {
                    Log.d(LiveSubVVImpl.TAG, "release render");
                    LiveSubVVImpl.this.mMiGLSurfaceViewRender.release();
                }
            }
        });
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public void releaseRender() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVExternal
    public void resumePlay() {
        this.mEffectMediaPlayer.ResumePreView();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVExternal
    public void startPlay(Surface surface) {
        prepareEffectGraph();
        this.mPlayFinished = false;
        this.mEffectMediaPlayer = new EffectMediaPlayer(this.mMediaEffectGraph);
        this.mEffectMediaPlayer.ConstructMediaPlayer();
        this.mEffectMediaPlayer.SetPlayerNotify(new EffectNotifier() {
            /* class com.android.camera.module.impl.component.LiveSubVVImpl.AnonymousClass6 */

            @Override // com.xiaomi.mediaprocess.EffectNotifier
            public void OnReceiveFailed() {
                Log.d("OnReceiveFailed:", "yes");
                boolean unused = LiveSubVVImpl.this.mPlayFinished = true;
                LiveSubVVImpl.this.mLiveVVProcess.onResultPreviewFinished(false);
            }

            @Override // com.xiaomi.mediaprocess.EffectNotifier
            public void OnReceiveFinish() {
                Log.d("OnReceiveFinish:", "yes");
                boolean unused = LiveSubVVImpl.this.mPlayFinished = true;
                LiveSubVVImpl.this.mLiveVVProcess.onResultPreviewFinished(true);
            }
        });
        this.mEffectMediaPlayer.SetViewSurface(surface);
        this.mEffectMediaPlayer.setGravity(EffectMediaPlayer.SurfaceGravity.SurfaceGravityResizeAspectFit, 1920, 1080);
        this.mEffectMediaPlayer.SetPlayLoop(true);
        this.mEffectMediaPlayer.SetGraphLoop(true);
        this.mEffectMediaPlayer.StartPreView();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordControl
    public void startRecordingNewFragment() {
        startRecordingNextFragment();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordControl
    public void startRecordingNextFragment() {
        this.mMediaRecorderRecordingPaused = false;
        this.mAudioController.stopAudio();
        makeSureVVProcess();
        this.mCurrentIndex = this.mTempVideoList.size();
        if (this.mCurrentIndex == 0) {
            FileUtils.deleteSubFiles(SEGMENTS_PATH);
        }
        long duration = this.mCurrentVVItem.getDuration(this.mCurrentIndex);
        VVItem vVItem = this.mCurrentVVItem;
        String str = vVItem.musicPath;
        String str2 = vVItem.configJsonPath;
        String str3 = vVItem.filterPath;
        this.mLiveVVProcess.onRecordingNewFragmentStart(this.mCurrentIndex, duration);
        long j = 0;
        for (int i = 0; i < this.mCurrentIndex; i++) {
            j += this.mCurrentVVItem.getDuration(i);
        }
        Log.d(TAG, "start : " + duration + " | " + str + " | " + str2 + " | " + str3 + " | " + j);
        this.mMediaCamera.SetOrientation(90);
        this.mMediaCamera.StartRecording(this.mCurrentIndex, SEGMENTS_PATH, str2, str3, str, j);
        this.mMediaRecorderRecording = true;
        startCountDown(duration);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordControl
    public void stopRecording() {
        this.mMediaCamera.StopRecording();
        this.mAudioController.restoreAudio();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveModuleSub
    public void trackVideoParams() {
        String str = this.mCurrentVVItem.name;
        boolean z = true;
        if (this.mCameraFacing != 1) {
            z = false;
        }
        CameraStatUtils.trackVVRecordingParams(str, z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(228, this);
        release();
    }
}
