package com.xiaomi.stat.a;

import java.util.concurrent.Callable;

class e implements Callable<k> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ b[] f350a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c f351b;

    e(c cVar, b[] bVarArr) {
        this.f351b = cVar;
        this.f350a = bVarArr;
    }

    /* renamed from: a */
    public k call() throws Exception {
        return this.f351b.b(this.f350a);
    }
}
