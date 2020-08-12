package com.android.camera.network.resource;

import com.android.camera.network.net.BaseGalleryRequest;
import com.android.camera.network.net.HttpManager;
import com.android.camera.network.net.base.ErrorCode;
import com.android.camera.network.resource.RequestContracts;
import com.android.camera.sticker.StickerInfo;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class StickerResourceRequest extends BaseGalleryRequest {
    private static final long EXPIRE_TIME = 147122892800L;
    private static final long SOFT_EXPIRE_TIME = 86400000;
    private static final String STICKER_LIST_DEFAULT = "sticker_list_default";
    private static final long STICKER_PARENT_ID = 7326868816920608L;

    public StickerResourceRequest() {
        super(0, RequestContracts.Info.URL);
        addParam("id", Long.toString(STICKER_PARENT_ID));
        setUseCache(true);
        setCacheExpires(System.currentTimeMillis() + EXPIRE_TIME);
        setCacheSoftTtl(System.currentTimeMillis() + 86400000);
        HttpManager.getInstance().putDefaultCache(getCacheKey(), STICKER_LIST_DEFAULT);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.network.net.BaseGalleryRequest
    public void onRequestSuccess(JSONObject jSONObject) throws Exception {
        try {
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray = jSONObject.getJSONArray(RequestContracts.Info.JSON_KEY_ITEMS);
            long optLong = jSONObject.optLong(RequestContracts.Info.EXPIRE_AT);
            if (optLong != 0) {
                setCacheSoftTtl(optLong);
            }
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    StickerInfo stickerInfo = new StickerInfo();
                    ((Resource) stickerInfo).id = jSONObject2.optLong("id");
                    ((Resource) stickerInfo).icon = jSONObject2.optString("icon");
                    ((Resource) stickerInfo).extra = jSONObject2.optString(RequestContracts.Info.JSON_KEY_EXTRA);
                    arrayList.add(stickerInfo);
                }
            }
            deliverResponse(arrayList);
        } catch (JsonParseException e2) {
            e2.printStackTrace();
            deliverError(ErrorCode.PARSE_ERROR, e2.getMessage(), jSONObject);
        } catch (Exception e3) {
            e3.printStackTrace();
            deliverError(ErrorCode.HANDLE_ERROR, e3.getMessage(), jSONObject);
        }
    }
}
