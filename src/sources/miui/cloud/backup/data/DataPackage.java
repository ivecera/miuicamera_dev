package miui.cloud.backup.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.arcsoft.camera.wideselfie.WideSelfieEngine;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class DataPackage implements Parcelable {
    public static final Parcelable.Creator<DataPackage> CREATOR = new a();
    public static final String KEY_VERSION = "version";
    public static final String Yx = "data_package";
    private final Map<String, SettingItem<?>> Wx = new HashMap();
    private final Map<String, ParcelFileDescriptor> Xx = new HashMap();

    public static DataPackage c(Bundle bundle) {
        Bundle bundle2 = (Bundle) bundle.clone();
        bundle2.setClassLoader(SettingItem.class.getClassLoader());
        return e(bundle2.getBundle(Yx));
    }

    public static int d(Bundle bundle) {
        return bundle.getInt("version");
    }

    /* access modifiers changed from: private */
    public static DataPackage e(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(SettingItem.class.getClassLoader());
        DataPackage dataPackage = new DataPackage();
        for (String str : bundle.keySet()) {
            Parcelable parcelable = bundle.getParcelable(str);
            if (parcelable instanceof SettingItem) {
                dataPackage.Wx.put(str, (SettingItem) parcelable);
            }
            if (parcelable instanceof ParcelFileDescriptor) {
                dataPackage.Xx.put(str, (ParcelFileDescriptor) parcelable);
            }
        }
        return dataPackage;
    }

    private Bundle po() {
        Bundle bundle = new Bundle();
        for (Map.Entry<String, SettingItem<?>> entry : this.Wx.entrySet()) {
            bundle.putParcelable(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, ParcelFileDescriptor> entry2 : this.Xx.entrySet()) {
            bundle.putParcelable(entry2.getKey(), entry2.getValue());
        }
        return bundle;
    }

    public ParcelFileDescriptor G(String str) {
        return this.Xx.get(str);
    }

    public void a(String str, File file) throws FileNotFoundException {
        this.Xx.put(str, ParcelFileDescriptor.open(file, WideSelfieEngine.MPAF_RGB_BASE));
    }

    public void a(String str, SettingItem<?> settingItem) {
        this.Wx.put(str, settingItem);
    }

    public void b(Bundle bundle) {
        bundle.putBundle(Yx, po());
    }

    public void b(String str, JSONObject jSONObject) {
        KeyJsonSettingItem keyJsonSettingItem = new KeyJsonSettingItem();
        ((SettingItem) keyJsonSettingItem).key = str;
        keyJsonSettingItem.setValue(jSONObject);
        this.Wx.put(str, keyJsonSettingItem);
    }

    public int describeContents() {
        return 0;
    }

    public SettingItem<?> get(String str) {
        return this.Wx.get(str);
    }

    public void o(String str, String str2) {
        KeyStringSettingItem keyStringSettingItem = new KeyStringSettingItem();
        ((SettingItem) keyStringSettingItem).key = str;
        keyStringSettingItem.setValue(str2);
        this.Wx.put(str, keyStringSettingItem);
    }

    public Map<String, SettingItem<?>> wm() {
        return this.Wx;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(po());
    }

    public Map<String, ParcelFileDescriptor> xm() {
        return this.Xx;
    }
}
