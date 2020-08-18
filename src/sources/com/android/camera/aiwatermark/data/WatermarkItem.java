package com.android.camera.aiwatermark.data;

import android.graphics.Rect;

public class WatermarkItem {
    private int[] mCoordinate;
    private int mCountry;
    private boolean mHasMove;
    private String mKey;
    private Rect mLimitArea;
    private int mLocation;
    private int mResId;
    private int mResRvItem;
    private String mText;
    private int mType;

    public WatermarkItem() {
        this.mKey = "";
        this.mType = 1;
        this.mText = null;
        this.mResId = -1;
        this.mLocation = 1;
        this.mCountry = -1;
        this.mResRvItem = -1;
        this.mCoordinate = new int[4];
        this.mHasMove = false;
    }

    public WatermarkItem(String str, int i, int i2, int i3) {
        this(str, i, null, i2, i3);
    }

    public WatermarkItem(String str, int i, String str2, int i2, int i3) {
        this.mKey = "";
        this.mType = 1;
        this.mText = null;
        this.mResId = -1;
        this.mLocation = 1;
        this.mCountry = -1;
        this.mResRvItem = -1;
        this.mCoordinate = new int[4];
        this.mHasMove = false;
        this.mKey = str;
        this.mType = i;
        this.mText = str2;
        this.mResId = i2;
        this.mLocation = i3;
    }

    public WatermarkItem(String str, int i, String str2, int i2, int i3, int i4) {
        this(str, i, str2, i2, i3);
        this.mCountry = i4;
    }

    public WatermarkItem(String str, int i, String str2, int i2, int i3, int i4, int i5) {
        this(str, i, str2, i2, i3, i4);
        this.mResRvItem = i5;
    }

    public int[] getCoordinate() {
        return this.mCoordinate;
    }

    public int getCountry() {
        return this.mCountry;
    }

    public String getKey() {
        return this.mKey;
    }

    public Rect getLimitArea() {
        return this.mLimitArea;
    }

    public int getLocation() {
        return this.mLocation;
    }

    public int getResId() {
        return this.mResId;
    }

    public int getResRvItem() {
        return this.mResRvItem;
    }

    public String getText() {
        return this.mText;
    }

    public int getType() {
        return this.mType;
    }

    public boolean hasMove() {
        return this.mHasMove;
    }

    public void setCountry(int i) {
        this.mCountry = i;
    }

    public void setHasMove(boolean z) {
        this.mHasMove = z;
    }

    public void setKey(String str) {
        this.mKey = str;
    }

    public void setLimitArea(Rect rect) {
        this.mLimitArea = rect;
    }

    public void setLocation(int i) {
        this.mLocation = i;
    }

    public void setResId(int i) {
        this.mResId = i;
    }

    public void setResRvItem(int i) {
        this.mResRvItem = i;
    }

    public void setText(String str) {
        this.mText = str;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public String toString() {
        return "WatermarkItem: key is " + getKey() + "; type is " + getType() + "; text is " + getText() + "; res id is " + getResId() + "; location is " + getLocation() + "; country is " + getCountry();
    }

    public void updateCoordinate(int[] iArr) {
        this.mCoordinate = iArr;
    }
}
