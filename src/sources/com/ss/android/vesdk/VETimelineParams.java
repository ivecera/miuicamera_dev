package com.ss.android.vesdk;

import android.support.annotation.NonNull;
import java.util.Arrays;

public class VETimelineParams {
    public int[] aTrimIn;
    public int[] aTrimOut;
    private int[] audioFileIndex;
    public String[] audioFilePaths = null;
    public boolean[] enable;
    public ROTATE_DEGREE[] rotate;
    public double[] speed;
    public String[] transitions = null;
    public int[] vTrimIn;
    public int[] vTrimOut;
    public int[] videoFileIndex;
    public String[] videoFilePaths;

    public VETimelineParams(@NonNull String[] strArr) {
        int length = strArr.length;
        this.videoFilePaths = (String[]) strArr.clone();
        this.vTrimIn = new int[length];
        Arrays.fill(this.vTrimIn, 0);
        this.vTrimOut = new int[length];
        Arrays.fill(this.vTrimOut, -1);
        this.aTrimIn = new int[length];
        Arrays.fill(this.aTrimIn, 0);
        this.aTrimOut = new int[length];
        Arrays.fill(this.aTrimOut, -1);
        this.speed = new double[length];
        Arrays.fill(this.speed, 1.0d);
        this.videoFileIndex = new int[length];
        this.audioFileIndex = new int[length];
        this.enable = new boolean[length];
        Arrays.fill(this.enable, true);
        this.rotate = new ROTATE_DEGREE[length];
        Arrays.fill(this.rotate, ROTATE_DEGREE.ROTATE_NONE);
        for (int i = 0; i < length; i++) {
            this.videoFileIndex[i] = i;
            this.audioFileIndex[i] = i;
        }
    }
}
