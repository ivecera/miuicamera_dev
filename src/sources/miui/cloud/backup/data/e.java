package miui.cloud.backup.data;

import android.content.SharedPreferences;
import android.util.Log;
import java.util.Map;

/* compiled from: PrefsBackupHelper */
public class e {
    private static final String TAG = "PrefsBackupHelper";

    /* compiled from: PrefsBackupHelper */
    public static class a {
        private String Zx;
        private String _x;
        private Class<?> by;
        private Object mDefaultValue;

        private a(String str, String str2, Class<?> cls, Object obj) {
            this.Zx = str;
            this._x = str2;
            this.by = cls;
            this.mDefaultValue = obj;
        }

        public static a a(String str, String str2, int i) {
            return new a(str, str2, Integer.class, Integer.valueOf(i));
        }

        public static a a(String str, String str2, long j) {
            return new a(str, str2, Long.class, Long.valueOf(j));
        }

        public static a b(String str, String str2, String str3) {
            return new a(str, str2, String.class, str3);
        }

        public static a b(String str, String str2, boolean z) {
            return new a(str, str2, Boolean.class, Boolean.valueOf(z));
        }

        public static a p(String str, String str2) {
            return new a(str, str2, Boolean.class, null);
        }

        public static a q(String str, String str2) {
            return new a(str, str2, Integer.class, null);
        }

        public static a r(String str, String str2) {
            return new a(str, str2, Long.class, null);
        }

        public static a s(String str, String str2) {
            return new a(str, str2, String.class, null);
        }

        public Class<?> Am() {
            return this.by;
        }

        public Object getDefaultValue() {
            return this.mDefaultValue;
        }

        public String ym() {
            return this.Zx;
        }

        public String zm() {
            return this._x;
        }
    }

    private e() {
    }

    public static void a(SharedPreferences sharedPreferences, DataPackage dataPackage, a[] aVarArr) {
        Map<String, ?> all = sharedPreferences.getAll();
        for (a aVar : aVarArr) {
            Object obj = all.get(aVar.zm());
            if (obj != null) {
                if (obj.getClass() == aVar.Am()) {
                    dataPackage.o(aVar.ym(), obj.toString());
                } else {
                    throw new IllegalStateException("Preference type of " + aVar.zm() + " mismatched. actual type = " + obj.getClass().getSimpleName() + ", expected type = " + aVar.Am().getSimpleName());
                }
            } else if (aVar.getDefaultValue() != null) {
                dataPackage.o(aVar.ym(), aVar.getDefaultValue().toString());
            }
        }
    }

    public static void b(SharedPreferences sharedPreferences, DataPackage dataPackage, a[] aVarArr) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (a aVar : aVarArr) {
            try {
                KeyStringSettingItem keyStringSettingItem = (KeyStringSettingItem) dataPackage.get(aVar.ym());
                if (keyStringSettingItem != null) {
                    String str = (String) keyStringSettingItem.getValue();
                    if (aVar.Am() == Integer.class) {
                        edit.putInt(aVar.zm(), Integer.parseInt(str));
                    } else if (aVar.Am() == Long.class) {
                        edit.putLong(aVar.zm(), Long.parseLong(str));
                    } else if (aVar.Am() == Boolean.class) {
                        edit.putBoolean(aVar.zm(), Boolean.parseBoolean(str));
                    } else if (aVar.Am() == String.class) {
                        edit.putString(aVar.zm(), str);
                    }
                } else {
                    edit.remove(aVar.zm());
                }
            } catch (ClassCastException unused) {
                Log.e(TAG, "entry " + aVar.ym() + " is not KeyStringSettingItem");
            }
        }
        edit.commit();
    }
}
