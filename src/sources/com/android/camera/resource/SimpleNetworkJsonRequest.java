package com.android.camera.resource;

import android.content.Context;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class SimpleNetworkJsonRequest<T> extends SimpleNetworkBaseRequest<T> {
    public SimpleNetworkJsonRequest(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public abstract T parseJson(JSONObject jSONObject) throws BaseRequestException, JSONException;

    /* access modifiers changed from: protected */
    @Override // com.android.camera.resource.SimpleNetworkBaseRequest
    public T process(String str) throws BaseRequestException {
        try {
            return parseJson(new JSONObject(str));
        } catch (Exception e2) {
            e2.printStackTrace();
            throw new BaseRequestException(2, e2.getMessage(), e2);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0049 A[SYNTHETIC, Splitter:B:29:0x0049] */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    public final void writeToCache(String str, Context context, String str2) {
        File file = new File(context.getCacheDir(), str);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(str2.getBytes());
                try {
                    fileOutputStream2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (FileNotFoundException e3) {
                e = e3;
                fileOutputStream = fileOutputStream2;
                e.printStackTrace();
                if (fileOutputStream == null) {
                    fileOutputStream.close();
                }
            } catch (IOException e4) {
                e = e4;
                fileOutputStream = fileOutputStream2;
                try {
                    e.printStackTrace();
                    if (fileOutputStream == null) {
                        fileOutputStream.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    if (fileOutputStream != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e6) {
            e = e6;
            e.printStackTrace();
            if (fileOutputStream == null) {
            }
        } catch (IOException e7) {
            e = e7;
            e.printStackTrace();
            if (fileOutputStream == null) {
            }
        }
    }
}
