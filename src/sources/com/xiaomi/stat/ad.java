package com.xiaomi.stat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.FileObserver;
import android.text.TextUtils;
import com.xiaomi.stat.d.k;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;

public class ad {

    /* renamed from: a  reason: collision with root package name */
    private static final String f391a = "MiStatPref";

    /* renamed from: b  reason: collision with root package name */
    private static final String f392b = "true";

    /* renamed from: c  reason: collision with root package name */
    private static final String f393c = "false";

    /* renamed from: e  reason: collision with root package name */
    private static ad f394e;

    /* renamed from: d  reason: collision with root package name */
    private FileObserver f395d;

    /* renamed from: f  reason: collision with root package name */
    private Map<String, String> f396f = new HashMap();
    /* access modifiers changed from: private */
    public SQLiteOpenHelper g;

    private static class a extends SQLiteOpenHelper {

        /* renamed from: a  reason: collision with root package name */
        public static final String f397a = "mistat_pf";

        /* renamed from: b  reason: collision with root package name */
        public static final String f398b = "pref";

        /* renamed from: c  reason: collision with root package name */
        public static final String f399c = "pref_key";

        /* renamed from: d  reason: collision with root package name */
        public static final String f400d = "pref_value";

        /* renamed from: e  reason: collision with root package name */
        private static final int f401e = 1;

        /* renamed from: f  reason: collision with root package name */
        private static final String f402f = "_id";
        private static final String g = "CREATE TABLE pref (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,pref_key TEXT,pref_value TEXT)";

        public a(Context context) {
            super(context, f397a, (SQLiteDatabase.CursorFactory) null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(g);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    private ad() {
        Context a2 = am.a();
        this.g = new a(a2);
        b();
        c(a2.getDatabasePath(a.f397a).getAbsolutePath());
    }

    public static ad a() {
        if (f394e == null) {
            synchronized (ad.class) {
                if (f394e == null) {
                    f394e = new ad();
                }
            }
        }
        return f394e;
    }

    /* access modifiers changed from: private */
    public void b() {
        FutureTask futureTask = new FutureTask(new ae(this));
        try {
            c.a(futureTask);
            Cursor cursor = null;
            try {
                cursor = (Cursor) futureTask.get();
            } catch (InterruptedException | ExecutionException unused) {
            }
            if (cursor != null) {
                this.f396f.clear();
                try {
                    k.c(f391a, "load pref from db");
                    int columnIndex = cursor.getColumnIndex(a.f399c);
                    int columnIndex2 = cursor.getColumnIndex(a.f400d);
                    while (cursor.moveToNext()) {
                        String string = cursor.getString(columnIndex);
                        String string2 = cursor.getString(columnIndex2);
                        this.f396f.put(string, string2);
                        k.c(f391a, "key=" + string + " ,value=" + string2);
                    }
                } catch (Exception unused2) {
                } catch (Throwable th) {
                    cursor.close();
                    throw th;
                }
                cursor.close();
            }
        } catch (RejectedExecutionException e2) {
            k.c(f391a, "load data execute failed with " + e2);
        }
    }

    private void c(String str) {
        this.f395d = new af(this, str);
        this.f395d.startWatching();
    }

    private void c(String str, String str2) {
        synchronized (this) {
            boolean z = true;
            if (!TextUtils.isEmpty(str2)) {
                this.f396f.put(str, str2);
            } else if (this.f396f.containsKey(str)) {
                this.f396f.remove(str);
            } else {
                z = false;
            }
            k.c(f391a, "put value: key=" + str + " ,value=" + str2);
            if (z) {
                FutureTask futureTask = new FutureTask(new ag(this, str2, str), null);
                try {
                    c.a(futureTask);
                    try {
                        futureTask.get();
                    } catch (InterruptedException | ExecutionException unused) {
                    }
                } catch (RejectedExecutionException e2) {
                    k.c(f391a, "execute failed with " + e2);
                }
            }
        }
    }

    public float a(String str, float f2) {
        synchronized (this) {
            if (this.f396f.containsKey(str)) {
                try {
                    float floatValue = Float.valueOf(this.f396f.get(str)).floatValue();
                    return floatValue;
                } catch (NumberFormatException unused) {
                    return f2;
                }
            }
        }
    }

    public int a(String str, int i) {
        synchronized (this) {
            if (this.f396f.containsKey(str)) {
                try {
                    int intValue = Integer.valueOf(this.f396f.get(str)).intValue();
                    return intValue;
                } catch (NumberFormatException unused) {
                    return i;
                }
            }
        }
    }

    public long a(String str, long j) {
        synchronized (this) {
            if (this.f396f.containsKey(str)) {
                try {
                    long longValue = Long.valueOf(this.f396f.get(str)).longValue();
                    return longValue;
                } catch (NumberFormatException unused) {
                    return j;
                }
            }
        }
    }

    public String a(String str, String str2) {
        synchronized (this) {
            if (!this.f396f.containsKey(str)) {
                return str2;
            }
            String str3 = this.f396f.get(str);
            return str3;
        }
    }

    public boolean a(String str) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.f396f.containsKey(str);
        }
        return containsKey;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0028, code lost:
        return r3;
     */
    public boolean a(String str, boolean z) {
        synchronized (this) {
            if (this.f396f.containsKey(str)) {
                String str2 = this.f396f.get(str);
                if ("true".equalsIgnoreCase(str2)) {
                    return true;
                }
                if ("false".equalsIgnoreCase(str2)) {
                    return false;
                }
            }
        }
    }

    public void b(String str) {
        b(str, (String) null);
    }

    public void b(String str, float f2) {
        c(str, Float.toString(f2));
    }

    public void b(String str, int i) {
        c(str, Integer.toString(i));
    }

    public void b(String str, long j) {
        c(str, Long.toString(j));
    }

    public void b(String str, String str2) {
        c(str, str2);
    }

    public void b(String str, boolean z) {
        c(str, Boolean.toString(z));
    }
}
