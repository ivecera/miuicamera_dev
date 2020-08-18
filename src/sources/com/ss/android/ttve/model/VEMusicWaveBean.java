package com.ss.android.ttve.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;

@Keep
public class VEMusicWaveBean {
    private ArrayList<Float> waveBean;

    @Nullable
    public ArrayList<Float> getWaveBean() {
        return this.waveBean;
    }

    public void setWaveBean(@NonNull ArrayList<Float> arrayList) {
        this.waveBean = arrayList;
    }
}
