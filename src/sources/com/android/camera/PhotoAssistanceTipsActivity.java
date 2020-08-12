package com.android.camera;

import a.a.a;
import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;

public class PhotoAssistanceTipsActivity extends BasePreferenceActivity {
    private static final String TAG = "PhotoAssistanceTipsActivity";
    protected PreferenceScreen mPreferenceGroup;

    private void filterByFeature() {
        if (!DataRepository.dataItemFeature().c_19039_0x0008()) {
            removePreference(this.mPreferenceGroup, "pref_pic_flaw_tip");
        }
        if (!DataRepository.dataItemFeature().c_0x08()) {
            removePreference(this.mPreferenceGroup, "pref_lens_dirty_tip");
        }
        if (!DataRepository.dataItemFeature().c_0x48()) {
            removePreference(this.mPreferenceGroup, "pref_camera_lying_tip_switch_key");
        }
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
        filterByFeature();
        registerListener(this.mPreferenceGroup, this);
        updatePreferences(this.mPreferenceGroup, ((BasePreferenceActivity) this).mPreferences);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.BasePreferenceActivity
    public int getPreferenceXml() {
        return R.xml.camera_preferences_photo_assistance_tips;
    }

    @Override // com.android.camera.BasePreferenceActivity
    public void onCreate(Bundle bundle) {
        ActionBar actionBar;
        super.onCreate(bundle);
        if (getIntent().getBooleanExtra(a.mf, false) == 1) {
            setShowWhenLocked(true);
        }
        if (!(getIntent().getCharSequenceExtra(a.zf) == null || (actionBar = getActionBar()) == null)) {
            actionBar.setTitle(R.string.photo_assistance_tips_title);
        }
        initializeActivity();
    }

    @Override // com.android.camera.BasePreferenceActivity
    public boolean onPreferenceChange(Preference preference, Object obj) {
        return super.onPreferenceChange(preference, obj);
    }

    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        updatePreferences(this.mPreferenceGroup, ((BasePreferenceActivity) this).mPreferences);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.BasePreferenceActivity
    public void updateConflictPreference(Preference preference) {
    }

    @Override // com.android.camera.BasePreferenceActivity
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
