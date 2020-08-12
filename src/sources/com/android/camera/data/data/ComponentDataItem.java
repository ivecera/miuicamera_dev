package com.android.camera.data.data;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import com.android.camera.CameraAppImpl;

public final class ComponentDataItem {
    @DrawableRes
    public static final int RES_NULL = -1;
    @StringRes
    public static final int STRING_NULL = -1;
    @StringRes
    public int mDisplayNameRes;
    public String mDisplayNameStr;
    @DrawableRes
    public int mIconDisabledRes;
    @DrawableRes
    public int mIconRes;
    @DrawableRes
    public int mIconSelectedRes;
    public String mValue;

    public ComponentDataItem(@DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @StringRes int i4, String str) {
        this.mIconRes = i;
        this.mIconSelectedRes = i2;
        this.mIconDisabledRes = i3;
        this.mDisplayNameRes = i4;
        this.mValue = str;
    }

    public ComponentDataItem(@DrawableRes int i, @DrawableRes int i2, @StringRes int i3, String str) {
        this.mIconRes = i;
        this.mIconSelectedRes = i2;
        this.mIconDisabledRes = i;
        this.mDisplayNameRes = i3;
        this.mValue = str;
    }

    public ComponentDataItem(@DrawableRes int i, @DrawableRes int i2, String str, String str2) {
        this.mIconRes = i;
        this.mIconSelectedRes = i2;
        this.mIconDisabledRes = i;
        this.mDisplayNameStr = str;
        this.mValue = str2;
    }

    public String toString() {
        String string = TextUtils.isEmpty(this.mDisplayNameStr) ? CameraAppImpl.getAndroidContext().getString(this.mDisplayNameRes) : this.mDisplayNameStr;
        return "ComponentDataItem{mDisplayName='" + string + '\'' + "mValue='" + this.mValue + '\'' + '}';
    }
}
