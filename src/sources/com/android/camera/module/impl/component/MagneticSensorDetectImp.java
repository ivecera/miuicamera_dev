package com.android.camera.module.impl.component;

import android.hardware.SensorEvent;
import android.hardware.camera2.CaptureResult;
import android.util.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.CaptureResultParser;

public class MagneticSensorDetectImp implements ModeProtocol.MagneticSensorDetect {
    private static final boolean DEBUG = false;
    private static final int MAGNETIC_DELTA = 15;
    private static final int MAGNETIC_DETECT_TIME_OUT = 60000;
    private static final String TAG = "MagneticSensorDetectImp";
    private float mAECLux;
    private long mCostTime = 0;
    private long mLastCaptureTime = 0;
    private boolean mMagneticChanged = true;
    private float[] mMagneticValues = new float[3];
    private float[] mPostMagneticValues = new float[3];

    public MagneticSensorDetectImp() {
        resetMagneticInfo();
        Log.d(TAG, "init MagneticSensorDetectImp");
    }

    public static ModeProtocol.BaseProtocol create() {
        return new MagneticSensorDetectImp();
    }

    private void printMagnetInfo() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.MagneticSensorDetect
    public boolean isLockHDRChecker() {
        boolean z = !this.mMagneticChanged;
        Log.e(TAG, "(isLockHDRChecker)is_lock_hdr:" + z);
        return z;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MagneticSensorDetect
    public void onMagneticSensorChanged(SensorEvent sensorEvent) {
        for (int i = 0; i < 3; i++) {
            this.mMagneticValues[i] = sensorEvent.values[i];
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MagneticSensorDetect
    public void recordMagneticInfo() {
        System.arraycopy(this.mMagneticValues, 0, this.mPostMagneticValues, 0, 3);
        this.mLastCaptureTime = System.currentTimeMillis();
        this.mCostTime = this.mLastCaptureTime;
        this.mMagneticChanged = false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(2576, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MagneticSensorDetect
    public void resetMagneticInfo() {
        if (this.mLastCaptureTime > 0) {
            for (int i = 0; i < 3; i++) {
                this.mPostMagneticValues[i] = 0.0f;
            }
            this.mLastCaptureTime = 0;
            this.mCostTime = 0;
            this.mMagneticChanged = true;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(2576, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MagneticSensorDetect
    public void updateMagneticDetection() {
        printMagnetInfo();
        if (this.mLastCaptureTime > 0) {
            long currentTimeMillis = System.currentTimeMillis();
            float f2 = this.mAECLux;
            boolean z = f2 >= 1.0f && f2 <= 1000.0f;
            if (Math.abs(currentTimeMillis - this.mLastCaptureTime) > 60000) {
                z = false;
            }
            if (z) {
                float abs = Math.abs(this.mPostMagneticValues[0] - this.mMagneticValues[0]) + Math.abs(this.mPostMagneticValues[1] - this.mMagneticValues[1]) + Math.abs(this.mPostMagneticValues[2] - this.mMagneticValues[2]);
                if (currentTimeMillis >= this.mCostTime || abs >= 15.0f) {
                    this.mCostTime += 500;
                }
                if (abs < 15.0f) {
                    this.mMagneticChanged = false;
                } else {
                    resetMagneticInfo();
                }
            } else {
                resetMagneticInfo();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MagneticSensorDetect
    public void updatePreview(CaptureResult captureResult) {
        if (this.mLastCaptureTime > 0) {
            this.mAECLux = CaptureResultParser.getAecLux(captureResult);
        }
    }
}
