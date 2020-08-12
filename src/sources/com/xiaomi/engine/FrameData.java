package com.xiaomi.engine;

import android.media.Image;
import android.os.Parcelable;
import android.util.Log;

public class FrameData {
    private static final String TAG = "FrameData";
    private Image mBufferImage;
    private Parcelable mCaptureRequestMetadataNative;
    private Parcelable mCaptureResultMetadataNative;
    private long mFrameNumber;
    private FrameStatusCallback mFrameStatusCallback;
    private int mImageFlag;
    private int mSequenceId;

    public interface FrameStatusCallback {
        void onFrameImageClosed(Image image);
    }

    public FrameData(int i, int i2, long j, Parcelable parcelable, Image image) {
        this.mImageFlag = i;
        this.mSequenceId = i2;
        this.mFrameNumber = j;
        this.mCaptureResultMetadataNative = parcelable;
        this.mBufferImage = image;
    }

    public FrameData(int i, int i2, long j, Parcelable parcelable, Parcelable parcelable2, Image image) {
        this.mImageFlag = i;
        this.mSequenceId = i2;
        this.mFrameNumber = j;
        this.mCaptureResultMetadataNative = parcelable;
        this.mCaptureRequestMetadataNative = parcelable2;
        this.mBufferImage = image;
    }

    public Image getBufferImage() {
        return this.mBufferImage;
    }

    public Parcelable getCaptureRequestMetaDataNative() {
        return this.mCaptureRequestMetadataNative;
    }

    public Parcelable getCaptureResultMetaDataNative() {
        return this.mCaptureResultMetadataNative;
    }

    public FrameStatusCallback getFrameCallback() {
        return this.mFrameStatusCallback;
    }

    public long getFrameNumber() {
        return this.mFrameNumber;
    }

    public int getImageFlag() {
        return this.mImageFlag;
    }

    public int getSequenceId() {
        return this.mSequenceId;
    }

    public void release() {
        if (this.mBufferImage != null) {
            String str = TAG;
            Log.d(str, "release: close Image: " + this.mBufferImage);
            this.mBufferImage.close();
            FrameStatusCallback frameStatusCallback = this.mFrameStatusCallback;
            if (frameStatusCallback != null) {
                frameStatusCallback.onFrameImageClosed(this.mBufferImage);
            }
        }
    }

    public void setBufferImage(Image image) {
        this.mBufferImage = image;
    }

    public void setCaptureRequestMetaDataNative(Parcelable parcelable) {
        this.mCaptureRequestMetadataNative = parcelable;
    }

    public void setCaptureResultMetaDataNative(Parcelable parcelable) {
        this.mCaptureResultMetadataNative = parcelable;
    }

    public void setFrameCallback(FrameStatusCallback frameStatusCallback) {
        this.mFrameStatusCallback = frameStatusCallback;
    }

    public void setFrameNumber(long j) {
        this.mFrameNumber = j;
    }

    public void setImageFlag(int i) {
        this.mImageFlag = i;
    }

    public void setSequenceId(int i) {
        this.mSequenceId = i;
    }

    public String toString() {
        return "FrameData{ mImageFlag=" + this.mImageFlag + ", mFrameNumber=" + this.mFrameNumber + ", mCaptureResultMetadataNative=" + this.mCaptureResultMetadataNative + ", mCaptureRequestMetadataNative=" + this.mCaptureRequestMetadataNative + ", mBufferImage=" + this.mBufferImage + '}';
    }
}
