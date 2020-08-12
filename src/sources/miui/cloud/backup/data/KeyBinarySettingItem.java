package miui.cloud.backup.data;

import android.os.Parcelable;
import android.util.Base64;
import com.xiaomi.stat.MiStat;
import org.json.JSONObject;

public class KeyBinarySettingItem extends SettingItem<byte[]> {
    public static final Parcelable.Creator<KeyBinarySettingItem> CREATOR = new b();
    public static final String TYPE = "binary";

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public Object Bm() {
        return s((byte[]) getValue());
    }

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public byte[] H(String str) {
        return Base64.decode(str, 2);
    }

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public void c(JSONObject jSONObject) {
        setValue(H(jSONObject.optString(MiStat.Param.VALUE)));
    }

    /* access modifiers changed from: protected */
    @Override // miui.cloud.backup.data.SettingItem
    public String getType() {
        return TYPE;
    }

    /* access modifiers changed from: protected */
    /* renamed from: k */
    public String s(byte[] bArr) {
        return Base64.encodeToString(bArr, 2);
    }
}
