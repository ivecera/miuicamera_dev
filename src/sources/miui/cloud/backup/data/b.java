package miui.cloud.backup.data;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: KeyBinarySettingItem */
class b implements Parcelable.Creator<KeyBinarySettingItem> {
    b() {
    }

    @Override // android.os.Parcelable.Creator
    public KeyBinarySettingItem createFromParcel(Parcel parcel) {
        KeyBinarySettingItem keyBinarySettingItem = new KeyBinarySettingItem();
        keyBinarySettingItem.c(parcel);
        return keyBinarySettingItem;
    }

    @Override // android.os.Parcelable.Creator
    public KeyBinarySettingItem[] newArray(int i) {
        return new KeyBinarySettingItem[i];
    }
}
