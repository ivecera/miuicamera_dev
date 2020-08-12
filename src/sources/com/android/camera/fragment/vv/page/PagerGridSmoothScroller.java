package com.android.camera.fragment.vv.page;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

public class PagerGridSmoothScroller extends LinearSmoothScroller {
    private RecyclerView mRecyclerView;

    public PagerGridSmoothScroller(@NonNull RecyclerView recyclerView) {
        super(recyclerView.getContext());
        this.mRecyclerView = recyclerView;
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.widget.LinearSmoothScroller
    public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return PagerConfig.getMillisecondsPreInch() / ((float) displayMetrics.densityDpi);
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.widget.LinearSmoothScroller, android.support.v7.widget.RecyclerView.SmoothScroller
    public void onTargetFound(View view, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (layoutManager != null && (layoutManager instanceof PagerGridLayoutManager)) {
            int[] snapOffset = ((PagerGridLayoutManager) layoutManager).getSnapOffset(this.mRecyclerView.getChildAdapterPosition(view));
            int i = snapOffset[0];
            int i2 = snapOffset[1];
            int calculateTimeForScrolling = calculateTimeForScrolling(Math.max(Math.abs(i), Math.abs(i2))) * 2;
            if (calculateTimeForScrolling > 0) {
                action.update(i, i2, calculateTimeForScrolling, ((LinearSmoothScroller) this).mDecelerateInterpolator);
            }
        }
    }
}
