package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.util.i;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: TransformationUtils */
public final class y {
    private static final Set<String> Ar = new HashSet(Arrays.asList("XT1085", "XT1092", "XT1093", "XT1094", "XT1095", "XT1096", "XT1097", "XT1098", "XT1031", "XT1028", "XT937C", "XT1032", "XT1008", "XT1033", "XT1035", "XT1034", "XT939G", "XT1039", "XT1040", "XT1042", "XT1045", "XT1063", "XT1064", "XT1068", "XT1069", "XT1072", "XT1077", "XT1078", "XT1079"));
    private static final Lock Br = (Ar.contains(Build.MODEL) ? new ReentrantLock() : new a());
    private static final String TAG = "TransformationUtils";
    public static final int vr = 6;
    private static final Paint wr = new Paint(6);
    private static final int xr = 7;
    private static final Paint yr = new Paint(7);
    private static final Paint zr = new Paint(7);

    /* compiled from: TransformationUtils */
    private static final class a implements Lock {
        a() {
        }

        public void lock() {
        }

        @Override // java.util.concurrent.locks.Lock
        public void lockInterruptibly() throws InterruptedException {
        }

        @NonNull
        public Condition newCondition() {
            throw new UnsupportedOperationException("Should not be called");
        }

        public boolean tryLock() {
            return true;
        }

        @Override // java.util.concurrent.locks.Lock
        public boolean tryLock(long j, @NonNull TimeUnit timeUnit) throws InterruptedException {
            return true;
        }

        public void unlock() {
        }
    }

    static {
        zr.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    private y() {
    }

    public static int D(int i) {
        switch (i) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 6:
                return 90;
            case 7:
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    public static boolean E(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }

    public static Lock Ij() {
        return Br;
    }

    public static Bitmap a(@NonNull Bitmap bitmap, int i) {
        if (i == 0) {
            return bitmap;
        }
        try {
            Matrix matrix = new Matrix();
            matrix.setRotate((float) i);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e2) {
            if (!Log.isLoggable(TAG, 6)) {
                return bitmap;
            }
            Log.e(TAG, "Exception when trying to orient image", e2);
            return bitmap;
        }
    }

    public static Bitmap a(@NonNull d dVar, @NonNull Bitmap bitmap, int i) {
        if (!E(i)) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        initializeMatrixForRotation(i, matrix);
        RectF rectF = new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight());
        matrix.mapRect(rectF);
        Bitmap d2 = dVar.d(Math.round(rectF.width()), Math.round(rectF.height()), o(bitmap));
        matrix.postTranslate(-rectF.left, -rectF.top);
        a(bitmap, d2, matrix);
        return d2;
    }

    public static Bitmap a(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        float f2;
        float f3;
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        float f4 = 0.0f;
        if (bitmap.getWidth() * i2 > bitmap.getHeight() * i) {
            f3 = ((float) i2) / ((float) bitmap.getHeight());
            f2 = (((float) i) - (((float) bitmap.getWidth()) * f3)) * 0.5f;
        } else {
            f3 = ((float) i) / ((float) bitmap.getWidth());
            f4 = (((float) i2) - (((float) bitmap.getHeight()) * f3)) * 0.5f;
            f2 = 0.0f;
        }
        matrix.setScale(f3, f3);
        matrix.postTranslate((float) ((int) (f2 + 0.5f)), (float) ((int) (f4 + 0.5f)));
        Bitmap d2 = dVar.d(i, i2, o(bitmap));
        a(bitmap, d2);
        a(bitmap, d2, matrix);
        return d2;
    }

    @Deprecated
    public static Bitmap a(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2, int i3) {
        return b(dVar, bitmap, i3);
    }

    public static void a(Bitmap bitmap, Bitmap bitmap2) {
        bitmap2.setHasAlpha(bitmap.hasAlpha());
    }

    private static void a(@NonNull Bitmap bitmap, @NonNull Bitmap bitmap2, Matrix matrix) {
        Br.lock();
        try {
            Canvas canvas = new Canvas(bitmap2);
            canvas.drawBitmap(bitmap, matrix, wr);
            a(canvas);
        } finally {
            Br.unlock();
        }
    }

    private static void a(Canvas canvas) {
        canvas.setBitmap(null);
    }

    private static Bitmap b(@NonNull d dVar, @NonNull Bitmap bitmap) {
        Bitmap.Config n = n(bitmap);
        if (n.equals(bitmap.getConfig())) {
            return bitmap;
        }
        Bitmap d2 = dVar.d(bitmap.getWidth(), bitmap.getHeight(), n);
        new Canvas(d2).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        return d2;
    }

    /* JADX INFO: finally extract failed */
    public static Bitmap b(@NonNull d dVar, @NonNull Bitmap bitmap, int i) {
        i.b(i > 0, "roundingRadius must be greater than 0.");
        Bitmap.Config n = n(bitmap);
        Bitmap b2 = b(dVar, bitmap);
        Bitmap d2 = dVar.d(b2.getWidth(), b2.getHeight(), n);
        d2.setHasAlpha(true);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        BitmapShader bitmapShader = new BitmapShader(b2, tileMode, tileMode);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        RectF rectF = new RectF(0.0f, 0.0f, (float) d2.getWidth(), (float) d2.getHeight());
        Br.lock();
        try {
            Canvas canvas = new Canvas(d2);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            float f2 = (float) i;
            canvas.drawRoundRect(rectF, f2, f2, paint);
            a(canvas);
            Br.unlock();
            if (!b2.equals(bitmap)) {
                dVar.a(b2);
            }
            return d2;
        } catch (Throwable th) {
            Br.unlock();
            throw th;
        }
    }

    public static Bitmap b(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        if (bitmap.getWidth() > i || bitmap.getHeight() > i2) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "requested target size too big for input, fit centering instead");
            }
            return d(dVar, bitmap, i, i2);
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "requested target size larger or equal to input, returning input");
        }
        return bitmap;
    }

    /* JADX INFO: finally extract failed */
    public static Bitmap c(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        int min = Math.min(i, i2);
        float f2 = (float) min;
        float f3 = f2 / 2.0f;
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        float max = Math.max(f2 / width, f2 / height);
        float f4 = width * max;
        float f5 = max * height;
        float f6 = (f2 - f4) / 2.0f;
        float f7 = (f2 - f5) / 2.0f;
        RectF rectF = new RectF(f6, f7, f4 + f6, f5 + f7);
        Bitmap b2 = b(dVar, bitmap);
        Bitmap d2 = dVar.d(min, min, n(bitmap));
        d2.setHasAlpha(true);
        Br.lock();
        try {
            Canvas canvas = new Canvas(d2);
            canvas.drawCircle(f3, f3, f3, yr);
            canvas.drawBitmap(b2, (Rect) null, rectF, zr);
            a(canvas);
            Br.unlock();
            if (!b2.equals(bitmap)) {
                dVar.a(b2);
            }
            return d2;
        } catch (Throwable th) {
            Br.unlock();
            throw th;
        }
    }

    public static Bitmap d(@NonNull d dVar, @NonNull Bitmap bitmap, int i, int i2) {
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "requested target size matches input, returning input");
            }
            return bitmap;
        }
        float min = Math.min(((float) i) / ((float) bitmap.getWidth()), ((float) i2) / ((float) bitmap.getHeight()));
        int round = Math.round(((float) bitmap.getWidth()) * min);
        int round2 = Math.round(((float) bitmap.getHeight()) * min);
        if (bitmap.getWidth() == round && bitmap.getHeight() == round2) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "adjusted target size matches input, returning input");
            }
            return bitmap;
        }
        Bitmap d2 = dVar.d((int) (((float) bitmap.getWidth()) * min), (int) (((float) bitmap.getHeight()) * min), o(bitmap));
        a(bitmap, d2);
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "request: " + i + "x" + i2);
            Log.v(TAG, "toFit:   " + bitmap.getWidth() + "x" + bitmap.getHeight());
            Log.v(TAG, "toReuse: " + d2.getWidth() + "x" + d2.getHeight());
            StringBuilder sb = new StringBuilder();
            sb.append("minPct:   ");
            sb.append(min);
            Log.v(TAG, sb.toString());
        }
        Matrix matrix = new Matrix();
        matrix.setScale(min, min);
        a(bitmap, d2, matrix);
        return d2;
    }

    @VisibleForTesting
    static void initializeMatrixForRotation(int i, Matrix matrix) {
        switch (i) {
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                return;
            case 3:
                matrix.setRotate(180.0f);
                return;
            case 4:
                matrix.setRotate(180.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 5:
                matrix.setRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 6:
                matrix.setRotate(90.0f);
                return;
            case 7:
                matrix.setRotate(-90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 8:
                matrix.setRotate(-90.0f);
                return;
            default:
                return;
        }
    }

    @NonNull
    private static Bitmap.Config n(@NonNull Bitmap bitmap) {
        return (Build.VERSION.SDK_INT < 26 || !Bitmap.Config.RGBA_F16.equals(bitmap.getConfig())) ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGBA_F16;
    }

    @NonNull
    private static Bitmap.Config o(@NonNull Bitmap bitmap) {
        return bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }
}
