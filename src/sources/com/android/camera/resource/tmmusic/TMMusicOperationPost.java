package com.android.camera.resource.tmmusic;

import com.android.camera.resource.AESUtils;
import com.android.camera.resource.BaseRequestException;
import com.android.camera.resource.RequestHelper;
import com.android.camera.resource.SimpleNetworkJsonRequest;
import java.util.Random;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;

public class TMMusicOperationPost extends SimpleNetworkJsonRequest<String> {
    private static final String APP_ID = "RM";
    private static final String BASE_URL = "https://statist.tingmall.com/tango-statist/report/wmReportSongs";

    public TMMusicOperationPost(String str) {
        super(BASE_URL);
        String encryString = AESUtils.getEncryString(APP_ID, RequestHelper.md5((long) new Random().nextInt(100), System.currentTimeMillis()).substring(0, 16), RequestHelper.getTMMusicAccessKey());
        addHeaders("oauth_token", APP_ID + encryString);
        try {
            addParam("reportData", TangoCompressTests.toHexString(DataZipUtil.compress(str.getBytes())));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.resource.SimpleNetworkBaseRequest
    public RequestBody generatePostBody() {
        return new FormBody.Builder().build();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.resource.SimpleNetworkJsonRequest
    public String parseJson(JSONObject jSONObject) throws BaseRequestException, JSONException {
        return "";
    }
}
