package com.xiaomi.stat.a;

import com.xiaomi.stat.d.k;

class d implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ l f348a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c f349b;

    d(c cVar, l lVar) {
        this.f349b = cVar;
        this.f348a = lVar;
    }

    public void run() {
        try {
            this.f349b.b(this.f348a);
        } catch (Exception e2) {
            k.e("EventManager", "addEvent exception: " + e2.toString());
        }
    }
}
