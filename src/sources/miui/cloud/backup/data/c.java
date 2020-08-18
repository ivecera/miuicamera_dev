package miui.cloud.backup.data;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: KeyJsonSettingItem */
class c implements Parcelable.Creator<KeyJsonSettingItem> {
    c() {
    }

    @Override // android.os.Parcelable.Creator
    public KeyJsonSettingItem createFromParcel(Parcel parcel) {
        KeyJsonSettingItem keyJsonSettingItem = new KeyJsonSettingItem();
        keyJsonSettingItem.c(parcel);
        return keyJsonSettingItem;
    }

    @Override // android.os.Parcelable.Creator
    public KeyJsonSettingItem[] newArray(int i) {
        return new KeyJsonSettingItem[i];
    }
}
