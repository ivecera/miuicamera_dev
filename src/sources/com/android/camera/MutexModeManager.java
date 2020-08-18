package com.android.camera;

import android.support.annotation.MainThread;
import com.android.camera.statistic.MistatsConstants;
import com.mi.config.b;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MutexModeManager {
    public static final int MUTEX_AOHDR = 2;
    public static final int MUTEX_BURST_SHOOT = 7;
    public static final int MUTEX_GROUP = 9;
    public static final int MUTEX_HAND_NIGHT = 3;
    public static final int MUTEX_MORPHO_HDR = 1;
    public static final int MUTEX_NONE = 0;
    public static final int MUTEX_RAW = 4;
    public static final int MUTEX_SCENE_HDR = 5;
    public static final int MUTEX_STEREO = 8;
    public static final int MUTEX_SUPER_RESOLUTION = 10;
    public static final int MUTEX_UBI_FOCUS = 6;
    private int mCurrentMutexMode = 0;
    private boolean mIsMandatory;
    private MutexCallBack mMutexCallBack;

    public interface MutexCallBack {
        void enterMutexMode(int i);

        void exitMutexMode(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MutexMode {
    }

    public MutexModeManager(MutexCallBack mutexCallBack) {
        this.mMutexCallBack = mutexCallBack;
    }

    private void enter(int i) {
        MutexCallBack mutexCallBack;
        if (i != 0 && (mutexCallBack = this.mMutexCallBack) != null && this.mCurrentMutexMode != i) {
            this.mCurrentMutexMode = i;
            mutexCallBack.enterMutexMode(i);
        }
    }

    private void exit(int i) {
        MutexCallBack mutexCallBack;
        if (i != 0 && (mutexCallBack = this.mMutexCallBack) != null) {
            this.mCurrentMutexMode = 0;
            mutexCallBack.exitMutexMode(i);
        }
    }

    private void switchMutexMode(int i, int i2) {
        if (i != i2) {
            exit(i);
            enter(i2);
        }
    }

    public void clearMandatoryFlag() {
        this.mIsMandatory = false;
    }

    public String getAlgorithmName() {
        int i = this.mCurrentMutexMode;
        return i != 1 ? i != 2 ? i != 3 ? i != 5 ? "" : "HDR" : MistatsConstants.FeatureName.VALUE_HHT : "AO_HDR" : "HDR";
    }

    public int getMutexMode() {
        return this.mCurrentMutexMode;
    }

    public String getSuffix() {
        if (this.mCurrentMutexMode == 4) {
            return "_RAW";
        }
        if (b.Rk() != 1 && Util.isForceNameSuffix() != 1) {
            return "";
        }
        int i = this.mCurrentMutexMode;
        return i != 1 ? i != 2 ? i != 3 ? i != 5 ? "" : "_HDR" : "_HHT" : "_AO_HDR" : "_HDR";
    }

    public boolean inMandatoryMode() {
        return this.mIsMandatory;
    }

    public boolean isAoHdr() {
        return this.mCurrentMutexMode == 2 ? true : false;
    }

    public boolean isBurstShoot() {
        return this.mCurrentMutexMode == 7 ? true : false;
    }

    public boolean isGroupShot() {
        return this.mCurrentMutexMode == 9 ? true : false;
    }

    public boolean isHandNight() {
        return this.mCurrentMutexMode == 3 ? true : false;
    }

    public boolean isHdr() {
        int i = this.mCurrentMutexMode;
        return (i == 2 || i == 1 || i == 5) ? true : false;
    }

    public boolean isMorphoHdr() {
        return this.mCurrentMutexMode == 1 ? true : false;
    }

    public boolean isNeedComposed() {
        int i = this.mCurrentMutexMode;
        return (i == 0 || i == 2 || i == 7) ? false : true;
    }

    public boolean isNormal() {
        return this.mCurrentMutexMode == 0 ? true : false;
    }

    public boolean isRAW() {
        return this.mCurrentMutexMode == 4 ? true : false;
    }

    public boolean isSceneHdr() {
        return this.mCurrentMutexMode == 5 ? true : false;
    }

    public boolean isSuperResolution() {
        return this.mCurrentMutexMode == 10 ? true : false;
    }

    public boolean isSupportedFlashOn() {
        int i = this.mCurrentMutexMode;
        return (i == 0 || i == 4 || i == 3) ? true : false;
    }

    public boolean isSupportedTorch() {
        int i;
        return (!b.lm() || !((i = this.mCurrentMutexMode) == 0 || i == 2 || i == 7 || i == 9 || i == 10)) ? false : true;
    }

    public boolean isUbiFocus() {
        return this.mCurrentMutexMode == 6 ? true : false;
    }

    @MainThread
    public void resetMutexMode() {
        this.mIsMandatory = false;
        switchMutexMode(this.mCurrentMutexMode, 0);
    }

    @MainThread
    public void setMutexMode(int i) {
        this.mIsMandatory = false;
        switchMutexMode(this.mCurrentMutexMode, i);
    }

    public void setMutexModeMandatory(int i) {
        this.mIsMandatory = true;
        switchMutexMode(this.mCurrentMutexMode, i);
    }
}
