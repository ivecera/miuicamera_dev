package com.xiaomi.stat;

import com.xiaomi.stat.a.c;

class i implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ boolean f631a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f632b;

    i(e eVar, boolean z) {
        this.f632b = eVar;
        this.f631a = z;
    }

    public void run() {
        if (b.d(this.f632b.f621c)) {
            int i = 2;
            if (!this.f631a && b.e(this.f632b.f621c) != 2) {
                c.a().a(this.f632b.f621c);
            }
            String b2 = this.f632b.f621c;
            if (this.f631a) {
                i = 1;
            }
            b.a(b2, i);
        }
    }
}
