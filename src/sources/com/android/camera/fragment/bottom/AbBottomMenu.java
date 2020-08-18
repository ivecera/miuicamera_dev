package com.android.camera.fragment.bottom;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import com.android.camera.fragment.beauty.MenuItem;
import com.android.camera.ui.ColorActivateTextView;

public abstract class AbBottomMenu {
    private static final int CLICK_INTERVAL = 300;
    protected BeautyMenuAnimator mBeautyMenuAnimator;
    protected LinearLayout mContainerView;
    protected Context mContext;
    protected ColorActivateTextView mCurrentBeautyTextView;
    private long mLastClickTime;

    public AbBottomMenu(Context context, LinearLayout linearLayout, BeautyMenuAnimator beautyMenuAnimator) {
        this.mContext = context;
        this.mContainerView = linearLayout;
        this.mBeautyMenuAnimator = beautyMenuAnimator;
    }

    /* access modifiers changed from: package-private */
    public abstract void addAllView();

    /* access modifiers changed from: package-private */
    public abstract SparseArray<ColorActivateTextView> getChildMenuViewList();

    /* access modifiers changed from: package-private */
    public abstract int getDefaultType();

    /* access modifiers changed from: package-private */
    public abstract SparseArray<MenuItem> getMenuData();

    /* access modifiers changed from: protected */
    public void hideAdvance() {
        this.mContainerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /* class com.android.camera.fragment.bottom.AbBottomMenu.AnonymousClass1 */

            public void onGlobalLayout() {
                BeautyMenuAnimator beautyMenuAnimator = AbBottomMenu.this.mBeautyMenuAnimator;
                if (beautyMenuAnimator != null) {
                    beautyMenuAnimator.shrinkImmediately();
                    AbBottomMenu.this.mContainerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean isClickEnable() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastClickTime < 300) {
            return false;
        }
        this.mLastClickTime = currentTimeMillis;
        return true;
    }

    /* access modifiers changed from: package-private */
    public abstract boolean isRefreshUI();

    /* access modifiers changed from: package-private */
    public void selectBeautyType(int i) {
        ColorActivateTextView colorActivateTextView;
        ColorActivateTextView colorActivateTextView2 = this.mCurrentBeautyTextView;
        if (colorActivateTextView2 != null) {
            colorActivateTextView2.setActivated(false);
        }
        SparseArray<ColorActivateTextView> childMenuViewList = getChildMenuViewList();
        if (childMenuViewList != null && (colorActivateTextView = childMenuViewList.get(i)) != null) {
            colorActivateTextView.setActivated(true);
            this.mCurrentBeautyTextView = colorActivateTextView;
        }
    }

    /* access modifiers changed from: package-private */
    public abstract void switchMenu();
}
