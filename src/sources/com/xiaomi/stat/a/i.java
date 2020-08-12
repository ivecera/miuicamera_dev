package com.xiaomi.stat.a;

import android.database.DatabaseUtils;
import java.util.concurrent.Callable;

class i implements Callable<Long> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ c f357a;

    i(c cVar) {
        this.f357a = cVar;
    }

    /* renamed from: a */
    public Long call() {
        return Long.valueOf(DatabaseUtils.queryNumEntries(this.f357a.l.getReadableDatabase(), j.f359b));
    }
}
