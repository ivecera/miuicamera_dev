package com.android.camera.fragment.fullscreen;

import android.os.Parcel;
import android.os.Parcelable;

public class ShareInfo implements Parcelable {
    public static final Parcelable.Creator<ShareInfo> CREATOR = new Parcelable.Creator<ShareInfo>() {
        /* class com.android.camera.fragment.fullscreen.ShareInfo.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public ShareInfo createFromParcel(Parcel parcel) {
            return new ShareInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ShareInfo[] newArray(int i) {
            return new ShareInfo[i];
        }
    };
    public String className;
    public int drawableRes;
    public int index;
    public String packageName;
    public int textRes;

    public ShareInfo(int i, String str, String str2, int i2, int i3) {
        this.index = i;
        this.packageName = str;
        this.className = str2;
        this.drawableRes = i2;
        this.textRes = i3;
    }

    protected ShareInfo(Parcel parcel) {
        this.index = parcel.readInt();
        this.packageName = parcel.readString();
        this.className = parcel.readString();
        this.drawableRes = parcel.readInt();
        this.textRes = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.index);
        parcel.writeString(this.packageName);
        parcel.writeString(this.className);
        parcel.writeInt(this.drawableRes);
        parcel.writeInt(this.textRes);
    }
}
