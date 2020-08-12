package com.android.camera.features.mimoji2.fragment.gif;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class DragRelativeLayout extends RelativeLayout {
    private int bottomParent;
    private int bottomParentAfter;
    private float endx;
    private float endy;
    private int horVector;
    private int leftParent;
    private int leftParentAfter;
    private int rightParent;
    private int rightParentAfter;
    private float startx;
    private float starty;
    private int topParent;
    private int topParentAfter;
    private int verVector;

    public DragRelativeLayout(Context context) {
        this(context, null);
    }

    public DragRelativeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DragRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public int getBottomParentAfter() {
        return this.bottomParentAfter;
    }

    public int getLeftParentAfter() {
        return this.leftParentAfter;
    }

    public int getRightParentAfter() {
        return this.rightParentAfter;
    }

    public int getTopParentAfter() {
        return this.topParentAfter;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startx = motionEvent.getX();
            this.starty = motionEvent.getY();
        } else if (action != 1 && action == 2) {
            this.endx = motionEvent.getX();
            this.endy = motionEvent.getY();
            this.leftParent = getLeft();
            this.topParent = getTop();
            this.rightParent = getRight();
            this.bottomParent = getBottom();
            this.horVector = (int) (this.endx - this.startx);
            this.verVector = (int) (this.endy - this.starty);
            int i = this.leftParent;
            int i2 = this.horVector;
            this.leftParentAfter = i + i2;
            int i3 = this.topParent;
            int i4 = this.verVector;
            this.topParentAfter = i3 + i4;
            this.rightParentAfter = this.rightParent + i2;
            this.bottomParentAfter = this.bottomParent + i4;
            if (!(i2 == 0 && i4 == 0)) {
                layout(this.leftParentAfter, this.topParentAfter, this.rightParentAfter, this.bottomParentAfter);
                invalidate();
            }
        }
        return true;
    }
}
