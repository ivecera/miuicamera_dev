package com.android.camera;

import a.c.a;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.permission.PermissionManager;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.storage.PriorityStorageBroadcastReceiver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.PreviewListPreference;
import com.android.camera.ui.ValuePreference;
import com.android.camera2.DetachableClickListener;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CameraPreferenceActivity extends BasePreferenceActivity {
    public static final String IS_IMAGE_CAPTURE_INTENT = "IsCaptureIntent";
    public static final String PREF_KEY_POPUP_CAMERA = "pref_popup_camera";
    public static final String PREF_KEY_PRIVACY = "pref_privacy";
    public static final String PREF_KEY_RESTORE = "pref_restore";
    public static final String REMOVE_KEYS = "remove_keys";
    public static final String TAG = "CameraPreferenceActivity";
    private AlertDialog mAlertDialog;
    /* access modifiers changed from: private */
    public CompatibilityUtils.PackageInstallerListener mAppInstalledListener = new CompatibilityUtils.PackageInstallerListener() {
        /* class com.android.camera.CameraPreferenceActivity.AnonymousClass3 */

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x000a, code lost:
            r2 = (android.preference.CheckBoxPreference) r1.this$0.mPreferenceGroup.findPreference("pref_scan_qrcode_key");
         */
        @Override // com.android.camera.lib.compatibility.util.CompatibilityUtils.PackageInstallerListener
        public void onPackageInstalled(String str, boolean z) {
            final CheckBoxPreference checkBoxPreference;
            if (z && TextUtils.equals(str, "com.xiaomi.scanner") && checkBoxPreference != null) {
                CameraPreferenceActivity.this.runOnUiThread(new Runnable() {
                    /* class com.android.camera.CameraPreferenceActivity.AnonymousClass3.AnonymousClass1 */

                    public void run() {
                        checkBoxPreference.setChecked(true);
                        CameraPreferenceActivity.this.onPreferenceChange(checkBoxPreference, Boolean.TRUE);
                    }
                });
            }
        }
    };
    /* access modifiers changed from: private */
    public AlertDialog mDoubleConfirmActionChooseDialog = null;
    private int mFromWhere;
    private boolean mGoToActivity;
    private boolean mHasReset;
    private boolean mIsFrontCamera;
    private boolean mKeyguardSecureLocked = false;
    private AlertDialog mPermissionNotAskDialog = null;
    private Preference mPhotoAssistanceTips;
    protected PreferenceScreen mPreferenceGroup;
    private Preference mWatermark;

    private void bringUpDoubleConfirmDlg(final Preference preference, final String str) {
        if (this.mDoubleConfirmActionChooseDialog == null) {
            final boolean snapBoolValue = getSnapBoolValue(str);
            DetachableClickListener wrap = DetachableClickListener.wrap(new DialogInterface.OnClickListener() {
                /* class com.android.camera.CameraPreferenceActivity.AnonymousClass1 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == -1) {
                        AlertDialog unused = CameraPreferenceActivity.this.mDoubleConfirmActionChooseDialog = null;
                        MistatsWrapper.settingClickEvent("pref_camera_snap_key", str);
                        Preference preference = preference;
                        if (preference instanceof CheckBoxPreference) {
                            ((CheckBoxPreference) preference).setChecked(snapBoolValue);
                        } else if (preference instanceof PreviewListPreference) {
                            ((PreviewListPreference) preference).setValue(str);
                        }
                        Settings.Secure.putString(CameraPreferenceActivity.this.getContentResolver(), a.b.Lh, CameraSettings.getMiuiSettingsKeyForStreetSnap(str));
                    } else if (i == -2) {
                        CameraPreferenceActivity.this.mDoubleConfirmActionChooseDialog.dismiss();
                        AlertDialog unused2 = CameraPreferenceActivity.this.mDoubleConfirmActionChooseDialog = null;
                    }
                }
            });
            this.mDoubleConfirmActionChooseDialog = new AlertDialog.Builder(this).setTitle(R.string.title_snap_double_confirm).setMessage(R.string.message_snap_double_confirm).setPositiveButton(R.string.snap_confirmed, wrap).setNegativeButton(R.string.snap_cancel, wrap).setCancelable(false).create();
            wrap.clearOnDetach(this.mDoubleConfirmActionChooseDialog);
            this.mDoubleConfirmActionChooseDialog.show();
        }
    }

    private void closeLocationPreference() {
        ((CheckBoxPreference) this.mPreferenceGroup.findPreference("pref_camera_recordlocation_key")).setChecked(false);
        CameraSettings.updateRecordLocationPreference(false);
    }

    private void filterByCloud() {
        for (String str : DataRepository.dataCloudMgr().DataCloudFeature().getAllDisabledFeatures()) {
            removePreference(this.mPreferenceGroup, str);
        }
    }

    private void filterByConfig() {
        Log.d(TAG, "filterByConfig:");
        if (CameraSettings.getVideoTimeLapseFrameIntervalNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removeFromGroup(this.mPreferenceGroup, "pref_video_time_lapse_frame_interval_key");
        }
        if (CameraSettings.getMirrorSettingUiNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removeFromGroup(this.mPreferenceGroup, "pref_front_mirror_key");
        }
    }

    private void filterByDeviceCapability() {
        if (!CameraSettings.isH265EncoderSupport()) {
            removeFromGroup(this.mPreferenceGroup, "pref_video_encoder_key");
        }
    }

    private void filterByDeviceID() {
        DataRepository.dataItemFeature();
        if (CameraSettings.getRetainCameraModeSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_retain_camera_mode_key");
        }
        if (CameraSettings.getFocusShootSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_camera_focus_shoot_key");
        }
        if (CameraSettings.getMovieSolidSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_camera_movie_solid_key");
        }
        if (CameraSettings.getVideoTagSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_camera_video_tag_key");
        }
        boolean dualCameraWaterMarkState = CameraSettings.getDualCameraWaterMarkState(this.mFromWhere, this.mIsFrontCamera);
        boolean timeWaterMarkState = CameraSettings.getTimeWaterMarkState(this.mFromWhere, this.mIsFrontCamera);
        boolean customWaterMarkState = CameraSettings.getCustomWaterMarkState(this.mFromWhere, this.mIsFrontCamera);
        if (timeWaterMarkState && dualCameraWaterMarkState) {
            removePreference(this.mPreferenceGroup, "pref_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_dualcamera_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_time_watermark_key");
        } else if (!timeWaterMarkState && dualCameraWaterMarkState) {
            removePreference(this.mPreferenceGroup, "pref_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_dualcamera_watermark_key");
        } else if (!timeWaterMarkState || !customWaterMarkState || dualCameraWaterMarkState) {
            removePreference(this.mPreferenceGroup, "pref_dualcamera_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_time_watermark_key");
        } else {
            removePreference(this.mPreferenceGroup, "pref_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_time_watermark_key");
        }
        if (!b.am()) {
            removePreference(this.mPreferenceGroup, "pref_camerasound_key");
        }
        if (!b.Wl()) {
            removePreference(this.mPreferenceGroup, "pref_camera_recordlocation_key");
        }
        if (!Storage.hasSecondaryStorage()) {
            removePreference(this.mPreferenceGroup, "pref_priority_storage");
        }
        if (!b.Sl()) {
            removePreference(this.mPreferenceGroup, "pref_auto_chroma_flash_key");
        }
        if (CameraSettings.getLongPressShutterSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_camera_long_press_shutter_feature_key");
        }
        if (!b.cm()) {
            removePreference(this.mPreferenceGroup, "pref_capture_when_stable_key");
        }
        if (!b.Rl()) {
            removePreference(this.mPreferenceGroup, "pref_camera_asd_night_key");
        }
        if (CameraSettings.getCameraSnapSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_camera_snap_key");
        }
        if (!b.Gl()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_GROUPSHOT_PRIMITIVE);
        }
        if (!CameraSettings.isSupportedPortrait()) {
            removePreference(this.mPreferenceGroup, "pref_camera_portrait_with_facebeauty_key");
        }
        if (!b.isSupportedOpticalZoom() && !DataRepository.dataItemFeature().Xd()) {
            removePreference(this.mPreferenceGroup, "pref_camera_dual_enable_key");
        }
        if (!b.isSupportedOpticalZoom()) {
            removePreference(this.mPreferenceGroup, "pref_camera_dual_sat_enable_key");
        }
        if (!b.isSupportSuperResolution()) {
            removePreference(this.mPreferenceGroup, "pref_camera_super_resolution_key");
        }
        if (!DataRepository.dataItemFeature().ye()) {
            removePreference(this.mPreferenceGroup, "pref_camera_parallel_process_enable_key");
        }
        if (!CameraSettings.isSupportQuickShot()) {
            removePreference(this.mPreferenceGroup, "pref_camera_quick_shot_enable_key");
        }
        if (b.pm()) {
            removePreference(this.mPreferenceGroup, "pref_camera_facedetection_key");
            removePreference(this.mPreferenceGroup, "pref_camera_facedetection_auto_hidden_key");
            removePreference(this.mPreferenceGroup, "pref_camera_parallel_process_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_quick_shot_anim_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_touch_focus_delay_key");
            removePreference(this.mPreferenceGroup, "pref_camera_quick_shot_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_dual_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_dual_sat_enable_key");
            removePreference(this.mPreferenceGroup, "pref_qc_camera_sharpness_key");
            removePreference(this.mPreferenceGroup, "pref_qc_camera_contrast_key");
            removePreference(this.mPreferenceGroup, "pref_qc_camera_saturation_key");
            removePreference(this.mPreferenceGroup, "pref_camera_autoexposure_key");
        }
        if (CameraSettings.getCameraProximityLockSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_camera_proximity_lock_key");
        }
        if (CameraSettings.getFingerprintCaptureSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_fingerprint_capture_key");
        }
        if (CameraSettings.getNormalWideLDCNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_camera_normal_wide_ldc_key");
        }
        if (!CameraSettings.shouldUltraWideLDCBeVisibleInMode(this.mFromWhere) || ((HybridZoomingSystem.IS_2_SAT && !CameraSettings.isUltraWideConfigOpen(this.mFromWhere)) || ((HybridZoomingSystem.IS_3_OR_MORE_SAT && this.mFromWhere == 167) || (!HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isSupportedOpticalZoom() && !CameraSettings.isUltraWideConfigOpen(this.mFromWhere))))) {
            removePreference(this.mPreferenceGroup, "pref_camera_ultra_wide_ldc_key");
        }
        if (!CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(this.mFromWhere) || !CameraSettings.isUltraWideConfigOpen(this.mFromWhere)) {
            removePreference(this.mPreferenceGroup, "pref_camera_ultra_wide_video_ldc_key");
        }
        if (CameraSettings.getScanQrcodeSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_scan_qrcode_key");
        }
        if (!DataRepository.dataItemFeature().oc()) {
            removePreference(this.mPreferenceGroup, "pref_popup_camera");
        }
        removeIncompatibleAdvancePreference();
        int i = 0;
        if (DataRepository.dataItemFeature().Gd()) {
            i = 1;
        }
        if (DataRepository.dataItemFeature().Pe()) {
            i++;
        }
        if (DataRepository.dataItemFeature().sd()) {
            i++;
        }
        if (i <= 1) {
            removePreference(this.mPreferenceGroup, "pref_photo_assistance_tips");
            if (!DataRepository.dataItemFeature().Gd()) {
                removePreference(this.mPreferenceGroup, "pref_pic_flaw_tip");
            }
            if (!DataRepository.dataItemFeature().sd()) {
                removePreference(this.mPreferenceGroup, "pref_lens_dirty_tip");
            }
            if (!DataRepository.dataItemFeature().Pe()) {
                removePreference(this.mPreferenceGroup, "pref_camera_lying_tip_switch_key");
            }
        } else {
            removePreference(this.mPreferenceGroup, "pref_pic_flaw_tip");
            removePreference(this.mPreferenceGroup, "pref_lens_dirty_tip");
            removePreference(this.mPreferenceGroup, "pref_camera_lying_tip_switch_key");
        }
        if (!DataRepository.dataItemFeature().sf()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_VIDEO_SAT_ENABLE);
        }
    }

    private void filterByFrom() {
        if (CameraSettings.isInAllCaptureModeSet(this.mFromWhere)) {
            removePreference(this.mPreferenceGroup, "category_camcorder_setting");
        } else if (CameraSettings.isInAllRecordModeSet(this.mFromWhere)) {
            removeNonVideoPreference();
        }
        if (CameraSettings.getVolumeCameraSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_camera_volumekey_function_key");
        }
        if (CameraSettings.getVolumeVideoSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_video_volumekey_function_key");
        }
        if (CameraSettings.getVolumeProVideoSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_PRO_VIDEO_FUNCTION);
        }
        if (CameraSettings.getVolumeLiveSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_live_volumekey_function_key");
        }
        int i = this.mFromWhere;
        if (!(i == 167 || i == 180)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_MANUALLY_DESCRIPTION_TIP);
        }
        if (DataRepository.dataItemFeature().wf()) {
            String componentValue = DataRepository.dataItemConfig().getComponentConfigSlowMotion().getComponentValue(172);
            if (!DataRepository.dataItemFeature().Tc() || !ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960.equals(componentValue) || this.mFromWhere != 172) {
                removePreference(this.mPreferenceGroup, "pref_960_watermark_status");
                return;
            }
            return;
        }
        removePreference(this.mPreferenceGroup, "pref_960_watermark_status");
    }

    private void filterByPreference() {
        if (!Util.isLabOptionsVisible()) {
            removePreference(this.mPreferenceGroup, "pref_camera_facedetection_key");
            removePreference(this.mPreferenceGroup, "pref_camera_portrait_with_facebeauty_key");
            removePreference(this.mPreferenceGroup, "pref_camera_facedetection_auto_hidden_key");
            removePreference(this.mPreferenceGroup, "pref_camera_dual_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_dual_sat_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_mfnr_sat_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_sr_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_parallel_process_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_quick_shot_anim_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_touch_focus_delay_key");
            removePreference(this.mPreferenceGroup, "pref_camera_quick_shot_enable_key");
            removePreference(this.mPreferenceGroup, "pref_camera_live_sticker_internal");
            removePreference(this.mPreferenceGroup, "pref_camera_autoexposure_key");
            removePreference(this.mPreferenceGroup, "pref_video_autoexposure_key");
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_VIDEO_SAT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_DEBUG_INFO_AS_WATERMARK);
        }
        if (CameraSettings.getLongPressViewFinderSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera, this.mKeyguardSecureLocked)) {
            removePreference(this.mPreferenceGroup, "pref_camera_long_press_viewfinder_key");
        }
        if (CameraSettings.getGoogleLensSuggestionsSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_google_lens_suggestions_key");
        }
    }

    private void filterGroup() {
        filterGroupIfEmpty("category_device_setting");
        filterGroupIfEmpty("category_camcorder_setting");
        filterGroupIfEmpty("category_camera_setting");
        filterGroupIfEmpty("category_advance_setting");
    }

    private void filterGroupIfEmpty(String str) {
        Preference findPreference = this.mPreferenceGroup.findPreference(str);
        if (findPreference != null && (findPreference instanceof PreferenceGroup) && ((PreferenceGroup) findPreference).getPreferenceCount() == 0) {
            removePreference(this.mPreferenceGroup, str);
        }
    }

    private String getFilterValue(PreviewListPreference previewListPreference, SharedPreferences sharedPreferences) {
        String value = previewListPreference.getValue();
        if (sharedPreferences == null) {
            return value;
        }
        String string = sharedPreferences.getString(previewListPreference.getKey(), value);
        if (Util.isStringValueContained(string, previewListPreference.getEntryValues())) {
            return string;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(previewListPreference.getKey(), value);
        edit.apply();
        return value;
    }

    private boolean getSnapBoolValue(String str) {
        return str.equals(getString(R.string.pref_camera_snap_value_take_picture)) || str.equals(getString(R.string.pref_camera_snap_value_take_movie));
    }

    private String getSnapStringValue(boolean z) {
        return z ? getString(R.string.pref_camera_snap_value_take_picture) : getString(R.string.pref_camera_snap_value_off);
    }

    private void initializeActivity() {
        this.mPreferenceGroup = getPreferenceScreen();
        PreferenceScreen preferenceScreen = this.mPreferenceGroup;
        if (preferenceScreen != null) {
            preferenceScreen.removeAll();
        }
        addPreferencesFromResource(getPreferenceXml());
        this.mPreferenceGroup = getPreferenceScreen();
        if (this.mPreferenceGroup == null) {
            Log.e(TAG, "fail to init PreferenceGroup");
            finish();
        }
        if (!isHeicImageFormatSelectable()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_HEIC_FORMAT);
        }
        registerListener();
        filterByCloud();
        filterByPreference();
        filterByFrom();
        filterByDeviceID();
        filterByDeviceCapability();
        filterByIntent();
        filterByConfig();
        filterGroup();
        updateEntries();
        updatePreferences(this.mPreferenceGroup, ((BasePreferenceActivity) this).mPreferences);
        updateConflictPreference(null);
    }

    /* access modifiers changed from: private */
    public void installQRCodeReceiver() {
        new AsyncTask<Void, Void, Void>() {
            /* class com.android.camera.CameraPreferenceActivity.AnonymousClass2 */

            /* access modifiers changed from: protected */
            public Void doInBackground(Void... voidArr) {
                Log.v(CameraPreferenceActivity.TAG, "install...");
                CameraPreferenceActivity cameraPreferenceActivity = CameraPreferenceActivity.this;
                Util.installPackage(cameraPreferenceActivity, "com.xiaomi.scanner", cameraPreferenceActivity.mAppInstalledListener, false, true);
                return null;
            }
        }.execute(new Void[0]);
    }

    private boolean isHeicImageFormatSelectable() {
        if (!DataRepository.dataItemFeature().nb() || !CameraSettings.isCameraParallelProcessEnable() || !DataRepository.dataItemGlobal().isNormalIntent() || CameraSettings.isLiveShotOn() || CameraSettings.isDocumentModeOn(this.mFromWhere)) {
            return false;
        }
        int i = this.mFromWhere;
        return i == 163 || i == 165 || i == 175;
    }

    private static HashMap<String, Boolean> readKeptValues(boolean z) {
        HashMap<String, Boolean> hashMap = new HashMap<>(6);
        hashMap.put("pref_camera_first_use_permission_shown_key", Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean("pref_camera_first_use_permission_shown_key", true)));
        if (z) {
            for (String str : DataItemGlobal.sUseHints) {
                if (DataRepository.dataItemGlobal().contains(str)) {
                    hashMap.put(str, Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean(str, false)));
                }
            }
        }
        return hashMap;
    }

    private void registerListener() {
        registerListener(this.mPreferenceGroup, this);
        Preference findPreference = this.mPreferenceGroup.findPreference("pref_restore");
        if (findPreference != null) {
            findPreference.setOnPreferenceClickListener(this);
        }
        Preference findPreference2 = this.mPreferenceGroup.findPreference("pref_privacy");
        if (findPreference2 != null) {
            findPreference2.setOnPreferenceClickListener(this);
        }
        Preference findPreference3 = this.mPreferenceGroup.findPreference("pref_popup_camera");
        if (findPreference3 != null) {
            findPreference3.setOnPreferenceClickListener(this);
        }
        Preference findPreference4 = this.mPreferenceGroup.findPreference("pref_priority_storage");
        if (findPreference4 != null) {
            findPreference4.setOnPreferenceClickListener(this);
        }
        Preference findPreference5 = this.mPreferenceGroup.findPreference("pref_camera_facedetection_key");
        if (findPreference5 != null) {
            findPreference5.setOnPreferenceClickListener(this);
        }
        Preference findPreference6 = this.mPreferenceGroup.findPreference("pref_scan_qrcode_key");
        if (findPreference6 != null) {
            findPreference6.setOnPreferenceClickListener(this);
        }
        this.mWatermark = this.mPreferenceGroup.findPreference("pref_watermark_key");
        Preference preference = this.mWatermark;
        if (preference != null) {
            preference.setOnPreferenceClickListener(this);
        }
        this.mPhotoAssistanceTips = this.mPreferenceGroup.findPreference("pref_photo_assistance_tips");
        Preference preference2 = this.mPhotoAssistanceTips;
        if (preference2 != null) {
            preference2.setOnPreferenceClickListener(this);
        }
    }

    private void removeIncompatibleAdvancePreference() {
        removePreference(this.mPreferenceGroup, "pref_qc_camera_contrast_key");
        removePreference(this.mPreferenceGroup, "pref_qc_camera_saturation_key");
        removePreference(this.mPreferenceGroup, "pref_qc_camera_sharpness_key");
    }

    private void removeNonVideoPreference() {
        removePreference(this.mPreferenceGroup, "category_camera_setting");
    }

    public static void resetPreferences(boolean z) {
        HashMap<String, Boolean> readKeptValues = readKeptValues(z);
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        int intentType = dataItemGlobal.getIntentType();
        dataItemGlobal.resetAll();
        ((DataItemConfig) DataRepository.provider().dataConfig(0, intentType)).resetAll();
        ((DataItemConfig) DataRepository.provider().dataConfig(1, intentType)).resetAll();
        DataRepository.dataItemLive().resetAll();
        DataRepository.dataItemRunning().clearArrayMap();
        DataRepository.getInstance().backUp().clearBackUp();
        rewriteKeptValues(readKeptValues);
        Util.generateWatermark2File();
    }

    private void resetSnapSetting() {
        String string = Settings.Secure.getString(getContentResolver(), a.b.Lh);
        if (a.b.Oh.equals(string) || a.b.Ph.equals(string)) {
            Settings.Secure.putString(getContentResolver(), a.b.Lh, "none");
        }
    }

    private void resetTimeOutFlag() {
        if (!this.mHasReset) {
            DataRepository.dataItemGlobal().resetTimeOut();
        }
    }

    /* access modifiers changed from: private */
    public void restorePreferences() {
        this.mHasReset = true;
        resetPreferences(false);
        resetSnapSetting();
        initializeActivity();
        PriorityStorageBroadcastReceiver.setPriorityStorage(getResources().getBoolean(R.bool.priority_storage));
        onSettingChanged(3);
        this.mAlertDialog = null;
    }

    private static void rewriteKeptValues(HashMap<String, Boolean> hashMap) {
        for (String str : hashMap.keySet()) {
            DataRepository.dataItemGlobal().putBoolean(str, hashMap.get(str).booleanValue());
        }
    }

    private void updateEntries() {
        int i;
        PreviewListPreference previewListPreference = (PreviewListPreference) this.mPreferenceGroup.findPreference("pref_camera_antibanding_key");
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.mPreferenceGroup.findPreference("pref_auto_chroma_flash_key");
        CheckBoxPreference checkBoxPreference2 = (CheckBoxPreference) this.mPreferenceGroup.findPreference("pref_camera_snap_key");
        PreviewListPreference previewListPreference2 = (PreviewListPreference) this.mPreferenceGroup.findPreference("pref_camera_volumekey_function_key");
        if (previewListPreference != null && Util.isAntibanding60()) {
            String string = getString(R.string.pref_camera_antibanding_entryvalue_60hz);
            previewListPreference.setValue(string);
            previewListPreference.setDefaultValue(string);
        }
        if (checkBoxPreference != null) {
            checkBoxPreference.setChecked(getResources().getBoolean(CameraSettings.getDefaultPreferenceId(R.bool.pref_camera_auto_chroma_flash_default)));
        }
        if (checkBoxPreference2 != null && b.em()) {
            checkBoxPreference2.setChecked(false);
            String string2 = Settings.Secure.getString(getContentResolver(), a.b.Lh);
            if (a.b.Qh.equals(string2) || "none".equals(string2)) {
                checkBoxPreference2.setChecked(false);
            } else {
                String string3 = DataRepository.dataItemGlobal().getString("pref_camera_snap_key", null);
                if (string3 != null) {
                    Settings.Secure.putString(getContentResolver(), a.b.Lh, CameraSettings.getMiuiSettingsKeyForStreetSnap(string3));
                    DataRepository.dataItemGlobal().editor().remove("pref_camera_snap_key").apply();
                    checkBoxPreference2.setChecked(getSnapBoolValue(string3));
                } else if (a.b.Oh.equals(string2) || a.b.Ph.equals(string2)) {
                    checkBoxPreference2.setChecked(true);
                }
            }
        }
        if (previewListPreference2 != null && ((i = this.mFromWhere) == 176 || i == 166)) {
            String string4 = getString(R.string.pref_camera_volumekey_function_entry_shutter);
            String string5 = getString(R.string.pref_camera_volumekey_function_entry_volume);
            String string6 = getString(R.string.pref_camera_volumekey_function_entryvalue_shutter);
            String string7 = getString(R.string.pref_camera_volumekey_function_entryvalue_volume);
            previewListPreference2.setEntries(new CharSequence[]{string4, string5});
            previewListPreference2.setEntryValues(new CharSequence[]{string6, string7});
            previewListPreference2.setDefaultValue(string6);
            previewListPreference2.setValue(string7);
        }
        CheckBoxPreference checkBoxPreference3 = (CheckBoxPreference) this.mPreferenceGroup.findPreference("pref_dualcamera_watermark_key");
        if (checkBoxPreference3 != null) {
            checkBoxPreference3.setDefaultValue(Boolean.valueOf(b.G(getResources().getBoolean(R.bool.pref_device_watermark_default))));
            checkBoxPreference3.setChecked(b.G(getResources().getBoolean(R.bool.pref_device_watermark_default)));
        }
    }

    private void updatePhotoAssistanceTips(SharedPreferences sharedPreferences, ValuePreference valuePreference) {
        if (sharedPreferences != null && valuePreference != null) {
            if (DataRepository.dataItemFeature().Pe() && sharedPreferences.getBoolean("pref_camera_lying_tip_switch_key", true)) {
                valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_on));
            } else if (DataRepository.dataItemFeature().Gd() && sharedPreferences.getBoolean("pref_pic_flaw_tip", getResources().getBoolean(R.bool.pref_pic_flaw_tip_default))) {
                valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_on));
            } else if (!DataRepository.dataItemFeature().sd() || !sharedPreferences.getBoolean("pref_lens_dirty_tip", getResources().getBoolean(R.bool.pref_lens_dirty_tip_default))) {
                valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_off));
            } else {
                valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_on));
            }
        }
    }

    private void updateQRCodeEntry() {
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.mPreferenceGroup.findPreference("pref_scan_qrcode_key");
        if (checkBoxPreference != null && ((BasePreferenceActivity) this).mPreferences.getBoolean("pref_scan_qrcode_key", checkBoxPreference.isChecked()) && !CameraSettings.isQRCodeReceiverAvailable(this)) {
            Log.v(TAG, "disable QRCodeScan");
            SharedPreferences.Editor edit = ((BasePreferenceActivity) this).mPreferences.edit();
            edit.putBoolean("pref_scan_qrcode_key", false);
            edit.apply();
            checkBoxPreference.setChecked(false);
        }
    }

    private void updateWaterMark(SharedPreferences sharedPreferences, ValuePreference valuePreference) {
        if ((!CameraSettings.isSupportedDualCameraWaterMark() || !sharedPreferences.getBoolean("pref_dualcamera_watermark_key", b.G(CameraSettings.getBool(R.bool.pref_device_watermark_default)))) && !sharedPreferences.getBoolean("pref_time_watermark_key", false)) {
            valuePreference.setValue(getString(R.string.pref_watermark_off));
        } else {
            valuePreference.setValue(getString(R.string.pref_watermark_on));
        }
    }

    public /* synthetic */ void d(DialogInterface dialogInterface, int i) {
        closeLocationPreference();
    }

    public /* synthetic */ void e(DialogInterface dialogInterface, int i) {
        startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + getPackageName())));
    }

    /* access modifiers changed from: protected */
    public void filterByIntent() {
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(REMOVE_KEYS);
        if (stringArrayListExtra != null) {
            Iterator<String> it = stringArrayListExtra.iterator();
            while (it.hasNext()) {
                removePreference(this.mPreferenceGroup, it.next());
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.BasePreferenceActivity
    public int getPreferenceXml() {
        return R.xml.camera_other_preferences_new;
    }

    public void onBackPressed() {
        resetTimeOutFlag();
        super.onBackPressed();
    }

    @Override // com.android.camera.BasePreferenceActivity
    public void onCreate(Bundle bundle) {
        ActionBar actionBar;
        super.onCreate(bundle);
        this.mIsFrontCamera = CameraSettings.isFrontCamera();
        this.mFromWhere = getIntent().getIntExtra(BasePreferenceActivity.FROM_WHERE, 0);
        if (getIntent().getBooleanExtra(a.a.a.mf, false)) {
            setShowWhenLocked(true);
            this.mKeyguardSecureLocked = ((KeyguardManager) getSystemService("keyguard")).isKeyguardSecure();
        } else {
            this.mKeyguardSecureLocked = false;
        }
        CameraSettings.upgradeGlobalPreferences();
        Storage.initStorage(this);
        initializeActivity();
        if (getIntent().getCharSequenceExtra(a.a.a.zf) != null && (actionBar = getActionBar()) != null) {
            actionBar.setTitle(R.string.pref_camera_settings_category);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        resetTimeOutFlag();
        finish();
        return true;
    }

    @Override // com.android.camera.BasePreferenceActivity
    public boolean onPreferenceChange(Preference preference, Object obj) {
        String key = preference.getKey();
        if (TextUtils.isEmpty(key)) {
            return true;
        }
        char c2 = 65535;
        int hashCode = key.hashCode();
        if (hashCode != 852574760) {
            if (hashCode == 2069752292 && key.equals("pref_camera_recordlocation_key")) {
                c2 = 1;
            }
        } else if (key.equals("pref_camera_snap_key")) {
            c2 = 0;
        }
        if (c2 != 0) {
            if (c2 == 1) {
                Log.d(TAG, "onPreferenceChange: KEY_RECORD_LOCATION " + obj);
                if (((Boolean) obj).booleanValue() && !PermissionManager.checkCameraLocationPermissions()) {
                    PermissionManager.requestCameraLocationPermissions(this);
                }
            }
        } else if (obj != null) {
            String string = getString(R.string.pref_camera_snap_value_off);
            if (obj instanceof Boolean) {
                string = getSnapStringValue(((Boolean) obj).booleanValue());
            } else if (obj instanceof String) {
                string = (String) obj;
            }
            if ((string.equals(getString(R.string.pref_camera_snap_value_take_picture)) || string.equals(getString(R.string.pref_camera_snap_value_take_movie))) && a.b.Qh.equals(Settings.Secure.getString(getContentResolver(), a.b.Lh))) {
                bringUpDoubleConfirmDlg(preference, string);
                return false;
            }
            Settings.Secure.putString(getContentResolver(), a.b.Lh, CameraSettings.getMiuiSettingsKeyForStreetSnap(string));
            MistatsWrapper.settingClickEvent(MistatsConstants.Setting.PARAM_CAMERA_SNAP, string);
            return true;
        }
        return super.onPreferenceChange(preference, obj);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onPreferenceClick(Preference preference) {
        char c2;
        String key = preference.getKey();
        if (TextUtils.isEmpty(key)) {
            return true;
        }
        switch (key.hashCode()) {
            case -1717659284:
                if (key.equals("pref_privacy")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1620641004:
                if (key.equals("pref_scan_qrcode_key")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -305641358:
                if (key.equals("pref_restore")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 76287668:
                if (key.equals("pref_popup_camera")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 829778300:
                if (key.equals("pref_priority_storage")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 1069539048:
                if (key.equals("pref_watermark_key")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 2047422134:
                if (key.equals("pref_photo_assistance_tips")) {
                    c2 = 4;
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
                if (this.mAlertDialog != null) {
                    return true;
                }
                MistatsWrapper.settingClickEvent(MistatsConstants.Setting.PREF_KEY_RESTORE, null);
                this.mAlertDialog = RotateDialogController.showSystemAlertDialog(this, getString(R.string.confirm_restore_title), getString(R.string.confirm_restore_message), getString(17039370), new f(this), getString(17039360), new d(this));
                return true;
            case 1:
                ActivityLauncher.launchPrivacyPolicyWebpage(this);
                MistatsWrapper.settingClickEvent(MistatsConstants.Setting.PREF_KEY_PRIVACY, null);
                return true;
            case 2:
                this.mGoToActivity = true;
                ActivityLauncher.launchPopupCameraSetting(this);
                MistatsWrapper.settingClickEvent(MistatsConstants.Setting.PREF_KEY_POPUP_CAMERA, null);
                return true;
            case 3:
                Intent intent = new Intent(this, WatermarkActivity.class);
                intent.putExtra(BasePreferenceActivity.FROM_WHERE, this.mFromWhere);
                if (getIntent().getBooleanExtra(a.a.a.mf, false)) {
                    intent.putExtra(a.a.a.mf, true);
                }
                this.mGoToActivity = true;
                startActivity(intent);
                return true;
            case 4:
                Intent intent2 = new Intent(this, PhotoAssistanceTipsActivity.class);
                try {
                    if (getIntent().getBooleanExtra(a.a.a.mf, false)) {
                        intent2.putExtra(a.a.a.mf, true);
                    }
                    this.mGoToActivity = true;
                    startActivity(intent2);
                    HashMap hashMap = new HashMap();
                    hashMap.put(MistatsConstants.FeatureName.VALUE_PHOTO_ASSISTANCE_TIP_CLICK, 1);
                    MistatsWrapper.mistatEvent(MistatsConstants.FeatureName.KEY_COMMON_TIPS, hashMap);
                } catch (Exception unused) {
                }
                return true;
            case 5:
                PriorityStorageBroadcastReceiver.setPriorityStorage(((CheckBoxPreference) preference).isChecked());
                return true;
            case 6:
                if (!CameraSettings.isQRCodeReceiverAvailable(this)) {
                    RotateDialogController.showSystemAlertDialog(this, getString(R.string.confirm_install_scanner_title), getString(R.string.confirm_install_scanner_message), getString(R.string.install_confirmed), new g(this), getString(17039360), null);
                    return true;
                }
                break;
        }
        return false;
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        String str = TAG;
        Log.d(str, "onRequestPermissionsResult: requestCode = " + i);
        if (i == PermissionManager.getCameraLocationPermissionRequestCode() && !PermissionManager.checkCameraLocationPermissions()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_COARSE_LOCATION")) {
                closeLocationPreference();
                return;
            }
            Log.d(TAG, "onRequestPermissionsResult: not ask again!");
            if (this.mPermissionNotAskDialog == null) {
                this.mPermissionNotAskDialog = new AlertDialog.Builder(this).setMessage(R.string.location_permission_not_ask_again).setNegativeButton(17039360, new e(this)).setPositiveButton(R.string.location_permission_not_ask_again_go_to_settings, new c(this)).setCancelable(false).create();
            }
            this.mPermissionNotAskDialog.show();
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        if (this.mGoToActivity) {
            updateWaterMark(((BasePreferenceActivity) this).mPreferences, (ValuePreference) this.mWatermark);
            updatePhotoAssistanceTips(((BasePreferenceActivity) this).mPreferences, (ValuePreference) this.mPhotoAssistanceTips);
            this.mGoToActivity = false;
        } else if (getReferrer() == null || !TextUtils.equals(getReferrer().getHost(), getPackageName())) {
            initializeActivity();
        } else {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        updateQRCodeEntry();
        if (Util.isLabOptionsVisible()) {
            Toast.makeText(this, (int) R.string.camera_facedetection_sub_option_hint, 1).show();
        }
    }

    public /* synthetic */ void ra() {
        this.mAlertDialog = null;
    }

    @Override // com.android.camera.BasePreferenceActivity
    public void updateConflictPreference(Preference preference) {
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.mPreferenceGroup.findPreference("pref_camera_movie_solid_key");
        boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(this.mFromWhere);
        boolean isSuperEISEnabled = CameraSettings.isSuperEISEnabled(this.mFromWhere);
        if (this.mFromWhere == 162 && checkBoxPreference != null) {
            if (isAutoZoomEnabled || isSuperEISEnabled) {
                checkBoxPreference.setEnabled(false);
            } else {
                checkBoxPreference.setEnabled(true);
            }
        }
    }

    @Override // com.android.camera.BasePreferenceActivity
    public void updatePreferences(PreferenceGroup preferenceGroup, SharedPreferences sharedPreferences) {
        if (preferenceGroup != null) {
            int preferenceCount = preferenceGroup.getPreferenceCount();
            for (int i = 0; i < preferenceCount; i++) {
                Preference preference = preferenceGroup.getPreference(i);
                if (preference instanceof ValuePreference) {
                    if (preference.getKey().equals("pref_watermark_key")) {
                        updateWaterMark(sharedPreferences, (ValuePreference) preference);
                    }
                    if (preference.getKey().equals("pref_photo_assistance_tips")) {
                        updatePhotoAssistanceTips(sharedPreferences, (ValuePreference) preference);
                    }
                } else if (preference instanceof PreviewListPreference) {
                    PreviewListPreference previewListPreference = (PreviewListPreference) preference;
                    if (!b.hl() || !"pref_front_mirror_key".equals(previewListPreference.getKey()) || sharedPreferences.getString("pref_front_mirror_key", null) != null) {
                        previewListPreference.setValue(getFilterValue(previewListPreference, sharedPreferences));
                    } else {
                        String string = getString(R.string.pref_front_mirror_entryvalue_off);
                        previewListPreference.setValue(string);
                        previewListPreference.setDefaultValue(string);
                    }
                    preference.setPersistent(false);
                } else if (preference instanceof CheckBoxPreference) {
                    CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                    String key = checkBoxPreference.getKey();
                    checkBoxPreference.setChecked(sharedPreferences.getBoolean(key, checkBoxPreference.isChecked()));
                    preference.setPersistent(false);
                    if ("pref_camera_recordlocation_key".equals(key)) {
                        preference.setEnabled(!this.mKeyguardSecureLocked);
                        if (!PermissionManager.checkCameraLocationPermissions() && CameraSettings.isRecordLocation()) {
                            checkBoxPreference.setChecked(false);
                            CameraSettings.updateRecordLocationPreference(false);
                        }
                    }
                } else if (preference instanceof PreferenceGroup) {
                    updatePreferences((PreferenceGroup) preference, sharedPreferences);
                } else {
                    Log.v(TAG, "no need update preference for " + preference.getKey());
                }
            }
        }
    }
}
