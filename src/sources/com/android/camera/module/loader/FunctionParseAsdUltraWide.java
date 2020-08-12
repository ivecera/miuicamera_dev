package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.scene.MiAlgoAsdSceneProfile;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseAsdUltraWide implements Function<CaptureResult, CaptureResult> {
    private static final int[] ULTRA_WIDE_RECOM = {2, 1};
    public static final int ULTRA_WIDE_SIDEFACE_TYPE = 2;
    public static final int ULTRA_WIDE_TOWER_BUILDING_TYPE = 1;
    private boolean mEnable;
    private boolean mIsOpenUltraWide;
    private ModeProtocol.MiAsdDetect mMiAsdDetectProtocol;
    private WeakReference<BaseModule> mModule;

    public FunctionParseAsdUltraWide(int i, BaseModule baseModule, float f2) {
        this.mEnable = DataRepository.dataItemFeature().isSupportUltraWide() && (i == 163 || i == 165) && !CameraSettings.isUltraWideConfigOpen(i) && !CameraSettings.isUltraPixelOn() && !CameraSettings.isMacroModeEnabled(i) && f2 < 3.0f;
        if (this.mEnable) {
            this.mModule = new WeakReference<>(baseModule);
        }
    }

    private boolean isOpenUltraWide(int i) {
        int i2 = 0;
        boolean z = false;
        while (true) {
            int[] iArr = ULTRA_WIDE_RECOM;
            if (i2 >= iArr.length) {
                return z;
            }
            z = iArr[i2] == i;
            if (z) {
                return z;
            }
            i2++;
        }
    }

    public CaptureResult apply(CaptureResult captureResult) throws Exception {
        BaseModule baseModule;
        if (!this.mEnable || (baseModule = this.mModule.get()) == null) {
            return captureResult;
        }
        if (this.mMiAsdDetectProtocol == null) {
            this.mMiAsdDetectProtocol = (ModeProtocol.MiAsdDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(235);
        }
        int ultraWideDetectedResult = CaptureResultParser.getUltraWideDetectedResult(captureResult);
        boolean isOpenUltraWide = isOpenUltraWide(ultraWideDetectedResult);
        if (((Camera2Module) baseModule).isZoomRatioBetweenUltraAndWide()) {
            isOpenUltraWide = false;
        }
        if (this.mIsOpenUltraWide == isOpenUltraWide) {
            return captureResult;
        }
        this.mIsOpenUltraWide = isOpenUltraWide;
        if (this.mMiAsdDetectProtocol == null || MiAlgoAsdSceneProfile.isAlreadyTip()) {
            return captureResult;
        }
        this.mMiAsdDetectProtocol.updateUltraWide(isOpenUltraWide, ultraWideDetectedResult);
        return captureResult;
    }
}
