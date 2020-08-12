package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.util.e;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/* compiled from: Downsampler */
public final class o {
    static final String TAG = "Downsampler";
    public static final f<DecodeFormat> Yq = f.a("com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeFormat", DecodeFormat.DEFAULT);
    @Deprecated
    public static final f<DownsampleStrategy> Zq = DownsampleStrategy.Wq;
    public static final f<Boolean> _q = f.a("com.bumptech.glide.load.resource.bitmap.Downsampler.FixBitmapSize", (Object) false);
    public static final f<Boolean> br = f.A("com.bumtpech.glide.load.resource.bitmap.Downsampler.AllowHardwareDecode");
    private static final String cr = "image/vnd.wap.wbmp";
    private static final String er = "image/x-ico";
    private static final Set<String> fr = Collections.unmodifiableSet(new HashSet(Arrays.asList(cr, er)));
    private static final a gr = new n();
    private static final Set<ImageHeaderParser.ImageType> hr = Collections.unmodifiableSet(EnumSet.of(ImageHeaderParser.ImageType.JPEG, ImageHeaderParser.ImageType.PNG_A, ImageHeaderParser.ImageType.PNG));
    private static final Queue<BitmapFactory.Options> ir = l.createQueue(0);
    private static final int wl = 10485760;
    private final b Ll;
    private final DisplayMetrics Wo;
    private final d Xi;
    private final t Xq = t.getInstance();
    private final List<ImageHeaderParser> cm;

    /* compiled from: Downsampler */
    public interface a {
        void N();

        void a(d dVar, Bitmap bitmap) throws IOException;
    }

    public o(List<ImageHeaderParser> list, DisplayMetrics displayMetrics, d dVar, b bVar) {
        this.cm = list;
        i.checkNotNull(displayMetrics);
        this.Wo = displayMetrics;
        i.checkNotNull(dVar);
        this.Xi = dVar;
        i.checkNotNull(bVar);
        this.Ll = bVar;
    }

    private static synchronized BitmapFactory.Options Jn() {
        BitmapFactory.Options poll;
        synchronized (o.class) {
            synchronized (ir) {
                poll = ir.poll();
            }
            if (poll == null) {
                poll = new BitmapFactory.Options();
                d(poll);
            }
        }
        return poll;
    }

    private Bitmap a(InputStream inputStream, BitmapFactory.Options options, DownsampleStrategy downsampleStrategy, DecodeFormat decodeFormat, boolean z, int i, int i2, boolean z2, a aVar) throws IOException {
        o oVar;
        int i3;
        int i4;
        int i5;
        long Jk = e.Jk();
        int[] b2 = b(inputStream, options, aVar, this.Xi);
        boolean z3 = false;
        int i6 = b2[0];
        int i7 = b2[1];
        String str = options.outMimeType;
        boolean z4 = (i6 == -1 || i7 == -1) ? false : z;
        int a2 = com.bumptech.glide.load.b.a(this.cm, inputStream, this.Ll);
        int D = y.D(a2);
        boolean E = y.E(a2);
        int i8 = i == Integer.MIN_VALUE ? i6 : i;
        int i9 = i2 == Integer.MIN_VALUE ? i7 : i2;
        ImageHeaderParser.ImageType b3 = com.bumptech.glide.load.b.b(this.cm, inputStream, this.Ll);
        a(b3, inputStream, aVar, this.Xi, downsampleStrategy, D, i6, i7, i8, i9, options);
        a(inputStream, decodeFormat, z4, E, options, i8, i9);
        if (Build.VERSION.SDK_INT >= 19) {
            z3 = true;
        }
        if (options.inSampleSize == 1 || z3) {
            oVar = this;
            if (oVar.a(b3)) {
                if (i6 < 0 || i7 < 0 || !z2 || !z3) {
                    float f2 = b(options) ? ((float) options.inTargetDensity) / ((float) options.inDensity) : 1.0f;
                    int i10 = options.inSampleSize;
                    float f3 = (float) i10;
                    i5 = Math.round(((float) ((int) Math.ceil((double) (((float) i6) / f3)))) * f2);
                    i4 = Math.round(((float) ((int) Math.ceil((double) (((float) i7) / f3)))) * f2);
                    if (Log.isLoggable(TAG, 2)) {
                        Log.v(TAG, "Calculated target [" + i5 + "x" + i4 + "] for source [" + i6 + "x" + i7 + "], sampleSize: " + i10 + ", targetDensity: " + options.inTargetDensity + ", density: " + options.inDensity + ", density multiplier: " + f2);
                    }
                } else {
                    i5 = i8;
                    i4 = i9;
                }
                if (i5 > 0 && i4 > 0) {
                    a(options, oVar.Xi, i5, i4);
                }
            }
        } else {
            oVar = this;
        }
        Bitmap a3 = a(inputStream, options, aVar, oVar.Xi);
        aVar.a(oVar.Xi, a3);
        if (Log.isLoggable(TAG, 2)) {
            i3 = a2;
            a(i6, i7, str, options, a3, i, i2, Jk);
        } else {
            i3 = a2;
        }
        Bitmap bitmap = null;
        if (a3 != null) {
            a3.setDensity(oVar.Wo.densityDpi);
            bitmap = y.a(oVar.Xi, a3, i3);
            if (!a3.equals(bitmap)) {
                oVar.Xi.a(a3);
            }
        }
        return bitmap;
    }

    private static Bitmap a(InputStream inputStream, BitmapFactory.Options options, a aVar, d dVar) throws IOException {
        if (options.inJustDecodeBounds) {
            inputStream.mark(wl);
        } else {
            aVar.N();
        }
        int i = options.outWidth;
        int i2 = options.outHeight;
        String str = options.outMimeType;
        y.Ij().lock();
        try {
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
            y.Ij().unlock();
            if (options.inJustDecodeBounds) {
                inputStream.reset();
            }
            return decodeStream;
        } catch (IllegalArgumentException e2) {
            IOException a2 = a(e2, i, i2, str, options);
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to decode with inBitmap, trying again without Bitmap re-use", a2);
            }
            if (options.inBitmap != null) {
                try {
                    inputStream.reset();
                    dVar.a(options.inBitmap);
                    options.inBitmap = null;
                    Bitmap a3 = a(inputStream, options, aVar, dVar);
                    y.Ij().unlock();
                    return a3;
                } catch (IOException unused) {
                    throw a2;
                }
            } else {
                throw a2;
            }
        } catch (Throwable th) {
            y.Ij().unlock();
            throw th;
        }
    }

    private static IOException a(IllegalArgumentException illegalArgumentException, int i, int i2, String str, BitmapFactory.Options options) {
        return new IOException("Exception decoding bitmap, outWidth: " + i + ", outHeight: " + i2 + ", outMimeType: " + str + ", inBitmap: " + a(options), illegalArgumentException);
    }

    private static String a(BitmapFactory.Options options) {
        return k(options.inBitmap);
    }

    private static void a(int i, int i2, String str, BitmapFactory.Options options, Bitmap bitmap, int i3, int i4, long j) {
        Log.v(TAG, "Decoded " + k(bitmap) + " from [" + i + "x" + i2 + "] " + str + " with inBitmap " + a(options) + " for [" + i3 + "x" + i4 + "], sample size: " + options.inSampleSize + ", density: " + options.inDensity + ", target density: " + options.inTargetDensity + ", thread: " + Thread.currentThread().getName() + ", duration: " + e.e(j));
    }

    @TargetApi(26)
    private static void a(BitmapFactory.Options options, d dVar, int i, int i2) {
        Bitmap.Config config;
        if (Build.VERSION.SDK_INT < 26) {
            config = null;
        } else if (options.inPreferredConfig != Bitmap.Config.HARDWARE) {
            config = options.outConfig;
        } else {
            return;
        }
        if (config == null) {
            config = options.inPreferredConfig;
        }
        options.inBitmap = dVar.c(i, i2, config);
    }

    private static void a(ImageHeaderParser.ImageType imageType, InputStream inputStream, a aVar, d dVar, DownsampleStrategy downsampleStrategy, int i, int i2, int i3, int i4, int i5, BitmapFactory.Options options) throws IOException {
        int i6;
        int i7;
        int i8;
        double d2;
        if (i2 > 0 && i3 > 0) {
            float b2 = (i == 90 || i == 270) ? downsampleStrategy.b(i3, i2, i4, i5) : downsampleStrategy.b(i2, i3, i4, i5);
            if (b2 > 0.0f) {
                DownsampleStrategy.SampleSizeRounding a2 = downsampleStrategy.a(i2, i3, i4, i5);
                if (a2 != null) {
                    float f2 = (float) i2;
                    float f3 = (float) i3;
                    int round = i2 / round((double) (b2 * f2));
                    int round2 = i3 / round((double) (b2 * f3));
                    int max = a2 == DownsampleStrategy.SampleSizeRounding.MEMORY ? Math.max(round, round2) : Math.min(round, round2);
                    if (Build.VERSION.SDK_INT > 23 || !fr.contains(options.outMimeType)) {
                        int max2 = Math.max(1, Integer.highestOneBit(max));
                        i6 = (a2 != DownsampleStrategy.SampleSizeRounding.MEMORY || ((float) max2) >= 1.0f / b2) ? max2 : max2 << 1;
                    } else {
                        i6 = 1;
                    }
                    options.inSampleSize = i6;
                    if (imageType == ImageHeaderParser.ImageType.JPEG) {
                        float min = (float) Math.min(i6, 8);
                        i7 = (int) Math.ceil((double) (f2 / min));
                        i8 = (int) Math.ceil((double) (f3 / min));
                        int i9 = i6 / 8;
                        if (i9 > 0) {
                            i7 /= i9;
                            i8 /= i9;
                        }
                    } else {
                        if (imageType == ImageHeaderParser.ImageType.PNG || imageType == ImageHeaderParser.ImageType.PNG_A) {
                            float f4 = (float) i6;
                            i7 = (int) Math.floor((double) (f2 / f4));
                            d2 = Math.floor((double) (f3 / f4));
                        } else if (imageType == ImageHeaderParser.ImageType.WEBP || imageType == ImageHeaderParser.ImageType.WEBP_A) {
                            if (Build.VERSION.SDK_INT >= 24) {
                                float f5 = (float) i6;
                                i7 = Math.round(f2 / f5);
                                i8 = Math.round(f3 / f5);
                            } else {
                                float f6 = (float) i6;
                                i7 = (int) Math.floor((double) (f2 / f6));
                                d2 = Math.floor((double) (f3 / f6));
                            }
                        } else if (i2 % i6 == 0 && i3 % i6 == 0) {
                            i7 = i2 / i6;
                            i8 = i3 / i6;
                        } else {
                            int[] b3 = b(inputStream, options, aVar, dVar);
                            int i10 = b3[0];
                            i8 = b3[1];
                            i7 = i10;
                        }
                        i8 = (int) d2;
                    }
                    double b4 = (double) downsampleStrategy.b(i7, i8, i4, i5);
                    if (Build.VERSION.SDK_INT >= 19) {
                        options.inTargetDensity = b(b4);
                        options.inDensity = c(b4);
                    }
                    if (b(options)) {
                        options.inScaled = true;
                    } else {
                        options.inTargetDensity = 0;
                        options.inDensity = 0;
                    }
                    if (Log.isLoggable(TAG, 2)) {
                        Log.v(TAG, "Calculate scaling, source: [" + i2 + "x" + i3 + "], target: [" + i4 + "x" + i5 + "], power of two scaled: [" + i7 + "x" + i8 + "], exact scale factor: " + b2 + ", power of 2 sample size: " + i6 + ", adjusted scale factor: " + b4 + ", target density: " + options.inTargetDensity + ", density: " + options.inDensity);
                        return;
                    }
                    return;
                }
                throw new IllegalArgumentException("Cannot round with null rounding");
            }
            throw new IllegalArgumentException("Cannot scale with factor: " + b2 + " from: " + downsampleStrategy + ", source: [" + i2 + "x" + i3 + "], target: [" + i4 + "x" + i5 + "]");
        } else if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Unable to determine dimensions for: " + imageType + " with target [" + i4 + "x" + i5 + "]");
        }
    }

    private void a(InputStream inputStream, DecodeFormat decodeFormat, boolean z, boolean z2, BitmapFactory.Options options, int i, int i2) {
        if (!this.Xq.a(i, i2, options, decodeFormat, z, z2)) {
            if (decodeFormat == DecodeFormat.Gx || decodeFormat == DecodeFormat.Hx || Build.VERSION.SDK_INT == 16) {
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                return;
            }
            boolean z3 = false;
            try {
                z3 = com.bumptech.glide.load.b.b(this.cm, inputStream, this.Ll).hasAlpha();
            } catch (IOException e2) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Cannot determine whether the image has alpha or not from header, format " + decodeFormat, e2);
                }
            }
            options.inPreferredConfig = z3 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            if (options.inPreferredConfig == Bitmap.Config.RGB_565) {
                options.inDither = true;
            }
        }
    }

    private boolean a(ImageHeaderParser.ImageType imageType) {
        if (Build.VERSION.SDK_INT >= 19) {
            return true;
        }
        return hr.contains(imageType);
    }

    private static int b(double d2) {
        int c2 = c(d2);
        int round = round(((double) c2) * d2);
        return round((d2 / ((double) (((float) round) / ((float) c2)))) * ((double) round));
    }

    private static boolean b(BitmapFactory.Options options) {
        int i;
        int i2 = options.inTargetDensity;
        return i2 > 0 && (i = options.inDensity) > 0 && i2 != i;
    }

    private static int[] b(InputStream inputStream, BitmapFactory.Options options, a aVar, d dVar) throws IOException {
        options.inJustDecodeBounds = true;
        a(inputStream, options, aVar, dVar);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static int c(double d2) {
        if (d2 > 1.0d) {
            d2 = 1.0d / d2;
        }
        return (int) Math.round(d2 * 2.147483647E9d);
    }

    private static void c(BitmapFactory.Options options) {
        d(options);
        synchronized (ir) {
            ir.offer(options);
        }
    }

    private static void d(BitmapFactory.Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;
        options.inBitmap = null;
        options.inMutable = true;
    }

    @TargetApi(19)
    @Nullable
    private static String k(Bitmap bitmap) {
        String str;
        if (bitmap == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            str = " (" + bitmap.getAllocationByteCount() + ")";
        } else {
            str = "";
        }
        return "[" + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig() + str;
    }

    private static int round(double d2) {
        return (int) (d2 + 0.5d);
    }

    public A<Bitmap> a(InputStream inputStream, int i, int i2, g gVar) throws IOException {
        return a(inputStream, i, i2, gVar, gr);
    }

    public A<Bitmap> a(InputStream inputStream, int i, int i2, g gVar, a aVar) throws IOException {
        i.b(inputStream.markSupported(), "You must provide an InputStream that supports mark()");
        byte[] bArr = (byte[]) this.Ll.a(65536, byte[].class);
        BitmapFactory.Options Jn = Jn();
        Jn.inTempStorage = bArr;
        DecodeFormat decodeFormat = (DecodeFormat) gVar.a(Yq);
        try {
            return f.a(a(inputStream, Jn, (DownsampleStrategy) gVar.a(DownsampleStrategy.Wq), decodeFormat, decodeFormat == DecodeFormat.Hx ? false : gVar.a(br) != null && ((Boolean) gVar.a(br)).booleanValue(), i, i2, ((Boolean) gVar.a(_q)).booleanValue(), aVar), this.Xi);
        } finally {
            c(Jn);
            this.Ll.put(bArr);
        }
    }

    public boolean b(ByteBuffer byteBuffer) {
        return true;
    }

    public boolean h(InputStream inputStream) {
        return true;
    }
}
