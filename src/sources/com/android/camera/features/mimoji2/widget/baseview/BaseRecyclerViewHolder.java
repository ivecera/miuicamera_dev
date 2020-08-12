package com.android.camera.features.mimoji2.widget.baseview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseRecyclerViewHolder(@NonNull View view) {
        super(view);
    }

    public void setClickListener(OnRecyclerItemClickListener<T> onRecyclerItemClickListener, T t, int i) {
        ((RecyclerView.ViewHolder) this).itemView.setOnClickListener(new a(onRecyclerItemClickListener, t, i));
    }

    public abstract void setData(T t, int i);
}
