package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.utils.SurfaceUtils;
import android.support.annotation.NonNull;
import android.util.Size;
import android.view.Surface;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocalParallelService;
import com.android.camera.MemoryHelper;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.HdrEvValue;
import com.mi.config.b;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.imagecodec.ImagePool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MiCamera2ShotParallelBurst extends MiCamera2ShotParallel<ParallelTaskData> {
    private static final String TAG = "ShotParallelBurst";
    /* access modifiers changed from: private */
    public int mAlgoType = 0;
    /* access modifiers changed from: private */
    public int mCompletedNum;
    /* access modifiers changed from: private */
    public boolean mFirstNum;
    private int mHdrCheckerAdrc;
    private int[] mHdrCheckerEvValue;
    private int mHdrCheckerSceneType;
    private boolean mIsHdrBokeh;
    private final int mOperationMode;
    /* access modifiers changed from: private */
    public int mSequenceNum;
    private boolean mShouldDoMFNR;
    private boolean mShouldDoQcfaCapture;
    private boolean mShouldDoSR;
    private boolean mSingleCaptureForHDRplusMFNR;

    public MiCamera2ShotParallelBurst(MiCamera2 miCamera2, CaptureResult captureResult) {
        super(miCamera2);
        ((MiCamera2ShotParallel) this).mPreviewCaptureResult = captureResult;
        this.mOperationMode = ((MiCamera2Shot) this).mMiCamera.getCapabilities().getOperatingMode();
    }

    static /* synthetic */ int access$308(MiCamera2ShotParallelBurst miCamera2ShotParallelBurst) {
        int i = miCamera2ShotParallelBurst.mCompletedNum;
        miCamera2ShotParallelBurst.mCompletedNum = i + 1;
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000f, code lost:
        if (r5 != 9) goto L_0x0038;
     */
    private void applyAlgoParameter(CaptureRequest.Builder builder, int i, int i2) {
        if (i2 == 1) {
            applyHdrParameter(builder, i);
        } else if (i2 == 2) {
            applyClearShotParameter(builder);
        } else if (i2 != 3) {
            if (i2 == 7) {
                MiCameraCompat.applySwMfnrEnable(builder, this.mShouldDoMFNR);
                MiCameraCompat.applyMfnrEnable(builder, false);
                MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum);
                MiCameraCompat.applyHHT(builder, true);
                Log.i(TAG, "HHT algo in applyAlgoParameter");
            }
            applyLowLightBokehParameter(builder);
        } else {
            applySuperResolutionParameter(builder);
        }
        if (b.isMTKPlatform()) {
            MiCameraCompat.copyAiSceneFromCaptureResultToRequest(((MiCamera2ShotParallel) this).mPreviewCaptureResult, builder);
        } else if (isIn3OrMoreSatMode()) {
            CaptureRequestBuilder.applySmoothTransition(builder, ((MiCamera2Shot) this).mMiCamera.getCapabilities(), false);
            CaptureRequestBuilder.applySatFallback(builder, ((MiCamera2Shot) this).mMiCamera.getCapabilities(), false);
        }
    }

    private void applyClearShotParameter(CaptureRequest.Builder builder) {
        MiCameraCompat.applySwMfnrEnable(builder, this.mShouldDoMFNR);
        MiCameraCompat.applyMfnrEnable(builder, false);
        if (b.hl() || b.deviceIsMi9Lite) {
            CompatibilityUtils.setZsl(builder, true);
        }
    }

    private void applyHdrParameter(CaptureRequest.Builder builder, int i) {
        CameraCapabilities capabilities;
        if (i <= this.mSequenceNum) {
            if (!DataRepository.dataItemFeature().s_a_u_e_f_m()) {
                if (this.mIsHdrBokeh) {
                    MiCameraCompat.applyHdrBracketMode(builder, (byte) (this.mHdrCheckerEvValue[i] < 0 ? 1 : 0));
                } else {
                    MiCameraCompat.applyHdrBracketMode(builder, (byte) 1);
                }
            }
            if (!DataRepository.dataItemFeature().s_a_u_e_f_m() || !((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isHDREnabled()) {
                MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum);
            } else {
                Log.e(TAG, "[ALGOUP|MMCAMERA] Algo Up HDR!!!!");
                MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum * 2);
                builder.set(CaptureRequest.CONTROL_AE_LOCK, true);
            }
            if (b.isMTKPlatform() || b.deviceIsMiNote10 || b.deviceIsMi10 || b.deviceIsRedmiK30ProZoom || b.deviceIsMi10Pro) {
                builder.set(CaptureRequest.CONTROL_AE_LOCK, true);
            }
            builder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(this.mHdrCheckerEvValue[i]));
            MiCameraCompat.applyHdrParameter(builder, Integer.valueOf(this.mHdrCheckerSceneType), Integer.valueOf(this.mHdrCheckerAdrc));
            boolean z = !b.deviceIsMiNote10 ? (b.deviceIsMi10Pro || b.deviceIsMi10 || b.deviceIsRedmiK30ProZoom) && this.mHdrCheckerEvValue[i] == 0 : this.mHdrCheckerEvValue[i] >= 0;
            if (DataRepository.dataItemFeature().c_28041_0x0007() && ((((MiCamera2Shot) this).mMiCamera.getSatMasterCameraId() == 2 || ((MiCamera2Shot) this).mMiCamera.getSatMasterCameraId() == 1 || (((MiCamera2Shot) this).mMiCamera.getSatMasterCameraId() == 3 && b.deviceIsRedmiK30ProZoom)) && z && isIn3OrMoreSatMode() && this.mSequenceNum < 4)) {
                Log.d(TAG, "applyHdrParameter enable mfnr EV = " + this.mHdrCheckerEvValue[i]);
                MiCameraCompat.applyMfnrEnable(builder, true);
            } else if (this.mSingleCaptureForHDRplusMFNR) {
                Log.d(TAG, "applyHdrParameter enable mfnr");
                MiCameraCompat.applyMfnrEnable(builder, true);
            } else if (!z || !this.mIsHdrBokeh) {
                Log.d(TAG, "applyHdrParameter disable mfnr EV = " + this.mHdrCheckerEvValue[i]);
                MiCameraCompat.applyMfnrEnable(builder, false);
            } else {
                Log.d(TAG, "applyHdrParameter enable mfnr EV = " + this.mHdrCheckerEvValue[i]);
                MiCameraCompat.applyMfnrEnable(builder, true);
            }
            if (this.mIsHdrBokeh && (capabilities = ((MiCamera2Shot) this).mMiCamera.getCapabilities()) != null && capabilities.isSupportHdrBokeh()) {
                MiCameraCompat.applyHdrBokeh(builder, true);
                return;
            }
            return;
        }
        throw new RuntimeException("wrong request index " + i);
    }

    private void applyLowLightBokehParameter(CaptureRequest.Builder builder) {
        MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum);
        MiCameraCompat.applySwMfnrEnable(builder, false);
        MiCameraCompat.applyMfnrEnable(builder, false);
    }

    private void applySuperResolutionParameter(CaptureRequest.Builder builder) {
        MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum);
        MiCameraCompat.applyMfnrEnable(builder, false);
        if (b.isMTKPlatform()) {
            builder.set(CaptureRequest.SCALER_CROP_REGION, ((MiCamera2ShotParallel) this).mActiveArraySize);
            MiCameraCompat.applyPostProcessCropRegion(builder, HybridZoomingSystem.toCropRegion(((MiCamera2Shot) this).mMiCamera.getZoomRatio(), ((MiCamera2ShotParallel) this).mActiveArraySize));
        }
    }

    private int getGroupShotMaxImage() {
        Face[] faceArr = (Face[]) ((MiCamera2ShotParallel) this).mPreviewCaptureResult.get(CaptureResult.STATISTICS_FACES);
        return Util.clamp((faceArr != null ? faceArr.length : 0) + 1, 2, 4);
    }

    private int getGroupShotNum() {
        if (Util.isMemoryRich(CameraAppImpl.getAndroidContext())) {
            return getGroupShotMaxImage();
        }
        Log.w(TAG, "getGroupShotNum: low memory");
        return 2;
    }

    private boolean isUpdateHDRCheckerValues() {
        return getMagneticDetectedCallback() == null || !getMagneticDetectedCallback().isLockHDRChecker();
    }

    private void prepareClearShot(int i) {
        if (b.hl()) {
            this.mSequenceNum = 10;
        } else {
            this.mSequenceNum = 5;
        }
    }

    private void prepareGroupShot() {
        this.mSequenceNum = getGroupShotNum();
    }

    private void prepareHHT() {
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder == null || localBinder.isIdle() || ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isAiASDEnabled() || ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().getBeautyValues().isSmoothLevelOn()) {
            this.mSequenceNum = 5;
            Log.i(TAG, "hht(5 -> 1)");
            return;
        }
        this.mSequenceNum = 3;
        Log.i(TAG, "switch to quick shot hht(3 -> 1)");
    }

    private void prepareHdr() {
        if (this.mSingleCaptureForHDRplusMFNR) {
            this.mSequenceNum = 1;
            this.mHdrCheckerEvValue = new int[]{0};
        } else {
            HdrEvValue hdrEvValue = new HdrEvValue(CaptureResultParser.getHdrCheckerValues(((MiCamera2ShotParallel) this).mPreviewCaptureResult));
            this.mSequenceNum = hdrEvValue.getSequenceNum();
            this.mHdrCheckerEvValue = hdrEvValue.getHdrCheckerEvValue();
        }
        if (isUpdateHDRCheckerValues() || ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().getHdrCheckerEvValue() == null) {
            this.mHdrCheckerSceneType = CaptureResultParser.getHdrCheckerSceneType(((MiCamera2ShotParallel) this).mPreviewCaptureResult);
            this.mHdrCheckerAdrc = CaptureResultParser.getHdrCheckerAdrc(((MiCamera2ShotParallel) this).mPreviewCaptureResult);
            Log.d(TAG, "prepareHdr: scene = " + this.mHdrCheckerSceneType + " adrc = " + this.mHdrCheckerAdrc);
            ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().setHdrCheckerEvValue(this.mHdrCheckerEvValue);
            ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().setHdrCheckerSceneType(this.mHdrCheckerSceneType);
            ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().setHdrCheckerAdrc(this.mHdrCheckerAdrc);
            return;
        }
        Log.d(TAG, "hdr checker values not update：");
        this.mHdrCheckerEvValue = ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().getHdrCheckerEvValue();
        this.mHdrCheckerSceneType = ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().getHdrCheckerSceneType();
        this.mHdrCheckerAdrc = ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().getHdrCheckerAdrc();
        StringBuilder sb = new StringBuilder();
        sb.append("prepareHdr: scene = ");
        sb.append(this.mHdrCheckerSceneType);
        sb.append(",adrc = ");
        sb.append(this.mHdrCheckerAdrc);
        sb.append(",EvValue = ");
        int[] iArr = this.mHdrCheckerEvValue;
        sb.append(iArr != null ? Arrays.toString(iArr) : null);
        Log.d(TAG, sb.toString());
    }

    private void prepareLowLightBokeh() {
        this.mSequenceNum = 6;
    }

    private void prepareSR() {
        this.mSequenceNum = DataRepository.dataItemFeature().Ib();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotParallelBurst.AnonymousClass1 */
            long captureTimestamp = -1;

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                MiCamera2ShotParallelBurst.access$308(MiCamera2ShotParallelBurst.this);
                if (!DataRepository.dataItemFeature().s_a_u_e_f_m() || !((MiCamera2Shot) MiCamera2ShotParallelBurst.this).mMiCamera.getCameraConfigs().isHDREnabled()) {
                    Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: " + MiCamera2ShotParallelBurst.this.mCompletedNum + "/" + MiCamera2ShotParallelBurst.this.mSequenceNum);
                } else {
                    Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: " + MiCamera2ShotParallelBurst.this.mCompletedNum + "/" + (MiCamera2ShotParallelBurst.this.mSequenceNum * 2));
                }
                AlgoConnector.getInstance().getLocalBinder().onCaptureCompleted(CameraDeviceUtil.getCustomCaptureResult(totalCaptureResult), MiCamera2ShotParallelBurst.this.mCompletedNum == 1);
                if (!DataRepository.dataItemFeature().s_a_u_e_f_m() || !((MiCamera2Shot) MiCamera2ShotParallelBurst.this).mMiCamera.getCameraConfigs().isHDREnabled()) {
                    if (MiCamera2ShotParallelBurst.this.mSequenceNum == MiCamera2ShotParallelBurst.this.mCompletedNum) {
                        MiCamera2ShotParallelBurst miCamera2ShotParallelBurst = MiCamera2ShotParallelBurst.this;
                        ((MiCamera2Shot) miCamera2ShotParallelBurst).mMiCamera.onCapturePictureFinished(true, miCamera2ShotParallelBurst);
                    }
                } else if (MiCamera2ShotParallelBurst.this.mSequenceNum * 2 == MiCamera2ShotParallelBurst.this.mCompletedNum) {
                    MiCamera2ShotParallelBurst miCamera2ShotParallelBurst2 = MiCamera2ShotParallelBurst.this;
                    ((MiCamera2Shot) miCamera2ShotParallelBurst2).mMiCamera.onCapturePictureFinished(true, miCamera2ShotParallelBurst2);
                }
                boolean isSREnable = CaptureResultParser.isSREnable(totalCaptureResult);
                Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: isSREnabled = " + isSREnable);
                Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: hdrEnabled = " + ((Boolean) VendorTagHelper.getValue(totalCaptureResult, CaptureResultVendorTags.IS_HDR_ENABLE)));
                ImagePool.getInstance().trimPoolBuffer();
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                Log.e(MiCamera2ShotParallelBurst.TAG, "onCaptureFailed: reason=" + captureFailure.getReason() + " timestamp=" + this.captureTimestamp);
                MiCamera2ShotParallelBurst miCamera2ShotParallelBurst = MiCamera2ShotParallelBurst.this;
                ((MiCamera2Shot) miCamera2ShotParallelBurst).mMiCamera.onCapturePictureFinished(false, miCamera2ShotParallelBurst);
                if (this.captureTimestamp != -1) {
                    AlgoConnector.getInstance().getLocalBinder().onCaptureFailed(this.captureTimestamp, captureFailure.getReason());
                }
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureStarted: timestamp=" + j + " frameNumber=" + j2 + " isFirst=" + MiCamera2ShotParallelBurst.this.mFirstNum);
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                if (MiCamera2ShotParallelBurst.this.mFirstNum) {
                    Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotParallelBurst.this.getPictureCallback();
                    if (pictureCallback != null) {
                        ParallelTaskData parallelTaskData = new ParallelTaskData(((MiCamera2Shot) MiCamera2ShotParallelBurst.this).mMiCamera.getId(), j, ((MiCamera2Shot) MiCamera2ShotParallelBurst.this).mMiCamera.getCameraConfigs().getShotType(), ((MiCamera2Shot) MiCamera2ShotParallelBurst.this).mMiCamera.getCameraConfigs().getShotPath(), ((MiCamera2Shot) MiCamera2ShotParallelBurst.this).mMiCamera.getCameraConfigs().getCaptureTime());
                        MiCamera2ShotParallelBurst miCamera2ShotParallelBurst = MiCamera2ShotParallelBurst.this;
                        ParallelTaskData onCaptureStart = pictureCallback.onCaptureStart(parallelTaskData, ((MiCamera2ShotParallel) miCamera2ShotParallelBurst).mCapturedImageSize, miCamera2ShotParallelBurst.isQuickShotAnimation());
                        if (onCaptureStart != null) {
                            onCaptureStart.setAlgoType(MiCamera2ShotParallelBurst.this.mAlgoType);
                            if (!DataRepository.dataItemFeature().s_a_u_e_f_m() || !((MiCamera2Shot) MiCamera2ShotParallelBurst.this).mMiCamera.getCameraConfigs().isHDREnabled()) {
                                onCaptureStart.setBurstNum(MiCamera2ShotParallelBurst.this.mSequenceNum);
                            } else {
                                onCaptureStart.setBurstNum(MiCamera2ShotParallelBurst.this.mSequenceNum * 2);
                            }
                            this.captureTimestamp = j;
                            AlgoConnector.getInstance().getLocalBinder().onCaptureStarted(onCaptureStart);
                        } else {
                            Log.w(MiCamera2ShotParallelBurst.TAG, "onCaptureStarted: null task data");
                        }
                    } else {
                        Log.w(MiCamera2ShotParallelBurst.TAG, "onCaptureStarted: null picture callback");
                    }
                    boolean unused = MiCamera2ShotParallelBurst.this.mFirstNum = false;
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder createCaptureRequest = ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(2);
        if (((MiCamera2Shot) this).mMiCamera.isQcfaEnable()) {
            Surface wideRemoteSurface = this.mShouldDoQcfaCapture ? ((MiCamera2Shot) this).mMiCamera.getWideRemoteSurface() : ((MiCamera2Shot) this).mMiCamera.getQcfaRemoteSurface();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(wideRemoteSurface);
            Log.d(TAG, String.format(Locale.ENGLISH, "[QCFA]add surface %s to capture request, size is: %s", wideRemoteSurface, surfaceSize));
            createCaptureRequest.addTarget(wideRemoteSurface);
            if (b.hl() || b.deviceIsMi9Lite) {
                createCaptureRequest.addTarget(((MiCamera2Shot) this).mMiCamera.getPreviewSurface());
            }
            configParallelSession(surfaceSize);
        } else {
            if (isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) {
                Surface mainCaptureSurface = getMainCaptureSurface();
                Size surfaceSize2 = SurfaceUtils.getSurfaceSize(mainCaptureSurface);
                Log.d(TAG, String.format(Locale.ENGLISH, "[SAT]add surface %s to capture request, size is: %s", mainCaptureSurface, surfaceSize2));
                createCaptureRequest.addTarget(mainCaptureSurface);
                int i = 513;
                if (mainCaptureSurface == ((MiCamera2Shot) this).mMiCamera.getUltraWideRemoteSurface()) {
                    i = 3;
                }
                Log.d(TAG, "[SAT]combinationMode: " + i);
                configParallelSession(surfaceSize2, i);
            } else {
                for (Surface surface : ((MiCamera2Shot) this).mMiCamera.getRemoteSurfaceList()) {
                    Log.d(TAG, String.format(Locale.ENGLISH, "add surface %s to capture request, size is: %s", surface, SurfaceUtils.getSurfaceSize(surface)));
                    createCaptureRequest.addTarget(surface);
                }
                ((MiCamera2ShotParallel) this).mCapturedImageSize = ((MiCamera2Shot) this).mMiCamera.getPictureSize();
            }
            if (!b.isMTKPlatform() && this.mOperationMode != 36865 && (b.hl() || b.deviceIsMi9Lite || this.mOperationMode != 36867)) {
                Surface previewSurface = ((MiCamera2Shot) this).mMiCamera.getPreviewSurface();
                Log.d(TAG, String.format(Locale.ENGLISH, "add preview surface %s to capture request, size is: %s", previewSurface, SurfaceUtils.getSurfaceSize(previewSurface)));
                createCaptureRequest.addTarget(previewSurface);
            }
        }
        createCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, 1);
        ((MiCamera2Shot) this).mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        if (this.mAlgoType == 1) {
            if (!b.deviceIsMi10Pro || !this.mIsHdrBokeh) {
                Log.d(TAG, "disable ZSL for HDR");
                CompatibilityUtils.setZsl(createCaptureRequest, false);
            }
        } else if (!b.isMTKPlatform()) {
            Log.d(TAG, "disable ZSL for algo " + this.mAlgoType);
            CompatibilityUtils.setZsl(createCaptureRequest, false);
        }
        CaptureRequestBuilder.applyFlawDetectEnable(((MiCamera2Shot) this).mMiCamera.getCapabilities(), createCaptureRequest, ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isFlawDetectEnable());
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
        this.mFirstNum = true;
        this.mShouldDoQcfaCapture = false;
        this.mShouldDoSR = ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isSuperResolutionEnabled();
        if (((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isHDREnabled()) {
            this.mIsHdrBokeh = ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isRearBokehEnabled();
            this.mSingleCaptureForHDRplusMFNR = b.deviceIsMi10Pro && ((MiCamera2Shot) this).mMiCamera.getCapabilities().getFacing() == 1 && !((MiCamera2Shot) this).mMiCamera.isMacroMode() && !this.mIsHdrBokeh;
            this.mAlgoType = 1;
            prepareHdr();
        } else if (DataRepository.dataItemFeature().c_9006_0x0003() && ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isRearBokehEnabled()) {
            this.mAlgoType = 9;
            prepareLowLightBokeh();
        } else if (CameraSettings.isGroupShotOn()) {
            this.mAlgoType = 5;
            prepareGroupShot();
        } else if (this.mShouldDoSR) {
            this.mAlgoType = 3;
            prepareSR();
        } else {
            Integer num = (Integer) ((MiCamera2ShotParallel) this).mPreviewCaptureResult.get(CaptureResult.SENSOR_SENSITIVITY);
            Log.d(TAG, "prepare: iso = " + num);
            if (b.fl() || b.Zk()) {
                this.mShouldDoMFNR = true;
            } else {
                this.mShouldDoMFNR = num != null && num.intValue() >= 800;
            }
            if (!this.mShouldDoMFNR) {
                this.mAlgoType = 0;
                this.mSequenceNum = 1;
            } else if (b.fl() && CameraSettings.isFrontCamera()) {
                this.mAlgoType = 2;
                prepareClearShot(num.intValue());
            } else if (!b.Zk() || !CameraSettings.isBackCamera()) {
                this.mAlgoType = 2;
                prepareClearShot(num.intValue());
            } else {
                this.mAlgoType = 7;
                prepareHHT();
            }
        }
        Log.d(TAG, String.format(Locale.ENGLISH, "prepare: algo=%d captureNum=%d doMFNR=%b doSR=%b", Integer.valueOf(this.mAlgoType), Integer.valueOf(this.mSequenceNum), Boolean.valueOf(this.mShouldDoMFNR), Boolean.valueOf(this.mShouldDoSR)));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        try {
            CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
            CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            for (int i2 = 0; i2 < this.mSequenceNum; i2++) {
                if (b.isMTKPlatform() && ((MiCamera2Shot) this).mMiCamera.getCapabilities().getCameraId() == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                    MiCameraCompat.copyFpcDataFromCaptureResultToRequest(((MiCamera2ShotParallel) this).mPreviewCaptureResult, generateRequestBuilder);
                }
                if (DataRepository.dataItemFeature().s_a_u_e_f_m()) {
                    MiCameraCompat.applyAlgoUpEnabled(generateRequestBuilder, true);
                }
                applyAlgoParameter(generateRequestBuilder, i2, this.mAlgoType);
                arrayList.add(generateRequestBuilder.build());
                if (DataRepository.dataItemFeature().s_a_u_e_f_m() && ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isHDREnabled()) {
                    CaptureRequest.Builder generateRequestBuilder2 = generateRequestBuilder();
                    MiCameraCompat.applyAlgoUpEnabled(generateRequestBuilder2, true);
                    applyAlgoParameter(generateRequestBuilder2, i2, this.mAlgoType);
                    arrayList.add(generateRequestBuilder2.build());
                }
            }
            if ((b.deviceIsMi10 || b.ql()) && this.mAlgoType == 1) {
                while (true) {
                    if (i >= this.mSequenceNum) {
                        break;
                    } else if (this.mHdrCheckerEvValue[i] == 0) {
                        CaptureRequest captureRequest = (CaptureRequest) arrayList.remove(i);
                        if (captureRequest != null) {
                            arrayList.add(captureRequest);
                        }
                    } else {
                        i++;
                    }
                }
            }
            Log.d(TAG, "startSessionCapture request number:" + arrayList.size());
            ((MiCamera2Shot) this).mMiCamera.getCaptureSession().captureBurst(arrayList, generateCaptureCallback, ((MiCamera2Shot) this).mCameraHandler);
            MemoryHelper.addCapturedNumber(((MiCamera2Shot) this).mMiCamera.hashCode(), this.mSequenceNum);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(TAG, "Cannot captureBurst");
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to captureBurst, IllegalState", e3);
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(256);
        } catch (IllegalArgumentException e4) {
            Log.e(TAG, "Failed to capture a still picture, IllegalArgument", e4);
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(256);
        }
    }
}
