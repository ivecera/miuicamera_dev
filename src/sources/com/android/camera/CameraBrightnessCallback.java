package com.android.camera;

public interface CameraBrightnessCallback {
    void adjustBrightnessInAutoMode(float f2);

    int getPreviousBrightnessMode();

    void setBrightness(int i);

    void setPreviousBrightnessMode(int i);
}
