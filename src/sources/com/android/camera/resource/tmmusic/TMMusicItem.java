package com.android.camera.resource.tmmusic;

import android.os.Parcel;
import com.android.camera.resource.BaseResourceItem;
import com.xiaomi.stat.MiStat;
import java.util.Locale;
import org.json.JSONObject;

public class TMMusicItem extends BaseResourceItem {
    public String albumImage320;
    public String albumName;
    public String artistName;
    public long auditionBegin;
    public long auditionEnd;
    public String detailName;
    public String detailTotalDuration;
    public String detailVersion;
    public String fileExtension;
    public String fileSize;
    public String itemID;
    public String url;

    public int describeContents() {
        return 0;
    }

    @Override // com.android.camera.resource.BaseResourceItem
    public void fillDetailData(JSONObject jSONObject) {
    }

    public String getDurationText() {
        int floor = (int) Math.floor((double) (((float) (this.auditionEnd - this.auditionBegin)) / 1000.0f));
        return String.format(Locale.ENGLISH, "00 : %02d", Integer.valueOf(Math.abs(floor)));
    }

    @Override // com.android.camera.resource.BaseResourceItem
    public void onDecompressFinished(String str) {
    }

    public void parseDownloadInfo(JSONObject jSONObject) {
    }

    @Override // com.android.camera.resource.BaseResourceItem
    public void parseSummaryData(JSONObject jSONObject, int i) {
        this.itemID = jSONObject.optString("itemID");
        JSONObject optJSONObject = jSONObject.optJSONObject("datainfo");
        this.detailName = optJSONObject.optString("name");
        this.detailVersion = optJSONObject.optString("version");
        this.detailTotalDuration = optJSONObject.optString("duration");
        this.auditionBegin = optJSONObject.optLong("auditionBegin");
        this.auditionEnd = optJSONObject.optLong("auditionEnd");
        JSONObject optJSONObject2 = jSONObject.optJSONObject("album");
        this.albumName = optJSONObject2.optString("albumName");
        this.albumImage320 = optJSONObject2.optJSONArray("imagePathMap").optJSONObject(1).optString(MiStat.Param.VALUE);
        this.artistName = jSONObject.optJSONArray("artists").optJSONObject(0).optString("artistName");
    }

    @Override // com.android.camera.resource.BaseResourceItem
    public boolean simpleVerification(String str) {
        return false;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }
}
