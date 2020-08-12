package com.ss.android.vesdk.keyvaluepair;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class VEKeyValue {
    private boolean mIsFirst = true;
    private Map<String, String> mKVPair = new HashMap();
    private StringBuilder mKVSB = new StringBuilder();

    private void addSB(String str, String str2) {
        if (!this.mIsFirst) {
            this.mKVSB.append(",");
        }
        this.mKVSB.append("\"");
        this.mKVSB.append(str);
        this.mKVSB.append("\"");
        this.mKVSB.append(":");
        this.mKVSB.append("\"");
        this.mKVSB.append(str2);
        this.mKVSB.append("\"");
        if (this.mIsFirst) {
            this.mIsFirst = false;
        }
    }

    public VEKeyValue add(String str, float f2) {
        Map<String, String> map = this.mKVPair;
        map.put(str, f2 + "");
        addSB(str, f2 + "");
        return this;
    }

    public VEKeyValue add(String str, int i) {
        Map<String, String> map = this.mKVPair;
        map.put(str, i + "");
        addSB(str, i + "");
        return this;
    }

    public VEKeyValue add(String str, String str2) {
        this.mKVPair.put(str, str2);
        addSB(str, str2);
        return this;
    }

    @Nullable
    public JSONObject parseJsonObj() {
        try {
            JSONObject jSONObject = new JSONObject();
            for (String str : this.mKVPair.keySet()) {
                jSONObject.put(str, this.mKVPair.get(str));
            }
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }

    @NonNull
    public String parseJsonStr() {
        return "{" + ((CharSequence) this.mKVSB) + "}";
    }
}
