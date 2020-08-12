package miui.reflect;

import java.lang.reflect.Constructor;

/* compiled from: Constructor */
public class a {
    private Constructor dy;

    private a(Constructor constructor) {
        this.dy = constructor;
    }

    public static a b(Class<?> cls, String str) throws NoSuchMethodException {
        return new a(null);
    }

    public Object newInstance(Object... objArr) throws IllegalArgumentException {
        return null;
    }
}
