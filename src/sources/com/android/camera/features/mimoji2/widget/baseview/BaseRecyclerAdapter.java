package com.android.camera.features.mimoji2.widget.baseview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    public static final String TAG = "BaseRecyclerAdapter";
    private List<T> mDdataList;
    private OnRecyclerItemClickListener<T> onRecyclerItemClickListener;

    public BaseRecyclerAdapter(List<T> list) {
        this.mDdataList = list;
    }

    public synchronized void addData(T t) {
        if (this.mDdataList == null) {
            this.mDdataList = new ArrayList();
        }
        int size = this.mDdataList.size();
        if (this.mDdataList.add(t)) {
            notifyItemInserted(size);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
        return;
     */
    public synchronized void addData(T t, int i) {
        if (this.mDdataList != null) {
            if (i <= this.mDdataList.size()) {
                this.mDdataList.add(i, t);
                notifyItemInserted(i);
            }
        }
    }

    public List<T> getDataList() {
        return this.mDdataList;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<T> list = this.mDdataList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseRecyclerViewHolder, int i) {
        List<T> list = this.mDdataList;
        if (list != null && i <= list.size() - 1) {
            T t = this.mDdataList.get(i);
            if (t == null) {
                Log.e(TAG, "data null error");
            }
            baseRecyclerViewHolder.setData(t, i);
            OnRecyclerItemClickListener<T> onRecyclerItemClickListener2 = this.onRecyclerItemClickListener;
            if (onRecyclerItemClickListener2 != null) {
                baseRecyclerViewHolder.setClickListener(onRecyclerItemClickListener2, t, i);
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract BaseRecyclerViewHolder<T> onCreateBaseRecyclerViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return onCreateBaseRecyclerViewHolder(viewGroup, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001d, code lost:
        return;
     */
    public synchronized void removeData(int i) {
        if (this.mDdataList != null && i >= 0) {
            if (i <= this.mDdataList.size() - 1) {
                this.mDdataList.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public synchronized void setDataList(List<T> list) {
        this.mDdataList = list;
        notifyDataSetChanged();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener<T> onRecyclerItemClickListener2) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002f, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000b, code lost:
        return;
     */
    public synchronized void updateData(int i, T t) {
        if (this.mDdataList == null) {
            if (i == 0) {
                addData(t);
            }
        } else if (i >= 0 && i <= this.mDdataList.size()) {
            if (i == this.mDdataList.size()) {
                addData(t);
                return;
            }
            this.mDdataList.set(i, t);
            notifyItemChanged(i);
        }
    }
}
