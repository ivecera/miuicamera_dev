package com.android.camera.fragment.bottom.action;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.ComponentRunningLighting;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ColorImageView;

public class FragmentActionLighting extends BaseFragment implements View.OnClickListener {
    private ComponentRunningLighting mComponentRunningLighting;
    private int mCurrentIndex = 0;
    private int mHolderWidth;
    private int mLastIndex = 0;
    private LinearLayoutManagerWrapper mLayoutManager;
    private LightingAdapter mLightingAdapter;
    private RecyclerView mRecyclerView;
    private int mTotalWidth;

    private static class LightingAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
        private int mBgAlpha = 255;
        private Drawable mBgNormal;
        private Drawable mBgSelected;
        private ComponentRunningLighting mComponentRunningLighting;
        private String[] mContent;
        private int mCount;
        private int mCurrentMode;
        private int mMargin;
        private View.OnClickListener mOnClickListener;

        public LightingAdapter(Context context, int i, View.OnClickListener onClickListener, ComponentRunningLighting componentRunningLighting) {
            this.mCurrentMode = i;
            this.mOnClickListener = onClickListener;
            this.mComponentRunningLighting = componentRunningLighting;
            this.mCount = this.mComponentRunningLighting.getItems().size();
            this.mMargin = context.getResources().getDimensionPixelSize(R.dimen.lighting_item_margin);
            this.mBgNormal = context.getResources().getDrawable(R.drawable.bg_lighting_normal);
            this.mBgSelected = context.getResources().getDrawable(R.drawable.bg_lighting_selected);
            updateContent(context);
        }

        private void updateContent(Context context) {
            if (Util.isAccessible() || Util.isSetContentDesc()) {
                this.mContent = new String[this.mCount];
                for (int i = 0; i < this.mCount; i++) {
                    this.mContent[i] = context.getString(this.mComponentRunningLighting.getItems().get(i).mDisplayNameRes);
                }
            }
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mCount;
        }

        public void onBindViewHolder(CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getLayoutParams();
            ColorImageView colorImageView = (ColorImageView) commonRecyclerViewHolder.getView(R.id.lighting_item_base);
            ColorImageView colorImageView2 = (ColorImageView) commonRecyclerViewHolder.getView(R.id.lighting_item_image);
            String componentValue = this.mComponentRunningLighting.getComponentValue(this.mCurrentMode);
            ComponentDataItem componentDataItem = this.mComponentRunningLighting.getItems().get(i);
            if (Util.isAccessible() || Util.isSetContentDesc()) {
                colorImageView2.setContentDescription(this.mContent[i]);
            }
            if (componentValue.equals(componentDataItem.mValue)) {
                colorImageView.setBackground(this.mBgSelected);
                colorImageView2.setImageResource(componentDataItem.mIconSelectedRes);
            } else {
                colorImageView.setBackground(this.mBgNormal);
                colorImageView2.setImageResource(componentDataItem.mIconRes);
            }
            marginLayoutParams.setMarginStart(this.mMargin);
            if (i < getItemCount() - 1) {
                marginLayoutParams.setMarginEnd(0);
            } else {
                marginLayoutParams.setMarginEnd(this.mMargin);
            }
            colorImageView.setOnClickListener(this.mOnClickListener);
            colorImageView.setTag(Integer.valueOf(i));
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new CommonRecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_lighting_item, viewGroup, false));
        }

        public void setBgMode(boolean z) {
            if (z) {
                this.mBgAlpha = 102;
            } else {
                this.mBgAlpha = 255;
            }
            this.mBgNormal.setAlpha(this.mBgAlpha);
        }

        public void updateLightingData(Context context, ComponentRunningLighting componentRunningLighting) {
            this.mComponentRunningLighting = componentRunningLighting;
            this.mCount = this.mComponentRunningLighting.getItems().size();
            updateContent(context);
            notifyDataSetChanged();
        }
    }

    private void notifyItemChanged(int i, int i2) {
        if (i > -1) {
            this.mLightingAdapter.notifyItemChanged(i);
        }
        if (i2 > -1) {
            this.mLightingAdapter.notifyItemChanged(i2);
        }
    }

    private void onItemSelected(int i, boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            String componentValue = this.mComponentRunningLighting.getComponentValue(((BaseFragment) this).mCurrentMode);
            String str = this.mComponentRunningLighting.getItems().get(i).mValue;
            if (!componentValue.equals(str)) {
                this.mComponentRunningLighting.setComponentValue(((BaseFragment) this).mCurrentMode, str);
                configChanges.setLighting(false, componentValue, str, true);
                this.mLastIndex = this.mCurrentIndex;
                this.mCurrentIndex = i;
                scrollIfNeed(i);
                notifyItemChanged(this.mLastIndex, this.mCurrentIndex);
            }
        }
    }

    private void scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
        } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mLightingAdapter.getItemCount() - 1));
        }
    }

    private void setItemInCenter(int i) {
        this.mCurrentIndex = i;
        this.mLastIndex = i;
        int i2 = (this.mTotalWidth / 2) - (this.mHolderWidth / 2);
        this.mLightingAdapter.notifyDataSetChanged();
        this.mLayoutManager.scrollToPositionWithOffset(i, i2);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_LIGHTING;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_bottom_action_lighting;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.lighting_list);
        this.mRecyclerView.setFocusable(false);
        this.mComponentRunningLighting = DataRepository.dataItemRunning().getComponentRunningLighting();
        this.mLightingAdapter = new LightingAdapter(getContext(), ((BaseFragment) this).mCurrentMode, this, this.mComponentRunningLighting);
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), 0, false, "lighting_list");
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mLightingAdapter);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mRecyclerView.setItemAnimator(defaultItemAnimator);
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        this.mHolderWidth = getResources().getDimensionPixelSize(R.dimen.lighting_item_height);
        try {
            this.mCurrentIndex = Integer.valueOf(this.mComponentRunningLighting.getComponentValue(((BaseFragment) this).mCurrentMode)).intValue();
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
        reInitAdapterBgMode(false);
    }

    public void onClick(View view) {
        if (isEnableClick()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                onItemSelected(((Integer) view.getTag()).intValue(), true);
            }
        }
    }

    public void reInit() {
        reInitAdapterBgMode(false);
        setItemInCenter(this.mComponentRunningLighting.findIndexOfValue(this.mComponentRunningLighting.getComponentValue(((BaseFragment) this).mCurrentMode)));
    }

    public void reInitAdapterBgMode(boolean z) {
        if (DataRepository.dataItemRunning().getUiStyle() != 0) {
            this.mLightingAdapter.setBgMode(true);
        } else {
            this.mLightingAdapter.setBgMode(false);
        }
        if (z) {
            this.mComponentRunningLighting = DataRepository.dataItemRunning().getComponentRunningLighting();
            this.mComponentRunningLighting.initItems();
            this.mLightingAdapter.updateLightingData(getContext(), this.mComponentRunningLighting);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0027  */
    /* JADX WARNING: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    public void switchLighting(int i) {
        int i2;
        if (i == 3) {
            int i3 = this.mCurrentIndex;
            if (i3 > 0) {
                i2 = i3 - 1;
                if (i2 > -1) {
                }
            }
        } else if (i == 5 && this.mCurrentIndex < this.mComponentRunningLighting.getItems().size() - 1) {
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
}
