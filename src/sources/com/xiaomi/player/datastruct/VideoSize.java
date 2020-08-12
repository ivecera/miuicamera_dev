package com.xiaomi.player.datastruct;

public class VideoSize {
    public float video_height;
    public float video_width;

    public VideoSize(float f2, float f3) {
        this.video_height = f2;
        this.video_width = f3;
    }

    public String toString() {
        return "video_height=" + this.video_height + "," + "video_width=" + this.video_width;
    }
}
