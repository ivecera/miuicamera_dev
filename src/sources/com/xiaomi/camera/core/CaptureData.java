package com.xiaomi.camera.core;

import android.media.Image;
import android.support.annotation.NonNull;
import com.android.camera.log.Log;
import com.xiaomi.camera.core.ParallelDataZipper;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CaptureData {
    /* access modifiers changed from: private */
    public static final String TAG = "CaptureData";
    private int mAlgoType;
    private int mAlreadyDataNum = 0;
    private int mBurstNum;
    private List<CaptureDataBean> mCaptureDataBeanList = new ArrayList();
    private CaptureDataListener mCaptureDataListener;
    private long mCaptureTimestamp;
    private boolean mCapturedByFrontCamera;
    private ParallelDataZipper.DataListener mDataListener;
    private ImageProcessor mImageProcessor;
    private boolean mIsAbandoned;
    private boolean mIsMoonMode;
    private CaptureDataBean mMultiFrameProcessResult;
    private boolean mSaveInputImage;
    private int mStreamNum;

    public static class CaptureDataBean {
        private boolean mIsFirstResult;
        private Image mMainImage;
        private ICustomCaptureResult mResult;
        private int mStreamNum;
        private Image mSubImage;

        public CaptureDataBean(int i) {
            this.mStreamNum = i;
        }

        public void close() {
            Image image = this.mMainImage;
            if (image != null) {
                image.close();
                this.mMainImage = null;
            }
            Image image2 = this.mSubImage;
            if (image2 != null) {
                image2.close();
                this.mSubImage = null;
            }
        }

        public Image getMainImage() {
            return this.mMainImage;
        }

        public ICustomCaptureResult getResult() {
            return this.mResult;
        }

        public int getStreamNum() {
            return this.mStreamNum;
        }

        public Image getSubImage() {
            return this.mSubImage;
        }

        public boolean isDataReady() {
            int i = this.mStreamNum;
            return 2 == i ? (this.mResult == null || this.mMainImage == null || this.mSubImage == null) ? false : true : (1 != i || this.mResult == null || this.mMainImage == null) ? false : true;
        }

        public boolean isFirstResult() {
            return this.mIsFirstResult;
        }

        public void setCaptureResult(ICustomCaptureResult iCustomCaptureResult, boolean z) {
            this.mResult = iCustomCaptureResult;
            this.mIsFirstResult = z;
        }

        public void setImage(Image image, int i) {
            if (i == 0) {
                this.mMainImage = image;
            } else if (i == 1) {
                this.mSubImage = image;
            } else {
                String access$000 = CaptureData.TAG;
                Log.e(access$000, "setImage: unknown imageFlag: " + i);
            }
        }

        public void setMainImage(Image image) {
            this.mMainImage = image;
        }

        public void setStreamNum(int i) {
            this.mStreamNum = i;
        }
    }

    public CaptureData(int i, int i2, int i3, long j, boolean z, @NonNull ImageProcessor imageProcessor) {
        this.mAlgoType = i;
        this.mStreamNum = i2;
        this.mBurstNum = i3;
        this.mCaptureTimestamp = j;
        this.mIsAbandoned = z;
        this.mImageProcessor = imageProcessor;
    }

    public int getAlgoType() {
        return this.mAlgoType;
    }

    public int getBurstNum() {
        return this.mBurstNum;
    }

    public List<CaptureDataBean> getCaptureDataBeanList() {
        return this.mCaptureDataBeanList;
    }

    public CaptureDataListener getCaptureDataListener() {
        return this.mCaptureDataListener;
    }

    public long getCaptureTimestamp() {
        return this.mCaptureTimestamp;
    }

    public ParallelDataZipper.DataListener getDataListener() {
        return this.mDataListener;
    }

    public ImageProcessor getImageProcessor() {
        return this.mImageProcessor;
    }

    public CaptureDataBean getMultiFrameProcessResult() {
        return this.mMultiFrameProcessResult;
    }

    public int getStreamNum() {
        return this.mStreamNum;
    }

    public boolean isAbandoned() {
        return this.mIsAbandoned;
    }

    public boolean isCapturedByFrontCamera() {
        return this.mCapturedByFrontCamera;
    }

    public boolean isDataReady() {
        return this.mAlreadyDataNum == this.mBurstNum;
    }

    public boolean isMoonMode() {
        return this.mIsMoonMode;
    }

    public boolean isSaveInputImage() {
        return this.mSaveInputImage;
    }

    public void putCaptureDataBean(CaptureDataBean captureDataBean) {
        this.mCaptureDataBeanList.add(captureDataBean);
        this.mAlreadyDataNum++;
    }

    public void setCapturedByFrontCamera(boolean z) {
        this.mCapturedByFrontCamera = z;
    }

    public void setDataListener(ParallelDataZipper.DataListener dataListener) {
        this.mDataListener = dataListener;
    }

    public void setMoonMode(boolean z) {
        this.mIsMoonMode = z;
    }

    public void setMultiFrameProcessListener(CaptureDataListener captureDataListener) {
        this.mCaptureDataListener = captureDataListener;
    }

    public void setMultiFrameProcessResult(CaptureDataBean captureDataBean) {
        this.mMultiFrameProcessResult = captureDataBean;
    }

    public void setSaveInputImage(boolean z) {
        this.mSaveInputImage = z;
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "CaptureData{mAlgoType=%d, mStreamNum=%d, mBurstNum=%d, mCaptureTimestamp=%d, mIsAbandoned=%b}", Integer.valueOf(this.mAlgoType), Integer.valueOf(this.mStreamNum), Integer.valueOf(this.mBurstNum), Long.valueOf(this.mCaptureTimestamp), Boolean.valueOf(this.mIsAbandoned));
    }
}
