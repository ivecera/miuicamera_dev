package miui.cloud.backup.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class SettingItem<T> implements Parcelable, Comparable<SettingItem<?>> {
    private static final String KEY_KEY = "key";
    private static final String KEY_TYPE = "type";
    protected static final String KEY_VALUE = "value";
    protected static final String TAG = "SettingsBackup";
    public String key;
    private T value;

    private static SettingItem<?> Z(String str) {
        if (KeyStringSettingItem.TYPE.equals(str)) {
            return new KeyStringSettingItem();
        }
        if (KeyBinarySettingItem.TYPE.equals(str)) {
            return new KeyBinarySettingItem();
        }
        if (KeyJsonSettingItem.TYPE.equals(str)) {
            return new KeyJsonSettingItem();
        }
        Log.w("SettingsBackup", "type: " + str + " are not handled!");
        return null;
    }

    public static SettingItem<?> b(JSONObject jSONObject) {
        if (jSONObject != null) {
            SettingItem<?> Z = Z(jSONObject.optString("type"));
            if (Z == null) {
                return null;
            }
            Z.key = jSONObject.optString("key");
            Z.c(jSONObject);
            return Z;
        }
        throw new IllegalArgumentException("json cannot be null");
    }

    /* access modifiers changed from: protected */
    public abstract Object Bm();

    /* access modifiers changed from: protected */
    public abstract T H(String str);

    /* renamed from: a */
    public int compareTo(SettingItem<?> settingItem) {
        if (settingItem == null) {
            return 1;
        }
        if (this.key != null || settingItem.key == null) {
            return this.key.compareTo(settingItem.key);
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public void c(Parcel parcel) {
        String readString = parcel.readString();
        String readString2 = parcel.readString();
        this.key = readString;
        setValue(H(readString2));
    }

    /* access modifiers changed from: protected */
    public abstract void c(JSONObject jSONObject);

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public abstract String getType();

    public T getValue() {
        return this.value;
    }

    /* access modifiers changed from: protected */
    public abstract String s(T t);

    public void setValue(T t) {
        this.value = t;
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("key", this.key);
            jSONObject.put("type", getType());
            jSONObject.put("value", Bm());
        } catch (JSONException e2) {
            Log.e("SettingsBackup", "JSONException occorred when toJson()", e2);
        }
        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        String s = s(getValue());
        parcel.writeString(this.key);
        parcel.writeString(s);
    }
}
