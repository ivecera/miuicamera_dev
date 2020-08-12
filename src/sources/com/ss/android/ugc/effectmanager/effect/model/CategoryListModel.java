package com.ss.android.ugc.effectmanager.effect.model;

import java.util.List;

public class CategoryListModel {
    private EffectCategoryIconsModel icon;
    private EffectCategoryIconsModel icon_selected;
    private String id;
    private String key;
    private String name;
    private List<String> tags;
    private String tags_updated_at;

    public String getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public void setName(String str) {
        this.name = str;
    }
}
