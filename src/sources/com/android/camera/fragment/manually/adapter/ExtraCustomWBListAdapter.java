package com.android.camera.fragment.manually.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import com.android.camera.R;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.data.config.ComponentManuallyWB;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.ui.ColorActivateTextView;
import com.android.camera.ui.ColorImageView;
import com.android.camera.ui.HorizontalListView;

public class ExtraCustomWBListAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    private static final int GAP_K_LONG_VALUE = 500;
    private static final int GAP_K_SHOT_VALUE = 200;
    private static final int MAX_K_VALUE = 8000;
    private static final int MIDDLE_K_VALUE = 6000;
    private static final int MIN_K_VALUE = 2000;
    private int mCurrentMode;
    private boolean mEnableGradient;
    private ManuallyListener mManuallyListener;
    private ComponentManuallyWB mManuallyWB;
    private boolean mOnCreated = true;

    private static class ViewHolder {
        /* access modifiers changed from: private */
        public ColorImageView mColorImageView;
        /* access modifiers changed from: private */
        public ColorActivateTextView mText;

        private ViewHolder() {
        }
    }

    public ExtraCustomWBListAdapter(ComponentManuallyWB componentManuallyWB, int i, boolean z, ManuallyListener manuallyListener) {
        this.mManuallyWB = componentManuallyWB;
        this.mCurrentMode = i;
        this.mEnableGradient = z;
        this.mManuallyListener = manuallyListener;
    }

    private void changeValue(int i) {
        int shotValueCount = getShotValueCount();
        int i2 = i < shotValueCount ? (i * 200) + 2000 : ((i - shotValueCount) * 500) + 6000;
        int customWB = this.mManuallyWB.getCustomWB();
        if (i2 != customWB) {
            this.mManuallyWB.setCustomWB(i2);
            ManuallyListener manuallyListener = this.mManuallyListener;
            if (manuallyListener != null) {
                manuallyListener.onManuallyDataChanged(this.mManuallyWB, String.valueOf(customWB), String.valueOf(i2), true, this.mCurrentMode);
            }
        }
    }

    public int getCount() {
        return getShotValueCount() + getLongValueCount() + 1;
    }

    public Object getItem(int i) {
        return this.mManuallyWB.getItems().get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getLongValueCount() {
        return 4;
    }

    public int getShotValueCount() {
        return 20;
    }

    public int getValuePosition() {
        int customWB = this.mManuallyWB.getCustomWB();
        return customWB < 6000 ? (customWB - 2000) / 200 : ((customWB - 6000) / 500) + getShotValueCount();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
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
        int shotValueCount = getShotValueCount();
        viewHolder.mText.setText(i < shotValueCount ? String.valueOf((i * 200) + 2000) : String.valueOf(((i - shotValueCount) * 500) + 6000));
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
