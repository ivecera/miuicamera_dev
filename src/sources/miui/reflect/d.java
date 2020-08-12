package miui.reflect;

import android.util.Log;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ReflectTool */
public class d {
    private static final String TAG = "Camera";
    private static Map<Character, Class> py = new HashMap();

    /* compiled from: ReflectTool */
    private static class a {
        private static final int STATIC = 321;
        private String ny = "The quick fox jumps over the lazy dogs.";
        private int[] oy = new int[5];

        public a() {
            int[] iArr = this.oy;
            iArr[0] = 1;
            iArr[1] = 2;
            iArr[2] = 3;
            iArr[3] = 4;
            iArr[4] = 5;
        }

        public String b(String[] strArr) {
            StringBuilder sb = new StringBuilder();
            for (String str : strArr) {
                sb.append(str);
            }
            return sb.toString();
        }

        public int sum(int[] iArr) {
            int i = 0;
            for (int i2 : iArr) {
                i += i2;
            }
            return i;
        }
    }

    static {
        py.put('V', Void.TYPE);
        py.put('Z', Boolean.TYPE);
        py.put('B', Byte.TYPE);
        py.put('C', Character.TYPE);
        py.put('S', Short.TYPE);
        py.put('I', Integer.TYPE);
        py.put('J', Long.TYPE);
        py.put('F', Float.TYPE);
        py.put('D', Double.TYPE);
    }

    private static void A(Object obj) {
        if (obj != null) {
            PrintStream printStream = System.out;
            printStream.println("Assert failed: expected null but was " + obj);
        }
    }

    public static Class<?>[] J(String str) throws ClassNotFoundException {
        String substring;
        if (str == null || str == "" || (substring = str.substring(str.indexOf(40) + 1, str.indexOf(41))) == null || substring == "") {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = -1;
        boolean z = false;
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (i >= 0 || !py.containsKey(Character.valueOf(charAt))) {
                if (charAt == '[') {
                    z = true;
                } else if (charAt == 'L') {
                    if (i == -1) {
                        i = i2;
                    }
                } else if (charAt == ';') {
                    String replaceAll = substring.substring(i + 1, i2).replaceAll("/", ".");
                    if (z) {
                        arrayList.add(Array.newInstance(Class.forName(replaceAll), 0).getClass());
                    } else {
                        arrayList.add(Class.forName(replaceAll));
                    }
                    i = -1;
                }
            } else if (z) {
                arrayList.add(Array.newInstance(py.get(Character.valueOf(charAt)), 0).getClass());
            } else {
                arrayList.add(py.get(Character.valueOf(charAt)));
            }
            z = false;
        }
        Class<?>[] clsArr = new Class[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            clsArr[i3] = (Class) arrayList.get(i3);
        }
        return clsArr;
    }

    public static int a(Class<?> cls, Object obj, String str, int i) {
        Object a2 = a(cls, obj, str, b.iy);
        return a2 == null ? i : ((Integer) a2).intValue();
    }

    /* JADX WARN: Failed to insert an additional move for type inference into block B:1:0x0005 */
    /* JADX DEBUG: Additional 1 move instruction added to help type inference */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0016 */
    public static Object a(Class<?> cls, Object obj, String str, String str2) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            Object obj2 = declaredField.get(obj);
            cls = obj2;
            return obj2;
        } catch (NoSuchFieldException unknown) {
            try {
                Field field = cls.getField(str);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e2) {
                Log.w(TAG, "ReflectUtil#getFieldValue ", e2);
                return null;
            } catch (IllegalAccessException e3) {
                Log.w(TAG, "ReflectUtil#getFieldValue ", e3);
                return null;
            }
        } catch (IllegalAccessException e4) {
            Log.w(TAG, "ReflectUtil#getFieldValue ", e4);
            return null;
        }
    }

    public static Object a(Class<?> cls, Object obj, String str, String str2, Object... objArr) {
        try {
            Method method = cls.getMethod(str, J(str2));
            method.setAccessible(true);
            return method.invoke(obj, objArr);
        } catch (NoSuchMethodException e2) {
            Log.w(TAG, "ReflectUtil#callMethod ", e2);
            return null;
        } catch (InvocationTargetException e3) {
            Log.w(TAG, "ReflectUtil#callMethod ", e3);
            return null;
        } catch (IllegalAccessException e4) {
            Log.w(TAG, "ReflectUtil#callMethod ", e4);
            return null;
        } catch (ClassNotFoundException e5) {
            Log.w(TAG, "ReflectUtil#callMethod ", e5);
            return null;
        }
    }

    public static boolean a(Class<?> cls, Object obj, String str, boolean z) {
        Object a2 = a(cls, obj, str, b.ey);
        return a2 == null ? z : ((Boolean) a2).booleanValue();
    }

    public static void main(String[] strArr) {
        System.out.println("msg is: \n" + "Hello, world of Reflection");
        String str = (String) a("Hello, world of Reflection".getClass(), "Hello, world of Reflection", "substring", "(II)Ljava/lang/String;", 2, 10);
        System.out.println("substring(2, 10) is\n" + str);
        u(str, "llo, wor");
        int intValue = ((Integer) a(String.class, "Hello, world of Reflection", "indexOf", "(Ljava/lang/String;)I", "llo")).intValue();
        System.out.println("index is " + intValue);
        u(intValue, 2);
        int intValue2 = ((Integer) a(String.class, "Hello, world of Reflection", "length", "()I", new Object[0])).intValue();
        System.out.println("length of it is " + intValue2);
        u(intValue2, 26);
        Object a2 = a(String.class, "Hello, world of Reflection", "count", b.iy);
        PrintStream printStream = System.out;
        StringBuilder sb = new StringBuilder();
        sb.append("field value count of string is ");
        sb.append(a2 == null ? "no such field" : a2);
        printStream.println(sb.toString());
        A(a2);
        a aVar = new a();
        String str2 = (String) a(a.class, aVar, "mTestField", "Ljava/lang/String;");
        System.out.println("test field " + str2);
        u(str2, "The quick fox jumps over the lazy dogs.");
        int a3 = a(a.class, (Object) null, "STATIC", -1023);
        System.out.println(" static field " + a3);
        u(a3, 321);
        int[] iArr = (int[]) a(a.class, aVar, "mTestArray", (String) null);
        System.out.println(iArr.getClass());
        System.out.println(new String[0].getClass());
        int i = 0;
        for (int i2 : iArr) {
            i += i2;
        }
        u(i, 15);
        u(((Integer) a(a.class, aVar, "sum", "([I)I", iArr)).intValue(), 15);
        String str3 = (String) a(a.class, aVar, "concat", "([Ljava/lang/String;)Ljava/lang/String;", new String[]{"Hello", ",", " ", "world", "!"});
        System.out.println(str3);
        u(str3, "Hello, world!");
    }

    private static void u(int i, int i2) {
        if (i != i2) {
            PrintStream printStream = System.out;
            printStream.println("Assert failed: expected<" + i2 + ">, but was <" + i + ">");
        }
    }

    private static void u(String str, String str2) {
        if (!str2.equals(str)) {
            PrintStream printStream = System.out;
            printStream.println("Assert failed: expected<" + str2 + ">, but was <" + str + ">");
        }
    }
}
