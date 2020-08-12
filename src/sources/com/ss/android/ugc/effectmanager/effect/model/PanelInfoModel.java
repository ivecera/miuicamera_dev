package com.ss.android.ugc.effectmanager.effect.model;

import java.util.List;

public class PanelInfoModel {
    private CategoryEffectModel category_effects;
    private List<EffectCategoryModel> category_list;
    private String front_effect_id;
    private EffectPanelModel panel;
    private String rear_effect_id;
    private String version;

    public CategoryEffectModel getCategoryEffectModel() {
        return this.category_effects;
    }

    public List<EffectCategoryModel> getCategoryList() {
        return this.category_list;
    }

    public String getFrontEffectId() {
        return this.front_effect_id;
    }

    public EffectPanelModel getPanel() {
        return this.panel;
    }

    public String getRearEffectId() {
        return this.rear_effect_id;
    }

    public String getVersion() {
        return this.version;
    }

    public void setCategoryEffectModel(CategoryEffectModel categoryEffectModel) {
        this.category_effects = categoryEffectModel;
    }

    public void setCategoryList(List<EffectCategoryModel> list) {
        this.category_list = list;
    }

    public void setFrontEffectId(String str) {
        this.front_effect_id = str;
    }

    public void setPanel(EffectPanelModel effectPanelModel) {
        this.panel = effectPanelModel;
    }

    public void setRearEffectId(String str) {
        this.rear_effect_id = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }
}
