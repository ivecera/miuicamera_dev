package com.android.camera.data.cloud;

import android.content.SharedPreferences;
import android.support.v4.util.SimpleArrayMap;
import com.android.camera.CameraAppImpl;
import com.android.camera.data.cloud.DataCloud;

public abstract class DataCloudItemBase implements DataCloud.CloudItem {
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;
    private boolean mReady;
    private SimpleArrayMap<String, Object> mValues = new SimpleArrayMap<>();

    private SharedPreferences getSharedPreferences() {
        if (this.mPreferences == null) {
            initPreferences();
        }
        return this.mPreferences;
    }

    private void initPreferences() {
        this.mPreferences = CameraAppImpl.getAndroidContext().getSharedPreferences(provideKey(), 0);
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public SharedPreferences.Editor editor() {
        this.mEditor = getSharedPreferences().edit();
        return this.mEditor;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public boolean getCloudBooleanDefault(String str, boolean z) {
        if (!this.mReady) {
            return z;
        }
        Boolean bool = (Boolean) this.mValues.get(str);
        if (bool == null) {
            bool = Boolean.valueOf(getSharedPreferences().getBoolean(str, z));
        }
        return bool == null ? z : bool.booleanValue();
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public float getCloudFloatDefault(String str, float f2) {
        if (!this.mReady) {
            return f2;
        }
        Float f3 = (Float) this.mValues.get(str);
        if (f3 == null) {
            f3 = Float.valueOf(getSharedPreferences().getFloat(str, f2));
        }
        return f3 == null ? f2 : f3.floatValue();
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public int getCloudIntDefault(String str, int i) {
        if (!this.mReady) {
            return i;
        }
        Integer num = (Integer) this.mValues.get(str);
        if (num == null) {
            num = Integer.valueOf(getSharedPreferences().getInt(str, i));
        }
        return num == null ? i : num.intValue();
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public long getCloudLongDefault(String str, long j) {
        if (!this.mReady) {
            return j;
        }
        Long l = (Long) this.mValues.get(str);
        if (l == null) {
            l = Long.valueOf(getSharedPreferences().getLong(str, j));
        }
        return l == null ? j : l.longValue();
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public String getCloudStringDefault(String str, String str2) {
        if (!this.mReady) {
            return str2;
        }
        String str3 = (String) this.mValues.get(str);
        String string = str3 == null ? getSharedPreferences().getString(str, str2) : str3;
        return string == null ? str2 : string;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public void setReady(boolean z) {
        this.mReady = z;
    }
}
