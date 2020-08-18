package com.android.camera.resource.tmmusic;

import com.android.camera.resource.BaseResourceCacheable;
import com.android.camera.resource.BaseResourceList;
import org.json.JSONArray;
import org.json.JSONObject;

public class TMMusicList extends BaseResourceList<TMMusicItem> implements BaseResourceCacheable {
    public static final String CACHE_INITIAL = "tt_initial";
    public static final String CACHE_LIST = "tt_list";
    public static final int TYPE = 2;
    private String categoryID;
    private String name;

    @Override // com.android.camera.resource.BaseResourceCacheable
    public long getCacheExpireTime() {
        return 5400000;
    }

    public String getCategoryID() {
        return this.categoryID;
    }

    @Override // com.android.camera.resource.BaseResourceList
    public JSONArray getItemJsonArray(JSONObject jSONObject) {
        return jSONObject.optJSONArray("stationItems");
    }

    @Override // com.android.camera.resource.BaseResourceList
    public String getLocalVersion() {
        return null;
    }

    @Override // com.android.camera.resource.BaseResourceList
    public int getResourceType() {
        return 2;
    }

    @Override // com.android.camera.resource.BaseResourceList
    public void parseInitialData(JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONArray("stations").optJSONObject(0);
        this.categoryID = optJSONObject.optString("categoryID");
        this.name = optJSONObject.optString("name");
    }

    @Override // com.android.camera.resource.BaseResourceList
    public TMMusicItem parseSingleItem(JSONObject jSONObject, int i) {
        TMMusicItem tMMusicItem = new TMMusicItem();
        tMMusicItem.parseSummaryData(jSONObject, i);
        return tMMusicItem;
    }

    @Override // com.android.camera.resource.BaseResourceList
    public void setLocalVersion(String str) {
    }
}
