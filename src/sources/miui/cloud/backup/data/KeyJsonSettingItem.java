package miui.cloud.backup.data;

import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.stat.MiStat;
import d.a.a.c;
import org.json.JSONException;
import org.json.JSONObject;

public class KeyJsonSettingItem extends SettingItem<JSONObject> {
    public static final Parcelable.Creator<KeyJsonSettingItem> CREATOR = new c();
    public static final String TYPE = "json";

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public Object Bm() {
        return getValue();
    }

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public JSONObject H(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e2) {
            Log.e(c.TAG, "JSONException occorred when stringToValue()", e2);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public void c(JSONObject jSONObject) {
        setValue(jSONObject.optJSONObject(MiStat.Param.VALUE));
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public String s(JSONObject jSONObject) {
        return jSONObject.toString();
    }

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public String getType() {
        return TYPE;
    }
}
