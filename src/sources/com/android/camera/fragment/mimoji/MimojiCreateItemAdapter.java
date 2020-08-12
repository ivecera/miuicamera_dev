package com.android.camera.fragment.mimoji;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.music.RoundedCornersTransformation;
import com.bumptech.glide.c;
import com.bumptech.glide.request.f;
import java.util.ArrayList;
import java.util.List;

public class MimojiCreateItemAdapter extends RecyclerView.Adapter<MimojiItemHolder> {
    private String adapterSelectState;
    /* access modifiers changed from: private */
    public List<MimojiInfo> datas = new ArrayList();
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private View mSelectItemView;
    private MimojiInfo mimojiInfoSelected;
    /* access modifiers changed from: private */
    public OnItemClickListener onItemClickListener;

    class MimojiItemHolder extends CommonRecyclerViewHolder implements View.OnClickListener {
        public MimojiItemHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            int adapterPosition = getAdapterPosition() - 1;
            if (adapterPosition != -2) {
                MimojiInfo mimojiInfo = (MimojiInfo) MimojiCreateItemAdapter.this.datas.get(adapterPosition);
                if (MimojiCreateItemAdapter.this.onItemClickListener != null) {
                    MimojiCreateItemAdapter.this.onItemClickListener.onItemClick(mimojiInfo, adapterPosition, view);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MimojiInfo mimojiInfo, int i, View view);
    }

    public MimojiCreateItemAdapter(Context context, String str) {
        this.mContext = context;
        this.adapterSelectState = str;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<MimojiInfo> list = this.datas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public MimojiInfo getMimojiInfoSelected() {
        return this.mimojiInfoSelected;
    }

    public OnItemClickListener getOnItemClickListener() {
        return this.onItemClickListener;
    }

    public void onBindViewHolder(MimojiItemHolder mimojiItemHolder, int i) {
        String str;
        ImageView imageView = (ImageView) mimojiItemHolder.getView(R.id.mimoji_item_image);
        this.mSelectItemView = mimojiItemHolder.getView(R.id.mimoji_item_selected_indicator);
        View view = mimojiItemHolder.getView(R.id.mimoji_long_item_selected_indicator);
        MimojiInfo mimojiInfo = this.datas.get(i);
        ((RecyclerView.ViewHolder) mimojiItemHolder).itemView.setTag(mimojiInfo);
        if (mimojiInfo != null && (str = mimojiInfo.mConfigPath) != null) {
            if ("add_state".equals(str)) {
                imageView.setImageResource(R.drawable.mimoji_add);
            } else {
                c.H(this.mContext).load(mimojiInfo.mThumbnailUrl).b(f.a(new RoundedCornersTransformation(10, 1))).a(imageView);
            }
            if (mimojiInfo == null || TextUtils.isEmpty(this.adapterSelectState) || TextUtils.isEmpty(mimojiInfo.mConfigPath) || !this.adapterSelectState.equals(mimojiInfo.mConfigPath) || mimojiInfo.mConfigPath.equals("add_state")) {
                this.mSelectItemView.setVisibility(8);
                view.setVisibility(8);
                this.mimojiInfoSelected = null;
                return;
            }
            this.mSelectItemView.setVisibility(0);
            if (AvatarEngineManager.isPrefabModel(mimojiInfo.mConfigPath)) {
                view.setVisibility(8);
                this.mSelectItemView.setBackground(this.mContext.getResources().getDrawable(R.drawable.bg_mimoji_animal_selected));
            } else {
                view.setVisibility(0);
                this.mSelectItemView.setBackground(this.mContext.getResources().getDrawable(R.drawable.bg_mimoji_selected));
            }
            this.mimojiInfoSelected = mimojiInfo;
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public MimojiItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MimojiItemHolder(this.mLayoutInflater.inflate(R.layout.fragment_mimoji_item, viewGroup, false));
    }

    public void setMimojiInfoList(List<MimojiInfo> list) {
        this.datas.clear();
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public void updateSelect() {
        this.adapterSelectState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
        notifyDataSetChanged();
    }
}
