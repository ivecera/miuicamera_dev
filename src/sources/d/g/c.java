package d.g;

import android.util.Log;
import java.lang.reflect.Field;
import java.util.HashMap;

/* compiled from: ReflectionUtils */
public class c {
    private static final HashMap<String, Field> Iy = new HashMap<>();
    private static final String TAG = "ReflectionUtils";

    public static Field c(Class<?> cls, String str) throws NoSuchFieldException {
        String str2 = cls.getName() + '#' + str;
        synchronized (Iy) {
            if (Iy.containsKey(str2)) {
                Field field = Iy.get(str2);
                if (field != null) {
                    return field;
                }
                throw new NoSuchFieldException(str2);
            }
            try {
                Field g = g(cls, str);
                g.setAccessible(true);
                synchronized (Iy) {
                    Iy.put(str2, g);
                }
                return g;
            } catch (NoSuchFieldException e2) {
                synchronized (Iy) {
                    Iy.put(str2, null);
                    throw e2;
                }
            }
        }
    }

    public static Field f(Class<?> cls, String str) {
        try {
            return c(cls, str);
        } catch (NoSuchFieldException e2) {
            Log.w(TAG, "", e2);
            return null;
        }
    }

    private static Field g(Class<?> cls, String str) throws NoSuchFieldException {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e2) {
            while (true) {
                cls = cls.getSuperclass();
                if (cls == null || cls.equals(Object.class)) {
                    throw e2;
                }
                try {
                    return cls.getDeclaredField(str);
                } catch (NoSuchFieldException unused) {
                }
            }
            throw e2;
        }
    }
}
