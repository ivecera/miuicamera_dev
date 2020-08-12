package miui.cloud.backup.data;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: KeyStringSettingItem */
class d implements Parcelable.Creator<KeyStringSettingItem> {
    d() {
    }

    @Override // android.os.Parcelable.Creator
    public KeyStringSettingItem createFromParcel(Parcel parcel) {
        KeyStringSettingItem keyStringSettingItem = new KeyStringSettingItem();
        keyStringSettingItem.c(parcel);
        return keyStringSettingItem;
    }

    @Override // android.os.Parcelable.Creator
    public KeyStringSettingItem[] newArray(int i) {
        return new KeyStringSettingItem[i];
    }
}
