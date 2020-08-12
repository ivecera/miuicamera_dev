package com.android.camera.data.data.runing;

import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.data.data.config.ComponentConfigFilter;
import com.android.camera.data.data.config.ComponentRunning8KVideoQuality;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import java.util.ArrayList;

public class DataItemRunning extends DataItemBase {
    public static final String DATA_CONFIG_ULTRA_PIXEL_PORTRAIT = "pref_camera_ultra_pixel_portrait_mode_key";
    public static final String DATA_RUNING_HAND_GESTURE = "pref_hand_gesture";
    public static final String DATA_RUNING_HAND_GESTURE_STATUS = "pref_hand_gesture_status";
    public static final String DATA_RUNNING_AI_WATERMARK = "pref_watermark_key";
    public static final String DATA_RUNNING_AUTO_ZOOM = "pref_camera_auto_zoom";
    public static final String DATA_RUNNING_CAMERA_GIF = "data_running_camera_gif";
    public static final String DATA_RUNNING_CAMERA_GIF_STATE = "data_running_camera_gif_state";
    public static final String DATA_RUNNING_COLOR_ENHANCE = "pref_color_enhance";
    public static final String DATA_RUNNING_DOCUMENT_MODE = "pref_document_mode_key";
    public static final String DATA_RUNNING_DOCUMENT_MODE_VALUE = "pref_document_mode_value_key";
    public static final String DATA_RUNNING_DUAL_VIDEO = "pref_dual_video_key";
    public static final String DATA_RUNNING_EXPOSURE_FEEDBACK = "pref_camera_exposure_feedback";
    public static final String DATA_RUNNING_EYE_LIGHT_TYPE = "pref_eye_light_type_key";
    public static final String DATA_RUNNING_FILTER_INDEX = "pref_camera_shader_coloreffect_key";
    public static final String DATA_RUNNING_FOCUS_PEAK = "pref_camera_peak_key";
    public static final String DATA_RUNNING_GENDER_AGE = "pref_camera_show_gender_age_key";
    public static final String DATA_RUNNING_GROUP_SELFIES = "pref_camera_groupshot_mode_key";
    public static final String DATA_RUNNING_HHT = "pref_camera_hand_night_key";
    public static final String DATA_RUNNING_KALEIDOSCOPE = "pref_kaleidoscope";
    public static final String DATA_RUNNING_LIVE_MUSIC_FIRST_REQUEST_TIME = "pref_key_live_music_first_request_time";
    public static final String DATA_RUNNING_LIVE_SHOT = "pref_live_shot_enabled";
    public static final String DATA_RUNNING_MAGIC_FOCUS = "pref_camera_ubifocus_key";
    public static final String DATA_RUNNING_MAGIC_MIRROR = "pref_camera_magic_mirror_key";
    public static final String DATA_RUNNING_MANUALLY = "pref_camera_manual_mode_key";
    public static final String DATA_RUNNING_PANORAMA_MOVE_DIRECTION = "pref_panorana_move_direction_key";
    public static final String DATA_RUNNING_PORTRAIT_LIGHTING = "pref_portrait_lighting";
    public static final String DATA_RUNNING_PORTRAIT_MODE = "pref_camera_portrait_mode_key";
    public static final String DATA_RUNNING_SCENE = "pref_camera_scenemode_setting_key";
    public static final String DATA_RUNNING_SCENE_VALUE = "pref_camera_scenemode_key";
    public static final String DATA_RUNNING_SQUARE_MODE = "pref_camera_square_mode_key";
    public static final String DATA_RUNNING_SUPER_EIS = "pref_camera_super_eis";
    public static final String DATA_RUNNING_SUPER_RESOLUTION = "pref_camera_super_resolution_key";
    public static final String DATA_RUNNING_TILT = "pref_camera_tilt_shift_mode";
    public static final String DATA_RUNNING_TILT_VALUE = "pref_camera_tilt_shift_key";
    public static final String DATA_RUNNING_TIMER = "pref_delay_capture_mode";
    public static final String DATA_RUNNING_ULTRA_PIXEL = "pref_ultra_pixel";
    public static final String DATA_RUNNING_ULTRA_WIDE_BOKEH = "pref_ultra_wide_bokeh_enabled";
    public static final String DATA_RUNNING_VIDEO_FAST = "pref_video_speed_fast_key";
    public static final String DATA_RUNNING_VIDEO_SUBTITLE = "pref_video_subtitle_key";
    private static final String KEY = "camera_running";
    private ComponentRunningTiltValue componentRunningTiltValue;
    private ComponentRunning8KVideoQuality mComponentConfig8KVideoQuality;
    private ComponentConfigFilter mComponentConfigFilter;
    private ComponentRunningAIWatermark mComponentRunningAIWatermark;
    private ComponentRunningAutoZoom mComponentRunningAutoZoom;
    private ComponentRunningColorEnhance mComponentRunningColorEnhance;
    private ComponentRunningDocument mComponentRunningDocument;
    private ComponentRunningDualVideo mComponentRunningDualVideo;
    private ComponentRunningEyeLight mComponentRunningEyeLight;
    private ComponentRunningKaleidoscope mComponentRunningKaleidoscope;
    private ComponentRunningLighting mComponentRunningLighting;
    private ComponentRunningLiveShot mComponentRunningLiveShot;
    private ComponentRunningMacroMode mComponentRunningMacroMode;
    private ComponentRunningSceneValue mComponentRunningSceneValue;
    private ComponentRunningShine mComponentRunningShine = new ComponentRunningShine(this);
    private ComponentRunningSubtitle mComponentRunningSubtitle;
    private ComponentRunningSuperEIS mComponentRunningSuperEIS;
    private ComponentRunningTimer mComponentRunningTimer;
    private ComponentRunningUltraPixel mComponentUltraPixel = new ComponentRunningUltraPixel(this);
    private int mLastUiStyle;
    private int[] mRecordingClosedElements;
    private boolean mSupportHandGesture;
    private boolean mSupportMacroMode;
    private boolean mSupportUltraPixelPortrait;
    private int mUiStyle;

    private ArrayList<ComponentDataItem> getAIWatermarkData() {
        ArrayList<ComponentDataItem> arrayList = new ArrayList<>();
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_general, String.valueOf(0)));
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_spots, String.valueOf(1)));
        if (DataRepository.dataItemFeature().c_35955_0x0002_OR_1() == 1) {
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_city, String.valueOf(4)));
        }
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_festival, String.valueOf(2)));
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_scene, String.valueOf(3)));
        return arrayList;
    }

    private void reCheckMutexEarly(int i) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.reCheckMutexEarly(i);
        }
    }

    public ComponentConfigFilter getComponentConfigFilter() {
        if (this.mComponentConfigFilter == null) {
            this.mComponentConfigFilter = new ComponentConfigFilter(this);
        }
        return this.mComponentConfigFilter;
    }

    public ComponentRunning8KVideoQuality getComponentRunning8KVideoQuality() {
        if (this.mComponentConfig8KVideoQuality == null) {
            this.mComponentConfig8KVideoQuality = new ComponentRunning8KVideoQuality(this);
        }
        return this.mComponentConfig8KVideoQuality;
    }

    public ComponentRunningAIWatermark getComponentRunningAIWatermark() {
        if (this.mComponentRunningAIWatermark == null) {
            this.mComponentRunningAIWatermark = new ComponentRunningAIWatermark(this, getAIWatermarkData());
        }
        return this.mComponentRunningAIWatermark;
    }

    public ComponentRunningAutoZoom getComponentRunningAutoZoom() {
        if (this.mComponentRunningAutoZoom == null) {
            this.mComponentRunningAutoZoom = new ComponentRunningAutoZoom(this);
        }
        return this.mComponentRunningAutoZoom;
    }

    public ComponentRunningColorEnhance getComponentRunningColorEnhance() {
        if (this.mComponentRunningColorEnhance == null) {
            this.mComponentRunningColorEnhance = new ComponentRunningColorEnhance(this);
        }
        return this.mComponentRunningColorEnhance;
    }

    public ComponentRunningDocument getComponentRunningDocument() {
        if (this.mComponentRunningDocument == null) {
            this.mComponentRunningDocument = new ComponentRunningDocument(this);
        }
        return this.mComponentRunningDocument;
    }

    public ComponentRunningEyeLight getComponentRunningEyeLight() {
        if (this.mComponentRunningEyeLight == null) {
            this.mComponentRunningEyeLight = new ComponentRunningEyeLight(this);
        }
        return this.mComponentRunningEyeLight;
    }

    public ComponentRunningKaleidoscope getComponentRunningKaleidoscope() {
        if (this.mComponentRunningKaleidoscope == null) {
            this.mComponentRunningKaleidoscope = new ComponentRunningKaleidoscope(this);
        }
        return this.mComponentRunningKaleidoscope;
    }

    public ComponentRunningLighting getComponentRunningLighting() {
        if (this.mComponentRunningLighting == null) {
            this.mComponentRunningLighting = new ComponentRunningLighting(this);
        }
        return this.mComponentRunningLighting;
    }

    public ComponentRunningLiveShot getComponentRunningLiveShot() {
        if (this.mComponentRunningLiveShot == null) {
            this.mComponentRunningLiveShot = new ComponentRunningLiveShot(this);
        }
        return this.mComponentRunningLiveShot;
    }

    public ComponentRunningMacroMode getComponentRunningMacroMode() {
        if (this.mComponentRunningMacroMode == null) {
            this.mComponentRunningMacroMode = new ComponentRunningMacroMode(this);
        }
        return this.mComponentRunningMacroMode;
    }

    public ComponentRunningSceneValue getComponentRunningSceneValue() {
        if (this.mComponentRunningSceneValue == null) {
            this.mComponentRunningSceneValue = new ComponentRunningSceneValue(this);
        }
        return this.mComponentRunningSceneValue;
    }

    public ComponentRunningShine getComponentRunningShine() {
        return this.mComponentRunningShine;
    }

    public ComponentRunningSubtitle getComponentRunningSubtitle() {
        if (this.mComponentRunningSubtitle == null) {
            this.mComponentRunningSubtitle = new ComponentRunningSubtitle(this);
        }
        return this.mComponentRunningSubtitle;
    }

    public ComponentRunningSuperEIS getComponentRunningSuperEIS() {
        if (this.mComponentRunningSuperEIS == null) {
            this.mComponentRunningSuperEIS = new ComponentRunningSuperEIS(this);
        }
        return this.mComponentRunningSuperEIS;
    }

    public ComponentRunningTiltValue getComponentRunningTiltValue() {
        if (this.componentRunningTiltValue == null) {
            this.componentRunningTiltValue = new ComponentRunningTiltValue(this);
        }
        return this.componentRunningTiltValue;
    }

    public ComponentRunningTimer getComponentRunningTimer() {
        if (this.mComponentRunningTimer == null) {
            this.mComponentRunningTimer = new ComponentRunningTimer(this);
        }
        return this.mComponentRunningTimer;
    }

    public ComponentRunningUltraPixel getComponentUltraPixel() {
        return this.mComponentUltraPixel;
    }

    public boolean getHandGestureRunning() {
        return getBoolean("pref_hand_gesture_status", false);
    }

    public int getLastUiStyle() {
        return this.mLastUiStyle;
    }

    public long getLiveMusicFirstRequestTime() {
        return getLong("pref_key_live_music_first_request_time", -1);
    }

    public int[] getRecordingClosedElements() {
        return this.mRecordingClosedElements;
    }

    public int getUiStyle() {
        return this.mUiStyle;
    }

    public String getVideoSpeed() {
        return isSwitchOn("pref_video_speed_fast_key") ? CameraSettings.VIDEO_SPEED_FAST : "normal";
    }

    public ComponentRunningDualVideo getmComponentRunningDualVideo() {
        if (this.mComponentRunningDualVideo == null) {
            this.mComponentRunningDualVideo = new ComponentRunningDualVideo(this);
        }
        return this.mComponentRunningDualVideo;
    }

    public boolean isSwitchOn(String str) {
        return getBoolean(str, false);
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public boolean isTransient() {
        return true;
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public String provideKey() {
        return KEY;
    }

    public void reInitComponent(int i, int i2, CameraCapabilities cameraCapabilities) {
        this.mComponentUltraPixel.reInit(i, i2, cameraCapabilities);
        this.mComponentRunningShine.reInit(i, i2, cameraCapabilities);
        getComponentRunningColorEnhance().reInit(i, i2, cameraCapabilities);
        getComponentRunningMacroMode().reInit(i2, DataRepository.dataItemGlobal().isNormalIntent());
        getComponentRunningAutoZoom().reInit(i2, DataRepository.dataItemGlobal().isNormalIntent());
        getComponentRunningSubtitle().reInit(i2, DataRepository.dataItemGlobal().isNormalIntent());
        getComponentRunningDocument().reInit(i2, DataRepository.dataItemGlobal().isNormalIntent());
        getComponentRunningSuperEIS().reInit(i2, DataRepository.dataItemGlobal().isNormalIntent());
        getComponentRunning8KVideoQuality().reInit(i, i2, cameraCapabilities);
    }

    public void reInitSupport(int i, int i2) {
        this.mSupportHandGesture = false;
        this.mSupportMacroMode = false;
        this.mSupportUltraPixelPortrait = false;
        if (i2 == 1 && ((i == 163 || i == 165 || i == 171) && b.pd())) {
            this.mSupportHandGesture = true;
        }
        if (DataRepository.dataItemFeature().isSupportMacroMode() && i2 == 0 && (163 == i || 165 == i || 162 == i || 169 == i || 172 == i)) {
            this.mSupportMacroMode = true;
        }
        if (i2 != 1) {
            return;
        }
        if ((i == 163 || i == 165) && !Util.isGlobalVersion()) {
            this.mSupportUltraPixelPortrait = true;
            reCheckMutexEarly(i);
        }
    }

    public void setHandGestureRunning(boolean z) {
        putBoolean("pref_hand_gesture_status", z);
    }

    public void setLiveMusicFirstRequestTime(long j) {
        putLong("pref_key_live_music_first_request_time", j);
    }

    public void setRecordingClosedElements(int[] iArr) {
        this.mRecordingClosedElements = iArr;
    }

    public void setUiStyle(int i) {
        Log.d("DataItemRunning", "setUiStyle: " + i);
        this.mLastUiStyle = this.mUiStyle;
        this.mUiStyle = i;
    }

    public boolean supportHandGesture() {
        return this.mSupportHandGesture;
    }

    public boolean supportMacroMode() {
        return (!this.mSupportMacroMode || !DataRepository.dataItemGlobal().isNormalIntent()) ? false : true;
    }

    public boolean supportPopShineEntry() {
        return this.mComponentRunningShine.supportPopUpEntry();
    }

    public boolean supportTopShineEntry() {
        return this.mComponentRunningShine.supportTopConfigEntry();
    }

    public boolean supportUltraPixel() {
        return !this.mComponentUltraPixel.isEmpty();
    }

    public boolean supportUltraPixelPortrait() {
        return this.mSupportUltraPixelPortrait;
    }

    public void switchOff(String str) {
        putBoolean(str, false);
    }

    public void switchOn(String str) {
        putBoolean(str, true);
    }

    public boolean triggerSwitchAndGet(String str) {
        if (isSwitchOn(str)) {
            switchOff(str);
            return false;
        }
        switchOn(str);
        return true;
    }
}
