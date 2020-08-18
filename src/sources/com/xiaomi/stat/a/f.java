package com.xiaomi.stat.a;

import java.util.ArrayList;

class f implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ArrayList f352a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c f353b;

    f(c cVar, ArrayList arrayList) {
        this.f353b = cVar;
        this.f352a = arrayList;
    }

    public void run() {
        this.f353b.b(this.f352a);
    }
}
