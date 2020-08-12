package d.a.a;

import android.content.Context;
import miui.cloud.backup.data.DataPackage;

/* compiled from: ICloudBackup */
public interface b {
    int getCurrentVersion(Context context);

    void onBackupSettings(Context context, DataPackage dataPackage);

    void onRestoreSettings(Context context, DataPackage dataPackage, int i);
}
