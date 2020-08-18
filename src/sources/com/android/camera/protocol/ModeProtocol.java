package com.android.camera.protocol;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSize;
import com.android.camera.Thumbnail;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.lisenter.IASDListener;
import com.android.camera.animation.AnimationComposite;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.TypeItem;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.ComponentManuallyET;
import com.android.camera.data.data.config.ComponentManuallyEV;
import com.android.camera.data.data.config.ComponentManuallyFocus;
import com.android.camera.data.data.config.ComponentManuallyISO;
import com.android.camera.data.data.config.ComponentManuallyWB;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.fragment.lifeCircle.BaseLifecycleListener;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.fragment.mimoji.MimojiInfo;
import com.android.camera.fragment.vv.VVItem;
import com.android.camera.module.impl.component.ILive;
import com.android.camera.module.loader.StartControl;
import com.android.camera.ui.FocusView;
import com.android.camera.ui.ObjectView;
import com.android.camera.watermark.WaterMarkData;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraHardwareFace;
import com.android.camera2.autozoom.AutoZoomCaptureResult;
import com.ss.android.vesdk.TERecorder;
import com.ss.android.vesdk.VECommonCallback;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public interface ModeProtocol {
    public static final int ADJUST_EV_START = 1;
    public static final int ADJUST_EV_STOP = 2;
    public static final int APPLY_EV_IMMEDIATELY = 3;
    public static final int EVENT_AI_WATERMARK = 21;
    public static final int EVENT_BEAUTY = 2;
    public static final int EVENT_BEAUTYLEVEL = 5;
    public static final int EVENT_BLANK_BEAUTY = 10;
    public static final int EVENT_EYE_POPU_TIP = 9;
    public static final int EVENT_FILTER = 1;
    public static final int EVENT_GIF_EDIT = 20;
    public static final int EVENT_INTENT_SHOT_FINISH = 6;
    public static final int EVENT_KALEIDOSCOPE = 18;
    public static final int EVENT_LIVE_REVIEW = 11;
    public static final int EVENT_LIVE_SPEED = 13;
    public static final int EVENT_LIVE_STICKER = 12;
    public static final int EVENT_MAKEUP = 3;
    public static final int EVENT_MIMOJI = 14;
    public static final int EVENT_MIMOJI_EMOTICON = 19;
    public static final int EVENT_RESTORE_BOTTOM_ACTION = 7;
    public static final int EVENT_RESTORE_FRAGMENT = 16;
    public static final int EVENT_STICKER = 4;
    public static final int EVENT_SUBTITLE = 17;
    public static final int EVENT_VV = 15;
    public static final int INDICATOR_FACE_VIEW = 1;
    public static final int INDICATOR_FOCUS_VIEW = 2;
    public static final int INDICATOR_OBJECT_VIEW = 3;
    public static final int PROTOCOL_ACTION = 161;
    public static final int PROTOCOL_ACTION_PROCESSING = 162;
    public static final int PROTOCOL_ACTION_TRACK = 186;
    public static final int PROTOCOL_AI_WATERMARK = 253;
    public static final int PROTOCOL_AI_WATERMARK_DETECT = 254;
    public static final int PROTOCOL_AUTO_ZOOM_MODULE = 215;
    public static final int PROTOCOL_AUTO_ZOOM_VIEW = 214;
    public static final int PROTOCOL_BACK_STACK = 171;
    public static final int PROTOCOL_BEAUTY_RECORDING = 173;
    public static final int PROTOCOL_BEAUTY_SHOW_STATUS = 213;
    public static final int PROTOCOL_BOKEH_F_NUMBER = 210;
    public static final int PROTOCOL_BOTTOM_MENU = 197;
    public static final int PROTOCOL_BOTTOM_POPUP_TIPS = 175;
    public static final int PROTOCOL_CAMERA_CLICK_OBSERVABLE = 227;
    public static final int PROTOCOL_CAMERA_MODULE_SPEC = 195;
    public static final int PROTOCOL_CONFIG_CHANGE = 164;
    public static final int PROTOCOL_DELEGATE = 160;
    public static final int PROTOCOL_DUAL_CONTROLLER = 182;
    public static final int PROTOCOL_EFFECT_CONTROL = 165;
    public static final int PROTOCOL_EV_CHANGED = 169;
    public static final int PROTOCOL_FACE_BEAUTY = 185;
    public static final int PROTOCOL_FILTER_SWITCHER = 183;
    public static final int PROTOCOL_FRONT_BEAUTY = 194;
    public static final int PROTOCOL_ID_CARD = 233;
    public static final int PROTOCOL_KALEIDOSCOPE = 236;
    public static final int PROTOCOL_KEY_EVENT = 239;
    public static final int PROTOCOL_LIVE_BEAUTY = 244;
    public static final int PROTOCOL_LIVE_CONFIG = 201;
    public static final int PROTOCOL_LIVE_CONFIG_VV = 228;
    public static final int PROTOCOL_LIVE_FILTER = 243;
    public static final int PROTOCOL_LIVE_MUSIC = 211;
    public static final int PROTOCOL_LIVE_SPEED = 232;
    public static final int PROTOCOL_LIVE_STATE = 245;
    public static final int PROTOCOL_LIVE_VIDEO_EDITOR = 209;
    public static final int PROTOCOL_LIVE_VV_CHOOSER = 229;
    public static final int PROTOCOL_LIVE_VV_PROCESS = 230;
    public static final int PROTOCOL_MAGNETIC_SENSOR_DETECT = 2576;
    public static final int PROTOCOL_MAIN_CONTENT = 166;
    public static final int PROTOCOL_MAKEUP_CONTROL = 180;
    public static final int PROTOCOL_MANUALLY_ADJUST = 181;
    public static final int PROTOCOL_MANUALLY_CHANGE = 174;
    public static final int PROTOCOL_MIMOJI = 217;
    public static final int PROTOCOL_MIMOJI2 = 246;
    public static final int PROTOCOL_MIMOJI_ALERT = 226;
    public static final int PROTOCOL_MIMOJI_BOTTOM_LIST = 248;
    public static final int PROTOCOL_MIMOJI_DATA_BASE = 225;
    public static final int PROTOCOL_MIMOJI_EDITOR = 224;
    public static final int PROTOCOL_MIMOJI_EDITOR2 = 247;
    public static final int PROTOCOL_MIMOJI_EMOTICON = 250;
    public static final int PROTOCOL_MIMOJI_FULL_SCREEN = 249;
    public static final int PROTOCOL_MIMOJI_GIF_EDITOR = 251;
    public static final int PROTOCOL_MIMOJI_GIF_RECORD = 237;
    public static final int PROTOCOL_MIMOJI_VIDEO_EDITOR = 252;
    public static final int PROTOCOL_MI_ASD_DETECT = 235;
    public static final int PROTOCOL_MI_LIVE_CONFIG = 241;
    public static final int PROTOCOL_MI_LIVE_PLAYER = 242;
    public static final int PROTOCOL_MODE_CHANGE_CONTROLLER = 179;
    public static final int PROTOCOL_ON_FACE_BEAUTY_CHANGED = 199;
    public static final int PROTOCOL_ON_SHINE_CHANGED = 234;
    public static final int PROTOCOL_OPTICAL_ZOOM_RATIO = 203;
    public static final int PROTOCOL_PANORAMA = 176;
    public static final int PROTOCOL_PLAY_VIDEO = 167;
    public static final int PROTOCOL_PREVIEW_CHANGED = 168;
    public static final int PROTOCOL_RECORDING_STATE = 212;
    public static final int PROTOCOL_SCREEN_LIGHT = 196;
    public static final int PROTOCOL_SNAP_INDICATOR = 184;
    public static final int PROTOCOL_STANDALONE_MACRO = 202;
    public static final int PROTOCOL_STICKER_CONTROL = 178;
    public static final int PROTOCOL_SUBTITLE = 231;
    public static final int PROTOCOL_TOP_ALERT = 172;
    public static final int PROTOCOL_TOP_CONFIG = 193;
    public static final int PROTOCOL_ULTRA_WIDE = 200;
    public static final int PROTOCOL_VERTICAL = 198;
    public static final int PROTOCOL_WIDE_SELFIE = 216;
    public static final int PROTOCOL_ZOOM_CHANGED = 170;
    public static final int SHOW_FAIL = 3;
    public static final int SHOW_START = 1;
    public static final int SHOW_SUCCESS = 2;

    public interface AIWatermarkDetect extends BaseProtocol {
        public static final int TYPE_TAG = 254;

        void onASDChange(int i);

        void resetResult();

        void setListener(IASDListener iASDListener);
    }

    public interface ActionProcessing extends BaseProtocol {
        public static final int TYPE_TAG = 162;

        void entryOrExitMiMojiGif(boolean z);

        void filterUiChange();

        boolean forceSwitchFront();

        void hideExtra();

        boolean isShowFilterView();

        boolean isShowLightingView();

        void processingAudioCapture(boolean z);

        void processingFailed();

        void processingFinish();

        void processingMimojiBack();

        void processingMimojiPrepare();

        void processingPause();

        void processingPostAction();

        void processingPrepare();

        void processingResume();

        void processingStart();

        void processingWorkspace(boolean z);

        void setLightingViewStatus(boolean z);

        void showCameraPicker(boolean z);

        void showOrHideBottomViewWithAnim(boolean z);

        boolean showOrHideFilterView();

        boolean showOrHideLightingView();

        void showOrHideLoadingProgress(boolean z);

        boolean showOrHideMiMojiView();

        void showOrHideMimojiProgress(boolean z);

        void updateLoading(boolean z);

        void updateRecordingTime(String str);

        void updateThumbnail(Thumbnail thumbnail, boolean z, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AdjustEvState {
    }

    public interface AutoZoomModuleProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 215;

        void onTrackLost();

        void onTrackLosting();

        void setAutoZoomMode(int i);

        void setAutoZoomStartCapture(RectF rectF);

        void setAutoZoomStopCapture(int i);

        void startAutoZoom();

        void startTracking(RectF rectF);

        void stopAutoZoom();

        void stopTracking(int i);
    }

    public interface AutoZoomViewProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 214;

        void feedData(AutoZoomCaptureResult autoZoomCaptureResult);

        boolean isAutoZoomActive();

        boolean isAutoZoomEnabled();

        void onAutoZoomStarted();

        void onAutoZoomStopped();

        void onTrackingStarted(RectF rectF);

        void onTrackingStopped(int i);
    }

    public interface BackStack extends BaseProtocol {
        public static final int TYPE_TAG = 171;

        <P extends HandleBackTrace> void addInBackStack(P p);

        boolean handleBackStackFromKeyBack();

        void handleBackStackFromShutter();

        boolean handleBackStackFromTapDown(int i, int i2);

        <P extends HandleBackTrace> void removeBackStack(P p);
    }

    public interface BaseDelegate extends BaseProtocol {
        public static final int TYPE_TAG = 160;

        void delegateEvent(int i);

        Disposable delegateMode(@Nullable Completable completable, StartControl startControl, BaseLifecycleListener baseLifecycleListener);

        int getActiveFragment(@IdRes int i);

        AnimationComposite getAnimationComposite();

        int getOriginalFragment(@IdRes int i);

        void init(FragmentManager fragmentManager, int i, BaseLifecycleListener baseLifecycleListener);
    }

    public interface BaseProtocol {
        public static final int TYPE_TAG = -1;

        void registerProtocol();

        void unRegisterProtocol();
    }

    public interface BeautyRecording extends BaseProtocol {
        public static final int TYPE_TAG = 173;

        <P extends HandleBeautyRecording> void addBeautyStack(P p);

        void handleAngleChang(float f2);

        void handleBeautyRecordingStart();

        void handleBeautyRecordingStop();

        <P extends HandleBeautyRecording> void removeBeautyStack(P p);
    }

    public interface BokehFNumberController extends BaseProtocol {
        public static final int TYPE_TAG = 210;

        int getBokehFButtonHeight();

        void hideFNumberPanel(boolean z);

        boolean isFNumberVisible();

        void showFNumberPanel(boolean z);

        void updateFNumberValue();

        int visibleHeight();
    }

    public interface BottomMenuProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 197;

        void animateShineBeauty(boolean z);

        void expandAIWatermarkBottomMenu(ComponentRunningAIWatermark componentRunningAIWatermark);

        void expandShineBottomMenu(ComponentRunningShine componentRunningShine);

        int getBeautyActionMenuType();

        void onBottomMenuAnimate(int i, int i2);

        void onSwitchBeautyActionMenu(int i);

        void onSwitchCameraActionMenu(int i);

        void onSwitchCameraPicker();

        void onSwitchKaleidoscopeActionMenu(int i);

        void onSwitchLiveActionMenu(int i);
    }

    public interface BottomPopupTips extends BaseProtocol, LyingDirectHint {
        public static final int FILTER_CLOSE_TYPE = 1;
        public static final int LIGHT_CLOSE_TYPE = 2;
        public static final int NONE_CLOSE_TYPE = 0;
        public static final int TIP_DURATION_2S = 5;
        public static final int TIP_DURATION_3S = 6;
        public static final int TIP_DURATION_6S = 7;
        public static final int TIP_DURATION_LONG = 2;
        public static final int TIP_DURATION_PERSISTED = 4;
        public static final int TIP_DURATION_SHORT = 1;
        public static final int TIP_DURATION_STABLE = 3;
        public static final int TIP_SHOW_ALL = 0;
        public static final int TIP_SHOW_LANDSCAPE = 1;
        public static final int TIP_SHOW_PORTRAIT = 2;
        public static final int TIP_TYPE_3A_LOCK = 8;
        public static final int TIP_TYPE_AI_FOOD = 20;
        public static final int TIP_TYPE_CINEMATIC_ASPECT_RATIO = 21;
        public static final int TIP_TYPE_DUAL_CAMERA = 6;
        public static final int TIP_TYPE_DUAL_CAMERA_SUCCESS = 7;
        public static final int TIP_TYPE_EYE_LIGHT = 10;
        public static final int TIP_TYPE_GROUP_SWITCH = 17;
        public static final int TIP_TYPE_HAND_GESTURE = 16;
        public static final int TIP_TYPE_HINT = 4;
        public static final int TIP_TYPE_LIGHTING = 12;
        public static final int TIP_TYPE_MACRO_MODE = 18;
        public static final int TIP_TYPE_MAX_VIDEO_DURATION = 22;
        public static final int TIP_TYPE_MIMOJI = 19;
        public static final int TIP_TYPE_NEW_SLOW_MOTION = 9;
        public static final int TIP_TYPE_SUPER_NIGHT = 11;
        public static final int TIP_TYPE_ULTRA_WIDE = 13;
        public static final int TIP_TYPE_ULTRA_WIDE_RECOMMEND = 14;
        public static final int TIP_TYPE_WARNING = 5;
        public static final int TIP_ULTRA_PIXEL_NO_SUPPORT_ZOOM = 15;
        public static final int TYPE_TAG = 175;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CloseType {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface TipDuration {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface TipShowOrientation {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface TipType {
        }

        boolean containTips(@StringRes int i);

        void directHideCenterTipImage();

        void directHideLeftImageIntro();

        void directHideTipImage();

        void directShowLeftImageIntro();

        void directShowOrHideLeftTipImage(boolean z);

        void directlyHideTips();

        void directlyHideTips(@StringRes int i);

        void directlyHideTipsForce();

        void directlyShowTips(int i, @StringRes int i2);

        String getCurrentBottomTipMsg();

        int getCurrentBottomTipType();

        void hideCenterTipImage();

        void hideLeftTipImage();

        void hideMimojiTip();

        void hideQrCodeTip();

        void hideSpeedTipImage();

        void hideTipImage();

        boolean isLightingHintVisible();

        boolean isPortraitHintVisible();

        boolean isQRTipVisible();

        boolean isTipShowing();

        void reConfigBottomTipOfUltraWide();

        boolean reConfigQrCodeTip();

        void reInitTipImage();

        void selectBeautyTipImage(boolean z);

        void setLightingPattern(String str);

        void setPortraitHintVisible(int i);

        void showCloseTip(int i, boolean z);

        void showIDCardTip(boolean z);

        void showMimojiPanel(int i);

        void showMimojiTip();

        void showOrHideCloseImage(boolean z);

        void showOrHideMimojiPanel();

        void showOrHideVideoBeautyPanel();

        void showQrCodeTip();

        void showTips(int i, @StringRes int i2, int i3);

        void showTips(int i, @StringRes int i2, int i3, int i4);

        void showTips(int i, String str, int i2);

        void showTipsWithOrientation(int i, @StringRes int i2, int i3, int i4, int i5);

        void updateLeftTipImage();

        void updateMimojiBottomTipImage();

        void updateTipBottomMargin(int i, boolean z);

        void updateTipImage();
    }

    public interface CameraAction extends BaseProtocol {
        public static final int TYPE_TAG = 161;

        boolean isBlockSnap();

        boolean isDoingAction();

        boolean isRecording();

        void onReviewCancelClicked();

        void onReviewDoneClicked();

        void onShutterButtonClick(int i);

        void onShutterButtonFocus(boolean z, int i);

        boolean onShutterButtonLongClick();

        void onShutterButtonLongClickCancel(boolean z);

        void onThumbnailClicked(View view);
    }

    public interface CameraActionTrack extends BaseProtocol {
        public static final int TYPE_TAG = 186;

        void onTrackShutterButtonMissTaken(long j);

        void onTrackShutterButtonTaken(long j);
    }

    public interface CameraClickObservable extends BaseProtocol {
        public static final int OBSERVABLE_AI_SCENE = 166;
        public static final int OBSERVABLE_CINEMATIC_ASPECT_RATIO = 169;
        public static final int OBSERVABLE_FLASH = 161;
        public static final int OBSERVABLE_FOCUS = 165;
        public static final int OBSERVABLE_HDR = 162;
        public static final int OBSERVABLE_LIVEPHOTO = 163;
        public static final int OBSERVABLE_MORE = 164;
        public static final int OBSERVABLE_SAT = 167;
        public static final int OBSERVABLE_ZOOM = 168;
        public static final int OBSERVER_ASD_RCOMM = 161;
        public static final int TYPE_TAG = 227;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ClickObservableType {
        }

        public interface ClickObserver {
            void action();

            int getObserver();
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface ClickObserverType {
        }

        void addObservable(int[] iArr, ClickObserver clickObserver, int... iArr2);

        void subscribe(int i);
    }

    public interface CameraModuleSpecial extends BaseProtocol {
        public static final int TYPE_TAG = 195;

        void showOrHideChip(boolean z);

        void showQRCodeResult();
    }

    public interface ConfigChanges extends BaseProtocol {
        public static final int CHECK_TYPE_FORCE_CLOSE = 4;
        public static final int CHECK_TYPE_MANUALLY = 1;
        public static final int CHECK_TYPE_MUTEX = 3;
        public static final int CHECK_TYPE_WATCH = 2;
        public static final int TYPE_TAG = 164;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ConfigCheckType {
        }

        void closeMutexElement(String str, int... iArr);

        void configAuxiliary(String str);

        void configBackSoftLightSwitch(String str);

        void configBeautySwitch(int i);

        void configBokeh(String str);

        void configCinematicAspectRatio(int i);

        void configDualWaterMarkSwitch();

        void configFlash(String str);

        void configFocusPeakSwitch(int i);

        void configGenderAgeSwitch(int i);

        void configGradienterSwitch(int i);

        void configGroupSwitch(int i);

        void configHHTSwitch(int i);

        void configHdr(String str);

        void configLiveReview();

        void configLiveShotSwitch(int i);

        void configLiveVV(VVItem vVItem, boolean z, boolean z2);

        void configMagicFocusSwitch();

        void configMagicMirrorSwitch(int i);

        void configMeter(String str);

        void configPortraitSwitch(int i);

        void configRGBHistogramSwitch(int i);

        void configRawSwitch(int i);

        void configRotationChange(int i, int i2);

        void configScene(int i);

        void configSuperResolutionSwitch(int i);

        void configSwitchUltraPixel(int i);

        void configTiltSwitch(int i);

        void configTimerSwitch();

        void configVideoFast();

        void configVideoLogSwitch(int i);

        void onConfigChanged(int i);

        void onShineDismiss(int i);

        void onThermalNotification(int i);

        void reCheckAIWatermark(boolean z);

        void reCheckAiScene();

        void reCheckAutoZoom();

        void reCheckAuxiliaryConfig();

        void reCheckCinematicAspectRatio(boolean z);

        void reCheckColorEnhance();

        void reCheckDocumentMode();

        void reCheckEyeLight();

        void reCheckFocusPeakConfig();

        void reCheckFrontBokenTip();

        void reCheckGradienter();

        void reCheckHandGesture();

        void reCheckLighting();

        void reCheckLiveShot();

        void reCheckLiveVV();

        void reCheckMacroMode();

        void reCheckMutexConfigs(int i);

        void reCheckMutexEarly(int i);

        void reCheckParameterDescriptionTip();

        void reCheckParameterResetTip(boolean z);

        void reCheckRaw();

        void reCheckSubtitleMode();

        void reCheckSuperEIS();

        void reCheckUltraPixel();

        void reCheckUltraPixelPortrait();

        void reCheckVideoBeautify();

        void reCheckVideoBeautyPipeline();

        void reCheckVideoLog();

        void reCheckVideoUltraClearTip();

        void resetMeter(int i);

        void restoreAllMutexElement(String str);

        void restoreMutexFlash(String str);

        void setEyeLight(String str);

        void setFilter(int i);

        void setKaleidoscope(String str);

        void setLighting(boolean z, String str, String str2, boolean z2);

        void setWatermarkEnable(boolean z);

        void showCloseTip(int i, boolean z);

        void showOrHideAIWatermark();

        void showOrHideFilter();

        void showOrHideLighting(boolean z);

        void showOrHideMimoji();

        void showOrHideShine();

        void showSetting();

        void switchOffElementsSilent(int... iArr);

        void updateASDForWatermark();
    }

    public interface DualController extends BaseProtocol {
        public static final int TYPE_TAG = 182;

        void hideSlideView();

        void hideZoomButton();

        boolean isSlideVisible();

        boolean isZoomSliderViewIdle();

        boolean isZoomVisible();

        void setImmersiveModeEnabled(boolean z);

        void setRecordingOrPausing(boolean z);

        void showZoomButton();

        boolean updateSlideAndZoomRatio(int i);

        void updateZoomRatio(int i);

        int visibleHeight();
    }

    public interface EffectCropViewController {
        void destroyEffectCropView();

        void initEffectCropView();

        boolean isAutoZoomViewEnabled();

        boolean isEffectViewMoved();

        boolean isEffectViewVisible();

        boolean isZoomAdjustVisible();

        boolean onEffectViewTouchEvent(MotionEvent motionEvent);

        void removeTiltShiftMask();

        void setEffectViewVisible(boolean z);

        void setZoomViewVisible(boolean z, boolean z2);

        void updateEffectViewVisible();

        void updateEffectViewVisible(int i);
    }

    public interface EvChangedProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 169;

        void onEvChanged(int i, int i2);

        void onFocusAreaChanged(int i, int i2);

        void onMeteringAreaChanged(int i, int i2);

        void resetEvValue();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    public interface FilterProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 165;

        void onFilterChanged(int i, int i2);
    }

    public interface FullScreenProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 196;

        ContentValues getSaveContentValues();

        void hideScreenLight();

        boolean isLiveRecordPreviewShown();

        boolean isLiveRecordSaving();

        void onLiveSaveToLocalFinished(Uri uri);

        void onRecordSegmentCreated();

        void quitLiveRecordPreview(boolean z);

        void setScreenLightColor(int i);

        boolean showScreenLight();

        void startLiveRecordPreview(ContentValues contentValues);

        void startLiveRecordSaving();
    }

    public interface GestureDetected {
        void onPrepare();

        void onSliding(int i);

        void onTapCapture();

        void onTapDown(float f2, float f3);

        void onZoom(float f2);
    }

    public interface HandleBackTrace {
        public static final int CALLING_KEY_BACK = 1;
        public static final int CALLING_RECEIVER_NOT_SPECIFIED = 1;
        public static final int CALLING_RESTART_MODE = 7;
        public static final int CALLING_SELF = 5;
        public static final int CALLING_SHUTTER = 3;
        public static final int CALLING_SWITCH_MODE = 4;
        public static final int CALLING_TAP_DOWN = 2;
        public static final int CALLING_USER = 6;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CallingFrom {
        }

        boolean canProvide();

        boolean onBackEvent(int i);
    }

    public interface HandleBeautyRecording {
        void onAngleChanged(float f2);

        void onBeautyRecordingStart();

        void onBeautyRecordingStop();
    }

    public interface HandlerSwitcher extends BaseProtocol {
        public static final int TYPE_TAG = 183;

        boolean onHandleSwitcher(int i);
    }

    public interface IDCardModeProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 233;

        void callBackEvent();

        String getCurrentPictureName();

        void switchNextPage();
    }

    public interface IndicatorProtocol {
        void clearFocusView(int i);

        void clearIndicator(int i);

        int getActiveIndicator();

        CameraHardwareFace[] getFaces();

        RectF getFocusRect(int i);

        RectF getFocusRectInPreviewFrame();

        void initializeFocusView(FocusView.ExposureViewListener exposureViewListener);

        boolean initializeObjectTrack(RectF rectF, boolean z);

        boolean initializeObjectView(RectF rectF, boolean z);

        boolean isAdjustingObjectView();

        boolean isEvAdjusted(boolean z);

        boolean isFaceExists(int i);

        boolean isFaceStable(int i);

        boolean isFocusViewVisible();

        boolean isIndicatorVisible(int i);

        boolean isNeedExposure(int i);

        boolean isObjectTrackFailed();

        void lightingCancel();

        void lightingDetectFace(CameraHardwareFace[] cameraHardwareFaceArr, boolean z);

        void lightingFocused();

        void lightingStart();

        void mimojiEnd();

        void mimojiFaceDetect(int i);

        void mimojiStart();

        void onStopObjectTrack();

        void reShowFaceRect();

        void setActiveIndicator(int i);

        void setAfRegionView(MeteringRectangle[] meteringRectangleArr, Rect rect, float f2);

        void setCameraDisplayOrientation(int i);

        void setDisplaySize(int i, int i2);

        void setEvAdjustable(boolean z);

        boolean setFaces(int i, CameraHardwareFace[] cameraHardwareFaceArr, Rect rect, float f2);

        void setFocusViewPosition(int i, int i2, int i3);

        void setFocusViewType(boolean z);

        void setObjectViewListener(ObjectView.ObjectViewListener objectViewListener);

        void setPreviewSize(int i, int i2);

        void setShowGenderAndAge(boolean z);

        void setShowMagicMirror(boolean z);

        void setSkipDrawFace(boolean z);

        void showIndicator(int i, int i2);

        void updateFaceView(boolean z, boolean z2, boolean z3, boolean z4, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IndicatorType {
    }

    public interface KaleidoscopeProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 236;

        void onKaleidoscopeChanged(String str);
    }

    public interface KeyEvent extends BaseProtocol {
        public static final int TYPE_TAG = 239;

        boolean onKeyDown(int i, android.view.KeyEvent keyEvent);

        boolean onKeyUp(int i, android.view.KeyEvent keyEvent);
    }

    public interface LensProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 200;

        void onSwitchLens(boolean z, boolean z2);
    }

    public interface LiveAudioChanges extends BaseProtocol {
        public static final int TYPE_TAG = 211;

        void clearAudio();

        default String getAudioPath() {
            return null;
        }

        void setAudioPath(String str);
    }

    public interface LiveBeautyChanges extends BaseProtocol {
        public static final int TYPE_TAG = 244;
    }

    public interface LiveConfigChanges extends BaseProtocol, LiveSpeedChanges, LiveAudioChanges, LiveFilterChangers, LiveBeautyChanges, LiveRecordState {
        public static final int ACTIVATE_EXPIRED = 2;
        public static final int ACTIVATE_FAIL = 3;
        public static final int ACTIVATE_NOT_MATCH = 4;
        public static final int ACTIVATE_OK = 0;
        public static final int ACTIVATE_TBD = 1;
        public static final int TYPE_TAG = 201;

        @Retention(RetentionPolicy.SOURCE)
        public @interface AuthResult {
        }

        int getAuthResult();

        Pair<String, String> getConcatResult();

        SurfaceTexture getInputSurfaceTexture();

        void initPreview(int i, int i2, boolean z, int i3);

        void initResource();

        void onDeviceRotationChange(float[] fArr);

        boolean onRecordConcat(TERecorder.OnConcatFinishedListener onConcatFinishedListener);

        void onRecordPause();

        void onRecordResume();

        void onRecordReverse();

        void onRecordStart();

        void onRecordStop();

        void onSensorChanged(SensorEvent sensorEvent);

        void release();

        void setBeautify(boolean z, float f2);

        void setBeautyFaceReshape(boolean z, float f2, float f3);

        void setEffectAudio(boolean z);

        void startPreview(Surface surface);

        void updateRecordingTime();

        void updateRotation(float f2, float f3, float f4);
    }

    public interface LiveConfigVV extends BaseProtocol, LiveGenericControl, LiveRecordControl, LiveModuleSub, LiveVVExternal, Camera2Proxy.PreviewCallback {
        public static final int TYPE_TAG = 228;
    }

    public interface LiveEditor2 extends BaseProtocol {
        void combineVideoAudio(String str);

        void onDestroy();

        void pausePlay();

        void resumePlay();

        void startPlay();
    }

    public interface LiveFilterChangers extends BaseProtocol {
        public static final int TYPE_TAG = 243;

        void setFilter(boolean z, String str);
    }

    public interface LiveGenericControl extends BaseProtocol {
        CameraSize getAlgorithmPreviewSize(List<CameraSize> list);

        int getAuthResult();

        float getPreviewRatio();

        void initResource();

        void onOrientationChanged(int i, int i2, int i3);

        void prepare();

        void release();
    }

    public interface LiveModuleSub extends BaseProtocol {
        SurfaceTexture getInputSurfaceTexture();

        void initPreview(int i, int i2, int i3, CameraScreenNail cameraScreenNail);

        boolean onBackPressed();

        void trackVideoParams();
    }

    public interface LiveRecordControl extends BaseProtocol {
        public static final int STEP_IGNORE = 1;
        public static final int STEP_START = 2;
        public static final int STEP_STOP = 3;

        boolean canFinishRecording();

        void deleteLastFragment();

        int getNextRecordStep();

        boolean isRecording();

        boolean isRecordingPaused();

        void onRecordingNewFragmentFinished();

        void startRecordingNewFragment();

        void startRecordingNextFragment();

        void stopRecording();
    }

    public interface LiveRecordState extends BaseProtocol {
        public static final int TYPE_TAG = 245;

        boolean canRecordingStop();

        int getSegmentSize();

        boolean isRecording();

        boolean isRecordingPaused();
    }

    public interface LiveSpeedChanges extends BaseProtocol {
        public static final int TYPE_TAG = 232;

        float getRecordSpeed();

        default long getStartRecordingTime() {
            return 0;
        }

        default String getTimeValue() {
            return null;
        }

        default long getTotalRecordingTime() {
            return 0;
        }

        void setRecordSpeed(int i);
    }

    public interface LiveVVChooser extends BaseProtocol {
        public static final int TYPE_TAG = 229;

        void hide();

        boolean isPreviewShow();

        boolean isShow();

        void show(int i);

        void startShot();
    }

    public interface LiveVVExternal extends BaseProtocol {
        void combineVideoAudio(String str);

        String getSegmentPath(int i);

        void pausePlay();

        void prepare(VVItem vVItem);

        void resumePlay();

        void startPlay(Surface surface);
    }

    public interface LiveVVProcess extends BaseProtocol {
        public static final int TYPE_TAG = 230;

        ContentValues getSaveContentValues();

        void onCombinePrepare(ContentValues contentValues);

        void onKeyCodeCamera();

        void onLiveSaveToLocalFinished(Uri uri);

        void onRecordingFragmentUpdate(int i, long j);

        void onRecordingNewFragmentStart(int i, long j);

        void onResultCombineFinished(boolean z);

        void onResultPreviewFinished(boolean z);

        void prepare(VVItem vVItem);

        void processingFinish();

        void processingPause();

        void processingPrepare();

        void processingResume();

        void processingStart();

        void quit();

        void showExitConfirm();

        void updateRecordingTime(String str);
    }

    public interface LiveVideoEditor extends BaseProtocol {
        public static final int TYPE_TAG = 209;

        void combineVideoAudio(String str, VECommonCallback vECommonCallback, VECommonCallback vECommonCallback2);

        boolean init(TextureView textureView, String str, String str2, VECommonCallback vECommonCallback, VECommonCallback vECommonCallback2);

        void onDestory();

        void pausePlay();

        void resumePlay();

        void setRecordParameter(int i, int i2, int i3);

        void startPlay();
    }

    public interface LyingDirectHint {
        void directHideLyingDirectHint();

        void updateLyingDirectHint(boolean z, boolean z2);
    }

    public interface MagneticSensorDetect extends BaseProtocol {
        public static final int TYPE_TAG = 2576;

        boolean isLockHDRChecker();

        void onMagneticSensorChanged(SensorEvent sensorEvent);

        void recordMagneticInfo();

        void resetMagneticInfo();

        void updateMagneticDetection();

        void updatePreview(CaptureResult captureResult);
    }

    public interface MainContentProtocol extends BaseProtocol, EffectCropViewController, IndicatorProtocol, LyingDirectHint {
        public static final int TYPE_TAG = 166;

        void adjustHistogram(int i);

        List<WaterMarkData> getFaceWaterMarkInfos();

        String getVideoTagContent();

        void hideDelayNumber();

        void hideReviewViews();

        boolean isFaceLocationOK();

        boolean isFocusViewMoving();

        boolean isShowReviewViews();

        boolean isZoomViewMoving();

        boolean onViewTouchEvent(int i, MotionEvent motionEvent);

        void performHapticFeedback(int i);

        void processingFinish(boolean z);

        void processingPause();

        void processingResume();

        void processingStart(String str);

        void refreshHistogramStatsView();

        void setCenterHint(int i, String str, String str2, int i2);

        void setMimojiDetectTipType(int i);

        void setPreviewAspectRatio(float f2);

        void setWatermarkVisible(int i);

        void showDelayNumber(int i);

        void showReviewViews(Bitmap bitmap);

        void updateCinematicAspectRatioSwitched(boolean z);

        void updateContentDescription();

        void updateCurrentZoomRatio(float f2);

        void updateFocusMode(String str);

        void updateGradienterSwitched(boolean z);

        void updateHistogramStatsData(int[] iArr);

        void updateHistogramStatsData(int[] iArr, int[] iArr2, int[] iArr3);

        void updateMimojiFaceDetectResultTip(boolean z);

        void updateRGBHistogramSwitched(boolean z);

        void updateReferenceLineSwitched(boolean z);

        void updateWatermarkSample(WatermarkItem watermarkItem);

        void updateZoomRatio(float f2, float f3);
    }

    public interface MakeupProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 180;

        boolean isSeekBarVisible();

        void onMakeupItemSelected(String str, boolean z);
    }

    public interface ManuallyAdjust extends BaseProtocol {
        public static final int ADJUST_MANUAL = 1;
        public static final int ADJUST_NOT_SPECIFIED = -1;
        public static final int ADJUST_NULL = 0;
        public static final int ADJUST_PRO_VIDEO = 4;
        public static final int ADJUST_SCENE = 2;
        public static final int ADJUST_TILT = 3;
        public static final int MANUALLY_ANIMATE_IN = 1;
        public static final int MANUALLY_ANIMATE_OUT = 2;
        public static final int MANUALLY_GONE = 4;
        public static final int MANUALLY_INVISIBLE = 3;
        public static final int MANUALLY_NOT_SPECIFIED = -1;
        public static final int TYPE_TAG = 181;

        @Retention(RetentionPolicy.SOURCE)
        public @interface AdjustType {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface ManuallyVisible {
        }

        void hideManuallyParent();

        boolean isProVideoPannelHiding();

        void onRecordingStart();

        void onRecordingStop();

        void resetManually();

        void setManuallyLayoutVisible(boolean z);

        int setManuallyVisible(int i, int i2, ManuallyListener manuallyListener);

        int visibleHeight();
    }

    public interface ManuallyValueChanged extends BaseProtocol {
        public static final int TYPE_TAG = 174;

        void onBokehFNumberValueChanged(String str);

        void onDualLensSwitch(ComponentManuallyDualLens componentManuallyDualLens, int i);

        void onDualLensZooming(boolean z);

        void onDualZoomHappened(boolean z);

        void onDualZoomValueChanged(float f2, int i);

        void onETValueChanged(ComponentManuallyET componentManuallyET, String str);

        void onEVValueChanged(ComponentManuallyEV componentManuallyEV, String str);

        void onFocusValueChanged(ComponentManuallyFocus componentManuallyFocus, String str, String str2);

        void onISOValueChanged(ComponentManuallyISO componentManuallyISO, String str);

        void onWBValueChanged(ComponentManuallyWB componentManuallyWB, String str, boolean z);

        void resetManuallyParameters(List<ComponentData> list);

        void updateSATIsZooming(boolean z);
    }

    public interface MiAsdDetect extends BaseProtocol {
        public static final int TYPE_TAG = 235;

        void updateUltraWide(boolean z, int i);
    }

    public interface MiBeautyProtocol extends BaseProtocol {
        public static final int DISMISS_ALPHA = 3;
        public static final int DISMISS_SILENT = 1;
        public static final int DISMISS_SLIDE = 2;
        public static final int TYPE_TAG = 194;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissType {
        }

        void clearBeauty();

        void dismiss(int i);

        List<TypeItem> getSupportedBeautyItems(@ComponentRunningShine.ShineType String str);

        boolean isBeautyPanelShow();

        boolean isEyeLightShow();

        void resetBeauty();

        void show();

        void switchShineType(@ComponentRunningShine.ShineType String str, boolean z);
    }

    public interface MiLiveConfigChanges extends BaseProtocol, LiveGenericControl, LiveSpeedChanges, LiveAudioChanges, LiveFilterChangers, LiveBeautyChanges, MiLiveRecorderControl, LiveModuleSub, LiveRecordState, Camera2Proxy.PreviewCallback {
        public static final int TYPE_TAG = 241;
    }

    public interface MiLivePlayerControl extends BaseProtocol {
        public static final int TYPE_TAG = 242;

        ContentValues getSaveContentValues();

        boolean isShowing();

        void onLiveSaveToLocalFinished(Uri uri);

        void prepare(ContentValues contentValues, List<ILive.ILiveSegmentData> list, String str);

        void release();

        void show();

        void startLiveRecordSaving();
    }

    public interface MiLiveRecorderControl {
        public static final int STATE_IDLE = 0;
        public static final int STATE_PAUSED = 3;
        public static final int STATE_PREVIEW = 1;
        public static final int STATE_RECORDING = 2;

        public interface IRecorderListener {
            void onRecorderCancel();

            void onRecorderError();

            void onRecorderFinish(List<ILive.ILiveSegmentData> list, String str);

            void onRecorderPaused(List<ILive.ILiveSegmentData> list);
        }

        void deleteLastFragment();

        int getCurState();

        void onSurfaceTextureReleased();

        void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute);

        void pauseRecording();

        void resumeRecording();

        void setRecorderListener(IRecorderListener iRecorderListener);

        void startRecording();

        void stopRecording();
    }

    public interface MimojiAlert extends BaseProtocol {
        public static final int TYPE_TAG = 226;

        void firstProgressShow(boolean z);

        int refreshMimojiList();
    }

    public interface MimojiAvatarEngine extends BaseProtocol {
        public static final int TYPE_TAG = 217;

        void backToPreview(boolean z, boolean z2);

        void initAvatarEngine(int i, int i2, int i3, int i4, boolean z);

        boolean isOnCreateMimoji();

        boolean isRecordStopping();

        boolean isRecording();

        void onCaptureImage();

        boolean onCreateCapture();

        void onDeviceRotationChange(int i);

        void onDrawFrame(Rect rect, int i, int i2, boolean z);

        void onMimojiCreate();

        void onMimojiDeleted();

        void onMimojiInitFinish();

        void onMimojiSelect(MimojiInfo mimojiInfo);

        void onRecordStart(ContentValues contentValues);

        void onRecordStop(boolean z);

        void onResume();

        void release();

        void releaseRender();

        void setDetectSuccess(boolean z);

        void setDisableSingleTapUp(boolean z);
    }

    public interface MimojiEditor extends BaseProtocol {
        public static final int TYPE_TAG = 224;

        void directlyEnterEditMode(MimojiInfo mimojiInfo, int i);

        void goBack(boolean z, boolean z2);

        void onDeviceRotationChange(int i);

        void onTypeConfigSelect(int i);

        void releaseRender();

        void requestRender(boolean z);

        void resetClickEnable(boolean z);

        void resetConfig();

        void startMimojiEdit(boolean z, int i);
    }

    public interface MimojiGifEditor extends BaseProtocol {
        public static final int TYPE_TAG = 251;

        void completeVideoRecord(String str, boolean z);

        void operateGifPannelVisibleState(int i);

        void reconfigPreviewRadio(int i);
    }

    public interface ModeChangeController extends BaseProtocol {
        public static final int TYPE_TAG = 179;

        boolean canSwipeChangeMode();

        void changeCamera(View... viewArr);

        void changeModeByGravity(int i, int i2);

        void changeModeByNewMode(int i, int i2);
    }

    public interface ModeCoordinator {
        <P extends BaseProtocol> void attachProtocol(int i, P p);

        <P extends BaseProtocol> void detachProtocol(int i, P p);

        <P extends BaseProtocol> P getAttachProtocol(int i);
    }

    public interface ModuleFeature {
        String getFullFragmentAlias();

        String getModuleAlias();
    }

    public interface OnShineChangedProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 234;

        void onShineChanged(boolean z, int i);
    }

    public interface PanoramaProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 176;

        ViewGroup getPreivewContainer();

        void initPreviewLayout(int i, int i2, int i3, int i4);

        boolean isShown();

        void onCaptureOrientationDecided(int i, int i2, int i3);

        void onPreviewMoving();

        void requestRender();

        void resetShootUI();

        void setDirectionPosition(Point point, int i);

        void setDirectionTooFast(boolean z, int i);

        void setDisplayPreviewBitmap(Bitmap bitmap);

        void setShootUI();

        void showSmallPreview(boolean z);
    }

    public interface PlayVideoProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 167;

        void playVideo();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProtocolType {
    }

    public interface RecordState extends BaseProtocol {
        public static final int TYPE_TAG = 212;

        void onFailed();

        void onFinish();

        void onMimojiCreateBack();

        void onPause();

        void onPostPreview();

        void onPostPreviewBack();

        void onPostSavingFinish();

        void onPostSavingStart();

        void onPrepare();

        void onResume();

        void onStart();

        void prepareCreateMimoji();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowType {
    }

    public interface SnapShotIndicator extends BaseProtocol {
        public static final int TYPE_TAG = 184;

        void setSnapNumValue(int i);

        void setSnapNumVisible(boolean z, boolean z2);
    }

    public interface StickerProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 178;

        void onStickerChanged(String str);
    }

    public interface SubtitleRecording extends BaseProtocol {
        public static final int TYPE_TAG = 231;

        void checkNetWorkStatus();

        String getSubtitleContent();

        void handleSubtitleRecordingPause();

        void handleSubtitleRecordingResume();

        void handleSubtitleRecordingStart();

        void handleSubtitleRecordingStop();

        void initPermission();
    }

    public interface TopAlert extends BaseProtocol, LyingDirectHint {
        public static final int TYPE_TAG = 172;

        void alertAiDetectTipHint(int i, @StringRes int i2, long j);

        void alertAiSceneSelector(int i);

        void alertFlash(int i, String str, boolean z);

        void alertHDR(int i, boolean z, boolean z2);

        void alertHandGestureHint(@StringRes int i);

        void alertLightingHint(int i);

        void alertLightingTitle(boolean z);

        void alertLiveShotHint(int i, @StringRes int i2, long j);

        void alertMacroModeHint(int i, @StringRes int i2);

        void alertMimojiFaceDetect(boolean z, @StringRes int i);

        void alertMoonModeSelector(int i, boolean z);

        void alertMusicClose(boolean z);

        void alertParameterDescriptionTip(int i);

        void alertParameterResetTip(boolean z, int i, @StringRes int i2);

        void alertSubtitleHint(int i, @StringRes int i2);

        void alertSuperNightSeTip(int i);

        void alertSwitchHint(int i, @StringRes int i2, long j);

        void alertSwitchHint(int i, String str, long j);

        void alertTopHint(int i, @StringRes int i2);

        void alertTopHint(int i, @StringRes int i2, long j);

        void alertTopHint(int i, String str);

        void alertUpdateValue(int i);

        void alertVideoBeautifyHint(int i, @StringRes int i2);

        void alertVideoUltraClear(int i, @StringRes int i2);

        void clearAlertStatus();

        void clearVideoUltraClear();

        void disableMenuItem(boolean z, int... iArr);

        void enableMenuItem(boolean z, int... iArr);

        boolean getAlertIsShow();

        int getCurrentAiSceneLevel();

        boolean getUpdateTipState(int i);

        void hideAlert();

        void hideConfigMenu();

        void hideExtraMenu();

        void hideSwitchHint();

        void insertConfigItem(int i);

        boolean isContainAlertRecommendTip(@StringRes int... iArr);

        boolean isCurrentRecommendTipText(@StringRes int i);

        boolean isExtraMenuShowing();

        boolean isHDRShowing();

        boolean isShowBacklightSelector();

        boolean isTopToastShowing();

        void reInitAlert(boolean z);

        void refreshExtraMenu();

        void removeConfigItem(int i);

        void removeExtraMenu(int i);

        void setAiSceneImageLevel(int i);

        void setLiveShotHintVisibility(int i);

        void setRecordingTimeState(int i);

        void setUpdateTipState(int i, boolean z);

        void showConfigMenu();

        void showDocumentStateButton(int i);

        void startLiveShotAnimation();

        void updateConfigItem(int... iArr);

        void updateContentDescription();

        void updateRecordingTime(String str);
    }

    public interface TopConfigProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 193;

        default void onExtraMenuVisibilityChange(boolean z) {
        }

        void reShowMoon();

        void startAiLens();
    }

    public interface VerticalProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 198;

        void alertLightingHint(int i);

        boolean isAnyViewVisible();

        void setLightingPattern(String str);
    }

    public interface WatermarkProtocol extends BaseProtocol {
        public static final int DISMISS_ALPHA = 3;
        public static final int DISMISS_SILENT = 1;
        public static final int DISMISS_SLIDE = 2;
        public static final int KEEP = 0;
        public static final int TYPE_TAG = 253;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissType {
        }

        boolean dismiss(int i);

        boolean isWatermarkPanelShow();

        void show();

        void switchType(String str, boolean z);
    }

    public interface WideSelfieProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 216;

        void initPreviewLayout(int i, int i2, int i3, int i4);

        void requestRender();

        void resetShootUI();

        void setShootingUI();

        void updateHintText(@StringRes int i);

        void updatePreviewBitmap(Bitmap bitmap, Rect rect, Rect rect2);

        void updateThumbBackgroudLayout(boolean z, boolean z2, int i);
    }

    public interface ZoomProtocol extends BaseProtocol {
        public static final int TYPE_TAG = 170;

        float getMaxZoomRatio();

        float getMinZoomRatio();

        float getZoomRatio();

        void notifyDualZoom(boolean z);

        void notifyZooming(boolean z);

        void onZoomRatioChanged(float f2, int i);

        void onZoomSwitchCamera();

        void setMaxZoomRatio(float f2);

        void setMinZoomRatio(float f2);

        boolean zoomIn(float f2);

        boolean zoomOut(float f2);
    }
}
