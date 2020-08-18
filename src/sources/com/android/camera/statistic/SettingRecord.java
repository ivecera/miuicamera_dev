package com.android.camera.statistic;

import android.content.Context;
import android.content.res.Resources;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.statistic.MistatsConstants;
import java.util.HashMap;

public class SettingRecord {
    private static String TAG = "SettingRecord";
    private Context mContext;
    private boolean mInRecording = false;

    public SettingRecord(Context context) {
        this.mContext = context;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getMistatString(String str) {
        char c2;
        switch (str.hashCode()) {
            case -2144607600:
                if (str.equals("pref_camera_ultra_wide_ldc_key")) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case -2108353415:
                if (str.equals("pref_camera_normal_wide_ldc_key")) {
                    c2 = 18;
                    break;
                }
                c2 = 65535;
                break;
            case -2096668591:
                if (str.equals("pref_front_mirror_key")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -1717659284:
                if (str.equals("pref_privacy")) {
                    c2 = 25;
                    break;
                }
                c2 = 65535;
                break;
            case -1620641004:
                if (str.equals("pref_scan_qrcode_key")) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case -1408552375:
                if (str.equals("pref_lens_dirty_tip")) {
                    c2 = 16;
                    break;
                }
                c2 = 65535;
                break;
            case -1334394994:
                if (str.equals("pref_camerasound_key")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1153050370:
                if (str.equals("pref_camera_movie_solid_key")) {
                    c2 = 20;
                    break;
                }
                c2 = 65535;
                break;
            case -636369951:
                if (str.equals("pref_camera_referenceline_key")) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -435486694:
                if (str.equals("pref_camera_focus_shoot_key")) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case -305641358:
                if (str.equals("pref_restore")) {
                    c2 = 26;
                    break;
                }
                c2 = 65535;
                break;
            case -233551147:
                if (str.equals("pref_960_watermark_status")) {
                    c2 = 19;
                    break;
                }
                c2 = 65535;
                break;
            case -44500048:
                if (str.equals("pref_camera_volumekey_function_key")) {
                    c2 = 23;
                    break;
                }
                c2 = 65535;
                break;
            case -33912691:
                if (str.equals("pref_camera_antibanding_key")) {
                    c2 = 24;
                    break;
                }
                c2 = 65535;
                break;
            case 386125541:
                if (str.equals(CameraSettings.KEY_HEIC_FORMAT)) {
                    c2 = 15;
                    break;
                }
                c2 = 65535;
                break;
            case 554750382:
                if (str.equals("pref_time_watermark_key")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 852574760:
                if (str.equals("pref_camera_snap_key")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 966436379:
                if (str.equals("pref_video_time_lapse_frame_interval_key")) {
                    c2 = 22;
                    break;
                }
                c2 = 65535;
                break;
            case 1069539048:
                if (str.equals("pref_watermark_key")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 1167378432:
                if (str.equals("pref_camera_lying_tip_switch_key")) {
                    c2 = 17;
                    break;
                }
                c2 = 65535;
                break;
            case 1324596611:
                if (str.equals("pref_camera_long_press_shutter_feature_key")) {
                    c2 = '\r';
                    break;
                }
                c2 = 65535;
                break;
            case 1613717468:
                if (str.equals("pref_video_encoder_key")) {
                    c2 = 21;
                    break;
                }
                c2 = 65535;
                break;
            case 1739638146:
                if (str.equals("pref_dualcamera_watermark_key")) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 1752299636:
                if (str.equals("user_define_watermark_key")) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case 1761265663:
                if (str.equals("pref_retain_camera_mode_key")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 1934228025:
                if (str.equals("pref_camera_jpegquality_key")) {
                    c2 = 14;
                    break;
                }
                c2 = 65535;
                break;
            case 2069752292:
                if (str.equals("pref_camera_recordlocation_key")) {
                    c2 = 0;
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
                return MistatsConstants.Setting.PARAM_SAVE_LOCATION;
            case 1:
                return MistatsConstants.Setting.PARAM_CAMERA_SOUND;
            case 2:
                return MistatsConstants.Setting.PARAM_RETAIN_CAMERA_MODE;
            case 3:
                return MistatsConstants.Setting.PARAM_CAMERA_SNAP;
            case 4:
                return MistatsConstants.Setting.PARAM_FRONT_MIRROR;
            case 5:
                return MistatsConstants.Setting.PARAM_WATERMARK;
            case 6:
                return MistatsConstants.Setting.PARAM_TIME_WATERMARK;
            case 7:
                return MistatsConstants.Setting.PARAM_DEVICE_WATERMARK;
            case '\b':
                return MistatsConstants.Setting.PARAM_USERDEFINE_WATERMARK;
            case '\t':
                return "attr_reference_line";
            case '\n':
                return MistatsConstants.Setting.PARAM_ULTRA_WIDE_LDC;
            case 11:
                return MistatsConstants.Setting.PARAM_FOCUS_SHOOT;
            case '\f':
                return MistatsConstants.Setting.PARAM_SCAN_QRCODE;
            case '\r':
                return MistatsConstants.Setting.PARAM_LONG_PRESS_SHUTTER_FEATURE;
            case 14:
                return MistatsConstants.Setting.PARAM_JPEG_QUALITY;
            case 15:
                return MistatsConstants.Setting.PARAM_HEIC_FORMAT;
            case 16:
                return MistatsConstants.Setting.PARAM_LENS_DIRTY_SWITCH;
            case 17:
                return MistatsConstants.Setting.PARAM_LYING_TIP_SWITCH;
            case 18:
                return MistatsConstants.Setting.PARAM_NORMAL_WIDE_LDC;
            case 19:
                return MistatsConstants.Setting.PARAM_960_WATERMARK_STATUS;
            case 20:
                return MistatsConstants.Setting.PARAM_MOVIE_SOLID;
            case 21:
                return MistatsConstants.Setting.PARAM_VIDEO_ENCODER;
            case 22:
                return MistatsConstants.Setting.PARAM_VIDEO_TIME_LAPSE_FRAME_INTERVAL;
            case 23:
                return MistatsConstants.Setting.PARAM_VOLUME_CAMERA_FUNCTION;
            case 24:
                return MistatsConstants.Setting.PARAM_ANTIBANDING;
            case 25:
                return MistatsConstants.Setting.PREF_KEY_PRIVACY;
            case 26:
                return MistatsConstants.Setting.PREF_KEY_RESTORE;
            default:
                return null;
        }
    }

    private void uploadAdvanceSetting() {
        HashMap hashMap = new HashMap();
        Resources resources = this.mContext.getResources();
        hashMap.put(MistatsConstants.Setting.PARAM_SUB_MODULE, MistatsConstants.Setting.VALUE_SETTING_ADVANCE);
        hashMap.put(MistatsConstants.Setting.PARAM_VOLUME_CAMERA_FUNCTION, DataRepository.dataItemGlobal().getString("pref_camera_volumekey_function_key", resources.getString(R.string.pref_camera_volumekey_function_default)));
        hashMap.put(MistatsConstants.Setting.PARAM_ANTIBANDING, CameraSettings.getAntiBanding());
        MistatsWrapper.settingSchedualEvent(hashMap);
    }

    private void uploadCaptureSetting() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Setting.PARAM_SUB_MODULE, MistatsConstants.Setting.VALUE_SETTING_CAPTURE);
        boolean isTimeWaterMarkOpen = CameraSettings.isTimeWaterMarkOpen();
        hashMap.put(MistatsConstants.Setting.PARAM_TIME_WATERMARK, Boolean.valueOf(CameraSettings.isTimeWaterMarkOpen()));
        boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
        hashMap.put(MistatsConstants.Setting.PARAM_DEVICE_WATERMARK, Boolean.valueOf(isDualCameraWaterMarkOpen));
        boolean isCustomWatermarkOpen = CameraSettings.isCustomWatermarkOpen();
        hashMap.put(MistatsConstants.Setting.PARAM_USERDEFINE_WATERMARK, Boolean.valueOf(isCustomWatermarkOpen));
        hashMap.put(MistatsConstants.Setting.PARAM_WATERMARK, Boolean.valueOf(((!CameraSettings.isSupportedDualCameraWaterMark() || !isDualCameraWaterMarkOpen) && !isTimeWaterMarkOpen && !isCustomWatermarkOpen) ? false : true));
        hashMap.put("attr_reference_line", Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean("pref_camera_referenceline_key", false)));
        hashMap.put(MistatsConstants.Setting.PARAM_ULTRA_WIDE_LDC, Boolean.valueOf(CameraSettings.isUltraWideLDCEnabled()));
        hashMap.put(MistatsConstants.Setting.PARAM_NORMAL_WIDE_LDC, Boolean.valueOf(CameraSettings.isNormalWideLDCEnabled()));
        hashMap.put(MistatsConstants.Setting.PARAM_FOCUS_SHOOT, Boolean.valueOf(DataRepository.dataItemGlobal().isGlobalSwitchOn("pref_camera_focus_shoot_key")));
        hashMap.put(MistatsConstants.Setting.PARAM_SCAN_QRCODE, Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean("pref_scan_qrcode_key", Boolean.valueOf(this.mContext.getResources().getString(R.string.pref_scan_qrcode_default)).booleanValue())));
        hashMap.put(MistatsConstants.Setting.PARAM_LONG_PRESS_SHUTTER_FEATURE, DataRepository.dataItemGlobal().getString("pref_camera_long_press_shutter_feature_key", this.mContext.getResources().getString(R.string.pref_camera_long_press_shutter_feature_default)));
        hashMap.put(MistatsConstants.Setting.PARAM_JPEG_QUALITY, DataRepository.dataItemConfig().getString("pref_camera_jpegquality_key", this.mContext.getResources().getString(R.string.pref_camera_jpegquality_default)));
        hashMap.put(MistatsConstants.Setting.PARAM_HEIC_FORMAT, Boolean.valueOf(CameraSettings.isHeicImageFormatSelected()));
        hashMap.put(MistatsConstants.Setting.PARAM_LYING_TIP_SWITCH, Boolean.valueOf(CameraSettings.isCameraLyingHintOn()));
        MistatsWrapper.settingSchedualEvent(hashMap);
    }

    private void uploadRecordSettingGlobal() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Setting.PARAM_SUB_MODULE, MistatsConstants.Setting.VALUE_SETTING_GOLBAL);
        hashMap.put(MistatsConstants.Setting.PARAM_SAVE_LOCATION, Boolean.valueOf(CameraSettings.isRecordLocation()));
        hashMap.put(MistatsConstants.Setting.PARAM_CAMERA_SOUND, Boolean.valueOf(CameraSettings.isCameraSoundOpen()));
        hashMap.put(MistatsConstants.Setting.PARAM_RETAIN_CAMERA_MODE, Boolean.valueOf(CameraSettings.retainCameraMode()));
        hashMap.put(MistatsConstants.Setting.PARAM_CAMERA_SNAP, DataRepository.dataItemGlobal().getString("pref_camera_snap_key", null));
        hashMap.put(MistatsConstants.Setting.PARAM_FRONT_MIRROR, Boolean.valueOf(CameraSettings.isRecordLocation()));
        MistatsWrapper.settingSchedualEvent(hashMap);
    }

    private void uploadVideoSetting() {
        HashMap hashMap = new HashMap();
        Resources resources = this.mContext.getResources();
        hashMap.put(MistatsConstants.Setting.PARAM_SUB_MODULE, MistatsConstants.Setting.VALUE_SETTING_VIDEO_RECORD);
        hashMap.put(MistatsConstants.Setting.PARAM_MOVIE_SOLID, Boolean.valueOf(CameraSettings.isMovieSolidOn()));
        hashMap.put(MistatsConstants.Setting.PARAM_VIDEO_ENCODER, Integer.valueOf(CameraSettings.getVideoEncoder()));
        hashMap.put(MistatsConstants.Setting.PARAM_VIDEO_TIME_LAPSE_FRAME_INTERVAL, DataRepository.dataItemGlobal().getString("pref_video_time_lapse_frame_interval_key", resources.getString(R.string.pref_video_time_lapse_frame_interval_default)));
        MistatsWrapper.settingSchedualEvent(hashMap);
    }

    public void endRecord() {
        if (this.mInRecording) {
            uploadRecordSettingGlobal();
            uploadCaptureSetting();
            uploadVideoSetting();
            uploadAdvanceSetting();
            this.mInRecording = false;
        }
    }

    public void startRecord() {
        this.mInRecording = true;
    }
}
