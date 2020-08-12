package com.android.camera2;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.utils.SurfaceUtils;
import android.support.annotation.NonNull;
import android.util.Size;
import android.view.Surface;
import com.android.camera.CameraSettings;
import com.android.camera.MemoryHelper;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.ParallelSnapshotManager;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.mi.config.b;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.imagecodec.ImagePool;
import java.util.Locale;

@TargetApi(21)
public class MiCamera2ShotParallelStill extends MiCamera2ShotParallel<ParallelTaskData> {
    private static final String TAG = "ShotParallelStill";
    /* access modifiers changed from: private */
    public int mAlgoType;
    /* access modifiers changed from: private */
    public long mCaptureTimestamp = -1;
    private final int mOperationMode;
    private boolean mShouldDoQcfaCapture;
    /* access modifiers changed from: private */
    public CaptureResult mStillCaptureResult;

    public MiCamera2ShotParallelStill(@NonNull MiCamera2 miCamera2, @NonNull CaptureResult captureResult) {
        super(miCamera2);
        ((MiCamera2ShotParallel) this).mPreviewCaptureResult = captureResult;
        this.mOperationMode = miCamera2.getCapabilities().getOperatingMode();
    }

    private void applyAlgoParameter(CaptureRequest.Builder builder) {
        MiCameraCompat.applySwMfnrEnable(builder, false);
        MiCameraCompat.applyHDR(builder, false);
        MiCameraCompat.applySuperResolution(builder, false);
        MiCameraCompat.applyMultiFrameInputNum(builder, 1);
        if (b.isMTKPlatform()) {
            if (((MiCamera2Shot) this).mMiCamera.getCapabilities().getCameraId() == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                MiCameraCompat.copyFpcDataFromCaptureResultToRequest(((MiCamera2ShotParallel) this).mPreviewCaptureResult, builder);
                builder.set(CaptureRequest.SCALER_CROP_REGION, ((MiCamera2ShotParallel) this).mActiveArraySize);
                MiCameraCompat.applyPostProcessCropRegion(builder, (Rect) builder.get(CaptureRequest.SCALER_CROP_REGION));
            }
            MiCameraCompat.copyAiSceneFromCaptureResultToRequest(((MiCamera2ShotParallel) this).mPreviewCaptureResult, builder);
        } else if (isIn3OrMoreSatMode()) {
            CaptureRequestBuilder.applySmoothTransition(builder, ((MiCamera2Shot) this).mMiCamera.getCapabilities(), false);
        }
    }

    private boolean shouldDoQCFA() {
        if (((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isHDREnabled() || ((MiCamera2Shot) this).mMiCamera.isBeautyOn()) {
            return false;
        }
        if (CameraSettings.isFrontCamera() && !b.Uu) {
            return false;
        }
        if (((MiCamera2Shot) this).mMiCamera.getCapabilities().isRemosaicDetecedSupported()) {
            return CaptureResultParser.isRemosaicDetected(((MiCamera2ShotParallel) this).mPreviewCaptureResult);
        }
        Integer num = (Integer) ((MiCamera2ShotParallel) this).mPreviewCaptureResult.get(CaptureResult.SENSOR_SENSITIVITY);
        Log.d(TAG, "shouldDoQCFA: iso = " + num);
        return num != null && num.intValue() <= 200;
    }

    private boolean useVTCameraSnapshot() {
        int satMasterCameraId = ((MiCamera2Shot) this).mMiCamera.getSatMasterCameraId();
        return ParallelSnapshotManager.getInstance().isParallelCameraDeviceConfiged(((MiCamera2Shot) this).mMiCamera.getCapabilities()) && (isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) && !((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isLLSEnabled() && (satMasterCameraId == 2 || satMasterCameraId == 1);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotParallelStill.AnonymousClass1 */

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                Log.d(MiCamera2ShotParallelStill.TAG, "onCaptureCompleted: frameNumber=" + totalCaptureResult.getFrameNumber());
                MiCamera2ShotParallelStill miCamera2ShotParallelStill = MiCamera2ShotParallelStill.this;
                ((MiCamera2Shot) miCamera2ShotParallelStill).mMiCamera.onCapturePictureFinished(true, miCamera2ShotParallelStill);
                Boolean bool = (Boolean) VendorTagHelper.getValue(totalCaptureResult, CaptureResultVendorTags.IS_HDR_ENABLE);
                if (bool != null && bool.booleanValue()) {
                    Log.e(MiCamera2ShotParallelStill.TAG, "onCaptureCompleted: HDR error");
                }
                Boolean bool2 = (Boolean) VendorTagHelper.getValue(totalCaptureResult, CaptureResultVendorTags.IS_SR_ENABLE);
                if (bool2 != null && bool2.booleanValue()) {
                    Log.e(MiCamera2ShotParallelStill.TAG, "onCaptureCompleted: SR error");
                }
                CaptureResult unused = MiCamera2ShotParallelStill.this.mStillCaptureResult = totalCaptureResult;
                AlgoConnector.getInstance().getLocalBinder().onCaptureCompleted(CameraDeviceUtil.getCustomCaptureResult(MiCamera2ShotParallelStill.this.mStillCaptureResult), true);
                ImagePool.getInstance().trimPoolBuffer();
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                Log.e(MiCamera2ShotParallelStill.TAG, "onCaptureFailed: reason=" + captureFailure.getReason() + "frameNumber=" + captureFailure.getFrameNumber());
                MiCamera2ShotParallelStill miCamera2ShotParallelStill = MiCamera2ShotParallelStill.this;
                ((MiCamera2Shot) miCamera2ShotParallelStill).mMiCamera.onCapturePictureFinished(false, miCamera2ShotParallelStill);
                if (MiCamera2ShotParallelStill.this.mCaptureTimestamp != -1) {
                    AlgoConnector.getInstance().getLocalBinder().onCaptureFailed(MiCamera2ShotParallelStill.this.mCaptureTimestamp, captureFailure.getReason());
                }
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                Log.d(MiCamera2ShotParallelStill.TAG, "onCaptureStarted: timestamp=" + j + " frameNumber=" + j2);
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotParallelStill.this.getPictureCallback();
                if (pictureCallback != null) {
                    ParallelTaskData parallelTaskData = new ParallelTaskData(((MiCamera2Shot) MiCamera2ShotParallelStill.this).mMiCamera.getId(), j, ((MiCamera2Shot) MiCamera2ShotParallelStill.this).mMiCamera.getCameraConfigs().getShotType(), ((MiCamera2Shot) MiCamera2ShotParallelStill.this).mMiCamera.getCameraConfigs().getShotPath(), ((MiCamera2Shot) MiCamera2ShotParallelStill.this).mMiCamera.getCameraConfigs().getCaptureTime());
                    MiCamera2ShotParallelStill miCamera2ShotParallelStill = MiCamera2ShotParallelStill.this;
                    ParallelTaskData onCaptureStart = pictureCallback.onCaptureStart(parallelTaskData, ((MiCamera2ShotParallel) miCamera2ShotParallelStill).mCapturedImageSize, miCamera2ShotParallelStill.isQuickShotAnimation());
                    Boolean bool = (Boolean) VendorTagHelper.getValue(captureRequest, CaptureRequestVendorTags.MFNR_ENABLED);
                    if (onCaptureStart != null) {
                        onCaptureStart.setAlgoType(MiCamera2ShotParallelStill.this.mAlgoType);
                        onCaptureStart.setBurstNum(1);
                        if (bool == null || !bool.booleanValue()) {
                            onCaptureStart.setHWMFNRProcessing(false);
                        } else {
                            Log.d(MiCamera2ShotParallelStill.TAG, "onCaptureStarted, set HWMFNRProcessing is true");
                            onCaptureStart.setHWMFNRProcessing(true);
                        }
                        long unused = MiCamera2ShotParallelStill.this.mCaptureTimestamp = j;
                        AlgoConnector.getInstance().getLocalBinder().onCaptureStarted(onCaptureStart);
                        return;
                    }
                    Log.w(MiCamera2ShotParallelStill.TAG, "onCaptureStarted: null task data");
                    return;
                }
                Log.w(MiCamera2ShotParallelStill.TAG, "onCaptureStarted: null picture callback");
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        int i;
        CaptureRequest.Builder createCaptureRequest = useVTCameraSnapshot() ? ParallelSnapshotManager.getInstance().getCameraDevice().createCaptureRequest(2) : ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(2);
        if (((MiCamera2Shot) this).mMiCamera.isQcfaEnable()) {
            Surface wideRemoteSurface = (((MiCamera2Shot) this).mMiCamera.alwaysUseRemosaicSize() || this.mShouldDoQcfaCapture) ? ((MiCamera2Shot) this).mMiCamera.getWideRemoteSurface() : ((MiCamera2Shot) this).mMiCamera.getQcfaRemoteSurface();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(wideRemoteSurface);
            configParallelSession(surfaceSize);
            Log.d(TAG, String.format(Locale.ENGLISH, "[QCFA]add surface %s to capture request, size is: %s", wideRemoteSurface, surfaceSize));
            createCaptureRequest.addTarget(wideRemoteSurface);
        } else {
            if (isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) {
                Surface captureSurface = useVTCameraSnapshot() ? ParallelSnapshotManager.getInstance().getCaptureSurface(((MiCamera2Shot) this).mMiCamera.getSatMasterCameraId()) : getMainCaptureSurface();
                Size surfaceSize2 = SurfaceUtils.getSurfaceSize(captureSurface);
                Log.d(TAG, String.format(Locale.ENGLISH, "[SAT]add surface %s to capture request, size is: %s", captureSurface, surfaceSize2));
                createCaptureRequest.addTarget(captureSurface);
                int i2 = 513;
                if (captureSurface == ((MiCamera2Shot) this).mMiCamera.getUltraWideRemoteSurface() || (useVTCameraSnapshot() && captureSurface == ParallelSnapshotManager.getInstance().getCaptureSurface(1))) {
                    i2 = 3;
                }
                Log.d(TAG, "[SAT]combinationMode: " + i2);
                configParallelSession(surfaceSize2, i2);
            } else {
                for (Surface surface : ((MiCamera2Shot) this).mMiCamera.getRemoteSurfaceList()) {
                    Log.d(TAG, String.format(Locale.ENGLISH, "add surface %s to capture request, size is: %s", surface, SurfaceUtils.getSurfaceSize(surface)));
                    createCaptureRequest.addTarget(surface);
                }
                ((MiCamera2ShotParallel) this).mCapturedImageSize = ((MiCamera2Shot) this).mMiCamera.getPictureSize();
            }
            if (!b.isMTKPlatform() && (i = this.mOperationMode) != 36865 && i != 36867 && (!((MiCamera2Shot) this).mMiCamera.isFacingFront() || this.mOperationMode != 36869)) {
                Surface previewSurface = ((MiCamera2Shot) this).mMiCamera.getPreviewSurface();
                Log.d(TAG, String.format(Locale.ENGLISH, "add preview surface %s to capture request, size is: %s", previewSurface, SurfaceUtils.getSurfaceSize(previewSurface)));
                if (!useVTCameraSnapshot()) {
                    createCaptureRequest.addTarget(previewSurface);
                }
            }
        }
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) ((MiCamera2Shot) this).mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        ((MiCamera2Shot) this).mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        if (this.mShouldDoQcfaCapture) {
            MiCameraCompat.applyMfnrEnable(createCaptureRequest, false);
        }
        if (((MiCamera2Shot) this).mMiCamera.isQcfaEnable()) {
            if (b.isMTKPlatform()) {
                Log.d(TAG, "enable remosaic capture hint");
                MiCameraCompat.applyRemosaicHint(createCaptureRequest, true);
            }
            Log.d(TAG, "apply remosaic capture request: " + this.mShouldDoQcfaCapture);
            MiCameraCompat.applyRemosaicEnabled(createCaptureRequest, this.mShouldDoQcfaCapture);
        }
        CaptureRequestBuilder.applyFlawDetectEnable(((MiCamera2Shot) this).mMiCamera.getCapabilities(), createCaptureRequest, ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isFlawDetectEnable());
        if (((MiCamera2Shot) this).mMiCamera.isFixShotTime() && DataRepository.dataItemFeature().Dc() && AlgoConnector.getInstance().getLocalBinder().isAnyRequestIsHWMFNRProcessing()) {
            Log.d(TAG, "Do not apply hwmfnr.");
            MiCameraCompat.applyMfnrEnable(createCaptureRequest, false);
            MiCameraCompat.applyMultiFrameInputNum(createCaptureRequest, 1);
        }
        return createCaptureRequest;
    }

    @Override // com.android.camera2.MiCamera2Shot
    public boolean isShutterReturned() {
        return this.mCaptureTimestamp != -1;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
        this.mAlgoType = 0;
        if (((MiCamera2Shot) this).mMiCamera.isQcfaEnable()) {
            this.mShouldDoQcfaCapture = shouldDoQCFA();
        }
        Log.d(TAG, "prepare: qcfa = " + this.mShouldDoQcfaCapture);
        if (this.mShouldDoQcfaCapture) {
            this.mAlgoType = 6;
        }
        if (((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isLLSEnabled()) {
            this.mAlgoType = 8;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        PerformanceTracker.trackPictureCapture(0);
        try {
            CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
            CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
            applyAlgoParameter(generateRequestBuilder);
            Log.dumpRequest("parallel shotstill  for camera " + ((MiCamera2Shot) this).mMiCamera.getId(), generateRequestBuilder.build());
            if (useVTCameraSnapshot()) {
                ParallelSnapshotManager.getInstance().getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, ((MiCamera2Shot) this).mCameraHandler);
            } else {
                ((MiCamera2Shot) this).mMiCamera.getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, ((MiCamera2Shot) this).mCameraHandler);
            }
            MemoryHelper.addCapturedNumber(((MiCamera2Shot) this).mMiCamera.hashCode(), 1);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(TAG, "Cannot capture a still picture");
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to capture a still picture, IllegalState", e3);
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(256);
        } catch (IllegalArgumentException e4) {
            Log.e(TAG, "Failed to capture a still picture, IllegalArgument", e4);
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(256);
        }
    }
}
