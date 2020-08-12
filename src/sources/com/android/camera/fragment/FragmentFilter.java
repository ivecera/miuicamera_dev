package com.android.camera.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.config.ComponentConfigFilter;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.beauty.BaseBeautyFragment;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.ui.ScrollTextview;
import d.h.a.m;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;

public class FragmentFilter extends BaseBeautyFragment implements View.OnClickListener {
    public static final int FRAGMENT_INFO = 250;
    private static final String TAG = "FragmentFilter";
    /* access modifiers changed from: private */
    public boolean isAnimation = false;
    private ComponentConfigFilter mComponentConfigFilter;
    /* access modifiers changed from: private */
    public m mCubicEaseOut;
    /* access modifiers changed from: private */
    public int mCurrentIndex = -1;
    private int mCurrentMode;
    private EffectItemAdapter mEffectItemAdapter;
    private EffectItemPadding mEffectItemPadding;
    private int mHolderWidth;
    private boolean mIgnoreSameItemClick = true;
    private int mIsShowIndex = -1;
    /* access modifiers changed from: private */
    public int mLastIndex = -1;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private int mTotalWidth;

    protected class EffectItemAdapter extends RecyclerView.Adapter {
        protected ComponentConfigFilter mFilters;
        protected LayoutInflater mLayoutInflater;

        public EffectItemAdapter(Context context, ComponentConfigFilter componentConfigFilter) {
            this.mFilters = componentConfigFilter;
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mFilters.getItems().size();
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            EffectItemHolder effectItemHolder = (EffectItemHolder) viewHolder;
            ((RecyclerView.ViewHolder) effectItemHolder).itemView.setTag(Integer.valueOf(i));
            effectItemHolder.bindEffectIndex(i, this.mFilters.getItems().get(i));
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View inflate = this.mLayoutInflater.inflate(R.layout.effect_still_item, viewGroup, false);
            EffectStillItemHolder effectStillItemHolder = new EffectStillItemHolder(inflate);
            inflate.setOnClickListener(FragmentFilter.this);
            return effectStillItemHolder;
        }

        public void updateData(ComponentConfigFilter componentConfigFilter) {
            this.mFilters = componentConfigFilter;
            notifyDataSetChanged();
        }
    }

    private static abstract class EffectItemHolder extends RecyclerView.ViewHolder {
        protected int mEffectIndex;
        protected ScrollTextview mTextView;

        public EffectItemHolder(View view) {
            super(view);
            this.mTextView = (ScrollTextview) view.findViewById(R.id.effect_item_text);
        }

        public void bindEffectIndex(int i, ComponentDataItem componentDataItem) {
            this.mEffectIndex = getRenderId(i, componentDataItem);
            this.mTextView.setText(componentDataItem.mDisplayNameRes);
        }

        /* access modifiers changed from: protected */
        public int getRenderId(int i, ComponentDataItem componentDataItem) {
            return Integer.parseInt(componentDataItem.mValue);
        }
    }

    private static class EffectItemPadding extends RecyclerView.ItemDecoration {
        protected int mEffectListLeft;
        protected int mHorizontalPadding;
        protected int mVerticalPadding;

        public EffectItemPadding(Context context) {
            this.mHorizontalPadding = context.getResources().getDimensionPixelSize(R.dimen.effect_item_padding_horizontal);
            this.mVerticalPadding = context.getResources().getDimensionPixelSize(R.dimen.effect_item_padding_vertical);
            this.mEffectListLeft = context.getResources().getDimensionPixelSize(R.dimen.effect_list_padding_left);
        }

        @Override // android.support.v7.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int i = recyclerView.getChildPosition(view) == 0 ? this.mEffectListLeft : 0;
            int i2 = this.mVerticalPadding;
            rect.set(i, i2, this.mHorizontalPadding, i2);
        }
    }

    private class EffectStillItemHolder extends EffectItemHolder {
        private ImageView mImageView;
        /* access modifiers changed from: private */
        public ImageView mSelectedIndicator;

        public EffectStillItemHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.effect_item_image);
            this.mSelectedIndicator = (ImageView) view.findViewById(R.id.effect_item_selected_indicator);
        }

        @Override // com.android.camera.fragment.FragmentFilter.EffectItemHolder
        public void bindEffectIndex(int i, ComponentDataItem componentDataItem) {
            super.bindEffectIndex(i, componentDataItem);
            this.mImageView.setImageResource(componentDataItem.mIconRes);
            if (i == FragmentFilter.this.mCurrentIndex) {
                ((RecyclerView.ViewHolder) this).itemView.setActivated(true);
                FragmentFilter.this.showSelected(this.mImageView, componentDataItem.mIconRes);
                if (Util.isAccessible() || Util.isSetContentDesc()) {
                    View view = ((RecyclerView.ViewHolder) this).itemView;
                    view.setContentDescription(FragmentFilter.this.getContext().getString(componentDataItem.mDisplayNameRes) + FragmentFilter.this.getString(R.string.accessibility_selected));
                    ((RecyclerView.ViewHolder) this).itemView.postDelayed(new Runnable() {
                        /* class com.android.camera.fragment.FragmentFilter.EffectStillItemHolder.AnonymousClass1 */

                        public void run() {
                            if (FragmentFilter.this.isAdded()) {
                                ((RecyclerView.ViewHolder) EffectStillItemHolder.this).itemView.sendAccessibilityEvent(128);
                            }
                        }
                    }, 100);
                }
                if (FragmentFilter.this.isAnimation) {
                    ViewCompat.setAlpha(this.mSelectedIndicator, 0.0f);
                    ViewCompat.animate(this.mSelectedIndicator).setDuration(500).alpha(1.0f).setInterpolator(FragmentFilter.this.mCubicEaseOut).setListener(new ViewPropertyAnimatorListener() {
                        /* class com.android.camera.fragment.FragmentFilter.EffectStillItemHolder.AnonymousClass2 */

                        @Override // android.support.v4.view.ViewPropertyAnimatorListener
                        public void onAnimationCancel(View view) {
                        }

                        @Override // android.support.v4.view.ViewPropertyAnimatorListener
                        public void onAnimationEnd(View view) {
                        }

                        @Override // android.support.v4.view.ViewPropertyAnimatorListener
                        public void onAnimationStart(View view) {
                            EffectStillItemHolder.this.mSelectedIndicator.setVisibility(0);
                        }
                    }).start();
                    return;
                }
                this.mSelectedIndicator.setVisibility(0);
                this.mSelectedIndicator.setAlpha(1.0f);
                return;
            }
            ((RecyclerView.ViewHolder) this).itemView.setActivated(false);
            if (!FragmentFilter.this.isAnimation || i != FragmentFilter.this.mLastIndex) {
                this.mSelectedIndicator.setVisibility(8);
                this.mSelectedIndicator.setAlpha(0.0f);
                return;
            }
            ViewCompat.setAlpha(this.mSelectedIndicator, 1.0f);
            ViewCompat.animate(this.mSelectedIndicator).setDuration(500).alpha(0.0f).setInterpolator(FragmentFilter.this.mCubicEaseOut).setListener(new ViewPropertyAnimatorListener() {
                /* class com.android.camera.fragment.FragmentFilter.EffectStillItemHolder.AnonymousClass3 */

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationCancel(View view) {
                }

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view) {
                    EffectStillItemHolder.this.mSelectedIndicator.setVisibility(8);
                }

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationStart(View view) {
                }
            }).start();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
        if (com.android.camera.CameraSettings.isFrontCamera() != false) goto L_0x002c;
     */
    private ArrayList<FilterInfo> getFilterInfo() {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        int i = 2;
        if (currentMode != 165) {
            if (currentMode != 171) {
                if (currentMode != 180) {
                    if (currentMode != 183) {
                        switch (currentMode) {
                            case 161:
                                i = 3;
                                break;
                            case 162:
                                break;
                            case 163:
                                break;
                            default:
                                i = 1;
                                break;
                        }
                    } else {
                        i = 8;
                    }
                }
                i = 7;
            }
            return EffectController.getInstance().getFilterInfo(i);
        }
    }

    private void initView(View view) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.effect_list);
        this.mRecyclerView.setFocusable(false);
        ArrayList<FilterInfo> filterInfo = getFilterInfo();
        this.mComponentConfigFilter = DataRepository.dataItemRunning().getComponentConfigFilter();
        this.mComponentConfigFilter.mapToItems(filterInfo, this.mCurrentMode);
        Context context = getContext();
        this.mTotalWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mHolderWidth = context.getResources().getDimensionPixelSize(R.dimen.effect_item_width);
        this.mEffectItemAdapter = new EffectItemAdapter(context, this.mComponentConfigFilter);
        this.mLayoutManager = new LinearLayoutManagerWrapper(context, "effect_list");
        this.mLayoutManager.setOrientation(0);
        this.mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, EffectController.getInstance().getEffectCount(1));
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        if (this.mEffectItemPadding == null) {
            this.mEffectItemPadding = new EffectItemPadding(getContext());
            this.mRecyclerView.addItemDecoration(this.mEffectItemPadding);
        }
        this.mRecyclerView.setAdapter(this.mEffectItemAdapter);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /* class com.android.camera.fragment.FragmentFilter.AnonymousClass1 */

            @Override // android.support.v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                boolean unused = FragmentFilter.this.isAnimation = false;
            }
        });
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mRecyclerView.setItemAnimator(defaultItemAnimator);
        this.mCubicEaseOut = new m();
    }

    private void notifyItemChanged(int i, int i2) {
        if (i > -1) {
            this.mEffectItemAdapter.notifyItemChanged(i);
        }
        if (i2 > -1) {
            this.mEffectItemAdapter.notifyItemChanged(i2);
        }
    }

    private void onItemSelected(int i, boolean z) {
        String str = TAG;
        Log.d(str, "onItemSelected: index = " + i + ", fromClick = " + z + ", mCurrentMode = " + this.mCurrentMode + ", DataRepository.dataItemGlobal().getCurrentMode() = " + DataRepository.dataItemGlobal().getCurrentMode());
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges == null) {
            Log.e(TAG, "onItemSelected: configChanges = null");
            return;
        }
        try {
            String str2 = this.mComponentConfigFilter.getItems().get(i).mValue;
            this.mComponentConfigFilter.setClosed(false, DataRepository.dataItemGlobal().getCurrentMode());
            int intValue = Integer.valueOf(str2).intValue();
            CameraStatUtils.trackFilterChanged(intValue, z);
            selectItem(i);
            configChanges.setFilter(intValue);
        } catch (NumberFormatException e2) {
            String str3 = TAG;
            Log.e(str3, "invalid filter id: " + e2.getMessage());
        }
    }

    private void scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
        } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mEffectItemAdapter.getItemCount() - 1));
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

    private void setItemInCenter(int i) {
        this.mCurrentIndex = i;
        this.mIsShowIndex = i;
        int i2 = (this.mTotalWidth / 2) - (this.mHolderWidth / 2);
        this.mEffectItemAdapter.notifyDataSetChanged();
        this.mLayoutManager.scrollToPositionWithOffset(i, i2);
    }

    /* access modifiers changed from: private */
    public void showSelected(ImageView imageView, int i) {
        if (isAdded()) {
            Canvas canvas = new Canvas();
            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.filter_item_selected_view);
            Bitmap decodeResource2 = BitmapFactory.decodeResource(getResources(), i);
            Bitmap createBitmap = Bitmap.createBitmap(decodeResource.getWidth(), decodeResource.getHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(createBitmap);
            Paint paint = new Paint();
            paint.setFilterBitmap(false);
            canvas.drawBitmap(decodeResource, 0.0f, 0.0f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(decodeResource2, 0.0f, 0.0f, paint);
            paint.setXfermode(null);
            imageView.setImageBitmap(createBitmap);
        }
    }

    private void updateCurrentIndex() {
        CameraSettings.getShaderEffect();
        String componentValue = this.mComponentConfigFilter.getComponentValue(DataRepository.dataItemGlobal().getCurrentMode());
        int findIndexOfValue = this.mComponentConfigFilter.findIndexOfValue(componentValue);
        if (findIndexOfValue == -1) {
            String str = TAG;
            Log.w(str, "invalid filter " + componentValue);
            findIndexOfValue = 0;
        }
        setItemInCenter(findIndexOfValue);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public View getAnimateView() {
        return this.mRecyclerView;
    }

    public void isShowAnimation(List<Completable> list) {
        if (list == null) {
            this.isAnimation = false;
        } else {
            this.isAnimation = true;
        }
    }

    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        if (this.mRecyclerView.isEnabled()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                int intValue = ((Integer) view.getTag()).intValue();
                if (this.mCurrentIndex != intValue || !this.mIgnoreSameItemClick) {
                    this.isAnimation = false;
                    onItemSelected(intValue, true);
                }
            }
        }
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        this.mCurrentMode = DataRepository.dataItemGlobal().getCurrentMode();
        View inflate = layoutInflater.inflate(R.layout.fragment_filter, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment, com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        updateCurrentIndex();
    }

    public void reInit() {
        setItemInCenter(this.mComponentConfigFilter.findIndexOfValue(this.mComponentConfigFilter.getComponentValue(this.mCurrentMode)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    public void switchFilter(int i) {
        int i2;
        if (i == 3) {
            int i3 = this.mCurrentIndex;
            if (i3 > 0) {
                i2 = i3 - 1;
                if (i2 > -1) {
                }
            }
        } else if (i != 5) {
            Log.e(TAG, "unexpected gravity " + i);
        } else if (this.mCurrentIndex < this.mComponentConfigFilter.getItems().size() - 1) {
            i2 = this.mCurrentIndex + 1;
            if (i2 > -1) {
                onItemSelected(i2, false);
                return;
            }
            return;
        }
        i2 = -1;
        if (i2 > -1) {
        }
    }

    public void updateFilterData() {
        ArrayList<FilterInfo> filterInfo = getFilterInfo();
        this.mComponentConfigFilter = DataRepository.dataItemRunning().getComponentConfigFilter();
        this.mComponentConfigFilter.mapToItems(filterInfo, this.mCurrentMode);
        this.mEffectItemAdapter.updateData(this.mComponentConfigFilter);
        updateCurrentIndex();
    }
}
