package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import com.android.camera.CameraSettings;
import com.android.camera.log.Log;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseBeautyBodySlimCount implements Function<CaptureResult, CaptureResult> {
    private static final String TAG = "FunctionParseBeautyBodySlimCount";
    public static final long TIP_INTERVAL_TIME = 10000;
    public static final long TIP_TIME = 4000;
    private final WeakReference<Camera2Proxy.BeautyBodySlimCountCallback> mCallbackRef;
    private final boolean mIsSupportBeautyBody = CameraSettings.isSupportBeautyBody();
    private long mTipHideTime;
    private long mTipShowTime;
    private boolean mTipStatus;

    public FunctionParseBeautyBodySlimCount(Camera2Proxy.BeautyBodySlimCountCallback beautyBodySlimCountCallback) {
        this.mCallbackRef = new WeakReference<>(beautyBodySlimCountCallback);
    }

    public CaptureResult apply(CaptureResult captureResult) throws Exception {
        Camera2Proxy.BeautyBodySlimCountCallback beautyBodySlimCountCallback;
        if (!this.mIsSupportBeautyBody || (beautyBodySlimCountCallback = this.mCallbackRef.get()) == null) {
            return captureResult;
        }
        boolean z = false;
        if (this.mTipStatus && System.currentTimeMillis() - this.mTipShowTime > TIP_TIME) {
            this.mTipStatus = false;
            this.mTipHideTime = System.currentTimeMillis();
        }
        if (!beautyBodySlimCountCallback.isBeautyBodySlimCountDetectStarted()) {
            if (this.mTipStatus) {
                beautyBodySlimCountCallback.onBeautyBodySlimCountChange(false);
                this.mTipStatus = false;
                this.mTipHideTime = 0;
            }
            return captureResult;
        }
        int beautyBodySlimCountResult = CaptureResultParser.getBeautyBodySlimCountResult(captureResult);
        if (beautyBodySlimCountResult == -1 || System.currentTimeMillis() - this.mTipHideTime < TIP_INTERVAL_TIME) {
            return captureResult;
        }
        if (beautyBodySlimCountResult != 1) {
            z = true;
        }
        if (this.mTipStatus == z) {
            return captureResult;
        }
        this.mTipStatus = z;
        Log.d(TAG, "Beauty body slim count:" + beautyBodySlimCountResult);
        beautyBodySlimCountCallback.onBeautyBodySlimCountChange(z);
        if (z) {
            this.mTipShowTime = System.currentTimeMillis();
        } else {
            this.mTipHideTime = System.currentTimeMillis();
        }
        return captureResult;
    }
}
