package com.android.camera.resource.tmmusic;

import com.android.camera.fragment.music.LiveMusicInfo;
import com.android.camera.network.resource.RequestContracts;
import com.android.camera.resource.AESUtils;
import com.android.camera.resource.BaseRequestException;
import com.android.camera.resource.RequestHelper;
import com.android.camera.resource.SimpleNetworkJsonRequest;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class TMMusicItemRequest extends SimpleNetworkJsonRequest<LiveMusicInfo> {
    private static final String APP_ID = "RM";
    private static final String BASE_URL = "https://sapi.tingmall.com/SkymanWS/Streaming/MusicURL";
    private LiveMusicInfo liveMusicInfo;

    public TMMusicItemRequest(LiveMusicInfo liveMusicInfo2) {
        super(BASE_URL);
        this.liveMusicInfo = liveMusicInfo2;
        String encryString = AESUtils.getEncryString(APP_ID, RequestHelper.md5((long) new Random().nextInt(100), System.currentTimeMillis()).substring(0, 16), RequestHelper.getTMMusicAccessKey());
        addHeaders("oauth_token", APP_ID + encryString);
        addParam("itemid", liveMusicInfo2.mRequestItemID);
        addParam("subitemtype", "MP3-64K-FTD-P");
        addParam("identityid", RequestHelper.getIdentityID());
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.resource.SimpleNetworkJsonRequest
    public LiveMusicInfo parseJson(JSONObject jSONObject) throws BaseRequestException, JSONException {
        JSONObject optJSONObject = jSONObject.optJSONObject("response").optJSONObject("docs");
        this.liveMusicInfo.mPlayUrl = optJSONObject.optString(RequestContracts.Download.JSON_KEY_URL);
        return this.liveMusicInfo;
    }
}
