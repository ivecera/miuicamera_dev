package com.xiaomi.stat;

import com.xiaomi.stat.a.c;
import com.xiaomi.stat.b.g;
import com.xiaomi.stat.d.e;

class f implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f625a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ boolean f626b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f627c;

    f(e eVar, String str, boolean z) {
        this.f627c = eVar;
        this.f625a = str;
        this.f626b = z;
    }

    public void run() {
        e.a();
        if (this.f627c.f619a) {
            b.h(this.f625a);
        }
        b.e();
        g.a().a(b.g());
        b.a(this.f627c.f621c, this.f626b);
        b.o();
        if (!this.f627c.f619a) {
            b.f(this.f627c.f620b);
        }
        this.f627c.f();
        c.a().b();
        com.xiaomi.stat.b.e.a().execute(new g(this));
    }
}
