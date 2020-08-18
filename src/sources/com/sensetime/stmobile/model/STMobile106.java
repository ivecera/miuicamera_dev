package com.sensetime.stmobile.model;

public class STMobile106 {
    int ID;
    float eye_dist;
    float pitch;
    STPoint[] points_array = new STPoint[106];
    STRect rect;
    float roll;
    float score;
    float[] visibility_array = new float[106];
    float yaw;

    public STMobile106(STRect sTRect, float f2, STPoint[] sTPointArr, float f3, float f4, float f5, float f6, int i) {
        this.rect = sTRect;
        this.score = f2;
        this.points_array = sTPointArr;
        this.yaw = f3;
        this.pitch = f4;
        this.roll = f5;
        this.eye_dist = f6;
        this.ID = i;
    }

    public float getEye_dist() {
        return this.eye_dist;
    }

    public int getID() {
        return this.ID;
    }

    public float getPitch() {
        return this.pitch;
    }

    public STPoint[] getPoints_array() {
        return this.points_array;
    }

    public STRect getRect() {
        return this.rect;
    }

    public float getRoll() {
        return this.roll;
    }

    public float getScore() {
        return this.score;
    }

    public float[] getVisibilityArray() {
        return this.visibility_array;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setEye_dist(float f2) {
        this.eye_dist = f2;
    }

    public void setID(int i) {
        this.ID = i;
    }

    public void setPitch(float f2) {
        this.pitch = f2;
    }

    public void setPoints_array(STPoint[] sTPointArr) {
        this.points_array = sTPointArr;
    }

    public void setRect(STRect sTRect) {
        this.rect = sTRect;
    }

    public void setRoll(float f2) {
        this.roll = f2;
    }

    public void setScore(float f2) {
        this.score = f2;
    }

    public void setYaw(float f2) {
        this.yaw = f2;
    }
}
