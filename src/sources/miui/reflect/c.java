package miui.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: Method */
public class c {
    private static final String TAG = "Method";
    private final Method mMethod;

    private c(Method method) {
        this.mMethod = method;
    }

    public static c a(Class<?> cls, String str, Class<?> cls2, Class<?>... clsArr) {
        return new c(null);
    }

    public static c a(Class<?> cls, String str, String str2) throws NoSuchMethodException {
        try {
            return new c(cls.getMethod(str, d.J(str2)));
        } catch (NoSuchMethodException e2) {
            throw new NoSuchMethodException(e2.getMessage());
        } catch (ClassNotFoundException e3) {
            throw new NoSuchMethodException(e3.getMessage());
        }
    }

    public static c c(String str, String str2, String str3) throws NoSuchClassException, NoSuchMethodException {
        try {
            return a(Class.forName(str), str2, str3);
        } catch (ClassNotFoundException e2) {
            throw new NoSuchClassException(e2.getMessage());
        }
    }

    public void a(Class<?> cls, Object obj, Object... objArr) throws IllegalArgumentException {
        Method method = this.mMethod;
        if (method != null) {
            try {
                method.setAccessible(true);
                this.mMethod.invoke(obj, objArr);
            } catch (InvocationTargetException e2) {
                throw new IllegalArgumentException(e2.getMessage());
            } catch (IllegalAccessException e3) {
                throw new IllegalArgumentException(e3.getMessage());
            }
        }
    }

    public boolean b(Class<?> cls, Object obj, Object... objArr) throws IllegalArgumentException {
        Object g = g(cls, obj, objArr);
        if (g != null && (g instanceof Boolean)) {
            return ((Boolean) g).booleanValue();
        }
        return false;
    }

    public double c(Class<?> cls, Object obj, Object... objArr) throws IllegalArgumentException {
        Object g = g(cls, obj, objArr);
        if (g != null && (g instanceof Double)) {
            return ((Double) g).doubleValue();
        }
        return 0.0d;
    }

    public float d(Class<?> cls, Object obj, Object... objArr) throws IllegalArgumentException {
        Object g = g(cls, obj, objArr);
        if (g != null && (g instanceof Double)) {
            return ((Float) g).floatValue();
        }
        return 0.0f;
    }

    public int e(Class<?> cls, Object obj, Object... objArr) throws IllegalArgumentException {
        Object g = g(cls, obj, objArr);
        if (g != null && (g instanceof Integer)) {
            return ((Integer) g).intValue();
        }
        return -1;
    }

    public long f(Class<?> cls, Object obj, Object... objArr) throws IllegalArgumentException {
        Object g = g(cls, obj, objArr);
        if (g != null && (g instanceof Long)) {
            return ((Long) g).longValue();
        }
        return -1;
    }

    public Object g(Class<?> cls, Object obj, Object... objArr) throws IllegalArgumentException {
        Method method = this.mMethod;
        if (method == null) {
            return null;
        }
        try {
            method.setAccessible(true);
            return this.mMethod.invoke(obj, objArr);
        } catch (InvocationTargetException e2) {
            throw new IllegalArgumentException(e2.getMessage());
        } catch (IllegalAccessException e3) {
            throw new IllegalArgumentException(e3.getMessage());
        }
    }
}
