package com.xiaomi.stat.a;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.stat.am;
import com.xiaomi.stat.d.k;

class h implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f355a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c f356b;

    h(c cVar, String str) {
        this.f356b = cVar;
        this.f355a = str;
    }

    public void run() {
        String str;
        String[] strArr;
        try {
            SQLiteDatabase writableDatabase = this.f356b.l.getWritableDatabase();
            if (TextUtils.equals(this.f355a, am.b())) {
                str = "sub is null";
                strArr = null;
            } else {
                String[] strArr2 = {this.f355a};
                str = "sub = ?";
                strArr = strArr2;
            }
            writableDatabase.delete(j.f359b, str, strArr);
        } catch (Exception e2) {
            k.b("EventManager", "removeAllEventsForApp exception: " + e2.toString());
        }
    }
}
