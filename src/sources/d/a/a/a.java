package d.a.a;

import android.app.IntentService;
import android.content.Intent;
import android.os.Process;
import android.util.Log;
import java.util.Map;
import miui.cloud.backup.data.DataPackage;
import miui.cloud.backup.data.SettingItem;

/* compiled from: CloudBackupServiceBase */
public abstract class a extends IntentService {
    private static final String TAG = "SettingsBackup";

    public a() {
        super("SettingsBackup");
    }

    protected static void dumpDataPackage(DataPackage dataPackage) {
        for (Map.Entry<String, SettingItem<?>> entry : dataPackage.wm().entrySet()) {
            Log.d("SettingsBackup", "key: " + entry.getKey() + ", value: " + entry.getValue().getValue());
        }
    }

    private String prependPackageName(String str) {
        return getPackageName() + ": " + str;
    }

    /* access modifiers changed from: protected */
    public abstract b getBackupImpl();

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d("SettingsBackup", prependPackageName("myPid: " + Process.myPid()));
            Log.d("SettingsBackup", prependPackageName("intent: " + intent));
            Log.d("SettingsBackup", prependPackageName("extras: " + intent.getExtras()));
        }
    }
}
