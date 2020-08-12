package com.xiaomi.stat.b;

import com.xiaomi.stat.d.k;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class h {

    /* renamed from: a  reason: collision with root package name */
    static final String f461a = "key_update_time";

    /* renamed from: b  reason: collision with root package name */
    static final String f462b = "get_all_config";

    /* renamed from: c  reason: collision with root package name */
    static final String f463c = "mistats/v3";

    /* renamed from: d  reason: collision with root package name */
    static final String f464d = "key_get";

    /* renamed from: e  reason: collision with root package name */
    static final String f465e = "http://";

    /* renamed from: f  reason: collision with root package name */
    static final String f466f = "https://";
    static final String g = "/";
    private static final String h = "RegionManagerHelper";

    /* access modifiers changed from: package-private */
    public HashMap<String, String> a(String str, JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        if (optJSONObject != null) {
            HashMap<String, String> hashMap = new HashMap<>();
            k.b(h, "parse the map contains key:" + str);
            Iterator<String> keys = optJSONObject.keys();
            while (keys.hasNext()) {
                try {
                    String next = keys.next();
                    String string = optJSONObject.getString(next);
                    k.b(h, "[region]:" + next + "\n[domain]:" + string);
                    hashMap.put(next, string);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            return hashMap;
        }
        k.d(h, "can not find the specific key" + str);
        return null;
    }

    /* access modifiers changed from: package-private */
    public HashMap<String, String> a(HashMap<String, String> hashMap, HashMap<String, String> hashMap2) {
        HashMap<String, String> hashMap3 = new HashMap<>();
        if (hashMap2 != null) {
            hashMap3.putAll(hashMap2);
        }
        Set<String> keySet = hashMap.keySet();
        Set<String> keySet2 = hashMap3.keySet();
        for (String str : keySet) {
            if (!keySet2.contains(str)) {
                hashMap3.put(str, hashMap.get(str));
            }
        }
        return hashMap3;
    }
}
