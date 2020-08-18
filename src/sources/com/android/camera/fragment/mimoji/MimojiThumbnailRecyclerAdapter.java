package com.android.camera.fragment.mimoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder;
import com.android.camera.fragment.music.RoundedCornersTransformation;
import com.arcsoft.avatar.AvatarConfig;
import com.bumptech.glide.request.a.c;
import com.bumptech.glide.request.f;
import java.util.ArrayList;

public class MimojiThumbnailRecyclerAdapter extends BaseRecyclerAdapter<AvatarConfig.ASAvatarConfigInfo> {
    private Context mContext;
    private float selectIndex;

    static class ThumbnailViewViewHolder extends BaseRecyclerViewHolder<AvatarConfig.ASAvatarConfigInfo> {
        ImageView imageView;

        public ThumbnailViewViewHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail_image_view);
        }

        public void setData(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo, int i) {
            Bitmap bitmap;
            new c.a(300).setCrossFadeEnabled(true).build();
            if (aSAvatarConfigInfo == null || (bitmap = aSAvatarConfigInfo.thum) == null || bitmap.isRecycled()) {
                Log.e(ThumbnailViewViewHolder.class.getSimpleName(), "fmoji bitmap isRecycled");
            } else {
                com.bumptech.glide.c.H(((RecyclerView.ViewHolder) this).itemView.getContext()).g(aSAvatarConfigInfo.thum).b(new f().i(this.imageView.getDrawable())).b(f.a(new RoundedCornersTransformation(20, 1))).a(this.imageView);
            }
            this.imageView.setBackground(MimojiThumbnailRecyclerAdapter.getSelectItem(aSAvatarConfigInfo.configType) == ((float) aSAvatarConfigInfo.configID) ? ((RecyclerView.ViewHolder) this).itemView.getContext().getDrawable(R.drawable.bg_mimoji_thumbnail_selected) : null);
        }
    }

    public MimojiThumbnailRecyclerAdapter(Context context, int i) {
        this(null);
        this.mContext = context;
        this.selectIndex = getSelectItem(i);
    }

    public MimojiThumbnailRecyclerAdapter(ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList) {
        super(arrayList);
        this.selectIndex = -1.0f;
    }

    public static float getSelectItem(int i) {
        return AvatarEngineManager.getInstance().getInnerConfigSelectIndex(i);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter
    public BaseRecyclerViewHolder<AvatarConfig.ASAvatarConfigInfo> onCreateBaseRecyclerViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ThumbnailViewViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mimoji_thumbnail_view, viewGroup, false));
    }

    public void setSelectItem(int i, int i2) {
        AvatarEngineManager instance = AvatarEngineManager.getInstance();
        if (instance != null) {
            instance.setInnerConfigSelectIndex(i, (float) i2);
        }
    }
}
