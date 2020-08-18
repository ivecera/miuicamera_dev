package com.android.camera.features.mimoji2.fragment.edit;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.features.mimoji2.bean.MimojiEmoticonInfo;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MimojiEmoticonAdapter extends BaseRecyclerAdapter<MimojiEmoticonInfo> {
    private AtomicBoolean mIsAllSelected = new AtomicBoolean(false);
    private OnAllSelectStateChangeListener onAllSelectStateChangeListener;

    static class EmoticonViewViewHolder extends BaseRecyclerViewHolder<MimojiEmoticonInfo> {
        ImageView imageMimojiEmoticon;
        ImageView imageMimojiSelect;

        public EmoticonViewViewHolder(@NonNull View view) {
            super(view);
            this.imageMimojiEmoticon = (ImageView) view.findViewById(R.id.image_item_mimoji_emoticon);
            this.imageMimojiSelect = (ImageView) view.findViewById(R.id.image_item_select);
        }

        public void setData(MimojiEmoticonInfo mimojiEmoticonInfo, int i) {
            if (mimojiEmoticonInfo.getBitmap() != null) {
                this.imageMimojiEmoticon.setImageBitmap(mimojiEmoticonInfo.getBitmap());
            }
            this.imageMimojiSelect.setImageResource(mimojiEmoticonInfo.isSelected() ? R.drawable.ic_mimoji_emoticon_item_selected : R.drawable.ic_mimoji_emoticon_item_unselected);
        }
    }

    interface OnAllSelectStateChangeListener {
        void onAllSelectStateChange(boolean z);
    }

    public MimojiEmoticonAdapter(List<MimojiEmoticonInfo> list) {
        super(list);
    }

    private void checkAllSelectedState() {
        if (getDataList() != null) {
            boolean z = true;
            Iterator it = getDataList().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (!((MimojiEmoticonInfo) it.next()).isSelected()) {
                        z = false;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (this.mIsAllSelected.get() != z) {
                this.mIsAllSelected.set(z);
                OnAllSelectStateChangeListener onAllSelectStateChangeListener2 = this.onAllSelectStateChangeListener;
                if (onAllSelectStateChangeListener2 != null) {
                    onAllSelectStateChangeListener2.onAllSelectStateChange(z);
                }
            }
        }
    }

    public void clearState() {
        if (getDataList() != null) {
            for (MimojiEmoticonInfo mimojiEmoticonInfo : getDataList()) {
                mimojiEmoticonInfo.setSelected(false);
            }
            notifyDataSetChanged();
            checkAllSelectedState();
        }
    }

    public boolean getIsAllSelected() {
        return this.mIsAllSelected.get();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter
    public BaseRecyclerViewHolder<MimojiEmoticonInfo> onCreateBaseRecyclerViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EmoticonViewViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_mimoji_emoticon_item, viewGroup, false));
    }

    public void selectAll() {
        if (getDataList() != null) {
            for (MimojiEmoticonInfo mimojiEmoticonInfo : getDataList()) {
                mimojiEmoticonInfo.setSelected(true);
            }
            notifyDataSetChanged();
            checkAllSelectedState();
        }
    }

    public void setOnAllSelectStateChangeListener(OnAllSelectStateChangeListener onAllSelectStateChangeListener2) {
        this.onAllSelectStateChangeListener = onAllSelectStateChangeListener2;
    }

    public void setSelectState(MimojiEmoticonInfo mimojiEmoticonInfo, int i) {
        if (getDataList() != null && i < getItemCount()) {
            getDataList().set(i, mimojiEmoticonInfo);
            notifyItemChanged(i);
            checkAllSelectedState();
        }
    }
}
