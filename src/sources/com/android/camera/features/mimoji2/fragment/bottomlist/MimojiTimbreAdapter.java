package com.android.camera.features.mimoji2.fragment.bottomlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.features.mimoji2.bean.MimojiTimbreInfo;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder;
import java.util.List;

public class MimojiTimbreAdapter extends BaseRecyclerAdapter<MimojiTimbreInfo> {
    private int mLastSelectedPosition = -1;

    static class VoiceViewViewHolder extends BaseRecyclerViewHolder<MimojiTimbreInfo> {
        ImageView imageView;
        ImageView longSelectedView;
        ImageView mSelectItemView;

        public VoiceViewViewHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.mimoji_item_image);
            this.mSelectItemView = (ImageView) view.findViewById(R.id.mimoji_item_selected_indicator);
            this.longSelectedView = (ImageView) view.findViewById(R.id.mimoji_long_item_selected_indicator);
        }

        public void setData(MimojiTimbreInfo mimojiTimbreInfo, int i) {
            this.longSelectedView.setVisibility(8);
            if (mimojiTimbreInfo.getTimbreId() > 0) {
                if (mimojiTimbreInfo.getResourceId() > 0) {
                    this.imageView.setImageResource(mimojiTimbreInfo.getResourceId());
                } else {
                    this.imageView.setImageResource(R.drawable.bg_thumbnail_play);
                }
                if (mimojiTimbreInfo.isSelected()) {
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

    public MimojiTimbreAdapter(List<MimojiTimbreInfo> list) {
        super(list);
    }

    public void clearState() {
        this.mLastSelectedPosition = -1;
        if (getDataList() != null) {
            for (int i = 0; i < getItemCount(); i++) {
                MimojiTimbreInfo mimojiTimbreInfo = (MimojiTimbreInfo) getDataList().get(i);
                if (mimojiTimbreInfo.isSelected()) {
                    mimojiTimbreInfo.setSelected(false);
                    notifyItemChanged(i);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter
    public BaseRecyclerViewHolder<MimojiTimbreInfo> onCreateBaseRecyclerViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_mimoji_item, viewGroup, false);
        if (i == 0) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) inflate.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = viewGroup.getResources().getDimensionPixelSize(R.dimen.live_share_item_margin);
            inflate.setLayoutParams(layoutParams);
        }
        return new VoiceViewViewHolder(inflate);
    }

    public boolean setSelectState(int i) {
        if (this.mLastSelectedPosition == i || getDataList() == null || i >= getItemCount()) {
            return false;
        }
        if (this.mLastSelectedPosition >= 0) {
            ((MimojiTimbreInfo) getDataList().get(this.mLastSelectedPosition)).setSelected(false);
            notifyItemChanged(this.mLastSelectedPosition);
            this.mLastSelectedPosition = -1;
        }
        ((MimojiTimbreInfo) getDataList().get(i)).setSelected(true);
        notifyItemChanged(i);
        this.mLastSelectedPosition = i;
        return true;
    }
}
