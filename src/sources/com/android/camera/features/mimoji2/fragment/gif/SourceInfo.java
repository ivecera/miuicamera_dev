package com.android.camera.features.mimoji2.fragment.gif;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class SourceInfo implements Parcelable {
    public static final Parcelable.Creator<SourceInfo> CREATOR = new Parcelable.Creator<SourceInfo>() {
        /* class com.android.camera.features.mimoji2.fragment.gif.SourceInfo.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public SourceInfo createFromParcel(Parcel parcel) {
            return new SourceInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SourceInfo[] newArray(int i) {
            return new SourceInfo[i];
        }
    };
    public String filePath;
    public Map<Integer, Long> filterMap = new HashMap();
    public long sourceId;

    protected SourceInfo(Parcel parcel) {
        this.filePath = parcel.readString();
        this.sourceId = parcel.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.filePath);
        parcel.writeLong(this.sourceId);
    }
}
