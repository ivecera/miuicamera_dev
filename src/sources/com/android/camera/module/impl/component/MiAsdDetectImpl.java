package com.android.camera.module.impl.component;

import com.android.camera.ActivityBase;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.scene.MiAlgoAsdSceneProfile;
import com.android.camera.statistic.CameraStatUtils;
import java.util.Optional;

public class MiAsdDetectImpl implements ModeProtocol.MiAsdDetect {
    private static final String TAG = "MiAsdDetectImpl";
    private ActivityBase mActivity;

    public MiAsdDetectImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
    }

    static /* synthetic */ void a(Camera2Module camera2Module, boolean z, int i) {
        if (camera2Module.isGoogleLensAvailable()) {
            camera2Module.setIsUltraWideConflict(z);
            camera2Module.showOrHideChip(!z);
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            boolean isZoomRatioBetweenUltraAndWide = camera2Module.isZoomRatioBetweenUltraAndWide();
            int i2 = R.string.ultra_wide_recommend_tip_hint_sat;
            if (isZoomRatioBetweenUltraAndWide && (bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint) || bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint_sat))) {
                bottomPopupTips.directlyHideTips();
            } else if (z) {
                ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                if (!(dualController != null && dualController.isSlideVisible()) && camera2Module.getZoomRatio() <= 1.0f && 171 != camera2Module.getModuleIndex()) {
                    if (i == 2) {
                        CameraStatUtils.trackAISceneChanged(camera2Module.getModuleIndex(), 36, camera2Module.getResources());
                    }
                    MiAlgoAsdSceneProfile.setTipEnable(MiAlgoAsdSceneProfile.COMPAT_ULTRA_WIDE, true);
                    if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                        i2 = R.string.ultra_wide_recommend_tip_hint;
                    }
                    bottomPopupTips.showTips(14, i2, 7);
                }
            } else if (bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint) || bottomPopupTips.containTips(R.string.ultra_wide_recommend_tip_hint_sat)) {
                bottomPopupTips.directlyHideTips();
            }
        }
    }

    public static ModeProtocol.BaseProtocol create(ActivityBase activityBase) {
        return new MiAsdDetectImpl(activityBase);
    }

    private Optional<BaseModule> getBaseModule() {
        ActivityBase activityBase = this.mActivity;
        return activityBase == null ? Optional.empty() : Optional.ofNullable((BaseModule) activityBase.getCurrentModule());
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(235, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(235, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiAsdDetect
    public void updateUltraWide(boolean z, int i) {
        BaseModule baseModule = getBaseModule().get();
        if (baseModule != null && (baseModule instanceof Camera2Module)) {
            Camera2Module camera2Module = (Camera2Module) baseModule;
            camera2Module.getHandler().post(new u(camera2Module, z, i));
        }
    }
}
