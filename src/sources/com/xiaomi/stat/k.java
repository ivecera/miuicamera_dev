package com.xiaomi.stat;

import com.xiaomi.stat.a.l;
import com.xiaomi.stat.d.r;

class k implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ e f636a;

    k(e eVar) {
        this.f636a = eVar;
    }

    public void run() {
        if (b.a() && this.f636a.g() && b.A()) {
            long b2 = r.b();
            if (this.f636a.a(b.s(), b2)) {
                b.a(b2);
                this.f636a.a(l.a());
            }
        }
    }
}
