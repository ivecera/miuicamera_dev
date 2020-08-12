package com.android.camera.fragment.vv;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import java.util.List;

public class VVShareAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private int mItemWidth;
    private View.OnClickListener mOnClickListener;
    private PackageManager mPackageManager;
    private List<ResolveInfo> mShareInfoList;

    public VVShareAdapter(PackageManager packageManager, List<ResolveInfo> list, View.OnClickListener onClickListener, int i) {
        this.mPackageManager = packageManager;
        this.mShareInfoList = list;
        this.mOnClickListener = onClickListener;
        this.mItemWidth = i;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mShareInfoList.size();
    }

    public void onBindViewHolder(CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        ResolveInfo resolveInfo = this.mShareInfoList.get(i);
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setTag(resolveInfo);
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setOnClickListener(this.mOnClickListener);
        ImageView imageView = (ImageView) commonRecyclerViewHolder.getView(R.id.live_share_icon);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageDrawable(resolveInfo.loadIcon(this.mPackageManager));
        ((TextView) commonRecyclerViewHolder.getView(R.id.live_share_name)).setText(resolveInfo.loadLabel(this.mPackageManager));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_vv_share_item, viewGroup, false);
        inflate.getLayoutParams().width = this.mItemWidth;
        return new CommonRecyclerViewHolder(inflate);
    }

    public void setShareInfoList(List<ResolveInfo> list) {
        this.mShareInfoList = list;
    }
}
