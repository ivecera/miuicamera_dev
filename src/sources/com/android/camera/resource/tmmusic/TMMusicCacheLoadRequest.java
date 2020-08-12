package com.android.camera.resource.tmmusic;

import android.content.Context;
import com.android.camera.CameraAppImpl;
import com.android.camera.resource.SimpleLocalJsonCacheRequest;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class TMMusicCacheLoadRequest extends SimpleLocalJsonCacheRequest<TMMusicList> {
    /* access modifiers changed from: protected */
    public boolean isCacheValid(TMMusicList tMMusicList) {
        Context androidContext = CameraAppImpl.getAndroidContext();
        File cacheFile = getCacheFile(TMMusicList.CACHE_INITIAL, androidContext);
        File cacheFile2 = getCacheFile(TMMusicList.CACHE_LIST, androidContext);
        if (!cacheFile.exists() || !cacheFile2.exists()) {
            return false;
        }
        return System.currentTimeMillis() - cacheFile.lastModified() <= tMMusicList.getCacheExpireTime();
    }

    /* access modifiers changed from: protected */
    public void processRestore(TMMusicList tMMusicList) {
        Context androidContext = CameraAppImpl.getAndroidContext();
        try {
            tMMusicList.parseInitialData(new JSONObject(getCacheJsonString(TMMusicList.CACHE_INITIAL, androidContext)));
            tMMusicList.createResourcesList(new JSONObject(getCacheJsonString(TMMusicList.CACHE_LIST, androidContext)));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}
