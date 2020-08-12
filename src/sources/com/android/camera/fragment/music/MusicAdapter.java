package com.android.camera.fragment.music;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.bumptech.glide.c;
import com.bumptech.glide.request.f;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;
    private List<LiveMusicInfo> mMusicList;
    private View.OnClickListener mOnClickListener;
    private View.OnTouchListener mOnTouchListener;

    public MusicAdapter(Context context, View.OnClickListener onClickListener, View.OnTouchListener onTouchListener, List<LiveMusicInfo> list) {
        this.mContext = context;
        this.mOnClickListener = onClickListener;
        this.mOnTouchListener = onTouchListener;
        this.mMusicList = list;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mMusicList.size();
    }

    public void onBindViewHolder(CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        LiveMusicInfo liveMusicInfo = this.mMusicList.get(i);
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setOnTouchListener(this.mOnTouchListener);
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setTag(liveMusicInfo);
        float f2 = this.mContext.getResources().getConfiguration().fontScale;
        TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.music_author);
        textView.setText(liveMusicInfo.mAuthor.trim());
        TextView textView2 = (TextView) commonRecyclerViewHolder.getView(R.id.music_title);
        textView2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView2.setSingleLine();
        textView2.setText(liveMusicInfo.mTitle.trim());
        c.H(this.mContext).load(liveMusicInfo.mThumbnailUrl).b(f.a(new RoundedCornersTransformation(10, 1))).a((ImageView) commonRecyclerViewHolder.getView(R.id.music_thumbnail));
        ImageView imageView = (ImageView) commonRecyclerViewHolder.getView(R.id.music_play);
        imageView.setOnClickListener(this.mOnClickListener);
        imageView.setTag(liveMusicInfo);
        ((ProgressBar) commonRecyclerViewHolder.getView(R.id.music_loading)).setTag(liveMusicInfo);
        TextView textView3 = (TextView) commonRecyclerViewHolder.getView(R.id.music_duration);
        String trim = liveMusicInfo.mDurationText.trim();
        if (trim.split(":").length < 2) {
            trim = "00 : " + trim;
        }
        textView3.setText(trim);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        if (f2 > 1.25f) {
            layoutParams.topMargin = 0;
        } else {
            layoutParams.topMargin = this.mContext.getResources().getDimensionPixelOffset(R.dimen.music_author_margin_top);
        }
        textView.setLayoutParams(layoutParams);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonRecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_music_adapter, viewGroup, false));
    }

    public void setData(List<LiveMusicInfo> list) {
        this.mMusicList = list;
        notifyDataSetChanged();
    }
}
