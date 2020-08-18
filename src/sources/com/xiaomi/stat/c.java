package com.xiaomi.stat;

import android.os.Handler;
import android.os.HandlerThread;
import com.xiaomi.stat.d.k;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class c {

    /* renamed from: a  reason: collision with root package name */
    private static final String f473a = "DBExecutor";

    /* renamed from: b  reason: collision with root package name */
    private static String f474b = "mistat_db";

    /* renamed from: c  reason: collision with root package name */
    private static final String f475c = "mistat";

    /* renamed from: d  reason: collision with root package name */
    private static final String f476d = "db.lk";

    /* renamed from: e  reason: collision with root package name */
    private static Handler f477e;

    /* renamed from: f  reason: collision with root package name */
    private static FileLock f478f;
    private static FileChannel g;

    private static class a implements Runnable {

        /* renamed from: a  reason: collision with root package name */
        private Runnable f479a;

        public a(Runnable runnable) {
            this.f479a = runnable;
        }

        public void run() {
            if (c.d()) {
                Runnable runnable = this.f479a;
                if (runnable != null) {
                    runnable.run();
                }
                c.e();
            }
        }
    }

    public static void a(Runnable runnable) {
        c();
        f477e.post(new a(runnable));
    }

    private static void c() {
        if (f477e == null) {
            synchronized (c.class) {
                if (f477e == null) {
                    HandlerThread handlerThread = new HandlerThread(f474b);
                    handlerThread.start();
                    f477e = new Handler(handlerThread.getLooper());
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean d() {
        File file = new File(am.a().getFilesDir(), f475c);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            try {
                g = new FileOutputStream(new File(file, f476d)).getChannel();
                f478f = g.lock();
                k.c(f473a, "acquire lock for db");
                return true;
            } catch (Exception e2) {
                k.c(f473a, "acquire lock for db failed with " + e2);
                try {
                    g.close();
                    g = null;
                } catch (Exception e3) {
                    k.c(f473a, "close file stream failed with " + e3);
                }
                return false;
            }
        } catch (Exception e4) {
            k.c(f473a, "acquire lock for db failed with " + e4);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static void e() {
        try {
            if (f478f != null) {
                f478f.release();
                f478f = null;
            }
            k.c(f473a, "release sDBFileLock for db");
        } catch (Exception e2) {
            k.c(f473a, "release sDBFileLock for db failed with " + e2);
        }
        try {
            if (g != null) {
                g.close();
                g = null;
            }
            k.c(f473a, "release sLockFileChannel for db");
        } catch (Exception e3) {
            k.c(f473a, "release sLockFileChannel for db failed with " + e3);
        }
    }
}
