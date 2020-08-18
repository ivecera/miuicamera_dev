package com.android.camera.module.impl.component;

import java.util.List;

public class TimeSpeedModel {
    long duration;
    double speed;

    public TimeSpeedModel(long j, double d2) {
        this.duration = j;
        this.speed = d2;
    }

    public static int calculateRealTime(int i, double d2) {
        return (int) ((((double) i) * 1.0d) / d2);
    }

    public static long calculateRealTime(long j, double d2) {
        return (long) ((((double) j) * 1.0d) / d2);
    }

    public static long calculateRealTime(List<TimeSpeedModel> list) {
        if (list == null || list.size() <= 0) {
            return 0;
        }
        int i = 0;
        for (TimeSpeedModel timeSpeedModel : list) {
            i = (int) (((long) i) + calculateRealTime(timeSpeedModel.duration, timeSpeedModel.speed));
        }
        return (long) i;
    }

    public long getDuration() {
        return this.duration;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setDuration(int i) {
        this.duration = (long) i;
    }

    public void setSpeed(float f2) {
        this.speed = (double) f2;
    }
}
