package com.arcsoft.avatar;

import android.text.TextUtils;
import java.util.ArrayList;

public class BackgroundInfo {
    public static final int RESOLUTION_16_9 = 2;
    public static final int RESOLUTION_4_3 = 1;
    public static final int RESOLUTION_FULL_SIZE = 3;

    /* renamed from: a  reason: collision with root package name */
    private static final String f42a = "background.xml";

    /* renamed from: b  reason: collision with root package name */
    private static final String f43b = "info";

    /* renamed from: c  reason: collision with root package name */
    private static final String f44c = "name";

    /* renamed from: d  reason: collision with root package name */
    private static final String f45d = "count";

    /* renamed from: e  reason: collision with root package name */
    private static final String f46e = "delay";

    /* renamed from: f  reason: collision with root package name */
    private static final String f47f = "crop";
    private static final String g = "cropx";
    private int h;
    private ArrayList<String> i;
    private String j;
    private int k;
    private int l = 1;
    private int[] m = new int[2];
    private int[] n = new int[2];

    public static String getXMLCountTag() {
        return "count";
    }

    public static String getXMLCrop16_9_XY() {
        return g;
    }

    public static String getXMLCrop4_3_XY() {
        return f47f;
    }

    public static String getXMLDelayTag() {
        return f46e;
    }

    public static String getXMLInfoTag() {
        return f43b;
    }

    public static String getXMLName() {
        return f42a;
    }

    public static String getXMLNameTag() {
        return f44c;
    }

    public String getBackGroundPath(int i2) {
        ArrayList<String> arrayList = this.i;
        return (arrayList == null || arrayList.size() <= 0) ? "" : this.i.get(i2);
    }

    public int getCount() {
        return this.k;
    }

    public int[] getCrop16_9_XY() {
        return this.n;
    }

    public int[] getCrop4_3_XY() {
        return this.m;
    }

    public int getDelayMillis() {
        return this.h;
    }

    public String getName() {
        return this.j;
    }

    public int getResolutionMode() {
        return this.l;
    }

    public void setCount(int i2) {
        this.k = i2;
    }

    public void setCrop16_9_XY(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(",");
            for (int i2 = 0; i2 < split.length; i2++) {
                this.n[i2] = Integer.parseInt(split[i2]);
            }
        }
    }

    public void setCrop4_3_XY(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(",");
            for (int i2 = 0; i2 < split.length; i2++) {
                this.m[i2] = Integer.parseInt(split[i2]);
            }
        }
    }

    public void setDelayMillis(int i2) {
        this.h = i2;
    }

    public void setName(String str) {
        this.j = str;
    }

    public void setResolutionMode(int i2) {
        this.l = i2;
    }

    public void setResolution_FullSize_PathList(ArrayList<String> arrayList) {
        this.i = arrayList;
    }
}
