package com.xiaomi.stat.a;

import android.text.TextUtils;
import com.xiaomi.stat.a.l;

public class b {

    /* renamed from: a  reason: collision with root package name */
    public static final int f336a = 0;

    /* renamed from: b  reason: collision with root package name */
    public static final int f337b = 1;

    /* renamed from: c  reason: collision with root package name */
    private String f338c;

    /* renamed from: d  reason: collision with root package name */
    private int f339d;

    /* renamed from: e  reason: collision with root package name */
    private boolean f340e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f341f;

    public b(String str, int i, boolean z) {
        this.f338c = str;
        this.f339d = i;
        this.f340e = z;
        this.f341f = TextUtils.isEmpty(str);
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(j.i);
        if (this.f341f) {
            sb.append(" is null");
        } else {
            sb.append(" = \"");
            sb.append(this.f338c);
            sb.append("\"");
        }
        if (this.f339d != 0) {
            sb.append(" and ");
            sb.append("eg");
            sb.append(" = \"");
            sb.append(l.a.h);
            sb.append("\"");
        }
        sb.append(" and ");
        sb.append(j.j);
        sb.append(" = ");
        sb.append(this.f340e ? 1 : 0);
        return sb.toString();
    }

    public boolean a(String str, String str2, boolean z) {
        if (TextUtils.equals(str, this.f338c) && this.f340e == z) {
            if (this.f339d == 0) {
                return true;
            }
            return this.f341f && TextUtils.equals(str2, l.a.h);
        }
    }
}
