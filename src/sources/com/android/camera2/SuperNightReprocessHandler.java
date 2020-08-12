package com.android.camera2;

import android.graphics.Rect;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Surface;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.arcsoft.supernight.SuperNightProcess;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class SuperNightReprocessHandler extends Handler {
    private static final int MSG_CLEANUP = 19;
    private static final int MSG_DATA = 16;
    private static final int MSG_META = 17;
    private static final int MSG_PREPARE = 18;
    private static final int RAW_FORMAT_RAW10 = 1;
    private static final int RAW_FORMAT_RAW12 = 0;
    private static final String TAG = "SNReprocessHandler";
    private final int mBaseEvIndex = 3;
    /* access modifiers changed from: private */
    public MiCamera2ShotRawBurst mCamera2Shot;
    /* access modifiers changed from: private */
    public final MiCamera2 mCameraDevice;
    private final int mCameraMode;
    private final Handler mCaptureStateHandler;
    private final AtomicBoolean mIsCancelled;
    private final int mMaxInputImageCount = MiCamera2ShotRawBurst.EV_LIST.length;
    private final SuperNightProcess mSuperNightProcess;
    private final ArrayList<Image> mUnprocessedData = new ArrayList<>();
    private final ArrayList<TotalCaptureResult> mUnprocessedMeta = new ArrayList<>();
    private int superNightRawFormat;

    public SuperNightReprocessHandler(Looper looper, MiCamera2 miCamera2, int i) {
        super(looper);
        int i2;
        if (i == 0) {
            this.superNightRawFormat = SuperNightProcess.ASVL_PAF_RAW12_GRBG_16B;
            i2 = DataRepository.dataItemFeature().tc() ? SuperNightProcess.ARC_SN_CAMERA_MODE_XIAOMI_G90_GW1_INDIA : 1793;
        } else if (i != 1) {
            i2 = 0;
        } else {
            this.superNightRawFormat = SuperNightProcess.ASVL_PAF_RAW10_GRBG_16B;
            i2 = DataRepository.dataItemFeature().tc() ? SuperNightProcess.ARC_SN_CAMERA_MODE_XIAOMI_J15S_G85_S5KGM1_N_INDIA : SuperNightProcess.ARC_SN_CAMERA_MODE_XIAOMI_J15S_G85_S5KGM1_N;
        }
        this.mCameraDevice = miCamera2;
        this.mCaptureStateHandler = this.mCameraDevice.getCameraHandler();
        this.mSuperNightProcess = new SuperNightProcess(this.mCameraDevice.getCapabilities().getActiveArraySize());
        this.mIsCancelled = new AtomicBoolean(false);
        this.mCameraMode = i2;
    }

    private void clearCache() {
        Log.e(TAG, "clearCache: E");
        this.mUnprocessedMeta.clear();
        Iterator<Image> it = this.mUnprocessedData.iterator();
        while (it.hasNext()) {
            it.next().close();
        }
        this.mUnprocessedData.clear();
        Log.e(TAG, "clearCache: X");
    }

    private boolean convert(Rect rect) {
        int width = this.mUnprocessedData.get(0).getWidth();
        int height = this.mUnprocessedData.get(0).getHeight();
        int i = rect.left;
        int i2 = width - rect.right;
        if (i > i2) {
            rect.right = width - i;
        } else if (i < i2) {
            rect.left = i2;
        }
        int i3 = rect.top;
        int i4 = height - rect.bottom;
        if (i3 > i4) {
            rect.bottom = height - i3;
        } else if (i3 < i4) {
            rect.top = i4;
        }
        int width2 = rect.width() * height;
        int height2 = rect.height() * width;
        if (width2 > height2) {
            int i5 = (int) ((((float) height2) * 0.5f) / ((float) height));
            rect.left = rect.centerX() - i5;
            rect.right = rect.centerX() + i5;
        } else if (width2 < height2) {
            int i6 = (int) ((((float) width2) * 0.5f) / ((float) width));
            rect.top = rect.centerY() - i6;
            rect.bottom = rect.centerY() + i6;
        }
        Rect activeArraySize = this.mCameraDevice.getCapabilities().getActiveArraySize();
        float width3 = ((float) activeArraySize.width()) / ((float) width);
        float height3 = ((float) activeArraySize.height()) / ((float) height);
        rect.left = (int) (((float) rect.left) * width3);
        rect.top = (int) (((float) rect.top) * height3);
        int i7 = activeArraySize.right;
        int i8 = rect.left;
        rect.right = i7 - i8;
        rect.bottom = activeArraySize.bottom - rect.top;
        if (i8 % 2 != 0) {
            rect.left = i8 + 1;
        }
        int i9 = rect.top;
        if (i9 % 2 != 0) {
            rect.top = i9 + 1;
        }
        int i10 = rect.right;
        if (i10 % 2 != 0) {
            rect.right = i10 - 1;
        }
        int i11 = rect.bottom;
        if (i11 % 2 != 0) {
            rect.bottom = i11 - 1;
        }
        return rect.intersect(HybridZoomingSystem.toCropRegion(this.mCameraDevice.getZoomRatio(), activeArraySize));
    }

    private CameraCaptureSession.CaptureCallback generateReprocessCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.SuperNightReprocessHandler.AnonymousClass1 */

            public void onCaptureBufferLost(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull Surface surface, long j) {
                super.onCaptureBufferLost(cameraCaptureSession, captureRequest, surface, j);
                Log.e(SuperNightReprocessHandler.TAG, "onCaptureBufferLost:<JPEG>: frameNumber = " + j);
                if (SuperNightReprocessHandler.this.mCameraDevice.getSuperNight()) {
                    SuperNightReprocessHandler.this.mCameraDevice.setAWBLock(false);
                }
                SuperNightReprocessHandler.this.mCameraDevice.onCapturePictureFinished(false, SuperNightReprocessHandler.this.mCamera2Shot);
                SuperNightReprocessHandler.this.mCameraDevice.setCaptureEnable(true);
            }

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                Log.d(SuperNightReprocessHandler.TAG, "onCaptureCompleted:<JPEG>: " + totalCaptureResult.getFrameNumber());
                if (SuperNightReprocessHandler.this.mCameraDevice.getSuperNight()) {
                    SuperNightReprocessHandler.this.mCameraDevice.setAWBLock(false);
                }
                SuperNightReprocessHandler.this.mCameraDevice.onCapturePictureFinished(true, SuperNightReprocessHandler.this.mCamera2Shot);
                SuperNightReprocessHandler.this.mCameraDevice.setCaptureEnable(true);
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                Log.e(SuperNightReprocessHandler.TAG, "onCaptureFailed:<JPEG>: reason = " + captureFailure.getReason());
                if (SuperNightReprocessHandler.this.mCameraDevice.getSuperNight()) {
                    SuperNightReprocessHandler.this.mCameraDevice.setAWBLock(false);
                }
                SuperNightReprocessHandler.this.mCameraDevice.onCapturePictureFinished(false, SuperNightReprocessHandler.this.mCamera2Shot);
                SuperNightReprocessHandler.this.mCameraDevice.setCaptureEnable(true);
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                Log.d(SuperNightReprocessHandler.TAG, "onCaptureStarted:<JPEG>: " + j2);
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
            }
        };
    }

    private void sendReprocessRequest() {
        if (this.mUnprocessedData.size() == this.mMaxInputImageCount && this.mUnprocessedMeta.size() == this.mMaxInputImageCount && this.mSuperNightProcess != null && !this.mIsCancelled.get()) {
            Collections.reverse(this.mUnprocessedData);
            Collections.reverse(this.mUnprocessedMeta);
            Log.d(TAG, "sendReprocessRequest:<SNP>: E");
            this.mSuperNightProcess.init(this.mCameraMode, this.mUnprocessedData.get(0).getWidth(), this.mUnprocessedData.get(0).getHeight(), this.mUnprocessedData.get(0).getPlanes()[0].getRowStride());
            Image dequeueInputImage = this.mCameraDevice.getRawImageWriter().dequeueInputImage();
            Rect rect = new Rect();
            this.mSuperNightProcess.addAllInputInfo(this.mUnprocessedData, this.mUnprocessedMeta, this.superNightRawFormat, dequeueInputImage, rect);
            this.mSuperNightProcess.unInit();
            Log.d(TAG, "sendReprocessRequest:<SNP>: X");
            if (this.mIsCancelled.get()) {
                clearCache();
                return;
            }
            Log.d(TAG, "sendReprocessRequest:<CROP>: E " + rect);
            convert(rect);
            Log.d(TAG, "sendReprocessRequest:<CROP>: X " + rect);
            TotalCaptureResult totalCaptureResult = this.mUnprocessedMeta.get(this.mMaxInputImageCount + -1 + -3);
            try {
                Log.d(TAG, "sendReprocessRequest:<CAM>: E");
                this.mCameraDevice.getRawImageWriter().queueInputImage(dequeueInputImage);
                CaptureRequest.Builder createReprocessCaptureRequest = this.mCameraDevice.getCameraDevice().createReprocessCaptureRequest(totalCaptureResult);
                this.mCameraDevice.applySettingsForJpeg(createReprocessCaptureRequest);
                createReprocessCaptureRequest.addTarget(this.mCameraDevice.getPhotoImageReader().getSurface());
                createReprocessCaptureRequest.set(CaptureRequest.SCALER_CROP_REGION, rect);
                this.mCameraDevice.getCaptureSession().capture(createReprocessCaptureRequest.build(), generateReprocessCaptureCallback(), this.mCaptureStateHandler);
                Log.d(TAG, "sendReprocessRequest:<CAM>: X");
            } catch (Exception e2) {
                Log.d(TAG, "sendReprocessRequest:<CAM>", e2);
            } catch (Throwable th) {
                clearCache();
                throw th;
            }
            clearCache();
        } else if (this.mIsCancelled.get()) {
            clearCache();
            Log.d(TAG, "sendReprocessRequest:<CAM>: CANCELLED");
        }
    }

    public void cancel() {
        if (this.mSuperNightProcess != null) {
            Log.d(TAG, "cancelSuperNight: E");
            this.mIsCancelled.set(true);
            this.mSuperNightProcess.cancelSuperNight();
            Log.d(TAG, "cancelSuperNight: X");
        }
        obtainMessage(19).sendToTarget();
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 16:
                this.mUnprocessedData.add((Image) message.obj);
                sendReprocessRequest();
                return;
            case 17:
                this.mUnprocessedMeta.add((TotalCaptureResult) message.obj);
                sendReprocessRequest();
                return;
            case 18:
                this.mIsCancelled.set(false);
                clearCache();
                this.mCamera2Shot = (MiCamera2ShotRawBurst) message.obj;
                return;
            case 19:
                clearCache();
                return;
            default:
                return;
        }
    }

    public void prepare(MiCamera2ShotRawBurst miCamera2ShotRawBurst) {
        obtainMessage(18, miCamera2ShotRawBurst).sendToTarget();
    }

    public void queueCaptureResult(TotalCaptureResult totalCaptureResult) {
        obtainMessage(17, totalCaptureResult).sendToTarget();
    }

    public void queueImage(Image image) {
        obtainMessage(16, image).sendToTarget();
    }
}
