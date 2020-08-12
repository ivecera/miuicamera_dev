package com.android.camera.fragment.vv.page;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

public class PagerGridSnapHelper extends SnapHelper {
    private RecyclerView mRecyclerView;

    @Override // android.support.v7.widget.SnapHelper
    private boolean snapFromFling(@NonNull RecyclerView.LayoutManager layoutManager, int i, int i2) {
        LinearSmoothScroller createSnapScroller;
        int findTargetSnapPosition;
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) || (createSnapScroller = createSnapScroller(layoutManager)) == null || (findTargetSnapPosition = findTargetSnapPosition(layoutManager, i, i2)) == -1) {
            return false;
        }
        createSnapScroller.setTargetPosition(findTargetSnapPosition);
        layoutManager.startSmoothScroll(createSnapScroller);
        return true;
    }

    @Override // android.support.v7.widget.SnapHelper
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    @Override // android.support.v7.widget.SnapHelper
    @Nullable
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view) {
        return layoutManager instanceof PagerGridLayoutManager ? ((PagerGridLayoutManager) layoutManager).getSnapOffset(layoutManager.getPosition(view)) : new int[2];
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.widget.SnapHelper
    public LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new PagerGridSmoothScroller(this.mRecyclerView);
    }

    @Override // android.support.v7.widget.SnapHelper
    @Nullable
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof PagerGridLayoutManager) {
            return ((PagerGridLayoutManager) layoutManager).findSnapView();
        }
        return null;
    }

    @Override // android.support.v7.widget.SnapHelper
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int i, int i2) {
        if (layoutManager != null && (layoutManager instanceof PagerGridLayoutManager)) {
            PagerGridLayoutManager pagerGridLayoutManager = (PagerGridLayoutManager) layoutManager;
            if (pagerGridLayoutManager.canScrollHorizontally()) {
                if (i > PagerConfig.getFlingThreshold()) {
                    return pagerGridLayoutManager.findNextPageFirstPos();
                }
                if (i < (-PagerConfig.getFlingThreshold())) {
                    return pagerGridLayoutManager.findPrePageFirstPos();
                }
            } else if (pagerGridLayoutManager.canScrollVertically()) {
                if (i2 > PagerConfig.getFlingThreshold()) {
                    return pagerGridLayoutManager.findNextPageFirstPos();
                }
                if (i2 < (-PagerConfig.getFlingThreshold())) {
                    return pagerGridLayoutManager.findPrePageFirstPos();
                }
            }
        }
        return -1;
    }

    @Override // android.support.v7.widget.SnapHelper, android.support.v7.widget.RecyclerView.OnFlingListener
    public boolean onFling(int i, int i2) {
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (layoutManager == null || this.mRecyclerView.getAdapter() == null) {
            return false;
        }
        int flingThreshold = PagerConfig.getFlingThreshold();
        return (Math.abs(i2) > flingThreshold || Math.abs(i) > flingThreshold) && snapFromFling(layoutManager, i, i2);
    }

    public void setFlingThreshold(int i) {
        PagerConfig.setFlingThreshold(i);
    }
}
