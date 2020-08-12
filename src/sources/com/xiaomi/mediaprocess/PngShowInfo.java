package com.xiaomi.mediaprocess;

public class PngShowInfo {
    public int display_height;
    public int display_width;
    public String png_path = new String();
    public int position_x;
    public int position_y;
    public long start_show_time;
    public long stop_show_time;

    public PngShowInfo() {
    }

    public PngShowInfo(String str, long j, long j2, int i, int i2, int i3, int i4) {
        this.png_path = str;
        this.start_show_time = j;
        this.stop_show_time = j2;
        this.display_width = i;
        this.display_height = i2;
        this.position_x = i3;
        this.position_y = i4;
    }
}
