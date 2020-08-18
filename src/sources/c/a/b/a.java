package c.a.b;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import java.util.regex.Pattern;

/* compiled from: TextMeshUtils */
public class a {
    private static int a(Canvas canvas, String str, float f2, float f3, Paint paint, int i) {
        int i2;
        int i3;
        Rect rect = new Rect();
        System.out.printf("the text space is: %d\n", Integer.valueOf(i));
        System.out.printf("the text length is: %d\n", Integer.valueOf(str.length()));
        float f4 = f2;
        int i4 = 0;
        int i5 = 0;
        while (i4 < str.length()) {
            char charAt = str.charAt(i4);
            String valueOf = String.valueOf(charAt);
            Math.round(paint.measureText(String.valueOf(charAt)));
            if (charAt > 55296) {
                i4++;
                String str2 = String.valueOf(charAt) + str.charAt(i4);
                if (i4 != 1) {
                    paint.getTextBounds(str, i4, i4 + 1, rect);
                    rect.width();
                }
                int width = rect.width() + (i / 2);
                f4 += (float) width;
                i5 += width;
                if (canvas != null) {
                    canvas.drawText(str2, f4, f3, paint);
                }
            } else {
                if (charAt == ' ') {
                    i2 = Math.round(paint.measureText(String.valueOf(str.charAt(i4)))) + i;
                } else {
                    paint.getTextBounds(str, i4, i4 + 1, rect);
                    if (charAt >= 128) {
                        i3 = rect.width() + (i / 2);
                        System.out.printf("%s is not NumOrLetters\n", Character.valueOf(charAt));
                    } else if (m(valueOf)) {
                        i3 = rect.width() + i;
                        System.out.printf("%s is NumOrLetters\n", Character.valueOf(charAt));
                    } else {
                        i2 = rect.width() + (i * 2);
                        System.out.printf("%s is: %d, space is: %d, text is %d\n", Character.valueOf(str.charAt(i4)), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(rect.width()));
                    }
                    i2 = i3;
                    System.out.printf("%s is: %d, space is: %d, text is %d\n", Character.valueOf(str.charAt(i4)), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(rect.width()));
                }
                f4 += (float) i2;
                i5 += i2;
                if (canvas != null) {
                    canvas.drawText(String.valueOf(str.charAt(i4)), f4, f3, paint);
                }
            }
            i4++;
        }
        return i5;
    }

    private static Bitmap a(byte[] bArr, String str, int i, int i2, int i3, float f2, int i4, float f3, int i5, float f4, float f5, float f6, int i6, int i7, int i8, int i9) {
        String str2 = new String(bArr);
        Rect rect = new Rect(0, 0, i8, i9);
        Bitmap createBitmap = Bitmap.createBitmap(i8, i9, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(0);
        paint.setStyle(Paint.Style.FILL);
        Canvas canvas = new Canvas(createBitmap);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(i2);
        float f7 = (float) i;
        textPaint.setTextSize(f7);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        if (i7 == 0) {
            textPaint.setTextAlign(Paint.Align.LEFT);
        } else if (i7 == 1) {
            textPaint.setTextAlign(Paint.Align.CENTER);
        } else {
            textPaint.setTextAlign(Paint.Align.RIGHT);
        }
        if ((i4 & 16) == 16) {
            textPaint.setUnderlineText(true);
        }
        if ((i4 & 32) == 32) {
            textPaint.setStrikeThruText(true);
        }
        if ((i4 & 4) == 4) {
            textPaint.setTextSkewX((-f2) / 90.0f);
        }
        if ((i4 & 8) == 8) {
            textPaint.setFakeBoldText(true);
        }
        if ((i4 & 2) == 2) {
            textPaint.setShadowLayer(f4, f5, f6, i6);
        }
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int centerY = (int) ((((float) rect.centerY()) - (fontMetrics.top / 2.0f)) - (fontMetrics.bottom / 2.0f));
        if ((i4 & 1) == 1) {
            TextPaint textPaint2 = new TextPaint();
            textPaint2.setColor(i5);
            textPaint2.setTextSize(textPaint.getTextSize());
            textPaint2.setAntiAlias(textPaint.isAntiAlias());
            textPaint2.setStyle(Paint.Style.STROKE);
            textPaint2.setStrokeWidth((5.0f * f3) / f7);
            textPaint2.setTextAlign(textPaint.getTextAlign());
            textPaint2.setTextSkewX(textPaint.getTextSkewX());
            textPaint.setFakeBoldText(false);
            textPaint2.setFakeBoldText(true);
            float f8 = ((float) i3) / f7;
            if (Build.VERSION.SDK_INT >= 21) {
                textPaint2.setLetterSpacing(f8);
                if (i7 == 0) {
                    canvas.drawText(str2, (float) rect.left, (float) centerY, textPaint2);
                } else if (i7 == 1) {
                    canvas.drawText(str2, (float) rect.centerX(), (float) centerY, textPaint2);
                } else {
                    canvas.drawText(str2, (float) rect.right, (float) centerY, textPaint2);
                }
            } else {
                Rect rect2 = new Rect();
                textPaint.getTextBounds(str2, 0, str2.length(), rect2);
                int i10 = 0;
                int i11 = 0;
                while (i10 < str2.length()) {
                    if (str2.charAt(i10) > 55296) {
                        i10++;
                        i11++;
                    }
                    i10++;
                }
                if (i7 == 0) {
                    a(canvas, str2, 0.0f, (float) centerY, textPaint2, i3);
                } else if (i7 == 1) {
                    a(canvas, str2, (float) ((((rect.width() - rect2.width()) + (i11 * i)) - ((str2.length() - 1) * i3)) / 2), (float) centerY, textPaint2, i3);
                } else {
                    a(canvas, str2, (float) (((rect.width() - rect2.width()) + (i11 * i)) - ((str2.length() - 1) * i3)), (float) centerY, textPaint2, i3);
                }
            }
        }
        float f9 = ((float) i3) / f7;
        if (Build.VERSION.SDK_INT >= 21) {
            textPaint.setLetterSpacing(f9);
            if (i7 == 0) {
                canvas.drawText(str2, (float) rect.left, (float) centerY, textPaint);
            } else if (i7 == 1) {
                canvas.drawText(str2, (float) rect.centerX(), (float) centerY, textPaint);
            } else {
                canvas.drawText(str2, (float) rect.right, (float) centerY, textPaint);
            }
        } else {
            Rect rect3 = new Rect();
            int i12 = 0;
            textPaint.getTextBounds(str2, 0, str2.length(), rect3);
            int i13 = 0;
            while (i12 < str2.length()) {
                if (str2.charAt(i12) > 55296) {
                    i12++;
                    i13++;
                }
                i12++;
            }
            if (i7 == 0) {
                a(canvas, str2, 0.0f, (float) centerY, textPaint, i3);
            } else if (i7 == 1) {
                a(canvas, str2, (float) ((((rect.width() - rect3.width()) + (i13 * i)) - ((str2.length() - 1) * i3)) / 2), (float) centerY, textPaint, i3);
            } else {
                a(canvas, str2, (float) (((rect.width() - rect3.width()) + (i13 * i)) - ((str2.length() - 1) * i3)), (float) centerY, textPaint, i3);
            }
        }
        canvas.save(31);
        canvas.restore();
        return createBitmap;
    }

    public static boolean m(String str) {
        return Pattern.compile("^[A-Za-z0-9_]+$").matcher(str).matches();
    }
}
