package com.android.camera.data.data;

import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public final class TypeItem<T> {
    @DrawableRes
    public static final int RES_NULL = -1;
    @StringRes
    public static final int STRING_NULL = -1;
    public String mCategory;
    public T mDefaultValue;
    @StringRes
    public int mDescriptionRes;
    @StringRes
    public int mDisplayNameRes;
    @ArrayRes
    public int mEntryArrayRes;
    public boolean mExpandable;
    @DrawableRes
    public int mIconRes;
    @DrawableRes
    public int mIconSelectedRes;
    public String mKeyOrType;
    @ArrayRes
    public int mValueArrayRes;

    public TypeItem(@DrawableRes int i, @StringRes int i2, String str, String str2) {
        this.mIconRes = i;
        this.mDisplayNameRes = i2;
        this.mCategory = str;
        this.mKeyOrType = str2;
    }

    public TypeItem(@StringRes int i, String str, String str2, T t) {
        this.mDisplayNameRes = i;
        this.mCategory = str;
        this.mKeyOrType = str2;
        this.mDefaultValue = t;
    }

    public boolean asBoolean() {
        return this.mDefaultValue instanceof Boolean;
    }

    public boolean asInteger() {
        return this.mDefaultValue instanceof Integer;
    }

    public boolean asString() {
        return this.mDefaultValue instanceof String;
    }

    public T getDefaultValue() {
        return this.mDefaultValue;
    }

    @ArrayRes
    public int getEntryArrayRes() {
        return this.mEntryArrayRes;
    }

    public int getImageResource() {
        return this.mIconRes;
    }

    public int getTextResource() {
        return this.mDisplayNameRes;
    }

    @ArrayRes
    public int getValueArrayRes() {
        return this.mValueArrayRes;
    }

    public TypeItem<T> setDefaultValue(T t) {
        this.mDefaultValue = t;
        return this;
    }

    public TypeItem setDescriptionRes(@StringRes int i) {
        this.mDescriptionRes = i;
        return this;
    }

    public TypeItem setEntryArrayRes(@ArrayRes int i) {
        this.mEntryArrayRes = i;
        return this;
    }

    public TypeItem setExpandable(boolean z) {
        this.mExpandable = z;
        return this;
    }

    public TypeItem setValueArrayRes(@ArrayRes int i) {
        this.mValueArrayRes = i;
        return this;
    }
}
