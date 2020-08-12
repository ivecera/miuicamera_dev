package com.android.camera.resource;

import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.json.JSONObject;

public abstract class BaseResourceItem implements Parcelable {
    public static final int STATE_DOWNLOADING = 2;
    public static final int STATE_NATIVE_EXISTS = 99;
    public static final int STATE_NEED_DOWNLOAD = 1;
    public String archivesPath;
    public String id;
    public String uri;
    public int versionCode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResourceState {
    }

    public abstract void fillDetailData(JSONObject jSONObject);

    public abstract void onDecompressFinished(String str);

    public abstract void parseSummaryData(JSONObject jSONObject, int i);

    public abstract boolean simpleVerification(String str);
}
