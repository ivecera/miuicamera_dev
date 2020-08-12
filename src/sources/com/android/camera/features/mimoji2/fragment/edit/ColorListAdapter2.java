package com.android.camera.features.mimoji2.fragment.edit;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.camera.R;
import com.android.camera.features.mimoji2.utils.ClickCheck2;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.ui.CircleImageView;
import com.arcsoft.avatar.AvatarConfig;
import java.util.ArrayList;
import java.util.List;

public class ColorListAdapter2 extends RecyclerView.Adapter<ViewHolder> {
    public static final String TAG = "ColorListAdapter2";
    /* access modifiers changed from: private */
    public AvatarConfigItemClick2 mAvatarConfigItemClick2;
    private Context mContext;
    private List<AvatarConfig.ASAvatarConfigInfo> mDatas;
    public int mLastPosion = -1;
    /* access modifiers changed from: private */
    public LinearLayoutManagerWrapper mLinearLayoutManagerWrapper;
    /* access modifiers changed from: private */
    public RecyclerView mRootView;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivColor;

        public ViewHolder(View view) {
            super(view);
            this.ivColor = (CircleImageView) view.findViewById(R.id.iv_color);
        }
    }

    public ColorListAdapter2(Context context, AvatarConfigItemClick2 avatarConfigItemClick2, LinearLayoutManagerWrapper linearLayoutManagerWrapper) {
        this.mContext = context;
        this.mDatas = new ArrayList();
        this.mAvatarConfigItemClick2 = avatarConfigItemClick2;
        this.mLinearLayoutManagerWrapper = linearLayoutManagerWrapper;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<AvatarConfig.ASAvatarConfigInfo> list = this.mDatas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public float getSelectItem(int i) {
        return AvatarEngineManager2.getInstance().getInnerConfigSelectIndex(i);
    }

    public LinearLayoutManagerWrapper getmLinearLayoutManagerWrapper() {
        return this.mLinearLayoutManagerWrapper;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRootView = recyclerView;
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo = this.mDatas.get(i);
        final CircleImageView circleImageView = viewHolder.ivColor;
        float selectItem = getSelectItem(aSAvatarConfigInfo.configType);
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        int intValue = ((Integer) argbEvaluator.evaluate(aSAvatarConfigInfo.continuousValue, Integer.valueOf(aSAvatarConfigInfo.startColorValue), Integer.valueOf(aSAvatarConfigInfo.endColorValue))).intValue();
        if (selectItem == ((float) aSAvatarConfigInfo.configID)) {
            circleImageView.updateView(true, intValue);
            this.mLastPosion = i;
        } else {
            circleImageView.updateView(false, intValue);
        }
        ((RecyclerView.ViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            /* class com.android.camera.features.mimoji2.fragment.edit.ColorListAdapter2.AnonymousClass1 */

            public void onClick(View view) {
                if (ClickCheck2.getInstance().checkClickable()) {
                    ColorListAdapter2 colorListAdapter2 = ColorListAdapter2.this;
                    if (colorListAdapter2.mLastPosion != i && argbEvaluator != null && colorListAdapter2.mRootView != null) {
                        if (aSAvatarConfigInfo.configType == 2) {
                            AvatarEngineManager2.getInstance().setInnerConfigSelectIndex(19, (float) aSAvatarConfigInfo.configID);
                            AvatarEngineManager2.getInstance().setInnerConfigSelectIndex(15, (float) aSAvatarConfigInfo.configID);
                        }
                        if (ColorListAdapter2.this.mLinearLayoutManagerWrapper != null) {
                            if (i == ColorListAdapter2.this.mLinearLayoutManagerWrapper.findFirstVisibleItemPosition() || i == ColorListAdapter2.this.mLinearLayoutManagerWrapper.findFirstCompletelyVisibleItemPosition()) {
                                ColorListAdapter2.this.mRootView.scrollToPosition(Math.max(0, i - 1));
                            } else if (i == ColorListAdapter2.this.mLinearLayoutManagerWrapper.findLastVisibleItemPosition() || i == ColorListAdapter2.this.mLinearLayoutManagerWrapper.findLastCompletelyVisibleItemPosition()) {
                                ColorListAdapter2.this.mRootView.scrollToPosition(Math.min(i + 1, ColorListAdapter2.this.getItemCount() - 1));
                            }
                        }
                        AvatarEngineManager2 instance = AvatarEngineManager2.getInstance();
                        AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo = aSAvatarConfigInfo;
                        instance.setInnerConfigSelectIndex(aSAvatarConfigInfo.configType, (float) aSAvatarConfigInfo.configID);
                        ViewHolder viewHolder = (ViewHolder) ColorListAdapter2.this.mRootView.findViewHolderForAdapterPosition(ColorListAdapter2.this.mLastPosion);
                        if (viewHolder != null) {
                            viewHolder.ivColor.updateView(false);
                        } else {
                            ColorListAdapter2 colorListAdapter22 = ColorListAdapter2.this;
                            colorListAdapter22.notifyItemChanged(colorListAdapter22.mLastPosion);
                        }
                        circleImageView.updateView(true);
                        ColorListAdapter2 colorListAdapter23 = ColorListAdapter2.this;
                        colorListAdapter23.mLastPosion = i;
                        colorListAdapter23.mAvatarConfigItemClick2.onConfigItemClick(aSAvatarConfigInfo, true, i);
                    }
                }
            }
        });
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.item_mimoji_color, viewGroup, false));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mRootView = null;
    }

    public void setData(List<AvatarConfig.ASAvatarConfigInfo> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }
}
