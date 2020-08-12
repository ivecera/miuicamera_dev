package com.xiaomi.stat.b;

import android.content.Context;
import com.xiaomi.stat.d.k;
import java.lang.reflect.Method;

public class f {

    /* renamed from: a  reason: collision with root package name */
    private static final String f449a = "IdentifierManager";

    /* renamed from: b  reason: collision with root package name */
    private static Object f450b;

    /* renamed from: c  reason: collision with root package name */
    private static Class<?> f451c;

    /* renamed from: d  reason: collision with root package name */
    private static Method f452d;

    /* renamed from: e  reason: collision with root package name */
    private static Method f453e;

    /* renamed from: f  reason: collision with root package name */
    private static Method f454f;
    private static Method g;

    static {
        try {
            f451c = Class.forName("com.android.id.impl.IdProviderImpl");
            f450b = f451c.newInstance();
            f452d = f451c.getMethod("getUDID", Context.class);
            f453e = f451c.getMethod("getOAID", Context.class);
            f454f = f451c.getMethod("getVAID", Context.class);
            g = f451c.getMethod("getAAID", Context.class);
        } catch (Exception e2) {
            k.d(f449a, "reflect exception!", e2);
        }
    }

    public static String a(Context context) {
        return a(context, f452d);
    }

    private static String a(Context context, Method method) {
        Object obj = f450b;
        if (obj == null || method == null) {
            return "";
        }
        try {
            Object invoke = method.invoke(obj, context);
            return invoke != null ? (String) invoke : "";
        } catch (Exception e2) {
            k.d(f449a, "invoke exception!", e2);
            return "";
        }
    }

    public static boolean a() {
        return (f451c == null || f450b == null) ? false : true;
    }

    public static String b(Context context) {
        return a(context, f453e);
    }

    public static String c(Context context) {
        return a(context, f454f);
    }

    public static String d(Context context) {
        return a(context, g);
    }
}
