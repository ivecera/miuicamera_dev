package com.android.camera.resource.tmmusic;

import com.android.camera.CameraAppImpl;
import com.android.camera.resource.AESUtils;
import com.android.camera.resource.BaseRequestException;
import com.android.camera.resource.RequestHelper;
import com.android.camera.resource.SimpleNetworkJsonRequest;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class TMMusicStationsRequest extends SimpleNetworkJsonRequest<TMMusicList> {
    private static final String APP_ID = "RM";
    private static final String BASE_URL = "https://sapi.tingmall.com/SkymanWS/Category/Stations";

    public TMMusicStationsRequest(boolean z) {
        super(BASE_URL);
        String encryString = AESUtils.getEncryString(APP_ID, RequestHelper.md5((long) new Random().nextInt(100), System.currentTimeMillis()).substring(0, 16), RequestHelper.getTMMusicAccessKey());
        addHeaders("oauth_token", APP_ID + encryString);
        addParam("categorycode", "RM_Genre_CA");
    }

    public static String getRandomString(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(62)));
        }
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.resource.SimpleNetworkJsonRequest
    public TMMusicList parseJson(JSONObject jSONObject) throws BaseRequestException, JSONException {
        JSONObject optJSONObject = jSONObject.optJSONObject("response").optJSONObject("docs");
        writeToCache(TMMusicList.CACHE_INITIAL, CameraAppImpl.getAndroidContext(), optJSONObject.toString());
        TMMusicList tMMusicList = new TMMusicList();
        tMMusicList.parseInitialData(optJSONObject);
        return tMMusicList;
    }
}
