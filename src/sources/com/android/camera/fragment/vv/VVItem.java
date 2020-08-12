package com.android.camera.fragment.vv;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.camera.Util;
import com.android.camera.resource.BaseResourceItem;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class VVItem extends BaseResourceItem {
    public static final Parcelable.Creator<VVItem> CREATOR = new Parcelable.Creator<VVItem>() {
        /* class com.android.camera.fragment.vv.VVItem.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public VVItem createFromParcel(Parcel parcel) {
            return new VVItem(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public VVItem[] newArray(int i) {
            return new VVItem[i];
        }
    };
    private static final int DURATION_WATERMARK = 2000;
    public String composeJsonPath;
    public String configJsonPath;
    public String coverPath;
    public List<Long> durationList;
    public String filterPath;
    public transient int index;
    public String musicPath;
    public String name;
    public String previewVideoPath;
    public long totalDuration;

    public VVItem() {
    }

    protected VVItem(Parcel parcel) {
        this.name = parcel.readString();
        this.coverPath = parcel.readString();
        this.previewVideoPath = parcel.readString();
        this.filterPath = parcel.readString();
        this.configJsonPath = parcel.readString();
        this.composeJsonPath = parcel.readString();
        this.musicPath = parcel.readString();
        this.durationList = new ArrayList();
        parcel.readList(this.durationList, Long.class.getClassLoader());
        this.totalDuration = parcel.readLong();
        ((BaseResourceItem) this).id = parcel.readString();
        ((BaseResourceItem) this).versionCode = parcel.readInt();
        ((BaseResourceItem) this).uri = parcel.readString();
        ((BaseResourceItem) this).archivesPath = parcel.readString();
    }

    public VVItem(String str, String str2, String str3) {
        ((BaseResourceItem) this).id = str;
        this.name = str2;
        this.coverPath = str3;
    }

    public int describeContents() {
        return 0;
    }

    @Override // com.android.camera.resource.BaseResourceItem
    public void fillDetailData(JSONObject jSONObject) {
    }

    public long getDuration(int i) {
        return this.durationList.get(i).longValue();
    }

    public int getEssentialFragmentSize() {
        return this.durationList.size();
    }

    public long getTotalDuration() {
        return this.totalDuration;
    }

    @Override // com.android.camera.resource.BaseResourceItem
    public void onDecompressFinished(String str) {
        this.coverPath = str + "pv/cover.jpg";
        this.previewVideoPath = str + "pv/preview.mov";
        this.filterPath = str + "filter.png";
        this.configJsonPath = str + ComposerHelper.CONFIG_FILE_NAME;
        this.composeJsonPath = str + "compose.json";
        this.musicPath = str + "bgm.mp3";
    }

    @Override // com.android.camera.resource.BaseResourceItem
    public void parseSummaryData(JSONObject jSONObject, int i) {
        this.index = i;
        ((BaseResourceItem) this).id = jSONObject.optString("id");
        ((BaseResourceItem) this).uri = jSONObject.optString("uri");
        JSONArray optJSONArray = jSONObject.optJSONArray("fragments");
        this.durationList = new ArrayList();
        for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
            this.durationList.add(Long.valueOf(optJSONArray.optLong(i2)));
            this.totalDuration += optJSONArray.optLong(i2);
        }
        this.totalDuration += 2000;
        JSONArray optJSONArray2 = jSONObject.optJSONArray("overlapping");
        if (optJSONArray2 != null) {
            for (int i3 = 0; i3 < optJSONArray2.length(); i3++) {
                this.totalDuration -= optJSONArray2.optLong(i3);
            }
        }
        String str = Util.sRegion;
        JSONArray optJSONArray3 = jSONObject.optJSONArray("i18n");
        for (int i4 = 0; i4 < optJSONArray3.length(); i4++) {
            JSONObject optJSONObject = optJSONArray3.optJSONObject(i4);
            String optString = optJSONObject.optString("lang");
            if (optString.equalsIgnoreCase("default")) {
                this.name = optJSONObject.optString("name");
            } else if (optString.equalsIgnoreCase(str)) {
                this.name = optJSONObject.optString("name");
                return;
            }
        }
    }

    @Override // com.android.camera.resource.BaseResourceItem
    public boolean simpleVerification(String str) {
        return new File(str, ComposerHelper.CONFIG_FILE_NAME).exists() && new File(str, "compose.json").exists();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.coverPath);
        parcel.writeString(this.previewVideoPath);
        parcel.writeString(this.filterPath);
        parcel.writeString(this.configJsonPath);
        parcel.writeString(this.composeJsonPath);
        parcel.writeString(this.musicPath);
        parcel.writeList(this.durationList);
        parcel.writeLong(this.totalDuration);
        parcel.writeString(((BaseResourceItem) this).id);
        parcel.writeInt(((BaseResourceItem) this).versionCode);
        parcel.writeString(((BaseResourceItem) this).uri);
        parcel.writeString(((BaseResourceItem) this).archivesPath);
    }
}
