package com.android.camera.fragment.manually;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ManuallyDecoration extends RecyclerView.ItemDecoration {
    private Paint mDividerPaint;
    private int mDividerWidth = 1;
    private int mSpanCount;

    public ManuallyDecoration(int i, @ColorInt int i2) {
        this.mDividerWidth = i;
        this.mDividerPaint = new Paint();
        this.mDividerPaint.setAntiAlias(true);
        this.mDividerPaint.setColor(i2);
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, int i, RecyclerView recyclerView) {
        int i2 = this.mSpanCount;
        int i3 = this.mDividerWidth;
        int i4 = ((i2 - 1) * i3) / i2;
        int i5 = (i % i2) * (i3 - i4);
        int i6 = i4 - i5;
        if (i == 0) {
            i5 = i3;
        }
        rect.set(i5, 0, i6, i3);
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int left = childAt.getLeft();
            int right = childAt.getRight();
            int bottom = childAt.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            int i2 = this.mDividerWidth + bottom;
            Paint paint = this.mDividerPaint;
            if (paint != null) {
                canvas.drawRect((float) left, (float) bottom, (float) right, (float) i2, paint);
            }
            int top = childAt.getTop();
            int bottom2 = childAt.getBottom() + this.mDividerWidth;
            int right2 = childAt.getRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            int i3 = this.mDividerWidth + right2;
            Paint paint2 = this.mDividerPaint;
            if (paint2 != null) {
                canvas.drawRect((float) right2, (float) top, (float) i3, (float) bottom2, paint2);
            }
        }
    }

    public void setStyle(int i) {
        this.mSpanCount = i;
    }
}
