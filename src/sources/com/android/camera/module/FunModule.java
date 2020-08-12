package com.android.camera.module;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.telephony.TelephonyManager;
import android.util.Range;
import android.view.Surface;
import com.android.camera.AutoLockManager;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.camera.module.encoder.MediaAudioEncoder;
import com.android.camera.module.encoder.MediaEncoder;
import com.android.camera.module.encoder.MediaMuxerWrapper;
import com.android.camera.module.encoder.MediaVideoEncoder;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.storage.Storage;
import com.android.camera.ui.PopupManager;
import com.android.camera.ui.V6CameraGLSurfaceView;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.gallery3d.ui.GLCanvasImpl;
import com.mi.config.b;
import com.xiaomi.camera.core.ParallelTaskData;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class FunModule extends VideoBase implements ModeProtocol.StickerProtocol, ModeProtocol.LiveSpeedChanges, ModeProtocol.KaleidoscopeProtocol, Camera2Proxy.PictureCallback {
    private static final int FRAME_RATE = 30;
    private static final long START_OFFSET_MS = 450;
    private final float[] SPEEDS = {3.0f, 2.0f, 1.0f, 0.5f, 0.33f};
    private V6CameraGLSurfaceView mCameraView;
    private CountDownTimer mCountDownTimer;
    private MediaMuxerWrapper mLastMuxer;
    private MediaAudioEncoder mMediaAudioEncoder;
    private final MediaEncoder.MediaEncoderListener mMediaEncoderListener = new EncoderListener(this);
    private MediaVideoEncoder mMediaVideoEncoder;
    private MediaMuxerWrapper mMuxer;
    private ArrayList<SaveVideoTask> mPendingSaveTaskList = new ArrayList<>();
    private int mQuality;
    private long mRequestStartTime;
    /* access modifiers changed from: private */
    public float mSpeed = 1.0f;

    private static class EncoderListener implements MediaEncoder.MediaEncoderListener {
        private WeakReference<FunModule> mModule;

        public EncoderListener(FunModule funModule) {
            this.mModule = new WeakReference<>(funModule);
        }

        @Override // com.android.camera.module.encoder.MediaEncoder.MediaEncoderListener
        public void onPrepared(MediaEncoder mediaEncoder) {
            String str = VideoBase.TAG;
            Log.v(str, "onPrepared: encoder=" + mediaEncoder);
        }

        @Override // com.android.camera.module.encoder.MediaEncoder.MediaEncoderListener
        public void onStopped(MediaEncoder mediaEncoder, boolean z) {
            FunModule funModule;
            String str = VideoBase.TAG;
            Log.v(str, "onStopped: encoder=" + mediaEncoder);
            if (z && (funModule = this.mModule.get()) != null) {
                funModule.executeSaveTask(true);
            }
        }
    }

    private final class SaveVideoTask {
        public ContentValues contentValues;
        public String videoPath;

        public SaveVideoTask(String str, ContentValues contentValues2) {
            this.videoPath = str;
            this.contentValues = contentValues2;
        }
    }

    public FunModule() {
        super(FunModule.class.getSimpleName());
        ((VideoBase) this).mOutputFormat = 2;
    }

    private void addSaveTask(String str, ContentValues contentValues) {
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        SaveVideoTask saveVideoTask = new SaveVideoTask(str, contentValues);
        synchronized (this) {
            this.mPendingSaveTaskList.add(saveVideoTask);
        }
    }

    private String convertToFilePath(FileDescriptor fileDescriptor) {
        return null;
    }

    /* access modifiers changed from: private */
    public void executeSaveTask(boolean z) {
        synchronized (this) {
            do {
                if (this.mPendingSaveTaskList.isEmpty()) {
                    break;
                }
                SaveVideoTask remove = this.mPendingSaveTaskList.remove(0);
                String str = VideoBase.TAG;
                Log.d(str, "executeSaveTask: " + remove.videoPath);
                ((BaseModule) this).mActivity.getImageSaver().addVideo(remove.videoPath, remove.contentValues, true);
            } while (!z);
            doLaterReleaseIfNeed();
        }
    }

    private boolean initializeRecorder() {
        String str;
        if (((BaseModule) this).mCamera2Device == null) {
            Log.e(VideoBase.TAG, "initializeRecorder: null camera");
            return false;
        }
        Log.v(VideoBase.TAG, "initializeRecorder");
        closeVideoFileDescriptor();
        if (isCaptureIntent()) {
            parseIntent(((BaseModule) this).mActivity.getIntent());
        }
        ParcelFileDescriptor parcelFileDescriptor = ((VideoBase) this).mVideoFileDescriptor;
        if (parcelFileDescriptor != null) {
            str = convertToFilePath(parcelFileDescriptor.getFileDescriptor());
        } else {
            ((VideoBase) this).mCurrentVideoValues = genContentValues(((VideoBase) this).mOutputFormat, -1, null, false, true);
            ((VideoBase) this).mCurrentVideoFilename = ((VideoBase) this).mCurrentVideoValues.getAsString("_data");
            str = ((VideoBase) this).mCurrentVideoFilename;
        }
        ((VideoBase) this).mOrientationCompensationAtRecordStart = ((BaseModule) this).mOrientationCompensation;
        try {
            releaseLastMediaRecorder();
            this.mMuxer = new MediaMuxerWrapper(str);
            this.mMediaVideoEncoder = new MediaVideoEncoder(getActivity().getGLView().getEGLContext14(), this.mMuxer, this.mMediaEncoderListener, ((VideoBase) this).mVideoSize.width, ((VideoBase) this).mVideoSize.height);
            this.mMediaAudioEncoder = new MediaAudioEncoder(this.mMuxer, this.mMediaEncoderListener);
            this.mMediaVideoEncoder.setRecordSpeed(this.mSpeed);
            this.mMediaAudioEncoder.setRecordSpeed(this.mSpeed);
            this.mMuxer.prepare();
            String str2 = VideoBase.TAG;
            Log.d(str2, "rotation: " + ((BaseModule) this).mOrientationCompensation);
            this.mMuxer.setOrientationHint(((BaseModule) this).mOrientationCompensation);
            return true;
        } catch (IOException e2) {
            Log.e(VideoBase.TAG, "initializeRecorder: ", e2);
            return false;
        }
    }

    private boolean isEisOn() {
        return isBackCamera() && DataRepository.dataItemFeature().qc() && CameraSettings.isMovieSolidOn();
    }

    private boolean isSupportShortVideoBeautyBody() {
        return isBackCamera() && DataRepository.dataItemFeature().isSupportShortVideoBeautyBody();
    }

    private void onStartRecorderFail() {
        enableCameraControls(true);
        restoreOuterAudio();
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onFailed();
        }
    }

    private void onStartRecorderSucceed() {
        enableCameraControls(true);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            updateZoomRatioToggleButtonState(true);
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(2.0f);
            } else if (isAuxCamera()) {
                setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            }
        }
        ((BaseModule) this).mActivity.sendBroadcast(new Intent(VideoBase.START_VIDEO_RECORDING_ACTION));
        ((VideoBase) this).mMediaRecorderRecording = true;
        ((VideoBase) this).mRecordingStartTime = SystemClock.uptimeMillis();
        ((VideoBase) this).mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 32);
        Log.v(VideoBase.TAG, "listen call state");
        updateRecordingTime();
        keepScreenOn();
        AutoLockManager.getInstance(((BaseModule) this).mActivity).removeMessage();
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(((VideoBase) this).m3ALocked));
        trackGeneralInfo(hashMap, 1, false, ((VideoBase) this).mBeautyValues, false, 0);
    }

    private void releaseLastMediaRecorder() {
        String str = VideoBase.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("releaseLastMediaRecorder ");
        sb.append(this.mLastMuxer != null);
        Log.d(str, sb.toString());
        MediaMuxerWrapper mediaMuxerWrapper = this.mLastMuxer;
        if (mediaMuxerWrapper != null) {
            mediaMuxerWrapper.join();
            this.mLastMuxer = null;
        }
    }

    private void releaseMediaRecorder() {
        Log.v(VideoBase.TAG, "releaseMediaRecorder");
        MediaMuxerWrapper mediaMuxerWrapper = this.mMuxer;
        if (mediaMuxerWrapper != null) {
            this.mLastMuxer = mediaMuxerWrapper;
            cleanupEmptyFile();
        }
    }

    private void releaseResources() {
        closeCamera();
        releaseMediaRecorder();
        releaseLastMediaRecorder();
    }

    private void setVideoSize(int i, int i2) {
        if (((BaseModule) this).mCameraDisplayOrientation % 180 == 0) {
            ((VideoBase) this).mVideoSize = new CameraSize(i, i2);
        } else {
            ((VideoBase) this).mVideoSize = new CameraSize(i2, i);
        }
    }

    private boolean shouldApplyUltraWideLDC() {
        if (CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(((BaseModule) this).mModuleIndex) && ((BaseModule) this).mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideVideoLDCEnabled();
        }
        return false;
    }

    private boolean startRecorder() {
        if (!initializeRecorder()) {
            Log.e(VideoBase.TAG, "fail to initialize recorder");
            return false;
        }
        long currentTimeMillis = START_OFFSET_MS - (System.currentTimeMillis() - this.mRequestStartTime);
        if (currentTimeMillis < 0) {
            currentTimeMillis = 0;
        }
        boolean startRecording = this.mMuxer.startRecording(currentTimeMillis);
        if (!startRecording) {
            this.mMuxer.stopRecording();
            showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_recorder_busy_alert);
            releaseMediaRecorder();
        }
        return startRecording;
    }

    private void updateFilter() {
        int shaderEffect = CameraSettings.getShaderEffect();
        String str = VideoBase.TAG;
        Log.v(str, "updateFilter: 0x" + Integer.toHexString(shaderEffect));
        EffectController.getInstance().setEffect(shaderEffect);
    }

    private void updateFpsRange() {
        if (b.isMTKPlatform()) {
            ((BaseModule) this).mCamera2Device.setVideoFpsRange(new Range<>(5, 30));
            ((BaseModule) this).mCamera2Device.setFpsRange(new Range<>(5, 30));
            return;
        }
        ((BaseModule) this).mCamera2Device.setVideoFpsRange(new Range<>(30, 30));
        ((BaseModule) this).mCamera2Device.setFpsRange(new Range<>(30, 30));
    }

    private void updateKaleidoscope() {
        String kaleidoscopeValue = DataRepository.dataItemRunning().getComponentRunningKaleidoscope().getKaleidoscopeValue();
        V6CameraGLSurfaceView v6CameraGLSurfaceView = this.mCameraView;
        if (v6CameraGLSurfaceView != null) {
            GLCanvasImpl gLCanvas = v6CameraGLSurfaceView.getGLCanvas();
            if (gLCanvas instanceof GLCanvasImpl) {
                gLCanvas.setKaleidoscope(kaleidoscopeValue);
            }
        }
        EffectController.getInstance().setKaleidoscope(kaleidoscopeValue);
    }

    private void updatePictureAndPreviewSize() {
        ((BaseModule) this).mPreviewSize = Util.getOptimalPreviewSize(false, ((BaseModule) this).mBogusCameraId, ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class), (double) CameraSettings.getPreviewAspectRatio(16, 9), CameraSettings.getPreferVideoQuality(((BaseModule) this).mActualCameraId, ((BaseModule) this).mModuleIndex) != 5 ? new CameraSize(1920, 1080) : new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH));
        CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
        updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
        String str = VideoBase.TAG;
        Log.d(str, "previewSize: " + ((BaseModule) this).mPreviewSize);
    }

    private void updateUltraWideLDC() {
        ((BaseModule) this).mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (CameraSettings.isVideoBokehOn() && isFrontCamera()) {
                Log.d(VideoBase.TAG, "videoStabilization: disabled EIS and OIS when VIDEO_BOKEH is opened");
                ((BaseModule) this).mCamera2Device.setEnableEIS(false);
                ((BaseModule) this).mCamera2Device.setEnableOIS(false);
                ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
            } else if (isEisOn()) {
                String str = VideoBase.TAG;
                Log.d(str, "videoStabilization: EIS isEISPreviewSupported = " + ((BaseModule) this).mCameraCapabilities.isEISPreviewSupported());
                ((BaseModule) this).mCamera2Device.setEnableEIS(true);
                ((BaseModule) this).mCamera2Device.setEnableOIS(false);
                if (!((BaseModule) this).mCameraCapabilities.isEISPreviewSupported()) {
                    ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                }
            } else {
                ((BaseModule) this).mCamera2Device.setEnableEIS(false);
                ((BaseModule) this).mCamera2Device.setEnableOIS(true);
                ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
                if (b.isMTKPlatform() && ((BaseModule) this).mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                    ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                }
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void consumePreference(@UpdateConstant.UpdateType int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (i == 2) {
                updateFilter();
            } else if (i == 3) {
                updateFocusArea();
            } else if (i == 5) {
                updateFace();
            } else if (i == 50) {
                continue;
            } else if (i == 58) {
                updateBackSoftLightPreference();
            } else if (i == 66) {
                updateThermalLevel();
            } else if (i == 71) {
                updateKaleidoscope();
            } else if (i == 19) {
                updateFpsRange();
            } else if (i == 20) {
                continue;
            } else if (i == 24) {
                applyZoomRatio();
            } else if (i == 25) {
                focusCenter();
            } else if (i == 34) {
                continue;
            } else if (i == 35) {
                updateDeviceOrientation();
            } else if (!(i == 42 || i == 43 || i == 54)) {
                if (i != 55) {
                    switch (i) {
                        case 9:
                            updateAntiBanding(CameraSettings.getAntiBanding());
                            continue;
                        case 10:
                            updateFlashPreference();
                            continue;
                        case 11:
                            continue;
                        case 12:
                            setEvValue();
                            continue;
                        case 13:
                            updateBeauty();
                            continue;
                        case 14:
                            updateVideoFocusMode();
                            continue;
                        default:
                            switch (i) {
                                case 29:
                                    updateExposureMeteringMode();
                                    continue;
                                case 30:
                                    continue;
                                case 31:
                                    updateVideoStabilization();
                                    continue;
                                default:
                                    switch (i) {
                                        case 46:
                                        case 48:
                                            continue;
                                        case 47:
                                            updateUltraWideLDC();
                                            continue;
                                        default:
                                            if (!BaseModule.DEBUG) {
                                                Log.w(VideoBase.TAG, "no consumer for this updateType: " + i);
                                                continue;
                                            } else {
                                                throw new RuntimeException("no consumer for this updateType: " + i);
                                            }
                                    }
                            }
                    }
                } else {
                    updateModuleRelated();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        int i = isEisOn() ? 32772 : (!CameraSettings.isVideoBokehOn() || !isFrontCamera()) ? ((BaseModule) this).mCameraCapabilities.isSupportVideoBeauty() ? CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_BEAUTY : DataRepository.dataItemFeature().Cc() ? CameraCapabilities.SESSION_OPERATION_MODE_MCTF : 0 : 32770;
        String str = VideoBase.TAG;
        Log.d(str, "getOperatingMode: " + Integer.toHexString(i));
        return i;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public float getRecordSpeed() {
        return this.mSpeed;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        int i;
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = ((BaseModule) this).mModuleIndex) == 161 && !CameraSettings.isMacroModeEnabled(i) && isBackCamera() && !((VideoBase) this).mMediaRecorderRecording && !((VideoBase) this).mMediaRecorderRecordingPaused;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isNeedHapticFeedback() {
        return !((VideoBase) this).mMediaRecorderRecording;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isNeedMute() {
        return ((VideoBase) this).mMediaRecorderRecording;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule, com.android.camera.module.VideoBase
    public void onCameraOpened() {
        super.onCameraOpened();
        readVideoPreferences();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
        startPreview();
        setRecordSpeed(Integer.valueOf(CameraSettings.getCurrentLiveSpeed()).intValue());
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureCompleted(boolean z) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z) {
        ((BaseModule) this).mActivity.getCameraScreenNail().requestFullReadPixels();
        CameraStatUtils.trackKaleidoscopeClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_KALEIDOSCOPE_CAPTURE);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
        return null;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        setCaptureIntent(((BaseModule) this).mActivity.getCameraIntentManager().isVideoCaptureIntent());
        ((BaseModule) this).mActivity.getSensorStateManager().setSensorStateListener(((VideoBase) this).mSensorStateListener);
        this.mCameraView = ((BaseModule) this).mActivity.getGLView();
        enableCameraControls(false);
        ((VideoBase) this).mTelephonyManager = (TelephonyManager) ((BaseModule) this).mActivity.getSystemService("phone");
        ((VideoBase) this).mVideoFocusMode = AutoFocus.LEGACY_CONTINUOUS_VIDEO;
        onCameraOpened();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onDestroy() {
        super.onDestroy();
        ((BaseModule) this).mHandler.sendEmptyMessage(45);
    }

    @Override // com.android.camera.protocol.ModeProtocol.KaleidoscopeProtocol
    public void onKaleidoscopeChanged(String str) {
        updatePreferenceTrampoline(71);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule, com.android.camera.module.VideoBase
    public void onPause() {
        super.onPause();
        waitStereoSwitchThread();
        releaseResources();
        closeVideoFileDescriptor();
        ((BaseModule) this).mActivity.getSensorStateManager().reset();
        stopFaceDetection(true);
        resetScreenOn();
        ((BaseModule) this).mHandler.removeCallbacksAndMessages(null);
        if (!((BaseModule) this).mActivity.isActivityPaused()) {
            PopupManager.getInstance(((BaseModule) this).mActivity).notifyShowPopup(null, 1);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTakenFinished(boolean z) {
        ((BaseModule) this).mActivity.getCameraScreenNail().animateCapture(((BaseModule) this).mOrientation);
        ((BaseModule) this).mActivity.getCameraScreenNail().setPreviewSaveListener(null);
        setCameraState(1);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public boolean onPictureTakenImageConsumed(Image image) {
        return false;
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewLayoutChanged(Rect rect) {
        String str = VideoBase.TAG;
        Log.v(str, "onPreviewLayoutChanged: " + rect);
        ((BaseModule) this).mActivity.onLayoutChange(rect);
        FocusManager2 focusManager2 = ((VideoBase) this).mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setRenderSize(((BaseModule) this).mActivity.getCameraScreenNail().getRenderWidth(), ((BaseModule) this).mActivity.getCameraScreenNail().getRenderHeight());
            ((VideoBase) this).mFocusManager.setPreviewSize(rect.width(), rect.height());
        }
    }

    @Override // com.android.camera.module.VideoBase, com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        super.onPreviewSessionSuccess(cameraCaptureSession);
        if (!isCreated()) {
            Log.w(VideoBase.TAG, "onPreviewSessionSuccess: module is not ready");
            return;
        }
        String str = VideoBase.TAG;
        Log.d(str, "onPreviewSessionSuccess: " + cameraCaptureSession);
        ((VideoBase) this).mFaceDetectionEnabled = false;
        updatePreferenceInWorkThread(UpdateConstant.FUN_TYPES_ON_PREVIEW_SUCCESS);
        updatePreferenceInWorkThread(71);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    @MainThread
    public void onPreviewStart() {
        if (((VideoBase) this).mPreviewing) {
            ((BaseModule) this).mMainProtocol.initializeFocusView(this);
            onShutterButtonFocus(true, 3);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onSharedPreferenceChanged() {
        if (!((BaseModule) this).mPaused && ((BaseModule) this).mCamera2Device != null) {
            readVideoPreferences();
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.VideoBase
    public void onShineChanged(int i) {
        if (i == 196) {
            updatePreferenceTrampoline(2);
            ((BaseModule) this).mMainProtocol.updateEffectViewVisible();
        } else if (i == 239) {
            updatePreferenceInWorkThread(13);
        } else {
            throw new RuntimeException("unknown configItem changed");
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction, com.android.camera.module.VideoBase
    public void onShutterButtonClick(int i) {
        String str = VideoBase.TAG;
        Log.v(str, "onShutterButtonClick  isRecording=" + ((VideoBase) this).mMediaRecorderRecording + " inStartingFocusRecording=" + ((VideoBase) this).mInStartingFocusRecording);
        ((VideoBase) this).mInStartingFocusRecording = false;
        ((BaseModule) this).mLastBackPressedTime = 0;
        if (isIgnoreTouchEvent()) {
            Log.w(VideoBase.TAG, "onShutterButtonClick: ignore touch event");
        } else if (!isFrontCamera() || !((BaseModule) this).mActivity.isScreenSlideOff()) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (((VideoBase) this).mMediaRecorderRecording) {
                stopVideoRecording(true, false);
                return;
            }
            recordState.onPrepare();
            if (!checkCallingState()) {
                recordState.onFailed();
                return;
            }
            ((BaseModule) this).mActivity.getScreenHint().updateHint();
            if (Storage.isLowStorageAtLastPoint()) {
                recordState.onFailed();
                return;
            }
            setTriggerMode(i);
            enableCameraControls(false);
            playCameraSound(2);
            this.mRequestStartTime = System.currentTimeMillis();
            if (((VideoBase) this).mFocusManager.canRecord()) {
                startVideoRecording();
                return;
            }
            Log.v(VideoBase.TAG, "wait for autoFocus");
            ((VideoBase) this).mInStartingFocusRecording = true;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction, com.android.camera.module.VideoBase
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSingleTapUp(int i, int i2, boolean z) {
        ModeProtocol.BackStack backStack;
        if (!((BaseModule) this).mPaused && ((BaseModule) this).mCamera2Device != null && !hasCameraException() && ((BaseModule) this).mCamera2Device.isSessionReady() && !((VideoBase) this).mSnapshotInProgress && isInTapableRect(i, i2)) {
            if (!isFrameAvailable()) {
                Log.w(VideoBase.TAG, "onSingleTapUp: frame not available");
            } else if ((!isFrontCamera() || !((BaseModule) this).mActivity.isScreenSlideOff()) && (backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)) != null && !backStack.handleBackStackFromTapDown(i, i2)) {
                ((BaseModule) this).mMainProtocol.setFocusViewType(true);
                ((VideoBase) this).mTouchFocusStartingTime = System.currentTimeMillis();
                Point point = new Point(i, i2);
                mapTapCoordinate(point);
                unlockAEAF();
                ((VideoBase) this).mFocusManager.onSingleTapUp(point.x, point.y, z);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.StickerProtocol
    public void onStickerChanged(String str) {
        String str2 = VideoBase.TAG;
        Log.v(str2, "onStickerChanged: " + str);
        V6CameraGLSurfaceView v6CameraGLSurfaceView = this.mCameraView;
        if (v6CameraGLSurfaceView != null) {
            GLCanvasImpl gLCanvas = v6CameraGLSurfaceView.getGLCanvas();
            if (gLCanvas instanceof GLCanvasImpl) {
                gLCanvas.setSticker(str);
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onStop() {
        super.onStop();
        EffectController.getInstance().setCurrentSticker(null);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute) {
        MediaVideoEncoder mediaVideoEncoder;
        boolean z;
        synchronized (this) {
            mediaVideoEncoder = this.mMediaVideoEncoder;
            z = ((VideoBase) this).mMediaRecorderRecording;
        }
        if (mediaVideoEncoder != null && z) {
            mediaVideoEncoder.frameAvailableSoon(drawExtTexAttribute);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule, com.android.camera.module.VideoBase
    public void onThermalConstrained() {
        super.onThermalConstrained();
        if (((VideoBase) this).mMediaRecorderRecording) {
            stopVideoRecording(true, false);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionEnd(int i) {
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isSlideVisible()) {
            if ((i == 2 || i == 1) && !((VideoBase) this).mMediaRecorderRecording && !((VideoBase) this).mMediaRecorderRecordingPaused && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex)) {
                dualController.setImmersiveModeEnabled(false);
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionStart(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController == null) {
            return;
        }
        if (i == 2 || i == 1) {
            dualController.setImmersiveModeEnabled(true);
        }
    }

    @Override // com.android.camera.module.Module
    public void pausePreview() {
        Log.v(VideoBase.TAG, "pausePreview");
        ((VideoBase) this).mPreviewing = false;
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.pausePreview();
        }
        FocusManager2 focusManager2 = ((VideoBase) this).mFocusManager;
        if (focusManager2 != null) {
            focusManager2.resetFocused();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void readVideoPreferences() {
        ((VideoBase) this).mMaxVideoDurationInMs = 15450;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(178, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(201, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(236, this);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 234, 212);
        } else {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 234, 212);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void resizeForPreviewAspectRatio() {
        if (((((BaseModule) this).mCameraCapabilities.getSensorOrientation() - Util.getDisplayRotation(((BaseModule) this).mActivity)) + 360) % 180 == 0) {
            ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) this).mMainProtocol;
            CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
            mainContentProtocol.setPreviewAspectRatio(((float) cameraSize.height) / ((float) cameraSize.width));
            return;
        }
        ModeProtocol.MainContentProtocol mainContentProtocol2 = ((BaseModule) this).mMainProtocol;
        CameraSize cameraSize2 = ((BaseModule) this).mPreviewSize;
        mainContentProtocol2.setPreviewAspectRatio(((float) cameraSize2.width) / ((float) cameraSize2.height));
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        Log.v(VideoBase.TAG, "resumePreview");
        ((VideoBase) this).mPreviewing = true;
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.resumePreview();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public void setRecordSpeed(int i) {
        this.mSpeed = this.SPEEDS[i];
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        String str = VideoBase.TAG;
        Log.d(str, "startPreview: " + ((VideoBase) this).mPreviewing);
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
            setVideoSize(cameraSize.width, cameraSize.height);
            this.mQuality = Util.convertSizeToQuality(((BaseModule) this).mPreviewSize);
            ((BaseModule) this).mCamera2Device.setPictureSize(((BaseModule) this).mPreviewSize);
            ((BaseModule) this).mCamera2Device.setErrorCallback(((BaseModule) this).mErrorCallback);
            ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            ((BaseModule) this).mCamera2Device.startPreviewSession(new Surface(((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture()), false, false, false, getOperatingMode(), false, this);
            ((VideoBase) this).mFocusManager.resetFocused();
            if (((BaseModule) this).mAELockOnlySupported) {
                ((BaseModule) this).mCamera2Device.setFocusCallback(this);
            }
            ((VideoBase) this).mPreviewing = true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void startVideoRecording() {
        Log.v(VideoBase.TAG, "startVideoRecording");
        ((VideoBase) this).mCurrentVideoUri = null;
        silenceOuterAudio();
        if (!startRecorder()) {
            onStartRecorderFail();
            return;
        }
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onStart();
        }
        Log.v(VideoBase.TAG, "startVideoRecording process done");
        onStartRecorderSucceed();
    }

    @Override // com.android.camera.module.VideoBase
    public void stopVideoRecording(boolean z, boolean z2) {
        if (((VideoBase) this).mMediaRecorderRecording) {
            if (is3ALocked()) {
                unlockAEAF();
            }
            ((VideoBase) this).mMediaRecorderRecording = false;
            long uptimeMillis = SystemClock.uptimeMillis() - ((VideoBase) this).mRecordingStartTime;
            this.mMuxer.stopRecording();
            if (!((BaseModule) this).mPaused) {
                playCameraSound(3);
            }
            releaseMediaRecorder();
            boolean z3 = ((VideoBase) this).mCurrentVideoFilename == null;
            if (!z3 && uptimeMillis < 1000) {
                deleteVideoFile(((VideoBase) this).mCurrentVideoFilename);
                z3 = true;
            }
            if (!z3) {
                addSaveTask(((VideoBase) this).mCurrentVideoFilename, ((VideoBase) this).mCurrentVideoValues);
            }
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
                updateZoomRatioToggleButtonState(false);
                if (isUltraWideBackCamera()) {
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(((BaseModule) this).mCameraCapabilities.getMaxZoomRatio() * 0.6f);
                } else {
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
                }
            }
            ((BaseModule) this).mActivity.sendBroadcast(new Intent(VideoBase.STOP_VIDEO_RECORDING_ACTION));
            ((VideoBase) this).mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 0);
            Log.v(VideoBase.TAG, "listen none");
            animateHold();
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            }
            if (((BaseModule) this).mCamera2Device != null) {
                CameraStatUtils.trackVideoRecorded(isFrontCamera(), getActualCameraId(), getModuleIndex(), false, false, CameraSettings.isUltraWideConfigOpen(getModuleIndex()), CameraSettings.VIDEO_MODE_FUN, this.mQuality, ((BaseModule) this).mCamera2Device.getFlashMode(), 30, 0, ((VideoBase) this).mBeautyValues, uptimeMillis / 1000, false);
            }
            if (!z2 && !AutoFocus.LEGACY_CONTINUOUS_VIDEO.equals(((VideoBase) this).mVideoFocusMode)) {
                ((BaseModule) this).mMainProtocol.clearFocusView(2);
                setVideoFocusMode(AutoFocus.LEGACY_CONTINUOUS_VIDEO, false);
                updatePreferenceInWorkThread(14);
            }
            restoreOuterAudio();
            keepScreenOnAwhile();
            AutoLockManager.getInstance(((BaseModule) this).mActivity).hibernateDelayed();
        }
    }

    public void takePreviewSnapShoot() {
        if (getCameraState() != 3) {
            setCameraState(3);
            ((BaseModule) this).mCamera2Device.setShotType(-8);
            ((BaseModule) this).mCamera2Device.takeSimplePicture(this, ((BaseModule) this).mActivity.getImageSaver(), ((BaseModule) this).mActivity.getCameraScreenNail());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(178, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(201, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(236, this);
        getActivity().getImplFactory().detachAdditional();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void updateRecordingTime() {
        super.updateRecordingTime();
        if (((VideoBase) this).mMediaRecorderRecording) {
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            float f2 = this.mSpeed;
            this.mCountDownTimer = new CountDownTimer((long) (((float) ((VideoBase) this).mMaxVideoDurationInMs) / f2), (long) (1000.0f / f2)) {
                /* class com.android.camera.module.FunModule.AnonymousClass1 */

                public void onFinish() {
                    FunModule.this.stopVideoRecording(true, false);
                }

                public void onTick(long j) {
                    String millisecondToTimeString = Util.millisecondToTimeString((((long) (((float) j) * FunModule.this.mSpeed)) + 950) - FunModule.START_OFFSET_MS, false);
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null) {
                        topAlert.updateRecordingTime(millisecondToTimeString);
                    }
                }
            };
            this.mCountDownTimer.start();
        }
    }
}
