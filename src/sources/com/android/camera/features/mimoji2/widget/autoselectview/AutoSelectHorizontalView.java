package com.android.camera.features.mimoji2.widget.autoselectview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import java.util.concurrent.atomic.AtomicBoolean;

public class AutoSelectHorizontalView extends RecyclerView {
    private AtomicBoolean isClickMove = new AtomicBoolean(false);
    private boolean isFling = false;
    /* access modifiers changed from: private */
    public boolean isInit = false;
    private boolean isMoveFinished = true;
    /* access modifiers changed from: private */
    public int itemFirstLengthInvalid = 0;
    private int itemWidthMargin;
    /* access modifiers changed from: private */
    public AutoSelectAdapter mAutoSelectAdapter;
    private int mDeltaX;
    private int mLastMoveX;
    /* access modifiers changed from: private */
    public LinearLayoutManagerWrapper mLinearLayoutManager;
    private Scroller mScroller;
    private int mSelectPosition = 0;
    private int mTempSelectPosition = this.mSelectPosition;
    /* access modifiers changed from: private */
    public WrapperAdapter mWrapAdapter;

    /* access modifiers changed from: package-private */
    public class WrapperAdapter extends RecyclerView.Adapter {
        private static final int HEADER_FOOTER_TYPE = -1;
        private RecyclerView.Adapter adapter;
        /* access modifiers changed from: private */
        public Context context;
        private int headerFooterWidth;

        class HeaderFooterViewHolder extends RecyclerView.ViewHolder {
            HeaderFooterViewHolder(View view) {
                super(view);
            }
        }

        public WrapperAdapter(RecyclerView.Adapter adapter2, Context context2) {
            this.adapter = adapter2;
            this.context = context2;
        }

        private boolean isHeaderOrFooter(int i) {
            return i == 0 || i == getItemCount() - 1;
        }

        public int getHeaderFooterWidth() {
            return this.headerFooterWidth;
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.adapter.getItemCount() + 2;
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public long getItemId(int i) {
            if (i <= 0 || i > this.adapter.getItemCount()) {
                return -1;
            }
            return this.adapter.getItemId(i - 1);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == 0 || i == getItemCount() - 1) {
                return -1;
            }
            return this.adapter.getItemViewType(i - 1);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            this.adapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (!isHeaderOrFooter(i)) {
                this.adapter.onBindViewHolder(viewHolder, i - 1);
            }
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i != -1) {
                return this.adapter.onCreateViewHolder(viewGroup, i);
            }
            View view = new View(this.context);
            this.headerFooterWidth = viewGroup.getMeasuredWidth() / 2;
            view.setLayoutParams(new RecyclerView.LayoutParams(this.headerFooterWidth, -1));
            return new HeaderFooterViewHolder(view);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
            this.adapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    public AutoSelectHorizontalView(Context context) {
        super(context);
        init();
    }

    public AutoSelectHorizontalView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public AutoSelectHorizontalView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void calculateSelectedPos() {
        this.mSelectPosition = getRealPosition();
        int realPosition = getRealPosition();
        SelectItemBean selectItemBean = (SelectItemBean) this.mAutoSelectAdapter.getDataList().get(realPosition);
        if (((float) this.mDeltaX) - (selectItemBean.getCurLength() / 2.0f) > ((selectItemBean.getCurTotalLength() + ((float) (this.itemWidthMargin * realPosition))) - ((float) this.itemFirstLengthInvalid)) - (selectItemBean.getCurLength() / 2.0f)) {
            this.mSelectPosition = realPosition + 1;
        } else {
            this.mSelectPosition = realPosition;
        }
        if (this.mSelectPosition >= this.mAutoSelectAdapter.getItemCount()) {
            this.mSelectPosition = this.mAutoSelectAdapter.getItemCount() - 1;
        }
        if (this.mSelectPosition < 0) {
            this.mSelectPosition = 0;
        }
    }

    private int getRealPosition() {
        if (this.mAutoSelectAdapter.getItemCount() == 0) {
            return 0;
        }
        int itemCount = this.mAutoSelectAdapter.getItemCount() - 1;
        do {
            itemCount--;
            if ((((SelectItemBean) this.mAutoSelectAdapter.getDataList().get(itemCount)).getCurTotalLength() + ((float) (this.itemWidthMargin * itemCount))) - ((float) this.itemFirstLengthInvalid) <= ((float) Math.abs(this.mDeltaX))) {
                break;
            }
        } while (itemCount >= 0);
        return itemCount + 1;
    }

    private void init() {
        this.mScroller = new Scroller(getContext(), new DecelerateInterpolator());
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /* class com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectHorizontalView.AnonymousClass1 */

            public void onGlobalLayout() {
                if (!AutoSelectHorizontalView.this.isInit) {
                    return;
                }
                if (AutoSelectHorizontalView.this.mAutoSelectAdapter == null) {
                    Log.e("View", "mAutoSelectAdapter  不能为空");
                    return;
                }
                AutoSelectHorizontalView.this.selectedPositionChanged(0);
                AutoSelectHorizontalView.this.mLinearLayoutManager.scrollToPositionWithOffset(0, -AutoSelectHorizontalView.this.itemFirstLengthInvalid);
                boolean unused = AutoSelectHorizontalView.this.isInit = false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void onDataChanged() {
        selectedPositionChanged(this.mSelectPosition);
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            int currX = this.mScroller.getCurrX();
            int i = this.mLastMoveX;
            int i2 = currX - i;
            this.mLastMoveX = i + i2;
            scrollBy(i2, 0);
        } else if (this.mScroller.isFinished()) {
            this.isFling = false;
            if (!this.isMoveFinished) {
                calculateSelectedPos();
                selectedPositionChanged(this.mSelectPosition);
                this.isMoveFinished = true;
                this.isClickMove.set(false);
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView
    public boolean fling(int i, int i2) {
        this.isFling = true;
        smoothMoveToPosition(i);
        return false;
    }

    public int getItemWidthMargin() {
        return this.itemWidthMargin;
    }

    public void moveMiddlePositionChanged(int i, boolean z) {
        AutoSelectAdapter autoSelectAdapter = this.mAutoSelectAdapter;
        if (autoSelectAdapter != null) {
            autoSelectAdapter.onMoveMiddlePoisionChanged(i, z);
        }
    }

    public void moveToPosition(int i) {
        if (i < 0) {
            i = 0;
        }
        if (this.mSelectPosition < 0) {
            this.mSelectPosition = 0;
        }
        if (i >= this.mAutoSelectAdapter.getItemCount()) {
            i = this.mAutoSelectAdapter.getItemCount() - 1;
        }
        this.isClickMove.set(true);
        this.mLastMoveX = 0;
        this.isMoveFinished = false;
        if (i != this.mSelectPosition) {
            SelectItemBean selectItemBean = (SelectItemBean) this.mAutoSelectAdapter.getDataList().get(this.mSelectPosition);
            SelectItemBean selectItemBean2 = (SelectItemBean) this.mAutoSelectAdapter.getDataList().get(i);
            int curTotalLength = (int) ((((selectItemBean2.getCurTotalLength() + ((float) (i * this.itemWidthMargin))) - (selectItemBean.getCurTotalLength() + ((float) (this.mSelectPosition * this.itemWidthMargin)))) + (selectItemBean.getCurLength() / 2.0f)) - (selectItemBean2.getCurLength() / 2.0f));
            if (Util.isLayoutRTL(getContext())) {
                curTotalLength = -curTotalLength;
            }
            this.mScroller.startScroll(getScrollX(), getScrollY(), curTotalLength, 0, 250);
            postInvalidate();
        }
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.widget.RecyclerView
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAutoSelectAdapter != null) {
            this.mAutoSelectAdapter = null;
        }
        WrapperAdapter wrapperAdapter = this.mWrapAdapter;
        if (wrapperAdapter != null) {
            Context unused = wrapperAdapter.context = null;
            this.mWrapAdapter = null;
        }
    }

    @Override // android.support.v7.widget.RecyclerView
    public void onScrollStateChanged(int i) {
        super.onScrollStateChanged(i);
        if (i == 0 && this.mWrapAdapter != null) {
            calculateSelectedPos();
            SelectItemBean selectItemBean = (SelectItemBean) this.mAutoSelectAdapter.getDataList().get(this.mSelectPosition);
            int curTotalLength = (int) (((((selectItemBean.getCurTotalLength() + ((float) (this.itemWidthMargin * this.mSelectPosition))) - (selectItemBean.getCurLength() / 2.0f)) - ((float) this.itemFirstLengthInvalid)) * ((float) (Util.isLayoutRTL(getContext()) ? -1 : 1))) - ((float) this.mDeltaX));
            if (Math.abs(curTotalLength) == 0) {
                selectedPositionChanged(this.mSelectPosition);
                this.isClickMove.set(false);
            } else if (!this.isFling) {
                this.isMoveFinished = false;
                this.mScroller.startScroll(getScrollX(), getScrollY(), curTotalLength, 0, 200);
                postInvalidate();
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView
    public void onScrolled(int i, int i2) {
        int i3;
        super.onScrolled(i, i2);
        this.mTempSelectPosition = this.mSelectPosition;
        this.mDeltaX += i;
        calculateSelectedPos();
        if (!this.isClickMove.get() && this.mTempSelectPosition != (i3 = this.mSelectPosition)) {
            moveMiddlePositionChanged(i3, true);
        }
    }

    public void selectedPositionChanged(int i) {
        AutoSelectAdapter autoSelectAdapter = this.mAutoSelectAdapter;
        if (autoSelectAdapter != null) {
            autoSelectAdapter.onSelectedPositionChanged(i);
        }
    }

    @Override // android.support.v7.widget.RecyclerView
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (!(adapter instanceof AutoSelectAdapter)) {
            Log.e(AutoSelectHorizontalView.class.getSimpleName(), "mAutoSelectAdapter must extends AutoSelectAdapter<T extends SelectItemBean> ");
            return;
        }
        this.mAutoSelectAdapter = (AutoSelectAdapter) adapter;
        this.mWrapAdapter = new WrapperAdapter(adapter, getContext());
        this.mWrapAdapter.setHasStableIds(true);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            /* class com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectHorizontalView.AnonymousClass2 */

            @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                AutoSelectHorizontalView.this.mWrapAdapter.notifyDataSetChanged();
                AutoSelectHorizontalView.this.onDataChanged();
            }

            @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i, int i2) {
                super.onItemRangeChanged(i, i2);
                AutoSelectHorizontalView.this.mWrapAdapter.notifyItemRangeChanged(i + 1, i2);
            }

            @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int i, int i2) {
                AutoSelectHorizontalView.this.mWrapAdapter.notifyDataSetChanged();
            }

            @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int i, int i2) {
                AutoSelectHorizontalView.this.mWrapAdapter.notifyDataSetChanged();
            }
        });
        this.mDeltaX = 0;
        this.itemFirstLengthInvalid = (int) (((SelectItemBean) this.mAutoSelectAdapter.getDataList().get(0)).getCurLength() / 2.0f);
        this.itemWidthMargin = getResources().getDimensionPixelSize(R.dimen.mimoji_edit_type_padding_right);
        if (this.mLinearLayoutManager == null) {
            this.mLinearLayoutManager = new LinearLayoutManagerWrapper(getContext(), "autoselect");
        }
        this.mLinearLayoutManager.setOrientation(0);
        super.setLayoutManager(this.mLinearLayoutManager);
        super.setAdapter(this.mWrapAdapter);
        this.isInit = true;
    }

    @Override // android.support.v7.widget.RecyclerView
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof LinearLayoutManagerWrapper)) {
            Log.e("View", "The LayoutManager here must be LinearLayoutManager!");
        } else {
            this.mLinearLayoutManager = (LinearLayoutManagerWrapper) layoutManager;
        }
    }

    public void smoothMoveToPosition(int i) {
        if (this.mAutoSelectAdapter != null && this.mWrapAdapter != null) {
            this.isMoveFinished = false;
            this.mLastMoveX = 0;
            calculateSelectedPos();
            boolean isLayoutRTL = Util.isLayoutRTL(getContext());
            int i2 = -1;
            int i3 = ((i / 1000) * (isLayoutRTL ? -1 : 1)) + this.mSelectPosition;
            if (i3 < 0) {
                i3 = 0;
            }
            if (i3 >= this.mAutoSelectAdapter.getItemCount()) {
                i3 = this.mAutoSelectAdapter.getItemCount() - 1;
            }
            SelectItemBean selectItemBean = (SelectItemBean) this.mAutoSelectAdapter.getDataList().get(i3);
            float curTotalLength = ((selectItemBean.getCurTotalLength() + ((float) (this.itemWidthMargin * i3))) - (selectItemBean.getCurLength() / 2.0f)) - ((float) this.itemFirstLengthInvalid);
            if (!isLayoutRTL) {
                i2 = 1;
            }
            this.mScroller.startScroll(getScrollX(), getScrollY(), (int) ((curTotalLength * ((float) i2)) - ((float) this.mDeltaX)), 0, 500);
            postInvalidate();
        }
    }
}
