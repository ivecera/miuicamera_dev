package com.android.camera.fragment.beauty;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ScrollTextview;
import d.h.a.m;
import java.util.List;

public class LiveBeautyFilterFragment extends Fragment implements View.OnClickListener {
    public static final int LIVE_FILTER_NONE_ID = 0;
    private static final String TAG = "LiveBeautyFilterFragment";
    /* access modifiers changed from: private */
    public boolean isAnimation = false;
    /* access modifiers changed from: private */
    public m mCubicEaseOut;
    /* access modifiers changed from: private */
    public int mCurrentIndex = 0;
    private FilterItemAdapter mFilterItemAdapter;
    /* access modifiers changed from: private */
    public List<LiveFilterItem> mFilters;
    private int mHolderWidth;
    /* access modifiers changed from: private */
    public int mLastIndex = -1;
    private LinearLayoutManagerWrapper mLayoutManager;
    private RecyclerView mRecyclerView;
    private int mTotalWidth;
    private View mView;

    protected class EffectItemPadding extends RecyclerView.ItemDecoration {
        protected int mEffectListLeft;
        protected int mHorizontalPadding;
        protected int mVerticalPadding;

        public EffectItemPadding() {
            this.mHorizontalPadding = LiveBeautyFilterFragment.this.getContext().getResources().getDimensionPixelSize(R.dimen.effect_item_padding_horizontal);
            this.mVerticalPadding = LiveBeautyFilterFragment.this.getContext().getResources().getDimensionPixelSize(R.dimen.effect_item_padding_vertical);
            this.mEffectListLeft = LiveBeautyFilterFragment.this.getContext().getResources().getDimensionPixelSize(R.dimen.effect_list_padding_left);
        }

        @Override // android.support.v7.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int i = recyclerView.getChildPosition(view) == 0 ? this.mEffectListLeft : 0;
            int i2 = this.mVerticalPadding;
            rect.set(i, i2, this.mHorizontalPadding, i2);
        }
    }

    protected class FilterItemAdapter extends RecyclerView.Adapter {
        protected LayoutInflater mLayoutInflater;

        public FilterItemAdapter(Context context) {
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return LiveBeautyFilterFragment.this.mFilters.size();
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            FilterItemHolder filterItemHolder = (FilterItemHolder) viewHolder;
            ((RecyclerView.ViewHolder) filterItemHolder).itemView.setTag(Integer.valueOf(i));
            filterItemHolder.bindEffectIndex(i, (LiveFilterItem) LiveBeautyFilterFragment.this.mFilters.get(i));
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View inflate = this.mLayoutInflater.inflate(R.layout.live_filter_item, viewGroup, false);
            FilterStillItemHolder filterStillItemHolder = new FilterStillItemHolder(inflate);
            inflate.setOnClickListener(LiveBeautyFilterFragment.this);
            return filterStillItemHolder;
        }
    }

    protected abstract class FilterItemHolder extends RecyclerView.ViewHolder {
        protected int mEffectIndex;
        protected ScrollTextview mTextView;

        public FilterItemHolder(View view) {
            super(view);
            this.mTextView = (ScrollTextview) view.findViewById(R.id.effect_item_text);
        }

        public void bindEffectIndex(int i, LiveFilterItem liveFilterItem) {
            this.mEffectIndex = i;
            this.mTextView.setText(liveFilterItem.name);
        }
    }

    protected class FilterStillItemHolder extends FilterItemHolder {
        private ImageView mImageView;
        private ImageView mSelectedOuterIndicator;

        public FilterStillItemHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.effect_item_image);
            this.mSelectedOuterIndicator = (ImageView) view.findViewById(R.id.effect_item_selected_indicator);
        }

        private void normalAnim(View view) {
            ViewCompat.setAlpha(view, 1.0f);
            ViewCompat.animate(view).setDuration(500).alpha(0.0f).setInterpolator(LiveBeautyFilterFragment.this.mCubicEaseOut).setListener(new ViewPropertyAnimatorListener() {
                /* class com.android.camera.fragment.beauty.LiveBeautyFilterFragment.FilterStillItemHolder.AnonymousClass2 */

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationCancel(View view) {
                }

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view) {
                    view.setVisibility(8);
                }

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationStart(View view) {
                }
            }).start();
        }

        private void selectAnim(View view) {
            ViewCompat.setAlpha(this.mSelectedOuterIndicator, 0.0f);
            ViewCompat.animate(view).setDuration(500).alpha(1.0f).setInterpolator(LiveBeautyFilterFragment.this.mCubicEaseOut).setListener(new ViewPropertyAnimatorListener() {
                /* class com.android.camera.fragment.beauty.LiveBeautyFilterFragment.FilterStillItemHolder.AnonymousClass3 */

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationCancel(View view) {
                }

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view) {
                }

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationStart(View view) {
                    view.setVisibility(0);
                }
            }).start();
        }

        @Override // com.android.camera.fragment.beauty.LiveBeautyFilterFragment.FilterItemHolder
        public void bindEffectIndex(int i, LiveFilterItem liveFilterItem) {
            super.bindEffectIndex(i, liveFilterItem);
            this.mImageView.setImageDrawable(liveFilterItem.imageViewRes);
            if (i == LiveBeautyFilterFragment.this.mCurrentIndex) {
                ((RecyclerView.ViewHolder) this).itemView.setActivated(true);
                if (Util.isAccessible() || Util.isSetContentDesc()) {
                    ((RecyclerView.ViewHolder) this).itemView.postDelayed(new Runnable() {
                        /* class com.android.camera.fragment.beauty.LiveBeautyFilterFragment.FilterStillItemHolder.AnonymousClass1 */

                        public void run() {
                            if (LiveBeautyFilterFragment.this.isAdded()) {
                                ((RecyclerView.ViewHolder) FilterStillItemHolder.this).itemView.sendAccessibilityEvent(128);
                            }
                        }
                    }, 100);
                }
                if (LiveBeautyFilterFragment.this.isAnimation) {
                    selectAnim(this.mSelectedOuterIndicator);
                    return;
                }
                this.mSelectedOuterIndicator.setVisibility(0);
                this.mSelectedOuterIndicator.setAlpha(1.0f);
                return;
            }
            ((RecyclerView.ViewHolder) this).itemView.setActivated(false);
            if (!LiveBeautyFilterFragment.this.isAnimation || i != LiveBeautyFilterFragment.this.mLastIndex) {
                this.mSelectedOuterIndicator.setVisibility(8);
                this.mSelectedOuterIndicator.setAlpha(0.0f);
                return;
            }
            normalAnim(this.mSelectedOuterIndicator);
        }
    }

    public static class LiveFilterItem {
        public String directoryName;
        public int id;
        public Drawable imageViewRes;
        public String name;
    }

    private int findIndex(int i) {
        for (int i2 = 0; i2 < this.mFilters.size(); i2++) {
            if (this.mFilters.get(i2).id == i) {
                return i2;
            }
        }
        return 0;
    }

    private void initData() {
        this.mFilters = EffectController.getInstance().getLiveFilterList(getContext());
        this.mCurrentIndex = findIndex(DataRepository.dataItemLive().getLiveFilter());
    }

    private void initView() {
        this.mRecyclerView = (RecyclerView) this.mView.findViewById(R.id.effect_list);
        this.mRecyclerView.setFocusable(false);
        this.mCubicEaseOut = new m();
        this.mFilterItemAdapter = new FilterItemAdapter(getContext());
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), "effect_list");
        this.mLayoutManager.setOrientation(0);
        this.mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, EffectController.getInstance().getEffectCount(1));
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.addItemDecoration(new EffectItemPadding());
        this.mRecyclerView.setAdapter(this.mFilterItemAdapter);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /* class com.android.camera.fragment.beauty.LiveBeautyFilterFragment.AnonymousClass1 */

            @Override // android.support.v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                boolean unused = LiveBeautyFilterFragment.this.isAnimation = false;
            }
        });
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mRecyclerView.setItemAnimator(defaultItemAnimator);
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        this.mHolderWidth = getResources().getDimensionPixelSize(R.dimen.effect_item_width);
    }

    private void notifyItemChanged(int i, int i2) {
        if (i > -1) {
            this.mFilterItemAdapter.notifyItemChanged(i);
        }
        if (i2 > -1) {
            this.mFilterItemAdapter.notifyItemChanged(i2);
        }
    }

    private void onItemSelected(int i, boolean z) {
        String str = TAG;
        Log.d(str, "onItemSelected: index = " + i + ", fromClick = " + z);
        if (((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) == null) {
            Log.e(TAG, "onItemSelected: configChanges = null");
            return;
        }
        try {
            selectItem(i);
        } catch (NumberFormatException e2) {
            String str2 = TAG;
            Log.e(str2, "invalid filter id: " + e2.getMessage());
        }
    }

    private void scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
        } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mFilterItemAdapter.getItemCount() - 1));
        }
    }

    private void selectItem(int i) {
        if (i != -1) {
            this.mLastIndex = this.mCurrentIndex;
            this.mCurrentIndex = i;
            scrollIfNeed(i);
            notifyItemChanged(this.mLastIndex, this.mCurrentIndex);
        }
    }

    private void showSelected(ImageView imageView, int i) {
        float dimension = getResources().getDimension(R.dimen.live_filter_item_mask_size);
        float dimension2 = getResources().getDimension(R.dimen.live_filter_item_corners_size);
        int i2 = (int) dimension;
        Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawRoundRect(new RectF(4.0f, 4.0f, dimension, dimension), dimension2, dimension2, paint);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), i);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap.getWidth(), createBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        Paint paint2 = new Paint();
        canvas2.drawBitmap(createBitmap, 0.0f, 0.0f, paint2);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas2.drawBitmap(decodeResource, 0.0f, 0.0f, paint2);
        paint2.setXfermode(null);
        imageView.setImageBitmap(createBitmap2);
    }

    public void onClick(View view) {
        int intValue;
        if (this.mRecyclerView.isEnabled()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if ((cameraAction == null || !cameraAction.isDoingAction()) && this.mCurrentIndex != (intValue = ((Integer) view.getTag()).intValue())) {
                this.isAnimation = false;
                ModeProtocol.LiveFilterChangers liveFilterChangers = (ModeProtocol.LiveFilterChangers) ModeCoordinatorImpl.getInstance().getAttachProtocol(243);
                if (liveFilterChangers != null) {
                    String str = TAG;
                    Log.e(str, "filter_path:" + this.mFilters.get(intValue).directoryName);
                    if (intValue != 0) {
                        liveFilterChangers.setFilter(true, this.mFilters.get(intValue).directoryName);
                    } else {
                        liveFilterChangers.setFilter(false, null);
                    }
                    DataRepository.dataItemLive().setLiveFilter(this.mFilters.get(intValue).id);
                }
                onItemSelected(intValue, true);
            }
        }
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        if (this.mView == null) {
            this.mView = LayoutInflater.from(getContext()).inflate(R.layout.live_fragment_filter, viewGroup, false);
            initView();
            initData();
        }
        return this.mView;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        int i = (this.mTotalWidth / 2) - (this.mHolderWidth / 2);
        this.mFilterItemAdapter.notifyDataSetChanged();
        this.mLayoutManager.scrollToPositionWithOffset(this.mCurrentIndex, i);
    }
}
