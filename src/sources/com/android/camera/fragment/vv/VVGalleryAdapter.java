package com.android.camera.fragment.vv;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.statistic.CameraStatUtils;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.request.f;
import java.util.Locale;

public class VVGalleryAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements View.OnClickListener {
    private f mGlideOptions;
    private LinearLayoutManager mLayoutManager;
    private View.OnClickListener mParentClickListener;
    private ResourceSelectedListener mResourceSelectedListener;
    private int mSelectedIndex;
    private VVList mVVList;

    public VVGalleryAdapter(VVList vVList, LinearLayoutManager linearLayoutManager, int i, View.OnClickListener onClickListener, ResourceSelectedListener resourceSelectedListener) {
        this.mVVList = vVList;
        this.mLayoutManager = linearLayoutManager;
        if (i >= 0) {
            this.mSelectedIndex = i;
        }
        this.mParentClickListener = onClickListener;
        this.mResourceSelectedListener = resourceSelectedListener;
        this.mGlideOptions = new f();
        this.mGlideOptions.B(false);
        this.mGlideOptions.a(o.NONE);
    }

    private String getDurationString(long j) {
        return String.format(Locale.ENGLISH, "00:%02d", Integer.valueOf(Math.abs((int) Math.floor((double) (((float) j) / 1000.0f)))));
    }

    private void notifyItemChanged(int i, int i2) {
        if (i > -1) {
            notifyItemChanged(i);
        }
        if (i2 > -1) {
            notifyItemChanged(i2);
        }
    }

    private void scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
        } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, getItemCount() - 1));
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        VVList vVList = this.mVVList;
        if (vVList != null) {
            return vVList.getSize();
        }
        return 0;
    }

    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        VVItem vVItem = (VVItem) this.mVVList.getItem(i);
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setTag(Integer.valueOf(i));
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setOnClickListener(this);
        ImageView imageView = (ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_image);
        ImageView imageView2 = (ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_play);
        ImageView imageView3 = (ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_indicator);
        TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_text);
        TextView textView2 = (TextView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_duration);
        textView.setText(vVItem.name);
        if (this.mSelectedIndex == i) {
            textView.setTextColor(-1);
            imageView2.setVisibility(0);
            imageView3.setVisibility(0);
            textView2.setVisibility(0);
            textView2.setText(getDurationString(vVItem.getTotalDuration()));
        } else {
            textView.setTextColor(textView.getResources().getColor(R.color.vv_gallery_item_text));
            imageView2.setVisibility(8);
            imageView3.setVisibility(8);
            textView2.setVisibility(8);
        }
        ViewCompat.setTransitionName(imageView, vVItem.name);
        c.c(((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView).load(vVItem.coverPath).b(this.mGlideOptions).a(imageView);
    }

    public void onClick(View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        CameraStatUtils.trackVVTemplateThumbnailClick(((VVItem) this.mVVList.getItem(intValue)).name);
        onSelected(intValue, view, true);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommonRecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_vv_gallery_item, viewGroup, false));
    }

    public void onSelected(int i, View view, boolean z) {
        int i2 = this.mSelectedIndex;
        if (i2 != i) {
            this.mSelectedIndex = i;
            this.mResourceSelectedListener.onResourceSelected((VVItem) this.mVVList.getItem(i));
            if (z) {
                scrollIfNeed(i);
                notifyItemChanged(i2, this.mSelectedIndex);
                return;
            }
            notifyDataSetChanged();
        } else if (view != null) {
            this.mParentClickListener.onClick(view);
        }
    }
}
