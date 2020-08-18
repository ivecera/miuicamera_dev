package com.android.camera.scene;

import android.content.res.Resources;
import android.os.Handler;
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
import java.util.Objects;

public class SemanticsClassResultParse extends ASDResultParse {
    public SemanticsClassResultParse(WeakReference<BaseModule> weakReference) {
        super(weakReference);
    }

    private void eventTrack(int i) {
        HashMap hashMap = new HashMap();
        if (i == 1) {
            hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_DETECT_TIP, MistatsConstants.CaptureAttr.VALUE_ASD_PORTRAIT_TIP);
        } else if (i == 2) {
            hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_DETECT_TIP, MistatsConstants.CaptureAttr.VALUE_ASD_BACKLIT_TIP);
            hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_DETECT_TIP, MistatsConstants.CaptureAttr.VALUE_ASD_PORTRAIT_TIP);
        } else if (i == 6) {
            hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_DETECT_TIP, MistatsConstants.CaptureAttr.VALUE_ASD_DIRTY_TIP);
        } else if (i == 7) {
            hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_DETECT_TIP, MistatsConstants.CaptureAttr.VALUE_ASD_MACRO_TIP);
        } else {
            return;
        }
        MistatsWrapper.mistatEvent(MistatsConstants.FeatureName.KEY_COMMON_TIPS, hashMap);
    }

    private void updateASDScene(MarshalQueryableASDScene.ASDScene aSDScene) {
        int i = aSDScene.type;
        int i2 = aSDScene.value;
        BaseModule baseModule = ((ASDResultParse) this).mModule.get();
        if (baseModule != null) {
            if (baseModule.getModuleIndex() == 163 || baseModule.getModuleIndex() == 165) {
                Camera2Module camera2Module = (Camera2Module) baseModule;
                int sceneTipResId = MiAlgoAsdSceneProfile.getSceneTipResId(i, i2);
                if (MiAlgoAsdSceneProfile.isSceneChange(i, i2)) {
                    ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    if (i != 6) {
                        if ((i != 7 && i != 9) || isSuggestionIntercept(aSDScene)) {
                            return;
                        }
                        if (MiAlgoAsdSceneProfile.isTipEnable(i)) {
                            Resources resources = CameraAppImpl.getAndroidContext().getResources();
                            String str = null;
                            if (sceneTipResId > 0) {
                                try {
                                    str = resources.getString(sceneTipResId);
                                } catch (Resources.NotFoundException unused) {
                                    return;
                                }
                            }
                            boolean z = false;
                            if (bottomPopupTips != null && bottomPopupTips.isTipShowing() && str != null && str.equals(bottomPopupTips.getCurrentBottomTipMsg())) {
                                z = true;
                            }
                            if (z && !MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2)) {
                                closeTip();
                            }
                        } else if (MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2) && sceneTipResId > 0) {
                            if (i == 9) {
                                ModeProtocol.MiAsdDetect miAsdDetect = (ModeProtocol.MiAsdDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(235);
                                if (miAsdDetect != null) {
                                    miAsdDetect.updateUltraWide(true, i2);
                                }
                            } else {
                                showTip(14, sceneTipResId, 2);
                            }
                            MiAlgoAsdSceneProfile.setTipEnable(i, true);
                            eventTrack(i);
                        }
                    } else if (!isGeneralInterception()) {
                        baseModule.showLensDirtyTip();
                        eventTrack(i);
                    }
                }
            }
        }
    }

    private void updateBacklitScene(int i, int i2, Camera2Module camera2Module) {
        if (MiAlgoAsdSceneProfile.isCheckSceneEnable(i, i2)) {
            if (!camera2Module.isShowBacklightTip()) {
                Handler handler = camera2Module.getHandler();
                Objects.requireNonNull(camera2Module);
                handler.post(new c(camera2Module));
                eventTrack(i);
            }
        } else if (camera2Module.isShowBacklightTip()) {
            camera2Module.getHandler().post(new d((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)));
        }
    }

    public void parseMiAlgoAsdResult(MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        if (aSDSceneArr != null && aSDSceneArr.length > 0) {
            FunctionMiAlgoASDEngine.LOGD("(Semantics)scenes size:" + aSDSceneArr.length);
            for (MarshalQueryableASDScene.ASDScene aSDScene : aSDSceneArr) {
                FunctionMiAlgoASDEngine.LOGD("(Semantics)-->" + aSDScene.toString());
                updateASDScene(aSDScene);
            }
        }
    }
}
