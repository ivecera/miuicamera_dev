package com.android.camera.features.mimoji2.fragment.bottomlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.features.mimoji2.bean.MimojiBgInfo;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder;
import java.util.List;

public class MimojiBgAdapter extends BaseRecyclerAdapter<MimojiBgInfo> {

    static class BgViewViewHolder extends BaseRecyclerViewHolder<MimojiBgInfo> {
        ImageView imageView;
        ImageView longSelectedView;
        ImageView mSelectItemView;

        public BgViewViewHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.mimoji_item_image);
            this.mSelectItemView = (ImageView) view.findViewById(R.id.mimoji_item_selected_indicator);
            this.longSelectedView = (ImageView) view.findViewById(R.id.mimoji_long_item_selected_indicator);
        }

        public void setData(MimojiBgInfo mimojiBgInfo, int i) {
            this.longSelectedView.setVisibility(8);
            if (mimojiBgInfo.getBackgroundInfo() != null) {
                if (mimojiBgInfo.getResourceId() > 0) {
                    this.imageView.setImageResource(mimojiBgInfo.getResourceId());
                } else {
                    this.imageView.setImageResource(R.drawable.bg_thumbnail_play);
                }
                if (mimojiBgInfo.isSelected()) {
                    this.mSelectItemView.setVisibility(0);
                    this.mSelectItemView.setBackground(((RecyclerView.ViewHolder) this).itemView.getContext().getResources().getDrawable(R.drawable.bg_mimoji_circle_selected));
                    return;
                }
                this.mSelectItemView.setVisibility(8);
                return;
            }
            this.mSelectItemView.setVisibility(8);
        }
    }

    public MimojiBgAdapter(List<MimojiBgInfo> list) {
        super(list);
    }

    public void clearState() {
        if (getDataList() != null) {
            for (int i = 0; i < getItemCount(); i++) {
                MimojiBgInfo mimojiBgInfo = (MimojiBgInfo) getDataList().get(i);
                if (mimojiBgInfo.isSelected()) {
                    mimojiBgInfo.setSelected(false);
                    notifyItemChanged(i);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter
    public BaseRecyclerViewHolder<MimojiBgInfo> onCreateBaseRecyclerViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_mimoji_item, viewGroup, false);
        if (i == 0) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) inflate.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = viewGroup.getResources().getDimensionPixelSize(R.dimen.live_share_item_margin);
            inflate.setLayoutParams(layoutParams);
        }
        return new BgViewViewHolder(inflate);
    }

    public void setSelectState(int i) {
        if (getDataList() != null && i < getItemCount()) {
            ((MimojiBgInfo) getDataList().get(i)).setSelected(true);
            notifyItemChanged(i);
        }
    }
}
