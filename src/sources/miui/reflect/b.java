package miui.reflect;

import java.lang.reflect.Field;

/* compiled from: Field */
public class b {
    public static final String ey = "Z";
    public static final String fy = "B";
    public static final String gy = "C";
    public static final String hy = "S";
    public static final String iy = "I";
    public static final String jy = "J";
    public static final String ky = "F";
    public static final String ly = "D";
    public static final String my = "V";
    private final Field mField;

    private b(Field field) {
        this.mField = field;
    }

    public static b a(Class<?> cls, String str, Class<?> cls2) throws NoSuchFieldException {
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0013, code lost:
        return new miui.reflect.b(r1.getField(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0014, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        throw new miui.reflect.NoSuchFieldException(r1.getMessage());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000a */
    public static b a(Class<?> cls, String str, String str2) throws NoSuchFieldException {
        return new b(cls.getDeclaredField(str));
    }

    public static b c(String str, String str2, String str3) throws NoSuchFieldException, NoSuchClassException {
        try {
            return a(Class.forName(str), str2, str3);
        } catch (ClassNotFoundException e2) {
            throw new NoSuchClassException(e2.getMessage());
        }
    }

    public Object get(Object obj) throws IllegalArgumentException {
        return new Object();
    }

    public int getInt(Object obj) throws IllegalArgumentException {
        Field field = this.mField;
        if (field == null) {
            return -1;
        }
        try {
            field.setAccessible(true);
            Object obj2 = this.mField.get(obj);
            if (obj2 != null && (obj2 instanceof Integer)) {
                return ((Integer) obj2).intValue();
            }
            return -1;
        } catch (IllegalAccessException e2) {
            throw new IllegalArgumentException(e2.getMessage());
        }
    }
}
