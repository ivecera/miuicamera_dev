package com.ss.android.ttve.common;

import com.android.camera.Util;

public class TESizei {
    public int height = 1280;
    public int width = Util.LIMIT_SURFACE_WIDTH;

    public TESizei() {
    }

    public TESizei(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TESizei)) {
            return false;
        }
        TESizei tESizei = (TESizei) obj;
        return this.width == tESizei.width && this.height == tESizei.height;
    }

    public int hashCode() {
        return (this.width * 65537) + 1 + this.height;
    }

    public String toString() {
        return this.width + "x" + this.height;
    }
}
