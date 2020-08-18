package com.xiaomi.stat;

import android.database.Cursor;
import com.xiaomi.stat.ad;
import java.util.concurrent.Callable;

class ae implements Callable<Cursor> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ad f403a;

    ae(ad adVar) {
        this.f403a = adVar;
    }

    /* renamed from: a */
    public Cursor call() throws Exception {
        try {
            return this.f403a.g.getWritableDatabase().query(ad.a.f398b, null, null, null, null, null, null);
        } catch (Exception unused) {
            return null;
        }
    }
}
