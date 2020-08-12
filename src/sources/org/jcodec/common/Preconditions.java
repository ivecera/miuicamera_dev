package org.jcodec.common;

public final class Preconditions {
    private Preconditions() {
    }

    public static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static <T> T checkNotNull(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    public static <T> T checkNotNull(T t, String str, char c2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Character.valueOf(c2)));
    }

    public static <T> T checkNotNull(T t, String str, char c2, char c3) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Character.valueOf(c2), Character.valueOf(c3)));
    }

    public static <T> T checkNotNull(T t, String str, char c2, int i) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Character.valueOf(c2), Integer.valueOf(i)));
    }

    public static <T> T checkNotNull(T t, String str, char c2, long j) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Character.valueOf(c2), Long.valueOf(j)));
    }

    public static <T> T checkNotNull(T t, String str, char c2, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Character.valueOf(c2), obj));
    }

    public static <T> T checkNotNull(T t, String str, int i) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Integer.valueOf(i)));
    }

    public static <T> T checkNotNull(T t, String str, int i, char c2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Integer.valueOf(i), Character.valueOf(c2)));
    }

    public static <T> T checkNotNull(T t, String str, int i, int i2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public static <T> T checkNotNull(T t, String str, int i, long j) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Integer.valueOf(i), Long.valueOf(j)));
    }

    public static <T> T checkNotNull(T t, String str, int i, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Integer.valueOf(i), obj));
    }

    public static <T> T checkNotNull(T t, String str, long j) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Long.valueOf(j)));
    }

    public static <T> T checkNotNull(T t, String str, long j, char c2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Long.valueOf(j), Character.valueOf(c2)));
    }

    public static <T> T checkNotNull(T t, String str, long j, int i) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Long.valueOf(j), Integer.valueOf(i)));
    }

    public static <T> T checkNotNull(T t, String str, long j, long j2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Long.valueOf(j), Long.valueOf(j2)));
    }

    public static <T> T checkNotNull(T t, String str, long j, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, Long.valueOf(j), obj));
    }

    public static <T> T checkNotNull(T t, String str, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj));
    }

    public static <T> T checkNotNull(T t, String str, Object obj, char c2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, Character.valueOf(c2)));
    }

    public static <T> T checkNotNull(T t, String str, Object obj, int i) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, Integer.valueOf(i)));
    }

    public static <T> T checkNotNull(T t, String str, Object obj, long j) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, Long.valueOf(j)));
    }

    public static <T> T checkNotNull(T t, String str, Object obj, Object obj2) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, obj2));
    }

    public static <T> T checkNotNull(T t, String str, Object obj, Object obj2, Object obj3) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, obj2, obj3));
    }

    public static <T> T checkNotNull(T t, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, obj, obj2, obj3, obj4));
    }

    public static <T> T checkNotNull(T t, String str, Object... objArr) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(format(str, objArr));
    }

    public static void checkState(boolean z) {
        if (!z) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean z, Object obj) {
        if (!z) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    public static void checkState(boolean z, String str, char c2) {
        if (!z) {
            throw new IllegalStateException(format(str, Character.valueOf(c2)));
        }
    }

    public static void checkState(boolean z, String str, char c2, char c3) {
        if (!z) {
            throw new IllegalStateException(format(str, Character.valueOf(c2), Character.valueOf(c3)));
        }
    }

    public static void checkState(boolean z, String str, char c2, int i) {
        if (!z) {
            throw new IllegalStateException(format(str, Character.valueOf(c2), Integer.valueOf(i)));
        }
    }

    public static void checkState(boolean z, String str, char c2, long j) {
        if (!z) {
            throw new IllegalStateException(format(str, Character.valueOf(c2), Long.valueOf(j)));
        }
    }

    public static void checkState(boolean z, String str, char c2, Object obj) {
        if (!z) {
            throw new IllegalStateException(format(str, Character.valueOf(c2), obj));
        }
    }

    public static void checkState(boolean z, String str, int i) {
        if (!z) {
            throw new IllegalStateException(format(str, Integer.valueOf(i)));
        }
    }

    public static void checkState(boolean z, String str, int i, char c2) {
        if (!z) {
            throw new IllegalStateException(format(str, Integer.valueOf(i), Character.valueOf(c2)));
        }
    }

    public static void checkState(boolean z, String str, int i, int i2) {
        if (!z) {
            throw new IllegalStateException(format(str, Integer.valueOf(i), Integer.valueOf(i2)));
        }
    }

    public static void checkState(boolean z, String str, int i, long j) {
        if (!z) {
            throw new IllegalStateException(format(str, Integer.valueOf(i), Long.valueOf(j)));
        }
    }

    public static void checkState(boolean z, String str, int i, Object obj) {
        if (!z) {
            throw new IllegalStateException(format(str, Integer.valueOf(i), obj));
        }
    }

    public static void checkState(boolean z, String str, long j) {
        if (!z) {
            throw new IllegalStateException(format(str, Long.valueOf(j)));
        }
    }

    public static void checkState(boolean z, String str, long j, char c2) {
        if (!z) {
            throw new IllegalStateException(format(str, Long.valueOf(j), Character.valueOf(c2)));
        }
    }

    public static void checkState(boolean z, String str, long j, int i) {
        if (!z) {
            throw new IllegalStateException(format(str, Long.valueOf(j), Integer.valueOf(i)));
        }
    }

    public static void checkState(boolean z, String str, long j, long j2) {
        if (!z) {
            throw new IllegalStateException(format(str, Long.valueOf(j), Long.valueOf(j2)));
        }
    }

    public static void checkState(boolean z, String str, long j, Object obj) {
        if (!z) {
            throw new IllegalStateException(format(str, Long.valueOf(j), obj));
        }
    }

    public static void checkState(boolean z, String str, Object obj) {
        if (!z) {
            throw new IllegalStateException(format(str, obj));
        }
    }

    public static void checkState(boolean z, String str, Object obj, char c2) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, Character.valueOf(c2)));
        }
    }

    public static void checkState(boolean z, String str, Object obj, int i) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, Integer.valueOf(i)));
        }
    }

    public static void checkState(boolean z, String str, Object obj, long j) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, Long.valueOf(j)));
        }
    }

    public static void checkState(boolean z, String str, Object obj, Object obj2) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, obj2));
        }
    }

    public static void checkState(boolean z, String str, Object obj, Object obj2, Object obj3) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, obj2, obj3));
        }
    }

    public static void checkState(boolean z, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        if (!z) {
            throw new IllegalStateException(format(str, obj, obj2, obj3, obj4));
        }
    }

    public static void checkState(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalStateException(format(str, objArr));
        }
    }

    static String format(String str, Object... objArr) {
        int indexOf;
        if (objArr != null) {
            String valueOf = String.valueOf(str);
            StringBuilder sb = new StringBuilder(valueOf.length() + (objArr.length * 16));
            int i = 0;
            int i2 = 0;
            while (i < objArr.length && (indexOf = valueOf.indexOf("%s", i2)) != -1) {
                sb.append((CharSequence) valueOf, i2, indexOf);
                sb.append(objArr[i]);
                i2 = indexOf + 2;
                i++;
            }
            sb.append((CharSequence) valueOf, i2, valueOf.length());
            if (i < objArr.length) {
                sb.append(" [");
                sb.append(objArr[i]);
                for (int i3 = i + 1; i3 < objArr.length; i3++) {
                    sb.append(", ");
                    sb.append(objArr[i3]);
                }
                sb.append(']');
            }
            return sb.toString();
        }
        throw new NullPointerException();
    }
}
