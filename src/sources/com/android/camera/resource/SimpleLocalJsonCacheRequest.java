package com.android.camera.resource;

import android.content.Context;
import com.android.camera.resource.BaseResourceCacheable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public abstract class SimpleLocalJsonCacheRequest<T extends BaseResourceCacheable> extends BaseObservableRequest<T> {
    private String convertStreamToString(InputStream inputStream) {
        Scanner useDelimiter = new Scanner(inputStream).useDelimiter("\\A");
        return useDelimiter.hasNext() ? useDelimiter.next() : "";
    }

    /* access modifiers changed from: protected */
    public File getCacheFile(String str, Context context) {
        return new File(context.getCacheDir(), str);
    }

    /* access modifiers changed from: protected */
    public String getCacheJsonString(String str, Context context) {
        FileInputStream fileInputStream;
        File cacheFile = getCacheFile(str, context);
        if (cacheFile.exists()) {
            try {
                fileInputStream = new FileInputStream(cacheFile);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                return null;
            }
        } else {
            fileInputStream = null;
        }
        return convertStreamToString(fileInputStream);
    }

    /* access modifiers changed from: protected */
    public abstract boolean isCacheValid(T t);

    /* access modifiers changed from: protected */
    public abstract void processRestore(T t);

    /* access modifiers changed from: protected */
    public void scheduleRequest(ResponseListener<T> responseListener, Class<T> cls, T t) {
        if (t == null) {
            t = create(cls);
        }
        if (isCacheValid(t)) {
            try {
                processRestore(t);
                responseListener.onResponse(t, false);
            } catch (Exception e2) {
                e2.printStackTrace();
                responseListener.onResponse(null, true);
            }
        } else if (responseListener != null) {
            responseListener.onResponse(null, true);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.resource.BaseObservableRequest
    public /* bridge */ /* synthetic */ void scheduleRequest(ResponseListener responseListener, Class cls, Object obj) {
        scheduleRequest(responseListener, cls, (BaseResourceCacheable) ((BaseResourceCacheable) obj));
    }
}
