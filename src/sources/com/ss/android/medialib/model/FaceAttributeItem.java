package com.ss.android.medialib.model;

import android.support.annotation.Keep;

@Keep
public class FaceAttributeItem {
    String category;
    float label;
    float score;

    public String getCategory() {
        return this.category;
    }

    public float getLabel() {
        return this.label;
    }

    public float getScore() {
        return this.score;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public void setLabel(float f2) {
        this.label = f2;
    }

    public void setScore(float f2) {
        this.score = f2;
    }
}
