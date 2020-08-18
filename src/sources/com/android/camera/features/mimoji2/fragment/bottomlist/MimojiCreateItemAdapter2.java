package com.android.camera.features.mimoji2.fragment.bottomlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.music.RoundedCornersTransformation;
import com.bumptech.glide.c;
import com.bumptech.glide.request.f;
import java.util.ArrayList;
import java.util.List;

public class MimojiCreateItemAdapter2 extends RecyclerView.Adapter<MimojiItemHolder> {
    private String adapterSelectState;
    private Context mContext;
    /* access modifiers changed from: private */
    public List<MimojiInfo2> mDatas = new ArrayList();
    LayoutInflater mLayoutInflater;
    private View mSelectItemView;
    private MimojiInfo2 mimojiInfo2Selected;
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
                MimojiInfo2 mimojiInfo2 = (MimojiInfo2) MimojiCreateItemAdapter2.this.mDatas.get(adapterPosition);
                if (MimojiCreateItemAdapter2.this.onItemClickListener != null) {
                    MimojiCreateItemAdapter2.this.onItemClickListener.onItemClick(mimojiInfo2, adapterPosition, view);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MimojiInfo2 mimojiInfo2, int i, View view);
    }

    public MimojiCreateItemAdapter2(Context context, String str) {
        this.mContext = context;
        this.adapterSelectState = str;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<MimojiInfo2> list = this.mDatas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public MimojiInfo2 getMimojiInfo2Selected() {
        return this.mimojiInfo2Selected;
    }

    public OnItemClickListener getOnItemClickListener() {
        return this.onItemClickListener;
    }

    public void onBindViewHolder(MimojiItemHolder mimojiItemHolder, int i) {
        String str;
        ImageView imageView = (ImageView) mimojiItemHolder.getView(R.id.mimoji_item_image);
        this.mSelectItemView = mimojiItemHolder.getView(R.id.mimoji_item_selected_indicator);
        View view = mimojiItemHolder.getView(R.id.mimoji_long_item_selected_indicator);
        MimojiInfo2 mimojiInfo2 = this.mDatas.get(i);
        ((RecyclerView.ViewHolder) mimojiItemHolder).itemView.setTag(mimojiInfo2);
        if (mimojiInfo2 != null && (str = mimojiInfo2.mConfigPath) != null) {
            if ("add_state".equals(str)) {
                imageView.setImageResource(R.drawable.ic_mimoji_add);
            } else {
                c.H(this.mContext).load(mimojiInfo2.mThumbnailUrl).b(f.a(new RoundedCornersTransformation(10, 1))).a(imageView);
            }
            if (mimojiInfo2 == null || TextUtils.isEmpty(this.adapterSelectState) || TextUtils.isEmpty(mimojiInfo2.mConfigPath) || !this.adapterSelectState.equals(mimojiInfo2.mConfigPath) || mimojiInfo2.mConfigPath.equals("add_state")) {
                this.mSelectItemView.setVisibility(8);
                view.setVisibility(8);
                this.mimojiInfo2Selected = null;
                return;
            }
            this.mSelectItemView.setVisibility(0);
            if (AvatarEngineManager2.isPrefabModel(mimojiInfo2.mConfigPath)) {
                view.setVisibility(8);
                this.mSelectItemView.setBackground(this.mContext.getResources().getDrawable(R.drawable.bg_mimoji_animal_selected));
            } else {
                view.setVisibility(0);
                this.mSelectItemView.setBackground(this.mContext.getResources().getDrawable(R.drawable.bg_mimoji_selected));
            }
            this.mimojiInfo2Selected = mimojiInfo2;
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public MimojiItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MimojiItemHolder(this.mLayoutInflater.inflate(R.layout.fragment_mimoji_item, viewGroup, false));
    }

    public void setMimojiInfoList(List<MimojiInfo2> list) {
        this.mDatas.clear();
        this.mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public void updateSelect() {
        this.adapterSelectState = DataRepository.dataItemLive().getMimojiStatusManager2().getCurrentMimojiState();
        notifyDataSetChanged();
    }
}
