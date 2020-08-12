package com.android.zxing;

import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Environment;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.handgesture.HandGesture;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.storage.Storage;
import io.reactivex.BackpressureStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class HandGestureDecoder extends Decoder {
    private static boolean DEBUG = false;
    private static int DETECTION_FRAMES_PER_SECOND = 16;
    public static final String TAG = "HandGestureDecoder";
    private int mCameraId;
    private AtomicInteger mContinuousInterval = new AtomicInteger(0);
    private HandGesture mHandGesture;
    private boolean mTargetDetected = false;
    private int mTipShowInterval = DETECTION_FRAMES_PER_SECOND;
    private boolean mTipVisible = true;
    private boolean mTriggeringPhoto = false;

    HandGestureDecoder() {
        ((Decoder) this).mSubjects = PublishSubject.create();
        this.mHandGesture = new HandGesture();
        ((Decoder) this).mDecodeMaxCount = 5000;
        ((Decoder) this).mDecodeAutoInterval = (long) (1000 / DETECTION_FRAMES_PER_SECOND);
        ((Decoder) this).mEnable = true;
        ((Decoder) this).mSubjects.toFlowable(BackpressureStrategy.LATEST).observeOn(Schedulers.computation()).map(new a(this)).observeOn(AndroidSchedulers.mainThread()).subscribe(new b(this));
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0089 A[SYNTHETIC, Splitter:B:21:0x0089] */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    private void dumpPreviewImage(PreviewImage previewImage) {
        FileOutputStream fileOutputStream = null;
        try {
            long timestamp = previewImage.getTimestamp();
            int width = previewImage.getWidth();
            int height = previewImage.getHeight();
            FileOutputStream fileOutputStream2 = new FileOutputStream(Environment.getExternalStorageDirectory() + "/DCIM/Camera/hand_" + String.valueOf(timestamp) + Storage.JPEG_SUFFIX);
            try {
                Log.d(TAG, "PreviewImage timestamp: [" + timestamp + "]");
                new YuvImage(previewImage.getData(), 17, width, height, null).compressToJpeg(new Rect(0, 0, width, height), 100, fileOutputStream2);
                try {
                    fileOutputStream2.close();
                } catch (IOException e2) {
                    Log.e(TAG, "Close stream failed!", e2);
                }
            } catch (IOException e3) {
                e = e3;
                fileOutputStream = fileOutputStream2;
                try {
                    Log.e(TAG, "Dump preview Image failed!", e);
                    if (fileOutputStream == null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            Log.e(TAG, "Close stream failed!", e4);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            Log.e(TAG, "Dump preview Image failed!", e);
            if (fileOutputStream == null) {
                fileOutputStream.close();
            }
        }
    }

    public /* synthetic */ Integer a(PreviewImage previewImage) throws Exception {
        Log.d(TAG, "HandGestureDecoder: decode E");
        int previewStatus = previewImage.getPreviewStatus();
        if (previewStatus == 1) {
            this.mHandGesture.init(previewImage.getCameraId());
        } else if (previewStatus != 2) {
            if (previewStatus == 3) {
                try {
                    this.mHandGesture.unInit();
                } catch (Exception unused) {
                }
            }
        } else if (!(previewImage.getData() == null || previewImage.getData().length == 0)) {
            if (DEBUG) {
                dumpPreviewImage(previewImage);
            }
            return Integer.valueOf(decode(previewImage));
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public int decode(PreviewImage previewImage) {
        int orientation = previewImage.getOrientation();
        if (orientation == -1) {
            orientation = 0;
        }
        return this.mHandGesture.detectGesture(previewImage.getData(), previewImage.getWidth(), previewImage.getHeight(), (this.mCameraId == 1 && DataRepository.dataItemGlobal().getDisplayMode() == 1) ? 270 - orientation : DataRepository.dataItemGlobal().getDisplayMode() == 2 ? ((90 - orientation) + 360) % 360 : (orientation + 90) % 360);
    }

    public /* synthetic */ void e(Integer num) throws Exception {
        Log.d(TAG, "Detected rect left = " + num + " " + this.mTipShowInterval);
        if (num.intValue() >= 0) {
            this.mTargetDetected = true;
        } else {
            this.mTargetDetected = false;
            this.mContinuousInterval.set(0);
        }
        if (!this.mTriggeringPhoto) {
            Log.d(TAG, "Continuous interval: " + this.mContinuousInterval.get());
            if (this.mContinuousInterval.get() > 0) {
                this.mContinuousInterval.getAndDecrement();
            } else if (this.mTargetDetected) {
                Log.d(TAG, "Triggering countdown...");
                ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                if (cameraAction != null && !cameraAction.isDoingAction()) {
                    cameraAction.onShutterButtonClick(100);
                    this.mTriggeringPhoto = true;
                    this.mContinuousInterval.set(DETECTION_FRAMES_PER_SECOND * 3);
                    DataRepository.dataItemRunning().setHandGestureRunning(!this.mTriggeringPhoto);
                    this.mTipVisible = false;
                    this.mTipShowInterval = DETECTION_FRAMES_PER_SECOND;
                }
            }
            if (!this.mTipVisible && this.mTipShowInterval <= 0) {
                DataRepository.dataItemRunning().setHandGestureRunning(true);
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null && !topAlert.isExtraMenuShowing()) {
                    topAlert.reInitAlert(true);
                }
                this.mTipVisible = true;
            }
            int i = this.mTipShowInterval;
            if (i > 0) {
                this.mTipShowInterval = i - 1;
            }
        }
    }

    @Override // com.android.zxing.Decoder
    public void init(int i) {
        reset();
        this.mCameraId = i;
        ((Decoder) this).mSubjects.onNext(new PreviewImage(1, i));
        DataRepository.dataItemRunning().setHandGestureRunning(true);
    }

    @Override // com.android.zxing.Decoder
    public boolean needPreviewFrame() {
        return CameraSettings.isHandGestureOpen() && super.needPreviewFrame();
    }

    @Override // com.android.zxing.Decoder
    public void onPreviewFrame(PreviewImage previewImage) {
        PublishSubject<PreviewImage> publishSubject = ((Decoder) this).mSubjects;
        if (publishSubject != null) {
            publishSubject.onNext(previewImage);
        }
    }

    @Override // com.android.zxing.Decoder
    public void quit() {
        super.quit();
        ((Decoder) this).mSubjects.onNext(new PreviewImage(3, this.mCameraId));
        ((Decoder) this).mSubjects.onComplete();
    }

    @Override // com.android.zxing.Decoder
    public void reset() {
        Log.d(TAG, "Reset");
        ((Decoder) this).mDecodingCount.set(0);
        this.mTriggeringPhoto = false;
    }

    @Override // com.android.zxing.Decoder
    public void startDecode() {
        ((Decoder) this).mDecoding = true;
        ((Decoder) this).mDecodingCount.set(0);
    }
}
