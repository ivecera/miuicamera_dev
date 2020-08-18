package com.android.camera;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import com.android.camera.log.Log;
import com.android.camera.preferences.CameraSettingPreferences;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.statistic.SettingRecord;
import com.android.camera.ui.PreviewListPreference;
import com.mi.config.b;
import java.util.List;
import miui.preference.PreferenceActivity;

public abstract class BasePreferenceActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public static final String FROM_WHERE = "from_where";
    private static final String TAG = "BasePreferenceActivity";
    protected int mFromWhere;
    protected CameraSettingPreferences mPreferences;

    /* access modifiers changed from: protected */
    public boolean addPreference(PreferenceGroup preferenceGroup, String str, Preference preference) {
        Preference findPreference = preferenceGroup.findPreference(str);
        if ((findPreference instanceof PreferenceGroup) != 1) {
            return false;
        }
        ((PreferenceGroup) findPreference).addPreference(preference);
        return true;
    }

    public void changeRequestOrientation() {
        if (b.el()) {
            if (CameraSettings.isFrontCamera() == 1) {
                setRequestedOrientation(7);
            } else {
                setRequestedOrientation(1);
            }
        }
    }

    public void filterUnsupportedOptions(PreferenceGroup preferenceGroup, PreviewListPreference previewListPreference, List<String> list) {
        if (list == null || list.size() <= 1) {
            removePreference(preferenceGroup, previewListPreference.getKey());
            return;
        }
        previewListPreference.filterUnsupported(list);
        if (previewListPreference.getEntries().length <= 1) {
            removePreference(preferenceGroup, previewListPreference.getKey());
        } else {
            resetIfInvalid(previewListPreference);
        }
    }

    /* access modifiers changed from: protected */
    public abstract int getPreferenceXml();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MistatsWrapper.initialize(CameraApplicationDelegate.getAndroidContext());
        Util.updateDeviceConfig(this);
        this.mPreferences = CameraSettingPreferences.instance();
        changeRequestOrientation();
    }

    public boolean onPreferenceChange(Preference preference, Object obj) {
        onSettingChanged(1);
        SharedPreferences.Editor edit = this.mPreferences.edit();
        String key = preference.getKey();
        if ((obj instanceof String) == 1) {
            edit.putString(key, (String) obj);
        } else if ((obj instanceof Boolean) == 1) {
            edit.putBoolean(key, ((Boolean) obj).booleanValue());
        } else if ((obj instanceof Integer) == 1) {
            edit.putInt(key, ((Integer) obj).intValue());
        } else if ((obj instanceof Long) == 1) {
            edit.putLong(key, ((Long) obj).longValue());
        } else if ((obj instanceof Float) == 1) {
            edit.putFloat(key, ((Float) obj).floatValue());
        } else {
            throw new IllegalStateException("unhandled new value with type=" + obj.getClass().getName());
        }
        edit.apply();
        MistatsWrapper.settingClickEvent(SettingRecord.getMistatString(key), obj);
        updateConflictPreference(preference);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onSettingChanged(int i) {
        CameraSettings.sCameraChangeManager.request(i);
    }

    /* access modifiers changed from: protected */
    public void registerListener(PreferenceGroup preferenceGroup, Preference.OnPreferenceChangeListener onPreferenceChangeListener) {
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if ((preference instanceof PreferenceGroup) == 1) {
                registerListener((PreferenceGroup) preference, onPreferenceChangeListener);
            } else {
                preference.setOnPreferenceChangeListener(onPreferenceChangeListener);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void removeFromGroup(Preference preference, String str) {
        if (preference != null && (preference instanceof PreferenceGroup)) {
            PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
            if (preferenceGroup.getPreferenceCount() > 0) {
                removePreference(preferenceGroup, str);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean removePreference(PreferenceGroup preferenceGroup, String str) {
        Preference findPreference = preferenceGroup.findPreference(str);
        if (findPreference != null && preferenceGroup.removePreference(findPreference)) {
            return true;
        }
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if ((preference instanceof PreferenceGroup) && removePreference((PreferenceGroup) preference, str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void resetIfInvalid(ListPreference listPreference) {
        if (listPreference.findIndexOfValue(listPreference.getValue()) == -1) {
            listPreference.setValueIndex(0);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void updateConflictPreference(Preference preference);

    public void updatePreferences(PreferenceGroup preferenceGroup, SharedPreferences sharedPreferences) {
        if (preferenceGroup != null) {
            int preferenceCount = preferenceGroup.getPreferenceCount();
            for (int i = 0; i < preferenceCount; i++) {
                Preference preference = preferenceGroup.getPreference(i);
                if ((preference instanceof CheckBoxPreference) == 1) {
                    CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                    checkBoxPreference.setChecked(sharedPreferences.getBoolean(checkBoxPreference.getKey(), checkBoxPreference.isChecked()));
                    preference.setPersistent(false);
                } else if ((preference instanceof PreferenceGroup) == 1) {
                    updatePreferences((PreferenceGroup) preference, sharedPreferences);
                } else {
                    Log.v(TAG, "no need update preference for " + preference.getKey());
                }
            }
        }
    }
}
