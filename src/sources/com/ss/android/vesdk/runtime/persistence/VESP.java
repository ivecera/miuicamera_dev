package com.ss.android.vesdk.runtime.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import java.util.Map;

public class VESP {
    public static final String KEY_DEVICEID = "KEY_DEVICEID";
    public static final String KEY_MODELS_DIR_SP_KEY = "vesdk_models_dir_sp_key";
    public static final String KEY_SENSOR_REPORTED = "sensor_reported";
    private SharedPreferences.Editor editor;
    private boolean mInited;
    private SharedPreferences sharedPreferences;

    private enum VESPSingleton {
        INSTANCE;
        
        private VESP mInstance = new VESP();

        private VESPSingleton() {
        }

        public VESP getInstance() {
            return this.mInstance;
        }
    }

    private VESP() {
        this.mInited = false;
    }

    public static VESP getInstance() {
        return VESPSingleton.INSTANCE.getInstance();
    }

    private synchronized void initEditorIfNeed() {
        if (this.editor == null) {
            this.editor = this.sharedPreferences.edit();
        }
    }

    public void clear() {
        initEditorIfNeed();
        this.editor.clear();
        this.editor.commit();
    }

    public boolean contain(@NonNull String str) {
        return this.sharedPreferences.contains(str);
    }

    public <T> T get(@NonNull String str, @NonNull T t) {
        return t instanceof String ? this.sharedPreferences.getString(str, t) : t instanceof Integer ? Integer.valueOf(this.sharedPreferences.getInt(str, t.intValue())) : t instanceof Boolean ? Boolean.valueOf(this.sharedPreferences.getBoolean(str, t.booleanValue())) : t instanceof Float ? Float.valueOf(this.sharedPreferences.getFloat(str, t.floatValue())) : t instanceof Long ? Long.valueOf(this.sharedPreferences.getLong(str, t.longValue())) : this.sharedPreferences.getString(str, null);
    }

    public Map<String, ?> getAll() {
        return this.sharedPreferences.getAll();
    }

    public void init(Context context) {
        synchronized (this) {
            if (!this.mInited) {
                this.sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
                this.mInited = true;
            }
        }
    }

    public void put(@NonNull String str, @NonNull Object obj) {
        put(str, obj, false);
    }

    public void put(@NonNull String str, @NonNull Object obj, boolean z) {
        initEditorIfNeed();
        if (obj instanceof String) {
            this.editor.putString(str, (String) obj);
        } else if (obj instanceof Integer) {
            this.editor.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Boolean) {
            this.editor.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Float) {
            this.editor.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Long) {
            this.editor.putLong(str, ((Long) obj).longValue());
        } else {
            this.editor.putString(str, obj.toString());
        }
        if (z) {
            this.editor.apply();
        } else {
            this.editor.commit();
        }
    }

    public void remove(@NonNull String str) {
        initEditorIfNeed();
        this.editor.remove(str);
        this.editor.commit();
    }
}
