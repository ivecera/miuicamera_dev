package com.android.camera.fragment.sticker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.camera.R;
import java.util.List;

public abstract class BaseSelectAdapter<T> extends RecyclerView.Adapter<BaseSelectHolder> implements View.OnClickListener {
    protected Context mContext;
    private ItemSelectChangeListener mItemSelectChangeListener;
    protected int mLastSelectedItemPosition = -1;
    protected List<T> mList;
    protected int mSelectedItemPosition = 0;

    public static abstract class BaseSelectHolder extends RecyclerView.ViewHolder {
        protected View mIVSelected;

        public BaseSelectHolder(View view) {
            super(view);
            view.setTag(R.id.item_root, this);
            this.mIVSelected = view.findViewById(R.id.iv_selected);
        }

        public abstract void bindView(int i);
    }

    public interface ItemSelectChangeListener {
        boolean onItemSelect(BaseSelectHolder baseSelectHolder, int i, boolean z);
    }

    public BaseSelectAdapter(Context context) {
        this.mContext = context;
    }

    private void setSelected(BaseSelectHolder baseSelectHolder, boolean z) {
        ((RecyclerView.ViewHolder) baseSelectHolder).itemView.setSelected(z);
        baseSelectHolder.mIVSelected.setVisibility(z ? 0 : 4);
    }

    /* access modifiers changed from: protected */
    public abstract BaseSelectHolder getHolder(View view);

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<T> list = this.mList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public T getItemData(int i) {
        return this.mList.get(i);
    }

    /* access modifiers changed from: protected */
    public abstract int getLayoutId();

    public List<T> getList() {
        return this.mList;
    }

    public int getSelectedItemPosition() {
        return this.mSelectedItemPosition;
    }

    public void initSelectItem(int i) {
        this.mSelectedItemPosition = i;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [android.support.v7.widget.RecyclerView$ViewHolder, int, java.util.List] */
    @Override // android.support.v7.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(BaseSelectHolder baseSelectHolder, int i, List list) {
        onBindViewHolder(baseSelectHolder, i, (List<Object>) list);
    }

    public void onBindViewHolder(BaseSelectHolder baseSelectHolder, int i) {
        ((RecyclerView.ViewHolder) baseSelectHolder).itemView.setOnClickListener(this);
        setSelected(baseSelectHolder, i == this.mSelectedItemPosition);
        baseSelectHolder.bindView(i);
    }

    public void onBindViewHolder(BaseSelectHolder baseSelectHolder, int i, List<Object> list) {
        if (list == null || list.size() <= 0) {
            super.onBindViewHolder((RecyclerView.ViewHolder) baseSelectHolder, i, list);
        } else {
            setSelected(baseSelectHolder, i == this.mSelectedItemPosition);
        }
    }

    public void onClick(View view) {
        BaseSelectHolder baseSelectHolder = (BaseSelectHolder) view.getTag(R.id.item_root);
        int adapterPosition = baseSelectHolder.getAdapterPosition();
        if (this.mSelectedItemPosition != adapterPosition) {
            ItemSelectChangeListener itemSelectChangeListener = this.mItemSelectChangeListener;
            if (itemSelectChangeListener != null && itemSelectChangeListener.onItemSelect(baseSelectHolder, adapterPosition, true)) {
                setSelectedItemPosition(adapterPosition);
                return;
            }
            return;
        }
        ItemSelectChangeListener itemSelectChangeListener2 = this.mItemSelectChangeListener;
        if (itemSelectChangeListener2 != null) {
            itemSelectChangeListener2.onItemSelect(baseSelectHolder, adapterPosition, false);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public BaseSelectHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return getHolder(LayoutInflater.from(this.mContext).inflate(getLayoutId(), viewGroup, false));
    }

    public void setItemSelectChangeListener(ItemSelectChangeListener itemSelectChangeListener) {
        this.mItemSelectChangeListener = itemSelectChangeListener;
    }

    public void setList(List<T> list) {
        this.mList = list;
    }

    public void setSelectedItemPosition(int i) {
        this.mLastSelectedItemPosition = this.mSelectedItemPosition;
        this.mSelectedItemPosition = i;
        notifyItemChanged(this.mLastSelectedItemPosition, true);
        notifyItemChanged(this.mSelectedItemPosition, true);
    }
}
