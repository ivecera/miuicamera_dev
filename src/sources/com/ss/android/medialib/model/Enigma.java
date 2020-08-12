package com.ss.android.medialib.model;

import android.support.annotation.Keep;

@Keep
public class Enigma {
    TdPoint[] points;
    String text;
    int type;

    public interface CodeType {
        public static final int DOUYIN_CODE = 2;
        public static final int QRCODE = 1;
    }

    public TdPoint[] getPoints() {
        return this.points;
    }

    public String getText() {
        return this.text;
    }

    public int getType() {
        return this.type;
    }

    public void setPoints(TdPoint[] tdPointArr) {
        this.points = tdPointArr;
    }

    public void setText(String str) {
        this.text = str;
    }

    public void setType(int i) {
        this.type = i;
    }
}
