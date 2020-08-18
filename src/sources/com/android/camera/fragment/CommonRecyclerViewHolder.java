package com.android.camera.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews = new SparseArray<>();

    public CommonRecyclerViewHolder(View view) {
        super(view);
    }

    public <T extends View> T getView(int i) {
        T t = this.mViews.get(i);
        if (t != null) {
            return t;
        }
        T findViewById = ((RecyclerView.ViewHolder) this).itemView.findViewById(i);
        this.mViews.put(i, findViewById);
        return findViewById;
    }
}
