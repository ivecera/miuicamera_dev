package com.android.camera.fragment.aiwatermark;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.camera.R;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.aiwatermark.adapter.WatermarkAdapter;
import com.android.camera.fragment.beauty.BaseBeautyFragment;
import java.util.List;

public abstract class FragmentBaseWatermark extends BaseBeautyFragment implements View.OnClickListener {
    public static final int FRAGMENT_INFO = 65535;
    private static final String TAG = "FragmentBaseWatermark";
    private int mCurrentMode;
    private EffectItemPadding mEffectItemPadding;
    private int mHolderWidth;
    private LinearLayoutManager mLayoutManager = null;
    private RecyclerView mRecyclerView;
    private int mTotalWidth;
    private WatermarkAdapter mWatermarkAdapter;

    private static class EffectItemPadding extends RecyclerView.ItemDecoration {
        protected int mEffectListLeft;
        protected int mHorizontalPadding;
        protected int mVerticalPadding;

        public EffectItemPadding(Context context) {
            this.mHorizontalPadding = context.getResources().getDimensionPixelSize(R.dimen.rv_padding_h);
            this.mVerticalPadding = context.getResources().getDimensionPixelSize(R.dimen.rv_padding_v);
            this.mEffectListLeft = context.getResources().getDimensionPixelSize(R.dimen.rv_padding_left);
        }

        @Override // android.support.v7.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int i = recyclerView.getChildPosition(view) == 0 ? 0 : this.mEffectListLeft;
            int i2 = this.mVerticalPadding;
            rect.set(i, i2, this.mHorizontalPadding, i2);
        }
    }

    private void initView(View view) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.watermark_list);
        List<WatermarkItem> watermarkList = getWatermarkList();
        Context context = getContext();
        this.mLayoutManager = new LinearLayoutManager(context);
        int i = 0;
        this.mLayoutManager.setOrientation(0);
        int size = watermarkList.size();
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            } else if (TextUtils.equals(watermarkList.get(i).getKey(), WatermarkConstant.SELECT_KEY)) {
                break;
            } else {
                i++;
            }
        }
        this.mWatermarkAdapter = new WatermarkAdapter(context, this.mLayoutManager, i, watermarkList);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mWatermarkAdapter);
        if (this.mEffectItemPadding == null) {
            this.mEffectItemPadding = new EffectItemPadding(getContext());
        }
        this.mRecyclerView.addItemDecoration(this.mEffectItemPadding);
        this.mTotalWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mHolderWidth = context.getResources().getDimensionPixelSize(R.dimen.wm_item_width);
        if (i >= 0) {
            setItemInCenter(i);
        }
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mRecyclerView.setItemAnimator(defaultItemAnimator);
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, (this.mTotalWidth / 2) - (this.mHolderWidth / 2));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public View getAnimateView() {
        return this.mRecyclerView;
    }

    public abstract List<WatermarkItem> getWatermarkList();

    public void onClick(View view) {
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.mCurrentMode = DataRepository.dataItemGlobal().getCurrentMode();
        View inflate = layoutInflater.inflate(R.layout.fragment_watermark_rv, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment, com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreatedAndVisibleToUser(boolean z) {
        WatermarkAdapter watermarkAdapter;
        super.onViewCreatedAndVisibleToUser(z);
        if (!z && (watermarkAdapter = this.mWatermarkAdapter) != null && watermarkAdapter.getSelectedIndex() >= 0) {
            List<WatermarkItem> items = this.mWatermarkAdapter.getItems();
            int size = items.size();
            int i = -1;
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                } else if (TextUtils.equals(items.get(i2).getKey(), WatermarkConstant.SELECT_KEY)) {
                    i = i2;
                    break;
                } else {
                    i2++;
                }
            }
            this.mWatermarkAdapter.onSelected(i, false);
        }
    }
}
