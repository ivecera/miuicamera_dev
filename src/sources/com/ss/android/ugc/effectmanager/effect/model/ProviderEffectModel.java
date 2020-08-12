package com.ss.android.ugc.effectmanager.effect.model;

import java.util.List;

public class ProviderEffectModel {
    private int cursor;
    private boolean has_more;
    private String search_tips;
    private List<ProviderEffect> sticker_list;

    public int getCursor() {
        return this.cursor;
    }

    public String getSearchTips() {
        return this.search_tips;
    }

    public List<ProviderEffect> getStickerList() {
        return this.sticker_list;
    }

    public boolean hasMore() {
        return this.has_more;
    }

    public void setCursor(int i) {
        this.cursor = i;
    }

    public void setHasMore(boolean z) {
        this.has_more = z;
    }

    public void setSearchTips(String str) {
        this.search_tips = str;
    }

    public void setStickerList(List<ProviderEffect> list) {
        this.sticker_list = list;
    }

    public String toString() {
        return "ProviderEffectModel{search_tips='" + this.search_tips + '\'' + ", cursor=" + this.cursor + ", has_more=" + this.has_more + '}';
    }
}
