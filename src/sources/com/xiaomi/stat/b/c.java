package com.xiaomi.stat.b;

import com.xiaomi.stat.d.l;

class c implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ b f441a;

    c(b bVar) {
        this.f441a = bVar;
    }

    public void run() {
        if (l.a()) {
            this.f441a.f440a.a(false, false);
            i.a().a(false);
            d.a().b();
        }
    }
}
