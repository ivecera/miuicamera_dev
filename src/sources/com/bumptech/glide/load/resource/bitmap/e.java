package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.a.c;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.i;
import com.bumptech.glide.util.l;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: BitmapEncoder */
public class e implements i<Bitmap> {
    private static final String TAG = "BitmapEncoder";
    public static final f<Integer> sq = f.a("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionQuality", (Object) 90);
    public static final f<Bitmap.CompressFormat> tq = f.A("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionFormat");
    @Nullable
    private final b Ma;

    @Deprecated
    public e() {
        this.Ma = null;
    }

    public e(@NonNull b bVar) {
        this.Ma = bVar;
    }

    private Bitmap.CompressFormat b(Bitmap bitmap, g gVar) {
        Bitmap.CompressFormat compressFormat = (Bitmap.CompressFormat) gVar.a(tq);
        return compressFormat != null ? compressFormat : bitmap.hasAlpha() ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
    }

    @Override // com.bumptech.glide.load.i
    @NonNull
    public EncodeStrategy a(@NonNull g gVar) {
        return EncodeStrategy.TRANSFORMED;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005f, code lost:
        if (r6 == null) goto L_0x0062;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x005a A[Catch:{ all -> 0x0050 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b2 A[SYNTHETIC, Splitter:B:33:0x00b2] */
    public boolean a(@NonNull A<Bitmap> a2, @NonNull File file, @NonNull g gVar) {
        c cVar;
        Bitmap bitmap = a2.get();
        Bitmap.CompressFormat b2 = b(bitmap, gVar);
        Integer.valueOf(bitmap.getWidth());
        Integer.valueOf(bitmap.getHeight());
        long Jk = com.bumptech.glide.util.e.Jk();
        int intValue = ((Integer) gVar.a(sq)).intValue();
        boolean z = false;
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                cVar = this.Ma != null ? new c(fileOutputStream2, this.Ma) : fileOutputStream2;
                bitmap.compress(b2, intValue, cVar);
                cVar.close();
                z = true;
            } catch (IOException e2) {
                e = e2;
                fileOutputStream = fileOutputStream2;
                try {
                    if (Log.isLoggable(TAG, 3)) {
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cVar != null) {
                        try {
                            cVar.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cVar = fileOutputStream2;
                if (cVar != null) {
                }
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to encode Bitmap", e);
            }
        }
        try {
            cVar.close();
        } catch (IOException unused2) {
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "Compressed with type: " + b2 + " of size " + l.j(bitmap) + " in " + com.bumptech.glide.util.e.e(Jk) + ", options format: " + gVar.a(tq) + ", hasAlpha: " + bitmap.hasAlpha());
        }
        return z;
    }
}
