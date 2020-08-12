package com.android.camera.panorama;

import android.hardware.SensorEvent;
import com.android.camera.Util;

public class GyroscopeRoundDetector extends RoundDetector {
    private static final float NS2S = 1.0E-9f;
    private float mLastTimestamp;
    private float mRadianLandscape;
    private float mRadianPortrait;
    private float mTargetDegree = 360.0f;
    private boolean mUseSensor;

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001c, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0031, code lost:
        return r2;
     */
    @Override // com.android.camera.panorama.RoundDetector
    public boolean detect() {
        synchronized (RoundDetector.SynchronizedObject) {
            boolean z = false;
            if (!((RoundDetector) this).mIsEndOk) {
                return false;
            }
            if (((RoundDetector) this).mDirection == 0) {
                if (this.mTargetDegree <= ((float) currentDegree())) {
                    z = true;
                }
            } else if (((double) currentDegree()) <= 360.0d - ((double) this.mTargetDegree)) {
                z = true;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00b5, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00a3  */
    @Override // com.android.camera.panorama.RoundDetector
    public void onSensorChanged(SensorEvent sensorEvent) {
        boolean z;
        if (sensorEvent.sensor.getType() == 4 || sensorEvent.sensor.getType() == 16) {
            synchronized (RoundDetector.SynchronizedObject) {
                if (this.mUseSensor) {
                    boolean z2 = false;
                    if (!Util.isEqualsZero((double) this.mLastTimestamp)) {
                        float f2 = (((float) sensorEvent.timestamp) - this.mLastTimestamp) * NS2S;
                        float f3 = sensorEvent.values[0];
                        float f4 = sensorEvent.values[1];
                        this.mRadianLandscape += f3 * f2;
                        this.mRadianPortrait += f4 * f2;
                    }
                    this.mLastTimestamp = (float) sensorEvent.timestamp;
                    int radianToDegree = RoundDetector.radianToDegree(this.mRadianLandscape);
                    int radianToDegree2 = RoundDetector.radianToDegree(this.mRadianPortrait);
                    if (radianToDegree <= 0 && ((RoundDetector) this).mDirection == 1) {
                        radianToDegree += 360;
                    }
                    if (radianToDegree2 <= 0 && ((RoundDetector) this).mDirection == 1) {
                        radianToDegree2 += 360;
                    }
                    int i = ((RoundDetector) this).isLandscape ? radianToDegree : radianToDegree2;
                    int i2 = ((RoundDetector) this).isLandscape ? ((RoundDetector) this).mCurrentDegreeLandscape : ((RoundDetector) this).mCurrentDegreePortrait;
                    if (((RoundDetector) this).mDirection != 0) {
                        if (i2 != 0) {
                            if (i2 >= i && Math.abs(i - i2) < 180) {
                            }
                        }
                        z = true;
                        if (z) {
                            ((RoundDetector) this).mCurrentDegreeLandscape = radianToDegree;
                            ((RoundDetector) this).mCurrentDegreePortrait = radianToDegree2;
                        }
                        if (!((RoundDetector) this).mIsEndOk) {
                            if (180 <= currentDegree() && currentDegree() <= 190) {
                                z2 = true;
                            }
                            ((RoundDetector) this).mIsEndOk = z2;
                        }
                    } else {
                        if (i2 != 0) {
                            if (i2 <= i && Math.abs(i - i2) < 180) {
                            }
                        }
                        z = true;
                        if (z) {
                        }
                        if (!((RoundDetector) this).mIsEndOk) {
                        }
                    }
                    z = false;
                    if (z) {
                    }
                    if (!((RoundDetector) this).mIsEndOk) {
                    }
                }
            }
        }
    }

    @Override // com.android.camera.panorama.RoundDetector
    public void setStartPosition(int i, int i2, float f2, float f3, boolean z) {
        ((RoundDetector) this).isLandscape = i == 0 || i == 180;
        synchronized (RoundDetector.SynchronizedObject) {
            this.mLastTimestamp = 0.0f;
            this.mRadianLandscape = 0.0f;
            this.mRadianPortrait = 0.0f;
            ((RoundDetector) this).mCurrentDegreeLandscape = 0;
            ((RoundDetector) this).mCurrentDegreePortrait = 0;
            ((RoundDetector) this).mDirection = i2;
            ((RoundDetector) this).mIsEndOk = false;
            this.mUseSensor = true;
            if (!z) {
                if (((RoundDetector) this).isLandscape) {
                    this.mTargetDegree = (360.0f - f2) + 20.0f;
                } else {
                    this.mTargetDegree = (360.0f - f3) + 20.0f;
                }
            }
        }
    }

    @Override // com.android.camera.panorama.RoundDetector
    public void stop() {
        synchronized (RoundDetector.SynchronizedObject) {
            this.mUseSensor = false;
        }
    }
}
