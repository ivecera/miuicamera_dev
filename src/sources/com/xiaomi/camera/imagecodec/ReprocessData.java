package com.xiaomi.camera.imagecodec;

import android.media.Image;
import android.support.annotation.NonNull;
import com.xiaomi.protocol.ICustomCaptureResult;

public class ReprocessData {
    private static final int DEFAULT_JPEG_QUALITY = 100;
    private String mImageTag;
    private boolean mIsFrontCamera;
    private boolean mIsFrontMirror;
    private boolean mIsPoolImage;
    private int mJpegQuality;
    private int mOutputFormat;
    private int mOutputHeight;
    private int mOutputWidth;
    private OnDataAvailableListener mResultListener;
    private ICustomCaptureResult mTotalCaptureResult;
    private Image mYuvImage;

    public interface OnDataAvailableListener {
        void onError(String str, String str2);

        void onJpegAvailable(byte[] bArr, byte[] bArr2, String str);

        void onYuvAvailable(Image image, String str);
    }

    public ReprocessData(@NonNull Image image, @NonNull String str, @NonNull ICustomCaptureResult iCustomCaptureResult, boolean z, int i, int i2, int i3, @NonNull OnDataAvailableListener onDataAvailableListener) {
        this.mYuvImage = image;
        this.mImageTag = str;
        this.mTotalCaptureResult = iCustomCaptureResult;
        this.mIsFrontCamera = z;
        this.mOutputWidth = i == 0 ? image.getWidth() : i;
        this.mOutputHeight = i2 == 0 ? image.getHeight() : i2;
        this.mOutputFormat = i3;
        this.mResultListener = onDataAvailableListener;
        this.mJpegQuality = 100;
    }

    public String getImageTag() {
        return this.mImageTag;
    }

    public int getJpegQuality() {
        return this.mJpegQuality;
    }

    public int getOutputFormat() {
        return this.mOutputFormat;
    }

    public int getOutputHeight() {
        return this.mOutputHeight;
    }

    public int getOutputWidth() {
        return this.mOutputWidth;
    }

    public OnDataAvailableListener getResultListener() {
        return this.mResultListener;
    }

    public ICustomCaptureResult getTotalCaptureResult() {
        return this.mTotalCaptureResult;
    }

    public Image getYuvImage() {
        return this.mYuvImage;
    }

    public boolean isFrontCamera() {
        return this.mIsFrontCamera;
    }

    public boolean isFrontMirror() {
        return this.mIsFrontMirror;
    }

    public boolean isImageFromPool() {
        return this.mIsPoolImage;
    }

    public void setFrontMirror(boolean z) {
        this.mIsFrontMirror = z;
    }

    public void setImageFromPool(boolean z) {
        this.mIsPoolImage = z;
    }

    public void setJpegQuality(int i) {
        if (i < 1 || i > 100) {
            this.mJpegQuality = 100;
        } else {
            this.mJpegQuality = i;
        }
    }

    public void setYuvImage(Image image) {
        this.mYuvImage = image;
    }
}
