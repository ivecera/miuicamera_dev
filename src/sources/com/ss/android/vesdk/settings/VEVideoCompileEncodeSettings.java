package com.ss.android.vesdk.settings;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

@Keep
public class VEVideoCompileEncodeSettings implements Parcelable {
    public static final Parcelable.Creator<VEVideoCompileEncodeSettings> CREATOR = new Parcelable.Creator<VEVideoCompileEncodeSettings>() {
        /* class com.ss.android.vesdk.settings.VEVideoCompileEncodeSettings.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public VEVideoCompileEncodeSettings createFromParcel(Parcel parcel) {
            return new VEVideoCompileEncodeSettings(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public VEVideoCompileEncodeSettings[] newArray(int i) {
            return new VEVideoCompileEncodeSettings[i];
        }
    };
    public VEVideoHWEncodeSettings mHWEncodeSetting = new VEVideoHWEncodeSettings();
    public VEVideoSWEncodeSettings mSWEncodeSetting = new VEVideoSWEncodeSettings();
    public boolean useHWEncoder;

    public VEVideoCompileEncodeSettings() {
    }

    protected VEVideoCompileEncodeSettings(Parcel parcel) {
        this.useHWEncoder = parcel.readByte() != 0;
        this.mHWEncodeSetting = (VEVideoHWEncodeSettings) parcel.readParcelable(VEVideoHWEncodeSettings.class.getClassLoader());
        this.mSWEncodeSetting = (VEVideoSWEncodeSettings) parcel.readParcelable(VEVideoSWEncodeSettings.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.useHWEncoder ? (byte) 1 : 0);
        parcel.writeParcelable(this.mHWEncodeSetting, i);
        parcel.writeParcelable(this.mSWEncodeSetting, i);
    }
}
