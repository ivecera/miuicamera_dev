package com.android.camera.fragment.manually.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ColorImageView;

public class ExtraRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements View.OnClickListener {
    private final BaseFragment mBaseFragment;
    private final ComponentData mComponentData;
    private final int mCurrentMode;
    private final int mItemWidth;
    private final ManuallyListener mManuallyListener;

    public ExtraRecyclerViewAdapter(ComponentData componentData, int i, ManuallyListener manuallyListener, int i2) {
        this(null, componentData, i, manuallyListener, i2);
    }

    public ExtraRecyclerViewAdapter(BaseFragment baseFragment, ComponentData componentData, int i, ManuallyListener manuallyListener, int i2) {
        this.mBaseFragment = baseFragment;
        this.mComponentData = componentData;
        this.mCurrentMode = i;
        this.mManuallyListener = manuallyListener;
        this.mItemWidth = i2;
    }

    /* access modifiers changed from: protected */
    public boolean couldNewValueTakeEffect(String str) {
        return str != null && !str.equals(this.mComponentData.getComponentValue(this.mCurrentMode));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mComponentData.getItems().size();
    }

    public int getValuePosition() {
        String componentValue = this.mComponentData.getComponentValue(this.mCurrentMode);
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (componentValue.equals(this.mComponentData.getItems().get(i).mValue)) {
                return i;
            }
        }
        return -1;
    }

    public void onBindViewHolder(CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        boolean z;
        ComponentDataItem componentDataItem = this.mComponentData.getItems().get(i);
        String str = componentDataItem.mValue;
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setOnClickListener(this);
        ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.setTag(str);
        TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.extra_item_text);
        ColorImageView colorImageView = (ColorImageView) commonRecyclerViewHolder.getView(R.id.extra_item_image);
        int i2 = componentDataItem.mIconRes;
        if (i2 != -1) {
            int i3 = componentDataItem.mIconSelectedRes;
            z = true;
            if (i3 == -1 || i3 == i2) {
                z = false;
            } else {
                colorImageView.setIsNeedTransparent(true, false);
                colorImageView.setColor(0);
            }
            colorImageView.setVisibility(0);
            colorImageView.setImageResource(componentDataItem.mIconRes);
            textView.setTextSize(0, (float) ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getResources().getDimensionPixelSize(R.dimen.grid_setting_item_textSize));
        } else {
            colorImageView.setVisibility(8);
            textView.setTextSize(0, (float) ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getResources().getDimensionPixelSize(R.dimen.grid_text_only_setting_item_textSize));
            z = false;
        }
        if (componentDataItem.mDisplayNameRes != -1) {
            textView.setVisibility(0);
            textView.setText(componentDataItem.mDisplayNameRes);
            colorImageView.setPadding(0, 0, 0, ((RecyclerView.ViewHolder) commonRecyclerViewHolder).itemView.getResources().getDimensionPixelSize(R.dimen.manually_item_image_padding_bottom));
        } else {
            colorImageView.setPadding(0, 0, 0, 0);
            textView.setVisibility(8);
        }
        boolean equals = str.equals(this.mComponentData.getComponentValue(this.mCurrentMode));
        int i4 = equals ? ColorConstant.COLOR_COMMON_SELECTED : ColorConstant.COLOR_COMMON_NORMAL;
        if (!z) {
            colorImageView.setColor(i4);
        } else if (equals) {
            colorImageView.setImageResource(componentDataItem.mIconSelectedRes);
        }
        textView.setTextColor(i4);
    }

    public void onClick(View view) {
        BaseFragment baseFragment = this.mBaseFragment;
        if (baseFragment == null || baseFragment.isEnableClick()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                String str = (String) view.getTag();
                if (couldNewValueTakeEffect(str)) {
                    String componentValue = this.mComponentData.getComponentValue(this.mCurrentMode);
                    this.mComponentData.setComponentValue(this.mCurrentMode, str);
                    notifyDataSetChanged();
                    ManuallyListener manuallyListener = this.mManuallyListener;
                    if (manuallyListener != null) {
                        manuallyListener.onManuallyDataChanged(this.mComponentData, componentValue, str, false, this.mCurrentMode);
                    }
                }
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_manually_extra_item, viewGroup, false);
        inflate.getLayoutParams().width = this.mItemWidth;
        return new CommonRecyclerViewHolder(inflate);
    }
}
