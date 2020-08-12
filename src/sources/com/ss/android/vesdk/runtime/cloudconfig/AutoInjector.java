package com.ss.android.vesdk.runtime.cloudconfig;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.apps.photos.api.PhotosOemApi;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AutoInjector implements IInjector {
    public static final String TAG = "AutoInjector";

    private Map<String, String> createParamMap(JSONObject jSONObject) throws JSONException {
        HashMap hashMap = new HashMap();
        if (jSONObject.has("record_camera_type")) {
            int i = jSONObject.getInt("record_camera_type");
            if (i >= 1) {
                hashMap.put("record_camera_type", String.valueOf(i));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 1) record_camera_type = " + i);
                hashMap.put("record_camera_type", "1");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_camera_type");
            hashMap.put("record_camera_type", "1");
        }
        if (jSONObject.has("record_camera_compat_level")) {
            int i2 = jSONObject.getInt("record_camera_compat_level");
            if (i2 >= 0) {
                hashMap.put("record_camera_compat_level", String.valueOf(i2));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0) record_camera_compat_level = " + i2);
                hashMap.put("record_camera_compat_level", "1");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_camera_compat_level");
            hashMap.put("record_camera_compat_level", "1");
        }
        if (jSONObject.has("record_video_sw_crf")) {
            int i3 = jSONObject.getInt("record_video_sw_crf");
            if (i3 < 1 || i3 > 50) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 1 && value <= 50) record_video_sw_crf = " + i3);
                hashMap.put("record_video_sw_crf", "15");
            } else {
                hashMap.put("record_video_sw_crf", String.valueOf(i3));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_video_sw_crf");
            hashMap.put("record_video_sw_crf", "15");
        }
        if (jSONObject.has("record_video_sw_maxrate")) {
            int i4 = jSONObject.getInt("record_video_sw_maxrate");
            if (i4 < 100000 || i4 > 100000000) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 100000 && value <= 100000000) record_video_sw_maxrate = " + i4);
                hashMap.put("record_video_sw_maxrate", "15000000");
            } else {
                hashMap.put("record_video_sw_maxrate", String.valueOf(i4));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_video_sw_maxrate");
            hashMap.put("record_video_sw_maxrate", "15000000");
        }
        if (jSONObject.has("record_video_sw_preset")) {
            int i5 = jSONObject.getInt("record_video_sw_preset");
            if (i5 < 0 || i5 > 9) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0 && value <= 9) record_video_sw_preset = " + i5);
                hashMap.put("record_video_sw_preset", "0");
            } else {
                hashMap.put("record_video_sw_preset", String.valueOf(i5));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_video_sw_preset");
            hashMap.put("record_video_sw_preset", "0");
        }
        if (jSONObject.has("record_video_sw_gop")) {
            int i6 = jSONObject.getInt("record_video_sw_gop");
            if (i6 >= 1) {
                hashMap.put("record_video_sw_gop", String.valueOf(i6));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 1) record_video_sw_gop = " + i6);
                hashMap.put("record_video_sw_gop", "35");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_video_sw_gop");
            hashMap.put("record_video_sw_gop", "35");
        }
        if (jSONObject.has("record_video_sw_qp")) {
            int i7 = jSONObject.getInt("record_video_sw_qp");
            if (i7 < 1 || i7 > 50) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 1 && value <= 50) record_video_sw_qp = " + i7);
                hashMap.put("record_video_sw_qp", "2");
            } else {
                hashMap.put("record_video_sw_qp", String.valueOf(i7));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_video_sw_qp");
            hashMap.put("record_video_sw_qp", "2");
        }
        if (jSONObject.has("record_sw_bitrate_mode")) {
            int i8 = jSONObject.getInt("record_sw_bitrate_mode");
            if (i8 < 0 || i8 > 2) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0 && value <= 2) record_sw_bitrate_mode = " + i8);
                hashMap.put("record_sw_bitrate_mode", "1");
            } else {
                hashMap.put("record_sw_bitrate_mode", String.valueOf(i8));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_sw_bitrate_mode");
            hashMap.put("record_sw_bitrate_mode", "1");
        }
        if (jSONObject.has("record_video_hw_bitrate")) {
            int i9 = jSONObject.getInt("record_video_hw_bitrate");
            if (i9 > 0) {
                hashMap.put("record_video_hw_bitrate", String.valueOf(i9));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value > 0) record_video_hw_bitrate = " + i9);
                hashMap.put("record_video_hw_bitrate", "4194304");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_video_hw_bitrate");
            hashMap.put("record_video_hw_bitrate", "4194304");
        }
        if (jSONObject.has("record_encode_mode")) {
            int i10 = jSONObject.getInt("record_encode_mode");
            if (i10 == 0 || i10 == 1) {
                hashMap.put("record_encode_mode", String.valueOf(i10));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value == 0 || value == 1) record_encode_mode = " + i10);
                hashMap.put("record_encode_mode", "0");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_encode_mode");
            hashMap.put("record_encode_mode", "0");
        }
        if (jSONObject.has("record_hw_bitrate_mode")) {
            int i11 = jSONObject.getInt("record_hw_bitrate_mode");
            if (i11 >= 0) {
                hashMap.put("record_hw_bitrate_mode", String.valueOf(i11));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0) record_hw_bitrate_mode = " + i11);
                hashMap.put("record_hw_bitrate_mode", "0");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_hw_bitrate_mode");
            hashMap.put("record_hw_bitrate_mode", "0");
        }
        if (jSONObject.has("record_hw_profile")) {
            int i12 = jSONObject.getInt("record_hw_profile");
            if (i12 >= 0) {
                hashMap.put("record_hw_profile", String.valueOf(i12));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0) record_hw_profile = " + i12);
                hashMap.put("record_hw_profile", "0");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_hw_profile");
            hashMap.put("record_hw_profile", "0");
        }
        if (jSONObject.has("record_resolution_width")) {
            int i13 = jSONObject.getInt("record_resolution_width");
            if (i13 % 16 != 0 || i13 < 160 || i13 > 5120) {
                Log.w(TAG, "Fetched config doesn't pass:(value % 16 == 0 && value >= 160 && value <= 5120) record_resolution_width = " + i13);
                hashMap.put("record_resolution_width", "576");
            } else {
                hashMap.put("record_resolution_width", String.valueOf(i13));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_resolution_width");
            hashMap.put("record_resolution_width", "576");
        }
        if (jSONObject.has("record_resolution_height")) {
            int i14 = jSONObject.getInt("record_resolution_height");
            if (i14 % 16 != 0 || i14 < 160 || i14 > 5120) {
                Log.w(TAG, "Fetched config doesn't pass:(value % 16 == 0 && value >= 160 && value <= 5120) record_resolution_height = " + i14);
                hashMap.put("record_resolution_height", "1024");
            } else {
                hashMap.put("record_resolution_height", String.valueOf(i14));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: record_resolution_height");
            hashMap.put("record_resolution_height", "1024");
        }
        if (jSONObject.has("import_video_sw_crf")) {
            int i15 = jSONObject.getInt("import_video_sw_crf");
            if (i15 < 1 || i15 > 50) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 1 && value <= 50) import_video_sw_crf = " + i15);
                hashMap.put("import_video_sw_crf", "15");
            } else {
                hashMap.put("import_video_sw_crf", String.valueOf(i15));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_video_sw_crf");
            hashMap.put("import_video_sw_crf", "15");
        }
        if (jSONObject.has("import_video_sw_maxrate")) {
            int i16 = jSONObject.getInt("import_video_sw_maxrate");
            if (i16 < 100000 || i16 > 100000000) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 100000 && value <= 100000000) import_video_sw_maxrate = " + i16);
                hashMap.put("import_video_sw_maxrate", "15000000");
            } else {
                hashMap.put("import_video_sw_maxrate", String.valueOf(i16));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_video_sw_maxrate");
            hashMap.put("import_video_sw_maxrate", "15000000");
        }
        if (jSONObject.has("import_video_sw_preset")) {
            int i17 = jSONObject.getInt("import_video_sw_preset");
            if (i17 < 0 || i17 > 9) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0 && value <= 9) import_video_sw_preset = " + i17);
                hashMap.put("import_video_sw_preset", "0");
            } else {
                hashMap.put("import_video_sw_preset", String.valueOf(i17));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_video_sw_preset");
            hashMap.put("import_video_sw_preset", "0");
        }
        if (jSONObject.has("import_video_sw_gop")) {
            int i18 = jSONObject.getInt("import_video_sw_gop");
            if (i18 >= 1) {
                hashMap.put("import_video_sw_gop", String.valueOf(i18));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 1) import_video_sw_gop = " + i18);
                hashMap.put("import_video_sw_gop", "35");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_video_sw_gop");
            hashMap.put("import_video_sw_gop", "35");
        }
        if (jSONObject.has("import_video_sw_qp")) {
            int i19 = jSONObject.getInt("import_video_sw_qp");
            if (i19 < 1 || i19 > 50) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 1 && value <= 50) import_video_sw_qp = " + i19);
                hashMap.put("import_video_sw_qp", "2");
            } else {
                hashMap.put("import_video_sw_qp", String.valueOf(i19));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_video_sw_qp");
            hashMap.put("import_video_sw_qp", "2");
        }
        if (jSONObject.has("import_sw_bitrate_mode")) {
            int i20 = jSONObject.getInt("import_sw_bitrate_mode");
            if (i20 < 0 || i20 > 2) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0 && value <= 2) import_sw_bitrate_mode = " + i20);
                hashMap.put("import_sw_bitrate_mode", "0");
            } else {
                hashMap.put("import_sw_bitrate_mode", String.valueOf(i20));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_sw_bitrate_mode");
            hashMap.put("import_sw_bitrate_mode", "0");
        }
        if (jSONObject.has("import_encode_mode")) {
            int i21 = jSONObject.getInt("import_encode_mode");
            if (i21 == 0 || i21 == 1) {
                hashMap.put("import_encode_mode", String.valueOf(i21));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value == 0 || value == 1) import_encode_mode = " + i21);
                hashMap.put("import_encode_mode", "0");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_encode_mode");
            hashMap.put("import_encode_mode", "0");
        }
        if (jSONObject.has("import_video_hw_bitrate")) {
            int i22 = jSONObject.getInt("import_video_hw_bitrate");
            if (i22 > 0) {
                hashMap.put("import_video_hw_bitrate", String.valueOf(i22));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value > 0) import_video_hw_bitrate = " + i22);
                hashMap.put("import_video_hw_bitrate", "4194304");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_video_hw_bitrate");
            hashMap.put("import_video_hw_bitrate", "4194304");
        }
        if (jSONObject.has("import_hw_bitrate_mode")) {
            int i23 = jSONObject.getInt("import_hw_bitrate_mode");
            if (i23 >= 0) {
                hashMap.put("import_hw_bitrate_mode", String.valueOf(i23));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0) import_hw_bitrate_mode = " + i23);
                hashMap.put("import_hw_bitrate_mode", "0");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_hw_bitrate_mode");
            hashMap.put("import_hw_bitrate_mode", "0");
        }
        if (jSONObject.has("import_hw_profile")) {
            int i24 = jSONObject.getInt("import_hw_profile");
            if (i24 >= 0) {
                hashMap.put("import_hw_profile", String.valueOf(i24));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0) import_hw_profile = " + i24);
                hashMap.put("import_hw_profile", "0");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_hw_profile");
            hashMap.put("import_hw_profile", "0");
        }
        if (jSONObject.has("import_shorter_pixels")) {
            int i25 = jSONObject.getInt("import_shorter_pixels");
            if (i25 % 16 != 0 || i25 < 160 || i25 > 5120) {
                Log.w(TAG, "Fetched config doesn't pass:(value % 16 == 0 && value >= 160 && value <= 5120) import_shorter_pixels = " + i25);
                hashMap.put("import_shorter_pixels", "576");
            } else {
                hashMap.put("import_shorter_pixels", String.valueOf(i25));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: import_shorter_pixels");
            hashMap.put("import_shorter_pixels", "576");
        }
        if (jSONObject.has("synthetic_video_sw_crf")) {
            int i26 = jSONObject.getInt("synthetic_video_sw_crf");
            if (i26 < 1 || i26 > 50) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 1 && value <= 50) synthetic_video_sw_crf = " + i26);
                hashMap.put("synthetic_video_sw_crf", "15");
            } else {
                hashMap.put("synthetic_video_sw_crf", String.valueOf(i26));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: synthetic_video_sw_crf");
            hashMap.put("synthetic_video_sw_crf", "15");
        }
        if (jSONObject.has("synthetic_video_sw_maxrate")) {
            int i27 = jSONObject.getInt("synthetic_video_sw_maxrate");
            if (i27 < 100000 || i27 > 100000000) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 100000 && value <= 100000000) synthetic_video_sw_maxrate = " + i27);
                hashMap.put("synthetic_video_sw_maxrate", "15000000");
            } else {
                hashMap.put("synthetic_video_sw_maxrate", String.valueOf(i27));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: synthetic_video_sw_maxrate");
            hashMap.put("synthetic_video_sw_maxrate", "15000000");
        }
        if (jSONObject.has("synthetic_video_sw_preset")) {
            int i28 = jSONObject.getInt("synthetic_video_sw_preset");
            if (i28 < 0 || i28 > 9) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0 && value <= 9) synthetic_video_sw_preset = " + i28);
                hashMap.put("synthetic_video_sw_preset", "0");
            } else {
                hashMap.put("synthetic_video_sw_preset", String.valueOf(i28));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: synthetic_video_sw_preset");
            hashMap.put("synthetic_video_sw_preset", "0");
        }
        if (jSONObject.has("synthetic_video_sw_gop")) {
            int i29 = jSONObject.getInt("synthetic_video_sw_gop");
            if (i29 >= 1) {
                hashMap.put("synthetic_video_sw_gop", String.valueOf(i29));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 1) synthetic_video_sw_gop = " + i29);
                hashMap.put("synthetic_video_sw_gop", "35");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: synthetic_video_sw_gop");
            hashMap.put("synthetic_video_sw_gop", "35");
        }
        if (jSONObject.has("synthetic_video_sw_qp")) {
            int i30 = jSONObject.getInt("synthetic_video_sw_qp");
            if (i30 < 1 || i30 > 50) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 1 && value <= 50) synthetic_video_sw_qp = " + i30);
                hashMap.put("synthetic_video_sw_qp", "2");
            } else {
                hashMap.put("synthetic_video_sw_qp", String.valueOf(i30));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: synthetic_video_sw_qp");
            hashMap.put("synthetic_video_sw_qp", "2");
        }
        if (jSONObject.has("synthetic_sw_bitrate_mode")) {
            int i31 = jSONObject.getInt("synthetic_sw_bitrate_mode");
            if (i31 < 0 || i31 > 2) {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0 && value <= 2) synthetic_sw_bitrate_mode = " + i31);
                hashMap.put("synthetic_sw_bitrate_mode", "1");
            } else {
                hashMap.put("synthetic_sw_bitrate_mode", String.valueOf(i31));
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: synthetic_sw_bitrate_mode");
            hashMap.put("synthetic_sw_bitrate_mode", "1");
        }
        if (jSONObject.has("synthetic_encode_mode")) {
            int i32 = jSONObject.getInt("synthetic_encode_mode");
            if (i32 == 0 || i32 == 1) {
                hashMap.put("synthetic_encode_mode", String.valueOf(i32));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value == 0 || value == 1) synthetic_encode_mode = " + i32);
                hashMap.put("synthetic_encode_mode", "0");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: synthetic_encode_mode");
            hashMap.put("synthetic_encode_mode", "0");
        }
        if (jSONObject.has("synthetic_video_hw_bitrate")) {
            int i33 = jSONObject.getInt("synthetic_video_hw_bitrate");
            if (i33 >= 0) {
                hashMap.put("synthetic_video_hw_bitrate", String.valueOf(i33));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0) synthetic_video_hw_bitrate = " + i33);
                hashMap.put("synthetic_video_hw_bitrate", "4194304");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: synthetic_video_hw_bitrate");
            hashMap.put("synthetic_video_hw_bitrate", "4194304");
        }
        if (jSONObject.has("synthetic_hw_bitrate_mode")) {
            int i34 = jSONObject.getInt("synthetic_hw_bitrate_mode");
            if (i34 >= 0) {
                hashMap.put("synthetic_hw_bitrate_mode", String.valueOf(i34));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0) synthetic_hw_bitrate_mode = " + i34);
                hashMap.put("synthetic_hw_bitrate_mode", "0");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: synthetic_hw_bitrate_mode");
            hashMap.put("synthetic_hw_bitrate_mode", "0");
        }
        if (jSONObject.has("synthetic_hw_profile")) {
            int i35 = jSONObject.getInt("synthetic_hw_profile");
            if (i35 >= 0) {
                hashMap.put("synthetic_hw_profile", String.valueOf(i35));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value >= 0) synthetic_hw_profile = " + i35);
                hashMap.put("synthetic_hw_profile", "0");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: synthetic_hw_profile");
            hashMap.put("synthetic_hw_profile", "0");
        }
        if (jSONObject.has("earphone_echo_normal")) {
            int i36 = jSONObject.getInt("earphone_echo_normal");
            if (i36 == 0 || i36 == 1) {
                hashMap.put("earphone_echo_normal", String.valueOf(i36));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value == 0 || value == 1) earphone_echo_normal = " + i36);
                hashMap.put("earphone_echo_normal", "1");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: earphone_echo_normal");
            hashMap.put("earphone_echo_normal", "1");
        }
        if (jSONObject.has("earphone_echo_aaudio")) {
            int i37 = jSONObject.getInt("earphone_echo_aaudio");
            if (i37 == 0 || i37 == 1) {
                hashMap.put("earphone_echo_aaudio", String.valueOf(i37));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value == 0 || value == 1) earphone_echo_aaudio = " + i37);
                hashMap.put("earphone_echo_aaudio", "1");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: earphone_echo_aaudio");
            hashMap.put("earphone_echo_aaudio", "1");
        }
        if (jSONObject.has("earphone_echo_huawei")) {
            int i38 = jSONObject.getInt("earphone_echo_huawei");
            if (i38 == 0 || i38 == 1) {
                hashMap.put("earphone_echo_huawei", String.valueOf(i38));
            } else {
                Log.w(TAG, "Fetched config doesn't pass:(value == 0 || value == 1) earphone_echo_huawei = " + i38);
                hashMap.put("earphone_echo_huawei", "1");
            }
        } else {
            Log.w(TAG, "Fetched config doesn't contain: earphone_echo_huawei");
            hashMap.put("earphone_echo_huawei", "1");
        }
        return hashMap;
    }

    private void fillWithDefaultValue(@NonNull VECloudConfig vECloudConfig) {
        vECloudConfig.mRecordCameraType = 1;
        vECloudConfig.mRecordCameraCompatLevel = 1;
        vECloudConfig.mRecordSWEncodeCRF = 15;
        vECloudConfig.mRecordVideoSWMaxrate = 15000000;
        vECloudConfig.mRecordVideoSWPreset = 0;
        vECloudConfig.mRecordVideoSWGop = 35;
        vECloudConfig.mRecordVideoSWQP = 2;
        vECloudConfig.mRecordSWBitrateMode = 1;
        vECloudConfig.mRecordHWEncodeBPS = 4194304;
        vECloudConfig.mRecordEncodeMode = 0;
        vECloudConfig.mRecordHwBitrateMode = 0;
        vECloudConfig.mRecordHwProfile = 0;
        vECloudConfig.mRecordResolutionWidth = 576;
        vECloudConfig.mRecordResolutionHeight = 1024;
        vECloudConfig.mImportSWEncodeCRF = 15;
        vECloudConfig.mImportVideoSWMaxrate = 15000000;
        vECloudConfig.mImportVideoSWPreset = 0;
        vECloudConfig.mImportVideoSWGop = 35;
        vECloudConfig.mImportVideoSWQP = 2;
        vECloudConfig.mImportSWBitrateMode = 0;
        vECloudConfig.mImportEncodeMode = 0;
        vECloudConfig.mImportHWEncodeBPS = 4194304;
        vECloudConfig.mImportHwBitrateMode = 0;
        vECloudConfig.mImportHwProfile = 0;
        vECloudConfig.mImportShortEdgeValue = 576;
        vECloudConfig.mCompileEncodeSWCRF = 15;
        vECloudConfig.mCompileEncodeSWMaxrate = 15000000;
        vECloudConfig.mCompileEncodeSWCRFPreset = 0;
        vECloudConfig.mCompileEncodeSWGOP = 35;
        vECloudConfig.mCompileVideoSWQP = 2;
        vECloudConfig.mCompileSWBitrateMode = 1;
        vECloudConfig.mCompileEncodeMode = 0;
        vECloudConfig.mCompileEncodeHWBPS = 4194304;
        vECloudConfig.mCompileHwBitrateMode = 0;
        vECloudConfig.mCompileHwProfile = 0;
        vECloudConfig.mEarphoneEchoNormal = 1;
        vECloudConfig.mEarphoneEchoAAudio = 1;
        vECloudConfig.mEarphoneEchoHuawei = 1;
    }

    @Override // com.ss.android.vesdk.runtime.cloudconfig.IInjector
    public void inject(Map<String, String> map, @NonNull VECloudConfig vECloudConfig) {
        fillWithDefaultValue(vECloudConfig);
        if (map == null) {
            Log.w(TAG, "Inject source map is null. Everything will be overridden with default value in CloudConfig!!!");
        }
        if (map.containsKey("record_camera_type")) {
            vECloudConfig.mRecordCameraType = Integer.parseInt(map.get("record_camera_type"));
        }
        if (map.containsKey("record_camera_compat_level")) {
            vECloudConfig.mRecordCameraCompatLevel = Integer.parseInt(map.get("record_camera_compat_level"));
        }
        if (map.containsKey("record_video_sw_crf")) {
            vECloudConfig.mRecordSWEncodeCRF = Integer.parseInt(map.get("record_video_sw_crf"));
        }
        if (map.containsKey("record_video_sw_maxrate")) {
            vECloudConfig.mRecordVideoSWMaxrate = Integer.parseInt(map.get("record_video_sw_maxrate"));
        }
        if (map.containsKey("record_video_sw_preset")) {
            vECloudConfig.mRecordVideoSWPreset = Integer.parseInt(map.get("record_video_sw_preset"));
        }
        if (map.containsKey("record_video_sw_gop")) {
            vECloudConfig.mRecordVideoSWGop = Integer.parseInt(map.get("record_video_sw_gop"));
        }
        if (map.containsKey("record_video_sw_qp")) {
            vECloudConfig.mRecordVideoSWQP = Integer.parseInt(map.get("record_video_sw_qp"));
        }
        if (map.containsKey("record_sw_bitrate_mode")) {
            vECloudConfig.mRecordSWBitrateMode = Integer.parseInt(map.get("record_sw_bitrate_mode"));
        }
        if (map.containsKey("record_video_hw_bitrate")) {
            vECloudConfig.mRecordHWEncodeBPS = Integer.parseInt(map.get("record_video_hw_bitrate"));
        }
        if (map.containsKey("record_encode_mode")) {
            vECloudConfig.mRecordEncodeMode = Integer.parseInt(map.get("record_encode_mode"));
        }
        if (map.containsKey("record_hw_bitrate_mode")) {
            vECloudConfig.mRecordHwBitrateMode = Integer.parseInt(map.get("record_hw_bitrate_mode"));
        }
        if (map.containsKey("record_hw_profile")) {
            vECloudConfig.mRecordHwProfile = Integer.parseInt(map.get("record_hw_profile"));
        }
        if (map.containsKey("record_resolution_width")) {
            vECloudConfig.mRecordResolutionWidth = Integer.parseInt(map.get("record_resolution_width"));
        }
        if (map.containsKey("record_resolution_height")) {
            vECloudConfig.mRecordResolutionHeight = Integer.parseInt(map.get("record_resolution_height"));
        }
        if (map.containsKey("import_video_sw_crf")) {
            vECloudConfig.mImportSWEncodeCRF = Integer.parseInt(map.get("import_video_sw_crf"));
        }
        if (map.containsKey("import_video_sw_maxrate")) {
            vECloudConfig.mImportVideoSWMaxrate = Integer.parseInt(map.get("import_video_sw_maxrate"));
        }
        if (map.containsKey("import_video_sw_preset")) {
            vECloudConfig.mImportVideoSWPreset = Integer.parseInt(map.get("import_video_sw_preset"));
        }
        if (map.containsKey("import_video_sw_gop")) {
            vECloudConfig.mImportVideoSWGop = Integer.parseInt(map.get("import_video_sw_gop"));
        }
        if (map.containsKey("import_video_sw_qp")) {
            vECloudConfig.mImportVideoSWQP = Integer.parseInt(map.get("import_video_sw_qp"));
        }
        if (map.containsKey("import_sw_bitrate_mode")) {
            vECloudConfig.mImportSWBitrateMode = Integer.parseInt(map.get("import_sw_bitrate_mode"));
        }
        if (map.containsKey("import_encode_mode")) {
            vECloudConfig.mImportEncodeMode = Integer.parseInt(map.get("import_encode_mode"));
        }
        if (map.containsKey("import_video_hw_bitrate")) {
            vECloudConfig.mImportHWEncodeBPS = Integer.parseInt(map.get("import_video_hw_bitrate"));
        }
        if (map.containsKey("import_hw_bitrate_mode")) {
            vECloudConfig.mImportHwBitrateMode = Integer.parseInt(map.get("import_hw_bitrate_mode"));
        }
        if (map.containsKey("import_hw_profile")) {
            vECloudConfig.mImportHwProfile = Integer.parseInt(map.get("import_hw_profile"));
        }
        if (map.containsKey("import_shorter_pixels")) {
            vECloudConfig.mImportShortEdgeValue = Integer.parseInt(map.get("import_shorter_pixels"));
        }
        if (map.containsKey("synthetic_video_sw_crf")) {
            vECloudConfig.mCompileEncodeSWCRF = Integer.parseInt(map.get("synthetic_video_sw_crf"));
        }
        if (map.containsKey("synthetic_video_sw_maxrate")) {
            vECloudConfig.mCompileEncodeSWMaxrate = Integer.parseInt(map.get("synthetic_video_sw_maxrate"));
        }
        if (map.containsKey("synthetic_video_sw_preset")) {
            vECloudConfig.mCompileEncodeSWCRFPreset = Integer.parseInt(map.get("synthetic_video_sw_preset"));
        }
        if (map.containsKey("synthetic_video_sw_gop")) {
            vECloudConfig.mCompileEncodeSWGOP = Integer.parseInt(map.get("synthetic_video_sw_gop"));
        }
        if (map.containsKey("synthetic_video_sw_qp")) {
            vECloudConfig.mCompileVideoSWQP = Integer.parseInt(map.get("synthetic_video_sw_qp"));
        }
        if (map.containsKey("synthetic_sw_bitrate_mode")) {
            vECloudConfig.mCompileSWBitrateMode = Integer.parseInt(map.get("synthetic_sw_bitrate_mode"));
        }
        if (map.containsKey("synthetic_encode_mode")) {
            vECloudConfig.mCompileEncodeMode = Integer.parseInt(map.get("synthetic_encode_mode"));
        }
        if (map.containsKey("synthetic_video_hw_bitrate")) {
            vECloudConfig.mCompileEncodeHWBPS = Integer.parseInt(map.get("synthetic_video_hw_bitrate"));
        }
        if (map.containsKey("synthetic_hw_bitrate_mode")) {
            vECloudConfig.mCompileHwBitrateMode = Integer.parseInt(map.get("synthetic_hw_bitrate_mode"));
        }
        if (map.containsKey("synthetic_hw_profile")) {
            vECloudConfig.mCompileHwProfile = Integer.parseInt(map.get("synthetic_hw_profile"));
        }
        if (map.containsKey("earphone_echo_normal")) {
            vECloudConfig.mEarphoneEchoNormal = Integer.parseInt(map.get("earphone_echo_normal"));
        }
        if (map.containsKey("earphone_echo_aaudio")) {
            vECloudConfig.mEarphoneEchoAAudio = Integer.parseInt(map.get("earphone_echo_aaudio"));
        }
        if (map.containsKey("earphone_echo_huawei")) {
            vECloudConfig.mEarphoneEchoHuawei = Integer.parseInt(map.get("earphone_echo_huawei"));
        }
    }

    @Override // com.ss.android.vesdk.runtime.cloudconfig.IInjector
    public Map<String, String> parse(@NonNull JSONObject jSONObject) {
        try {
            if (jSONObject.getInt("code") == 0) {
                return createParamMap(jSONObject.getJSONObject(PhotosOemApi.PATH_SPECIAL_TYPE_DATA));
            }
            Log.e(TAG, "Fetched Config return code is not 0 but " + jSONObject.getInt("code"));
            return null;
        } catch (JSONException e2) {
            Log.e(TAG, e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }
}
