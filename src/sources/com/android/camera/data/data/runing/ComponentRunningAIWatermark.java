package com.android.camera.data.data.runing;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.List;

public class ComponentRunningAIWatermark extends ComponentData {
    private static String mCurrentType = String.valueOf(0);
    private boolean mIWatermarkEnable;
    private boolean mIsClosed;
    private ArrayList<ComponentDataItem> mList;
    private WatermarkItem mWatermarkItem;

    public ComponentRunningAIWatermark(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
        this.mList = new ArrayList<>();
        this.mIsClosed = true;
        this.mIWatermarkEnable = true;
        this.mWatermarkItem = null;
    }

    public ComponentRunningAIWatermark(DataItemRunning dataItemRunning, ArrayList<ComponentDataItem> arrayList) {
        this(dataItemRunning);
        this.mList = arrayList;
    }

    public boolean getAIWatermarkEnable() {
        try {
            return Boolean.valueOf(getComponentValue(160)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean getAIWatermarkEnable(int i) {
        return (!getAIWatermarkEnable() || !supportTopConfigEntry(i)) ? false : true;
    }

    public String getCurrentType() {
        return mCurrentType;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    public boolean getIWatermarkEnable() {
        return this.mIWatermarkEnable;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mList;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_watermark_key";
    }

    public int getResIcon(boolean z) {
        return z ? R.drawable.ai_watermark_enter_select : R.drawable.ai_watermark_enter_normal;
    }

    public WatermarkItem getWatermarkItem() {
        if (getAIWatermarkEnable()) {
            return this.mWatermarkItem;
        }
        return null;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean needActive() {
        return (!getAIWatermarkEnable() || !getIWatermarkEnable()) ? false : true;
    }

    public void resetAIWatermark(boolean z) {
        setAIWatermarkEnable(z);
        setIWatermarkEnable(z);
        WatermarkConstant.SELECT_KEY = WatermarkConstant.AI_TRIGGER;
        mCurrentType = String.valueOf(0);
        this.mIsClosed = true;
    }

    public void setAIWatermarkEnable(boolean z) {
        setComponentValue(160, String.valueOf(z));
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    public void setCurrentType(String str) {
        mCurrentType = str;
    }

    public void setHasMove(boolean z) {
        WatermarkItem watermarkItem = this.mWatermarkItem;
        if (watermarkItem != null) {
            watermarkItem.setHasMove(z);
        }
    }

    public void setIWatermarkEnable(boolean z) {
        this.mIWatermarkEnable = z;
    }

    public boolean supportTopConfigEntry(int i) {
        return i != 163 ? false : true;
    }

    public void updateLocation(int[] iArr, Rect rect) {
        WatermarkItem watermarkItem = this.mWatermarkItem;
        if (watermarkItem != null) {
            watermarkItem.updateCoordinate(iArr);
            this.mWatermarkItem.setLimitArea(rect);
        }
    }

    public void updateWatermarkItem(WatermarkItem watermarkItem) {
        this.mWatermarkItem = watermarkItem;
    }
}
