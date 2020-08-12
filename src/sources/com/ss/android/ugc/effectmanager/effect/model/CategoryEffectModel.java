package com.ss.android.ugc.effectmanager.effect.model;

import java.util.List;

public class CategoryEffectModel {
    private String category_key;
    private List<Effect> collection;
    private int cursor;
    private List<Effect> effects;
    private boolean has_more;
    private int sorting_position;
    private String version;

    public String getCategoryKey() {
        return this.category_key;
    }

    public List<Effect> getCollectEffects() {
        return this.collection;
    }

    public int getCursor() {
        return this.cursor;
    }

    public List<Effect> getEffects() {
        return this.effects;
    }

    public int getSortingPosition() {
        return this.sorting_position;
    }

    public String getVersion() {
        return this.version;
    }

    public boolean hasMore() {
        return this.has_more;
    }

    public void setCategoryKey(String str) {
        this.category_key = str;
    }

    public void setCollectEffects(List<Effect> list) {
        this.collection = list;
    }

    public void setCursor(int i) {
        this.cursor = i;
    }

    public void setEffects(List<Effect> list) {
        this.effects = list;
    }

    public void setHasMore(boolean z) {
        this.has_more = z;
    }

    public void setSortingPosition(int i) {
        this.sorting_position = this.sorting_position;
    }

    public void setVersion(String str) {
        this.version = str;
    }
}
