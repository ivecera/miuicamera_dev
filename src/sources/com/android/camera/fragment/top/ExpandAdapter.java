package com.android.camera.fragment.top;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.ui.ScrollTextview;
import java.util.ArrayList;
import java.util.List;

public class ExpandAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements View.OnClickListener {
    private ComponentData mComponentData;
    private int mCurrentMode = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode();
    private String mCurrentValue = this.mComponentData.getComponentValue(this.mCurrentMode);
    private List<ComponentDataItem> mDatas;
    private ExpandListener mExpandListener;
    private int mMaxWidthViewItem;

    public interface ExpandListener {
        ImageView getTopImage(int i);

        void onExpandValueChange(ComponentData componentData, String str, String str2);
    }

    public ExpandAdapter(ComponentData componentData, ExpandListener expandListener) {
        this.mComponentData = componentData;
        this.mDatas = new ArrayList(componentData.getItems());
        this.mExpandListener = expandListener;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<ComponentDataItem> list = this.mDatas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (this.mDatas == null) {
            return 0;
        }
        return this.mComponentData.getDisplayTitleString();
    }

    public void onBindViewHolder(CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        ComponentDataItem componentDataItem = this.mDatas.get(i);
        final TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.text);
        String str = componentDataItem.mValue;
        textView.setOnClickListener(this);
        textView.setTag(str);
        String string = ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getResources().getString(componentDataItem.mDisplayNameRes);
        int dimensionPixelSize = ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getResources().getDimensionPixelSize(R.dimen.top_expanded_selected_padding_horizontal);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getLayoutParams();
        int measureText = (dimensionPixelSize * 2) + ((int) textView.getPaint().measureText(string));
        int i2 = this.mMaxWidthViewItem;
        if (measureText > i2) {
            measureText = i2;
        }
        ((ViewGroup.MarginLayoutParams) layoutParams).width = measureText;
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setLayoutParams(layoutParams);
        textView.setText(string);
        if (this.mCurrentValue.equals(componentDataItem.mValue)) {
            textView.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            textView.setBackgroundResource(R.drawable.bg_top_expanded_selected);
            if (Util.isAccessible()) {
                final String string2 = ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getResources().getString(R.string.accessibility_selected);
                textView.postDelayed(new Runnable() {
                    /* class com.android.camera.fragment.top.ExpandAdapter.AnonymousClass1 */

                    public void run() {
                        TextView textView = textView;
                        textView.setContentDescription(((Object) textView.getText()) + ", " + string2);
                        textView.sendAccessibilityEvent(128);
                    }
                }, 100);
                return;
            }
            return;
        }
        textView.setShadowLayer(4.0f, 0.0f, 0.0f, -1073741824);
        textView.setBackgroundResource(R.drawable.bg_top_expanded_normal);
    }

    public void onClick(View view) {
        String str = (String) view.getTag();
        if (str != null && !str.equals(this.mCurrentValue)) {
            this.mComponentData.setComponentValue(this.mCurrentMode, str);
            ExpandListener expandListener = this.mExpandListener;
            if (expandListener != null) {
                expandListener.onExpandValueChange(this.mComponentData, this.mCurrentValue, str);
                this.mExpandListener = null;
            }
            this.mCurrentValue = str;
            notifyDataSetChanged();
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_setting_expanded_text_item, viewGroup, false);
        ((ScrollTextview) inflate.findViewById(R.id.text)).setMaxWidth(this.mMaxWidthViewItem);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) inflate.getLayoutParams();
        int dimensionPixelSize = viewGroup.getContext().getResources().getDimensionPixelSize(R.dimen.expanded_text_item_margin);
        layoutParams.setMargins(dimensionPixelSize, 0, dimensionPixelSize, 0);
        inflate.setLayoutParams(layoutParams);
        return new CommonRecyclerViewHolder(inflate);
    }

    public void setMaxWidthItemView(int i) {
        this.mMaxWidthViewItem = i;
    }
}
