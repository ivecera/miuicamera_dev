package d.d;

import android.os.SystemProperties;

/* compiled from: SystemProperties */
public class c {
    public static final int PROP_NAME_MAX = 31;
    public static final int PROP_VALUE_MAX = 91;

    protected c() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static boolean getBoolean(String str, boolean z) {
        if (str.length() <= 31) {
            return SystemProperties.getBoolean(str, z);
        }
        throw new IllegalArgumentException("key.length > 31");
    }
}
