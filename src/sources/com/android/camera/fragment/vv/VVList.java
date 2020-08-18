package com.android.camera.fragment.vv;

import com.android.camera.data.DataRepository;
import com.android.camera.data.data.extra.DataItemLive;
import com.android.camera.resource.BaseResourceList;
import com.google.android.apps.photos.api.PhotosOemApi;
import org.json.JSONArray;
import org.json.JSONObject;

public class VVList extends BaseResourceList<VVItem> {
    public static final int TYPE = 1;

    @Override // com.android.camera.resource.BaseResourceList
    public JSONArray getItemJsonArray(JSONObject jSONObject) {
        return jSONObject.optJSONArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA);
    }

    @Override // com.android.camera.resource.BaseResourceList
    public String getLocalVersion() {
        return DataRepository.dataItemLive().getString(DataItemLive.DATA_VV_VERSION, "");
    }

    @Override // com.android.camera.resource.BaseResourceList
    public int getResourceType() {
        return 1;
    }

    @Override // com.android.camera.resource.BaseResourceList
    public void parseInitialData(JSONObject jSONObject) {
        ((BaseResourceList) this).version = jSONObject.optString("version");
    }

    @Override // com.android.camera.resource.BaseResourceList
    public VVItem parseSingleItem(JSONObject jSONObject, int i) {
        VVItem vVItem = new VVItem();
        vVItem.parseSummaryData(jSONObject, i);
        return vVItem;
    }

    @Override // com.android.camera.resource.BaseResourceList
    public void setLocalVersion(String str) {
        DataRepository.dataItemLive().editor().putString(DataItemLive.DATA_VV_VERSION, str).apply();
    }
}
