package com.android.camera.module.impl.component;

import a.a.a;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import com.android.camera.ActivityBase;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.MutexModeManager;
import com.android.camera.R;
import com.android.camera.ThermalDetector;
import com.android.camera.ToastUtils;
import com.android.camera.aiwatermark.chain.AbstractPriorityChain;
import com.android.camera.aiwatermark.chain.PriorityChainFactory;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.EyeLightConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.config.ComponentConfigAi;
import com.android.camera.data.data.config.ComponentConfigAuxiliary;
import com.android.camera.data.data.config.ComponentConfigBeauty;
import com.android.camera.data.data.config.ComponentConfigFilter;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentConfigMeter;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.config.ComponentConfigRaw;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.ComponentConfigSlowMotionQuality;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.data.data.runing.ComponentRunningColorEnhance;
import com.android.camera.data.data.runing.ComponentRunningDualVideo;
import com.android.camera.data.data.runing.ComponentRunningEyeLight;
import com.android.camera.data.data.runing.ComponentRunningLiveShot;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.ComponentRunningTimer;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.observeable.VMProcessing;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.fragment.beauty.ShineHelper;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.fragment.vv.VVItem;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.VideoBase;
import com.android.camera.module.VideoModule;
import com.android.camera.module.interceptor.ConfigChangeInterceptor;
import com.android.camera.module.loader.StartControl;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.storage.Storage;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ConfigChangeImpl implements ModeProtocol.ConfigChanges {
    private static final String TAG = "ConfigChangeImpl";
    private PriorityChainFactory factory = new PriorityChainFactory();
    private ActivityBase mActivity;
    private AbstractPriorityChain mChain = null;
    private ConfigChangeInterceptor mChangeInterceptor;
    private int[] mRecordingClosedElements;

    public ConfigChangeImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
    }

    static /* synthetic */ void a(boolean z, BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            String str = TAG;
            Log.d(str, "(moon_mode) config moon:" + z);
            ((Camera2Module) baseModule).updateMoon(z);
        }
    }

    static /* synthetic */ void a(int[] iArr, BaseModule baseModule) {
        baseModule.updatePreferenceTrampoline(iArr);
        baseModule.getCameraDevice().resumePreview();
    }

    private void applyConfig(int i, int i2) {
        if (i == 161) {
            showOrHideMimoji();
        } else if (i == 162) {
            configGif(i2);
        } else if (i == 195) {
            configPortraitSwitch(i2);
        } else if (i == 196) {
            showOrHideShine();
        } else if (i == 212) {
            showOrHideShine();
        } else if (i == 213) {
            configSlowQuality();
        } else if (i == 255) {
            configMacroMode();
        } else if (i != 256) {
            switch (i) {
                case 199:
                    configFocusPeakSwitch(i2);
                    return;
                case 201:
                    configAiSceneSwitch(i2);
                    return;
                case 215:
                    configUltraPixelPortrait(i2);
                    return;
                case 216:
                    configVV();
                    return;
                case 217:
                    configBack();
                    return;
                case 218:
                    configSuperEIS();
                    return;
                case 219:
                    configReferenceLineSwitch(i2);
                    return;
                case 220:
                    configVideoSubtitle();
                    return;
                case 221:
                    configDocumentMode(i2);
                    return;
                case 222:
                    configDualVideo();
                    return;
                case 223:
                    configAIWatermark(i2);
                    return;
                case 243:
                    configVideoBokehSwitch(i2);
                    return;
                case 260:
                    configVideoLogSwitch(i2);
                    return;
                case 261:
                    configRGBHistogramSwitch(i2);
                    return;
                default:
                    switch (i) {
                        case 203:
                            showOrHideLighting(true);
                            return;
                        case 204:
                            configFPS960();
                            return;
                        case 205:
                            configSwitchUltraWide();
                            return;
                        case 206:
                            configLiveShotSwitch(i2);
                            return;
                        case 207:
                            configSwitchUltraWideBokeh();
                            return;
                        case 208:
                            configVideoQuality();
                            return;
                        case 209:
                            configSwitchUltraPixel(i2);
                            return;
                        case 210:
                            configRatio(false);
                            return;
                        default:
                            switch (i) {
                                case 225:
                                    showSetting();
                                    return;
                                case 226:
                                    configTimerSwitch();
                                    return;
                                case 227:
                                    configColorEnhance(i2);
                                    return;
                                case 228:
                                    configTiltSwitch(i2);
                                    return;
                                case 229:
                                    configGradienterSwitch(i2);
                                    return;
                                case 230:
                                    configHHTSwitch(i2);
                                    return;
                                case 231:
                                    configMagicFocusSwitch();
                                    return;
                                default:
                                    switch (i) {
                                        case 233:
                                            configVideoFast();
                                            return;
                                        case 234:
                                            beautyMutexHandle();
                                            configScene(i2);
                                            return;
                                        case 235:
                                            configGroupSwitch(i2);
                                            return;
                                        case 236:
                                            configMagicMirrorSwitch(i2);
                                            return;
                                        case 237:
                                            configRawSwitch(i2);
                                            return;
                                        case 238:
                                            configGenderAgeSwitch(i2);
                                            return;
                                        case 239:
                                            showOrHideShine();
                                            return;
                                        case 240:
                                            configDualWaterMarkSwitch();
                                            return;
                                        case 241:
                                            configSuperResolutionSwitch(i2);
                                            return;
                                        default:
                                            switch (i) {
                                                case 246:
                                                    configMoon(true);
                                                    return;
                                                case 247:
                                                    configMoonNight();
                                                    return;
                                                case 248:
                                                    configSilhouette();
                                                    return;
                                                case 249:
                                                    configMoonBacklight();
                                                    return;
                                                default:
                                                    switch (i) {
                                                        case 251:
                                                            configCinematicAspectRatio(i2);
                                                            return;
                                                        case 252:
                                                            configSwitchHandGesture();
                                                            return;
                                                        case 253:
                                                            configAutoZoom();
                                                            return;
                                                        default:
                                                            return;
                                                    }
                                            }
                                    }
                            }
                    }
            }
        } else {
            configVideo8K();
        }
    }

    static /* synthetic */ void b(boolean z, BaseModule baseModule) {
        if ((baseModule instanceof Camera2Module) && z) {
            ((Camera2Module) baseModule).closeMoonMode(0, 8);
        }
    }

    private void beautyMutexHandle() {
    }

    private void changeMode(int i) {
        DataRepository.dataItemGlobal().setCurrentMode(i);
        this.mActivity.onModeSelected(StartControl.create(i).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
    }

    private void clearToast() {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTips();
            bottomPopupTips.setPortraitHintVisible(8);
            bottomPopupTips.hideTipImage();
            bottomPopupTips.hideLeftTipImage();
            bottomPopupTips.hideSpeedTipImage();
            bottomPopupTips.hideCenterTipImage();
            bottomPopupTips.directHideLyingDirectHint();
            bottomPopupTips.reConfigQrCodeTip();
        }
    }

    private boolean closeVideoFast() {
        DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
        if (dataItemGlobal.getCurrentMode() != 169) {
            return false;
        }
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        dataItemGlobal.setCurrentMode(162);
        dataItemRunning.switchOff("pref_video_speed_fast_key");
        return true;
    }

    private void configAIWatermark(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
            boolean aIWatermarkEnable = componentRunningAIWatermark.getAIWatermarkEnable();
            if (i != 3 || aIWatermarkEnable) {
                setUpdateTipState(223, true);
                boolean z = !aIWatermarkEnable;
                if (z) {
                    configDocumentMode(3);
                    ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                    if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                        componentRunningMacroMode.setSwitchOff(moduleIndex);
                    }
                    watermarkToast();
                }
                componentRunningAIWatermark.setAIWatermarkEnable(z);
                if (!z) {
                    componentRunningAIWatermark.resetAIWatermark(false);
                }
                setWatermarkEnable(z);
                if (1 == i) {
                    CameraStatUtils.trackAIWatermarkClick(z ? MistatsConstants.AIWatermark.AI_WATERMARK_OPEN : MistatsConstants.AIWatermark.AI_WATERMARK_CLOSE);
                }
                updateASDForWatermark();
            }
        }
    }

    private void configAiSceneSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean aiSceneOpen = CameraSettings.getAiSceneOpen(moduleIndex);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("configAiSceneSwitch: ");
            sb.append(!aiSceneOpen);
            Log.d(str, sb.toString());
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (i == 1) {
                if (!aiSceneOpen) {
                    topAlert.alertSwitchHint(1, R.string.pref_camera_front_ai_scene_entry_on, 3000);
                    CameraSettings.setAiSceneOpen(moduleIndex, true);
                    if (EffectController.getInstance().getAiColorCorrectionVersion() == 1) {
                        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.VAULE_AI_CC, true, null);
                    } else {
                        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.VAULE_AI_SCENE, true, null);
                    }
                } else {
                    topAlert.alertSwitchHint(1, R.string.pref_camera_front_ai_scene_entry_off, 3000);
                    CameraSettings.setAiSceneOpen(moduleIndex, false);
                    if (EffectController.getInstance().getAiColorCorrectionVersion() == 1) {
                        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.VAULE_AI_CC, false, null);
                    } else {
                        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.VAULE_AI_SCENE, false, null);
                    }
                    BaseModule baseModule2 = baseModule.get();
                    if (baseModule2 != null && (baseModule2 instanceof Camera2Module)) {
                        ((Camera2Module) baseModule2).closeMoonMode(0, 8);
                    }
                }
                topAlert.updateConfigItem(201);
                if (CameraSettings.isGroupShotOn()) {
                    ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).configGroupSwitch(4);
                    topAlert.refreshExtraMenu();
                }
            } else if (i != 2 && i == 3) {
                CameraSettings.setAiSceneOpen(moduleIndex, false);
                topAlert.updateConfigItem(201);
            }
            baseModule.get().updatePreferenceTrampoline(36);
            baseModule.get().getCameraDevice().resumePreview();
            if (i == 1 && CameraSettings.isUltraPixelOn()) {
                configSwitchUltraPixel(3);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
            if (i == 1 && CameraSettings.isUltraPixelPortraitFrontOn()) {
                configUltraPixelPortrait(3);
            }
        }
    }

    private void configAutoZoom() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            setUpdateTipState(253, true);
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(moduleIndex);
            HybridZoomingSystem.clearZoomRatioHistory();
            if (isAutoZoomEnabled) {
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                topAlert.updateConfigItem(253);
            } else {
                CameraSettings.setAutoZoomEnabled(moduleIndex, true);
                topAlert.updateConfigItem(253);
                switchOffElementsSilent(216);
                closeVideoFast();
                singeSwitchVideoBeauty(false);
                CameraSettings.setSuperEISEnabled(moduleIndex, false);
                CameraSettings.setSubtitleEnabled(moduleIndex, false);
                CameraSettings.setGradienterOn(false);
                CameraSettings.setCinematicAspectRatioEnabled(moduleIndex, false);
                CameraSettings.setVideoQuality8K(moduleIndex, false);
            }
            ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
            if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                componentRunningMacroMode.setSwitchOff(moduleIndex);
            }
            this.mActivity.onModeSelected(StartControl.create(162).setViewConfigType(2).setResetType(7).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (CameraSettings.isAutoZoomEnabled(162)) {
                topAlert.alertSwitchHint(2, R.string.autozoom_hint, 3000);
            } else {
                topAlert.alertSwitchHint(2, R.string.autozoom_disabled_hint, 3000);
            }
            bottomPopupTips.updateLeftTipImage();
            bottomPopupTips.updateTipImage();
        }
    }

    private void configBack() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 179) {
                configVVBack();
            } else if (moduleIndex == 182) {
                configIDCardBack();
            }
        }
    }

    private void configColorEnhance(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            ComponentRunningColorEnhance componentRunningColorEnhance = DataRepository.dataItemRunning().getComponentRunningColorEnhance();
            boolean isEnabled = componentRunningColorEnhance.isEnabled(moduleIndex);
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (i == 1) {
                if (isEnabled) {
                    componentRunningColorEnhance.setEnabled(false, 1);
                    topAlert.hideSwitchHint();
                } else {
                    componentRunningColorEnhance.setEnabled(true, 1);
                    topAlert.alertSwitchHint(1, R.string.pro_color_mode, 3000);
                }
            }
            topAlert.updateConfigItem(227);
            baseModule.get().updatePreferenceInWorkThread(74);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0085  */
    private void configDocumentMode(int i) {
        ModeProtocol.TopAlert topAlert;
        if (DataRepository.dataItemFeature().ed()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                int moduleIndex = baseModule.get().getModuleIndex();
                boolean isDocumentModeOn = CameraSettings.isDocumentModeOn(moduleIndex);
                if (i != 1) {
                    if (i != 2 && i == 3) {
                        if (isDocumentModeOn) {
                            CameraSettings.setDocumentModeOn(moduleIndex, false);
                        } else {
                            return;
                        }
                    }
                    ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                    if (isDocumentModeOn) {
                        topAlert.showDocumentStateButton(0);
                        getBaseModule().ifPresent(n.INSTANCE);
                    } else {
                        topAlert.showDocumentStateButton(8);
                    }
                    CameraStatUtils.trackDocumentModeChanged(CameraStatUtils.getDocumentModeValue(moduleIndex));
                } else if (isDocumentModeOn) {
                    CameraSettings.setDocumentModeOn(moduleIndex, false);
                    restoreAllMutexElement("p");
                } else {
                    CameraSettings.setDocumentModeOn(moduleIndex, true);
                    closeMutexElement("p", 196);
                    configLiveShotSwitch(3);
                    configTiltSwitch(3);
                    configAIWatermark(3);
                    Storage.createHideFile();
                    isDocumentModeOn = true;
                    ModeProtocol.ActionProcessing actionProcessing2 = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                    if (isDocumentModeOn) {
                    }
                    CameraStatUtils.trackDocumentModeChanged(CameraStatUtils.getDocumentModeValue(moduleIndex));
                }
                isDocumentModeOn = false;
                ModeProtocol.ActionProcessing actionProcessing22 = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                if (isDocumentModeOn) {
                }
                CameraStatUtils.trackDocumentModeChanged(CameraStatUtils.getDocumentModeValue(moduleIndex));
            }
        }
    }

    private void configDualVideo() {
        ComponentRunningDualVideo componentRunningDualVideo = DataRepository.dataItemRunning().getmComponentRunningDualVideo();
        int currentMode = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode();
        if (componentRunningDualVideo.isEnabled(currentMode)) {
            componentRunningDualVideo.setEnabled(false);
        } else {
            componentRunningDualVideo.setEnabled(true);
        }
        String str = TAG;
        Log.d(str, String.format("onClick:534: dual video: " + componentRunningDualVideo.isEnabled(currentMode), new Object[0]));
    }

    private void configGif(int i) {
        CameraSettings.isFrontCamera();
        if (getBaseModule() != null) {
            boolean z = !CameraSettings.isGifOn();
            CameraSettings.setGifSwitch(z);
            DataRepository.dataItemLive().getMimojiStatusManager2().setIsGifState(z);
            if (z) {
                DataRepository.dataItemLive().getMimojiStatusManager2().setMimojiRecordState(1);
            }
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine2 != null) {
                mimojiAvatarEngine2.changeToSquare(z);
            }
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.updateConfigItem(162);
                getBaseModule().get().restartModule();
            }
        }
    }

    private void configIDCardBack() {
        ((ModeProtocol.IDCardModeProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(233)).callBackEvent();
    }

    private void configMacroMode() {
        boolean z;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean z2 = false;
            boolean z3 = 169 == moduleIndex;
            boolean z4 = !CameraSettings.isMacroModeEnabled(moduleIndex);
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (!z4 || !z3) {
                z = false;
            } else {
                z = DataRepository.dataItemFeature().Ac();
                DataRepository.dataItemGlobal().setCurrentMode(162);
            }
            switchOffElementsSilent(216);
            CameraSettings.setAutoZoomEnabled(moduleIndex, false);
            CameraSettings.setSuperEISEnabled(moduleIndex, false);
            if (z4 && moduleIndex == 162) {
                singeSwitchVideoBeauty(false);
            }
            CameraSettings.setVideoQuality8K(moduleIndex, false);
            if (z4) {
                configAIWatermark(3);
                DataRepository.dataItemRunning().getComponentRunningAIWatermark().setAIWatermarkEnable(false);
            }
            if (!DataRepository.dataItemFeature().Ac() || z4) {
                HybridZoomingSystem.clearZoomRatioHistory();
            }
            setUpdateTipState(255, true);
            ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
            if (z4) {
                componentRunningMacroMode.setSwitchOn(moduleIndex);
            } else {
                componentRunningMacroMode.setSwitchOff(moduleIndex);
            }
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.MacroAttr.PARAM_SWITCH_MACRO, z4 ? "on" : "off");
            MistatsWrapper.mistatEvent(MistatsConstants.MacroAttr.FUCNAME_MACRO_MODE, hashMap);
            ModeProtocol.LensProtocol lensProtocol = (ModeProtocol.LensProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(200);
            if (lensProtocol != null) {
                lensProtocol.onSwitchLens(DataRepository.dataItemFeature().m1if(), z);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (z4) {
                if (bottomPopupTips != null) {
                    bottomPopupTips.directHideTipImage();
                    bottomPopupTips.directShowOrHideLeftTipImage(false);
                }
                if (dualController != null && !DataRepository.dataItemFeature().Ac()) {
                    dualController.hideZoomButton();
                    return;
                }
                return;
            }
            if (miBeautyProtocol != null) {
                z2 = miBeautyProtocol.isBeautyPanelShow();
            }
            if (bottomPopupTips != null && !z2) {
                bottomPopupTips.reInitTipImage();
            }
            if (dualController != null && !z2 && !DataRepository.dataItemFeature().Ac()) {
                if (!CameraSettings.isUltraWideConfigOpen(moduleIndex) && (moduleIndex != 172 || !DataRepository.dataItemFeature().zd())) {
                    dualController.showZoomButton();
                }
                if (topAlert != null) {
                    topAlert.clearAlertStatus();
                }
            }
        }
    }

    private void configMoon(boolean z) {
        getBaseModule().ifPresent(new o(z));
    }

    private void configMoonBacklight() {
        getBaseModule().ifPresent(a.INSTANCE);
    }

    private void configMoonNight() {
        getBaseModule().ifPresent(new s(this));
    }

    private void configReferenceLineSwitch(int i) {
        ModeProtocol.MainContentProtocol mainContentProtocol;
        boolean z = false;
        if (!DataRepository.dataItemGlobal().getBoolean("pref_camera_referenceline_key", false)) {
            z = true;
        }
        DataRepository.dataItemGlobal().putBoolean("pref_camera_referenceline_key", z).apply();
        if (1 == i) {
            trackReferenceLineChanged(z);
        }
        if (getBaseModule().isPresent() && (mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)) != null) {
            mainContentProtocol.updateReferenceLineSwitched(z);
        }
    }

    private void configSilhouette() {
        getBaseModule().ifPresent(k.INSTANCE);
    }

    private void configSlowQuality() {
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        ComponentConfigSlowMotionQuality componentConfigSlowMotionQuality = dataItemConfig.getComponentConfigSlowMotionQuality();
        int currentMode = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode();
        String nextValue = componentConfigSlowMotionQuality.getNextValue(currentMode);
        CameraStatUtils.trackSlowMotionQuality(dataItemConfig.getComponentConfigSlowMotion().getComponentValue(currentMode), nextValue);
        componentConfigSlowMotionQuality.setComponentValue(currentMode, nextValue);
        this.mActivity.onModeSelected(StartControl.create(currentMode).setViewConfigType(2).setResetType(7).setNeedReConfigureData(false).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
    }

    private void configSuperEIS() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule != null && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            setUpdateTipState(218, true);
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isSuperEISEnabled = CameraSettings.isSuperEISEnabled(moduleIndex);
            HybridZoomingSystem.clearZoomRatioHistory();
            if (isSuperEISEnabled) {
                CameraSettings.setSuperEISEnabled(moduleIndex, false);
                topAlert.updateConfigItem(218);
            } else {
                CameraSettings.setSuperEISEnabled(moduleIndex, true);
                topAlert.updateConfigItem(218);
                switchOffElementsSilent(216);
                closeVideoFast();
                singeSwitchVideoBeauty(false);
                resetVideoBokehLevel();
                resetVideoFilter();
                ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                    componentRunningMacroMode.setSwitchOff(moduleIndex);
                }
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                CameraSettings.setCinematicAspectRatioEnabled(moduleIndex, false);
                CameraSettings.setVideoQuality8K(moduleIndex, false);
            }
            trackSuperEISChanged(!isSuperEISEnabled);
            this.mActivity.onModeSelected(StartControl.create(162).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (isSuperEISEnabled) {
                topAlert.alertTopHint(8, R.string.super_eis_disabled_hint);
                topAlert.alertSwitchHint(2, R.string.super_eis_disabled_hint, 3000);
            }
            bottomPopupTips.updateLeftTipImage();
            bottomPopupTips.updateTipImage();
        }
    }

    private void configSwitchHandGesture() {
        BaseModule baseModule;
        if (DataRepository.dataItemRunning().supportHandGesture()) {
            Optional<BaseModule> baseModule2 = getBaseModule();
            if (baseModule2.isPresent() && (baseModule = baseModule2.get()) != null) {
                boolean z = !CameraSettings.isHandGestureOpen();
                CameraSettings.setHandGestureStatus(z);
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (z) {
                    bottomPopupTips.showTips(16, R.string.hand_gesture_open_tip, 2);
                }
                ((Camera2Module) baseModule).onHanGestureSwitched(z);
            }
        }
    }

    private void configSwitchUltraWide() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean z = !CameraSettings.isUltraWideConfigOpen(moduleIndex);
            if (CameraSettings.isUltraPixelOn()) {
                CameraSettings.switchOffUltraPixel();
            }
            HybridZoomingSystem.clearZoomRatioHistory();
            CameraSettings.setUltraWideConfig(moduleIndex, z);
            ModeProtocol.LensProtocol lensProtocol = (ModeProtocol.LensProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(200);
            if (lensProtocol != null) {
                lensProtocol.onSwitchLens(true, false);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                if (!z) {
                    bottomPopupTips.showTips(13, R.string.ultra_wide_close_tip, 6);
                } else if (CameraSettings.shouldShowUltraWideStickyTip(moduleIndex)) {
                    bottomPopupTips.showTips(13, R.string.ultra_wide_open_tip, 4, 500);
                } else {
                    bottomPopupTips.showTips(13, R.string.ultra_wide_open_tip, 7, 500);
                }
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    private void configSwitchUltraWideBokeh() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                boolean isSwitchOn = DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled");
                MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.PORTRAIT, MistatsConstants.PortraitAttr.PARAM_ULTRA_WIDE_BOKEH, Boolean.valueOf(!isSwitchOn));
                if (isSwitchOn) {
                    DataRepository.dataItemRunning().switchOff("pref_ultra_wide_bokeh_enabled");
                    topAlert.alertSwitchHint(2, R.string.ultra_wide_bokeh_close_tip, 3000);
                } else {
                    DataRepository.dataItemRunning().switchOn("pref_ultra_wide_bokeh_enabled");
                    topAlert.alertSwitchHint(2, R.string.ultra_wide_bokeh_open_tip, 3000);
                }
                baseModule.get().restartModule();
            }
        }
    }

    private void configVV() {
        boolean z;
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate.getActiveFragment(R.id.bottom_action) != 65523) {
            int moduleIndex = getBaseModule().get().getModuleIndex();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null && DataRepository.dataItemFeature().xd()) {
                topAlert.clearVideoUltraClear();
            }
            if (moduleIndex == 169) {
                closeVideoFast();
                z = true;
            } else {
                z = false;
            }
            if (CameraSettings.isFaceBeautyOn(moduleIndex, null)) {
                singeSwitchVideoBeauty(false);
                z = true;
            }
            if (CameraSettings.isSuperEISEnabled(moduleIndex)) {
                CameraSettings.setSuperEISEnabled(moduleIndex, false);
                z = true;
            }
            if (CameraSettings.isSubtitleEnabled(moduleIndex)) {
                CameraSettings.setSubtitleEnabled(moduleIndex, false);
                z = true;
            }
            if (CameraSettings.isAutoZoomEnabled(moduleIndex)) {
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                z = true;
            }
            if (CameraSettings.isMacroModeEnabled(moduleIndex)) {
                DataRepository.dataItemRunning().getComponentRunningMacroMode().setSwitchOff(moduleIndex);
                z = true;
            }
            if (CameraSettings.isVideoQuality8KOpen(162)) {
                CameraSettings.setVideoQuality8K(162, false);
                z = true;
            }
            if (z) {
                CameraSettings.writeZoom(1.0f);
                HybridZoomingSystem.setZoomRatioHistory(moduleIndex, String.valueOf(1.0f));
            }
            DataRepository.dataItemConfig().getComponentConfigVideoQuality().setForceValueOverlay("6");
            CameraSettings.setCinematicAspectRatioEnabled(moduleIndex, false);
            ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).updateCinematicAspectRatioSwitched(false);
            baseDelegate.delegateEvent(15);
        } else {
            DataRepository.dataItemConfig().getComponentConfigVideoQuality().setForceValueOverlay(null);
            ModeProtocol.TopAlert topAlert2 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert2 != null) {
                topAlert2.alertTopHint(8, R.string.vv_use_hint_text_title);
            }
            baseDelegate.delegateEvent(15);
            reCheckVideoUltraClearTip();
            z = false;
        }
        ModeProtocol.TopAlert topAlert3 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert3 != null) {
            topAlert3.updateConfigItem(216);
            topAlert3.updateConfigItem(256);
            topAlert3.refreshExtraMenu();
            if (z) {
                changeMode(162);
            }
        }
    }

    private void configVVBack() {
        ModeProtocol.LiveVVProcess liveVVProcess = (ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230);
        if (liveVVProcess != null) {
            liveVVProcess.showExitConfirm();
        }
    }

    private void configVideo8K() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
            int currentMode = dataItemGlobal.getCurrentMode();
            boolean isVideoQuality8KOpen = CameraSettings.isVideoQuality8KOpen(currentMode);
            MistatsWrapper.moduleUIClickEvent(CameraStatUtils.modeIdToName(currentMode), MistatsConstants.Manual.VIDEO_8K, isVideoQuality8KOpen ? "off" : "on");
            if (isVideoQuality8KOpen) {
                CameraSettings.setVideoQuality8K(currentMode, false);
                topAlert.updateConfigItem(256);
                topAlert.alertVideoUltraClear(8, R.string.pref_camera_video_8k_tips);
            } else {
                if (closeVideoFast()) {
                    currentMode = dataItemGlobal.getCurrentMode();
                }
                CameraSettings.setVideoQuality8K(currentMode, true);
                switchOffElementsSilent(216);
                CameraSettings.setCinematicAspectRatioEnabled(currentMode, false);
                int actualCameraId = baseModule.get().getActualCameraId();
                CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(actualCameraId);
                if (capabilities == null || !ComponentConfigVideoQuality.is8K30FpsCamcorderSupported(actualCameraId, capabilities)) {
                    HybridZoomingSystem.clearZoomRatioHistory();
                    if (currentMode == 180) {
                        DataRepository.dataItemConfig().getManuallyDualLens().setComponentValue(currentMode, ComponentManuallyDualLens.LENS_WIDE);
                    }
                }
                singeSwitchVideoBeauty(false);
                resetVideoBokehLevel();
                resetVideoFilter();
                CameraSettings.setAutoZoomEnabled(currentMode, false);
                CameraSettings.setSuperEISEnabled(currentMode, false);
                DataRepository.dataItemRunning().getComponentRunningMacroMode().setSwitchOff(currentMode);
                CameraSettings.setProVideoLog(false);
                CameraSettings.setSubtitleEnabled(currentMode, false);
                topAlert.updateConfigItem(256);
                topAlert.alertVideoUltraClear(0, R.string.pref_camera_video_8k_tips);
            }
            changeMode(currentMode);
        }
    }

    private void configVideoBokehSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
            boolean isSwitchOn = dataItemConfig.isSwitchOn("pref_video_bokeh_key");
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("configVideoBokehSwitch: switchOn = ");
            sb.append(!isSwitchOn);
            Log.d(str, sb.toString());
            HashMap hashMap = new HashMap();
            if (!isSwitchOn) {
                dataItemConfig.switchOn("pref_video_bokeh_key");
                hashMap.put(MistatsConstants.AlgoAttr.PARAM_BOKEN, "on");
            } else {
                dataItemConfig.switchOff("pref_video_bokeh_key");
                hashMap.put(MistatsConstants.AlgoAttr.PARAM_BOKEN, "off");
            }
            MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
            topAlert.updateConfigItem(243);
            this.mActivity.onModeSelected(StartControl.create(baseModule.get().getModuleIndex()).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureCamera(true).setNeedReConfigureData(false));
            topAlert.alertSwitchHint(2, !isSwitchOn ? R.string.pref_camera_video_bokeh_on : R.string.pref_camera_video_bokeh_off, 3000);
        }
    }

    private void configVideoQuality() {
        switchOffElementsSilent(216);
        ComponentConfigVideoQuality componentConfigVideoQuality = DataRepository.dataItemConfig().getComponentConfigVideoQuality();
        DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
        int currentMode = dataItemGlobal.getCurrentMode();
        String nextValue = componentConfigVideoQuality.getNextValue(currentMode);
        if (ComponentConfigVideoQuality.QUALITY_8K.equals(nextValue)) {
            if (closeVideoFast()) {
                currentMode = dataItemGlobal.getCurrentMode();
            }
            CameraSettings.setVideoQuality8K(currentMode, true);
        } else {
            CameraSettings.setVideoQuality8K(currentMode == 169 ? 162 : currentMode, false);
        }
        boolean supportVideoSATForVideoQuality = DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(currentMode);
        CameraStatUtils.trackVideoQuality("pref_video_quality_key", CameraSettings.isFrontCamera(), nextValue);
        componentConfigVideoQuality.setComponentValue(currentMode, nextValue);
        if (supportVideoSATForVideoQuality && !DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(currentMode)) {
            HybridZoomingSystem.clearZoomRatioHistory();
        }
        changeMode(currentMode);
    }

    private void configVideoSubtitle() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isSubtitleEnabled = CameraSettings.isSubtitleEnabled(moduleIndex);
            CameraStatUtils.trackSubtitle(!isSubtitleEnabled);
            if (isSubtitleEnabled) {
                CameraSettings.setSubtitleEnabled(moduleIndex, false);
                topAlert.updateConfigItem(220);
            } else {
                CameraSettings.setSubtitleEnabled(162, true);
                topAlert.updateConfigItem(220);
                CameraSettings.setVideoQuality8K(162, false);
                switchOffElementsSilent(216);
                closeVideoFast();
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                CameraSettings.writeZoom(1.0f);
                HybridZoomingSystem.setZoomRatioHistory(moduleIndex, String.valueOf(1.0f));
            }
            this.mActivity.onModeSelected(StartControl.create(162).setViewConfigType(2).setResetType(7).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (CameraSettings.isSubtitleEnabled(162)) {
                updateTipMessage(4, R.string.hint_subtitle, 2);
                ModeProtocol.SubtitleRecording subtitleRecording = (ModeProtocol.SubtitleRecording) ModeCoordinatorImpl.getInstance().getAttachProtocol(231);
                subtitleRecording.initPermission();
                subtitleRecording.checkNetWorkStatus();
            } else {
                hideTipMessage(R.string.hint_subtitle);
            }
            bottomPopupTips.updateLeftTipImage();
            bottomPopupTips.updateTipImage();
        }
    }

    private void conflictWithFlashAndHdr() {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        dataItemRunning.switchOff("pref_camera_hand_night_key");
        dataItemRunning.switchOff("pref_camera_groupshot_mode_key");
        dataItemRunning.switchOff("pref_camera_super_resolution_key");
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        int activeModuleIndex = ModuleManager.getActiveModuleIndex();
        if (CameraSettings.shouldShowUltraWideStickyTip(activeModuleIndex) && bottomPopupTips.getCurrentBottomTipType() == 13) {
            return;
        }
        if (CameraSettings.shouldShowUltraWideStickyTip(activeModuleIndex) && bottomPopupTips.getCurrentBottomTipType() == 17) {
            bottomPopupTips.showTips(13, R.string.ultra_wide_open_tip, 4);
        } else if (!CameraSettings.isMacroModeEnabled(activeModuleIndex) || bottomPopupTips.getCurrentBottomTipType() != 18) {
            bottomPopupTips.directlyHideTips();
        }
    }

    public static ConfigChangeImpl create(ActivityBase activityBase) {
        return new ConfigChangeImpl(activityBase);
    }

    private WatermarkItem getAIWatermarkItem() {
        if (this.mChain == null) {
            this.mChain = this.factory.createPriorityChain(DataRepository.dataItemFeature().Rb());
        }
        return this.mChain.createChain(this.mActivity).handleRequest();
    }

    private Optional<BaseModule> getBaseModule() {
        ActivityBase activityBase = this.mActivity;
        return activityBase == null ? Optional.empty() : Optional.ofNullable((BaseModule) activityBase.getCurrentModule());
    }

    private boolean getState(int i, String str) {
        if (i == 2) {
            return DataRepository.dataItemRunning().isSwitchOn(str);
        }
        if (i != 4) {
            return DataRepository.dataItemRunning().triggerSwitchAndGet(str);
        }
        DataRepository.dataItemRunning().switchOff(str);
        return false;
    }

    static /* synthetic */ void h(BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            ((Camera2Module) baseModule).updateBacklight();
        }
    }

    private void hideTipMessage(@StringRes int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (i <= 0 || bottomPopupTips.containTips(i)) {
            bottomPopupTips.directlyHideTips();
        }
    }

    private boolean is4KQuality(int i, int i2) {
        return i >= 3840 && i2 >= 2160;
    }

    private boolean is8KQuality(int i, int i2) {
        return i >= 7680 && i2 >= 4320;
    }

    private boolean isAlive() {
        return this.mActivity != null;
    }

    private boolean isBeautyPanelShow() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null) {
            return miBeautyProtocol.isBeautyPanelShow();
        }
        return false;
    }

    private boolean isChangeManuallyParameters() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (!baseModule.isPresent()) {
            return false;
        }
        int moduleIndex = baseModule.get().getModuleIndex();
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        return dataItemConfig.getmComponentManuallyWB().isModified(moduleIndex) || dataItemConfig.getmComponentManuallyET().isModified(moduleIndex) || dataItemConfig.getmComponentManuallyISO().isModified(moduleIndex) || dataItemConfig.getManuallyFocus().isModified(moduleIndex) || dataItemConfig.getComponentConfigMeter().isModified(moduleIndex) || dataItemConfig.getComponentManuallyEV().isModified(moduleIndex);
    }

    private boolean isVideoRecoding(BaseModule baseModule) {
        if (baseModule == null || !(baseModule instanceof VideoBase)) {
            return false;
        }
        return baseModule.isRecording();
    }

    static /* synthetic */ void k(BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            ((Camera2Module) baseModule).updateSilhouette();
        }
    }

    private void mutexBeautyBusiness(int i) {
        DataRepository.dataItemConfig().getComponentConfigBeauty().setClosed(false, i);
        switchOffElementsSilent(216);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (CameraSettings.isAutoZoomEnabled(i)) {
            HybridZoomingSystem.clearZoomRatioHistory();
            CameraSettings.setAutoZoomEnabled(i, false);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            bottomPopupTips.updateLeftTipImage();
            bottomPopupTips.updateTipImage();
            if (topAlert != null) {
                topAlert.hideSwitchHint();
            }
        }
        if (CameraSettings.isSuperEISEnabled(i)) {
            HybridZoomingSystem.clearZoomRatioHistory();
            CameraSettings.setSuperEISEnabled(i, false);
            ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            bottomPopupTips2.updateLeftTipImage();
            bottomPopupTips2.updateTipImage();
            if (topAlert != null) {
                topAlert.hideSwitchHint();
            }
        }
        ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
        if (componentRunningMacroMode.isSwitchOn(i)) {
            HybridZoomingSystem.clearZoomRatioHistory();
            componentRunningMacroMode.setSwitchOff(i);
        }
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        if (dataItemConfig.isSwitchOn("pref_video_bokeh_key")) {
            dataItemConfig.switchOff("pref_video_bokeh_key");
        }
        CameraSettings.setVideoQuality8K(i, false);
        if (topAlert != null) {
            topAlert.updateConfigItem(256);
        }
    }

    private void persistFilter(int i) {
        String str = TAG;
        Log.d(str, "persistFilter: filterId = " + i);
        CameraSettings.setShaderEffect(i);
    }

    public static void preload() {
        Log.i(TAG, "preload");
    }

    private void resetVideoBokehLevel() {
        CameraSettings.setVideoBokehRatio(0.0f);
    }

    private void resetVideoFilter() {
        CameraSettings.setShaderEffect(0);
    }

    private void setUpdateTipState(int i, boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.setUpdateTipState(i, z);
        }
    }

    private void showDualController(boolean z) {
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            boolean isBackCamera = CameraSettings.isBackCamera();
            if (z || !isBackCamera) {
                dualController.hideZoomButton();
            } else {
                dualController.showZoomButton();
            }
        }
    }

    private void showLiveShotTip(ModeProtocol.TopAlert topAlert, int i) {
        if (i != 0 || !DataRepository.dataItemFeature().xc()) {
            topAlert.alertLiveShotHint(1, R.string.camera_liveshot_on_tip, 3000);
        } else {
            topAlert.alertLiveShotHint(1, R.string.camera_liveshot_on_tip, 0);
        }
    }

    private void showOrHideTipImage(boolean z) {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        if (!z && CameraSettings.isFrontCamera() && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null) {
            bottomPopupTips.updateTipImage();
        }
    }

    private void showWatermarkSample(boolean z) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol == null) {
            return;
        }
        if (z) {
            WatermarkItem aIWatermarkItem = getAIWatermarkItem();
            mainContentProtocol.setWatermarkVisible(0);
            if (aIWatermarkItem != null) {
                mainContentProtocol.updateWatermarkSample(aIWatermarkItem);
                return;
            }
            return;
        }
        mainContentProtocol.setWatermarkVisible(4);
    }

    private void singeSwitchVideoBeauty(boolean z) {
        ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
        int i = 0;
        if (componentRunningShine.supportBeautyLevel()) {
            if (z) {
                i = 3;
            }
            CameraSettings.setFaceBeautyLevel(i);
        } else if (componentRunningShine.supportSmoothLevel()) {
            CameraSettings.setFaceBeautySmoothLevel(z ? 40 : 0);
            if (!z) {
                int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
                DataRepository.dataItemRunning().getComponentRunningShine().setVideoBeautySwitch(currentMode, false);
                if (DataRepository.dataItemRunning().getComponentRunningShine().isSmoothBarVideoBeautyVersion(currentMode)) {
                    CameraSettings.setShaderEffect(0);
                    CameraSettings.setVideoBokehRatio(0.0f);
                }
                ShineHelper.onBeautyChanged();
                ShineHelper.onVideoBokehRatioChanged();
                ShineHelper.onVideoFilterChanged();
            }
        }
    }

    private void trackFocusPeakChanged(boolean z) {
        MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.MANUAL, MistatsConstants.Manual.MANUAL_FOCUS_PEAK, String.valueOf(z));
    }

    private void trackGenderAgeChanged(boolean z) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_GENDER_AGE, Boolean.valueOf(z), null);
    }

    private void trackGotoSettings() {
        BaseModule baseModule = (BaseModule) this.mActivity.getCurrentModule();
        if (baseModule != null) {
            CameraStatUtils.trackGotoSettings(baseModule.getModuleIndex());
        }
    }

    private void trackGradienterChanged(boolean z) {
        MistatsWrapper.moduleUIClickEvent(DataRepository.dataItemGlobal().getCurrentMode(), MistatsConstants.Manual.GRADIENT, Boolean.valueOf(z));
    }

    private void trackHHTChanged(boolean z) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_HHT, Boolean.valueOf(z), null);
    }

    private void trackMagicMirrorChanged(boolean z) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_MAGIC_MIRROR, Boolean.valueOf(z), null);
    }

    private void trackReferenceLineChanged(boolean z) {
        MistatsWrapper.commonKeyTriggerEvent("attr_reference_line", Boolean.valueOf(z), null);
    }

    private void trackSuperEISChanged(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.MODULE_NAME, MistatsWrapper.getStatsModuleKey(DataRepository.dataItemGlobal().getCurrentMode()));
        hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, CameraSettings.isFrontCamera() ? "front" : MistatsConstants.BaseEvent.BACK);
        hashMap.put(MistatsConstants.VideoAttr.PARAM_SUPER_EIS, String.valueOf(z));
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
    }

    private void trackSuperResolutionChanged(boolean z) {
        MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.MANUAL, MistatsConstants.Manual.SUPER_RESOLUTION, String.valueOf(z));
    }

    private void trackUltraPixelPortraitChanged(boolean z) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.PortraitAttr.ULTRAPIXEL_PORTRAIT, String.valueOf(z), null);
    }

    private void updateAiScene(boolean z) {
        DataRepository.dataItemGlobal().getCurrentMode();
        ComponentConfigAi componentConfigAi = DataRepository.dataItemConfig().getComponentConfigAi();
        if (!componentConfigAi.isEmpty() && componentConfigAi.isClosed() != z) {
            componentConfigAi.setClosed(z);
            getBaseModule().ifPresent(new c(z));
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(201);
        }
    }

    private void updateAutoZoom(boolean z) {
    }

    private void updateComponentBeauty(boolean z) {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol;
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        ComponentConfigBeauty componentConfigBeauty = DataRepository.dataItemConfig().getComponentConfigBeauty();
        if (!componentConfigBeauty.isEmpty() && componentConfigBeauty.isClosed(currentMode) != z) {
            componentConfigBeauty.setClosed(z, currentMode);
            if (z && (miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194)) != null && miBeautyProtocol.isBeautyPanelShow()) {
                miBeautyProtocol.dismiss(2);
            }
            ModeProtocol.OnShineChangedProtocol onShineChangedProtocol = (ModeProtocol.OnShineChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(234);
            if (onShineChangedProtocol != null) {
                onShineChangedProtocol.onShineChanged(true, 239);
            }
        }
    }

    private void updateComponentFilter(boolean z) {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol;
        String str = TAG;
        Log.d(str, "updateComponentFilter: close = " + z);
        ComponentConfigFilter componentConfigFilter = DataRepository.dataItemRunning().getComponentConfigFilter();
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (!componentConfigFilter.isEmpty() && componentConfigFilter.isClosed(currentMode) != z) {
            componentConfigFilter.setClosed(z, currentMode);
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(212);
            if (z && (miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194)) != null && miBeautyProtocol.isBeautyPanelShow()) {
                miBeautyProtocol.dismiss(2);
            }
        }
    }

    private void updateComponentFlash(String str, boolean z) {
        ComponentConfigFlash componentFlash = DataRepository.dataItemConfig().getComponentFlash();
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (!componentFlash.isEmpty() && componentFlash.isClosed() != z) {
            if (!z || !componentFlash.getComponentValue(currentMode).equals("2") || !SupportedConfigFactory.CLOSE_BY_BURST_SHOOT.equals(str)) {
                componentFlash.setClosed(z);
                ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(193);
            }
        }
    }

    private void updateComponentHdr(boolean z) {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        DataRepository.dataItemGlobal().getCurrentMode();
        if (!componentHdr.isEmpty() && componentHdr.isClosed() != z) {
            componentHdr.setClosed(z);
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(194);
        }
    }

    private void updateComponentShine(boolean z) {
        ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
        if (!componentRunningShine.isEmpty() && componentRunningShine.isClosed() != z) {
            componentRunningShine.setClosed(z);
        }
    }

    private void updateEyeLight(boolean z) {
        ComponentRunningEyeLight componentRunningEyeLight = DataRepository.dataItemRunning().getComponentRunningEyeLight();
        if (componentRunningEyeLight.isClosed() != z) {
            componentRunningEyeLight.setClosed(z);
            String eyeLightType = CameraSettings.getEyeLightType();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (topAlert != null && bottomPopupTips != null) {
                if (!"-1".equals(eyeLightType)) {
                    topAlert.alertTopHint(0, R.string.eye_light);
                    if (z) {
                        bottomPopupTips.showTips(10, EyeLightConstant.getString(eyeLightType), 4);
                        return;
                    }
                    return;
                }
                topAlert.alertTopHint(8, R.string.eye_light);
                bottomPopupTips.directlyHideTips();
            }
        }
    }

    private void updateFlashModeAndRefreshUI(BaseModule baseModule, String str) {
        int moduleIndex = baseModule.getModuleIndex();
        String str2 = TAG;
        Log.d(str2, "updateFlashModeAndRefreshUI flashMode = " + str);
        if (!TextUtils.isEmpty(str)) {
            CameraSettings.setFlashMode(moduleIndex, str);
        }
        if ("0".equals(str)) {
            if (CameraSettings.isFrontCamera()) {
                ToastUtils.showToast(this.mActivity, (int) R.string.close_front_flash_toast);
            } else {
                ToastUtils.showToast(this.mActivity, (int) R.string.close_back_flash_toast);
            }
        }
        if (!baseModule.isDoingAction() || "0".equals(str)) {
            baseModule.updatePreferenceInWorkThread(10);
        } else {
            baseModule.updatePreferenceTrampoline(10);
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.updateConfigItem(193);
        }
    }

    private void updateLiveShot(boolean z) {
        if (DataRepository.dataItemFeature().vd()) {
            int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
            if (currentMode == 163 || currentMode == 165) {
                ComponentRunningLiveShot componentRunningLiveShot = DataRepository.dataItemRunning().getComponentRunningLiveShot();
                if (componentRunningLiveShot.isClosed() != z) {
                    componentRunningLiveShot.setClosed(z);
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    topAlert.updateConfigItem(206);
                    if (z) {
                        topAlert.setLiveShotHintVisibility(8);
                    } else {
                        topAlert.setLiveShotHintVisibility(0);
                    }
                }
            }
        }
    }

    private void updateRaw(boolean z) {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        ComponentConfigRaw componentConfigRaw = DataRepository.dataItemConfig().getComponentConfigRaw();
        if (!componentConfigRaw.isEmpty() && componentConfigRaw.isClosed(currentMode) != z) {
            componentConfigRaw.setClosed(z, currentMode);
        }
    }

    private void updateTipMessage(int i, @StringRes int i2, int i3) {
        ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).showTips(i, i2, i3);
    }

    private void updateUltraPixel(boolean z) {
        ComponentRunningUltraPixel componentUltraPixel = DataRepository.dataItemRunning().getComponentUltraPixel();
        if (!componentUltraPixel.isEmpty() && componentUltraPixel.isClosed() != z) {
            componentUltraPixel.setClosed(z);
        }
    }

    private void watermarkToast() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertSwitchHint(2, R.string.ai_watermark_title, 3000);
        }
    }

    public /* synthetic */ void a(BaseModule baseModule) {
        if (172 != baseModule.getModuleIndex()) {
            conflictWithFlashAndHdr();
        }
        baseModule.updatePreferenceInWorkThread(11, 58);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void closeMutexElement(String str, int... iArr) {
        int[] iArr2 = new int[iArr.length];
        this.mRecordingClosedElements = iArr;
        for (int i = 0; i < iArr.length; i++) {
            int i2 = iArr[i];
            if (i2 == 193) {
                updateComponentFlash(str, true);
                iArr2[i] = 10;
            } else if (i2 == 194) {
                updateComponentHdr(true);
                iArr2[i] = 11;
            } else if (i2 == 196) {
                updateComponentFilter(true);
                iArr2[i] = 2;
            } else if (i2 == 201) {
                updateAiScene(true);
                iArr2[i] = 36;
            } else if (i2 == 206) {
                updateLiveShot(true);
                iArr2[i] = 49;
            } else if (i2 == 209) {
                updateUltraPixel(true);
                iArr2[i] = 50;
            } else if (i2 == 212) {
                updateComponentShine(true);
                iArr2[i] = 2;
            } else if (i2 == 227) {
                iArr2[i] = 74;
            } else if (i2 == 237) {
                updateRaw(true);
                iArr2[i] = 44;
            } else if (i2 == 239) {
                updateComponentBeauty(true);
                iArr2[i] = 13;
            } else if (i2 == 253) {
                updateAutoZoom(true);
                iArr2[i] = 51;
            } else if (i2 == 254) {
                updateEyeLight(true);
                iArr2[i] = 45;
            } else {
                throw new RuntimeException("unknown mutex element");
            }
        }
        getBaseModule().ifPresent(new i(iArr2));
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configAuxiliary(String str) {
        if (str.equals("close")) {
            DataRepository.dataItemRunning().switchOff("pref_camera_peak_key");
            EffectController.getInstance().setDrawPeaking(false);
            DataRepository.dataItemRunning().switchOff("pref_camera_exposure_feedback");
            EffectController.getInstance().setDrawExposure(false);
        } else if (str.equals(ComponentConfigAuxiliary.A_FOCUS_PEAK)) {
            DataRepository.dataItemRunning().switchOff("pref_camera_exposure_feedback");
            EffectController.getInstance().setDrawExposure(false);
            DataRepository.dataItemRunning().switchOn("pref_camera_peak_key");
            EffectController.getInstance().setDrawPeaking(true);
        } else {
            DataRepository.dataItemRunning().switchOff("pref_camera_peak_key");
            EffectController.getInstance().setDrawPeaking(false);
            DataRepository.dataItemRunning().switchOn("pref_camera_exposure_feedback");
            EffectController.getInstance().setDrawExposure(true);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configBackSoftLightSwitch(String str) {
        getBaseModule().ifPresent(new e(this));
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configBeautySwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean z = moduleIndex == 162 || moduleIndex == 169;
            if (moduleIndex == 163 || moduleIndex == 165 || moduleIndex == 171 || moduleIndex == 161 || z) {
                ComponentConfigBeauty componentConfigBeauty = DataRepository.dataItemConfig().getComponentConfigBeauty();
                String nextValue = componentConfigBeauty.getNextValue(moduleIndex);
                boolean z2 = (!BeautyConstant.LEVEL_CLOSE.equals(componentConfigBeauty.getComponentValue(moduleIndex))) ^ (!BeautyConstant.LEVEL_CLOSE.equals(nextValue));
                componentConfigBeauty.setComponentValue(moduleIndex, nextValue);
                CameraStatUtils.trackBeautySwitchChanged(nextValue);
                if (z2 && z) {
                    if (moduleIndex != 162) {
                        DataRepository.dataItemRunning().switchOff("pref_video_speed_fast_key");
                        CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                        ((DataItemGlobal) DataRepository.provider().dataGlobal()).setCurrentMode(162);
                        DataRepository.getInstance().backUp().removeOtherVideoMode();
                        CameraStatUtils.trackVideoModeChanged("normal");
                    }
                    this.mActivity.onModeSelected(StartControl.create(162).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
                } else if (!z2 || moduleIndex != 161) {
                    baseModule.get().updatePreferenceInWorkThread(13);
                } else {
                    this.mActivity.onModeSelected(StartControl.create(161).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configBokeh(String str) {
        if (str.equals("on")) {
            updateTipMessage(4, R.string.bokeh_use_hint, 2);
        } else {
            hideTipMessage(R.string.bokeh_use_hint);
        }
        getBaseModule().ifPresent(f.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configCinematicAspectRatio(int i) {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (baseModule2.isFrameAvailable()) {
                    setUpdateTipState(251, true);
                    int moduleIndex = baseModule2.getModuleIndex();
                    boolean z = !CameraSettings.isCinematicAspectRatioEnabled(moduleIndex);
                    CameraSettings.setCinematicAspectRatioEnabled(moduleIndex, z);
                    String str = "on";
                    if (moduleIndex == 171) {
                        if (!z) {
                            str = "off";
                        }
                        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.PARAM_PHOTO_RATIO_MOVIE, str, null);
                        configRatio(true);
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        if (topAlert != null) {
                            topAlert.updateConfigItem(251);
                            return;
                        }
                        return;
                    }
                    if (!z) {
                        str = "off";
                    }
                    MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.PARAM_VIDEO_RATIO_MOVIE, str, null);
                    switchOffElementsSilent(216);
                    CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                    CameraSettings.setSuperEISEnabled(moduleIndex, false);
                    CameraSettings.setVideoQuality8K(moduleIndex, false);
                    changeMode(moduleIndex);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configDualWaterMarkSwitch() {
        boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
        CameraStatUtils.trackDualWaterMarkChanged(!isDualCameraWaterMarkOpen);
        if (isDualCameraWaterMarkOpen) {
            hideTipMessage(R.string.hunt_dual_water_mark);
            CameraSettings.setDualCameraWaterMarkOpen(false);
        } else {
            updateTipMessage(4, R.string.hunt_dual_water_mark, 2);
            CameraSettings.setDualCameraWaterMarkOpen(true);
        }
        getBaseModule().ifPresent(l.INSTANCE);
    }

    public void configFPS960() {
        ComponentConfigSlowMotion componentConfigSlowMotion = DataRepository.dataItemConfig().getComponentConfigSlowMotion();
        if (componentConfigSlowMotion.getSupportedFpsOptions().length > 1) {
            componentConfigSlowMotion.setComponentValue(172, componentConfigSlowMotion.getNextValue(172));
            this.mActivity.onModeSelected(StartControl.create(172).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureData(false).setNeedReConfigureCamera(true));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configFlash(String str) {
        if (!ModuleManager.isVideoNewSlowMotion()) {
            conflictWithFlashAndHdr();
        }
        getBaseModule().ifPresent(p.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configFocusPeakSwitch(int i) {
        boolean state = getState(i, "pref_camera_peak_key");
        if (1 == i) {
            trackFocusPeakChanged(state);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            baseModule.get().getModuleIndex();
            if (DataRepository.dataItemConfig().getManuallyFocus().disableUpdate()) {
                EffectController.getInstance().setDrawPeaking(false);
            } else {
                EffectController.getInstance().setDrawPeaking(state);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configGenderAgeSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_show_gender_age_key");
            if (1 == i) {
                trackGenderAgeChanged(state);
            }
            ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).setShowGenderAndAge(state);
            baseModule.get().updatePreferenceInWorkThread(38);
            if (state) {
                Camera2Proxy cameraDevice = baseModule.get().getCameraDevice();
                if (cameraDevice != null) {
                    String string = CameraAppImpl.getAndroidContext().getResources().getString(R.string.face_age_info);
                    cameraDevice.setFaceWaterMarkEnable(true);
                    cameraDevice.setFaceWaterMarkFormat(string);
                    return;
                }
                return;
            }
            Camera2Proxy cameraDevice2 = baseModule.get().getCameraDevice();
            if (cameraDevice2 != null) {
                cameraDevice2.setFaceWaterMarkEnable(false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configGradienterSwitch(int i) {
        boolean z;
        boolean z2 = true;
        if (i == 2) {
            z = CameraSettings.isGradienterOn();
        } else if (i != 4) {
            boolean isGradienterOn = CameraSettings.isGradienterOn();
            CameraSettings.setGradienterOn(!isGradienterOn);
            z = !isGradienterOn;
        } else {
            CameraSettings.setGradienterOn(false);
            z = false;
        }
        if (1 == i) {
            trackGradienterChanged(z);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            baseModule.get().onGradienterSwitched(z);
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null) {
                mainContentProtocol.updateGradienterSwitched(z);
            }
            BaseModule baseModule2 = baseModule.get();
            if (z) {
                z2 = false;
            }
            baseModule2.showOrHideChip(z2);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
            if (z && CameraSettings.isAutoZoomEnabled(moduleIndex)) {
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                CameraSettings.writeZoom(1.0f);
                HybridZoomingSystem.setZoomRatioHistory(moduleIndex, String.valueOf(1.0f));
                changeMode(162);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configGroupSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_groupshot_mode_key");
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            Camera2Module camera2Module = (Camera2Module) baseModule.get();
            camera2Module.showOrHideChip(!state);
            boolean isBeautyPanelShow = isBeautyPanelShow();
            if (state) {
                if (!isBeautyPanelShow) {
                    updateTipMessage(17, R.string.hint_groupshot, 2);
                }
                if (CameraSettings.shouldShowUltraWideStickyTip(camera2Module.getModuleIndex()) && !isBeautyPanelShow) {
                    bottomPopupTips.showTips(13, R.string.ultra_wide_open_tip, 4, 5000);
                }
                closeMutexElement(SupportedConfigFactory.CLOSE_BY_GROUP, 193, 194, 196, 201, 254);
            } else {
                restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_GROUP);
                hideTipMessage(R.string.hint_groupshot);
                if (CameraSettings.shouldShowUltraWideStickyTip(camera2Module.getModuleIndex()) && !isBeautyPanelShow) {
                    bottomPopupTips.directlyShowTips(13, R.string.ultra_wide_open_tip);
                }
            }
            camera2Module.onSharedPreferenceChanged();
            baseModule.get().updatePreferenceInWorkThread(42, 34, 30);
            bottomPopupTips.reConfigQrCodeTip();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configHHTSwitch(int i) {
        boolean state = getState(i, "pref_camera_hand_night_key");
        if (1 == i) {
            trackHHTChanged(state);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            MutexModeManager mutexModePicker = baseModule.get().getMutexModePicker();
            if (state) {
                updateTipMessage(4, R.string.hine_hht, 2);
                closeMutexElement(SupportedConfigFactory.CLOSE_BY_HHT, 193, 194);
                mutexModePicker.setMutexModeMandatory(3);
                return;
            }
            hideTipMessage(R.string.hine_hht);
            mutexModePicker.clearMandatoryFlag();
            baseModule.get().resetMutexModeManually();
            restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_HHT);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configHdr(String str) {
        conflictWithFlashAndHdr();
        getBaseModule().ifPresent(q.INSTANCE);
        if ("off" != str && CameraSettings.isUltraPixelRearOn()) {
            configSwitchUltraPixel(3);
        }
        if ("off" != str && CameraSettings.isUltraPixelPortraitFrontOn()) {
            configUltraPixelPortrait(3);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configLiveReview() {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(11);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configLiveShotSwitch(int i) {
        ModeProtocol.TopAlert topAlert;
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (baseModule2.isFrameAvailable()) {
                    if ((baseModule2.getModuleIndex() == 163 || baseModule2.getModuleIndex() == 165) && DataRepository.dataItemFeature().vd() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                        Camera2Module camera2Module = (Camera2Module) baseModule2;
                        if (i == 1) {
                            boolean isLiveShotOn = CameraSettings.isLiveShotOn();
                            CameraSettings.setLiveShotOn(!isLiveShotOn);
                            if (isLiveShotOn) {
                                camera2Module.stopLiveShot(false);
                                if (CameraSettings.isUltraPixelOn() || !DataRepository.dataItemConfig().getComponentConfigRatio().isSquareModule()) {
                                    topAlert.alertLiveShotHint(1, R.string.camera_liveshot_off_tip, 3000);
                                } else {
                                    topAlert.alertLiveShotHint(2, R.string.camera_liveshot_off_tip, 3000);
                                    DataRepository.dataItemGlobal().setCurrentMode(165);
                                    this.mActivity.onModeSelected(StartControl.create(165).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureCamera(true).setNeedReConfigureData(false));
                                }
                            } else {
                                configDocumentMode(3);
                                if (CameraSettings.isUltraPixelOn()) {
                                    Log.d(TAG, "Ignore #startLiveShot in ultra pixel photography mode");
                                } else if (camera2Module.getModuleIndex() == 165) {
                                    configRatio(true);
                                } else {
                                    camera2Module.startLiveShot();
                                    showLiveShotTip(topAlert, baseModule2.getBogusCameraId());
                                }
                            }
                        } else if ((i == 3 || i == 4) && CameraSettings.isLiveShotOn()) {
                            CameraSettings.setLiveShotOn(false);
                            camera2Module.stopLiveShot(false);
                        }
                        topAlert.updateConfigItem(206);
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configLiveVV(VVItem vVItem, boolean z, boolean z2) {
        ((VMProcessing) DataRepository.dataItemObservable().get(VMProcessing.class)).reset();
        if (z) {
            ((ModeProtocol.LiveVVChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(229)).hide();
            ((ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230)).prepare(vVItem);
            DataRepository.dataItemLive().setCurrentVVItem(vVItem);
            changeMode(179);
            return;
        }
        if (z2) {
            configVV();
        } else {
            int i = 0;
            VVItem currentVVItem = DataRepository.dataItemLive().getCurrentVVItem();
            if (currentVVItem != null) {
                i = currentVVItem.index;
            }
            ((ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230)).quit();
            ((ModeProtocol.LiveVVChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(229)).show(i);
        }
        changeMode(162);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configMagicFocusSwitch() {
        trackMagicMirrorChanged(DataRepository.dataItemRunning().triggerSwitchAndGet("pref_camera_ubifocus_key"));
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configMagicMirrorSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_magic_mirror_key");
            if (1 == i) {
                trackMagicMirrorChanged(state);
            }
            ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).setShowMagicMirror(state);
            baseModule.get().updatePreferenceInWorkThread(39);
            if (state) {
                Camera2Proxy cameraDevice = baseModule.get().getCameraDevice();
                if (cameraDevice != null) {
                    String string = CameraAppImpl.getAndroidContext().getResources().getString(R.string.face_score_info);
                    cameraDevice.setFaceWaterMarkEnable(true);
                    cameraDevice.setFaceWaterMarkFormat(string);
                    return;
                }
                return;
            }
            Camera2Proxy cameraDevice2 = baseModule.get().getCameraDevice();
            if (cameraDevice2 != null) {
                cameraDevice2.setFaceWaterMarkEnable(false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configMeter(String str) {
        reCheckParameterResetTip(false);
        getBaseModule().ifPresent(b.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configPortraitSwitch(int i) {
        getBaseModule().ifPresent(d.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configRGBHistogramSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            BaseModule baseModule2 = baseModule.get();
            if (baseModule2.isFrameAvailable()) {
                int moduleIndex = baseModule2.getModuleIndex();
                boolean isProVideoHistogramOpen = CameraSettings.isProVideoHistogramOpen(moduleIndex);
                CameraSettings.setProVideoHistogramOpen(!isProVideoHistogramOpen);
                MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.PROVIDEO, MistatsConstants.Manual.HISTOGRAM, isProVideoHistogramOpen ? "off" : "on");
                ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
                if (mainContentProtocol != null) {
                    mainContentProtocol.updateRGBHistogramSwitched(!isProVideoHistogramOpen);
                }
                changeMode(moduleIndex);
            }
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public void configRatio(boolean z) {
        String str;
        boolean z2;
        boolean z3;
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (baseModule2.isFrameAvailable()) {
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    int moduleIndex = baseModule2.getModuleIndex();
                    ComponentConfigRatio componentConfigRatio = DataRepository.dataItemConfig().getComponentConfigRatio();
                    if (z) {
                        str = componentConfigRatio.getDefaultValue(moduleIndex);
                    } else {
                        String nextValue = componentConfigRatio.getNextValue(moduleIndex);
                        CameraStatUtils.trackPictureSize(moduleIndex, nextValue);
                        if (DataRepository.dataItemConfig().reConfigCinematicAspectRatioIfRatioChanged(moduleIndex, nextValue) && topAlert != null) {
                            topAlert.updateConfigItem(251);
                        }
                        str = nextValue;
                    }
                    if (CameraSettings.isCinematicAspectRatioEnabled(moduleIndex)) {
                        z = true;
                        str = ComponentConfigRatio.RATIO_16X9;
                    }
                    char c2 = 65535;
                    int i = 2;
                    switch (str.hashCode()) {
                        case 50858:
                            if (str.equals(ComponentConfigRatio.RATIO_1X1)) {
                                c2 = 6;
                                break;
                            }
                            break;
                        case 53743:
                            if (str.equals(ComponentConfigRatio.RATIO_4X3)) {
                                c2 = 0;
                                break;
                            }
                            break;
                        case 1515430:
                            if (str.equals(ComponentConfigRatio.RATIO_16X9)) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 1517352:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_18X9)) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 1518313:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_19X9)) {
                                c2 = 3;
                                break;
                            }
                            break;
                        case 1539455:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_20X9)) {
                                c2 = 5;
                                break;
                            }
                            break;
                        case 1456894192:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_195X9)) {
                                c2 = 4;
                                break;
                            }
                            break;
                    }
                    int i2 = 165;
                    switch (c2) {
                        case 0:
                            if (moduleIndex == 165 || moduleIndex == 163) {
                                updateLiveShot(false);
                                z3 = false;
                                z2 = false;
                                i2 = 163;
                                break;
                            }
                            i2 = moduleIndex;
                            z3 = false;
                            z2 = z3;
                            break;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            if (moduleIndex == 165 || moduleIndex == 163) {
                                moduleIndex = 163;
                            }
                            i2 = moduleIndex;
                            z3 = true;
                            z2 = z3;
                            break;
                        case 6:
                            componentConfigRatio.setComponentValue(moduleIndex, componentConfigRatio.getDefaultValue(moduleIndex));
                            if (moduleIndex == 165 || moduleIndex == 163) {
                                updateLiveShot(true);
                            }
                            z3 = true;
                            z2 = z3;
                            break;
                        default:
                            i2 = moduleIndex;
                            z3 = false;
                            z2 = z3;
                            break;
                    }
                    if (z3) {
                        DataRepository.dataItemRunning().getComponentRunningAIWatermark().setAIWatermarkEnable(false);
                    }
                    if (z2 && CameraSettings.isUltraPixelOn()) {
                        switchOffElementsSilent(209);
                    }
                    if (!z) {
                        componentConfigRatio.setComponentValue(i2, str);
                    }
                    DataRepository.dataItemGlobal().setCurrentMode(i2);
                    ActivityBase activityBase = this.mActivity;
                    StartControl viewConfigType = StartControl.create(i2).setViewConfigType(2);
                    if (!z) {
                        i = 7;
                    }
                    activityBase.onModeSelected(viewConfigType.setResetType(i).setNeedReConfigureData(false).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configRawSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            ComponentConfigRaw componentConfigRaw = DataRepository.dataItemConfig().getComponentConfigRaw();
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isSwitchOn = componentConfigRaw.isSwitchOn(moduleIndex);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("configRawSwitch: ");
            sb.append(!isSwitchOn);
            Log.d(str, sb.toString());
            if (i == 1) {
                if (isSwitchOn) {
                    componentConfigRaw.setRaw(moduleIndex, false);
                    MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.MANUAL, "raw", "off");
                } else {
                    componentConfigRaw.setRaw(moduleIndex, true);
                    if (DataRepository.dataItemFeature().Kc()) {
                        closeMutexElement("n", 209);
                    }
                    MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.MANUAL, "raw", "on");
                }
                this.mActivity.onModeSelected(StartControl.create(moduleIndex).setViewConfigType(3).setResetType(7).setNeedReConfigureData(false).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
                reCheckRaw();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configRotationChange(int i, int i2) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        boolean z = true;
        if (i2 != 0) {
            if (i2 != 90) {
                if (i2 == 180) {
                    if (mainContentProtocol != null) {
                        mainContentProtocol.updateLyingDirectHint(false, false);
                    }
                    if (topAlert != null) {
                        topAlert.updateLyingDirectHint(false, false);
                    }
                    if (bottomPopupTips != null) {
                        if (i != 1) {
                            z = false;
                        }
                        bottomPopupTips.updateLyingDirectHint(z, false);
                        return;
                    }
                    return;
                } else if (i2 != 270) {
                    return;
                }
            }
            if (topAlert != null) {
                topAlert.updateLyingDirectHint(false, false);
            }
            if (bottomPopupTips != null) {
                bottomPopupTips.updateLyingDirectHint(false, false);
            }
            if (mainContentProtocol != null) {
                if (i != 1) {
                    z = false;
                }
                mainContentProtocol.updateLyingDirectHint(z, false);
                return;
            }
            return;
        }
        if (mainContentProtocol != null) {
            mainContentProtocol.updateLyingDirectHint(false, false);
        }
        if (bottomPopupTips != null) {
            bottomPopupTips.updateLyingDirectHint(false, false);
        }
        if (topAlert != null) {
            if (i != 1) {
                z = false;
            }
            topAlert.updateLyingDirectHint(z, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configScene(int i) {
        final Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_scenemode_setting_key");
            ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (state) {
                bottomPopupTips.hideTipImage();
                if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                    miBeautyProtocol.dismiss(2);
                }
                manuallyAdjust.setManuallyVisible(2, 1, new ManuallyListener() {
                    /* class com.android.camera.module.impl.component.ConfigChangeImpl.AnonymousClass1 */

                    @Override // com.android.camera.fragment.manually.ManuallyListener
                    public void onManuallyDataChanged(ComponentData componentData, String str, String str2, boolean z, int i) {
                        ((BaseModule) baseModule.get()).onSharedPreferenceChanged();
                        ((BaseModule) baseModule.get()).updatePreferenceInWorkThread(4);
                    }
                });
            } else {
                bottomPopupTips.reInitTipImage();
                manuallyAdjust.setManuallyVisible(2, i == 1 ? 4 : 3, null);
            }
            baseModule.get().onSharedPreferenceChanged();
            baseModule.get().updatePreferenceInWorkThread(4);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configSuperResolutionSwitch(int i) {
        boolean state = getState(i, "pref_camera_super_resolution_key");
        if (1 == i) {
            trackSuperResolutionChanged(state);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            MutexModeManager mutexModePicker = baseModule.get().getMutexModePicker();
            if (state) {
                closeMutexElement(SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION, 193, 194);
                mutexModePicker.setMutexModeMandatory(10);
                return;
            }
            mutexModePicker.clearMandatoryFlag();
            baseModule.get().resetMutexModeManually();
            restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION);
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00fd  */
    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configSwitchUltraPixel(int i) {
        boolean z;
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                int moduleIndex = baseModule2.getModuleIndex();
                boolean isUltraPixelOn = CameraSettings.isUltraPixelOn();
                boolean z2 = !isUltraPixelOn;
                ComponentRunningUltraPixel componentUltraPixel = DataRepository.dataItemRunning().getComponentUltraPixel();
                String currentSupportUltraPixel = componentUltraPixel.getCurrentSupportUltraPixel();
                if (i == 1) {
                    if (CameraSettings.isUltraWideConfigOpen(moduleIndex)) {
                        CameraSettings.setUltraWideConfig(moduleIndex, false);
                        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                        bottomPopupTips.updateLeftTipImage();
                        bottomPopupTips.directlyHideTips();
                    }
                    if (z2) {
                        int hashCode = currentSupportUltraPixel.hashCode();
                        if (hashCode != -1379357773) {
                            switch (hashCode) {
                                case -70725170:
                                    if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_64M)) {
                                        z = true;
                                        break;
                                    }
                                    break;
                                case -70725169:
                                    if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_48M)) {
                                        z = false;
                                        break;
                                    }
                                    break;
                                case -70725168:
                                    if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_108M)) {
                                        z = true;
                                        break;
                                    }
                                    break;
                            }
                            if (!z) {
                                int[] iArr = {194, 239, 201, 206};
                                if (DataRepository.dataItemFeature().Kc()) {
                                    iArr = Arrays.copyOf(iArr, iArr.length + 1);
                                    iArr[iArr.length - 1] = 237;
                                }
                                closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL, iArr);
                            } else if (z) {
                                closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL, 196, 201, 206);
                            } else if ((z || z) && DataRepository.dataItemFeature().Kc()) {
                                closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL, 237);
                            }
                            DataRepository.dataItemRunning().setRecordingClosedElements(this.mRecordingClosedElements);
                            CameraSettings.switchOnUltraPixel(currentSupportUltraPixel);
                        } else if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_FRONT_32M)) {
                            z = true;
                            if (!z) {
                            }
                            DataRepository.dataItemRunning().setRecordingClosedElements(this.mRecordingClosedElements);
                            CameraSettings.switchOnUltraPixel(currentSupportUltraPixel);
                        }
                        z = true;
                        if (!z) {
                        }
                        DataRepository.dataItemRunning().setRecordingClosedElements(this.mRecordingClosedElements);
                        CameraSettings.switchOnUltraPixel(currentSupportUltraPixel);
                    } else {
                        this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                        restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL);
                        CameraSettings.switchOffUltraPixel();
                    }
                    ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                    if (miBeautyProtocol != null ? miBeautyProtocol.isBeautyPanelShow() : false) {
                        miBeautyProtocol.dismiss(2);
                    }
                    if (baseModule2.getModuleIndex() == 165) {
                        changeMode(163);
                    } else if ((!z2 || DataRepository.dataItemRunning().getUiStyle() != 3) && (!isUltraPixelOn || DataRepository.dataItemRunning().getLastUiStyle() != 3)) {
                        baseModule2.restartModule();
                    } else {
                        changeMode(baseModule2.getModuleIndex());
                    }
                    if (z2) {
                        topAlert.alertTopHint(0, componentUltraPixel.getUltraPixelOpenTip());
                    } else {
                        topAlert.alertTopHint(8, componentUltraPixel.getUltraPixelCloseTip());
                        topAlert.alertSwitchHint(1, componentUltraPixel.getUltraPixelCloseTip(), 3000);
                    }
                    if (baseModule2.getModuleIndex() == 167) {
                        StringBuilder sb = new StringBuilder(16);
                        sb.append(String.valueOf(z2));
                        sb.append(currentSupportUltraPixel);
                        MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.MANUAL, MistatsConstants.Manual.SUPERME_PIXEL, sb.toString());
                    }
                } else if (i == 3 && isUltraPixelOn) {
                    this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                    if (this.mRecordingClosedElements != null) {
                        restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL);
                    }
                    CameraSettings.switchOffUltraPixel();
                    if (DataRepository.dataItemRunning().getLastUiStyle() == 3) {
                        changeMode(baseModule2.getModuleIndex());
                    } else {
                        baseModule2.restartModule();
                    }
                    topAlert.alertTopHint(8, componentUltraPixel.getUltraPixelCloseTip());
                }
                ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                ModeProtocol.MiBeautyProtocol miBeautyProtocol2 = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                if (z2) {
                    if (ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_48M.equals(currentSupportUltraPixel) && bottomPopupTips2 != null) {
                        bottomPopupTips2.directHideTipImage();
                        bottomPopupTips2.directShowOrHideLeftTipImage(false);
                        bottomPopupTips2.hideQrCodeTip();
                    }
                    if (dualController != null) {
                        dualController.hideZoomButton();
                        return;
                    }
                    return;
                }
                boolean isBeautyPanelShow = miBeautyProtocol2 != null ? miBeautyProtocol2.isBeautyPanelShow() : false;
                if (bottomPopupTips2 != null && !isBeautyPanelShow) {
                    bottomPopupTips2.reInitTipImage();
                }
                if (dualController != null && !isBeautyPanelShow) {
                    if (moduleIndex != 167) {
                        dualController.showZoomButton();
                    }
                    if (topAlert != null) {
                        topAlert.clearAlertStatus();
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configTiltSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        BaseModule baseModule2 = baseModule.get();
        if (baseModule.isPresent() && (baseModule2 instanceof Camera2Module)) {
            DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
            boolean isSwitchOn = dataItemRunning.isSwitchOn("pref_camera_tilt_shift_mode");
            ComponentRunningTiltValue componentRunningTiltValue = dataItemRunning.getComponentRunningTiltValue();
            boolean z = false;
            if (i == 1) {
                if (!isSwitchOn) {
                    CameraStatUtils.trackTiltShiftChanged(ComponentRunningTiltValue.TILT_CIRCLE);
                    dataItemRunning.switchOn("pref_camera_tilt_shift_mode");
                    componentRunningTiltValue.setComponentValue(160, ComponentRunningTiltValue.TILT_CIRCLE);
                    configDocumentMode(3);
                    isSwitchOn = true;
                } else if (ComponentRunningTiltValue.TILT_CIRCLE.equals(componentRunningTiltValue.getComponentValue(160))) {
                    CameraStatUtils.trackTiltShiftChanged(ComponentRunningTiltValue.TILT_PARALLEL);
                    componentRunningTiltValue.setComponentValue(160, ComponentRunningTiltValue.TILT_PARALLEL);
                } else {
                    CameraStatUtils.trackTiltShiftChanged("off");
                    dataItemRunning.switchOff("pref_camera_tilt_shift_mode");
                    isSwitchOn = false;
                }
                Camera2Module camera2Module = (Camera2Module) baseModule.get();
                if (!isSwitchOn) {
                    z = true;
                }
                camera2Module.showOrHideChip(z);
            } else if (i != 2 && i == 3) {
                dataItemRunning.switchOff("pref_camera_tilt_shift_mode");
                isSwitchOn = false;
            }
            ((Camera2Module) baseModule.get()).onTiltShiftSwitched(isSwitchOn);
            EffectController.getInstance().setDrawTilt(isSwitchOn);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configTimerSwitch() {
        ComponentRunningTimer componentRunningTimer = DataRepository.dataItemRunning().getComponentRunningTimer();
        String nextValue = componentRunningTimer.getNextValue();
        CameraStatUtils.trackTimerChanged(nextValue);
        componentRunningTimer.setComponentValue(160, nextValue);
    }

    public void configUltraPixelPortrait(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
            boolean isSwitchOn = dataItemRunning.isSwitchOn("pref_camera_ultra_pixel_portrait_mode_key");
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (i == 1) {
                if (isSwitchOn) {
                    dataItemRunning.switchOff("pref_camera_ultra_pixel_portrait_mode_key");
                    topAlert.alertTopHint(8, R.string.ultra_pixel_portrait_hint);
                    this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                    restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT);
                } else {
                    dataItemRunning.switchOn("pref_camera_ultra_pixel_portrait_mode_key");
                    closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, 194, 196, 201, 239, 254);
                    dataItemRunning.setRecordingClosedElements(this.mRecordingClosedElements);
                    ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                    if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                        miBeautyProtocol.dismiss(2);
                    }
                    topAlert.alertTopHint(0, R.string.ultra_pixel_portrait_hint);
                }
                trackUltraPixelPortraitChanged(!isSwitchOn);
            } else if (i == 3 && isSwitchOn) {
                topAlert.alertTopHint(8, R.string.ultra_pixel_portrait_hint);
                this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                if (this.mRecordingClosedElements != null) {
                    restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT);
                }
                dataItemRunning.switchOff("pref_camera_ultra_pixel_portrait_mode_key");
            }
            baseModule.get().updatePreferenceInWorkThread(57);
            ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).updateTipImage();
            topAlert.updateConfigItem(215);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configVideoFast() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex != 169) {
                CameraStatUtils.trackVideoModeChanged(CameraSettings.VIDEO_SPEED_FAST);
                switchOffElementsSilent(216);
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                CameraSettings.setSuperEISEnabled(moduleIndex, false);
                singeSwitchVideoBeauty(false);
                if (CameraSettings.isMacroModeEnabled(moduleIndex)) {
                    DataRepository.dataItemRunning().getComponentRunningMacroMode().setSwitchOff(moduleIndex);
                }
                CameraSettings.setSubtitleEnabled(moduleIndex, false);
                CameraSettings.setVideoQuality8K(moduleIndex, false);
                changeMode(169);
                updateTipMessage(4, R.string.hint_fast_motion, 2);
                return;
            }
            hideTipMessage(R.string.hint_fast_motion);
            dataItemRunning.switchOff("pref_video_speed_fast_key");
            CameraStatUtils.trackVideoModeChanged("normal");
            DataRepository.dataItemGlobal().setCurrentMode(162);
            changeMode(162);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configVideoLogSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isProVideoLogOpen = CameraSettings.isProVideoLogOpen(moduleIndex);
            CameraSettings.setProVideoLog(!isProVideoLogOpen);
            MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.PROVIDEO, MistatsConstants.Manual.LOG, isProVideoLogOpen ? "off" : "on");
            if (!isProVideoLogOpen) {
                resetVideoFilter();
                CameraSettings.setVideoQuality8K(moduleIndex, false);
                DataRepository.dataItemConfig().getManuallyDualLens().setComponentValue(moduleIndex, ComponentManuallyDualLens.LENS_WIDE);
            }
            changeMode(moduleIndex);
        }
    }

    public /* synthetic */ void i(BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            configMoon(false);
            Log.d(TAG, "(moon_mode) config moon night");
            ((Camera2Module) baseModule).updateMoonNight();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0060, code lost:
        if (r14 == 206) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0075, code lost:
        if (r14 == 229) goto L_0x0073;
     */
    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void onConfigChanged(int i) {
        if (isAlive()) {
            ConfigChangeInterceptor configChangeInterceptor = this.mChangeInterceptor;
            if (configChangeInterceptor != null && configChangeInterceptor.consumeConfigChanged(i) && this.mChangeInterceptor.asBlocker()) {
                return;
            }
            if (SupportedConfigFactory.isMutexConfig(i)) {
                DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
                int[] iArr = SupportedConfigFactory.MUTEX_MENU_CONFIGS;
                boolean z = false;
                int i2 = 176;
                for (int i3 : iArr) {
                    if (!(i3 == i || ((i == 209 && i3 == 229) || (i == 229 && i3 == 209)))) {
                        if (i3 != 203) {
                            if (i3 != 206) {
                                if (i3 != 209) {
                                    if (i3 != 229) {
                                        if (!dataItemRunning.isSwitchOn(SupportedConfigFactory.getConfigKey(i3))) {
                                        }
                                        i2 = i3;
                                    } else if (!CameraSettings.isGradienterOn()) {
                                    }
                                } else if (CameraSettings.isUltraPixelOn()) {
                                    z = true;
                                    i2 = i3;
                                }
                            } else if (CameraSettings.isLiveShotOn()) {
                                if (i != 209) {
                                }
                            }
                            i2 = 176;
                        } else if (((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).isShowLightingView()) {
                            showOrHideLighting(false);
                        }
                    }
                }
                if (!z) {
                    if (i2 != 176) {
                        applyConfig(i2, 3);
                    }
                    applyConfig(i, 1);
                    return;
                }
                applyConfig(i, 1);
                if (i2 != 176) {
                    applyConfig(i2, 3);
                    return;
                }
                return;
            }
            applyConfig(i, 1);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void onShineDismiss(int i) {
        if (i == 1 || i == 2) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().getModuleIndex() != 162) {
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void onThermalNotification(int i) {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (!baseModule.isPresent()) {
                Log.w(TAG, "onThermalNotification current module is null");
                return;
            }
            BaseModule baseModule2 = baseModule.get();
            if (!baseModule2.isFrameAvailable() || baseModule2.isSelectingCapturedResult()) {
                Log.w(TAG, "onThermalNotification current module has not ready");
                return;
            }
            baseModule2.setThermalLevel(i);
            baseModule2.updatePreferenceInWorkThread(66);
            if (ThermalDetector.thermalConstrained(i)) {
                Log.w(TAG, "thermalConstrained");
                baseModule2.thermalConstrained();
            }
            ComponentConfigFlash componentFlash = DataRepository.dataItemConfig().getComponentFlash();
            if (componentFlash.isEmpty() || !componentFlash.isHardwareSupported()) {
                Log.w(TAG, "onThermalNotification don't support hardware flash");
                return;
            }
            String str = "0";
            if (ThermalDetector.thermalCloseFlash(i)) {
                Log.w(TAG, "thermalCloseFlash");
            } else if (!baseModule2.isThermalThreshold() || ((i != 2 || !CameraSettings.isFrontCamera()) && i != 3)) {
                str = "";
            } else {
                Log.w(TAG, "recording time is up to thermal threshold");
            }
            updateFlashModeAndRefreshUI(baseModule2, str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckAIWatermark(boolean z) {
        ModeProtocol.TopAlert topAlert;
        if (DataRepository.dataItemFeature().we()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                int moduleIndex = baseModule.get().getModuleIndex();
                ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
                boolean aIWatermarkEnable = componentRunningAIWatermark.getAIWatermarkEnable(moduleIndex);
                if (aIWatermarkEnable && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                    topAlert.alertSwitchHint(1, R.string.ai_watermark_title, 3000);
                }
                if (!z) {
                    boolean iWatermarkEnable = componentRunningAIWatermark.getIWatermarkEnable();
                    if (aIWatermarkEnable && iWatermarkEnable) {
                        ModeProtocol.AIWatermarkDetect aIWatermarkDetect = (ModeProtocol.AIWatermarkDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(254);
                        if (aIWatermarkDetect != null) {
                            aIWatermarkDetect.resetResult();
                        }
                        setWatermarkEnable(true);
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckAiScene() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && CameraSettings.getAiSceneOpen(baseModule.get().getModuleIndex()) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            topAlert.alertSwitchHint(1, R.string.pref_camera_front_ai_scene_entry_on, 3000);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckAutoZoom() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && CameraSettings.isAutoZoomEnabled(baseModule.get().getModuleIndex()) && DataRepository.dataItemFeature().Yb() && !isVideoRecoding(baseModule.get())) {
                topAlert.alertSwitchHint(2, R.string.autozoom_hint, 3000);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckAuxiliaryConfig() {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().isCreated()) {
                int moduleIndex = baseModule.get().getModuleIndex();
                if (moduleIndex == 180 || moduleIndex == 167) {
                    configAuxiliary(DataRepository.dataItemConfig().getComponentConfigAuxiliary().getComponentValue(moduleIndex));
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckCinematicAspectRatio(boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex != 171 && moduleIndex != 180 && moduleIndex != 162 && moduleIndex != 169) {
                return;
            }
            if (CameraSettings.isCinematicAspectRatioEnabled(moduleIndex)) {
                if (topAlert != null && !isVideoRecoding(baseModule.get())) {
                    topAlert.alertTopHint(0, R.string.config_name_mimovie, 3000);
                }
                if (bottomPopupTips == null) {
                    return;
                }
                if (moduleIndex == 180 || (moduleIndex == 162 && (isVideoRecoding(baseModule.get()) || !z))) {
                    bottomPopupTips.directlyHideTipsForce();
                } else {
                    bottomPopupTips.showTips(21, R.string.hint_mimovie, 6);
                }
            } else if (bottomPopupTips != null) {
                bottomPopupTips.directlyHideTips(R.string.hint_mimovie);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckColorEnhance() {
        ModeProtocol.TopAlert topAlert;
        if (DataRepository.dataItemFeature().supportColorEnhance()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                if (DataRepository.dataItemRunning().getComponentRunningColorEnhance().isEnabled(baseModule.get().getModuleIndex()) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                    topAlert.alertSwitchHint(1, R.string.pro_color_mode, 3000);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckDocumentMode() {
        if (DataRepository.dataItemFeature().ed()) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            if (topAlert != null) {
                Optional<BaseModule> baseModule = getBaseModule();
                if (baseModule.isPresent() && CameraSettings.isDocumentModeOn(baseModule.get().getModuleIndex())) {
                    topAlert.showDocumentStateButton(0);
                    getBaseModule().ifPresent(h.INSTANCE);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckEyeLight() {
        String eyeLightType = CameraSettings.getEyeLightType();
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (topAlert != null && bottomPopupTips != null && !"-1".equals(eyeLightType)) {
            topAlert.alertTopHint(0, R.string.eye_light);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckFocusPeakConfig() {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().isCreated() && baseModule.get().getModuleIndex() != 180 && DataRepository.dataItemRunning().isSwitchOn("pref_camera_peak_key")) {
                Log.d(TAG, "reCheckFocusPeakConfig: configFocusPeakSwitch");
                configFocusPeakSwitch(2);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckFrontBokenTip() {
        if (DataRepository.dataItemFeature().kd() && ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && "on".equals(DataRepository.dataItemConfig().getComponentBokeh().getComponentValue(baseModule.get().getModuleIndex()))) {
                updateTipMessage(4, R.string.bokeh_use_hint, 2);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckGradienter() {
        if (CameraSettings.isGradienterOn()) {
            configGradienterSwitch(2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckHandGesture() {
        ModeProtocol.TopAlert topAlert;
        if (getBaseModule().isPresent() && CameraSettings.isHandGestureOpen() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            topAlert.alertHandGestureHint(R.string.hand_gesture_tip);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckLighting() {
        String componentValue = DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171);
        if (!componentValue.equals("0")) {
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.BokehFNumberController bokehFNumberController = (ModeProtocol.BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210);
            if (!actionProcessing.isShowLightingView()) {
                actionProcessing.showOrHideLightingView();
            }
            if (bottomPopupTips != null) {
                bottomPopupTips.showCloseTip(2, true);
                bottomPopupTips.directHideTipImage();
            }
            if (bokehFNumberController != null) {
                bokehFNumberController.hideFNumberPanel(false);
            }
            setLighting(false, "0", componentValue, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckLiveShot() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            BaseModule baseModule2 = baseModule.get();
            if ((baseModule2.getModuleIndex() == 163 || baseModule2.getModuleIndex() == 165) && DataRepository.dataItemFeature().vd() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null && CameraSettings.isLiveShotOn()) {
                showLiveShotTip(topAlert, baseModule2.getBogusCameraId());
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckLiveVV() {
        ModeProtocol.TopAlert topAlert;
        ModeProtocol.BaseDelegate baseDelegate;
        if (DataRepository.dataItemFeature().wd()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().getModuleIndex() == 162 && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null && this.mActivity != null && (baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 65523) {
                topAlert.alertTopHint(0, R.string.vv_use_hint_text_title);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckMacroMode() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if ((baseModule2.getModuleIndex() == 163 || baseModule2.getModuleIndex() == 162 || baseModule2.getModuleIndex() == 165 || baseModule2.getModuleIndex() == 172) && !topAlert.isExtraMenuShowing() && CameraSettings.isMacroModeEnabled(baseModule2.getModuleIndex())) {
                    topAlert.alertMacroModeHint(0, R.string.macro_mode);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckMutexConfigs(int i) {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().isCreated()) {
                DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
                int[] iArr = SupportedConfigFactory.MUTEX_MENU_CONFIGS;
                for (int i2 : iArr) {
                    if (i2 != 203) {
                        if (i2 != 209 && dataItemRunning.isSwitchOn(SupportedConfigFactory.getConfigKey(i2))) {
                            applyConfig(i2, 2);
                        }
                    } else if (dataItemRunning.getComponentRunningLighting().isSwitchOn(i)) {
                        reCheckLighting();
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckMutexEarly(int i) {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        boolean isUltraPixelPortraitFrontOn = CameraSettings.isUltraPixelPortraitFrontOn();
        boolean aiSceneOpen = CameraSettings.getAiSceneOpen(i);
        if (isUltraPixelPortraitFrontOn && aiSceneOpen) {
            this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
            if (this.mRecordingClosedElements != null) {
                restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT);
            }
            dataItemRunning.switchOff("pref_camera_ultra_pixel_portrait_mode_key");
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckParameterDescriptionTip() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 167 || moduleIndex == 180) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (!topAlert.isExtraMenuShowing()) {
                    topAlert.alertParameterDescriptionTip(CameraSettings.isParameterDescriptionEnable() ? 0 : 8);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckParameterResetTip(boolean z) {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if ((moduleIndex != 167 && moduleIndex != 180) || (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) == null || topAlert.isExtraMenuShowing()) {
                return;
            }
            if (!isChangeManuallyParameters()) {
                topAlert.alertParameterResetTip(z, 8, R.string.reset_manually_parameter_hint);
            } else {
                topAlert.alertParameterResetTip(z, 0, R.string.reset_manually_parameter_hint);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckRaw() {
        int moduleIndex;
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (!baseModule.isPresent() || (moduleIndex = baseModule.get().getModuleIndex()) != 167 || topAlert.isExtraMenuShowing()) {
                return;
            }
            if (DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(moduleIndex)) {
                topAlert.alertVideoUltraClear(0, R.string.manually_raw_hint);
            } else {
                topAlert.alertVideoUltraClear(8, R.string.manually_raw_hint);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckSubtitleMode() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (CameraSettings.isSubtitleEnabled(baseModule2.getModuleIndex()) && !isVideoRecoding(baseModule2)) {
                    topAlert.alertSubtitleHint(0, R.string.pref_video_subtitle);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckSuperEIS() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null && getBaseModule().isPresent() && CameraSettings.isSuperEISEnabled(getBaseModule().get().getModuleIndex())) {
            topAlert.alertTopHint(0, R.string.super_eis, 3000);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckUltraPixel() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null && getBaseModule().isPresent() && CameraSettings.isUltraPixelOn()) {
            topAlert.alertTopHint(0, DataRepository.dataItemRunning().getComponentUltraPixel().getUltraPixelOpenTip());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckUltraPixelPortrait() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null && getBaseModule().isPresent() && CameraSettings.isUltraPixelPortraitFrontOn()) {
            topAlert.alertTopHint(0, R.string.ultra_pixel_portrait_hint);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckVideoBeautify() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().getModuleIndex() == 162 && !topAlert.isExtraMenuShowing() && CameraSettings.isFaceBeautyOn(162, null)) {
                List<ComponentDataItem> items = DataRepository.dataItemRunning().getComponentRunningShine().getItems();
                if (items == null || items.size() <= 1) {
                    topAlert.alertVideoBeautifyHint(0, R.string.video_beauty_tip);
                } else {
                    topAlert.alertVideoBeautifyHint(0, R.string.video_beauty_tip_beautification);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckVideoBeautyPipeline() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 162 || moduleIndex == 180 || moduleIndex == 183) {
                baseModule.get().restartModule();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckVideoLog() {
        int moduleIndex;
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && (moduleIndex = baseModule.get().getModuleIndex()) == 180 && CameraSettings.isProVideoLogOpen(moduleIndex) && !topAlert.isExtraMenuShowing()) {
            topAlert.alertVideoUltraClear(0, R.string.pref_camera_video_log_tips);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckVideoUltraClearTip() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 162 || moduleIndex == 169 || moduleIndex == 180) {
                CameraSize videoSize = ((VideoModule) baseModule.get()).getVideoSize();
                int i = videoSize.width;
                int i2 = videoSize.height;
                if (is4KQuality(i, i2)) {
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate == null || baseDelegate.getActiveFragment(R.id.bottom_action) != 65523) {
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        boolean isProVideoLogOpen = CameraSettings.isProVideoLogOpen(moduleIndex);
                        if (topAlert != null && !topAlert.isExtraMenuShowing() && !isProVideoLogOpen) {
                            topAlert.alertVideoUltraClear(0, is8KQuality(i, i2) ? R.string.video_ultra_clear_tip_8k : R.string.video_ultra_clear_tip);
                        }
                        if (DataRepository.dataItemGlobal().getBoolean(DataItemGlobal.DATA_COMMON_CAMCORDER_TIP_8K_MAX_VIDEO_DURATION_SHOWN, true)) {
                            DataRepository.dataItemGlobal().editor().putBoolean(DataItemGlobal.DATA_COMMON_CAMCORDER_TIP_8K_MAX_VIDEO_DURATION_SHOWN, false).apply();
                            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                            if (bottomPopupTips != null && is8KQuality(i, i2)) {
                                bottomPopupTips.showTips(22, R.string.camcorder_tip_8k_max_video_duration, 6);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(164, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void resetMeter(int i) {
        if (i == 167 || i == 180) {
            ComponentConfigMeter componentConfigMeter = DataRepository.dataItemConfig().getComponentConfigMeter();
            if (componentConfigMeter.isModified(i)) {
                componentConfigMeter.reset(i);
                ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(214);
                getBaseModule().ifPresent(r.INSTANCE);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void restoreAllMutexElement(String str) {
        int[] iArr = this.mRecordingClosedElements;
        if (iArr != null) {
            int[] iArr2 = new int[iArr.length];
            int i = 0;
            while (true) {
                int[] iArr3 = this.mRecordingClosedElements;
                if (i < iArr3.length) {
                    int i2 = iArr3[i];
                    if (i2 == 193) {
                        updateComponentFlash(null, false);
                        iArr2[i] = 10;
                    } else if (i2 == 194) {
                        updateComponentHdr(false);
                        iArr2[i] = 11;
                    } else if (i2 == 196) {
                        updateComponentFilter(false);
                        iArr2[i] = 2;
                    } else if (i2 == 201) {
                        updateAiScene(false);
                        iArr2[i] = 36;
                    } else if (i2 == 206) {
                        updateLiveShot(false);
                        if (str != SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL) {
                            iArr2[i] = 49;
                        } else {
                            iArr2[i] = 50;
                        }
                    } else if (i2 == 209) {
                        updateUltraPixel(false);
                        iArr2[i] = 50;
                    } else if (i2 == 212) {
                        updateComponentShine(false);
                        iArr2[i] = 2;
                    } else if (i2 == 237) {
                        updateRaw(false);
                        iArr2[i] = 44;
                    } else if (i2 == 239) {
                        updateComponentBeauty(false);
                        iArr2[i] = 13;
                    } else if (i2 == 253) {
                        updateAutoZoom(false);
                        iArr2[i] = 51;
                    } else if (i2 == 254) {
                        updateEyeLight(false);
                        iArr2[i] = 45;
                    } else {
                        throw new RuntimeException("unknown mutex element");
                    }
                    i++;
                } else {
                    this.mRecordingClosedElements = null;
                    getBaseModule().ifPresent(new g(iArr2));
                    return;
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void restoreMutexFlash(String str) {
        if (DataRepository.dataItemConfig().getComponentFlash().isClosed()) {
            updateComponentFlash(str, false);
            getBaseModule().ifPresent(m.INSTANCE);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void setEyeLight(String str) {
        CameraSettings.setEyeLight(str);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.showTips(10, EyeLightConstant.getString(str), 4);
        }
        getBaseModule().ifPresent(j.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void setFilter(int i) {
        int shaderEffect = CameraSettings.getShaderEffect();
        persistFilter(i);
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if ((i == 0 || shaderEffect == 0) && shaderEffect != i && currentMode == 180) {
            CameraSettings.setVideoQuality8K(currentMode, false);
            CameraSettings.setProVideoLog(false);
            reCheckVideoBeautyPipeline();
        }
        EffectController.getInstance().setInvertFlag(0);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (CameraSettings.isGroupShotOn()) {
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).configGroupSwitch(4);
            topAlert.refreshExtraMenu();
        }
        ModeProtocol.OnShineChangedProtocol onShineChangedProtocol = (ModeProtocol.OnShineChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(234);
        String str = TAG;
        Log.d(str, "setFilter: filterId = " + i + ", FilterProtocol = " + onShineChangedProtocol);
        String str2 = TAG;
        Log.d(str2, "onFilterChanged: category = " + FilterInfo.getCategory(i) + ", newIndex = " + FilterInfo.getIndex(i));
        if (onShineChangedProtocol != null) {
            onShineChangedProtocol.onShineChanged(false, 196);
        }
        if (CameraSettings.isUltraPixelFront32MPOn()) {
            configSwitchUltraPixel(3);
        }
        if (i != FilterInfo.FILTER_ID_NONE) {
            configDocumentMode(3);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void setKaleidoscope(String str) {
        ModeProtocol.KaleidoscopeProtocol kaleidoscopeProtocol = (ModeProtocol.KaleidoscopeProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(236);
        if (kaleidoscopeProtocol != null) {
            kaleidoscopeProtocol.onKaleidoscopeChanged(str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void setLighting(boolean z, String str, String str2, boolean z2) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        ModeProtocol.VerticalProtocol verticalProtocol = (ModeProtocol.VerticalProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(198);
        if (str.equals("0") || str2.equals("0")) {
            topAlert.updateConfigItem(203);
            boolean equals = str2.equals("0");
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            if (equals) {
                if (!z) {
                    topAlert.alertLightingTitle(true);
                }
                mainContentProtocol.lightingCancel();
            } else {
                topAlert.alertLightingTitle(false);
                mainContentProtocol.lightingStart();
                actionProcessing.setLightingViewStatus(true);
            }
        }
        bottomPopupTips.setLightingPattern(str2);
        verticalProtocol.setLightingPattern(str2);
        if (str2 == "0") {
            topAlert.alertLightingHint(-1);
            verticalProtocol.alertLightingHint(-1);
        }
        getBaseModule().ifPresent(t.INSTANCE);
        if (z2) {
            CameraStatUtils.trackLightingChanged(171, str2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void setWatermarkEnable(boolean z) {
        ModeProtocol.WatermarkProtocol watermarkProtocol;
        clearToast();
        showDualController(z);
        showWatermarkSample(z);
        showOrHideTipImage(z);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directShowOrHideLeftTipImage(z);
        }
        if (!z && (watermarkProtocol = (ModeProtocol.WatermarkProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(253)) != null) {
            watermarkProtocol.dismiss(2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showCloseTip(int i, boolean z) {
        ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).showCloseTip(i, z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showOrHideAIWatermark() {
        ModeProtocol.ManuallyAdjust manuallyAdjust;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            clearToast();
            if (baseModule.get().getModuleIndex() == 163 && (manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181)) != null) {
                manuallyAdjust.setManuallyVisible(0, 4, null);
            }
            ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
            ModeProtocol.WatermarkProtocol watermarkProtocol = (ModeProtocol.WatermarkProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(253);
            ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
            if (componentRunningAIWatermark.isClosed()) {
                componentRunningAIWatermark.setClosed(false);
                bottomMenuProtocol.expandAIWatermarkBottomMenu(componentRunningAIWatermark);
                if (watermarkProtocol != null) {
                    watermarkProtocol.show();
                } else {
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate != null) {
                        baseDelegate.delegateEvent(21);
                    }
                }
                CameraStatUtils.trackAIWatermarkClick(MistatsConstants.AIWatermark.AI_WATERMARK_LIST_SHOW);
                return;
            }
            componentRunningAIWatermark.setClosed(true);
            if (watermarkProtocol != null) {
                watermarkProtocol.dismiss(2);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showOrHideFilter() {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) != null) {
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            boolean isShowLightingView = actionProcessing.isShowLightingView();
            boolean showOrHideFilterView = actionProcessing.showOrHideFilterView();
            ModeProtocol.BokehFNumberController bokehFNumberController = (ModeProtocol.BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (showOrHideFilterView && isShowLightingView) {
                String componentValue = DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171);
                DataRepository.dataItemRunning().getComponentRunningLighting().setComponentValue(171, "0");
                setLighting(true, componentValue, "0", false);
                if (bottomPopupTips != null) {
                    bottomPopupTips.reInitTipImage();
                }
            }
            if (showOrHideFilterView && bokehFNumberController != null && currentMode == 171) {
                bokehFNumberController.showFNumberPanel(true);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (bottomPopupTips2 != null) {
                if (showOrHideFilterView) {
                    bottomPopupTips2.updateLeftTipImage();
                } else if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
                    bottomPopupTips2.updateLeftTipImage();
                }
                bottomPopupTips2.reConfigQrCodeTip();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showOrHideLighting(boolean z) {
        beautyMutexHandle();
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            boolean showOrHideLightingView = ((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).showOrHideLightingView();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.BokehFNumberController bokehFNumberController = (ModeProtocol.BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210);
            if (showOrHideLightingView) {
                Optional<BaseModule> baseModule = getBaseModule();
                if (baseModule.isPresent()) {
                    setLighting(false, "0", "0", false);
                    DataRepository.dataItemRunning().getComponentConfigFilter().reset(baseModule.get().getModuleIndex());
                    setFilter(FilterInfo.FILTER_ID_NONE);
                    topAlert.alertLightingTitle(true);
                    if (bokehFNumberController != null) {
                        bokehFNumberController.hideFNumberPanel(true);
                    }
                    bottomPopupTips.directHideTipImage();
                    topAlert.refreshExtraMenu();
                } else {
                    return;
                }
            } else {
                String componentValue = DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171);
                DataRepository.dataItemRunning().getComponentRunningLighting().setComponentValue(171, "0");
                setLighting(true, componentValue, "0", false);
                bottomPopupTips.reInitTipImage();
                topAlert.alertLightingTitle(false);
                if (bokehFNumberController != null) {
                    bokehFNumberController.showFNumberPanel(true);
                }
            }
            if (baseDelegate.getActiveFragment(R.id.bottom_action) == 251) {
                baseDelegate.delegateEvent(7);
            }
            if (z) {
                MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.PortraitAttr.VALUE_LIGHTING_OUT_BUTTON, null, null);
            }
            if (bottomPopupTips == null) {
                return;
            }
            if (showOrHideLightingView) {
                bottomPopupTips.showCloseTip(2, true);
            } else {
                bottomPopupTips.updateLeftTipImage();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showOrHideMimoji() {
        beautyMutexHandle();
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (baseDelegate != null && actionProcessing != null && bottomPopupTips != null) {
            if (actionProcessing.showOrHideMiMojiView()) {
                if (topAlert != null) {
                    topAlert.updateConfigItem(193);
                }
                if (bottomPopupTips != null) {
                    bottomPopupTips.directlyHideTips();
                }
            }
            bottomPopupTips.updateMimojiBottomTipImage();
            baseDelegate.delegateEvent(14);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01aa  */
    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showOrHideShine() {
        ModeProtocol.ManuallyAdjust manuallyAdjust;
        ModeProtocol.TopAlert topAlert;
        boolean z;
        boolean z2;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            boolean z3 = miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow();
            boolean isFaceBeautyOn = CameraSettings.isFaceBeautyOn(moduleIndex, null);
            ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
            if (moduleIndex == 162) {
                z = false;
            } else if (moduleIndex != 169) {
                if (moduleIndex == 183) {
                    if (CameraSettings.isMiLiveBeautyOpen()) {
                        z2 = true;
                    } else {
                        mutexBeautyBusiness(moduleIndex);
                        z2 = false;
                    }
                    CameraSettings.setLiveBeautyStatus(true ^ CameraSettings.isMiLiveBeautyOpen());
                    if (z2) {
                        CameraSettings.setFaceBeautyRatio("key_live_shrink_face_ratio", 40);
                        CameraSettings.setFaceBeautyRatio("key_live_enlarge_eye_ratio", 40);
                        CameraSettings.setFaceBeautyRatio("key_live_smooth_strength", 40);
                        CameraSettings.setShaderEffect(0);
                        ShineHelper.onBeautyChanged();
                        ShineHelper.onVideoFilterChanged();
                        changeMode(183);
                        return;
                    }
                    reCheckVideoBeautyPipeline();
                }
                if (!z3) {
                    ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    if (bottomPopupTips != null) {
                        bottomPopupTips.directlyHideTips();
                        bottomPopupTips.setPortraitHintVisible(8);
                        bottomPopupTips.hideTipImage();
                        bottomPopupTips.hideLeftTipImage();
                        bottomPopupTips.hideSpeedTipImage();
                        bottomPopupTips.hideCenterTipImage();
                        bottomPopupTips.directHideLeftImageIntro();
                        bottomPopupTips.directHideLyingDirectHint();
                        bottomPopupTips.reConfigQrCodeTip();
                    }
                    ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                    if (dualController != null) {
                        dualController.hideZoomButton();
                        if (!(moduleIndex == 171 || (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) == null)) {
                            topAlert.alertUpdateValue(2);
                        }
                    }
                    if (moduleIndex == 163) {
                        ModeProtocol.ManuallyAdjust manuallyAdjust2 = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
                        if (manuallyAdjust2 != null) {
                            manuallyAdjust2.setManuallyVisible(0, 4, null);
                        }
                    } else if (moduleIndex == 167) {
                        ModeProtocol.ManuallyAdjust manuallyAdjust3 = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
                        if (manuallyAdjust3 != null) {
                            manuallyAdjust3.setManuallyLayoutVisible(false);
                        }
                    } else if (moduleIndex == 171) {
                        ModeProtocol.BokehFNumberController bokehFNumberController = (ModeProtocol.BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210);
                        if (bokehFNumberController != null && bokehFNumberController.isFNumberVisible()) {
                            bokehFNumberController.hideFNumberPanel(false);
                        }
                    } else if (moduleIndex == 180 && moduleIndex == 180 && (manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181)) != null && !manuallyAdjust.isProVideoPannelHiding()) {
                        manuallyAdjust.hideManuallyParent();
                    }
                    ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).expandShineBottomMenu(componentRunningShine);
                    if (miBeautyProtocol != null) {
                        miBeautyProtocol.show();
                        return;
                    }
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate != null) {
                        baseDelegate.delegateEvent(2);
                        return;
                    }
                    return;
                } else if (miBeautyProtocol != null) {
                    miBeautyProtocol.dismiss(2);
                    return;
                } else {
                    return;
                }
            } else {
                closeVideoFast();
                z = true;
            }
            setUpdateTipState(212, true);
            if (!isFaceBeautyOn) {
                mutexBeautyBusiness(moduleIndex);
            } else {
                z = true;
                z3 = false;
            }
            if (componentRunningShine.isTopBeautyEntry()) {
                singeSwitchVideoBeauty(true ^ isFaceBeautyOn);
            } else {
                DataRepository.dataItemRunning().getComponentRunningShine().setVideoBeautySwitch(162, true ^ isFaceBeautyOn);
            }
            if (z) {
                CameraSettings.setFaceBeautySmoothLevel(0);
                CameraSettings.setShaderEffect(0);
                CameraSettings.setVideoBokehRatio(0.0f);
                ShineHelper.onBeautyChanged();
                ShineHelper.onVideoBokehRatioChanged();
                ShineHelper.onVideoFilterChanged();
                changeMode(162);
            } else {
                reCheckVideoBeautyPipeline();
            }
            if (componentRunningShine.isTopBeautyEntry()) {
                return;
            }
            if (!z3) {
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showSetting() {
        ActivityBase activityBase = this.mActivity;
        if (activityBase != null) {
            switchOffElementsSilent(216);
            Intent intent = new Intent();
            intent.setClass(activityBase, CameraPreferenceActivity.class);
            intent.putExtra(BasePreferenceActivity.FROM_WHERE, DataRepository.dataItemGlobal().getCurrentMode());
            if (DataRepository.dataItemGlobal().getIntentType() == 1) {
                intent.putExtra(CameraPreferenceActivity.IS_IMAGE_CAPTURE_INTENT, true);
            }
            intent.putExtra(a.zf, activityBase.getResources().getString(R.string.pref_camera_settings_category));
            if (activityBase.startFromKeyguard()) {
                intent.putExtra(a.mf, true);
            }
            activityBase.getIntent().removeExtra(CameraIntentManager.EXTRAS_CAMERA_FACING);
            activityBase.startActivity(intent);
            activityBase.setJumpFlag(2);
            trackGotoSettings();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void switchOffElementsSilent(int... iArr) {
        for (int i : iArr) {
            if (i == 209) {
                this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                if (this.mRecordingClosedElements != null) {
                    restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL);
                }
                CameraSettings.switchOffUltraPixel();
            } else if (i == 216) {
                ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                if (baseDelegate != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 65523) {
                    baseDelegate.delegateEvent(15);
                }
                DataRepository.dataItemConfig().getComponentConfigVideoQuality().setForceValueOverlay(null);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        this.mActivity = null;
        ModeCoordinatorImpl.getInstance().detachProtocol(164, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void updateASDForWatermark() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            baseModule.get().updatePreferenceInWorkThread(73);
        }
    }
}
