package android.support.v7.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RoundRectDrawableWithShadow;

@RequiresApi(17)
class CardViewApi17Impl extends CardViewBaseImpl {
    CardViewApi17Impl() {
    }

    @Override // android.support.v7.widget.CardViewImpl, android.support.v7.widget.CardViewBaseImpl
    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper = new RoundRectDrawableWithShadow.RoundRectHelper() {
            /* class android.support.v7.widget.CardViewApi17Impl.AnonymousClass1 */

            @Override // android.support.v7.widget.RoundRectDrawableWithShadow.RoundRectHelper
            public void drawRoundRect(Canvas canvas, RectF rectF, float f2, Paint paint) {
                canvas.drawRoundRect(rectF, f2, f2, paint);
            }
        };
    }
}
