package com.android.camera.scene;

import android.support.annotation.StringRes;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.module.BaseModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import java.lang.ref.WeakReference;

public abstract class ASDResultParse implements IResultParse<MarshalQueryableASDScene.ASDScene[]> {
    private WeakReference<ModeProtocol.DualController> mDualController;
    private boolean mIsMacroModeEnable;
    protected final WeakReference<BaseModule> mModule;
    private WeakReference<ModeProtocol.BottomPopupTips> mTips;
    private WeakReference<ModeProtocol.TopAlert> mTopAlert;

    public ASDResultParse(WeakReference<BaseModule> weakReference) {
        this.mModule = weakReference;
        WeakReference<BaseModule> weakReference2 = this.mModule;
        if (weakReference2 != null && weakReference2.get() != null) {
            this.mIsMacroModeEnable = CameraSettings.isMacroModeEnabled(this.mModule.get().getModuleIndex());
        }
    }

    private ModeProtocol.DualController getDualController() {
        WeakReference<ModeProtocol.DualController> weakReference = this.mDualController;
        if (weakReference == null || weakReference.get() == null) {
            this.mDualController = new WeakReference<>((ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182));
        }
        return this.mDualController.get();
    }

    private ModeProtocol.BottomPopupTips getTips() {
        WeakReference<ModeProtocol.BottomPopupTips> weakReference = this.mTips;
        if (weakReference == null || weakReference.get() == null) {
            this.mTips = new WeakReference<>((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175));
        }
        return this.mTips.get();
    }

    public /* synthetic */ void a(int i, int i2, int i3) {
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
        if (cameraModuleSpecial != null) {
            cameraModuleSpecial.showOrHideChip(false);
        }
        getTips().showTips(i, i2, i3);
    }

    /* access modifiers changed from: protected */
    public boolean closeTip() {
        BaseModule baseModule;
        if (getTips() == null || (baseModule = this.mModule.get()) == null) {
            return false;
        }
        baseModule.getHandler().post(new a(this));
        return true;
    }

    public /* synthetic */ void eg() {
        getTips().directlyHideTips();
    }

    /* access modifiers changed from: protected */
    public ModeProtocol.TopAlert getTopAlert() {
        WeakReference<ModeProtocol.TopAlert> weakReference = this.mTopAlert;
        if (weakReference == null || weakReference.get() == null) {
            this.mTopAlert = new WeakReference<>((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172));
        }
        return this.mTopAlert.get();
    }

    /* access modifiers changed from: protected */
    public boolean isGeneralInterception() {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips == null || !bottomPopupTips.isQRTipVisible()) {
            return this.mIsMacroModeEnable;
        }
        FunctionMiAlgoASDEngine.LOGD("QR tip is visible!");
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isSuggestionIntercept(MarshalQueryableASDScene.ASDScene aSDScene) {
        BaseModule baseModule = this.mModule.get();
        if (baseModule == null || isGeneralInterception()) {
            return true;
        }
        if (baseModule.getBogusCameraId() != 0) {
            FunctionMiAlgoASDEngine.LOGD("no back camera!");
            return true;
        } else if (1.0f != baseModule.getZoomRatio()) {
            FunctionMiAlgoASDEngine.LOGD("zoom > 1x!");
            return true;
        } else if (!MiAlgoAsdSceneProfile.isAlreadyTip() || !MiAlgoAsdSceneProfile.isCheckSceneEnable(aSDScene.type, aSDScene.value)) {
            if (getTopAlert() != null) {
                if (getTopAlert().isCurrentRecommendTipText(baseModule.isFrontCamera() ? R.string.lens_dirty_detected_title_front : R.string.lens_dirty_detected_title_back)) {
                    FunctionMiAlgoASDEngine.LOGD("dirty tip is visible!");
                    return true;
                }
            }
            if (!(getDualController() != null && getDualController().isSlideVisible())) {
                return false;
            }
            FunctionMiAlgoASDEngine.LOGD("Zoom bar is in effect, no prompt！");
            return true;
        } else {
            FunctionMiAlgoASDEngine.LOGD("A tip has occurred this time.!");
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public boolean showTip(int i, @StringRes int i2, int i3) {
        BaseModule baseModule;
        if (i2 <= 0 || getTips() == null || (baseModule = this.mModule.get()) == null) {
            return false;
        }
        baseModule.getHandler().post(new b(this, i, i2, i3));
        return true;
    }
}
