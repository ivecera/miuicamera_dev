package com.bumptech.glide.util;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.model.r;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

/* compiled from: Util */
public final class l {
    private static final int lu = 31;
    private static final int mu = 17;
    private static final char[] nu = "0123456789abcdef".toCharArray();
    private static final char[] ou = new char[64];

    private l() {
    }

    public static void Kk() {
        if (!Mk()) {
            throw new IllegalArgumentException("You must call this method on a background thread");
        }
    }

    public static void Lk() {
        if (!Nk()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    public static boolean Mk() {
        return !Nk();
    }

    public static boolean Nk() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static int a(@Nullable Object obj, int i) {
        return n(obj == null ? 0 : obj.hashCode(), i);
    }

    @NonNull
    private static String b(@NonNull byte[] bArr, @NonNull char[] cArr) {
        for (int i = 0; i < bArr.length; i++) {
            byte b2 = bArr[i] & 255;
            int i2 = i * 2;
            char[] cArr2 = nu;
            cArr[i2] = cArr2[b2 >>> 4];
            cArr[i2 + 1] = cArr2[b2 & 15];
        }
        return new String(cArr);
    }

    @NonNull
    public static <T> List<T> b(@NonNull Collection<T> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (T t : collection) {
            if (t != null) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static int c(float f2, int i) {
        return n(Float.floatToIntBits(f2), i);
    }

    @Deprecated
    public static int c(@NonNull Bitmap bitmap) {
        return j(bitmap);
    }

    public static int c(boolean z, int i) {
        return n(z ? 1 : 0, i);
    }

    public static boolean c(@Nullable Object obj, @Nullable Object obj2) {
        return obj == null ? obj2 == null : obj instanceof r ? ((r) obj).d(obj2) : obj.equals(obj2);
    }

    @NonNull
    public static <T> Queue<T> createQueue(int i) {
        return new ArrayDeque(i);
    }

    public static boolean d(@Nullable Object obj, @Nullable Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    private static int e(@Nullable Bitmap.Config config) {
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        int i = k.ro[config.ordinal()];
        if (i == 1) {
            return 1;
        }
        if (i == 2 || i == 3) {
            return 2;
        }
        return i != 4 ? 4 : 8;
    }

    private static boolean fa(int i) {
        return i > 0 || i == Integer.MIN_VALUE;
    }

    public static int g(int i, int i2, @Nullable Bitmap.Config config) {
        return i * i2 * e(config);
    }

    public static int hashCode(float f2) {
        return c(f2, 17);
    }

    public static int hashCode(int i) {
        return n(i, 17);
    }

    public static int hashCode(boolean z) {
        return c(z, 17);
    }

    @TargetApi(19)
    public static int j(@NonNull Bitmap bitmap) {
        if (!bitmap.isRecycled()) {
            if (Build.VERSION.SDK_INT >= 19) {
                try {
                    return bitmap.getAllocationByteCount();
                } catch (NullPointerException unused) {
                }
            }
            return bitmap.getHeight() * bitmap.getRowBytes();
        }
        throw new IllegalStateException("Cannot obtain size for recycled Bitmap: " + bitmap + "[" + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig());
    }

    @NonNull
    public static String j(@NonNull byte[] bArr) {
        String b2;
        synchronized (ou) {
            b2 = b(bArr, ou);
        }
        return b2;
    }

    public static int n(int i, int i2) {
        return (i2 * 31) + i;
    }

    public static boolean o(int i, int i2) {
        return fa(i) && fa(i2);
    }
}
