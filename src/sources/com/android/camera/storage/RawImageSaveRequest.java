package com.android.camera.storage;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import com.xiaomi.camera.base.PerformanceTracker;

public class RawImageSaveRequest implements SaveRequest {
    private static final String TAG = "RawImageSaveRequest";
    private CaptureResult captureResult;
    private CameraCharacteristics characteristics;
    private Context context;
    private byte[] data;
    private long dateTaken;
    private int height;
    private int orientation;
    private SaverCallback saverCallback;
    private int size;
    private String title;
    private int width;

    public RawImageSaveRequest(byte[] bArr, CaptureResult captureResult2, CameraCharacteristics cameraCharacteristics, long j, String str, int i, int i2, int i3) {
        this.data = bArr;
        this.captureResult = captureResult2;
        this.characteristics = cameraCharacteristics;
        this.dateTaken = j;
        this.title = str;
        this.width = i;
        this.height = i2;
        this.size = bArr == null ? 0 : bArr.length;
        this.orientation = i3;
    }

    @Override // com.android.camera.storage.SaveRequest
    public int getSize() {
        return this.size;
    }

    @Override // com.android.camera.storage.SaveRequest
    public boolean isFinal() {
        return true;
    }

    @Override // com.android.camera.storage.SaveRequest
    public void onFinish() {
        this.data = null;
        this.saverCallback.onSaveFinish(getSize());
    }

    public void run() {
        save();
        PerformanceTracker.trackImageSaver(this.data, 1);
        onFinish();
    }

    @Override // com.android.camera.storage.SaveRequest
    public void save() {
        Storage.addRawImage(this.context, this.title, this.characteristics, this.captureResult, this.data, this.dateTaken, this.width, this.height, this.orientation);
        Storage.getAvailableSpace();
    }

    @Override // com.android.camera.storage.SaveRequest
    public void setContextAndCallback(Context context2, SaverCallback saverCallback2) {
        this.context = context2;
        this.saverCallback = saverCallback2;
    }
}
