package com.android.camera.fragment.idcard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.log.Log;

public class IDCardView extends View {
    private static final String TAG = "IDCardView";
    private int mBigColor;
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private Bitmap mBitmap3;
    private Bitmap mBitmap4;
    private float mBitmapHeight;
    private float mBitmapWidth;
    private Context mContext;
    private Rect mDisplayRect;
    private String mHint;
    private RectF mIDCardRectF;
    private float mMarginLeft;
    private float mMarginTop;
    private Paint mPaint;
    private Path mPath;
    private float mRectHeight;
    private Paint mRectPaint;
    private float mRectWidth;
    private Bitmap mSignBitmap;
    private float mSignMarginLeft;
    private float mSignMarginTop;
    private Paint mTextPaint;

    public IDCardView(Context context, boolean z) {
        super(context);
        init(context, z);
    }

    private Bitmap decodeResourceCatchOOM(Resources resources, int i) {
        try {
            return BitmapFactory.decodeResource(resources, i);
        } catch (OutOfMemoryError e2) {
            Log.e(TAG, "decodeResourceCatchOOM", e2);
            return null;
        }
    }

    private void init(Context context, boolean z) {
        this.mContext = context;
        Resources resources = this.mContext.getResources();
        this.mDisplayRect = Util.getDisplayRect();
        this.mTextPaint = new Paint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setColor(-1);
        this.mTextPaint.setTextSize(resources.getDimension(R.dimen.id_card_text_size));
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mRectPaint = new Paint();
        this.mRectPaint.setAntiAlias(true);
        this.mBigColor = resources.getColor(R.color.id_card_big_rect_color);
        this.mMarginLeft = resources.getDimension(R.dimen.id_card_margin_left);
        this.mMarginTop = resources.getDimension(R.dimen.id_card_margin_top);
        this.mRectWidth = resources.getDimension(R.dimen.id_card_rect_width);
        this.mRectHeight = resources.getDimension(R.dimen.id_card_rect_height);
        float dimension = resources.getDimension(R.dimen.id_card_head_margin_left);
        float dimension2 = resources.getDimension(R.dimen.id_card_head_margin_top);
        float dimension3 = resources.getDimension(R.dimen.id_card_emblem_margin_left);
        float dimension4 = resources.getDimension(R.dimen.id_card_emblem_margin_top);
        this.mBitmap1 = decodeResourceCatchOOM(resources, R.drawable.id_card_1);
        this.mBitmap2 = decodeResourceCatchOOM(resources, R.drawable.id_card_2);
        this.mBitmap3 = decodeResourceCatchOOM(resources, R.drawable.id_card_3);
        this.mBitmap4 = decodeResourceCatchOOM(resources, R.drawable.id_card_4);
        Bitmap decodeResourceCatchOOM = decodeResourceCatchOOM(resources, R.drawable.id_card_head);
        Bitmap decodeResourceCatchOOM2 = decodeResourceCatchOOM(resources, R.drawable.id_card_emblem);
        this.mBitmapWidth = (float) this.mBitmap1.getWidth();
        this.mBitmapHeight = (float) this.mBitmap1.getHeight();
        String string = resources.getString(R.string.id_card_mode_hint_front);
        String string2 = resources.getString(R.string.id_card_mode_hint_back);
        if (z) {
            this.mHint = string;
            this.mSignMarginLeft = dimension;
            this.mSignMarginTop = dimension2;
            this.mSignBitmap = decodeResourceCatchOOM;
        } else {
            this.mHint = string2;
            this.mSignMarginLeft = dimension3;
            this.mSignMarginTop = dimension4;
            this.mSignBitmap = decodeResourceCatchOOM2;
        }
        this.mIDCardRectF = new RectF();
        RectF rectF = this.mIDCardRectF;
        float f2 = this.mMarginLeft;
        rectF.left = f2;
        int i = this.mDisplayRect.top;
        float f3 = this.mMarginTop;
        rectF.top = ((float) i) + f3;
        rectF.right = f2 + this.mRectWidth;
        rectF.bottom = ((float) i) + f3 + this.mRectHeight;
        this.mPath = new Path();
        Rect rect = new Rect();
        Paint paint = this.mTextPaint;
        String str = this.mHint;
        paint.getTextBounds(str, 0, str.length(), rect);
        float dimension5 = (((float) this.mDisplayRect.right) - resources.getDimension(R.dimen.id_card_draw_text_margin_right)) - ((float) rect.height());
        Rect rect2 = this.mDisplayRect;
        this.mPath.moveTo(dimension5, (float) (rect2.top + ((rect2.height() - rect.width()) / 2)));
        this.mPath.lineTo(dimension5, (float) this.mDisplayRect.bottom);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mRectPaint.setColor(this.mBigColor);
        this.mRectPaint.setXfermode(null);
        canvas.drawRect(this.mDisplayRect, this.mRectPaint);
        this.mRectPaint.setColor(0);
        this.mRectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(this.mIDCardRectF, this.mRectPaint);
        canvas.drawTextOnPath(this.mHint, this.mPath, 0.0f, 0.0f, this.mTextPaint);
        Bitmap bitmap = this.mBitmap1;
        if (bitmap != null) {
            RectF rectF = this.mIDCardRectF;
            canvas.drawBitmap(bitmap, rectF.left, rectF.top, this.mPaint);
        }
        Bitmap bitmap2 = this.mBitmap2;
        if (bitmap2 != null) {
            RectF rectF2 = this.mIDCardRectF;
            canvas.drawBitmap(bitmap2, rectF2.right - this.mBitmapWidth, rectF2.top, this.mPaint);
        }
        Bitmap bitmap3 = this.mBitmap3;
        if (bitmap3 != null) {
            RectF rectF3 = this.mIDCardRectF;
            canvas.drawBitmap(bitmap3, rectF3.left, rectF3.bottom - this.mBitmapHeight, this.mPaint);
        }
        Bitmap bitmap4 = this.mBitmap4;
        if (bitmap4 != null) {
            RectF rectF4 = this.mIDCardRectF;
            canvas.drawBitmap(bitmap4, rectF4.right - this.mBitmapWidth, rectF4.bottom - this.mBitmapHeight, this.mPaint);
        }
        Bitmap bitmap5 = this.mSignBitmap;
        if (bitmap5 != null) {
            canvas.drawBitmap(bitmap5, this.mSignMarginLeft, ((float) this.mDisplayRect.top) + this.mSignMarginTop, this.mPaint);
        }
    }
}
