package com.android.camera.data.data.global;

import android.content.Intent;
import android.support.v4.util.Pair;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.mi.config.a;
import com.mi.config.b;
import com.mi.config.c;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class DataItemGlobal extends DataItemBase {
    public static final int BACK_DISPLAY_MODE = 2;
    public static final String CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY = "can_connect_network";
    public static final String DATA_COMMON_AI_SCENE_HINT = "pref_camera_first_ai_scene_use_hint_shown_key";
    public static final String DATA_COMMON_CAMCORDER_TIP_8K_MAX_VIDEO_DURATION_SHOWN = "pref_camcorder_tip_8k_max_video_duration_shown";
    public static final String DATA_COMMON_CURRENT_CAMERA_ID = "pref_camera_id_key";
    public static final String DATA_COMMON_CURRENT_MODE = "pref_camera_mode_key_intent_";
    public static final String DATA_COMMON_CUSTOM_WATERMARK_VERSION = "pref_custom_watermark_version";
    public static final String DATA_COMMON_DEVICE_WATERMARK = "pref_dualcamera_watermark_key";
    public static final String DATA_COMMON_DOCUMENT_MODE_USE_HINT_SHOWN = "pref_document_use_hint_shown";
    public static final String DATA_COMMON_DUALCAMERA_USERDEFINE_WATERMARK = "user_define_watermark_key";
    public static final String DATA_COMMON_FIRST_USE_HINT = "pref_camera_first_use_hint_shown_key";
    public static final String DATA_COMMON_FOCUS_SHOOT = "pref_camera_focus_shoot_key";
    public static final String DATA_COMMON_FRONT_CAM_ROTATE_HINT = "pref_front_camera_first_use_hint_shown_key";
    public static final String DATA_COMMON_ID_CARD_MODE_HINT = "pref_camera_first_id_card_mode_use_hint_shown_key";
    public static final String DATA_COMMON_MACRO_MODE_HINT = "pref_camera_first_macro_mode_use_hint_shown_key";
    private static final String DATA_COMMON_OPEN_TIME = "pref_camera_open_time";
    public static final String DATA_COMMON_PORTRAIT_HINT = "pref_camera_first_portrait_use_hint_shown_key";
    public static final String DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_APP = "pref_camera_tiktok_more_show_app_key";
    public static final String DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_MARKET = "pref_camera_tiktok_more_show_market_key";
    public static final String DATA_COMMON_TIME_WATER_MARK = "pref_time_watermark_key";
    public static final String DATA_COMMON_ULTRA_TELE_HINT = "pref_camera_first_ultra_tele_use_hint_shown_key";
    public static final String DATA_COMMON_ULTRA_WIDE_HINT = "pref_camera_first_ultra_wide_use_hint_shown_key";
    public static final String DATA_COMMON_ULTRA_WIDE_SAT_HINT = "pref_camera_first_ultra_wide_sat_use_hint_shown_key";
    public static final String DATA_COMMON_VV_HINT = "pref_camera_first_vv_hint_shown_key";
    public static final int FRONT_DISPLAY_MODE = 1;
    public static final int INTENT_TYPE_IMAGE = 1;
    public static final int INTENT_TYPE_NORMAL = 0;
    public static final int INTENT_TYPE_SCAN_QR = 3;
    public static final int INTENT_TYPE_UNSPECIFIED = -1;
    public static final int INTENT_TYPE_VIDEO = 2;
    public static final int INTENT_TYPE_VOICE_CONTROL = 4;
    public static final String IS_FIRST_SHOW_VIDEOTAG = "first_show_videotag";
    public static final String KEY = "camera_settings_global";
    private static final String TAG = "DataItemGlobal";
    public static List<String> sUseHints = new ArrayList();
    private a mDataItemFeature;
    private int mIntentType = 0;
    private boolean mIsForceMainBackCamera;
    private Boolean mIsTimeOut;
    private int mLastCameraId;
    private boolean mMimojiStandAlone;
    private ComponentModuleList mModuleList;
    private boolean mRetriedIfCameraError;
    private boolean mStartFromKeyguard;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IntentType {
    }

    static {
        sUseHints.add("pref_camera_first_use_hint_shown_key");
        sUseHints.add("pref_camera_first_ai_scene_use_hint_shown_key");
        sUseHints.add("pref_camera_first_ultra_wide_use_hint_shown_key");
        sUseHints.add("pref_camera_first_portrait_use_hint_shown_key");
        sUseHints.add("pref_front_camera_first_use_hint_shown_key");
        sUseHints.add(DATA_COMMON_DOCUMENT_MODE_USE_HINT_SHOWN);
        sUseHints.add("pref_camera_recordlocation_key");
    }

    public DataItemGlobal(a aVar) {
        this.mDataItemFeature = aVar;
        this.mMimojiStandAlone = this.mDataItemFeature.Ed();
        this.mLastCameraId = getCurrentCameraId();
        this.mModuleList = new ComponentModuleList(this);
    }

    private boolean determineTimeOut() {
        if (CameraSettings.retainCameraMode()) {
            return false;
        }
        return isActualTimeOut();
    }

    private int getCurrentCameraId(int i) {
        if (!(i == 166 || i == 167 || i == 175)) {
            if (i == 176) {
                return 1;
            }
            if (i != 180) {
                switch (i) {
                    case 171:
                        if (this.mDataItemFeature.be()) {
                            return Integer.valueOf(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i)))).intValue();
                        }
                        return 0;
                    case 172:
                        if (this.mDataItemFeature.ae()) {
                            return Integer.valueOf(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i)))).intValue();
                        }
                        return 0;
                    case 173:
                        break;
                    default:
                        return Integer.valueOf(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i)))).intValue();
                }
            }
        }
        return 0;
    }

    private int getCurrentMode(int i) {
        return getInt(DATA_COMMON_CURRENT_MODE + i, getDefaultMode(i));
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private int getCurrentModeForFrontCamera(int i) {
        int currentMode = getCurrentMode(i);
        switch (currentMode) {
            case 166:
            case 167:
            case 173:
            case 175:
                break;
            case 168:
            case 170:
            case 174:
            default:
                return currentMode;
            case 169:
            case 172:
                return 162;
            case 171:
                if (this.mDataItemFeature.be()) {
                    return currentMode;
                }
                break;
        }
        return 163;
    }

    private int getDefaultCameraId(int i) {
        return 0;
    }

    private boolean isActualTimeOut() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - getLong(DATA_COMMON_OPEN_TIME, currentTimeMillis) > 30000 || this.mIsTimeOut == null;
    }

    public boolean getCTACanCollect() {
        return getBoolean(CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY, false);
    }

    public ComponentModuleList getComponentModuleList() {
        return this.mModuleList;
    }

    public int getCurrentCameraId() {
        return getCurrentCameraId(getCurrentMode());
    }

    public int getCurrentMode() {
        return getCurrentMode(this.mIntentType);
    }

    public int getDataBackUpKey(int i) {
        if (i == 165) {
            i = ComponentModuleList.getTransferredMode(i);
        }
        int i2 = i | ((this.mIntentType + 2) << 8);
        return this.mStartFromKeyguard ? i2 | 65536 : i2;
    }

    public int getDefaultMode(int i) {
        if (i != 1) {
            if (i != 2) {
                return (i == 3 || !this.mMimojiStandAlone) ? 163 : 177;
            }
            return 162;
        }
        return 163;
    }

    public int getDisplayMode() {
        return (!DataRepository.dataItemFeature().dd() || DataRepository.dataItemGlobal().getCurrentCameraId() != 1) ? 1 : 2;
    }

    public int getIntentType() {
        return this.mIntentType;
    }

    public int getLastCameraId() {
        return this.mLastCameraId;
    }

    public boolean getStartFromKeyguard() {
        return this.mStartFromKeyguard;
    }

    public boolean isFirstShowCTAConCollect() {
        return !contains(CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY);
    }

    public boolean isFirstShowTag() {
        return getBoolean(IS_FIRST_SHOW_VIDEOTAG, true);
    }

    public boolean isForceMainBackCamera() {
        return this.mIsForceMainBackCamera;
    }

    public boolean isGlobalSwitchOn(String str) {
        return getBoolean(str, false);
    }

    public boolean isIntentAction() {
        return this.mIntentType != 0;
    }

    public boolean isNormalIntent() {
        return this.mIntentType == 0;
    }

    public boolean isRetriedIfCameraError() {
        return this.mRetriedIfCameraError;
    }

    public boolean isTiktokMoreButtonEnabled(boolean z) {
        return getBoolean(z ? DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_APP : DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_MARKET, b.Vu ? true : z);
    }

    public boolean isTimeOut() {
        Boolean bool = this.mIsTimeOut;
        return bool == null || bool.booleanValue();
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public boolean isTransient() {
        return false;
    }

    public boolean matchCustomWatermarkVersion() {
        String wb = this.mDataItemFeature.wb();
        if (!contains(DATA_COMMON_CUSTOM_WATERMARK_VERSION)) {
            return !this.mDataItemFeature.q(c.QB);
        }
        if (arrayMapContainsKey(DATA_COMMON_CUSTOM_WATERMARK_VERSION)) {
            arrayMapRemove(DATA_COMMON_CUSTOM_WATERMARK_VERSION);
        }
        String string = getString(DATA_COMMON_CUSTOM_WATERMARK_VERSION, "");
        int indexOf = string.indexOf(58);
        if (indexOf > 0) {
            String substring = string.substring(0, indexOf);
            String substring2 = string.substring(indexOf + 1);
            if (substring.equals(b.vu + b.Uk()) && substring2.equals(wb)) {
                return true;
            }
        }
        Log.w(TAG, "mismatch custom watermark version: " + string);
        return false;
    }

    /* JADX DEBUG: Additional 3 move instruction added to help type inference */
    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0090, code lost:
        if (r18.booleanValue() == false) goto L_0x0089;
     */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x01b2  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0202  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0140  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0152  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0159  */
    public Pair<Integer, Integer> parseIntent(Intent intent, Boolean bool, boolean z, boolean z2, boolean z3) {
        char c2;
        int i;
        int cameraFacing;
        int i2;
        int i3;
        int currentCameraId;
        int i4;
        setForceMainBackCamera(false);
        if (DataRepository.dataItemFeature().Lc() && Util.isScreenSlideOff(CameraAppImpl.getAndroidContext())) {
            setCameraId(0);
        }
        String action = intent.getAction();
        if (action == null) {
            action = "<unknown>";
        }
        switch (action.hashCode()) {
            case -1960745709:
                if (action.equals("android.media.action.IMAGE_CAPTURE")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -1658348509:
                if (action.equals("android.media.action.IMAGE_CAPTURE_SECURE")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1528697361:
                if (action.equals(CameraIntentManager.ACTION_VOICE_CONTROL)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -1449841107:
                if (action.equals(CameraIntentManager.ACTION_QR_CODE_ZXING)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 464109999:
                if (action.equals("android.media.action.STILL_IMAGE_CAMERA")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 701083699:
                if (action.equals("android.media.action.VIDEO_CAPTURE")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 1130890360:
                if (action.equals("android.media.action.VIDEO_CAMERA")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 1280056183:
                if (action.equals(CameraIntentManager.ACTION_QR_CODE_CAPTURE)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case 1:
                i = 1;
                cameraFacing = CameraIntentManager.getInstance(intent).getCameraFacing();
                if (cameraFacing != -1) {
                    setCameraIdTransient(cameraFacing);
                }
                boolean z4 = (z3 || !determineTimeOut()) ? false : true;
                boolean z5 = this.mIntentType == i || this.mStartFromKeyguard != z;
                int i5 = 162;
                i5 = 162;
                if (!"android.media.action.STILL_IMAGE_CAMERA".equals(action)) {
                    currentCameraId = getCurrentCameraId(163);
                } else {
                    if ("android.media.action.VIDEO_CAMERA".equals(action)) {
                        i2 = getCurrentCameraId(162);
                        i3 = 162;
                    } else if (CameraIntentManager.getInstance(intent).isQuickLaunch()) {
                        currentCameraId = getCurrentCameraId(163);
                    } else if (z4) {
                        i3 = getDefaultMode(i);
                        i2 = cameraFacing < 0 ? getDefaultCameraId(i3) : getCurrentCameraId(i3);
                        if (i3 == 163 && ((DataItemConfig) DataRepository.provider().dataConfig(i2, i)).getComponentConfigRatio().isSquareModule()) {
                            i3 = 165;
                        }
                    } else {
                        i3 = cameraFacing != 1 ? getCurrentMode(i) : getCurrentModeForFrontCamera(i);
                        i2 = getCurrentCameraId(i3);
                    }
                    if (i3 != 168 || i3 == 170) {
                        if (DataRepository.dataItemFeature().wf()) {
                            i5 = 172;
                        }
                    } else if ((!isActualTimeOut() && !z5) || i3 != 179) {
                        i5 = i3;
                    }
                    Log.d(TAG, String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", Boolean.valueOf(z4), Boolean.valueOf(z5), action, Integer.valueOf(i2), Integer.valueOf(i5), Integer.valueOf(cameraFacing)));
                    if (!z2) {
                        this.mIsTimeOut = Boolean.valueOf(z4);
                        if (z5) {
                            this.mIntentType = i;
                            this.mStartFromKeyguard = z;
                            this.mModuleList.setIntentType(this.mIntentType);
                        }
                        if (i5 != getCurrentMode()) {
                            setCurrentMode(i5);
                            ModuleManager.setActiveModuleIndex(i5);
                        }
                        if (i2 != getCurrentCameraId()) {
                            setCameraId(i2);
                        }
                    }
                    return new Pair<>(Integer.valueOf(i2), Integer.valueOf(i5));
                }
                i2 = currentCameraId;
                i3 = 163;
                if (i3 != 168) {
                }
                if (DataRepository.dataItemFeature().wf()) {
                }
                Log.d(TAG, String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", Boolean.valueOf(z4), Boolean.valueOf(z5), action, Integer.valueOf(i2), Integer.valueOf(i5), Integer.valueOf(cameraFacing)));
                if (!z2) {
                }
                return new Pair<>(Integer.valueOf(i2), Integer.valueOf(i5));
            case 2:
                i = 2;
                cameraFacing = CameraIntentManager.getInstance(intent).getCameraFacing();
                if (cameraFacing != -1) {
                }
                if (z3) {
                    break;
                }
                if (this.mIntentType == i) {
                    break;
                }
                int i52 = 162;
                i52 = 162;
                if (!"android.media.action.STILL_IMAGE_CAMERA".equals(action)) {
                }
                i2 = currentCameraId;
                i3 = 163;
                if (i3 != 168) {
                }
                if (DataRepository.dataItemFeature().wf()) {
                }
                Log.d(TAG, String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", Boolean.valueOf(z4), Boolean.valueOf(z5), action, Integer.valueOf(i2), Integer.valueOf(i52), Integer.valueOf(cameraFacing)));
                if (!z2) {
                }
                return new Pair<>(Integer.valueOf(i2), Integer.valueOf(i52));
            case 3:
            case 4:
                i = 3;
                cameraFacing = CameraIntentManager.getInstance(intent).getCameraFacing();
                if (cameraFacing != -1) {
                }
                if (z3) {
                }
                if (this.mIntentType == i) {
                }
                int i522 = 162;
                i522 = 162;
                if (!"android.media.action.STILL_IMAGE_CAMERA".equals(action)) {
                }
                i2 = currentCameraId;
                i3 = 163;
                if (i3 != 168) {
                }
                if (DataRepository.dataItemFeature().wf()) {
                }
                Log.d(TAG, String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", Boolean.valueOf(z4), Boolean.valueOf(z5), action, Integer.valueOf(i2), Integer.valueOf(i522), Integer.valueOf(cameraFacing)));
                if (!z2) {
                }
                return new Pair<>(Integer.valueOf(i2), Integer.valueOf(i522));
            case 5:
            case 6:
                break;
            case 7:
                CameraIntentManager instance = CameraIntentManager.getInstance(intent);
                int cameraModeId = instance.getCameraModeId();
                if (cameraModeId == 160) {
                    cameraModeId = determineTimeOut() ? getDefaultMode(0) : getCurrentMode(0);
                }
                try {
                    i4 = instance.isUseFrontCamera();
                } catch (Exception unused) {
                    if (instance.isOnlyForceOpenMainBackCamera()) {
                        setForceMainBackCamera(true);
                        i4 = 0;
                    } else {
                        i4 = determineTimeOut() ? getDefaultCameraId(cameraModeId) : getCurrentCameraId(cameraModeId);
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append("intent from voice control assist : pendingOpenId = ");
                int i6 = i4 == true ? 1 : 0;
                int i7 = i4 == true ? 1 : 0;
                int i8 = i4 == true ? 1 : 0;
                int i9 = i4 == true ? 1 : 0;
                sb.append(i6);
                sb.append(";pendingOpenModule = ");
                sb.append(cameraModeId);
                sb.append(",newIntentType = ");
                sb.append(0);
                Log.d(TAG, sb.toString());
                this.mIntentType = 0;
                this.mStartFromKeyguard = z;
                this.mModuleList.setIntentType(this.mIntentType);
                if (cameraModeId != getCurrentMode()) {
                    setCurrentMode(cameraModeId);
                    ModuleManager.setActiveModuleIndex(cameraModeId);
                }
                if (i4 != getCurrentCameraId()) {
                    setCameraId(i4);
                }
                return new Pair<>(Integer.valueOf(i4), Integer.valueOf(cameraModeId));
            default:
                i = 0;
                cameraFacing = CameraIntentManager.getInstance(intent).getCameraFacing();
                if (cameraFacing != -1) {
                }
                if (z3) {
                }
                if (this.mIntentType == i) {
                }
                int i5222 = 162;
                i5222 = 162;
                if (!"android.media.action.STILL_IMAGE_CAMERA".equals(action)) {
                }
                i2 = currentCameraId;
                i3 = 163;
                if (i3 != 168) {
                }
                if (DataRepository.dataItemFeature().wf()) {
                }
                Log.d(TAG, String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", Boolean.valueOf(z4), Boolean.valueOf(z5), action, Integer.valueOf(i2), Integer.valueOf(i5222), Integer.valueOf(cameraFacing)));
                if (!z2) {
                }
                return new Pair<>(Integer.valueOf(i2), Integer.valueOf(i5222));
        }
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public String provideKey() {
        return "camera_settings_global";
    }

    public void reInit() {
        this.mModuleList.reInit();
        DataProvider.ProviderEditor editor = editor();
        this.mIsTimeOut = false;
        editor.putLong(DATA_COMMON_OPEN_TIME, System.currentTimeMillis());
        editor.putLong(CameraSettings.KEY_OPEN_CAMERA_FAIL, 0);
        int currentCameraId = getCurrentCameraId(getCurrentMode());
        this.mLastCameraId = currentCameraId;
        editor.putString("pref_camera_id_key", String.valueOf(currentCameraId));
        Log.d(TAG, "reInit: mLastCameraId = " + this.mLastCameraId + ", currentCameraId = " + currentCameraId);
        editor.apply();
    }

    public void resetAll() {
        this.mIsTimeOut = null;
        editor().clear().putInt(CameraSettings.KEY_VERSION, 4).apply();
    }

    public void resetTimeOut() {
        this.mIsTimeOut = false;
        editor().putLong(DATA_COMMON_OPEN_TIME, System.currentTimeMillis()).apply();
    }

    public void setCTACanCollect(boolean z) {
        editor().putBoolean(CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY, z).apply();
    }

    public void setCameraId(int i) {
        this.mLastCameraId = getCurrentCameraId(getCurrentMode());
        editor().putString("pref_camera_id_key", String.valueOf(i)).apply();
        Log.d(TAG, "setCameraId: mLastCameraId = " + this.mLastCameraId + ", cameraId = " + i);
    }

    public void setCameraIdTransient(int i) {
        this.mLastCameraId = getCurrentCameraId(getCurrentMode());
        putString("pref_camera_id_key", String.valueOf(i));
        Log.d(TAG, "setCameraIdTransient: mLastCameraId = " + this.mLastCameraId + ", cameraId = " + i);
    }

    public void setCurrentMode(int i) {
        DataProvider.ProviderEditor editor = editor();
        editor.putInt(DATA_COMMON_CURRENT_MODE + this.mIntentType, i).apply();
    }

    public void setForceMainBackCamera(boolean z) {
        this.mIsForceMainBackCamera = z;
    }

    public void setRetriedIfCameraError(boolean z) {
        this.mRetriedIfCameraError = z;
    }

    public void setStartFromKeyguard(boolean z) {
        this.mStartFromKeyguard = z;
    }

    public void setVideoTagNote() {
        editor().putBoolean(IS_FIRST_SHOW_VIDEOTAG, false).apply();
    }

    public void updateCustomWatermarkVersion() {
        String str = b.vu + b.Uk() + ":" + this.mDataItemFeature.wb();
        editor().putString(DATA_COMMON_CUSTOM_WATERMARK_VERSION, str).apply();
        Log.i(TAG, "custom watermark version updated: " + str);
    }
}
