package com.android.camera.features.mimoji2.widget.autoselectview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.camera.R;
import com.android.camera.features.mimoji2.widget.autoselectview.SelectItemBean;
import java.util.ArrayList;

public class AutoSelectAdapter<T extends SelectItemBean> extends RecyclerView.Adapter<AutoSelectViewHolder> implements OnPositionChangedListener {
    private ArrayList<T> mDdataList;
    private int mLastMiddlePosition = 0;
    private int mLastSelectPosition = -1;
    private OnSelectListener onSelectListener;
    private RecyclerView recyclerView;

    public interface OnSelectListener<T> {
        void onSelectListener(T t, int i);
    }

    public AutoSelectAdapter(ArrayList<T> arrayList) {
        this.mDdataList = arrayList;
    }

    public ArrayList<T> getDataList() {
        return this.mDdataList;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<T> arrayList = this.mDdataList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return (long) i;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    public int getLastSelectPosition() {
        return this.mLastSelectPosition;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView2) {
        super.onAttachedToRecyclerView(recyclerView2);
        this.recyclerView = recyclerView2;
    }

    public void onBindViewHolder(@NonNull AutoSelectViewHolder autoSelectViewHolder, int i) {
        autoSelectViewHolder.setData(this.mDdataList.get(i), i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public AutoSelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AutoSelectViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mimoij_type_item, viewGroup, false));
    }

    @Override // com.android.camera.features.mimoji2.widget.autoselectview.OnPositionChangedListener
    public void onMoveMiddlePoisionChanged(int i, boolean z) {
        if (z) {
            int i2 = this.mLastSelectPosition;
            if (i2 != i) {
                updateDataAlpha(i2, 0);
                updateDataAlpha(i, 1);
            }
            int i3 = this.mLastMiddlePosition;
            if (i3 != i) {
                updateDataAlpha(i3, 0);
                updateDataAlpha(i, 1);
                this.mLastMiddlePosition = i;
                return;
            }
            return;
        }
        updateDataAlpha(this.mLastMiddlePosition, 0);
    }

    @Override // com.android.camera.features.mimoji2.widget.autoselectview.OnPositionChangedListener
    public void onSelectedPositionChanged(int i) {
        int i2 = this.mLastMiddlePosition;
        if (i2 != i) {
            updateDataAlpha(i2, 0);
            updateDataAlpha(i, 1);
        }
        int i3 = this.mLastSelectPosition;
        if (i3 != i) {
            updateDataAlpha(i3, 0);
            updateDataAlpha(i, 1);
            this.mLastSelectPosition = i;
            onSelectedPositionFinish(i);
        }
    }

    @Override // com.android.camera.features.mimoji2.widget.autoselectview.OnPositionChangedListener
    public void onSelectedPositionFinish(int i) {
        if (this.onSelectListener != null && i > -1 && i < getItemCount()) {
            this.onSelectListener.onSelectListener(getDataList().get(i), i);
        }
    }

    public synchronized void setDataList(ArrayList<T> arrayList) {
        this.mDdataList = arrayList;
        this.mLastSelectPosition = -1;
        this.mLastMiddlePosition = 0;
        notifyDataSetChanged();
    }

    public void setOnSelectListener(OnSelectListener onSelectListener2) {
        this.onSelectListener = onSelectListener2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        return;
     */
    public synchronized void updateData(int i, T t) {
        if (this.mDdataList != null && i >= 0) {
            if (i <= getItemCount() - 1) {
                this.mDdataList.set(i, t);
                notifyItemChanged(i);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0040, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0042, code lost:
        return;
     */
    public synchronized void updateDataAlpha(int i, int i2) {
        if (this.mDdataList != null && i >= 0) {
            if (i <= getItemCount() - 1) {
                this.mDdataList.get(i).setAlpha(i2);
                if (this.recyclerView != null) {
                    long j = (long) i;
                    if (this.recyclerView.findViewHolderForItemId(j) != null) {
                        ((AutoSelectViewHolder) this.recyclerView.findViewHolderForItemId(j)).setData(this.mDdataList.get(i), i);
                    }
                }
                notifyItemChanged(i);
            }
        }
    }
}
