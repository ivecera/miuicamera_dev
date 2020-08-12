package com.xiaomi.stat;

class p implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ boolean f645a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ boolean f646b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f647c;

    p(e eVar, boolean z, boolean z2) {
        this.f647c = eVar;
        this.f645a = z;
        this.f646b = z2;
    }

    public void run() {
        b.g(this.f645a);
        b.h(this.f646b);
    }
}
