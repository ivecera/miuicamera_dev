package com.android.camera.aiwatermark.data;

public class Region {
    private double mGreatLatitude = 0.0d;
    private double mGreatLongitude = 0.0d;
    private double mLittleLatitude = 0.0d;
    private double mLittleLongitude = 0.0d;

    public Region(double d2, double d3, double d4, double d5) {
        this.mLittleLatitude = d2;
        this.mGreatLatitude = d3;
        this.mLittleLongitude = d4;
        this.mGreatLongitude = d5;
    }

    private boolean checkInScope(double d2, double d3, double d4) {
        if (d4 < d3) {
            d3 = d4;
            d4 = d3;
        }
        return d2 >= d3 && d2 <= d4;
    }

    public boolean isInRegion(double d2, double d3) {
        return checkInScope(d2, this.mLittleLatitude, this.mGreatLatitude) && checkInScope(d3, this.mLittleLongitude, this.mGreatLongitude);
    }
}
