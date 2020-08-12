package com.xiaomi.stat;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.stat.ad;
import com.xiaomi.stat.d.k;

class ag implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f405a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f406b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ad f407c;

    ag(ad adVar, String str, String str2) {
        this.f407c = adVar;
        this.f405a = str;
        this.f406b = str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    public void run() {
        Cursor cursor = null;
        try {
            SQLiteDatabase writableDatabase = this.f407c.g.getWritableDatabase();
            if (TextUtils.isEmpty(this.f405a)) {
                writableDatabase.delete(ad.a.f398b, "pref_key=?", new String[]{this.f406b});
                return;
            }
            Cursor query = writableDatabase.query(ad.a.f398b, null, "pref_key=?", new String[]{this.f406b}, null, null, null);
            try {
                boolean z = query.getCount() <= 0;
                ContentValues contentValues = new ContentValues();
                contentValues.put(ad.a.f399c, this.f406b);
                contentValues.put(ad.a.f400d, this.f405a);
                if (z) {
                    writableDatabase.insert(ad.a.f398b, null, contentValues);
                } else {
                    writableDatabase.update(ad.a.f398b, contentValues, "pref_key=?", new String[]{this.f406b});
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e2) {
                e = e2;
                cursor = query;
                try {
                    k.c("MiStatPref", "update pref db failed with " + e);
                    if (cursor == null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = query;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            k.c("MiStatPref", "update pref db failed with " + e);
            if (cursor == null) {
                cursor.close();
            }
        }
    }
}
