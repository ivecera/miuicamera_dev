package com.android.camera.fragment.aiwatermark.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.camera.R;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.aiwatermark.holder.WatermarkHolder;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import java.util.ArrayList;
import java.util.List;

public class WatermarkAdapter extends RecyclerView.Adapter<WatermarkHolder> implements View.OnClickListener {
    private static final String TAG = "WatermarkAdapter";
    protected List<WatermarkItem> mItems = new ArrayList();
    protected LayoutInflater mLayoutInflater;
    private LinearLayoutManager mLayoutManager;
    private int mSelectedIndex;

    public WatermarkAdapter(Context context, LinearLayoutManager linearLayoutManager, int i, List<WatermarkItem> list) {
        this.mItems = list;
        this.mLayoutManager = linearLayoutManager;
        this.mSelectedIndex = i;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    private void notifyItemChanged(int i, int i2) {
        if (i > -1) {
            notifyItemChanged(i);
        }
        if (i2 > -1) {
            notifyItemChanged(i2);
        }
    }

    private void scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
        } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, getItemCount() - 1));
        }
    }

    private void updateASDStatus() {
        ModeProtocol.AIWatermarkDetect aIWatermarkDetect = (ModeProtocol.AIWatermarkDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(254);
        if (aIWatermarkDetect != null) {
            aIWatermarkDetect.resetResult();
        }
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).updateASDForWatermark();
    }

    private void updateSelectItem(WatermarkHolder watermarkHolder, int i) {
        watermarkHolder.updateSelectItem(TextUtils.equals(this.mItems.get(i).getKey(), WatermarkConstant.SELECT_KEY) ? 0 : 4);
    }

    private void updateWatermark(WatermarkItem watermarkItem) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.updateWatermarkSample(watermarkItem);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<WatermarkItem> list = this.mItems;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public List<WatermarkItem> getItems() {
        return this.mItems;
    }

    public int getSelectedIndex() {
        return this.mSelectedIndex;
    }

    public void onBindViewHolder(@NonNull WatermarkHolder watermarkHolder, int i) {
        String str = TAG;
        Log.d(str, "onBindViewHolder i = " + i);
        watermarkHolder.bindHolder(i, this.mItems.get(i));
        updateSelectItem(watermarkHolder, i);
        ((RecyclerView.ViewHolder) watermarkHolder).itemView.setTag(Integer.valueOf(i));
        ((RecyclerView.ViewHolder) watermarkHolder).itemView.setOnClickListener(this);
    }

    public void onClick(View view) {
        int i;
        try {
            i = Integer.parseInt(view.getTag().toString());
        } catch (NumberFormatException unused) {
            Log.e(TAG, "Object can not cast to Integer");
            i = 0;
        }
        WatermarkItem watermarkItem = this.mItems.get(i);
        String key = watermarkItem.getKey();
        if (TextUtils.equals(key, WatermarkConstant.SELECT_KEY)) {
            Log.d(TAG, "user touch the same item. do nothing.");
            return;
        }
        if (TextUtils.equals(key, WatermarkConstant.AI_TRIGGER) || TextUtils.equals(WatermarkConstant.SELECT_KEY, WatermarkConstant.AI_TRIGGER)) {
            updateASDStatus();
        }
        WatermarkConstant.SELECT_KEY = key;
        boolean equals = TextUtils.equals(this.mItems.get(i).getKey(), WatermarkConstant.AI_TRIGGER);
        DataRepository.dataItemRunning().getComponentRunningAIWatermark().setIWatermarkEnable(equals);
        if (equals) {
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).setWatermarkEnable(true);
        } else {
            updateWatermark(watermarkItem);
        }
        onSelected(i, true);
        CameraStatUtils.trackAIWatermarkClick(MistatsConstants.AIWatermark.AI_WATERMARK_SELECT);
        CameraStatUtils.trackAIWatermarkKey(WatermarkConstant.SELECT_KEY);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public WatermarkHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        String str = TAG;
        Log.d(str, "onCreateViewHolder i = " + i);
        return new WatermarkHolder(this.mLayoutInflater.inflate(R.layout.watermark_recyclerview_item, viewGroup, false));
    }

    public void onSelected(int i, boolean z) {
        int i2 = this.mSelectedIndex;
        if (i2 != i) {
            this.mSelectedIndex = i;
            if (z) {
                scrollIfNeed(i);
                notifyItemChanged(i2, this.mSelectedIndex);
                return;
            }
            notifyDataSetChanged();
        }
    }
}
