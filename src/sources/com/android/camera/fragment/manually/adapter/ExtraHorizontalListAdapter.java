package com.android.camera.fragment.manually.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.log.Log;
import com.android.camera.ui.ColorActivateTextView;
import com.android.camera.ui.ColorImageView;
import com.android.camera.ui.HorizontalListView;
import java.util.List;

public class ExtraHorizontalListAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ExtraHorizontalListAdapter";
    private ComponentData mComponentData;
    private int mCurrentMode;
    private String mCurrentValue;
    private boolean mEnableGradient;
    private ManuallyListener mManuallyListener;
    private boolean mOnCreated = true;

    private static class ViewHolder {
        /* access modifiers changed from: private */
        public ColorImageView mColorImageView;
        /* access modifiers changed from: private */
        public ColorActivateTextView mText;

        private ViewHolder() {
        }
    }

    public ExtraHorizontalListAdapter(ComponentData componentData, int i, boolean z, ManuallyListener manuallyListener) {
        this.mComponentData = componentData;
        this.mCurrentMode = i;
        this.mEnableGradient = z;
        this.mManuallyListener = manuallyListener;
        this.mCurrentValue = componentData.getComponentValue(this.mCurrentMode);
    }

    private void changeValue(int i) {
        List<ComponentDataItem> items = this.mComponentData.getItems();
        if (items == null || items.isEmpty() || i >= items.size()) {
            String str = TAG;
            Log.d(str, "Error change value, required postion is " + i + " but size is " + items.size());
            return;
        }
        String str2 = items.get(i).mValue;
        if (str2 != null && !str2.equals(this.mCurrentValue)) {
            this.mComponentData.setComponentValue(this.mCurrentMode, str2);
            ManuallyListener manuallyListener = this.mManuallyListener;
            if (manuallyListener != null) {
                manuallyListener.onManuallyDataChanged(this.mComponentData, this.mCurrentValue, str2, false, this.mCurrentMode);
            }
            this.mCurrentValue = str2;
        }
    }

    public int getCount() {
        return this.mComponentData.getItems().size();
    }

    public Object getItem(int i) {
        return this.mComponentData.getItems().get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getValuePosition() {
        int count = getCount();
        for (int i = 0; i < count; i++) {
            if (this.mCurrentValue.equals(this.mComponentData.getItems().get(i).mValue)) {
                return i;
            }
        }
        return -1;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        int i2 = ColorConstant.COLOR_COMMON_SELECTED;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_manually_extra_item, (ViewGroup) null);
            viewHolder = new ViewHolder();
            ColorImageView unused = viewHolder.mColorImageView = (ColorImageView) view.findViewById(R.id.extra_item_image);
            ColorActivateTextView unused2 = viewHolder.mText = (ColorActivateTextView) view.findViewById(R.id.extra_item_text);
            viewHolder.mText.setNormalCor(ColorConstant.COLOR_COMMON_NORMAL);
            viewHolder.mText.setActivateColor(ColorConstant.COLOR_COMMON_SELECTED);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ComponentDataItem componentDataItem = this.mComponentData.getItems().get(i);
        String str = componentDataItem.mValue;
        if (componentDataItem.mIconRes != -1) {
            viewHolder.mColorImageView.setVisibility(0);
            viewHolder.mColorImageView.setImageResource(componentDataItem.mIconRes);
        } else {
            viewHolder.mColorImageView.setVisibility(8);
        }
        viewHolder.mText.setText(!TextUtils.isEmpty(componentDataItem.mDisplayNameStr) ? componentDataItem.mDisplayNameStr : CameraAppImpl.getAndroidContext().getString(componentDataItem.mDisplayNameRes));
        if (!str.equals(this.mCurrentValue)) {
            i2 = -1275068417;
        }
        viewHolder.mColorImageView.setColor(i2);
        return view;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.mOnCreated) {
            this.mOnCreated = false;
            return;
        }
        adapterView.setSelection(i);
        if (this.mEnableGradient || !((HorizontalListView) adapterView).isScrolling()) {
            changeValue(i);
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
