package com.bumptech.glide.load.engine.a;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.VisibleForTesting;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.util.i;

/* compiled from: MemorySizeCalculator */
public final class q {
    @VisibleForTesting
    static final int BYTES_PER_ARGB_8888_PIXEL = 4;
    private static final String TAG = "MemorySizeCalculator";
    private static final int _o = 2;
    private final int Xo;
    private final int Yo;
    private final int Zo;
    private final Context context;

    /* compiled from: MemorySizeCalculator */
    public static final class a {
        @VisibleForTesting
        static final int MEMORY_CACHE_TARGET_SCREENS = 2;
        static final int So = (Build.VERSION.SDK_INT < 26 ? 4 : 1);
        static final float To = 0.4f;
        static final float Uo = 0.33f;
        static final int Vo = 4194304;
        ActivityManager Mo;
        float No = 2.0f;
        float Oo = ((float) So);
        float Po = 0.4f;
        float Qo = Uo;
        int Ro = 4194304;
        final Context context;
        c screenDimensions;

        public a(Context context2) {
            this.context = context2;
            this.Mo = (ActivityManager) context2.getSystemService("activity");
            this.screenDimensions = new b(context2.getResources().getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= 26 && q.a(this.Mo)) {
                this.Oo = 0.0f;
            }
        }

        public a A(int i) {
            this.Ro = i;
            return this;
        }

        public q build() {
            return new q(this);
        }

        public a c(float f2) {
            i.b(f2 >= 0.0f, "Bitmap pool screens must be greater than or equal to 0");
            this.Oo = f2;
            return this;
        }

        public a d(float f2) {
            i.b(f2 >= 0.0f && f2 <= 1.0f, "Low memory max size multiplier must be between 0 and 1");
            this.Qo = f2;
            return this;
        }

        public a e(float f2) {
            i.b(f2 >= 0.0f && f2 <= 1.0f, "Size multiplier must be between 0 and 1");
            this.Po = f2;
            return this;
        }

        public a f(float f2) {
            i.b(f2 >= 0.0f, "Memory cache screens must be greater than or equal to 0");
            this.No = f2;
            return this;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public a setActivityManager(ActivityManager activityManager) {
            this.Mo = activityManager;
            return this;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public a setScreenDimensions(c cVar) {
            this.screenDimensions = cVar;
            return this;
        }
    }

    /* compiled from: MemorySizeCalculator */
    private static final class b implements c {
        private final DisplayMetrics Wo;

        b(DisplayMetrics displayMetrics) {
            this.Wo = displayMetrics;
        }

        @Override // com.bumptech.glide.load.engine.a.q.c
        public int J() {
            return this.Wo.heightPixels;
        }

        @Override // com.bumptech.glide.load.engine.a.q.c
        public int U() {
            return this.Wo.widthPixels;
        }
    }

    /* compiled from: MemorySizeCalculator */
    interface c {
        int J();

        int U();
    }

    q(a aVar) {
        this.context = aVar.context;
        this.Zo = a(aVar.Mo) ? aVar.Ro / 2 : aVar.Ro;
        int a2 = a(aVar.Mo, aVar.Po, aVar.Qo);
        float U = (float) (aVar.screenDimensions.U() * aVar.screenDimensions.J() * 4);
        int round = Math.round(aVar.Oo * U);
        int round2 = Math.round(U * aVar.No);
        int i = a2 - this.Zo;
        int i2 = round2 + round;
        if (i2 <= i) {
            this.Yo = round2;
            this.Xo = round;
        } else {
            float f2 = (float) i;
            float f3 = aVar.Oo;
            float f4 = aVar.No;
            float f5 = f2 / (f3 + f4);
            this.Yo = Math.round(f4 * f5);
            this.Xo = Math.round(f5 * aVar.Oo);
        }
        if (Log.isLoggable(TAG, 3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Calculation complete, Calculated memory cache size: ");
            sb.append(ba(this.Yo));
            sb.append(", pool size: ");
            sb.append(ba(this.Xo));
            sb.append(", byte array size: ");
            sb.append(ba(this.Zo));
            sb.append(", memory class limited? ");
            sb.append(i2 > a2);
            sb.append(", max size: ");
            sb.append(ba(a2));
            sb.append(", memoryClass: ");
            sb.append(aVar.Mo.getMemoryClass());
            sb.append(", isLowMemoryDevice: ");
            sb.append(a(aVar.Mo));
            Log.d(TAG, sb.toString());
        }
    }

    private static int a(ActivityManager activityManager, float f2, float f3) {
        boolean a2 = a(activityManager);
        float memoryClass = (float) (activityManager.getMemoryClass() * 1024 * 1024);
        if (a2) {
            f2 = f3;
        }
        return Math.round(memoryClass * f2);
    }

    @TargetApi(19)
    static boolean a(ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= 19) {
            return activityManager.isLowRamDevice();
        }
        return true;
    }

    private String ba(int i) {
        return Formatter.formatFileSize(this.context, (long) i);
    }

    public int Aj() {
        return this.Yo;
    }

    public int yj() {
        return this.Zo;
    }

    public int zj() {
        return this.Xo;
    }
}
