package miui.cloud.backup.data;

import android.os.Parcelable;
import com.xiaomi.stat.MiStat;
import org.json.JSONObject;

public class KeyStringSettingItem extends SettingItem<String> {
    public static final Parcelable.Creator<KeyStringSettingItem> CREATOR = new d();
    public static final String TYPE = "string";

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public Object Bm() {
        return getValue();
    }

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public String H(String str) {
        return str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: I */
    public String s(String str) {
        return str;
    }

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public void c(JSONObject jSONObject) {
        setValue(jSONObject.optString(MiStat.Param.VALUE));
    }

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public String getType() {
        return TYPE;
    }
}
