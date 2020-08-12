package com.arcsoft.avatar.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ArrayUtil {

    /* renamed from: a  reason: collision with root package name */
    private static final String f169a = "ArrayUtil";

    /* renamed from: b  reason: collision with root package name */
    private static final double f170b = 1.0E-9d;

    private ArrayUtil() {
    }

    public static <T> String array2String(T[] tArr) {
        return Arrays.toString(tArr);
    }

    public static <T> int getIndex(T[] tArr, T t) {
        if (tArr == null) {
            return -1;
        }
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            if (isEqual(t, tArr[i])) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int[] getIndices(T[] tArr, T t) {
        if (!hasElementInArray(tArr, t)) {
            return new int[0];
        }
        int length = tArr.length;
        int[] iArr = new int[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (isEqual(tArr[i2], t)) {
                iArr[i] = i2;
                i++;
            }
        }
        if (i == 0) {
            return new int[0];
        }
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    public static boolean hasElementInArray(int[] iArr, int i) {
        if (iArr == null || iArr.length <= 0) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean hasElementInArray(T[] tArr, T t) {
        if (tArr == null || tArr.length <= 0) {
            return false;
        }
        for (T t2 : tArr) {
            if (isEqual(t2, t)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean isEqual(T t, T t2) {
        if (t == null && t2 == null) {
            return true;
        }
        if (t == null || t2 == null) {
            return false;
        }
        if (t instanceof Byte) {
            return t.byteValue() == t2.byteValue();
        }
        if (t instanceof Short) {
            return t.shortValue() == t2.shortValue();
        }
        if (t instanceof Integer) {
            return t.intValue() == t2.intValue();
        }
        if (t instanceof Long) {
            return t.longValue() == t2.longValue();
        }
        if (t instanceof Float) {
            double abs = (double) Math.abs(t.floatValue() - t2.floatValue());
            return abs > -1.0E-9d && abs < f170b;
        } else if (!(t instanceof Double)) {
            return t instanceof String ? t.equals(t2) : t.equals(t2);
        } else {
            double abs2 = Math.abs(t.doubleValue() - t2.doubleValue());
            return abs2 > -1.0E-9d && abs2 < f170b;
        }
    }

    public static <T> T[] mergeAll(T[] tArr, T[]... tArr2) {
        int length = tArr.length;
        for (T[] tArr3 : tArr2) {
            length += tArr3.length;
        }
        T[] copyOf = Arrays.copyOf(tArr, length);
        int length2 = tArr.length;
        for (T[] tArr4 : tArr2) {
            System.arraycopy(tArr4, 0, copyOf, length2, tArr4.length);
            length2 += tArr4.length;
        }
        return copyOf;
    }

    public static <T> T[] removeArrayElementsByValue(T[] tArr, T t, Class<T> cls) {
        if (!hasElementInArray(tArr, t)) {
            return tArr;
        }
        int length = tArr.length;
        if (length == 1) {
            return tArr[0] == t ? (Object[]) Array.newInstance((Class<?>) cls, 0) : tArr;
        }
        int[] indices = getIndices(tArr, t);
        if (indices == null || indices.length <= 0) {
            return tArr;
        }
        T[] tArr2 = (Object[]) Array.newInstance((Class<?>) cls, length - indices.length);
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (!hasElementInArray(indices, i2)) {
                tArr2[i] = tArr[i2];
                i++;
            }
        }
        return tArr2;
    }

    public static <T> T[] removeRedundantElement(T[] tArr, Class<T> cls) {
        HashSet hashSet = new HashSet();
        for (T t : tArr) {
            hashSet.add(t);
        }
        return hashSet.toArray((Object[]) Array.newInstance((Class<?>) cls, hashSet.size()));
    }

    public static <T> void reverse(T[] tArr) {
        if (tArr != null && tArr.length != 0 && tArr.length != 1) {
            int length = tArr.length;
            int i = length / 2;
            for (int i2 = 0; i2 < i; i2++) {
                T t = tArr[i2];
                int i3 = (length - i2) - 1;
                tArr[i2] = tArr[i3];
                tArr[i3] = t;
            }
        }
    }

    public static int[] selectSort(int[] iArr) {
        if (iArr != null && iArr.length > 1) {
            int length = iArr.length;
            int i = 0;
            while (i < length - 1) {
                int i2 = i + 1;
                int i3 = i;
                for (int i4 = i2; i4 < length; i4++) {
                    if (iArr[i3] > iArr[i4]) {
                        i3 = i4;
                    }
                }
                if (i3 != i) {
                    int i5 = iArr[i];
                    iArr[i] = iArr[i3];
                    iArr[i3] = i5;
                }
                i = i2;
            }
        }
        return iArr;
    }

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        Collections.sort(list);
    }

    public static <T extends Comparable<? super T>> void sort(T[] tArr) {
        Arrays.sort(tArr);
    }
}
