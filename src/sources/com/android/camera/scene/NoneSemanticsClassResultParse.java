package com.android.camera.scene;

import android.content.res.Resources;
import com.android.camera.CameraAppImpl;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class NoneSemanticsClassResultParse extends ASDResultParse {
    private static final int SUPER_NIGHT_VALUE_MASK = 255;

    public NoneSemanticsClassResultParse(WeakReference<BaseModule> weakReference) {
        super(weakReference);
    }

    private void showSuperNightTip(int i, int i2) {
        int sceneTipResId = MiAlgoAsdSceneProfile.getSceneTipResId(i, i2);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (MiAlgoAsdSceneProfile.isTipEnable(i)) {
            try {
                String string = CameraAppImpl.getAndroidContext().getResources().getString(sceneTipResId);
                boolean z = false;
                if (bottomPopupTips != null && bottomPopupTips.isTipShowing() && string != null && string.equals(bottomPopupTips.getCurrentBottomTipMsg())) {
                    z = true;
                }
                if (z && !MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2)) {
                    closeTip();
                }
            } catch (Resources.NotFoundException unused) {
            }
        } else if (MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2) && sceneTipResId > 0) {
            if (showTip(14, sceneTipResId, 2)) {
                MiAlgoAsdSceneProfile.setTipEnable(i, true);
            }
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_DETECT_TIP, MistatsConstants.CaptureAttr.VALUE_ASD_SUPER_NIGHT_TIP);
            MistatsWrapper.mistatEvent(MistatsConstants.FeatureName.KEY_COMMON_TIPS, hashMap);
        }
    }

    private void updateASDScene(MarshalQueryableASDScene.ASDScene aSDScene) {
        int i = aSDScene.type;
        int i2 = aSDScene.value;
        if (i == 8) {
            updateASDSceneHDR(i, i2);
        }
    }

    private void updateASDSceneHDR(int i, int i2) {
        if (!MiAlgoAsdSceneProfile.isSceneChange(i, i2)) {
            boolean z = false;
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                z = topAlert.isHDRShowing();
            }
            if (z == MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2)) {
                return;
            }
        }
        WeakReference<BaseModule> weakReference = ((ASDResultParse) this).mModule;
        if (weakReference != null && weakReference.get() != null && (((ASDResultParse) this).mModule.get() instanceof Camera2Module)) {
            ((Camera2Module) ((ASDResultParse) this).mModule.get()).updateHDRTip(MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2));
        }
    }

    public void parseMiAlgoAsdResult(MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        if (aSDSceneArr != null && aSDSceneArr.length > 0) {
            FunctionMiAlgoASDEngine.LOGD("(NoneSemantics)scenes size:" + aSDSceneArr.length);
            for (MarshalQueryableASDScene.ASDScene aSDScene : aSDSceneArr) {
                FunctionMiAlgoASDEngine.LOGD("(NoneSemantics)-->" + aSDScene.toString());
                updateASDScene(aSDScene);
            }
        }
    }
}
