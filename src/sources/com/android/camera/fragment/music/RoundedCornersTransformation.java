package com.android.camera.fragment.music;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.resource.bitmap.g;
import java.security.MessageDigest;

public class RoundedCornersTransformation extends g {
    private static final String ID = "jp.wasabeef.glide.transformations.RoundedCornersTransformation.1";
    private static final int VERSION = 1;
    private CornerType cornerType;
    private int diameter;
    private int margin;
    private int radius;

    /* renamed from: com.android.camera.fragment.music.RoundedCornersTransformation$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType = new int[CornerType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(30:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|(3:29|30|32)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(32:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|32) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x007a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0086 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0092 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x009e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x00aa */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.ALL.ordinal()] = 1;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.TOP_LEFT.ordinal()] = 2;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.TOP_RIGHT.ordinal()] = 3;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.BOTTOM_LEFT.ordinal()] = 4;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.BOTTOM_RIGHT.ordinal()] = 5;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.TOP.ordinal()] = 6;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.BOTTOM.ordinal()] = 7;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.LEFT.ordinal()] = 8;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.RIGHT.ordinal()] = 9;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.OTHER_TOP_LEFT.ordinal()] = 10;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.OTHER_TOP_RIGHT.ordinal()] = 11;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.OTHER_BOTTOM_LEFT.ordinal()] = 12;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.OTHER_BOTTOM_RIGHT.ordinal()] = 13;
            $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.DIAGONAL_FROM_TOP_LEFT.ordinal()] = 14;
            try {
                $SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[CornerType.DIAGONAL_FROM_TOP_RIGHT.ordinal()] = 15;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public enum CornerType {
        ALL,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        OTHER_TOP_LEFT,
        OTHER_TOP_RIGHT,
        OTHER_BOTTOM_LEFT,
        OTHER_BOTTOM_RIGHT,
        DIAGONAL_FROM_TOP_LEFT,
        DIAGONAL_FROM_TOP_RIGHT
    }

    public RoundedCornersTransformation(int i, int i2) {
        this(i, i2, CornerType.ALL);
    }

    public RoundedCornersTransformation(int i, int i2, CornerType cornerType2) {
        this.radius = i;
        this.diameter = this.radius * 2;
        this.margin = i2;
        this.cornerType = cornerType2;
    }

    private void drawBottomLeftRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.margin;
        int i2 = this.diameter;
        RectF rectF = new RectF((float) i, f3 - ((float) i2), (float) (i + i2), f3);
        int i3 = this.radius;
        canvas.drawRoundRect(rectF, (float) i3, (float) i3, paint);
        int i4 = this.margin;
        canvas.drawRect(new RectF((float) i4, (float) i4, (float) (i4 + this.diameter), f3 - ((float) this.radius)), paint);
        int i5 = this.margin;
        canvas.drawRect(new RectF((float) (this.radius + i5), (float) i5, f2, f3), paint);
    }

    private void drawBottomRightRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.diameter;
        RectF rectF = new RectF(f2 - ((float) i), f3 - ((float) i), f2, f3);
        int i2 = this.radius;
        canvas.drawRoundRect(rectF, (float) i2, (float) i2, paint);
        int i3 = this.margin;
        canvas.drawRect(new RectF((float) i3, (float) i3, f2 - ((float) this.radius), f3), paint);
        int i4 = this.radius;
        canvas.drawRect(new RectF(f2 - ((float) i4), (float) this.margin, f2, f3 - ((float) i4)), paint);
    }

    private void drawBottomRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        RectF rectF = new RectF((float) this.margin, f3 - ((float) this.diameter), f2, f3);
        int i = this.radius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        int i2 = this.margin;
        canvas.drawRect(new RectF((float) i2, (float) i2, f2, f3 - ((float) this.radius)), paint);
    }

    private void drawDiagonalFromTopLeftRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.margin;
        int i2 = this.diameter;
        RectF rectF = new RectF((float) i, (float) i, (float) (i + i2), (float) (i + i2));
        int i3 = this.radius;
        canvas.drawRoundRect(rectF, (float) i3, (float) i3, paint);
        int i4 = this.diameter;
        RectF rectF2 = new RectF(f2 - ((float) i4), f3 - ((float) i4), f2, f3);
        int i5 = this.radius;
        canvas.drawRoundRect(rectF2, (float) i5, (float) i5, paint);
        int i6 = this.margin;
        canvas.drawRect(new RectF((float) i6, (float) (i6 + this.radius), f2 - ((float) this.diameter), f3), paint);
        int i7 = this.margin;
        canvas.drawRect(new RectF((float) (this.diameter + i7), (float) i7, f2, f3 - ((float) this.radius)), paint);
    }

    private void drawDiagonalFromTopRightRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.diameter;
        int i2 = this.margin;
        RectF rectF = new RectF(f2 - ((float) i), (float) i2, f2, (float) (i2 + i));
        int i3 = this.radius;
        canvas.drawRoundRect(rectF, (float) i3, (float) i3, paint);
        int i4 = this.margin;
        int i5 = this.diameter;
        RectF rectF2 = new RectF((float) i4, f3 - ((float) i5), (float) (i4 + i5), f3);
        int i6 = this.radius;
        canvas.drawRoundRect(rectF2, (float) i6, (float) i6, paint);
        int i7 = this.margin;
        int i8 = this.radius;
        canvas.drawRect(new RectF((float) i7, (float) i7, f2 - ((float) i8), f3 - ((float) i8)), paint);
        int i9 = this.margin;
        int i10 = this.radius;
        canvas.drawRect(new RectF((float) (i9 + i10), (float) (i9 + i10), f2, f3), paint);
    }

    private void drawLeftRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.margin;
        RectF rectF = new RectF((float) i, (float) i, (float) (i + this.diameter), f3);
        int i2 = this.radius;
        canvas.drawRoundRect(rectF, (float) i2, (float) i2, paint);
        int i3 = this.margin;
        canvas.drawRect(new RectF((float) (this.radius + i3), (float) i3, f2, f3), paint);
    }

    private void drawOtherBottomLeftRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.margin;
        RectF rectF = new RectF((float) i, (float) i, f2, (float) (i + this.diameter));
        int i2 = this.radius;
        canvas.drawRoundRect(rectF, (float) i2, (float) i2, paint);
        RectF rectF2 = new RectF(f2 - ((float) this.diameter), (float) this.margin, f2, f3);
        int i3 = this.radius;
        canvas.drawRoundRect(rectF2, (float) i3, (float) i3, paint);
        int i4 = this.margin;
        int i5 = this.radius;
        canvas.drawRect(new RectF((float) i4, (float) (i4 + i5), f2 - ((float) i5), f3), paint);
    }

    private void drawOtherBottomRightRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.margin;
        RectF rectF = new RectF((float) i, (float) i, f2, (float) (i + this.diameter));
        int i2 = this.radius;
        canvas.drawRoundRect(rectF, (float) i2, (float) i2, paint);
        int i3 = this.margin;
        RectF rectF2 = new RectF((float) i3, (float) i3, (float) (i3 + this.diameter), f3);
        int i4 = this.radius;
        canvas.drawRoundRect(rectF2, (float) i4, (float) i4, paint);
        int i5 = this.margin;
        int i6 = this.radius;
        canvas.drawRect(new RectF((float) (i5 + i6), (float) (i5 + i6), f2, f3), paint);
    }

    private void drawOtherTopLeftRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        RectF rectF = new RectF((float) this.margin, f3 - ((float) this.diameter), f2, f3);
        int i = this.radius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        RectF rectF2 = new RectF(f2 - ((float) this.diameter), (float) this.margin, f2, f3);
        int i2 = this.radius;
        canvas.drawRoundRect(rectF2, (float) i2, (float) i2, paint);
        int i3 = this.margin;
        int i4 = this.radius;
        canvas.drawRect(new RectF((float) i3, (float) i3, f2 - ((float) i4), f3 - ((float) i4)), paint);
    }

    private void drawOtherTopRightRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.margin;
        RectF rectF = new RectF((float) i, (float) i, (float) (i + this.diameter), f3);
        int i2 = this.radius;
        canvas.drawRoundRect(rectF, (float) i2, (float) i2, paint);
        RectF rectF2 = new RectF((float) this.margin, f3 - ((float) this.diameter), f2, f3);
        int i3 = this.radius;
        canvas.drawRoundRect(rectF2, (float) i3, (float) i3, paint);
        int i4 = this.margin;
        int i5 = this.radius;
        canvas.drawRect(new RectF((float) (i4 + i5), (float) i4, f2, f3 - ((float) i5)), paint);
    }

    private void drawRightRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        RectF rectF = new RectF(f2 - ((float) this.diameter), (float) this.margin, f2, f3);
        int i = this.radius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        int i2 = this.margin;
        canvas.drawRect(new RectF((float) i2, (float) i2, f2 - ((float) this.radius), f3), paint);
    }

    private void drawRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.margin;
        float f4 = f2 - ((float) i);
        float f5 = f3 - ((float) i);
        switch (AnonymousClass1.$SwitchMap$com$android$camera$fragment$music$RoundedCornersTransformation$CornerType[this.cornerType.ordinal()]) {
            case 1:
                int i2 = this.margin;
                RectF rectF = new RectF((float) i2, (float) i2, f4, f5);
                int i3 = this.radius;
                canvas.drawRoundRect(rectF, (float) i3, (float) i3, paint);
                return;
            case 2:
                drawTopLeftRoundRect(canvas, paint, f4, f5);
                return;
            case 3:
                drawTopRightRoundRect(canvas, paint, f4, f5);
                return;
            case 4:
                drawBottomLeftRoundRect(canvas, paint, f4, f5);
                return;
            case 5:
                drawBottomRightRoundRect(canvas, paint, f4, f5);
                return;
            case 6:
                drawTopRoundRect(canvas, paint, f4, f5);
                return;
            case 7:
                drawBottomRoundRect(canvas, paint, f4, f5);
                return;
            case 8:
                drawLeftRoundRect(canvas, paint, f4, f5);
                return;
            case 9:
                drawRightRoundRect(canvas, paint, f4, f5);
                return;
            case 10:
                drawOtherTopLeftRoundRect(canvas, paint, f4, f5);
                return;
            case 11:
                drawOtherTopRightRoundRect(canvas, paint, f4, f5);
                return;
            case 12:
                drawOtherBottomLeftRoundRect(canvas, paint, f4, f5);
                return;
            case 13:
                drawOtherBottomRightRoundRect(canvas, paint, f4, f5);
                return;
            case 14:
                drawDiagonalFromTopLeftRoundRect(canvas, paint, f4, f5);
                return;
            case 15:
                drawDiagonalFromTopRightRoundRect(canvas, paint, f4, f5);
                return;
            default:
                int i4 = this.margin;
                RectF rectF2 = new RectF((float) i4, (float) i4, f4, f5);
                int i5 = this.radius;
                canvas.drawRoundRect(rectF2, (float) i5, (float) i5, paint);
                return;
        }
    }

    private void drawTopLeftRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.margin;
        int i2 = this.diameter;
        RectF rectF = new RectF((float) i, (float) i, (float) (i + i2), (float) (i + i2));
        int i3 = this.radius;
        canvas.drawRoundRect(rectF, (float) i3, (float) i3, paint);
        int i4 = this.margin;
        int i5 = this.radius;
        canvas.drawRect(new RectF((float) i4, (float) (i4 + i5), (float) (i4 + i5), f3), paint);
        int i6 = this.margin;
        canvas.drawRect(new RectF((float) (this.radius + i6), (float) i6, f2, f3), paint);
    }

    private void drawTopRightRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.diameter;
        int i2 = this.margin;
        RectF rectF = new RectF(f2 - ((float) i), (float) i2, f2, (float) (i2 + i));
        int i3 = this.radius;
        canvas.drawRoundRect(rectF, (float) i3, (float) i3, paint);
        int i4 = this.margin;
        canvas.drawRect(new RectF((float) i4, (float) i4, f2 - ((float) this.radius), f3), paint);
        int i5 = this.radius;
        canvas.drawRect(new RectF(f2 - ((float) i5), (float) (this.margin + i5), f2, f3), paint);
    }

    private void drawTopRoundRect(Canvas canvas, Paint paint, float f2, float f3) {
        int i = this.margin;
        RectF rectF = new RectF((float) i, (float) i, f2, (float) (i + this.diameter));
        int i2 = this.radius;
        canvas.drawRoundRect(rectF, (float) i2, (float) i2, paint);
        int i3 = this.margin;
        canvas.drawRect(new RectF((float) i3, (float) (i3 + this.radius), f2, f3), paint);
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (obj instanceof RoundedCornersTransformation) {
            RoundedCornersTransformation roundedCornersTransformation = (RoundedCornersTransformation) obj;
            return roundedCornersTransformation.radius == this.radius && roundedCornersTransformation.diameter == this.diameter && roundedCornersTransformation.margin == this.margin && roundedCornersTransformation.cornerType == this.cornerType;
        }
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        return ID.hashCode() + (this.radius * 10000) + (this.diameter * 1000) + (this.margin * 100) + (this.cornerType.ordinal() * 10);
    }

    public String toString() {
        return "RoundedTransformation(radius=" + this.radius + ", margin=" + this.margin + ", diameter=" + this.diameter + ", cornerType=" + this.cornerType.name() + ")";
    }

    /* access modifiers changed from: protected */
    @Override // com.bumptech.glide.load.resource.bitmap.g
    public Bitmap transform(d dVar, Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap d2 = dVar.d(width, height, Bitmap.Config.ARGB_8888);
        d2.setHasAlpha(true);
        Canvas canvas = new Canvas(d2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint.setShader(new BitmapShader(bitmap, tileMode, tileMode));
        drawRoundRect(canvas, paint, (float) width, (float) height);
        return d2;
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}
