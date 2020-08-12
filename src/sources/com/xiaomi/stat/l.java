package com.xiaomi.stat;

import android.text.TextUtils;
import com.xiaomi.stat.b.g;
import com.xiaomi.stat.d.m;

class l implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ boolean f637a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f638b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f639c;

    l(e eVar, boolean z, String str) {
        this.f639c = eVar;
        this.f637a = z;
        this.f638b = str;
    }

    public void run() {
        if (!m.a()) {
            b.d(this.f637a);
            g.a().a(this.f637a);
        }
        if (b.f() && !TextUtils.isEmpty(this.f638b)) {
            b.a(this.f638b);
            g.a().a(this.f638b);
        }
    }
}
