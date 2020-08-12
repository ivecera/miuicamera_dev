package com.android.camera.data.data.config;

import java.util.ArrayList;
import java.util.List;

public class SupportedConfigs {
    private List<TopConfigItem> mConfigs;
    private List<Integer> mSupportedConfigs;

    public SupportedConfigs() {
        this.mSupportedConfigs = new ArrayList();
        this.mConfigs = new ArrayList();
    }

    public SupportedConfigs(int i) {
        this.mConfigs = new ArrayList(i);
        this.mSupportedConfigs = new ArrayList(i);
    }

    public SupportedConfigs add(int i) {
        this.mSupportedConfigs.add(Integer.valueOf(i));
        return this;
    }

    public void add(TopConfigItem topConfigItem) {
        this.mSupportedConfigs.add(Integer.valueOf(topConfigItem.configItem));
        this.mConfigs.add(topConfigItem);
    }

    public void add(int... iArr) {
        for (int i : iArr) {
            this.mSupportedConfigs.add(Integer.valueOf(i));
        }
    }

    public boolean contains(int i) {
        return this.mSupportedConfigs.contains(Integer.valueOf(i));
    }

    public int getConfig(int i) {
        return this.mSupportedConfigs.get(i).intValue();
    }

    public TopConfigItem getConfigItem(int i) {
        return this.mConfigs.get(i);
    }

    public int getConfigsSize() {
        if (this.mConfigs.isEmpty()) {
            return 0;
        }
        List<TopConfigItem> list = this.mConfigs;
        return list.get(list.size() - 1).index + 1;
    }

    public int getLength() {
        return this.mSupportedConfigs.size();
    }

    public void set(int i, TopConfigItem topConfigItem) {
        this.mSupportedConfigs.set(i, Integer.valueOf(topConfigItem.configItem));
        this.mConfigs.set(i, topConfigItem);
    }
}
