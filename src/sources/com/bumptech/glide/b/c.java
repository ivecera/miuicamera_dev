package com.bumptech.glide.b;

import android.support.annotation.ColorInt;
import java.util.ArrayList;
import java.util.List;

/* compiled from: GifHeader */
public class c {
    public static final int Gk = 0;
    public static final int Hk = -1;
    @ColorInt
    int[] Ak = null;
    b Bk;
    boolean Ck;
    int Dk;
    int Ek;
    int Fk;
    int Uf = -1;
    @ColorInt
    int bgColor;
    int frameCount = 0;
    final List<b> frames = new ArrayList();
    int height;
    int status = 0;
    int width;

    public int getHeight() {
        return this.height;
    }

    public int getNumFrames() {
        return this.frameCount;
    }

    public int getStatus() {
        return this.status;
    }

    public int getWidth() {
        return this.width;
    }
}
